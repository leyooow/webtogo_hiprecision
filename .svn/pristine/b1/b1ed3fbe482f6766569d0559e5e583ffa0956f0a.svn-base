package com.ivant.cms.action.company.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cart.action.PaypalAction;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.ShippingStatus;
import com.ivant.cms.enums.ShippingType;

public class PureNectarJSONAction extends AbstractBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1240438909766399395L;
	
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	private final CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	private final MemberShippingInfoDelegate memberShippingInfoDelegate = MemberShippingInfoDelegate.getInstance();
	private final PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	private final ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	private final ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
	
	private ShoppingCart shoppingCart;
	private List<ShoppingCartItem> cartItemList;
	
	private InputStream inputStream;
	
	private static final NumberFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String placeOrder() {
		
		String address1 = request.getParameter("address-1");
		String address2 = request.getParameter("address-2");
		String deliveryAddress = request.getParameter("delivery-address");
		
		String mobileNumber = request.getParameter("mobile-number");
		String instructions = request.getParameter("instructions");
		
		String scheduleDelivery = request.getParameter("schedule-delivery");
		
		String paymentMethod = request.getParameter("payment-method");
		String receivedMoney = request.getParameter("received-money");
		
		shoppingCart = shoppingCartDelegate.find(company, member);
		
		cartItemList = shoppingCartItemDelegate.findAllByCartAndCompany(shoppingCart, company).getList();

		ShippingType shippingType = ShippingType.DELIVERY;
		PaymentType paymentType = PaymentType.PAYPAL;
		ShippingStatus shippingStatus = ShippingStatus.PENDING;
		
		CartOrder cartOrder = new CartOrder();
		cartOrder.setAddress1(deliveryAddress);//DELIVERY ADDRESS
		cartOrder.setBillingAddress1(address1);//BILLING ADDRESS
		cartOrder.setShippingType(shippingType);
		cartOrder.setStatus(OrderStatus.PENDING);
		cartOrder.setPaymentStatus(PaymentStatus.PENDING);
		cartOrder.setPaymentType(paymentType);
		cartOrder.setInfo1(paymentMethod);
		
		cartOrder.setShippingStatus(shippingStatus);
		cartOrder.setCompany(company);
		cartOrder.setMember(member);
		cartOrder.setEmailAddress(member.getEmail());
		cartOrder.setName(member.getFirstname() + " " +member.getLastname());
		
		MemberShippingInfo memberShippingInfo = new MemberShippingInfo();
		memberShippingInfo.setCompany(company);
		memberShippingInfo.setMember(member);
		ShippingInfo shippingInfo = new ShippingInfo();
		shippingInfo.setAddress1(deliveryAddress);
		memberShippingInfo.setShippingInfo(shippingInfo);
		cartOrder.setShippingInfo(memberShippingInfo);
		
		List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
		List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();
		
		Double shippingPrice = 150.00;
		Double totalPrice = 0.00;
		
		for(ShoppingCartItem shoppingCartItem : cartItemList) {
			CartOrderItem cartOrderItem = new CartOrderItem();
			CategoryItem categoryItem = shoppingCartItem.getCategoryItem();
			
			ItemDetail itemDetail = new ItemDetail();
			itemDetail.setName(categoryItem.getName());
			itemDetail.setPrice(categoryItem.getPrice());
			Double itemTotalPrice = categoryItem.getPrice() * shoppingCartItem.getQuantity();
			itemDetail.setDescription(categoryItem.getDescription());
			itemDetail.setDiscount(new Double(0.0));
			itemDetail.setDiscountedPrice(itemTotalPrice);
			itemDetail.setRealID(categoryItem.getId());
			
			cartOrderItem.setCompany(company);
			cartOrderItem.setStatus("OK");
			cartOrderItem.setQuantity(shoppingCartItem.getQuantity());
			cartOrderItem.setItemDetail(itemDetail);
			cartOrderItem.setCategoryItem(categoryItem);
			
			categoryItem.setOtherDetails(shoppingCartItem.getQuantity().toString());
			
			categoryItems.add(categoryItem);
			cartOrderItems.add(cartOrderItem);
			
			totalPrice += itemTotalPrice;
			
		}
		
		categoryItems.add(addShippingFee(DEFAULT_DECIMAL_FORMAT.format(150.00), categoryItems));
		
		totalPrice += shippingPrice;
		
		
		cartOrder.setItems(cartOrderItems);
		cartOrder.setTotalPrice(totalPrice);
		cartOrder.setTotalShippingPrice(shippingPrice);
		
		request.getSession().setAttribute("cartOrder", cartOrder);
		
		String result = "";
		String pToken = "";
		String checkoutUrl = "";
		
		//PAYPAL PAYMENT
		PaypalAction paypalAction = new PaypalAction(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live",
				company.getPalCurrencyType(), company.getPalSuccessUrl() + "?memberId=" + member.getId(), company.getPalCancelUrl());
		
		Double tp = 0.00;
		
		for(CategoryItem catItem : categoryItems){
			logger.info("--------------------------Category Item Name: " + catItem.getName());
			tp += catItem.getPrice();
		}
		
		logger.info("-----------------------------Total Price of Category Items: PHP " + tp);
		logger.info("------------------------------Total Price With Shipping Fee: PHP " + totalPrice);
		
		paypalAction.setCategoryItems(categoryItems);
		
		result = paypalAction.setExpressCheckoutRequest(DEFAULT_DECIMAL_FORMAT.format(totalPrice), DEFAULT_DECIMAL_FORMAT.format(0.00));
		
		pToken = paypalAction.getToken();
		checkoutUrl = paypalAction.getPaypalUrl();
		
		JSONObject obj = new JSONObject();
		if(result.equalsIgnoreCase("Failure"))
		{
			obj.put("success", false);
			obj.put("message", "Error PayPal");
		} else {
			obj.put("success", true);
			obj.put("pToken", pToken);
			obj.put("checkoutUrl", checkoutUrl);
		}
		
		request.getSession().setAttribute("cartOrder", cartOrder);
		setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		return SUCCESS;
	}
	
	public CategoryItem addShippingFee(String shippingFee, List<CategoryItem> cartItems) {

		CategoryItem catItem = new CategoryItem();
		catItem.setId(0L);
		catItem.setName("Shipping Fee");
		catItem.setPrice(Double.parseDouble(shippingFee));
		catItem.setOtherDetails("1");
		/*catItem.setDescription(request.getParameter("o1043"));*/
		catItem.setDescription("Shipping Fee");
		
		int itemSessionId = 1;
		if(cartItems != null)
		{
			itemSessionId += cartItems.size();
			
			catItem.setSku("" + itemSessionId);
		}
		
		logger.info("------------------------Shipping Fee: PHP " + catItem.getPrice());
		
		return catItem;
	
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
