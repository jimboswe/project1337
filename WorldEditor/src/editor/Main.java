package editor;

import javax.swing.JFrame;

public class Main {

	public static void main(String args[]) {

		JFrame frame = new JFrame("Survive");
		Editor editor = new Editor();
		frame.add(editor);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); //Placera i mitten av skärmen

		int FRAMES_PER_SECOND = 200;
		int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
		long next_game_tick = System.currentTimeMillis();

		long sleep_time = 0;

		while (true) {
			editor.update();
			editor.repaint();
			next_game_tick += SKIP_TICKS;
			sleep_time = next_game_tick - System.currentTimeMillis();
			if (sleep_time >= 0) {
				try {
					Thread.sleep(sleep_time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else {
				
			}
		}
	}

}