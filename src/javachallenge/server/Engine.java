package javachallenge.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javachallenge.IllegalAgentException;
import javachallenge.common.Action;
import javachallenge.common.ActionType;
import javachallenge.common.AgentMessage;
import javachallenge.common.BlockType;
import javachallenge.common.CycleAction;
import javachallenge.common.Direction;
import javachallenge.common.InitMessage;
import javachallenge.common.Point;
import javachallenge.common.PowerUp;
import javachallenge.common.PowerUpPoint;
import javachallenge.common.ServerMessage;
import javachallenge.common.VisionPoint;
import javachallenge.graphics.GraphicClient;
import javachallenge.graphics.GraphicClient.DuplicateMemberException;
import javachallenge.graphics.GraphicClient.OutOfMapException;
import javachallenge.graphics.util.Position;

public class Engine {
	private static final int FLAG_POINT = 1;
	private static final int GAME_CYCLES = 700;
	private static final int SPAWN_MARGIN = 15;
	private static final int SPAWN_LOW_PERIOD = 20;
	private static final int SPAWN_NORM_PERIOD = 5;
	private static final int MAX_SCORE = 2000;
	private static final int POWERUP_GEN_PERIOD = 50;
	
	private Map map;
	private int cycle, teamCount;
	private ArrayList<Team> teams = new ArrayList<Team>();
	private boolean gameEnded = false;
	private ArrayList<Agent> deadAgents;
	private ArrayList<ArrayList<Agent>> spawnedAgents;
	private GraphicClient graphicClient;
	private Game game;
	
	private Agent vestAgent = null;
	private int lastPowerUpGen = -1;
	private Agent gotVest = null;
	private Agent lostVest = null;
	
	public boolean gameIsOver() {
		return gameEnded;
	}
	
	public Engine(Game game, GraphicClient graphicClient) {
		this.game = game;
		this.map = game.getMap();
		this.cycle = 0;
		teamCount = map.getTeamCount();
		ArrayList<Point> spawnLoc = map.getSpawnLocations();
		for(int i = 0 ; i < teamCount ; i++){
			teams.add(new Team(spawnLoc.get(i), i));
		}
		
		this.graphicClient = graphicClient ;
	}
	
	private void generatePowerups() {
		if(vestAgent != null){
			vestAgent.setHasVest(false);
			lostVest = vestAgent;
			vestAgent = null;
		}
		ArrayList<PowerUpPoint> free = new ArrayList<PowerUpPoint>();
		boolean hasVest = false;
		boolean agentBlock = false;
		for(PowerUpPoint point : game.getPowerups()){
			if(point.getType() == null){
				if(game.getAgent(point.getLocation()) == null){
					free.add(point);
				}
				else{
					agentBlock = true;
				}
			}
			if(point.getType() == PowerUp.SUICIDE_VEST){
				hasVest = true;
			}
		}
		if(free.isEmpty()){
			if(!agentBlock){
				lastPowerUpGen = cycle;
			}
			return;
		}
		
		lastPowerUpGen = cycle;
		Random r = new Random();
		int genLoc = r.nextInt(free.size());
		int type = hasVest ? 1 : r.nextInt(PowerUp.values().length);
		PowerUpPoint toGen = free.get(genLoc);
		toGen.setType(PowerUp.values()[type]);
		graphicClient.addPowerUp(toGen.getId(), toGen.getLocation(), PowerUp.values()[type]);
//		System.out.println(game.getPowerups());
	}
	
	//methods for running the game
	
	public void beginStep(){
		deadAgents = new ArrayList<Agent>();
		spawnedAgents = new ArrayList<ArrayList<Agent>>();
		gotVest = null;
		lostVest = null;
		for(int i = 0 ; i < teamCount ; i++){
			spawnedAgents.add(new ArrayList<Agent>());
		}
		if(lastPowerUpGen == -1 || cycle - lastPowerUpGen > POWERUP_GEN_PERIOD || (cycle - lastPowerUpGen) % POWERUP_GEN_PERIOD == 0){
			generatePowerups();
		}
	}
	
	

