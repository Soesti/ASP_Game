package hs.ss16.asp;

import java.awt.Color;
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
	private volatile ArrayList<Sprite> sprites;
	
	private Timer timer;
	private CollisionThread colisionThread;
	private stopwatchThread stopwatch;
	private QuestionTimer questionTimer;
	
	private int[] difficultArray = {0, 15, 30, 45, 60 , 75 };
	private int currentDifficult = 0;
	
	Board board = this;

	boolean run;
	
	boolean questionActive = false;
	
	int width = 20;
	int obstracleSpeed = 5;
	
	JPanel panel;
	JLabel life1, life2, life3, timeLeft;
	JButton newGameButton;
	JLabel[] lives;
	QuestionGUI questPanel;
	HighscorePanel highscore;
	
	private int numberOfSeconds;
	private int numberOfLifeSeconds;
	
	private int carrotOnScreen = 0;
	private int rockNumber = 0;
	 
	private World world;

	public Board(World world) {
		//
		this.world = world;
		try {
			initUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		numberOfSeconds = 0;
		numberOfLifeSeconds = 10;

		run = true;
		sprites = new ArrayList<Sprite>();

		
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
		
		panel = new JPanel();
		panel.setBackground(new Color(169, 169, 169));
		panel.setBounds(0, 0, 1000, 96);
		add(panel);
		panel.setLayout(null);
		
		timeLeft = new JLabel("0");
		timeLeft.setBounds(944, 37, 46, 22);
		panel.add(timeLeft);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {
		
		timeLeft.setText(timeDisplay(numberOfLifeSeconds));
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background.getImage(), background.getXPosition(), background.getYPosition(), this);
		g2d.drawImage(player.getImage(), player.getXPosition(), player.getYPosition(), this);

		if(!sprites.isEmpty()){
			int size = sprites.size();
			for (int i = 0; i < size; i++) {
				Sprite sprite = sprites.get(i);
				g2d.drawImage( sprite.getImage(), sprite.getXPosition(),
						 sprite.getYPosition(), this);
				if(size != sprites.size()){
					size = sprites.size();
					i--;
				}
			}
		}
		
		if(questionActive){
			try {
				InputStream resource = Carrot.class.getResourceAsStream("/img/question_border.png");
				Image image = ImageIO.read(resource);
				g.drawImage(image, 181, ((int)World.screenSize.getHeight()/2)-291, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void createObstacle() {
		int randomPosition = (int) (Math.random() * 700);
		int randomObstacle = (int) (Math.random() * 30)+1;
		
		if(carrotOnScreen > 0){
			Rock rock = new Rock(randomPosition, 0);
			rock.setSpeed(obstracleSpeed);

			sprites.add(rock);
			carrotOnScreen--;
		}
		else if(rockNumber == 4){
			Carrot carrot = new Carrot(randomPosition, 0);
			carrot.setSpeed(obstracleSpeed);
			
			sprites.add(carrot);
			carrotOnScreen = 2;
		}
		else if(randomObstacle <= 14){
			Rock rock = new Rock(randomPosition, 0);
			rock.setSpeed(obstracleSpeed);

			sprites.add(rock);
		}
		else if(randomObstacle <= 28){
			Carrot carrot = new Carrot(randomPosition, 0);
			carrot.setSpeed(obstracleSpeed);
			
			sprites.add(carrot);
			carrotOnScreen = 2;
		}
		/*else{
			EasterEggs egg = new EasterEggs(randomPosition, 0);
			egg.setSpeed(obstracleSpeed);
			
			sprites.add(egg);
		}*/
	}

	public boolean checkCollisions() {
		for (int i = 0; i < sprites.size(); i++) {
			if ((player.getXPosition() <= ((sprites.get(i)).getXPosition() + sprites.get(i).getWidth()) && player.getXPosition()+ player.getWidth() >= sprites.get(i).getXPosition())
					|| ((player.getXPosition() + player.width) >= (sprites.get(i)).getXPosition()) && player.getXPosition() <= sprites.get(i).getXPosition()+ sprites.get(i).getWidth()) {
				if ((player.getYPosition()) <= (sprites.get(i).getYPosition() + sprites.get(i).getHeight()) && (player.getYPosition() + player.getHeight() > sprites.get(i).getYPosition())) {
					
					if(sprites.get(i).getClass() == Rock.class){

						if(player.getCurrentLives() == 1){
							player.decrementLive();
							InputStream resource = Rock.class.getResourceAsStream("/img/life_empty.png");
							Image imageVari;
							try {
								imageVari = ImageIO.read(resource);
								ImageIcon ii = new ImageIcon(imageVari);
								lives[player.getCurrentLives()].setIcon(ii);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							
							//Stop gameloop
							timer.endLoop();
							questionTimer.stopQuestions();
							
							highscore.activateHighscorePanelAfterGame(numberOfSeconds);
							highscore.setFocusable(true);
							highscore.requestFocus();
							this.repaint();
							
							return false;
						}
						else{
							player.decrementLive();
							InputStream resource = Rock.class.getResourceAsStream("/img/life_empty.png");
							Image imageVari;
							try {
								imageVari = ImageIO.read(resource);
								ImageIcon ii = new ImageIcon(imageVari);
								lives[player.getCurrentLives()].setIcon(ii);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							sprites.remove(i);
							i--;
							return true;
						}
					}
					else if(sprites.get(i).getClass() == Carrot.class){
						collectedCarrots++;
						//Erh�hung der Lebenszeit
						int time = (int)(((((World.screenSize.getHeight() / board.obstracleSpeed)/3)*4)/100)+0.5);
						increaseNumberOfLifeSeconds(time);
						sprites.remove(i);
						i--;
					}
					
					else if(sprites.get(i).getClass() == EasterEggs.class){
						
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
		
		//Create Player with KeyListener
		player = new Player(500, World.screenSize.height - 220);
		keyListenerPlayer = new KeyListenerPlayer(player);
		this.addKeyListener(keyListenerPlayer);
		
		//Create background
		background = new Background(B_HEIGHT);
		background.setSpeed(obstracleSpeed);
		
		//Create Highscore panel
		highscore = new HighscorePanel(board);
		board.add(highscore);
		
		JLabel carrots = new JLabel("");
		InputStream resource = Rock.class.getResourceAsStream("/img/carrot.png");
		Image imageVari = ImageIO.read(resource);
		ImageIcon ii = new ImageIcon(imageVari);
				
		life1 = new JLabel("");
		resource = Rock.class.getResourceAsStream("/img/life_full.png");
		imageVari = ImageIO.read(resource);
		ii = new ImageIcon(imageVari);
//		ii.setImage(ii.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
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
		
		questPanel = new QuestionGUI();
	}
	
	public void newGame(){		
		world.createNewBoard();
	}
	
	private void pauseGame() {
		stopwatch.toggleBool();
		timer.endLoop();
	}
	
	private void continueGame() {
		player.setDirection(Direction.Top);
		
		questionActive = false;
		
		JLabel wait = new JLabel("3");
		
		board.repaint();
		wait.setBounds(460, World.screenSize.height/2 - 40, 100,80);
		wait.setFont(new Font(wait.getName(), Font.PLAIN, 50));
		this.add(wait);
		
		new Thread(){
			 public void run() {
				try {
					sleep(500);
					wait.setText("2");
					board.repaint();
					sleep(500);
					wait.setText("1");
					board.repaint();
					sleep(500);
					wait.setText("GO!");
					board.repaint();
					sleep(500);
					
					board.remove(wait);
					
					timer = new Timer(player, sprites, background, board);
					timer.start();
					
					stopwatch = new stopwatchThread(board);
					stopwatch.start();
					
					questionTimer = new QuestionTimer(board);
					questionTimer.start();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    }
		}.start();
	}
	
	public void continueAfterQuestEvent() {
		this.remove(questPanel);
		continueGame();
	}
	
	public void doQuestionEvent() {
		pauseGame();
		
		questionActive = true;
		
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

			stopAllThreads();
			
			highscore.activateHighscorePanelAfterGame(numberOfSeconds);
			highscore.setFocusable(true);
			highscore.requestFocus();
			
		}
	}
	
	public void stopAllThreads() {
		questionTimer.stopQuestions();
		timer.endLoop();
		stopwatch.toggleBool();
	}
	
	public void setDifficult(){
		if(currentDifficult < difficultArray.length-1){
			if(numberOfSeconds > difficultArray[currentDifficult+1]){
				
				obstracleSpeed = obstracleSpeed + 1;
				System.out.println("Raise speed to: " + obstracleSpeed);
				background.setSpeed(obstracleSpeed);
				for(int i = 0; i < sprites.size(); i++){
					sprites.get(i).setSpeed(obstracleSpeed);
				}
				player.setSpeed(player.getSpeed() + 1);
				
				currentDifficult++;
			}
		}
	}
	public String timeDisplay(int seconds){
		String temp = "" ;
		if(seconds%60 == 0){
			temp = "00" ;
		} else if(seconds%60 < 10){
			temp = "0" + seconds%60 ;
		} else {
			temp = "" + seconds%60;
		}
		
		return "0" + (int)seconds/60 + " : " + temp;
	}
}