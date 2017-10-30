package ua.max.pane;

import application.Game;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Block extends Pane {
	Image blocksImg;// = new Image(getClass().getResourceAsStream("res/1.png"));
	ImageView block;

	public enum BlockType {
		GRASS, BRICK;
	}

	public Block(BlockType blockType, int x, int y) {
	//	blocksImg = new Image(getClass().getResourceAsStream("img/1.jpg"));
		blocksImg = new Image("rout.png");
		block = new ImageView(blocksImg);
		block.setFitWidth(Game.BLOCK_SIZE);
		block.setFitHeight(Game.BLOCK_SIZE);
		setTranslateX(x);
		setTranslateY(y);

		switch (blockType) {
		case GRASS:
			block.setViewport(new Rectangle2D(0, 134, 60, 60));
			break;
		case BRICK:
			block.setViewport(new Rectangle2D(0, 0, 60, 60));
			break;
		}
		getChildren().add(block);
		Game.platforms.add(this);
		Game.gameRoot.getChildren().add(this);
	}
}