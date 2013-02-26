package javachallenge.graphics.components;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 2/26/13
 * Time: 3:17 PM
 */
public class ScoreBar extends Panel
{
	protected ProgressBar bar;
	protected Label status;
	protected String name;
	protected int score;
	public ScoreBar(int x, int y, int w, int h, Color fill,Color empty,String name)
	{
		super(x, y, w, h);
		bar=new ProgressBar(0,30,w,h-30,fill,empty,0);
		add(bar);
		this.name=name;
		(status=new Label()).setBounds(0,0,w,30);
		setScore(0);
		add(status);
	}
	public void updateBar(double ratio)
	{
		bar.updateProgressBar(ratio);
	}
	public void setScore(int score)
	{
		this.score=score;
		update();
	}
	public void setName(String name)
	{
		this.name=name;
		update();
	}
	protected void update()
	{
		status.setText(name+": "+score);
	}
}
