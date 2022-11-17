package com.ivant.cms.db;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.entity.list.ObjectList;

public class EventDAO extends AbstractBaseDAO<Event> {

	public ObjectList<Event> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, conj);
	}
	
	public ObjectList<Event> findAll(Company company) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(-1, -1, null, null, null, 
				null, conj);
	}

	public Event find(Company company, String title) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("title", title));
	
		List<Event> eventList = findAllByCriterion(0, 0, null, null, null, null, conj).getList();
		if(eventList.size() > 0) {
			return eventList.get(0);
		}
		
		return null;
	}
	
	public List<Event> findAll(Company company, long startDayTS, long endDayTS, Order...orders) {		
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));		
		conj.add(Restrictions.ge("eventDate", new Date(startDayTS)));	
		conj.add(Restrictions.le("eventDate", new Date(endDayTS)));	
		
		return findAllByCriterion(0, 0, null, null, null, orders, conj).getList();
	}
	
	
	public List<Event> findAll(EventGroup eventGroup, Company company, Order...orders)  {
		
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));	
		conj.add(Restrictions.eq("eventGroup", eventGroup));
		
		return findAllByCriterion(0, 0, null, null, null, orders, conj).getList();
	}
	
	
	
	
}
