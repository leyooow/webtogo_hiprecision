package com.ivant.cms.action.order;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.ItemVariationDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemVariation;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.order.Cart;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class CartAction extends OrderAction implements CompanyAware, Preparable {
	private static final Logger log = Logger.getLogger(CartAction.class);
	private static final boolean IS_DEBUG = log.isDebugEnabled();
	
	public static final String CART = "session_cart";

	private Map<String, Integer> quantityMap;
	
	@SuppressWarnings("unchecked")
	private Map session;
	
	private Company company;
	
	private ItemVariationDelegate itemVariationDelegate = ItemVariationDelegate.getInstance();
	
	private Cart cart;
	
//	public String viewCart() throws Exception {
//		log.debug("viewCart was invoked...");
//		
//		CategoryItem[] categoryItems = null;
//		
//		if(cart.hasItem()) {
//			quantityMap = new HashMap<String, Integer>();
//			categoryItems = cart.getCategoryItems();
//		}
//		
//		if(IS_DEBUG) log.debug(categoryItems);
//		
//		if(categoryItems != null) {
//			for (CategoryItem categoryItem : categoryItems) {
//				if(IS_DEBUG) log.debug("adding item to map: " + categoryItem);
//				quantityMap.put(categoryItem.getSku(), cart.getQuantity(categoryItem));
//			}
//		}
//		
//		log.debug("viewCart return success!");
//		
//		return Action.SUCCESS;
//	}
	
	@SuppressWarnings("unchecked")
	public String upcateCart() throws Exception {
		log.debug("updateCart was invoked...");
		
		Set<String> skuSet = quantityMap.keySet();
		for (String sku : skuSet) {
			if(IS_DEBUG) log.debug("fetch item with sku: " + sku);
			ItemVariation itemVariation = itemVariationDelegate.findBySKU(company, sku);
			
			if(IS_DEBUG) log.debug("category item: " + itemVariation);

			Integer quantity = quantityMap.get(sku);
			if(IS_DEBUG) log.debug("quantity: " + quantity);
			
			if(itemVariation != null && quantity != null) {
				cart.setQuantity(itemVariation, quantity < 0 ? 0 : quantity);
			}
		}

		log.debug("updateCart returning success.");
		
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map arg0) {
		session = arg0;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setQuantityMap(Map<String, Integer> quantityMap) {
		this.quantityMap = quantityMap;
	}

	@SuppressWarnings("unchecked")
	public void prepare() throws Exception {
		log.debug("prepare was invoked...");
		
		cart = (Cart) session.get(CART);
		if(cart == null) {
			log.debug("no cart found. creating a new one");
			cart = new Cart(company);
			session.put(CART, cart);
		}
	}

	public Map<String, Integer> getQuantityMap() {
		return quantityMap;
	}
}