<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fintech - Inquiry Manager</title>

<style>
.myTableStyle {
	/*
           position:absolute;
           top:50%;
           left:50%; 
		*/
	/*Alternatively you could use: */
	position: fixed;
	bottom: 50%;
	right: 50%;
}
.roundedCorner { width:300px;
	height: 140px;
	border: solid 1px #555;
	background-color: #0431B4;
	box-shadow: 10px -10px 5px rgba(0, 0, 0, 0.6);
	-moz-box-shadow: 10px -10px 5px rgba(0, 0, 0, 0.6);
	-webkit-box-shadow: 10px -10px 5px rgba(0, 0, 0, 0.6);
	-o-box-shadow: 10px -10px 5px rgba(0, 0, 0, 0.6);
	border-radius: 25px;
}
label {
color: #EFFBFB;
font-weight: bold;
display: block;
width: 100px;
float: right;
font-family: Times New Roman;
font-size: 20px;
}
.btn {
--color: #EFFBFB;
font-weight: bold;
--display: block;
--width: 100px;
--float: right;
font-family: Times New Roman;
font-size: 15px;
}
.inputText {
font-weight: bold;
font-family: Times New Roman;
font-size: 15px;
}
select {
font-weight: bold;
font-family: Times New Roman;
font-size: 15px;
}
</style>
</head>
<body bgcolor="#CEE3F6">
	<%
		session.removeAttribute("message");
		session.removeAttribute("language");
		session.removeAttribute("context");
		session.removeAttribute("name");
	%>
	<form method="post" action="/conversation">
		<div class="myTableStyle">
			<table align="center" class="roundedCorner" border="0" cellpadding="5">
				<tr>
					<td><label for="name" style="bgcolor=white;">Name:</label></td>
					<td><input class="inputText" type="text" name="name" id="name" value="Guest"></td>
				</tr>

				<tr>
					<td><label for="name">Language:</label></td>
					<td><select name="language">
							<option value="ENGLISH">ENGLISH</option>
							<option value="SPANISH">SPANISH</option>
							<option value="FRENCH">FRENCH</option>
							<option value="GERMAN">GERMAN</option>
					</select></td>
				</tr>
				<tr>
					<td align="center" colspan=2><input class="btn" type="submit" value="Login"></td>
				</tr>
			</table>
			<br>
			<div align="center">Video Demo : <a href="/video/Watson_English.webm" target="_blank">Watson-English</a> / <a href="/video/Watson_Spanish.webm" target="_blank">Watson-Spanish</a></div>
			
		</div>
	</form>
	
	
	
</body>
</html>
