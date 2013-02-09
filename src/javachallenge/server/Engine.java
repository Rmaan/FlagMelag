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
	private ArrayList<ObjectOutputStream> oos; //TODO fill them!
	private ArrayList<ObjectInputStream> ois;
	
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
		
		for (int i = 0; i < teamCount; i++) {
			// TODO threaded!
			ServerMessage message = prepareTeamMessage(i);
			oos.get(i).writeObject(message);
			oos.get(i).flush();
		}
		
		for (int i = 0; i < teamCount; i++) {
			// TODO threaded!
			ClientMessage msg = (ClientMessage) ois.get(i).readObject();
		}
		
		throw new NotImplementedException();
	}
}
