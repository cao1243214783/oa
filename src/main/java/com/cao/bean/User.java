package com.cao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * 用户实体类，员工用账号密码登陆oa系统
 * @id id
 * @employeeId 账号使用员工id
 * @username 用户名
 * @passwrd 密码
 * @ifAvailable 用户是否可用
 */
@Entity(name="user")
public class User {
	public User(){
		super();
	}
	
    public User(long employeeId,String username,String password){
    	super();
    	this.employeeId = employeeId;
    	this.username = username;
    	this.password = password;
    	this.ifAvailable = true;
    }

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="employee_id",unique=true)
    private Long employeeId;
    
    @Column(name="username",length=32,unique=true)
    private String username;
    
    @Column(name="password",length=32)
    private String password;
    
    @Column(name="if_available")
    private boolean ifAvailable;
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isIfAvailable() {
		return ifAvailable;
	}

	public void setIfAvailable(boolean ifAvailable) {
		this.ifAvailable = ifAvailable;
	}
}