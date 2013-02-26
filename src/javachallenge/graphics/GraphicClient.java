package javachallenge.graphics;

import java.util.ArrayList;
import java.util.TreeMap;

import javachallenge.common.Direction;
import javachallenge.common.Point;
import javachallenge.graphics.components.MapPanel;
import javachallenge.graphics.components.Sprite;
import javachallenge.graphics.util.AnimatedImage;
import javachallenge.graphics.util.ImageHolder;
import javachallenge.graphics.util.Mover;
import javachallenge.graphics.util.Position;
import javachallenge.server.Map;

public class GraphicClient {
	public static int x[]={0,1,1,0,-1,-1};
	public static int y[]={-1,-1,0,1,0,-1};
	public static int moveSpeed = 300, moveSteps = 25;
	
	protected MapPanel panel;
	protected java.util.Map<Integer,Sprite> flags=new TreeMap<Integer,Sprite>();
	protected java.util.Map<Integer,Sprite> spawnPoints=new TreeMap<Integer,Sprite>();
	protected java.util.Map<Integer,Sprite> units=new TreeMap<Integer,Sprite>();
	protected PlayGround ground;

	public void setTime(int a)
	{
		ground.getStatus().setTime(a);
		//ground.getStatus().getTime().setText(new Integer(a).toString());
	}
	public void setScore(int id, int a)
	{
		ground.getStatus().setScore(a);
		//	ground.getStatus().getScore().setText(new Integer(a).toString());
	}

	public MapPanel getPanel() {
		return panel;
	}

	public void setPanel(MapPanel panel) {
		this.panel = panel;
	}
	public GraphicClient(int width,int height, final Position[] positions) throws NullPointerException,OutOfMapException{
		this (new Map(width, height, 0, null, null) {
			{  
				flagLocations = new ArrayList<Point>();
				for (Position position : positions) 
					flagLocations.add(new Point(position.getX(), position.getY()));
			}
		});
	}
	
	public GraphicClient(Map map) throws OutOfMapException
	{
		ground=new PlayGround();
		ground.createScreenElements(panel=new MapPanel(map) {
			@Override
			public void onClick(int x, int y) {
				/*try
				{
					spawn(units.size()+1, new Position(x, y));
				} catch (OutOfMapException e)
				{
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (DuplicateMemberException e)
				{
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}*/
			}
		});
		for (int i = 0; i < map.getFlagLocations().size(); i++) {
			Position position = new Position(map.getFlagLocations().get(i));
			flags.put(i+1, panel.setFlag(position, i));
		}
		for (int i = 0; i < map.getSpawnLocations().size(); i++) {
			Position position = new Position(map.getSpawnLocations().get(i));
			if (panel.isOut(position)) throw new OutOfMapException();
			Sprite spawn = new AnimatedImage(ImageHolder.Objects.mage, 250, position);
			panel.addToContainer(spawn ,2);
			spawnPoints.put(i+1, spawn);
		}
	}

	public void spawn(Integer id,Position position) throws OutOfMapException, DuplicateMemberException
	{
		if (panel.isOut(position)) throw new OutOfMapException();
		if (units.get(id)!=null) throw new DuplicateMemberException();
		Sprite sprite=new Sprite(ImageHolder.Units.wesfolkOutcast, position);
		units.put(id,sprite);
		panel.addToContainer(sprite,3);
	}
	
	public void die(Integer id) throws NullPointerException{
		Sprite sprite=units.get(id);
		units.remove(id);
		sprite.setVisible(false);
		panel.remove(sprite);
	}
	
	public void attack(Integer attacker,Integer defender) {

	}
	
	public void move(Integer id,Direction dir) throws NullPointerException {
		int direction=dir.ordinal();
		Sprite sprite=units.get(id);
		Position position=MapPanel.getAbsolutePosition(x[direction],y[direction]);
		if (direction == 1 || direction == 2)
			units.get(id).setIcon(ImageHolder.Units.wesfolkOutcast);
		if (direction == 4 || direction == 5)
			units.get(id).setIcon(ImageHolder.Units.wesfolkOutcastMirror);
		new Mover(sprite,position,moveSpeed/moveSteps,moveSteps).start();
	}
	
	public void obtainFlag (Integer id)  throws NullPointerException{
		Sprite flag = flags.get(id);
		flag.setVisible(false);
		panel.remove(flag);
		flags.remove(id);
		flags.put(id, new Sprite(ImageHolder.Objects.underFire, flag.getPosition()));
		panel.addToContainer(flags.get(id), 2);
	}
	
	public void setFlagStatus(Integer id, int progressTeam, int progressPercent, int curTeam){
		
	}
	public static class DuplicateMemberException extends Exception
	{

	}
	public static class OutOfMapException extends Exception
	{
	}
}