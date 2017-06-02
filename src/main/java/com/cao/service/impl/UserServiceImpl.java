package com.cao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cao.bean.User;
import com.cao.dao.UserDao;
import com.cao.service.UserService;
import com.cao.utils.Utils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public User ifLoginSuccess(String username,String password) {
		return userDao.getUserByUsernameAndPassword(username, Utils.EncoderByMd5(password));
	}

	@Override
	public boolean save(User user) {
		user.setPassword(Utils.EncoderByMd5(user.getPassword()));
		return userDao.save(user);
	}

	@Override
	public boolean ifUsernameExists(String username) {
		User user = userDao.getUserByUsername(username);
		return user != null;
	}

	@Override
	public boolean delete(Long id) {
		User user = userDao.getUserByEmployeeId(id);
		if(user == null){
			return false;
		}
		user.setIfAvailable(false);
		userDao.update(user);
		return true;
	}

	@Override
	public boolean modifyPassword(String username, String password,
			String newPassword) {
		User user = userDao.getUserByUsernameAndPassword(username,Utils.EncoderByMd5(password));
		if(user == null){
			return false;
		}
		user.setPassword(Utils.EncoderByMd5(newPassword));
		userDao.update(user);
		return true;
	}

	@Override
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public boolean formatPassword(Long employeeId) {
		User user = userDao.getUserByEmployeeId(employeeId);
		if(user == null)
			return false;
		user.setPassword(Utils.EncoderByMd5("000000"));
		userDao.update(user);
		return true;
	}

	@Override
	public boolean ifEmployeeHaveUser(Long employeeId) {
		User user = userDao.getUserByEmployeeId(employeeId);
		return user != null;
	}
}
