package javachallenge.common;

import java.io.Serializable;

public class VisionPoint implements Serializable {
	private static final long serialVersionUID = 4360952701992884079L;
	
	private Point location;
	private BlockType[] blockTypes;
	private int[] agentTeamId;
	
	public VisionPoint(Point location, BlockType[] blockTypes, int[] agentTeamId) {
		this.location = location;
		this.blockTypes = blockTypes;
		this.agentTeamId = agentTeamId;
	}

	public Point getLocation() {
		return location;
	}

	public BlockType[] getBlockTypes() {
		return blockTypes;
	}

	public int[] getAgentTeamId() {
		return agentTeamId;
	}
	
	
	/**
	 *  @return the block type of agent's passed direction
	 */
	public BlockType getAdjBlockType(Direction dir){
		return blockTypes[dir.ordinal()];
	}
	
	/**
	 *  @return the teamId of agent's in the adjecent cell (if NONE, adjecent cell is the same :D), if no one return -1 
	 */
	public int getAdjTeamId(Direction dir){
		return agentTeamId[dir.ordinal()];
	}
	
	/**
	 *  @return true if there is an agent in the adjecent cell (if NONE, adjecent cell is the same :D) 
	 */
	public boolean hasAdjAgent(Direction dir){
		return (agentTeamId[dir.ordinal()] == -1);
	}
	
//	/**
//	 *  @return  
//	 */
//	public int getAdjTeamId(Direction dir){
//		return agentTeamId[dir.ordinal()];
//	}
}
