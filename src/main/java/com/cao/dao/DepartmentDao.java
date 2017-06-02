package com.cao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cao.bean.Department;

@Repository
public class DepartmentDao extends BaseDao<Department>{
	
	public List<Department> getByIds(Long[] ids){
		String hql = "from department where id in (:ids)";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameterList("ids", ids);
		List<Department> list = query.list();
		session.close();
		return list;
	}
	
	public void updateAllSonDepartments(Long[] ids,Long id) {
		String sql = "UPDATE department SET superior_department_id=:id WHERE id IN (:ids)";
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(sql);
		query.setParameterList("ids", ids);
		query.setParameter("id", id);
		query.executeUpdate();
		session.close();
	}

	public List<Department> getFirstDepartments() {
		String hql = "from department where superiorDepartmentId is null";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		List<Department> list = query.list();
		session.close();
		return list;
	}
	
	public Department getByName(String name){
		String hql = "from department where departmentName=:name";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		List<Department> list = query.list();
		session.close();
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

}
