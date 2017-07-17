<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="errorException.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inquiry Manager - Conversation</title>
<!-- <script type="text/javascript" language="javascript" src="http://www.technicalkeeda.com/js/javascripts/plugin/jquery.js"></script>
<script type="text/javascript" src="http://www.technicalkeeda.com/js/javascripts/plugin/json2.js"></script> -->
<script src="js/jquery.js"></script>
<script src="js/json2.js"></script>
<style>
Table.GridOne {
	padding: 3px;
	margin: 0;
	background: lightblue;
	border-collapse: collapse;
	width: 40%;
}

Table.GridOne Td {
	padding: 2px;
	border: 0px solid #ff9900;
	border-collapse: collapse;
}
</style>
<script type="text/javascript">
	function madeAjaxCall() {
		$.ajax({
			type : "POST",
			url : "/chat",
			cache : false,
			data : 'message=' + $("#message").val(),
			success : function(response) {
				$('#result').html("");
				$('#message').val("");
				var obj = JSON.parse(response);
				$('#result').html(
						"<b><font color=red>Watson:-</font></b><br> "
								+ obj.message);
			},
			error : function() {
				alert('Error while request..');
			}
		});
	}
</script>
</head>
<body onload="madeAjaxCall();">
	<h1>Conversation:</h1>
	<div id="result"></div>
	<br>
	<form name="conversationForm" method="post">
		<table cellpadding="0" cellspacing="0" border="1" class="GridOne">
			<tr>
				<td><textarea name="message" id="message" cols="70" rows="3"></textarea></td>
				<td><input type="button" value="Send" onclick="madeAjaxCall();"></td>
			</tr>
		</table>
	</form>
</body>
</html>