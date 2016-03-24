package hs.ss16.asp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuestionHandler {
	List<Question> questions;

	public QuestionHandler() {
	}

	public Question getRandomQuestion() {
		if (this.questions == null) {
			getQuestionsFromXML();
		}
		int randomNumber = (int) (Math.random() * questions.size());
		System.out.println(randomNumber);
		return questions.get(randomNumber);
	}

	private void getQuestionsFromXML() {
		this.questions = new ArrayList<Question>();
		try {
			InputStream input = getClass().getResourceAsStream("/xml/questions.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(input);

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("question");

			for (int i = 0; i < nList.getLength(); i++) {
				List<Answer> answers = new ArrayList<Answer>();
				Node questionNode = nList.item(i);
				if (questionNode.getNodeType() == Node.ELEMENT_NODE) {
					boolean right = false;
					Element questionElement = (Element) nList.item(i);
					String questionText = questionElement.getAttribute("text");

					NodeList answerNodes = questionElement.getElementsByTagName("answer");
					for (int j = 0; j < answerNodes.getLength(); j++) {
						Node answerNode = answerNodes.item(j);
						if (answerNode.getNodeType() == Node.ELEMENT_NODE) {
							Element answerElement = (Element) answerNode;
							String answerText = answerElement.getAttribute("text");

							if (answerElement.hasAttribute("right")) {
								right = true;
							}
							answers.add(new Answer(answerText, right));
						}
					}
					Question question = new Question(answers, questionText);
					this.questions.add(question);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		;
	}
}
