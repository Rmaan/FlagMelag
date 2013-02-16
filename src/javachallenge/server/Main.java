package javachallenge.server;

import java.util.ArrayList;

import javachallenge.common.Action;

public class Main {
	public void run() {
		ServerMap sampleMap = ServerMap.getSampleMap();
		Engine engine = new Engine(sampleMap);
		
		while (engine.gameIsOver()) {
			System.out.println("Cylce filan " + engine.getCycle());
			
			engine.beginStep();
			engine.teamStep(new ArrayList<Action>());
			engine.endStep();
		}
	}
	
	public static void main(String[] args) {
		new Main().run();
	}
}
