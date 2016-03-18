package hs.ss16.asp;

public class Obstacle extends Sprite {
	
	private int speed = 0;

	public Obstacle(int xPosition, int yPosition) {
		super(xPosition, yPosition);
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	@Override
	public void calculatePosition() {
		this.yPosition += speed;
	}
}
