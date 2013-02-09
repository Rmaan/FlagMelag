package javachallenge.common;

import javachallenge.NotImplementedException;

public class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Point applyDirection(Direction dir) {
		throw new NotImplementedException();
	}
}
