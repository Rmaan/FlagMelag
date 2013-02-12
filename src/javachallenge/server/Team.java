package javachallenge.server;

import java.util.ArrayList;

import javachallenge.NotImplementedException;
import javachallenge.common.Point;

public class Team {
	private int score;
	private ArrayList<Agent> agents;
	private boolean activeSpawnPoint;
	private Point spawnLocation;
	private int id;

	
	public Team(Point spawnLocation, int id){
		
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
		throw new NotImplementedException();
	}
}
