package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.CompanyStatusEnum;

public class CompanyDAO extends AbstractBaseDAO<Company> {

	public CompanyDAO() {
		super();
	}
	
	public ObjectList<Company> findAll(Boolean isValid)
	{
		final Junction junc = Restrictions.conjunction();
		
		if(isValid == null)
		{
			isValid = Boolean.TRUE;
		}
		
		if(isValid)
		{
			junc.add(Restrictions.eq("isValid", isValid));
		}
		
		return findAllByCriterion(null, null, null, null, junc);
	}
	
	public Company find(String name) {
		Company company = null;
		
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("name", name));
		
		try {
			company = findAllByCriterion(null, null, null, null, conj).getList().get(0);
		}
		catch(Exception e) {}
		
		return company;
	}
	
	public Company findServerName(String serverName) {
		Company company = null;
		
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.like("serverName", serverName, MatchMode.START));
			
		try {
			company = findAllByCriterion(null, null, null, null, conj).getList().get(0);
		}
		catch(Exception e) {}
		
		return company;
	}
	
	public ObjectList<Company> findAll(String[] orderBy) {
		Order[] orders = null;
		
		if(orderBy.length > 0) {
			orders = new Order[orderBy.length];
			for(int i = 0; i < orderBy.length; i++) {
				orders[i] = Order.asc(orderBy[i]);
			}
		}
		
		return findAllByCriterion(null, null, null, orders, Restrictions.eq("isValid", Boolean.TRUE));
	}
		
	public ObjectList<Company> findAllWithPaging(int resultPerPage, int pageNumber, String[] orderBy) {	
		Order[] orders = null;
		
		try {
			orders = new Order[orderBy.length];
			for(int i = 0; i < orderBy.length; i++) {
				orders[i] = Order.asc(orderBy[i]);
			}
		}
		catch(Exception e) {}
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, 
				orders, 
				Restrictions.eq("isValid", Boolean.TRUE));
	}
	public ObjectList<Company> findByStatus(CompanyStatusEnum companyStatus) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("companySettings.companyStatus", companyStatus));
		final String aliases[] = {"companySettings"};
		final int joinType[] = {CriteriaSpecification.LEFT_JOIN};

		return findAllByCriterion(aliases, aliases, joinType, null, junc);
	}
	
	public ObjectList<Company> findByStatus(CompanyStatusEnum companyStatus, String[] orderBy) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("companySettings.companyStatus", companyStatus));
		final String aliases[] = {"companySettings"};
		final int joinType[] = {CriteriaSpecification.LEFT_JOIN};
		
		Order[] orders = null;
		
		if(orderBy.length > 0) {
			orders = new Order[orderBy.length];
			for(int i = 0; i < orderBy.length; i++) {
				orders[i] = Order.asc(orderBy[i]);
			}
		}	

		return findAllByCriterion(aliases, aliases, joinType, orders, junc);
	}
	
	public List<Company> filterByStatus(CompanyStatusEnum[] companyStatus) {
		Junction junc = Restrictions.conjunction();
		Junction junc2 = Restrictions.disjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		for(CompanyStatusEnum status : companyStatus) 
			junc2.add(Restrictions.eq("companySettings.companyStatus", status));
	
		final String aliases[] = {"companySettings"};
		final int joinType[] = {CriteriaSpecification.LEFT_JOIN};
		junc.add(junc2);

		return findAllByCriterion(aliases, aliases, joinType, null, junc).getList();
	}

}
