package javachallenge.common;

import java.io.Serializable;

public class Action implements Serializable{
	private static final long serialVersionUID = 224152328507029973L;
	
	private ActionType type;
	private Direction dir;
	private int id;
	private int teamId;
	
	public Action(ActionType type, Direction dir, int id) {
		this.type = type;
		this.dir = dir;
		this.id = id;
	}
	
	public ActionType getType() {
		return type;
	}

	public Direction getDir() {
		return dir;
	};
	
	public int getId(){
		return id;
	}
	
	public int getTeamId(){
		return teamId;
	}
	
	public void setTeamId(int teamId){
		this.teamId = teamId;
	}
}
