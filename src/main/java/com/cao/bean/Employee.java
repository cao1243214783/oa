package com.cao.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * 员工实体类
 * @id id
 * @name 姓名
 * @birthday 出生日期
 * @position 职位
 * @department 部门
 * @salary 薪资
 * @sex 性别
 * @Card 工资卡
 * @hireDate 入职日期
 * @termDate 离职日期，默认为空，离职时给字段赋值
 * 				员工是否在职的标识列
 */
@Entity(name = "employee")
public class Employee {
	
	public Employee() {
		super();
	}
	

	public Employee(String name, Date birthday, String position,String sex,
			String department, Double salary) {
		super();
		this.name = name;
		this.sex = sex; 
		this.birthday = birthday;
		this.position = position;
		this.department = department;
		this.salary = salary;
		this.hireDate = new Date();
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "position", length = 32)
	private String position;
	
	@Column(name = "card", length = 32)
	private String card;

	@Column(name = "department", length = 32)
	private String department;

	@Column(name = "salary")
	private Double salary;
	
	@Column(name = "hireDate")
	private Date hireDate;

	@Column(name = "termDate")
	private Date termDate;
	
	@Column(name = "sex" ,length = 8)
	private String sex;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Date getHiredate() {
		return hireDate;
	}

	public void setHiredate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getTermDate() {
		return termDate;
	}

	public void setTermDate(Date termDate) {
		this.termDate = termDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getCard() {
		return card;
	}


	public void setCard(String card) {
		this.card = card;
	}
}
