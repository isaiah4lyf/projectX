package projectXTomCatServer.serverRecords;

import javax.websocket.Session;

public class Client {
	private int ClientID;
	private Session ClientSession;
	private int GameSessionID;
	public int getClientID() {
		return ClientID;
	}
	public void setClientID(int clientID) {
		ClientID = clientID;
	}
	public Session getClientSession() {
		return ClientSession;
	}
	public void setClientSession(Session clientSession) {
		ClientSession = clientSession;
	}
	public int getGameSessionID() {
		return GameSessionID;
	}
	public void setGameSessionID(int gameSessionID) {
		GameSessionID = gameSessionID;
	}

}
