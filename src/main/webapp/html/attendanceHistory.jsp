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
			url : "../attendance/getAllAttendanceOfEmployee",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				for(var i =0;i<data.length;i++){
					$('#employeetable').append('<tr><td class="usertablerow2" width="20%">'+data[i].leaveDate+'</td><td class="usertablerow2" width="30%">'+data[i].description+'</td><td class="usertablerow2" width="10%">'+data[i].appId+'</td><td class="usertablerow2" width="20%">'+data[i].processTime+'</td><td class="usertablerow2" width="10%">'+data[i].isAllowed+'</td><td class="usertablerow2" width="10%">'+canCancel(data[i].id,data[i].isAllowed)+'</td></tr>');
				}
			},
			error : function(err) {
				alert(err);
			}
		});
	});

	function refreshAndDelete(id) {
		url = "../employee/deleteEmployee?id=" + id;
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : url,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				if (data.result == "SUCCESS") {
					window.location.reload();
				} else {
					alert("操作失败！");
				}
			},
			error : function(err) {
				alert("操作失败！");
			}
		});
	};

	function canCancel(id,isAllowed){
		var str = '<a href="../attendance/cancelRequest?id='+id+'">取消</a>';
		if(isAllowed=="未处理")
			return str;
		return "";
	}
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<br>
	<table id="employeetable" class="employeetable" cellspacing="1"
		cellpadding="3" width="96%" align="center" border="0">
		<tr>
			<th colspan="0.5">时间</th>
			<th colspan="0.5">原因</th>
			<th colspan="0.5">审批人</th>
			<th colspan="0.5">审批时间</th>
			<th colspan="0.5">结果</th>
			<th colspan="0.5">操作</th>
		</tr>
	</table>
	<br>
</body>
</html>
