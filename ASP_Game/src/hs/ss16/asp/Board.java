package hs.ss16.asp;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	private final int B_WIDTH = 1000;
	private final int B_HEIGHT = World.screenSize.height - 50;
	private final int INITIAL_X = -40;
	private final int INITIAL_Y = -40;
	private final int DELAY = 25;
	
	private int collectedCarrots;

	private Player player;
	private Background background;
	private KeyListenerPlayer keyListenerPlayer;
	private ArrayList<Sprite> sprites;
	
	private Timer timer;
	private CollisionThread colisionThread;
	private QuestionTimer questionTimer;

	boolean run;
	
	int width = 20;
	int obstracleSpeed = 3;
	
	JLabel life1, life2, life3, collectedCarrotsLabel;
	JButton newGameButton;
	JLabel[] lives;
	QuestionGUI questPanel;

	public Board() {

		initUI();
		
		collectedCarrots = 0;

		run = true;
		sprites = new ArrayList<Sprite>();

		player = new Player(500, World.screenSize.height - 170);
		background = new Background(B_HEIGHT);
		background.setSpeed(obstracleSpeed);
		keyListenerPlayer = new KeyListenerPlayer(player);
		this.addKeyListener(keyListenerPlayer);

		timer = new Timer(player, sprites, background, this);
		timer.start();

		colisionThread = new CollisionThread(this);
		colisionThread.start();
		
		questionTimer = new QuestionTimer(this);
		questionTimer.start();
		
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
		g2d.drawImage(background.getImage(), background.getXPosition(), background.getYPosition(), this);
		g2d.drawImage(player.getImage(), player.getXPosition(), player.getYPosition(), this);

		if(!sprites.isEmpty()){
			for (Object sprite : sprites) {
				g2d.drawImage(((Sprite) sprite).getImage(), ((Sprite) sprite).getXPosition(),
						((Sprite) sprite).getYPosition(), this);
			}
		}
		
		collectedCarrotsLabel.setText("" + collectedCarrots);
	}

	public void createObstacle() {
		int randomPosition = (int) (Math.random() * 700);
		int randomObstacle = (int) (Math.random() * 2)+1;
		
		System.out.println(randomObstacle);
		
		if(randomObstacle == 1){
			Rock rock = new Rock(randomPosition, 0);
			rock.setSpeed(obstracleSpeed);

			sprites.add(rock);
		}
		if(randomObstacle == 2){
			Carrot carrot = new Carrot(randomPosition, 0);
			carrot.setSpeed(obstracleSpeed);
			
			sprites.add(carrot);
		}
	}

	public boolean checkCollisions() {
		for (int i = 0; i < sprites.size(); i++) {
			if ((player.getXPosition() <= ((sprites.get(i)).getXPosition() + sprites.get(i).getWidth()) && player.getXPosition()+ player.getWidth() >= sprites.get(i).getXPosition())
					|| ((player.getXPosition() + player.width) >= (sprites.get(i)).getXPosition()) && player.getXPosition() <= sprites.get(i).getXPosition()+ sprites.get(i).getWidth()) {
				if ((player.getYPosition()) <= (sprites.get(i).getYPosition() + sprites.get(i).getHeight()) && (player.getYPosition() + player.getHeight() > sprites.get(i).getYPosition())) {
					
					if(sprites.get(i).getClass() == Rock.class){
					
						if(player.getCurrentLives() == 1){
							player.decrementLive();
							lives[player.getCurrentLives()].setIcon(new ImageIcon("img/life_empty.png"));
							//Stop gameloop
							timer.endLoop();
							
							newGameButton = new JButton("Neues Spiel");
							newGameButton.setBounds(425, 400, 150, 40);
							newGameButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									newGame();
								}
							});
							this.add(newGameButton);
							
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
						sprites.remove(i);
						i--;
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
		collectedCarrotsLabel.setFont(new Font(collectedCarrotsLabel.getFont().getName(), Font.PLAIN, 40));
		collectedCarrotsLabel.setBounds(907, 30, 100, 30);
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
		
		questPanel = new QuestionGUI(this);
	}
	
	public void newGame(){
		collectedCarrots = 0;
		this.remove(newGameButton);
		
		life1.setIcon(new ImageIcon("img/life_full.png"));
		life2.setIcon(new ImageIcon("img/life_full.png"));
		life3.setIcon(new ImageIcon("img/life_full.png"));
		
		run = true;
		sprites = new ArrayList<Sprite>();

		this.removeKeyListener(keyListenerPlayer);
		player = new Player(500, 660);
		keyListenerPlayer = new KeyListenerPlayer(player);
		this.addKeyListener(keyListenerPlayer);

		timer = new Timer(player, sprites, background, this);
		timer.start();

		colisionThread = new CollisionThread(this);
		colisionThread.start();
	}
	
	public void pauseGame() {
		timer.endLoop();
	}
	
	public void continueGame() {
		timer = new Timer(player, sprites, background, this);
		timer.start();
	}
	
	public void doQuestionEvent() {
		pauseGame();
		
		questPanel.askQuestion();
		this.add(questPanel);
		
		
		
		//wait for the answer of the gamer___________________________not implemented yet__________
		for(int i = 0; i<1000; i++) {
			for(int i2 = 0; i2<200; i2++) {
					System.out.println(",fsaoafoiö");
			}
		}
		//________________________________________________________________________________________
		
		
		
		this.remove(questPanel);
		
		
		continueGame();
	}
}
