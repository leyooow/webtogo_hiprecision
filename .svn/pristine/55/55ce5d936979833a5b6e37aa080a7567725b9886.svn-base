package com.ivant.cms.helper;

import com.ivant.cms.entity.Company;
import com.ivant.cms.enums.BusinessType;

public class CompanyHelper {

	public static Company createCompany(String name, String domainName, String address, 
			String phone, String contactPerson, BusinessType businessType, String email) {
		Company company = new Company();
		
		company.setName(name);
		company.setAddress(address);
		company.setPhone(phone);
		company.setContactPerson(contactPerson);
		company.setBusinessType(businessType);
		company.setEmail(email);
		company.setDomainName(domainName);
		
		return company;
	}
	
	public static Company createCompany(String name, String address, 
			String phone, String contactPerson, BusinessType businessType, String email) {
		Company company = new Company();
		
		company.setName(name);
		company.setAddress(address);
		company.setPhone(phone);
		company.setContactPerson(contactPerson);
		company.setBusinessType(businessType);
		company.setEmail(email);
		company.setDomainName("www." + name + ".com");
		
		return company;
	}
	
}
