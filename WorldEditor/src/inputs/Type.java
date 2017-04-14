package inputs;

import java.util.concurrent.ThreadLocalRandom;

import mapobjects.ObjectType;

public enum Type {
	TABLE(true, null),
	GRASS(true, null),
	ZOMBIE(false, ObjectType.CREATURE),
	
	STONE1(true, ObjectType.HARVESTABLE),
	STONE2(true, ObjectType.HARVESTABLE),
	STONE3(true, ObjectType.HARVESTABLE),
	STONE4(true, ObjectType.HARVESTABLE),
	
	TREE1(true, ObjectType.HARVESTABLE),
	TREE2(true, ObjectType.HARVESTABLE),
	TREE3(true, ObjectType.HARVESTABLE),
	TREE4(true, ObjectType.HARVESTABLE),
	TREE5(true, ObjectType.HARVESTABLE),
	TREE6(true, ObjectType.HARVESTABLE),
	TREE7(true, ObjectType.HARVESTABLE),
	
	ARROW(false, ObjectType.PROJECTILE),
	BOW(false, ObjectType.COLLECTABLE),
	BLOOD(true, ObjectType.VISUAL),
	EXPLOSION(true, ObjectType.VISUAL),
	PISTOL(false, ObjectType.COLLECTABLE),
	BULLET(false, ObjectType.PROJECTILE),
	MUZZLEFLASH(true, ObjectType.VISUAL),
	UZI(false, ObjectType.COLLECTABLE);

	boolean fixed;
	ObjectType objectType;

	private Type(boolean setFixed, ObjectType setObjectType) {
		this.fixed = setFixed;
		this.objectType = setObjectType;
	}

	public ObjectType getObjectType() {
		return objectType;
	}

	public boolean isFixed() {
		return fixed;
	}
	
	public static Type getRandomTreeModel() {
		int r = ThreadLocalRandom.current().nextInt(1, 7 + 1);
		switch(r) {
		case 1:
			return TREE1;
		case 2:
			return TREE2;
		case 3:
			return TREE3;
		case 4:
			return TREE4;
		case 5:
			return TREE5;
		case 6:
			return TREE6;
		case 7:
			return TREE7;
		default:
			return TREE1;
		}
	}
	
	public static Type getRandomStoneModel() {
		int r = ThreadLocalRandom.current().nextInt(1, 4 + 1);
		switch(r) {
		case 1:
			return STONE1;
		case 2:
			return STONE2;
		case 3:
			return STONE3;
		case 4:
			return STONE4;
		default:
			return STONE1;
		}
	}

	public static Type getModel(String s) {
		switch (s) {
		case "TREE":
			return TREE1;
		case "STONE":
			return STONE1;
		case "ZOMBIE":
			return ZOMBIE;
		default:
			return TREE1;
		}
	}
	
	public static String getString(Type t) {
		switch (t) {
		case TREE1:
			return "TREE";
		case STONE1:
			return "STONE";
		case ZOMBIE:
			return "ZOMBIE";
		default:
			System.out.println("Error getString");
			return null;
		}
	}
}