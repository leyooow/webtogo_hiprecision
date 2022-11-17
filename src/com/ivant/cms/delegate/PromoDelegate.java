package com.ivant.cms.delegate;

import com.ivant.cms.db.PromoDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Promo;
import com.ivant.cms.entity.list.ObjectList;

public class PromoDelegate extends AbstractBaseDelegate<Promo, PromoDAO>
{
	private static PromoDelegate instance = new PromoDelegate();
	
	public static PromoDelegate getInstance() 
	{
		return instance;
	}
	
	public PromoDelegate() 
	{
		super(new PromoDAO());
	}
	
	public Promo findByName(Company company, String name) 
	{
		return dao.findByName(company, name);
	}

	public ObjectList<Promo> findAll(Company company) 
	{
		return dao.findAll(company);
	}

}