package javachallenge.graphics.util;

import java.awt.Component;

/**
 * This class provides a tool for animated moving of swing components.
 */
public class Mover extends Thread {
	protected Component component;
	protected Position vector;
	protected int delay, steps;
	public Mover(Component component, Position position, int delay, int steps) {
		super();
		this.component = component;
		this.vector = position;
		this.delay = delay;
		this.steps = steps;
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
	
	public void run() {
		for (int step = 1; step <= steps; step++) {
			try {
				sleep (delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (component) {
				move (getStepVectorX(step), getStepVectorY(step));
			}
		}
		atTheEnd();
	}
	
	public void atTheEnd() {
	}
}
