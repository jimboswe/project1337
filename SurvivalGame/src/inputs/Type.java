package inputs;

public enum Type {
	TABLE(true, null),
	GRASS(true, null),
	ZOMBIE(false, ObjectType.CREATURE),
	STONE(true, ObjectType.HARVESTABLE),
	ARROW(false, ObjectType.PROJECTILE),
	TREE(true, ObjectType.HARVESTABLE),
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

	public static Type getModel(String s) {
		switch (s) {
		case "TREE":
			return TREE;
		case "STONE":
			return STONE;
		case "ZOMBIE":
			return ZOMBIE;
		default:
			return TREE;
		}
	}
	
	public static String getString(Type t) {
		switch (t) {
		case TREE:
			return "TREE";
		case STONE:
			return "STONE";
		case ZOMBIE:
			return "ZOMBIE";
		default:
			System.out.println("Error getString");
			return null;
		}
	}
}