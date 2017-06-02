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

	function showfindResult() {
		var departmentId = $('#department').val();
		$
				.ajax({
					type : "Post",
					//方法所在页面和方法名      
					url : "../department/getEmployeeOfDepartment?departmentId=" + departmentId,
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success : function(data) {
						$('#employeetable').empty();
						$('#employeetable')
								.append(
										'<tr><th colspan="0.5">编号</th><th colspan="0.5">姓名</th><th colspan="0.5">性别</th>	<th colspan="0.5">年龄</th><th colspan="0.5">部门</th><th colspan="0.5">职位</th><th colspan="0.5">工资</th><th colspan="0.5">入职时间</th><th colspan="0.5">操作用户</th></tr>');
						showtable(data);
					},
					error : function(err) {
						alert(err);
					}
				});
	}
	
	function formatPassword(id){
		$
				.ajax({
					type : "Post",
					//方法所在页面和方法名      
					url : "../user/formatPassword?employeeId=" + id,
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success : function(data) {
						if(data.result=="SUCCESS"){
							alert("修改成功");
						}
					},
					error : function(err) {
						alert(err);
					}
				});
	}

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
									+ data[i].salary
									+ '</div></td><td class="usertablerow1"><div align="left">'
									+ data[i].hiredate
									+ '</div></td><td class="usertablerow1"><div align="left"><a href="../employee/toAlterPage?id='
									+ data[i].id + '&salary=' + data[i].salary
									+ '&position=' + data[i].position
									+ '&department=' + data[i].department
									+ '">修改</a> <a onclick="refreshAndDelete('
									+ data[i].id
									+ ')">离职</a><a onclick=formatPassword('
									+ data[i].id + ')> 格式化密码</a></div></td></tr>');
		}
	}
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
	<label for="department"> 查找:</label>
	<select name="department" id="department" onchange="onselectchange();">
	</select>
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
			<th colspan="0.5">工资</th>
			<th colspan="0.5">入职时间</th>
			<th colspan="0.5">操作用户</th>
		</tr>
	</table>
	<br>
</body>
</html>
