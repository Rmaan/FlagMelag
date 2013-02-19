package javachallenge.client;

import java.util.ArrayList;

import javachallenge.common.Action;
import javachallenge.common.AgentMessage;
import javachallenge.common.ClientMessage;
import javachallenge.common.InitMessage;
import javachallenge.common.ServerMessage;

public abstract class Player {
	private int id ;
	private ArrayList<Agent> agents ;
	private ArrayList<Integer> agentAliveId ;
	private World world ;
	
	public Player(World world) {
		this.world =  world ;
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
		spawn(msg.getSpawnedId()) ;
		agentAliveId = new ArrayList<Integer>() ;
		for (AgentMessage agentMsg : msg.getAgentMsg()) {
			setAgentMsg(agentMsg);
			agentAliveId.add(agentMsg.getId()) ;
		}
	}
	
	public ArrayList<Integer> getAgentIds(){
		return agentAliveId ;
	}
	
	private void setAgentMsg(AgentMessage agentMsg) {
		Agent agent = getAgentById(agentMsg.getId());
		agent.updateAgent(agentMsg); 
	}

	private void spawn(int spawnedId) {
		if (spawnedId == Agent.noAgent) 
			return ;
		Agent agent = new Agent(spawnedId, world.getSpawnLocation()) ;
		agents.add(agent) ;
	}
	
	public ArrayList<Agent> getAgents(){
		return agents ;
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
	
	void beginStep() {
		for (Agent agent: agents) {
			agent.beginStep();
		}
	}
	
	public ClientMessage endStep(){
		ClientMessage clientMsg = new ClientMessage();
		for (Agent agent: agents) {
			Action act = agent.endStep();
			if (act != null)
				clientMsg.addAction(act);
		}
		return clientMsg;
	}
	
	public abstract void step();
	
}
