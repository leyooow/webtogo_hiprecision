package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.entity.Member;
import com.ivant.cms.interfaces.MemberAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MemberLoginInterceptor implements Interceptor{

	private static final long serialVersionUID = 3365786524030941304L;

	private static final Logger logger = Logger.getLogger(MemberLoginInterceptor.class);
	
	public static final String MEMBER_SESSION_KEY = "member_id";
	public static final String MEMBER_REQUEST_KEY = "member";
		
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		try {
			Long memberId = (Long)session.getAttribute(MemberInterceptor.MEMBER_SESSION_KEY);
			Member member = memberDelegate.find(memberId); 			
						
			if(member != null) {
				logger.debug("Member Aware");
				Object action = invocation.getAction();
				if(action instanceof MemberAware){
					logger.debug("Set Member Aware.");
					logger.debug(member);
					((MemberAware)action).setMember(member);
				}
				
				
				return invocation.invoke(); 
			} else
				return "login";
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
		}
				
		return invocation.invoke();
	}

}
