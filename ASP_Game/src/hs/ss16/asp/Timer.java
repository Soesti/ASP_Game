package hs.ss16.asp;

import java.util.ArrayList;

public class Timer extends Thread{
	
	Player play;
	ArrayList<Sprite> sprites;
	Board board;
	
	public Timer(Player play, ArrayList<Sprite> sprites, Board board){
		this.play = play;
		this.sprites = sprites;
		this.board = board;
	}
	
	@Override
	public void run(){
		while(true){
			play.calculatePosition();
			board.repaint();
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
