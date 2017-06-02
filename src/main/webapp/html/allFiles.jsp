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
			url : "../files/getAllFiles",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				for(var i=0;i<data.length;i++){
					$('#filesTable').append('<tr><td class="usertablerow2" width="20%">'+data[i].filename+'</td><td class="usertablerow2" width="10%">'+data[i].uploadtime+'</td><td class="usertablerow2" width="10%">'+data[i].employee+'</td><td class="usertablerow2" width="10%">'+data[i].size+'</td><td class="usertablerow2" width="20%">'+data[i].departments+'</td><td class="usertablerow2" width="20%">'+data[i].employees+'</td><td class="usertablerow2" width="20%"><a href="../html/alterFiles.jsp?department='+data[i].departments+'&employee='+data[i].employees+'&id='+data[i].id+'">修改</a> <a href="../files/downloadFile?id='+data[i].id+'">下载</a> <a href="../files/delete?id='+data[i].id+'">删除</a></td></tr>');
				}
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
	<table id="filesTable" class="filestableborder" cellspacing="1"
		cellpadding="3" width="96%" align="center" border="0">
		<tr>
			<th colspan="0.5">文件名</th>
			<th colspan="0.5">上传时间</th>
			<th colspan="0.5">上传用户</th>
			<th colspan="0.5">文件大小</th>
			<th colspan="0.5">可使用用户</th>
			<th colspan="0.5">可使用部门</th>
			<th colspan="0.5">操作</th>
		</tr>
	</table>
	<br>
</body>
</html>
