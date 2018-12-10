using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace projectXService
{
	/// <summary>
	/// Summary description for projectXAsmx
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class projectXAsmx : System.Web.Services.WebService
	{
		private projectXLinqDataContext linq = new projectXLinqDataContext();
		[WebMethod]
		public string CreateSession(string CurrentQuestion, string CurrentAnswer,string CurrentRemainingTime,string CurrentNumberOfUsers)
		{
			Session session = new Session();
			session.CurrentQuestion = CurrentQuestion;
			session.CurrentAnswer = CurrentAnswer;
			session.CurrentRemainingTime = Convert.ToInt32(CurrentRemainingTime);
			session.CurrentNumberOfUsers = Convert.ToInt32(CurrentNumberOfUsers);
			linq.Sessions.InsertOnSubmit(session);
			linq.SubmitChanges();
			return "true";
		}
		[WebMethod]
		public string[] GetSessionData(string sessionID)
		{
			Session session = (from Session in linq.Sessions
							   where Session.SessionId == Convert.ToInt32(sessionID)
							   select Session).First();
			string[] sessionArray = new string[5];
			sessionArray[0] = session.SessionId.ToString();
			sessionArray[1] = session.CurrentQuestion;
			sessionArray[2] = session.CurrentAnswer;
			sessionArray[3] = session.CurrentRemainingTime.ToString();
			sessionArray[4] = session.CurrentNumberOfUsers.ToString();
			return sessionArray;
		}
		[WebMethod]
		public string UpdateSessionData(string SessionID,string CurrentQuestion, string CurrentAnswer, string CurrentRemainingTime, string CurrentNumberOfUsers)
		{
			Session session = (from Session in linq.Sessions
							   where Session.SessionId == Convert.ToInt32(SessionID)
							   select Session).First();
			session.CurrentQuestion = CurrentQuestion;
			session.CurrentAnswer = CurrentAnswer;
			session.CurrentRemainingTime = Convert.ToInt32(CurrentRemainingTime);
			session.CurrentNumberOfUsers = Convert.ToInt32(CurrentNumberOfUsers);
			linq.SubmitChanges();
			return "true";
		}
		[WebMethod]
		public string[] GetAllSessions()
		{
			List<Session> sessions = (from Session in linq.Sessions
									  select Session).ToList();
			string[] sessionArray = new string[sessions.Count];
			for(int i = 0; i < sessions.Count; i++)
			{
				sessionArray[i] = sessions.ElementAt(i).SessionId.ToString();
			}
			return sessionArray;
		}
	}
}
