package javachallenge.common;

public class AgentMessage {
	private int id;
	private Point location;
	private BlockType[] blockTypes;
	private int[] agentTeamId;
	
	public AgentMessage(int id, Point location, BlockType[] blockTypes, int[] agentTeamId) {
		super();
		this.id = id;
		this.location = location;
		this.blockTypes = blockTypes;
		this.agentTeamId = agentTeamId;
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
