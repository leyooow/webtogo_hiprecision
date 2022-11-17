package com.ivant.cms.action.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.ivant.cms.delegate.EventGroupDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.interfaces.CompanyAware;
import com.opensymphony.xwork2.ActionSupport;

public class EventGroupAction extends ActionSupport implements CompanyAware {

	private static final long serialVersionUID = 8778749603477417499L;
	private static final Logger logger = Logger.getLogger(EventGroupAction.class);
	private static final EventGroupDelegate eventGroupDelegate = EventGroupDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	
	private EventGroup eventGroup;
	private Company company;
	private long eventGroupId;
	private String eventGroupDate;
	private String eventGroupEnd;
	private String title;
	private String detail;
	private String eventGroupCategory;
	private String name;
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getDetail() {
		return detail;
	}

	public String save() throws Exception {
		if(company != null) {
			Date date = null;
			Date dateEnd = null;
			
			try {
				String[] dateString = eventGroupDate.split("-");
				DateTime dt = new DateTime(Integer.parseInt(dateString[2]), 
						Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), 0, 0 ,0 ,0);
				date = new Date(dt.getMillis());
				
				if(eventGroupEnd != null) {
					String[] eventGroupEndString = eventGroupEnd.split("-");
					DateTime dtEnd = new DateTime(Integer.parseInt(eventGroupEndString[2]), 
							Integer.parseInt(eventGroupEndString[0]), Integer.parseInt(eventGroupEndString[1]), 0, 0 ,0 ,0);
					dateEnd = new Date(dtEnd.getMillis());
				}
				else {
					dateEnd = date; 
				}
			}
			catch(Exception e) {
				logger.fatal("unable to properly process eventGroup date!!!");
			} 
			 
			if( eventGroupId == 0 && eventGroupDelegate.find(company, title) == null) {
				EventGroup eventGroup = new EventGroup();
				eventGroup.setCompany(company);
				//eventGroup.setEventGroupCategory(eventGroupCategory);
				
				eventGroup.setDescription(description);
				eventGroup.setName(name);
				
				if(eventGroupDelegate.insert(eventGroup) > 0) {
					
					lastUpdatedDelegate.saveLastUpdated(company);
					
					return SUCCESS;
				}
			}
			else {
				if(eventGroupId > 0) {
					eventGroup = eventGroupDelegate.find(eventGroupId);

						eventGroup.setName(name);
						eventGroup.setDescription(description);
						
						
						eventGroupDelegate.update(eventGroup);
						
						lastUpdatedDelegate.saveLastUpdated(company);
						
						return SUCCESS;
					}
				}
		}
		
		return ERROR;
	}
	
	public String delete() throws Exception {
		if(company != null && eventGroupId > 0) {
			EventGroup eventGroup = eventGroupDelegate.find(eventGroupId);
			if(eventGroup.getCompany().equals(company)) {
				eventGroupDelegate.delete(eventGroup);
				
				lastUpdatedDelegate.saveLastUpdated(company);
				
				return SUCCESS;
			}
		}
		
		return ERROR; 
	}
	
	public String edit() throws Exception {
		if(company != null && eventGroupId > 0) {
			eventGroup = eventGroupDelegate.find(eventGroupId);
			if(eventGroup != null && eventGroup.getCompany().equals(company)) {
				return SUCCESS;
			}
		}
		
		return ERROR;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setEventGroupDate(String eventGroupDate) {
		this.eventGroupDate = eventGroupDate;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public void setEventGroupId(long eventGroupId) {
		this.eventGroupId = eventGroupId;
	}
	
	public EventGroup getEventGroup() {
		return eventGroup;
	}
	
	public void setEventGroupEnd(String eventGroupEnd) {
		this.eventGroupEnd = eventGroupEnd;
	}
	
	public void setEventGroupCategory(String eventGroupCategory) {
		this.eventGroupCategory = eventGroupCategory;
	}
}
