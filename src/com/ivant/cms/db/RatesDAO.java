package com.ivant.cms.db;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Rates;

import com.ivant.cms.entity.list.ObjectList;

public class RatesDAO extends AbstractBaseDAO<Rates>{
	public RatesDAO() {
		super();
	}
	
	public ObjectList<Rates> getParentFund(Company company){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.isNull("parent"));
		conj.add(Restrictions.eq("isValid", true));
		conj.add(Restrictions.eq("company", company));
		return this.findAllByCriterion(null, null, null, null, conj);
	}
	
	public Rates getLatestByParent(Rates parent, Order...orders){
		Rates rates = null;
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("parent", parent));
		conj.add(Restrictions.eq("isValid", true));
		try{
		rates = findAllByCriterion(null, null, null, orders, null, null, conj).getList().get(0);
		}
		catch(Exception e) {}
	 
		return rates;
	}
	
	public Rates getLatestRates(Rates parent, String name){
		Rates rates = null;
		
		Junction conj = Restrictions.conjunction();		
		conj.add(Restrictions.eq("isValid", true));
		conj.add(Restrictions.eq("parent", parent));
		conj.add(Restrictions.eq("name", name));
		
		Order[] orders = {Order.desc("createdOn")};
		
		try{
			rates = findAllByCriterion(null, null, null, orders, null, null, conj).getList().get(0);
		}
		catch(Exception e) {}
	 
		return rates;
	}
	
	public Rates find(Long statID){
		Rates rates = null;
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("id", statID));
		conj.add(Restrictions.eq("isValid", true));
		try{
		rates = findAllByCriterion(null, null, null, null, null, null, conj).getList().get(0);
		}
		catch(Exception e) {}
	 
		return rates;
	}
	
	public Rates find(Company company, String name){
		Rates rates = null;
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("name", name));
		conj.add(Restrictions.eq("isValid", true));
		try{
		rates = findAllByCriterion(null, null, null, null, null, null, conj).getList().get(0);
		}
		catch(Exception e) {}

		return rates;
		}

	public Rates getParentById(Long statID){
		Rates rates = null;
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("id", statID));
		conj.add(Restrictions.eq("isValid", true));
		try{
		rates = findAllByCriterion(null, null, null, null, null, null, conj).getList().get(0);
		}
		catch(Exception e) {}
	 
	return rates;
	}
	
	public Rates getRateById(Long statID){
		Rates rates = null;
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("id", statID));
		conj.add(Restrictions.eq("isValid", true));
		try{
		rates = findAllByCriterion(null, null, null, null, null, null, conj).getList().get(0);
		}
		catch(Exception e) {}
	 
	return rates;
	}
	
	public List<Rates> getRatesByName(String name, Rates parent){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("name", name));
		conj.add(Restrictions.eq("parent", parent));
		conj.add(Restrictions.eq("isValid", true));
		
		Order[] orders = {Order.desc("createdOn")};
			
		return findAllByCriterion(null, null, null, orders, null, null, conj).getList();
	}
	
	public ObjectList<Rates> getRatesByParent(Rates parent){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("parent", parent));
		conj.add(Restrictions.eq("isValid", true));
		return this.findAllByCriterion(null, null, null, null, conj);
	}
	
	public ObjectList<Rates> getRatesByParent(Rates parent, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("parent", parent));
		conj.add(Restrictions.eq("isValid", true));
		return this.findAllByCriterion(null, null, null, orders, conj);
	}
	
	public ObjectList<Rates> getMultipleParentRates(List<Rates> parentList, Order...orders){
		Junction conj = Restrictions.conjunction();

		conj.add(Restrictions.eq("isValid", true));
		if(parentList != null && !parentList.isEmpty()){
			conj.add(Restrictions.in("parent", parentList));
		}
		
		return this.findAllByCriterion(null, null, null, orders, conj);
	}
	
	public ObjectList<Rates> getRatesByParentWithPaging(Rates parent, int resultPerPage,int pageNumber, Order...orders){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("parent", parent));
		conj.add(Restrictions.eq("isValid", true));
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, conj);
	}
	
	public ObjectList<Rates> getRatesByFund(Long fundID){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("parent", null));
		conj.add(Restrictions.eq("isValid", true));
		return this.findAllByCriterion(null, null, null, null, conj);
	}
	
	public ObjectList<Rates> getAll(Company company){
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", true));
		conj.add(Restrictions.eq("company", company));
		return this.findAllByCriterion(null, null, null, null, conj);
	}
	
	public ObjectList<Rates> getRatesByCompany(Company company){
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM rates WHERE company_id = :currentCompanyID and valid is true ORDER BY created_on ASC");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (ObjectList<Rates>) q.uniqueResult();
	}
	
	public ObjectList<Rates> getLatestDate(Company company){
		StringBuilder query = new StringBuilder();
		//Junction conj = Restrictions.conjunction();
		//conj.add(Restrictions.eq("isValid", true));
		//conj.add(Restrictions.eq("company", company));
		//conj.add(Restrictions.isNotNull("parent"));
		
		//return this.findAllByCriterionProjection(Projections.countDistinct("date"), conj).size();
				
		query.append("SELECT DISTINCT date FROM rates WHERE company_id = :currentCompanyID and valid is true and parent in (1,2,3) ORDER BY date DESC");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (ObjectList<Rates>) q.uniqueResult();
	}
	
	public ObjectList<Rates> getTrustProductsRates(Company company){
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM rates WHERE company_id = :currentCompanyID and parent in (1,2,3) and valid = true GROUP BY created_on, parent ORDER BY created_on, parent DESC");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (ObjectList<Rates>) q.uniqueResult();
	}
	
	public ObjectList<Rates> getBuyAndSellRates(Company company){
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM rates WHERE company_id = :currentCompanyID and parent in (4,5) and valid = true GROUP BY created_on, parent ORDER BY created_on, parent DESC");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (ObjectList<Rates>) q;
	}
}
