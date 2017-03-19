package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;

public class UI {

	private static HashMap<String, Text> text = new HashMap<String, Text>();

	UI() {
	}

	public void AddText(String setKey, String setS, int setX, int setY, int setSize) {
		text.put(setKey, new Text(setS, setX, setY, setSize));
	}

	public void DeleteText(String string) {
		if(text.containsKey(string))
			text.remove(string);
	}

	public void paint(Graphics2D gfx) {
		gfx.setColor(Color.BLACK);
		for (Text it : text.values()) {
			gfx.setFont(new Font("Comic Sans MS", Font.ITALIC, it.getSize()));
			gfx.drawString(it.getString(), it.getX(), it.getY());
		}
	}

	class Text {
		String s;
		int x, y;
		int size;

		Text(String setS, int setX, int setY, int setSize) {
			s = setS;
			x = setX;
			y = setY;
			size = setSize;
		}

		public String getString() {
			return s;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getSize() {
			return size;
		}
	}

}
