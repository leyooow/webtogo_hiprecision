package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.entity.Language;
import com.ivant.cms.interfaces.LanguageAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LanguageInterceptor implements Interceptor {

	private static final long serialVersionUID = 2213220221768773180L;
	private LanguageDelegate languageDelegate = LanguageDelegate.getInstance();

private static final Logger logger = Logger.getLogger(LanguageInterceptor.class);
	
	public static final String LANGUAGE_SESSION_KEY = "language_id";
	public static final String LANGUAGE_REQUEST_KEY = "language";
	
	@Override
	public void destroy() {
		logger.debug("DESTROYING LANGUAGE INTERCEPTOR.......");
	}

	@Override
	public void init() {
		logger.debug("INITIALIZING LANGUAGE INTERCEPTOR");
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		try
		{
			Long languageId = (Long)session.getAttribute(LanguageInterceptor.LANGUAGE_SESSION_KEY);
			Language language = languageDelegate.find(languageId);
			
			if(language != null)
			{
				Object action = invocation.getAction();
				
				if(action instanceof LanguageAware){
					logger.debug("Set Language Aware.");
					logger.debug(language);
					((LanguageAware)action).setLanguage(language);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return invocation.invoke();
	}

}
