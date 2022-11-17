package com.ivant.cms.delegate;

import com.ivant.cms.db.CategoryItemLanguageDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemLanguage;
import com.ivant.cms.entity.Language;

public class CategoryItemLanguageDelegate extends
		AbstractBaseDelegate<CategoryItemLanguage, CategoryItemLanguageDAO> {

	private static CategoryItemLanguageDelegate instance = new CategoryItemLanguageDelegate();

	public static CategoryItemLanguageDelegate getInstance() {
		return instance;
	}

	public CategoryItemLanguageDelegate() {
		super(new CategoryItemLanguageDAO());
	}

	public CategoryItemLanguage find(Language language,
			CategoryItem categoryItem) {
		return dao.find(language, categoryItem);
	}
}
