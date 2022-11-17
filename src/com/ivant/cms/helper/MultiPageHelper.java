package com.ivant.cms.helper;

import java.util.Date;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.User;

public class MultiPageHelper {

	public static MultiPage createMultiPage(Company company, String name, User createdBy, 
			Date validFrom, Date validTo, boolean isFeatured) {
		MultiPage multiPage = new MultiPage();
		multiPage.setName(name);
		multiPage.setCompany(company);
		multiPage.setCreatedBy(createdBy);
		multiPage.setValidFrom(validFrom);
		multiPage.setValidTo(validTo);
		multiPage.setFeatured(isFeatured);
		
		return multiPage;
	}
	
	public static MultiPage createMultiPage(Company company, String name, User createdBy) {
		return MultiPageHelper.createMultiPage(company, name, createdBy, new Date(), new Date(), false);
	}
	
}
