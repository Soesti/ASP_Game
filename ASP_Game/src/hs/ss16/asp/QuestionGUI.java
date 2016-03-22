package hs.ss16.asp;

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
	
	public QuestionGUI(){
		setBounds(200, ((int)World.screenSize.getHeight()/2)-150,G_Width, G_Height);
		setLayout(new GridLayout(5,1));
		
		questionHandler = new QuestionHandler();
		
		questionLabel = new JLabel("",SwingConstants.CENTER);
		answer1Label = new JLabel();
		answer2Label = new JLabel();
		answer3Label = new JLabel();
		answer4Label = new JLabel();
		
		this.add(questionLabel,0);
		this.add(answer1Label,1);
		this.add(answer2Label,2);
		this.add(answer3Label,3);
		this.add(answer4Label,4);
	}
	
	public void initializeQuestionGUI(Question question) {
		questionLabel.setText(question.getQuestion());

		answer1Label.setText("	1. " + question.getAnswers().get(0).getAnswerString());
		answer2Label.setText("	2. " + question.getAnswers().get(1).getAnswerString());
		answer3Label.setText("	3. " + question.getAnswers().get(2).getAnswerString());
		answer4Label.setText("	4. " + question.getAnswers().get(3).getAnswerString());
		
		setVisible(true);
	}
	
	
	public void askQuestion(Board board) {
		Question randomQuestion = questionHandler.getRandomQuestion();
		
		initializeQuestionGUI(randomQuestion);
		
		board.validate();
		this.removeKeyListener(keyListernerQuestion);
		keyListernerQuestion = new KeyListenerQuestion(randomQuestion.getAnswers(), board);
		this.addKeyListener(keyListernerQuestion);
	}

}
