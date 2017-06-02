package com.cao.service;

import java.util.List;

import com.cao.bean.Files;

public interface FilesService {

	public boolean save(Files file);
	
	public Files getById(Long id);
	
	public boolean ifHavePermission(Long id,Long employeeId);
	
	public List<Files> getByEmployeeId(Long employeeId);
	
	public List<Files> findByName(String name,Long employeeId);
	
	public List<Files> getMyUpload(Long employeeId);

	public void delete(Files files);
	
	public List<Files> getAll();
	
	public void update(Files files);
	
}
