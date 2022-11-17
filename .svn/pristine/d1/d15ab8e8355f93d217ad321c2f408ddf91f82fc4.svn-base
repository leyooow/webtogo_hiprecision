package com.ivant.cms.delegate;

import com.ivant.cms.db.FormPageLanguageDAO;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.FormPageLanguage;
import com.ivant.cms.entity.Language;

public class FormPageLanguageDelegate extends
		AbstractBaseDelegate<FormPageLanguage, FormPageLanguageDAO> {
	private static FormPageLanguageDelegate instance = new FormPageLanguageDelegate();

	public static FormPageLanguageDelegate getInstance() {
		return instance;
	}

	public FormPageLanguageDelegate() {
		super(new FormPageLanguageDAO());
	}

	public FormPageLanguage find(Language language, FormPage formPage) {
		return dao.find(language, formPage);
	}
}
