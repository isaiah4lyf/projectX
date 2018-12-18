package x;

import java.awt.TextArea;
import java.util.Random;

import databaseClasses.*;

public class Session implements Runnable{
	private String question;
	private TextArea consoleLike;
	private String URL;
	private volatile boolean truee;
	public boolean isTruee() {
		return truee;
	}
	synchronized void setTruee(boolean truee) {
		this.truee = truee;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String sessionID = "1";
				String PreviousQuestionID = "";
				String PreviousAnswerID = "";
				String[] previousQuestionAnswer = null;
				while(true)
				{
					try 
					{

						GetSessionQuestion question = new GetSessionQuestion();
						String[] dataArray = question.DoTheWork(URL, sessionID);
						if(!PreviousQuestionID.equals(dataArray[4]))
						{
							if(previousQuestionAnswer != null)
							{
								if(!PreviousAnswerID.equals(previousQuestionAnswer[4]))
								{
									consoleLike.append("Answer: " + previousQuestionAnswer[2] + "\n");
									truee = false;
									consoleLike.append(String.valueOf(truee));
								}
								PreviousAnswerID = previousQuestionAnswer[4];
							}
							consoleLike.append("Question: " + dataArray[1] + "\n"); // Use this in order to handle the exception here if the peer terminates the connection in an unusual way
						}
						PreviousQuestionID = dataArray[4];	
						previousQuestionAnswer = dataArray;
						Thread.sleep(500);
						truee = true;
						consoleLike.append(String.valueOf(truee));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
				}
			}			
		}).start();
		while(true)
		{
			try {
				GetAllQuestionsAndAnswers questions = new GetAllQuestionsAndAnswers();
				String[] QuestionsArray = questions.DoTheWork(URL);
				Random rand = new Random();
				int n = rand.nextInt(QuestionsArray.length);
				
				InsertSessionQuestion insert = new InsertSessionQuestion();
				insert.DoTheWork(URL, "1", QuestionsArray[n]);
				Thread.sleep(10000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public TextArea getConsoleLike() {
		return consoleLike;
	}
	public void setConsoleLike(TextArea consoleLike) {
		this.consoleLike = consoleLike;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
}
