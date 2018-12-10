package x;

import java.awt.TextArea;

public class SessionsManager implements Runnable{
	private TextArea consoleLike;
	private String URL;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Session session = new Session();
		session.setConsoleLike(consoleLike);
		session.setURL(URL);
		new Thread(session).start();
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
