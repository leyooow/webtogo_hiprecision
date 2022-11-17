package com.ivant.cms.db;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.entity.list.ObjectList;

public class EventGroupDAO extends AbstractBaseDAO<EventGroup> {

	public ObjectList<EventGroup> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));

		
//		System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//		System.out.println("company " + company);
//		System.out.println("resultPerPage " + resultPerPage);
//		System.out.println("pageNumber " + pageNumber);
//		System.out.println("orders--- " + orders.toString());
//		System.out.println("conj ---  " + conj.toString());
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, conj);
	}
	
	
	public ObjectList<EventGroup> findAll(Company company) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(-1, -1, null, null, null, 
				null, conj);
	}
	
	public EventGroup find(Company company, String name) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("name", name));
	
		List<EventGroup> eventGroupList = findAllByCriterion(0, 0, null, null, null, null, conj).getList();
		if(eventGroupList.size() > 0) {
			return eventGroupList.get(0);
		}
		
		return null;
	}
	

	
	
//	public List<Event> findAll(EventGroup eventGroup, Company company, Order...orders)  {
//		
//		Conjunction conj = Restrictions.conjunction();
//		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
//		conj.add(Restrictions.eq("company", company));	
//		conj.add(Restrictions.eq("eventGroup", eventGroup));
//		
//		return findAllByCriterion(0, 0, null, null, null, orders, conj).getList();
//	}
	
	
	
	
}

