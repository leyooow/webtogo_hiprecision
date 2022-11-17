package com.ivant.cms.db;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Receipts;
import com.ivant.cms.entity.list.ObjectList;

public class ReceiptImageDAO extends AbstractBaseDAO<Receipts> {
	
	public ReceiptImageDAO() {
		super();
	}
	
	public ObjectList<Receipts> findAll(Company company, Member member) {
		Conjunction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("member.id", member.getId()));
		
		return findAllByCriterion(-1, -1, null, null, null, 
				null, conj);
	}	
}
