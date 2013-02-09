package javachallenge.server;

import javachallenge.common.Point;

public class Agent {
	private Point location;
	private boolean isAlive;
	private int id;

	private static int refCount = 0;
	
	public Agent() {
		this.id = refCount++;
	}
}
