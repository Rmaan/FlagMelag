package javachallenge.common;

import java.io.Serializable;
import java.util.ArrayList;

import javachallenge.ImproperlyConfiguredException;

public class Map implements Serializable {
	private static final long serialVersionUID = 96375824927929628L;
	
	protected BlockType[][] map;
	protected int teamCount;
	protected int hei;
	protected int wid;
	protected ArrayList<Point> spawnLocations;
	protected ArrayList<Point> flagLocations;
	
	public Map(int wid, int hei, int teamCount, ArrayList<Point> spawnLocations, ArrayList<Point> flagLocations) {
		if (wid % 2 == 1 || hei % 2 == 1)
			throw new ImproperlyConfiguredException("Width and height must be even.");
		this.spawnLocations = spawnLocations; 
		this.wid = wid;
		this.hei = hei;
		this.teamCount = teamCount;
		this.flagLocations = flagLocations;
		this.map = new BlockType[wid][hei];
		for (int x = 0; x < wid; x++)
			for (int y = 0; y < hei; y++)
				map[x][y] = BlockType.GROUND;
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

	public ArrayList<Point> getFlagLocations() {
		return flagLocations;
	}

	public boolean isInsideMap(Point p) {
		return (p.x >= 0 && p.x < wid && p.y >= 0 && p.y < hei);
	}
	
	public BlockType getBlockType(Point p) {
		if (!isInsideMap(p))
			return BlockType.OUT ;
		return map[p.x][p.y];
	}
	
	public void setBlockType(Point p, BlockType bt) {
		map[p.x][p.y] = bt;
	}
	
	public BlockType[] getBlockTypes(Point p){
		Direction[] dirs = Direction.values();
		BlockType[] blockTypes = new BlockType[6];
		for(int i = 0 ; i < dirs.length ; i++){
			Point newp = p.applyDirection(dirs[i]) ;
			blockTypes[i] = getBlockType(newp);
		}
		return blockTypes;
	}

	public void setFlagLocations(ArrayList<Point> flags) {
		this.flagLocations = flags ;
	}
	
	public Map shallowClone() {
		Map m = new Map(wid, hei, teamCount, spawnLocations, flagLocations);
		m.map = this.map;
		return m;
	}
}
