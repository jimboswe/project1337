package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import inputs.ImageHandler;
import inputs.InputHandler;
import inputs.Keys;
import inputs.Type;
import mapobjects.Player;
import mapobjects.StaticObject;
import mapobjects.Zombie;
import soundmodule.Audio;

public class Game extends JPanel implements ActionListener {
	private static final long serialVersionUID = -5544168147348667199L;

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;

	public static InputHandler input;

	public static Map map;

	public static Player player;
	public static UI ui;
	public static Audio sound;
	private Timer adjustCameraDelayTimer;
	boolean adjustCamera = false;

	Game() {
		setFocusable(true);

		ImageHandler.init();
		input = new InputHandler();
		addKeyListener(input);
		addMouseMotionListener(input);
		addMouseListener(input);

		map = new Map();
		player = new Player(0, 0, 0.5, Type.ZOMBIE, 1, 0, InputHandler.MOUSE);
		ui = new UI();
		sound = new Audio();
		adjustCameraDelayTimer = new Timer(500, this);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	private void paintTest(Graphics2D gfx) {
		//double angle = Math.atan2(player.getY() - InputHandler.MOUSE.getY(), player.getX() - InputHandler.MOUSE.getX()) - Math.PI / 2;

		//gfx.drawLine((int) player.getX() + 25, (int) player.getY() + 25, 500 + 10, 500 + 10);

		gfx.drawString(Double.toString(InputHandler.MOUSE.getX()), 50, 150);
		gfx.drawString(Double.toString(InputHandler.MOUSE.getY()), 50, 170);

		//gfx.rotate(angle + Math.toRadians(-90), player.getX(), player.getY());

		//gfx.drawLine((int) player.getX(), (int) player.getY(), (int) player.getX() + 60, (int) player.getY());

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gfx = (Graphics2D) g;
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		map.paintAllObjects(gfx);
		player.paint(gfx);

		paintTest(gfx);
		ui.paint(gfx);
	}

	public void updateGame() {
		player.update(input.getMoveX(), input.getMoveY());
		map.updateWorld(input.getMoveX(), input.getMoveY(), player.getCoord());

		adjustCamera();

		CheckInputs();
	}



	private void adjustCamera() {
		if (player.getX() > (WIDTH / 2) + 1 || player.getX() < (WIDTH / 2) - 1
				|| (player.getY() > (HEIGHT / 2) + 1 || player.getY() < HEIGHT / 2 - 1)) { //parantes
			adjustCameraDelayTimer.start();
		}

		if (adjustCamera)
			map.centerPlayer(player.getCoord());

		if (player.getX() > (WIDTH / 2) - 1 && player.getX() < (WIDTH / 2) + 1) {
			if (player.getY() > (HEIGHT / 2) - 1 && player.getY() < (HEIGHT / 2) + 1) {
				adjustCamera = false;
				adjustCameraDelayTimer.stop();
			}
		}
	}

	private void CheckInputs() {

		if (input.isLeftMouseButton()) {
			player.shoot();
				if (!player.getAuto())
					input.setLeftMouseButton(false);
		}
		
		if (input.isRightMouseButton()) {
			map.mapContent.addCreature(new Zombie(InputHandler.MOUSE.getX(), InputHandler.MOUSE.getY(), 0.5, Type.ZOMBIE));
			input.setRightMouseButton(false);
		}
		

		if (input.getKeyState(Keys.INTERACT.getKey())) {
			Interact();
			input.setKeyState(Keys.INTERACT.getKey(), false);
		}
		if (input.getKeyState(Keys.RELOAD.getKey())) {
			player.reload();
			input.setKeyState(Keys.RELOAD.getKey(), false);
		}
		if (input.getKeyState(Keys.ONE.getKey())) {
			player.switchWeapon(1);
			input.setKeyState(Keys.ONE.getKey(), false);
		}
		if (input.getKeyState(Keys.TWO.getKey())) {
			player.switchWeapon(2);
			input.setKeyState(Keys.TWO.getKey(), false);
		}
		if (input.getKeyState(Keys.THREE.getKey())) {
			player.switchWeapon(3);
			input.setKeyState(Keys.THREE.getKey(), false);
		}
		if (input.getKeyState(Keys.FOUR.getKey())) {
			player.switchWeapon(4);
			input.setKeyState(Keys.FOUR.getKey(), false);
		}
		if (input.getKeyState(Keys.FIVE.getKey())) {
			player.switchWeapon(5);
			input.setKeyState(Keys.FIVE.getKey(), false);
		}
		if (input.getKeyState(Keys.SIX.getKey())) {
			player.switchWeapon(6);
			input.setKeyState(Keys.SIX.getKey(), false);
		}
		if (input.getKeyState(Keys.SEVEN.getKey())) {
			player.switchWeapon(7);
			input.setKeyState(Keys.SEVEN.getKey(), false);
		}
		if (input.getKeyState(Keys.EIGHT.getKey())) {
			player.switchWeapon(8);
			input.setKeyState(Keys.EIGHT.getKey(), false);
		}
		if (input.getKeyState(Keys.NINE.getKey())) {
			player.switchWeapon(9);
			input.setKeyState(Keys.NINE.getKey(), false);
		}
	}

	private void Interact() {
		StaticObject ob = map.mapContent.getNearestCollectableObject(player.getCoord());
		if(ob != null) {
			player.offerInventory(ob);
			map.mapContent.delete(ob);
		}
		else {
			ob = map.mapContent.getNearestHarvestableObject(player.getCoord());
			if(ob != null) {
				// TODO Lägg in vad som ska hända
			}
		}
	}

	public static void addProjectileToMap(Coord start, Coord target, Type projectile) { //Flytta till map
		double speed = 5;

		Coord dir = getRotation(start, target);

		map.addProjectile(start.getX() + (dir.getX() * 20), start.getY() + (dir.getY() * 20), speed * dir.getX(), speed * dir.getY(),
				target.getX(), target.getY(), projectile);
	}

	public static double getAngle(Coord start, Coord target) {
		double angle = Math.atan2(start.getY() - target.getY(), start.getX() - target.getX()) - Math.PI / 2;
		angle += Math.toRadians(-90);
		return angle;
	}

	public static Coord getRotation(Coord start, Coord target) {
		if(start.getX() == target.getX() && start.getY() == target.getY())
			return new Coord(0,0);
		
		double angle = getAngle(start, target);

		double ax = Math.cos(angle);
		double ay = Math.sin(angle);
		return new Coord(ax, ay);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adjustCamera = true;
	}
}
