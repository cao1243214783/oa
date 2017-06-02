package com.cao.service;

import java.util.List;

import com.cao.bean.Admin;

public interface AdminService {
	public Admin ifLoginSuccess(String username,String password);
	
	public List<Admin> getAll();
}
