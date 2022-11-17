package com.ivant.cms.action.admin.dwr;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.ComponentCategoryDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ComponentCategory;

public class DWRComponentCategoryAction {

	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private ComponentCategoryDelegate componentCategoryDelegate = ComponentCategoryDelegate.getInstance();
	
	
	public Long save(Long companyId, Long categoryId, String name) {
		
		Company company = companyDelegate.find(companyId);
		ComponentCategory category = componentCategoryDelegate.find(categoryId);
		
		if(category == null)
			category = new ComponentCategory();
		
		category.setCompany(company);
		category.setName(name);
		
		if(category.getId() == null)
			componentCategoryDelegate.find(componentCategoryDelegate.insert(category));
		else
			componentCategoryDelegate.update(category);
		
		return category.getId();
	}
	
	public Boolean delete(Long categoryId) {
		ComponentCategory category = componentCategoryDelegate.find(categoryId);
		
		return componentCategoryDelegate.delete(category);
	}
}
