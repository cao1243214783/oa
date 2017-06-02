package com.cao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cao.bean.Department;
import com.cao.bean.Employee;
import com.cao.service.DepartmentService;
import com.cao.service.EmployeeService;
import com.cao.utils.Utils;

@RequestMapping("department")
@Controller
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("getFirstDepartment")
	@ResponseBody
	public void getFirstDepartment(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		JSONArray jsonArray = new JSONArray();
		List<Department> list = departmentService.getFirstDepartments();
		for(Department department : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", department.getId());
			jsonObject.put("name", department.getDepartmentName());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("getSontDepartments")
	@ResponseBody
	public void getSontDepartments(@RequestParam("id") Long id,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		JSONArray jsonArray = new JSONArray();
		List<Department> list = departmentService.getSonDepartmentList(id);
		if (list.isEmpty()) {
			jsonArray.add("no");
			PrintWriter out = response.getWriter();
			out.write(jsonArray.toJSONString());
			out.flush();
			out.close();
			return;
		}
		for(Department department : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", department.getId());
			jsonObject.put("name", department.getDepartmentName());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("getEmployeeOfDepartment")
	@ResponseBody
	public void getEmployeeOfDepartment(@RequestParam("departmentId") Long departmentId,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		JSONArray jsonArray = new JSONArray();
		Department department = departmentService.getById(departmentId);
		List<Employee> list = employeeService.getEmployeesByDepartment(department.getDepartmentName());
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

	@RequestMapping("getAvailableSup")
	@ResponseBody
	public void getAvailableSup(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		JSONArray jsonArray = new JSONArray();
		List<Department> list = departmentService.getAllDepartments();
		for(Department department : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", department.getId());
			jsonObject.put("name", department.getDepartmentName());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}

	@RequestMapping("newDepartment")
	public void newDepartment(@RequestParam("name") String name,
			@RequestParam(value = "supDepartment", required=false) Long supDepartment,
			@RequestParam(value = "sonDepartment", required=false) String sonDepartment,
			HttpServletResponse response) throws IOException, ParseException{
		name = new String (name.getBytes("iso8859-1"),"UTF-8");//解决乱码
		Department department;
		if(sonDepartment==null){
			department = new Department(name,null,supDepartment);
		}else{
			String[] str =sonDepartment.split(",");
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<str.length;i++){
				jsonArray.add(str[i]);
			}
			department = new Department(name,jsonArray.toJSONString(),supDepartment);
		}
		departmentService.createNewDepartment(department);
		response.sendRedirect("../html/allEmployees.jsp");
	}
	
	@RequestMapping("getAvailableSon")
	@ResponseBody
	public void getAvailableSon(@RequestParam("id") Long id,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		JSONArray cantUseDepartmengIds = new JSONArray();
		recursive(cantUseDepartmengIds, id);
		JSONArray jsonArray = new JSONArray();
		List<Department> list = departmentService.getAllDepartments();
		for(Department department : list){
			if (department.getSuperiorDepartmentId()!=null||cantUseDepartmengIds.contains(department.getId())) {
				continue;
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", department.getId());
			jsonObject.put("name", department.getDepartmentName());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("getDepartmentByName")
	@ResponseBody
	public void getDepartmentByName(@RequestParam("name") String name,
			HttpServletResponse response) throws IOException{
		name = new String (name.getBytes("iso8859-1"),"UTF-8");//解决乱码
		response.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		Department department = departmentService.getByName(name);
		if (department!=null) {
			jsonObject.put("result", Utils.SUCCESS);
			jsonObject.put("id", department.getId());
			jsonObject.put("sup", department.getSuperiorDepartmentId());
			JSONArray jsonArray = new JSONArray();
			List<Department> list = departmentService.getSonDepartmentList(department.getId());
			for(Department d:list){
				jsonArray.add(d.getId());
				jsonArray.add(d.getDepartmentName());
			}
			jsonObject.put("sons", jsonArray);
		}else{
			jsonObject.put("result", Utils.FAILURE);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("alterDepartment")
	public void alterDepartment(@RequestParam("name") String name,
			@RequestParam(value = "supDepartment", required=false) Long supDepartment,
			@RequestParam(value = "sonDepartment", required=false) String sonDepartment,
			HttpServletResponse response) throws IOException, ParseException{
		name = new String (name.getBytes("iso8859-1"),"UTF-8");//解决乱码
		Department department = departmentService.getByName(name);
		departmentService.modifySuperiorDepartment(department.getId(), supDepartment);
		String[] str =sonDepartment.split(",");
		List<Long> list = new ArrayList<Long>();
		for(int i=0;i<str.length;i++){
			list.add(Long.parseLong(str[i]));
		}
		List<Long> list2 = new ArrayList<Long>();
		JSONArray jsonArray = JSON.parseArray(department.getSonDepartmentIds());
		for(int i=0;i<jsonArray.size();i++){
			list2.add(jsonArray.getLong(i));
		}
		departmentService.deleteSonDepartments(department.getId(), list2);
		departmentService.addSonDepartments(department.getId(), list);
		response.sendRedirect("../html/allEmployees.jsp");
	}

    public void recursive(JSONArray jsonArray,Long id){
		jsonArray.add(id);
    	Department department = departmentService.getSuperiorDepartment(id);
    	if (department!=null) {
			recursive(jsonArray, department.getId());
		}
    }
}
