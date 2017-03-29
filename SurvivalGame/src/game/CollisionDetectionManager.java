package game;

import java.util.ArrayList;

import inputs.Type;
import mapobjects.AnimatedObject;
import mapobjects.AttachableObject;
import mapobjects.Projectile;
import mapobjects.StaticObject;
import mapobjects.Zombie;
import soundmodule.Sound;

public class CollisionDetectionManager {
	
	public static ArrayList<StaticObject> checkCollision(Projectile p, Type checkThisType, ArrayList<StaticObject> add,
			ArrayList<StaticObject> del) {

		for (StaticObject it : Game.map.mapContent.getAllObjects()) {
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
											new Coord(Game.player.getX(), Game.player.getY()), true, true)); //Lägg till blod

								} else {

								}
								add.add(new AttachableObject(z, p.getX(), p.getY(), p.getLastRotation(), p.getScale(),
										p.getModel(), 1, 0, false, 1, true));
								del.add(p);
							}
							if (checkThisType == Type.TREE1) {
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

	public static void adjustCollision(StaticObject o, ArrayList<Type> checkTheseTypes, boolean adjustSelf) {
		for (StaticObject t : Game.map.mapContent.getAllObjects()) {
			if (o == t) //Om samma som sig själv, hoppa över
				continue;
			if (checkTheseTypes.contains(t.getModel())) {
				double diffX = Math.abs(o.getX() - t.getX());
				double diffY = Math.abs(o.getY() - t.getY());

				if (diffX < o.getWidth() || diffY < o.getHeight()) { //Ej korrekt
					if (Math.hypot(diffX, diffY) < o.getWidth()) {
						Coord rot = Helper.getRotation(t.getCoord(), o.getCoord());
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

}
