package mapobjects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import game.Coord;
import inputs.ImageHandler;
import inputs.Type;

public class StaticObject implements ImageObserver {
	protected Coord coord;
	protected double scale;
	protected Type model;
	protected double width, height;
	protected boolean active = true;
	protected boolean remove = false;

	public StaticObject(double setX, double setY, double setScale, Type setType) {
		coord = new Coord(setX, setY);
		scale = setScale;
		model = setType;
		width = ImageHandler.getImageCoord(setType).getWidth() * scale;
		height = ImageHandler.getImageCoord(setType).getHeight() * scale;
	}

	public double getX() {
		return coord.getX();
	}

	public double getY() {
		return coord.getY();
	}
	public void setX(double x) {
		coord.setX(x);
	}

	public void setY(double y) {
		coord.setY(y);
	}

	public int getXpaint() {
		return (int) (coord.getX() - (width / 2));
	}

	public int getYpaint() {
		return (int) (coord.getY() - (height / 2));
	}

	public void move(double movex, double movey) {
		coord.setX(coord.getX() - movex);
		coord.setY(coord.getY() - movey);
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public Coord getCoord() {
		return coord;
	}
	
	public double getScale() {
		return scale;
	}
	
	public Type getModel() {
		return model;
	}

	public void paint(Graphics2D gfx) {
		int x = ImageHandler.getImageCoord(model).getX();
		int y = ImageHandler.getImageCoord(model).getY();
		int w = ImageHandler.getImageCoord(model).getWidth();
		int h = ImageHandler.getImageCoord(model).getHeight();
		
		int space;
		if(w > 64)
			space = 0;
		else
			space = 1;
		
		gfx.drawImage(ImageHandler.getImage(model),
				getXpaint(),
				getYpaint(),
				getXpaint() + (int)width,
				getYpaint() + (int)height,
				((space * x) + space) + (64 * x),
				((space * y) + space) + (64 * y),
				(space * x + space) + (64 * x) + w,
				(space * y + space) + (64 * y) + h,
				this);
	}
	
	@Override
	public String toString() {
		return "StaticObject [coord=" + coord + ", scale=" + scale + ", model=" + model + ", width=" + width
				+ ", height=" + height + "]";
	}



	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		if(remove)
			setActive(false);
		this.remove = remove;
	}
	
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {return false;}
}