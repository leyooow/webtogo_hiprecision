package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.list.ObjectList;

public class MemberTypeDAO
	extends AbstractBaseDAO<MemberType>{

	public ObjectList<MemberType> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Order...orders) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, orders, conj);
	}
	
	public List<MemberType> findAll(Company company) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, conj).getList();
	}	
}
