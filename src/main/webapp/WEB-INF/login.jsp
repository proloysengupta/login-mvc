<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap')
	;

@import
	url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap')
	;

* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Poppins', sans-serif;
}

html, body {
	display: grid;
	height: 100vh;
	width: 100%;
	place-items: center;
	background: linear-gradient(to right, #99004d 0%, #ff0080 100%);
}

::selection {
	background: #ff80bf;
}

.container {
	background: #fff;
	max-width: 350px;
	width: 100%;
	padding: 25px 30px;
	border-radius: 5px;
	box-shadow: 0 10px 10px rgba(0, 0, 0, 0.15);
}

.container form .title {
	font-size: 30px;
	font-weight: 600;
	margin: 20px 0 10px 0;
	position: relative;
}

.container form .title:before {
	content: '';
	position: absolute;
	height: 4px;
	width: 33px;
	left: 0px;
	bottom: 3px;
	border-radius: 5px;
	background: linear-gradient(to right, #99004d 0%, #ff0080 100%);
}

.container form .input-box {
	width: 100%;
	height: 45px;
	margin-top: 25px;
	position: relative;
}

.container form .input-box input {
	width: 100%;
	height: 100%;
	outline: none;
	font-size: 16px;
	border: none;
}

.container form .underline::before {
	content: '';
	position: absolute;
	height: 2px;
	width: 100%;
	background: #ccc;
	left: 0;
	bottom: 0;
}

.container form .underline::after {
	content: '';
	position: absolute;
	height: 2px;
	width: 100%;
	background: linear-gradient(to right, #99004d 0%, #ff0080 100%);
	left: 0;
	bottom: 0;
	transform: scaleX(0);
	transform-origin: left;
	transition: all 0.3s ease;
}

.container form .input-box input:focus ~ .underline::after, .container form .input-box input:valid 
	 ~ .underline::after {
	transform: scaleX(1);
	transform-origin: left;
}

.container form .button {
	margin: 40px 0 20px 0;
}

.container .input-box input[type="submit"] {
	background: linear-gradient(to right, #99004d 0%, #ff0080 100%);
	font-size: 17px;
	color: #fff;
	border-radius: 5px;
	cursor: pointer;
	transition: all 0.3s ease;
}

.container .input-box input[type="submit"]:hover {
	letter-spacing: 1px;
	background: linear-gradient(to left, #99004d 0%, #ff0080 100%);
}

.container .option {
	font-size: 14px;
	text-align: center;
}

.container .facebook a, .container .twitter a {
	display: block;
	height: 45px;
	width: 100%;
	font-size: 15px;
	text-decoration: none;
	padding-left: 20px;
	line-height: 45px;
	color: #fff;
	border-radius: 5px;
	transition: all 0.3s ease;
}

.container .facebook i, .container .twitter i {
	padding-right: 12px;
	font-size: 20px;
}

.container .twitter a {
	background: linear-gradient(to right, #00acee 0%, #1abeff 100%);
	margin: 20px 0 15px 0;
}

.container .twitter a:hover {
	background: linear-gradient(to left, #00acee 0%, #1abeff 100%);
	margin: 20px 0 15px 0;
}

.container .facebook a {
	background: linear-gradient(to right, #3b5998 0%, #476bb8 100%);
	margin: 20px 0 50px 0;
}

.container .facebook a:hover {
	background: linear-gradient(to left, #3b5998 0%, #476bb8 100%);
	margin: 20px 0 50px 0;
}
</style>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<script type="text/javascript">
	function preventBack() {
		window.history.forward()
	};
	setTimeout("preventBack()", 0);
	window.onunload = function() {
		null;
	}
</script>
<body>
	<%
		session.invalidate();
	%>
	<div class="container">
		<form action="/login" method="post">
			<c:if test="${not empty error}">
				<div style="color: red;">
					<h3>${error}</h3>
				</div>
				<div>
					<br>
				</div>
			</c:if>
			<c:if test="${not empty registerSuccess}">
				<div style="color: yellow;">
					<h3>${registerSuccess}</h3>
				</div>
				<div>
					<br>
				</div>
			</c:if>
			<c:if test="${not empty registrationError}">
				<div style="color: red;">
					<h3>${registrationError}</h3>
				</div>
				<div>
					<br>
				</div>
			</c:if>
			<c:if test="${not empty blank}">
				<div style="color: red;">
					<h3>${blank}</h3>
				</div>
				<div>
					<br>
				</div>
			</c:if>
			<div class="title">Login</div>
			<div class="input-box underline">
				<input type="text" placeholder="Enter Your Email" name="userName"
					required="required" value="">
				<div class="underline"></div>
			</div>
			<div class="input-box">
				<input type="password" placeholder="Enter Your Password"
					name="password" required="required" value="">
				<div class="underline"></div>
			</div>
			<div class="input-box button">
				<input type="submit" value="Login" name="">
			</div>
			<div class="input-box button">
				<input type="button" value="Register" onclick="goToRegister()">
			</div>
			<div class="input-box button">
				<input type="button" value="Change Password"
					onclick="goToPassword()">
			</div>
		</form>
	</div>
</body>
</html>

<script type="text/javascript">
	function goToRegister() {
		window.location.href = "/register";
	}
	function goToPassword() {
		window.location.href = "/password";
	}
</script>