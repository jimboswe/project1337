package soundmodule;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class StreamAudio implements Runnable {

	String audioFilePath = "C:\\java\\sfx\\deagle_fire.wav";
	File audioFile;
	boolean playCompleted;
	private static final int BUFFER_SIZE = 4096;
	AudioInputStream audioStream;
	SourceDataLine audioLine;
	AudioFormat format;
	DataLine.Info info;

	StreamAudio() {
		audioFile = new File(audioFilePath);
	}

	void play() {
		try {
			audioStream = AudioSystem.getAudioInputStream(audioFile);
			format = audioStream.getFormat();
			info = new DataLine.Info(SourceDataLine.class, format);
			audioLine = (SourceDataLine) AudioSystem.getLine(info);
			audioLine.open(format);
			audioLine.start();

			byte[] bytesBuffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
				audioLine.write(bytesBuffer, 0, bytesRead);
			}
			audioLine.drain();
			audioLine.close();
			audioStream.close();
			
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {e.printStackTrace();}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
			play();
		}
	}

}