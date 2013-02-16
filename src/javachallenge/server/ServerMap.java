package javachallenge.server;

import java.util.ArrayList;

import javachallenge.common.BlockType;
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
	
	private static ServerMap sampleMap;
	
	static {
		ArrayList<Point> spawnLoc = new ArrayList<Point>();
		spawnLoc.add(new Point(2, 2));
		ArrayList<Point> flagLoc = new ArrayList<Point>();
		flagLoc.add(new Point(5, 1));
		flagLoc.add(new Point(4, 4));
		sampleMap = new ServerMap(10, 10, 1, spawnLoc, flagLoc);
		sampleMap.setBlockType(new Point(8, 8), BlockType.GROUND);
		sampleMap.setBlockType(new Point(8, 9), BlockType.GROUND);
		sampleMap.setBlockType(new Point(9, 9), BlockType.RIVER);
		sampleMap.setBlockType(new Point(9, 8), BlockType.RIVER);
		sampleMap.setBlockType(new Point(9, 7), BlockType.RIVER);
	}
	
	public static ServerMap getSampleMap() {
		return sampleMap;
	}

}
