package com.ivant.cms.delegate;

import java.text.ParseException;
import java.util.Date;

import com.ivant.cms.db.PromoCodeDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.list.ObjectList;

public class PromoCodeDelegate extends AbstractBaseDelegate<PromoCode, PromoCodeDAO>
{
	private static PromoCodeDelegate instance = new PromoCodeDelegate();
	
	public static PromoCodeDelegate getInstance() 
	{
		return instance;
	}
	
	public PromoCodeDelegate() 
	{
		super(new PromoCodeDAO());
	}
	
	public PromoCode findByItems(Company company, String items) 
	{
		return dao.findByItems(company, items);
	}
	
	public PromoCode findByCode(Company company, String code) 
	{
		return dao.findByCode(company, code);
	}
	
	public PromoCode findByNote(Company company, String note) 
	{
		return dao.findByNote(company, note);
	}

	public ObjectList<PromoCode> findAll(Company company) 
	{
		return dao.findAll(company);
	}

	public ObjectList<PromoCode> findAllWithinExpiryDate(Company company) throws ParseException 
	{
		return dao.findAllWithinExpiryDate(company);
	}
	
	public ObjectList<PromoCode> findAllWithPaging(Company company, int resultPerPage, int pageNumber, Date fromDate, Date toDate, String note)
	{
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, fromDate, toDate, note);
	}
}