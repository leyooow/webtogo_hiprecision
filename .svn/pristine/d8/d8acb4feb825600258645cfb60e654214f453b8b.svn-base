package com.ivant.cms.interceptors;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.wrapper.XssRequestWrapper;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author Kevin Roy K. Chua
 *
 * @version 1.0 Jun 5, 2013
 * @since 1.0, Jun 5, 2013
 *
 */
public class XssInterceptor
		implements Interceptor 
{	
	private static final long serialVersionUID = -4731753024503081823L;

	private static final Logger logger = LoggerFactory.getLogger(XssInterceptor.class);
	
	@Override
	public void destroy()
	{
		logger.debug("DESTROYING XSS INTERCEPTOR...");
	}

	@Override
	public void init()
	{
		logger.debug("INITIALIZING XSS INTERCEPTOR...");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{		
		logger.debug("Xss intercept was invoked...");
		
		Object action = invocation.getAction();
		
		if (action instanceof AbstractBaseAction)
		{		
			((AbstractBaseAction) action).setServletRequest(new XssRequestWrapper(ServletActionContext.getRequest()));			
		}
		
		return invocation.invoke();
	}
}