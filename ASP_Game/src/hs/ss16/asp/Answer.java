package hs.ss16.asp;

public class Answer {
	
	String answer;
	boolean right;
	
	public Answer(String answer, boolean right){
		this.answer =answer;
		this.right = right;
	}
	
	public boolean isRight(){
		return this.right;
	}
	
	public String getAnswer(){
		return this.answer;
	}

}
