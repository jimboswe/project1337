package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import inputs.Type;
import mapobjects.Projectile;
import mapobjects.StaticObject;
import mapobjects.Stone;
import mapobjects.Tree;
import mapobjects.Zombie;
import mapobjects.weapons.AutoWeapon;
import mapobjects.weapons.FireArm;
import mapobjects.weapons.WeaponType;

public class Map implements ImageObserver {
	public static int world_size = 10000;
	public MapObjectsHandler mapContent;
	//private int[][] world = { { world_size }, { world_size } };

	Map() {

		//mapObjects.addAll(FileHandler.ReadFromFile());
		//FileHandler.SaveToFile(mapObjects);
		mapContent = new MapObjectsHandler();

		mapContent.addCollectable(new FireArm(500, 500, null, WeaponType.BOW));
		mapContent.addCollectable(new FireArm(900, 600, null, WeaponType.PISTOL));
		mapContent.addCollectable(new AutoWeapon(800, 600, null, WeaponType.UZI));
		mapContent.addCollectable(new AutoWeapon(1000, 600, null, WeaponType.UZI));
		mapContent.addCollectable(new AutoWeapon(1100, 600, null, WeaponType.UZI));
		mapContent.addCollectable(new AutoWeapon(1200, 600, null, WeaponType.UZI));

		for (int i = 0; i < 1000; i++)
			mapContent.addVisual(new Tree());
		for (int i = 0; i < 1000; i++)
			mapContent.addVisual(new Stone());

		for (int i = 0; i < 1000; i++) {
			Zombie z = new Zombie(ThreadLocalRandom.current().nextInt(100, world_size + 1),
					ThreadLocalRandom.current().nextInt(100, world_size + 1), 0.5, Type.ZOMBIE);
			mapContent.addCreature(z);
		}
	}

	public void paintAllObjects(Graphics2D gfx) {
		//mapContent.paintGround(gfx);
		mapContent.paintVisuals(gfx);
		mapContent.paintHarvestables(gfx);
		mapContent.paintCreatures(gfx);
		mapContent.paintProjectiles(gfx);
		mapContent.paintCollectables(gfx);
	}


	public void centerPlayer(Coord player) {
		if (Math.abs(player.getX() - Game.WIDTH / 2) < 1 && Math.abs(player.getY() - Game.HEIGHT / 2) < 1) { //Om väldigt nära noll, skippa
			return;
		}

		double speed = Game.player.getActualSpeed() * 0.008;
		Coord c = Helper.getRotation(player, new Coord(Game.WIDTH / 2, Game.HEIGHT / 2));
		c.setX(c.getX() * speed);
		c.setY(c.getY() * speed);

		mapContent.moveGround(-c.getX(), -c.getY());

		Game.player.move(-c.getX(), -c.getY());

		mapContent.update(-c.getX(), -c.getY());
	}

	public void updateWorld(double movex, double movey, Coord player) {
		
		ArrayList<StaticObject> add = new ArrayList<StaticObject>();
		ArrayList<StaticObject> del = new ArrayList<StaticObject>();
		
		ArrayList<Type> check = new ArrayList<Type>();
		check.add(Type.ZOMBIE);
		check.add(Type.TREE1);
		check.add(Type.STONE1);

		CollisionDetectionManager.adjustCollision(Game.player, check, true);

		mapContent.moveGround(movex, movey);

		mapContent.update(movex, movey);

		Helper.addInteractText(player);

		manageCollisions(check, add, del);

		mapContent.delete(del);
		mapContent.addObjects(add);
	}

	private void manageCollisions(ArrayList<Type> check, ArrayList<StaticObject> add, ArrayList<StaticObject> del) {
		for (StaticObject o : mapContent.getAllObjects()) {

			if (o.getClass().equals(Projectile.class) && ((Projectile) o).isMoving()) {

				del.addAll(CollisionDetectionManager.checkCollision((Projectile) o, Type.ZOMBIE, add, del));
				CollisionDetectionManager.checkCollision((Projectile) o, Type.TREE1, add, del);
			}
			if (o.getModel() == Type.ZOMBIE) {
				if (((Zombie) o).update(Game.player.getCoord()))
					CollisionDetectionManager.adjustCollision(o, check, true);
			}

		}
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}
