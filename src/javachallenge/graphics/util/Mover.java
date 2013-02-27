package javachallenge.graphics.util;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class provides a tool for animated moving of swing components.
 */
public class Mover extends Thread {
	protected Component component;
	protected Position vector;
	protected int steps, step;
	
	public static int delay = 20;
	public static boolean destroy = false;
	public static ArrayList<Mover> movers = new ArrayList<Mover>(); 

	static {
		new Thread() {
			public void run() {
				while (!destroy) {
					try {
						sleep (delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (movers) {
						ArrayList<Mover> newMovers = new ArrayList<Mover>();
						for (Mover mover : movers) {
							if (mover.step())
								newMovers.add(mover);
							else
								mover.atTheEnd();
						}
						movers.clear();
						movers = newMovers;
					}
				}	
			}
		}.start();
	}
	
	public Mover(Component component, Position position, int steps) {
		super();
		this.component = component;
		this.vector = position;
		this.steps = steps;
		this.step = 1;
	}
	
	public int getStepPositionX (int step) {
		return step * vector.getX() / steps;
	}
	public int getStepPositionY (int step) {
		return step * vector.getY() / steps;
	}
	public int getStepVectorX (int step) {
		return getStepPositionX(step) - getStepPositionX(step - 1);
	}
	public int getStepVectorY (int step) {
		return getStepPositionY(step) - getStepPositionY(step - 1);
	}
	
	protected void move (int x, int y) {
		component.setLocation(component.getX() + x, component.getY() + y);
	}
	
	@Override
	public void start() {
		synchronized (movers) {
			movers.add(this);	
		}
	}
	
	protected boolean step() {
		synchronized (component) {
			move (getStepVectorX(step), getStepVectorY(step));
			step++;
			if (step > steps) return false;
			return true;
		}
	}
	
	public void run() {
		for (step = 1; step <= steps;) {
			try {
				sleep (delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			step();
		}
		atTheEnd();
	}
	
	public void atTheEnd() {
	}
}
