package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryItemOtherFieldLanguage;
import com.ivant.cms.entity.Language;

/**
 * @author Isaac Arenas Pichay
 * @since Apr 8, 2014
 */
public class CategoryItemOtherFieldLanguageDAO extends
		AbstractBaseDAO<CategoryItemOtherFieldLanguage> {

	public CategoryItemOtherFieldLanguageDAO() {
		super();
	}

	public CategoryItemOtherFieldLanguage find(Language language,
			CategoryItemOtherField categoryItemOtherField) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("language", language));
		conj.add(Restrictions.eq("defaultCategoryItemOtherField", categoryItemOtherField));

		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
}
