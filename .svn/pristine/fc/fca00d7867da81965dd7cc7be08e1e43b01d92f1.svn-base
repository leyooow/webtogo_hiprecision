package com.ivant.cms.email.template;

import java.util.ArrayList;
import java.util.List;

import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;

public class HBCSuccessfulPaymentTemplate {
	
	private String cartContentsMessage;
	private List<ShoppingCartItem> cartItemList;
	private Company company;
	private Member member;
	private CartOrder cartOrder;
	private ShoppingCart shoppingCart;
	private ArrayList<CategoryItem> catItem;
	private String header = "";
	private String footer = "";
	private String welcomeMessage="";
	private String shippingInfoMessage = "";
	
	
	
	private String otherMessage = "";
	
	private String thankYouMessage = "";
	

	public HBCSuccessfulPaymentTemplate(Company company, Member member,CartOrder cartOrder, List<ShoppingCartItem> cartItemList, ShoppingCart shoppingCart, ArrayList<CategoryItem> catItem ) {
		this.cartItemList = cartItemList;
		this.company = company;
		this.member = member;
		this.cartOrder = cartOrder;
		this.shoppingCart = shoppingCart;
		this.catItem = catItem;
		generateHeader();
		generateWelcomeMessage();
		generateCartContentsMessage();
		generateShippingInfoMessage();
		generateOtherMessage();
		generateFooter();
		
	}
	
	private void generateFooter() {
		footer += "</div>";
		footer += "<div style=\"background:url(http://hbc.com.ph/images/bgHBC.gif) right top no-repeat #db1f30; height: 80px; padding: 5px 0 0 10px;\"><img src=\"http://hbc.com.ph/images/newHBC_logo.jpg\" /></div>";
		footer += "</div>";
		
	}

	private void generateHeader(){
		
		header += "<div style=\"width: 670px; font-family: Arial; font-size: 12px; border: 8px solid #db1f30;\">";
		header += "<div style=\"background:url(http://hbc.com.ph/images/bgHBC.gif) right top no-repeat #db1f30; height: 85px; padding: 0 0 0 10px;\">";
		header += "<img src=\"http://hbc.com.ph/images/newHBC_logo.jpg\" />";
		header += "</div>";
		header += "<div style=\"padding: 25px;\">";
		
	}
	
	
	
	
	
