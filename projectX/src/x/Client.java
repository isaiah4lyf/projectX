package x;

import java.awt.TextArea;
import java.io.*;
import java.net.*;

import databaseClasses.*;


public class Client implements Runnable{
	private TextArea consoleLike;
	private String URL;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private boolean ProcessingClient;
	private boolean SessionAlive;
	private String[] sessionsArray;
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
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			ProcessingClient = true;
			SessionAlive = false;
			sessionsArray = null;
			while(ProcessingClient == true)
			{
				String command = in.readUTF();
				consoleLike.append(command + "\n");
				try 
				{
					switch(command)
					{
						case "LOGIN": 				
							Login();
							break;
						case "GET_SESSIONS":
							GetAllSessions(); 
							break;
						case "GET_SESSION_CURRENT_DATA":
							GetSessionCurrentData();
							break;
	                    case "SUBMIT_SESSION_ANSWER":
	                    	 ProcessSubmitSessionAnswerCommand();
	                        break;
						case "QUIT":
							ProcessingClient = false;
							try {
								in.close();
								out.close();
								socket.close();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} 
	
	// Clients Methods
	private void Login()
	{
		sendMessage("Hello");
	}
	private void GetAllSessions() 
	{	
		GetAllSessions sessions = new GetAllSessions();
		sessionsArray = sessions.DoTheWork(URL);
		sendMessage(String.valueOf(sessionsArray.length));
	}
	private void GetSessionCurrentData()
	{
		try 
		{
			SessionAlive = true;
			String session = in.readUTF();
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub

					while(SessionAlive == true)
					{
						try 
						{
							out.writeUTF(String.valueOf(truee));
							Thread.sleep(500);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							SessionAlive = false;	// Discontinue the loop
							e.printStackTrace();
							
						}
					}
				}			
			}).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    private void ProcessSubmitSessionAnswerCommand()
    {
    	
		try {
			String SessionAnswer = in.readUTF();
	    	consoleLike.append(SessionAnswer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	// Other methods
	private void sendMessage(String message)
	{
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	// Getters and Setters
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
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
