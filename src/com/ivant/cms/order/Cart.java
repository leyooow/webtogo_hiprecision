/**
 * 
 */
package com.ivant.cms.order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.ItemVariationDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemVariation;

/**
 * 
 * @author acer
 * 
 * @since Dec 5, 2007
 */
public class Cart implements Serializable {
	
	private static final Logger logger = Logger.getLogger(Cart.class);
	private static final boolean IS_DEBUG = logger.isDebugEnabled();
	/**
	 * 
	 */
	private static final long serialVersionUID = 8331835082802512656L;
	private static final ItemVariationDelegate itemVariationDelegate = ItemVariationDelegate.getInstance();
	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();

	/**
	 * mapping of sku and quantity
	 */
	private Map<String, Integer> cartItemQuantityMap;
	private Long companyID; 
	
	public Cart(Company company) {
		
		if(company == null) {
			throw new IllegalArgumentException("company should be null");
		}
		
		cartItemQuantityMap = new HashMap<String, Integer>();
		companyID = company.getId();
	}

	/**
	 * returns all the items that was added in this shopping cart
	 * @return
	 */
	public ItemVariation[] getItems() {
		logger.debug("get items was invoked...");
		
		ItemVariation[] itemSKUArr = null;
		
		Set<String> itemSKUSet = cartItemQuantityMap.keySet();
		
		if(itemSKUSet != null) {
			logger.debug("we have item in the cart...");
			Company company = companyDelegate.find(companyID);
			
			List<ItemVariation> itemList = new LinkedList<ItemVariation>();
			for (String catItemSKU : itemSKUSet) {
				if(IS_DEBUG) logger.debug("retrieve item: " + catItemSKU);
				
				ItemVariation itemVariation = itemVariationDelegate.findBySKU(company, catItemSKU);
				int q = getQuantity(itemVariation);
				
				if(IS_DEBUG) logger.debug("quantity: " + q);
				
				if(itemVariation != null && q > 0) {
					logger.debug("added in list");
					itemList.add(itemVariation);
				}
			}
			
			if(!itemList.isEmpty()) {
				logger.debug("item list is not empty");
				itemSKUArr = new ItemVariation[itemList.size()];
				itemList.toArray(itemSKUArr);
			}
		}
		
		logger.debug("return getItems");
		
		return itemSKUArr;
	}
	
	/**
	 * returns the overall amount of the cart
	 * @return
	 */
	public double getTotalPrice() {
		
		double totalPrice = 0D;
		
		ItemVariation[] items = getItems();
		for (ItemVariation item : items) {
			totalPrice += getTotalPrice(item);
		}
		
		return totalPrice;
	}
	
	/**
	 * returns the total amount of a certain item in the cart
	 * @param item
	 * @return
	 */
	public double getTotalPrice(ItemVariation item) {
		if(item == null) {
			throw new IllegalArgumentException("item is null");
		}
		
		double totalPrice = item.getPrice() * getQuantity(item);
		
		return totalPrice;
	}
	
	/**
	 * returns the quantity of a particular item in the cart
	 * @param item
	 * @return
	 */
	public int getQuantity(ItemVariation item) {
		if(item == null) {
			throw new IllegalArgumentException("item is null");
		}
		
		int quantity = 0;
		final String sku = item.getSku();
		if(cartItemQuantityMap.containsKey(sku)) {
			quantity = cartItemQuantityMap.get(sku).intValue();
		}
		
		if(quantity < 0) {
			quantity = 0;
		}
		
		return quantity;
	}
	
	/**
	 * set the quantity of a product in the cart. 
	 * @param item
	 * @param quantity
	 */
	public void setQuantity(ItemVariation item, int quantity) {
		if(item == null) {
			throw new IllegalArgumentException("item is null");
		}
		
		cartItemQuantityMap.put(item.getSku(), quantity);
	}
	
	/**
	 * checks if there are any item in the cart
	 * @return
	 */
	public boolean hasItem() {
		ItemVariation[] items = getItems();
		
		return items != null && items.length > 0;
	}
}