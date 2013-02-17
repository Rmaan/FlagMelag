package javachallenge.common;

import java.io.Serializable;

public class Point implements Serializable {
	private static final long serialVersionUID = -8898176112993619962L;
	
	public int x;
	public int y;

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
		int x, y;
		switch(dir){
		case NORTH:
			x = this.x;
			y = this.y - 1;
			break;
			
		case SOUTH:
			x = this.x;
			y = this.y + 1;
			break;
			
		default:
			if(this.x % 2 == 1){
				switch(dir){
				case SOUTHEAST:
					x = this.x + 1;
					y = this.y + 1;
					break;
					
				case SOUTHWEST:
					x = this.x - 1;
					y = this.y + 1;
					break;
					
				case NORTHEAST:
					x = this.x + 1;
					y = this.y;
					break;
				
				case NORTHWEST:
					x = this.x - 1;
					y = this.y;
					break;
					
					default:
						x = this.x;
						y = this.y;
				}
			}
			else{
				switch(dir){
				case SOUTHEAST:
					x = this.x + 1;
					y = this.y;
					break;
					
				case SOUTHWEST:
					x = this.x - 1;
					y = this.y;
					break;
					
				case NORTHEAST:
					x = this.x + 1;
					y = this.y - 1;
					break;
				
				case NORTHWEST:
					x = this.x - 1;
					y = this.y - 1;
					break;
					
					default:
						x = this.x;
						y = this.y;
				}
			}
		
		}
		return new Point(x, y);
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
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
}
