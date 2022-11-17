package com.ivant.cms.helper;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;

public class GroupHelper {

	public static Group createGroup(Company company, String name, boolean isFeatured) {
		Group group = new Group();
		group.setCompany(company);
		group.setName(name);
		group.setFeatured(isFeatured);
		return group;
	}	
	
	public static Group createGroup(Company company, String name) {
		return GroupHelper.createGroup(company, name, true);
	}	
}
