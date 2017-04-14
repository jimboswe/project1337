package mapobjects.weapons;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import editor.Coord;

public class FireArm extends Weapon{
	
	int ammo = 100;
	double accuracy = 0.9;
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
