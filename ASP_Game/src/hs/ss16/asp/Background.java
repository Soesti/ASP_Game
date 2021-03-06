package hs.ss16.asp;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


/**
* Created by Patrick on 21.03.2016.
*/
public class Background extends Sprite {

    public Background(int boardheight) {

        super(0, -boardheight + 50);

        InputStream resource1 = Rock.class.getResourceAsStream("/img/background.png");
        Image imageVari;
		try {
			imageVari = ImageIO.read(resource1);
			ImageIcon ii = new ImageIcon(imageVari);
				
		    ii.setImage(ii.getImage().getScaledInstance(1000, boardheight * 2, Image.SCALE_DEFAULT));
		    this.image = ii.getImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        height = boardheight*2;
        width = 1000;
    }

    @Override
    public void calculatePosition() {

        if(this.yPosition >= -125){
            this.yPosition = -(height /2);
        }

        this.yPosition += speed;
    }

}