package com.ivant.cms.delegate;

import com.ivant.cms.db.GroupLanguageDAO;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.GroupLanguage;
import com.ivant.cms.entity.Language;

public class GroupLanguageDelegate extends
		AbstractBaseDelegate<GroupLanguage, GroupLanguageDAO> {
	private static GroupLanguageDelegate instance = new GroupLanguageDelegate();

	public static GroupLanguageDelegate getInstance() {
		return instance;
	}

	public GroupLanguageDelegate() {
		super(new GroupLanguageDAO());
	}

	public GroupLanguage find(Language language, Group group) {
		return dao.find(language, group);
	}
}
