package com.cao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * 管理员实体类
 * @id id
 * @employeeId 员工ID
 * @employeeName 员工姓名
 * @time 时间，格式为yyyy-MM
 * @realWages 实际工资
 * @detail 工资明细
*/
@Entity(name="payroll")
public class Payroll {
	
	public Payroll(){
		super();
	}
	
	public Payroll(Long employeeId,String employeeName, String time, Double realWages, String detail) {
		super();
		this.employeeId = employeeId;
		this.time = time;
		this.realWages = realWages;
		this.detail = detail;
		this.setEmployeeName(employeeName);
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="employee_id")
    private Long employeeId;
    
    @Column(name="employee_name",length=32)
    private String employeeName;
    
    @Column(name="time",length=32)
    private String time;
    
    @Column(name="real_wages",length=32)
    private Double realWages;
    
    @Column(name="detail",columnDefinition = "LongText")
    private String detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployee_id() {
		return employeeId;
	}

	public void setEmployee_id(Long employee_id) {
		this.employeeId = employee_id;
	}

	public Double getRealWages() {
		return realWages;
	}

	public void setRealWages(Double realWages) {
		this.realWages = realWages;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}