	private void generateWelcomeMessage(){
		//System.out.println("METHOD CALLED    -- > generateWelcomeMessage");
		/*
		welcomeMessage = "<strong>Beautiful day "+member.getFirstname()+" "+member.getLastname()+",</strong><br><br>";
		welcomeMessage += "Thank you for shopping at HBC, Inc. Your Order ID number is "+cartOrder.getId()+".<br>";
		welcomeMessage += "Please refer to this number if you need further assistance for this order.<br><br>";
		welcomeMessage += "Your order has already been sent to our warehouse for processing.<br><br>";

		*/

		welcomeMessage += "<span style=\"font-size:14px;\">Beautiful Day <strong> "+member.getFirstname()+" "+member.getLastname()+",</strong></span><br><br>";
		welcomeMessage += "Thank you for shopping at HBC, Inc. Your Order ID number is "+cartOrder.getId()+". Please refer to this number if you need further assistance for this order.<br><br>";

		welcomeMessage += "Your order has already been sent to our warehouse for processing.<br><br><br />:";
		
		
		
		
	}
	
	
	private void generateCartContentsMessage(){
		
		String thStyle = "class=\"title\" width=\"10%\" align=\"center\" style=\"background: #e9e9e9;\"";

		//System.out.println("METHOD CALLED    -- > generateCartContentsMessage");
		cartContentsMessage = "<strong>Order ID "+cartOrder.getId()+" Summary:</strong><br><br>";
		cartContentsMessage = "<table  cellpadding=\"4\" cellspacing=\"0\" style=\"font-size:12px;\" width=\"100%\" align=\"center\">";
		
		
		
		cartContentsMessage +="<tr><td "+thStyle+"><strong>Quantity</strong></td><td "+thStyle+"><strong>Item</strong></td><td  "+thStyle+"><strong>Price</strong></td><td  "+thStyle+"><strong>Subtotal</strong></td></tr>";
	
		
		
		for(int i=0; i<cartItemList.size(); i++)
		{
			cartContentsMessage +="<tr>";
			cartContentsMessage +="<td  align=\"center\" style=\"border-bottom: 1px solid #e9e9e9;\">" + cartItemList.get(i).getQuantity() + "</td>";
			cartContentsMessage +="<td style=\"border-bottom: 1px solid #e9e9e9;\">"+catItem.get(i).getParent().getName() + ", " + catItem.get(i).getBrand().getName() + " - " + cartItemList.get(i).getItemDetail().getName() + "</td>";
		
			
			cartContentsMessage +="<td align=\"right\" style=\"border-bottom: 1px solid #e9e9e9; padding: 0 10px 0 0;\"><strong>$</strong>" + cartItemList.get(i).getItemDetail().getPrice() + "</td>";
			cartContentsMessage +="<td align=\"right\" style=\"border-bottom: 1px solid #e9e9e9; padding: 0 10px 0 0;\"><strong>$</strong>" + cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity() + "</td>";
			cartContentsMessage +="</tr>";
		}

		cartContentsMessage +="<tr>";
		cartContentsMessage +="	<td  colspan=\"3\" align=\"right\" style=\"border-top: 2px solid #e9e9e9;\"><strong>Total Item Price:</strong></td>";       
		//formattedTotalItemsPrice
		cartContentsMessage +="<td align=\"right\" style=\"padding: 0 10px 0 0; border-top: 2px solid #e9e9e9;\"><strong>$</strong>"+shoppingCart.getFormattedTotalItemsPrice()+"</td>";
		//cartContentsMessage +="	<td><span style=\"float: right;\"><strong>$</strong>"+shoppingCart.getFormattedTotalItemsPrice()+"</span></td>";
		cartContentsMessage +="</tr>";
		cartContentsMessage +="<tr>";
		
		
		
		
		cartContentsMessage +="<tr>";
			cartContentsMessage +="<td colspan=\"3\" align=\"right\"><strong>Shipping Price</strong></td>";
			cartContentsMessage +="<td align=\"right\" style=\"padding: 0 10px 0 0;\"><div id=\"totalPrice\"><strong>$</strong> "+shoppingCart.getFormattedTotalShippingPrice()+"</span></td>";
		cartContentsMessage +="</tr>";
			cartContentsMessage +="<tr>";
			
			//String totalCost = shoppingCart.getFormattedTotalItemsPrice() + shoppingCart.getFormattedTotalShippingPrice();
			
			Double totalitemsprice  = Double.parseDouble(shoppingCart.getFormattedTotalItemsPrice());
			Double totalshippingcost = Double.parseDouble(shoppingCart.getFormattedTotalShippingPrice());
			
			Double totalCost = totalitemsprice + totalshippingcost;
			
			cartContentsMessage +="<td colspan=\"3\" align=\"right\"><strong>Total Price</strong></td>";
			cartContentsMessage +="<td align=\"right\" style=\"padding: 0 10px 0 0;\"><div id=\"totalPrice\"><strong>$</strong> "+totalCost+"</span></td>";
		cartContentsMessage +="</tr></table>";


		cartContentsMessage +="<br><br>";
		cartContentsMessage +="<strong>Comments or special instructions about this order:</strong>";
		if(cartOrder.getComments()!=null)
			cartContentsMessage +="<br>"+cartOrder.getComments() + "<br /><br />";
		else
			cartContentsMessage +="<br>None.<br /><br />";
		
		
	
	}
	
	
	private void generateShippingInfoMessage(){

		//System.out.println("METHOD CALLED    -- > generateShippingInfoMessage");
	   shippingInfoMessage = "<strong><i  style=\"font-size:14px;\">Your order will be shipped to:</i>";
		ShippingInfo shippingInfo = cartOrder.getShippingInfo().getShippingInfo();
		shippingInfoMessage +=("<table>");
		shippingInfoMessage +=("<tr><td>Address 1:</td><td>"+shippingInfo.getAddress1()+"</td></tr>");
		shippingInfoMessage +=("<tr><td>Address 2:</td><td>"+shippingInfo.getAddress2()+"</td></tr>");
		shippingInfoMessage +=("<tr><td>City:</td><td>"+shippingInfo.getCity()+"</td></tr>");
		shippingInfoMessage +=("<tr><td>State:</td><td>"+shippingInfo.getState()+"</td></tr>");
		shippingInfoMessage +=("<tr><td>Country:</td><td>"+shippingInfo.getCountry()+"</td></tr>");
		shippingInfoMessage +=("<tr><td>Zip Code:</td><td>"+shippingInfo.getZipCode()+"</td></tr>");
		shippingInfoMessage +=("<tr><td>Phone Number:</td><td>"+shippingInfo.getPhoneNumber()+"</td></tr>");
		shippingInfoMessage +=("<tr><td>Email Address:</td><td>"+shippingInfo.getEmailAddress()+"</td></tr>");
		shippingInfoMessage +=("<tr><td>&nbsp;</td></tr>");
		shippingInfoMessage +=("</table><br/>");
		
		shippingInfoMessage +="When you receive your credit card statement, this transaction will be displayed as being billed by MyAyala.com Inc.<br><br>";

		shippingInfoMessage +="At <a href=\"http://www.hbc.com.ph\" target=\"_blank\" style=\"color: #db1f30; text-decoration:none;\">www.hbc.com.ph</a>, you can always check your order status by logging in to your account and clicking on the My <br>";
		shippingInfoMessage +="Orders link located at the top leftmost portion of every page. <br><br>";
		
	}
	
	
	private void generateOtherMessage(){
		
		
		//otherMessage
		

		//System.out.println("METHOD CALLED    -- > generateOtherMessage");

		otherMessage = "<p><strong  style=\"font-size:14px;\"><i>Order Processing<br></i></strong>";
		otherMessage += "For areas within the PHILIPPINES,allow 2 (two) business days for processing time. <br><br></p>";
			
		otherMessage += "<p><strong><i style=\"font-size:14px;\">Delivery Lead Time(after processing)</i></strong><br>";
		otherMessage += "Metro Manila: next business day<br>";
		otherMessage += "Out-of-town/ provincial:3-5 days <br>";
		otherMessage += "(Note: Orders posted on Sundays and holidays will be processed on the next business day)<br><br></p>";

		otherMessage += "<strong><i  style=\"font-size:14px;\">Return Policy.</i></strong> Return requests must be made within 7 days from receipt of goods. Shipping is non-refundable <br>";
		otherMessage += "except for those returns as a result of HBC, Inc.’s error. All returned merchandise must be new, unused, and include original,<br> undamaged packaging, attached tags, instructions, shipping label, etc.<br><br> ";

		otherMessage += "We can’t wait for you to receive your delivery from HBC, Inc. We look forward to doing business with you again.<br><br><br>";


		otherMessage += "Thank you for your order,<br>";
		otherMessage += "Customer Care Team<br>";
		otherMessage += "HBC, Inc.<br>";
		otherMessage += "Website: www.hbc.com.ph<br>";
		otherMessage += "Email: mddeguzman@hbc.com.ph<br>";
	}
	
	
	private void generateThankYouMessage(){
		thankYouMessage += "Thank you for your order,<br>";
		thankYouMessage += "<br />";
		thankYouMessage += "<strong>Customer Care Team</strong><br>";
		thankYouMessage += " <strong>HBC, Inc.</strong><br><br />";
		thankYouMessage += " Website: <a href=\"http://www.hbc.com.ph\" target=\"_blank\" style=\"color: #db1f30; text-decoration:none;\">www.hbc.com.ph</a><br>";
		thankYouMessage += " Email: <a href=\"mailto:mddeguzman@hbc.com.ph\" style=\"color: #db1f30; text-decoration:none;\">mddeguzman@hbc.com.ph</a><br>";
		
	}
	
	public String getMessage(){
		String message = "";
		message = message.concat(header);
		message = message.concat("<table cellpadding=\"4\" cellspacing=\"0\" style=\"font-size: 12px;\">");
		message = message.concat("<tr>");
		message = message.concat("<td style=\"border-color:red\">");
		message = message.concat(welcomeMessage);
		message = message.concat(cartContentsMessage);
		message = message.concat(shippingInfoMessage);
		message = message.concat(otherMessage);
		message = message.concat("</td>");
		message = message.concat("</tr>");
		message = message.concat("<tr>");
		message = message.concat("<td style=\"border-top: 1px solid #e9e9e9;\">");
		message = message.concat(thankYouMessage);
		message = message.concat("</td></tr></table>");
		message = message.concat(footer);
		
		return message;
	}
	
	
	
	
	

}
