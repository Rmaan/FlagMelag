package javachallenge.graphics.components;

import javachallenge.graphics.util.Position;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Sprite extends Label {
	protected Position position;
	
	public Sprite(ImageIcon icon, Position position) {
		super (icon);
		this.position = position;
		Position absPosition = MapPanel.getAbsolutePosition(
				position.getX(), position.getY());
		setBounds(absPosition.getX(), absPosition.getY(), 36, 36);
	}
	
	public Position getPosition() {
		return position;
	}
}
