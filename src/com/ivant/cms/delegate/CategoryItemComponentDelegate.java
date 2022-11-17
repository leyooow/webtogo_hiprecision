package com.ivant.cms.delegate;

import com.ivant.cms.db.CategoryItemComponentDAO;
import com.ivant.cms.entity.CategoryItemComponent;

public class CategoryItemComponentDelegate 
	extends AbstractBaseDelegate<CategoryItemComponent, CategoryItemComponentDAO>{

	private static CategoryItemComponentDelegate instance = new CategoryItemComponentDelegate();
	
	public CategoryItemComponentDelegate() {
		super(new CategoryItemComponentDAO());
		
	}
	
	public static CategoryItemComponentDelegate getInstance() {
		return instance;
	}

}
