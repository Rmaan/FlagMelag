package javachallenge.client;

import javachallenge.common.Action;
import javachallenge.common.ActionType;
import javachallenge.common.AgentMessage;
import javachallenge.common.BlockType;
import javachallenge.common.Direction;
import javachallenge.common.Point;

public class Agent {
	public final static int noAgent = -1 ;
	
	private Point location;
	private BlockType[] blockTypes;
	int id;

	private Direction direction;

	public Agent(int spawnedId, Point spawnLocation) {
		this.id = spawnedId ;
		this.location = spawnLocation ;
	}

	void updateAgent(AgentMessage msg){
		this.setLocation(msg.getLocation());
		this.setAdjBlockType(msg.getBlockTypes()) ;
	}
	
	void beginStep() {
		this.direction = null;
	}
	
	public void doMove(Direction d) {
		this.direction = d;
	}
	
	Action endStep() {
		if (direction != null)
			return new Action(ActionType.MOVE, direction, id);
		return null;
	}
	
	private void setAdjBlockType(BlockType[] blockTypes) {
		this.blockTypes = blockTypes ;
	}

	private void setLocation(Point location) {
		this.location = location ;
	}
	
	public BlockType getAdjBlockType(Direction dir){
		return blockTypes[dir.ordinal()];
	}
	
	public Point getLocation(){
		return location ;
	}

	public int getId() {
		return this.id;
	}
}