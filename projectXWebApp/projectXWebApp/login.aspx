<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="login.aspx.cs" Inherits="projectXWebApp.login" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
        <div>
			<asp:TextBox ID="userName" runat="server" placeholder="Username"></asp:TextBox><br/>
			<asp:TextBox ID="password" runat="server" placeholder="Password"></asp:TextBox><br/>
			<asp:Label ID="result" runat="server" Text=""></asp:Label><br/>
			<asp:Button ID="Button1" runat="server" Text="Sign in" OnClick="Button1_Click" /><br/>
			<asp:Button ID="Button2" runat="server" Text="Sign Up" OnClick="Button2_Click" />
        </div>
    </form>
</body>
</html>
