package com.ivant.cms.interceptors;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.helper.NwdiHelper;
import com.ivant.utils.hibernate.HibernateUtil;
import com.ivant.utils.hibernate.NwdiHibernateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * 
 * @author Jobert
 *
 */
public class HibernateInterceptor implements Interceptor{
	private static final long serialVersionUID = -7728720111521134653L;
	private static final Logger log = Logger.getLogger(HibernateInterceptor.class);
	
	public void destroy() {
		log.debug("DESTROY INTERCEPTOR: Hibernate Interceptor");
	}

	public void init() {
		log.debug("INIT INTERCEPTOR: Hibernate Interceptor");
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		
		try
		{
			
			HibernateUtil.createSession();
			NwdiHibernateUtil.createSession(ServletActionContext.getRequest());
				
			String result = invocation.invoke();
			return result;
		}
		finally
		{
			HibernateUtil.destroySession();
			NwdiHibernateUtil.destroySession();			
		}
	}	
}

