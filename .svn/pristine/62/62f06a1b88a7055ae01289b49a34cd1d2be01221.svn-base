package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.list.ObjectList;

public class CompanySettingsDAO extends AbstractBaseDAO<CompanySettings> {

	public CompanySettingsDAO() {
		super();
	}
	
	public CompanySettings find(Company company) {
		CompanySettings companySettings = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		try {
			companySettings = findAllByCriterion(null, null, null, null, null,	null, junc).getList().get(0);
		}
		catch(Exception e) {}
		
		return companySettings;
	}

	public ObjectList<CompanySettings> findAll(Boolean isValid)
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
}
