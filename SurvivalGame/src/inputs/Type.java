package inputs;

public enum Type {
	TABLE(true), GRASS(true), ZOMBIE(false), STONE(true), ARROW(false), TREE(true), BOW(false), BLOOD(true), EXPLOSION(
			true), PISTOL(false), BULLET(false), MUZZLEFLASH(true), UZI(false);

	boolean fixed;

	private Type(boolean setFixed) {
		this.fixed = setFixed;
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