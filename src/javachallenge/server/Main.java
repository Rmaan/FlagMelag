package javachallenge.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javachallenge.common.Action;
import javachallenge.common.ClientMessage;
import javachallenge.common.InitMessage;
import javachallenge.common.Point;
import javachallenge.common.ServerMessage;
import javachallenge.graphics.GraphicClient;
import javachallenge.graphics.GraphicClient.OutOfMapException;
import javachallenge.graphics.util.Position;

public class Main {
	private static int PORT = 5555;
	private static int CYCLE_TIME = 350;
	private GraphicClient graphicClient;
	
	private final boolean DBG_PAUSE_ENABLED = false;
	private final int DBG_PAUSE_CYCLE_NUM = 52;
	private final int DBG_PAUSE_CYCLE_TIME = 350;
	
	public void run(int listenPort, String mapFilename) throws IOException, InterruptedException, OutOfMapException {
		ServerSocket ss = new ServerSocket(listenPort);
		Map map = Map.load(mapFilename);
		
		Position[] tmpFlagPositions = new Position[map.getFlagLocations().size()];
		for(int i = 0 ; i < map.getFlagLocations().size() ; i++) {
			Point flag = map.getFlagLocations().get(i);
			tmpFlagPositions[i] = new Position(flag.x, flag.y);
		}
		
		Game g = new Game(map);
		RemoteControl ctrl = new RemoteControl();
		graphicClient = new GraphicClient(map, ctrl);
		Engine engine = new Engine(g, graphicClient);
		
		ArrayList<TeamConnection> connections = new ArrayList<TeamConnection>();
		
		for (int i = 0; i < map.getTeamCount(); i++) {
			System.out.println("Waiting for team " + i + " to connect...");
			Socket socket = ss.accept();
			connections.add(new TeamConnection(engine.getTeam(i), socket));
			
			Team team = engine.getTeam(i) ;
			graphicClient.setName(team.getId(), team.getName()) ;
			
			System.out.println("connected");
		}

		ArrayList<InitMessage> initialMessage = engine.getInitialMessage();
		for (TeamConnection c: connections) {
			c.sendInitialMessage(initialMessage.get(c.getTeam().getId()));
		}
		
		engine.beginStep();
		engine.teamStep(new ArrayList<Action>());
		engine.endStep();
		
		Scanner scn = DBG_PAUSE_ENABLED ? new Scanner(System.in) : null;
		
		int temp = 1 ;
		while (!engine.gameIsOver() || temp != 0) {
			ctrl.waitForPlay();
			
			temp += (engine.gameIsOver() ? -1 : 0) ;
			
			//System.out.println("Cycle " + engine.getCycle());
			
			ArrayList<ServerMessage> stepMessage = engine.getStepMessage();
			for (int i = 0; i < map.getTeamCount(); i++) {
				connections.get(i).sendStepMessage(stepMessage.get(i));
				System.out.println("Sending to team " + i + " : " + stepMessage.get(i));
				connections.get(i).clearClientMessage();
			}
			System.out.println("-----------------------------------------");
			
			if (DBG_PAUSE_ENABLED) {
				if (engine.getCycle() < DBG_PAUSE_CYCLE_NUM)
					Thread.sleep(DBG_PAUSE_CYCLE_TIME);
				else
					scn.nextLine();
			} else {
				Thread.sleep(CYCLE_TIME);
			}

			ArrayList<Action> allActions = new ArrayList<Action>();
			for (int i = 0; i < map.getTeamCount(); i++) {
				ClientMessage msg = connections.get(i).getClientMessage();
//				System.out.println("Recieved from team " + i + " : " + msg);
				if (msg == null) {
					System.out.println("Team " + i + " message loss");
				} else {
					ArrayList<Action> actions = msg.getActions();
					for(Action action : actions){
						action.setTeamId(i);
					}
					actions = validate(actions);
//					System.out.println("Actions team " + i);
//					System.out.println(actions);
					allActions.addAll(actions);
				}
			}
			engine.beginStep();
			engine.teamStep(allActions);
			engine.endStep();
		}
		
		ss.close();
		System.out.println(engine.getTeamScore());
	}
	
	private ArrayList<Action> validate(ArrayList<Action> actions) {
		ArrayList<Action> validActions = new ArrayList<Action>();
		ArrayList<Integer> seen = new ArrayList<Integer>();
		
		for(int i = actions.size() - 1 ; i >= 0 ; i--){
			Action action = actions.get(i);
			if(seen.contains(action.getId())){
				System.out.println("Client manipulation from team : " + action.getTeamId());
			}
			else{
				seen.add(action.getId());
				validActions.add(action);
			}
		}
		
		return validActions;
	}

	public static void main(String[] args) throws IOException, InterruptedException, OutOfMapException {
		new Main().run(PORT, args.length < 1 ? "map/final1.txt" : args[0]);
	}
}
