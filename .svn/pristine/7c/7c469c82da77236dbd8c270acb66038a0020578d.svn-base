package com.ivant.cms.delegate;

import com.ivant.cms.db.PageFileDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PageFile;

public class PageFileDelegate extends AbstractBaseDelegate<PageFile, PageFileDAO> {
	

	private static PageFileDelegate instance = new PageFileDelegate();
	
	public static PageFileDelegate getInstance() {
		return PageFileDelegate.instance;
	}
	
	public PageFileDelegate() {
		super(new PageFileDAO());
	}
	
	public PageFile find(Company company, Long id)
	{
		return dao.find(company, id);
	}

}
