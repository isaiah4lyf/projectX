<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="SignUp.aspx.cs" Inherits="projectXWebApp.SignUp" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
        <div>
			<asp:TextBox ID="UserName" runat="server" placeholder="Username"></asp:TextBox><br/>
			<asp:TextBox ID="Password" runat="server" placeholder="Password"></asp:TextBox><br/>
			<asp:TextBox ID="FirstName" runat="server" placeholder="First Name"></asp:TextBox><br/>
			<asp:TextBox ID="LastName" runat="server" placeholder="Last Name"></asp:TextBox><br/>
			<asp:TextBox ID="Email" runat="server" placeholder="Email"></asp:TextBox><br/>
			<asp:TextBox ID="PhoneNumber" runat="server" placeholder="Phone Number"></asp:TextBox><br/>
			<asp:Label ID="result" runat="server" Text=""></asp:Label><br/>
			<asp:Button ID="Button1" runat="server" Text="Sign Up" OnClick="Button1_Click" /><br/>
			<asp:Button ID="Button2" runat="server" Text="Sign in" OnClick="Button2_Click" />
        </div>
    </form>
</body>
</html>
