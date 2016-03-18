package hs.ss16.asp;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestenGUI extends JPanel {
	
	static final int G_Width = 600;
	static final int G_Height = 300;
	
	JLabel question, answer1, answer2, answer3, answer4;
	
	QuestionHandler questionHandler;
	
	public QuestenGUI(Board board){
		setSize(G_Width, G_Height);
		setLayout(new GridLayout(5,1));
		
		questionHandler = new QuestionHandler();
		
		question = new JLabel();
		answer1 = new JLabel();
		answer2 = new JLabel();
		answer3 = new JLabel();
		answer4 = new JLabel();
		
	}
	
	public void createNewQuestion(){
		Question question = questionHandler.getQuestions();
		
		
		setVisible(true);
	}

}
