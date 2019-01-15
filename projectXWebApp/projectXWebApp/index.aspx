<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="index.aspx.cs" Inherits="projectXWebApp.index" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
	<script src="Javascript/indexScript.js"></script>
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
		<asp:ScriptManager ID="ScriptManager1" runat="server">
			<Services>
				<asp:ServiceReference Path="~/projectXLocalService.asmx"/>
			</Services>
		</asp:ScriptManager>
        <div>
			<asp:Button ID="Button1" runat="server" Text="Play" OnClick="Button1_Click" />
        </div>
    </form>
	<div id="Posts">
	</div>
</body>
</html>
