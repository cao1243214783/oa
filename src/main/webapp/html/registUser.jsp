<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link href="../css/main.css" type="text/css" rel="stylesheet">
<script language="javascript" src="../js/jquery-3.0.0.js"
	type="text/javascript"></script>
<script type="text/javascript"> 
function ifUserNameAvailable(){
	var username = $('#username').val();
	var password = $('#password').val();
	var employeeId = $('#employeeId').val();
	$.ajax({
			type : "Post",
			url : "../user/save?username="+username+"&password="+password+"&employeeId="+employeeId,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				alert(data.result);
			},
			error : function(err) {
				alert(err);
			}
		});
}
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
<form method="get" class="form-inline">
		<p>
			<label for="username">用户名:</label> <input class="form-control input-lg"  name="username"
				id="username" type="text" >
		</p><p>
			<label for="password">密码:</label> <input class="form-control input-lg"  name="password"
				id="password" type="password" >	
		</p><p>
			<label for="employeeId">员工ID:</label> <input class="form-control input-lg"  name="employeeId"
				id="employeeId" type="text" >
		</p>
		<button class="positive" onclick="return ifUserNameAvailable();return false;">新建</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
		</form>
</body>
</html>
