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
	private boolean[] fire;
	int id;

	private Direction direction;

	public Agent(int spawnedId, Point spawnLocation) {
		this.id = spawnedId ;
		this.location = spawnLocation ;
	}

	void updateAgent(AgentMessage msg){
		location = msg.getLocation();
		blockTypes = msg.getBlockTypes();
		fire = msg.getFire();
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
	
	public BlockType getAdjBlockType(Direction dir){
		return blockTypes[dir.ordinal()];
	}
	
	public boolean hasFire(Direction dir) {
		return fire[dir.ordinal()];
	}
	
	public Point getLocation(){
		return location ;
	}

	public int getId() {
		return this.id;
	}
}