package javachallenge.common;

import java.io.Serializable;

public class AgentMessage implements Serializable {
	private static final long serialVersionUID = -5218587968391584486L;
	
	private int id;
	private Point location;
	private BlockType[] blockTypes;
	private int[] agentTeamId;
	private boolean[] fire;
	
	public AgentMessage(int id, Point location, BlockType[] blockTypes, int[] agentTeamId) {
		super();
		this.id = id;
		this.location = location;
		this.blockTypes = blockTypes;
		this.fire = fire;
		this.agentTeamId = agentTeamId;
	}

	public boolean[] getFire() {
		return fire;
	}

	public int getId() {
		return id;
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
}
