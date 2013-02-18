package javachallenge.graphics.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Panel extends JLayeredPane {
	public Panel() { }
	
	public Panel (int x, int y, int w, int h) {
		setBounds(x, y, w, h);
	}
	
	public Panel (Color color) {
		this (0, 0, 0, 0, color);
	}
	
	public Panel (int x, int y, int w, int h, Color color) {
		setBounds(x, y, w, h);
		setBackground(color);
		setOpaque(true);
		setLayout(null);
	}
	
	@Override
	public void removeAll() {
		while (getComponents().length > 0) {
			Component component = getComponent(0);
			component.setVisible(false);
			remove(component);
		}
	}
}