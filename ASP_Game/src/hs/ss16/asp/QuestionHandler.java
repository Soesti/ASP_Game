package hs.ss16.asp;

import java.util.List;

public class QuestionHandler {
	List<Question> questions;

	public QuestionHandler(){
		//read Questions from xml
	}
	
	public Question getQuestions(){
		int randomNumber = (int)Math.random()*questions.size();
		return questions.get(randomNumber);		
	}
	
}
