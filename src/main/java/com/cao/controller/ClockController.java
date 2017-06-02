package com.cao.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cao.bean.User;
import com.cao.service.ClockService;



@Controller
@RequestMapping("clock")
public class ClockController {
	
	@Autowired
	private ClockService clockService;
	
	@RequestMapping("signIn")
	public void signIn(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return;
		clockService.signIn(user.getEmployeeId());
		response.sendRedirect("../html/myseflInfo.jsp");
	}

	@RequestMapping("signOut")
	public void signOut(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return;
		clockService.signOut(user.getEmployeeId());
		response.sendRedirect("../html/myseflInfo.jsp");
	}
	
	@RequestMapping("getAll")
	public void getAll(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
	}
}
