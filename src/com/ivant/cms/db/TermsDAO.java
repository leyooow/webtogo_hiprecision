package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Terms;
import com.ivant.cms.entity.list.ObjectList;

public class TermsDAO extends AbstractBaseDAO<Terms> {
	
	public TermsDAO() {
		super();
	}
	
	public Terms findByCompany(Company company) {
		if(company == null) {
			throw new IllegalArgumentException("company is null");
		}
		
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		
		ObjectList<Terms> companyList = findAllByCriterion(null, null, null, null, conj);
		if(companyList.getSize() > 0) {
			return companyList.getList().get(0);
		}
		
		return null;
	}

}
