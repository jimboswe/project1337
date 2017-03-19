package mapobjects.weapons;

import game.Coord;
import game.Game;
import mapobjects.AnimatedObject;
import soundmodule.Sound;

public class Weapon extends AnimatedObject{
	WeaponType weapon;
	boolean ready = true;
	int ammo = 100;
	int damage = 40;
	double accuracy = 0.9;
	int noise = 200;
	boolean firing = false;

	public Weapon(double setX, double setY, Coord setRotationTarget, WeaponType setType) {
		super("", setX, setY, setType.getScale(), setType.getModel(), setType.getNumberOfFrames(), 0, false, setType.getStandardFrame(),
				setRotationTarget, false, false);
		weapon = setType;
	}

	public void update(double dirX, double dirY) {
		setX(Game.player.getX() + (dirX * 40));
		setY(Game.player.getY() + (dirY * 40));
		
		Game.ui.AddText("weapon", weapon.getName(), 10, 750, 20);
	}

	public void fire() {
		Game.sound.playNewClip(Sound.getFireSound(weapon), -20);
		
		firing = true;
		
		runLoopOnce();
		currentFrame++; //För att få nästa bild direkt
	}
	
	public WeaponType getWeapon() {
		return weapon;
	}

	public void reload() {}
}
