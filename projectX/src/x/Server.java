

package x;

import java.awt.TextArea;

public class Server implements Runnable{
	private TextArea consoleLike;
	private String URL;
	private volatile boolean truee; 

	@Override
	public void run() {
		// TODO Auto-generated method stub
		truee = true;
		SessionsManager sessions = new SessionsManager();
		sessions.setConsoleLike(consoleLike);
		sessions.setURL(URL);
		sessions.setTruee(truee);
		new Thread(sessions).start();
		
		ClientsManager clients = new ClientsManager();
		clients.setConsoleLike(consoleLike);
		clients.setURL(URL);
		clients.setTruee(truee);
		new Thread(clients).start();
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
