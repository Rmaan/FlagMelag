package javachallenge.graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

import javachallenge.common.Direction;
import javachallenge.common.Point;
import javachallenge.common.PowerUp;
import javachallenge.graphics.components.*;
import javachallenge.graphics.util.*;
import javachallenge.server.Map;
import javachallenge.server.RemoteControl;

public class GraphicClient {
	public static int x[]={0,1,1,0,-1,-1};
	public static int y[]={-1,-1,0,1,0,-1};
	public static int moveSpeed = 300, moveSteps = moveSpeed / Mover.delay;
	public static int attackSpeed = 100, attackSteps = attackSpeed / Mover.delay;
	public static ImageAnimator animator=new ImageAnimator(200);
	protected MapPanel panel;
	protected java.util.Map<Integer,Sprite> flags=new TreeMap<Integer,Sprite>();
	protected java.util.Map<Integer,Sprite> spawnPoints=new TreeMap<Integer,Sprite>();
	protected java.util.Map<Integer,Sprite> units=new TreeMap<Integer,Sprite>();
	protected java.util.Map<Integer,Sprite> powerUps=new TreeMap<Integer, Sprite>();
	protected PlayGround ground;
	protected java.util.Map<Integer,VerticalTransparentProgressBar> barMap=new TreeMap<Integer, VerticalTransparentProgressBar>();
	public void setTime(int a)
	{
		ground.getStatus().setTime(a);
		//ground.getStatus().getTime().setText(new Integer(a).toString());
	}
	public void setScore(int id, int a,double ratio)
	{
		ground.getStatus().updateScore(id,a,ratio);
		//	ground.getStatus().getScore().setText(new Integer(a).toString());
	}

	public MapPanel getPanel() {
		return panel;
	}

	public void setPanel(MapPanel panel) {
		this.panel = panel;
	}

	public GraphicClient(int width,int height, final Position[] positions,int Players) throws NullPointerException,OutOfMapException{
		this (new Map(width, height, 3, null, null, null) {
			{
				flagLocations = new ArrayList<Point>();
				for (Position position : positions)
					flagLocations.add(new Point(position.getX(), position.getY()));
			}
		}, null);
	}

	@SuppressWarnings("serial")
	public GraphicClient(Map map, final RemoteControl ctrl) throws OutOfMapException
	{
		animator.start();
		int Players = map.getTeamCount() ;
		ground=new PlayGround() {
			{
				play = new ClickableLabel("") {
					boolean isPlay=true;
					{
						setIcon(ImageHolder.pause);
						setToolTipText("Pause");
					}
					public void onClick() {
						if (isPlay)
						{
							setIcon(ImageHolder.play);
							setToolTipText("Play");
						}
						else
						{
							setIcon(ImageHolder.pause);
							setToolTipText("Pause");
						}
						GraphicClient.animator.setPause(GraphicClient.animator.isPause()^true);
						isPlay^=true;
						ctrl.playPauseToggle();
					}
				};	
				pause = new ClickableLabel("useless") {
					public void onClick() { System.out.println(""); }
				};
				forward = new ClickableLabel("") {
					{
						setToolTipText("Next Cycle");
						setIcon(ImageHolder.nextCycle);
					}
					public void onClick() { ctrl.step(); }
				};
			}
		};
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

		ground.getStatus().addBars(Players);
		ground.addBottomBar();

		for (int i = 0; i < map.getFlagLocations().size(); i++) {
			Position position = new Position(map.getFlagLocations().get(i));
			barMap.put(i+1,panel.setBar(position,i));
			flags.put(i+1, panel.setFlag(position, i));
		}

		for (int i = 0; i < map.getSpawnLocations().size(); i++) {
			Position position = new Position(map.getSpawnLocations().get(i));
			if (panel.isOut(position)) throw new OutOfMapException();
			Sprite spawn = new AnimatedImage(ImageHolder.Objects.mage, position, true,animator);
			panel.addToContainer(spawn ,2);
			spawnPoints.put(i+1, spawn);
		}
	}

