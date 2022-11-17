package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ComponentCategory;
import com.ivant.cms.entity.list.ObjectList;

public class ComponentCategoryDAO 
	extends AbstractBaseDAO<ComponentCategory>{
	
	public ComponentCategoryDAO() {
		super();
	}

	public ObjectList<ComponentCategory> findAll(Company company)
	{
		Order[] orders =
		{ Order.asc("name") };
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ComponentCategory findByKeyword(Company company, String keyword)
	{
		final Junction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.like("name", keyword, MatchMode.ANYWHERE));
		
		return findAllByCriterion(null, null, null, null, conj).uniqueResult(); 
	}
	
	public ObjectList<ComponentCategory> findAll(Company company, Order...orders)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, orders, junc);
	}
}
