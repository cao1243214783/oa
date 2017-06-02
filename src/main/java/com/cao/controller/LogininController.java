package com.cao.controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cao.bean.Admin;
import com.cao.bean.User;
import com.cao.service.AdminService;
import com.cao.service.ClockService;
import com.cao.service.UserService;

@Controller
@RequestMapping("login")
public class LogininController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ClockService clockService;

	@RequestMapping("userlogin")
	public void userlogin(HttpServletRequest request,HttpServletResponse responce) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.ifLoginSuccess(username, password);
		if(user != null){
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)<9) {
				clockService.signIn(user.getEmployeeId());
			}
		}
		responce.sendRedirect("../userIndex.jsp");
	}
	
	@RequestMapping("adminlogin")
	public void adminlogin(HttpServletRequest request,HttpServletResponse responce) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Admin admin = adminService.ifLoginSuccess(username, password);
		if(admin != null){
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
		}
		responce.sendRedirect("../adminIndex.jsp");
	}
	
	@RequestMapping("adminLogout")
	public void adminLogout(HttpServletRequest request,HttpServletResponse responce) throws IOException{
		HttpSession session = request.getSession();
		session.removeAttribute("admin");
		responce.sendRedirect("../adminIndex.jsp");
	}
	
	@RequestMapping("userLogout")
	public void userLogout(HttpServletRequest request,HttpServletResponse responce) throws IOException{
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		responce.sendRedirect("../userIndex.jsp");
	}

}
