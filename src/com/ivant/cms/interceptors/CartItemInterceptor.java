package com.ivant.cms.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.action.order.CartAction;
import com.ivant.cms.entity.ItemVariation;
import com.ivant.cms.order.Cart;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CartItemInterceptor extends AbstractInterceptor {

	private static final Logger logger = Logger.getLogger(CartItemInterceptor.class);
	
	public static final String CART_ITEMS = "cartItems";
	public static final String CART_ITEM_QUANTITIES = "cartItemQuantities";
	public static final String CART_ITEM_TOTAL_AMOUNT = "cartItemTotalAmount";
	public static final String CART_TOTAL_AMOUNT = "totalAmount";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2132384534066143310L;

	public String intercept(ActionInvocation arg0) throws Exception {
		logger.debug("intercept was invoked...");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		Cart cart = (Cart) session.getAttribute(CartAction.CART);
		
		ItemVariation[] items = null;
		if(cart != null) {
			logger.debug("we have cart in session...get all items...");
			items = cart.getItems();
			request.setAttribute(CART_ITEMS, items);
		}
		
		boolean hasItems = false;
		
		Map<String, Integer> cartItemQuantities = null;
		Map<String, Double> cartItemTotalAmount = null;
		if(items != null && items.length > 0) {
			logger.debug("we have items in cart...check for quantity");
			cartItemQuantities = new HashMap<String, Integer>();
			cartItemTotalAmount = new HashMap<String, Double>();
			
			for (ItemVariation item : items) {
				int quantity = cart.getQuantity(item);
				cartItemQuantities.put(item.getSku(), quantity);
				
				double amount = cart.getTotalPrice(item);
				cartItemTotalAmount.put(item.getSku(), amount);
				
				hasItems = true;
			}
		}
		
		if(hasItems) {
			logger.debug("get total amount so far...");
			request.setAttribute(CART_ITEM_QUANTITIES, cartItemQuantities);
			request.setAttribute(CART_ITEM_TOTAL_AMOUNT, cartItemTotalAmount);
			request.setAttribute(CART_TOTAL_AMOUNT, cart.getTotalPrice());
		}
		
		return arg0.invoke();
	}
}
