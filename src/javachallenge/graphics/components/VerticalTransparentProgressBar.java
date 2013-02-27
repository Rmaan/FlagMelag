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

	public void setLast(int last) {
		this.last = last;
	}
	
	synchronized public void animatedBar(final int progressPercentage)
	{
		new Thread()
		{
			public void run()
			{
				while (last!=progressPercentage)
				{
					int diff = progressPercentage - last;
					int sign = diff < 0 ? -1 : 1;
					int step = Math.min(Math.abs(diff), 3) * sign; 
					
					last+=step;
					updateVerticalTransparentProgressBar((double)last/100);
					
					try
					{
						sleep(4);
					} catch (InterruptedException e)
					{
						e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
					}
				}
			}
		}.start();
	}
}
