package com.ivant.cart.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.enums.OrderStatus;

/**
 * 
 * Receiver of the PayDollar Transaction
 *
 */
public class HBCPaymentOutput extends AbstractBaseAction{
	
	private Logger logger = LoggerFactory.getLogger(HBCPaymentOutput.class);
	private static final long serialVersionUID = 1L;
	
	/** The status of the transaction 0-success, 1-failure */
	String successCode;
	
	/** Payment Reference Number */
	String payRef;
	
	/**Merchant�s Order Reference Number */
	String Ref;	

	/** Order by the user */
	private CartOrder cartOrder;
	
	/** Object responsible for order CRUD tasks */
	private CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	
	/** Object responsible for ordered items CRUD tasks */
	private CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	
	/** List of ordered items, can be 0 or more */
	private List<CartOrderItem> orderItemList;
	
	@Override
	public void prepare() throws Exception {
		
		// Print out 'OK' to notify us you have received the payment result
		Ref = request.getParameter("Ref");
		//System.out.print("OK");
		initHttpServerUrl();
	}
	
	@Override
	public String execute() throws Exception {
		Long cartOrderID;
			logger.debug("Transaction Accepted."); // Transaction Accepted
			this.setNotificationMessage("Your transaction has been accepted. Thank you.");
			
			try {
				cartOrderID = Long.parseLong(Ref);
				
				cartOrder = cartOrderDelegate.find(cartOrderID);
				//System.out.println("Status: "+cartOrder.getStatus());
				if(cartOrder.getStatus() == OrderStatus.NEW || cartOrder.getStatus() == OrderStatus.CANCELLED)
					cartOrder.setStatus(OrderStatus.PENDING);
				
				for(CartOrderItem x : cartOrder.getItems()) x.setStatus("OK");
				cartOrderDelegate.update(cartOrder);
			} catch (Exception e) {
				this.setNotificationMessage("Your transaction cannot be completed.");
				logger.debug("No cart order id specified");
			}
		
		
		return SUCCESS;
	}
	
	public String getNotificationMessage() {
		return notificationMessage;
	}

	public String getRef() {
		return Ref;
	}

	public void setRef(String ref) {
		Ref = ref;
	}

	
}
