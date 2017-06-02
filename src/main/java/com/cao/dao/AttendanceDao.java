package com.cao.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cao.bean.Attendance;
import com.cao.utils.Utils;

@Repository
public class AttendanceDao extends BaseDao<Attendance>{
	
	public List<Attendance> getAll(Short isAllowed,Date startTime,Date endTime){
		String hql = "from attendance where isAllowed=:isAllowed";
		if(startTime!=null && endTime!=null){
			hql = hql + " and leaveDate >= :startTime and leaveDate <= :endTime";
		}
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("isAllowed", isAllowed);
		Utils.setParameter(query, "startTime", startTime);
		Utils.setParameter(query, "endTime", endTime);
		List<Attendance> list = query.list();
		session.close();
		return list;
	}
	
	public void setRequestFailure() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		String sql = "UPDATE attendance SET is_allowed=3 WHERE leave_date < :date and is_allowed = 0";
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("date", calendar.getTime());
		query.executeUpdate();
	}

	public List<Attendance> getByEmployeeId(Long id) {
		String hql = "from attendance where employeeId=:id";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		List<Attendance> list = query.list();
		session.close();
		return list;
	}
}
