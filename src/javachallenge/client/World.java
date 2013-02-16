package javachallenge.client;

import java.util.ArrayList;

import javachallenge.common.InitMessage;
import javachallenge.common.Map;

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
}
