package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.MemberPageFile;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.PageFile;

public class MemberPageFileDAO 
	extends AbstractBaseDAO<MemberPageFile>{

	public MemberPageFile find(MultiPageFile multiPageFile, MemberType memberType) {
		
		Junction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("memberType", memberType));
		conj.add(Restrictions.eq("multiPageFile", multiPageFile));
		
		List<MemberPageFile> results = findAllByCriterion(null, null, null, null, conj).getList();
		
		if(results != null && results.size() > 0)
			return results.get(0);
		else
			return null;
	}
	
	public MemberPageFile find(PageFile pageFile, MemberType memberType) {
		
		Junction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("memberType", memberType));
		conj.add(Restrictions.eq("pageFile", pageFile));
		
		List<MemberPageFile> results = findAllByCriterion(null, null, null, null, conj).getList();
		
		if(results != null && results.size() > 0)
			return results.get(0);
		else
			return null;
	}
}
