package com.cao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * 管理员实体类
 * @id id
 * @username 用户名
 * @passwrd 密码
 * @permissions 管理员权限
 */
@Entity(name="admin")
public class Admin {
	
	public Admin(){
		super();
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="username",length=32,unique=true)
    private String username;
    
    @Column(name="password",length=32)
    private String password;
    
    @Column(name="permissions",length=32)
    private Short permissions;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getPermissions() {
		return permissions;
	}

	public void setPermissions(Short permissions) {
		this.permissions = permissions;
	}

}
