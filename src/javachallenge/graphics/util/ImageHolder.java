package javachallenge.graphics.util;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

/**
 * This class holds common images statically to prevent lags in loading images.
 */
public class ImageHolder {
	// resources
	public static String prefix = "gitdata/pix/wesnoth/";
	public static String buttons= "gitdata/pix/Buttons/";

	public static String menuPrefix = prefix + "menu/";
	public static String corePrefix = prefix + "game/core/";
	public static String riseOfWesnothPrefix = prefix + "game/The_Rise_Of_Wesnoth/";
	public static String heirToTheThronePrefix = prefix + "game/Heir_To_The_Throne/";

	public static ImageIcon[] getAll (String path, String type, int count, boolean removeFirstIndex) {
		ImageIcon[] all = new ImageIcon[count];
		for (int i = 1; i <= count; i++)
			all[i - 1] = new ImageIcon(path + "/" + type +
					(i == 1 && removeFirstIndex ? "" : new Integer(i).toString()) + ".png");
		return all;
	}
	
	public static ImageIcon[] concat (ImageIcon[]... args) {
		int size = 0;
		for (ImageIcon[] arr : args) size += arr.length;
		ImageIcon[] ret = new ImageIcon[size];
		int index = 0;
		for (ImageIcon[] arr : args)
			for (ImageIcon img : arr) {
				ret[index] = img;
				index++;
			}
		return ret;
	}

	// common images
	public static ImageIcon play=new ImageIcon(buttons+"play.png");
	public static ImageIcon pause=new ImageIcon(buttons+"pause.png");
	public static ImageIcon nextCycle=new ImageIcon(buttons+"nextcycle.png");
	public static ImageIcon mapBrush = new ImageIcon(menuPrefix + "editor/brush.png");
	public static ImageIcon waiting = new ImageIcon(menuPrefix + "cursors/wait.png");

	public static class Units {
		public static String risePrefix = riseOfWesnothPrefix + "units/";
		public static String heirPrefix = heirToTheThronePrefix + "units/";
		public static String prefix = corePrefix + "units/";
		public static ImageIcon wesfolkOutcast = new ImageIcon(risePrefix + "wesfolk-lady.png");
		public static ImageIcon wesfolkOutcastMirror = new ImageIcon(risePrefix + "wesfolk-lady-mirror.png");
		public static ImageIcon adept = new ImageIcon(prefix + "undead-necromancers/adept.png");
		public static ImageIcon adeptMirror = new ImageIcon(prefix + "undead-necromancers/adept-mirror.png");
		public static ImageIcon princess = new ImageIcon(heirPrefix + "human-princess.png");
		public static ImageIcon princessMirror = new ImageIcon(heirPrefix + "human-princess-mirror.png");

		public static ImageIcon[][] units = {
			{
				adept, adeptMirror
			},
			{
				wesfolkOutcast, wesfolkOutcastMirror
			},
			{
				princess, princessMirror
			}
		};
	}

	public static class Terrain {
		public static String prefix = corePrefix + "terrain/";

		public static ImageIcon[] getAllDirs(String path, String type) {
			ImageIcon[] ret = new ImageIcon[6];
			int index = 0;
			for (String dir : new String[] { "bl", "br", "l", "r", "tl", "tr" }) {
				ret[index] = new ImageIcon(path + "/" + type + "-" + dir + ".png");
				index++;
			}
			return ret;
		}

		public static ImageIcon[] grass = getAll(prefix + "grass", "green", 8, true);
		public static ImageIcon[] ice = getAll(prefix + "frozen", "ice", 6, true);
		public static ImageIcon[] beach = getAll(prefix + "sand", "beach", 8, true);
		public static ImageIcon[] water = { new ImageIcon(prefix + "water/ocean-tile.png") };
		public static ImageIcon fog = new ImageIcon(prefix + "darken.png");
		
		public static ImageIcon[] monolith = getAll (corePrefix + "scenery", "monolith", 4, false);
		public static ImageIcon[] rock = getAll (corePrefix + "scenery", "rock", 4, false);
		public static ImageIcon[] rockCairn = getAll (corePrefix + "scenery", "rock-cairn", 1, true);
		public static ImageIcon[] kkh = concat(monolith, rock, rockCairn);

		public static ImageIcon[] castle = getAllDirs (prefix + "castle", "castle-convex");

		public static ArrayList<ImageIcon[]> mapBlocks = new ArrayList<ImageIcon[]>(
				Arrays.asList(new ImageIcon[][] {
						grass,
						water,
						kkh,
				}));
		public static ImageIcon[] generalMap = grass;
	}

	public static class Objects {
		// Flags!
		/* Hue Saturation Lightness:
		 * yellow: 52 61 30
		 * blue: 224 64 1
		 * red: 10 100 11
		 * black: 180 0 -70
		 */
		public static ImageIcon[] greenFlag = getAll(menuPrefix + "flags", "long-flag-", 4, false);
		public static ImageIcon[] yellowFlag = getAll(menuPrefix + "flags", "long-flag-yellow-", 4, false);
		public static ImageIcon[] redFlag = getAll(menuPrefix + "flags", "long-flag-red-", 4, false);
		public static ImageIcon[] blueFlag = getAll(menuPrefix + "flags", "long-flag-blue-", 4, false);
		public static ImageIcon[] blackFlag = getAll(menuPrefix + "flags", "long-flag-black-", 4, false);
		//public static ImageIcon[] longFlag = getAll(menuPrefix + "flags", "long-flag-", 1, false);
		public static ImageIcon[][] flags = { blackFlag, redFlag, yellowFlag, blueFlag };

		//public static ImageIcon scepture = new ImageIcon(corePrefix + "items/sceptre-of-fire.png");
		public static ImageIcon[] brazier = getAll(corePrefix + "items", "brazier-lit", 8, false);
		public static ImageIcon[] fire = getAll(corePrefix + "scenery", "fire", 8, false);
		public static ImageIcon[] mage = getAll(corePrefix + "halo", "mage-halo", 5, false);
		public static ImageIcon flagRock = new ImageIcon(corePrefix + "scenery/rock-cairn.png");
		public static ImageIcon underFire = new ImageIcon(corePrefix + "scenery/rune2-glow.png");
		
		public static ImageIcon illusionRune = new ImageIcon(corePrefix + "scenery/rune4.png");
		public static ImageIcon illusionRuneGlow = new ImageIcon(corePrefix + "scenery/rune4-glow.png");
		public static ImageIcon bombRune = new ImageIcon(corePrefix + "scenery/rune6.png");
		public static ImageIcon bombRuneGlow = new ImageIcon(corePrefix + "scenery/rune6-glow.png");
		public static ImageIcon[][] runes = {
			{bombRune, bombRune, bombRune, bombRune, bombRune, bombRuneGlow, bombRuneGlow},
			{illusionRune, illusionRune, illusionRune, illusionRune, illusionRune, illusionRuneGlow, illusionRuneGlow}
		};
	}
}
