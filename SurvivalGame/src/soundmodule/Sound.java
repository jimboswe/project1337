package soundmodule;

import java.util.Random;

import mapobjects.WeaponType;

public enum Sound {
	Click("resources\\sfx\\click_empty_pistol.wav"),
	Deagle_Fire("resources\\sfx\\deagle_fire.wav"),
	Deagle_Reload("resources\\sfx\\deagle_reload.wav"),
	Bow_Fire("resources\\sfx\\bow_fire.wav"),
	Zombie_In_Pain("resources\\sfx\\zombie_in_pain.wav"),
	Splat1("resources\\sfx\\splat1.wav"),
	Splat2("resources\\sfx\\splat2.wav"),
	Splat3("resources\\sfx\\splat3.wav"),
	Broken_Bones("resources\\sfx\\broken_bones.wav"),
	Zombie_Long_Death("resources\\sfx\\zombie_long_death.wav"),
	Zombie_Moan("resources\\sfx\\zombie_moan.wav"),
	Arrow_Hit1("resources\\sfx\\punch.wav"),
	Male_Grunt("resources\\sfx\\male_grunt.wav"),
	
	Music("resources\\sfx\\menu_music.wav");

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
