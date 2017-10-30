package ua.max.pane;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import application.Game;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ua.max.sprite.SpriteAnimation;

public class Player extends Task<Void> {
	public Pane pane;
	public Image playerImg;// = new
							// Image(getClass().getResourceAsStream("res/player_1.png"));
	public ImageView imageView;// = new ImageView(playerImg);
	private int count = 8;
	private int columns = 8;
	private int offsetX = 0;
	private int offsetY = 155;
	private int width = 46;
	private int height = 45;
	public int score = 0;
	
	public boolean win = false;
	public boolean movement = false;
	public Semaphore sem1;
	public Semaphore sem2;
	public Semaphore sem3;

	public Circle removeCirc = null;
	public Rectangle removeRect = null;
	public SpriteAnimation animation;
	public Point2D playerVelocity = new Point2D(0, 0);
	private boolean jumpping = true;

	public Player(Pane pane, Semaphore sem1, Semaphore sem2) {
		this.sem1 = sem1;
		this.sem2 = sem2;
		this.pane = pane;
		playerImg = new Image("player_1.png");
		imageView = new ImageView(playerImg);
		imageView.setFitHeight(40);
		imageView.setFitWidth(40);
		imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		animation = new SpriteAnimation(this.imageView, Duration.millis(600), count, columns, offsetX, offsetY, width,
				height);
		pane.getChildren().addAll(this.imageView);
	}

	public synchronized void moveX(int value) {
		boolean movingRight = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (Node platform : Game.platforms) {
				if (pane.getBoundsInParent().intersects(platform.getBoundsInParent())) {
					if (movingRight) {
						if (pane.getTranslateX() + Game.PLAYER_SIZE == platform.getTranslateX()) {
							pane.setTranslateX(pane.getTranslateX() - 1);
							isBonuseEat();
							getMainPrize();
							return;
						}
					} else {
						if (pane.getTranslateX() == Game.BLOCK_SIZE + platform.getTranslateX()) {
							pane.setTranslateX(pane.getTranslateX() + 1);
							isBonuseEat();
							return;
						}
					}
				}
			}
			pane.setTranslateX(pane.getTranslateX() + (movingRight ? 1 : -1));
		}
	}

	public synchronized void moveY(int value) {
		boolean movingDown = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (Block platform : Game.platforms) {
				if (pane.getBoundsInParent().intersects(platform.getBoundsInParent())) {
					isBonuseEat();
					getMainPrize();
					if (movingDown) {
						if (pane.getTranslateY() + Game.PLAYER_SIZE == platform.getTranslateY()) {
							pane.setTranslateY(pane.getTranslateY() - 1);
							jumpping = true;
							return;
						}
					} else {
						if (pane.getTranslateY() == platform.getTranslateY() + Game.BLOCK_SIZE) {
							pane.setTranslateY(pane.getTranslateY() + 1);
							playerVelocity = new Point2D(0, 10);
							return;
						}
					}
				} else {
					isBonuseEat();
					getMainPrize();
				}
			}
			pane.setTranslateY(pane.getTranslateY() + (movingDown ? 1 : -1));
			if (pane.getTranslateY() > Game.HEIGHT_SCREEN) {
				pane.setTranslateX(0);
				pane.setTranslateY(400);
				Game.gameRoot.setLayoutX(0);
			}
		}
	}

	public void jumpPlayer() {
		if (jumpping) {
			playerVelocity = playerVelocity.add(0, -30);
			jumpping = false;
			isBonuseEat();
		}
	}

	public void isBonuseEat() {
		Bonuse.bonuses.forEach((circ) -> {
			if (pane.getBoundsInParent().intersects(circ.getBoundsInParent())) {
				removeCirc = circ;
				score++;
				System.out.println(score);
			}

		});
		Bonuse.bonuses.remove(removeCirc);
		Game.gameRoot.getChildren().remove(removeCirc);
	}

	public void getMainPrize() {
		Game.mainPrize.forEach((rect) -> {
			if (pane.getBoundsInParent().intersects(rect.getBoundsInParent())) {
				removeRect = rect;
				int rand = ((int) Math.floor(Math.random() * 60));
				score += rand;
				System.out.println(score);
				win = true;
			}

		});
		Game.mainPrize.remove(removeRect);
		Game.gameRoot.getChildren().remove(removeRect);
	}

	@Override
	protected Void call() throws Exception {
		if (!Game.sem && Game.semN == 2) {
			try {
				sem2.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
		while (Game.exist) {
			if (isCancelled()) {
				break;
			}
			System.out.println("S1");
			TimeUnit.SECONDS.sleep(1);
			Game.sem = false;
			Game.semN = 2;
			sem1.release();
		}

		return null;
	}

}
