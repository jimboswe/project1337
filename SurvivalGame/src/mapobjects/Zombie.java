package mapobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

import game.Coord;
import game.Game;
import inputs.ImageHandler;
import inputs.Type;

public class Zombie extends AnimatedObject{
	int health = 100;
	int attackDamage = 20;
	double speed = 1;
	double actualSpeedX = 0;
	double actualSpeedY = 0;
	boolean moving = false;
	public Timer attackTimer;

	public Zombie(double setX, double setY, double setScale, Type setType) {
		super(setX, setY, setScale, setType, 1, 0, false, 1, null, false, true);
		attackTimer = new Timer(1000, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == attackTimer)
			attack();
		else
			super.actionPerformed(e);
	}

	private void attack() {
		Game.player.changeHealth(-attackDamage);
	}
	
	public boolean changeHealth(int change) {
		if(health + change <= 0) {
			health = 0;
			kill();
			return true;
		}
		else {
			health += change;
			return false;
		}
	}

	public boolean update(Coord b) {
		int vicinity = 400;
		int offset = 40;
		int attackDistance = 100;
		double hypo = Math.hypot(b.getX() - getX(), b.getY() - getY());
		
		lastRotation = Game.getAngle(coord, b);
		
		if(hypo < vicinity) {
			Coord dir = Game.getRotation(b, coord);
			
			move(dir.getX()*speed, dir.getY()*speed); //Dra n�rmare
			if (hypo < offset) {
				move(-dir.getX(), -dir.getY()); //St�t ifr�n
			}
			if(hypo < attackDistance)
				attackTimer.start();
			else
				attackTimer.stop();
			
			moving = true;
		}
		else
			moving = false;
		
		return moving;
	}

	public boolean attractTo(Coord b, int vicinity, int offset) { //offset borde egentligen vara w/2*2
		double hypo = Math.hypot(b.getX() - getX(), b.getY() - getY());
		
		lastRotation = Game.getAngle(coord, b);
		
		if(hypo < vicinity) {
			Coord dir = Game.getRotation(b, coord);
			
			move(dir.getX()*speed, dir.getY()*speed); //Dra n�rmare
			if (hypo < offset) {
				move(-dir.getX(), -dir.getY()); //St�t ifr�n
			}
			moving = true;
		}
		else
			moving = false;
		
		return moving;
	}
	
	public void kill() {
		attackTimer.stop();
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

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
}
