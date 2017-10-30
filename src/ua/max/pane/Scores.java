package ua.max.pane;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import application.Game;
import javafx.concurrent.Task;

public class Scores extends Task<Void> {
	private Player player;
	public Semaphore sem2;
	public Semaphore sem3;

	public Scores(Player player, Semaphore sem2, Semaphore sem3) {
		this.sem2 = sem2;
		this.sem3 = sem3;
		this.player = player;
	};

	@Override
	protected Void call() throws Exception {
		if (Game.sem && Game.semN == 3) {
			try {
				sem3.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
		int max = 45;
		while (Game.exist) {
			if (isCancelled()) {
				break;
			}
			System.out.println("S2");
			TimeUnit.SECONDS.sleep(1);
			updateProgress(player.score, max);
			updateMessage("" + player.score);
			Game.sem = true;
			Game.semN = 3;
			sem2.release();

		}

		return null;
	}

}
