package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.list.ObjectList;

public class FormPageDAO extends AbstractBaseDAO<FormPage>{

	public FormPageDAO() {
		super();
	}
	
	public FormPage find(Company company, String name) {
		FormPage singlePage = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("name", name));
		
		List<FormPage> singlePageList = findAllByCriterion(null, null, null, null, null,	null, junc).getList();
		if(singlePageList.size() > 0) 		{
			singlePage = singlePageList.get(0);
		}
		return singlePage;
	}
	
	public FormPage findJsp(Company company, String jsp) {
		FormPage singlePage = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("jsp", jsp));
		
		List<FormPage> singlePageList = findAllByCriterion(null, null, null, null, null,	null, junc).getList();
		if(singlePageList.size() > 0) 		{
			singlePage = singlePageList.get(0);
		}
		return singlePage;
	}
	
	public ObjectList<FormPage> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
	
//	public ObjectList<FormPage> findAllPublished(Company company, boolean published) {
//		Junction junc = Restrictions.conjunction();
//		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
//		junc.add(Restrictions.eq("company", company));
//		junc.add(Restrictions.eq("published", published));		
//		return findAllByCriterion(null, null, null, null, null,	null, junc);
//	}
	
	public ObjectList<FormPage> findAllWithPaging(Company company, int resultPerPage, int pageNumber, String[] orderBy) {	
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
}
