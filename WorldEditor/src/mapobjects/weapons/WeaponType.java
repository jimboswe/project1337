package mapobjects.weapons;

import inputs.Type;

public enum WeaponType {
	HANDS("Hands", 0, 1, Type.BULLET, 1, 1, null, 0),
	BOW("Improvised Bow", 0, 0.8, Type.BOW, 6, 6, Type.ARROW, 0),
	PISTOL("Pistol", 10, 0.8, Type.PISTOL, 2, 1, Type.BULLET, 8),
	UZI("Uzi", 6, 0.8, Type.UZI, 2, 1, Type.BULLET, 8);

	private String name;
	private int recoil;
	private double scale;
	private Type model;
	private int numberOfFrames;
	private int standardFrame;
	private Type projectile;
	private int offsetPaint;

	private WeaponType(String setName, int setRecoil, double setScale, Type setModel, int setNumberOfFrames,
			int setStandardFrame, Type setProjectile, int setOffsetPaint) {
		this.name = setName;
		this.recoil = setRecoil;
		this.scale = setScale;
		this.model = setModel;
		this.numberOfFrames = setNumberOfFrames;
		this.standardFrame = setStandardFrame;
		this.projectile = setProjectile;
		this.offsetPaint = setOffsetPaint;
	}

	public Type getProjectile() {
		return projectile;
	}

	public String getName() {
		return name;
	}

	public int getRecoil() {
		return recoil;
	}
	
	public double getScale() {
		return scale;
	}
	
	public Type getModel() {
		return model;
	}

	public int getNumberOfFrames() {
		return numberOfFrames;
	}

	public int getStandardFrame() {
		return standardFrame;
	}

	public int getOffsetPaint() {
		return offsetPaint;
	}
}
