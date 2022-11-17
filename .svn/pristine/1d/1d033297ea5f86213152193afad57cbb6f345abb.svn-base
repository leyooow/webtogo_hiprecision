package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.list.ObjectList;

public class GroupDAO extends AbstractBaseDAO<Group>{

	public GroupDAO() {
		super();
	}
	
	public Group find(Company company, String name, boolean isKeyword) {
		Group group = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		if(isKeyword)
		{
			junc.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		else
		{
			junc.add(Restrictions.eq("name", name));	
		}
		
		try {
			group = findAllByCriterion(null, null, null, null, null, null, junc).getList().get(0);
		}
		catch(Exception e) {}
		 
		return group;
	}
	
	public Group find(Company company, Long gid) {
		Group group = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("id", gid));
		
		try {
			group = findAllByCriterion(null, null, null, null, null, null, junc).getList().get(0);
		}
		catch(Exception e) {}
		 
		return group;
	}
	
	public ObjectList<Group> findAll(Company company, String[] orderBy) {
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
		
		return findAllByCriterion(null, null, null, orders, junc);
	}

	public ObjectList<Group> findAllFeatured(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("featured", Boolean.TRUE));
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public ObjectList<Group> findAllWithPaging(Company company, int resultPerPage, int pageNumber, String[] orderBy) {		
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
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}	
	
	public ObjectList<Group> findAllWithPagingAndUserRights(Company company, Long[] forbiddenGroupIds, int resultPerPage, int pageNumber, String[] orderBy) {		
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
		
		if(forbiddenGroupIds != null){
			junc.add(Restrictions.not(Restrictions.in("id", forbiddenGroupIds)));
		}
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, junc);
	}
} 

