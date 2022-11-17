package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageLanguage;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.list.ObjectList;

public class MultiPageLanguageDAO
		extends AbstractBaseDAO<MultiPageLanguage>
{
	public MultiPageLanguageDAO() {
		super();
	} 
	
	public MultiPageLanguage find(Language language,MultiPage multiPage) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("language", language));
		conj.add(Restrictions.eq("defaultPage", multiPage));
		
		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
	
}
