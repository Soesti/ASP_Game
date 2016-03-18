package hs.ss16.asp;

import javax.swing.ImageIcon;

public class Rock extends Obstacle {

	public Rock(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		
		ImageIcon ii = new ImageIcon("img/rock.png");
		this.image = ii.getImage();
	}
}
