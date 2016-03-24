package hs.ss16.asp;

public class CollisionThread extends Thread{
	Board board;
	
	boolean noHit = true;
	int sleepMilliseconds = 100;
	
	public CollisionThread(Board board){
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
