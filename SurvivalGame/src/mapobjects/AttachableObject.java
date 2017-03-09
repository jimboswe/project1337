package mapobjects;

import java.awt.Graphics2D;

import game.Coord;
import inputs.Type;

public class AttachableObject extends AnimatedObject {

	Zombie attachedTo;
	Coord diffPos;
	double diffAngle;

	public AttachableObject(Zombie setAttachedTo, double setX, double setY, double setLastRotation, double setScale,
			Type setType, int setNumberOfFrames, int setNumberOfLoops, boolean setLoopOnce, int setStandardFrame,
			boolean directLoopStart) {
		super(setX, setY, setScale, setType, setNumberOfFrames, setNumberOfLoops, setLoopOnce, setStandardFrame, null,
				false, directLoopStart);

		attachedTo = setAttachedTo;
		lastRotation = setLastRotation;
		diffPos = new Coord(attachedTo.getX(), attachedTo.getY());
		diffAngle = attachedTo.getLastRotation();
	}

	@Override
	public void paint(Graphics2D gfx) {
		if (attachedTo != null) {
			lastRotation += attachedTo.getLastRotation()-diffAngle;
			diffAngle = attachedTo.getLastRotation();

			move(diffPos.getX()-attachedTo.getX(), diffPos.getY()-attachedTo.getY());
			diffPos.setX(attachedTo.getX());
			diffPos.setY(attachedTo.getY());
			System.out.println(toString());
		}
		super.paint(gfx);
	}

	@Override
	public void move(double movex, double movey) {
		diffPos.setX(diffPos.getX() - movex);
		diffPos.setY(diffPos.getY() - movey);
		super.move(movex, movey);
	}
}
