package javachallenge.graphics.util;

import javachallenge.graphics.components.Sprite;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class AnimatedImage extends Sprite {
	protected ImageIcon[] icons;
	protected int index=0;
	protected boolean repeated = false;
	private boolean stop = false;
	protected boolean destroyed=false;
	protected ImageAnimator animator;
	public void setDestroyed(boolean destroyed)
	{
		this.destroyed = destroyed;
	}

	public AnimatedImage(ImageIcon[] icons, Position position,boolean repeated, ImageAnimator animator) {
		super(icons[0], position);
		this.animator=animator;
		this.icons = icons;
		this.repeated = repeated;
		if (repeated)
			animator.add(this);
	/*	showThread = new Thread("AnimatedImage") {
			public void run() {
				int index = 0;
				while (!stop) {
					AnimatedImage.this.setIcon(AnimatedImage.this.icons[index]);
					index = (index  + 1) % AnimatedImage.this.icons.length;
					if (AnimatedImage.this.repeated == false && index == 0) break;
					try { sleep(AnimatedImage.this.delay); } catch (InterruptedException e) {}
				}
			}
		};
		if (repeated)
			showThread.start();*/
	}
	
	public void start() {
		destroyed=false;
		animator.add(this);
	}
	
	public void destroy() {
		destroyed=true;
		stop = true;
	}
	public void next()
	{
		index++;
		if (index>=icons.length)
			index-=icons.length;
		setIcon(icons[index]);
	}
	public boolean isDestroyed()
	{
		return destroyed;
	}
}
