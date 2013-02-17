package javachallenge.graphics.components;


import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javachallenge.graphics.util.ColorMaker;
import javachallenge.graphics.util.ImageHolder;
import javachallenge.graphics.util.Position;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class MapPanel extends ScrollablePanel {
	protected int width, height;
	protected Sprite[][] blocks;
	protected Sprite brush;
	
	public static Position getAbsolutePosition (int x, int y) {
		return new Position(26 * x, 36 * y + 18 * (Math.abs(x) % 2));
	}
	
	public static Dimension getAbsoluteSize (int w, int h) {
		return new Dimension(26 * w + 10, 36 * h + 18);
	}
	
	public MapPanel(int width, int height) {
		super(ColorMaker.black);
		
		this.width = width;
		this.height = height;
		
		// create blocks
		blocks = new Sprite[width][height];
		ImageIcon[] environment = ImageHolder.Terrain.grass;
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				int index = new Random().nextInt(environment.length);
				addToContainer(blocks[i][j] = new Sprite(environment[index], 
						new Position(i, j)), 1);
			}
		
		addToContainer(brush = new Sprite(ImageHolder.mapBrush, new Position(-2, -2)), 3);
		
		scroll.getViewport().getView().addMouseMotionListener(new MouseMotionListener() {
			private Position lastPosition = new Position(-2, -2);
			
			@Override
			public void mouseMoved(MouseEvent e) {
				Position position = getPosition(e);
				if (position.equals(lastPosition)) return;
				lastPosition = position;
				if (insideMap(position))
					onEnter(position.getX(), position.getY());	
			}
			public void mouseDragged(MouseEvent e) {}
		});
		scroll.getViewport().getView().addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				Position position = getPosition(e);
				if (insideMap(position))
					onClick(position.getX(), position.getY());
			}
		});
		setContainerSize(getAbsoluteSize(width, height));
	}
	
	public int getMapWidth() {
		return width;
	}
	
	public int getMapHeight() {
		return height;
	}
	
	public Sprite getBrush() {
		return brush;
	}
	
	public void onClick(int x, int y) {}
	public void onEnter(int x, int y) {}
	public void onExit(int x, int y) {}
	
	public boolean insideMap (Position position) {
		return position.getX() >= 0 && position.getY() >= 0 &&
				position.getX() < width && position.getY() < height;
	}
	
	public static int sqr(int a) { return a*a; }
	
	public Position getPosition (MouseEvent e) {
		Position position=new Position(-1,-1);
		int bst=(1<<30);
		for (int i=-1;i<=width;i++)
			for (int j=-1;j<=height;j++)
			{
				Position temp= MapPanel.getAbsolutePosition(i,j);
				temp.setX(temp.getX()+18);
				temp.setY(temp.getY()+18);
				int dis=sqr(temp.getX()-e.getX())+sqr(temp.getY()-e.getY());
				if (dis<bst)
				{
					bst=dis;
					position=new Position(i,j);
				}
			}	
		return position;
	}
}