package com.ivant.cms.action.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.joda.time.DateTime;

import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.EventGroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.entity.Member;
import com.ivant.cms.enums.ColorCode;
import com.ivant.cms.interfaces.CompanyAware;
import com.opensymphony.xwork2.ActionSupport;

public class EventAction extends ActionSupport implements CompanyAware,  ServletRequestAware   {

	private static final long serialVersionUID = 8778749603477417499L;
	private static final Logger logger = Logger.getLogger(EventAction.class);
	private static final EventDelegate eventDelegate = EventDelegate.getInstance();
	private static final EventGroupDelegate eventGroupDelegate = EventGroupDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	private HttpServletRequest request;	
	private Event event;
	private Company company;
	private long eventId;
	private String eventDate;
	private String eventEnd;
	private String title;
	private String detail;
	private String eventCategory;
	private List<ColorCode> colorCodes;	
	private String colorCode;
	private String eventGroup;
	private String fee;
	private List<EventGroup> eventGroups;
	private List<Member> pcoMember;
	
	public String save() throws Exception {
		//System.out.println("Event Action save()");
		
		if(company != null) {
			Date date = null;
			Date dateEnd = null;
			
			try {
				String[] dateTimeString = eventDate.split(" ");
				String[] dateString 	= dateTimeString[0].split("-");
				String[] timeString 	= dateTimeString[1].split(":");
				if (dateTimeString[2].equalsIgnoreCase("PM")){
					if (timeString[0].equals("12") == false){
						int timeConverted = Integer.parseInt(timeString[0]) + 12;
						timeString[0] = Integer.toString(timeConverted);
					}
				}else{
					if (timeString[0].equals("12")){
						timeString[0] = "0";
					}
					
				}
				DateTime dt = new DateTime(Integer.parseInt(dateString[2]), 
						Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), Integer.parseInt(timeString[0]), Integer.parseInt(timeString[1]) ,0 ,0);
				date = new Date(dt.getMillis());
				 
				if(eventEnd != null) {
					dateTimeString = eventEnd.split(" ");
					dateString 	= dateTimeString[0].split("-");
					timeString 	= dateTimeString[1].split(":");
					if (dateTimeString[2].equalsIgnoreCase("PM")){
						if (timeString[0].equals("12") == false){
							int timeConverted = Integer.parseInt(timeString[0]) + 12;
							timeString[0] = Integer.toString(timeConverted);
						}
					}else{
						if (timeString[0].equals("12")){
							timeString[0] = "0";
						}
						
					}
					dt = new DateTime(Integer.parseInt(dateString[2]), 
							Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), Integer.parseInt(timeString[0]), Integer.parseInt(timeString[1]) ,0 ,0);
					dateEnd = new Date(dt.getMillis());
				}
				else {
					dateEnd = date; 
				}
			}
			catch(Exception e) {
				logger.fatal("unable to properly process event date!!!");
			} 
			
			setColorCodes(Arrays.asList(ColorCode.values()));		
			detail = request.getParameter("detail");
			colorCode = request.getParameter("colorCode");
			
			setColorCodes(Arrays.asList(ColorCode.values()));	
			ColorCode event_colorCode = null;
			for (int i = 0; i< colorCodes.size(); i++){	
				
				if (colorCodes.get(i).getValue().toUpperCase().equals(colorCode)){
					event_colorCode = colorCodes.get(i);
					break;	
				}				
			}				
			
			if( eventId == 0 ) {
				Event event = new Event();
				event.setCompany(company);
				event.setEventCategory(eventCategory);
				event.setColorCode(event_colorCode);		
				event.setFee(fee);
				if(date != null) {
					event.setEventDate(date);
				}
				if(dateEnd != null) {
					event.setEventEnd(dateEnd);
				}
				
				event.setTitle(title);
				event.setDetail(detail);
				if (eventGroup.trim().length()!=0)
				event.setEventGroup(eventGroupDelegate.find(new Long(eventGroup)));
				
				if(eventDelegate.insert(event) > 0) {
					
					lastUpdatedDelegate.saveLastUpdated(company);
					
					return SUCCESS;
				}
			}
			else {
				if(eventId > 0) {
					event = eventDelegate.find(eventId);
					if(event != null && event.getId().equals(eventId)) {
						if(date != null) {
							event.setEventDate(date);
						}
						
						if(dateEnd != null) {
							event.setEventEnd(dateEnd);
						} 

						event.setTitle(title);
						event.setDetail(detail);
						event.setEventCategory(eventCategory);
						//System.out.println("eventGroup " + eventGroup);
						if (eventGroup.trim().length()!=0)
							event.setEventGroup(eventGroupDelegate.find(new Long(eventGroup)));
						
						event.setColorCode(event_colorCode);
						event.setFee(fee);
						
						eventDelegate.update(event);
						
						lastUpdatedDelegate.saveLastUpdated(company);
						
						return SUCCESS;
					}
				}
			}
		}
		
		return ERROR;
	}
	
	public String delete() throws Exception {
		if(company != null && eventId > 0) {
			Event event = eventDelegate.find(eventId);
			if(event.getCompany().equals(company)) {
				eventDelegate.delete(event);
				
				lastUpdatedDelegate.saveLastUpdated(company);
				
				return SUCCESS;
			}
		}
		
		return ERROR; 
	}
	
	public String edit() throws Exception {
		//System.out.println("Event Action edit()");
		if(company != null && eventId > 0) {
			event = eventDelegate.find(eventId);
			eventGroups = eventGroupDelegate.findAll(company);
			pcoMember = memberDelegate.findAllByName(company).getList();
			request.setAttribute("pcoMember", pcoMember);
			if(event != null && event.getCompany().equals(company)) {
				setColorCodes(Arrays.asList(ColorCode.values()));	
				ColorCode event_colorCode = null;
				for (int i = 0; i< colorCodes.size(); i++){	
					if (colorCodes.get(i).getValue().toString().equals(colorCode)){
						event_colorCode = colorCodes.get(i);
						break;	
					}				
				}				
				event.setColorCode(event_colorCode);		
				return SUCCESS;
			}
		}
		
		return ERROR;
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
	
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public void setEventEnd(String eventEnd) {
		this.eventEnd = eventEnd;
	}
	
	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getEventGroup() {
		return eventGroup;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public void setEventGroup(String eventGroup) {
		this.eventGroup = eventGroup;
	}

	public void setColorCodes(List<ColorCode> colorCodes) {
		this.colorCodes = colorCodes;
	}

	public List<ColorCode> getColorCodes() {
		return colorCodes;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;		
	}
}
