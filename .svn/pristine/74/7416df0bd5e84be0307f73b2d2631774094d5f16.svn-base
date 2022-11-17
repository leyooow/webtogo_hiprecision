package com.ivant.cms.db;

import java.util.Date;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Billing;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.BillingStatusEnum;
import com.ivant.cms.enums.BillingTypeEnum;

public class BillingDAO extends AbstractBaseDAO<Billing>
{

	public BillingDAO() {
		super();
	}
	
	public ObjectList<Billing> findAll(Company company) {
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		
		return findAllByCriterion(null, null, null, null, null,	null, junc);
	}
	
	
	public ObjectList<Billing> findAllWithPaging(Company company, int resultPerPage, int pageNumber, BillingStatusEnum status, BillingTypeEnum type, Date dueDate, Date fromDate, Date toDate, String note) {		
	
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company", company));
		if (status != null) {
			junc.add(Restrictions.eq("status", status));
		}
		if (type != null) {
			junc.add(Restrictions.eq("type", type));
		}
		if (dueDate != null ) {
			junc.add(Restrictions.eq("dueDate", dueDate));
		}
		if (fromDate != null) {
			junc.add(Restrictions.eq("fromDate", fromDate));
		}
		if (fromDate != null && toDate != null) {
			junc.add(Restrictions.ge("fromDate", toDate));
			junc.add(Restrictions.le("toDate", toDate));
		}
		if (note != null)
			junc.add(Restrictions.eq("note", note));
		
		return findAllByCriterion(pageNumber, resultPerPage, null, null, null, new Order[]{Order.desc("id")}, junc);
	}	
	
	
	
}
