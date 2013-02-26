package javachallenge.graphics.components;

import javachallenge.graphics.PlayGround;
import javachallenge.graphics.util.ColorMaker;
import javachallenge.graphics.util.HTMLMaker;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 2/18/13
 * Time: 12:20 AM
 */
public class StatusPanel extends Panel
{
	public static int GAP_SIZE=10;
	protected Label time;
	protected ScoreBar[] scores;
	public static Color[] filled={ 
		new ColorMaker(250, 75, 75), 
		new ColorMaker(250, 250, 75) , 
		new ColorMaker(150, 150, 250),
		Color.RED
	};
	public static Color[] empty={ 
		new ColorMaker(150, 75, 75), 
		new ColorMaker(150, 150, 75) , 
		new ColorMaker(100, 100, 150),
		Color.RED
	};
	public void addBars(int no)
	{
		scores=new ScoreBar[no];
		for (int i=0;i<no;i++)
		{
			scores[i]=new ScoreBar(10,(i+1)*50, PlayGround.statusWidth-20,50,filled[i],empty[i],"Player "+(i+1));
			add(scores[i]);
		}
	}
	public StatusPanel(Color color)
	{
		super(color);
		setLayout(null);
		time=new Label();
		setTime(0);
		time.setBounds(10, 0, PlayGround.statusWidth-20, 30);
		add(time);
	//	addLabel(score = new Label("score: 0"));
	}
	public void updateScore(int id,int score,double ratio)
	{
		scores[id].updateBar(ratio);
		scores[id].setScore(score);
	}
	
	public void setTime(int a) {
		time.setText(new HTMLMaker("Time: "+a, ColorMaker.white, 10).toString());
	}

	public void setName(int id, String name)
	{
		scores[id].setName(name);
	}
}
