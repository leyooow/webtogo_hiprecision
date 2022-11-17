package com.ivant.cms.delegate;

import com.ivant.cms.db.TruecareTestimonialDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.TruecareTestimonial;
import com.ivant.cms.entity.list.ObjectList;

public class TruecareTestimonialDelegate extends AbstractBaseDelegate<TruecareTestimonial, TruecareTestimonialDAO>
{
	private static TruecareTestimonialDelegate instance = new TruecareTestimonialDelegate();
	
	public static TruecareTestimonialDelegate getInstance() 
	{
		return instance;
	}
	
	public TruecareTestimonialDelegate() 
	{
		super(new TruecareTestimonialDAO());
	}
	
	public TruecareTestimonial findByEmail(Company company, String email) 
	{
		return dao.findByEmail(company, email);
	}
	
	public ObjectList<TruecareTestimonial> findAll(Company company)
	{
		return dao.findAll(company);
	}

}