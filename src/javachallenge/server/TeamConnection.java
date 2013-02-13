package javachallenge.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javachallenge.common.AgentMessage;
import javachallenge.common.ServerMessage;

public class TeamConnection {
	private  Team team;
	private Socket socket;
	private ServerMessage message;
	private ObjectOutputStream out;
	
	public TeamConnection(Team team, Socket socket) throws IOException{
		this.team = team;
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
	}

	public void sendInitialMessage(){
		
	}
	
	public void prepareMessage(){
		message = new ServerMessage();
	}
	
	public void addRespawnMessage(int id){
		message.setSpawnedId(id);
	}
	
	public void addScoresMessage(ArrayList<Integer> scores){
		message.setScores(scores);
	}
	
	public void addAgentMessage(ArrayList<AgentMessage> agentmsg){
		message.setAgentMsg(agentmsg);
	}
	
	public void addDeadAgents(ArrayList<Integer> deadAgents){
		message.setDeadAgents(deadAgents);
	}
	
	public void sendMessage() throws IOException{
		out.writeObject(message);
	}
}
