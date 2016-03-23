package hs.ss16.asp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerHighscore implements KeyListener {

	private HighscorePanel highscorePanel;
	
	
	public KeyListenerHighscore(HighscorePanel highscorePanel) {
		this.highscorePanel = highscorePanel;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		String pressedKey = "" + e.getKeyChar();
		
		if(pressedKey.matches("[a-zA-Z]")) {
			highscorePanel.typedLetter(pressedKey);
		}
		
		if(e.getKeyChar()=='\b') {  
	        highscorePanel.removeLastTypedLetter();
	    }
		
		if(e.getKeyChar()=='\n') {
		     highscorePanel.endNameEntry();
		}
		
		highscorePanel.revalidate();
		highscorePanel.repaint();
	}

	
}
