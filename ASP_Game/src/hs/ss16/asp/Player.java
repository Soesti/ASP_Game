package hs.ss16.asp;


import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Sprite {
	
	private Direction direction = Direction.Top;
	private int lives;
	private boolean visible = true;
	private int invisibleTime = 10;
	
	private Image[] images;

	public Player(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		
		//Load all images
		images = new Image[4];
		
		try {
			InputStream resource1 = Rock.class.getResourceAsStream("/img/player.png");
			images[0] = ImageIO.read(resource1);
			
			InputStream resource2 = Rock.class.getResourceAsStream("/img/player_left.png");
			images[1] = ImageIO.read(resource2);
			
			InputStream resource3 = Rock.class.getResourceAsStream("/img/player_right.png");
			images[2] = ImageIO.read(resource3);
			
			InputStream resource4 = Player.class.getResourceAsStream("/img/player_hit.png");
			images[3] = ImageIO.read(resource4);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		image = images[0];
		speed = 5;
		lives = 3;
		height = 92;
		width = 92;
	}
	
	public void setDirection(Direction dir){
		this.direction = dir;
		if(visible){
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
	}
	
	@Override
	public void calculatePosition(){
		if(visible == false){
			if(invisibleTime == 0){
				visible = true;
				setDirection(direction);
			}
			else{
				invisibleTime--;
			}
		}
		
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
	
	public void setLives(int lives){
		this.lives = lives;
	}
	
	public void setInvisible(){
		visible = false;
		image = images[3];
	}

}
