package javachallenge.graphics.components;

import javachallenge.graphics.util.ColorMaker;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 2/18/13
 * Time: 12:20 AM
 */
public class StatusPanel extends Panel
{
	protected Label score=new Label(),time=new Label();
	protected int ynow=5;
	public void setScore(int a)
	{

	}
	public void setTime(int a)
	{

	}
	public void addLabel(Label n)
	{
		n.setBounds(5,ynow,0,0);
	}
	public StatusPanel(Color color)
	{
		super(color);
		updatePosition();
	}
	public void updatePosition()
	{

	}
}
