package com.ivant.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import com.ivant.cms.entity.Event;

public class EventUtil {

	private Logger logger = Logger.getLogger(EventUtil.class);
	
	private DateTime date;
	private String month;
	private String year;
	private DateTime nextMonthDate;
	private DateTime prevMonthDate;
	private List<Day> days = new ArrayList<Day>();
	private List<Event> events;
		
	public EventUtil(DateTime date, List<Event> events) {
		this.events = events;
		this.date = date;
		
		month = date.monthOfYear().getAsText();
		year = String.valueOf(date.getYear()); 
		
		nextMonthDate = date.plusMonths(1);
		prevMonthDate = date.minusMonths(1);
		
		int minDay = date.dayOfMonth().getMinimumValue();
		int maxDay = date.dayOfMonth().getMaximumValue();
		
		DateTime startDayOfMonthDate = new DateTime(date.getYear(), date.getMonthOfYear(), 1, 0, 0 ,0, 0);
		int startDayOfWeek = startDayOfMonthDate.dayOfWeek().get();

		// prepare the events; create a map where the key is the day and the value is the event item
		Map<Integer, Map<String, Event>> dayEventsMap = new HashMap<Integer, Map<String,Event>>();
		if(events != null && events.size() > 0) {
			for(Event e : events) {
				 
				DateTime dt = new DateTime(e.getEventDate().getTime());
				Map<String, Event> eventItems = dayEventsMap.get(dt.getDayOfMonth());
				 
				if(eventItems == null) {
					eventItems = new HashMap<String, Event>();
				} 
				
				eventItems.put(e.getTitle(), e);
								
				dayEventsMap.put(dt.getDayOfMonth(), eventItems);
				
				if(e.getEventEnd().compareTo(e.getEventDate())==1) {
					DateTime dtEnd = new DateTime(e.getEventEnd().getTime());
					DateTime dtStart = new DateTime(e.getEventDate().getTime());
					Days d = Days.daysBetween(dtStart, dtEnd);
					
					for(int i=1; i<=d.getDays(); i++) {
						Map<String, Event> currentEventItems = dayEventsMap.get(dt.getDayOfMonth() + i);
																			
						if(currentEventItems == null) {
							Map<String, Event> newEventItems = new HashMap<String, Event>();
							newEventItems.put(e.getTitle(), e);
							dayEventsMap.put(dt.getDayOfMonth() + i, newEventItems);
						}  
						else {
							Map<String, Event> newEventItems = new HashMap<String, Event>();
							Iterator<Map.Entry<String, Event>> iterator = eventItems.entrySet().iterator();
							Iterator<Map.Entry<String, Event>> iteratorCurrent = currentEventItems.entrySet().iterator();
							
							while (iterator.hasNext()) {
							   Map.Entry<String, Event> entry = iterator.next();
							   newEventItems.put(entry.getKey(), entry.getValue());
							} 
							
							while (iteratorCurrent.hasNext()) {
							   Map.Entry<String, Event> entry = iteratorCurrent.next();
							   newEventItems.put(entry.getKey(), entry.getValue());
							} 
							 
							dayEventsMap.put(dt.getDayOfMonth() + 1, newEventItems);
						}	
					}
				}
			}
		}
				
		for(int i=0; i<startDayOfWeek; i++) {
			Day d = new Day(0, 0, "", null);
			days.add(d);
			
		}
		
		for(int i=minDay; i<= maxDay; i++) {
			DateTime dt = new DateTime(date.getYear(), date.getMonthOfYear(), i, 0 ,0 ,0 ,0);
			Day d = new Day(i,  dt.dayOfWeek().get(), dt.dayOfWeek().getAsText(), dayEventsMap.get(i));
			
			if(i == this.date.getDayOfMonth()) {
				d.setToday(true);
			}
			
			days.add(d);
		}
	}
	
	public DateTime getNextMonthDate() {
		return nextMonthDate;
	}
	
	public DateTime getPrevMonthDate() {
		return prevMonthDate;
	}
	
	public DateTime getDate() {
		return date;
	}
	
	public String getMonth() {
		return month;
	}
	
	public String getYear() {
		return year;
	}
	
	public List<Day> getDays() {
		return days;
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MONTH: ");
		sb.append(month);
		sb.append("\n");
		sb.append("DAYS: [");
		
		for(Day d : days) {
			sb.append(d.toString());
			sb.append(", ");
		}
		
		sb.append("]");
		return sb.toString();
	}
	 
	protected class Day {
		
		private int value;
		private int dayOfWeek;
		private String dayOfWeekString;
		private Map<String, Event> events;
		private boolean today; 
		
		public Day(int value, int dayOfWeek, String dayOfWeekString, Map<String, Event> events) {
			this.value = value;
			this.dayOfWeek = dayOfWeek;
			this.events = events;
			this.dayOfWeekString = dayOfWeekString;
			this.today = false;
		}
		
		public int getValue() {
			return value;
		}
		
		public int getDayOfWeek() {
			return dayOfWeek;
		}
		
		public Map<String, Event> getEvents() {
			return events;
		}		
		
		public String getDayOfWeekString() {
			return dayOfWeekString;
		}
		
		public boolean isToday() {
			return today;
		}

		public void setToday(boolean today) {
			this.today = today;
		}

		@Override
		public String toString() {
			return "(" +  value + ", " + dayOfWeek + ")";
		}
	}
}
