package com.ivant.cms.helper;

import java.util.Date;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;

public class SinglePageHelper {
	
	public static SinglePage createSinglePage(Company company, String name, String title, String subTitle,
								String content, User createdBy, Date validFrom, Date validTo, boolean isFeatured) {
		SinglePage singlePage = new SinglePage();
		singlePage.setName(name);
		singlePage.setCompany(company);
		singlePage.setTitle(title);
		singlePage.setSubTitle(subTitle);
		singlePage.setContent(content);
		singlePage.setCreatedBy(createdBy);
		singlePage.setValidFrom(validFrom);
		singlePage.setValidTo(validTo);
		
		return singlePage;
	}
	
	public static SinglePage createSinglePage(Company company, String name, String title, String subTitle,
			String content, User createdBy) {
		return SinglePageHelper.createSinglePage(company, name, title, subTitle, content, createdBy, null, null, false);
	}
	
	public static SinglePage createSinglePage(Company company, String name, String title, String subTitle,
			String content, User createdBy, boolean isFeatured) {
		return SinglePageHelper.createSinglePage(company, name, title, subTitle, content, createdBy, null, null, isFeatured);
	}
}
