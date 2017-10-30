package application;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javafx.scene.input.KeyCode;
import ua.max.pane.Player;

public class KeyInput {

	public Player player;
	private HashMap<KeyCode, Boolean> keys;
	private int levelWidth;
	public Semaphore sem2;
	public Semaphore sem3;

	public KeyInput(Player player, HashMap<KeyCode, Boolean> keys, int levelWidth) {
		super();
		this.player = player;
		this.keys = keys;
		this.levelWidth = levelWidth;
	}

	public void keyPressed() {
		if ((isPressed(KeyCode.UP) || (isPressed(KeyCode.W))) && player.pane.getTranslateY() >= 0) {
			player.jumpPlayer();
			player.movement = true;
		}
		if ((isPressed(KeyCode.LEFT) || (isPressed(KeyCode.A))) && player.pane.getTranslateX() >= 0) {
			player.pane.setScaleX(-1);
			player.animation.play();
			player.moveX(-5);
			player.movement = true;
		}
		if ((isPressed(KeyCode.RIGHT) || (isPressed(KeyCode.D)))
				&& player.pane.getTranslateX() + 40 <= levelWidth - 5) {
			player.pane.setScaleX(1);
			player.animation.play();
			player.moveX(5);
			player.movement = true;
		}
		if (player.playerVelocity.getY() < 10) {
			player.playerVelocity = player.playerVelocity.add(0, 1);
		}
		player.moveY((int) player.playerVelocity.getY());
	}

	public boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}
}
