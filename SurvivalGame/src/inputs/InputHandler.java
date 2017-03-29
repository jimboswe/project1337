package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JPanel;

import game.Coord;
import game.Game;
import game.Helper;

public class InputHandler extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 4119458751948816070L;

	private static HashMap<Character, Boolean> KEYS = new HashMap<Character, Boolean>();
	private static HashMap<Character, Boolean> ALLOWEDTOREPRESS = new HashMap<Character, Boolean>();

	private int moveX = 0;
	private int moveY = 0;

	private boolean leftMouseButton = false;
	private boolean rightMouseButton = false;

	public static Coord MOUSE = new Coord(0, 0);

	public InputHandler() {
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public boolean getKeyState(Character key) {
		if (KEYS.get(key) == null)
			return false;

		if (KEYS.get(key))
			return true;
		else
			return false;
	}

	public void setKeyState(Character key, boolean state) {
		KEYS.put(key, state);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (!ALLOWEDTOREPRESS.containsKey(key.getKeyChar()) || ALLOWEDTOREPRESS.get(key.getKeyChar()))
			KEYS.put(key.getKeyChar(), true);

		ALLOWEDTOREPRESS.put(key.getKeyChar(), false);

		updateMovement(key.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KEYS.put(e.getKeyChar(), false);
		ALLOWEDTOREPRESS.put(e.getKeyChar(), true);

		if (e.getKeyChar() == Keys.UP.getKey()) {
			if (KEYS.get(Keys.DOWN.getKey()) == null || !KEYS.get(Keys.DOWN.getKey()))
				moveY = 0;
			else
				moveY = 1;
		} else if (e.getKeyChar() == Keys.DOWN.getKey()) {
			if (KEYS.get(Keys.UP.getKey()) == null || !KEYS.get(Keys.UP.getKey()))
				moveY = 0;
			else
				moveY = -1;
		} else if (e.getKeyChar() == Keys.LEFT.getKey()) {
			if (KEYS.get(Keys.RIGHT.getKey()) == null || !KEYS.get(Keys.RIGHT.getKey()))
				moveX = 0;
			else
				moveX = 1;
		} else if (e.getKeyChar() == Keys.RIGHT.getKey()) {
			if (KEYS.get(Keys.LEFT.getKey()) == null || !KEYS.get(Keys.LEFT.getKey()))
				moveX = 0;
			else
				moveX = -1;
		}
	}

	public void updateMovement(Character key) {
		if (key == Keys.UP.getKey()) {
			moveY = -1;
		} else if (key == Keys.DOWN.getKey()) {
			moveY = 1;
		} else if (key == Keys.LEFT.getKey()) {
			moveX = -1;
		} else if (key == Keys.RIGHT.getKey()) {
			moveX = 1;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		MOUSE.setX(e.getX());
		MOUSE.setY(e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MOUSE.setX(e.getX());
		MOUSE.setY(e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			leftMouseButton = true;
			break;
		case MouseEvent.BUTTON3:
			rightMouseButton = true;
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			leftMouseButton = false;
			break;
		case MouseEvent.BUTTON3:
			rightMouseButton = false;
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public boolean isLeftMouseButton() {
		return leftMouseButton;
	}

	public void setLeftMouseButton(boolean state) {
		this.leftMouseButton = state;
	}

	public double getMoveX() {
		Coord dir = Helper.getRotation(Game.player.getCoord(), new Coord(Game.player.getX()+moveX, Game.player.getY()+moveY));
		return dir.getX()*Game.player.getSpeed();
	}

	public double getMoveY() {
		Coord dir = Helper.getRotation(Game.player.getCoord(), new Coord(Game.player.getX()+moveX, Game.player.getY()+moveY));
		return dir.getY()*Game.player.getSpeed();
	}

	public boolean isRightMouseButton() {
		// TODO Auto-generated method stub
		return rightMouseButton;
	}
	
	public void setRightMouseButton(boolean rightMouseButton) {
		this.rightMouseButton = rightMouseButton;
	}

}
