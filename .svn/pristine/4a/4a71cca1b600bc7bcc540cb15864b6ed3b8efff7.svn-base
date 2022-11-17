package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Notification;
import com.ivant.cms.entity.list.ObjectList;

public class NotificationDAO extends AbstractBaseDAO<Notification>{

	public NotificationDAO(){
		super();
	}
	
	public Notification findByEmail(Company company, String email){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("email", email));//null
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult(); 
	}
	
	public ObjectList<Notification> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
	public ObjectList<Notification> findAllNotifs(Company company, Order...orders){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));	
		
		return this.findAllByCriterion(null, null, null, orders, junc);
	}
	
}
