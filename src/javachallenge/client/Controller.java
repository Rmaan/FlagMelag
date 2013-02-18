package javachallenge.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javachallenge.common.ClientMessage;
import javachallenge.common.InitMessage;
import javachallenge.common.ServerMessage;

public class Controller {
	private static int PORT = 5555;
	private static String IP = "127.0.0.1";
	
	protected static final long WAIT_TIME = 50;
	Player player ;
	private ServerMessage serverMsg;
	private ServerMessage msg;
	private Object lock = new Object();
	private boolean gameEnded = false;
	private World world;
	
	private final int CYCLE_TIME = 500;
	private ObjectOutputStream out;
	private ObjectInputStream   in;
	
	
	public Controller(String IP, int PORT) throws Exception{
		
		System.out.println("Creating socket...");
		Socket s = new Socket(IP, PORT);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
		
		System.out.println("Creating player & world...");
		world = new World();
		player = new Player(world) ;
		
		System.out.println("Waiting for initial Msg...");
		InitMessage initMsg = (InitMessage)in.readObject();
		player.initMsg(initMsg);
		
		System.out.println("Starting the game?!...");
		new Thread(){
			public void run() {
					try {
						while(!gameEnded){
							//TODO: end game
							ServerMessage tmp = (ServerMessage)in.readObject();
							synchronized (lock) {
								serverMsg = tmp;
							}
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
							player.prepareClientMsg();
							player.step() ;
							ClientMessage cMsg = player.getClientMsg() ;
							out.writeObject(cMsg);
						}
						else{
							Thread.sleep(WAIT_TIME);
						}
					}
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	
	public static void main(String[] args) throws Exception {
		new Controller(IP, PORT);
	}
}
