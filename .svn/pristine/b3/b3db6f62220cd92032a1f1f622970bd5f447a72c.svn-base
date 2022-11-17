package com.ivant.cms.delegate;

import java.util.Date;

import com.ivant.cms.db.BillingDAO;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.BillingStatusEnum;
import com.ivant.cms.enums.BillingTypeEnum;


public class BillingDelegate extends AbstractBaseDelegate<Billing, BillingDAO>
{
	
	private static BillingDelegate instance = new BillingDelegate();
	
	public static BillingDelegate getInstance() 
	{
		return instance;
	}
	
	public BillingDelegate() 
	{
		super(new BillingDAO());
	}
	
	public ObjectList<Billing> findAll(Company company) 
	{
		return dao.findAll(company);
	}
	
	public ObjectList<Billing> findAllWithPaging(Company company, int resultPerPage, int pageNumber, BillingStatusEnum status, BillingTypeEnum type, Date dueDate, Date fromDate, Date toDate, String note)
	{
		return dao.findAllWithPaging(company, resultPerPage, pageNumber, status, type, dueDate, fromDate, toDate, note);
	}
	
	
}