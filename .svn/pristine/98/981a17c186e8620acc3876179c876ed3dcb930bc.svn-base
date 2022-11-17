package com.ivant.cms.action.dwr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.ivant.cms.action.admin.dwr.AbstractDWRAction;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Group;

public class DWRCategoryItemAction extends AbstractDWRAction {
	private static final Logger logger = Logger.getLogger(DWRCategoryItemAction.class);
	
	public List<CategoryItem> loadGurkkaProductItem(String keyWord){
		logger.info("DWRCategoryItemAction.loadGurkkaProductItem..........");
		Order[] orders = {Order.asc("name")};
		List<CategoryItem> listGurkkaProductItem = new ArrayList<CategoryItem>();
		Group group = new Group();
		//group = groupDelegate.find(company, GurkkaConstants.PRODUCTS_GROUP_NAME);
		
		if(group != null){
			listGurkkaProductItem = categoryItemDelegate.findAllByKeyWordName(company, group, keyWord, MatchMode.START, false, orders);
		}
		logger.info("Retrieve size : "+ listGurkkaProductItem.size());
		return listGurkkaProductItem;
	}
	
	public List<CategoryItem> loadGurkkaPromoBasketItem(String keyWord){
		logger.info("DWRCategoryItemAction.loadGurkkaPromoBasketItem..........");
		Order[] orders = {Order.asc("name")};
		List<CategoryItem> listGurkkaPromoBasketItem = new ArrayList<CategoryItem>();
		Group group = new Group();
		//group = groupDelegate.find(company, GurkkaConstants.PROMOBASKET);
		if(group != null){
			listGurkkaPromoBasketItem = categoryItemDelegate.findAllByKeyWordName(company, group, keyWord, MatchMode.START, false, orders);
		}
		logger.info("Retrieve size : "+ listGurkkaPromoBasketItem.size());
		return listGurkkaPromoBasketItem;
	}
	
	public List<CategoryItem> loadGurkkaCocktailsItem(String keyWord) {
		logger.info("DWRCategoryItemAction.loadGurkkaCocktailsItem..........");
		Order[] orders = {Order.asc("name")};
		List<CategoryItem> listGurkkaCocktailsItem = new ArrayList<CategoryItem>();
		Group group = new Group();
		//group = groupDelegate.find(company, GurkkaConstants.COCKTAILS_GROUP_NAME);
		if(group != null){
			listGurkkaCocktailsItem = categoryItemDelegate.findAllByKeyWordName(company, group, keyWord, MatchMode.START, false, orders);
		}
		logger.info("Retrieve size : "+ listGurkkaCocktailsItem.size());
		return listGurkkaCocktailsItem;
	}
	
	public List<CategoryItem> loadGurkkaVideosItem(String keyWord) {
		logger.info("DWRCategoryItemAction.loadGurkkaVideosItem..........");
		Order[] orders = {Order.asc("name")};
		List<CategoryItem> listGurkkaVideosItem = new ArrayList<CategoryItem>();
		Group group = new Group();
		//group = groupDelegate.find(company, GurkkaConstants.VIDEOS_GROUP_NAME);
		if(group != null){
			listGurkkaVideosItem = categoryItemDelegate.findAllByKeyWordName(company, group, keyWord, MatchMode.START, false, orders);
		}
		logger.info("Retrieve size : "+ listGurkkaVideosItem.size());
		return listGurkkaVideosItem;
	}
}
