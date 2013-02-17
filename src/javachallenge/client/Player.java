package javachallenge.client;


public class Player extends Behrooz {
	public Player(World world) {
		super(world);
	}
	
	public void step(){
		return ;
		/*
		Random random=new Random(System.currentTimeMillis());
		World myWorld=this.getWorld();
		Point[] flagLocs= (Point[])myWorld.getMap().getFlagLocations().toArray();
		
		Integer[] AliveID= (Integer[])(this.getAgentAliveId().toArray());
		for(int i=0;i<AliveID.length;i++){
			int direction = random.nextInt(6);
			int ID=AliveID[i];
			Action action=new Action(ActionType.MOVE, Direction.values()[direction],
					ID);
			this.doAction(action);
//			this.getAgentById(ID).
		}
		
		*/
	}
}
