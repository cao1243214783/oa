package com.cao.service;

import java.util.List;

import com.cao.bean.Employee;

public interface EmployeeService {
	
	public List<Employee> getEmployeesByDepartment(String department);
	
	public boolean save(Employee employee);
	
	public boolean delete(Long id);
	
	public boolean changeSalary(Long id,Double salary);
	
	public boolean changePositionAndDepartment(Long id,String position,String department);
	
	public List<Employee> getEmployeeByBirthday();
	
	public List<Employee> getEmployeeByName(String name);
	
	public List<Employee> leaveEmployees();
	
	public Employee getById(Long id);
	
	public void updateEmployee(Employee employee);
}
