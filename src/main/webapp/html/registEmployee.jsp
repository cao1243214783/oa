<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<script language="javascript" src="../js/jquery-3.0.0.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var a = 1;

	$(document).ready(function() {
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../department/getFirstDepartment",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				$('#department').append('<option value="0"></option>');
				for(var i=0 ;i<data.length;i++){
					$('#department').append('<option value="'+data[i].id+'">'+data[i].name+'</option>');
				}
			},
			error : function(err) {
				alert(err);
			}
		});
	});

		function onselectchange() {
			var id = $('#department').val();
			$.ajax({
				type : "Post",
				//方法所在页面和方法名      
				url : "../department/getSontDepartments?id="+id,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					if(data[0]=="no"){
						return false;
					}
					$('#department').attr("name","department"+a);
					$('#department').attr("disabled","disabled");
					$('#department').attr("onchange","javascript:0");
					$('#department').attr("id","department"+a);
					$('#department'+a).after('<select name="department" id="department" onchange="onselectchange();">');
					$('#department').append('<option value="0"></option>');
					a = a+1;
					for(var i=0 ;i<data.length;i++){
						$('#department').append('<option value="'+data[i].id+'">'+data[i].name+'</option>');
					}
				},
				error : function(err) {
					alert(err);
				}
			});
		};
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<form action="../employee/registEmployee" method="get" class="form-inline">
		<p>
			<label for="name">姓名:</label> <input  class="form-control input-lg" name="name" id="name"
				type="text">
		</p>
		<p>
			<label for="department">性别:</label> <label><input class="form-control input-lg" name="sex"
				type="radio" value="男" />男 </label> <label><input class="form-control" name="sex"
				type="radio" value="女" />女 </label>
		</p>
		<p>
			<label for="birthday">出生日期:</label> <input class="form-control input-lg" name="birthday"
				id="birthday" type="date">
		</p>
		<p>
			<label for="department">部门:</label> <select class="form-control input-lg" name="department"
				id="department" onchange="onselectchange();">
			</select>
		</p>
		<p>
			<label for="position">职位:</label> <input class="form-control input-lg" name="position"
				id="position" type="text">
		</p>
		<p>
			<label for="salary">工资:</label> <input class="form-control input-lg" name="salary" id="salary"
				type="text">
		</p>
		<button type="submit" class="positive" name="Submit">新建</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
	</form>
</body>
</html>
