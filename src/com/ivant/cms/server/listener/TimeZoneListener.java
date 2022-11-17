/**
 *
 */
package com.ivant.cms.server.listener;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Set the default timezone
 * 
 * @author Edgar S. Dacpano
 */
public class TimeZoneListener
		implements ServletContextListener
{
	private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	
	/**
	 * The timezone to set as default. Using - Asia/Manila, Philippines timezone
	 * 
	 * @see See <a
	 *      href="http://joda-time.sourceforge.net/timezones.html">Joda-Time
	 *      Available Time Zones</a>
	 */
	public static final String DEFAULT_TIMEZONE = "Asia/Manila";
	
	/**
	 * Set the {@link TimeZone} and {@link DateTimeZone}
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		logger.info("Setting Timezone from: " + TimeZone.getDefault() + " to: " + TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		final TimeZone defaultTimeZone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);
		final DateTimeZone defaultDateTimeZone = DateTimeZone.forTimeZone(defaultTimeZone);
		TimeZone.setDefault(defaultTimeZone);
		DateTimeZone.setDefault(defaultDateTimeZone);
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
	}
	
}
