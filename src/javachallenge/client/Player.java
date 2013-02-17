package javachallenge.client;

import java.util.ArrayList;
import java.util.Random;

import javachallenge.common.Action;
import javachallenge.common.ActionType;
import javachallenge.common.BlockType;
import javachallenge.common.Direction;
import javachallenge.common.Point;

public class Player extends Behrooz {
//	World myWorld;
	public Player(World world) {
		super(world);
	}
	
	public void step(){
		Random random=new Random(System.currentTimeMillis());
		World myWorld=this.getWorld();
		Point[] flagLocs= (Point[])myWorld.getMap().getFlagLocations().toArray();
		
		Integer[] AliveID= (Integer[])(this.getAgentAliveId().toArray());
		for(int i=0;i<AliveID.length;i++){
			int direction = random.nextInt(6);
			int ID=AliveID[i];
			Action action=new Action(ActionType.MOVE, Direction.values()[direction],
					ID);
			this.doAction(action);
//			this.getAgentById(ID).
		}
	}
}
