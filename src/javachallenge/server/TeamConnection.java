package javachallenge.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javachallenge.common.ClientMessage;
import javachallenge.common.InitMessage;
import javachallenge.common.ServerMessage;
import javachallenge.graphics.GraphicClient;

public class TeamConnection {
	private Team team;
//	private Socket socket;
	private ObjectOutputStream out;
	public Team getTeam() {
		return team;
	}

	private ObjectInputStream in;
	private boolean listening = true;
	private ClientMessage clientMessage;
	private GraphicClient graphicClient;
	
	public TeamConnection(final Team team, Socket socket, GraphicClient gc) throws IOException{
		this.team = team;
		this.graphicClient = gc;
//		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		
		try {
//			System.err.println("Waiting for name...");
			team.setName((String) in.readObject()) ;
//			System.err.println("Read name...");
		} catch (ClassNotFoundException e1) {
			//pass do nothing 
		}
		
		// Recieve team messages
		new Thread("Recieve Message Team "+ team.getId()) {
			@Override
			public void run() {
				try {
					while (listening) {
						ClientMessage tmp = (ClientMessage)in.readObject();
						synchronized (TeamConnection.this) {
							clientMessage = tmp;
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
				graphicClient.log("Team " + team.getId() + " disconnected");
			}
		}.start();
	}

	public synchronized ClientMessage getClientMessage() {
		return clientMessage;
	}
	
	public synchronized void clearClientMessage() {
		clientMessage = null;
	}

	public void sendInitialMessage(InitMessage msg) throws IOException {
		out.writeObject(msg);
		out.flush();
	}
	
	public void sendStepMessage(ServerMessage msg) throws IOException {
		out.writeObject(msg);
		out.flush();
	}
}
