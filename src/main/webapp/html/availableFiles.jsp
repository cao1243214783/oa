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
	var aa= "";
	$(document).ready(function() {
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../files/showAvailableEmployees",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				for(var i=0;i<data.length;i++){
					$('#filesTable').append('<tr><td class="usertablerow2" width="20%">'+data[i].filename+'</td><td class="usertablerow2" width="20%">'+data[i].employeeName+'</td><td class="usertablerow2" width="20%">'+data[i].uploadtime+'</td><td class="usertablerow2" width="10%">'+data[i].size+'</td><td class="usertablerow2" width="20%"><a href="../files/downloadFile?id='+data[i].id+'">下载</a> <input type="checkbox" id="file'+data[i].id+'" name="'+data[i].id+'">批量下载</label></td></tr>');
					$("#file"+data[i].id).change(function() {
						aa= aa+$(this).attr("name")+"_";
					});
				}
			},
			error : function(err) {
				alert(err);
			}
		});
	});
	
	function onclickFun(){
		window.location.href="../files/downloadFiles?ids="+aa;
	};
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<br>
	<table id="filesTable" class="filestableborder" cellspacing="1"
		cellpadding="3" width="96%" align="center" border="0">
		<tr>
			<th colspan="0.5">文件名</th>
			<th colspan="0.5">上传者</th>
			<th colspan="0.5">上传时间</th>
			<th colspan="0.5">文件大小</th>
			<th colspan="0.5">操作</th>
		</tr>
	</table>
	<br>
	<button id="download" onclick="onclickFun();">批量下载</button>
</body>
</html>
