package com.ivant.cms.action.order.hbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cart.action.HBCPaymentInput;
import com.ivant.cart.action.OrderAction;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.OrderStatus;
import com.opensymphony.xwork2.Action;

public class YesPaymentsAction extends AbstractBaseAction{

	private Logger logger = LoggerFactory.getLogger(OrderAction.class);
	private static final long serialVersionUID = -7353617770904058249L;
	
	/** Object responsible for shopping cart CRUD tasks */
	private ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	
	/** Object responsible for shopping cart items CRUD tasks */
	private ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
	
	/** Object responsible for order CRUD tasks */
	private CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();

	/** Object responsible for ordered items CRUD tasks */
	private CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	
	/** Object responsible for member shipping info CRUD tasks */
	private MemberShippingInfoDelegate memberShippingInfoDelegate = MemberShippingInfoDelegate.getInstance();
	
	
	/** User's shopping cart for storing items bought */
	private ShoppingCart shoppingCart;
	
	/** Currently selected shopping cart item, can be null */
	@SuppressWarnings("unused")
	private ShoppingCartItem shoppingCartItem;
	
	/** Order by the user */
	private CartOrder cartOrder;
	
	/** Ordered item by the user */
	private CartOrderItem cartOrderItem;
	
	/** Shopping cart items, can be 0 or more */
	@SuppressWarnings("unused")
	private List<ShoppingCartItem> cartItemList;
	
	/** List of orders by the member of the company, can be 0 or more */
	private List<CartOrder> orderList;
	
	/** List of ordered items, can be 0 or more */
	private List<CartOrderItem> orderItemList;
	
	CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private ShoppingCartItemDelegate cartItemDelegate = ShoppingCartItemDelegate.getInstance();
	private ShoppingCartDelegate cartDelegate = ShoppingCartDelegate.getInstance();
	
	private HBCPaymentInput paymentInput = new HBCPaymentInput();
	//look-ahead count of orders
	private int orderCount;

	private String comments;
	
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private ArrayList<CategoryItem> catItem = new ArrayList<CategoryItem>();

	private long transID;
	private String merchRef;
	private String transTP;
	private String yesRespCd;
	private String transResult;
	private String cardId;
	private String cardNbrMasked;
	private String hashValue;
	private String errorMessage;
	
	@Override
	public void prepare() throws Exception {
		Long cartOrderID;
		//get member shopping cart information
		initShoppingCart();
		
		//load all orders by member
		initOrderList();
		
		try {
			if(isNull(request.getParameter("Ref")))
				cartOrderID = Long.parseLong(request.getParameter("cart_order_id"));
			else cartOrderID = Long.parseLong(request.getParameter("Ref"));
			
			cartOrder = cartOrderDelegate.find(cartOrderID); 
			this.setOrderCount(cartOrder.getId().intValue());
			if(!isNull(request.getParameter("Ref"))) {
				cartOrder.setStatus(OrderStatus.CANCELLED);
				for(CartOrderItem x : cartOrder.getItems()) x.setStatus("CANCELLED");
				cartOrderDelegate.update(cartOrder);
			}
			logger.debug("Total Price : " + cartOrder.getTotalPrice());
			orderItemList = cartOrderItemDelegate.findAll(cartOrder).getList();
			
			if(request.getParameter("notificationMessage") != null)
			this.setNotificationMessage(request.getParameter("notificationMessage"));
		} catch (Exception e) {
			logger.debug("No cart order id specified");
		}
		
		loadAllRootCategories();
		getCartSize();
		
		//populate server URL to be redirected to
		initHttpServerUrl();
		
		paymentInput.setCancelUrl(httpServer + paymentInput.getCancelUrl());
		paymentInput.setFailUrl(httpServer + paymentInput.getFailUrl());
		paymentInput.setSuccessUrl(httpServer + paymentInput.getSuccessUrl());
		request.setAttribute("paymentInput", paymentInput);
	}
	
	 
	
	@Override
	public String execute() throws Exception {
		//System.out.println("in yes payments action execute method.");
		//validate current user, must not be empty
		if(isNull(member))
			return INPUT;
		
		return super.execute();
	}
	
	public String saveComments(){
		if(isNull(member)) return INPUT;
		this.comments = request.getParameter("comments").toString();
		
		////////////////////
		ObjectList<ShoppingCartItem> tempCartItems = cartItemDelegate
		.findAll(shoppingCart);
		cartItemList = tempCartItems.getList();
		for( ShoppingCartItem item : cartItemList)	
			catItem.add(categoryItemDelegate.find(item.getItemDetail().getRealID()));
			
		///////////////////
		
		return SUCCESS;
	}
	
