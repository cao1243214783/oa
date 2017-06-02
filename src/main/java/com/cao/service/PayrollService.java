package com.cao.service;

import java.util.List;

import com.cao.bean.Payroll;

public interface PayrollService {

	public List<Payroll> getByEmployeeIdAndTime(Long employeeId,String time);
	
	public boolean calculateAndSavePayroll(Integer year,Integer month);
}
