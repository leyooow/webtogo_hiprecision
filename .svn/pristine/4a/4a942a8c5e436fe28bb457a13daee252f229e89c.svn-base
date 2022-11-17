package com.ivant.cms.delegate;


import com.ivant.cms.db.CategoryItemHistoryDAO;

import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemHistory;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.list.ObjectList;
/**
 * 
 * @author Anjerico D. Gutierrez
 * @since October 12, 2015
 */
public class CategoryItemHistoryDelegate extends AbstractBaseDelegate<CategoryItemHistory, CategoryItemHistoryDAO>{
	private static CategoryItemHistoryDelegate instance = new CategoryItemHistoryDelegate();
	public static CategoryItemHistoryDelegate getInstance() {
		return instance;
	}
	
	public CategoryItemHistoryDelegate() {
		super(new CategoryItemHistoryDAO());
	}
	
	public ObjectList<CategoryItemHistory> findByCategoryItem(Company company, CategoryItem categoryItem) {
		return dao.findByCategoryItem(company, categoryItem);
	}
	
	public ObjectList<CategoryItemHistory> findByUser(Company company, User user) {
		return dao.findByUser(company, user);
	}
	
}
