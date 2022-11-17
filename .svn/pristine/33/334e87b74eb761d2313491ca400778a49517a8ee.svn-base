package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.TruecareTestimonial;
import com.ivant.cms.entity.list.ObjectList;

public class TruecareTestimonialDAO extends AbstractBaseDAO<TruecareTestimonial>{

	public TruecareTestimonialDAO(){
		super();
	}
	
	public TruecareTestimonial findByEmail(Company company, String email){
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("email", email));//null
		return findAllByCriterion(null, null, null, null, null,	null, junc).uniqueResult(); 
	}
	
	public ObjectList<TruecareTestimonial> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
}
