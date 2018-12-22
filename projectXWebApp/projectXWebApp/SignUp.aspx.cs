using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using projectXWebApp.remoteService;
namespace projectXWebApp
{
	public partial class SignUp : System.Web.UI.Page
	{
		protected void Page_Load(object sender, EventArgs e)
		{

		}

		protected void Button1_Click(object sender, EventArgs e)
		{
			result.Text = new projectXAsmx().InsertUser(UserName.Text,Password.Text,FirstName.Text,LastName.Text,Email.Text,PhoneNumber.Text,"0","0","0");
		}

		protected void Button2_Click(object sender, EventArgs e)
		{
			Response.Redirect("login.aspx");
		}
	}
}