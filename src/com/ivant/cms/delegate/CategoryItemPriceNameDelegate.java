package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.CategoryItemPriceNameDAO;
import com.ivant.cms.entity.CategoryItemPriceName;
import com.ivant.cms.entity.Group;

public class CategoryItemPriceNameDelegate extends AbstractBaseDelegate<CategoryItemPriceName, CategoryItemPriceNameDAO>{

	private static CategoryItemPriceNameDelegate categoryItemPriceNameDelegate = new CategoryItemPriceNameDelegate();
	
	public CategoryItemPriceNameDelegate()
	{
		super(new CategoryItemPriceNameDAO());
	}
	
	public static CategoryItemPriceNameDelegate getInstance()
	{
		return categoryItemPriceNameDelegate;
	}
	
	public List<CategoryItemPriceName> findByGroup(Group group)
	{
		return dao.findByGroup(group);
	}
	
	public CategoryItemPriceName findByName(String name, Group group)
	{
		return dao.findByName(name, group);
	}
	
}
