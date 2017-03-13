package mapobjects;

import game.Coord;
import inputs.Type;

public class MeleeWeapon extends Weapon{

	MeleeWeapon(double setX, double setY, double setScale, Type setModel, int setNumberOfFrames, int setNumberOfLoops,
			boolean setLoopOnce, int setStandardFrame, Coord setRotationTarget, boolean directLoopStart,
			WeaponType setType) {
		super(setX, setY, setScale, setModel, setNumberOfFrames, setNumberOfLoops, setLoopOnce, setStandardFrame,
				setRotationTarget, directLoopStart, setType);
	}

}
