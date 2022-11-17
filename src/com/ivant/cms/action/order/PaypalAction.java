package com.ivant.cms.action.order;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.OrderDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemVariation;
import com.ivant.cms.entity.Order;
import com.ivant.cms.entity.OrderItem;
import com.ivant.cms.entity.ProductItem;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.order.Cart;

public class PaypalAction extends OrderAction implements CompanyAware {
	private static final Logger log = Logger.getLogger(PaypalAction.class);
//	private static final boolean IS_DEBUG = log.isDebugEnabled();	
	
	private static final String PAYPAL_TOKEN_ID = "paypaltokenid";
	
	private Company company;
	
	private OrderDelegate orderDelegate = OrderDelegate.getInstance();
	
	private String paypalTokenID;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String setExpressCheckoutRequest() throws Exception {
		String retval = VIEW_CART;
		
		Cart cart = (Cart)session.get(CartAction.CART);
		
		if(cart != null && cart.hasItem()) {
			log.debug("cart is existing and item was found in the cart....");
			log.debug("proceed to shipping form");
			
			//TODO setExpressCheckoutRequest
			
			session.put(PAYPAL_TOKEN_ID, "ABCDE123456");
			retval = PAYMENT;
		}
		
		return retval;
	}
	
	public String getExpressCheckoutDetailsRequest() throws Exception {
		String retval = VIEW_CART;
		
		Cart cart = (Cart)session.get(CartAction.CART);
		
		if(cart != null && cart.hasItem()) {
			log.debug("cart is existing and item was found in the cart....");
			log.debug("proceed to shipping form");
			
			retval = PAYMENT;
		}
		
		if(retval.equals(PAYMENT) && 
			paypalTokenID != null && 
			paypalTokenID.trim().length() > 0 &&
			paypalTokenID.trim().equals(session.get(PAYPAL_TOKEN_ID))) {
			
			//TODO getExpressCheckoutDetailsRequest
			retval = REVIEW_ORDER;
		}
		
		return retval;
	}
	
	public String doExpressCheckoutPaymentRequest() throws Exception {
		String retval = VIEW_CART;
		
		Cart cart = (Cart)session.get(CartAction.CART);
		
		if(cart != null && cart.hasItem()) {
			retval = PAYMENT;
		}
		
		paypalTokenID = (String)session.get(PAYPAL_TOKEN_ID);
		if(retval.equals(PAYMENT) && 
				paypalTokenID != null && 
				paypalTokenID.trim().length() > 0) {
			//TODO getExpressCheckoutDetailsRequest
			retval = REVIEW_ORDER;
		}
		
		if(retval.equals(REVIEW_ORDER)) {
			//TODO doExpressCheckoutPaymentRequest
			
			Order order = new Order();
			
			ShippingInfo shippingInfo = new ShippingInfo();
			//TODO set shipping data here from paypal
			order.setShippingInfo(shippingInfo);
			order.setCompany(company);
			
			ItemVariation[] categoryItemArr = cart.getItems();
			
			List<OrderItem> orderItemList = new LinkedList<OrderItem>();
			for (ItemVariation categoryItem : categoryItemArr) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				
				ProductItem productItem = new ProductItem();
				productItem.setId(categoryItem.getId());
				productItem.setName(categoryItem.getName());
				productItem.setPrice(categoryItem.getPrice());
				productItem.setSku(categoryItem.getSku());
				
				orderItem.setProductItem(productItem);
				orderItem.setQuantity(cart.getQuantity(categoryItem));
				
				orderItemList.add(orderItem);
			}
			
			order.setOrderItemList(orderItemList);
			
			orderDelegate.insert(order);
			
			retval = SUCCESS;
		}
		
		
		if(retval.equals(SUCCESS)) {
			session.remove(PAYPAL_TOKEN_ID);
			session.remove(CartAction.CART);
		}
		
		return retval;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setPaypalTokenID(String paypalTokenID) {
		this.paypalTokenID = paypalTokenID;
	}
}
