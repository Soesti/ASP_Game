package hs.ss16.asp;

import java.util.ArrayList;
import java.util.List;

public class Question {

	String question;
	List<Answer> answers;
	
	public Question(List<Answer> answer, String question){
		this.answers = new ArrayList<Answer>(answer);
		this.question = question;
	}
	
	public List<Answer> getAnswers(){
		return this.answers;
	}
	
	public String getQuestion(){
		return this. question;
	}
	
}
