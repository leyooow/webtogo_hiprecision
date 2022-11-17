package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.LanguageDAO;
import com.ivant.cms.db.MultiPageLanguageDAO;
import com.ivant.cms.db.OtherFieldDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageLanguage;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.list.ObjectList;

public class MultiPageLanguageDelegate
		extends AbstractBaseDelegate<MultiPageLanguage, MultiPageLanguageDAO>
{
	private static MultiPageLanguageDelegate instance = new MultiPageLanguageDelegate();
	
	public static MultiPageLanguageDelegate getInstance() {
		return instance;
	}
	
	public MultiPageLanguageDelegate() {
		super(new MultiPageLanguageDAO());
	}	
	
	public MultiPageLanguage find(Language language,MultiPage multiPage) {
		return dao.find(language,multiPage);
	}
}
