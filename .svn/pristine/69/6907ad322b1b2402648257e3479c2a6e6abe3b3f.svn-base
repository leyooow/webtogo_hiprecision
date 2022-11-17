package com.ivant.cms.delegate;

import com.ivant.cms.db.CategoryItemPriceDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemPrice;
import com.ivant.cms.entity.CategoryItemPriceName;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.list.ObjectList;

public class CategoryItemPriceDelegate extends AbstractBaseDelegate<CategoryItemPrice, CategoryItemPriceDAO>{

	private static CategoryItemPriceDelegate categoryItemPriceDelegate = new CategoryItemPriceDelegate();
	
	public CategoryItemPriceDelegate()
	{
		super(new CategoryItemPriceDAO());
	}
	
	public static CategoryItemPriceDelegate getInstance()
	{
		return categoryItemPriceDelegate;
	}
	
	public CategoryItemPrice findByCategoryItemPriceName(Company company, CategoryItem categoryItem, CategoryItemPriceName categoryItemPriceName)
	{
		return dao.findByCategoryItemPriceName(company, categoryItem, categoryItemPriceName);
	}
	
	public ObjectList<CategoryItemPrice> findAllByCategoryItem(Company company, CategoryItem categoryItem)
	{
		return dao.findAllByCategoryItem(company, categoryItem);
	}
}