	public void spawn(Integer id, Integer teamId, Position position) throws OutOfMapException, DuplicateMemberException
	{
		if (panel.isOut(position)) throw new OutOfMapException();
		if (units.get(id)!=null) throw new DuplicateMemberException();
		Sprite sprite=new Sprite(ImageHolder.Units.units[teamId][0], position);
		units.put(id, sprite);
		panel.addToContainer(sprite, 3);
	}

	public void die(Integer id) throws NullPointerException{
		Sprite sprite=units.get(id);
		units.remove(id);
		sprite.setVisible(false);
		panel.remove(sprite);
	}

	public void attack(Integer id, Integer teamId, Direction dir) {
		int direction = dir.ordinal();
		final Sprite sprite = units.get(id);
		final Position position = MapPanel.getAbsolutePosition(x[direction], y[direction]);
		//	position.x*=2;
		//	position.y*=2;
		position.x /= 3;
		position.y /= 3;
		if (direction == 1 || direction == 2)
			units.get(id).setIcon(ImageHolder.Units.units[teamId][0]);
		if (direction == 4 || direction == 5)
			units.get(id).setIcon(ImageHolder.Units.units[teamId][1]);
		new Mover(sprite,position,attackSteps) {
			@Override
			public void atTheEnd() {
				position.x *= -1;
				position.y *= -1;
				new Mover(sprite,position,attackSteps).start();
			}
		}.start();
	}

	public void move(Integer id, Integer teamId, Direction dir) throws NullPointerException {
		int direction=dir.ordinal();
		Sprite sprite=units.get(id);
		Position position=MapPanel.getAbsolutePosition(x[direction],y[direction]);
		if (direction == 1 || direction == 2)
			units.get(id).setIcon(ImageHolder.Units.units[teamId][0]);
		if (direction == 4 || direction == 5)
			units.get(id).setIcon(ImageHolder.Units.units[teamId][1]);
		new Mover(sprite,position,moveSteps).start();
	}

	public void obtainFlag (Integer id)  throws NullPointerException{
		Sprite flag = flags.get(id);
		flag.setVisible(false);
		panel.remove(flag);
		flags.remove(id);
		flags.put(id, new Sprite(ImageHolder.Objects.underFire, flag.getPosition()));
		panel.addToContainer(flags.get(id), 2);
	}
	
	public void setFlagStatus(Integer id, int progressTeam, int progressPercentage, int curTeam) {
		setFlagStatus(id, progressTeam, progressPercentage, curTeam, true);
	}

	public void setFlagStatus(Integer id, int progressTeam, int progressPercentage, int curTeam, boolean animated) {
		Sprite flag = flags.get(id);
		flag.setVisible(false);
		VerticalTransparentProgressBar bar=barMap.get(id);
		if (progressTeam==-1)
			bar.setColor(Color.BLACK);
		else
			bar.setColor(StatusPanel.filled[progressTeam]);
		if (animated)
			bar.animatedBar(progressPercentage);
		else
		{
			bar.updateVerticalTransparentProgressBar((double)progressPercentage/100);
			bar.setLast(progressPercentage);
		}
		panel.remove(flag);
		((AnimatedImage) flag).destroy();
		flags.remove(id);
		flags.put(id, new AnimatedImage(ImageHolder.Objects.flags[curTeam + 1], flag.getPosition(), true,animator));
		panel.addToContainer(flags.get(id), 4);
	}
	public void addPowerUp(int id,Point point,PowerUp powerUp)
	{
		AnimatedImage image=new AnimatedImage(ImageHolder.Objects.runes[powerUp.ordinal()],new Position(point),true,animator);
		panel.addToContainer(image,2);
		powerUps.put(id,image);
	}
	public void hidePowerUp(int id)
	{
		Sprite image=powerUps.get(id);
		image.setVisible(false);
	    panel.remove(image);
		powerUps.remove(id);
	}
	public void log (String message) {
//		System.err.println("log: " + message);
		ground.addLog(message);
	}

	public void setName(int id, String name)
	{
		ground.getStatus().setName(id, name);
	}

	public static class DuplicateMemberException extends Exception
	{

	}
	public static class OutOfMapException extends Exception
	{
	}

}
