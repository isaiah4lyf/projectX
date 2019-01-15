package projectXTomCatServer;

import javax.websocket.server.ServerEndpoint;

import databaseManagement.GetAllQuestions;
import databaseManagement.GetQuestion;
import databaseManagement.GetUser;
import databaseManagement.UpdateUser;
import databaseManagement.databaseRecords.QuestionRecord;
import projectXTomCatServer.serverRecords.Client;
import projectXTomCatServer.serverRecords.GameSession;
import projectXTomCatServer.serverRecords.GameSessionAudience;

import java.io.IOException;
import java.util.*;
import javax.websocket.*;


@ServerEndpoint("/serverendpoint")
public class ProjectXServerEndpoint {
	static String URL = "http://localhost/";
	static volatile int MaxNumOfSessionUsers = 3;

	static List<GameSessionAudience> GameSessionAudience = Collections.synchronizedList(new ArrayList<GameSessionAudience>());
	static List<GameSession> GameSessions = Collections.synchronizedList(new ArrayList<GameSession>());
	static List<Session> GameSessionsClients = Collections.synchronizedList(new ArrayList<Session>());
	static List<QuestionRecord> GlobalQuestions = Collections.synchronizedList(new ArrayList<QuestionRecord>());
	static volatile int PreviouesNumOfGameSessionsClients = 0;
	static volatile boolean FirstSessionRunning = false;
	@OnOpen
	public void handleOpen(Session clientSession)
	{

	}	
	
	@OnMessage
	public void handleMessage(String message,Session clientSession)
	{
		String command = message.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::")[0];
		switch(command)
		{
			case "GET_GAME_SESSIONS":
				GameSessionsClients.add(clientSession);
				PreviouesNumOfGameSessionsClients = GameSessionsClients.size();
				handleGetGameSessionsCommand(clientSession);
				Iterator<Session> iterator = GameSessionsClients.iterator();
				while(iterator.hasNext())
				{
					handleGetGameSessionsCommand(iterator.next());
				}
				break;
			case "JOIN_GAME_SESSION":
				handleJoinGameSessionCommand(message,clientSession);
				Iterator<Session> iterator2 = GameSessionsClients.iterator();
				while(iterator2.hasNext())
				{
					handleGetGameSessionsCommand(iterator2.next());
				}
				break;
			case "VIEW_POST":
				handleViewPost(message,clientSession);
				break;
			default:
				handDefaultCommand(message,clientSession);
				break;
		}			
	}
	
	@OnClose
	public void handleClose(Session clientSession)
	{
		GameSessionsClients.remove(clientSession);
		int GameSessionID = -1;
		String ClientName = "";
		for(int i = 0; i < GameSessions.size(); i++)
		{
			Client client = GameSessions.get(i).SearchClientWithSession(clientSession);
			if(client != null)
			{
				ClientName = new GetUser().DoTheWork(URL, String.valueOf(client.getClientID()))[1];
			}
			GameSessionID = GameSessions.get(i).RemoveSessionClient(client);
			if(GameSessionID != -1)
			{
				break;
			}
		}
		if(GameSessionID != -1)
		{
			GameSessions.get(GameSessionID).setGameSessionNumberOfUsers(GameSessions.get(GameSessionID).getGameSessionNumberOfUsers()-1);
			GameSessions.get(GameSessionID).SendSessionClientsMessage(":::::-::::::::-:::::::-:::::::-::::::"+ClientName + " left the session");
		}
		boolean RemoveLastSession = false;
		for(int i = 0; i < GameSessions.size()-1; i++)
		{
			if(GameSessions.get(i).getGameSessionNumberOfUsers() == MaxNumOfSessionUsers)
			{
				RemoveLastSession = false;
			}
			else
			{
				RemoveLastSession = true;
			}
		}
		if(RemoveLastSession == true && GameSessions.get(GameSessions.size()-1).getGameSessionNumberOfUsers() == 0 && PreviouesNumOfGameSessionsClients == GameSessionsClients.size())
		{
			GameSessions.remove(GameSessions.get(GameSessions.size()-1));
		}
		for(int i = GameSessions.size() - 2; i > 0; i--)
		{
			if(GameSessions.get(i).getGameSessionNumberOfUsers() == 0 &&  PreviouesNumOfGameSessionsClients == GameSessionsClients.size())
			{
				if(GameSessions.get(GameSessions.size()-1).getGameSessionNumberOfUsers() == 0)
				{
					GameSessions.remove(i);
				}
			}
			else
			{
				break;
			}
		}
	}
	
