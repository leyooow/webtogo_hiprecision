package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Component;
import com.ivant.cms.entity.list.ObjectList;

public class ComponentDAO 
	extends AbstractBaseDAO<Component>{
	
	public ComponentDAO() {
		super();
	}

	public ObjectList<Component> findAll(int page, int maxResults, Order[] orders, Company company ) {
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return	findAllByCriterion(page, maxResults, null, null, null, orders, junc);
		
	}
	
	public ObjectList<Component> findAll(Company company)
	{
		final Junction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, conj); 
	}
	
	public Component findByKeyword(Company company, String keyword)
	{
		final Junction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
		
		return findAllByCriterion(null, null, null, null, conj).uniqueResult(); 
	}
	
}
