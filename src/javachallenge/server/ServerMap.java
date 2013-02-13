package javachallenge.server;

import java.util.ArrayList;

import javachallenge.common.Map;
import javachallenge.common.Point;

public class ServerMap extends Map {
	private Agent[][] agents = new Agent[getWid()][getHei()];

	public ServerMap(int wid, int hei, int teamCount,
			ArrayList<Point> spawnLocations, ArrayList<Point> flagLocations) {
		super(wid, hei, teamCount, spawnLocations, flagLocations);
	}
	
	public Agent getAgent(Point p){
		return agents[p.x][p.y];
	}

}
