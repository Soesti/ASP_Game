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

	int lengthUp = 20;
	int lengthDown = 20;
	int width = 20;

	public Board() {

		initUI();
		
		collectedCarrots = 0;

		run = true;
		sprites = new ArrayList<Sprite>();

		player = new Player(500, 700);
		this.addKeyListener(new KeyListenerPlayer(player));

		timer = new Timer(player, sprites, this);
		timer.start();

		colisionThread = new CollisionThread(this);
		colisionThread.start();

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
		rock.setSpeed(3);

		sprites.add(rock);
	}

	public boolean checkCollisions() {
		for (int i = 0; i < sprites.size(); i++) {
			if ((player.getXPosition() <= ((sprites.get(i)).getXPosition() + width) && player.getXPosition()+ width >= sprites.get(i).getXPosition())
					|| ((player.getXPosition() + width) >= (sprites.get(i)).getXPosition()) && player.getXPosition() <= sprites.get(i).getXPosition()+width) {
				if ((player.getYPosition() - lengthUp) <= (sprites.get(i).getYPosition() + lengthDown) && (player.getYPosition() + lengthDown > sprites.get(i).getYPosition()-lengthUp)) {
					run = false;
					
					//Stop gameloop
					timer.endLoop();
					return false;
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
		carrots.setIcon(new ImageIcon("img/PixelCarrot.png"));
		carrots.setBounds(960, 11, 30, 30);
		add(carrots);

		JLabel collectedCarrots = new JLabel("0");
		collectedCarrots.setBounds(927, 11, 30, 30);
		add(collectedCarrots);

		JLabel life1 = new JLabel("");
		life1.setIcon(new ImageIcon("img/pixelheart.png"));
		life1.setBounds(10, 11, 30, 30);
		add(life1);

		JLabel life2 = new JLabel("");
		life2.setIcon(new ImageIcon("img/pixelheart.png"));
		life2.setBounds(50, 11, 30, 30);
		add(life2);

		JLabel life3 = new JLabel("");
		life3.setIcon(new ImageIcon("img/pixelheart.png"));
		life3.setBounds(90, 11, 30, 30);
		add(life3);
	}
}
