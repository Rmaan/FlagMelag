package javachallenge.graphics;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

import javachallenge.common.Direction;
import javachallenge.graphics.components.VerticalTransparentProgressBar;
import javachallenge.graphics.util.Position;
import javachallenge.server.Map;

import javax.swing.*;

public class GraphicTest {
	public static void main(String[] args) throws Exception{
		Map map=Map.load("map/final1.txt");
		GraphicClient client=new GraphicClient(map, null);
		//GraphicClient client = new GraphicClient(30, 14, new Position[] {}); 
/*		JFrame frame=new JFrame();
		frame.setLayout(null);
		frame.getContentPane().setPreferredSize(new Dimension(400, 400));
		//final ProgressBar bar=new ProgressBar(10,10,100,20, Color.green,Color.red,0);
		VerticalTransparentProgressBar bar=new VerticalTransparentProgressBar(10,10,10,100,Color.green,0.5);
		frame.pack();
		frame.setVisible(true);
		frame.add(bar);
		for (;;)
		{
			bar.updateVerticalTransparentProgressBar((double)new Random().nextInt(101));
		}*/
		for (int i = 0; i < 40; i++)
			client.log("salam" + i);
		client.log("*iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiirr");
		Scanner scanner=new Scanner(System.in);
		client.log("hiiii");
		client.setScore(0,1000,0.75);
		client.setScore(1,1000,0.5);
		client.setScore(2,1000,0.6);
	//	client.spawn(1,new Position(2,2));
	//	client.spawn(2,new Position(2,3));
		while (true)
		{
			String com=scanner.next();
			if (com.startsWith("s")) // spawn
			{
				int id=scanner.nextInt(),x=scanner.nextInt(),y=scanner.nextInt();
	//			client.spawn(id,new Position(x,y));
			}
			else if (com.startsWith("k")) // kill
			{
				int id=scanner.nextInt();
				client.die(id);
			}
			else if (com.startsWith("m")) // move
			{
				int id=scanner.nextInt(),pos=scanner.nextInt();
		//		client.move(id,Direction.values()[pos]);
			}
			else if (com.startsWith("a")) // attack
			{
				int id=scanner.nextInt(),pos=scanner.nextInt();
		//		client.attack(id,Direction.values()[pos]);
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
			else if (com.startsWith("l")) {
				String s = scanner.next();
				client.log(s);
			}
			else if (com.startsWith("e")) // exit
				break;
		//	client.setName(1,"kir");
	//		client.attack(1,Direction.SOUTH);
	//		client.attack(2,Direction.NORTH);
		}
		scanner.close();
	}
}
