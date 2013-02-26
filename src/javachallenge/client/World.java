package javachallenge.client;

import java.util.ArrayList;

import javachallenge.common.InitMessage;
import javachallenge.common.Point;
import javachallenge.common.ServerMessage;

/**
 *  General information about the game (map width, height, spawn location, score)
 */
public class World {
	private int score;
	private int mapWidth, mapHeight;
	private ArrayList<Point> spawnLocation;
	private int teamId;

	public int getScore() {
		return score;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public Point getSpawnLocation(int teamId) {
		return (teamId >= 0 && teamId < spawnLocation.size() ? spawnLocation.get(teamId) : null); 
	}

	void update(InitMessage msg) {
		this.mapHeight = msg.getMapHeight();
		this.mapWidth = msg.getMapWidth();
		this.teamId = msg.getTeamId() ;
		this.spawnLocation = msg.getSpawnLocations();
	}

	void update(ServerMessage msg) {
		this.score = msg.getScores().get(0);
	}

	public Point getMySpawnLocation() {
		return getSpawnLocation(this.teamId) ;
	}
}
