package com.cao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cao.bean.User;

@Repository
public class UserDao extends BaseDao<User>{
	
	public User getUserByUsernameAndPassword(String username,String password) {
		String hql = "from user where username=:username and password=:password and ifAvailable=true";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("password", password);
		List<User> list = query.list();
		session.close();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public User getUserByUsername(String username){
		String hql = "from user where username=:username";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		List<User> list = query.list();
        session.close();
        if(list.isEmpty()){
        	return null;
        }
		return list.get(0);
	}
	
	public User getUserByEmployeeId(Long employeeId){
		String hql = "from user where employeeId=:employeeId";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("employeeId", employeeId);
		List<User> list = query.list();
        session.close();
        if(list.isEmpty()){
        	return null;
        }
		return list.get(0);
	}
}
