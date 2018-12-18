package x;

import java.awt.TextArea;
import java.io.IOException;
import java.net.*;

public class ClientsManager implements Runnable{
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
		ServerSocket server = null;
		try {
			server = new ServerSocket(8060);
			while(true)
			{
				Socket ClientConnection = server.accept();
				Client client = new Client();
				client.setConsoleLike(consoleLike);
				client.setURL(URL);
				client.setSocket(ClientConnection);
				client.setTruee(truee);
				new Thread(client).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
