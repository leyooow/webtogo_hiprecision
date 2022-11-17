package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.OtherField;

public class OtherFieldDAO
		extends AbstractBaseDAO<OtherField>
{
	public OtherFieldDAO() {
		super();
	} 
	
	public OtherField find(String name, Company company, boolean isKeyword) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		if(isKeyword)
		{
			conj.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		else
		{
			conj.add(Restrictions.eq("name", name));	
		}
		
		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
	}
	
	public OtherField find(String name, boolean isKeyword, Company company, Group group) {
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("group", group));
		if(isKeyword){
			conj.add(Restrictions.like("name",name, MatchMode.ANYWHERE));
		}
		else{
			conj.add(Restrictions.eq("name",name));
		}
		return findAllByCriterion(null, null, null, null, conj).uniqueResult();
		
	}
	
	public List<OtherField> find(Company company) {			
		Junction conj = Restrictions.conjunction();
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
			
		return findAllByCriterion(null, null, null, null, conj).getList();
	}

	public List<OtherField> findByGroup(Company company, Group group)
	{
		final Junction conj = Restrictions.conjunction();
		
		conj.add(Restrictions.eq("isValid", Boolean.TRUE));
		conj.add(Restrictions.eq("company", company));
		conj.add(Restrictions.eq("group", group));
			
		return findAllByCriterion(null, null, null, null, conj).getList();
	}
}