	public String postResult(){
		//System.out.println("in yes payments action post result method...");
		long orderId = Long.parseLong(request.getParameter("Ref"));
		//System.out.println("REF"+request.getParameter("Ref"));
		//System.out.println("cart order status: "+cartOrder.getStatus());
		try{
			long temp = Long.parseLong(request.getParameter("successcode"));
			//System.out.print("OK");
			//System.out.println("successcode:"+request.getParameter("successcode"));
			//System.out.println("temp"+temp);
			CartOrder cartOrder = cartOrderDelegate.find(orderId);
			if( temp == 0){
				cartOrder.setStatus(OrderStatus.COMPLETED);
				//System.out.println("COMPLETED"+cartOrder.getStatus());
			}
			else {
				cartOrder.setStatus(OrderStatus.REJECTED);
				//System.out.println("REJECTED");
				cartOrder.setStatusNotes(request.getParameter("TransResult"));
			}
			System.out.println("Cart Updated? "+cartOrderDelegate.update(cartOrder));
			//System.out.println("Cart Order Status"+ cartOrder.getStatus());
			
		}catch(Exception e){
			//System.out.println("yes payments post result error");
			cartOrder.setStatus(OrderStatus.REJECTED);
			cartOrder.setStatusNotes(request.getParameter("TransResult"));
			e.printStackTrace();
		}
		//System.out.println("in yes payments action post result success.");
		return SUCCESS;
	}
	
	public String postBack(){
		long orderId = Long.parseLong(request.getParameter("Ref"));
		try{
			CartOrder cartOrder = cartOrderDelegate.find(orderId);
			long temp = Long.parseLong(request.getParameter("sucesscode"));
			cartOrder.setStatus(OrderStatus.PENDING);
			//System.out.println("STATUS - PENDING");
		}catch(Exception e){
			cartOrder.setStatus(OrderStatus.ERROR);
			cartOrder.setStatusNotes(request.getParameter("successcode"));
			e.printStackTrace();
		}finally{
			if(cartOrder != null)
				cartOrderDelegate.update(cartOrder);
		}
		return SUCCESS;
	}
	
