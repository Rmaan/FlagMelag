package javachallenge.graphics;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javachallenge.common.Direction;
import javachallenge.common.Point;
import javachallenge.graphics.components.Label;
import javachallenge.graphics.components.MapPanel;
import javachallenge.graphics.components.Sprite;
import javachallenge.graphics.util.AnimatedImage;
import javachallenge.graphics.util.ImageHolder;
import javachallenge.graphics.util.Mover;
import javachallenge.graphics.util.Position;
import javachallenge.server.ServerMap;

public class GraphicClient {
	public static int x[]={0,1,1,0,-1,-1};
	public static int y[]={-1,-1,0,1,0,-1};
	public static int moveSpeed = 300, moveSteps = 25;
	
	protected MapPanel panel;
	protected Map<Integer,Sprite> flags=new TreeMap<Integer,Sprite>();
	protected Map<Integer,Sprite> units=new TreeMap<Integer,Sprite>();
	protected PlayGround ground;

	protected Label time=new Label(),score=new Label(),status=new Label("Fuck");

	public void setTime(int a)
	{
		time.setText(new Integer(a).toString());
		//ground.getStatus().getTime().setText(new Integer(a).toString());
	}
	public void setScore(int a)
	{
		score.setText(new Integer(a).toString());
		//	ground.getStatus().getScore().setText(new Integer(a).toString());
	}

	public MapPanel getPanel() {
		return panel;
	}

	public void setPanel(MapPanel panel) {
		this.panel = panel;
	}
	public GraphicClient(int width,int height, final Position[] positions) throws NullPointerException,OutOfMapException{
		this (new ServerMap(width, height, 0, null, null) {
			{  
				flagLocations = new ArrayList<Point>();
				for (Position position : positions) 
					flagLocations.add(new Point(position.getX(), position.getY()));
			}
		});
	}
	public GraphicClient(ServerMap map) throws OutOfMapException
	{
		setTime(0);
		setScore(0);
		Position[] positions=new Position[map.getFlagLocations().size()];
		for (int i=0;i<map.getFlagLocations().size();i++)
			positions[i]=new Position(map.getFlagLocations().get(i).getX(),map.getFlagLocations().get(i).getY());
		ground=new PlayGround();
		ground.addStatusBar();
		ground.getStatus().addLabel(status);
		ground.getStatus().addLabel(time);
		ground.getStatus().addLabel(score);
		ground.createScreenElements(panel=new MapPanel(map) {
			@Override
			public void onClick(int x, int y) {
				try
				{
					spawn(units.size()+1, new Position(x, y));
				} catch (OutOfMapException e)
				{
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (DuplicateMemberException e)
				{
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		});
		for (int i = 0; i < positions.length; i++) {
			System.err.println(i+ positions[i].getX()+" "+positions[i].getY());
			if (isOut(positions[i])) throw new OutOfMapException();
			Sprite flag = new AnimatedImage(ImageHolder.Objects.fire, 125, positions[i]);
			flags.put(i+1, flag);
			panel.addToContainer(flag ,2);
		}
	}
	private boolean isOut(Position position)
	{
		if (position.getX()<0 || position.getY()<0 || position.getX()>=panel.getMapWidth() || position.getY()>=panel.getMapHeight()) return true;
		return false;
	}

	public void spawn(Integer id,Position position) throws OutOfMapException, DuplicateMemberException
	{
		if (isOut(position)) throw new OutOfMapException();
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
	public static class DuplicateMemberException extends Exception
	{

	}
	public static class OutOfMapException extends Exception
	{
	}
}