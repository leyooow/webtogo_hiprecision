package com.ivant.cms.interceptors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.TermsDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class TermsInterceptor implements Interceptor {

	private static final long serialVersionUID = 1998819571246373971L;
	private static final Logger logger = Logger.getLogger(TermsInterceptor.class);
	private static final TermsDelegate termsDelegate = TermsDelegate.getInstance();
	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	
	public void destroy() {
		logger.debug("DESTROYING TERMS INTERCEPTOR...");
	}

	public void init() {
		logger.debug("INITIALIZING TERMS INTERCEPTOR...");
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		ServletContext servletContext = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		User user = (User) request.getAttribute(UserInterceptor.USER_REQUEST_KEY);
		if(user.getUserType() == UserType.SUPER_USER || user.getUserType() == UserType.WEBTOGO_ADMINISTRATOR) {
			return arg0.invoke();
		}
			
		logger.debug("REDIRECTING TO TERMS...");
		
		
//		Company company = (Company) session.getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		long companyId = 0L;
		if(user.getCompany().getName().equalsIgnoreCase("wendys")){
			companyId = 404L;
		}
		else{
			companyId = (Long) session.getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);
		}
		
		Company company = companyDelegate.find(companyId); 
		
		if(company != null) {
			if(termsDelegate.findByCompany(company) != null) {
				return arg0.invoke();
			}
		} 
		
		return "terms";
	}

	
	
}
