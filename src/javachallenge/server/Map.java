package javachallenge.server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javachallenge.ImproperlyConfiguredException;
import javachallenge.common.BlockType;
import javachallenge.common.Direction;
import javachallenge.common.Point;
import javachallenge.common.VisionPoint;

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
			return BlockType.OUT_OF_MAP ;
		return map[p.x][p.y];
	}
	
	public void setBlockType(Point p, BlockType bt) {
		map[p.x][p.y] = bt;
	}
	
	public BlockType[] getBlockTypes(Point p){
		BlockType[] blockTypes = new BlockType[Direction.values().length];
		for (Direction dir : Direction.values()) {
			Point newp = p.applyDirection(dir) ;
			blockTypes[dir.ordinal()] = getBlockType(newp);
		}
		return blockTypes;
	}

	public void setFlagLocations(ArrayList<Point> flags) {
		this.flagLocations = flags ;
	}
	
	public void save(String filename) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);
		
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(filename));
			out.write(json);
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	public static Map load(String filename) throws IOException {
		BufferedReader f = null;
		StringBuilder sb = new StringBuilder();
		try {
			f = new BufferedReader(new FileReader(filename));
			String line = f.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = f.readLine();
			}
		} finally {
			if (f != null)
				f.close();
		}
		
		String json = sb.toString();
		Gson gson = new Gson();
		Map obj = gson.fromJson(json, Map.class);
	//	System.out.println(obj.agents);
		
		return obj;
	}
}
