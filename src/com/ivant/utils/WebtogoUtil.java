package com.ivant.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;

/**
 * @author Eric John Apondar
 * @since January 2016
 */
public class WebtogoUtil {
	private static CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private static SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	
	public static Map<String, SinglePage> multiPageItemMap(MultiPage multiPage){
		Map<String, SinglePage> map = new HashMap<String, SinglePage>();
		if(multiPage != null){
			if(multiPage.getItems() != null){
				for(SinglePage i : multiPage.getItems()){
					map.put(i.getName(), i);
				}
			}
		}
		return map;
	}
	
	public static Map<String, Object> websiteSearch(Company company, String keyword){
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<SinglePage> singlePages = singlePageDelegate.findByKeyword(company, null, keyword).getList();
		List<CategoryItem> categoryItems = categoryItemDelegate.findByNameContainsOrSearchTagsContains(company, keyword).getList();
		
		map.put("singlePages", singlePages);
		map.put("categoryItems", categoryItems);
		
		return map;
	}
}  