	public void teamStep(ArrayList<Action> actions){
		if(gameEnded){
			return;
		}
		
		if(actions == null){
			graphicClient.log("client manipluation : nell action array sent");
		}
		
		if(cycle % 2 == 1){
			handleMoves(actions);
		}
		else{
			//handle attacks
			HashMap<Integer, ArrayList<Integer>> attackNum = new HashMap<Integer, ArrayList<Integer>>();
			ArrayList<Action> validActions = new ArrayList<Action>();
			for(Action action : actions){
				if(action == null){
					graphicClient.log("client manipluation : null action");
					continue;
				}
				if(action.getType() == ActionType.NONE || action.getType() != ActionType.ATTACK){
					graphicClient.log("move in wrong cycle");
					continue;
				}
				try{
					Agent agent = getAgent(action.getTeamId(), action.getId());
					
					if(action.getDir() != Direction.NONE){
						graphicClient.attack(agent.getId(), agent.getTeamId(), action.getDir());
					}
					
					Point dest = agent.getLocation().applyDirection(action.getDir());
					Agent opAgent = game.getAgent(dest);
					if(opAgent != null){
						int opId = opAgent.getId();
						if(agent.getTeamId() == opAgent.getTeamId()){
//							System.out.println("Attack to itself");
							continue;
						}
						if(attackNum.get(opId) == null){
							attackNum.put(opId, new ArrayList<Integer>());
						}
						if(attackNum.get(agent.getId()) == null){
							attackNum.put(agent.getId(), new ArrayList<Integer>());
						}
						ArrayList<Integer> tmp = attackNum.get(opId);
						tmp.add(agent.getTeamId());
						attackNum.put(opId, tmp);
						validActions.add(action);
					}
					
					//Handle spawn point attacks
//					if(getSpawnLocationTeam(dest) != -1){
//						spawnAttacks[getSpawnLocationTeam(dest)][agent.getTeamId()]++;
//					}
				}
				catch(Exception e){
					graphicClient.log("Bad Agent : Team " + action.getTeamId() + " id : " + action.getId());
//					e.printStackTrace();
					continue;
				}
				
			}
			for(Action action : validActions){
				if(action.getType() == ActionType.NONE || action.getType() != ActionType.ATTACK){
					continue;
				}
				try{
					Agent agent = getAgent(action.getTeamId(), action.getId());
					Point dest = agent.getLocation().applyDirection(action.getDir());
					Agent opAgent = game.getAgent(dest);
					int firstTeamAttacks = Collections.frequency(attackNum.get(opAgent.getId()), agent.getTeamId());
					int secondTeamAttacks = Collections.frequency(attackNum.get(agent.getId()), opAgent.getTeamId());
//					System.out.println(firstTeamAttacks + ", " + secondTeamAttacks);
					if(firstTeamAttacks >= secondTeamAttacks){
						if(!deadAgents.contains(opAgent)){
							deadAgents.add(opAgent);
							if(opAgent.hasVest()){
								vestAgent = null;
								for(Direction dir : Direction.values()){
//									System.out.println("Su agent killed");
									Agent suAgent = game.getAgent(dest.applyDirection(dir));
									if(suAgent != null && !deadAgents.contains(suAgent)){
										deadAgents.add(suAgent);
									}
								}
							}
//							System.out.println("KILLED " + opAgent.getTeamId());
						}
//						opAgent.setAlive(false);
				
					}
				}
				catch (Exception e) {
					graphicClient.log("Bad Agent : Team " + action.getTeamId() + " id : " + action.getId());
					e.printStackTrace();
					continue;
				}
			}
			for(Agent agent : deadAgents){
				agent.setAlive(false);
				Team t = getTeam(agent.getTeamId());
				t.killAgent(agent);
				game.setAgent(agent.getLocation(), null);
				graphicClient.die(agent.getId());
			}
		}
		
		for(Flag flag : game.getFlags()){
			Agent agent = game.getAgent(flag.getLocation());
			Team team = (agent == null ? null : teams.get(agent.getTeamId()));
			flag.step(team);
//			System.out.println(flag);
			if (flag.getTmp() == null || !flag.getTmp().equals(flag))
				graphicClient.setFlagStatus(flag.getId() + 1, (team == null || team == flag.getOwner()) ? -1 : team.getId(), flag.getPercent(), flag.getOwner() == null ? -1 : flag.getOwner().getId());
			flag.setTmp(flag.clone());
//			if (flag == game.getFlags().get(game.getFlags().size() - 1)) {
//				System.out.println("flagdbg " + flag + " " + team);
//			}
		}
//		System.out.println("\n-----------------------------------");
	}
	

