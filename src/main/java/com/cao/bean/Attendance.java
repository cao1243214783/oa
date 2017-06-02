package com.cao.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * 请假信息实体类
 * @id id
 * @employeeId 请假人id
 * @employeeName 请假人姓名
 * @approverId 审批人id
 * @leaveDate 请假日期
 * @desciption 请假原因
 * @isAllowed 是否通过，未审批为0，通过为1，未通过为2,失效为3
 */
@Entity(name="attendance")
public class Attendance implements Comparable<Attendance>{

	public Attendance(){
		super();
	}
	
	public Attendance(Long employeeId, String desciption, Date leaveDate,String employeeName) {
		super();
		this.employeeId = employeeId;
		this.leaveDate = leaveDate;
		this.desciption = desciption;
		this.employeeName = employeeName;
		this.isAllowed = 0;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="employee_id")
    private Long employeeId;
    
    @Column(name="employee_name",length=32)
    private String employeeName;
    
    @Column(name="approver_id")
    private Long approverId;
    
    @Column(name="leave_date")
    private Date leaveDate;
    
    @Column(name="desciption",columnDefinition = "LongText")
    private String desciption;
    
    @Column(name="is_allowed")
    private Short isAllowed;
    
    @Column(name="process_time")
    private Date processTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public Short getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(Short isAllowed) {
		this.isAllowed = isAllowed;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Override
	public int compareTo(Attendance o) {
		return this.getLeaveDate().getTime()>o.getLeaveDate().getTime()?1:-1;
	}
}
