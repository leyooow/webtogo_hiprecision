package com.ivant.utils.hibernate;

import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class HibernateListener implements ServletContextListener{
	private static Logger log = Logger.getLogger(HibernateListener.class);
	
	public void contextDestroyed(ServletContextEvent scx) {
		HibernateUtil.dispose();
	}

	public void contextInitialized(ServletContextEvent scx) {	
		log.info("current joda time: " + new DateTime());
		log.info("current jdk time: " + new Date());
		

		
		HibernateUtil.initAnnotation();
		NwdiHibernateUtil.initAnnotation();
//		HibernateUtil.createSession();
	}

}

