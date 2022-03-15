<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<form action="/login.do" method="post">
		Email : <input type="text" name="email" required><br>
		Password : <input type="password" name="password" required><br>
		<input type="submit" value="Log In">
	</form>
</body>
</html>