	public int getCycle() {
		return cycle;
	}

	public void endStep(){
		respawn();
		updateScores();
		cycle++;
		//----------------------------------------------
		int maxScore = -1;
		for(Team t : teams){
			maxScore = Math.max(maxScore, t.getScore());
		}
		if(cycle >= GAME_CYCLES || maxScore >= MAX_SCORE){
			gameEnded = true;
		}
	}
	
	//method to move the agents and check for the destination to be empty
	
	private boolean moveAgent(Action action){
		ActionType actionType = action.getType();
		if(actionType == ActionType.NONE){
			return false;
		}
		
		Direction dir = action.getDir() ;
		if(dir == Direction.NONE){
			return false;
		}
		Agent agent = null ;
		
		try {
			agent = getAgent(action.getTeamId(), action.getId());
		} catch (IllegalAgentException e) {
			e.printStackTrace();
			return false;
		}
		
		Point dest = agent.getLocation().applyDirection(dir);
		if(actionType == ActionType.MOVE){
			//System.err.println("Dest is : " + dest.x + " " + dest.y + " - " + map.isInsideMap(dest));
			if(map.isInsideMap(dest) && map.getBlockType(dest) == BlockType.GROUND && !occupied(dest)){
				PowerUpPoint puPoint = game.getPowerUpPoint(dest);
				if(puPoint != null && puPoint.getType() != null){
					PowerUp pType = puPoint.getType();
//					System.out.println("Got POwerUP " + puPoint + " by agent " + agent);
					puPoint.setType(null);
					graphicClient.hidePowerUp(puPoint.getId());
					if(pType == PowerUp.SUICIDE_VEST && vestAgent == null){
						agent.setVest();
						vestAgent = agent;
						gotVest = agent;
					}
					else if(pType == PowerUp.DUPLICATE){
						Agent newAgent = teams.get(agent.getTeamId()).addAgent();
						newAgent.setLocation(dest);
						game.spawnAgent(newAgent) ;
						spawnedAgents.get(agent.getTeamId()).add(newAgent);
						
						
						Position p = new Position(newAgent.getLocation().x, newAgent.getLocation().y);
						Integer id = new Integer(newAgent.getId()) ;
		 				try {
							graphicClient.spawn(id, newAgent.getTeamId(), p);
						} catch (OutOfMapException e) {
							e.printStackTrace();
						} catch (DuplicateMemberException e) {
							e.printStackTrace();
						}
		 				return true;
					}
				}
				game.moveAgent(agent, agent.getLocation(), dest) ;
				agent.setLocation(dest);
				Integer id = new Integer(agent.getId()) ;
				graphicClient.move(id, agent.getTeamId(), dir) ;
				
				return true;
			}
		}
		return false;
	}
	
	//returns the specified agent, checking for it to exist and to be alive
	
	private Agent getAgent(int teamId, int id) throws IllegalAgentException{
		Agent agent = teams.get(teamId).getAgent(id);
		if(agent == null || !agent.isAlive()){
			throw new IllegalAgentException();
		}
		return agent;
	}
	
	//checks if the given location is occupied by an agent, a flag or a spawned point
	private boolean occupied(Point p){
		return (game.getAgent(p) !=  null /*|| getSpawnLocationTeam(p) != -1*/);
	}
	
	//returns the id of the team for which the given location is the spawn point, -1 if it is not.
	private int getSpawnLocationTeam(Point p){
		for(Team t : teams){
			if(p.equals(t.getSpawnLocation())){
				return t.getId();
			}
		}
		return -1;
	}
	 
