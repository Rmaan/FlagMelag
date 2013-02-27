package javachallenge.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javachallenge.client.myplayer.MyPlayer;
import javachallenge.common.ClientMessage;
import javachallenge.common.InitMessage;
import javachallenge.common.ServerMessage;

public class Controller {
	protected static final long WAIT_TIME = 10;
	Player player ;
	private ServerMessage serverMsg;
	private ServerMessage msg;
	private Object lock = new Object();
	private boolean gameEnded = false;
	private World world;
	
	private ObjectOutputStream out;
	private ObjectInputStream   in;
	
	
	public Controller(String IP, int PORT) throws UnknownHostException, IOException, ClassNotFoundException {
		System.out.println("Creating socket...");
		final Socket s = new Socket(IP, PORT);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
		
//		System.out.println("Creating player & world...");
		
		
		System.out.println("Waiting for initial Msg...");
		InitMessage initMsg = (InitMessage)in.readObject();
		
		
		//---------------------------
		world = new World();
		player = new MyPlayer() ;
		player.setWorld(world) ;
		player.initMsg(initMsg);
		//---------------------------
		
		
		System.out.println("Starting game");
		new Thread(){
			public void run() {
				try {
					while(!gameEnded){
						//TODO: end game
						ServerMessage tmp = (ServerMessage)in.readObject();
					//	System.out.println("Got server message : " + serverMsg);
						synchronized (lock) {
							if(serverMsg != null){
								//System.out.println("TIMED OUT SEEEEEEEEEEEEEET");
								player.setTimedOut(true);
							}
							serverMsg = tmp;
							player.spawn(serverMsg.getSpawnedId());
						}
						gameEnded = serverMsg.isGameEnded() ; 
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				System.exit(1);
			};
			
		}.start();
		
		new Thread(){
			public void run() {
				try{
					while(!gameEnded){
						synchronized (lock) {
							msg = serverMsg;
							serverMsg = null;
							player.setTimedOut(false);
							//System.out.println("Read Message " + msg);
						}
						if(msg != null){
							player.updateMsg(msg) ;
							//------------------------
							player.beginStep();
							player.step() ;
							ClientMessage cMsg = player.endStep() ;
//							System.out.println("Sending actions");
//							System.out.println(cMsg);
							out.writeObject(cMsg);
						}
						else{
							Thread.sleep(WAIT_TIME);
						}
					}
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.exit(1);
			};
		}.start();
	}
}
