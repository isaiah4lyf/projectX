package projectXTomCatServer.serverRecords;

import java.io.IOException;
import java.util.List;

import javax.websocket.Session;

public class GameSession {
	private int GameSessionID;
	private int GameSessionCurrentQuestionID;
	private int GameSessionNumberOfUsers;
	private List<Client> SessionClientclients;

	public List<Client> getSessionClientclients() {
		return SessionClientclients;
	}
	public void setSessionClientclients(List<Client> clients) {
		this.SessionClientclients = clients;
	}
	public int getGameSessionID() {
		return GameSessionID;
	}
	public void setGameSessionID(int gameSessionID) {
		GameSessionID = gameSessionID;
	}
	public int getGameSessionCurrentQuestionID() {
		return GameSessionCurrentQuestionID;
	}
	public void setGameSessionCurrentQuestionID(int gameSessionCurrentQuestionID) {
		GameSessionCurrentQuestionID = gameSessionCurrentQuestionID;
	}
	public int getGameSessionNumberOfUsers() {
		return GameSessionNumberOfUsers;
	}
	public void setGameSessionNumberOfUsers(int gameSessionNumberOfUsers) {
		GameSessionNumberOfUsers = gameSessionNumberOfUsers;
	}
	public void SendSessionClientsMessage(String Message)
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
	public void RemoveSessionClient(Client client)
	{
		SessionClientclients.remove(client);
	}
	public void AddSessionClient(Client client)
	{
		SessionClientclients.add(client);
	}
}
