package mapobjects;

import game.Coord;
import inputs.Type;

public class Projectile extends AnimatedObject {

	double speedX, speedY;
	boolean isMoving = true;

	public Projectile(double setX, double setY, double setSpeedX, double setSpeedY, double setScale, Type setType,
			int setNumberOfFrames, int setNumberOfLoops, Coord setRotationTarget) {
		super(setX, setY, setScale, setType, setNumberOfFrames, setNumberOfLoops, false, 1, setRotationTarget, true,
				true);
		speedX = setSpeedX;
		speedY = setSpeedY;
	}

	public void updateMovement() {
		if (!isMoving)
			return;

		if ((getX() < rotationTarget.getX() && getX() + speedX > rotationTarget.getX()) // Kolla om den passerar målet
				|| (getX() > rotationTarget.getX() && getX() + speedX < rotationTarget.getX())) {
			if ((getY() < rotationTarget.getY() && getY() + speedY > rotationTarget.getY())
					|| (getY() > rotationTarget.getY() && getY() + speedY < rotationTarget.getY())) {
				setX(rotationTarget.getX());
				setY(rotationTarget.getY());
				isMoving = false;
			}
		}
		if (isMoving) {
			setX(getX() + speedX);
			setY(getY() + speedY);
		} else
			rotationTarget = null;
	}

	@Override
	public int getXpaint() {
		return (int) (coord.getX() - (width - 10));
	}

	public boolean isMoving() {
		return isMoving;
	}

	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	@Override
	public void move(double movex, double movey) {
		super.move(movex, movey);
		updateMovement();
	}
}
