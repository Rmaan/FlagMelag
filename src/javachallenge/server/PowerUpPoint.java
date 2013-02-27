package javachallenge.server;

import javachallenge.common.Point;
import javachallenge.common.PowerUp;

public class PowerUpPoint {
	private PowerUp type = null;
	private Point location;
	private int id;
	
	
	public PowerUpPoint(Point location, int id) {
		this.location = location;
		this.id = id;
	}
	
	public PowerUp getType() {
		return type;
	}
	public void setType(PowerUp type) {
		this.type = type;
	}
	public Point getLocation() {
		return location;
	}

	public int getId(){
		return id;
	}
	@Override
	public String toString() {
		return "PowerUpPoint [type=" + type + ", location=" + location + "]";
	}
	
	
}
