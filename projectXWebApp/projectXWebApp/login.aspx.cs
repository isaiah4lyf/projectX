using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using projectXWebApp.remoteService;
namespace projectXWebApp
{
	public partial class login : System.Web.UI.Page
	{
		protected void Page_Load(object sender, EventArgs e)
		{

		}

		protected void Button1_Click(object sender, EventArgs e)
		{
			if(userName.Text != "" && password.Text != "")
			{
				string[] userData = new projectXAsmx().Login(userName.Text, password.Text);
				if (userData != null)
				{
					Session["user"] = userData;
					Response.Redirect("index.aspx");
				}
				else
				{
					result.Text = "Wrong username or password!";
				}
			}

		}

		protected void Button2_Click(object sender, EventArgs e)
		{
			Response.Redirect("SignUp.aspx");
		}
	}
}