package hs.ss16.asp;

import java.util.ArrayList;

public class Timer extends Thread{
	
	Player player;
	ArrayList<Sprite> sprites;
	Board board;
	
	public Timer(Player player, ArrayList<Sprite> sprites, Board board){
		this.player = player;
		this.sprites = sprites;
		this.board = board;
	}
	
	@Override
	public void run(){
		while(true){
			
			player.calculatePosition();
			for (Sprite sprite : sprites) {
				 sprite.calculatePosition();
			 }
			board.repaint();
			
			
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
