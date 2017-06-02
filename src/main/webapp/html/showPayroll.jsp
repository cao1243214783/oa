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
		var oDate = new Date(); //实例一个时间对象；
		var year = oDate.getFullYear();   //获取系统的年；
		var month = oDate.getMonth();   //获取系统月份，由于月份是从0开始计算，所以要加1
		for(var i=2011;i<=year;i++){
			if(i==year){
				$('#year').append('<option value ="'+i+'" selected="selected">'+i+'</option>');
			}else{
				$('#year').append('<option value ="'+i+'">'+i+'</option>');
			}
		}
		for(var i=1;i<=month;i++){
			if(i==month){
				$('#month').append('<option value ="'+i+'" selected="selected">'+i+'</option>');
			}else{
				$('#month').append('<option value ="'+i+'">'+i+'</option>');
			}
		}
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../payroll/getPayrollByTime?year="+year+"&month="+month,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				for(var i=0;i<data.length;i++)
					$('#payrollTable').append('<tr><td class="usertablerow2" width="10%">'+data[i].id+'</td>	<td class="usertablerow2" width="10%">'+data[i].name+'</td><td class="usertablerow2" width="10%">'+data[i].time+'</td><td class="usertablerow2" width="10%">'+data[i].salary+'</td><td class="usertablerow2" width="60%">'+data[i].details+'</td></tr>');
			},
			error : function(err) {
				alert(err);
			}
		});
	});
		function onselectchange(){
			var oDate = new Date(); //实例一个时间对象；
			var year = oDate.getFullYear();   //获取系统的年；
			var month = oDate.getMonth()+1;   //获取系统月份，由于月份是从0开始计算，所以要加1
	    	if($("#year option:selected").val()==year){
	    		$('#month').empty();
	    		$('#month').append('<option value ="">空</option>');
	    		for(var i=1;i<=month;i++){
					$('#month').append('<option value ="'+i+'">'+i+'</option>');
				}
	    	}else{
	    		$('#month').empty();
	    		$('#month').append('<option value ="">空</option>');
	    		for(var i=1;i<=12;i++){
					$('#month').append('<option value ="'+i+'">'+i+'</option>');
				}
	    	}
	    }
	    
	function showfindResult(){
		var year = $("#year option:selected").val();
		var month = $("#month option:selected").val();
		$.ajax({
			type : "Post",
			//方法所在页面和方法名      
			url : "../payroll/getPayrollByTime?year="+year+"&month="+month,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				$('#payrollTable').empty();
				$('#payrollTable').append('<tr><th colspan="0.5">编号</th><th colspan="0.5">姓名</th><th colspan="0.5">时间</th><th colspan="0.5">工资</th><th colspan="1">明细</th></tr>');
				for(var i=0;i<data.length;i++)
					$('#payrollTable').append('<tr><td class="usertablerow2" width="10%">'+data[i].id+'</td><td class="usertablerow2" width="10%">'+data[i].name+'</td><td class="usertablerow2" width="10%">'+data[i].time+'</td><td class="usertablerow2" width="10%">'+data[i].salary+'</td><td class="usertablerow2" width="60%">'+data[i].details+'</td></tr>');
			},
			error : function(err) {
				alert(err);
			}
		});
	}
	function outToExcel(){
		var year = $("#year option:selected").val();
		var month = $("#month option:selected").val();
		window.location.href="../files/outToExcel?year="+year+"&month="+month;
	}
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">

			<label for="year">年份:</label> 
			<select id="year" name="year"  onchange="onselectchange();">
				<option value ="">空</option>
			</select>
			<label for="month">月份:</label> 
			<select id="month" name="month">
				<option value ="">空</option>
			</select>
	<button type="submit" class="positive" onclick="showfindResult();">查看</button>
	<button type="submit" class="positive" onclick="outToExcel();">导出到excel</button>
	<br>
	<table id="payrollTable" class="payrollTable" cellspacing="1"
		cellpadding="3" width="96%" align="center" border="0">
		<tr>
			<th colspan="0.5">编号</th>
			<th colspan="0.5">姓名</th>
			<th colspan="0.5">时间</th>
			<th colspan="0.5">工资</th>
			<th colspan="1">明细</th>
		</tr>
	</table>
	<br>
</body>
</html>
