package com.cao.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cao.bean.Employee;
import com.cao.dao.EmployeeDao;
import com.cao.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return employeeDao.getEmployeesByDepartment(department);
	}

	@Override
	public boolean save(Employee employee) {
		return employeeDao.save(employee);
	}

	@Override
	public boolean delete(Long id) {
		Employee employee = employeeDao.getById(id);
		if(employee == null){
			return false;
		}
		employee.setTermDate(new Date());
		employeeDao.update(employee);
		return true;
	}

	@Override
	public boolean changeSalary(Long id, Double salary) {
		Employee employee = employeeDao.getById(id);
		if(employee == null){
			return false;
		}
		employee.setSalary(salary);
		employeeDao.update(employee);
		return true;
	}

	@Override
	public boolean changePositionAndDepartment(Long id, String position,
			String department) {
		Employee employee = employeeDao.getById(id);
		if(employee == null){
			return false;
		}
		employee.setDepartment(department);
		employee.setPosition(position);
		employeeDao.update(employee);
		return true;
	}

	@Override
	public List<Employee> getEmployeeByBirthday() {
		List<Employee> list = employeeDao.getAll();
		List<Employee> returnList = new ArrayList<Employee>();
		for(Employee employee : list){
			if(employee.getTermDate()==null){
				Calendar birthday = Calendar.getInstance();
				birthday.setTime(employee.getBirthday());
				Calendar currentTime = Calendar.getInstance();
				int brithdayMonth = birthday.get(Calendar.MONTH);
				int currentMonth = currentTime.get(Calendar.MONTH);
				if(brithdayMonth == currentMonth){
					returnList.add(employee);
				}
			}
		}
		return returnList;
	}

	@Override
	public List<Employee> getEmployeeByName(String name) {
		return employeeDao.getEmployeeByName(name);
	}

	@Override
	public List<Employee> leaveEmployees() {
		return employeeDao.getleaveEmployees();
	}

	@Override
	public Employee getById(Long id) {
		return employeeDao.getById(id);
	}

	@Override
	public void updateEmployee(Employee employee) {
		employeeDao.update(employee);
	}
	
}
