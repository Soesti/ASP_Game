package hs.ss16.asp;

import java.awt.Image;

public abstract class Sprite {
	
	protected int xPosition;
    protected int yPosition;
    protected boolean visible;
    protected Image image;

    
    public Sprite(int xPosition, int yPosition) {

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        visible = true;
    }
    
    public Image getImage() {
        return image;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
