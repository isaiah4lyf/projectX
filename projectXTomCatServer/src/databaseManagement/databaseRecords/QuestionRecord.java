package databaseManagement.databaseRecords;

public class QuestionRecord {
	private String QuestionID;
	private String Question;
	private String Answer;
	private String Diagram;
	private int TimeInSeconds;
	public String getQuestionID() {
		return QuestionID;
	}
	public void setQuestionID(String questionID) {
		QuestionID = questionID;
	}
	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	public String getDiagram() {
		return Diagram;
	}
	public void setDiagram(String diagram) {
		Diagram = diagram;
	}
	public int getTimeInSeconds() {
		return TimeInSeconds;
	}
	public void setTimeInSeconds(int timeInSeconds) {
		TimeInSeconds = timeInSeconds;
	}

}
