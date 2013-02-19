package javachallenge.client;

import java.util.ArrayList;


import javachallenge.common.Action;
import javachallenge.common.AgentMessage;
import javachallenge.common.ClientMessage;
import javachallenge.common.InitMessage;
import javachallenge.common.Point;
import javachallenge.common.ServerMessage;

public class Behrooz {
	private int id ;
	private ArrayList<Agent> agents ;
	private ArrayList<Integer> agentAliveId ;
	private World world ;
	private ClientMessage clientMsg; 
	
	public Behrooz(World world) {
		this.world =  world ;
		//-------------------
		agents = new ArrayList<Agent>() ;
		agentAliveId = null ;
	}
	
	
	public World getWorld(){
		return this.world;
	}
	
	void initMsg(InitMessage msg){
		this.id = 0;
		world.update(msg);
	}
	
	void updateMsg(ServerMessage msg){
		world.update(msg);
		respawn(msg.getSpawnedId()) ;
		//-------------------------------
		agentAliveId = new ArrayList<Integer>() ;
		for (AgentMessage agentMsg : msg.getAgentMsg()) {
			setAgentMsg(agentMsg);
			agentAliveId.add(agentMsg.getId()) ;
		}
		//-------------------------------
//		setDeadAgent(msg.getDeadAgents());
	}
	
	public ArrayList<Integer> getAgentAliveId(){
		return agentAliveId ;
	}
	
//	public ArrayList<Integer> getDeadAgentId(){
//		return deadAgentsId ;
//	}
	
//	private void setDeadAgent(ArrayList<Integer> deadAgentsId) {
//		for (Integer agentId : deadAgentsId) {
//			Agent agent = getAgentById(agentId) ;
//			agent.die() ;
//		}
//		this.deadAgentsId = deadAgentsId ;
//	}

	private void setAgentMsg(AgentMessage agentMsg) {
		Agent agent = getAgentById(agentMsg.getId());
		agent.updateAgent(agentMsg); 
	}

//	private void setScore(ArrayList<Integer> scores) {
//		world.setScore(scores) ;
//	}

	private void respawn(int spawnedId) {
		
		if (spawnedId == Agent.noAgent) 
			return ;
		Agent agent = new Agent(spawnedId, world.getSpawnLocation()) ;
		agents.add(agent) ;
		
	//	System.err.println("Client, Spawn:" + agent.getId());
	}
	
//	public int getScore(){
//		return world.getScore(id) ;
//	}
//
//	public int getTeamScore(int teamId){
//		return world.getScore(teamId) ;
//	}
	
	public ArrayList<Agent> getAgents(){
		return agents ;
	}

//	public Point getSpawnLocation(){
//		return spawnLocation ;
//	}
	
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
	
	void prepareClientMsg(){
		this.clientMsg = new ClientMessage();
	}
	
	public void doAction(Action act){
		for (Action action : this.clientMsg.getActions()) {
			if (action.getId() == act.getId())
				return ;
			//TODO with first choice now :D or not!
		}
		//---------------------------------------------------
		this.clientMsg.addAction(act);
	}
	
	public ClientMessage getClientMsg(){
		return this.clientMsg ;
	}
}
