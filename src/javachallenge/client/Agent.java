package javachallenge.client;

import javachallenge.common.Action;
import javachallenge.common.ActionType;
import javachallenge.common.AgentMessage;
import javachallenge.common.Direction;
import javachallenge.common.Point;
import javachallenge.common.VisionPoint;

public class Agent {
	public final static int noAgent = -1 ;
	
	private Point location;
	int id;

	private Direction direction;

	private VisionPoint[] visions;

	public Agent(int spawnedId, Point spawnLocation) {
		this.id = spawnedId ;
		this.location = spawnLocation ;
	}

	void updateAgent(AgentMessage msg){
		location = msg.getLocation();
		visions = msg.getVisions() ;
	}
	
	void beginStep() {
		this.direction = null;
	}
	
	/**
	 * Moves agent in specified direction it end of cycle 
	 */
	public void doMove(Direction d) {
		this.direction = d;
	}
	
	Action endStep() {
		if (direction != null)
			return new Action(ActionType.MOVE, direction, id);
		return null;
	}
	
	/**
	 *  @return the vision for adjecnet cell (NONE return the vision for same location)
	 */
	public VisionPoint getAdjVisionPoint(Direction dir){
		return visions[dir.ordinal()];
	}
	
	/**
	 * @return location agent standing
	 */
	public Point getLocation(){
		return location ;
	}

	public int getId() {
		return this.id;
	}
}