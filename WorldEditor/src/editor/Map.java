package editor;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import inputs.ImageHandler;
import inputs.Type;
import mapobjects.*;

public class Map {
	public static int world_size = 10000;
	private LinkedList<StaticObject> ground = new LinkedList<StaticObject>();
	
	private ArrayList<StaticObject> allObjects = new ArrayList<StaticObject>();
	private ArrayList<StaticObject> creatures = new ArrayList<StaticObject>();
	private ArrayList<StaticObject> collectables = new ArrayList<StaticObject>();
	private ArrayList<StaticObject> harvestables = new ArrayList<StaticObject>();
	private ArrayList<StaticObject> visuals = new ArrayList<StaticObject>();
	private ArrayList<StaticObject> projectiles = new ArrayList<StaticObject>();

	public Map() {
		createGround();
	}
	
	public void update(double movex, double movey) {
		
		ArrayList<StaticObject> add = new ArrayList<StaticObject>();
		ArrayList<StaticObject> del = new ArrayList<StaticObject>();
		
		ArrayList<Type> check = new ArrayList<Type>();
		check.add(Type.ZOMBIE);
		check.add(Type.TREE1);
		check.add(Type.STONE1);

		moveGround(movex, movey);
		updateObjectsPosition(movex, movey);

		delete(del);
		addObjects(add);
	}
	
	public void createGround() {
		synchronized(ground) {
			for (int rows = 0; rows < 10; rows++) {
				for (int cols = 0; cols < 10; cols++) {
					ground.add(new StaticObject("", cols * ImageHandler.getImage(Type.GRASS).getWidth(),
							rows * ImageHandler.getImage(Type.GRASS).getHeight(), 1, Type.GRASS));
					//Gör om till att rita och spara en bild istället
				}
			}
		}
	}

	public void moveGround(double mx, double my) {
		synchronized(ground) {
			for (StaticObject f : ground)
				f.update(mx, my);
		}
	}
	
	public ArrayList<StaticObject> getAllObjects() {
			return allObjects; 
	}
	
	public void addObject(StaticObject o) {
		switch(o.getModel().getObjectType()) {
		case COLLECTABLE:
			addCollectable(o);
			break;
		case CREATURE:
			addCreature(o);
			break;
		case HARVESTABLE:
			addHarvestable(o);
			break;
		case PROJECTILE:
			addProjectile(o);
			break;
		case VISUAL:
			addVisual(o);
			break;
		default:
			break;
		}
	}
	
	public void addObjects(ArrayList<StaticObject> add) {
		synchronized (allObjects) {
			for(StaticObject o : add) {
				addObject(o);
			}
		}
	}

	public void addCreature(StaticObject o) {
		synchronized (allObjects) {
			allObjects.add(o);
		}
		synchronized (creatures) {
			creatures.add(o);
		}
	}
	
	public void addCollectable(StaticObject o) {
		synchronized (allObjects) {
			allObjects.add(o);
		}
		synchronized (collectables) {
			collectables.add(o);
		}
	}
	
	public void addHarvestable(StaticObject o) {
		synchronized (allObjects) {
			allObjects.add(o);
		}
		synchronized (harvestables) {
			harvestables.add(o);
		}
	}
	
	public void addVisual(StaticObject o) {
		synchronized (allObjects) {
			allObjects.add(o);
		}
		synchronized (visuals) {
			visuals.add(o);
		}
	}
	
	public void addProjectile(StaticObject o) {
		synchronized (allObjects) {
			allObjects.add(o);
		}
		synchronized (projectiles) {
			projectiles.add(o);
		}
	}
	
	public ArrayList<StaticObject> getCollectableObjects(Coord player) {
		synchronized (collectables) {
			ArrayList<StaticObject> ret = new ArrayList<StaticObject>();
			int a, b, c;
			int distance = 100;
			for (StaticObject l : collectables) {
				a = (int) Math.pow(player.getX() - l.getX(), 2);
				b = (int) Math.pow(player.getY() - l.getY(), 2);
				c = (int) Math.sqrt(a + b);
				if (c < distance) {
					ret.add(l);
				}
			}
			return ret;
		}
	}
	
	public StaticObject getNearestCollectableObject(Coord player) {
		StaticObject ret = null;
		int a, b, c;
		int last = 0;
	
		for(StaticObject ob : getCollectableObjects(player)) {
			a = (int) Math.pow(player.getX() - ob.getX(), 2);
			b = (int) Math.pow(player.getY() - ob.getY(), 2);
			c = (int) Math.sqrt(a + b);
			if(last == 0 || c < last) {
				ret = ob;
				last = c;
			}
		}
		
		return ret;	
	}
	
