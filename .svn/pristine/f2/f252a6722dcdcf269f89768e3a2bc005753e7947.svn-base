package com.ivant.cms.action.admin;

import java.util.List;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class ListMultiPageAction implements Action, UserAware, PagingAware {

	private Logger logger = Logger.getLogger(getClass());
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	
	private User user;
	private Company company;
	private CompanySettings companySettings;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<MultiPage> multiPages;
	
	public String execute() throws Exception {
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return Action.ERROR;
		}
		
		company = user.getCompany();
		companySettings = company.getCompanySettings();
		
		String[] order = {"name"};
		ObjectList<MultiPage> objectList = multiPageDelegate.findAllWithPaging(user.getCompany(), itemsPerPage, page, order);
		multiPages = objectList.getList();
		
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
		totalItems = multiPageDelegate.findAll(user.getCompany()).getTotal();
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public List<MultiPage> getMultiPages() {
		return multiPages;
	}

	public Company getCompany() {
		return company;
	}

	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}
}
