package hs.ss16.asp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class KeyListenerQuestion implements KeyListener {

	Board board;
	List<Answer> answers;
	boolean getAnswer = false;
	
	
	public KeyListenerQuestion(List<Answer> answers, Board board) {
		this.board = board;
		this.answers = answers;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(!isAnswered(e)){
			if(e.getKeyChar() == '1'){
				if(answers.get(0).isRight()){
					//doSomething_____________________________________________________________________
				}
				board.continueAfterQuestEvent();
			}
			else if(e.getKeyChar() == '2'){
				if(answers.get(1).isRight()){
					//doSomething_____________________________________________________________________
				}
				board.continueAfterQuestEvent();
			}
			else if(e.getKeyChar() == '3'){
				if(answers.get(2).isRight()){
					//doSomething_____________________________________________________________________
				}
				board.continueAfterQuestEvent();
			}
			else if(e.getKeyChar() == '4'){
				if(answers.get(3).isRight()){
					//doSomething_____________________________________________________________________
				}
				board.continueAfterQuestEvent();
			}
		}
	}
	
	public void setReady(){
		getAnswer = false;
	}
	
	private synchronized boolean isAnswered(KeyEvent e){
		if(e.getKeyChar() == '1'|| e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4'){
			if(!getAnswer){
				getAnswer = true;
				return false;
			}
		}
		return false;
	}

}
