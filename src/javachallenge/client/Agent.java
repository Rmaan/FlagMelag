package javachallenge.client;

import javachallenge.common.AgentMessage;
import javachallenge.common.BlockType;
import javachallenge.common.Direction;
import javachallenge.common.Point;

public class Agent {
	public final static int noAgent = -1 ;
	
	private Point location;
	private BlockType[] blockTypes;
	private int[] agentTeamId;
	int id;

	private boolean alive;

	public Agent(int spawnedId, Point spawnLocation) {
		this.id = spawnedId ;
		this.location = spawnLocation ;
	}

	void updateAgent(AgentMessage msg){
		this.setLocation(msg.getLocation());
		this.setAdjBlockType(msg.getBlockTypes()) ;
		this.setAdjAgentId(msg.getAgentTeamId()) ;
	}

	private void setAdjAgentId(int[] agentTeamId) {
		this.agentTeamId = agentTeamId ;
	}

	private void setAdjBlockType(BlockType[] blockTypes) {
		this.blockTypes = blockTypes ;
	}

	private void setLocation(Point location) {
		this.location = location ;
	}
	
	public int getAdjEnemyTeamId(Direction dir){
		return agentTeamId[dir.ordinal()];
	}
	
	public BlockType getAdjBlockType(Direction dir){
		return blockTypes[dir.ordinal()];
	}
	
	public boolean hasAdjEnemy(Direction dir){
		return getAdjEnemyTeamId(dir) != noAgent ;
	}
	
	public Point getLocation(){
		return location ;
	}

	public int getId() {
		return this.id;
	}

	public void die() {
		this.alive = false ;
		
	}

	public boolean isAlive() {
		return this.alive;
	}
}