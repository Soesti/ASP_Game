package hs.ss16.asp;

import java.util.ArrayList;

public class Timer extends Thread{
	
	Player player;
	ArrayList<Sprite> sprites;
	Board board;
	private int ticks =400;
	
	public Timer(Player player, ArrayList<Sprite> sprites, Board board){
		this.player = player;
		this.sprites = sprites;
		this.board = board;
	}
	
	@Override
	public void run(){
		while(true){
			if(ticks == 0){
				
				board.createObstacle();
				ticks = 400;
			}
			
			player.calculatePosition();
			for (Sprite sprite : sprites) {
				 sprite.calculatePosition();
			 }
			board.repaint();
			
			
			ticks--;
			
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
