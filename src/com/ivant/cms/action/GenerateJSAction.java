package com.ivant.cms.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.entity.Company;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.utils.JSWriter;
import com.opensymphony.xwork2.Action;

public class GenerateJSAction implements Action, ServletRequestAware,
CompanyAware{
	private Company company;
	private HttpServletRequest request;
	@Override
	public String execute() throws Exception {
		JSWriter jswriter = new JSWriter();
		
		jswriter.createJSFile("roxas", "http://www.pse.com.ph/stockMarket/companyInfo.html?id=64&security=250&tab=0&method=fetchHeaderData&ajax=true");
		try { Thread.currentThread().sleep(30000); }
		catch ( Exception e ) { }	
		
		jswriter.createJSFile("cadp", "http://www.pse.com.ph/stockMarket/companyInfo.html?id=54&security=363&tab=0&method=fetchHeaderData&ajax=true");
		try { Thread.currentThread().sleep(30000); }
		catch ( Exception e ) { }	
		
		jswriter.createJSFile("bhi", "http://www.pse.com.ph/stockMarket/companyInfo.html?id=63&security=187&tab=0&method=fetchHeaderData&ajax=true");
		return Action.SUCCESS;  
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;	
	}
}
