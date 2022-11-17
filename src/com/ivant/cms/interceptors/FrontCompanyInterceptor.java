package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.interfaces.CompanyAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/*
 * This class will be used only on the front end of the application since
 * the company is acquired through the user entity on the back-end
 */

public class FrontCompanyInterceptor implements Interceptor {

	private static final long serialVersionUID = 7131228573814104318L;
	private static final Logger logger = Logger.getLogger(FrontCompanyInterceptor.class);
	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private static final String SUSPENDED = "suspended";
	 
	public static final String COMPANY_REQUEST_KEY = "company";
	
	private Company company;
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void destroy() {
		logger.info("DESTROYING COMPANY INTERCEPTOR");
	}

	public void init() {
		logger.info("CREATING COMPANY INTERCEPTOR");
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		try {			
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String servletName = ServletActionContext.getServletContext().getServletContextName();
			//System.out.println("said"+servletName);
			String[] uri = request.getRequestURI().replaceAll("/" + servletName, "").split("/");
			logger.info("...finding company with name: \"" + uri[2] + "\"");
			Company company = companyDelegate.find(uri[2]);
			
			if (company!= null  && company.getCompanySettings().getSuspended())
			{
				//System.out.println(company.getNameEditable() + " SUSPENDED==================");
				session.setAttribute("companySuspended", company);
				return SUSPENDED;
			}	
			Object action = invocation.getAction();
			if(action instanceof CompanyAware) {
				((CompanyAware)action).setCompany(company);
			}
			
			request.setAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY, company);
			
			session.setAttribute("frontCompany", company.getName());// for chat
			
			request.setAttribute("com", company);
			
			request.setAttribute("company", company);
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action in company interceptor...");
		}
		return invocation.invoke();
	}

	public static Company getFromSession()
	{
		try
		{
			return companyDelegate.find((Long)ServletActionContext.getRequest().getSession().getAttribute(COMPANY_REQUEST_KEY));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
