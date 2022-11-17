package com.ivant.cms.db;

import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.list.ObjectList;

public class MultiPageDAO extends AbstractBaseDAO<MultiPage> {

	public MultiPageDAO() {
		super();
	}
	
	public MultiPage find(Company company, String name) {
		MultiPage multiPage = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("name", name));	
	
		try {
			multiPage = findAllByCriterion(null, null, null, null, null, null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return multiPage;
	}
	
	public MultiPage findJsp(Company company, String jsp) {
		MultiPage multiPage = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("jsp", jsp));	
	
		try {
			multiPage = findAllByCriterion(null, null, null, null, null, null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return multiPage;
	}

	public ObjectList<MultiPage> findAll(Company company) {
		//Order[] orders = null;
		//orders[0]=Order.desc("id");
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
				
		//return findAllByCriterion(null, null, null,	orders, junc);
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
	public ObjectList<MultiPage> findAllFeatured(Company company) {		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("featured", Boolean.TRUE));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<MultiPage> findAllWithPaging(Company company, int resultPerPage, int pageNumber, String[] orderBy) {	
		Order[] orders = null;
		try {
			orders = new Order[orderBy.length];
			for(int i = 0; i < orderBy.length; i++) {
				orders[i] = Order.asc(orderBy[i]);
			}
		}
		catch(Exception e) {}
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, junc);
	}	
	
	public ObjectList<MultiPage> findAllParentMultiPages (Company company)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.ne("parentMultiPage", null));
		return findAllByCriterion(null, null, null, null, junc);
		
		
	}
	
	public ObjectList<MultiPage> findPageByKeyWord(String key, Company company)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		Disjunction disjunc = Restrictions.disjunction();
		disjunc.add(Restrictions.like("description", key, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.like("name", key, MatchMode.ANYWHERE));
		disjunc.add(Restrictions.like("title", key, MatchMode.ANYWHERE));
		
		junc.add(disjunc);
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	
}
