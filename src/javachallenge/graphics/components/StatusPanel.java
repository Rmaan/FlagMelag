package javachallenge.graphics.components;

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
	protected ArrayList<Label> labels=new ArrayList<Label>();
	protected Label time, score;
	protected int yNow =GAP_SIZE;
	public void addLabel(Label label)
	{
		//System.err.println("add label " + label);
		label.setBounds(GAP_SIZE, yNow,getWidth()-2*GAP_SIZE,30);
		labels.add(label);
		add(label);
		yNow+=30+GAP_SIZE;
	}
	public StatusPanel(Color color)
	{
		super(color);
		setLayout(new FlowLayout());
		//addLabel(new Label("salam"));
		addLabel(time = new Label("time: 0"));
		addLabel(score = new Label("score: 0"));
	}
	public void updatePosition()
	{
		//for (Label label:labels)
		//	label.setBounds(GAP_SIZE,label.getY(),getWidth()-2*GAP_SIZE,30);
	}
	
	public void setTime(int a) {
		time.setText("time: " + a);
	}
	
	public void setScore(int a) {
		score.setText("score: " + a);
	}
}
