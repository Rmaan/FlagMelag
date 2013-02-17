package javachallenge.graphics.util;

import java.awt.Color;

/**
 * This class provides some tools for working with RGB colors, and also holds common colors statically.
 */
@SuppressWarnings("serial")
public class ColorMaker extends Color {
	public static ColorMaker background = new ColorMaker (150, 150, 150);
	public static ColorMaker fieldBackground = new ColorMaker (200, 200, 200);
	public static ColorMaker panelBack = new ColorMaker (80, 80, 80);
	public static ColorMaker shadedPanelBack = new ColorMaker (75, 75, 75);
	public static ColorMaker title = new ColorMaker (255, 102, 102);
	public static ColorMaker buttonText = new ColorMaker (255, 136, 136);
	public static ColorMaker optionPanelBack = new ColorMaker (90, 90, 90);
	public static ColorMaker warning = new ColorMaker (255, 68, 68);
	public static ColorMaker gold = new ColorMaker (255, 220, 150);
	public static ColorMaker income = new ColorMaker (200, 255, 150);
	public static ColorMaker blood = new ColorMaker (200, 50, 50);
	public static ColorMaker greenBlood = new ColorMaker (50, 200, 50);
	public static ColorMaker readyClient = new ColorMaker(yellow);
	
	private Color color;
	public ColorMaker (Color color) {
		super (color.getRed(), color.getGreen(), color.getBlue());
		this.color = color;
	}
	
	public ColorMaker (int r, int g, int b) {
		this (new Color (r, g, b));
	}
	
	public String intToHex(int x) {
		String hex = Integer.toHexString(x);
		while (hex.length() < 2)
			hex = "0" + hex;
		return hex;
	}
	
	public static Color lightener (Color color, int deg) {
		return new Color (color.getRed() + deg, color.getGreen() + deg, color.getBlue() + deg);
	}
	
	public String toString() {
		return "#" + intToHex (color.getRed()) +
				intToHex (color.getGreen()) +
				intToHex (color.getBlue());
	}
}

//icons HSB: 2, -10, 32
//small icons HSB: -5, 14, 20