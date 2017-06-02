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
			url : "../employee/sameDepartmentEmployees",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				showtable(data);
			},
			error : function(err) {
				alert(err);
			}
		});
	});

	function showtable(data) {
		for ( var i = 0; i < data.length; i++) {
			$('#employeetable')
					.append(
							'<tr><td class="usertablerow1"><div align="left">'
									+ data[i].id
									+ '</div></td><td class="usertablerow1"><div align="left">'
									+ data[i].name
									+ '</div></td><td class="usertablerow1"><div align="left">'
									+ data[i].sex
									+ '</div></td><td class="usertablerow1"><div align="left">'
									+ data[i].age
									+ '</div></td><td class="usertablerow1"><div align="left">'
									+ data[i].department
									+ '</div></td><td class="usertablerow1"><div align="left">'
									+ data[i].position
									+ '</div></td><td class="usertablerow1"><div align="left">'
									+ data[i].hiredate
									+ '</div></td></tr>');
		}
	}
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<label for="department"> 输入姓名查找:</label>
	<input name="name" id="inputtext" type="text">
	<button type="submit" class="positive" onclick="showfindResult();">查找</button>
	<br>
	<table id="employeetable" class="usertableborder" cellspacing="1"
		cellpadding="3" width="96%" align="center" border="0">
		<tr>
			<th colspan="0.5">编号</th>
			<th colspan="0.5">姓名</th>
			<th colspan="0.5">性别</th>
			<th colspan="0.5">年龄</th>
			<th colspan="0.5">部门</th>
			<th colspan="0.5">职位</th>
			<th colspan="0.5">入职时间</th>
		</tr>
	</table>
	<br>
</body>
</html>
