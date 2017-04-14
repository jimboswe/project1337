package mapobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

import inputs.ImageHandler;
import inputs.Type;

public class Zombie extends AnimatedObject{
	int health = 100;
	int attackDamage = 20;
	double speed = 0.5;
	double actualSpeedX = 0;
	double actualSpeedY = 0;
	boolean moving = false;
	public Timer attackTimer;

	public Zombie(double setX, double setY, double setScale, Type setType) {
		super("", setX, setY, setScale, setType, 1, 0, false, 1, null, false, true);
		attackTimer = new Timer(1000, this);
	}

	@Override
	public void paint(Graphics2D gfx) {
		AffineTransform transform = gfx.getTransform(); // Spara standardrotation innan rotering
		
		gfx.rotate(lastRotation, getX(), getY());

		int space = 1;
		int x = ImageHandler.getImageCoord(model).getX();
		int y = ImageHandler.getImageCoord(model).getY();
		int w = ImageHandler.getImageCoord(model).getWidth();
		int h = ImageHandler.getImageCoord(model).getHeight();
		
		gfx.drawImage(ImageHandler.getImage(Type.TABLE),
				getXpaint(),
				getYpaint(),
				getXpaint() + (int)width,
				getYpaint() + (int)height,
				((space * x) + space) + (64 * x),
				((space * y) + space) + (64 * y),
				(space * x + space) + (64 * x) + w,
				(space * y + space) + (64 * y) + h,
				this);

		gfx.setTransform(transform);
		
		gfx.setColor(Color.CYAN);
		gfx.fillRect((int)getX()-3, (int)getY()-3, 6, 6);
	}
}
