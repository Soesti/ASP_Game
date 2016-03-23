package hs.ss16.asp;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class World extends JFrame {
	 
	/**
	 * b
	 */
	private static final long serialVersionUID = 1L;
	// Links, Rechts, Oben, Unten
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
	private Board board;

	public World(){		
		initUI();	
	}
	
	private void initUI(){
		board = new Board(this);
		add(board);
		setSize(1000, screenSize.height - 50);
		setResizable(false);
		setTitle("BunnyWars");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		InputStream resource1 = Rock.class.getResourceAsStream("/img/player_right.png");
		Image img;
		try {
			img = ImageIO.read(resource1);
			setIconImage(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createNewBoard(){
		this.remove(board);
		board = new Board(this);
		add(board);
		board.requestFocus();
	}
	

	public static void main(String[] args){
		World world = new World();
		
	}
}
