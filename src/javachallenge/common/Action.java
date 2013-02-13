package javachallenge.common;

public class Action {
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
