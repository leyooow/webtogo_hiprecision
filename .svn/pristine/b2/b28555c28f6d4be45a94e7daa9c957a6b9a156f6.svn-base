package com.ivant.cms.db;

import java.math.BigInteger;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Compare;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.list.ObjectList;

public class CompareDAO extends AbstractBaseDAO<Compare>{
	public CompareDAO() {
		super();
	}
	
	public ObjectList<Compare> findAll(Company company, Member member) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		
		return this.findAllByCriterion(-1, -1, null, null, null, null, junc);
	}
	
	public ObjectList<Compare> findAllCompareWithPaging(Company company, int resultsPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		return this.findAllByCriterion(pageNumber, resultsPerPage, null, null, null, orders, junc );
	}
	
	public ObjectList<Compare> findAllCompareByMemberWithPaging(Company company, Member member, int resultsPerPage, int pageNumber, Order...orders) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		return this.findAllByCriterion(pageNumber, resultsPerPage, null, null, null, orders, junc );
	}
	
	public Compare find(Company company, Member member, CategoryItem categoryItem) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member", member));
		junc.add(Restrictions.eq("categoryItem", categoryItem));
		ObjectList<Compare> result = this.findAllByCriterion(null, null, null, null, junc);
		if(result == null || result.getList().isEmpty()) {
			return null;
		}
		return result.getList().get(0);
	}
	
	public BigInteger findCompareCount(Company company) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM compare WHERE company_id = :currentCompanyID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		return (BigInteger) q.uniqueResult();
	}
	
	public BigInteger findCompareCountByMember(Company company, Member member) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM compare WHERE company_id = :currentCompanyID and member_id = :currentMemberID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", company.getId());
		q.setLong("currentMemberID", member.getId());
		return (BigInteger) q.uniqueResult();
	}
}
