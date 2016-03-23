package hs.ss16.asp;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Carrot extends Obstacle {

	public Carrot(int xPosition, int yPosition) {
		super(xPosition, yPosition);
		
		
		try {
			InputStream resource = Rock.class.getResourceAsStream("/img/carrot.png");
			this.image = ImageIO.read(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		width = 27;
		height = 75;
	}
}
