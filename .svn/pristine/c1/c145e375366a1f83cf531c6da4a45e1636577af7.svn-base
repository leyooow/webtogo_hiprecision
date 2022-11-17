package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.ComponentCategoryDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ComponentCategory;
import com.ivant.cms.entity.list.ObjectList;

public class ComponentCategoryDelegate 
	extends AbstractBaseDelegate<ComponentCategory, ComponentCategoryDAO>{

	private static ComponentCategoryDelegate instance = new ComponentCategoryDelegate();
	
	public ComponentCategoryDelegate() {
		super(new ComponentCategoryDAO());
	}

	public static ComponentCategoryDelegate getInstance() {
		return instance;
	}
	
	public ObjectList<ComponentCategory> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ComponentCategory findByKeyword(Company company, String keyword)
	{
		return dao.findByKeyword(company, keyword);
	}
	
	public ObjectList<ComponentCategory> findAll(Company company, Order...orders) {
		return dao.findAll(company, orders);
	}
}
