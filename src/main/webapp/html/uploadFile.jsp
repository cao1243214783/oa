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
	function doOnClick(){
		$('#button').before('<p><input type="file" name="file"></p>');
		return false;
	}
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<form action="../files/uploadFile" enctype="multipart/form-data" method="post">
		<p><input type="file" name="file"></p>
		<button id="button" onclick="return doOnClick();">添加</button><br>
		<p><label for="permissionDepartments">指定部门开放权限:</label><input type="text" name="permissionDepartments"></p><br>
		<p><label for="permissionEmployees">指定员工开发权限:</label><input type="text" name="permissionEmployees"></p><br>
		<p>默认为公开</p>
		<button type="submit">上传</button>
	</form>
</body>
</html>
