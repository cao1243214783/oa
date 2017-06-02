package com.cao.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T>{
	
	@Autowired
	protected SessionFactory sessionFactory;

	private Class<T> clazz;
	
	public BaseDao() {
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	public boolean save(T t) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        long id = (Long) session.save(t);
        session.getTransaction().commit();
        session.close();
        return id!=0;
	}

	public void update(T t){
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit(); 
        session.close();
	}
	
	public void delete(T t){
		Session session = sessionFactory.openSession();
        session.beginTransaction();  
        session.delete(t);  
        session.getTransaction().commit(); 
        session.close();
	}

	public T getById(Long id){
		String hql = "from " + clazz.getSimpleName().toLowerCase() +" where id=:id";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		List<T> list = query.list();
		session.close();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<T> getAll(){
		String hql = "from " + clazz.getSimpleName().toLowerCase();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		List<T> list = query.list();
		session.close();
		return list;
	}
}
