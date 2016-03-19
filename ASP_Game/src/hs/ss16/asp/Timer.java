package hs.ss16.asp;

import java.util.ArrayList;

public class Timer extends Thread{
	
	private Player player;
	private ArrayList<Sprite> sprites;
	private Board board;
	private int ticks =400;
	private boolean run = true;
	
	public Timer(Player player, ArrayList<Sprite> sprites, Board board){
		this.player = player;
		this.sprites = sprites;
		this.board = board;
	}
	
	@Override
	public void run(){
		while(run){
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
				e.printStackTrace();
			}
		}
	}
	
	public void endLoop(){
		run = false;
	}

}
