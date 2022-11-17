package com.ivant.cms.interceptors;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class UserInterceptor implements Interceptor {

	private static final long serialVersionUID = 3436972944441513001L;
	private static final Logger logger = Logger.getLogger(UserInterceptor.class);
	
	public static final String USER_SESSION_KEY = "user_id";
	public static final String USER_REQUEST_KEY = "user";
		
	private UserDelegate userDelegate = UserDelegate.getInstance();
	
	public void destroy() {	
		logger.debug("DESTROYING USER INTERCEPTOR...");
	}

	public void init() {
		logger.debug("INITIALIZING USER INTERCEPTOR...");
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String returnValue = Action.LOGIN;
		
		try {
			Long userId = (Long)session.getAttribute(UserInterceptor.USER_SESSION_KEY);
			Company company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
			User user = userDelegate.find(userId); 			

			if(user != null) {
				try{
					if(user.getCompany() != null){
						if(user.getCompany().getId() == CompanyConstants.GURKKA_TEST){
							user.setLastLogin(new Date());
							userDelegate.update(user);
						}
					}
				}
				catch(Exception e){
					logger.info(e.toString());
				}
				Object action = invocation.getAction();
				if(action instanceof UserAware){
					((UserAware)action).setUser(user);
				}
				
//				Company company = (Company) session.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
				
				if(user.getUserType() == UserType.SUPER_USER ||  user.getUserType() == UserType.WEBTOGO_ADMINISTRATOR) {					
					if(company != null) {
						user.setCompany(company);
						
						if (action instanceof CompanyAware) {
							((CompanyAware)action).setCompany(user.getCompany());
						}
					}
				} 
				 									 		
				// save the user to the request
				request.setAttribute(UserInterceptor.USER_REQUEST_KEY, user);
				session.setAttribute("userName", user.getFirstname()+" "+user.getLastname()); //for chat
				
				returnValue = invocation.invoke();
				
				if(company != null && !company.getIsValid()) {
					returnValue = Action.LOGIN;
				} 
			}
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
		}
				
		return returnValue;
	}
}
