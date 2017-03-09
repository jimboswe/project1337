package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.Timer;

import inputs.ImageHandler;
import inputs.InputHandler;
import inputs.Keys;
import inputs.Type;
import mapobjects.Player;
import mapobjects.StaticObject;
import mapobjects.WeaponType;
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

	public void updateGame() {
		player.update(input.getMoveX(), input.getMoveY());
		map.updateWorld(input.getMoveX(), input.getMoveY(), player.getCoord());

		adjustCamera();

		if (input.isLeftMouseButton()) {
			if(player.getCurrentWeapon() == WeaponType.PISTOL) {
				//map.addNewObject(new StaticObject(InputHandler.MOUSE.getX(), InputHandler.MOUSE.getY(), 0.5, Type.TREE));
				map.addNewObject(new Zombie(InputHandler.MOUSE.getX(), InputHandler.MOUSE.getY(), 0.5, Type.ZOMBIE));
				input.setLeftMouseButton(false);
			}
			else {
				player.shoot();
				if (!player.getAuto())
					input.setLeftMouseButton(false);
			}
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
	}

	public static void addProjectileToMap(Coord start, Coord target, Type projectile) { //Flytta till map
		double speed = 5;

		Coord r = getRotation(start, target);
		double angle = getAngle(start, target);

		double speedX = speed * r.getX();
		double speedY = speed * r.getY();
		Map.addProjectile(start.getX() + (Math.cos(angle) * 50), start.getY() + (Math.sin(angle) * 50), speedX, speedY,
				target.getX(), target.getY(), projectile);
	}

	public static double getAngle(Coord start, Coord target) {
		double angle = Math.atan2(start.getY() - target.getY(), start.getX() - target.getX()) - Math.PI / 2;
		angle += Math.toRadians(-90);
		return angle;
	}

	public static Coord getRotation(Coord start, Coord target) {
		double angle = Math.atan2(start.getY() - target.getY(), start.getX() - target.getX()) - Math.PI / 2;
		angle += Math.toRadians(-90);

		double ax = Math.cos(angle);
		double ay = Math.sin(angle);
		return new Coord(ax, ay);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adjustCamera = true;
	}
}
