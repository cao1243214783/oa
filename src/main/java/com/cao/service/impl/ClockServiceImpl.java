package com.cao.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cao.bean.Clock;
import com.cao.dao.ClockDao;
import com.cao.service.ClockService;
import com.cao.utils.Utils;

@Service
public class ClockServiceImpl implements ClockService{
	
	@Autowired
	private ClockDao clockDao;

	@Override
	public void signIn(Long employeeId) {
		List<Clock> clocks = clockDao.getByEmployeeIdAndTime(employeeId,Utils.YYYY_MM_DD.format(new Date()));
		if (clocks!=null) {
			return;
		}
		Clock clock = new Clock(employeeId,new Date());
		clockDao.save(clock);
	}

	@Override
	public void signOut(Long employeeId) {
		List<Clock> clocks = clockDao.getByEmployeeIdAndTime(employeeId,Utils.YYYY_MM_DD.format(new Date()));
		if (clocks == null || clocks.isEmpty()){
			Clock clock = new Clock();
			clock.setEmployeeId(employeeId);
			clock.setSignOutTime(new Date());
			clock.setTime(Utils.YYYY_MM_DD.format(new Date()));
			clock.setWorkTime(0);
			clockDao.save(clock);
		}else{
			Clock clock = clocks.get(0);
			Date signOutTime = new Date();
			clock.setSignOutTime(signOutTime);
			Date signInTime = clock.getSignInTime();
			if (signInTime != null) {
				clock.setWorkTime(((double)signOutTime.getTime()-signInTime.getTime())/(1000*60*60));
			}else{
				clock.setWorkTime(0);
			}
			clockDao.update(clock);
		}
	}
	
	@Override
	public List<Clock> getByEmployeeIdAndTime(Long employeeId, Date date) {
		return clockDao.getByEmployeeIdAndTime(employeeId,Utils.YYYY_MM_DD.format(date));
	}

	@Override
	public Clock getById(Long id) {
		// TODO Auto-generated method stub
		return clockDao.getById(id);
	}

	@Override
	public void reissue(Long employeeId, Date date, boolean b) {
		List<Clock> list = clockDao.getByEmployeeIdAndTime(employeeId,Utils.YYYY_MM_DD.format(date));
		if(list!=null){
			Clock clock = list.get(0);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			if(b){
				calendar.set(Calendar.HOUR_OF_DAY, 9);
				calendar.set(Calendar.MINUTE, 0);
				clock.setSignInTime(calendar.getTime());
				Date signOutTime = clock.getSignInTime();
				if (signOutTime != null) {
					clock.setWorkTime(((double)signOutTime.getTime()-calendar.getTime().getTime())/(1000*60*60));
				}else{
					clock.setWorkTime(0);
				}
			}else{
				calendar.set(Calendar.HOUR_OF_DAY, 17);
				calendar.set(Calendar.MINUTE, 30);
				clock.setSignOutTime(calendar.getTime());
				Date signInTime = clock.getSignInTime();
				if (signInTime != null) {
					clock.setWorkTime(((double)calendar.getTime().getTime()-signInTime.getTime())/(1000*60*60));
				}else{
					clock.setWorkTime(0);
				}
			}
			clockDao.update(clock);
		}else{
			Clock clock = new Clock();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			if(b){
				calendar.set(Calendar.HOUR_OF_DAY, 9);
				calendar.set(Calendar.MINUTE, 0);
				clock.setSignInTime(calendar.getTime());
				Date signOutTime = clock.getSignInTime();
				if (signOutTime != null) {
					clock.setWorkTime(((double)signOutTime.getTime()-calendar.getTime().getTime())/(1000*60*60));
				}else{
					clock.setWorkTime(0);
				}
			}else{
				calendar.set(Calendar.HOUR_OF_DAY, 17);
				calendar.set(Calendar.MINUTE, 30);
				clock.setSignOutTime(calendar.getTime());
				Date signInTime = clock.getSignInTime();
				if (signInTime != null) {
					clock.setWorkTime(((double)calendar.getTime().getTime()-signInTime.getTime())/(1000*60*60));
				}else{
					clock.setWorkTime(0);
				}
			}
			clock.setEmployeeId(employeeId);
			clock.setTime(Utils.YYYY_MM_DD.format(new Date()));
			clockDao.save(clock);
		}
	}

	@Override
	public List<Clock> getByMonth(Long employeeId,String time) {
		return clockDao.getByEmployeeIdAndMonth(employeeId, time);
	}
	
}
