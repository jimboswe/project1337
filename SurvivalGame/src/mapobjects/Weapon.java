package mapobjects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import game.Coord;
import game.Game;
import inputs.InputHandler;
import inputs.Type;
import soundmodule.Sound;

public class Weapon extends AnimatedObject implements ActionListener{
	WeaponType weapon;
	boolean ready = true;
	int ammo = 100;
	int damage = 40;
	double accuracy = 0.9;
	int noise = 200;
	boolean shooting = false;
	protected int unloadedFrame = 1;
	int recoilTime = 80;
	Timer recoilTimer = null;
	Timer reloadTimer = null;

	Weapon(double setX, double setY, double setScale, Type setModel, int setNumberOfFrames, int setNumberOfLoops,
			boolean setLoopOnce, int setStandardFrame, Coord setRotationTarget, boolean directLoopStart, WeaponType setType) {
		super(setX, setY, setScale, setModel, setNumberOfFrames, setNumberOfLoops, setLoopOnce, setStandardFrame,
				setRotationTarget, false, directLoopStart);
		weapon = setType;
		if(weapon.getRecoil() > 0)
			recoilTimer = new Timer(recoilTime, this);
		reloadTimer = new Timer(1500, this);
	}

	public void update(double dirX, double dirY) {
		int includeRecoil = 0;
		if(shooting)
			includeRecoil = weapon.getRecoil();
		
		setX(Game.player.getX() + (dirX * 40) - (dirX*includeRecoil));
		setY(Game.player.getY() + (dirY * 40) - (dirY*includeRecoil));
		
		if(reloadTimer.isRunning())
			Game.ui.AddText("ammo", "Reloading...", 10, 720, 20);
		else if(ammo > 0)
			Game.ui.AddText("ammo", "Ammo: " + Integer.toString(ammo), 10, 720, 20);
		else
			Game.ui.AddText("ammo", "Press R to reload", 10, 720, 20);
		
		Game.ui.AddText("weapon", weapon.getName(), 10, 750, 20);
	}

	public void shoot() {
		if (ammo <= 0 || !ready) {
			Game.sound.playNewClip(Sound.Click, -20);
			return;
		}
			
		Game.sound.playNewClip(Sound.getFireSound(weapon), -20);
		
		shooting = true;
		
		runLoopOnce();
		currentFrame++; //För att få nästa bild direkt
		ammo--;
		
		Game.addProjectileToMap(Game.player.getCoord(), InputHandler.MOUSE, WeaponType.getProjectile(weapon));
		
		if(recoilTimer != null)
			recoilTimer.start();
		Coord c = Game.getRotation(Game.player.getCoord(), InputHandler.MOUSE);
		AnimatedObject o = new AnimatedObject(Game.player.getX()+(c.getX()*80), Game.player.getY()+(c.getY()*80), 0.8, Type.MUZZLEFLASH, 1, 1, true, 2, InputHandler.MOUSE, false, true);
		Game.map.addNewObject(o);
		//OBS RENSAS EJ UPP
	}
	
	public void reload() {
		reloadTimer.start();
		ready = false;
		Game.sound.playNewClip(Sound.getReloadSound(weapon), -20);
	}

	@Override
	public boolean updateAnimation() {

		boolean ret = super.updateAnimation();
		if (ammo <= 0)
			currentFrame = unloadedFrame;
		return ret;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == recoilTimer) {
			shooting = false;
			recoilTimer.stop();
		}
		else if(e.getSource() == reloadTimer) {
			currentFrame = standardFrame;
			ammo = 10;
			ready = true;
			reloadTimer.stop();
		}
		else if(e.getSource() == timer)
			super.actionPerformed(e);
	}
}
