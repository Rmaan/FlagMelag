package javachallenge.graphics.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

import javachallenge.graphics.util.ColorMaker;

import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class ScrollableList extends Panel {
	protected Panel container;
	protected JScrollPane scroll;
	protected int offset = 0;
	protected int padding = 20;
	protected ArrayList<Component> components;
	protected boolean alwaysDown, changed = false;
	
	public ScrollableList (int x, int y, int w, int h, Color color, boolean alwaysDown) {
		super (x, y, w, h);
		container = new Panel(color);
		scroll = new JScrollPane(container);
		scroll.setBounds(0, 0, w, h);
		scroll.setBorder(null);
		add (scroll);
		container.revalidate();
		components = new ArrayList<Component>();
		if (alwaysDown) {
			scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		        public void adjustmentValueChanged(AdjustmentEvent e) {
		        	if (changed == true) {
		        		e.getAdjustable().setValue(e.getAdjustable().getMaximum());
		        		changed = false;
		        	}
		        }
		    });
		}
	}
	
	public Panel getContainerPanel() {
		return container;
	}
	
	public ArrayList<Component> getComponentsList() {
		return components;
	}
	
	public void reValidate() {
		getContainerPanel().setPreferredSize(new Dimension(getWidth() - padding, offset));
		getContainerPanel().revalidate();
		changed = true;
	}

	synchronized public void addComponent(Component component, int height) {
		components.add(component);
		component.setBounds(0, offset, getWidth(), height);
		getContainerPanel().add(component);
		offset += height;
		reValidate();
	}
	
	synchronized public void popComponent() {
		Component last = components.get(components.size() - 1);
		components.remove(components.size() - 1);
		offset -= last.getHeight();
		last.setVisible(false);
		getContainerPanel().remove(last);
		reValidate();
	}
	
	synchronized public void redraw() {
		while (getContainerPanel().getComponents().length > 0) {
			getContainerPanel().remove(0);
		}
		offset = 0;
		for (Component component : components) {
			component.setBounds(0, offset, getWidth(), component.getHeight());
			getContainerPanel().add(component);
			offset += component.getHeight();
		}
		reValidate();
	}
	
	public JScrollPane getScroll() {
		return scroll;
	}
	
	synchronized public void clear() {
		while (components.size() > 0)
			popComponent();
		redraw();
	}
}
