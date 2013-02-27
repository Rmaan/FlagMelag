package javachallenge.server;

import javachallenge.common.Point;

public class Agent {
	private Point location;
	private boolean isAlive;
	private static int refCount = 0;
	public static int noAgent = -1 ;
	
	private int id;
	private int teamId;
	
	private boolean hasVest = false;
	static final int VEST_REM_TIME = 700;
	private int vestRemTime;
	
	public Agent(int teamId, Point location) {
		this.teamId = teamId;
		isAlive = true;
		this.location = location ;
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

	@Override
	public String toString() {
		return "Agent [isAlive=" + isAlive + ", id=" + id + ", teamId="
				+ teamId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agent other = (Agent) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void setVest(){
		hasVest = true;
		vestRemTime = VEST_REM_TIME;
	}
	
	public void setHasVest(boolean hasVest){
		this.hasVest = hasVest;
	}
	
	public void updateVestRemTime(){
		vestRemTime--;
		if(vestRemTime == 0){
			hasVest = false;
		}
	}
	
	public boolean hasVest(){
		return hasVest;
	}
	
	public int getVestRemTime(){
		return vestRemTime;
	}
}
