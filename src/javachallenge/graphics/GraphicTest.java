package javachallenge.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import javachallenge.common.Direction;
import javachallenge.common.Map;
import javachallenge.graphics.GraphicClient;
import javachallenge.graphics.util.Position;

public class GraphicTest {
	public static void main(String[] args) throws Exception{
		ObjectInputStream stream=new ObjectInputStream(new FileInputStream(new File("data/maps/chert.map")));
		Map map=(Map)stream.readObject();
		stream.close();
		GraphicClient client=new GraphicClient(map);
		Scanner scanner=new Scanner(System.in);
		while (true)
		{
			String com=scanner.next();
			if (com.startsWith("s")) // spawn
			{
				int id=scanner.nextInt(),x=scanner.nextInt(),y=scanner.nextInt();
				client.spawn(id,new Position(x,y));
			}
			else if (com.startsWith("k")) // kill
			{	
				int id=scanner.nextInt();
				client.die(id);
			}
			else if (com.startsWith("m")) // move
			{
				int id=scanner.nextInt(),pos=scanner.nextInt();
				client.move(id,Direction.values()[pos]);
			}
			else if (com.startsWith("f")) { // obtain flag
				int id = scanner.nextInt();
				client.obtainFlag (id);
			}
			else if (com.startsWith("h")) { // help
				System.out.println("h[elp]");
				System.out.println("e[xit]");
				System.out.println("s[pawn] id x y");
				System.out.println("k[ill] id");
				System.out.println("m[ove] id dir");
				System.out.println("f[lag] id");
				System.out.println(client.units);
				System.out.println(client.flags);
			}
			else if (com.startsWith("e")) // exit
				break;
		}
		scanner.close();
	}
}
