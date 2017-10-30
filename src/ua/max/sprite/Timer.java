package ua.max.sprite;

import application.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ua.max.pane.Player;

public class Timer {
	private final Integer startTime = 60;
	private Integer seconds = startTime;

	private Label label;
	protected HBox layout;
	protected Pane pane;
	protected Timeline time;
	private Player player;

	public Timer(Player player, Pane pane, Label label, HBox layout) {
		this.player=player;
		this.pane = pane;
		this.label = label;
		this.layout = layout;
		label.setTextFill(Color.BLACK);
		label.setFont(Font.font(20));
		layout.getChildren().add(label);
		doTime();
		pane.getChildren().add(layout);

	}

	public void doTime() {
		time = new Timeline();
		time.setCycleCount(Timeline.INDEFINITE);
		if (time != null) {
			time.stop();
		}
		KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				seconds--;
				label.setText("TIME LEFT: " + seconds.toString());
				if (seconds <= 0) {
					Game.aTimer.stop();
					Game.exist = false;
					time.stop();

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("MAX ALERT");
					alert.setHeaderText("TIME IS UP");
					String s;
					if(player.win){
						s ="YOU HAVE WIN! YOUR SCORES: " + player.score;
					} else {
						s ="YOU HAVE LOST!";
					}
					alert.setContentText(s);
					alert.show();
					
					alert.showingProperty().addListener((observable, oldValue, newValue) -> {
						if (!newValue) {
							System.exit(0);
						}
					});

					
				/*	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setHeaderText("Look, a Confirmation Dialog");
					alert.setContentText("Are you ok with this?");
					alert.show();
					
						Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						System.out.println("OK");
					} else {
						System.out.println("CANCEL");
					}*/
					/*
						int answer = JOptionPane.showConfirmDialog(null,"Restart Game",null, JOptionPane.YES_NO_CANCEL_OPTION);
					 if (answer == JOptionPane.YES_OPTION){
			                System.out.println("YES");
					 }else if (answer == JOptionPane.NO_OPTION){
						 System.exit(0);
						 System.out.println("NO");
					 } else if (answer == JOptionPane.CANCEL_OPTION){
						 Game.aTimer.start();
							Game.exist = true;
						 System.out.println("CANCEL");
					 }*/
					
				

				}
			}
		});
		time.getKeyFrames().add(frame);
		time.playFromStart();
	}
}
