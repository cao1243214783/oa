package com.cao.service;

import java.util.List;

import com.cao.bean.Department;

public interface DepartmentService {
	
	public List<Department> getSonDepartmentList(Long id);
	
	public List<Department> getFirstDepartments();
	
	public Department getSuperiorDepartment(Long id);
	
	public boolean addSonDepartments(Long id,List<Long> departmentIds);
	
	public boolean deleteSonDepartments(Long id,List<Long> departmentIds);
	
	public boolean deleteDepartment(Long id);
	
	public boolean modifySuperiorDepartment(Long id,Long sid);
	
	public boolean createNewDepartment(Department department);
	
	public boolean modifyDepartmentName(Long id,String newName);
	
	public Department getById(Long id);
	
	public Department getByName(String name);
	
	public List<Department> getAllDepartments();
}
