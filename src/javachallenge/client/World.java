package javachallenge.client;

import java.util.ArrayList;

import javachallenge.common.Map;
import javachallenge.common.Point;

public class World {
	private ArrayList<Integer> scores ;
	private Map map ;

	public void setScore(ArrayList<Integer> scores) {
		this.scores = scores ;
	}

	public int getScore(int teamId) {
		return this.scores.get(teamId) ;
	}
	
	public Map getMap(){
		return map ;
	}

	public void setMap(Map map) {
		this.map = map ;
	}

	public Point getSpawnLocation(int teamId) {
		return getMap().getSpawnLocations().get(teamId); 
	}
}
