package javachallenge.client.myplayer;

import java.io.IOException;
import java.net.UnknownHostException;

import javachallenge.client.Controller;

public class Main {
	private final String IP = "127.0.0.1";
	private final int PORT = 5555;
	
	void run() throws UnknownHostException, ClassNotFoundException, IOException {
		new Controller(IP, PORT);
	}
	
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		new Main().run();
	}

}
