package javachallenge.graphics.util;

import java.io.Serializable;

/**
 * {@link Position} is a class that provides position for every {@link Positionable} object.
 */
@SuppressWarnings("serial")
public class Position implements Serializable {
	private int X, Y;

	public Position (int x, int y) {
		X = x;
		Y = y;
	}

	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + X;
		result = prime * result + Y;
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
		if (X != other.X)
			return false;
		if (Y != other.Y)
			return false;
		return true;
	}
}