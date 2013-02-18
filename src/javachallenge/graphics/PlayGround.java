package javachallenge.graphics;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javachallenge.graphics.components.*;
import javachallenge.graphics.util.ColorMaker;
import javachallenge.graphics.util.HTMLMaker;
import javachallenge.graphics.util.ImageHolder;
import javachallenge.graphics.util.Position;

public class PlayGround extends Screen {
	protected Label[] loadings = {
		new Label(380, 300, 36, 36, ImageHolder.waiting),
		new Label(360, 270, 80, 20, new HTMLMaker("Loading...", ColorMaker.white, 10).toString()),
	};
	
	protected MapPanel map;
	protected StatusPanel status=new StatusPanel(ColorMaker.background);

	public StatusPanel getStatus()
	{
		return status;
	}


	public PlayGround (int width, int height) {
		super("Play Ground");
		getContentPane().setBackground(ColorMaker.black);
		
		for (Label loading : loadings) add(loading);

		setPreferredSize(new Dimension(800, 600));
		setVisible(true);
	}
	
	public void addMapPanel (MapPanel map) {
		this.map = map;
		add (map);
		add(status);
		for (Label loading : loadings) remove(loading);
		addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent arg0) {}
			public void componentMoved(ComponentEvent arg0) {}
			public void componentHidden(ComponentEvent arg0) {}
			public void componentResized(ComponentEvent arg0) {
				PlayGround.this.map.setLocation(10, 10);
				Dimension size = arg0.getComponent().getSize();
				status.setLocation(20+size.width*3/4,10);
				status.setSize(size.width/4-50,size.height-60);
				PlayGround.this.map.setSize(size.width * 3 / 4, size.height - 60);
			}
		});

		map.setLocation(10, 10);
		Dimension size = getSize();
		status.setLocation(20+size.width*3/4,10);
		status.setSize(size.width/4-50,size.height-60);
		map.setSize(size.width * 3 / 4, size.height - 60);
	}
}
