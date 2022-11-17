package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.LanguageDAO;
import com.ivant.cms.db.OtherFieldDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.list.ObjectList;

public class LanguageDelegate
		extends AbstractBaseDelegate<Language, LanguageDAO>
{
	private static LanguageDelegate instance = new LanguageDelegate();
	
	public static LanguageDelegate getInstance() {
		return instance;
	}
	
	public LanguageDelegate() {
		super(new LanguageDAO());
	}	
	
	public Language find(String language,Company company) {
		return dao.find(language,company);
	}
}
