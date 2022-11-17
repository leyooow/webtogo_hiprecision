package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Promo;
import com.ivant.cms.entity.list.ObjectList;

public class PromoDAO extends AbstractBaseDAO<Promo>
{

	public PromoDAO() {
		super();
	}
	
	
	public Promo findByName(Company company, String name) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("name", name));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult();
	}

	public ObjectList<Promo> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
}
