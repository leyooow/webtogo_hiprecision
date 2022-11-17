package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryFile;
import com.ivant.cms.entity.Company;

public class CategoryFileDAO extends AbstractBaseDAO<CategoryFile> {

	public CategoryFileDAO() {
		super();
	}
	
	public CategoryFile findFileID(Company company, long categoryID)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("category.id", categoryID));
		
		return (findAllByCriterion(null, null, null, null, junc)).getList().get(0);
	}
	
	public CategoryFile findItemFileID(Company company, long realID)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("category.id", realID));
		List<CategoryFile> list = (findAllByCriterion(null, null, null, null, junc)).getList();
		if((list != null) ? list.size() > 0 :  false )
			return list.get(0);
		else
			return null;
	}
	
}
