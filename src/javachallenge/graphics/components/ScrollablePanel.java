package javachallenge.graphics.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ScrollablePanel extends Panel {
	protected Panel container;
	protected JScrollPane scroll;
	
	public ScrollablePanel (int x, int y, int w, int h, Color color) {
		super (x, y, w, h);
		container = new Panel(color);
		scroll = new JScrollPane(container);
		scroll.setBounds(0, 0, w, h);
		scroll.setBorder(null);
		add (scroll);
		reValidate();
	}
	
	public ScrollablePanel (Color color) {
		super (0, 0, 0, 0);
		container = new Panel(color);
		scroll = new JScrollPane(container);
		scroll.setBorder(null);
		add (scroll);
		reValidate();
	}
	
	public void addToContainer(Component comp) {
		addToContainer(comp, 0);
	}
	
	public void addToContainer (Component comp, Integer layer) {
		container.add(comp, layer);
		reValidate();
	}
	
	public void setContainerSize (Dimension dimension) {
		container.setPreferredSize(dimension);
		reValidate();
	}
	
	@Override
	public void setSize(int x, int y) {
		super.setSize(x, y);
		scroll.setSize(x, y);
		reValidate();
	}
	
	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		reValidate();
	}
	
	public void reValidate() {
		container.revalidate();
	}
}
