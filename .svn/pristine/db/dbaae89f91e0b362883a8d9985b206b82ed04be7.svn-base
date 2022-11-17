package com.ivant.cms.delegate;

import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Payment;
import com.ivant.cms.enums.PaymentTypeEnum;
import com.ivant.cms.db.PaymentDAO;
import com.ivant.cms.entity.list.ObjectList;


public class PaymentDelegate extends AbstractBaseDelegate<Payment, PaymentDAO>{
	
	private static PaymentDelegate instance = new PaymentDelegate();
	
	public static PaymentDelegate getInstance() {
		return instance;
	}
	
	public PaymentDelegate() {
		super(new PaymentDAO());
	}
	
	public ObjectList<Payment> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ObjectList<Payment> 	findAllByBillingWithPaging(Company company, int resultPerPage, int pageNumber, Billing billing)
	{
		return dao.findAllByBillingWithPaging(company, resultPerPage, pageNumber, billing);
	}
	
	
	public ObjectList<Payment> 	findAllByBilling(Company company,  Billing billing)
	{
		return dao.findAllByBilling(company, billing);
	}
	
	public ObjectList<Payment> findAllWithPaging(Billing billing, Company company, int resultPerPage, int pageNumber, PaymentTypeEnum type, String note) {
		
		return dao.findAllWithPaging(billing, company, resultPerPage, pageNumber, type, note);
	}
	
	
	public ObjectList<Payment> findAllWithPaging(Company company, int resultPerPage, int pageNumber) {
		
		return dao.findAllWithPaging(company, resultPerPage, pageNumber);
	}	
	
	
}