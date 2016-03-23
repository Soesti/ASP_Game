package hs.ss16.asp;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Rock extends Obstacle {

	public Rock(int xPosition, int yPosition) {
		super(xPosition, yPosition);
	
		try {
			InputStream resource = Rock.class.getResourceAsStream("/img/rock.png");
			this.image = ImageIO.read(resource);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		height = 96;
		width = 156;
	}
}
