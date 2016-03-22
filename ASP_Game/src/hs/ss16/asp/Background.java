package hs.ss16.asp;

import javax.swing.ImageIcon;
import java.awt.*;


/**
 * Created by Patrick on 21.03.2016.
 */
public class Background extends Sprite {

    public Background(int boardheight) {

        super(0, -boardheight + 50);


        ImageIcon ii = new ImageIcon("img/background.png");
        ii.setImage(ii.getImage().getScaledInstance(1000, boardheight * 2, Image.SCALE_DEFAULT));
        this.image = ii.getImage();



        height = boardheight*2;
        width = 1000;
    }

    @Override
    public void calculatePosition() {

        if(this.yPosition >= -40){
            this.yPosition = -(height /2);
        }

        this.yPosition += speed;
    }

}
