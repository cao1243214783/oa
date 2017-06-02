package com.cao.service;

import java.util.Date;
import java.util.List;

import com.cao.bean.Clock;

public interface ClockService {
	
	public void signIn(Long employeeId);
	
	public void signOut(Long employeeId);
	
	public void reissue(Long employeeId,Date date ,boolean b);

	public List<Clock> getByEmployeeIdAndTime(Long employeeId,Date date);
	
	public Clock getById(Long id);
	
	public List<Clock> getByMonth(Long employeeId,String time);
}
