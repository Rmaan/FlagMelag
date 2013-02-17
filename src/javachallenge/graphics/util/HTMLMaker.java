package javachallenge.graphics.util;

import java.awt.Color;

/**
 * This class provides a tool for generating HTML format of a text with specific size and color.
 */
public class HTMLMaker {
	String HTML;
	public HTMLMaker (String text, Color color, int size) {
		HTML = "<font style='color:" + new ColorMaker(color) + "; font-size:" + size + "px;'>" + 
				text + "</font>";
	}
	
	public String toString() {
		return "<html><center>" + HTML + "</center></html>";
	}
	
	public String rawToString() {
		return HTML;
	}
}
