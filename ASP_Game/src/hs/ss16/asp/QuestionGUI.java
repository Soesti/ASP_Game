package hs.ss16.asp;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class QuestionGUI extends JPanel {
	
	private static final long serialVersionUID = -4767294290332006077L;
	static final int G_Width = 600;
	static final int G_Height = 300;
	
	JLabel questionLabel, answer1Label, answer2Label, answer3Label, answer4Label;
	
	QuestionHandler questionHandler;
	KeyListenerQuestion keyListernerQuestion;
	Board board;
	
	public QuestionGUI(Board board){
		this.board = board;
		setBounds(200, ((int)World.screenSize.getHeight()/2)-150,G_Width, G_Height);
		setLayout(new GridLayout(5,1));
		
		questionHandler = new QuestionHandler();
		
		questionLabel = new JLabel("",SwingConstants.CENTER);
		questionLabel.setForeground(Color.white);
		answer1Label = new JLabel();
		answer1Label.setForeground(Color.white);
		answer2Label = new JLabel();
		answer2Label.setForeground(Color.white);
		answer3Label = new JLabel();
		answer3Label.setForeground(Color.white);
		answer4Label = new JLabel();
		answer4Label.setForeground(Color.white);
		
		this.add(questionLabel,0);
		this.add(answer1Label,1);
		this.add(answer2Label,2);
		this.add(answer3Label,3);
		this.add(answer4Label,4);
		
		this.setOpaque( false ) ;
	}
	
	public void initializeQuestionGUI(Question question) {
		questionLabel.setText(question.getQuestion());

		answer1Label.setText(" \t\t\t\t	1. " + question.getAnswers().get(0).getAnswerString());
		answer2Label.setText(" \t\t\t\t	2. " + question.getAnswers().get(1).getAnswerString());
		answer3Label.setText(" \t\t\t\t 3. " + question.getAnswers().get(2).getAnswerString());
		answer4Label.setText(" \t\t\t\t 4. " + question.getAnswers().get(3).getAnswerString());
		
		setVisible(true);
	}
	
	
	public void askQuestion(Board board) {
		Question randomQuestion = questionHandler.getRandomQuestion();
		
		initializeQuestionGUI(randomQuestion);
		
		board.validate();
		this.removeKeyListener(keyListernerQuestion);
		keyListernerQuestion = new KeyListenerQuestion(randomQuestion.getAnswers(), board, this);
		this.addKeyListener(keyListernerQuestion);
	}
	
	public void setAnswer(boolean answerRight){
		if(answerRight){
			questionLabel.setText("Richtig!");
			answer1Label.setText("");
			answer2Label.setText("");
			answer3Label.setText("");
			answer4Label.setText("");
			this.repaint();
		}
		else{
			questionLabel.setText("Falsch!");
		}
		
		new Thread(){
			 public void run() {
				try {
					sleep(2000);
					board.continueAfterQuestEvent();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		}.start();
	}

}
