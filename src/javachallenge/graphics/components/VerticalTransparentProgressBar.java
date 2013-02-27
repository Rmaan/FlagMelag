package javachallenge.graphics.components;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 2/27/13
 * Time: 11:19 AM
 */
public class VerticalTransparentProgressBar extends Panel
{
	protected Label loaded;
	protected int w,h;
	protected int last=0;
	public VerticalTransparentProgressBar(int x, int y, int w, int h, Color fill, double ratio)
	{
		super(x, y, w, h);
		setOpaque(false);
		this.w=w; this.h=h;
		loaded=new Label();
		loaded.setOpaque(true);
		loaded.setBackground(fill);
		add(loaded);
		updateVerticalTransparentProgressBar(ratio);
	}
	public void setColor(Color color)
	{
		loaded.setBackground(color);
	}
	public void updateVerticalTransparentProgressBar(double ratio)
	{
		int first=(int)Math.round(h*ratio);
		loaded.setBounds(0,h-first,w,first);
	}

	synchronized public void animatedBar(final int progressPercentage)
	{
		final int step=(last>progressPercentage?-1:1);
		new Thread()
		{
			public void run()
			{
				while (last!=progressPercentage)
				{
					updateVerticalTransparentProgressBar((double)last/100);
					last+=step;
					try
					{
						sleep(5);
					} catch (InterruptedException e)
					{
						e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
					}
				}
			}
		}.start();
	}
}
