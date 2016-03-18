package hs.ss16.asp;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends Sprite {

	public Player(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		ImageIcon ii = new ImageIcon("img/player.png");
		image = ii.getImage();
	}

}
