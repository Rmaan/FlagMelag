	package javachallenge.client;

import java.io.IOException;
import java.net.UnknownHostException;


public class Main {
	private final static String IP = "127.0.0.1";
	private final static int PORT = 5555;
	
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		new Controller(IP, PORT);
	}
}
