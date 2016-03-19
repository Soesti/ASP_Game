package hs.ss16.asp;

import javax.swing.JFrame;

public class World extends JFrame {
	 
	/**
	 * b
	 */
	private static final long serialVersionUID = 1L;

	public World(){		
		initUI();	
	}
	
	private void initUI(){
		
		add(new Board());
		setSize(1000, 800);
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
