package com.cao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cao.bean.Payroll;
import com.cao.service.PayrollService;
import com.cao.utils.Utils;

@Controller
@RequestMapping("payroll")
public class PayrollController {
	
	@Autowired
	private PayrollService payrollService;
	
	@RequestMapping("calculateWage")
	public void alterEmployeeInfo(@RequestParam("year") Integer year,
			@RequestParam("month") Integer month,
			HttpServletResponse response) throws IOException{
		payrollService.calculateAndSavePayroll(year, month);
		response.sendRedirect("../html/showPayroll.jsp");
	}
	
	@RequestMapping("getPayrollByTime")
	public void getPayrollByTime(@RequestParam("year") Integer year,
			@RequestParam("month") Integer month,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		Calendar calendar = Calendar.getInstance();
		List<Payroll> list;
		if(year!=null&&month!=null){
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month-1);
			list = payrollService.getByEmployeeIdAndTime(null,Utils.YYYY_MM.format(calendar.getTime()));
		}else if(year==null&&month!=null){
			list = payrollService.getByEmployeeIdAndTime(null,month<10?"0"+month.toString():month.toString());
		}else if(year!=null&&month==null){
			list = payrollService.getByEmployeeIdAndTime(null,year.toString());
		}else{
			list = payrollService.getByEmployeeIdAndTime(null,null);
		}
		JSONArray jsonArray = new JSONArray();
		for(Payroll payroll : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", payroll.getEmployee_id());
			jsonObject.put("name", payroll.getEmployeeName());
			jsonObject.put("salary", payroll.getRealWages());
			jsonObject.put("details", payroll.getDetail());
			jsonObject.put("time", payroll.getTime());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}
	

}
