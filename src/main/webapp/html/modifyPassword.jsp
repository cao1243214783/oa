<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<link href="../css/main.css" type="text/css" rel="stylesheet">
<script language="javascript" src="../js/jquery-3.0.0.js"
	type="text/javascript"></script>
<script type="text/javascript">
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<form action="../user/modifyPassword" method="get">
		<p>
			<label for="old">原密码:</label> <input name="password" id="old"
				type="password">
		</p>
		<p>
			<label for="new">新密码:</label> <input name="newPassword" id="new"
				type="password">
		</p>
		<button type="submit" class="positive" name="Submit">新建</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
	</form>
</body>
</html>
