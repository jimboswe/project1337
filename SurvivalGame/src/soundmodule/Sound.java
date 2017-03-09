package soundmodule;

import java.util.Random;

import mapobjects.WeaponType;

public enum Sound {
	Click("C:\\java\\sfx\\click_empty_pistol.wav"),
	Deagle_Fire("C:\\java\\sfx\\deagle_fire.wav"),
	Deagle_Reload("C:\\java\\sfx\\deagle_reload.wav"),
	Bow_Fire("C:\\java\\sfx\\bow_fire.wav"),
	Zombie_In_Pain("C:\\java\\sfx\\zombie_in_pain.wav"),
	Splat1("C:\\java\\sfx\\splat1.wav"),
	Splat2("C:\\java\\sfx\\splat2.wav"),
	Splat3("C:\\java\\sfx\\splat3.wav"),
	Broken_Bones("C:\\java\\sfx\\broken_bones.wav"),
	Zombie_Long_Death("C:\\java\\sfx\\zombie_long_death.wav"),
	Zombie_Moan("C:\\java\\sfx\\zombie_moan.wav"),
	Music("C:\\java\\sfx\\menu_music.wav");

	private String path;

	private Sound(String setPath) {
		this.path = setPath;
	}

	public String getPath() {
		return path;
	}
	
	public static Sound getFireSound(WeaponType type) {
		switch(type) {
		case BOW:
			return Sound.Bow_Fire;
		case PISTOL:
			return Sound.Deagle_Fire;
		default:
			return Sound.Deagle_Fire;
		}
	}
	public static Sound getReloadSound(WeaponType type) {
		switch(type) {
		case BOW:
			return Sound.Deagle_Reload;
		case PISTOL:
			return Sound.Deagle_Reload;
		default:
			return Sound.Deagle_Reload;
		}
	}
	
	public static Sound getRandomZombieKillSound() {
		Random rand = new Random();
		int result = rand.nextInt(3) + 1;
		switch(result) {
		case 1:
			return Sound.Splat2;
		case 2:
			return Sound.Splat3;
		case 3:
			return Sound.Broken_Bones;
		default:
			return Sound.Splat1;
		}
	}
}
