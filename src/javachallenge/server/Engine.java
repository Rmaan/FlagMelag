package javachallenge.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javachallenge.NotImplementedException;
import javachallenge.common.ClientMessage;
import javachallenge.common.Map;
import javachallenge.common.ServerMessage;

public class Engine {
	private Map map;
	private int cycle, teamCount;
	private ArrayList<Team> team;
	
	public Engine(Map map) {
		this.map = map;
		this.cycle = 0;
		// TODO fill team
		teamCount = map.getTeamCount();
	}
	
	private ServerMessage prepareTeamMessage(int teamNum) {
		throw new NotImplementedException();
	}
	
	public void step() throws IOException, ClassNotFoundException {
		cycle++;
	}
}
