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
		public string InsertQuestion(string Question, string Answer,string Diagram,string TimeInSeconds)
		{
			QuestionsAndAnswer qustion = new QuestionsAndAnswer();
			qustion.Question = Question;
			qustion.Answer = Answer;
			qustion.Diagram = Diagram;
			qustion.TimeInSeconds = Convert.ToInt32(TimeInSeconds);
			linq.QuestionsAndAnswers.InsertOnSubmit(qustion);
			linq.SubmitChanges();
			return "true";
		}
		[WebMethod]
		public string UpdateQuestion(string QuestionID, string Question, string Answer, string Diagram, string TimeInSeconds)
		{
			QuestionsAndAnswer qustion = (from QuestionsAndAnswer in linq.QuestionsAndAnswers
										  where QuestionsAndAnswer.QuestionID == Convert.ToInt32(QuestionID)
										  select QuestionsAndAnswer).First();
			qustion.Question = Question;
			qustion.Answer = Answer;
			qustion.Diagram = Diagram;
			qustion.TimeInSeconds = Convert.ToInt32(TimeInSeconds);
			linq.SubmitChanges();
			return "true";
		}
		[WebMethod]
		public string[] GetQuestion(string QuestionID)
		{
			QuestionsAndAnswer qustion = (from QuestionsAndAnswer in linq.QuestionsAndAnswers
										  where QuestionsAndAnswer.QuestionID == Convert.ToInt32(QuestionID)
										  select QuestionsAndAnswer).First();
			string[] questionArray = new string[5];
			questionArray[0] = qustion.QuestionID.ToString();
			questionArray[1] = qustion.Question;
			questionArray[2] = qustion.Answer;
			questionArray[3] = qustion.Diagram;
			questionArray[4] = qustion.TimeInSeconds.ToString();
			return questionArray;
		}
		[WebMethod]
		public string[] GetAllQeustions()
		{
			List<QuestionsAndAnswer> questions = (from QuestionsAndAnswer in linq.QuestionsAndAnswers
												  select QuestionsAndAnswer).ToList();
			string[] QuestionsIDsArray = new string[questions.Count];
			for(int i = 0; i < questions.Count; i++)
			{
				QuestionsIDsArray[i] = questions.ElementAt(i).QuestionID.ToString();
			}
			return QuestionsIDsArray;
		}

	}
}
