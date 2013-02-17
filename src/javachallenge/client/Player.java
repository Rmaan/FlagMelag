package javachallenge.client;

import java.util.Random;

import javachallenge.common.Action;
import javachallenge.common.ActionType;
import javachallenge.common.Point;
import javachallenge.common.Direction;


public class Player extends Behrooz {
	public Player(World world) {
		super(world);
	}
	
	public void step(){
		//return ;
		
		Random random=new Random(System.currentTimeMillis());
		World myWorld=this.getWorld();
//		Point[] flagLocs= (Point[]) (myWorld.getMap().getFlagLocations().toArray());
		
		for(int i = 0 ; i < this.getAgentAliveId().size() ; i++){
			int d = random.nextInt(6);
			int ID = this.getAgentAliveId().get(i);
			Action action=new Action(ActionType.MOVE, Direction.values()[d],
					ID);
			this.doAction(action);
//			this.getAgentById(ID).
		}
		
		
	}
}
