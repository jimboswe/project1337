package mapobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.Coord;
import game.Game;
import inputs.ImageHandler;
import inputs.Type;

public class AttachableObject extends AnimatedObject {

	Zombie attachedTo;
	double diffAngle;
	Coord offsetPos;
	double arrowInitialRotation;
	double zombieInitialRotation;

	public AttachableObject(Zombie setAttachedTo, double setX, double setY, double setLastRotation, double setScale,
			Type setType, int setNumberOfFrames, int setNumberOfLoops, boolean setLoopOnce, int setStandardFrame,
			boolean directLoopStart) {
		super("", setX, setY, setScale, setType, setNumberOfFrames, setNumberOfLoops, setLoopOnce, setStandardFrame, null,
				false, directLoopStart);

		attachedTo = setAttachedTo;
		lastRotation = attachedTo.getLastRotation();// + setAttachedTo.getLastRotation();
		offsetPos = new Coord(getX()-setAttachedTo.getX(), getY()-setAttachedTo.getY()); //Viktig ordning <<<<<troligen ej rätt
		coord = setAttachedTo.getCoord(); //Viktig ordning
		
		diffAngle = attachedTo.getLastRotation();
		
		arrowInitialRotation = setLastRotation;
		zombieInitialRotation = attachedTo.getLastRotation();
		
		
		
		
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
		
		int x = ImageHandler.getImageCoord(model).getX();
		int y = ImageHandler.getImageCoord(model).getY();
		int w = ImageHandler.getImageCoord(model).getWidth();
		int h = ImageHandler.getImageCoord(model).getHeight();
		
		int space;
		if(w > 64)
			space = 0;
		else
			space = 1;
		
		BufferedImage image = ImageHandler.getImage(Type.TABLE).getSubimage(
				((space * x + (space * currentFrame)) + space) + (64 * x) + (64 * currentFrame),
				((space * y) + space) + (64 * y),
				w,
				h);
		
		
		AffineTransform at = new AffineTransform(); // Spara standardrotation innan rotering
		
		at.translate(getX() + offsetPos.getX(), getY() + offsetPos.getY());
		
		at.rotate(arrowInitialRotation + Math.toRadians(-90));
		
		at.scale(scale, scale);
		
		at.translate(-(width-30), -(height/2));
		
		
		AffineTransform transform = gfx.getTransform(); // Spara standardrotation innan rotering

		if (rotationTarget == null)
			if (lastRotation != 0)
				gfx.rotate(lastRotation - zombieInitialRotation, getX(), getY());
		
		gfx.drawImage(image, at, null);
		
		gfx.setTransform(transform);

		
		gfx.setColor(Color.CYAN);
		gfx.fillRect((int)getX()-3, (int)getY()-3, 6, 6);
		gfx.drawString(getX() + " " + getY(), 700, 200);
	}

	@Override
	public void move(double movex, double movey) {
		//System.out.println(attachedTo.getX());
		if(!attachedTo.isActive()) {
			if(coord == attachedTo.getCoord()) {
				coord = new Coord(attachedTo.getX(), attachedTo.getY()); //uppdateras varje gång
			}
			super.move(movex, movey);
		}
	}
}
