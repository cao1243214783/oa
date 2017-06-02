package com.cao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cao.bean.Employee;
import com.cao.utils.Utils;

@Repository
public class EmployeeDao extends BaseDao<Employee>{

	public List<Employee> getEmployeesByDepartment(String department) {
		String hql = "from employee where termDate is null";
		hql = Utils.joinHql(hql, "department", department);
		hql = hql + " order by id";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		Utils.setParameter(query, "department", department);
		List<Employee> list = query.list();
		session.close();
		return list;
	}
	
	public List<Employee> getAllEmployees() {
		String hql = "from employee where termDate is null";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		List<Employee> list = query.list();
		session.close();
		return list;
	}

	public List<Employee> getEmployeeByName(String name) {
		String hql = "from employee where name=:name";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		List<Employee> list = query.list();
		session.close();
		return list;
	}

	public List<Employee> getleaveEmployees() {
		String hql = "from employee where termDate is not null";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		List<Employee> list = query.list();
		session.close();
		return list;
	}

}
