package mapobjects.weapons;

import editor.Coord;
import mapobjects.AnimatedObject;

public class Weapon extends AnimatedObject{
	WeaponType weapon;
	boolean ready = true;
	int damage = 40;
	int noise = 200;
	boolean firing = false;
	public int offsetPaint;

	public Weapon(double setX, double setY, Coord setRotationTarget, WeaponType setType) {
		super(setType.getName(), setX, setY, setType.getScale(), setType.getModel(), setType.getNumberOfFrames(), 0, false, setType.getStandardFrame(),
				setRotationTarget, false, false);
		weapon = setType;
		offsetPaint = setType.getOffsetPaint();
	}
	
	public WeaponType getWeapon() {
		return weapon;
	}
}
