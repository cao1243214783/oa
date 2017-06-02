package com.cao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cao.bean.Clock;

@Repository
public class ClockDao extends BaseDao<Clock>{

	public List<Clock> getByEmployeeIdAndTime(Long employeeId, String date) {
		String hql = "from clock where employeeId=:employeeId and time=:date";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("employeeId", employeeId);
		query.setParameter("date", date);
		List<Clock> list = query.list();
		session.close();
		if(list.isEmpty())
			list=null;
		return list;
	}
	
	public List<Clock> getByEmployeeIdAndMonth(Long employeeId, String date) {
		String hql = "from clock where employeeId=:employeeId and time=:date";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("employeeId", employeeId);
		query.setParameter("date", date+"%");
		List<Clock> list = query.list();
		session.close();
		return list;
	}


}
