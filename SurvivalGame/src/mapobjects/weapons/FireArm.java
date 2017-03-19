package mapobjects.weapons;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import game.Coord;
import game.Game;
import inputs.InputHandler;
import inputs.Type;
import mapobjects.AnimatedObject;
import soundmodule.Sound;

public class FireArm extends Weapon{
	
	protected int unloadedFrame = 1;
	int recoilTime = 80;
	Timer recoilTimer = null;
	Timer reloadTimer = null;

	public FireArm(double setX, double setY, Coord setRotationTarget, WeaponType setType) {
		super(setX, setY, setRotationTarget, setType);
		
		if(weapon.getRecoil() > 0)
			recoilTimer = new Timer(recoilTime, this);
		reloadTimer = new Timer(1500, this);
	}

	@Override
	public void update(double dirX, double dirY) {
		int includeRecoil = 0;
		if(firing)
			includeRecoil = weapon.getRecoil();
		
		super.update(dirX, dirY);
		move(-(dirX*includeRecoil), -(dirY*includeRecoil));
		
		if(reloadTimer.isRunning())
			Game.ui.AddText("ammo", "Reloading...", 10, 720, 20);
		else if(ammo > 0)
			Game.ui.AddText("ammo", "Ammo: " + Integer.toString(ammo), 10, 720, 20);
		else
			Game.ui.AddText("ammo", "Press R to reload", 10, 720, 20);
	}
	
	public void fire() {
		if (ammo <= 0 || !ready) {
			Game.sound.playNewClip(Sound.Click, -20);
			return;
		}
			
		super.fire();
		ammo--;
		
		Game.addProjectileToMap(Game.player.getCoord(), InputHandler.MOUSE, weapon.getProjectile());
		
		if(recoilTimer != null)
			recoilTimer.start();
		Coord c = Game.getRotation(Game.player.getCoord(), InputHandler.MOUSE);
		AnimatedObject o = new AnimatedObject("", Game.player.getX()+(c.getX()*80), Game.player.getY()+(c.getY()*80), 0.8, Type.MUZZLEFLASH, 1, 1, true, 2, InputHandler.MOUSE, false, true);
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
			firing = false;
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
