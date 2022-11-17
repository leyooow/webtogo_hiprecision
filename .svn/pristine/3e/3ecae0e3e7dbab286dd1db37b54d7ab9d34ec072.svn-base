package com.ivant.cms.helper;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.User;

public class CategoryHelper {

	public static Category createCategory(Company company, String name, Group group, User createdBy, Category parent) {
		Category category = new Category();
		category.setCompany(company);
		category.setName(name);
		category.setParentCategory(parent);
		category.setCreatedBy(createdBy);
		category.setParentGroup(group);
		
		return category;
	}
	
	public static Category createCategory(Company company, String name, Group group, User createdBy) {
		return CategoryHelper.createCategory(company, name, group, createdBy, null);
	}
	
}
