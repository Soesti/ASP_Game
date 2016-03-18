package hs.ss16.asp;

public abstract class Obstacle extends Sprite {
	
	

	public Obstacle(int xPosition, int yPosition) {
		super(xPosition, yPosition);
	}
	
	@Override
	public void calculatePosition() {
		this.yPosition += speed;
	}
}
