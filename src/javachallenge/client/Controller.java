package javachallenge.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javachallenge.common.ClientMessage;
import javachallenge.common.InitMessage;
import javachallenge.common.ServerMessage;

public class Controller {
	protected static final long WAIT_TIME = 50;
	Player player ;
	private ServerMessage serverMsg;
	private ServerMessage msg;
	private Object lock;
	private boolean gameEnded = false;
	private World world;
	
	private final int CYCLE_TIME = 500;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Controller(String IP, int PORT) throws Exception{
		
		Socket s = new Socket(IP, PORT);
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());

		world = new World();
		player = new Player(world) ;
		
		InitMessage initMsg = (InitMessage)in.readObject();
		player.initMsg(initMsg);
		
		new Thread(){
			public void run() {
				while(!gameEnded){
				try {
					//TODO: end game
						ServerMessage tmp = (ServerMessage)in.readObject();
						synchronized (lock) {
							serverMsg = tmp;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
		
		new Thread(){
			public void run() {
				while(!gameEnded){
					synchronized (lock) {
						msg = serverMsg;
					}
					try{
						if(msg != null){
							synchronized (lock) {
								serverMsg = null;
							}
							player.prepareClientMsg();
							player.step() ;
							ClientMessage cMsg = player.getClientMsg() ;
							out.writeObject(cMsg);
						}
						else{
							Thread.sleep(WAIT_TIME);
						}
					}
					catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
	
	
	public static void main(String[] args) throws Exception {
		new Controller("", 0);
	}
}
