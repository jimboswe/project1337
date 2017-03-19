package mapobjects;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.Timer;

import game.Coord;
import game.Game;
import inputs.Type;
import mapobjects.weapons.AutoWeapon;
import mapobjects.weapons.MeleeWeapon;
import mapobjects.weapons.Weapon;
import mapobjects.weapons.WeaponType;

public class Player extends AnimatedObject implements ActionListener {
	int health = 2000;
	
	
	private LinkedHashMap<WeaponType, Weapon> inventory = new LinkedHashMap<WeaponType, Weapon>();
	private WeaponType currentWeapon;
	public Timer testTimer;
	Coord lastPos;
	private double actSpeed;
	
	public Player(double setX, double setY, double setScale, Type setType, int setNumberOfFrames, int setNumberOfLoops,
			Coord setRotationTarget) {
		super("", Game.WIDTH / 2, Game.HEIGHT / 2, setScale, setType, setNumberOfFrames, setNumberOfLoops, false, 1, setRotationTarget,
				false, false);
		currentWeapon = WeaponType.HANDS;
		inventory.put(WeaponType.HANDS, new MeleeWeapon(getX(), getY(), setRotationTarget, WeaponType.HANDS));
		
		//Weapon pistol = new FireArm(getX(), getY(), setRotationTarget, WeaponType.PISTOL);
		//pistol.offsetPaint = 8; //För att pistolen ska stämma i förhållande till vart projektilen utgår från
		//inventory.put(WeaponType.PISTOL, pistol);

		//inventory.put(WeaponType.BOW, new FireArm(getX(), getY(), setRotationTarget, WeaponType.BOW));
		//inventory.put(WeaponType.UZI, new AutoWeapon(getX(), getY(), setRotationTarget, WeaponType.UZI));
		
		
		inventory.get(currentWeapon).currentFrame = inventory.get(currentWeapon).standardFrame; //Eh?
		testTimer = new Timer(700, this);
		testTimer.start();
		lastPos = new Coord(getX(), getY());
		actSpeed = 0;

	}
	
	public int getHealth() {
		return health;
	}

	public void changeHealth(int change) {
		if(health + change < 0)
			health = 0;
		else
			health += change;
	}
	
	public WeaponType getCurrentWeapon() {
		return currentWeapon;
	}

	public void update(double dirX, double dirY) {
		//double angle = Math.atan2(getY() - inventory.get(currentWeapon).getRotationTarget().getY(),
		//		getX() - inventory.get(currentWeapon).getRotationTarget().getX()) - Math.PI / 2;
		double angle = Math.atan2(getY() - rotationTarget.getY(),
				getX() - rotationTarget.getX()) - Math.PI / 2;
		angle += Math.toRadians(-90);

		double ax = Math.cos(angle);
		double ay = Math.sin(angle);
		inventory.get(currentWeapon).update(ax, ay);
		
		Game.ui.AddText("health", Integer.toString(health), Game.WIDTH-50, 750, 20);
	}

	public void shoot() {
		inventory.get(currentWeapon).fire();
	}

	public void reload() {
		inventory.get(currentWeapon).reload();
	}

	public void switchWeapon(int index) {
		int i = 1;
		for(WeaponType w : inventory.keySet()) {
			if(i == index) {
				currentWeapon = w;
				break;
			}
			else
				i++;
		}
	}

	public boolean getAuto() {
		if (inventory.get(currentWeapon).getClass().equals(AutoWeapon.class))
			return true;
		else
			return false;
	}

	@Override
	public void paint(Graphics2D gfx) {
		super.paint(gfx);
		inventory.get(currentWeapon).paint(gfx);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == testTimer) {
			actSpeed = Math.hypot(getX() - lastPos.getX(), getY() - lastPos.getY()); //Math.abs?
			lastPos.setX(getX());
			lastPos.setY(getY());
		} else
			super.actionPerformed(e);
	}

	public double getActualSpeed() {
		return actSpeed;
	}

	public void updateLastPos(double x, double y) {
		lastPos.setX(lastPos.getX() + x);
		lastPos.setY(lastPos.getY() + y);
	}

	@Override
	public void move(double movex, double movey) {
		super.move(movex, movey);
		updateLastPos(movex, movey);
	}
	
	public void offerInventory(StaticObject item) {
		inventory.put(((Weapon) item).getWeapon(), (Weapon) item);
		inventory.get(((Weapon) item).getWeapon()).setRotationTarget(rotationTarget);
		currentWeapon = ((Weapon) item).getWeapon();
	}
}
