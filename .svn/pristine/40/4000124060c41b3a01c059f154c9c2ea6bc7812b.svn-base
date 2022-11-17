package com.ivant.cms.action.admin;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.EventGroupDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.ColorCode;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.PagingAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;

public class ListEventAction extends ActionSupport implements CompanyAware, PagingAware{

	private static final long serialVersionUID = -4302674593835913149L;
	private static final Logger logger = Logger.getLogger(ListEventAction.class);
	private static final EventDelegate eventDelegate = EventDelegate.getInstance();
	private static final EventGroupDelegate eventGroupDelegate = EventGroupDelegate.getInstance();	
	
	private Company company;
	private List<Event> events;
	private List<EventGroup> eventGroups;
	private List<ColorCode> colorCodes;
	
	private int page;
	private int totalItems;
	private int itemsPerPage;
	
	@Override
	public String execute() throws Exception {
		if(!company.getCompanySettings().getHasEventCalendar()) {
			return ERROR;
		}
		
		events = eventDelegate.findAllWithPaging(company, itemsPerPage, page, Order.desc("eventDate")).getList();
		eventGroups = eventGroupDelegate.findAll(company);
		setColorCodes(Arrays.asList(ColorCode.values()));		
		return SUCCESS;
	}
	
	
	public List<EventGroup> getEventGroups() {
		return eventGroups;
	}


	public void setEventGroups(List<EventGroup> eventGroups) {
		this.eventGroups = eventGroups;
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
	
	public void setTotalItems() {
		this.totalItems = eventDelegate.findAllWithPaging(company, itemsPerPage, page).getTotal();
	}
	
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public List<Event> getEvents() {
		return events;
	}


	public void setColorCodes(List<ColorCode> colorCodes) {
		this.colorCodes = colorCodes;
	}


	public List<ColorCode> getColorCodes() {
		return colorCodes;
	}
}
