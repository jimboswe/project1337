package editor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import inputs.ImageHandler;
import inputs.InputHandler;
import inputs.Keys;
import inputs.Type;
import mapobjects.StaticObject;
import mapobjects.Zombie;

public class Editor extends JPanel {
	private static final long serialVersionUID = -5544168147348667199L;

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;

	public static InputHandler input;

	public static Map map;
	public static UI ui;
	Tool tool;

	Editor() {
		setFocusable(true);

		ImageHandler.init();
		input = new InputHandler();
		addKeyListener(input);
		addMouseMotionListener(input);
		addMouseListener(input);
		addMouseWheelListener(input);

		map = new Map();
		ui = new UI();
		tool = new Tool();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	private void paintTest(Graphics2D gfx) {
		gfx.drawString(Double.toString(InputHandler.MOUSE.getX()), 50, 150);
		gfx.drawString(Double.toString(InputHandler.MOUSE.getY()), 50, 170);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gfx = (Graphics2D) g;
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		map.paintAllObjects(gfx);
		tool.paint(gfx);

		paintTest(gfx);
		ui.paint(gfx);
	}

	public void update() {
		map.update(input.getMoveX(), input.getMoveY());
		tool.update(InputHandler.MOUSE.getX(), InputHandler.MOUSE.getY());
		
		CheckInputs();

		ui.AddText("scale", "Scale: " + tool.getCurrentTool().getScale(), 10, HEIGHT - 20, 20);
	}

	private void CheckInputs() {

		if (input.isLeftMouseButton()) {
			map.addObject(new StaticObject("", tool.getCurrentTool().getX(), tool.getCurrentTool().getY(),
					tool.getCurrentTool().getScale(), tool.getCurrentTool().getModel()));
			input.setLeftMouseButton(false);
		}

		if (input.isRightMouseButton()) {
			map.addCreature(new Zombie(InputHandler.MOUSE.getX(), InputHandler.MOUSE.getY(), 0.5, Type.ZOMBIE));
			input.setRightMouseButton(false);
		}

		if (input.getMouseWheel() != 0) {
			tool.getCurrentTool().setScale(tool.getCurrentTool().getScale() + (input.getMouseWheel() * 0.05));
			input.setMouseWheel(0);
		}

		if (input.getKeyState(Keys.INTERACT.getKey())) {

			input.setKeyState(Keys.INTERACT.getKey(), false);
		}
		if (input.getKeyState(Keys.ONE.getKey())) {
			tool.getCurrentTool().setModel(Type.getRandomTreeModel());
			input.setKeyState(Keys.ONE.getKey(), false);
		}
		if (input.getKeyState(Keys.TWO.getKey())) {
			tool.getCurrentTool().setModel(Type.getRandomStoneModel());
			input.setKeyState(Keys.TWO.getKey(), false);
		}
		if (input.getKeyState(Keys.THREE.getKey())) {
			tool.getCurrentTool().setModel(Type.ZOMBIE);
			input.setKeyState(Keys.THREE.getKey(), false);
		}
		if (input.getKeyState(Keys.FOUR.getKey())) {

			input.setKeyState(Keys.FOUR.getKey(), false);
		}
		if (input.getKeyState(Keys.FIVE.getKey())) {

			input.setKeyState(Keys.FIVE.getKey(), false);
		}
		if (input.getKeyState(Keys.SIX.getKey())) {

			input.setKeyState(Keys.SIX.getKey(), false);
		}
		if (input.getKeyState(Keys.SEVEN.getKey())) {

			input.setKeyState(Keys.SEVEN.getKey(), false);
		}
		if (input.getKeyState(Keys.EIGHT.getKey())) {

			input.setKeyState(Keys.EIGHT.getKey(), false);
		}
		if (input.getKeyState(Keys.NINE.getKey())) {

			input.setKeyState(Keys.NINE.getKey(), false);
		}
	}
}
