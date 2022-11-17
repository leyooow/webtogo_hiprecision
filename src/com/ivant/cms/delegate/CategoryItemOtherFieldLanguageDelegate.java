package com.ivant.cms.delegate;

import com.ivant.cms.db.CategoryItemOtherFieldLanguageDAO;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryItemOtherFieldLanguage;
import com.ivant.cms.entity.Language;

/**
 * @author Isaac Arenas Pichay
 * @since Apr 8, 2014
 */
public class CategoryItemOtherFieldLanguageDelegate
		extends
		AbstractBaseDelegate<CategoryItemOtherFieldLanguage, CategoryItemOtherFieldLanguageDAO> {

	private static CategoryItemOtherFieldLanguageDelegate instance = new CategoryItemOtherFieldLanguageDelegate();

	public static CategoryItemOtherFieldLanguageDelegate getInstance() {
		return instance;
	}

	protected CategoryItemOtherFieldLanguageDelegate() {
		super(new CategoryItemOtherFieldLanguageDAO());
	}

	public CategoryItemOtherFieldLanguage find(Language language,
			CategoryItemOtherField categoryItemOtherField) {
		return dao.find(language, categoryItemOtherField);
	}
}
