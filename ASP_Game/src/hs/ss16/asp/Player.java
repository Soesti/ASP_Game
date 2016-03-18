package hs.ss16.asp;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends Sprite {
	
	Direction dir = Direction.Top;
	int player_speed = 5;

	public Player(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		ImageIcon ii = new ImageIcon("img/player.png");
		image = ii.getImage();
	}
	
	public void setDirection(Direction dir){
		this.dir = dir;
	}
	
	@Override
	public void calculatePosition(){
		if(dir == Direction.Left){
			if(xPosition - player_speed > 0){
				xPosition = xPosition - player_speed;
			}
		}
		if(dir == Direction.Right){
			if(xPosition + player_speed < 930){
				xPosition = xPosition + player_speed;
			}
		}
	}
	
	public Direction getDirection(){
		return dir;
	}

}
