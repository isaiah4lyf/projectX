<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="ViewPost.aspx.cs" Inherits="projectXWebApp.ViewPost" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link href="CSS/ViewPost.css" rel="stylesheet"/>
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
	<div id="menu">
		<div><a href="#"><i class="fa fa-fw fa-user-circle"></i>Home</a></div><br/>
		<div><a href="#"><i class="fa fa-fw fa-home"></i>My Profile</a></div><br/>
		<div><a href="#"><i class="fa fa-fw fa-home"></i>Friends</a></div><br/>
		<div><a href="#"><i class="fa fa-fw fa-home"></i>Groups List</a></div><br/>
		<div><a href="#"><i class="fa fa-fw fa-home"></i>Notifications</a></div><br/>
		<div><a href="#"><i class="fa fa-fw fa-question-circle"></i>Help</a></div><br/>
	</div>
	<div id="liveBox">
		<div></div>
		<input id="liveInput" type="text" class="form-control" placeholder="Type Comment" />
		<input id="liveButton" class="btn btn-primary btn-sm" value="submit" type="button"/> 
	</div>
	<div id="chatBox">
		<br/>
		<div class="darker">
			<p style="width:90%">Hey! I'm fine. Thanksess sssssssssss ssssssssss sssssssssssssssss ssssssss sssssssssss for asking!</p>
			<span class="time-right">11:01</span>
		</div>
		<input id="chatInput" type="text" class="form-control" placeholder="Type Message" />
		<input id="chatButton" class="btn btn-primary btn-sm" value="submit" type="button"/> 
	</div>
	<div id="content1">
		<br/><br/><br/><br/><br/><br/>	
		<div style="width:95%;margin:auto" id="content" ></div>
		<br/><br/><br/><br/><br/><br/><br/><br/>
	</div>
	<script src="Javascript/ViewPost.js"></script>
</body>
</html>
