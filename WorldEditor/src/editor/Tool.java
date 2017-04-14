package editor;

import java.awt.Graphics2D;
import inputs.Type;
import mapobjects.StaticObject;

public class Tool {
	private StaticObject currentTool;

	Tool() {
		currentTool = new StaticObject("", 0, 0, 1.0, Type.TREE1);
	}
	
	public void update(double setX, double setY) {
		currentTool.setX(setX);
		currentTool.setY(setY);
	}

	public void paint(Graphics2D gfx) {
		currentTool.paint(gfx);
	}

	public void setCurrentTool(StaticObject setCurrentTool) {
		currentTool = setCurrentTool;
	}

	public StaticObject getCurrentTool() {
		return currentTool;
	}
}
