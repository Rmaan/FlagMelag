package javachallenge.graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javachallenge.common.BlockType;
import javachallenge.common.Map;
import javachallenge.common.Point;
import javachallenge.graphics.components.FileChooser;
import javachallenge.graphics.components.MapPanel;
import javachallenge.graphics.util.ColorMaker;

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
		sidebar.setLayout(new FlowLayout());
		sidebar.add(new FileChooser.FileChooserButton("save", false, "Choose target", "data/maps/", "map", "Map files") {
			{ setForeground(ColorMaker.fieldBackground); }
			@Override
			public void onAccept(String filename) {
				try {
					ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(new File(filename)));
					objout.writeObject(map);
					objout.close();
					System.err.println("Map saved Successfully");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Map getMap() {
		return map;
	}
	
	public static void main(String[] args) {
		final MapEditor mapEditor = new MapEditor();
		mapEditor.createScreenElements(new MapPanel(new Map(30, 30, 0, null, null)) {
			@Override
			public void onClick(int x, int y) {
				int type = (mapEditor.getMap().getBlockType(new Point (x, y)).ordinal() + 1) % blockTypes;
				mapEditor.getMapPanel().setBlock(x, y, type);
				mapEditor.getMap().setBlockType(new Point (x, y), BlockType.values()[type]);
			}
		});
	}
}