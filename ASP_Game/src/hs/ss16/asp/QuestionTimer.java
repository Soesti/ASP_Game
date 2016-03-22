package hs.ss16.asp;


public class QuestionTimer extends Thread {

	private Board board;
	private boolean run = true;
	
	
	public QuestionTimer(Board board){
		this.board = board;
	}
	
	
	@Override
	public void run(){
		while(run){
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			board.doQuestionEvent();
		}
	}
}
