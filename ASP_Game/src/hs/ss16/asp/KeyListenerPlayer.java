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
		if(e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT){
			play.setDirection(Direction.Left);
		}
		else if(e.getKeyChar() == 'd' ||e.getKeyCode() == KeyEvent.VK_RIGHT){
			play.setDirection(Direction.Right);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT){
			if(play.getDirection() != Direction.Right){
				play.setDirection(Direction.Top);
			}
		}
		else if(e.getKeyChar() == 'd' ||e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(play.getDirection() != Direction.Left){
				play.setDirection(Direction.Top);
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
