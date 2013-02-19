package javachallenge.client;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javachallenge.common.Action;
import javachallenge.common.ActionType;
import javachallenge.common.Point;
import javachallenge.common.Direction;

public class Player extends Behrooz {
	int[] color;
	Vector<Vector<Integer>> adjList;
	Direction[] myDirection = new Direction[6];
	int cycle = 0;

	public Player(World world) {
		super(world);
		// int hei = this.getWorld().getMap().getHei();
		// int wid=this.getWorld().getMap().getWid();
		// color=new int[hei*wid];
		// adjList=new Vector<Vector<Integer>>(hei*wid);
		for(int i=0;i<6;i++)
			myDirection[i]=Direction.SOUTH;
	}

	// void DFS(int u) {
	// mytime++;
	// color[u] = true;
	// for (int i = 0; i < adjList[u].size(); i++) {
	// int v = adjList[u][i];
	// if (color[v] == false) {
	// DFS(v);
	// }
	// }
	// topolSort.push_back(u);
	// }

	public void step() {
/*		Direction[] p1 = {Direction.SOUTH, Direction.SOUTH, Direction.NORTH};
		// return ;
		if (cycle > 2){
			return;
		}
		
		ArrayList<Integer> agents = getAgentAliveId();
		for(int i = 0 ; i < agents.size(); i++){
			if(i == 0){
				Action act = new Action(ActionType.MOVE, p1[cycle], agents.get(i));
				doAction(act);
			}
			if(i == 1){
				if(cycle == 1){
					doAction(new Action(ActionType.NONE, Direction.SOUTH, 0));
				}
				else if(cycle == 2){
					doAction(new Action(ActionType.MOVE, Direction.SOUTH, agents.get(i)));
				}
			}
		}
		cycle++;*/

		Random random = new Random(System.currentTimeMillis());
		World myWorld = this.getWorld();
		// myWorld.getMap().get
		int height = this.getWorld().getMapHeight();
		int width = this.getWorld().getMapWidth();
		// Point[] flagLocs= (Point[])
		// (myWorld.getMap().getFlagLocations().toArray());

		// for(int i = 0 ; i < this.getAgentAliveId().size() ; i++){
		// int d = random.nextInt(6);
		// int ID = this.getAgentAliveId().get(i);
		// Action action=new Action(ActionType.MOVE, Direction.SOUTHEAST, ID);
		// this.doAction(action);
		// // this.getAgentById(ID).
		// }
		// Point SL = this.getSpawnLocation();
		// for(int i = 0 ; i < this.getAgentAliveId().size() ; i++){
		// for (int i = 0; i < height - SL.y - 2; i++) {
		for (int i = 0; i < this.getAgentAliveId().size(); i++) {
			int ID = this.getAgentAliveId().get(i);

			if (this.getAgentById(ID).getLocation().y == 1
					&& myDirection[i] != Direction.NORTHEAST) {
				myDirection[i] = Direction.NORTHEAST;
				this.getAgentById(ID).getAdjBlockType(Direction.NORTHEAST);
				Action action = new Action(ActionType.MOVE,
						Direction.NORTHEAST, ID);
				this.doAction(action);
				continue;
			}
			if (height - this.getAgentById(ID).getLocation().y == 1
					&& myDirection[i] != Direction.SOUTHEAST) {
				myDirection[i] = Direction.SOUTHEAST;
				Action action = new Action(ActionType.MOVE,
						Direction.SOUTHEAST, ID);
				this.doAction(action);
				continue;
			}
			if (myDirection[i] == Direction.SOUTHEAST
					|| myDirection[i] == Direction.NORTH) {
				myDirection[i] = Direction.NORTH;
				Action action = new Action(ActionType.MOVE, Direction.NORTH, ID);
				this.doAction(action);
				continue;
			}
			if (myDirection[i] == Direction.NORTHEAST
					|| myDirection[i] == Direction.SOUTH) {
				// if(this.getAgentById(ID).getLocation().y==0){
				myDirection[i] = Direction.SOUTH;
				Action action = new Action(ActionType.MOVE, Direction.SOUTH, ID);
				this.doAction(action);
				continue;
				// }

			}
		}

		// if (myDirection==Direction.SOUTH) {
		// myDirection=Direction.SOUTH;
		// Action action = new Action(ActionType.MOVE, Direction.SOUTH, ID);
		// this.doAction(action);
		// return;
		// }

		// }

	}
	// public void DFS(int u){
	// color[u]=1;
	// for(int i=0;i<)
	// }
}
