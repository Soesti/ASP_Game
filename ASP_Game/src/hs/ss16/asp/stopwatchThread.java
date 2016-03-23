package hs.ss16.asp;

public class stopwatchThread extends Thread {

	Board board;
	
	boolean run = true;

	public stopwatchThread(Board board) {
		this.board = board;
	}
	
	public void toggleBool () {
		run = !run;
	}
	
	@Override
	public void run(){
		while(true) {
			if(run == true) {
				try {
					sleep(1000);
					board.increaseNumberOfSeconds();
					board.decreaseNumberOfLifeSeconds(1);
					board.checkOutOfTime();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
