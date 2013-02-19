package javachallenge.client.myplayer;

import java.util.ArrayList;
import java.util.Random;

import javachallenge.client.Agent;
import javachallenge.client.Player;
import javachallenge.client.World;
import javachallenge.common.Direction;

public class MyPlayer extends Player {
	int cycle = 0;

	public MyPlayer(World world) {
		super(world);
	}

	public void step() {
		Random rnd = new Random();
		
		for (int id: getAgentIds()) {
			Agent agent = this.getAgentById(id);
			agent.doMove(Direction.values()[rnd.nextInt(6)]);
		}
	}
}
