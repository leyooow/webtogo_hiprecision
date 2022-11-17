package com.ivant.cms.action.admin;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class SortMultiPageAction extends ActionSupport implements UserAware, CompanyAware, 
									Preparable, ServletRequestAware {

	private static final long serialVersionUID = -3515243781604234991L;
	private static final Logger logger = Logger.getLogger(SortMultiPageAction.class);
	private static final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private static final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	
	private User user;
	private Company company;
	private HttpServletRequest request;
	
	private MultiPage multiPage;
	private List<SinglePage> multiPageItems;
	
	@Override
	public String execute() throws Exception {
		if(company == null) {
			return ERROR;
		}
		
		if(multiPage == null) {
			return ERROR;
		}
		
		multiPageItems = singlePageDelegate.findAll(company, multiPage, Order.asc("sortOrder")).getList();

		return SUCCESS;
	}
	
	public void prepare() throws Exception {
		try {
			long multiPageId = Long.parseLong(request.getParameter("multipage_id"));	
			multiPage = multiPageDelegate.find(multiPageId);
			if(!multiPage.getCompany().equals(company)) {
				multiPage = null;
			}
		}
		catch(Exception e) { 
			logger.debug("unable to find any multiPage_id parameter", e);
		}
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	
	public List<SinglePage> getMultiPageItems() {
		return multiPageItems;
	} 
	
	public MultiPage getMultiPage() {
		return multiPage;
	}
}
