package com.ivant.cms.db;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.Payment;
import com.ivant.cms.enums.PaymentTypeEnum;
import com.ivant.cms.entity.list.ObjectList;

public class PaymentDAO extends AbstractBaseDAO<Payment>
{

	public PaymentDAO() {
		super();
	}
	
	public ObjectList<Payment> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(-1, -1, null, null, null,	null, junc);
	}
	
	public ObjectList<Payment> findAllWithPaging(Company company, int resultPerPage, int pageNumber) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null,	new Order[]{Order.desc("id")}, junc);
	}
	
	
	public ObjectList<Payment> findAllByBilling(Company company, Billing billing)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("billing.id", billing.getId()));
		
		return findAllByCriterion(-1, -1, null, null, null,	null, junc);
	}
	
	
	
	
	public ObjectList<Payment> findAllByBillingWithPaging(Company company, int resultPerPage, int pageNumber, Billing billing)
	{
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("billing.id", billing.getId()));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null,	null, junc);
	}
	
	public ObjectList<Payment> findAllWithPaging(Billing billing, Company company, int resultPerPage, int pageNumber, PaymentTypeEnum type, String note) {		
		
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		junc.add(Restrictions.eq("billing", billing));
		if (type != null)
			junc.add(Restrictions.eq("type", type));
		if (note != null)
		  junc.add(Restrictions.eq("note", note));
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, null, junc);
	}	
	
}