package com.cao.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cao.bean.Attendance;
import com.cao.bean.Employee;
import com.cao.bean.Payroll;
import com.cao.dao.AttendanceDao;
import com.cao.dao.EmployeeDao;
import com.cao.dao.PayrollDao;
import com.cao.service.PayrollService;
import com.cao.utils.Utils;

@Service
public class PayrollServiceImpl implements PayrollService{

	@Autowired
	private PayrollDao payrollDao;
	
	@Autowired
	private AttendanceDao attendanceDao;
	
	@Autowired
	private EmployeeDao EmployeeDao;
	
	@Override
	public List<Payroll> getByEmployeeIdAndTime(Long employeeId,String time) {
		return payrollDao.getPayRoll(employeeId, time);
	}

	@Override
	public boolean calculateAndSavePayroll(Integer year,Integer month) {
		boolean isSuccess= true;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, 1, 0, 0, 0);
		Date startTime = calendar.getTime();
		calendar.add(Calendar.MONTH,1);
		Date endTime = calendar.getTime();
		payrollDao.deleteAllRepeatedRecord(startTime);
		List<Employee> employeeList = EmployeeDao.getAllEmployees();
		List<Attendance> attendanceList = attendanceDao.getAll((short)1, startTime, endTime);
		Map<Long,List<Attendance>> attendanceMap = new HashMap<Long,List<Attendance>>();
		if(attendanceList.size()>0){
			for(Attendance attendance:attendanceList){
				if(attendanceMap.containsKey(attendance.getEmployeeId())){
					List<Attendance> list = attendanceMap.get(attendance.getEmployeeId());
					list.add(attendance);
					attendanceMap.remove(attendance.getEmployeeId());
					attendanceMap.put(attendance.getEmployeeId(), list);
				}else{
					List<Attendance> list = new ArrayList<Attendance>();
					list.add(attendance);
					attendanceMap.put(attendance.getEmployeeId(), list);
				}
			}
		}
		for(Employee e :employeeList){
			double realWages = 0.0;
			int totalLeave = 0;
			int total = Utils.getDateCount(year, month ,1);
			StringBuilder detail = new StringBuilder();
			if(attendanceMap.containsKey(e.getId())){
				List<Attendance> list = attendanceMap.get(e.getId());
				for(Attendance attendance : list){
					detail.append(Utils.YYYY_MM_DD.format(attendance.getLeaveDate()))
					.append("请假:").append(attendance.getDesciption()).append(";");
				}
				totalLeave = list.size();
			}
			Calendar c = Calendar.getInstance();
			c.setTime(e.getHiredate());
			if(c.get(Calendar.YEAR)==year &&c.get(Calendar.MONTH)==month-1 &&c.get(Calendar.DATE)!=1){
				totalLeave += total - Utils.getDateCount(year, month, c.get(Calendar.DATE));
				detail.append("入职前有"+(total - Utils.getDateCount(year, month, c.get(Calendar.DATE)))+"个工作日不计算工资");
			}
			realWages = e.getSalary()*(total-totalLeave)/total;
			Payroll payroll = new Payroll(e.getId(),e.getName(), Utils.YYYY_MM.format(startTime), realWages, detail.toString());
			isSuccess = isSuccess && payrollDao.save(payroll);
		}
		return isSuccess;
	}
}
