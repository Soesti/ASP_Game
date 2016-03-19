package hs.ss16.asp;


import javax.swing.ImageIcon;

public class Player extends Sprite {
	
	private Direction direction = Direction.Top;
	private int lives;

	public Player(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		ImageIcon ii = new ImageIcon("img/player.png");
		image = ii.getImage();
		speed = 5;
		lives = 3;
	}
	
	public void setDirection(Direction dir){
		this.direction = dir;
	}
	
	@Override
	public void calculatePosition(){
		if(direction == Direction.Left){
			if(xPosition - speed > 0){
				xPosition = xPosition - speed;
			}
		}
		if(direction == Direction.Right){
			if(xPosition + speed < 908){
				xPosition = xPosition + speed;
			}
		}
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public int getCurrentLives(){
		return lives;
	}
	
	public void decrementLive(){
		lives--;
	}

}
