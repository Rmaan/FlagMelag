package javachallenge.client.myplayer;

import java.util.Random;

import javachallenge.client.Agent;
import javachallenge.client.Player;
import javachallenge.common.CycleAction;
import javachallenge.common.Direction;

public class MyPlayer extends Player {
	int cycle = 0;
	Random rnd = new Random();
	
	
	public void step() {
		for (Integer id : getAgentIds()) {
			
			Agent agent = this.getAgentById(id);
			
			if (getCycleAction() == CycleAction.MOVE_CYCLE)
				agent.doMove(Direction.values()[rnd.nextInt(Direction.values().length)]);
			else
				agent.doAttack(Direction.values()[rnd.nextInt(Direction.values().length)]);
			
		}
	}

	@Override
	public String getName() {
		return "Sample Player";
	}
}
