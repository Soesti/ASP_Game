package hs.ss16.asp;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestenGUI extends JPanel {
	
	static final int G_Width = 600;
	static final int G_Height = 300;
	
	JLabel questionLabel, answer1Label, answer2Label, answer3Label, answer4Label;
	
	QuestionHandler questionHandler;
	
	public QuestenGUI(Board board){
		setSize(G_Width, G_Height);
		setLayout(new GridLayout(5,1));
		
		questionHandler = new QuestionHandler();
		
		questionLabel = new JLabel();
		answer1Label = new JLabel();
		answer2Label = new JLabel();
		answer3Label = new JLabel();
		answer4Label = new JLabel();
		
	}
	
	public void createNewQuestion(){
		Question newQuestion = questionHandler.getQuestions();
		
		questionLabel.setText(newQuestion.getQuestion());
		setVisible(true);
	}

}
