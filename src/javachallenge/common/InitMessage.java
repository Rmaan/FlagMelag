package javachallenge.common;

import java.io.Serializable;

public class InitMessage implements Serializable {
	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public Point getSpawnLocation() {
		return spawnLocation;
	}

	private static final long serialVersionUID = -8951571199681260311L;
	
	private int mapWidth, mapHeight;
	private Point spawnLocation;
	
	public InitMessage(Map map) {
		mapWidth = map.getWid();
		mapHeight = map.getHei();
		spawnLocation = map.getSpawnLocations().get(0);
	}
}
