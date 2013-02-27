package javachallenge.server;

import java.util.ArrayList;

import javachallenge.common.Point;

public class Team {
	private int score;
	private ArrayList<Agent> agents = new ArrayList<Agent>();
	private boolean activeSpawnPoint;
	private Point spawnLocation;
	private int id;
	private String name ;
	private int lastSpawned = -1;
	
	public String getName(){
		return (name == null ? "Player " + id : name) ;
	}
	
	public int getLastSpawned() {
		return lastSpawned;
	}

	public void setLastSpawned(int lastSpawned) {
		this.lastSpawned = lastSpawned;
	}

	public Team(Point spawnLocation, int id){
		this.spawnLocation = spawnLocation;
		this.id = id;
		this.activeSpawnPoint = true ;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isActiveSpawnPoint() {
		return activeSpawnPoint;
	}
	public void setActiveSpawnPoint(boolean activeSpawnPoint) {
		this.activeSpawnPoint = activeSpawnPoint;
	}
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	public Point getSpawnLocation() {
		return spawnLocation;
	}
	
	public Agent getAgent(int id){
		for(Agent agent : agents){
			if(agent.getId() == id){
				return agent;
			}
		}
		return null;    
	}
	
	public int getId(){
		return id;
	}
	
	public Agent addAgent(){
		Agent newAgent = new Agent(id, spawnLocation);
		agents.add(newAgent);
		return newAgent;
	}

	public void updaetScore(int i) {
		this.setScore(this.getScore() + i) ;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + "]";
	}

	public void setName(String string) {
		this.name = string ;
	}
	
	void killAgent(Agent agent){
		agents.remove(agent);
	}
	
	void killAgent(int id){
		Agent agent = getAgent(id);
		agents.remove(agent);
	}
	
}
