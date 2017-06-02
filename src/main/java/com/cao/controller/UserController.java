package com.cao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cao.bean.Employee;
import com.cao.bean.User;
import com.cao.service.DepartmentService;
import com.cao.service.EmployeeService;
import com.cao.service.UserService;
import com.cao.utils.Utils;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("save")
	@ResponseBody
	public void save(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("employeeId") Long employeeId,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		if(userService.ifUsernameExists(username)){
			jsonObject.put("result", "用户名已存在");
			out.write(jsonObject.toJSONString());
			out.flush();
			out.close();
			return ;
		}
		if(userService.ifEmployeeHaveUser(employeeId)){
			jsonObject.put("result", "该员工已有账号");
			out.write(jsonObject.toJSONString());
			out.flush();
			out.close();
			return ;
		}
		User user = new User(employeeId,username,password);
		boolean b = userService.save(user);
		jsonObject.put("result", b?"新建账号成功":"新建失败，请重试");
		out.write(jsonObject.toJSONString());
		out.flush();
		out.close();
	}

	@RequestMapping("modifyPassword")
	public void modifyPassword(@RequestParam("password") String password,
			@RequestParam("newPassword") String newPassword,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		userService.modifyPassword(user.getUsername(), password, newPassword);
		response.sendRedirect("../html/myselfInfo.jsp");
	}
	
	@ResponseBody
	@RequestMapping("formatPassword")
	public void formatPassword(@RequestParam("employeeId") Long employeeId,
			HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		boolean b = userService.formatPassword(employeeId);
		jsonObject.put("result", b?Utils.SUCCESS:Utils.FAILURE);
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toJSONString());
		out.flush();
		out.close();
	}
}
