package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.FormPageLanguage;
import com.ivant.cms.entity.Language;

public class FormPageLanguageDAO extends AbstractBaseDAO<FormPageLanguage> {
	public FormPageLanguageDAO() {
		super();
	}

	public FormPageLanguage find(Language language, FormPage formPage) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("language", language));
		conj.add(Restrictions.eq("defaultGroup", formPage));

		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
}
