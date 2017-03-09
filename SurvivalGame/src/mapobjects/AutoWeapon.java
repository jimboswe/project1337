package mapobjects;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import game.Coord;
import inputs.Type;

public class AutoWeapon extends Weapon {
	Timer fullAutoTimer = null;

	AutoWeapon(double setX, double setY, double setScale, Type setModel, int setNumberOfFrames, int setNumberOfLoops,
			boolean setLoopOnce, int setStandardFrame, Coord setRotationTarget, boolean directLoopStart,
			WeaponType setType) {
		super(setX, setY, setScale, setModel, setNumberOfFrames, setNumberOfLoops, setLoopOnce, setStandardFrame,
				setRotationTarget, directLoopStart, setType);

		fullAutoTimer = new Timer(50, this);
	}

	@Override
	public void shoot() {
		if (ready) {
			super.shoot();
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
