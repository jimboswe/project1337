package game;

import inputs.Type;
import mapobjects.Projectile;
import mapobjects.StaticObject;

public class Helper {
	public static double getAngle(Coord start, Coord target) {
		double angle = Math.atan2(start.getY() - target.getY(), start.getX() - target.getX()) - Math.PI / 2;
		angle += Math.toRadians(-90);
		return angle;
	}

	public static Coord getRotation(Coord start, Coord target) {
		if(start.getX() == target.getX() && start.getY() == target.getY())
			return new Coord(0,0);
		
		double angle = getAngle(start, target);

		double ax = Math.cos(angle);
		double ay = Math.sin(angle);
		return new Coord(ax, ay);
	}
	
	public static void addInteractText(Coord player) {
		StaticObject ob = Game.map.mapContent.getNearestCollectableObject(player);
		if (ob != null) {
			Game.ui.AddText("interact", "Pick up " + ob.getObjectName(), Game.WIDTH / 2, Game.HEIGHT / 2 + 50, 20);
		} else
			Game.ui.DeleteText("interact"); //Eller automatiskt ta bort alla efter varje paint?
	}
	
	public static void addProjectile(Coord start, Coord target, Type projectile) { //Flytta till map
		double speed = 5;

		Coord dir = Helper.getRotation(start, target);

		Game.map.mapContent.addProjectile(new Projectile(start.getX() + (dir.getX() * 20), start.getY() + (dir.getY() * 20), speed * dir.getX(), speed * dir.getY(), 0.8, projectile, 1, 0,
				new Coord(target.getX(), target.getY())));
	}
}
