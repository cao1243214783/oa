package com.cao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cao.bean.Files;

@Repository
public class FilesDao extends BaseDao<Files> {

	public List<Files> findByName(String name) {
		String hql = "from files where filename = :name";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("name", "%" + name + "%");
		List<Files> list = query.list();
		session.close();
		return list;
	}

	public List<Files> getByEmployeeId(Long employeeId) {
		String hql = "from files where employeeId = :employeeId";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("employeeId", employeeId);
		List<Files> list = query.list();
		session.close();
		return list;
	}

}
