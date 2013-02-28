package javachallenge.common;

import java.io.Serializable;


public class PowerUpPoint implements Serializable{
	private static final long serialVersionUID = 4701318926083967528L;
	private PowerUp type = PowerUp.NONE;
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
