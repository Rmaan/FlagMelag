package javachallenge.server;

import java.net.Socket;

import javachallenge.common.ServerMessage;

public class TeamConnection {
	private  Team team;
	private Socket socket;
	private ServerMessage message;
	
	public TeamConnection(Team team, Socket socket){
		this.team = team;
		this.socket = socket;
	}

	public void sendInitialMessage(){
		
	}
	
	public void prepareMessage(){
		message = new ServerMessage();
	}
	
	public void addRespawnMessage(int id){
		
	}
	
}
