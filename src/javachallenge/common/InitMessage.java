package javachallenge.common;

import java.io.Serializable;
import java.util.ArrayList;

import javachallenge.server.Map;

public class InitMessage implements Serializable {
	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public ArrayList<Point> getSpawnLocations() {
		return spawnLocation;
	}

	private static final long serialVersionUID = -8951571199681260311L;
	
	private int mapWidth, mapHeight;
	private ArrayList<Point> spawnLocation;
	private int teamId ;
	
	public InitMessage(int teamId, Map map) {
		mapWidth = map.getWid();
		mapHeight = map.getHei();
		spawnLocation = map.getSpawnLocations();
		this.teamId = teamId ;
	}

	public int getTeamId() {
		return teamId;
	}
}
