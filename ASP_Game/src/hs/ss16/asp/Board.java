package hs.ss16.asp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Board extends JPanel {
	
	private final int B_WIDTH = 1000;
    private final int B_HEIGHT = 800;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;
    
    Player player;
    ArrayList<Sprite> sprites;
    
    boolean run;
    
    int lengthUp = 2;
    int lengthDown = 2;
    int width = 2;
    
    public Board(){
    	 run = true;
    	 sprites = new ArrayList<Sprite>();
    	 
    	 player = new Player(500,700);
    	 this.addKeyListener(new KeyListenerPlayer(player));
    	 
    	 Timer timer  = new Timer(player, sprites, this);
    	 timer.start();
    	 
    	 CollisionThread colision = new CollisionThread(this);
    	 colision.start();
    	 
    	 setFocusable(true);
    	 setVisible(true);
    }
   
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }
    
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(player.getImage(), player.getXPosition(),
                player.getYPosition(), this);

        for (Object sprite : sprites) {
            g2d.drawImage(((Sprite) sprite).getImage(), ((Sprite)sprite).getXPosition(),
            		((Sprite)sprite).getYPosition(), this);
        }
    }
    
    public void createObstacle(){
    	int random  = (int)(Math.random()* 700);
    	System.out.println(random);
    	Rock rock = new Rock(random,0);
    	rock.setSpeed(3);
    	
    	sprites.add(rock);
    }

    
    public boolean checkCollisions() {
    	for(int i = 0; i < sprites.size(); i++) {
    		if(player.getXPosition() <= ((sprites.get(i)).getXPosition() + width) || (player.getXPosition() + width) >= (sprites.get(i)).getXPosition()) {
    			if((player.getYPosition() + lengthUp) >= (sprites.get(i).getYPosition() - width)) {
    				run = false;
    				return false;
    			}
    		}
    	}
    	return true;
    }
}
