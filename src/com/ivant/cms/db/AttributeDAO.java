package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Attribute;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class AttributeDAO extends AbstractBaseDAO<Attribute> {
	
	public Attribute find(Company company, Group group, String name) {
		Attribute attribute = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		junc.add(Restrictions.eq("name", name));
		
		try {
			attribute = findAllByCriterion(null, null, null, null, junc).getList().get(0);
		}
		catch(Exception e) {
		}
		
		return attribute;
	}
	
	public ObjectList<Attribute> findAll(Company company, boolean showAll, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		//junc.add(Restrictions.eq("disabled", disabled));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<Attribute> findAll(Company company, Group group, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<Attribute> findAllFeatured(Company company, boolean showAll, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("featured", true));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	 
	public ObjectList<Attribute> findAllWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
}
