package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.entity.Member;
import com.ivant.cms.db.EventDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.entity.list.ObjectList;

public class EventDelegate extends AbstractBaseDelegate<Event, EventDAO> {
	
	private static EventDelegate instance = new EventDelegate();
	
	public static EventDelegate getInstance() {
		return EventDelegate.instance;
	}
	
	public EventDelegate() {
		super(new EventDAO());
	}
	
	public ObjectList<Event> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, orders);
	}
	
	public Event find(Company company, String title) {
		return dao.find(company, title);
	}
	
	public List<Event> findAll(Company company) {
		return dao.findAll(company).getList();
	}
	
	public List<Event> findAll(Company company, long startDayTS, long endDayTS, Order...orders) {
		return dao.findAll(company, startDayTS, endDayTS, orders);
	}
	
	public List<Event> findAll(EventGroup eventGroup, Company company, Order...orders)  {
		return dao.findAll(eventGroup, company, orders);
	}

}
