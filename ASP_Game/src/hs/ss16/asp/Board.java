package hs.ss16.asp;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private stopwatchThread stopwatch;
	private QuestionTimer questionTimer;

	boolean run;
	
	int width = 20;
	int obstracleSpeed = 3;
	
	JLabel life1, life2, life3, collectedCarrotsLabel, timeLeft;
	JButton newGameButton;
	JLabel[] lives;
	QuestionGUI questPanel;
	
	int numberOfSeconds;
	int numberOfLifeSeconds;

	public Board() {

		try {
			initUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		collectedCarrots = 0;
		numberOfSeconds = 0;
		numberOfLifeSeconds = 10;

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
		
		stopwatch = new stopwatchThread(this);
		stopwatch.start();
		
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
		
		timeLeft.setText(Integer.toString(numberOfLifeSeconds));
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
							questionTimer.stopQuestions();
							
							newGameButton = new JButton("Neues Spiel");
							newGameButton.setBounds(425, 400, 150, 40);
							newGameButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										newGame();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
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

	private void initUI() throws IOException {
		setSize(1000, 800);
		setFocusable(true);
		setVisible(true);
		setLayout(null);
		
		JLabel carrots = new JLabel("");
		InputStream resource = Rock.class.getResourceAsStream("/img/carrot.png");
		Image imageVari = ImageIO.read(resource);
		ImageIcon ii = new ImageIcon(imageVari);
		carrots.setIcon(ii);
		carrots.setBounds(960, 11, 27, 75);
		add(carrots);

		collectedCarrotsLabel = new JLabel("0");
		collectedCarrotsLabel.setFont(new Font(collectedCarrotsLabel.getFont().getName(), Font.PLAIN, 40));
		collectedCarrotsLabel.setBounds(907, 30, 100, 30);
		add(collectedCarrotsLabel);
		
		life1 = new JLabel("");
		resource = Rock.class.getResourceAsStream("/img/life_full.png");
		imageVari = ImageIO.read(resource);
		ii = new ImageIcon(imageVari);
		life1.setIcon(ii);
		life1.setBounds(10, 11, 75, 65);
		add(life1);

		life2 = new JLabel("");
		life2.setIcon(ii);
		life2.setBounds(95, 11, 75, 65);
		add(life2);

		life3 = new JLabel("");
		life3.setIcon(ii);
		life3.setBounds(180, 11, 75, 65);
		add(life3);
		
		timeLeft = new JLabel("0");
		timeLeft.setBounds(496, 38, 46, 22);
		add(timeLeft);
		
		questPanel = new QuestionGUI();
	}
	
	public void newGame() throws IOException{
		collectedCarrots = 0;
		numberOfSeconds = 0;
		numberOfLifeSeconds = 10;
		
		this.remove(newGameButton);
		
		InputStream resource1 = Rock.class.getResourceAsStream("/img/life_full.png");
		Image imageVari = ImageIO.read(resource1);
		ImageIcon ii = new ImageIcon(imageVari);
		
		life1.setIcon(ii);
		life2.setIcon(ii);
		life3.setIcon(ii);
		
		run = true;
		sprites = new ArrayList<Sprite>();

		this.removeKeyListener(keyListenerPlayer);
		player = new Player(500,  World.screenSize.height - 170);
		keyListenerPlayer = new KeyListenerPlayer(player);
		this.addKeyListener(keyListenerPlayer);

		timer = new Timer(player, sprites, background, this);
		timer.start();

		colisionThread = new CollisionThread(this);
		colisionThread.start();
		
		stopwatch = new stopwatchThread(this);
		stopwatch.start();
		
		questionTimer = new QuestionTimer(this);
		questionTimer.start();
	}
	
	private void pauseGame() {
		stopwatch.toggleBool();
		timer.endLoop();
	}
	
	private void continueGame() {
		timer = new Timer(player, sprites, background, this);
		timer.start();
		
		stopwatch.toggleBool();
		
		questionTimer = new QuestionTimer(this);
		questionTimer.start();
		
		player.setDirection(Direction.Top);
	}
	
	public void continueAfterQuestEvent() {
		this.remove(questPanel);
		continueGame();
	}
	
	public void doQuestionEvent() {
		pauseGame();
		
		questPanel.askQuestion(this);
		this.add(questPanel);
		
		//Show questions
		questPanel.revalidate();
		questPanel.repaint();
		
		//Set Fokus to Question GUI. Without Fokus the keylistener doesn't work
		questPanel.setFocusable(true);
		questPanel.requestFocus();
	}
	
	public void increaseNumberOfSeconds() {
		numberOfSeconds++;
	}
	
	public void increaseNumberOfLifeSeconds (int seconds) {
		numberOfLifeSeconds = numberOfLifeSeconds + seconds;
	}
	
	public void decreaseNumberOfLifeSeconds (int seconds) {
		numberOfLifeSeconds = numberOfLifeSeconds - seconds;
	}
	public void checkOutOfTime () {
		if(numberOfLifeSeconds <= 0) {
			
			pauseGame();
			stopAllThreads();
		}
	}
	
	public void stopAllThreads() {
		questionTimer.stopQuestions();
		timer.endLoop();
		stopwatch.toggleBool();
	}
}
