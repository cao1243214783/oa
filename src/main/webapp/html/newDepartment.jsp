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

	$(document).ready(function() {
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../department/getAvailableSup",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				for(var i=0;i<data.length;i++){
					$('#supDepartment').append('<label><input name="supDepartment" onclick="onselectchange($(this).val());" type="radio" value="'+data[i].id+'" />'+data[i].name+' </label><tt>');
					if((i+1)%5==0){
						$('#supDepartment').append("<br/>");
					}
				}
			},
			error : function(err) {
				alert(err);
			}
		});
	});
	$(document).ready(function() {
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../department/getAvailableSon?id=0",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				$('#sonDepartment').empty();
				for(var i=0;i<data.length;i++){
					$('#sonDepartment').append('<label><input name="sonDepartment" type="checkbox" value="'+data[i].id+'" />'+data[i].name+' </label><tt>');
					if((i+1)%5==0){
						$('#sonDepartment').append("<br/>");
					}
				}
			},
			error : function(err) {
				alert(err);
			}
		});
	
	});
	function onselectchange(id){
			$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../department/getAvailableSon?id="+id,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				$('#sonDepartment').empty();
				for(var i=0;i<data.length;i++){
					$('#sonDepartment').append('<label><input name="sonDepartment" type="checkbox" value="'+data[i].id+'" />'+data[i].name+' </label><tt>');
					if((i+1)%5==0){
						$('#sonDepartment').append("<br/>");
					}
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
	<form action="../department/newDepartment" method="get"  class="form-inline">
		<p>
			<label for="name">部门名:</label> <input class="form-control input-lg"  name="name"
				id="name" type="text" >
		</p><br/><p>
			<label for="supDepartment">上级部门:</label>
			<br /><br /> 
			<div id="supDepartment">
			</div>
		</p><br/><p>
			<label for="sonDepartment">下级部门:</label>
			<br /><br /> 
			<div id="sonDepartment">
			</div>
		</p><br/>
		<button type="submit" class="positive" name="Submit">新建</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
	</form>
</body>
</html>
