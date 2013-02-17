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
	protected Label scoreIcon,timeIcon,score=new Label("0"),time=new Label("0");

	public Label getScore()
	{
		return score;
	}

	public Label getTime()
	{
		return time;
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
