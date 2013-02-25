package javachallenge.graphics.util;

import java.io.Serializable;

import javachallenge.common.Point;

@SuppressWarnings("serial")
public class Position extends Point {
	public Position (int x, int y) {
		super (x, y);
	}
	
	public Position (Point point) {
		this (point.x, point.y);
	}
	
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}
	
	public void setX (int x) {
		this.x = x;
	}

	public void setY (int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}