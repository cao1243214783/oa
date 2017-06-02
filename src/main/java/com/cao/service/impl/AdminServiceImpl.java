package com.cao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cao.bean.Admin;
import com.cao.dao.AdminDao;
import com.cao.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminDao adminDao;
	
	@Override
	public Admin ifLoginSuccess(String username, String password) {
		return adminDao.getAdminByUsernameAndPassword(username, password);
	}

	@Override
	public List<Admin> getAll() {
		return adminDao.getAll();
	}
}
