package com.ivant.cms.action.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.EventGroupDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;


import com.opensymphony.xwork2.ActionSupport;

public class ListEventGroupAction extends ActionSupport implements CompanyAware, PagingAware, UserAware{

	private static final long serialVersionUID = -4302674593835913149L;
	private static final Logger logger = Logger.getLogger(ListEventGroupAction.class);
	private static final EventGroupDelegate eventGroupDelegate = EventGroupDelegate.getInstance();
	
	private Company company;
	private List<EventGroup> eventGroups;
	


	private int page;
	private int totalItems;
	private int itemsPerPage;
	private User user;
	
	@Override
	public String execute() throws Exception {
//		if(!company.getCompanySettings().getHasEventGroupCalendar()) {
//			return ERROR;
//		}
//		System.out.println("company``` " + company);
//		System.out.println("itemsPerPage``` " + user.getItemsPerPage());
//		System.out.println("page``` " + page);
		eventGroups = eventGroupDelegate.findAllWithPaging(company, user.getItemsPerPage(), page).getList();
//		System.out.println("evenGroups.size " + eventGroups.size());
//		for (EventGroup eg : eventGroups) System.out.println("description--> " + eg.getDescription());
		
		return SUCCESS;
	}
	
	
	public void setCompany(Company company) {
		this.company = company;
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

	public void setEventGroups(List<EventGroup> eventGroups) {
		this.eventGroups = eventGroups;
	}
	
//	public void setTotalItems(int totalItems) {
//		this.totalItems = totalItems;
//	}
	
	public void setTotalItems() {
//		System.out.println("company--- " + company);
//		System.out.println("itemsPerPage--- " + user.getItemsPerPage());
//		System.out.println("page--- " + page);
		this.totalItems = eventGroupDelegate.findAllWithPaging(company, user.getItemsPerPage(), page).getTotal();
//		System.out.println("totalItems--- " + this.getTotalItems());
	}
	
	public int getItemsPerPage() {
		return user.getItemsPerPage();
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public List<EventGroup> getEventGroups() {
		return eventGroups;
	}
	

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}


	public void setUser (User user) {
		this.user = user;
	}
	
}
