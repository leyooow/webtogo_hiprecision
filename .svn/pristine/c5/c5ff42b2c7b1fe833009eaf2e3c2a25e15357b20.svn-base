package com.ivant.cms.action.company.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cart.action.Paypal;
import com.ivant.cart.action.PaypalAction;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.ShippingType;
import com.ivant.utils.EmailUtil;

public class HiPreOnlineStoreJSONAction extends AbstractBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6740222165570708872L;
	
	protected Logger logger = Logger.getLogger(getClass());
	
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	private final CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	private final MemberShippingInfoDelegate memberShippingInfoDelegate = MemberShippingInfoDelegate.getInstance();
	private final PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	private final CategoryItemOtherFieldDelegate categoryItemOtherfieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	
	private final ArrayList<CategoryItem> noLogInCartItems = new ArrayList<CategoryItem>();
	
	Member tempMember = new Member();
	
	private static final NumberFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	
	public String itmId;
	
	private String smtp;
	private String portNumber;
	private String mailerUserName;
	private String mailerPassword;
	
	private InputStream inputStream;

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public String checkOut() {
		try {
			
			List<PromoCode> promoCodesList = promoCodeDelegate.findAllWithinExpiryDate(company).getList();
			
			JSONObject obj = new JSONObject();
			obj.put("success", true);
			if(session == null)
			{
				session = (Map) request.getSession(true);
			}
			String name = "", email = "", contactNumber = "", address = "", patientStatus = "";
			String deliveryOption = "", branchName = "", branchAddress = "", area = "", location = "";
			String promoCodeParam = "";
			String patient_name = "", patient_gender = "", patient_birthdate = "", patient_email ="", patient_contact_number = "", patient_address = "";
			String shippingCostAmount = "", isShipping = "";
			String discountPromoCode = ""; Double discountTotal = 0.0;
			boolean flag = false;
			Boolean isPatient = Boolean.parseBoolean(request.getParameter("isPatient"));
			flag = true;
			if(flag)
			{
				name = request.getParameter("1a|name");
				email = request.getParameter("1b|email");
				contactNumber = request.getParameter("1c|contact_number");
				address = request.getParameter("1d|address");
				
				patient_name = request.getParameter("1e|patient_name");
				patient_gender = request.getParameter("1f|patient_gender");
				patient_birthdate = request.getParameter("1g|patient_birthdate");
				patient_email = request.getParameter("1h|patient_email");
				patient_contact_number = request.getParameter("1i|patient_contact_number");
				patient_address = request.getParameter("patient_address");
				
				patientStatus = request.getParameter("patient-status");
				deliveryOption = request.getParameter("delivery-option");
				branchName = request.getParameter("branch_name");
				branchAddress = request.getParameter("branch_address");
				promoCodeParam = request.getParameter("promo_code");
				
				shippingCostAmount = request.getParameter("shippingCostAmount");
				isShipping = request.getParameter("isShipping");
				if(Double.parseDouble(shippingCostAmount) > 0 && isShipping.equals("true") ){
					addShippingItem(shippingCostAmount);
				}
				
				area = request.getParameter("province");
				location = request.getParameter("city");
				
				tempMember.setValue(shippingCostAmount);
				if(isPatient){
					tempMember.setFirstname(patient_name);
					tempMember.setEmail(patient_email);
					tempMember.setUsername(patient_email);
					tempMember.setMobile(patient_contact_number);
					email = patient_email;
					address = patient_address;
				}else{
					tempMember.setFirstname(name);
					tempMember.setEmail(email);
					tempMember.setUsername(email);
					tempMember.setMobile(contactNumber);
				}
				
				tempMember.setPassword("");
				tempMember.setCompany(company);
				tempMember.setReg_companyAddress(address);
				tempMember.setNewsletter(true);
				tempMember.setStatus(patientStatus);
				session.put("tempMember", tempMember);
				
				/*final */Member m = memberDelegate.findEmail(company, email);

				if(m != null)
				{
					if(isPatient){
						m.setFirstname(patient_name);
						m.setUsername(patient_email);
						m.setEmail(patient_email);
						m.setReg_companyAddress(patient_address);
						m.setMobile(patient_contact_number);
					}else{
						m.setFirstname(name);
						m.setUsername(email);
						m.setEmail(email);
						m.setReg_companyAddress(address);
						m.setMobile(contactNumber);
					}
					
					m.setValue(shippingCostAmount);
					memberDelegate.update(m);
					request.setAttribute("memId", m.getId());
				}
				else
				{
					m = new Member();
					final long memId = memberDelegate.insert(tempMember);
					request.setAttribute("memId", memId);
					m = memberDelegate.findEmail(company, email);

					if(m != null)
					{
						if(isPatient){
							m.setFirstname(patient_name);
							m.setUsername(patient_email);
							m.setEmail(patient_email);
							m.setReg_companyAddress(patient_address);
							m.setMobile(patient_contact_number);
						}else{
							m.setFirstname(name);
							m.setUsername(email);
							m.setEmail(email);
							m.setReg_companyAddress(address);
							m.setMobile(contactNumber);
						}
						m.setValue(shippingCostAmount);
						memberDelegate.update(m);
						request.setAttribute("memId", m.getId());
					}else{
						return ERROR;
					}
				}
				
				
				final List<CategoryItem> catItems = (List<CategoryItem>) request.getSession().getAttribute("noLogInCartItems");
				final List<CategoryItem> catItems2 = new ArrayList<CategoryItem>();
				
				CartOrder cartOrder = new CartOrder();
				
				if(request.getSession().getAttribute("order-id") != null) {
					//cartOrder = cartOrderDelegate.find((Long) request.getSession().getAttribute("order-id"));
				}
				
				cartOrder.setStatus(OrderStatus.PENDING);
				cartOrder.setCompany(company);
				if(isPatient){
					cartOrder.setName(patient_name);
					cartOrder.setEmailAddress(patient_email);
					cartOrder.setPhoneNumber(patient_contact_number);
					cartOrder.setAddress1(patient_address);
				}else{
					cartOrder.setName(name);
					cartOrder.setEmailAddress(email);
					cartOrder.setPhoneNumber(contactNumber);
					cartOrder.setAddress1(address);
				}
				cartOrder.setMember(m);
				
				final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
				
				Double totalPrice = 0.00;
				DecimalFormat df = new DecimalFormat("#.00"); 
				
				
				/*List<SavedEmail> num = savedEmailDelegate.findEmailByFormName(company, "Order Confirmation Email", Order.desc("updatedOn")).getList();
				*/
				List<CartOrder> num = cartOrderDelegate.findAllByCompany(company);
				int fixedNumber = 0;
				System.out.println(num.size());
				
				int orderNumber = fixedNumber + num.size() + 1;
				String finalOrderNumber = "";
				if(orderNumber < 10){
					finalOrderNumber = "00" + orderNumber;
				}else if(orderNumber < 100){
					finalOrderNumber = "0" + orderNumber;
				}else{
					finalOrderNumber = "" + orderNumber;
				}
				
				StringBuilder orderContent = new StringBuilder();
				
				orderContent.append("Order Number: <strong>" + finalOrderNumber + "</strong><br><br>");
				
				
				
				/*name = request.getParameter("1a|name");
				email = request.getParameter("1b|email");
				contactNumber = request.getParameter("1c|contact_number");
				address = request.getParameter("1d|address");
				
				patient_name = request.getParameter("1e|patient_name");
				patient_gender = request.getParameter("1f|patient_gender");
				patient_birthdate = request.getParameter("1g|patient_birthdate");
				patient_email = request.getParameter("1h|patient_email");
				patient_contact_number = request.getParameter("1i|patient_contact_number");
				
				patientStatus = request.getParameter("patient-status");
				deliveryOption = request.getParameter("delivery-option");
				branchName = request.getParameter("branch_name");
				branchAddress = request.getParameter("branch_address");
				promoCodeParam = request.getParameter("promo_code");
				 */
				if(isPatient){
					orderContent.append("<strong>PATIENT INFO</strong><br>")
					.append("Patient name: " + patient_name + "<br>")
					.append("Patient gender: " + patient_gender + "<br>")
					.append("Patient birthdate: " + patient_birthdate + "<br>")
					.append("Patient email: " + patient_email + "<br>")
					.append("Patient contact number: " + patient_contact_number + "<br>")
					.append("Patient Address: " + patient_address + "<br><br>");
				}else{
					orderContent.append("<strong>PAYER INFO</strong><br>")
					.append("Payer name: " + name + "<br>")
					.append("Payer email: " + email + "<br>")
					.append("Payer contact number: " + contactNumber + "<br>")
					.append("Payer address: " + address + "<br><br>");
					
					orderContent.append("<strong>PATIENT INFO</strong><br>")
					.append("Patient name: " + patient_name + "<br>")
					.append("Patient gender: " + patient_gender + "<br>")
					.append("Patient birthdate: " + patient_birthdate + "<br>")
					.append("Patient email: " + patient_email + "<br>")
					.append("Patient contact number: " + patient_contact_number + "<br>")
					.append("Patient Address: " + patient_address + "<br><br>");
				}
				
				if(deliveryOption.equalsIgnoreCase("For Delivery")){
					orderContent.append("Delivery Option: For delivery<br>")
					.append("Area: " + area + "<br>")
					.append("Location: " + location + "<br>")
					.append("Address: " + address + "<br><br>");
				}else if(deliveryOption.equalsIgnoreCase("For pick up only")){
					orderContent.append("Delivery Option: For pick up only<br>")
					.append("Branch: " + branchName + "<br>")
					.append("Branch address: " + branchAddress + "<br><br>");
				}else{
					orderContent.append("Delivery Option: For pick up with Administration<br>")
					.append("Branch: " + branchName + "<br>")
					.append("Branch address: " + branchAddress + "<br><br>");
				}
				
				/*
				 * orderContent.append("")
				.append("")
				.append("")
				.append("")
				.append("")
				.append("")
				.append("</table>");
				*/
				
				orderContent.append("<strong>ORDER ITEMS</strong></br>")
				.append("<table class='order_items_list'><tr><th width='150'>Name</th>")
				.append("<th width='100'>Price</th>")
				.append("<th width='100'>Quantity</th>")
				/*.append("<th width='150'>Discount</th>")*/
				.append("<th width='100' align='right'>Total</th>")
				.append("</tr>");
				
				
				
				
				int counter = 0;
				for(final CategoryItem cI : catItems) {
					
					final CartOrderItem cartOrderItem = new CartOrderItem();
					final ItemDetail itemDetail = new ItemDetail();
		
					Double itemPrice = cI.getPrice();
					Double originalPrice = cI.getPrice();
					Double discountedPrice = cI.getPrice();
					Double itemTotalPrice = cI.getPrice() * Integer.parseInt(cI.getOtherDetails());
					//itemDetail.setDescription(cI.getDescription());
					itemDetail.setName(cI.getName());
					itemDetail.setPrice(cI.getPrice());
					itemDetail.setDiscount(new Double(0.0));
					itemDetail.setDiscountedPrice(itemTotalPrice);
					itemDetail.setRealID(cI.getId());
					
					if(!cI.getName().equalsIgnoreCase("Shipping Fee")){
						CategoryItem cat = categoryItemDelegate.find(cI.getId());
						int availableStock = Integer.parseInt(cat.getCategoryItemOtherFieldMap().get("Stock").getContent());
						System.out.println("Available Stock: " + availableStock);
						int stockAfterCheckout = availableStock - (Integer.parseInt(cI.getOtherDetails()));
						System.out.println("Available Stock After Purchase: " + stockAfterCheckout);
						cat.getCategoryItemOtherFieldMap().get("Stock").setContent(String.format("%s", stockAfterCheckout));
						categoryItemDelegate.update(cat);
					}
					/*List<OtherField> otherFields = cI.getParent().getParentGroup().getOtherFields();
					for(int i = 0; i < otherFields.size(); i++)
					{
						final OtherField of = otherFields.get(i);
						String ofName = of.getName();
						if(ofName.equals("Stock")){
							CategoryItemOtherField otherfield = categoryItemOtherfieldDelegate.findByCategoryItemOtherField(company, cI, of);
							int stock = Integer.parseInt(otherfield.getContent());
							
							otherfield.setContent(String.format("%s", stock-(Integer.parseInt(cI.getOtherDetails()))));;
							categoryItemOtherfieldDelegate.update(otherfield);
							System.out.println(stock - (Integer.parseInt(cI.getOtherDetails())));
						}
						
					}
*/					
					if(!cI.getName().equalsIgnoreCase("shipping fee")){
						orderContent.append("<tr>")
						.append("<td>" + cI.getName() + "</td>")
						.append("<td align='left'>" + cI.getPrice() + "</td>")
						.append("<td align='left'>" + cI.getOtherDetails() + "</td>")
						/*.append("<td align='right'>" + cI.getItemDetail().getDiscount() + "</td>")*/
						.append("<td align='right'>" + itemTotalPrice + "</td>")
						.append("</tr>");
					
						if(request.getParameter("promo_code") != null && !request.getParameter("promo_code").isEmpty()) {
							String[] promoCodes = promoCodeParam.split(",");
							boolean hasPromo = false;
							for(int i=0; i<=promoCodes.length; i++) {
								if(hasPromo)
									break;
								for(PromoCode code : promoCodesList) {
									try {
										String promoCodeStr = promoCodes[i].trim().toLowerCase();
										String codeStr = code.getCode().trim().toLowerCase();
										discountPromoCode = String.format("%s", code.getDiscount());
										if((promoCodeStr.equalsIgnoreCase(codeStr) && code.getItems().contains("AllItems")) 
												|| (code.getItems().contains(cI.getId().toString()) && promoCodeStr.equalsIgnoreCase(codeStr))) {
											discountedPrice = itemPrice*(code.getDiscount()/100);
											itemPrice = itemPrice-discountedPrice;
											BigDecimal bd = new BigDecimal(itemPrice.doubleValue());
											BigDecimal roundOff = bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);
											itemPrice = roundOff.doubleValue();
											Double discountedTotalPrice = itemTotalPrice*(code.getDiscount()/100);
											itemTotalPrice = itemTotalPrice-discountedTotalPrice;
											itemDetail.setDiscountedPrice(itemTotalPrice);
											itemDetail.setPrice(itemPrice);
											/*itemDetail.setUOM(String.format("%s", cI.getPrice()));*/
											itemDetail.setDiscount(code.getDiscount());
											
											discountTotal += discountedTotalPrice;
											hasPromo = true;
											break;
										}
									} catch (IndexOutOfBoundsException e) {
										break;
									}
								}
							}
						}
					}
					
					
					
					totalPrice += itemTotalPrice;
					
					
					
					cartOrderItem.setItemDetail(itemDetail);
					cartOrderItem.setQuantity(Integer.parseInt(cI.getOtherDetails()));
					
					CategoryItem c2 = new CategoryItem();
					c2.setName(cI.getName());
					c2.setPrice(itemPrice);
					c2.setOtherDetails(cI.getOtherDetails());
					c2.setSku(cI.getSku());
					c2.setShippingPrice(0.0);
					catItems2.add(c2);
					
					cI.setPrice(originalPrice);
					cartOrderItem.setCategoryItem(cI);
					//cartOrderItem.setStatus(OrderStatus.PENDING.toString());
					
					cartOrderItem.setCompany(company);
					cartOrderItem.setOrder(cartOrder);
					cartOrderItems.add(cartOrderItem);
					
					counter++;
					
					
				}
				//totalPrice += Double.parseDouble(shippingCostAmount);
				
				orderContent.append("<tr></tr><tr>")
				.append("<td></td>")
				.append("<td></td>")
				.append("<td align='right'>Shipping Fee: </td>")
				.append("<td align='right'>" + Double.parseDouble(shippingCostAmount) + "</td>")
				.append("</tr>");
				
				orderContent.append("<tr></tr><tr>")
				.append("<td></td>")
				.append("<td></td>")
				.append("<td align='right'>Discount(" + discountPromoCode + "%): </td>")
				.append("<td align='right'>" + discountTotal + "</td>")
				.append("</tr>");
				
				orderContent.append("<tr>")
				.append("<td></td>")
				.append("<td></td>")
				.append("<td align='right'>Total: </td>")
				.append("<td align='right'><strong>&#8369;" + Double.toString(totalPrice) + "</strong></td>")
				.append("</tr></table><br><br>");
				
				sendOrderEmail(orderContent.toString());
				
				
				cartOrder.setTransactionNumber(String.valueOf(finalOrderNumber));
				cartOrder.setItems(cartOrderItems);
				cartOrder.setTotalPrice(totalPrice);
				cartOrder.setTotalPriceOkFormatted(Double.toString(totalPrice));
				cartOrder.setPaymentType(PaymentType.PAYPAL);
				cartOrder.setStatus(OrderStatus.PENDING);
				cartOrder.setPaymentStatus(PaymentStatus.PENDING);
				cartOrder.setTotalShippingPrice2(Double.parseDouble(shippingCostAmount));
				
				MemberShippingInfo memberShippingInfo = new MemberShippingInfo();
				memberShippingInfo.setCompany(company);
				memberShippingInfo.setMember(m);
				ShippingInfo shippingInfo = new ShippingInfo();
				if(deliveryOption.equalsIgnoreCase("For Pick up with Administration") || deliveryOption.equalsIgnoreCase("For Pick up only")) {
					shippingInfo.setAddress1(branchName);
					shippingInfo.setAddress2(branchAddress);
				} else {
					shippingInfo.setAddress1(address);
					shippingInfo.setAddress2(address);
				}
				
				memberShippingInfo.setShippingInfo(shippingInfo);
				cartOrder.setShippingInfo(memberShippingInfo);
				
				memberShippingInfoDelegate.insert(memberShippingInfo);
				
				cartOrder.setShippingType((deliveryOption.equalsIgnoreCase("For Pick up with Administration") || deliveryOption.equalsIgnoreCase("For Pick up only") ? ShippingType.PICKUP : ShippingType.DELIVERY));
				
				
				
				
				
				
				final long orderId = cartOrderDelegate.insert(cartOrder);
				request.getSession().setAttribute("order-id", orderId);
				
				cartOrderItemDelegate.batchInsert(cartOrderItems);
				
				request.getSession().setAttribute("cartOrder", cartOrder);
				
				Paypal paypal = null;
				PaypalAction paypalAction = null;
				
				String result = "";
				String pToken = "";
				String checkoutUrl = "";
				
				if (request.getParameter("paypal_sandbox") != null
						&& request.getParameter("paypal_sandbox").equalsIgnoreCase("true")) {
					System.out.println("Paypal True");
					String sandboxUsername = request.getParameter("sandbox_username");
					String sandboxPassword = request.getParameter("sandbox_password");
					String sandboxSignature = request.getParameter("sandbox_signature");
					
//					paypal = new Paypal(sandboxUsername, sandboxPassword, sandboxSignature, "sandbox", company.getPalSuccessUrl() + "?memberId="
//							+ m.getId(), company.getPalCancelUrl());
					
					request.getSession().setAttribute("sandbox_username", sandboxUsername);
					request.getSession().setAttribute("sandbox_password", sandboxPassword);
					request.getSession().setAttribute("sandbox_signature", sandboxSignature);
					request.getSession().setAttribute("paypal_environment", "sandbox");
					
					paypalAction = new PaypalAction(sandboxUsername, sandboxPassword, sandboxSignature, "sandbox", 
							company.getPalCurrencyType(), company.getPalSuccessUrl() + "?memberId=" + m.getId(), company.getPalCancelUrl());
					
					paypalAction.setCategoryItems(catItems2);
					//totalPrice -= Double.parseDouble(shippingCostAmount);
					result = paypalAction.setExpressCheckoutRequest(DEFAULT_DECIMAL_FORMAT.format(totalPrice), DEFAULT_DECIMAL_FORMAT.format(0.00));
					
					pToken = paypalAction.getToken();
					checkoutUrl = paypalAction.getPaypalUrl();
					
				} else {
					System.out.println("Paypal Else");
					paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl() + "?memberId="
							+ m.getId(), company.getPalCancelUrl());
					
//					paypalAction = new PaypalAction(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", 
//							company.getPalCurrencyType(), company.getPalSuccessUrl() + "?memberId=" + m.getId(), company.getPalCancelUrl());
					
					paypal.setCategoryItems(catItems2);
					
					result = paypal.setExpressCheckoutRequest(DEFAULT_DECIMAL_FORMAT.format(totalPrice), company.getPalCurrencyType(), null, null);
					
					pToken = paypal.getToken();
					checkoutUrl = paypal.getPaypalUrl();
					
					request.getSession().setAttribute("paypal_environment", "live");
					
				}
				
				if(result.equalsIgnoreCase("Failure"))
				{
					obj.put("success", false);
					obj.put("message", "Error PayPal");
				} else {
					obj.put("success", true);
					obj.put("pToken", pToken);
					obj.put("checkoutUrl", checkoutUrl);
					
					JSONArray cartItemArr = new JSONArray();
					for(CartOrderItem item : cartOrderItems) {
						JSONObject cartItem = new JSONObject();
						CategoryItem catItem = item.getCategoryItem();
						ItemDetail itm = item.getItemDetail();
						cartItem.put("name", itm.getName());
						cartItem.put("price", catItem.getPrice());
						cartItem.put("quantity", item.getQuantity());
						cartItem.put("discount", itm.getDiscount());
						cartItem.put("total", itm.getDiscountedPrice());
						cartItemArr.add(cartItem);
					}
					obj.put("cartItems", cartItemArr);
				}
				
				
				
				if(isPatient){
					sendConfirmEmail(patient_name, patient_email);
				}else{
					sendConfirmEmail(name, email);
				}
				
			} else {
				obj.put("success", false);
				obj.put("message", "All fields are required!");
			}
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" HIPRE " , e);
		}
		/*request.getSession().removeAttribute("noLogInCartItems");*/
		session.remove("noLogInCartItems");
		
		return SUCCESS;
	}
	
	public void addShippingItem(String shippingCostAmount) {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		boolean alreadyInNoLogInCart = false;
		String quantity = "0";
		/*final CategoryItem item = categoryItemDelegate.find(itemId);*/
		CategoryItem catItem = new CategoryItem();
		catItem.setId(0L);
		catItem.setName("Shipping Fee");
		catItem.setPrice(Double.parseDouble(shippingCostAmount));
		catItem.setOtherDetails("1");
		/*catItem.setDescription(request.getParameter("o1043"));*/
		catItem.setDescription("Shipping Fee");
		
		if(session.get("cartForNoLogIn") != null)
		{
			final List<CategoryItem> catItems = (List<CategoryItem>) request.getSession().getAttribute("noLogInCartItems");
			// CHECKING IF items is already in cart
			int itemSessionId = 1;
			if(catItems != null)
			{
				for(final CategoryItem cI : catItems)
				{
					// for update
					if(cI.getId().toString().equalsIgnoreCase(catItem.getId().toString()))
					{
						alreadyInNoLogInCart = true;
						cI.setName("Shipping Fee");
						cI.setPrice(Double.parseDouble(shippingCostAmount));
						final Integer temp = Integer.parseInt(quantity) + Integer.parseInt(cI.getOtherDetails());
						quantity = temp.toString();
						cI.setOtherDetails(quantity);
					}
					cI.setSku("" + itemSessionId);
					cI.getPrice();
					noLogInCartItems.add(cI);
					itemSessionId++;
				}
			}
			if(catItem != null && !alreadyInNoLogInCart)
			{
				catItem.setSku("" + itemSessionId);
				noLogInCartItems.add(catItem);
			}
		} else {
			catItem.setSku("1");
			noLogInCartItems.add(catItem);
			session.put("cartForNoLogIn", true);
			session.put("tempMember", tempMember);
		}
		request.getSession().setAttribute("noLogInCartItems", noLogInCartItems);
		
	}
	
	@SuppressWarnings("unchecked")
	public void sendOrderEmail(String content){
		try {
			JSONObject obj = new JSONObject();
			String emailContent = "";
			String to = "store@hi-precision.com.ph";
			String fullName = "";
			String emailAddress = "store@hi-precision.com.ph";
			String subjectMessage = "";
			String message = "";
			//setEmailSettings();
			EmailUtil.connect("smtpout.secureserver.net", 80,
					"store@hi-precision.com.ph", "store1996");
			String from = emailAddress;
			
			List<CartOrder> num = cartOrderDelegate.findAllByCompany(company);
			int fixedNumber = 0;
			System.out.println(num.size());
			
			int orderNumber = fixedNumber + num.size() + 1;
			String finalOrderNumber = "";
			if(orderNumber < 10){
				finalOrderNumber = "00" + orderNumber;
			}else if(orderNumber < 100){
				finalOrderNumber = "0" + orderNumber;
			}else{
				finalOrderNumber = "" + orderNumber;
			}
			
			boolean emailSent = EmailUtil.sendWithHTMLFormat("store@hi-precision.com.ph", to, "Hi-Precision Online Store Order Confirmation", content.toString(), null);
			EmailUtil.sendWithHTMLFormat("store@hi-precision.com.ph", "marlowe@hi-precision.com.ph", "Hi-Precision Online Store Order Confirmation", content.toString(), null);
			EmailUtil.sendWithHTMLFormat("store@hi-precision.com.ph", "marytibre@hi-precision.com.ph", "Hi-Precision Online Store Order Confirmation", content.toString(), null);
			
			if(!emailSent) {
				obj.put("success", false);
				obj.put("message", "Failed to send confirmation email!");
			} else {
				SavedEmail savedEmail = new SavedEmail();
				savedEmail.setCompany(company);
				savedEmail.setSender("Hi-Precision Online Store");
				savedEmail.setEmail(to);
				savedEmail.setPhone("");
				savedEmail.setFormName("Order Email");
				savedEmail.setEmailContent(content.toString());
				savedEmail.setTestimonial(String.format("%s", finalOrderNumber));
				savedEmailDelegate.insert(savedEmail);
			}
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void sendConfirmEmail(String name, String email){
	/*public String sendConfirmEmail(){*/
		try {
			JSONObject obj = new JSONObject();
			String emailContent = "";
			String to = email;
			String fullName = name;
			String emailAddress = "store@hi-precision.com.ph";
			String subjectMessage = "";
			String message = "";
			//setEmailSettings();
			EmailUtil.connect("smtpout.secureserver.net", 80,
					"store@hi-precision.com.ph", "store1996");
			String from = emailAddress;
			
			/*List<SavedEmail> num = savedEmailDelegate.findEmailByFormName(company, "Order Confirmation Email", Order.desc("updatedOn")).getList();
			 * */
			List<CartOrder> num = cartOrderDelegate.findAllByCompany(company);
			int fixedNumber = 0;
			System.out.println(num.size());
			
			int orderNumber = fixedNumber + num.size();
			String finalOrderNumber = "";
			if(orderNumber < 10){
				finalOrderNumber = "00" + orderNumber;
			}else if(orderNumber < 100){
				finalOrderNumber = "0" + orderNumber;
			}else{
				finalOrderNumber = "" + orderNumber;
			}
			
			
			StringBuilder contentEmail = new StringBuilder();
			contentEmail.append("Thank you for using the Hi-Precision Online Store. Your order number is <strong>" + finalOrderNumber + "</strong>.<br><br>")
			
			.append("<strong>For Vaccine Orders:</strong><br><br> Please give us 24-48 hours to confirm your order. Once your schedule is confirmed, you should ")
			.append("receive an email confirmation indicating your preferred branch and their available schedule. ")
			.append("If there are some issues with your preferred schedule, our staff will reach out to you<br><br>")
			
			.append("Orders cannot be cancelled once we have confirmed your order.<br><br>")
			
			.append("For questions and inquires pls call 741-7777 during office hours (7am-6pm). Kindly have on hand your ORDER NUMBER for faster processing.<br><br>")
			.append("Kindly take into account that we process your orders during office hours. Our Hi Precision Online Store office hours are from Mondays - Fridays 7am - 6pm. Closed during holidays.<br><br>")
			
			.append("Thank you,<br>")
			.append("Your Hi Precision Family");
			
			
			
			boolean emailSent = EmailUtil.sendWithHTMLFormat("store@hi-precision.com.ph", to, "Hi-Precision Online Store Order Confirmation", contentEmail.toString(), null);
			if(!emailSent) {
				obj.put("success", false);
				obj.put("message", "Failed to send confirmation email!");
			} else {
				SavedEmail savedEmail = new SavedEmail();
				savedEmail.setCompany(company);
				savedEmail.setSender("Hi-Precision Online Store");
				savedEmail.setEmail(email);
				savedEmail.setPhone("");
				savedEmail.setFormName("Order Confirmation Email");
				savedEmail.setEmailContent(contentEmail.toString());
				savedEmail.setTestimonial(String.format("%s", finalOrderNumber));
				savedEmailDelegate.insert(savedEmail);
			}
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*return SUCCESS;*/
	}
	
	@SuppressWarnings("unchecked")
	public String addCartItem() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		boolean alreadyInNoLogInCart = false;
		String id = request.getParameter("id");
		String quantity = request.getParameter("quantity");
		final Long itemId = Long.parseLong(id);
		final CategoryItem item = categoryItemDelegate.find(itemId);
		CategoryItem catItem = new CategoryItem();
		catItem.setId(itemId);
		catItem.setName(item.getName());
		catItem.setPrice(item.getPrice());
		catItem.setParentGroup(item.getParentGroup());
		catItem.setOtherDetails(quantity);
		/*catItem.setDescription(request.getParameter("o1043"));*/
		catItem.setDescription(item.getCategoryItemOtherFieldMap().get("Type").getContent());
		
		if(session.get("cartForNoLogIn") != null)
		{
			final List<CategoryItem> catItems = (List<CategoryItem>) request.getSession().getAttribute("noLogInCartItems");
			// CHECKING IF items is already in cart
			int itemSessionId = 1;
			if(catItems != null)
			{
				for(final CategoryItem cI : catItems)
				{
					// for update
					if(cI.getId().toString().equalsIgnoreCase(catItem.getId().toString()))
					{
						alreadyInNoLogInCart = true;
						cI.setName(item.getName());
						cI.setPrice(item.getPrice());
						final Integer temp = Integer.parseInt(quantity) + Integer.parseInt(cI.getOtherDetails());
						quantity = temp.toString();
						cI.setOtherDetails(quantity);
					}
					cI.setSku("" + itemSessionId);
					cI.getPrice();
					noLogInCartItems.add(cI);
					itemSessionId++;
				}
			}
			if(catItem != null && !alreadyInNoLogInCart)
			{
				catItem.setSku("" + itemSessionId);
				noLogInCartItems.add(catItem);
			}
		} else {
			catItem.setSku("1");
			noLogInCartItems.add(catItem);
			session.put("cartForNoLogIn", true);
			session.put("tempMember", tempMember);
		}
		request.getSession().setAttribute("noLogInCartItems", noLogInCartItems);
		if(noLogInCartItems.size() == 0)
		{
			request.getSession().setAttribute("noLogInCartItems", null);
			//session.put("noLogInCartItems", null);
		}
		setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String deleteCartItem() {
		try {
			JSONObject obj = new JSONObject();
			obj.put("success", true);
			noLogInCartItems.addAll((List<CategoryItem>) session.get("noLogInCartItems"));
			final String delete = request.getParameter("removeId");
			if(delete != null) {
				for(int j = 0; j < noLogInCartItems.size(); j++)
				{
					if(noLogInCartItems.get(j).getSku().equals(delete.toString()))
						noLogInCartItems.remove(j);
				}
			}
			if(noLogInCartItems.size() == 0)
				session.remove("noLogInCartItems");
			else
				session.put("noLogInCartItems", noLogInCartItems);
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String sendContactEmailAjax() {
		try {
			JSONObject obj = new JSONObject();
			obj.put("success", true);
			obj.put("message", "Success");
			String subject = request.getParameter("subject");
			String content = request.getParameter("email_content");
			String to = request.getParameter("to");
			String fullName = request.getParameter("1a|name");
			String emailAddress = request.getParameter("1b|email");
			String subjectMessage = request.getParameter("1c|subject");
			String message = request.getParameter("1d|message");
			//setEmailSettings();
			EmailUtil.connect("smtpout.secureserver.net", 80,
					"store@hi-precision.com.ph", "store1996");
			String from = emailAddress;
			if(!StringUtils.isEmpty(fullName) && !StringUtils.isEmpty(emailAddress) && !StringUtils.isEmpty(subjectMessage) && !StringUtils.isEmpty(message)) {
				boolean emailSent = EmailUtil.sendWithHTMLFormat("store@hi-precision.com.ph", to, "Hi-Precision Online Store Inquiry Submission", content, null);
				if(!emailSent) {
					obj.put("success", false);
					obj.put("message", "Email not sent.");
				} else {
					sendAcknowledgementEmail(emailAddress);
					saveEmailInformation(content);
				}
			} else {
				obj.put("success", false);
				obj.put("message", "All fields are required.");
			}
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String sendContactEmailNonAjax() {
		try {
			JSONObject obj = new JSONObject();
			String successUrl = request.getParameter("successUrl");
			String errorUrl = request.getParameter("errorUrl");
			
			String subject = request.getParameter("subject");
			String content = request.getParameter("email_content");
			String to = request.getParameter("to");
			String fullName = request.getParameter("1a|name");
			String emailAddress = request.getParameter("1b|email");
			String subjectMessage = request.getParameter("1c|subject");
			String message = request.getParameter("1d|message");
			//setEmailSettings();
			EmailUtil.connect("smtpout.secureserver.net", 80,
					"store@hi-precision.com.ph", "store1996");
			String from = emailAddress;
			if(!StringUtils.isEmpty(fullName) && !StringUtils.isEmpty(emailAddress) && !StringUtils.isEmpty(subjectMessage) && !StringUtils.isEmpty(message)) {
				boolean emailSent = EmailUtil.sendWithHTMLFormat("store@hi-precision.com.ph", to, "Hi-Precision Online Store Inquiry Submission", content, null);
				if(!emailSent) {
					return ERROR;
				} else {
					sendAcknowledgementEmail(emailAddress);
					saveEmailInformation(content);
				}
			} else {
				return SUCCESS;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void sendAcknowledgementEmail(String email) {
		StringBuilder content = new StringBuilder();
		content.append("<br>Thank You!<br>")
		.append("Your email has been successfully sent and we appreciate you contacting us. We'll be in touch soon.<br><br>")
		.append("Your Hi Precision Family");
		EmailUtil.connect("smtpout.secureserver.net", 80,
				"store@hi-precision.com.ph", "store1996");
		EmailUtil.sendWithHTMLFormat("store@hi-precision.com.ph", email, "Hi-Precision Online Store Inquiry Submission", content.toString(), null);
	}
	
	private void saveEmailInformation(String emailContent) {
		try {
			String formName = request.getParameter("se_formName");
			String sender = request.getParameter("se_sender");
			String senderEmail = request.getParameter("se_email");
			String senderPhone = request.getParameter("se_phone");
			String testimonial = request.getParameter("se_testimonial");
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(sender);
			savedEmail.setEmail(senderEmail);
			savedEmail.setPhone(senderPhone);
			savedEmail.setFormName(formName);
			savedEmail.setEmailContent(emailContent);
			savedEmail.setTestimonial(testimonial);
			savedEmailDelegate.insert(savedEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			
	public void setEmailSettings() {

		if (company.getCompanySettings().getEmailUserName() != null
				&& !company.getCompanySettings().getEmailUserName().equals("")) {
			setMailerUserName(company.getCompanySettings().getEmailUserName());
		} else {
			setMailerUserName(EmailUtil.DEFAULT_USERNAME);
		}
		if (company.getCompanySettings().getEmailPassword() != null
				&& !company.getCompanySettings().getEmailPassword().equals("")) {
			setMailerPassword(company.getCompanySettings().getEmailPassword());
		} else {
			setMailerPassword(EmailUtil.DEFAULT_PASSWORD);
		}
		if (company.getCompanySettings().getSmtp() != null
				&& !company.getCompanySettings().getSmtp().equals("")) {
			setSmtp(company.getCompanySettings().getSmtp());
		} else {
			setSmtp("smtp.gmail.com");
		}
		if (company.getCompanySettings().getPortNumber() != null
				&& !company.getCompanySettings().getPortNumber().equals("")) {
			setPortNumber(company.getCompanySettings().getPortNumber());
		} else {
			setPortNumber("587");
		}
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getMailerUserName() {
		return mailerUserName;
	}

	public void setMailerUserName(String mailerUserName) {
		this.mailerUserName = mailerUserName;
	}

	public String getMailerPassword() {
		return mailerPassword;
	}

	public void setMailerPassword(String mailerPassword) {
		this.mailerPassword = mailerPassword;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getItmId() {
		return itmId;
	}

	public void setItmId(String itmId) {
		this.itmId = itmId;
	}

}