	/**
	 * Returns {@code SUCCESS} if order was successfully added, {@code
	 * INPUT} if member is null, and {@code ERROR} if an error was encountered.
	 * 
	 * @return - {@code SUCCESS} if order was successfully added, {@code
	 * INPUT} if member is null, and {@code ERROR} if an error was encountered
	 * 
	 * @throws Exception -  indicates conditions that a reasonable application might want to catch
	 */
	public String addToOrder() throws Exception {
		
		// sets the look-ahead count of orders
		this.setOrderCount(Integer.parseInt(cartOrderDelegate.getOrderCount(company).toString()) + 1);
		
		//validate current user, must not be empty
		if(isNull(member))
			return INPUT;
		
		logger.debug("addToOrder : company  = " + company);
		
		try {
			//create new order
			createOrder();
			
			//create order item list
			initOrderItemList();
		} catch (Exception e) {
			
			addActionError("Error : " + e.toString());
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String addToOrderThenSubmit() throws Exception {
		
		// sets the look-ahead count of orders
		this.setOrderCount(Integer.parseInt(cartOrderDelegate.getOrderCount(company).toString()) + 1);
		
		//validate current user, must not be empty
		if(isNull(member))
			return INPUT;
		
		logger.debug("addToOrder : company  = " + company);
		
		try {
			//create new order
			createOrder();
			
			//create order item list
			initOrderItemList();
		} catch (Exception e) {
			
			addActionError("Error : " + e.toString());
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Converts shopping cart into a new order.
	 */
	private void createOrder() {
		cartOrder = new CartOrder();
		cartOrder.setMember(member);
		cartOrder.setStatus(OrderStatus.NEW);
		cartOrder.setCompany(company);
		cartOrder.setCreatedOn(new Date());
		cartOrder.setIsValid(true);
		//
		if(request.getParameter("comments") != null)
			cartOrder.setComments(request.getParameter("comments").toString());
		//set shipping info
		cartOrder.setShippingInfo(memberShippingInfoDelegate.find(company, member));
		
		// for storage of shipping address for each order
		ShippingInfo orderShippingInfo = memberShippingInfoDelegate.find(company, member).getShippingInfo();
		cartOrder.setTotalShippingPrice2( shoppingCart.getTotalShippingPrice());
		cartOrder.setAddress1(orderShippingInfo.getAddress1());
		cartOrder.setAddress2(orderShippingInfo.getAddress2());
		cartOrder.setCity(orderShippingInfo.getCity());
		cartOrder.setCountry(orderShippingInfo.getCountry());
		cartOrder.setEmailAddress(orderShippingInfo.getEmailAddress());
		cartOrder.setName(orderShippingInfo.getName());
		cartOrder.setPhoneNumber(orderShippingInfo.getPhoneNumber());
		cartOrder.setState(orderShippingInfo.getState());
		cartOrder.setZipCode(orderShippingInfo.getZipCode());
		
		cartOrder = cartOrderDelegate.find(cartOrderDelegate.insert(cartOrder));
		//System.out.println("cart order totalPrice: "+cartOrder.getTotalPrice());
		request.setAttribute("cartOrder", cartOrder);
	}

	/**
	 * Populates orders from the company.
	 */
	private void initOrderList() {
		try {
			orderList = cartOrderDelegate.findAll(company, member);
			logger.debug("Order List : " + orderList);
		} catch (Exception e) {
			logger.debug("No orders found.");
		}
	}
	
	/**
	 * Populates order items, added items are the items from the shopping cart.
	 */
	private void initOrderItemList() {
		List<CartOrderItem> items = new ArrayList<CartOrderItem>();
		//add shopping cart items to new order lists
		for(ShoppingCartItem currentCartItem : shoppingCart.getItems()){
			//convert cart item into cart order item
			cartOrderItem = new CartOrderItem();
			cartOrderItem.setOrder(cartOrder);
			cartOrderItem.setCompany(company);
			cartOrderItem.setCreatedOn(new Date());
			cartOrderItem.setIsValid(true);
			cartOrderItem.setItemDetail(currentCartItem.getItemDetail());
			cartOrderItem.setQuantity(currentCartItem.getQuantity());
			cartOrderItem.setStatus("OK");
			//update current cart item set it to invalid
			currentCartItem.setIsValid(false);
			currentCartItem.setUpdatedOn(new Date());
			shoppingCartItemDelegate.update(currentCartItem);
			items.add(cartOrderItemDelegate.find(cartOrderItemDelegate.insert(cartOrderItem)));
		}
		cartOrder.setItems(items);
		cartOrderDelegate.update(cartOrder);
	}
	
	/**
	 * Returns shopping cart based on current session parameters.
	 */
	private void initShoppingCart() {
		shoppingCart  = shoppingCartDelegate.find(company, member);
		
		try {
			logger.debug("Current shopping cart : " + shoppingCart);
			logger.debug("Current shopping cart items : " + shoppingCart.getItems());
			logger.debug("Current shopping cart total : " + shoppingCart.getTotalPrice());
		} catch (Exception e) {
			logger.debug("Shopping cart is currently empty.");
		}
		
		//get cart items
		initCartItems();
	}
	
	/**
	 * Populates shopping cart with cart items.
	 */
	private void initCartItems() {
		try {
			ObjectList<ShoppingCartItem> tempCartItems = shoppingCartItemDelegate.findAll(shoppingCart);
			cartItemList = tempCartItems.getList();
		} catch (Exception e) {
			logger.debug("No cart items retrieved.");
		}
	}

	/**
	 * Returns orderList property value.
	 * 
	 * @return - orderList property value
	 */
	public List<CartOrder> getOrderList() {
		return orderList;
	}

	/**
	 * Returns orderItemList property value.
	 * 
	 * @return - orderItemList property value
	 */
	public List<CartOrderItem> getOrderItemList() {
		return orderItemList;
	}

	/**
	 * Returns cartOrder property value.
	 * 
	 * @return - cartOrder property value
	 */
	public CartOrder getCartOrder() {
		return cartOrder;
	}
	
	public String edit() {
		return Action.SUCCESS;
	}
	
	public void getCartSize() {
		ObjectList<ShoppingCartItem> tempCartItems;
		try {
			shoppingCart = cartDelegate.find(company, member);
			tempCartItems = cartItemDelegate.findAll(shoppingCart);
			int cartSize = tempCartItems.getList().size();
			request.setAttribute("cartSize", cartSize);
		} catch (Exception e) {
			shoppingCart = null;
			logger.debug("Shopping cart is currently empty.");
		}
		
	}
	
	private void loadAllRootCategories()
	{
		Order[] orders = {Order.asc("sortOrder")};
		List<Category> rootCategories = categoryDelegate.findAllRootCategories(company,true,orders).getList();
		request.setAttribute("rootCategories", rootCategories);		
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}


/*
 * Getter-setters for shopping cart, order count and comments.
 */
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ArrayList<CategoryItem> getCatItem() {
		return catItem;
	}

	public void setCatItem(ArrayList<CategoryItem> catItem) {
		this.catItem = catItem;
	}

	public long getTransID() {
		return transID;
	}

	public void setTransID(long transID) {
		this.transID = transID;
	}

	public String getMerchRef() {
		return merchRef;
	}

	public void setMerchRef(String merchRef) {
		this.merchRef = merchRef;
	}

	public String getTransTP() {
		return transTP;
	}

	public void setTransTP(String transTP) {
		this.transTP = transTP;
	}

	public String getYesRespCd() {
		return yesRespCd;
	}

	public void setYesRespCd(String yesRespCd) {
		this.yesRespCd = yesRespCd;
	}

	public String getTransResult() {
		return transResult;
	}

	public void setTransResult(String transResult) {
		this.transResult = transResult;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardNbrMasked() {
		return cardNbrMasked;
	}

	public void setCardNbrMasked(String cardNbrMasked) {
		this.cardNbrMasked = cardNbrMasked;
	}

	public String getHashValue() {
		return hashValue;
	}

	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}



	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
