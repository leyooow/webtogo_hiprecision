/**
 *
 */
package com.ivant.cms.server.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.utils.hibernate.HibernateListener;
import com.ivant.utils.hibernate.HibernateUtil;

/**
 * This listener class will be called after {@link HibernateListener}
 * @author Edgar S. Dacpano
 *
 */
public class StartupListener implements ServletContextListener
{
	private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	
	/**
	 * 
	 */
	public StartupListener()
	{
		super();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		logger.info(getClass() + " initialized.");
		
		try
		{
			if(!HibernateUtil.hasSessionCreated()) // for loading delegates
			{
				HibernateUtil.createSession();
			}
			
			// TODO stuff here.
			
		}
		catch(Exception e)
		{
			logger.error("An error occured.", e);
			e.printStackTrace();
		}
		finally
		{
			if(HibernateUtil.hasSessionCreated())
			{
				HibernateUtil.destroySession();
			}
		}
	}
	
}