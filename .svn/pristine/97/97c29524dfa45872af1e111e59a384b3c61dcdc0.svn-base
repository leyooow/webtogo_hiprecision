package com.ivant.cms.delegate;

import com.ivant.cms.db.CategoryFileDAO;
import com.ivant.cms.entity.CategoryFile;
import com.ivant.cms.entity.Company;

public class CategoryFileDelegate extends AbstractBaseDelegate<CategoryFile, CategoryFileDAO> {
	

	private static CategoryFileDelegate instance = new CategoryFileDelegate();
	
	public static CategoryFileDelegate getInstance() {
		return CategoryFileDelegate.instance;
	}
	
	public CategoryFileDelegate() {
		super(new CategoryFileDAO());
	}
	
	public CategoryFile findFileID(Company company, long categoryID)
	{
		return dao.findFileID(company, categoryID);
	}
	public CategoryFile findItemFileID(Company company, long realID)
	{
		return dao.findItemFileID(company,realID);
	}

}
