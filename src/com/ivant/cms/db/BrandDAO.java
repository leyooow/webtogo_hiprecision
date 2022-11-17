package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class BrandDAO extends AbstractBaseDAO<Brand> {
	
	public Brand find(Company company, Group group, String name, boolean isKeyword) {
		Brand brand = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		if(isKeyword)
		{
			junc.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		else
		{
			junc.add(Restrictions.eq("name", name));	
		}
		
		try {
			brand = findAllByCriterion(null, null, null, null, junc).getList().get(0);
		}
		catch(Exception e) {
		}
		
		return brand;
	}
	
	public ObjectList<Brand> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<Brand> findAll(Company company, boolean showAll, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<Brand> findAllEnabled(Company company, boolean showAll, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<Brand> findAllEnabled(Company company, int resultPerPage, int  pageNumber, boolean showAll, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
	
	public ObjectList<Brand> findAll(Company company, Group group, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	
	public ObjectList<Brand> findAllFeatured(Company company, boolean showAll, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("featured", true));
		junc.add(Restrictions.eq("disabled", Boolean.FALSE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, orders, junc);
	}
	 
	public ObjectList<Brand> findAllWithPaging(Company company, Group group, int resultPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("group", group));
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
}
