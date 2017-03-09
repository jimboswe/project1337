/*package backup;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import inputs.Type;
import mapobjects.AnimatedObject;
import mapobjects.Projectile;
import mapobjects.StaticObject;
import mapobjects.Zombie;
import soundmodule.Sound;

public class Map implements ImageObserver {
	private int world_size = 10000;
	private static ArrayList<StaticObject> mapObjects = new ArrayList<StaticObject>();
	private StaticObject field = new StaticObject(0, 0, 0.5, Type.GRASS);
	//private int[][] world = { { world_size }, { world_size } };

	Map() {
		addTreesRandomly();
		addStonesRandomly();
		addZombiesRandomly();
	}

	public void addTreesRandomly() {
		synchronized (mapObjects) {
			for (int i = 0; i < 1000; i++) {
				mapObjects.add(new AnimatedObject(ThreadLocalRandom.current().nextInt(100, world_size + 1),
						ThreadLocalRandom.current().nextInt(100, world_size + 1), 0.5, Type.TREE, 4, 0, false, 1, null,
						true, true));
			}
		}
	}

	public void addStonesRandomly() {
		synchronized (mapObjects) {
			for (int i = 0; i < 1000; i++) {
				mapObjects.add(new StaticObject(ThreadLocalRandom.current().nextInt(100, world_size + 1),
						ThreadLocalRandom.current().nextInt(100, world_size + 1), 0.5, Type.STONE));
			}
		}
	}

	public void addZombiesRandomly() {
		synchronized (mapObjects) {
			for (int i = 0; i < 1000; i++) {
				mapObjects.add(new Zombie(ThreadLocalRandom.current().nextInt(100, world_size + 1),
						ThreadLocalRandom.current().nextInt(100, world_size + 1), 0.5, Type.ZOMBIE, 1, 0, null));
			}
		}
	}

	public void addNewObject(StaticObject o) {
		synchronized (mapObjects) {
			mapObjects.add(o);
		}
	}

	public void paintAllObjects(Graphics2D gfx) {
		field.paint(gfx);

		int trees = 0, stones = 0, zombies = 0, anim = 0;
		synchronized (mapObjects) {
			for (StaticObject o : mapObjects) {
				o.paint(gfx);

				switch (o.getType()) {
				case TREE:
					trees++;
					break;
				case STONE:
					stones++;
					break;
				case ZOMBIE:
					zombies++;
					break;
				case ARROW:
					break;
				default:
					break;
				}
				if (o.getClass().equals(AnimatedObject.class))
					anim++;
			}
		}
		gfx.drawString("Trees: " + String.valueOf(trees), 50, 20);
		gfx.drawString("Stones: " + String.valueOf(stones), 50, 40);
		gfx.drawString("Zombies: " + String.valueOf(zombies), 50, 60);
		gfx.drawString("Animated Objects: " + String.valueOf(anim), 50, 80);

	}

	public static void addProjectile(double setX, double setY, double speedX, double speedY, double targetX,
			double targetY, Type projectile) {
		synchronized (mapObjects) {
			mapObjects.add(
					new Projectile(setX, setY, speedX, speedY, 0.8, projectile, 1, 0, new Coord(targetX, targetY)));
		}
	}

	public ArrayList<StaticObject> checkCollision(Projectile o, Type checkThisType) {
		ArrayList<StaticObject> del = new ArrayList<StaticObject>();
		synchronized (mapObjects) {
			for (StaticObject it : mapObjects) {
				if ((o.getX() < it.getX() && o.getX() + o.getSpeedX() > it.getX()) // Kolla om den passerar målet
						|| (o.getX() > it.getX() && o.getX() + o.getSpeedX() < it.getX())) {
					if ((o.getY() < it.getY() && o.getY() + o.getSpeedY() > it.getY())
							|| (o.getY() > it.getY() && o.getY() + o.getSpeedY() < it.getY())) {
						del.add(it);
					}
				}
			}
			return del;
		}
	}

	public void updateWorld(int movex, int movey, Coord player) {
		field.moveX(movex);
		field.moveY(movey);

		ArrayList<StaticObject> del = new ArrayList<StaticObject>();
		ArrayList<StaticObject> add = new ArrayList<StaticObject>();

		synchronized (mapObjects) {
			for (StaticObject o : mapObjects) {
				o.moveX(movex);
				o.moveY(movey);
				if (o.getClass().equals(AnimatedObject.class))
					((AnimatedObject) o).updateTargetLocation(movex, movey);
				if (o.getClass().equals(Projectile.class) && ((Projectile) o).isMoving()) {
					((Projectile) o).updateTargetLocation(movex, movey);
					((Projectile) o).updateMovement();
					for (StaticObject z : mapObjects) {
						if (z.getModel() == Type.ZOMBIE) {
							if ((o.getX() + o.getWidth()) > z.getX() && o.getX() < (z.getX() + z.getWidth())
									&& (o.getY() + o.getHeight()) > z.getY() && o.getY() < (z.getY() + z.getHeight())) {
								del.add(z); //Ta bort zombie
								Game.sound.playNewClip(Sound.getRandomZombieKillSound(), -20);
								add.add(new AnimatedObject(z.getX(), z.getY(), 1, Type.BLOOD, 3, 1, true, 3,
										new Coord(player.getX(), player.getY()), true, true)); //Lägg till blod
								//add.add(new AnimatedObject(z.getX(), z.getY(), 1, Type.EXPLOSION, 16, 1, true, 17, new Coord(player.getX(), player.getY()), true, true)); //Lägg till explosion
							}
						}
					}
				}
				double close = 400;
				if (o.getModel() == Type.ZOMBIE) {
					if (Math.hypot(player.getX() - o.getX(), player.getY() - o.getY()) < close)
						((Zombie) o).attractTo(player, 40);
				}
			}
			mapObjects.removeAll(del);
			mapObjects.addAll(add);
		}

	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}*/
