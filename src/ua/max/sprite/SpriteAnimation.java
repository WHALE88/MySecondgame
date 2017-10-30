package ua.max.sprite;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
	private ImageView imageView;
	private int count; // ���������� ������ ��������
	private int columns; // ���������� ��������
	private int offsetX; // �������� ������� ����� �� ��� �
	private int offsetY; // �������� ������� ����� �� ��� y
	private int WIDTH; // ������ ����� ��������
	private int HEIGHT; // ����� ����� ��������

	public SpriteAnimation(ImageView imageView, Duration duration, int count, int columns, int offsetX, int offsetY,
			int WIDTH, int HEIGHT) {
		super();
		this.imageView = imageView;
		this.count = count;
		this.columns = columns;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		setCycleDuration(duration); // ������������� ����������������� �������
		setCycleCount(Animation.INDEFINITE);
		setInterpolator(Interpolator.LINEAR);
		this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, WIDTH, HEIGHT));
	}

	public void setOffsetX(int x) {
		this.offsetX = x;
	}

	@Override
	protected void interpolate(double k) {
		final int index = Math.min((int) Math.floor(k * count), count - 1);
		// Math.min �������� ������� �� ���� �����
		// Math.floor ��������� ����� � ������� �������
		final int x = (index % columns) * WIDTH + offsetX;
		final int y = (index / columns) * HEIGHT + offsetY;
		imageView.setViewport(new Rectangle2D(x, y, WIDTH, HEIGHT));
	}
}
