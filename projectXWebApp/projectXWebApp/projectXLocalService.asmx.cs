using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using projectXWebApp.remoteService;
namespace projectXWebApp
{
	/// <summary>
	/// Summary description for projectXLocalService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	[System.Web.Script.Services.ScriptService]
	public class projectXLocalService : System.Web.Services.WebService
	{
		private projectXAsmx remoteService = new projectXAsmx();

		[WebMethod]
		public string[] GetAllPosts()
		{
			string[] array = remoteService.GetAllPosts();
			Array.Reverse(array);
			return array;
		}
	}
}
