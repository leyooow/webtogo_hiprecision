package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.list.ObjectList;

public class LanguageDAO
		extends AbstractBaseDAO<Language>
{
	public LanguageDAO() {
		super();
	} 
	
	public Language find(String language, Company company) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("language", language));
		conj.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
	
}
