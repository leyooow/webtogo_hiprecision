package com.ivant.cms.helper;

import java.util.Date;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ContactUs;
import com.ivant.cms.entity.User;

public class ContactUsHelper {
	
	public static ContactUs createContactUs(Company company, String name, String title, 
								String content, User createdBy, Date validFrom, Date validTo,
								String details, String email  ) {
		ContactUs ContactUs = new ContactUs();
		ContactUs.setName(name);
		ContactUs.setCompany(company);
		ContactUs.setTitle(title);
		ContactUs.setCreatedBy(createdBy);
		ContactUs.setValidFrom(validFrom);
		ContactUs.setValidTo(validTo);
		//ContactUs.setCreatedOn(new Date());
		//ContactUs.setUpdatedOn(new Date());
		ContactUs.setDetails(details);
		ContactUs.setEmail(email);
		
		return ContactUs;
	}
	
	public static ContactUs createContactUs(Company company, String name, String title,
			String content, User createdBy, String details, String email) 
	{
		return ContactUsHelper.createContactUs(company, name, title, content, createdBy, null, null, details, email);
	}
	
}