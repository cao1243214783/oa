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
	function validation() {
		var d = new Date();  
	    var dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate().toString();  
	    var mm = d.getMonth()+1 < 10?"0" + (d.getMonth()+1) : (d.getMonth()+1).toString();  
	    var yyyy = d.getFullYear().toString();
	    var time = yyyy+"-"+mm+"-"+dd; 
		var time2 = $('#time').val();
		if(time>time2){
			alert("请假时间不能早于今天");
			return false;
		}else{
			submit(time2);
		}
	}
	function submit(time){
		var description = $('#description').val();
		$.ajax({
			type : "Post",
			url : "../attendance/askForLeave?time="+time+"&description="+description,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				if(data.result=="SUCCESS"){
					window.location.reload();
				}else{
					alert("提交失败");
				}
			},
			error : function(err) {
				alert(err);
			}
		});
	}
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
		<p>
			<label for="time">时间:</label> <input name="time"
				id="time" type="date" >
		</p><p>
			<label for="description">原因:</label> <br>
			<textarea id="description" name="description" style="width:200px;height:80px;"></textarea>
		</p>
		<button class="positive"  class="positive" type="submit" onclick="validation();">新建</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
</body>
</html>
