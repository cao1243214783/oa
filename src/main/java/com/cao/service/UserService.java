package com.cao.service;

import com.cao.bean.User;

public interface UserService {
	
	public User ifLoginSuccess(String username,String password);
	
	public boolean save(User user);
	
	public boolean ifUsernameExists(String username);
	
	public boolean delete(Long id);
	
	public boolean modifyPassword(String username,String password,String newPassword);
	
	public boolean formatPassword(Long employeeId);
	
	public User getUserByUsername(String username);
	
	public boolean ifEmployeeHaveUser(Long employeeId);
}
