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
	public int offsetPaint;

	public Weapon(double setX, double setY, Coord setRotationTarget, WeaponType setType) {
		super(setType.getName(), setX, setY, setType.getScale(), setType.getModel(), setType.getNumberOfFrames(), 0, false, setType.getStandardFrame(),
				setRotationTarget, false, false);
		weapon = setType;
		offsetPaint = setType.getOffsetPaint();
	}

	public void update(double dirX, double dirY) {
		setX(Game.player.getX() + (dirX * 35));
		setY(Game.player.getY() + (dirY * 35));
		
		Game.ui.AddText("weapon", weapon.getName(), 10, 750, 20);
	}

	public void fire() {
		Game.sound.playNewClip(Sound.getFireSound(weapon), -20);
		
		firing = true;
		
		runLoopOnce();
		currentFrame++; //F�r att f� n�sta bild direkt
	}
	
	public WeaponType getWeapon() {
		return weapon;
	}

	public void reload() {}

	public void changeAmmo(int ammo) {
		this.ammo += ammo;
	}

	public int getAmmo() {
		return ammo;
	}

	@Override
	public int getYpaint() {
		return super.getYpaint() + offsetPaint;
	}
}
