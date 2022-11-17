package com.ivant.cms.action.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListSinglePageAction implements Action, UserAware, PagingAware {

	private Logger logger = Logger.getLogger(getClass());
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	
	private User user;
	private Company company;
	private CompanySettings companySettings;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<SinglePage> singlePages;
		
	public String execute() throws Exception {
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return Action.ERROR;
		}
		
		company = user.getCompany();
		companySettings = company.getCompanySettings();
		
		Order[] order = {Order.asc("name")};
		ObjectList<SinglePage> objectList = singlePageDelegate.findAllWithPaging(user.getCompany(), 
				itemsPerPage, page, order);
		singlePages = objectList.getList();
				
		return Action.SUCCESS;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getPage() {
		return page;
	} 

	public void setPage(int page) {
		this.page = page;
	}
	
	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems() {
		totalItems = singlePageDelegate.findAll(user.getCompany()).getTotal();
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public List<SinglePage> getSinglePages() {
		return singlePages;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}
}
