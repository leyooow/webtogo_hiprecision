package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.KuysenClientAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.KuysenSalesConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class KuysenClientInterceptor implements Interceptor {

	private static final long serialVersionUID = -6491355998563987926L;

	private static final Logger logger = Logger.getLogger(UserInterceptor.class);

	public void destroy() {	
		logger.debug("DESTROYING KUYSEN CLIENT INTERCEPTOR...");
	}

	public void init() {
		logger.debug("INITIALIZING KUYSEN CLIENT INTERCEPTOR...");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		try {
			KuysenClientBean kuysenClient = (KuysenClientBean) session.getAttribute(KuysenSalesConstants.KUYSEN_CLIENT);			

			if(kuysenClient != null) {
				Object action = invocation.getAction();
				if(action instanceof KuysenClientAware){
					((KuysenClientAware)action).setKuysenClientBean(kuysenClient);
				}
				
				return invocation.invoke();
			} else {
				return KuysenSalesConstants.NO_CLIENT_TO_PROCESS;
			}
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
			return Action.ERROR;
		}
	}
}
