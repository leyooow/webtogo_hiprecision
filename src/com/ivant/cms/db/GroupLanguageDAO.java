package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.GroupLanguage;
import com.ivant.cms.entity.Language;

public class GroupLanguageDAO extends AbstractBaseDAO<GroupLanguage> {
	public GroupLanguageDAO() {
		super();
	}

	public GroupLanguage find(Language language, Group group) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("language", language));
		conj.add(Restrictions.eq("defaultGroup", group));

		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
}
