package javachallenge.client;

import java.util.ArrayList;

import javachallenge.common.Action;
import javachallenge.common.AgentMessage;
import javachallenge.common.ClientMessage;
import javachallenge.common.CycleAction;
import javachallenge.common.InitMessage;
import javachallenge.common.ServerMessage;

public abstract class Player {
	private int id ;
	private ArrayList<Agent> agents ;
	private ArrayList<Integer> agentAliveId ;
	private World world ;
	private boolean timedOut = false;
	private CycleAction cycleAction;
	
	public Player() {
		agents = new ArrayList<Agent>() ;
		agentAliveId = null ;
	}
	
	void setWorld(World world){
		this.world = world ;
	}
	
	public World getWorld(){
		return this.world;
	}
	
	void initMsg(InitMessage msg){
		this.id = msg.getTeamId();
		world.update(msg);
	}
	
	void updateMsg(ServerMessage msg){
		world.update(msg);
		spawn(msg.getSpawnedId()) ;
		agentAliveId = new ArrayList<Integer>() ;
		cycleAction = msg.getCycleAction();
		for (AgentMessage agentMsg : msg.getAgentMsg()) {
			setAgentMsg(agentMsg);
			agentAliveId.add(agentMsg.getId()) ;
		}
	}
	
	/**
	 * @return ID of your agents
	 */
	public ArrayList<Integer> getAgentIds(){
		return agentAliveId ;
	}
	
	private void setAgentMsg(AgentMessage agentMsg) {
		Agent agent = getAgentById(agentMsg.getId());
		agent.updateAgent(agentMsg); 
	}

	void spawn(int spawnedId) {
		if (spawnedId == Agent.noAgent) 
			return ;
		Agent agent = new Agent(spawnedId, world.getMySpawnLocation()) ;
		agents.add(agent) ;
	}
	
	public ArrayList<Agent> getAgents(){
		return agents ;
	}

	public int getTeamId(){
		return id ;
	}
	
	/**
	 * @return Agent with specified id or null if wrong id
	 */
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
	
	ClientMessage endStep(){
		ClientMessage clientMsg = new ClientMessage();
		for (Agent agent: agents) {
			Action act = agent.endStep();
			if (act != null){
				act.setTeamId(id);
				clientMsg.addAction(act, id);
			}
		}
		return clientMsg;
	}
	
	public abstract void step();
	
	void setTimedOut(boolean timedOut){
		this.timedOut = timedOut;
	}
	
	public boolean isTimedOut(){
		return timedOut;
	}

	public CycleAction getCycleAction() {
		return cycleAction;
	}
	
}
