package com.ivant.cms.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.ivant.cms.entity.Company;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements Action, CompanyAware{

	private static final long serialVersionUID = 1L;
	private Company company;
	
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(true);
		session.invalidate();
		request.setAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY, company);
		return Action.LOGIN;
	}

	public void setCompany(Company company)
	{
		this.company = company;
	}
}
