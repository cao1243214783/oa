package com.cao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cao.bean.Department;
import com.cao.bean.Employee;
import com.cao.bean.User;
import com.cao.service.DepartmentService;
import com.cao.service.EmployeeService;
import com.cao.service.UserService;
import com.cao.utils.Utils;

@RequestMapping("employee")
@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping("allEmployees")
	@ResponseBody
	public void getAllEmployees(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		JSONArray jsonArray = new JSONArray();
		List<Employee> list = employeeService.getEmployeesByDepartment(null);
		for(Employee employee : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", employee.getId());
			jsonObject.put("age", (int)((new Date().getTime()-employee.getBirthday().getTime())/1000/60/60/24/365.25)+1);
			jsonObject.put("name", employee.getName());
			jsonObject.put("sex", employee.getSex());
			jsonObject.put("department", employee.getDepartment());
			jsonObject.put("position", employee.getPosition());
			jsonObject.put("salary", employee.getSalary());
			jsonObject.put("hiredate", Utils.YYYY_MM_DD.format(employee.getHiredate()));
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("leaveEmployees")
	@ResponseBody
	public void leaveEmployees(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		JSONArray jsonArray = new JSONArray();
		List<Employee> list = employeeService.leaveEmployees();
		for(Employee employee : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", employee.getId());
			jsonObject.put("age", (int)((new Date().getTime()-employee.getBirthday().getTime())/1000/60/60/24/365.25)+1);
			jsonObject.put("name", employee.getName());
			jsonObject.put("sex", employee.getSex());
			jsonObject.put("department", employee.getDepartment());
			jsonObject.put("position", employee.getPosition());
			jsonObject.put("salary", employee.getSalary());
			jsonObject.put("hiredate", Utils.YYYY_MM_DD.format(employee.getHiredate()));
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}

	
	@RequestMapping("deleteEmployee")
	@ResponseBody
	public void deleteEmployee(@RequestParam("id") Long id,
			HttpServletResponse response) throws IOException{
		JSONObject jsonObject = new JSONObject();
		userService.delete(id);
		boolean b = employeeService.delete(id);
		jsonObject.put("result", b?Utils.SUCCESS:Utils.FAILURE);
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("getEmployeeByName")
	@ResponseBody
	public void getEmployeeByName(@RequestParam("name") String name,
			HttpServletResponse response) throws IOException{
		name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONArray jsonArray = new JSONArray();
		List<Employee> list = employeeService.getEmployeeByName(name);
		for(Employee employee : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", employee.getId());
			jsonObject.put("age", (int)((new Date().getTime()-employee.getBirthday().getTime())/1000/60/60/24/365.25)+1);
			jsonObject.put("name", employee.getName());
			jsonObject.put("sex", employee.getSex());
			jsonObject.put("department", employee.getDepartment());
			jsonObject.put("position", employee.getPosition());
			jsonObject.put("salary", employee.getSalary());
			jsonObject.put("hiredate", Utils.YYYY_MM_DD.format(employee.getHiredate()));
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("alterEmployeeInfo")
	public void alterEmployeeInfo(@RequestParam("id") Long id,
			@RequestParam("salary") Double salary,
			@RequestParam("department") Long departmentId,
			@RequestParam("position") String position,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Department department = departmentService.getById(departmentId);
		position = new String (position.getBytes("iso8859-1"),"UTF-8");//解决乱码
		employeeService.changeSalary(id, salary);
		employeeService.changePositionAndDepartment(id, position.trim(), department.getDepartmentName());
		HttpSession session = request.getSession();
		session.removeAttribute("id");
		session.removeAttribute("salary");
		session.removeAttribute("departmentName");
		session.removeAttribute("departmentId");
		session.removeAttribute("position");
		response.sendRedirect("../html/allEmployees.jsp");
	}

	@RequestMapping("toAlterPage")
	public void toAlterPage(@RequestParam("id") Long id,
			@RequestParam("salary") Long salary,
			@RequestParam("department") String departmentName,
			@RequestParam("position") String position,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		departmentName = new String(departmentName.getBytes("ISO-8859-1"), "UTF-8");
		position = new String(position.getBytes("ISO-8859-1"), "UTF-8");
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		session.setAttribute("salary", salary);
		session.setAttribute("departmentName", departmentName);
		Department department = departmentService.getByName(departmentName);
		session.setAttribute("departmentId", department.getId());
		session.setAttribute("position", position);
		response.sendRedirect("../html/alterEmployeeInfo.jsp");
	}
	
	@RequestMapping("registEmployee")
	public void registEmployee(@RequestParam("name") String name,
			@RequestParam("sex") String sex,
			@RequestParam("birthday") String brithday,
			@RequestParam("salary") Double salary,
			@RequestParam("department") Long departmentId,
			@RequestParam("position") String position,
			HttpServletResponse response) throws IOException, ParseException{
		Department department = departmentService.getById(departmentId);
		position = new String (position.getBytes("iso8859-1"),"UTF-8");//解决乱码
		name = new String (name.getBytes("iso8859-1"),"UTF-8");//解决乱码
		sex = new String (sex.getBytes("iso8859-1"),"UTF-8");//解决乱码
		Employee employee = new Employee(name,Utils.YYYY_MM_DD.parse(brithday),position,sex,department.getDepartmentName(),salary);
		employeeService.save(employee);
		response.sendRedirect("../html/allEmployees.jsp");
	}
	
	@RequestMapping("alterYourselfInfo")
	public void alterYourselfInfo(@RequestParam("name") String name,
			@RequestParam("card") String card,
			@RequestParam("birthday") String birthday,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Employee employee = employeeService.getById(user.getEmployeeId());
		employee.setBirthday(Utils.YYYY_MM_DD.parse(birthday));
		employee.setName(name);
		employee.setCard(card);
		employeeService.updateEmployee(employee);
		response.sendRedirect("../html/myselfInfo.jsp");
	}

	@RequestMapping("getEmployeeDetails")
	@ResponseBody
	public void getEmployeeDetails(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Employee employee = employeeService.getById(user.getEmployeeId());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", employee.getName());
		jsonObject.put("card", employee.getCard());
		jsonObject.put("birthday", employee.getBirthday());
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toJSONString());
		out.flush();
		out.close();
	}

	@RequestMapping("sameDepartmentEmployees")
	@ResponseBody
	public void sameDepartmentEmployees(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		User user = (User) request.getSession().getAttribute("user");
		Employee e = employeeService.getById(user.getEmployeeId());
		response.setCharacterEncoding("UTF-8");
		JSONArray jsonArray = new JSONArray();
		List<Employee> list = employeeService.getEmployeesByDepartment(e.getDepartment());
		for(Employee employee : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", employee.getId());
			jsonObject.put("age", (int)((new Date().getTime()-employee.getBirthday().getTime())/1000/60/60/24/365.25)+1);
			jsonObject.put("name", employee.getName());
			jsonObject.put("sex", employee.getSex());
			jsonObject.put("department", employee.getDepartment());
			jsonObject.put("position", employee.getPosition());
			jsonObject.put("salary", employee.getSalary());
			jsonObject.put("hiredate", Utils.YYYY_MM_DD.format(employee.getHiredate()));
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("myselfInfo")
	@ResponseBody
	public void myselfInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		Employee e = employeeService.getById(user.getEmployeeId());
		JSONArray jsonArray = new JSONArray();
		List<Employee> list = new ArrayList<Employee>();
		list.add(e);
		for(Employee employee : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", employee.getId());
			jsonObject.put("age", (int)((new Date().getTime()-employee.getBirthday().getTime())/1000/60/60/24/365.25)+1);
			jsonObject.put("name", employee.getName());
			jsonObject.put("sex", employee.getSex());
			jsonObject.put("department", employee.getDepartment());
			jsonObject.put("position", employee.getPosition());
			jsonObject.put("salary", employee.getSalary());
			jsonObject.put("hiredate", Utils.YYYY_MM_DD.format(employee.getHiredate()));
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}

}
