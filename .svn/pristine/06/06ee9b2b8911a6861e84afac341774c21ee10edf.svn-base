package com.ivant.cms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.constants.CompanyConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CompanyInterceptor implements Interceptor{

	private static final long serialVersionUID = 6680786820458509209L;
	private static final Logger logger = Logger.getLogger(CompanyInterceptor.class);
	
	public static final String COMPANY_REQUEST_KEY = "company";
	
	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	public void destroy() {
		logger.debug("DESTROYING COMPANY INTERCEPTOR...");
	}

	public void init() {
		logger.debug("CREATING COMPANY INTERCEPTOR...");
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		logger.debug("COMPANY INTERCEPTOR CALLED...");
		 
		try {
			String serverName = request.getServerName(); 
			
			logger.debug("Server Name..." + serverName);
			
			Company company = null;
			List<Company> companies;
			List<SavedEmail> emails;
			
			Company sessCompany = null;
			long companyId = 0;
			
			if(StringUtils.isNotEmpty(request.getParameter("companyId")))
			{
				try
				{
					companyId = Long.parseLong(request.getParameter("companyId"));
					sessCompany = companyDelegate.find(companyId);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			if(session.getAttribute(COMPANY_REQUEST_KEY) != null) {
				companyId = (Long) session.getAttribute(COMPANY_REQUEST_KEY);
				sessCompany = companyDelegate.find(companyId);
			}
		
			if(sessCompany == null) {
				if(serverName.equals("localhost")) {
					companyId = Long.parseLong(request.getParameter("companyId"));
					company = companyDelegate.find(companyId);
				}
				else {
					serverName = request.getServerName().replace("www.", "");
					company = companyDelegate.findServerName(serverName);
					
					if(company == null)
					{
						serverName = request.getServerName().replace("webtogo.com.ph", "");
						if(serverName != null && serverName.length() > 0)
						{
							company = companyDelegate.find(serverName);
						}
					}
				} 
			}
			else {
				if(sessCompany != null) {
					if(serverName.equals("localhost")) {
						Company newCompany = null;
						if(request.getParameter("companyId") != null) {
							companyId = Long.parseLong(request.getParameter("companyId"));
							
							newCompany = companyDelegate.find(companyId);
							if(!sessCompany.equals(newCompany)) {
								company = newCompany;
//								session.setAttribute(COMPANY_REQUEST_KEY, company);
							}
							else {
								company = sessCompany;
							}
						} 
						else {
							company = sessCompany;
						}
					}
					else {
						company = sessCompany;
					}
				}
			}
			
			if(company != null && CompanyConstants.ROBINSONSBANK != company.getId())
			{
				final String[] orderBy = {"nameEditable"};
				
				companies = companyDelegate.findAll(orderBy).getList();
				emails = savedEmailDelegate.findAll(company).getList();
						
				request.setAttribute("companies", companies);
				request.setAttribute("emails", emails);
			}
			 
			request.setAttribute(COMPANY_REQUEST_KEY, company);
			//
			if(company != null) {
				session.setAttribute(COMPANY_REQUEST_KEY, company.getId());
				session.setAttribute("hasChat",company.getCompanySettings().getHasClientChat());//for chat
				session.setAttribute("companyName",company.getName());//for chat
			}
			
			logger.debug("COMPANY(admin): " + company);
			
			Object action = invocation.getAction();
			if (action instanceof CompanyAware) {
				((CompanyAware)action).setCompany(company);
			} 
		}
		catch(Exception e) {
			logger.debug("company is null " + e);
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
