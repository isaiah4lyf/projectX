<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="gameSession.aspx.cs" Inherits="projectXWebApp.gameSession" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link href="CSS/JoinGameSessionCSS.css" rel="stylesheet" />
</head>
<body>
		<nav id="topNav" class="navbar navbar-inverse navbar-fixed-top">
			  <div class="container-fluid">
				<div id="ff">
					<a href="#"><i class="fa fa-fw fa-user-circle"></i></a>
					<a href="#"><i class="fa fa-fw fa-envelope-o"></i></a>
					<a href="#"><i class="fa fa-fw fa-camera-retro"></i></a>
					<a href="#"><i class="fa fa-fw fa-bar-chart"></i></a>
					<a href="#"><i class="fa fa-fw fa-cog"></i></a>
					<a href="#"><i class="fa fa-fw fas fa-bell"></i></a>
					<a href="#"><i class="fa fa-fw fa-exclamation-circle"></i></a>
					<a href="#"><i class="fa fa-fw fa-question-circle"></i></a>
				</div>

				<form style="position:relative;right:50px;bottom:28px" class="navbar-form navbar-right">
				  <div  class="form-group">
					<i style="font-size:14px" class="fa fa-fw fa-hourglass-2"></i>
					<label style="font-size:18px">||</label>
					<input type="text" class="form-control" id="time"/>
				  </div>

				</form>
			  </div>
			</nav>
			<br/><br/><br/><br/>	
			<div id="content1"><div id="content" ></div>
			<br/><br/><br/><br/><br/><br/><br/><br/>
			</div>

			<nav id="bottomNav" class="navbar navbar-inverse navbar-fixed-bottom">
			  <div id="inputBox" class="container-fluid">
				<form  class="navbar-form" action="/action_page.php">
				  <div id="timeForm" class="form-group">
					<input type="text" class="form-control" placeholder="Search" id="textMessage"/>
				  </div>
				  <input id="button" class="btn btn-primary btn-sm" onclick="sendMessage();"  value="submit" type="button"/> 
				</form>
			  </div>
			</nav>
	<script src="Javascript/JoinGameSession.js"></script>
</body>
</html>
