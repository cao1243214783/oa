package com.cao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cao.bean.Employee;
import com.cao.bean.Files;
import com.cao.dao.EmployeeDao;
import com.cao.dao.FilesDao;
import com.cao.service.FilesService;

@Service
public class FilesServiceImpl implements FilesService{
	
	@Autowired
	private FilesDao filesDao;
	
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public Files getById(Long id) {
		return filesDao.getById(id);
	}

	@Override
	public boolean ifHavePermission(Long id, Long employeeId) {
		Files f = filesDao.getById(id);
		if(f==null)
			return false;
		if (f.getEmployeeId()==employeeId) {
			return true;
		}
		JSONObject jsonObject = JSON.parseObject(f.getPermission());
		if("yes".equals(jsonObject.get("ALL"))){
			return true;
		}
		JSONArray empArray =new JSONArray();
		if(jsonObject.get("employees")!=null&&!"".equals(jsonObject.get("employees").toString())){
			empArray = JSON.parseArray(jsonObject.get("employees").toString());
		}
		if(empArray.contains(employeeId)){
			return true;
		}
		JSONArray departArray =new JSONArray();
		if(jsonObject.get("departments")!=null&&!"".equals(jsonObject.get("departments").toString())){
			departArray = JSON.parseArray(jsonObject.get("departments").toString());
		}
		Employee employee = employeeDao.getById(employeeId);
		String department = employee.getDepartment();
		if(departArray.contains(department)){
			return true;
		}
		return false;
	}

	@Override
	public List<Files> getByEmployeeId(Long employeeId) {
		List<Files> list = filesDao.getAll();
		List<Files> result = new ArrayList<Files>();
		for(Files files:list){
			if(this.ifHavePermission(files, employeeId)){
				result.add(files);
			}
		}
		return result;
	}

	@Override
	public List<Files> findByName(String name, Long employeeId) {
		List<Files> list = filesDao.findByName(name);
		List<Files> result = new ArrayList<Files>();
		for(Files files:list){
			if(this.ifHavePermission(files, employeeId)){
				result.add(files);
			}
		}
		return null;
	}

	@Override
	public boolean save(Files file) {
		return filesDao.save(file);
	}
	
	public boolean ifHavePermission(Files f, Long employeeId) {
		if(f==null)
			return false;
		if (f.getEmployeeId()==employeeId) {
			return true;
		}
		JSONObject jsonObject = JSON.parseObject(f.getPermission());
		if("yes".equals(jsonObject.get("ALL"))){
			return true;
		}
		JSONArray empArray =new JSONArray();
		if(jsonObject.get("employees")!=null&&!"".equals(jsonObject.get("employees").toString())){
			empArray = JSON.parseArray(jsonObject.get("employees").toString());
		}
		if(empArray.contains(employeeId)){
			return true;
		}
		JSONArray departArray =new JSONArray();
		if(jsonObject.get("departments")!=null&&!"".equals(jsonObject.get("departments").toString())){
			departArray = JSON.parseArray(jsonObject.get("departments").toString());
		}
		Employee employee = employeeDao.getById(employeeId);
		String department = employee.getDepartment();
		if(departArray.contains(department)){
			return true;
		}
		return false;
	}

	@Override
	public List<Files> getMyUpload(Long employeeId) {
		return filesDao.getByEmployeeId(employeeId);
	}

	@Override
	public void delete(Files files) {
		filesDao.delete(files);
	}

	@Override
	public List<Files> getAll() {
		return filesDao.getAll();
	}

	@Override
	public void update(Files files) {
		filesDao.update(files);
	}
	
	
}
