package com.cao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cao.bean.Admin;
import com.cao.bean.Attendance;
import com.cao.bean.Employee;
import com.cao.bean.User;
import com.cao.service.AdminService;
import com.cao.service.AttendanceService;
import com.cao.service.EmployeeService;
import com.cao.utils.Utils;

@Controller
@RequestMapping("attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;	
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("getAllAttendance")
	@ResponseBody
	public void getAllAttendance(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		List<Attendance> list = attendanceService.getAllRequests((short)0);
		JSONArray jsonArray = new JSONArray();
		for(Attendance attendance : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", attendance.getId());
			jsonObject.put("empid", attendance.getEmployeeId());
			jsonObject.put("name", attendance.getEmployeeName());
			jsonObject.put("time", Utils.YYYY_MM_DD.format(attendance.getLeaveDate()));
			jsonObject.put("description", attendance.getDesciption());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("examinationApprovalRequest")
	public void examinationApprovalRequest(@RequestParam("id") Long id,
			@RequestParam("isAllowed") Short isAllowed,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if(admin!=null)
			attendanceService.examinationApprovalRequest(id, admin.getId(), isAllowed);
		response.sendRedirect("../html/allEmployees.jsp");
	}
	
	@RequestMapping("cancelRequest")
	public void cancelRequest(@RequestParam("id") Long id,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Attendance attendance = attendanceService.getById(id);
		if(user.getEmployeeId()==attendance.getEmployeeId())
			attendanceService.examinationApprovalRequest(id, null, (short)3);
		response.sendRedirect("../html/myseflInfo.jsp");
	}


	@RequestMapping("askForLeave")
	@ResponseBody
	public void askForLeave(@RequestParam("time") String time,
			@RequestParam("description") String description,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException{
		description = new String (description.getBytes("iso8859-1"),"UTF-8");//解决乱码
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Employee employee = employeeService.getById(user.getEmployeeId());
		boolean b = attendanceService.createNewRequest(employee.getId(), employee.getName(), description, Utils.YYYY_MM_DD.parse(time));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", b?Utils.SUCCESS:Utils.FAILURE);
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toJSONString());
		out.flush();
		out.close();
	}

	@RequestMapping("getAllAttendanceOfEmployee")
	@ResponseBody
	public void getAllAttendanceOfEmployee(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException{
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Employee employee = employeeService.getById(user.getEmployeeId());
		List<Attendance> list = attendanceService.getByEmployeeId(employee.getId());
		List<Admin> admins = adminService.getAll();
		Collections.sort(list);
		Map<Long,String> map = new HashMap<Long, String>();
		map.put(null, null);
		for(Admin admin :admins){
			map.put(admin.getId(), admin.getUsername());
		}
		JSONArray jsonArray = new JSONArray();
		for(Attendance attendance:list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", attendance.getId());
			jsonObject.put("appId", map.get(attendance.getApproverId())==null?"":map.get(attendance.getApproverId()));
			jsonObject.put("description", attendance.getDesciption()==null?"":attendance.getDesciption());
			jsonObject.put("leaveDate", Utils.YYYY_MM_DD.format(attendance.getLeaveDate()));
			jsonObject.put("processTime", attendance.getProcessTime()==null?"":Utils.YYYY_MM_DD.format(attendance.getProcessTime()));
			int isAllowed = attendance.getIsAllowed();
			String message ="";
			switch (isAllowed){
				case 0:message="未处理";break;
				case 1:message="已通过";break;
				case 2:message="未通过";break;
				case 3:message="已失效";break;
				default :break;
			}
			jsonObject.put("isAllowed", message);
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
}