	private void respawn(){
//		System.out.println("respawn");
		for(Team team : teams){
			int spawnRate = (team.getAgents().size() >= SPAWN_MARGIN ? SPAWN_LOW_PERIOD : SPAWN_NORM_PERIOD);
			int lastSpawned = team.getLastSpawned();
			if(spawnRate > 0 && (lastSpawned < 0 || cycle - lastSpawned > spawnRate || (cycle - lastSpawned) % spawnRate == 0) 
					&& game.getAgent(team.getSpawnLocation()) == null && team.isActiveSpawnPoint()){
//				System.out.println("Spawning for team : " + team.getId());
				Agent newAgent = team.addAgent();
				game.spawnAgent(newAgent) ;
				spawnedAgents.get(team.getId()).add(newAgent);
				team.setLastSpawned(cycle);
				
				
				Position p = new Position(newAgent.getLocation().x, newAgent.getLocation().y);
				Integer id = new Integer(newAgent.getId()) ;
 				try {
					graphicClient.spawn(id, newAgent.getTeamId(), p);
				} catch (OutOfMapException e) {
					e.printStackTrace();
				} catch (DuplicateMemberException e) {
					e.printStackTrace();
				}
			}
//			else{
//				spawnedAgents.add(null);
//			}
		}
	}
	
	//updates the scores of teams
	private void updateScores(){
		for(Flag flag : game.getFlags()){
			Team owner = flag.getOwner();
			if(owner != null){
				owner.updaetScore(FLAG_POINT);
			}
		}
		//-----------------------
		for (Team t : teams) {
			//TODO fill the ratio correct!
			graphicClient.setScore(t.getId(), t.getScore(), ((double)t.getScore())/MAX_SCORE) ;
		}
		graphicClient.setTime(cycle) ;
	}
	
