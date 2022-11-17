package com.ivant.cms.delegate;

import com.ivant.cms.db.CategoryLanguageDAO;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryLanguage;
import com.ivant.cms.entity.Language;

public class CategoryLanguageDelegate extends
		AbstractBaseDelegate<CategoryLanguage, CategoryLanguageDAO> {

	private static CategoryLanguageDelegate instance = new CategoryLanguageDelegate();

	public static CategoryLanguageDelegate getInstance() {
		return instance;
	}

	public CategoryLanguageDelegate() {
		super(new CategoryLanguageDAO());
	}
	
	public CategoryLanguage find(Language language, Category category) {
		return dao.find(language,category);
	}

}
