package com.cao.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * 文件实体类
 * @id id
 * @filename 文件名
 * @path 路径
 * @permission 权限jsonObject中两个jsonArray，分别为部门和员工
 * @size 文件大小
 * @uploadtime 上传时间
 * @employeeId 上传者
 */
@Entity(name = "files")
public class Files {
	
	public Files() {
		super();
	}
	
	public Files(String filename, String path, String permission, Long size,Long employeeId) {
		super();
		this.filename = filename;
		this.path = path;
		this.permission = permission;
		this.size = size;
		this.employeeId = employeeId;
		this.uploadTime = new Date();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "filename", length = 255)
	private String filename;

	@Column(name = "path",columnDefinition = "LongText")
	private String path;

	@Column(name = "permission",columnDefinition = "LongText")
	private String permission;
	
	@Column(name = "uploadTime")
	private Date uploadTime;
	
	@Column(name = "size")
	private Long size;
	
	@Column(name = "employee_id")
	private Long employeeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

}
