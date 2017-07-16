<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>
	<form method="post" action="/conversation">
		<table>
			<tr>
				<td><label for="name">Username:</label></td>
				<td><input type="text" name="username" id="username"
					value="test"></td>
			</tr>
			<tr>
				<td><label for="name">Password:</label></td>
				<td><input type="password" name="password" id="password"
					value="test"></td>
			</tr>
			<tr>
				<td colspan=2><input type="submit" value="Login"></td>
			</tr>
		</table>
	</form>
	<br>
	<br>
	<div align="center">
	        <h1>Contact List</h1>
        	<table border="1">
	        	<th>ID</th>
	        	<th>USERNAME</th>
	        	<th>PASSWORD</th>
	        	
				<c:forEach var="user" items="${userList}" varStatus="status">
	        	<tr>
	        		<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.password}</td>
	        	</tr>
				</c:forEach>	        	
        	</table>	
        </div>
</body>
</html>
