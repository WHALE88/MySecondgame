package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Menu extends Application {
	public static final int WIDTH_SCREEN = 1280;
	public static final int HEIGHT_SCREEN = 720;

	private Stage primaryStage;
	private Scene scene;
	private Button start;
	private Button rating;
	private Button exit;
	private BorderPane bp;
	protected HBox hbox;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			exit = new Button("EXIT");
			exit.setPrefSize(140, 50);
			start = new Button("START");
			start.setPrefSize(140, 50);
			rating = new Button("RATING");
			rating.setPrefSize(140, 50);

			bp = new BorderPane();

			hbox = new HBox(60);
			hbox.setAlignment(Pos.CENTER);
			hbox.setPrefWidth(400);
			hbox.setPrefHeight(100);
			bp.setCenter(hbox);
			hbox.getChildren().add(rating);
			hbox.getChildren().add(start);
			hbox.getChildren().add(exit);

			scene = new Scene(bp, WIDTH_SCREEN, HEIGHT_SCREEN);

			primaryStage.setScene(scene);
			primaryStage.show();

			start.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					primaryStage.close();
					try {
						new Game().start(primaryStage);
					} catch (Exception e) {
					}
				}
			});
			rating.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

				}
			});
			exit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene setMenu() {
		start(primaryStage);
		return scene;
	}
}
