package com.cao.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cao.utils.Utils;

@Entity(name="clock")
public class Clock {
	
	public Clock(){
		super();
	}
	
	public Clock(Long employeeId, Date signInTime) {
		super();
		this.employeeId = employeeId;
		this.signInTime = signInTime;
	    this.time = Utils.YYYY_MM_DD.format(signInTime);
	    this.workTime = 0;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="employee_id")
    private Long employeeId;
    
    @Column(name="sign_in_time")
    private Date signInTime;
    
    @Column(name="sign_out_time")
    private Date signOutTime;

    @Column(name="work_time")
    private double workTime;
    
    @Column(name="time",length=32)
    private String time;
    

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

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public Date getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(Date signOutTime) {
		this.signOutTime = signOutTime;
	}

	public double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(double workTime) {
		this.workTime = workTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
    
}
