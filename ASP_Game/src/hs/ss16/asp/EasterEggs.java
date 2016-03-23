package hs.ss16.asp;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class EasterEggs extends Obstacle {

	public EasterEggs(int xPosition, int yPosition) {
		super(xPosition, yPosition);

		try {
			InputStream resource = EasterEggs.class.getResourceAsStream("/img/carrot.png");
			this.image = ImageIO.read(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		width = 50;
		height = 50;
		
	}

}
