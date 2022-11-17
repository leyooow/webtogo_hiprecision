package com.ivant.cms.delegate;

import com.ivant.cms.db.ScheduleDAO;
import com.ivant.cms.entity.Schedule;

public class ScheduleDelegate extends  AbstractBaseDelegate<Schedule, ScheduleDAO>{

	private static ScheduleDelegate instance = new ScheduleDelegate();
	
	public static ScheduleDelegate getInstance() {
		return instance;
	}
	
	public ScheduleDelegate() {
		super(new ScheduleDAO());
	}
}
