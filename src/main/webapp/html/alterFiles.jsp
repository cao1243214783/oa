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
	<form action="../files/alterFilesInfo" method="get">
		<p>
			<label for="departments">部门权限:</label> <input name="departments"
				id="name" type="text" value="${param.department}">
		</p><p>
			<label for="employees">员工权限:</label> <input name="employees"
				id="birthday" type="text" value="${param.employee}">
		</p>
		<input name="id" id="id" type="hidden" value="${param.id}">
		<button type="submit" class="positive" name="Submit">修改</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
	</form>
</body>
</html>
