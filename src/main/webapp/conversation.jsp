<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%-- <%@ page errorPage="errorException.jsp"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>AIWizard - IBM Bluemix - Watson Conversation</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery.min.js"></script>
<script src="js/jquery.js"></script>
<script src="js/json2.js"></script>
<script src="js/date.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style type="text/css">
body {
	margin-top: 10px;
	background: #CEE3F6;
}

.panel {
	box-shadow: 0 2px 0 rgba(0, 0, 0, 0.075);
	border-radius: 0;
	border: 0;
	margin-bottom: 10px;
}

.panel .panel-heading, .panel>:first-child {
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}

.panel-heading {
	position: relative;
	height: 40px;
	padding: 0;
	border-bottom: 1px solid #eee;
}

.panel-control {
	height: 100%;
	position: relative;
	float: right;
	padding: 0 5px;
}

.panel-title {
	font-weight: normal;
	padding: 0 5px 0 5px;
	font-size: 1.200em;
	line-height: 50px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.panel-control>.btn:last-child, .panel-control>.btn-group:last-child>.btn:first-child
	{
	border-bottom-right-radius: 0;
}

.panel-control .btn, .panel-control .dropdown-toggle.btn {
	border: 0;
}

.nano {
	position: relative;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.nano>.nano-content {
	position: absolute;
	overflow: scroll;
	overflow-x: hidden;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
}

.pad-all {
	padding: 10px;
}

.mar-btm {
	margin-bottom: 10px;
}

.media-block .media-left {
	display: block;
	float: left;
}

.img-sm {
	width: 30px;
	height: 30px;
}

.media-block .media-body {
	display: block;
	overflow: hidden;
	width: auto;
}

.pad-hor {
	padding-left: 10px;
	padding-right: 10px;
}

.speech {
	position: relative;
	background: #b7dcfe;
	color: #317787;
	display: inline-block;
	border-radius: 0;
	padding: 2px 5px;
}

.speech:before {
	content: "";
	display: block;
	position: absolute;
	width: 0;
	height: 0;
	left: 0;
	top: 0;
	border-top: 5px solid transparent;
	border-bottom: 5px solid transparent;
	border-right: 5px solid #b7dcfe;
	margin: 5px 0 0 -6px;
}

.speech-right>.speech:before {
	left: auto;
	right: 0;
	border-top: 5px solid transparent;
	border-bottom: 5px solid transparent;
	border-left: 5px solid #ffdc91;
	border-right: 0;
	margin: 5px -6px 0 0;
}

.speech .media-heading {
	font-size: .9em;
	color: #317787;
	display: block;
	border-bottom: 1px solid rgba(0, 0, 0, 0.1);
	margin-bottom: 3px;
	padding-bottom: 3px;
	font-weight: 300;
}

.speech-time {
	margin-top: 3px;
	margin-bottom: 0;
	font-size: .8em;
	font-weight: 300;
}

.media-block .media-right {
	float: right;
}

.speech-right {
	text-align: right;
}

.pad-hor {
	padding-left: 5px;
	padding-right: 5px;
}

.speech-right>.speech {
	background: #ffda87;
	color: #a07617;
	text-align: right;
}

.speech-right>.speech .media-heading {
	color: #a07617;
}

.btn-primary, .btn-primary:focus, .btn-hover-primary:hover,
	.btn-hover-primary:active, .btn-hover-primary.active, .btn.btn-active-primary:active,
	.btn.btn-active-primary.active, .dropdown.open>.btn.btn-active-primary,
	.btn-group.open .dropdown-toggle.btn.btn-active-primary {
	background-color: #579ddb;
	border-color: #5fa2dd;
	color: #fff !important;
}

.btn {
	cursor: pointer;
	/* background-color: transparent; */
	color: inherit;
	padding: 3px 5px;
	border-radius: 0;
	border: 1px solid 0;
	font-size: 11px;
	line-height: 1.42857;
	vertical-align: middle;
	-webkit-transition: all .25s;
	transition: all .25s;
}

.form-control {
	font-size: 11px;
	height: 100%;
	border-radius: 0;
	box-shadow: none;
	border: 1px solid #e9e9e9;
	transition-duration: .5s;
}

.nano>.nano-pane {
	background-color: rgba(0, 0, 0, 0.1);
	position: absolute;
	width: 5px;
	right: 0;
	top: 0;
	bottom: 0;
	opacity: 0;
	-webkit-transition: all .27s;
	transition: all .27s;
}

input.rounded {
	border: 1px solid #ccc;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;
	-moz-box-shadow: 2px 2px 3px #666;
	-webkit-box-shadow: 2px 2px 3px #666;
	box-shadow: 2px 2px 3px #666;
	font-size: 15px;
	padding: 4px 7px;
	outline: 0;
	-webkit-appearance: none;
	width: 430px;
}

input.rounded:focus {
	border-color: #339933;
}
</style>
</head>
<body onload="loadBody();">
	<div class="container">
		<div class="col-md-12 col-lg-6">
			<div class="panel">
				<!--Heading-->
				<div class="panel-heading">
					<div class="panel-control">
						<div class="btn-group">
							<button class="btn btn-default" type="button" data-toggle="collapse" data-target="#demo-chat-body">
								<i class="fa fa-chevron-down"></i>
							</button>
						</div>
					</div>
					<h3 class="panel-title">Watson Conversation</h3>
				</div>

				<!--Widget body-->
				<div id="demo-chat-body" class="collapse in">
					<div id="scrollView" class="nano has-scrollbar" style="height: 450px">
						<div class="nano-content pad-all" id="nanoContentID" tabindex="0" style="right: -17px;">
							<ul class="list-unstyled media-block">
								<div id="listView"></div>
							</ul>
						</div>
						<div class="nano-pane">
							<div class="nano-slider" style="height: 141px; transform: translate(0px, 0px);"></div>
						</div>
					</div>

					<!--Widget footer-->
					<div class="panel-footer">
						<!-- <div class="row"> -->
						<!-- <div class="col-xs-9"> -->
						<input type="text" id="message" name="message" placeholder="Enter your text" class="rounded">
						<!-- </div> -->
						<!-- <div class="col-xs-3"> -->
						<img src="images/record_off.jpg" id="speech" class="img-circle" height="32" width="32" style="cursor: pointer">
						<img src="images/send.jpg" id="send" name="send" class="img-circle" height="32" width="32" style="cursor: pointer">
						<!-- <button class="btn btn-primary btn-block" id="send" name="send" type="submit">Send</button> -->
						<!-- </div> -->
						<!-- </div> -->
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$("#message").keyup(function(event) {
			if (event.keyCode == 13) {
				$("#send").click();
			}
		});

		function loadBody() {
			$
					.ajax({
						type : "POST",
						url : "/chat",
						data : {
							message : null
						},
						cache : false,
						success : function(response) {
							var obj = JSON.parse(response);
							var watson = '<li class="mar-btm">'
									+ '<div class="media-right">'
									+ '<img src="images/watson.png" class="img-circle img-sm" alt="Profile Picture">'
									+ '</div>'
									+ '<div class="media-body pad-hor speech-right">'
									+ '<div class="speech">'
									//+ '<a href="#" class="media-heading">Watson</a>'
									+ '<p><a href="javascript:void(0);" onclick="return audioplay(\''
									+ obj.responseMsg + '\'); return false;">'
									+ obj.responseMsg + '</a></p>'
									+ '<p class="speech-time">'
									+ '<i class="fa fa-clock-o fa-fw"></i> '
									+ new Date().toString("hh:mm tt") + ''
									+ '</p>' + '</div>' + '</div>' + '</li>';

							$('#listView').append(watson);

							var objDiv = document
									.getElementById("nanoContentID");
							objDiv.scrollTop = objDiv.scrollHeight;
							document.getElementById("message").value = "";
							document.getElementById("message").focus();

						},
						error : function(xhr, status, error) {
							var err = eval("(" + xhr.responseText + ")");
							alert(err.Message);
						}
					});
		}

		// Send Button
		$('#send')
				.click(
						function() {

							var msg = $('#message').val();

							var user = '<li class="mar-btm">'
									+ '<div class="media-left">'
									+ '<img src="images/avatar1.png" class="img-circle img-sm" alt="Profile Picture">'
									+ '</div>'
									+ '<div class="media-body pad-hor">'
									+ '<div class="speech">'
									//+ '<a href="#" class="media-heading">Guest</a>'
									+ '<p>' + msg + '</p>'
									+ '<p class="speech-time">'
									+ '<i class="fa fa-clock-o fa-fw"></i>'
									+ new Date().toString("hh:mm tt") + ''
									+ '</p>' + '</div>' + '</div>' + '</li>';
							$('#listView').append(user);

							$("#send").attr("disabled", true);

							var objDiv = document
									.getElementById("nanoContentID");
							objDiv.scrollTop = objDiv.scrollHeight;

							$
									.ajax({
										type : "POST",
										url : "/chat",
										data : {
											message : $('#message').val()
										},
										cache : false,
										success : function(response) {
											var obj = JSON.parse(response);
											//$('#message').val(obj.responseMsg)
											//alert(" Message: " + obj.responseMsg);
											var watson = '<li class="mar-btm">'
													+ '<div class="media-right">'
													+ '<img src="images/watson.png" class="img-circle img-sm" alt="Profile Picture">'
													+ '</div>'
													+ '<div class="media-body pad-hor speech-right">'
													+ '<div class="speech">'
													//+ '<a href="#" class="media-heading">Watson</a>'
													+ '<p><a href="javascript:void(0);" onclick="return audioplay(\''
													+ obj.responseMsg
													+ '\'); return false;">'
													+ obj.responseMsg
													+ '</a></p>'
													+ '<p class="speech-time">'
													+ '<i class="fa fa-clock-o fa-fw"></i> '
													+ new Date()
															.toString("hh:mm tt")
													+ '' + '</p>' + '</div>'
													+ '</div>' + '</li>';

											$('#listView').append(watson);

											var objDiv = document
													.getElementById("nanoContentID");
											objDiv.scrollTop = objDiv.scrollHeight;

											document.getElementById("message").value = "";
											document.getElementById("message")
													.focus();
											$("#send").attr("disabled", false);

										},
										error : function(xhr, status, error) {
											var err = eval("("
													+ xhr.responseText + ")");
											alert(err.Message);
										}
									});

						});

		//Speech button
		var speechRecognizer = new webkitSpeechRecognition();
		speechRecognizer.continuous = true;
		speechRecognizer.interimResults = true;
		$('#speech')
				.click(
						function() {

							//var speechRecognizer = new webkitSpeechRecognition();
							//speechRecognizer.continuous = true;
							//speechRecognizer.interimResults = true;
							var image = document.getElementById('speech');
							var speechStatus = "";
							if (image.src.match("on")) {
								speechRecognizer.stop();
								image.src = "images/record_off.jpg";
								return;
							} else {
								image.src = "images/record_on.jpg";
							}

							if ('webkitSpeechRecognition' in window) {
								speechRecognizer.lang = 'en-IN';
								speechRecognizer.start();

								var finalTranscripts = '';

								speechRecognizer.onresult = function(event) {
									var interimTranscripts = '';
									for (var i = event.resultIndex; i < event.results.length; i++) {
										var transcript = event.results[i][0].transcript;
										transcript.replace("\n", "<br>");
										if (event.results[i].isFinal) {
											finalTranscripts += transcript;
										} else {
											interimTranscripts += transcript;
										}
									}
									document.getElementById('message').value = finalTranscripts
											+ interimTranscripts;
								};
								speechRecognizer.onerror = function(event) {
								};
							} else {
								document.getElementById('message').value = 'Your browser is not supported. If google chrome, please upgrade!';
							}
						});

		//Audio play
		function audioplay(audioMsg) {
			$.ajax({
				type : "post",
				url : "/tts",
				data : {
					text : audioMsg
				},
				cache : false,
				success : function(response) {
				},
				error : function() {
					alert('Error while request..');
				}
			});
		}
	</script>
</body>
</html>