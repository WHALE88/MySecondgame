package ua.max.menubar;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import application.Menu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RegisterMain extends Application {
	public Connection con;
	public Statement st;
	public ResultSet rs;
	protected Scene scene;
	public Stage primaryStage;
	private Image icon;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			icon = new Image("icon.png");
			Label l1 = new Label("Login: ");
			l1.setId("l1");
			Label l2 = new Label("Password: ");
			l2.setId("l2");

			Button log = new Button("LOG");
			Button reg = new Button("REG");
			TextField tf = new TextField();
			PasswordField pf = new PasswordField();
			GridPane grid = new GridPane();
			grid.setId("grid");

			grid.setAlignment(Pos.CENTER);
			grid.setHgap(15);
			grid.setVgap(15);

			grid.add(l1, 0, 0);
			grid.add(tf, 1, 0);
			grid.add(l2, 0, 1);
			grid.add(pf, 1, 1);

			HBox hbBtn = new HBox(10); // отступ между элементами
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(log);
			hbBtn.getChildren().add(reg);
			grid.add(hbBtn, 1, 2);

			scene = new Scene(grid, 540, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("MAX_GAME_V.2.84");
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(icon);
			primaryStage.show();

			reg.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					try {
						String user = tf.getText().trim();
						String pas = pf.getText().trim();

						if (!tf.getCharacters().toString().isEmpty() && !pf.getCharacters().toString().isEmpty()) {
							if (new Database().register(user, pas)) {
								JOptionPane.showMessageDialog(null, "New user have been created!");
								primaryStage.close();
								new Menu().start(primaryStage);
							} else {
								JOptionPane.showMessageDialog(null, "Duplicate user!");

							}
						}
					} catch (HeadlessException z) {
						z.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			});

			log.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					String user = tf.getText().trim();
					String pas = pf.getText().trim();
					try {

						if (!tf.getCharacters().toString().isEmpty() && !pf.getCharacters().toString().isEmpty()) {
							if (new Database().login(user, pas)) {
								JOptionPane.showMessageDialog(null, "Users Found, Access Granted");
								primaryStage.close();
								new Menu().start(primaryStage);
							} else {
								JOptionPane.showMessageDialog(null, "Invalid Username Or Password !");

							}
						}
					} catch (HeadlessException z) {
						z.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene getRegScene() {
		start(primaryStage);
		return scene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}