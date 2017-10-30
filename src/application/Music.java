package application;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music extends Task<Void> {
	private Media musicFile;
	private MediaPlayer mediaplayer;
	public Semaphore sem1;
	public Semaphore sem3;

	public Music(Semaphore sem1, Semaphore sem3) {
		this.sem1 = sem1;
		this.sem3 = sem3;
		getMusic();
	}

	public void getMusic() {
		musicFile = new Media("file:///C:/Users/Макс/workspace/MyProject/music/Heathens.mp3");
		mediaplayer = new MediaPlayer(musicFile);
		mediaplayer.setVolume(0.5);
		mediaplayer.setAutoPlay(true);
	}

	@Override
	protected Void call() throws Exception {
		if (Game.sem && Game.semN == 1) {
			sem1.acquire();
		}
		while (Game.exist) {
			System.out.println("S3");
			TimeUnit.MICROSECONDS.sleep(200);
			mediaplayer.setAutoPlay(true);
			Game.sem = true;
			Game.semN = 1;
			sem3.release();
		}

		return null;
	}

}
