package inputs;

public class ImageCoord {
	private int x, y, width, height, updateRate;
	ImageCoord(int setX, int setY, int setWidth, int setHeight, int setUpdateRate) {
		x = setX;
		y = setY;
		width = setWidth;
		height = setHeight;
		updateRate = setUpdateRate;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getUpdateRate() {
		return updateRate;
	}
}
