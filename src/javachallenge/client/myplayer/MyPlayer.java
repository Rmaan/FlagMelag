package javachallenge.client.myplayer;

import java.util.ArrayList;
import java.util.Random;

import javachallenge.client.Agent;
import javachallenge.client.Player;
import javachallenge.common.Direction;
import javachallenge.common.Point;
import javachallenge.common.VisionPoint;

public class MyPlayer extends Player {
	int cycle = 0;
	ArrayList<Point> putoutFires = new ArrayList<Point>();
	
	public void step() {
		Random rnd = new Random();
		
		for (int id: getAgentIds()) {
			Agent agent = this.getAgentById(id);
			
			agent.doMove(Direction.values()[rnd.nextInt(Direction.values().length)]);
			
			
			System.out.println("-------------------------------------");
			VisionPoint v = agent.getAdjVisionPoint(Direction.NONE) ;
			System.out.println(v.getAdjBlockType(Direction.NORTH));
			System.out.println(v.getAdjTeamId(Direction.NONE));
			System.out.println(v.getLocation());
			System.out.println("-------------------------------------");
		}
	}
}
