package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.LanguageDAO;
import com.ivant.cms.db.OtherFieldDAO;
import com.ivant.cms.db.SinglePageLanguageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.SinglePageLanguage;
import com.ivant.cms.entity.list.ObjectList;

public class SinglePageLanguageDelegate
		extends AbstractBaseDelegate<SinglePageLanguage, SinglePageLanguageDAO>
{
	private static SinglePageLanguageDelegate instance = new SinglePageLanguageDelegate();
	
	public static SinglePageLanguageDelegate getInstance() {
		return instance;
	}
	
	public SinglePageLanguageDelegate() {
		super(new SinglePageLanguageDAO());
	}	
	
	public SinglePageLanguage find(Language language,SinglePage singlePage) {
		return dao.find(language,singlePage);
	}
}
