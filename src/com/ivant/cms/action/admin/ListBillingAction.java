package com.ivant.cms.action.admin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.BillingDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.BillingStatusEnum;
import com.ivant.cms.enums.BillingTypeEnum;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListBillingAction implements Action, UserAware, PagingAware, CompanyAware {

	private Logger logger = Logger.getLogger(getClass());
	private BillingDelegate billingDelegate = BillingDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	HttpServletRequest request;
	ServletContext servletContext;
	
	private Billing billing;
	private User user;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<Billing> billings;
	private List<BillingStatusEnum> billingStatuses;
	private List<BillingTypeEnum> billingTypes;
	
	public String execute() throws Exception {
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return Action.ERROR;
		}
		ObjectList<Billing> objectList = billingDelegate.findAllWithPaging(user.getCompany(), user.getItemsPerPage(), page, null, null, null, null, null, null);
		billings = objectList.getList();
		logger.info("billing size:   " + billings.size());
				
		// set the status and types
		billingStatuses = Arrays.asList(BillingStatusEnum.values());
		billingTypes = Arrays.asList(BillingTypeEnum.values());
		
		return Action.SUCCESS;
	}

	
	
	public int getPage() {
		return page;
	} 

	public void setPage(int page) {
		this.page = page;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}



	
	public int getTotalItems() {
		return totalItems;
	}
	
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public void setTotalItems() {
		request = ServletActionContext.getRequest();
		Company company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		this.totalItems = billingDelegate.findAll(company).getList().size();
		logger.info("totalItemsBilling:  " + this.totalItems + " " + user.getItemsPerPage());
//		this.totalItems = 20;
		logger.info("totalItemsBilling:  " + this.totalItems + " " + user.getItemsPerPage());		
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	

	
	
	
	
	

	public void setUser (User user) {
		this.user = user;
	}
	
	public void setBilling (Billing billing) {
		this.billing = billing;
	}
	

	public List<Billing> getBillings() {
		return billings;
	}
	
	
	public List<BillingStatusEnum> getBillingStatuses() {
		return billingStatuses;
	}
	
	public List<BillingTypeEnum> getBillingTypes() {
		return billingTypes;
	}



	@Override
	public void setCompany(Company company) {
		// TODO Auto-generated method stub
		
	}
	
	
}
