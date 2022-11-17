package com.ivant.cms.db;

import java.util.List;

import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.entity.ContactUs;

public class ContactUsDAO extends AbstractBaseDAO<ContactUs>{

	public ContactUsDAO() {
		super();
	}
	
	public ContactUs findContactUsByCompany(Long companyid) {
		List<ContactUs> contactus = null;
		Junction junc = Restrictions.conjunction();
		junc.add(Restrictions.eq("isValid", Boolean.TRUE));
		junc.add(Restrictions.eq("company.id", companyid));
		
		
		contactus = findAllByCriterion(null, null, null, null, null,	null, junc).getList();
		if (contactus.size()!= 0)
			return contactus.get(0);
		else
			return null;
	}
	

	
}
