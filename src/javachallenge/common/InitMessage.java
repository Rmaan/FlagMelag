package javachallenge.common;

public class InitMessage {
	Map map;
	int teamId;
	
	public InitMessage(Map map, int teamId) {
		super();
		this.map = map;
		this.teamId = teamId;
	}

	public Map getMap() {
		return map;
	}

	public int getTeamId() {
		return teamId;
	}
}
