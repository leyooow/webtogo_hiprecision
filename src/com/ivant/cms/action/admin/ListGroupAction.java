package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class ListGroupAction implements Action, Preparable, ServletRequestAware, 
			UserAware, PagingAware {
	
	private Logger logger = Logger.getLogger(getClass());
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	
	private User user;
	private Company company;
	private CompanySettings companySettings;
	private ServletRequest request;
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	private List<Group> groups;
	
	public String execute() throws Exception {
		if(user.getCompany() == null) {
			return Action.ERROR;
		}
		
		company = user.getCompany();
		companySettings = company.getCompanySettings();
	
	/*	if(user.getUserType() != UserType.SUPER_USER    &&  user.getUserType() != UserType.WEBTOGO_ADMINISTRATOR) {
			return Action.ERROR;
		} 
	*/					
	//END OF TESTING	
		//System.out.println("1  ItemsPerPage,  TotalItems :  " + user.getItemsPerPage() + "  " + totalItems);
		Long[] forbiddenGroupIds = null;
		if(user.getForbiddenGroups() != null){
			if(user.getForbiddenGroups().size() > 0){
				List<Group> forbiddenGroups = user.getForbiddenGroups();
				forbiddenGroupIds = new Long[forbiddenGroups.size()];
				for(int i=0; i<forbiddenGroups.size(); i++)
					forbiddenGroupIds[i] = forbiddenGroups.get(i).getId();
			}
				
		}
//		groups = groupDelegate.findAllWithPaging(user.getCompany(), user.getItemsPerPage(), page).getList();
		groups = groupDelegate.findAllWithPagingAndUserRights(user.getCompany(), forbiddenGroupIds, user.getItemsPerPage(), page).getList();
		//System.out.println("2  ItemsPerPage,  TotalItems :  " + itemsPerPage + "  " + totalItems);
		//System.out.println("4 size: " + groups.size());
		
		return Action.SUCCESS;
	}
	
	public void prepare() throws Exception {
		try {
			
		}
		catch(Exception e) {
		}
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
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
		totalItems = groupDelegate.findAll(user.getCompany()).getTotal();
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
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
