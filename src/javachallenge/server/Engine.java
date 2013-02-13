package javachallenge.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import javachallenge.IllegalAgentException;
import javachallenge.NotImplementedException;
import javachallenge.common.Action;
import javachallenge.common.ActionType;
import javachallenge.common.AgentMessage;
import javachallenge.common.Direction;
import javachallenge.common.InitMessage;
import javachallenge.common.Map;
import javachallenge.common.Point;
import javachallenge.common.ServerMessage;

public class Engine {
	private ServerMap map;
	private int cycle, teamCount;
	private ArrayList<Team> teams;
	private boolean gameEnded = false;
	private ArrayList<Agent> deadAgents;
	private ArrayList<Agent> spawnedAgents;
	
	private final int GAME_CYCLES = 725;
	
	public Engine(ServerMap map) {
		this.map = map;
		this.cycle = 0;
		teamCount = map.getTeamCount();
		ArrayList<Point> spawnLoc = map.getSpawnLocations();
		for(int i = 0 ; i < teamCount ; i++){
			teams.add(new Team(spawnLoc.get(i), i));
		}
	}
	
	//methods for running the game
	
	public void beginStep(){
		deadAgents = new ArrayList<Agent>();
		spawnedAgents = new ArrayList<Agent>();
		for(int i = 0 ; i < teamCount ; i++){
			spawnedAgents.add(new Agent(-1));
		}
	}
	
	public void teamStep(ArrayList<Action> actions){
		if(gameEnded){
			return;
		}
		if(cycle % 2 == 0){
			//handle moves
			Collections.shuffle(actions);
			for(Action action : actions){
				moveAgent(action);
			}
		}
		else{
			//handle attacks
			HashMap<Integer, ArrayList<Integer>> attackNum = new HashMap<Integer, ArrayList<Integer>>();
			int[][] spawnAttacks = new int[teamCount][teamCount];
			for(Action action : actions){
				if(action.getType() == ActionType.NONE || action.getType() != ActionType.ATTACK){
					continue;
				}
				Agent agent = getAgent(action.getTeamId(), action.getId());
				Point dest = agent.getLocation().applyDirection(action.getDir());
				Agent opAgent = map.getAgent(dest);
				if(opAgent != null){
					int opId = opAgent.getId();
					if(attackNum.get(opId) == null){
						attackNum.put(opId, new ArrayList<Integer>());
					}
					ArrayList<Integer> tmp = attackNum.get(opId);
					tmp.add(agent.getTeamId());
					attackNum.put(opId, tmp);
				}
				
				//Handle spawn point attacks
				if(getSpawnLocationTeam(dest) != -1){
					spawnAttacks[getSpawnLocationTeam(dest)][agent.getTeamId()]++;
				}
			}
			for(Action action : actions){
				Agent agent = getAgent(action.getTeamId(), action.getId());
				Point dest = agent.getLocation().applyDirection(action.getDir());
				Agent opAgent = map.getAgent(dest);
				int firstTeamAttacks = Collections.frequency(attackNum.get(agent.getId()), opAgent.getId());
				int secondTeamAttacks = Collections.frequency(attackNum.get(opAgent.getId()), agent.getId());
				if(firstTeamAttacks >= secondTeamAttacks){
					if(opAgent.isAlive()){
						deadAgents.add(opAgent);
					}
					opAgent.setAlive(false);
				}
			}
		}
		cycle++;
		if(cycle >= GAME_CYCLES){
			gameEnded = true;
		}
	}
	
	public void endStep(){
		respawn();
		updateScores();
	}
	
	//method to move the agents and check for the destination to be empty
	
	public void moveAgent(Action action){
		ActionType actionType = action.getType();
		if(actionType == ActionType.NONE){
			return;
		}
		
		Agent agent = getAgent(action.getTeamId(), action.getId());
		Point dest = agent.getLocation().applyDirection(action.getDir());
		if(actionType == ActionType.MOVE){
			if(map.isInsideMap(dest) && !occupied(dest)){
				agent.setLocation(dest);
			}
		}
	}
	
	//returns the specified agent, checking for it to exist and to be alive
	
	public Agent getAgent(int teamId, int id){
		Agent agent = teams.get(teamId).getAgent(id);
		if(agent == null || !agent.isAlive()){
			throw new IllegalAgentException();
		}
		return agent;
	}
	
	//checks if the given location is occupied by an agent, a flag or a spawned point
	public boolean occupied(Point p){
		return (map.getAgent(p) !=  null && !isFlag(p) && getSpawnLocationTeam(p) == -1);
	}
	
	
	//checks if the given location is a flag
	public boolean isFlag(Point p){
		throw new NotImplementedException();
	}
	
	//returns the id of the team for which the given location is the spawn point, -1 if it is not.
	public int getSpawnLocationTeam(Point p){
		for(Team t : teams){
			if(p.equals(t.getSpawnLocation())){
				return t.getId();
			}
		}
		return -1;
	}
	
	
	//respawns an agent for each team if the spawn point is active and not occupied by another agent
	public void respawn(){
		for(Team team : teams){
			if(map.getAgent(team.getSpawnLocation()) == null && team.isActiveSpawnPoint()){
				Agent newAgent = team.addAgent();
				spawnedAgents.set(team.getId(), newAgent);
			}
		}
	}
	
	//updates the scores of teams
	public void updateScores(){
		throw new NotImplementedException();
	}
	
	
	//messages for the network controller to use for updating team connections
	public int getRespawnedAgent(int teamId){
		return spawnedAgents.get(teamId).getId();
	}
	
	public ArrayList<Integer> getScores(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i = 0 ; i < teamCount ; i++){
			result.add(teams.get(i).getScore());
		}
		return result;
	}
	
	public ArrayList<AgentMessage> getAgentMessages(int teamId){
		ArrayList<AgentMessage> result = new ArrayList<AgentMessage>();
		for(Agent agent : teams.get(teamId).getAgents()){
			if(!agent.isAlive()){
				continue;
			}
			Point loc = agent.getLocation();
			int[] agentTeamId = new int[6];
			for(int i = 0 ; i < agentTeamId.length ; i++){
				agentTeamId[i] = -1;
			}
			Direction[] dirs = Direction.values();
			for (int i = 0; i < dirs.length; i++) {
				Agent opAgent = map.getAgent(loc.applyDirection(dirs[i]));
				if(opAgent != null){
					agentTeamId[i] = opAgent.getTeamId();
				}
			}
			result.add(new AgentMessage(agent.getId(), loc, map.getBlockTypes(loc), agentTeamId));
		}
		return result;
	}
	
	public ArrayList<Integer> getDeadAgents(int teamId){
		ArrayList<Integer> deads = new ArrayList<Integer>();
		for(Agent agent : deadAgents){
			if(agent.getTeamId() == teamId){
				deads.add(agent.getId());
			}
		}
		return deads;
	}
	
	public ArrayList<InitMessage> getInitMessage(){
		return null;
	}
}
