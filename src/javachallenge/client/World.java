package javachallenge.client;

import java.util.ArrayList;

import javachallenge.common.InitMessage;
import javachallenge.common.Point;
import javachallenge.common.ServerMessage;

/**
 *  General information about the game (map width, height, spawn location, score)
 */
public class World {
	private int mapWidth, mapHeight;
	private ArrayList<Integer> scores;
	private ArrayList<Point> spawnLocations;
	private ArrayList<Point> flagLocations;
	private ArrayList<Integer> flagOwners;
	private int teamId;

	public ArrayList<Integer> getScore() {
		return scores;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public Point getSpawnLocation(int teamId) {
		return (teamId >= 0 && teamId < spawnLocations.size() ? spawnLocations.get(teamId) : null); 
	}
	
	public ArrayList<Point> getFlagLocations() {
		return flagLocations ; 
	}

	void update(InitMessage msg) {
		this.mapHeight = msg.getMapHeight();
		this.mapWidth = msg.getMapWidth();
		this.teamId = msg.getTeamId() ;
		this.spawnLocations = msg.getSpawnLocations();
		this.flagLocations = msg.getFlagLocations();
		//----------------------------------------------
		this.flagOwners = new ArrayList<Integer>();
		for(int i = 0 ; i < flagLocations.size() ; i++)
			flagOwners.add(-1) ;
	}
	
	public ArrayList<Integer> getFlagOwners(){
		return flagOwners;
	}

	void update(ServerMessage msg) {
		this.scores = msg.getScores() ;
		this.flagOwners = msg.getFlagOwners() ;
	}

	public Point getMySpawnLocation() {
		return getSpawnLocation(this.teamId) ;
	}
}
