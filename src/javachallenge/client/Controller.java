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
	protected static final long WAIT_TIME = 50;
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
						synchronized (lock) {
							serverMsg = tmp;
						}
						gameEnded = serverMsg.isGameEnded() ; 
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		
		new Thread(){
			public void run() {
				try{
					while(!gameEnded){
						synchronized (lock) {
							msg = serverMsg;
						}
						if(msg != null){
							synchronized (lock) {
								serverMsg = null;
							}
							player.updateMsg(msg) ;
							//------------------------
							player.beginStep();
							player.step() ;
							ClientMessage cMsg = player.endStep() ;
							System.out.println("Sending actions");
							out.writeObject(cMsg);
						}
						else{
							Thread.sleep(WAIT_TIME);
						}
					}
					s.close();
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			};
		}.start();
	}
}
