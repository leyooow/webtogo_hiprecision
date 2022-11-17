package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.ComponentDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Component;
import com.ivant.cms.entity.list.ObjectList;

public class ComponentDelegate 
	extends AbstractBaseDelegate<Component, ComponentDAO>{

	private static ComponentDelegate instance = new ComponentDelegate();
	
	public ComponentDelegate() {
		super(new ComponentDAO());
	}
	
	public static ComponentDelegate getInstance() {
		return instance;
	}

	public ObjectList<Component> findAll(int page, int maxResults, Order[] orders, Company company ) {
		return dao.findAll(page, maxResults, orders, company);
	}
	
	public ObjectList<Component> findAll(Company company)
	{
		return dao.findAll(company);
	}
	
	public Component findByKeyword(Company company, String keyword)
	{
		return dao.findByKeyword(company, keyword);
	}
}
