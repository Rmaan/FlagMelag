package javachallenge.graphics.components;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 2/25/13
 * Time: 7:29 PM
 */
public class ProgressBar extends Panel
{
	protected Label loaded,empty;
	protected int w,h;
	public ProgressBar(int x, int y, int w, int h,Color fill,Color empty,double ratio)
	{
		super(x, y, w, h);
		this.w=w; this.h=h;
		loaded=new Label();
		loaded.setOpaque(true);
		loaded.setBackground(fill);
		this.empty=new Label();
		this.empty.setOpaque(true);
		this.empty.setBackground(empty);
		add(loaded);
		add(this.empty);
		updateProgressBar(ratio);
	}

	public void updateProgressBar(double ratio)
	{
		int first=(int)Math.round(w*ratio);
		loaded.setBounds(0,0,first,h);
		empty.setBounds(0+first,0,w-first,h);
	}
}
