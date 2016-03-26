package hs.ss16.asp;

import java.util.ArrayList;

public class Timer extends Thread{
	
	private Player player;
	private ArrayList<Sprite> sprites;
	private Background background;
	private Board board;
	private int ticks = 100;
	private boolean run = true;
	
	public Timer(Player player, ArrayList<Sprite> sprites, Background background, Board board){
		this.player = player;
		this.sprites = sprites;
		this.background = background;
		this.board = board;
	}
	
	@Override
	public void run(){
		while(run){
			if(ticks == 0){				
				board.createObstacle();
				ticks = (int)((World.screenSize.getHeight() / board.obstracleSpeed)/3);
				
			}
			board.setDifficult();
			background.calculatePosition();
			player.calculatePosition();
			for (int i = 0; i < sprites.size(); i++) {
				 sprites.get(i).calculatePosition();
			 }
			board.repaint();
			
			
			ticks--;
			
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		board.repaint();
	}
	
	public void endLoop(){
		run = false;
	}

}
