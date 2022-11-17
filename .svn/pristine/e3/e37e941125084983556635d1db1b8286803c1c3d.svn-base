package com.ivant.cms.company.utils;

import java.util.List;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;

public class KuysenSalesClientUtils {
	
	private static final KuysenSalesClientUtils instance = new KuysenSalesClientUtils();
	
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	
	public static KuysenSalesClientUtils getInstance() {
		return KuysenSalesClientUtils.instance;
	}
	
	public List<CategoryItem> getClientStatus(Company company) {

		List<Category> categories = categoryDelegate.findAll(company).getList();
		List<CategoryItem> client_status = null;

		for(Category category : categories) {
			if(category.getName().equalsIgnoreCase("Client Status")) {
				client_status = category.getItems();
			}
		}
		return client_status;
	}
}
