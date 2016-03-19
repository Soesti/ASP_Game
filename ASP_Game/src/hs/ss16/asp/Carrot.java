package hs.ss16.asp;

import javax.swing.ImageIcon;

public class Carrot extends Obstacle {

	public Carrot(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		
		ImageIcon ii = new ImageIcon("img/carrot.png");
		this.image = ii.getImage();
		
		width = 27;
		height = 75;
	}
}
