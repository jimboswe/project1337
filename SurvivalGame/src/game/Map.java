package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import inputs.FileHandler;
import inputs.ImageHandler;
import inputs.Type;
import mapobjects.AnimatedObject;
import mapobjects.AttachableObject;
import mapobjects.Projectile;
import mapobjects.StaticObject;
import mapobjects.Zombie;
import soundmodule.Sound;

public class Map implements ImageObserver {
	private int world_size = 10000;
	private static ArrayList<StaticObject> mapObjects = new ArrayList<StaticObject>();
	private LinkedList<StaticObject> field = new LinkedList<StaticObject>();

	//private int[][] world = { { world_size }, { world_size } };

	Map() {
		//addTreesRandomly();
		//addStonesRandomly();
		//addZombiesRandomly();

		//mapObjects.addAll(FileHandler.ReadFromFile());
		//FileHandler.SaveToFile(mapObjects);

		for (int rows = 0; rows < 10; rows++) {
			for (int cols = 0; cols < 10; cols++) {
				field.add(new StaticObject(cols * ImageHandler.getImage(Type.GRASS).getWidth(),
						rows * ImageHandler.getImage(Type.GRASS).getHeight(), 1, Type.GRASS));
				//Gör om till att rita och spara en bild istället
			}
		}
		Zombie z = new Zombie(200, 200, 0.5, Type.ZOMBIE);
		mapObjects.add(z);
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
						ThreadLocalRandom.current().nextInt(100, world_size + 1), 0.5, Type.ZOMBIE));
			}
		}
	}

	public void addNewObject(StaticObject o) {
		synchronized (mapObjects) {
			mapObjects.add(o);
		}
	}

	public void paintAllObjects(Graphics2D gfx) {
		for (StaticObject f : field) {
			//f.paint(gfx);
		}
		LinkedList<StaticObject> paintAfter = new LinkedList<StaticObject>();

		int trees = 0, stones = 0, zombies = 0, anim = 0;
		synchronized (mapObjects) {
			for (StaticObject o : mapObjects) {
				switch (o.getModel()) {
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
					anim++;
					break;
				default:
					break;
				}
				if (o.getModel() != Type.BLOOD) {
					paintAfter.add(o);
					continue;
				}
				o.paint(gfx);

				if (o.getClass().equals(AnimatedObject.class))
					anim++;
			}
			for (StaticObject b : paintAfter) {
				b.paint(gfx);
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

	public ArrayList<StaticObject> checkCollision(Projectile p, Type checkThisType, ArrayList<StaticObject> add,
			ArrayList<StaticObject> del) {

		synchronized (mapObjects) {
			for (StaticObject it : mapObjects) {
				if (!(it.getModel() == checkThisType))
					continue;
				if (Math.abs(p.getX() - it.getX()) > 50 || Math.abs(p.getY() - it.getY()) > 50) //Ändra 50
					continue;

				double xcos = p.getSpeedX() / 50; //Ändra 50
				double ysin = p.getSpeedY() / 50; //Ändra 50

				double xx = p.getX();
				double yy = p.getY();
				double compareX = p.getX();
				double compareY = p.getY();

				while (compareX < p.getX() + Math.abs(p.getSpeedX()) && compareY < p.getY() + Math.abs(p.getSpeedY())) {
					xx += xcos;
					yy += ysin;
					compareX += Math.abs(xcos);
					compareY += Math.abs(ysin);

					if (xx > it.getX() - (it.getWidth() / 2) && xx < it.getX() + (it.getWidth() / 2)) {
						if (yy > it.getY() - (it.getHeight() / 2) && yy < it.getY() + (it.getHeight() / 2)) {
							if (Math.hypot(xx - it.getX(), yy - it.getY()) < (it.getWidth()/2)-1) {

								
								//EJ ALLA TYPER TAS BORT
								if (checkThisType == Type.ZOMBIE) {
									Game.sound.playNewClip(Sound.Arrow_Hit1, -20);
									Zombie z = (Zombie) it;
									if (z.changeHealth(-50)) {
										
										z.setRemove(true);
										del.add(z);
										Game.sound.playNewClip(Sound.getRandomZombieKillSound(), -20);

										add.add(new AnimatedObject(z.getX(), z.getY(), 1, Type.BLOOD, 3, 1, true, 3,
												new Coord(Game.player.getX(), Game.player.getY()), true, true)); //Lägg till blod

									}
									else {
										
									}
									add.add(new AttachableObject(z, p.getX(), p.getY(), p.getLastRotation(),
											p.getScale(), p.getModel(), 1, 0, false, 1, true));
									del.add(p);
								}
								if (checkThisType == Type.TREE) {
									p.setX(xx);
									p.setY(yy);
									p.setMoving(false);
									p.setRotationTarget(null);
								}
								break;
							}
						}
					}
				}
			}
		}
		return del;
	}

	public void adjustCollision(StaticObject o, ArrayList<Type> checkTheseTypes, boolean adjustSelf) {
		for (StaticObject t : mapObjects) {
			if (o == t) //Om samma som sig själv, hoppa över
				continue;
			if (checkTheseTypes.contains(t.getModel())) {
				double diffX = Math.abs(o.getX() - t.getX());
				double diffY = Math.abs(o.getY() - t.getY());

				if (diffX < o.getWidth() || diffY < o.getHeight()) { //Ej korrekt
					if (Math.hypot(diffX, diffY) < o.getWidth()) {
						Coord rot = Game.getRotation(t.getCoord(), o.getCoord());
						if (adjustSelf) {
							o.move(-rot.getX(), -rot.getY());
						}
						if (!t.getModel().isFixed()) {
							t.move(rot.getX(), rot.getY());
						}
					}
				}
			}
		}
	}

	public void centerPlayer(Coord player) {
		if (Math.abs(player.getX() - Game.WIDTH / 2) < 1 && Math.abs(player.getY() - Game.HEIGHT / 2) < 1) { //Om väldigt nära noll, skippa
			return;
		}

		double speed = Game.player.getActualSpeed() * 0.008;
		Coord c = Game.getRotation(player, new Coord(Game.WIDTH / 2, Game.HEIGHT / 2));
		c.setX(c.getX() * speed);
		c.setY(c.getY() * speed);

		for (StaticObject f : field)
			f.move(-c.getX(), -c.getY());

		Game.player.move(-c.getX(), -c.getY());
		synchronized (mapObjects) {
			for (StaticObject o : mapObjects) {
				o.move(-c.getX(), -c.getY());
			}
		}
	}

	public void updateWorld(int movex, int movey, Coord player) {

		ArrayList<StaticObject> del = new ArrayList<StaticObject>();
		ArrayList<StaticObject> add = new ArrayList<StaticObject>();
		ArrayList<Type> check = new ArrayList<Type>();
		check.add(Type.ZOMBIE);
		check.add(Type.TREE);
		check.add(Type.STONE);

		adjustCollision(Game.player, check, true);

		for (StaticObject f : field)
			f.move(movex, movey);

		synchronized (mapObjects) {
			for (StaticObject o : mapObjects) {

				o.move(movex, movey);

				if (o.getClass().equals(Projectile.class) && ((Projectile) o).isMoving()) {

					del.addAll(checkCollision((Projectile) o, Type.ZOMBIE, add, del));
					checkCollision((Projectile) o, Type.TREE, add, del);
				}
				if (o.getModel() == Type.ZOMBIE) {
					if (((Zombie) o).update(player))
						adjustCollision((Zombie) o, check, true);
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
}
