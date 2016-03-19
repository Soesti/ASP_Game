package hs.ss16.asp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	private final int B_WIDTH = 1000;
	private final int B_HEIGHT = 800;
	private final int INITIAL_X = -40;
	private final int INITIAL_Y = -40;
	private final int DELAY = 25;
	
	private int collectedCarrots;

	private Player player;
	private ArrayList<Sprite> sprites;
	
	private Timer timer;
	private CollisionThread colisionThread;

	boolean run;
	
	int width = 20;
	int obstracleSpeed = 3;
	
	JLabel life1, life2, life3, collectedCarrotsLabel;
	JLabel[] lives;

	public Board() {

		initUI();
		
		collectedCarrots = 0;

		run = true;
		sprites = new ArrayList<Sprite>();

		player = new Player(500, 660);
		this.addKeyListener(new KeyListenerPlayer(player));

		timer = new Timer(player, sprites, this);
		timer.start();

		colisionThread = new CollisionThread(this);
		colisionThread.start();
		
		lives = new JLabel[3];
		lives[0] = life1;
		lives[1] = life2;
		lives[2] = life3;

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(player.getImage(), player.getXPosition(), player.getYPosition(), this);

		for (Object sprite : sprites) {
			g2d.drawImage(((Sprite) sprite).getImage(), ((Sprite) sprite).getXPosition(),
					((Sprite) sprite).getYPosition(), this);
		}
	}

	public void createObstacle() {
		int random = (int) (Math.random() * 700);
		System.out.println(random);
		Rock rock = new Rock(random, 0);
		rock.setSpeed(obstracleSpeed);

		sprites.add(rock);
	}

	public boolean checkCollisions() {
		for (int i = 0; i < sprites.size(); i++) {
			if ((player.getXPosition() <= ((sprites.get(i)).getXPosition() + sprites.get(i).getWidth()) && player.getXPosition()+ player.getWidth() >= sprites.get(i).getXPosition())
					|| ((player.getXPosition() + player.width) >= (sprites.get(i)).getXPosition()) && player.getXPosition() <= sprites.get(i).getXPosition()+ sprites.get(i).getWidth()) {
				if ((player.getYPosition()) <= (sprites.get(i).getYPosition() + sprites.get(i).getHeight()) && (player.getYPosition() + player.getHeight() > sprites.get(i).getYPosition())) {
					
					if(sprites.get(i).getClass() == Rock.class){
					
						if(player.getCurrentLives() == 1){
							//Stop gameloop
							timer.endLoop();
							sprites.remove(i);
							i--;
							return false;
						}
						else{
							player.decrementLive();
							lives[player.getCurrentLives()].setIcon(new ImageIcon("img/life_empty.png"));
							sprites.remove(i);
							i--;
							return true;
						}
					}
					if(sprites.get(i).getClass() == Carrot.class){
						collectedCarrots++;
					}
				}
			}
		}
		return true;
	}

	private void initUI() {
		setSize(1000, 800);
		setFocusable(true);
		setVisible(true);
		setLayout(null);
		
		JLabel carrots = new JLabel("");
		carrots.setIcon(new ImageIcon("img/carrot.png"));
		carrots.setBounds(960, 11, 27, 75);
		add(carrots);

		collectedCarrotsLabel = new JLabel("0");
		collectedCarrotsLabel.setBounds(927, 11, 30, 30);
		add(collectedCarrotsLabel);

		life1 = new JLabel("");
		life1.setIcon(new ImageIcon("img/life_full.png"));
		life1.setBounds(10, 11, 75, 65);
		add(life1);

		life2 = new JLabel("");
		life2.setIcon(new ImageIcon("img/life_full.png"));
		life2.setBounds(95, 11, 75, 65);
		add(life2);

		life3 = new JLabel("");
		life3.setIcon(new ImageIcon("img/life_full.png"));
		life3.setBounds(180, 11, 75, 65);
		add(life3);
	}
}
