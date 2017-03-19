package mapobjects.weapons;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import game.Coord;

public class AutoWeapon extends FireArm {
	Timer fullAutoTimer = null;

	public AutoWeapon(double setX, double setY, Coord setRotationTarget, WeaponType setType) {
		super(setX, setY, setRotationTarget, setType);

		fullAutoTimer = new Timer(50, this);
	}

	@Override
	public void fire() {
		if (ready) {
			super.fire();
			fullAutoTimer.start();
			ready = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fullAutoTimer) {
			ready = true;
			fullAutoTimer.stop();
		} else
			super.actionPerformed(e);
	}

}
