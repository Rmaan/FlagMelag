package javachallenge.server;

import java.io.Serializable;

import javachallenge.common.Point;

public class Flag implements Serializable {
	private static final long serialVersionUID = -3703632960727476328L;
	private Point location;
	private int id;
	private boolean isAlive;
	
	public Flag(Point location, int id) {
		this.location = location ;
		this.id = id ;
		this.isAlive = true ;
	}

	public boolean isAlive(){
		return isAlive ;
	}
	
	public Point getLocation(){
		return location ;
	}
	
	public int getId(){
		return id ;
	}

	public void obtain() {
		this.isAlive = false ;
	}
	
}
