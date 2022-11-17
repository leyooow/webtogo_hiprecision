package com.ivant.cms.delegate;

import com.ivant.cms.db.CategoryImageDAO;
import com.ivant.cms.entity.CategoryImage;

public class CategoryImageDelegate extends AbstractBaseDelegate<CategoryImage, CategoryImageDAO>{

	private static CategoryImageDelegate instance = new CategoryImageDelegate();
	
	public static CategoryImageDelegate getInstance() {
		return CategoryImageDelegate.instance;
	}
	
	public CategoryImageDelegate() {
		super(new CategoryImageDAO());
	}
	
}
