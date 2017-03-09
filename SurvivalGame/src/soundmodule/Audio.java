package soundmodule;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
	private static ArrayList<AudioClip> audioclips = new ArrayList<AudioClip>();
	AudioClip clip;

	public Audio() {}

	public void playNewClip(Sound sound, float setVolume) {
		clip = new AudioClip(new File(sound.getPath()), setVolume);
		audioclips.add(clip);
		Thread t = new Thread(clip);
		t.start();
	}

	public void setVolume(Clip clippp) {
		Clip clip = audioclips.get(0).getClip();
		double gain = 0.1;
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(dB);
	}
}