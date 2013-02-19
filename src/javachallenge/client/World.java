package javachallenge.client;

import javachallenge.common.InitMessage;
import javachallenge.common.Point;
import javachallenge.common.ServerMessage;

public class World {
	private int score;
	private int mapWidth, mapHeight;
	private Point spawnLocation;

	public int getScore() {
		return score;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public Point getSpawnLocation() {
		return spawnLocation;
	}

	void update(InitMessage msg) {
		this.mapHeight = msg.getMapHeight();
		this.mapWidth = msg.getMapWidth();
		this.spawnLocation = msg.getSpawnLocation();
	}

	void update(ServerMessage msg) {
		this.score = msg.getScores().get(0);
	}
}
