package com.ivant.utils.hibernate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;

import com.ivant.cms.entity.NwdiPatient;
import com.ivant.cms.entity.NwdiResult;
import com.ivant.cms.helper.NwdiHelper;

public class NwdiHibernateUtil {

	private static Logger log = Logger.getLogger(NwdiHibernateUtil.class);
	private static SessionFactory sessionFactory = null;
	public static AnnotationConfiguration configuration = null;
	
	private static final ThreadLocal<Session> localSession = new ThreadLocal<Session>();
	
	private static final Lock lock = new ReentrantLock();
	
	public static AnnotationConfiguration getConfiguration()
	{
		return configuration;
	}
	
	private static void addClassesToAnnotationConf(AnnotationConfiguration conf) {
		for(Class<?> c : getClasses())
		{
			conf.addAnnotatedClass(c);
		}
	} 

	
	private static List<Class<?>> getClasses()
	{
		final List<Class<?>> classes = new LinkedList<Class<?>>();
		
		classes.add(NwdiPatient.class);
		classes.add(NwdiResult.class);
		return Collections.unmodifiableList(classes);
	}
	
//	private static void setUpC3P0(AnnotationConfiguration conf)
//	{
//		conf.setProperty(Environment.C3P0_ACQUIRE_INCREMENT, "1");
//		conf.setProperty(Environment.C3P0_IDLE_TEST_PERIOD, "300");
//		conf.setProperty(Environment.C3P0_MAX_SIZE, "100");
//		conf.setProperty(Environment.C3P0_MAX_STATEMENTS, "0");
//		conf.setProperty(Environment.C3P0_MIN_SIZE, "10");
//		conf.setProperty(Environment.C3P0_TIMEOUT, "100");
//	}
	
	public static void initAnnotation()
	{
		
		if(!NwdiHelper.toInludeSession()) return;
		
		//check if selenium test will be conducted
		lock.lock();
		try
		{
			dispose();
			AnnotationConfiguration conf = new AnnotationConfiguration();
			/*conf.setProperty(Environment.DATASOURCE, "java:comp/env/jdbc/datasource");*/
			conf.setProperty(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conf.setProperty(Environment.URL, "jdbc:sqlserver://nwdi.com.ph\\sql2012prncpl:1450;databaseName=CIMS");
			conf.setProperty(Environment.USER, "erecords");
			conf.setProperty(Environment.PASS, "erecords");
			
			conf.setProperty(Environment.CACHE_PROVIDER, "org.hibernate.cache.NoCacheProvider");
			conf.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
			conf.setProperty(Environment.DIALECT, "org.hibernate.dialect.SQLServerDialect");
			conf.setProperty(Environment.AUTOCOMMIT, "false");
			conf.setProperty(Environment.SHOW_SQL, "false");
			conf.setProperty(Environment.HBM2DDL_AUTO, "update");
			conf.setProperty(Environment.TRANSACTION_STRATEGY, "org.hibernate.transaction.JDBCTransactionFactory");
			
			addClassesToAnnotationConf(conf);
			configuration = conf;
			
			sessionFactory = conf.buildSessionFactory();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Dispose/Close the current sessionFactory.
	 *
	 */
	public static void dispose()
	{
		lock.lock();
		try
		{
			destroySession();
			if (sessionFactory != null)
			{
				sessionFactory.close();
			}
			sessionFactory = null; // initiate garbage collection
			configuration = null;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * get the session factory
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static boolean hasSessionCreated()
	{
		return localSession.get() != null;
	}
	
	
	public static void createSession() {
		createSession(null);
	}
	/**
	 * Create a session and store it in a thread local.
	 * @return the session created
	 */
	public static Session createSession(HttpServletRequest request) {
		
		if(!NwdiHelper.toInludeSession(request)) return null;
		
		Session s = localSession.get();
		// Open a new Session, if this Thread has none yet
		if (s != null) {
			s.close();
			throw new IllegalStateException("There is an opened session!");
		}
		
		lock.lock();
		try
		{
			log.debug("Creating a new session! Don't forget to close this session!");
			s = sessionFactory.openSession();
			localSession.set(s);
			return s;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static void destroySession() 
	{
		if(!NwdiHelper.toInludeSession()) return;
		
		Session s = localSession.get();
		localSession.set(null);
		localSession.remove();
		if (s != null)
		{
			log.debug("Closing current session");
			try
			{
				s.close();
			}
			catch (HibernateException e)
			{
				log.fatal("HibernateException caught while closing session!", e);
			}
		}
	}

	/**
	 * get the current session
	 * @return
	 */
	public static Session currentSession() {
		Session s = localSession.get();
		if (s == null) {
			createSession();
		}
		return s;
	}

	/**
	 * this method does not do a thing... if you want to close the session call {@link #destroySession()}
	 * 
	 */
	public static void closeSession(){

	}

	
}
