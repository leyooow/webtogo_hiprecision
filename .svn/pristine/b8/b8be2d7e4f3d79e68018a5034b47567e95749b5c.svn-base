package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.LastUpdated;
import com.ivant.cms.entity.list.ObjectList;

public class LastUpdatedDAO extends AbstractBaseDAO<LastUpdated> {
	
	public LastUpdatedDAO() {
		super();
	}
	
	public void insertLastUpdated(final Company company) {
		if(company == null) {
			throw new IllegalArgumentException("company is null");
		}
		
		LastUpdated lu = new LastUpdated();
		lu.setCompany(company);
		insert(lu);
	}
	
	public void updateLastUpdated(final Company company, LastUpdated lu) {
		if(company == null) {
			throw new IllegalArgumentException("company is null");
		}
		
		update(lu);
	}
	
	public LastUpdated findByCompany(final Company company) {
		if(company == null) {
			throw new IllegalArgumentException("company is null");
		}
		
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("company", company));
		
		ObjectList<LastUpdated> companyList = findAllByCriterion(null, null, null, null, conj);
		if(companyList.getSize() > 0) {
			return companyList.getList().get(0);
		}
			
		return null;
	}
}
