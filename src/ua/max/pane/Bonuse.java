package ua.max.pane;

import java.util.HashSet;
import java.util.Set;

import application.Game;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bonuse {
	// public static ArrayList<Circle> bonuses = new ArrayList<>();
	public static Set<Circle> bonuses = new HashSet<Circle>();
	public static final int BLOCK_SIZE = 45;
	private Circle circ;
	public Pane bonusPane;

	public Bonuse(Pane bonusPane) {
		this.bonusPane = bonusPane;
	}

	public void bonus() {
		int x = ((int) Math.floor(Math.random() * 200));
		int y = ((int) Math.floor(Math.random() * 15));
		int random = ((int) Math.floor(Math.random() * 5));

		circ = new Circle((x * BLOCK_SIZE) - BLOCK_SIZE / 2, (y * BLOCK_SIZE) + BLOCK_SIZE / 2, BLOCK_SIZE / 4,
				Color.YELLOW);
		if (random == 3) {
			for (Node platform : Game.platforms) {
				if (((circ.getBoundsInParent()).intersects(platform.getBoundsInParent().getMaxX() - BLOCK_SIZE,
						platform.getBoundsInParent().getMaxY() - (BLOCK_SIZE * 2),
						platform.getBoundsInParent().getWidth(), platform.getBoundsInParent().getHeight()))) {
					bonuses.add(circ);
					bonusPane.getChildren().addAll(circ);
				}

			}

			for (Node platform : Game.platforms) {
				if ((platform.getBoundsInParent()).intersects(circ.getBoundsInParent())) {
					bonuses.remove(circ);
					bonusPane.getChildren().remove(circ);

				}
			}
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bonusPane == null) ? 0 : bonusPane.hashCode());
		result = prime * result + ((circ == null) ? 0 : circ.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bonuse other = (Bonuse) obj;
		if (bonusPane == null) {
			if (other.bonusPane != null)
				return false;
		} else if (!bonusPane.equals(other.bonusPane))
			return false;
		if (circ == null) {
			if (other.circ != null)
				return false;
		} else if (!circ.equals(other.circ))
			return false;
		return true;
	}
}
