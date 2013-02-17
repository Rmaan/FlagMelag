package javachallenge.graphics.components;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This class is directly derived from {@link JLabel} and added some features to it.
 */
@SuppressWarnings("serial")
public class Label extends JLabel {
	public Label () {
		setForeground(Color.WHITE);
	}
	
	public Label (int x, int y, int w, int h, String text) {
		this (text);
		setBounds(x, y, w, h);
	}
	
	public Label (int x, int y, int w, int h, ImageIcon icon) {
		this (icon);
		setBounds(x, y, w, h);
	}
	
	public Label (String text) {
		setText(text);
	}
	
	public Label (ImageIcon icon) {
		setIcon(icon);
	}
}