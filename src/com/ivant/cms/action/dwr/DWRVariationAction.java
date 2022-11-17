package com.ivant.cms.action.dwr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.ivant.cms.action.admin.dwr.AbstractDWRAction;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemVariation;
import com.ivant.cms.entity.Variation;
import com.ivant.cms.enums.UserType;


public class DWRVariationAction extends AbstractDWRAction {

	private static final Logger logger = Logger.getLogger(DWRVariationAction.class);
	
	public void addVariation(long itemId, String name, String sku, float price, float weight) {
		if(company == null) {
			return;
		}
		
		CategoryItem item = categoryItemDelegate.find(itemId);
		
		if(item == null || !item.getCompany().equals(company)) {
			return;
		}
		
		ItemVariation iv = new ItemVariation();
		iv.setCompany(company);
		iv.setName(name);
		iv.setSku(sku);
		iv.setPrice(price);
		iv.setWeight(weight);
		iv.setCategoryItem(item);
		
		itemVariationDelegate.insert(iv);
	}
	
	public List<DWRItemVariation> findAllVariations(long itemId) {
		if(company != null) {
			CategoryItem item = categoryItemDelegate.find(itemId);
			
			if(item != null && item.getCompany().equals(company)) {
				List<ItemVariation>itemVariations = itemVariationDelegate.findAll(company, item, Order.asc("name"));
				List<DWRItemVariation> dwrItemVariations = new ArrayList<DWRItemVariation>();
				
				for(ItemVariation iv : itemVariations) {
					dwrItemVariations.add(new DWRItemVariation(iv.getId(),
							iv.getName(), iv.getSku(), iv.getPrice(), iv.getWeight()));	
				}

				return dwrItemVariations;				
			}
			
		}
		
		return null;
	}
	
	public void deleteVariation(long variationId) {
		if(variationId > 0 && company != null ) {
		
			ItemVariation itemVariation = itemVariationDelegate.find(variationId);
			if(itemVariation != null && itemVariation.getCompany().equals(company)) {
				itemVariationDelegate.delete(itemVariation);
			}
			
		}
	}
	
	public DWRItemVariation find(long variationId) {
		if(variationId > 0 && company != null ) {
			ItemVariation itemVariation = itemVariationDelegate.find(variationId);
			if(itemVariation != null && itemVariation.getCompany().equals(company)) {
				return new DWRItemVariation(itemVariation.getId(), 
						itemVariation.getName(), itemVariation.getSku(),
						itemVariation.getPrice(), itemVariation.getPrice());
			}
		}
		
		return null;
	}
	 
	public void update(long variationId, String name, String sku, float price, float weight) {
		if(company == null) {
			return;
		}
		
		ItemVariation itemVariation = itemVariationDelegate.find(variationId);
		
		if(itemVariation != null && itemVariation.getCompany().equals(company)) {
			itemVariation.setName(name);
			itemVariation.setSku(sku);
			itemVariation.setPrice(price);
			itemVariation.setWeight(weight);
			
			itemVariationDelegate.update(itemVariation);
		}
	}
}
