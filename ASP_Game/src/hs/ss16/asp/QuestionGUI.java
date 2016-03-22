package hs.ss16.asp;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestionGUI extends JPanel {
	
	private static final long serialVersionUID = -4767294290332006077L;
	static final int G_Width = 600;
	static final int G_Height = 300;
	
	JLabel questionLabel, answer1Label, answer2Label, answer3Label, answer4Label;
	
	QuestionHandler questionHandler;
	
	public QuestionGUI(Board board){
		setSize(G_Width, G_Height);
		setLayout(new GridLayout(5,1));
		
		questionHandler = new QuestionHandler();
		
		questionLabel = new JLabel();
		answer1Label = new JLabel();
		answer2Label = new JLabel();
		answer3Label = new JLabel();
		answer4Label = new JLabel();
		
	}
	
	public void initializeQuestionGUI(Question question) {
		questionLabel.setText(question.getQuestion());

		answer1Label.setText(question.getAnswers().get(0).getAnswerString());
		answer2Label.setText(question.getAnswers().get(1).getAnswerString());
		answer3Label.setText(question.getAnswers().get(2).getAnswerString());
		answer4Label.setText(question.getAnswers().get(3).getAnswerString());
		
		setVisible(true);
	}
	
	
	public void askQuestion() {
		Question randomQuestion = questionHandler.getRandomQuestion();
		
		initializeQuestionGUI(randomQuestion);
		
		KeyListenerQuestion keyListernerQuestion = new KeyListenerQuestion(randomQuestion.getAnswers());
	}

}
