package com.cao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cao.bean.Department;
import com.cao.dao.DepartmentDao;
import com.cao.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	public DepartmentDao departmentDao;

	@Override
	public List<Department> getSonDepartmentList(Long id) {
		Department department = departmentDao.getById(id);
		if (department == null) {
			return new ArrayList<Department>();
		}
		JSONArray jsonArray = JSON.parseArray(department.getSonDepartmentIds());
		if(jsonArray==null||jsonArray.isEmpty()){
			return new ArrayList<Department>();
		}
		Long[] ids = new Long[jsonArray.size()];
		for(int i=0;i<ids.length;i++){
			ids[i] = jsonArray.getLong(i);
		}
		List<Department> list = departmentDao.getByIds(ids);
		return list;
	}

	@Override
	public Department getSuperiorDepartment(Long id) {
		Department department = departmentDao.getById(id);
		if (department == null) {
			return null;
		}
		Long superiorDepartmentId = department.getSuperiorDepartmentId();
		return departmentDao.getById(superiorDepartmentId);
	}

	@Override
	public boolean addSonDepartments(Long id, List<Long> departmentIds) {
		boolean ifSuccess = true;
		Department department = departmentDao.getById(id);
		if (department == null) {
			return false;
		}
		JSONArray jsonArray;
		if(department.getSonDepartmentIds()==null||"".equals(department.getSonDepartmentIds())){
			jsonArray = new JSONArray();
		}else{
			jsonArray = JSON.parseArray(department.getSonDepartmentIds());
		}
		Long[] ids = new Long[departmentIds.size()];
		ids = departmentIds.toArray(ids);
		List<Department> list = departmentDao.getByIds(ids);
		for(Department d : list){
			if(!jsonArray.contains(d.getId())){
				jsonArray.add(d.getId());
			}
		}
		if(!jsonArray.containsAll(departmentIds)){
			ifSuccess = false;
		}
		department.setSonDepartmentIds(jsonArray.toJSONString());
		departmentDao.updateAllSonDepartments(ids, id);
		departmentDao.update(department);
		return ifSuccess;
	}

	@Override
	public boolean deleteSonDepartments(Long id, List<Long> departmentIds) {
		boolean ifSuccess = true;
		Department department = departmentDao.getById(id);
		if(department == null||department.getSonDepartmentIds()==null||"".equals(department.getSonDepartmentIds())){
			return false;
		}
		JSONArray jsonArray = JSON.parseArray(department.getSonDepartmentIds());
		if(jsonArray.size()==0)return false;
		if(!jsonArray.containsAll(departmentIds)){
			ifSuccess = false;
		}
		Long[] ids = new Long[departmentIds.size()];
		ids = departmentIds.toArray(ids);
		JSONArray array = new JSONArray();
		for(Object departmentId:jsonArray){
			Long _id =Long.parseLong(departmentId.toString());
			if(!departmentIds.contains(_id)){
				array.add(_id);
			}
		}
		department.setSonDepartmentIds(array.toJSONString());
		departmentDao.updateAllSonDepartments(ids, null);
		departmentDao.update(department);
		return ifSuccess;
	}

	@Override
	public boolean deleteDepartment(Long id) {
		Department department = departmentDao.getById(id);
		if(department == null){
			return false;
		}
		if(department.getSuperiorDepartmentId()!=null){
			Department superiorDepartment = departmentDao.getById(department.getSuperiorDepartmentId());
			JSONArray jsonArray = JSON.parseArray(superiorDepartment.getSonDepartmentIds());
			JSONArray array = new JSONArray();
			for(Object departmentId:jsonArray){
				Long _id =Long.parseLong(departmentId.toString());
				if(_id!=id){
					array.add(_id);
				}
			}
			superiorDepartment.setSonDepartmentIds(array.toJSONString());
			departmentDao.update(superiorDepartment);
		}
		if(department.getSonDepartmentIds()!=null&&!"".equals(department.getSonDepartmentIds())
				&&!"[]".equals(department.getSonDepartmentIds())){
			JSONArray jsonArray = JSON.parseArray(department.getSonDepartmentIds());
			Long[] ids = new Long[jsonArray.size()];
			for(int i=0;i<ids.length;i++){
				ids[i] = jsonArray.getLong(i);
			}
			departmentDao.updateAllSonDepartments(ids, null);
		}
		departmentDao.delete(department);
		return true;
	}

	@Override
	public boolean modifySuperiorDepartment(Long id, Long sid) {
		Department department = departmentDao.getById(id);
		if(department == null){
			return false;
		}
		if(department.getSuperiorDepartmentId()!=null){
			Department superiorDepartment = departmentDao.getById(department.getSuperiorDepartmentId());
			JSONArray jsonArray = JSON.parseArray(superiorDepartment.getSonDepartmentIds());
			JSONArray array = new JSONArray();
			for(Object departmentId:jsonArray){
				Long _id =Long.parseLong(departmentId.toString());
				if(_id!=id){
					array.add(_id);
				}
			}
			superiorDepartment.setSonDepartmentIds(array.toJSONString());
			departmentDao.update(superiorDepartment);
		}
		if(sid==null||sid==0){
			department.setSuperiorDepartmentId(null);
			departmentDao.update(department);
			return true;
		}
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		this.addSonDepartments(sid, ids);
		return false;
	}

	@Override
	public boolean createNewDepartment(Department department) {
		String sonDepartmentIds = department.getSonDepartmentIds();
		department.setSonDepartmentIds(null);
		if(sonDepartmentIds!=null&&!"".equals(sonDepartmentIds)
				&&!"[]".equals(sonDepartmentIds)){
			JSONArray jsonArray = JSON.parseArray(sonDepartmentIds);
			Long[] ids = new Long[jsonArray.size()];
			for(int i=0;i<ids.length;i++){
				ids[i] = jsonArray.getLong(i);
			}
			List<Department> list = departmentDao.getByIds(ids);
			if(list.isEmpty())return false;
			for(Department d : list){
				if(d.getSuperiorDepartmentId()!=null)
					return false;
			}
		}
		boolean isSuccess = departmentDao.save(department);
		if(!isSuccess)return false;
		if(department.getSuperiorDepartmentId()!=null){
			List<Long> ids = new ArrayList<Long>();
			ids.add(department.getId());
			isSuccess = this.addSonDepartments(department.getSuperiorDepartmentId(), ids)&&isSuccess;
		}
		if(sonDepartmentIds!=null&&!"".equals(sonDepartmentIds)
				&&!"[]".equals(sonDepartmentIds)){
			JSONArray jsonArray = JSON.parseArray(sonDepartmentIds);
			List<Long> ids = new ArrayList<Long>();
			for(Object id : jsonArray){
				ids.add(Long.parseLong(id.toString()));
			}
			isSuccess = this.addSonDepartments(department.getId(),ids)&&isSuccess;
		}
		return isSuccess;
	}

	@Override
	public boolean modifyDepartmentName(Long id, String newName) {
		if(newName==null||"".equals(newName)){
			return false;
		}
		Department department = departmentDao.getById(id);
		if(department == null){
			return false;
		}
		department.setDepartmentName(newName);
		departmentDao.update(department);
		return true;
	}

	@Override
	public List<Department> getFirstDepartments() {
		return departmentDao.getFirstDepartments();
	}

	@Override
	public Department getById(Long id) {
		return departmentDao.getById(id);
	}

	@Override
	public Department getByName(String name) {
		return departmentDao.getByName(name);
	}

	@Override
	public List<Department> getAllDepartments() {
		return departmentDao.getAll();
	}
}
