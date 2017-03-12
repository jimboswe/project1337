package game;

public class Coord implements Cloneable{
	private double x, y;

	public Coord(double setX, double setY) {
		x = setX;
		y = setY;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "Coord [x=" + x + ", y=" + y + "]";
	}
}
