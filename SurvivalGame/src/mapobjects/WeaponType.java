package mapobjects;

import inputs.Type;

public enum WeaponType {
	HANDS("Hands", 0), BOW("Improvised Bow", 0), PISTOL("Pistol", 10), UZI("Uzi", 6);

	private String name;
	private int recoil;

	private WeaponType(String setName, int setRecoil) {
		this.name = setName;
		this.recoil = setRecoil;
	}

	public int getRecoil() {
		return recoil;
	}

	public static Type getProjectile(WeaponType weapon) {
		switch (weapon) {
		case BOW:
			return Type.ARROW;
		case PISTOL:
			return Type.BULLET;
		case UZI:
			return Type.BULLET;
		default:
			return Type.BULLET;
		}
	}

	public String getName() {
		return name;
	}
}
