/*package backup;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JPanel;

import game.Coord;

public class InputHandler extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 4119458751948816070L;
	
	private static HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>(); //Använd denna till knappar istället
	
	
	private int moveX = 0;
	private int moveY = 0;
	
	private boolean mouseButtonPressed = false;
	private boolean reloadButtonPressed = false;
	private boolean number1 = false;
	private boolean number2 = false;
	private boolean number3 = false;
	private boolean number4 = false;
	private boolean number5 = false;
	private boolean number6 = false;
	private boolean number7 = false;
	private boolean number8 = false;
	private boolean number9 = false;
	private boolean number0 = false;
	
	
	public static Coord MOUSE = new Coord(0, 0);
	public InputHandler() {
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		keys.put(key.getKeyCode(), true);
		update(Character.toLowerCase(key.getKeyChar()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.put(e.getKeyCode(), false);

		if (e.getKeyChar() == 'w') {
			if (keys.get(83) == null || !keys.get(83))
				moveY = 0;
			else
				moveY = 1;
		}
		if (e.getKeyChar() == 's') {
			if (keys.get(87) == null || !keys.get(87))
				moveY = 0;
			else
				moveY = -1;
		}
		if (e.getKeyChar() == 'a') {
			if (keys.get(68) == null || !keys.get(68))
				moveX = 0;
			else
				moveX = 1;
		}
		if (e.getKeyChar() == 'd') {
			if (keys.get(65) == null || !keys.get(65))
				moveX = 0;
			else
				moveX = -1;
		}
		if (e.getKeyChar() == Keys.ONE.getKey()) {
			number1 = true;
		}
		if (e.getKeyChar() == Keys.TWO.getKey()) {
			number2 = true;
		}
		if (e.getKeyChar() == Keys.THREE.getKey()) {
			number3 = true;
		}
		if (e.getKeyChar() == Keys.RELOAD.getKey()) {
			reloadButtonPressed = true;
		}
	}

	public void update(char k) {

		if (k == 'w') {
			moveY = -1;
		}
		if (k == 's') {
			moveY = 1;
		}
		if (k == 'a') {
			moveX = -1;
		}
		if (k == 'd') {
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
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseButtonPressed = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseButtonPressed = false;
	}

	public int getMoveX() {
		return moveX;
	}

	public int getMoveY() {
		return moveY;
	}

	public boolean isMouseButtonPressed() {
		return mouseButtonPressed;
	}

	public void setMouseButtonPressed(boolean mouseButtonPressed) {
		this.mouseButtonPressed = mouseButtonPressed;
	}

	public boolean isReloadButtonPressed() {
		return reloadButtonPressed;
	}

	public void setReloadButtonPressed(boolean reloadButtonPressed) {
		this.reloadButtonPressed = reloadButtonPressed;
	}

	public boolean isNumber1() {
		return number1;
	}

	public void setNumber1(boolean number1) {
		this.number1 = number1;
	}

	public boolean isNumber2() {
		return number2;
	}

	public void setNumber2(boolean number2) {
		this.number2 = number2;
	}

	public boolean isNumber3() {
		return number3;
	}

	public void setNumber3(boolean number3) {
		this.number3 = number3;
	}

}
*/