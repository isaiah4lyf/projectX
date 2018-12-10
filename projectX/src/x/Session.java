package x;

import java.awt.TextArea;

import databaseClasses.*;

public class Session implements Runnable{
	private String question;
	private TextArea consoleLike;
	private String URL;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int index = 0;
		while(true)
		{
			try {
				UpdateSessionData update = new UpdateSessionData();
				update.DoTheWork(URL, "1", "Question " + index, "Answer " + index, String.valueOf(index),"1");
				Thread.sleep(4000);
				index++;
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
