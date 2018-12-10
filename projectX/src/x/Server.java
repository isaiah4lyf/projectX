

package x;

import java.awt.TextArea;

public class Server implements Runnable{
	private TextArea consoleLike;
	private String URL;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SessionsManager sessions = new SessionsManager();
		sessions.setConsoleLike(consoleLike);
		sessions.setURL(URL);
		new Thread(sessions).start();
		
		ClientsManager clients = new ClientsManager();
		clients.setConsoleLike(consoleLike);
		clients.setURL(URL);
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