	@OnError
	public void handleError(Throwable e,Session clientSession)
	{
		e.printStackTrace();
	}
	
	private void handleViewPost(String message,Session clientSession)
	{

		String[] messageTokens = message.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::");
		Client client = new Client();
		client.setClientID(Integer.parseInt(messageTokens[2]));
		client.setGameSessionID(Integer.parseInt(messageTokens[1]));
		client.setClientSession(clientSession);
		if(GameSessionAudience.size() == 0)
		{
			GameSessionAudience audience = new GameSessionAudience();
			audience.setGameSessionAudienceNumber(0);
			audience.setGameSessionID(Integer.parseInt(messageTokens[1]));
			audience.setSessionClientclients(new ArrayList<Client>());
			GameSessionAudience.add(audience);
		}
		for(int i = 0; i < GameSessionAudience.size(); i++)
		{
			if(GameSessionAudience.get(i).getGameSessionID() == Integer.parseInt(messageTokens[1]))
			{
				GameSessionAudience.get(i).setGameSessionAudienceNumber(GameSessionAudience.get(i).getGameSessionAudienceNumber()+1);
				GameSessionAudience.get(i).getSessionClientclients().add(client);
			}
		}

	}
	private void handleGetGameSessionsCommand(Session clientSession)
	{
		try {
			String numUserInEach = "";
			for(int i = 0; i < GameSessions.size(); i++)
			{
				numUserInEach += String.valueOf(GameSessions.get(i).getGameSessionNumberOfUsers()) + ",";
			}
			clientSession.getBasicRemote().sendText(String.valueOf(GameSessions.size())+","+numUserInEach);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	private void handDefaultCommand(String message,Session clientSession)
	{
		int GameSessionID = Integer.parseInt(message.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::")[1]);
		QuestionRecord question = GameSessions.get(GameSessionID).getCurrentQuestion();
		if(message.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::")[0].equals(question.getAnswer()))
		{
			Client client = GameSessions.get(GameSessionID).SearchClientWithSession(clientSession);
			GetUser user = new GetUser();
			String[] userData = user.DoTheWork(URL,String.valueOf(client.getClientID()));
			long totalScore = Long.parseLong(userData[9]) + (long)question.getPoints();
			long weekendScore = Long.parseLong(userData[8]) + (long)question.getPoints();
			long weekdaysScore = Long.parseLong(userData[7]) + (long)question.getPoints();
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek >= 2 && dayOfWeek <= 6)
			{
				if(dayOfWeek == 6)
				{
					Date date = new Date();
					if(date.getHours() >= 20 && date.getMinutes() >= 0)
					{
						new UpdateUser().do_The_Work(URL, userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], userData[6],userData[7], String.valueOf(weekendScore),String.valueOf(totalScore));
					}
					else
					{
						new UpdateUser().do_The_Work(URL, userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], userData[6],  String.valueOf(weekdaysScore), userData[8],String.valueOf(totalScore));
					}

				}
				else
				{
					new UpdateUser().do_The_Work(URL, userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], userData[6], String.valueOf(weekdaysScore), userData[8],String.valueOf(totalScore));
				}
			}
			else
			{

				new UpdateUser().do_The_Work(URL, userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], userData[6], userData[7],String.valueOf(weekendScore),String.valueOf(totalScore));
			}
			String[] UserString = new GetUser().DoTheWork(URL, String.valueOf(client.getClientID()));
			GameSessions.get(GameSessionID).SendSessionClientsMessage(":::::::-::::::::-:::::::::::-:::::::-::::" + UserString[1] + " - Correct");

			for(int i = 0; i < GameSessionAudience.size(); i++)
			{
				if(GameSessionAudience.get(i).getGameSessionID() == GameSessionID)
				{
					GameSessionAudience.get(i).SendSessionAudienceMessage(":::::::-::::::::-:::::::::::-:::::::-::::" + UserString[1] + " - Correct");
					break;
				}
			}
		}
		else
		{
			Client client = GameSessions.get(GameSessionID).SearchClientWithSession(clientSession);
			String[] UserString = new GetUser().DoTheWork(URL, String.valueOf(client.getClientID()));
			GameSessions.get(GameSessionID).SendSessionClientsMessage(UserString[1]+ " - " + message.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::")[0]);
			for(int i = 0; i < GameSessionAudience.size(); i++)
			{
				if(GameSessionAudience.get(i).getGameSessionID() == GameSessionID)
				{
					GameSessionAudience.get(i).SendSessionAudienceMessage(UserString[1]+ " - " + message.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::")[0]);
					break;
				}
			}
		}

	}
	private void handleJoinGameSessionCommand(String message,Session clientSession)
	{
		String[] messageTokens = message.split(":::::::::-::::::::::::::::-:::::::::::-:::::-::::");
		if(GameSessions.size() == 0 && FirstSessionRunning == false)
		{
			GameSession gameSession = new GameSession();
			gameSession.setGameSessionID(0);
			gameSession.setGameSessionNumberOfUsers(1);
			gameSession.setGameSessionCurrentQuestionID(1);
			gameSession.setSessionClientclients(new ArrayList<Client>());
			GameSessions.add(gameSession);
			Client client = new Client();
			client.setClientID(Integer.parseInt(messageTokens[2]));
			client.setClientSession(clientSession);
			client.setGameSessionID(0);
			GameSessions.get(0).AddSessionClient(client);
			new Thread(new Runnable() {
				private List<QuestionRecord> AllQuestionsData = new ArrayList<QuestionRecord>(); 
				private  String[] AllQuestionsIDs;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					AllQuestionsIDs = new GetAllQuestions().DoTheWork(URL);
					for(int i = 0; i < AllQuestionsIDs.length; i++)
					{
						String[] GetQuestion = new GetQuestion().DoTheWork(URL,AllQuestionsIDs[i]);
						QuestionRecord question = new QuestionRecord();
						question.setQuestionID(GetQuestion[0]);
						question.setQuestion(GetQuestion[1]);
						question.setAnswer(GetQuestion[2]);
						question.setDiagram(GetQuestion[3]);
						question.setTimeInSeconds(Integer.parseInt(GetQuestion[4]));
						question.setPoints(Integer.parseInt(GetQuestion[5]));
						question.setCategory(GetQuestion[6]);
						AllQuestionsData.add(question);
					}
					int CountSleep = 0;
					int ExactSecond = 0;
					int SecondCounter = 1;
					int n = 0;
					int QuestionTime = 20;
					QuestionRecord CurrentQuestion = null;
					QuestionRecord previousQuestion = null;
					
					while(true)
					{
						FirstSessionRunning = true;
						try {
							if(CountSleep == QuestionTime || CountSleep == 0)
							{
								if(previousQuestion != null)
								{
									GameSessions.get(0).SendSessionClientsMessage(previousQuestion.getAnswer() + "::::::::-::::::::::::::-:::::::::::-:::::-::::" +previousQuestion.getPoints() +"\n");
									for(int i = 0; i < GameSessionAudience.size(); i++)
									{
										if(GameSessionAudience.get(i).getGameSessionID() == 0)
										{
											GameSessionAudience.get(0).SendSessionAudienceMessage(previousQuestion.getAnswer() + "::::::::-::::::::::::::-:::::::::::-:::::-::::" +previousQuestion.getPoints() +"\n");
											break;
										}
									}
								}
								GameSessions.get(0).setCurrentQuestion(AllQuestionsData.get(n));
								QuestionTime = AllQuestionsData.get(n).getTimeInSeconds()*2;
								SecondCounter = AllQuestionsData.get(n).getTimeInSeconds();
								GameSessions.get(0).SendSessionClientsMessage(AllQuestionsData.get(n).getCategory()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+AllQuestionsData.get(n).getQuestion()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+(AllQuestionsData.get(n).getAnswer().length())+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+AllQuestionsData.get(n).getDiagram());
								for(int i = 0; i < GameSessionAudience.size(); i++)
								{
									if(GameSessionAudience.get(i).getGameSessionID() == 0)
									{
										GameSessionAudience.get(0).SendSessionAudienceMessage(AllQuestionsData.get(n).getCategory()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+AllQuestionsData.get(n).getQuestion()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+(AllQuestionsData.get(n).getAnswer().length())+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+AllQuestionsData.get(n).getDiagram());
										break;
									}
								}
								CurrentQuestion = AllQuestionsData.get(n);
								previousQuestion = AllQuestionsData.get(n);
								AllQuestionsData.remove(AllQuestionsData.get(n));
								if(AllQuestionsData.size() == 0)
								{
									if(GlobalQuestions.size() > 0)
									{
										AllQuestionsData.add(GlobalQuestions.get(0));
										new Thread(new Runnable() {
											@Override
											public void run() {
												// TODO Auto-generated method stub
												for(int i = 1; i < GlobalQuestions.size(); i++)
												{
													AllQuestionsData.add(GlobalQuestions.get(i));
												}
											}
											
										}).start();
									}
									else
									{
										AllQuestionsIDs = new GetAllQuestions().DoTheWork(URL);
										for(int i = 0; i < AllQuestionsIDs.length; i++)
										{
											String[] GetQuestion = new GetQuestion().DoTheWork(URL,AllQuestionsIDs[i]);
											QuestionRecord question = new QuestionRecord();
											question.setQuestionID(GetQuestion[0]);
											question.setQuestion(GetQuestion[1]);
											question.setAnswer(GetQuestion[2]);
											question.setDiagram(GetQuestion[3]);
											question.setTimeInSeconds(Integer.parseInt(GetQuestion[4]));
											question.setPoints(Integer.parseInt(GetQuestion[5]));
											question.setCategory(GetQuestion[6]);
											AllQuestionsData.add(question);
										}
									}
	
								}
								Random rand = new Random();
								n = rand.nextInt(AllQuestionsData.size());
								CountSleep = 1;
							}
							else
							{
								GameSessions.get(0).SendSessionClientsMessage(":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getCategory()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getQuestion()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+(CurrentQuestion.getAnswer().length())+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getDiagram());
								GameSessions.get(0).SendSessionClientsMessage(":::::::::-::::::::::::::::-:::::::::::-:::::-::::RemainingTime:::::::::-::::::::::::::::-:::::::::::-:::::-::::"+SecondCounter);
								for(int i = 0; i < GameSessionAudience.size(); i++)
								{
									if(GameSessionAudience.get(i).getGameSessionID() == 0)
									{
										GameSessionAudience.get(0).SendSessionAudienceMessage(":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getCategory()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getQuestion()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+(CurrentQuestion.getAnswer().length())+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getDiagram());
										GameSessionAudience.get(0).SendSessionAudienceMessage(":::::::::-::::::::::::::::-:::::::::::-:::::-::::RemainingTime:::::::::-::::::::::::::::-:::::::::::-:::::-::::"+SecondCounter);
										break;
									}
								}
							}
							
							Thread.sleep(500);
							CountSleep++;
							if(ExactSecond == 2)
							{
								ExactSecond = 0;
								SecondCounter--;
							}
							ExactSecond++;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}).start();
			new Thread(new Runnable() {
				private  String[] AllQuestionsIDs = new GetAllQuestions().DoTheWork(URL);
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true)
					{
						AllQuestionsIDs = new GetAllQuestions().DoTheWork(URL);
						GlobalQuestions = Collections.synchronizedList(new ArrayList<QuestionRecord>());
						for(int i = 0; i < AllQuestionsIDs.length; i++)
						{
							String[] GetQuestion = new GetQuestion().DoTheWork(URL,AllQuestionsIDs[i]);
							QuestionRecord question = new QuestionRecord();
							question.setQuestionID(GetQuestion[0]);
							question.setQuestion(GetQuestion[1]);
							question.setAnswer(GetQuestion[2]);
							question.setDiagram(GetQuestion[3]);
							question.setTimeInSeconds(Integer.parseInt(GetQuestion[4]));
							question.setPoints(Integer.parseInt(GetQuestion[5]));
							question.setCategory(GetQuestion[6]);
							GlobalQuestions.add(question);
						}
						try {
							Thread.sleep(600000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}).start();
		}
		else
		{
			if(Integer.parseInt(messageTokens[1]) > GameSessions.size()+1)
			{
				try {
					clientSession.getBasicRemote().sendText(":::::::::-::::::::::::::::-:::::::::::-:::::-::::SessionFull");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				if(Integer.parseInt(messageTokens[1]) != GameSessions.size() && GameSessions.get(Integer.parseInt(messageTokens[1])).getGameSessionNumberOfUsers() == MaxNumOfSessionUsers)
				{
					try {
						clientSession.getBasicRemote().sendText(":::::::::-::::::::::::::::-:::::::::::-:::::-::::SessionFull");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					Client client = new Client();
					client.setClientID(Integer.parseInt(messageTokens[2]));
					client.setClientSession(clientSession);
					client.setGameSessionID(Integer.parseInt(messageTokens[1]));
					GameSessions.get(Integer.parseInt(messageTokens[1])).AddSessionClient(client);
					GameSessions.get(Integer.parseInt(messageTokens[1])).setGameSessionNumberOfUsers(GameSessions.get(Integer.parseInt(messageTokens[1])).getGameSessionNumberOfUsers()+1);
					String ClientName = new GetUser().DoTheWork(URL, String.valueOf(client.getClientID()))[1];
					GameSessions.get(Integer.parseInt(messageTokens[1])).SendSessionClientsMessage(":::::-::::::::-:::::::-:::::::-::::::"+ClientName + " joined the session");
					for(int i = 0; i < GameSessionAudience.size(); i++)
					{
						if(GameSessionAudience.get(i).getGameSessionID() == Integer.parseInt(messageTokens[1]))
						{
							GameSessionAudience.get(i).SendSessionAudienceMessage(":::::-::::::::-:::::::-:::::::-::::::"+ClientName + " joined the session");
							break;
						}
					}
					boolean CreateNewSession = false;
					for(int i = 0; i < GameSessions.size(); i++)
					{
						if(GameSessions.get(i).getGameSessionNumberOfUsers() == MaxNumOfSessionUsers)
						{
							CreateNewSession = true;
						}
						else
						{
							CreateNewSession = false;
						}
					}
					if(CreateNewSession && FirstSessionRunning == true)
					{
						GameSession gameSession = new GameSession();
						gameSession.setGameSessionID(GameSessions.size());
						gameSession.setGameSessionNumberOfUsers(0);
						gameSession.setGameSessionCurrentQuestionID(1);
						gameSession.setSessionClientclients(new ArrayList<Client>());
						GameSessions.add(gameSession);
						new Thread(new Runnable() {
							private int GameSessionID = GameSessions.size()-1;
							private List<QuestionRecord> AllQuestionsData = new ArrayList<QuestionRecord>(); 
							private  String[] AllQuestionsIDs;
							@Override
							public void run() {
								// TODO Auto-generated method stub
								AllQuestionsIDs = new GetAllQuestions().DoTheWork(URL);
								for(int i = 0; i < AllQuestionsIDs.length; i++)
								{
									String[] GetQuestion = new GetQuestion().DoTheWork(URL,AllQuestionsIDs[i]);
									QuestionRecord question = new QuestionRecord();
									question.setQuestionID(GetQuestion[0]);
									question.setQuestion(GetQuestion[1]);
									question.setAnswer(GetQuestion[2]);
									question.setDiagram(GetQuestion[3]);
									question.setTimeInSeconds(Integer.parseInt(GetQuestion[4]));
									question.setPoints(Integer.parseInt(GetQuestion[5]));
									question.setCategory(GetQuestion[6]);
									AllQuestionsData.add(question);
								}
								int CountSleep = 0;
								int n = 0;
								int ExactSecond = 0;
								int SecondCounter = 1;
								int QuestionTime = 20;
								QuestionRecord CurrentQuestion = null;
								QuestionRecord previousQuestion = null;
								while(true)
								{
									try {
										if(GameSessionID >= GameSessions.size())
										{
											break;
										}
										if(CountSleep == QuestionTime || CountSleep == 0)
										{

											if(previousQuestion != null)
											{
												GameSessions.get(GameSessionID).SendSessionClientsMessage(previousQuestion.getAnswer() + "::::::::-::::::::::::::-:::::::::::-:::::-::::" +previousQuestion.getPoints() +"\n");
												for(int i = 0; i < GameSessionAudience.size(); i++)
												{
													if(GameSessionAudience.get(i).getGameSessionID() == GameSessionID)
													{
														GameSessionAudience.get(i).SendSessionAudienceMessage(previousQuestion.getAnswer() + "::::::::-::::::::::::::-:::::::::::-:::::-::::" +previousQuestion.getPoints() +"\n");
														System.out.println("No");
														break;
													}
													System.out.println("Yes");
												}
											}
											GameSessions.get(GameSessionID).setCurrentQuestion(AllQuestionsData.get(n));
											QuestionTime = AllQuestionsData.get(n).getTimeInSeconds()*2;
											SecondCounter = AllQuestionsData.get(n).getTimeInSeconds();
											GameSessions.get(GameSessionID).SendSessionClientsMessage(AllQuestionsData.get(n).getCategory()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+AllQuestionsData.get(n).getQuestion()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+(AllQuestionsData.get(n).getAnswer().length())+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+AllQuestionsData.get(n).getDiagram());
											
											for(int i = 0; i < GameSessionAudience.size(); i++)
											{
												if(GameSessionAudience.get(i).getGameSessionID() == GameSessionID)
												{
													GameSessionAudience.get(i).SendSessionAudienceMessage(AllQuestionsData.get(n).getCategory()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+AllQuestionsData.get(n).getQuestion()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+(AllQuestionsData.get(n).getAnswer().length())+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+AllQuestionsData.get(n).getDiagram());
													break;
												}
											}
											CurrentQuestion = AllQuestionsData.get(n);
											previousQuestion = AllQuestionsData.get(n);
											AllQuestionsData.remove(AllQuestionsData.get(n));
											if(AllQuestionsData.size() == 0)
											{
												if(GlobalQuestions.size() > 0)
												{
													AllQuestionsData.add(GlobalQuestions.get(0));
													new Thread(new Runnable() {
														@Override
														public void run() {
															// TODO Auto-generated method stub
															for(int i = 1; i < GlobalQuestions.size(); i++)
															{
																AllQuestionsData.add(GlobalQuestions.get(i));
															}
															System.out.println("Updating");
														}
														
													}).start();
												}
												else
												{
													AllQuestionsIDs = new GetAllQuestions().DoTheWork(URL);
													for(int i = 0; i < AllQuestionsIDs.length; i++)
													{
														String[] GetQuestion = new GetQuestion().DoTheWork(URL,AllQuestionsIDs[i]);
														QuestionRecord question = new QuestionRecord();
														question.setQuestionID(GetQuestion[0]);
														question.setQuestion(GetQuestion[1]);
														question.setAnswer(GetQuestion[2]);
														question.setDiagram(GetQuestion[3]);
														question.setTimeInSeconds(Integer.parseInt(GetQuestion[4]));
														question.setPoints(Integer.parseInt(GetQuestion[5]));
														question.setCategory(GetQuestion[6]);
														AllQuestionsData.add(question);
													}
												}								
											}
											Random rand = new Random();
											n = rand.nextInt(AllQuestionsData.size());
											CountSleep = 1;
										}
										else
										{
											GameSessions.get(GameSessionID).SendSessionClientsMessage(":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getCategory()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getQuestion()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+(CurrentQuestion.getAnswer().length())+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getDiagram());
											GameSessions.get(GameSessionID).SendSessionClientsMessage(":::::::::-::::::::::::::::-:::::::::::-:::::-::::RemainingTime:::::::::-::::::::::::::::-:::::::::::-:::::-::::"+SecondCounter);
											for(int i = 0; i < GameSessionAudience.size(); i++)
											{
												if(GameSessionAudience.get(i).getGameSessionID() == GameSessionID)
												{
													GameSessionAudience.get(i).SendSessionAudienceMessage(":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getCategory()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getQuestion()+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+(CurrentQuestion.getAnswer().length())+":::::::::-::::::::::::::::-:::::::::::-:::::-::::"+CurrentQuestion.getDiagram());
													GameSessionAudience.get(i).SendSessionAudienceMessage(":::::::::-::::::::::::::::-:::::::::::-:::::-::::RemainingTime:::::::::-::::::::::::::::-:::::::::::-:::::-::::"+SecondCounter);
													break;
												}
											}
										}
										Thread.sleep(500);
										if(ExactSecond == 2)
										{
											ExactSecond = 0;
											SecondCounter--;
										}
										ExactSecond++;
										CountSleep++;
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							
						}).start();
					}

				}
			}

		}
	}
}
