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
import mapobjects.Stone;
import mapobjects.Tree;
import mapobjects.Zombie;
import mapobjects.weapons.AutoWeapon;
import mapobjects.weapons.FireArm;
import mapobjects.weapons.WeaponType;
import soundmodule.Sound;

public class Map implements ImageObserver {
	public static int world_size = 10000;
	//private static ArrayList<StaticObject> mapObjects = new ArrayList<StaticObject>();
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


		for(int i=0; i < 1000; i++) {
			//Zombie z = new Zombie(200, 200, 0.5, Type.ZOMBIE);
			Zombie z = new Zombie(ThreadLocalRandom.current().nextInt(100, world_size + 1), ThreadLocalRandom.current().nextInt(100, world_size + 1), 0.5, Type.ZOMBIE);
			mapContent.addCreature(z);
		}
	}

	public void paintAllObjects(Graphics2D gfx) {
		mapContent.paintGround(gfx);
		mapContent.paintVisuals(gfx);
		mapContent.paintHarvestables(gfx);
		mapContent.paintCreatures(gfx);
		mapContent.paintProjectiles(gfx);
		mapContent.paintCollectables(gfx);
	}

	public void addProjectile(double setX, double setY, double speedX, double speedY, double targetX, double targetY,
			Type projectile) {
		mapContent.addProjectile(new Projectile(setX, setY, speedX, speedY, 0.8, projectile, 1, 0, new Coord(targetX, targetY)));
	}

	public ArrayList<StaticObject> checkCollision(Projectile p, Type checkThisType, ArrayList<StaticObject> add,
			ArrayList<StaticObject> del) {

		for (StaticObject it : mapContent.getAllObjects()) {
			if (!(it.getModel() == checkThisType))
				continue;
			if (Math.abs(p.getX() - it.getX()) > 50 || Math.abs(p.getY() - it.getY()) > 50) //�ndra 50
				continue;

			double xcos = p.getSpeedX() / 50; //�ndra 50
			double ysin = p.getSpeedY() / 50; //�ndra 50

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
						if (Math.hypot(xx - it.getX(), yy - it.getY()) < (it.getWidth() / 2) - 1) {

							//EJ ALLA TYPER TAS BORT
							if (checkThisType == Type.ZOMBIE) {
								Game.sound.playNewClip(Sound.Arrow_Hit1, -20);
								Zombie z = (Zombie) it;
								if (z.changeHealth(-50)) {

									z.setRemove(true);
									del.add(z);
									Game.sound.playNewClip(Sound.getRandomZombieKillSound(), -20);

									add.add(new AnimatedObject("", z.getX(), z.getY(), 1, Type.BLOOD, 3, 1, true, 3,
											new Coord(Game.player.getX(), Game.player.getY()), true, true)); //L�gg till blod

								} else {

								}
								add.add(new AttachableObject(z, p.getX(), p.getY(), p.getLastRotation(), p.getScale(),
										p.getModel(), 1, 0, false, 1, true));
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
		return del;
	}

	public void adjustCollision(StaticObject o, ArrayList<Type> checkTheseTypes, boolean adjustSelf) {
		for (StaticObject t : mapContent.getAllObjects()) {
			if (o == t) //Om samma som sig sj�lv, hoppa �ver
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
		if (Math.abs(player.getX() - Game.WIDTH / 2) < 1 && Math.abs(player.getY() - Game.HEIGHT / 2) < 1) { //Om v�ldigt n�ra noll, skippa
			return;
		}

		double speed = Game.player.getActualSpeed() * 0.008;
		Coord c = Game.getRotation(player, new Coord(Game.WIDTH / 2, Game.HEIGHT / 2));
		c.setX(c.getX() * speed);
		c.setY(c.getY() * speed);
		
		mapContent.moveGround(-c.getX(), -c.getY() );

		Game.player.move(-c.getX(), -c.getY());
		
		mapContent.update(-c.getX(), -c.getY());
	}

	public void updateWorld(double movex, double movey, Coord player) {

		ArrayList<StaticObject> del = new ArrayList<StaticObject>();
		ArrayList<StaticObject> add = new ArrayList<StaticObject>();
		ArrayList<Type> check = new ArrayList<Type>();
		check.add(Type.ZOMBIE);
		check.add(Type.TREE);
		check.add(Type.STONE);

		adjustCollision(Game.player, check, true);
		
		mapContent.moveGround(movex, movey);

		mapContent.update(movex, movey);
		
		addInteractText(player);

		for (StaticObject o : mapContent.getAllObjects()) {

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
		mapContent.delete(del);
		mapContent.addObjects(add);

	}
	
	public void addInteractText(Coord player) {
		StaticObject ob = mapContent.getNearestCollectableObject(player);
		if(ob != null) {
			Game.ui.AddText("interact", "Pick up " + ob.getObjectName(), Game.WIDTH / 2,
					Game.HEIGHT / 2 + 50, 20);
		}
		else
			Game.ui.DeleteText("interact"); //Eller automatiskt ta bort alla efter varje paint?
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}
