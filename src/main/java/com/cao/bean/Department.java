package com.cao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * 部门实体类
 * @id id
 * @departmentName 部门名字
 * @sonDepartmentIds 下属部门id，JSONArray形式的字符串
 * @superiorDepartmentId 上级直属部门id
 */
@Entity(name = "department")
public class Department {
	
	public Department(){
		super();
	}
	
	public Department(String departmentName, String sonDepartmentIds,
			Long superiorDepartmentId) {
		super();
		this.departmentName = departmentName;
		this.sonDepartmentIds = sonDepartmentIds;
		this.superiorDepartmentId = superiorDepartmentId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "department_name", length = 32)
	private String departmentName;

	@Column(name = "son_department_ids", length = 32)
	private String sonDepartmentIds;

	@Column(name = "superior_department_id", length = 32)
	private Long superiorDepartmentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getSonDepartmentIds() {
		return sonDepartmentIds;
	}

	public void setSonDepartmentIds(String sonDepartmentIds) {
		this.sonDepartmentIds = sonDepartmentIds;
	}

	public Long getSuperiorDepartmentId() {
		return superiorDepartmentId;
	}

	public void setSuperiorDepartmentId(Long superiorDepartmentId) {
		this.superiorDepartmentId = superiorDepartmentId;
	}

}
