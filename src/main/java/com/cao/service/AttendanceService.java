package com.cao.service;

import java.util.Date;
import java.util.List;

import com.cao.bean.Attendance;

public interface AttendanceService {
	
	public boolean createNewRequest(Long employeeId,String employeeName,String desciption,Date leaveDate);

	public boolean examinationApprovalRequest(Long id,Long approverId,Short isAllowed);
	
	public List<Attendance> getAllRequests(Short isAllowed);
	
	public void setRequestFailure();
	
	public List<Attendance> getByEmployeeId(Long id);
	
	public Attendance getById(Long id);
}
