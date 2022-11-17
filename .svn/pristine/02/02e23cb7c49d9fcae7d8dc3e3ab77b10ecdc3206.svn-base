package com.ivant.cms.component;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;

import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.utils.EventUtil;

public class EventCalendarComponent implements Component {

	private static final Logger logger = Logger.getLogger(EventCalendarComponent.class);
	private static final EventDelegate eventDelegate = EventDelegate.getInstance();
	private Company company;
	
	public EventCalendarComponent(Company company) {
		this.company = company;
	}
	
	public String getName() {
		return "eventcalendar";
	}
	
	public void prepareComponent(HttpServletRequest request) {
		DateTime currentDate = null;
		String monthParam = request.getParameter("month");
		String yearParam = request.getParameter("year");
		
		if( monthParam != null && yearParam != null) {
			try {
				currentDate = new DateTime(Integer.parseInt(yearParam), Integer.parseInt(monthParam), 
						1, 0 ,0 ,0 ,0);
			}
			catch(Exception e) {
				currentDate = new DateTime();
			}
		}
		else {
			currentDate = new DateTime();
		}
		
		DateTime startDate = new DateTime(currentDate.getYear(), currentDate.getMonthOfYear(), 
				currentDate.dayOfMonth().getMinimumValue(), 0, 0 ,0 ,0);
		
		DateTime endDate = new DateTime(currentDate.getYear(), currentDate.getMonthOfYear(), 
				currentDate.dayOfMonth().getMaximumValue(), 0, 0 ,0 ,0);
		
		List<Event> events = eventDelegate.findAll(company, startDate.getMillis(), endDate.getMillis(),
				Order.asc("eventDate"), Order.asc("eventCategory"));
		
		EventUtil eventUtil = new EventUtil(currentDate, events);
		request.setAttribute("eventUtil", eventUtil);
		
		try {
			long eventId = Long.parseLong(request.getParameter("eventId"));
			Event event = eventDelegate.find(eventId);
			if(event.getCompany().equals(company)) {
				request.setAttribute("selectedEvent", event);
			}
		}
		catch(Exception e) {
			logger.debug("invalid event id");
		}
	}
}
