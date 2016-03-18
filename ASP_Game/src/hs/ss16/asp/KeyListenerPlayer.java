package hs.ss16.asp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerPlayer implements KeyListener {
	
	Player play;
	
	public KeyListenerPlayer(Player play) {
		this.play = play;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'a' || e.getKeyChar() == KeyEvent.VK_RIGHT){
			play.setDirection(Direction.Left);
		}
		else if(e.getKeyChar() == 'd'){
			play.setDirection(Direction.Right);
		}
		else{
			play.setDirection(Direction.Top);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		play.setDirection(Direction.Top);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
