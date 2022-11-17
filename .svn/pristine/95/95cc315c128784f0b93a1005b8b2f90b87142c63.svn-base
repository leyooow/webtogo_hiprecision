package com.ivant.utils;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;

public class InventoryUtil {

	/*
	 * for inventory purposes
	 */
	public static Boolean updateInventory(Company company, CartOrder cartOrder,CategoryItemDelegate categoryItemDelegate, Boolean successful) {
		
		for(CartOrderItem coi : cartOrder.getItems()){
			 CategoryItem ci  = categoryItemDelegate.find(coi.getItemDetail().getRealID());
			 
			 if(ci.getAvailableQuantity()!=null && coi.getQuantity()!= null){
				 
				 if(successful)
				 		 ci.setAvailableQuantity((ci.getAvailableQuantity() - coi.getQuantity()));
				 else
					 	 ci.setAvailableQuantity((ci.getAvailableQuantity() + coi.getQuantity()));
			 }
			 
			 categoryItemDelegate.update(ci);
			 	
		}
		
		return Boolean.TRUE;
		
	}



	//for Checkout
	public static Boolean updateInventory(Company company,ShoppingCart shoppingCart, CategoryItemDelegate categoryItemDelegate, Boolean successful) {
		
		for(ShoppingCartItem sc : shoppingCart.getItems()){
			
			CategoryItem ci  = categoryItemDelegate.find(sc.getItemDetail().getRealID());
			
			//System.out.println("SC ID - >"+sc.getId());
			//System.out.println("ci.getAvailableQuantity() - >" + ci.getAvailableQuantity());
			//System.out.println("sc.getQuantity() - >" + sc.getQuantity());
			 
			 if(ci.getAvailableQuantity()!=null && sc.getQuantity()!= null){
				 
				 if(successful)
				 		 ci.setAvailableQuantity((ci.getAvailableQuantity() - sc.getQuantity()));
				 else
					 	 ci.setAvailableQuantity((ci.getAvailableQuantity() + sc.getQuantity()));
			 }
			 
			 Boolean updated = categoryItemDelegate.update(ci);
			 
			 //System.out.println("Category Item was successfully updated ? "+ updated);
			 	
		}
		
		return Boolean.TRUE;
	}
	
	

}
