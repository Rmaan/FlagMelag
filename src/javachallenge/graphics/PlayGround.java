package javachallenge.graphics;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javachallenge.graphics.components.*;
import javachallenge.graphics.components.Label;
import javachallenge.graphics.components.Panel;
import javachallenge.graphics.util.ColorMaker;
import javachallenge.graphics.util.HTMLMaker;
import javachallenge.graphics.util.ImageHolder;
import javachallenge.graphics.util.Position;

public class PlayGround extends Screen {
	protected Label[] loadings = {
		new Label(380, 300, 36, 36, ImageHolder.waiting),
		new Label(360, 270, 80, 20, new HTMLMaker("Loading...", ColorMaker.white, 10).toString()),
	};
	
	protected MapPanel mapPanel;
	protected Panel sidebar;
	protected StatusPanel status;
	
	public PlayGround() {
		this ("Java Challenge - Play Ground");
	}
	
	public PlayGround (String title) {
		super(title);
		getContentPane().setBackground(ColorMaker.black);
		
		for (Label loading : loadings) add(loading);

		setPreferredSize(new Dimension(800, 600));
		setVisible(true);
	}
	
	public MapPanel getMapPanel() {
		return mapPanel;
	}
	
	public Panel getSidebar() {
		return sidebar;
	}

	public StatusPanel getStatus() {
		return status;
	}

	public void updateDimensions() {
		Dimension size = getSize();
		mapPanel.setLocation(10, 10);
		mapPanel.setSize(size.width * 3 / 4, size.height - 110);
		mapPanel.getPosition().setBounds(10, size.height-90,size.width*3/4,30);
		if (sidebar!=null)
		{
			sidebar.setLocation(20 + mapPanel.getWidth(), 10);
			sidebar.setSize(size.width - sidebar.getX() - 30, size.height - 60);
		}
		if (status!=null)
		{
			status.setLocation(20 + mapPanel.getWidth(), 10);
			status.setSize(size.width - sidebar.getX() - 30, size.height - 60);
			status.updatePosition();
		}
	}
	
	public void createScreenElements (MapPanel mapPanel) {
		addMapPanel(mapPanel);
		add(mapPanel.getPosition());
		addSideBar();
		// remove loadings
		for (Label loading : loadings) remove(loading);
		
		// resize sensitive dimension updater
		addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent arg0) {}
			public void componentMoved(ComponentEvent arg0) {}
			public void componentHidden(ComponentEvent arg0) {}
			public void componentResized(ComponentEvent arg0) {
				updateDimensions();
			}
		});
		
		// update dimensions based on size
		Dimension dimension = MapPanel.getAbsoluteSize(mapPanel.getMapWidth(), mapPanel.getMapHeight());
		setPreferredSize(new Dimension(dimension.width*4/3+40, dimension.height+110));
		updateDimensions();
		pack();
	}
	
	public void addMapPanel (MapPanel mapPanel) {
		add (this.mapPanel = mapPanel);
	}
	public void addStatusBar() {
		status = new StatusPanel(ColorMaker.panelBack) {
			{ setBounds(0, 0, 100, 100); }
		};
		add (status);
	}
	public void addSideBar() {
		sidebar = new Panel (ColorMaker.shadedPanelBack);
		//add (sidebar);
		addStatusBar();
	}
}
