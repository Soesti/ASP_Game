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
			xPosition = xPosition - player_speed;
		}
		if(dir == Direction.Right){
			xPosition = xPosition + player_speed;
		}
	}

}
