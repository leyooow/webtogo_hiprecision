package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryLanguage;
import com.ivant.cms.entity.Language;

public class CategoryLanguageDAO extends AbstractBaseDAO<CategoryLanguage> {
	public CategoryLanguageDAO() {
		super();
	}

	public CategoryLanguage find(Language language, Category category) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("language", language));
		conj.add(Restrictions.eq("defaultCategory", category));

		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
}
