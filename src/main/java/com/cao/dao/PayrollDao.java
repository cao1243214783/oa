package com.cao.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cao.bean.Payroll;
import com.cao.utils.Utils;

@Repository
public class PayrollDao extends BaseDao<Payroll>{
	
	public List<Payroll> getPayRoll(Long employeeId,String time){
		String hql = "from payroll where 1=1 ";
		hql = Utils.joinHql(hql, "employeeId", employeeId);
		if(time!=null){
			hql = hql+" and time like :time";
		}
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		Utils.setParameter(query, "employeeId", employeeId);
		if(time!=null){
			query.setParameter("time", "%"+time+"%");
		}
		List<Payroll> list = query.list();
		session.close();
		return list;
	}
	
	public void deleteAllRepeatedRecord(Date date) {
		String sql = "delete from payroll WHERE time=:time";
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("time", Utils.YYYY_MM.format(date));
		query.executeUpdate();
		session.close();
	}

}
