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
		public string InsertQuestion(string Question, string Answer,string Diagram,string TimeInSeconds, string Points,string Category)
		{
			QuestionsAndAnswer question = new QuestionsAndAnswer();
			question.Question = Question;
			question.Answer = Answer;
			question.Diagram = Diagram;
			question.TimeInSeconds = Convert.ToInt32(TimeInSeconds);
			question.Points = Convert.ToInt32(Points);
			question.Category = Category;
			linq.QuestionsAndAnswers.InsertOnSubmit(question);
			linq.SubmitChanges();
			return "true";
		}
		[WebMethod]
		public string UpdateQuestion(string QuestionID, string Question, string Answer, string Diagram, string TimeInSeconds,string Points,string Category)
		{
			QuestionsAndAnswer question = (from QuestionsAndAnswer in linq.QuestionsAndAnswers
										  where QuestionsAndAnswer.QuestionID == Convert.ToInt32(QuestionID)
										  select QuestionsAndAnswer).First();
			question.Question = Question;
			question.Answer = Answer;
			question.Diagram = Diagram;
			question.TimeInSeconds = Convert.ToInt32(TimeInSeconds);
			question.Points = Convert.ToInt32(Points);
			question.Category = Category;
			linq.SubmitChanges();
			return "true";
		}
		[WebMethod]
		public string[] GetQuestion(string QuestionID)
		{
			QuestionsAndAnswer question = (from QuestionsAndAnswer in linq.QuestionsAndAnswers
										  where QuestionsAndAnswer.QuestionID == Convert.ToInt32(QuestionID)
										  select QuestionsAndAnswer).First();
			string[] questionArray = new string[7];
			questionArray[0] = question.QuestionID.ToString();
			questionArray[1] = question.Question;
			questionArray[2] = question.Answer;
			questionArray[3] = question.Diagram;
			questionArray[4] = question.TimeInSeconds.ToString();
			questionArray[5] = question.Points.ToString();
			questionArray[6] = question.Category;
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
		[WebMethod]
		public string InsertUser(string UserName, string Password, string FirstName,string LastName,string Email,string PhoneNumber,string WeekDaysScore,string WeekendScore,string TotalScore)
		{
			List<User> userName = (from User in linq.Users
							   where User.UserName == UserName
							   select User).ToList();
			List<User> userEmail = (from User in linq.Users
								   where User.Email == Email
									select User).ToList();
			List<User> userPhoneNumber = (from User in linq.Users
											where User.PhoneNumber == PhoneNumber
											select User).ToList();
			if (userName.Count > 0)
			{
				return "UserName already taken";
			}
			else if(userEmail.Count > 0 && Email != "null")
			{
				return "Email already registered";
			}
			else if(userPhoneNumber.Count > 0 && PhoneNumber != "null")
			{
				return "PhoneNumber already registered";
			}
			else
			{
				User user = new User();
				user.UserName = UserName;
				user.Password = Password;
				user.FirstName = FirstName;
				user.LastName = LastName;
				user.Email = Email;
				user.PhoneNumber = PhoneNumber;
				user.WeekDaysScore = WeekDaysScore;
				user.WeekendScore = WeekendScore;
				user.TotalScore = TotalScore;
				linq.Users.InsertOnSubmit(user);
				linq.SubmitChanges();
				return "true";
			}

		}
		[WebMethod]
		public string UpdateUser(string UserID,string UserName,string Password, string FirstName, string LastName, string Email, string PhoneNumber, string WeekDaysScore, string WeekendScore, string TotalScore)
		{
			User user = (from User in linq.Users
						 where User.UserID == Convert.ToInt32(UserID)
						 select User).First();
			user.UserName = UserName;
			user.Password = Password;
			user.FirstName = FirstName;
			user.LastName = LastName;
			user.Email = Email;
			user.PhoneNumber = PhoneNumber;
			user.WeekDaysScore = WeekDaysScore;
			user.WeekendScore = WeekendScore;
			user.TotalScore = TotalScore;
			linq.SubmitChanges();
			return "true";
		}
		[WebMethod]
		public string[] GetAllUsersIDs()
		{
			List<User> usersList = (from User in linq.Users
									select User).ToList();
			string[] usersArray = new string[usersList.Count];
			for(int  i = 0; i < usersList.Count; i++)
			{
				usersArray[i] = usersList.ElementAt(i).UserID.ToString();
			}
			return usersArray;
		}
		[WebMethod]
		public string[] GetUser(string UserID)
		{
			User user = (from User in linq.Users
						 where User.UserID == Convert.ToInt32(UserID)
						 select User).First();
			string[] userData = new string[10];
			userData[0] = user.UserID.ToString();
			userData[1] = user.UserName;
			userData[2] = user.Password;
			userData[3] = user.FirstName;
			userData[4] = user.LastName;
			userData[5] = user.Email;
			userData[6] = user.PhoneNumber;
			userData[7] = user.WeekDaysScore;
			userData[8] = user.WeekendScore;
			userData[9] = user.TotalScore;
			return userData;
		}
		[WebMethod]
		public string[] Login(string userName,string password)
		{
			List<User> user = (from User in linq.Users
							   where User.UserName == userName && User.Password == password
							   select User).ToList();
			if(user.Count > 0)
			{
				string[] userData = new string[10];
				userData[0] = user.ElementAt(0).UserID.ToString();
				userData[1] = user.ElementAt(0).UserName;
				userData[2] = user.ElementAt(0).Password;
				userData[3] = user.ElementAt(0).FirstName;
				userData[4] = user.ElementAt(0).LastName;
				userData[5] = user.ElementAt(0).Email;
				userData[6] = user.ElementAt(0).PhoneNumber;
				userData[7] = user.ElementAt(0).WeekDaysScore;
				userData[8] = user.ElementAt(0).WeekendScore;
				userData[9] = user.ElementAt(0).TotalScore;
				return userData;
			}
			else
			{
				return null;
			}
		}
		[WebMethod]
		public string[] InsertPost(string Title, string Description, string Diagram, string UserID)
		{
			Post post = new Post();
			post.Title = Title;
			post.Description = Description;
			post.Diagram = Diagram;
			post.UserID = Convert.ToInt32(UserID);
			DateTime datetime = DateTime.Now;
			post.DateTime = datetime;
			linq.Posts.InsertOnSubmit(post);
			linq.SubmitChanges();

			Post postRuturn = (from Post in linq.Posts
							   where Post.UserID == Convert.ToInt32(UserID) && Post.DateTime == datetime
							   select Post).First();
			string[] postReturnArray = new string[6];
			postReturnArray[0] = postRuturn.PostID.ToString();
			postReturnArray[1] = postRuturn.Title;
			postReturnArray[2] = postRuturn.Description;
			postReturnArray[3] = postRuturn.Diagram;
			postReturnArray[4] = postRuturn.UserID.ToString();
			postReturnArray[5] = postRuturn.DateTime.ToString();
			return postReturnArray;
		}
		[WebMethod]
		public string[] GetAllPosts()
		{
			List<Post> posts = (from Post in linq.Posts
								select Post).ToList();
			string[] postsArray = new string[posts.Count];
			for(int i = 0; i < posts.Count; i++)
			{
				postsArray[i] = posts.ElementAt(i).PostID.ToString();
			}
			return postsArray;
		}
		[WebMethod]
		public string[] GetPost(string PostID)
		{
			Post postRuturn = (from Post in linq.Posts
							   where Post.PostID == Convert.ToInt32(PostID)
							   select Post).First();
			string[] postReturnArray = new string[6];
			postReturnArray[0] = postRuturn.PostID.ToString();
			postReturnArray[1] = postRuturn.Title;
			postReturnArray[2] = postRuturn.Description;
			postReturnArray[3] = postRuturn.Diagram;
			postReturnArray[4] = postRuturn.UserID.ToString();
			postReturnArray[5] = postRuturn.DateTime.ToString();
			return postReturnArray;
		}
	}
}
