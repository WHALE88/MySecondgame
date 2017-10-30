package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ua.max.pane.Block;
import ua.max.pane.Bonuse;
import ua.max.pane.Player;
import ua.max.pane.Scores;
import ua.max.sprite.Timer;

public class Game extends Application {
	private Stage primaryStage;
	private Scene scene;

	public static final int WIDTH_SCREEN = 1280;
	public static final int HEIGHT_SCREEN = 720;
	public static final int BLOCK_SIZE = 45;
	public static final int PLAYER_SIZE = 40;

	private Image icon;
	private Image backgroundImg;
	private Scores scores;
	public static boolean exist = true;

	public KeyInput input;
	public static AnimationTimer aTimer;

	public static ArrayList<Block> platforms = new ArrayList<>();
	public static ArrayList<Rectangle> mainPrize = new ArrayList<>();
	private HashMap<KeyCode, Boolean> keys = new HashMap<>();
	public Timer timer;

	public static Pane appRoot = new Pane();
	public static Pane gameRoot = new Pane();
	public static Pane playerRoot = new Pane();
	public GridPane gp = new GridPane();

	private Bonuse bon;
	public Player player;
	public int levelNumber = 0;
	private int levelWidth;

	public Semaphore sem1;
	public Semaphore sem2;
	public Semaphore sem3;
	public static boolean sem = true;
	public static int semN = 1;

	protected Block floor, brick;
	protected Music music;
	public static Rectangle rect;

	private void initContent() {
		sem1 = new Semaphore(1);
		sem2 = new Semaphore(0);
		// sem3 = new Semaphore(0);
		icon = new Image("icon.png");
		backgroundImg = new Image("background.png");
		ImageView backgroundIV = new ImageView(backgroundImg);
		backgroundIV.setFitHeight(16 * BLOCK_SIZE);
		backgroundIV.setFitWidth(212 * BLOCK_SIZE);

		levelWidth = LevelMap.levels[levelNumber][0].length() * BLOCK_SIZE;
		for (int i = 0; i < LevelMap.levels[levelNumber].length; i++) {
			String line = LevelMap.levels[levelNumber][i];
			for (int j = 0; j < line.length(); j++) {
				switch (line.charAt(j)) {
				case '0':
					break;
				case '1':
					floor = new Block(Block.BlockType.GRASS, j * BLOCK_SIZE, i * BLOCK_SIZE);
					break;
				case '2':
					brick = new Block(Block.BlockType.BRICK, j * BLOCK_SIZE, i * BLOCK_SIZE);
					break;
				case '*':
					rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE, Color.RED);
					rect.setX(j * BLOCK_SIZE);
					rect.setY(i * BLOCK_SIZE);
					mainPrize.add(rect);
					gameRoot.getChildren().addAll(rect);

					break;
				}
			}
		}

		player = new Player(playerRoot, sem1, sem2);
		player.pane.setTranslateX(0);
		player.pane.setTranslateY(0);
		player.pane.translateXProperty().addListener((obs, old, newValue) -> {
			int offset = newValue.intValue();
			if (offset > 640 && offset < levelWidth - 640) {
				gameRoot.setLayoutX(-(offset - 640));
				backgroundIV.setLayoutX(-(offset - 640));
			}
		});

		bon = new Bonuse(gameRoot);
		scores = new Scores(player, sem2, sem3);
		music = new Music(sem1, sem3);

		Label sco = new Label("SCORES: ");
		sco.textProperty().bind(scores.messageProperty());

		ProgressBar bonus = new ProgressBar();
		bonus.progressProperty().bind(scores.progressProperty());

		input = new KeyInput(player, keys, levelWidth);
		gp.setAlignment(Pos.TOP_RIGHT);

		new Thread(player).start();
		new Thread(music).start();
		new Thread(scores).start();

		HBox hbBtn = new HBox(80);
		hbBtn.setAlignment(Pos.TOP_CENTER);
		hbBtn.getChildren().addAll(bonus, sco);
		gp.add(hbBtn, 0, 0);

		gameRoot.getChildren().add(player.pane);
		appRoot.getChildren().addAll(backgroundIV, gp, gameRoot);
		HBox layout = new HBox(5);
		layout.setLayoutX(WIDTH_SCREEN - 200);
		Label label = new Label();
		timer = new Timer(player, appRoot, label, layout);

	}

	private void update() {
		input.keyPressed();
		bon.bonus();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		initContent();
		scene = new Scene(appRoot, WIDTH_SCREEN, HEIGHT_SCREEN);
		scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		scene.setOnKeyReleased(event -> {
			keys.put(event.getCode(), false);
			player.animation.stop();
			player.movement = false;
		});

		primaryStage.setTitle("Max Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.getIcons().add(icon);

		aTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
			}
		};
		aTimer.start();

		primaryStage.setOnCloseRequest(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				primaryStage.close();
				aTimer.stop();
				exist = false;
			}
		});

	}

	public Scene setRegisterScene() {
		try {
			start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scene;
	}

	
	 public static void main(String[] args) { launch(args); }
	 
}
