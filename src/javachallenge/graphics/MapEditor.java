package javachallenge.graphics;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javachallenge.common.BlockType;
import javachallenge.common.Map;
import javachallenge.common.Point;
import javachallenge.graphics.components.MapPanel;

public class MapEditor extends PlayGround {
	public static int blockTypes = 3;
	protected Map map;
	
	public MapEditor() {
		super ("GoDitor");
	}
	
	@Override
	public void addMapPanel(MapPanel mapPanel) {
		ArrayList<Point> spawnLocations = new ArrayList<Point>();
		ArrayList<Point> flagLocations = new ArrayList<Point>();	
		this.map = new Map(mapPanel.getMapWidth(), mapPanel.getMapHeight(), 0, 
			spawnLocations, flagLocations);
	
		super.addMapPanel(mapPanel);
	}
	
	@Override
	public void addSideBar() {
		// TODO Auto-generated method stub
		super.addSideBar();
	}
	
	public Map getMap() {
		return map;
	}
	
	public static void main(String[] args) {
		final MapEditor mapEditor = new MapEditor();
		mapEditor.createScreenElements(new MapPanel(30, 30) {
			@Override
			public void onClick(int x, int y) {
				int type = (mapEditor.getMap().getBlockType(new Point (x, y)).ordinal() + 1) % blockTypes;
				mapEditor.getMapPanel().setBlock(x, y, type);
				mapEditor.getMap().setBlockType(new Point (x, y), BlockType.values()[type]);
			}
		});
	}
}