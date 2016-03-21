package hs.ss16.asp;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class World extends JFrame {
	 
	/**
	 * b
	 */
	private static final long serialVersionUID = 1L;
	//private final Dimension screenSize;

	public World(){		
		initUI();	
	}
	
	private void initUI(){
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		add(new Board());
		setSize(1000, screenSize.height);
		setResizable(false);
		setTitle("BunnyWars");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

	public static void main(String[] args){
		World world = new World();
	}
}
