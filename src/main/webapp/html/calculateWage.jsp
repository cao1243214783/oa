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
		for(var i=2011;i<=year;i++){
			$('#year').append('<option value ="'+i+'">'+i+'</option>');
		}
		for(var i=1;i<=12;i++){
			$('#month').append('<option value ="'+i+'">'+i+'</option>');
		}
	});
		function onselectchange(){
			var oDate = new Date(); //实例一个时间对象；
			var year = oDate.getFullYear();   //获取系统的年；
			var month = oDate.getMonth();   //获取系统月份，由于月份是从0开始计算，所以要加1
	    	if($("#year option:selected").val()==year){
	    		$('#month').empty();
	    		for(var i=1;i<=month;i++){
					$('#month').append('<option value ="'+i+'">'+i+'</option>');
				}
	    	}else{
	    		$('#month').empty();
	    		for(var i=1;i<=12;i++){
					$('#month').append('<option value ="'+i+'">'+i+'</option>');
				}
	    	}
	    }
	
</script>
</head>
<body bottommargin="0" leftmargin="0" topmargin="0" rightmargin="0">
	<form action="../payroll/calculateWage" method="get">
		<p>
			<label for="year">年份:</label> 
			<select id="year" name="year"  onchange="onselectchange();">
			</select>
		</p><p>
			<label for="month">月份:</label> 
			<select id="month" name="month">
			</select>
		</p>
		<button class="positive"  class="positive" name="Submit">计算</button>
		<a href="#" onClick="javascript :history.back(-1);">返回</a>
	</form>
</body>
</html>