	private ArrayList<Integer> getScores(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i = 0 ; i < teamCount ; i++){
			result.add(teams.get(i).getScore());
		}
		return result;
	}
	
	private ArrayList<AgentMessage> getAgentMessages(int teamId){
		ArrayList<AgentMessage> result = new ArrayList<AgentMessage>();
		for(Agent agent : teams.get(teamId).getAgents()){
			if(!agent.isAlive()){
				continue;
			}
			//------------------------------------------------------------------
			Point loc = agent.getLocation();
			VisionPoint[] visions = new VisionPoint[Direction.values().length] ;
			for (Direction dir : Direction.values()) {
				visions[dir.ordinal()] = game.getVision(loc.applyDirection(dir)) ; 
			}
			result.add(new AgentMessage(agent.getId(), loc, visions));
		}
		return result;
	}
	
	private ArrayList<Integer> getDeadAgents(int teamId){
		ArrayList<Integer> deads = new ArrayList<Integer>();
		for(Agent agent : deadAgents){
			if(agent.getTeamId() == teamId){
				deads.add(agent.getId());
			}
		}
		return deads;
	}
	
	public ArrayList<InitMessage> getInitialMessage() {
		ArrayList<InitMessage> msgs = new ArrayList<InitMessage>();

		for (Team t: teams) {
			InitMessage msg = new InitMessage(t.getId(), this.map);
			msgs.add(msg);
		}
		return msgs;
	}
	
	public ArrayList<ServerMessage> getStepMessage() {
		ArrayList<ServerMessage> msgs = new ArrayList<ServerMessage>();

		ArrayList<Integer> scores = getScores();
		
		for (Team t: teams) {
			ServerMessage msg = new ServerMessage();
			
			ArrayList<Integer> spawnedIds = new ArrayList<Integer>();
			for(Agent agent : spawnedAgents.get(t.getId())){
				spawnedIds.add(agent.getId());
			}
			msg.setSpawnedId(spawnedIds);
			msg.setScores(scores);
			msg.setDeadAgents(getDeadAgents(t.getId()));
			msg.setAgentMsg(getAgentMessages(t.getId()));
			msg.setGameEnded(this.gameIsOver());
			msg.setCycleAction((cycle % 2 == 1) ? CycleAction.MOVE_CYCLE : CycleAction.ATTACK_CYCLE);
			msg.setFlagOwners(game.getFlagOwners()) ;
			msg.setPowerups(game.getPowerups());
			if(gotVest != null){
				msg.setGotVest(gotVest.getTeamId() == t.getId() ? gotVest.getId() : Agent.noAgent);
			}
			else{
				msg.setGotVest(Agent.noAgent);
			}
			if(lostVest != null){
				msg.setLostVest(lostVest.getTeamId() == t.getId() ? lostVest.getId() : Agent.noAgent);
			}
			else{
				msg.setLostVest(Agent.noAgent);
			}
			msgs.add(msg);
		}
		
		return msgs;
	}

	public Team getTeam(int i) {
		return teams.get(i);
	}
	
	
	private void handleMoves(ArrayList<Action> actions){
		ArrayList<Integer> out = new ArrayList<Integer>();
		ArrayList<Action> outAct = new ArrayList<Action>();
		ArrayList<ArrayList<Integer>> firstIn = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> in = new ArrayList<Integer>();
		
		int nodeNum = map.getWid() * map.getHei();
		boolean[] seen = new boolean[nodeNum];
		
		for(int i = 0 ; i < nodeNum ; i++){
			out.add(-1);
			firstIn.add(new ArrayList<Integer>());
			outAct.add(null);
			in.add(-1);
		}
		
		for(Action act : actions){
			if(act == null){
				graphicClient.log("client manipulation : null action");
				continue;
			}
			if(act.getType() == ActionType.NONE || act.getType() != ActionType.MOVE){
				continue;
			}
			Agent agent = null;
			try {
				agent = getAgent(act.getTeamId(), act.getId());
			} catch (IllegalAgentException e) {
				graphicClient.log("Illegal Agent Id from team " + act.getTeamId());
				continue ;
			}
			//--------------------------------------------------
			Point loc = agent.getLocation();
			Point dest = loc.applyDirection(act.getDir());
			if(map.isInsideMap(dest)){
				int locNum = getNodeNum(loc);
				int destNum = getNodeNum(dest);
				out.set(locNum, destNum);
				outAct.set(locNum, act);
				firstIn.get(destNum).add(locNum);
			}
		}
		
		for(int i = 0 ; i < firstIn.size() ; i++){
			ArrayList<Integer> list = firstIn.get(i);
			if(list.size() > 1){
				Random r = new Random();
				int randNei = list.get(r.nextInt(list.size()));
				in.set(i, randNei);
				for(Integer integ : list){
					if(integ != randNei){
						out.set(integ, -1);
						outAct.set(integ, null);
					}
				}
			}
			else if(list.size() == 1){
				in.set(i, list.get(0));
			}
		}
		ArrayList<Integer> routeStart = new ArrayList<Integer>();
		for(int i = 0 ; i < in.size() ; i++){
			if(in.get(i) == -1){
				routeStart.add(i);
			}
		}
//		System.out.println("---------Routes--------");
		
		for(int i = 0 ; i < routeStart.size() ; i++){
			//find route
			ArrayList<Action> routeActs = new ArrayList<Action>();
			int node = routeStart.get(i);
			while(out.get(node) >= 0){
				routeActs.add(outAct.get(node));
				seen[node] = true;
				node = out.get(node);
			}
			for(int j = routeActs.size() - 1 ; j >= 0 ; j--){
				moveAgent(routeActs.get(j));
			}
		}
		
		for(int i = 0 ; i < nodeNum ; i++){
			ArrayList<Action> cycleActs = new ArrayList<Action>();
			if(!seen[i]){
				int node = i;
				while(!seen[node] && out.get(node) >= 0){
					seen[node] = true;
					cycleActs.add(outAct.get(node));
					node = out.get(node);
				}
			}
			if(cycleActs.size() > 2){
				Action firstAct = cycleActs.get(0);
				Agent agent = null ;
				try {
					agent = getAgent(firstAct.getTeamId(), firstAct.getId()) ;
				} catch (IllegalAgentException e) {
					e.printStackTrace();
					throw new RuntimeException() ;
				}
				game.setAgent(agent.getLocation(), null);
				for(int j = cycleActs.size() - 1; j > 0; j--){
					moveAgent(cycleActs.get(j));
				}
				
				Agent a = null ;
				try {
					a = getAgent(firstAct.getTeamId(), firstAct.getId());
				} catch (IllegalAgentException e) {
					e.printStackTrace();
					throw new RuntimeException() ;
				}
				Point dest = a.getLocation().applyDirection(firstAct.getDir());
				a.setLocation(dest);
				game.setAgent(dest, a);
				Integer id = new Integer(a.getId()) ;
				graphicClient.move(id, a.getTeamId(), firstAct.getDir()) ;
			}
		}
	}
	
	private int getNodeNum(Point p){
		return p.y * map.getWid() + p.x;
	}
}

