package projectXTomCatServer.serverRecords;

import java.io.IOException;
import java.util.List;

import javax.websocket.Session;


public class GameSessionAudience {
	private int GameSessionID;
	private int GameSessionAudienceNumber;
	private List<Client> SessionClientclients;
	public int getGameSessionID() {
		return GameSessionID;
	}
	public void setGameSessionID(int gameSessionID) {
		GameSessionID = gameSessionID;
	}
	public int getGameSessionAudienceNumber() {
		return GameSessionAudienceNumber;
	}
	public void setGameSessionAudienceNumber(int gameSessionAudienceNumber) {
		GameSessionAudienceNumber = gameSessionAudienceNumber;
	}
	public List<Client> getSessionClientclients() {
		return SessionClientclients;
	}
	public void setSessionClientclients(List<Client> sessionClientclients) {
		SessionClientclients = sessionClientclients;
	}
	public void SendSessionAudienceMessage(String Message)
	{
		for(int i = 0; i < SessionClientclients.size(); i++)
		{

			try {
				if(SessionClientclients.get(i).getClientSession().isOpen())
				{
					SessionClientclients.get(i).getClientSession().getBasicRemote().sendText(Message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public Client SearchClientWithSession(Session session)
	{
		Client client = null;
		for(int i = 0; i < SessionClientclients.size(); i++)
		{
			if(SessionClientclients.get(i).getClientSession() == session)
			{
				client =  SessionClientclients.get(i);
			}
		}
		return client;
	}
	public int RemoveSessionClient(Client client)
	{
		boolean remove = SessionClientclients.remove(client);
		if(remove == true)
		{
			return getGameSessionID();
		}
		else
		{
			return -1;
		}

	}
	public void AddSessionClient(Client client)
	{
		SessionClientclients.add(client);
	}
}
