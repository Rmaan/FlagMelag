package javachallenge.graphics;

import java.util.Scanner;

import javachallenge.common.Direction;
import javachallenge.graphics.GraphicClient;
import javachallenge.graphics.util.Position;

public class GraphicTest {
	public static void main(String[] args) throws Exception{
		GraphicClient client=new GraphicClient(40,20,new Position[]{new Position(4,2)});
		
		Scanner scanner=new Scanner(System.in);
		while (true)
		{
			String com=scanner.next();
			if (com.equals("spawn"))
			{
				int id=scanner.nextInt(),x=scanner.nextInt(),y=scanner.nextInt();
				client.spawn(id,new Position(x,y));
			}
			else if (com.equals("kill"))
			{	
				int id=scanner.nextInt();
				client.die(id);
			}
			else if (com.equals("move"))
			{
				int id=scanner.nextInt(),pos=scanner.nextInt();
				client.move(id,Direction.values()[pos]);
			}
			else if (com.equals("flag")) {
				int id = scanner.nextInt();
				client.obtainFlag (id);
			}
		}
	}
}
