package mapobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

import game.Coord;
import inputs.ImageHandler;
import inputs.Type;

public class AnimatedObject extends StaticObject implements ActionListener{
	protected int numberOfFrames = 0;
	protected int currentFrame = 0;
	protected int numberOfLoops = 0;
	protected int currentLoop = 0;
	protected boolean loopOnce;
	protected int standardFrame = 0;
	protected double lastRotation = 0;
	protected Coord rotationTarget;
	protected boolean fixedTarget;
	protected Timer timer;
	
	public int offsetPaint = 0;

	public AnimatedObject(double setX, double setY, double setScale, Type setType, int setNumberOfFrames, int setNumberOfLoops, boolean setLoopOnce, int setStandardFrame,
			Coord setRotationTarget, boolean setFixedTarget, boolean directLoopStart) {
		super(setX, setY, setScale, setType);
		numberOfFrames = setNumberOfFrames;
		numberOfLoops = setNumberOfLoops;
		loopOnce = setLoopOnce;
		standardFrame = setStandardFrame - 1;
		
		rotationTarget = setRotationTarget;
		fixedTarget = setFixedTarget;
		
		timer = new Timer(ImageHandler.getImageCoord(model).getUpdateRate(), this);
		if(directLoopStart)
			timer.start();
	}

	public double getLastRotation() {
		return lastRotation;
	}

	public boolean updateAnimation() {
		currentFrame++;
		if (currentFrame >= numberOfFrames) {
			currentLoop++;
			if(loopOnce) {
				currentFrame = standardFrame;
				loopOnce = false;
				return true;
			}
			currentFrame = 0;
		}
		return false;
	}
	
	public void runLoopOnce() {
		currentFrame = 0;
		numberOfLoops = 1;
		loopOnce = true;
		timer.start();
	}

	public void paint(Graphics2D gfx) {
		AffineTransform transform = gfx.getTransform(); // Spara standardrotation innan rotering

		if (rotationTarget == null) {
			if (lastRotation != 0)
				gfx.rotate(lastRotation + Math.toRadians(-90), getX(), getY());
		} else {
			lastRotation = Math.atan2(getY() - rotationTarget.getY(), getX() - rotationTarget.getX()) - Math.PI / 2;
			gfx.rotate(lastRotation + Math.toRadians(-90), getX(), getY()); //Lägg under istället
		}

		int x = ImageHandler.getImageCoord(model).getX();
		int y = ImageHandler.getImageCoord(model).getY();
		int w = ImageHandler.getImageCoord(model).getWidth();
		int h = ImageHandler.getImageCoord(model).getHeight();
		
		int space;
		if(w > 64)
			space = 0;
		else
			space = 1;

		gfx.drawImage(ImageHandler.getImage(Type.TABLE),
				getXpaint(),
				getYpaint() + offsetPaint,
				getXpaint() + (int) width,
				getYpaint() + (int) height + offsetPaint,
				((space * x + (space * currentFrame)) + space) + (64 * x) + (64 * currentFrame),
				((space * y) + space) + (64 * y),
				((space * x + (space * currentFrame)) + space) + (64 * x) + (64 * currentFrame) + w,
				space * y + (64 * y) + h,
				this);

		gfx.setTransform(transform);
		
		gfx.setColor(Color.CYAN);
		gfx.fillRect((int)getX()-3, (int)getY()-3, 6, 6);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(updateAnimation())
			timer.stop();
	}

	public Coord getRotationTarget() {
		return rotationTarget;
	}
	
	public void setRotationTarget(Coord rotationTarget) {
		this.rotationTarget = rotationTarget;
	}

	@Override
	public void move(double movex, double movey) {
		super.move(movex, movey);
		
		if(rotationTarget != null && fixedTarget) {
			rotationTarget.setX(rotationTarget.getX() - movex);
			rotationTarget.setY(rotationTarget.getY() - movey);
		}
	}
}
