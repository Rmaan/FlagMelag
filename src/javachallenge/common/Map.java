package javachallenge.common;

import javachallenge.NotImplementedException;

public class Map {
	private BlockType[][] mat; 
	
	public Map(int w, int h) {
		// w % 2 == 0
		// h % 2 == 0	
	}
	
	public boolean isInsideMap(Point p) {
		throw new NotImplementedException();
	}
	
	public BlockType getBlockType(Point p) {
		return mat[p.x][p.y];
	}
	
	public void setBlockType(Point p, BlockType bt) {
		mat[p.x][p.y] = bt;
	}
}
