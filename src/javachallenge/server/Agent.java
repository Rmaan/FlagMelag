package javachallenge.server;

import javachallenge.common.Point;

public class Agent {
	private Point location;
	private boolean isAlive;
	private static int refCount = 0;
	private int id;
	private int teamId;
	
	public Agent(int teamId) {
		this.teamId = teamId;
		isAlive = true;
		this.id = refCount++;
	}
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getId() {
		return id;
	}
	
	public int getTeamId(){
		return teamId;
	}
	
}
