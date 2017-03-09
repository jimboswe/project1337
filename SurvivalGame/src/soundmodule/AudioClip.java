package soundmodule;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineEvent.Type;

public class AudioClip implements Runnable {
	File clipFile;
	Clip clip;
	float volume;

	AudioClip(File playThis, float setVolume) {
		clipFile = playThis;
		volume = setVolume;
	}

	public void playdatClip()
			throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {

		AudioListener listener = new AudioListener();
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);

		try {
			clip = AudioSystem.getClip();
			clip.addLineListener(listener);
			clip.open(audioInputStream);
			try {
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume);
				clip.start();
				listener.waitUntilDone();
			} finally {
				clip.close();
			}
		} finally {
			audioInputStream.close();
		}
	}

	class AudioListener implements LineListener {
		private boolean done = false;

		@Override
		public synchronized void update(LineEvent event) {
			Type eventType = event.getType();
			if (eventType == Type.STOP || eventType == Type.CLOSE) {
				done = true;
				notifyAll();
			}
		}

		public synchronized void waitUntilDone() throws InterruptedException {
			while (!done) {
				wait();
			}
		}
	}

	@Override
	public void run() {
		try {
			playdatClip();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Clip getClip() {
		return clip;
	}

}
