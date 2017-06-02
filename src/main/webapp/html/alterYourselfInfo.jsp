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
	$(document).ready(function() {
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../employee/getEmployeeDetails",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				$('#name').val(data.name);
				$('#card').val(data.card);
			},
			error : function(err) {
				alert(err);
			}
		});
	});
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<form action="../employee/alterYourselfInfo" method="get">
		<p>
			<label for="name">姓名:</label> <input name="name"
				id="name" type="text" value="">
		</p><p>
			<label for="birthday">生日:</label> <input name="birthday"
				id="birthday" type="date" value="">
		</p><p>
			<label for="card">工资卡:</label> <input name="card"
				id="card" type="text" value="">
		</p>
		<button type="submit" class="positive" name="Submit">修改</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
	</form>
</body>
</html>
