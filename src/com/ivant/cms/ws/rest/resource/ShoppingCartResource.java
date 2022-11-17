package com.ivant.cms.ws.rest.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cart.action.CheckoutAction;
import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.ShippingStatus;
import com.ivant.cms.enums.ShippingType;
import com.ivant.cms.ws.rest.model.CartOrderModel;
import com.ivant.cms.ws.rest.model.MemberFileModel;
import com.ivant.cms.ws.rest.model.ShoppingCartModel;
import com.ivant.constants.WendysConstants;

@Path("/shoppingCart")
public class ShoppingCartResource extends AbstractResource 
{
	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartResource.class);
	
	private ShoppingCartModel shoppingCarModel;
	private Company company;
	private Member member;
	private PaymentType paymentTypeEnum;
	private List<CartOrderItem> orderItemList;
	private MemberFileModel memberFileModel;
	private MemberFile memberFile;
	/** parameters */
	/*private Long memberId;
	private String paymentType;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String phoneNumber;
	private String emailAddress;
	private Double shippingCostAmount;
	private String statusNotes;
	private Double otherCharges;
	private String transactionNumber;
	private String approvalCode;
	private String prescription;
	private String comments;*/
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ShoppingCartModel find(@Context HttpHeaders headers,
			@FormParam("memberId") Long memberId)
	{
		shoppingCarModel = null;
		
		try
		{
			openSession();
			company = getCompany(headers);
			member = memberDelegate.find(memberId);
			
			shoppingCarModel = new ShoppingCartModel(getShoppingCart());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return shoppingCarModel;
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/noCartCheckout")
	public CartOrderModel noCartCheckout(@Context HttpHeaders headers,
			@FormParam("memberId") Long memberId,
			@FormParam("paymentType") String paymentType,
			@FormParam("name") String name,
			@FormParam("address1") String address1,
			@FormParam("address2") String address2,
			@FormParam("city") String city,
			@FormParam("state") String state,
			@FormParam("country") String country,
			@FormParam("zipCode") String zipCode,
			@FormParam("phoneNumber") String phoneNumber,
			@FormParam("emailAddress") String emailAddress,
			@FormParam("shippingCostAmount") Double shippingCostAmount,
			@FormParam("statusNotes") String statusNotes,
			@FormParam("otherCharges") Double otherCharges,
			@FormParam("transactionNumber") String transactionNumber,
			@FormParam("approvalCode") String approvalCode,
			@FormParam("prescription") String prescription,
			@FormParam("comments") String comments,
			@FormParam("shippingType") String shippingType,
			@FormParam("itemIds") List<Long> itemIds,
			@FormParam("itemQtys") List<Long> itemQtys,
			@DefaultValue("") @FormParam("branch") String branch)
	{
		logger.info("noCartCheckout method executed! " + shippingCostAmount);
		
		
		CartOrderModel cartModel = null;
		try
		{
			openSession();
			
			company = getCompany(headers);
			member = memberDelegate.find(memberId);
			CartOrder lastCartOrder = cartOrderDelegate.findLastCartOrder(company, member, Order.desc("createdOn"));
			CartOrder cartOrder = createNewOrder(paymentType, name, address1, address2, city, state, country, 
					zipCode, phoneNumber, emailAddress, shippingCostAmount, statusNotes, otherCharges, 
					transactionNumber, approvalCode, prescription, comments, shippingType, branch);
			
			setItems(cartOrder, company, itemIds, itemQtys);
			
			if(cartOrder != null)
			{
				cartOrder.setItems(cartOrderItemDelegate.findAll(cartOrder).getList());
				cartModel = new CartOrderModel(cartOrder);
			}
			
			logger.info("ShoppingCartResource::: Before Sending Order Confirmation Email");
			if(cartOrder.getCompany().getName().equalsIgnoreCase("wendys") && cartOrder.getPaymentType()== PaymentType.COD)// && cartOrder.getShippingType()== ShippingType.DELIVERY)
			{
				checkForRewards(cartOrder, lastCartOrder);
				
				try
				{
				logger.info("ShoppingCartResource::: Inside Wendys Email Confirmation ");
				String strReceiver = comments;
				
				String strLimitter1 = "";
				String strLimitter2 = "";
				String strLimitter3 = "";
				if(cartOrder.getShippingType() == ShippingType.PICKUP){
					 strLimitter1 = "Prefferred Store: ";
					 strLimitter2 = "Preffered Date: ";
					 strLimitter3 = "Preffered Time: ";
				
					strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
					strLimitter2 = strReceiver.substring(strReceiver.indexOf(strLimitter2)+(strLimitter2.length()-1),strReceiver.indexOf(strLimitter3)).trim();
					strLimitter3 = strReceiver.substring(strReceiver.indexOf(strLimitter3)+(strLimitter3.length()-1),strReceiver.length()).trim();
				}
				
				final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
				Date date2 = 
					(cartOrder.getCreatedOn() != null)
						? cartOrder.getCreatedOn()
						: new Date();
				String billingDate2 = dateFormat.format(date2);
				Double dPriceTotal = 0.0;
				for(CartOrderItem orderItem : cartOrder.getItems()){
					if(orderItem.getStatus().equalsIgnoreCase("ok")){
						dPriceTotal = dPriceTotal + (orderItem.getItemDetail().getPrice()*orderItem.getQuantity());
					}
				}
				
				//-------ASSIGN POINTS--------
				System.out.println("***ENTERING ASSIGNING POINTS***");
				CheckoutAction checkoutAction  = new CheckoutAction();
				checkoutAction.wendysAssignPoints(company, cartOrder, dPriceTotal + cartOrder.getTotalShippingPrice2());
				
				
				EmailSenderAction wendysEmailSender = new EmailSenderAction();
				wendysEmailSender.sendWendysOrderConfirmation(cartOrder.getShippingType()== ShippingType.DELIVERY ? "Delivery" : "Pickup", String.valueOf(cartOrder.getId()), cartOrder.getName(), strLimitter1, strLimitter2, strLimitter3, cartOrder.getAddress1(), cartOrder.getCity(), cartOrder.getState(), cartOrder.getCountry(), billingDate2, cartOrder.getPhoneNumber(), cartOrder.getPaymentType() == PaymentType.COD ? "Cash on Delivery" : "Credit Card", cartOrder.getStatus().getName(), cartOrder.getPaymentStatus() == PaymentStatus.PAID ? "PAID" : "PENDING", cartOrderItemDelegate.findAll(cartOrder).getList(), String.valueOf(cartOrder.getTotalShippingPrice2()),  String.valueOf(dPriceTotal), String.valueOf(cartOrder.getTotalShippingPrice2() + dPriceTotal), cartOrder.getMember().getEmail());
				}
				catch(Exception e){
					logger.info("WENDYS ERROR ::: " + e.toString());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return cartModel;
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/noCartCheckoutIOS")
	public CartOrderModel noCartCheckoutIOS(@Context HttpHeaders headers,
			@FormParam("memberId") Long memberId,
			@FormParam("paymentType") String paymentType,
			@FormParam("name") String name,
			@FormParam("address1") String address1,
			@FormParam("address2") String address2,
			@FormParam("city") String city,
			@FormParam("state") String state,
			@FormParam("country") String country,
			@FormParam("zipCode") String zipCode,
			@FormParam("phoneNumber") String phoneNumber,
			@FormParam("emailAddress") String emailAddress,
			@FormParam("shippingCostAmount") Double shippingCostAmount,
			@FormParam("statusNotes") String statusNotes,
			@FormParam("otherCharges") Double otherCharges,
			@FormParam("transactionNumber") String transactionNumber,
			@FormParam("approvalCode") String approvalCode,
			@FormParam("prescription") String prescription,
			@FormParam("comments") String comments,
			@FormParam("shippingType") String shippingType,
			@FormParam("itemIds") List<String> itemIds,
			@FormParam("itemQtys") List<Long> itemQtys,
			@DefaultValue("") @FormParam("branch") String branch)
	{
		logger.info("noCartCheckout method executed! " + shippingCostAmount);
		
		
		CartOrderModel cartModel = null;
		try
		{
			openSession();
			
			company = getCompany(headers);
			member = memberDelegate.find(memberId);
			CartOrder lastCartOrder = cartOrderDelegate.findLastCartOrder(company, member, Order.desc("createdOn"));
			CartOrder cartOrder = createNewOrder(paymentType, name, address1, address2, city, state, country, 
					zipCode, phoneNumber, emailAddress, shippingCostAmount, statusNotes, otherCharges, 
					transactionNumber, approvalCode, prescription, comments, shippingType, branch);
			
			setItemsIOS(cartOrder, company, itemIds, itemQtys);
			
			if(cartOrder != null)
			{
				cartOrder.setItems(cartOrderItemDelegate.findAll(cartOrder).getList());
				cartModel = new CartOrderModel(cartOrder);
			}
			
			logger.info("ShoppingCartResource::: Before Sending Order Confirmation Email");
			if(cartOrder.getCompany().getName().equalsIgnoreCase("wendys") && cartOrder.getPaymentType()== PaymentType.COD)// && cartOrder.getShippingType()== ShippingType.DELIVERY)
			{
				checkForRewards(cartOrder, lastCartOrder);
				
				try
				{
				logger.info("ShoppingCartResource::: Inside Wendys Email Confirmation ");
				String strReceiver = comments;
				
				String strLimitter1 = "";
				String strLimitter2 = "";
				String strLimitter3 = "";
				if(cartOrder.getShippingType() == ShippingType.PICKUP){
					 strLimitter1 = "Prefferred Store: ";
					 strLimitter2 = "Preffered Date: ";
					 strLimitter3 = "Preffered Time: ";
				
					strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
					strLimitter2 = strReceiver.substring(strReceiver.indexOf(strLimitter2)+(strLimitter2.length()-1),strReceiver.indexOf(strLimitter3)).trim();
					strLimitter3 = strReceiver.substring(strReceiver.indexOf(strLimitter3)+(strLimitter3.length()-1),strReceiver.length()).trim();
				}
				
				final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
				Date date2 = 
					(cartOrder.getCreatedOn() != null)
						? cartOrder.getCreatedOn()
						: new Date();
				String billingDate2 = dateFormat.format(date2);
				Double dPriceTotal = 0.0;
				for(CartOrderItem orderItem : cartOrder.getItems()){
					if(orderItem.getStatus().equalsIgnoreCase("ok")){
						dPriceTotal = dPriceTotal + (orderItem.getItemDetail().getPrice()*orderItem.getQuantity());
					}
				}
				
				//-------ASSIGN POINTS--------
				System.out.println("***ENTERING ASSIGNING POINTS***");
				CheckoutAction checkoutAction  = new CheckoutAction();
				checkoutAction.wendysAssignPoints(company , cartOrder, dPriceTotal + cartOrder.getTotalShippingPrice2());
				
				
				EmailSenderAction wendysEmailSender = new EmailSenderAction();
				wendysEmailSender.sendWendysOrderConfirmation(cartOrder.getShippingType()== ShippingType.DELIVERY ? "Delivery" : "Pickup", String.valueOf(cartOrder.getId()), cartOrder.getName(), strLimitter1, strLimitter2, strLimitter3, cartOrder.getAddress1(), cartOrder.getCity(), cartOrder.getState(), cartOrder.getCountry(), billingDate2, cartOrder.getPhoneNumber(), cartOrder.getPaymentType() == PaymentType.COD ? "Cash on Delivery" : "Credit Card", cartOrder.getStatus().getName(), cartOrder.getPaymentStatus() == PaymentStatus.PAID ? "PAID" : "PENDING", cartOrderItemDelegate.findAll(cartOrder).getList(), String.valueOf(cartOrder.getTotalShippingPrice2()),  String.valueOf(dPriceTotal), String.valueOf(cartOrder.getTotalShippingPrice2() + dPriceTotal), cartOrder.getMember().getEmail());
				}
				catch(Exception e){
					logger.info("WENDYS ERROR ::: " + e.toString());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		
		return cartModel;
	}

	private void checkForRewards(CartOrder newCartOrder, CartOrder lastCartOrder) 
	{
		if(lastCartOrder != null)
			System.out.println("..............last cart order id ? " + lastCartOrder.getId());
		System.out.println("..............new cart order id ? " + newCartOrder.getId());
		
		if(lastCartOrder == null || !DateUtils.isSameDay(newCartOrder.getCreatedOn(), lastCartOrder.getCreatedOn()))
		{
			
			int orderCount = cartOrderDelegate.getMemberOrderCount(company, member).intValue();
			orderCount = orderCount % WendysConstants.WENDYS_ORDER_REWARDS.length;
			System.out.println("\n\n\n********************ORDER COUNT ? "  + orderCount);
			
			if(orderCount % 2 == 1)
			{
				long rewardId = WendysConstants.WENDYS_ORDER_REWARDS[orderCount - 1];
				Category category = categoryDelegate.find(rewardId);
				List<MemberFile> memberFiles = memberFileDelegate.findAllWithPaging(company, 1, 0, category, false, 
						Order.asc("createdOn"));
				
				if(memberFiles.size() > 0)
				{
					MemberFile memberFile = memberFiles.get(0);
					memberFile.setMember(member);
					
					memberFileDelegate.update(memberFile);
				}
			}
		}
		else
		{
			logger.info("Order count ignored. User has an existing order with the same date!");
		}
	}

	private CartOrder createNewOrder(String paymentType, String name, String address1, String address2, 
			String city, String state, String country, String zipCode, String phoneNumber, String emailAddress, 
			Double shippingCostAmount, String statusNotes, Double otherCharges, String transactionNumber, 
			String approvalCode, String prescription, String comments, String shippingType, String branch)
	{
		/** basic info */
		CartOrder order = new CartOrder();
		order.setCreatedOn(new Date());
		order.setUpdatedOn(new Date());
		order.setIsValid(true);
		order.setMember(member);
		order.setCompany(company);
		order.setStatus(OrderStatus.NEW);
		order.setComments(comments);
		
		/** customer info */
		order.setName(name);
		order.setAddress1(address1);
		order.setAddress2(address2);
		order.setCity(city);
		order.setState(state);
		order.setCountry(country);
		order.setZipCode(zipCode);
		order.setPhoneNumber(phoneNumber);
		order.setEmailAddress(emailAddress);
		order.setStatusNotes(statusNotes);
		order.setOtherCharges(otherCharges);
		
		
		/** shipping info */
		order.setPaymentType(getPaymentType(paymentType));
		order.setPaymentStatus(PaymentStatus.PENDING);
		order.setShippingStatus(ShippingStatus.PENDING);
		order.setTransactionNumber(transactionNumber);
		order.setApprovalCode(approvalCode);
		order.setPrescription(prescription);
		if(StringUtils.isNotEmpty(shippingType))
		{
			order.setShippingType(ShippingType.valueOf(shippingType.toUpperCase()));
		}
		if(shippingCostAmount != null)
		{
			order.setTotalShippingPrice2(shippingCostAmount);
		}
		
		//set branch data to info4 field
		order.setInfo4(branch);
		
		Long id = cartOrderDelegate.insert(order);
		
		if(id != null)
		{
			/*
			CartOrder cartOrder = cartOrderDelegate.find(id);
			
			if(company.getName().equalsIgnoreCase("wendys") && paymentType.contains("Delivery")){
				String strReceiver = comments;
				String strLimitter1 = "Prefferred Store: ";
				String strLimitter2 = "Preffered Date: ";
				String strLimitter3 = "Preffered Time: ";
				
				strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
				strLimitter2 = strReceiver.substring(strReceiver.indexOf(strLimitter2)+(strLimitter2.length()-1),strReceiver.indexOf(strLimitter3)).trim();
				strLimitter3 = strReceiver.substring(strReceiver.indexOf(strLimitter3)+(strLimitter3.length()-1),strReceiver.length()).trim();
				
				
				final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
				Date date2 = 
					(cartOrder.getCreatedOn() != null)
						? cartOrder.getCreatedOn()
						: new Date();
				String billingDate2 = dateFormat.format(date2);
				Double dPriceTotal = 0.0;
				for(CartOrderItem orderItem : cartOrder.getItems()){
					if(orderItem.getStatus().equalsIgnoreCase("ok")){
						dPriceTotal = dPriceTotal + (orderItem.getItemDetail().getPrice()*orderItem.getQuantity());
					}
				}
				EmailSenderAction wendysEmailSender = new EmailSenderAction();
				wendysEmailSender.sendWendysOrderConfirmation(cartOrder.getShippingType()== ShippingType.DELIVERY ? "Delivery" : "Pickup", String.valueOf(cartOrder.getId()), cartOrder.getName(), strLimitter1, strLimitter2, strLimitter3, cartOrder.getAddress1(), cartOrder.getCity(), cartOrder.getState(), cartOrder.getCountry(), billingDate2, cartOrder.getPhoneNumber(), cartOrder.getPaymentType() == PaymentType.COD ? "Cash on Delivery" : "Credit Card", cartOrder.getStatus().getName(), cartOrder.getPaymentStatus() == PaymentStatus.PAID ? "PAID" : "PENDING", cartOrderItemDelegate.findAll(cartOrder).getList(), String.valueOf(cartOrder.getTotalShippingPrice2()),  String.valueOf(dPriceTotal), String.valueOf(cartOrder.getTotalShippingPrice2() + dPriceTotal), cartOrder.getMember().getEmail());
				
			}
			*/
			
			
			
			return cartOrderDelegate.find(id);
		}
		
		return null;
	}

	private PaymentType getPaymentType(String paymentType) {
		try{
			return PaymentType.valueOf(paymentType);
		}
		catch(Exception e){
			
		}
		return null;
	}

	private void setItems(CartOrder order, Company company, List<Long> itemIds, List<Long> itemQtys)
	{
		if(itemIds != null && itemQtys != null && itemIds.size() > 0 && itemQtys.size() > 0 
				&& itemIds.size() == itemQtys.size())
		{
			for(int i=0; i<itemIds.size(); i++)
			{
				Long itemId = itemIds.get(i);
				Long quantity = itemQtys.get(i);
				
				CartOrderItem cartOrderItem = new CartOrderItem();
				cartOrderItem.setOrder(order);
				cartOrderItem.setCompany(company);
				cartOrderItem.setCreatedOn(new Date());
				cartOrderItem.setIsValid(true);
				cartOrderItem.setStatus("OK");
				cartOrderItem.setQuantity(quantity != null ? quantity.intValue() : 0);
				
				final CategoryItem categoryItem = categoryItemDelegate.find(itemId);
				if(categoryItem != null)
				{
					cartOrderItem.setItemDetail(categoryItem.getItemDetail());
				}
				
				cartOrderItemDelegate.insert(cartOrderItem);
			}
		}
		else
			System.out.println(itemIds + " | " + itemQtys);
	}
	
	private void setItemsIOS(CartOrder order, Company company, List<String> itemIds, List<Long> itemQtys)
	{
		//List<String> itemIds2 = Collections.reverse(itemIds);
		//Collections.sort(itemIds, Collections.reverseOrder());
		//Collections.sort(itemQtys, Collections.reverseOrder());
		
		int counterSplit = 0;
		String tempItemIdStr = "";
		String tempSizeIdStr = "";
		Long tempItemIdLong = 0L;
		Long tempSizeIdLong = 0L;
		Long tempParent = 0L;
		String tempParentStr = "";
		Boolean useSizePrice = false;
		
		if(itemIds != null && itemQtys != null && itemIds.size() > 0 && itemQtys.size() > 0 
				&& itemIds.size() == itemQtys.size()){
			
			for(int i=0; i<itemIds.size(); i++)
			{
				tempItemIdStr = "";
				tempSizeIdStr = "";
				tempItemIdLong = 0L;
				tempSizeIdLong = 0L;
				//tempParent = 0L;
				useSizePrice = false;
				if(itemIds.get(i).indexOf(WendysConstants.DELIMITER_TEXT)>=0){
					for(String s : itemIds.get(i).split(WendysConstants.DELIMITER_TEXT)){
						if(counterSplit==0 && s.trim().length() > 0){
							tempItemIdStr = s;
							try{
								tempItemIdLong = Long.parseLong(tempItemIdStr);
							}
							catch(Exception e){
								
							}
						}
						else if(counterSplit==1 && s.trim().length() > 0){
							tempSizeIdStr = s;
							try{
								tempSizeIdLong = Long.parseLong(tempSizeIdStr);
								if(tempSizeIdLong == 0L){
									tempParent = tempItemIdLong;
									CategoryItem tempC = categoryItemDelegate.find(tempParent);
									if(tempC != null){
										tempParentStr = tempC.getName();
									}
								}
								else if(tempSizeIdLong > 0L){
								
									useSizePrice = true;
									//System.out.println("#### Parent "+tempParent);
									//System.out.println("#### Value ::: "+(categoryItemDelegate.find(tempSizeIdLong).getName().indexOf("Large") >= 0 ? "Large" : "") + (categoryItemDelegate.find(tempSizeIdLong).getName().indexOf("Biggie") >= 0 ? "Biggie" : ""));
									CategoryItem cI = categoryItemDelegate.findContainsName(company, tempParentStr + " " + (categoryItemDelegate.find(tempSizeIdLong).getName().indexOf("Large") >= 0 ? "Large" : "") + (categoryItemDelegate.find(tempSizeIdLong).getName().indexOf("Biggie") >= 0 ? "Biggie" : ""));
									tempSizeIdLong = cI.getId();
								}
							}
							catch(Exception e){
								e.printStackTrace();
							}
							counterSplit = -1;
						}
						counterSplit++;
					}
				}
				else{
					tempItemIdStr = itemIds.get(i);
					try{
						tempItemIdLong = Long.parseLong(tempItemIdStr);
					}
					catch(Exception e){
						
					}
				}
				Long itemId = 0L;
				Long quantity = itemQtys.get(i);
				if(useSizePrice){
					itemId = tempSizeIdLong;
					
				}
				else{
					itemId = tempItemIdLong;
				}
				CartOrderItem cartOrderItem = new CartOrderItem();
				cartOrderItem.setOrder(order);
				cartOrderItem.setCompany(company);
				cartOrderItem.setCreatedOn(new Date());
				cartOrderItem.setIsValid(true);
				cartOrderItem.setStatus("OK");
				cartOrderItem.setQuantity(quantity != null ? quantity.intValue() : 0);
				final CategoryItem categoryItem = categoryItemDelegate.find(itemId);
				if(categoryItem != null)
				{
					cartOrderItem.setItemDetail(categoryItem.getItemDetail());
				}
				
				cartOrderItemDelegate.insert(cartOrderItem);
			}
		}
		else
			System.out.println(itemIds + " | " + itemQtys);
	}
	
	private ShoppingCart getShoppingCart()
	{
		ShoppingCart shoppingCart = shoppingCartDelegate.find(company, member);
		
		if(shoppingCart == null)
		{
			shoppingCart = new ShoppingCart();
			shoppingCart.setCompany(company);
			shoppingCart.setMember(member);
			shoppingCart.setCreatedOn(new Date());
			
			shoppingCartDelegate.insert(shoppingCart);
			
			shoppingCart = shoppingCartDelegate.find(company, member);
		}
		
		return shoppingCart;
	}
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/addMemberFile")
	public MemberFileModel addMemberFile(@Context HttpHeaders headers,
			@FormParam("id") Long id,
			@FormParam("approveddate") String approvedDate,
			@FormParam("value") String value,
			@FormParam("status") String status) {
		logger.info("addMemberFile method invoked...");
		memberFileModel = new MemberFileModel();
		memberFile = null;
		member = null;
		try{
			Company company = getCompany(headers);
			member = memberDelegate.find(id);
			memberFile = new MemberFile();
			memberFile.setMember(member);
			memberFile.setCompany(company);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateResult =  df.parse(approvedDate);
			
			memberFile.setApprovedDate(dateResult);
			memberFile.setStatus(status);
			memberFile.setValue(value);
			Long memberFileId = memberFileDelegate.insert(memberFile);
			if(id!=null){
				memberFile = memberFileDelegate.find(memberFileId);
			}
			
			if(memberFile != null) {
				memberFileModel = new MemberFileModel(memberFile);
				memberFileModel.setSuccess(Boolean.TRUE);
				memberFileModel.setNotificationMessage("New MemberFile was created!");
				
			}
			else{
				memberFileModel.setSuccess(Boolean.FALSE);
				memberFileModel.setNotificationMessage("MemberFile was not created!");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			closeSession();
		}
		return memberFileModel;
	}
	
	/*@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MemberFileModel findMemberFile(@Context HttpHeaders headers,
			@FormParam("id") Long id) {
		logger.info("findMemberFile method invoked! params id: {} ", id);

		memberFile = null;
		memberFileModel = null;

		try {
			openSession();
			Company company = getCompany(headers);
			if (id != null && company.equals(memberFile.getCompany())) {
				memberFile = memberFileDelegate.find(id);
			}

			if (memberFile != null) {
				memberFileModel = new MemberFileModel(memberFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}

		return memberFileModel;
	}*/
	
}
