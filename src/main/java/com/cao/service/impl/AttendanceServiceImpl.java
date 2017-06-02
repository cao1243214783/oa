package com.cao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cao.bean.Attendance;
import com.cao.dao.AttendanceDao;
import com.cao.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService{
	
	@Autowired
	private AttendanceDao attendanceDao;

	@Override
	public boolean createNewRequest(Long employeeId,String employeeName, String desciption,
			Date leaveDate) {
		Attendance attendance = new Attendance(employeeId,desciption==null?"":desciption,leaveDate,employeeName);
		return attendanceDao.save(attendance);
	}

	@Override
	public boolean examinationApprovalRequest(Long id, Long approverId,
			Short isAllowed) {
		Attendance attendance = attendanceDao.getById(id);
		if(attendance==null){
			return false;
		}
		attendance.setApproverId(approverId);
		attendance.setIsAllowed(isAllowed);
		if(approverId!=null)
			attendance.setProcessTime(new Date());
		attendanceDao.update(attendance);
		return true;
	}

	@Override
	public List<Attendance> getAllRequests(Short isAllowed) {
		return attendanceDao.getAll(isAllowed,null,null);
	}

	@Override
	public void setRequestFailure() {
		attendanceDao.setRequestFailure();
	}

	@Override
	public List<Attendance> getByEmployeeId(Long id) {
		return attendanceDao.getByEmployeeId(id);
	}

	@Override
	public Attendance getById(Long id) {
		return attendanceDao.getById(id);
	}

}
