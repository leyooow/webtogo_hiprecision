package com.ivant.cms.action.company;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;

import com.ivant.cart.action.PaypalAction;
import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.action.PageDispatcherAction;
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
import com.ivant.cms.interfaces.PageDispatcherAware;

public class PureNectarDispatcherAction extends PageDispatcherAction implements PageDispatcherAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7657404748745017266L;
	
	private String successUrl;
	private String errorUrl;
	
	private static final NumberFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	
	@Override
	public void prepare() throws Exception
	{
		super.prepare();
	}
	
	@Override
	public String execute() throws Exception
	{
		final String result = super.execute();
		
		return result;
	}
	
	public String checkOut() {
		
		if(request.getParameter("quantity") != null) {
			cartItemList = shoppingCartItemDelegate.findAllByCartAndCompany(shoppingCart, company).getList();
			String[] quantity = request.getParameterValues("quantity");
			int index = 0;
			for(ShoppingCartItem shoppingCartItem : cartItemList) {
				shoppingCartItem.setQuantity(new Integer(quantity[index]));
				shoppingCartItemDelegate.update(shoppingCartItem);
				index++;
			}
		}
		
		initCartItems();
		getCartSize();
		String promoCode = "";
		Double discountPrice = 0.00;
		if(request.getParameter("promo-code") != null) {
			promoCode = request.getParameter("promo-code");
			if(!promoCode.equals(""))
				discountPrice = 1.00;
		}
		request.setAttribute("discountPrice", discountPrice);
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String placeOrder() {
		
		String address1 = request.getParameter("address-1");
		String address2 = request.getParameter("address-2");
		String deliveryAddress = request.getParameter("delivery-address");
		
		String mobileNumber = request.getParameter("mobile-number");
		String instructions = request.getParameter("instructions");
		
		String scheduleDelivery = request.getParameter("schedule-delivery");
		
		String paymentMethod = request.getParameter("payment-method");
		String receivedMoney = request.getParameter("received-money");
		
		shoppingCart = cartDelegate.find(company, member);
		
		for(ShoppingCartItem item : shoppingCart.getItems()) {
			item.getItemDetail().setPrice(item.getCategoryItem().getPrice());
			shoppingCartItemDelegate.update(item);
		}
		
		cartItemList = shoppingCartItemDelegate.findAllByCartAndCompany(shoppingCart, company).getList();


		ShippingType shippingType = ShippingType.DELIVERY;
		PaymentType paymentType = PaymentType.BANK_DEPOSIT;
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
		cartOrder.setPhoneNumber(mobileNumber);
		cartOrder.setInfo1(scheduleDelivery);
		cartOrder.setInfo2(instructions);
		cartOrder.setInfo3(receivedMoney);
		
		MemberShippingInfo memberShippingInfo = new MemberShippingInfo();
		memberShippingInfo.setCompany(company);
		memberShippingInfo.setMember(member);
		memberShippingInfoDelegate.insert(memberShippingInfo);
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
		
		cartOrder.setItems(cartOrderItems);
		cartOrder.setTotalPrice(totalPrice);
		cartOrder.setTotalShippingPrice2(shippingPrice);
		
		member.setAddress1(address1);
		member.setAddress2(address2);
		member.setMobile(mobileNumber);
		
		memberDelegate.update(member);
		

		long id = cartOrderDelegate.insert(cartOrder);
		cartOrder = cartOrderDelegate.find(id);
		
		placeOrder(cartOrder, cartOrderItems, shippingPrice);
		sendOrderDetails(cartOrder, cartOrderItems, shippingPrice);
		
		

		for(CartOrderItem cartOrderItem : cartOrderItems)
			cartOrderItem.setOrder(cartOrder);
		cartOrderItemDelegate.batchInsert(cartOrderItems);
		/*sendConfirmationEmail(cartOrder, cartOrderItems, shippingPrice);*/
		
		for(ShoppingCartItem shoppingCartItem : cartItemList)
			shoppingCartItemDelegate.delete(shoppingCartItem);
		request.getSession().setAttribute("cartOrder", cartOrder);
	
		StringBuffer content = new StringBuffer();
		
		content.append("<center><img src='http://purenectar.co/images/logo-black-160px.png' /></center><br><br>");
		content.append("Greetings from Pure Nectar!<br><br>");
		content.append("If you have opted for Bank Deposit Payment Method, please see bank details below to settle total amount.<br><br>");

		content.append("Bank: Metrobank <br>");
		content.append("Account No. 307-7-307513109<br>");
		content.append("Account Name: Fruit Magic Co., Inc.<br><br>");
		
		content.append("Bank: BDO Unibank, Inc. <br>");
		content.append("Account No. 001240203096<br>");
		content.append("Account Name: Fruit Magic Co., Inc.<br><br>");
			
		content.append("Bank: Bank of the Philippines, Inc <br>");
		content.append("Account No. 0111-0142-99<br>");
		content.append("Account Name: Fruit Magic Co., Inc.<br><br>");

		content.append("<p>When deposit is completed, please e-mail scanned copy of your validated deposit slip to <a href='#'>melissa@fruitmagic.com.ph</a> and indicate your <b>Order Number</b>. Please email your proof of payment within the next 24 hours to avoid cancellation of your order. Package will be shipped the day before consumption and upon confirmation of payment.</p><br><br>");
		content.append("<p>If paid through Paypal, orders are processed as soon as we receive the confirmation.</p><br><br>");
		
		content.append("<p>All orders and inquiries sent during weekends will be processed the next working day. If you have any questions, feel free to contact us. Our office hours are Mondays - Fridays 8:00 AM - 5:00 AM.</p> <br><br>");
		content.append("Thank you very much!<br><br>");
		content.append("Pure Nectar Team");
		

		EmailSenderAction emailSenderAction = new EmailSenderAction();
		emailSenderAction.sendEmailConfirmationPureNectar(company, member.getEmail(), "Pure Nectar - Bank Deposit Instructions", content, null);

		return SUCCESS;
	}
	
	public void placeOrder(CartOrder cartOrder, List<CartOrderItem> cartOrderItems, Double shippingPrice) {
		
		
		Double totalPrice = cartOrder.getTotalPrice() + cartOrder.getTotalShippingPrice2();
		StringBuffer content = new StringBuffer();
		Date current = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);	
		
		content.append("<center><img src='http://purenectar.co/images/logo-black-160px.png' /></center><br><br>");
		content.append("Hi, " + member.getFirstname() + "<br/><br/>");
		
		content.append("Your order has been received.");
		content.append("Your order should be dispatched soon, so please review the details before and make sure your delivery address and items are correct.<br/><br/>");
		
		content.append("The summary of your order is: ")
			   .append("<ul>")
			   .append("<li>Order Date: " + df.format(current) + "</li>")
			   .append("<li>Order Number: "+ cartOrder.getId() + "</li>")
			   .append("<li>Order Total: Php " + totalPrice + "</li>")
			   .append("</ul><br><br>");
			   
		
		content.append("<b>Delivery Address: </b>" + request.getParameter("delivery-address"))
			    .append("<br><b>Customer Name:</b> " + member.getFirstname() + " " + member.getLastname())
			    .append("<br><b>Delivery Date:</b> " + cartOrder.getInfo1())
			    .append("<br><b>Email:</b> " + cartOrder.getEmailAddress())
			    .append("<br><b>Contact Number:</b> " + cartOrder.getPhoneNumber())
			    .append("<br><b>Special Instructions:</b> " + cartOrder.getInfo2());
			
		content.append("<br/><br/><b>ORDER DETAILS</b>");
		content.append("<br/><br/>");
		content.append("<table style='width:100%;' padding=5>");
		content.append("<tr>");
		content.append("<th colspan=2 style='background-color:gray;color:black;font-weight:bold;'>Items</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Qty</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Price</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Amount</th>");
		content.append("</tr>");
		
		
		for(CartOrderItem cartOrderItem : cartOrderItems) {
			content.append("<tr>");
			
			if(cartOrderItem.getCategoryItem().getImages().size() > 0){
				content.append("<td style='width:80px;'><img style='width:80px;height:80px;' src=\"http://"+company.getServerName()+"/images/items/"+cartOrderItem.getCategoryItem().getImages().get(0).getOriginal()+"\"> ");
			}else{
				content.append("<td>");
			}
			
			content.append("</td>");
			content.append("<td style-'text-align:right'><b>"+cartOrderItem.getCategoryItem().getName() + "</b></td>");
			content.append("<td style='text-align:right'>"+cartOrderItem.getQuantity()+"</td>");
			content.append("<td style='text-align:right'>Php "+DEFAULT_DECIMAL_FORMAT.format(cartOrderItem.getCategoryItem().getPrice()) + "</td>");
			content.append("<td style='text-align:right'>Php "+DEFAULT_DECIMAL_FORMAT.format(cartOrderItem.getCategoryItem().getPrice() * cartOrderItem.getQuantity())+ "</td>");
			content.append("</tr>");
		}
		
		content.append("<tr>");
		content.append("</tr>");
		
		content.append("<tr>");
		content.append("<td colspan='4' style='text-align:right'><b>Shipping</b></td>");
		content.append("<td style='text-align:right'> "+DEFAULT_DECIMAL_FORMAT.format(cartOrder.getTotalShippingPrice2())+"</td>");
		content.append("</tr>");
		content.append("<tr>");
		content.append("<td colspan='4' style='text-align:right'><b>Coupon</b></td>");
		content.append("<td style='text-align:right'>Php "+DEFAULT_DECIMAL_FORMAT.format(0)+"</td>");
		content.append("</tr>");
	
		
		content.append("<tr>");
		content.append("<td colspan='4' style='background-color:gray;color:black;font-weight:bold;text-align:right;'>Total PHP incl. VAT</td>");
		content.append("<td style='background-color:gray;color:black;font-weight:bold;text-align:right;'>Php "+DEFAULT_DECIMAL_FORMAT.format(totalPrice)+"</td>");
		content.append("</tr>");
				
		content.append("</table>");
		
		content.append("<br><br>If you have any questions, feel free to contact us. Our office hours are on Mondays through Fridays 8:00 AM - 5:00 PM. Payments and inquiries sent during weekends will be processed the next working day.");
		content.append("<br><br>Thank you,<br>Pure Nectar");
		
		EmailSenderAction emailSenderAction = new EmailSenderAction();
		emailSenderAction.sendEmailConfirmationPureNectar(company, member.getEmail(), "Pure Nectar - Order Information " +cartOrder.getId(), content, null);
		
		
	}
	
	public void sendOrderDetails(CartOrder cartOrder, List<CartOrderItem> cartOrderItems, Double shippingPrice) {
		
		
		Double totalPrice = cartOrder.getTotalPrice() + cartOrder.getTotalShippingPrice2();
		StringBuffer content = new StringBuffer();
		Date current = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);	
		
		content.append("<center><img src='http://purenectar.co/images/logo-black-160px.png' /><br><br>");
		
		content.append("<table>")
				
			   .append("<tr>")
			   .append("<td colspan='2'><h4>Customer Details</h4> </b></td>")
			   .append("</tr>")
		
			   .append("<tr>")
			   .append("<td><b>Full Name: </b></td>")
			   .append("<td>" + cartOrder.getMember().getFirstname() + " " + cartOrder.getMember().getLastname() +"</td>")
			   .append("</tr>")
			   
			   .append("<tr>")
			   .append("<td><b>Email Address: </b></td>")
			   .append("<td>" + cartOrder.getEmailAddress() +"</td>")
			   .append("</tr>")
			   
			   .append("<tr>")
			   .append("<td><b>Mobile Number: </b></td>")
			   .append("<td>" + cartOrder.getPhoneNumber() +"</td>")
			   .append("</tr>")
			   
			   .append("<tr style='width:25px'></tr>")
			   
			   .append("<tr>")
			   .append("<td colspan='2'><h4>Order Information</h4></td>")
			   .append("</tr>")
			   
			   .append("<tr>")
			   .append("<td><b>Order Date: </b></td>")
			   .append("<td>" + df.format(current) +"</td>")
			   .append("</tr>")
			    
			   .append("<tr>")
			   .append("<td><b>Delivery Address: </b></td>")
			   .append("<td>" + cartOrder.getAddress1() +"</td>")
			   .append("</tr>")
			   
			   .append("<tr>")
			   .append("<td><b>Delivery Date: </b></td>")
			   .append("<td>" + cartOrder.getInfo1() +"</td>")
			   .append("</tr>")
			   
			   .append("<tr>")
			   .append("<td><b>Payment Method: </b></td>")
			   .append("<td>" + cartOrder.getPaymentType() +"</td>")
			   .append("</tr>")
			   
			   .append("<tr>")
			   .append("<td><b>Instructions: </b></td>")
			   .append("<td>" + cartOrder.getInfo2() +"</td>")
			   .append("</tr></table>");
			
		content.append("<br/><br/><b>ORDER #"+ cartOrder.getId() +"</b>");
		content.append("<br/><br/>");
		content.append("<table style='width:100%;' padding=5>");
		content.append("<tr>");
		content.append("<th colspan=2 style='background-color:gray;color:black;font-weight:bold;'>Items</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Qty</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Price</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Amount</th>");
		content.append("</tr>");
		
		
		for(CartOrderItem cartOrderItem : cartOrderItems) {
			content.append("<tr>");
			if(cartOrderItem.getCategoryItem().getImages().size() > 0){
				content.append("<td style='width:80px;'><img style='width:80px;height:80px;' src=\"http://"+company.getServerName()+"/images/items/"+cartOrderItem.getCategoryItem().getImages().get(0).getOriginal()+"\"> ");
			}else{
				content.append("<td>");
			}
			content.append("</td>");
			content.append("<td style='text-align:right;'><b>"+cartOrderItem.getCategoryItem().getName() + "</b></td>");
			content.append("<td style='text-align:right;'>"+cartOrderItem.getQuantity()+"</td>");
			content.append("<td style='text-align:right;'>Php "+DEFAULT_DECIMAL_FORMAT.format(cartOrderItem.getCategoryItem().getPrice()) + "</td>");
			content.append("<td style='text-align:right;'>Php "+DEFAULT_DECIMAL_FORMAT.format(cartOrderItem.getCategoryItem().getPrice() * cartOrderItem.getQuantity())+ "</td>");
			content.append("</tr>");
		}
		
		content.append("<tr>");
		content.append("</tr>");
		
		content.append("<tr>");
		content.append("<td colspan='4' style='text-align:right'><b>Shipping</b></td>");
		content.append("<td style='text-align:right;'> "+DEFAULT_DECIMAL_FORMAT.format(cartOrder.getTotalShippingPrice2())+"</td>");
		content.append("</tr>");
		content.append("<tr>");
		content.append("<td colspan='4' style='text-align:right'><b>Coupon</b></td>");
		content.append("<td style='text-align:right;'>Php "+DEFAULT_DECIMAL_FORMAT.format(0)+"</td>");
		content.append("</tr>");
	
		
		content.append("<tr>");
		content.append("<td colspan='4' style='background-color:gray;color:black;font-weight:bold;text-align:right;'>Total PHP incl. VAT</td>");
		content.append("<td style='background-color:gray;color:black;font-weight:bold;text-align:right;'>Php "+DEFAULT_DECIMAL_FORMAT.format(totalPrice)+"</td>");
		content.append("</tr>");
				
		content.append("</table>");

		if(cartOrder.getPaymentType().equals("COD")){
			content.append("<br><br>Change for: P" + cartOrder.getInfo3());
		}
		
		content.append("</center>");
		EmailSenderAction emailSenderAction = new EmailSenderAction();
		emailSenderAction.sendEmailConfirmationPureNectar(company, "", "Pure Nectar - Order Information " +cartOrder.getId(), content, null);
		
		
	}

	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getErrorUrl() {
		return errorUrl;
	}
	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}
	
	private final String confirmationEmailContent = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
			"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
			"<head>"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
			"<title>Pure Nectar - Order Confirmation</title>"+
			"</head>"+
			"<body>"+
			"<div class=\"container\">"+
				"<div class=\"row\">"+
					"<div class=\"col-md-12\" style=\"text-align: center\">"+
						"<h1>Pure Nectar</h1>"+
					"</div>"+
					"<div class=\"col-md-12\">"+
						"<div class=\"header-image\"><img src=\"http://purenectar.co/images/home-banner.jpg\" class=\"img-responsive\"></div>"+
					"</div>"+
				"</div>"+
				"<div class=\"row\">"+
				"</div>"+
			"</div>"+
			"</body>"+
			"</html>";

}
