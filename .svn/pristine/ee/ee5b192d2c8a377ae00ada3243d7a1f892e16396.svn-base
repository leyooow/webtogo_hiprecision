package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.PageImageDAO;
import com.ivant.cms.entity.BasePage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PageImage;

public class PageImageDelegate extends AbstractBaseDelegate<PageImage, PageImageDAO>{

	private static PageImageDelegate instance = new PageImageDelegate();
	
	public static PageImageDelegate getInstance() {
		return PageImageDelegate.instance;
	}
	
	public PageImageDelegate() {
		super(new PageImageDAO());
	}
	
	public List<PageImage> findAllSortedByTitle(BasePage page)
	{
		return dao.findAllSortedByTitle(page);
	}
	
}
