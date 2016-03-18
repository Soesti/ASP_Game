package hs.ss16.asp;

import java.util.ArrayList;

public class CollisionThread extends Thread{
	Player play;
	ArrayList<Sprite> sprites;
	Board board;
	
	boolean noHit = true;
	int sleepMilliseconds = 100;
	
	public CollisionThread(Board board){
		this.play = play;
		this.sprites = sprites;
		this.board = board;
	}
	
	@Override
	public void run(){
		while(noHit == true) {
			try {
				noHit = board.checkCollisions();
				sleep(sleepMilliseconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
				interrupt();
			}
		}
	}
}
