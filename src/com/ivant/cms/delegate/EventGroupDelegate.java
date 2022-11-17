package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.EventGroupDAO;
import com.ivant.cms.db.EventDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.entity.list.ObjectList;

public class EventGroupDelegate extends AbstractBaseDelegate<EventGroup, EventGroupDAO> {
	
	private static EventGroupDelegate instance = new EventGroupDelegate();
	
	public static EventGroupDelegate getInstance() {
		return EventGroupDelegate.instance;
	}
	
	public EventGroupDelegate() {
		super(new EventGroupDAO());
	}
	
	public ObjectList<EventGroup> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, orders);
	}
	
	public EventGroup find(Company company, String title) {
		return dao.find(company, title);
	}
	
	public List<EventGroup> findAll(Company company) {
		return dao.findAll(company).getList();
	}
	

}
