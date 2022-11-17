package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class RemoteApplyInterceptor implements Interceptor  {
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
//		String requestUrl = request.getHeader("Referer");
		String requestUrl = request.getRemoteAddr();
		
//		requestUrl = request.getHeader("HTTP_X_FORWARDED_FOR");
		
		if (requestUrl.contains("0:0:0:0:0:0:0:1") || requestUrl.contains("127.0.0.")) {
			session.setAttribute("isLocal", true);
		} else {
			session.setAttribute("isLocal", false);
		}
		
		return invocation.invoke();
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		
	}
}
