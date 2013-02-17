package javachallenge.graphics;

import java.util.Map;
import java.util.TreeMap;

import javachallenge.graphics.components.MapPanel;
import javachallenge.graphics.components.Sprite;
import javachallenge.graphics.util.AnimatedImage;
import javachallenge.graphics.util.ImageHolder;
import javachallenge.graphics.util.Mover;
import javachallenge.graphics.util.Position;

public class Client {
	MapPanel panel;
	Map<Integer, Sprite> flags = new TreeMap<Integer, Sprite>();
	Map<Integer, Sprite> units = new TreeMap<Integer, Sprite>();
	public static int x[] = { 0, 1, 1, 0, -1, -1 };
	public static int y[] = { -1, -1, 0, 1, 0, -1 };

	public MapPanel getPanel() {
		return panel;
	}

	public void setPanel(MapPanel panel) {
		this.panel = panel;
	}

	@SuppressWarnings("serial")
	public Client(int width, int height, Position[] positions) {
		panel = new MapPanel(width, height) {
			@Override
			public void onClick(int x, int y) {
				spawn(units.size() + 1, new Position(x, y));
			}

			public void onEnter(int x, int y) {
				Position pos = getAbsolutePosition(x, y);
				brush.setLocation(pos.getX(), pos.getY());
			}
		};
		for (int i = 0; i < positions.length; i++) {
			Sprite flag = new AnimatedImage(ImageHolder.Objects.fire, 125, positions[i]);
			flags.put(i, flag);
			panel.addToContainer(flag, 2);
		}
	}

	public void spawn(Integer id, Position position) {
		Sprite sprite = new Sprite(ImageHolder.Units.wesfolkOutcast, position);
		units.put(id, sprite);
		panel.addToContainer(sprite, 2);

	}

	public void die(Integer id) {
		Sprite sprite = units.get(id);
		units.remove(id);
		sprite.setVisible(false);
		panel.remove(sprite);
	}

	public void attack(Integer attacker, Integer defender) {

	}

	public void move(Integer id, int direction) {
		Sprite sprite = units.get(id);
		Position position = MapPanel.getAbsolutePosition(x[direction], y[direction]);
		if (direction == 1 || direction == 2)
			units.get(id).setIcon(ImageHolder.Units.wesfolkOutcast);
		if (direction == 4 || direction == 5)
			units.get(id).setIcon(ImageHolder.Units.wesfolkOutcastMirror);
		new Mover(sprite, position, 3, 100).start();
	}

	public void obtainFlag(int id) {
		Sprite flag = flags.get(id);
		flag.setVisible(false);
		panel.remove(flag);
		flags.remove(id);
		flags.put(id, new Sprite(ImageHolder.Objects.underFire, flag.getPosition()));
		panel.addToContainer(flags.get(id), 2);
	}
}