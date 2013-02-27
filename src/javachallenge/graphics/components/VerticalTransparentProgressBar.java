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
}
