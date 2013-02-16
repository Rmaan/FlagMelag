package javachallenge.client;

import java.util.ArrayList;

import javachallenge.common.AgentMessage;
import javachallenge.common.Point;
import javachallenge.common.ServerMessage;

public class Team {
	private ArrayList<Integer> scores ;
	private int score ;
	private int id;
	private Point spawnLocation;
	private ArrayList<Agent> agents ;
	private ArrayList<Agent> aliveAgents ;

	void updateAgent(ServerMessage msg){
		respawn(msg.getSpawnedId()) ;
		setScore(msg.getScores());
		for (AgentMessage agentMsg : msg.getAgentMsg()) {
			setAgentMsg(agentMsg);
		}
		setDeadAgent(msg.getDeadAgents());
	}
	
	public Agent getAgentById(int agentId){
		for (Agent agent : agents) {
			if (agent.getId() == agentId)
				return agent ;
		}
		return null ;
	}
	
	private void setDeadAgent(ArrayList<Integer> deadAgents) {
		for (Integer agentId : deadAgents) {
			Agent agent = getAgentById(agentId) ;
			agent.die() ;
			//-----------
			aliveAgents.remove(agent) ;
			//TODO what I Should do after it dies
		}
	}

	private void setAgentMsg(AgentMessage agentMsg) {
		Agent agent = getAgentById(agentMsg.getId());
		agent.updateAgent(agentMsg); 
	}

	private void setScore(ArrayList<Integer> scores) {
		for (Integer score : scores) {
			this.scores.add(score) ;
		}
		this.score = scores.get(this.id) ;
	}

	private void respawn(int spawnedId) {
		if (spawnedId == Agent.noAgent) 
			return ;
		//TODO .... pass this (team) for equal -> id checking in agent (Can be removed :D) #DESIGN DESICISON?!
		Agent agent = new Agent(this, spawnedId, this.spawnLocation) ;
		
		agents.add(agent) ;
		aliveAgents.add(agent) ;
	}
	
	public int getScore(){
		return score ;
	}

	public int getTeamScore(int teamId){
		return scores.get(teamId) ;
	}
	
	public ArrayList<Agent> getAgents(){
		return agents ;
	}

	public ArrayList<Agent> getAliveAgents(){
		ArrayList<Agent> ret = new ArrayList<Agent>();
		for (Agent agent : agents) {
			if (agent.isAlive()) ;
				ret.add(agent) ;
		}
		return ret ;
	}
	
	public Point getSpawnLocation(){
		return spawnLocation ;
	}
	
	public int getId(){
		return id ;
	}
}
