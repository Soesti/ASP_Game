package hs.ss16.asp;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HighscorePanel extends JPanel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int G_Width = 600;
	private static final int G_Height = 300;
	
	private static final int MAX_HIGHSCORE_NAME_LENGTH = 10;
	private static final int SPACE_UNTIL_TIMESCORE_DISPLAYED = MAX_HIGHSCORE_NAME_LENGTH + 5;
	private static final int NUMBER_OF_SAVED_SCORES = 5;
	
	private static final String HIGHSCORE_FILENAME = "BunnyWars-Highscores.txt";
	
	private Board board;
	
	private JLabel headline;
	private JLabel scoreLines[];
	private JButton newGameButton;
	
	private String scoreNames[];
	private int scoreTimes[];
	
	private int newestHighscoreIndex;
	private boolean nameEntryActive;
	
	private KeyListenerHighscore keyListenerHighscore;
	
	
	
	public HighscorePanel(Board board) {
		
		this.board = board;
		nameEntryActive = false;
		
		setBounds(200, ((int)World.screenSize.getHeight()/2)-148,G_Width, G_Height);
		setLayout(new GridLayout(7,1));
		
		loadScores();
		
		headline = new JLabel("Highscore", SwingConstants.CENTER);
		add(headline);
		
		for(int i = 0; i < scoreLines.length; i++) {
			add(scoreLines[i]);
		}
		setVisible(false);
		this.setOpaque( false ) ;
	}
	
	
	private void loadScores() {
		String filePath = ClassLoader.getSystemClassLoader().getResource(".").getPath();
		File highscoreFile = new File(filePath + "\\" + HIGHSCORE_FILENAME);
		
		//generate Highscorefile if it doesn't already exist
		if((highscoreFile.exists() && !highscoreFile.isDirectory()) == false) { 
			generateDefaultHighscoreTextFile();
		}
		
		
		JLabel loadedScores[] = new JLabel[NUMBER_OF_SAVED_SCORES];
		scoreNames = new String[NUMBER_OF_SAVED_SCORES];
		scoreTimes = new int[NUMBER_OF_SAVED_SCORES];
		
		String line;
		
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(highscoreFile);
			bufferedReader = new BufferedReader(fileReader);
			
			for(int i = 0; i < NUMBER_OF_SAVED_SCORES; i++) {
				line = bufferedReader.readLine();
				loadScoreValuesFromLine(line, i);
				loadedScores[i] = new JLabel(convertToLabelString(scoreNames[i], scoreTimes[i]), SwingConstants.CENTER);
			}
		} catch(Exception e) {
			System.out.println("Error while reading highscore file line by line:" + e.getMessage()); 
		} finally {
            try {
            	bufferedReader.close();
            	fileReader.close();
            } catch (Exception e) {
            }
        }
		
		scoreLines = loadedScores;
	}
	
	
	private String convertToLabelString(String name, int score) {
		String space = "";
		
		for(int i = 0; i < (SPACE_UNTIL_TIMESCORE_DISPLAYED - name.length()); i++) {
			space += " ";
		}
		
		int minutes = score / 60;
		int seconds = score % 60;
		
		String labelString = name + space + minutes + ":";
		
		if(seconds < 10)
			labelString += "00";
		else
			labelString += seconds;
		
		return labelString;
	}
	
	
	private void loadScoreValuesFromLine(String line, int placementIndex) {
		String sequence = "";
		
		for(int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == ',') {
				scoreNames[placementIndex] = sequence;
				sequence = "";
				i += 2;	 //skip comma and space
			}
			
			sequence += line.charAt(i);
		}
		
		scoreTimes[placementIndex] = new Integer(sequence);
	}
		
	
	private boolean isNewHighscore(int score) {
		if(score > scoreTimes[NUMBER_OF_SAVED_SCORES - 1])
			return true;
		return false;
	}
	
	
	public void activateHighscorePanelAfterGame(int score) {
		nameEntryActive = true;
		
		setVisible(true);
		setFocusable(true);
		
		if(isNewHighscore(score))
			addNewHighscore(score);
		
		initializeNewGameButton();		
	}
	
	
	private void addNewHighscore(int score) {
		int newHighscoreIndex = 0;
		
		//find index of the score to be replaced
		while(score <= scoreTimes[newHighscoreIndex] && newHighscoreIndex < NUMBER_OF_SAVED_SCORES) {
			newHighscoreIndex++;
		}
		
		newestHighscoreIndex = newHighscoreIndex;
		insertScoreAtIndex(score, newHighscoreIndex);
		
		updateHighscoreLabels();
		
		keyListenerHighscore = new KeyListenerHighscore(this);
		addKeyListener(keyListenerHighscore);
	}
	
	
	private void insertScoreAtIndex(int score, int insertIndex) {
		int scoreIndex = scoreTimes.length - 1;
		
		
		while(scoreIndex >= insertIndex && scoreIndex > 0) {
			scoreTimes[scoreIndex] = scoreTimes[scoreIndex - 1];
			scoreNames[scoreIndex] = scoreNames[scoreIndex -1];
			
			scoreIndex--;
		}
		
		scoreTimes[insertIndex] = score;
		scoreNames[insertIndex] = "_";
	}
	

	private void initializeNewGameButton() {
		newGameButton = new JButton("Neues Spiel");
		newGameButton.setBounds(425, 400, 150, 40);
		newGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					closeHighscorePanelAndStartNewGame();
			}
		});
		
		add(newGameButton);
		revalidate();
		repaint();
	}
	
	
	private void updateHighscoreLabels() {
		for(int i = 0; i < NUMBER_OF_SAVED_SCORES; i++) {
			scoreLines[i].setText(convertToLabelString(scoreNames[i], scoreTimes[i]));
		}
		
		revalidate();
		repaint();
	}
	
	
	private void closeHighscorePanelAndStartNewGame() {
		if(nameEntryActive) {
			endNameEntry();
		}
		
		remove(newGameButton);
		
		setFocusable(false);
		setVisible(false);

		board.newGame();
	}
	
	
	public void typedLetter(String typedLetter) {
		String name = scoreNames[newestHighscoreIndex];
		
		//+1 because there is a '_' behind the name while KeyListenerHighscore is active
		if(name.length() < MAX_HIGHSCORE_NAME_LENGTH + 1) {
			name = name.substring(0, name.length() - 1)  + typedLetter + "_";
		}
		
		scoreNames[newestHighscoreIndex] = name;
		
		updateHighscoreLabels();
	}
	
	
	public void removeLastTypedLetter() {
		int nameLength = scoreNames[newestHighscoreIndex].length();
		
		if(nameLength > 1) {
			String newName = "";
			
			//-2 because there is a '_' behind the name while KeyListener is active
			newName = scoreNames[newestHighscoreIndex].substring(0, nameLength - 2);
			newName += "_";
			
			scoreNames[newestHighscoreIndex] = newName;
		}
		
		updateHighscoreLabels();
	}
	
	
	public void endNameEntry() {
		removeKeyListener(keyListenerHighscore);
		nameEntryActive = false;
		
		scoreNames[newestHighscoreIndex] 
				= scoreNames[newestHighscoreIndex].substring(0, scoreNames[newestHighscoreIndex].length() - 1);
		
		updateHighscoreLabels();
		writeHighscoresToFile();
	}
	
	
	private void generateDefaultHighscoreTextFile() {
		BufferedWriter writer = null;
		
		String filePath = ClassLoader.getSystemClassLoader().getResource(".").getPath();
		
        File highscoreFile = new File(filePath + "\\" + HIGHSCORE_FILENAME);
        
        try {
            writer = new BufferedWriter(new FileWriter(highscoreFile));
            
            int score = 15;
            
            for(int i = 0; i < NUMBER_OF_SAVED_SCORES; i++) {
            	writer.write("Noname, " + ((NUMBER_OF_SAVED_SCORES - i) * score));
            	writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}
	
	
	private void writeHighscoresToFile() {
		BufferedWriter writer = null;
		
		String filePath = ClassLoader.getSystemClassLoader().getResource(".").getPath();
		
        File highscoreFile = new File(filePath + "\\" + HIGHSCORE_FILENAME);
        
        try {
            writer = new BufferedWriter(new FileWriter(highscoreFile));
            
            for(int i = 0; i < NUMBER_OF_SAVED_SCORES; i++) {
            	writer.write(scoreNames[i] + ", " + scoreTimes[i]);
            	writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}
}
