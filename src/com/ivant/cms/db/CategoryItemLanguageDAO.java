package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemLanguage;
import com.ivant.cms.entity.Language;

public class CategoryItemLanguageDAO extends
		AbstractBaseDAO<CategoryItemLanguage> {
	public CategoryItemLanguageDAO() {
		super();
	}

	public CategoryItemLanguage find(Language language,
			CategoryItem categoryItem) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("language", language));
		conj.add(Restrictions.eq("defaultCategoryItem", categoryItem));

		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
}
