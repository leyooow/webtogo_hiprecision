package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.SinglePageLanguage;
import com.ivant.cms.entity.list.ObjectList;

public class SinglePageLanguageDAO
		extends AbstractBaseDAO<SinglePageLanguage>
{
	public SinglePageLanguageDAO() {
		super();
	} 
	
	public SinglePageLanguage find(Language language,SinglePage singlePage) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("language", language));
		conj.add(Restrictions.eq("defaultPage", singlePage));
		
		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
	
}