	public ArrayList<StaticObject> getHarvestableObjects(Coord player) {
		synchronized (harvestables) {
			ArrayList<StaticObject> ret = new ArrayList<StaticObject>();
			int a, b, c;
			int distance = 100;
			for (StaticObject l : harvestables) {
				a = (int) Math.pow(player.getX() - l.getX(), 2);
				b = (int) Math.pow(player.getY() - l.getY(), 2);
				c = (int) Math.sqrt(a + b);
				if (c < distance) {
					ret.add(l);
				}
			}
			return ret;
		}
	}
	
	public StaticObject getNearestHarvestableObject(Coord player) {
		StaticObject ret = null;
		int a, b, c;
		int last = 0;
	
		for(StaticObject ob : getHarvestableObjects(player)) {
			a = (int) Math.pow(player.getX() - ob.getX(), 2);
			b = (int) Math.pow(player.getY() - ob.getY(), 2);
			c = (int) Math.sqrt(a + b);
			if(last == 0 || c < last) {
				ret = ob;
				last = c;
			}
		}
		
		return ret;	
	}

	public void delete(ArrayList<StaticObject> del) {
		synchronized (allObjects) {
			allObjects.removeAll(del);
		}
		synchronized (creatures) {
			creatures.removeAll(del);
		}
		synchronized (collectables) {
			collectables.removeAll(del);
		}
		synchronized (harvestables) {
			harvestables.removeAll(del);
		}
		synchronized (visuals) {
			visuals.removeAll(del);
		}
		synchronized (projectiles) {
			projectiles.removeAll(del);
		}
	}
	
	public void delete(StaticObject del) {
		synchronized (allObjects) {
			allObjects.remove(del);
		}
		synchronized (creatures) {
			creatures.remove(del);
		}
		synchronized (collectables) {
			collectables.remove(del);
		}
		synchronized (harvestables) {
			harvestables.remove(del);
		}
		synchronized (visuals) {
			visuals.remove(del);
		}
		synchronized (projectiles) {
			projectiles.remove(del);
		}
		
	}

	public void updateObjectsPosition(double movex, double movey) {
		synchronized (allObjects) {
			for (StaticObject l : allObjects) {
				l.update(movex, movey);
			}
		}
	}
	
	public void paintGround(Graphics2D gfx) {
		synchronized (ground) {
			for (StaticObject o : ground) {
				o.paint(gfx);
			}
		}
	}

	public void paintAllObjects(Graphics2D gfx) {
		
		synchronized (allObjects) {
			for (StaticObject o : allObjects) {
				if(isInsideDisplay(o))
					o.paint(gfx);
			}
		}
	}
	
	public void paintCreatures(Graphics2D gfx) {
		synchronized (creatures) {
			for (StaticObject o : creatures) {
				if(isInsideDisplay(o))
					o.paint(gfx);
			}
		}
	}
	
	public void paintCollectables(Graphics2D gfx) {
		synchronized (collectables) {
			for (StaticObject o : collectables) {
				if(isInsideDisplay(o))
					o.paint(gfx);
			}
		}
	}
	
	public void paintHarvestables(Graphics2D gfx) {
		synchronized (harvestables) {
			for (StaticObject o : harvestables) {
				if(isInsideDisplay(o))
					o.paint(gfx);
			}
		}
	}
	
	public void paintVisuals(Graphics2D gfx) {
		synchronized (visuals) {
			for (StaticObject o : visuals) {
				if(isInsideDisplay(o))
					o.paint(gfx);
			}
		}
	}
	
	public void paintProjectiles(Graphics2D gfx) {
		synchronized (projectiles) {
			for (StaticObject o : projectiles) {
				if(isInsideDisplay(o))
					o.paint(gfx);
			}
		}
	}
	
	public boolean isInsideDisplay(StaticObject o) {
		if(o.getX() > 0 && o.getX() < Editor.WIDTH) {
			if(o.getY() > 0 && o.getY() < Editor.HEIGHT) {
				return true;
			}
		}
		return false;
	}
	
	class count {
		private int trees = 0, stones = 0, zombies = 0, anim = 0;

		count() {
		}

		public void add(Type type) {
			switch (type) {
			case TREE1:
				trees++;
				break;
			case STONE1:
				stones++;
				break;
			case ZOMBIE:
				zombies++;
				break;
			case ARROW:
				anim++;
				break;
			default:
				break;
			}

			if (type.getClass().equals(AnimatedObject.class))
				anim++;
		}

		public void drawInfo(Graphics2D gfx) {
			gfx.drawString("Trees: " + String.valueOf(trees), 50, 20);
			gfx.drawString("Stones: " + String.valueOf(stones), 50, 40);
			gfx.drawString("Zombies: " + String.valueOf(zombies), 50, 60);
			gfx.drawString("Animated Objects: " + String.valueOf(anim), 50, 80);
		}

	}
}
