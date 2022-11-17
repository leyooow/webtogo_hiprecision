package com.ivant.cms.interceptors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MemberInterceptor implements Interceptor {
	private static final long serialVersionUID = 9021835519874364691L;

	private static final Logger logger = Logger.getLogger(MemberInterceptor.class);
	
	public static final String MEMBER_SESSION_KEY = "member_id";
	public static final String MEMBER_REQUEST_KEY = "member";
		
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	
	public void destroy() {	
		logger.debug("DESTROYING MEMBER INTERCEPTOR...");
	}

	public void init() {
		logger.debug("INITIALIZING MEMBER INTERCEPTOR...");
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		try {
			Long memberId = (Long)session.getAttribute(MemberInterceptor.MEMBER_SESSION_KEY);
			try{
				logger.info("Member Id From Session : "+ memberId);
			}catch(Exception e){
				
			}
			Member member = memberDelegate.find(memberId); 			
						
			if(member != null) {
				logger.debug("Member Aware");
				Object action = invocation.getAction();
				if(action instanceof MemberAware){
					logger.debug("Set Member Aware.");
					logger.debug(member);
					try{
						logger.info("Member Id of MEMBER : "+ member.getId());
					}catch(Exception e){
						
					}
					((MemberAware)action).setMember(member);
				}
				
				// save the member to the request
				request.setAttribute(MemberInterceptor.MEMBER_REQUEST_KEY, member);
				
				return invocation.invoke(); 
			}
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
		}
				
		return invocation.invoke();
	}
}
