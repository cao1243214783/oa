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
			url : "../attendance/getAllAttendance",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				for(var i=0;i<data.length;i++)
					$('#attendanceTable').append('<tr><td class="usertablerow2" width="10%">'+data[i].empid+'</td>	<td class="usertablerow2" width="10%">'+data[i].name+'</td><td class="usertablerow2" width="10%">'+data[i].time+'</td><td class="usertablerow2" width="60%">'+data[i].description+'</td><td class="usertablerow2" width="10%"><a href="../attendance/examinationApprovalRequest?id='+data[i].id+'&isAllowed=1">提准</a> <a href="../attendance/examinationApprovalRequest?id='+data[i].id+'&isAllowed=1">不批准</a></td></tr>');
			},
			error : function(err) {
				alert(err);
			}
		});
	});
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<br>
	<table id="attendanceTable" class="attendanceTable" cellspacing="1"
		cellpadding="3" width="96%" align="center" border="0">
		<tr>
			<th colspan="0.5">编号</th>
			<th colspan="0.5">姓名</th>
			<th colspan="0.5">时间</th>
			<th colspan="1.0">原因</th>
			<th colspan="1.0">操作</th>
		</tr>
	</table>
	<br>
</body>
</html>
