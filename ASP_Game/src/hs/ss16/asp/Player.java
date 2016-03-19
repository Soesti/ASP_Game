package hs.ss16.asp;


import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends Sprite {
	
	private Direction direction = Direction.Top;
	private int lives;
	
	private Image[] images;

	public Player(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		
		//Load all images
		images = new Image[3];
		ImageIcon ii1 = new ImageIcon("img/player.png");
		images[0] = ii1.getImage();
		ImageIcon ii2 = new ImageIcon("img/player_left.png");
		images[1] = ii2.getImage();
		ImageIcon ii3 = new ImageIcon("img/player_right.png");
		images[2] = ii3.getImage();
		
		image = images[0];
		speed = 5;
		lives = 3;
		height = 92;
		width = 92;
	}
	
	public void setDirection(Direction dir){
		this.direction = dir;

		if(Direction.Left == dir){
			image = images[1];
		}
		else if(Direction.Right == dir){
			image = images[2];
		}
		else{
			image = images[0];
		}
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
