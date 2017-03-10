package mapobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.Coord;
import game.Game;
import inputs.ImageHandler;
import inputs.Type;

public class AttachableObject extends AnimatedObject {

	Zombie attachedTo;
	double diffAngle;
	Coord offsetPos;
	double testAngle;

	public AttachableObject(Zombie setAttachedTo, double setX, double setY, double setLastRotation, double setScale,
			Type setType, int setNumberOfFrames, int setNumberOfLoops, boolean setLoopOnce, int setStandardFrame,
			boolean directLoopStart) {
		super(setX, setY, setScale, setType, setNumberOfFrames, setNumberOfLoops, setLoopOnce, setStandardFrame, null,
				false, directLoopStart);

		attachedTo = setAttachedTo;
		lastRotation = setLastRotation;// + setAttachedTo.getLastRotation();
		offsetPos = new Coord(getX()-setAttachedTo.getX(), getY()-setAttachedTo.getY()); //Viktig ordning <<<<<troligen ej rätt
		testAngle = Game.getAngle(coord, offsetPos);
		coord = setAttachedTo.getCoord(); //Viktig ordning
		
		diffAngle = attachedTo.getLastRotation();
		
		
		//Pilen hamnar som inte zombien vore roterad
	}

	@Override
	public int getXpaint() {
		return (int) (coord.getX() - (width - 10) - offsetPos.getX());
	}
	
	@Override
	public int getYpaint() {
		return (int) (coord.getY() - (height / 2) - offsetPos.getY());
	}

	@Override
	public void paint(Graphics2D gfx) {
		if (attachedTo != null) {
			lastRotation += attachedTo.getLastRotation() - diffAngle;
			diffAngle = attachedTo.getLastRotation();

			//System.out.println(toString());
		}
		
		
		
		
		AffineTransform transform = gfx.getTransform(); // Spara standardrotation innan rotering

		if (rotationTarget == null) {
			if (lastRotation != 0)
				gfx.rotate(lastRotation + Math.toRadians(-90) + testAngle, getX(), getY());
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
		gfx.drawString(getX() + " " + getY(), 700, 200);
	}

	@Override
	public void move(double movex, double movey) {
		//Ta ej bort
		//Ej färdigt
	}
}
