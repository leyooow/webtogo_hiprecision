package com.ivant.cms.helper;

import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;

public class CategoryItemHelper {

	public static CategoryItem createCategoryItem(Company company, Category parent, String name, 
					String sku, double price, boolean isFeaturedOnHome) {
		CategoryItem item = new CategoryItem();
		item.setCompany(company);
		item.setName(name);
		item.setSku(sku);
		item.setPrice(price);
		item.setParent(parent);
		item.setFeatured(isFeaturedOnHome);
		
		return item;
	}
	public static CategoryItem createCategoryItem(Company company, Category parent, String name, String sku, double price) { 
		return CategoryItemHelper.createCategoryItem(company, parent, name, sku, price, false);
	}
}
