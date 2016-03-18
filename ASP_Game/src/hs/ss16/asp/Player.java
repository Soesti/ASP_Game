package hs.ss16.asp;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends Sprite {
	
	Direction direction = Direction.Top;
	int player_speed = 5;

	public Player(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		ImageIcon ii = new ImageIcon("img/player.png");
		image = ii.getImage();
	}
	
	public void setDirection(Direction dir){
		this.direction = dir;
	}
	
	@Override
	public void calculatePosition(){
		if(direction == Direction.Left){
			if(xPosition - player_speed > 0){
				xPosition = xPosition - player_speed;
			}
		}
		if(direction == Direction.Right){
			if(xPosition + player_speed < 930){
				xPosition = xPosition + player_speed;
			}
		}
	}
	
	public Direction getDirection(){
		return direction;
	}

}
