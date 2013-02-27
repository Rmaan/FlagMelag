package javachallenge.common;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerMessage implements Serializable {
	private static final long serialVersionUID = 4282530502489573811L;
	
	ArrayList<Integer> spawnedId;
	ArrayList<Integer> scores;
	ArrayList<AgentMessage> agentMsg;
	ArrayList<Integer> deadAgents;
	CycleAction cycleAction;
	ArrayList<Integer> flagOwners; 
	
	boolean isGameEnded ;

	
	public ServerMessage() {
		spawnedId = new ArrayList<Integer>();
		scores = new ArrayList<Integer>();
		agentMsg = new ArrayList<AgentMessage>();
	}
	
	public ArrayList<Integer> getSpawnedId() {
		return spawnedId;
	}

	public ArrayList<Integer> getScores() {
		return scores;
	}

	public ArrayList<AgentMessage> getAgentMsg() {
		return agentMsg;
	}

	public void setSpawnedId(ArrayList<Integer> spawnedId) {
		this.spawnedId = spawnedId;
	}

	public void setScores(ArrayList<Integer> scores) {
		this.scores = scores;
	}

	public void setDeadAgents(ArrayList<Integer> deadAgents) {
		this.deadAgents = deadAgents;
	}

	public void setAgentMsg(ArrayList<AgentMessage> agentMsg) {
		this.agentMsg = agentMsg;
	}
	
	public void setCycleAction(CycleAction cycleAction){
		this.cycleAction = cycleAction;
	}
	
	public void addScore(int score){
		scores.add(score);
	}
	
	public void addAgentMsg(AgentMessage msg){
		agentMsg.add(msg);
	}

	public ArrayList<Integer> getDeadAgents() {
		return deadAgents ;
	}
	
	public void setGameEnded(boolean gameEnded){
		isGameEnded = gameEnded ;
	}
	
	public boolean isGameEnded(){
		return isGameEnded ;
	}
	

	public CycleAction getCycleAction() {
		return cycleAction;
	}

	@Override
	public String toString() {
		return "ServerMessage [spawnedId=" + spawnedId + ", scores=" + scores
				+ ", agentMsg=" + agentMsg + ", deadAgents=" + deadAgents
				+ ", isGameEnded=" + isGameEnded + "]";
	}
	
	public void setFlagOwners(ArrayList<Integer> flagOwners){
		this.flagOwners = flagOwners ;
	}

	public ArrayList<Integer> getFlagOwners() {
		return flagOwners;
	}
}
