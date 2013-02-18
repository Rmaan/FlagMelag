package javachallenge.graphics.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.ImageIcon;

public abstract class ClickableLabel extends Label implements Serializable {
	public MouseListener mouseListener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {  }
		
		@Override
		public void mousePressed(MouseEvent arg0) {  }
		
		@Override
		public void mouseExited(MouseEvent arg0) {
			ClickableLabel.this.onExit();  
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			ClickableLabel.this.onEnter();
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			ClickableLabel.this.onClick();
		}
	};
	
	public ClickableLabel (String text) {
		super (text);
		addMouseListener(mouseListener);
	}
	public ClickableLabel (ImageIcon icon) {
		super (icon);
		addMouseListener(mouseListener);
	}
	
	public abstract void onClick();
	public void onEnter() {}
	public void onExit() {}
}