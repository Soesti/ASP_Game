package hs.ss16.asp;

import java.awt.Image;

public abstract class Sprite {
	
	protected int xPosition;
    protected int yPosition;
    protected boolean visible;
    protected Image image;
    protected int speed = 0;
    protected int width;
    protected int height;

    
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
    
    public void setSpeed(int speed){
    	this.speed = speed;
    }
    
    public int getSpeed(){
    	return speed;
    }
    
    public int getHeight(){
    	return height;
    }
    
    public int getWidth(){
    	return width;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public abstract void calculatePosition();
}
