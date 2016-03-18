package hs.ss16.asp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerPlayer implements KeyListener {
	
	Player player;
	
	public KeyListenerPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT){
			player.setDirection(Direction.Left);
		}
		else if(e.getKeyChar() == 'd' ||e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.setDirection(Direction.Right);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT){
			if(player.getDirection() != Direction.Right){
				player.setDirection(Direction.Top);
			}
		}
		else if(e.getKeyChar() == 'd' ||e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(player.getDirection() != Direction.Left){
				player.setDirection(Direction.Top);
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
