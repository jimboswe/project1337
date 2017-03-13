package mapobjects;

import game.Coord;
import game.Game;
import inputs.Type;
import soundmodule.Sound;

public class Weapon extends AnimatedObject{
	WeaponType weapon;
	boolean ready = true;
	int ammo = 100;
	int damage = 40;
	double accuracy = 0.9;
	int noise = 200;
	boolean firing = false;

	Weapon(double setX, double setY, double setScale, Type setModel, int setNumberOfFrames, int setNumberOfLoops,
			boolean setLoopOnce, int setStandardFrame, Coord setRotationTarget, boolean directLoopStart, WeaponType setType) {
		super(setX, setY, setScale, setModel, setNumberOfFrames, setNumberOfLoops, setLoopOnce, setStandardFrame,
				setRotationTarget, false, directLoopStart);
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
	
	public void reload() {}
}
