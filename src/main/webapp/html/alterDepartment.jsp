<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<link href="../css/main.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="../css/bootstrap.min.css">
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
					$('#supDepartment').append('<label><input name="supDepartment" onclick="onselectchange($(this).val());" type="radio" value="'+data[i].id+'"  id="department'+data[i].id+'"/>'+data[i].name+' </label><tt>');
					if((i+1)%5==0){
						$('#supDepartment').append("<br/>");
					}
				}
			},
			error : function(err) {
				alert(err);
			}
		});
		$('#name').bind('change propertychange', function() { 
			 var name = $('#name').val();
			 $.ajax({
				type : "Post",
				//方法所在页面和方法名      
				url : "../department/getDepartmentByName?name="+name,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					if(data.result=="FAILURE"){
						alert("部门不存在");
					}else if(data.result=="SUCCESS"){
						$('#department'+data.sup).prop('checked',true);
						onselectchange(data.sup);
						var array = data.sons;
						for(var i=0;i<array.length;i=i+2){
							$('#sonDepartment').append('<label><input name="sonDepartment" type="checkbox" value="'+array[i]+'" checked="checked"/>'+array[i+1]+' </label><tt>');
							if(((i+1)/2)%5==0){
								$('#sonDepartment').append("<br/>");
							}
						}
					}
				},
				error : function(err) {
					alert(err);
				}
			});
		});  
	});
	function onselectchange(id){
		$('#sonDepartment').empty();
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../department/getAvailableSon?id="+id,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
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
	<form action="../department/alterDepartment" method="get"  class="form-inline">
		<p>
			<label for="name">部门名:</label> <input  class="form-control input-lg"  name="name"
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
		<button type="submit" class="positive" name="Submit">修改</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
	</form>
</body>
</html>
