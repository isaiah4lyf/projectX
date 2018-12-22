using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace projectXWebApp
{
	public partial class index : System.Web.UI.Page
	{
		protected void Page_Load(object sender, EventArgs e)
		{
			string[] userData = (string[])Session["user"];
		}

		protected void Button1_Click(object sender, EventArgs e)
		{
			Response.Redirect("gameServers.aspx");
		}
	}
}