package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CategoryAware;
import com.ivant.cms.interfaces.MobileAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MobileInterceptor implements Interceptor{

	private static final long serialVersionUID = -8753042811521054802L;
	private static final Logger logger = Logger.getLogger(MobileInterceptor.class);
	
	
	public void destroy() {
		logger.debug("DESTROYING MOBILE INTERCEPTOR...");
	}

	public void init() {
		logger.debug("CREATING MOBILE INTERCEPTOR...");
	}

	public String intercept(ActionInvocation inv) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session=request.getSession();
	
		Object action = inv.getAction();
		String m=null;
		if(session.getAttribute("m")!=null){
			m=(String)session.getAttribute("m");
			
		}else{
			m=(String)request.getAttribute("m");
		}
		
		
		session.setAttribute("m", m);
		
		if (action instanceof MobileAware) {
			((MobileAware)action).setM(m);
			
		}
		
		return inv.invoke();
	}

	
}

