package com.cao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cao.bean.Admin;

@Repository
public class AdminDao extends BaseDao<Admin>{
	
	public Admin getAdminByUsernameAndPassword(String username,String password) {
		String hql = "from admin where username=:username and password=:password";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("password", password);
		List<Admin> list = query.list();
		session.close();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
