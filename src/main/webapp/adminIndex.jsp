<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	if (session.getAttribute("admin") == null) {
		response.sendRedirect("adminlogin.jsp");
		return;
	}
%>
<html>
<head>
<title>中小型企业人事管理系统</title>
<link href="css/style.css" type="text/css" rel="stylesheet">
<link href="css/default.css" type="text/css" rel="stylesheet">
<script language="javascript" src="js/menu.js" type="text/javascript"></script>
<script language="javascript" src="js/jquery-3.0.0.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	function showPage(src) {
		src = src;
		$('#dtab1').empty();
		$('#dtab1').append('<iframe id="iframe" name="content3" src="html/'+src+'" frameborder="0"></iframe>');
	}
</script>
</head>
<body
	onload="javascript:border_left('left_tab1','left_menu_cnt1');showPage('allEmployees.jsp');">
	<form id="form1" runat="server">
		<table id="indextablebody" cellpadding="0">
			<thead>
				<tr>
					<th>
						<div id="logo" title="用户管理后台"></div></th>
					<th><a style="color: #16547E">用户 ：${sessionScope.admin.username }</a>&nbsp;&nbsp; <a
						style="color: #16547E">身份 ：管理员</a>&nbsp;&nbsp;
						<a href="javascript:window.location.reload()" target="content3">管理首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
						<a href="login/adminLogout">登出</a></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="menu">
						<ul class="bigbtu">
							<li id="now01"><a title="安全退出" href="login/adminLogout">安全退出</a>
							</li>
							<li id="now02"><a title="刷新页面" href="javascript:0" onclick="$('#iframe').attr('src',$('#iframe').attr('src'));">刷新页面</a>
							</li>
						</ul></td>
					<td class="tab">
						<ul id="tabpage1">
							<li id="tab1" title="管理首页"><span id="spanTitle">管理首页</span>
							</li>
						</ul></td>
				</tr>
				<tr>
					<td class="t1">
						<div id="contents">
							<table cellpadding="0">
								<tr class="t1">
									<td>
										<div class="menu_top"></div></td>
								</tr>
								<tr class="t2">
									<td>
										<div id="menu" class="menu">
											<ul class="tabpage2">
												<li id="left_tab1" title="操作菜单"
													onClick="javascript:border_left('left_tab1','left_menu_cnt1');"><span>人事</span>
												</li>
											</ul>
											<div id="left_menu_cnt1" class="left_menu_cnt">
												<ul id="dleft_tab1">
													<li id="now11"><a title="员工信息" href="javascript:0" onclick="showPage('allEmployees.jsp');"
														target="content3"><span>员工信息</span>
													</a>
													</li>
													<li id="now11"><a title="新员工注册" href="javascript:0" onclick="showPage('registEmployee.jsp');"
														target="content3"><span>新员工注册</span>
													</a>
													</li>
													<li id="now11"><a title="创建账号" href="javascript:0" onclick="showPage('registUser.jsp');"
														target="content3"><span>创建账号</span>
													</a>
													</li>
													<li id="now11"><a title="离职员工" href="javascript:0"  onclick="showPage('leaveEmployees.jsp');"
														target="content3"><span>离职员工</span>
													</a>
													</li>
													<li id="now11"><a title="审批假条" href="javascript:0" onclick="showPage('attendance.jsp');"
														target="content3"><span>审批假条</span>
													</a>
													</li>
													<li id="now11"><a title="共享文件" href="javascript:0" onclick="showPage('allFiles.jsp');"
														target="content3"><span>共享文件</span>
													</a>
													</li>
												</ul>
											</div>
											<div class="clear"></div>

											<ul class="tabpage2">
												<li id="left_tab2"
													onClick="javascript:border_left('left_tab2','left_menu_cnt2');"
													title="操作菜单"><span>部门</span>
												</li>
											</ul>
											<div id="left_menu_cnt2" class="left_menu_cnt">
												<ul id="dleft_tab2">
													<li id="now12"><a title="部门下员工" href="javascript:0" onclick="showPage('employeesOfDepartment.jsp');"
														target="content3"><span>部门下员工</span>
													</a>
													</li>
													<li id="now12"><a title="新建部门" href="javascript:0" onclick="showPage('newDepartment.jsp')"
														target="content3"><span>新建部门</span>
													</a>
													</li>
													<li id="now12"><a title="修改部门信息" href="javascript:0" onclick="showPage('alterDepartment.jsp')"
														target="content3"><span>修改部门信息</span>
													</a>
													</li>
												</ul>
											</div>
											<div class="clear"></div>

											<ul class="tabpage2">
												<li id="left_tab3"
													onClick="javascript:border_left('left_tab3','left_menu_cnt3');"
													title="操作菜单"><span>财务</span>
												</li>
											</ul>
											<div id="left_menu_cnt3" class="left_menu_cnt">
												<ul id="dleft_tab3">
													<li id="now11"><a title="计算工资" href="javascript:0" onclick="showPage('calculateWage.jsp')"
														target="content3"><span>计算工资</span>
													</a>
													</li>
													<li id="now11"><a title="显示工资" href="javascript:0" onclick="showPage('showPayroll.jsp')"
														target="content3"><span>工资详情</span>
													</a>
													</li>
												</ul>
											</div>
											<div class="clear"></div>
										</div>
								<tr class="t3">
									<td>
										<div class="menu_end"></div></td>
								</tr>
							</table>
						</div></td>
					<td class="t2">
						<div id="cnt">
							<div id="dtab1"></div>
						</div></td>
				</tr>
			</tbody>
		</table>

	</form>
</body>
</html>
