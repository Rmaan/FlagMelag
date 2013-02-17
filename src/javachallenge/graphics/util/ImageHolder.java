package javachallenge.graphics.util;

import javax.swing.ImageIcon;

/**
 * This class holds common images statically to prevent lags in loading images.
 */
public class ImageHolder {
	// resources
	public static String prefix = "data/pix/wesnoth/";
	public static String menuPrefix = prefix + "menu/";
	public static String corePrefix = prefix + "game/core/";
	public static String riseOfWesnothPrefix = prefix + "game/The_Rise_Of_Wesnoth/";
	
	public static ImageIcon[] getAll (String path, String type, int count, boolean removeFirstIndex) {
		ImageIcon[] all = new ImageIcon[count];
		for (int i = 1; i <= count; i++)
			all[i - 1] = new ImageIcon(path + "/" + type + 
				(i == 1 && removeFirstIndex ? "" : new Integer(i).toString()) + ".png");
		return all;
	}
	
	// common images
	public static ImageIcon mapBrush = new ImageIcon(menuPrefix + "editor/brush.png");
	public static ImageIcon waiting = new ImageIcon(menuPrefix + "cursors/wait.png");
	
	public static class Units {
		public static String prefix = riseOfWesnothPrefix + "units/";
		public static ImageIcon wesfolkOutcast = new ImageIcon(prefix + "wesfolk-outcast.png");
		public static ImageIcon wesfolkOutcastMirror = new ImageIcon(prefix + "wesfolk-outcast-mirror.png");
	}
	
	public static class Terrain {
		public static String prefix = corePrefix + "terrain/";
		
		public static ImageIcon[] grass = getAll(prefix + "grass", "green", 8, true);
		public static ImageIcon[] ice = getAll(prefix + "frozen", "ice", 6, true);
		public static ImageIcon[] beach = getAll(prefix + "sand", "beach", 8, true);
	}
	
	public static class Objects {
		public static ImageIcon blueFlag = new ImageIcon(menuPrefix + "editor/tool-overlay-starting-position.png");
		public static ImageIcon longFlag = new ImageIcon(menuPrefix + "flags/long-flag-1.png");
		//public static ImageIcon scepture = new ImageIcon(corePrefix + "items/sceptre-of-fire.png");
		public static ImageIcon brazier[] = getAll(corePrefix + "items", "brazier-lit", 8, false); 
		public static ImageIcon fire[] = getAll(corePrefix + "scenery", "fire", 8, false);
		public static ImageIcon underFire = new ImageIcon(corePrefix + "scenery/rune2-glow.png");
	}
}
