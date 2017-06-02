<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人事管理系统</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />
</head>



<body style="background-image:url(images/bg.jpg);height:100%;width:100%;overflow: hidden;background-size:cover;">
	<br />
	<br />
	<br />
	<br />
	<center>
		<div id="wrapper">
			<div id="content">
				<div id="header">
					<h1>
						<h2>人事管理系统</h2>
					</h1>
				</div>
				<div id="darkbannerwrap"></div>
				<form action="login/adminlogin" method="post">
					<fieldset class="form">
						<p>
							<label for="username">账号:</label> <input name="username"
								id="username" type="text">
						</p>
						<p>
							<label for="password">密码:</label> <input name="password"
								id="password" type="password">
						</p>
						<button type="submit" class="positive" name="Submit">登录</button>
						<button type="button" class="positive" onclick="aa()">
							退出</button>
					</fieldset>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;
				</form>
			</div>
		</div>
	</center>
</body>
</html>