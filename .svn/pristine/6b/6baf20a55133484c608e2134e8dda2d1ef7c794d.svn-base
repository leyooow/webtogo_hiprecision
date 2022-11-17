package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MemberImages;
import com.ivant.cms.entity.list.ObjectList;

public class MemberImagesDAO extends AbstractBaseDAO<MemberImages>{

	public MemberImagesDAO(){
		super();
	}
	
	public MemberImages findByMember(Company company, String membername){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("member_name", membername));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult(); 
	}
	
	public ObjectList<MemberImages> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
}
