
package com.ivant.utils;

import javax.servlet.http.HttpServlet;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class JSUpdateSchedule extends HttpServlet {
	public JSUpdateSchedule ()throws Exception {
		SchedulerFactory sf=new StdSchedulerFactory();
	    Scheduler sched=sf.getScheduler();
	    JobDetail jd=new JobDetail("updateJS","updateJS",JSUpdateJob.class);
	    CronTrigger ct=new CronTrigger("cronTrigger","group2","30 10/10 0 * * ?");
	    sched.scheduleJob(jd,ct);
	    sched.start();   
	    //System.out.println("Schedule started constructor");
	}
	
}