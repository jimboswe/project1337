package game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import mapobjects.*;
import mapobjects.weapons.AutoWeapon;
import mapobjects.weapons.FireArm;
import mapobjects.weapons.WeaponType;

public class MapObjectsHandler {
	private ArrayList<StaticObject> allObjects = new ArrayList<StaticObject>();
	private ArrayList<StaticObject> onlyCreatures = new ArrayList<StaticObject>();
	private ArrayList<StaticObject> onlyInteractables = new ArrayList<StaticObject>();
	private ArrayList<StaticObject> onlyVisuals = new ArrayList<StaticObject>();

	public MapObjectsHandler() {
		addObject(new FireArm(500, 500, null, WeaponType.BOW));
		addObject(new AutoWeapon(800, 600, null, WeaponType.UZI));

		for (int i = 0; i < 1000; i++)
			addObject(new Tree());
		for (int i = 0; i < 1000; i++)
			addObject(new Stone());
	}
	
	public ArrayList<StaticObject> getAllObjects() {
		synchronized (allObjects) { //Behövs sync här?
			return allObjects; 
		}
	}
	
	public void addObjects(ArrayList<StaticObject> add) {
		synchronized (allObjects) {
			allObjects.addAll(add);
		}
	}

	public void addObject(StaticObject o) { //Ska nog bort
		synchronized (allObjects) {
			allObjects.add(o);
		}
	}
	

	
	public void addObject(StaticObject o, boolean creature, boolean interactable, boolean visual) {
		synchronized (allObjects) {
			allObjects.add(o);
		}
		if(creature) {
			synchronized (onlyCreatures) {
				onlyCreatures.add(o);
			}
		}
		if(interactable) {
			synchronized (onlyInteractables) {
				onlyInteractables.add(o);
			}
		}
		if(visual) {
			synchronized (onlyVisuals) {
				onlyVisuals.add(o);
			}
		}
	}

	public LinkedList<StaticObject> getInteractableObjects(Coord player) {
		synchronized (allObjects) {
			LinkedList<StaticObject> ret = new LinkedList<StaticObject>();
			int a, b, c;
			int distance = 100;
			for (StaticObject l : allObjects) {
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

	public void deleteObjects(ArrayList<StaticObject> del) {
		synchronized (allObjects) {
			allObjects.removeAll(del);
		}
	}

	public void deleteObjects(LinkedList<StaticObject> del) {
		synchronized (allObjects) {
			allObjects.removeAll(del);
		}
	}

	public void update(int movex, int movey) {
		synchronized (allObjects) {
			for (StaticObject l : allObjects) {
				l.move(movex, movey);
			}
		}
	}

	public void paint(Graphics2D gfx) {
		synchronized (allObjects) {
			for (StaticObject o : allObjects) {
				o.paint(gfx);
			}
		}
	}

}
