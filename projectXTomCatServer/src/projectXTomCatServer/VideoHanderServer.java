package projectXTomCatServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import projectXTomCatServer.serverRecords.Client;
import projectXTomCatServer.serverRecords.GameSession;

@ServerEndpoint("/VideoHanderServer")
public class VideoHanderServer {
	static Set<Client> clients = Collections.synchronizedSet(new HashSet<Client>());
	static List<GameSession> GameSessions = Collections.synchronizedList(new ArrayList<GameSession>());
	static Set<Session> GameSessionsClients = Collections.synchronizedSet(new HashSet<Session>());
	static volatile int PreviouesNumOfGameSessionsClients = 0;
	static volatile int MaxNumOfSessionUsers = 3;
	static volatile boolean FirstSessionRunning = false;
	@OnOpen
	public void handleOpen(Session clientSession)
	{
		System.out.println("Connected");
	}	
	
	@OnMessage
	public void handleMessage(String message,Session clientSession)
	{	
		//String s = new String(message);
		System.out.println(message);


	}
	
	@OnClose
	public void handleClose(Session clientSession)
	{

	}
	
	@OnError
	public void handleError(Throwable e)
	{
		e.printStackTrace();
	}
	
}