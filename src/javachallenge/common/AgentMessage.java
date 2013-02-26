package javachallenge.common;

import java.io.Serializable;
import java.util.Arrays;

public class AgentMessage implements Serializable {
	private static final long serialVersionUID = -5218587968391584486L;
	
	private int id;
	private Point location;
	private VisionPoint[] visions;
	
	public AgentMessage(int id, Point location, VisionPoint[] visions) {
		super();
		this.id = id;
		this.location = location;
		this.visions = visions ;
	}

	public int getId() {
		return id;
	}

	public Point getLocation() {
		return location;
	}
	
	public VisionPoint[] getVisions() {
		return visions;
	}

	@Override
	public String toString() {
		return "AgentMessage [id=" + id + ", location=" + location
				+ ", blockTypes=" + Arrays.toString(blockTypes)
				+ ", agentTeamId=" + Arrays.toString(agentTeamId) + ", fire="
				+ Arrays.toString(fire) + "]";
	}
	
	
}
