package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.interfaces.KuysenClientAware;
import com.ivant.cms.interfaces.KuysenSalesTransactionAware;
import com.ivant.constants.KuysenSalesConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class KuysenSalesTransactionInterceptor implements Interceptor {

	private static final long serialVersionUID = -6491355998563987926L;

	private static final Logger logger = Logger.getLogger(UserInterceptor.class);

	public void destroy() {	
		logger.debug("DESTROYING KUYSEN TRANSACTION INTERCEPTOR...");
	}

	public void init() {
		logger.debug("INITIALIZING KUYSEN TRANSACTION INTERCEPTOR...");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		try {
			KuysenSalesTransactionBean kuysenTransaction = (KuysenSalesTransactionBean) session.getAttribute(KuysenSalesConstants.KUYSEN_TRANSACTION);			
			
			if(kuysenTransaction == null) {
				kuysenTransaction = new KuysenSalesTransactionBean();
				session.setAttribute(KuysenSalesConstants.KUYSEN_TRANSACTION, kuysenTransaction);
			}
			
			session.setAttribute(KuysenSalesConstants.KUYSEN_CLIENT_BEAN, kuysenTransaction.getClient());

				Object action = invocation.getAction();
				if(action instanceof KuysenSalesTransactionAware){
					((KuysenSalesTransactionAware)action).setKuysenSalesTransactionBean(kuysenTransaction);
				}
				
				return invocation.invoke();

		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
			return Action.ERROR;
		}
	}
}
