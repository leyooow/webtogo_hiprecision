package com.ivant.cms.delegate;

import java.util.List;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.ItemVariationDAO;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemVariation;

public class ItemVariationDelegate extends AbstractBaseDelegate<ItemVariation, ItemVariationDAO>{

	private static final ItemVariationDelegate instance = new ItemVariationDelegate();
	
	public static ItemVariationDelegate getInstance() {
		return instance;
	}
	
	public ItemVariationDelegate() {
		super( new ItemVariationDAO());
	}
	
	public List<ItemVariation> findAll(Company company, CategoryItem item, Order...orders) {
		return dao.findAll(company, item, orders); 
	}

	public ItemVariation findBySKU(Company company, String sku) {
		if(company == null) {
			throw new IllegalArgumentException("company is null");
		}
		
		if(sku == null) {
			throw new IllegalArgumentException("SKU is null");
		}
		
		return dao.find(company, sku);
	}
	public ItemVariation find(CategoryItem categoryItem, String name) {
		return dao.find(categoryItem, name);
	}
	
}
