package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Activity;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;

public class ActivityDAO extends AbstractBaseDAO<Activity> {

	public ActivityDAO() {
		super();
	}
	
	public ObjectList<Activity> findAllWithPaging(Company company,int resultPerPage, int pageNumber, String[] orderBy) {
		Order[] orders = null;
		try {		
			if(orderBy.length > 0) {
				orders = new Order[orderBy.length];
				for(int i = 0; i < orderBy.length; i++) {
					orders[i] = Order.desc(orderBy[i]);
				}
			}
		}
		
		catch(Exception e) {}
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, junc);
	}
	
	public ObjectList<Activity> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
}

