package javachallenge.common;

import java.util.ArrayList;

import javachallenge.ImproperlyConfiguredException;
import javachallenge.NotImplementedException;

public class Map {
	private BlockType[][] map;
	private int teamCount;
	private int hei;
	private int wid;
	private ArrayList<Point> spawnLocations;
	private ArrayList<Point> flagLocations;
	
	public Map(int wid, int hei, int teamCount, ArrayList<Point> spawnLocations, ArrayList<Point> flagLocations) {
		if (wid % 2 == 1 || hei % 2 == 1)
			throw new ImproperlyConfiguredException("Width and height must be even.");
		this.spawnLocations = spawnLocations; 
		this.wid = wid;
		this.hei = hei;
		this.teamCount = teamCount;
		this.flagLocations = flagLocations;
	}
	
	public int getTeamCount() {
		return teamCount;
	}

	public int getHei() {
		return hei;
	}

	public int getWid() {
		return wid;
	}

	public ArrayList<Point> getSpawnLocations() {
		return spawnLocations;
	}

	public boolean isInsideMap(Point p) {
		return (p.x >= 0 && p.x < wid && p.y >= 0 && p.y < hei);
	}
	
	public BlockType getBlockType(Point p) {
		return map[p.x][p.y];
	}
	
	public void setBlockType(Point p, BlockType bt) {
		map[p.x][p.y] = bt;
	}
	
	public BlockType[] getBlockTypes(Point p){
		Direction[] dirs = Direction.values();
		BlockType[] blockTypes = new BlockType[6];
		for(int i = 0 ; i < dirs.length ; i++){
			blockTypes[i] = getBlockType(p.applyDirection(dirs[i]));
		}
		return blockTypes;
	}
}
