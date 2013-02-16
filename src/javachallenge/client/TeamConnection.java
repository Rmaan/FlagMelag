package javachallenge.client;

import java.util.ArrayList;

import javachallenge.common.AgentMessage;
import javachallenge.common.InitMessage;
import javachallenge.common.Point;
import javachallenge.common.ServerMessage;

public class TeamConnection {
	private int id ;
	private Point spawnLocation ;
	private ArrayList<Agent> agents ;
	private ArrayList<Integer> agentAliveId ;
	private World world ; 
	
	void initMsg(InitMessage msg){
		world.setMap(msg.getMap()) ;
		this.id = msg.getTeamId() ;
	}
	
	void updateMsg(ServerMessage msg){
		respawn(msg.getSpawnedId()) ;
		setScore(msg.getScores());
		//-------------------------------
		for (AgentMessage agentMsg : msg.getAgentMsg()) {
			setAgentMsg(agentMsg);
		}
		//-------------------------------
		setDeadAgent(msg.getDeadAgents());
		setAliveId(agentAliveId) ;
	}
	
	private void setAliveId(ArrayList<Integer> agentAliveId) {
		this.agentAliveId = agentAliveId ;
	}
	
	public ArrayList<Integer> getAgentAliveId(){
		return agentAliveId ;
	}
	
	private void setDeadAgent(ArrayList<Integer> deadAgents) {
		for (Integer agentId : deadAgents) {
			Agent agent = getAgentById(agentId) ;
			agent.die() ;
		}
	}

	private void setAgentMsg(AgentMessage agentMsg) {
		Agent agent = getAgentById(agentMsg.getId());
		agent.updateAgent(agentMsg); 
	}

	private void setScore(ArrayList<Integer> scores) {
		world.setScore(scores) ;
	}

	private void respawn(int spawnedId) {
		if (spawnedId == Agent.noAgent) 
			return ;
		Agent agent = new Agent(spawnedId, this.spawnLocation) ;
		agents.add(agent) ;
	}
	
	public int getScore(){
		return world.getScore(id) ;
	}

	public int getTeamScore(int teamId){
		return world.getScore(teamId) ;
	}
	
	public ArrayList<Agent> getAgents(){
		return agents ;
	}

	public Point getSpawnLocation(){
		return spawnLocation ;
	}
	
	public int getTeamId(){
		return id ;
	}
	
	public Agent getAgentById(int agentId){
		for (Agent agent : agents) {
			if (agent.getId() == agentId)
				return agent ;
		}
		return null ;
	}
}
