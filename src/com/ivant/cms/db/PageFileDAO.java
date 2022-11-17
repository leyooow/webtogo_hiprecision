package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PageFile;

public class PageFileDAO extends AbstractBaseDAO<PageFile> {

	public PageFileDAO() {
		super();
	}
	
	public PageFile	find(Company company, Long id)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("id", id));
		
		return findAllByCriterion(null, null, null, null, junc).uniqueResult();
	}
}
