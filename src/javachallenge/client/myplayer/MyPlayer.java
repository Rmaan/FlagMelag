package javachallenge.client.myplayer;

import java.util.Random;

import javachallenge.client.Agent;
import javachallenge.client.Player;
import javachallenge.common.Direction;
import javachallenge.common.Point;
import javachallenge.common.VisionPoint;

public class MyPlayer extends Player {
	int cycle = 0;
	Random rnd = new Random();
	
	void moveTo(Agent agent, Point p) {
		int ay = agent.getLocation().y;
		int ax = agent.getLocation().x;
		
		if (Math.abs(p.y - ay) >= 1) {
			agent.doMove(ay > p.y ? Direction.NORTH : Direction.SOUTH);
			return;
		}
		
		if (p.x > ax) {
			agent.doMove(ax % 2 == 0 ? Direction.SOUTHEAST : Direction.NORTHEAST);
		}
		
		if (p.x < ax) {
			agent.doMove(ax % 2 == 0 ? Direction.SOUTHWEST : Direction.NORTHWEST);
		}
	}
	
	public void step() {
		Point goal = new Point(12, 12);
		
		for (int id: getAgentIds()) {
			Agent agent = this.getAgentById(id);
			
			agent.doMove(Direction.values()[rnd.nextInt(Direction.values().length)]);
			
//			System.out.println("-------------------------------------");
//			VisionPoint v = agent.getAdjVisionPoint(Direction.NONE) ;
//			System.out.println(v.getAdjBlockType(Direction.NORTH));
//			System.out.println(v.getAdjTeamId(Direction.NONE));
//			System.out.println(v.getLocation());
//			System.out.println("-------------------------------------");
			
			moveTo(agent, goal);
		}
	}

	@Override
	public String getName() {
		return "Sample Player";
	}
}
