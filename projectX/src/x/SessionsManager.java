package x;

import java.awt.TextArea;

public class SessionsManager implements Runnable{
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
		Session session = new Session();
		session.setConsoleLike(consoleLike);
		session.setURL(URL);
		session.setTruee(truee);
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
