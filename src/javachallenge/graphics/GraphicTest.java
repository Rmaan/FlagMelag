package javachallenge.graphics;

import javachallenge.graphics.GraphicClient;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Scanner;

import javachallenge.graphics.components.Label;
import javachallenge.graphics.components.MapPanel;
import javachallenge.graphics.components.Screen;
import javachallenge.graphics.util.ColorMaker;
import javachallenge.graphics.util.HTMLMaker;
import javachallenge.graphics.util.ImageHolder;
import javachallenge.graphics.util.Position;

public class GraphicTest {
	public static void main(String[] args) {
		GraphicClient client=new GraphicClient(40,20,new Position[]{new Position(4,2)});
		
		Scanner scanner=new Scanner(System.in);
		while (true)
		{
			String com=scanner.next();
			System.err.println("read " + com);
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
				client.move(id,pos);
			}
			else if (com.equals("flag")) {
				int id = scanner.nextInt();
				client.obtainFlag (id);
			}
		}
	}
}
