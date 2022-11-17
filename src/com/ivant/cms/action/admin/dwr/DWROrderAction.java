package com.ivant.cms.action.admin.dwr;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cart.action.CheckoutAction;
import com.ivant.cart.action.MemberAction;
import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.SavedEmailType;
import com.ivant.cms.enums.ShippingStatus;
import com.ivant.cms.enums.ShippingType;
import com.ivant.constants.CompanyConstants;
import com.ivant.constants.Constant;
import com.ivant.utils.CartOrderUtil;
import com.ivant.utils.CategoryItemUtil;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.NumberUtil;

/**
 * Order reverse ajax class.
 *
 * @author Mark Kenneth M. Ra?osa
 */
public class DWROrderAction
		extends AbstractDWRAction implements ServletRequestAware, ServletContextAware
{
	private static final Logger logger = Logger.getLogger(DWROrderAction.class);
	private static final String DIY = "DIY";
	private static final String FACE = "Face";
	private static final String INDENT = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String PNG_EXTENSION = ".png";
	private static final String SINGLE_SPACE = " ";
	private static final String STRAP = "Strap";
	private static final String UPLOADED_PHOTO = "Uploaded Photo";
	private static final String ZERO = "0.00";
	public HttpServletRequest request;
	protected ServletContext servletContext;
	private final String MONEY_FORMAT = "##,###,##0.00";
	private CartOrder cartOrder;
	private double totalPrice = 0;
	private static final NumberFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	
	/**
	 * Returns order items.
	 *
	 * @param cartOrderID
	 *            - id of the order containing the items, must not be empty or null
	 * @return - order items
	 */
	public String getOrderList(String cartOrderID)
	{
		Double totalPrice = 0d;
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		// get cart order id
		final Long orderID = Long.parseLong(cartOrderID);
		// load order
		final CartOrder cartOrder = cartOrderDelegate.find(orderID);
		// load all items in order
		final List<CartOrderItem> itemList = cartOrderItemDelegate.findAll(cartOrder).getList();
		// generate html string containing list
		final StringBuilder htmlString = new StringBuilder();

		if(company != null && company.getId() == CompanyConstants.GURKKA || company != null && company.getId() == CompanyConstants.GURKKA_TEST)
		{
			final String voucherNumber = cartOrder.getTransactionNumber() != null
				? cartOrder.getTransactionNumber()
				: orderID.toString();
			htmlString.append("<h5>VOUCHER NUMBER/ID : ");
			htmlString.append(voucherNumber);
			
			return "";//displayGurkkaOrder(htmlString, cartOrder);
		}
		else
		{
			
			if(company.getName().equalsIgnoreCase("hiprecisiononlinestore")){
				htmlString.append("<h5>ORDER NUMBER : ");
				htmlString.append(cartOrder.getTransactionNumber());
			}else{
				htmlString.append("<h5>ORDER ID : ");
				htmlString.append(orderID);
			}
		}

		if(company == null && cartOrder != null)
		{
			company = cartOrder.getCompany();
		}

		if(company != null && company.getName().equalsIgnoreCase("hbc"))
		{
			htmlString.append("<h5>Details from MYAYALA");
			htmlString.append("<h5>TRANSACTION NUMBER: ");

			htmlString.append(cartOrder.getTransactionNumber() == null
				? ""
				: cartOrder.getTransactionNumber());
			htmlString.append("<h5>APPROVAL CODE: ");

			if(cartOrder.getApprovalCode() != null)
			{
				if(cartOrder.getApprovalCode().equalsIgnoreCase("1"))
				{
					htmlString.append("<strong>Approved</strong>");
				}
				else
				{
					htmlString.append("<strong>Dispproved</strong>");
				}
			}
			else
			{
				htmlString.append("");
			}
		}

		htmlString.append("</h5>");
		htmlString.append("<table><tr>");
		htmlString.append("<td>");
		htmlString.append("<strong>Quantity</strong>");
		htmlString.append("</td>");
		htmlString.append("<td>");
		htmlString.append("<strong>Item</strong>");
		htmlString.append("</td>");
		htmlString.append("<td>");
		htmlString.append("<strong>Price</strong>");
		htmlString.append("</td>");
		htmlString.append("<td>");
		htmlString.append("<strong>Sub-Total</strong>");
		htmlString.append("</td>");
		htmlString.append("<td>");
		htmlString.append("<strong>Status</strong>");
		htmlString.append("</td>");
		htmlString.append("</tr>");
		
		
		String dis = "";
		for(final CartOrderItem currentItem : itemList)
		{
			if(currentItem.getItemDetail().getName().indexOf("Shipping Cost") != -1)
			{
				continue;
			}
			
			if(currentItem.getItemDetail().getName().indexOf("Discount") != -1)
			{
				continue;
			}
			
			final ItemDetail itemDetail = currentItem.getItemDetail();
			final Double itemPrice = itemDetail.getPrice();
			final Integer qty = currentItem.getQuantity();
			
			if(company.getId() == CompanyConstants.TOMATO) {
				totalPrice = showDiyItem(currentItem, htmlString, totalPrice, numberFormatter, itemPrice, qty);
			}
			else if(company.getId() == CompanyConstants.SWAPCANADA) {
				CategoryItem face = currentItem.getOtherDetail().getFace();
				
				if(face != null) {
					totalPrice = showDiyItem(currentItem, htmlString, totalPrice, numberFormatter, itemPrice, qty);
				}
				else {
					totalPrice = showSwapItem(currentItem, htmlString, totalPrice, numberFormatter, itemPrice, qty);
				}
			}
			else {
				htmlString.append("<tr>");
				htmlString.append("<td>");
				htmlString.append(currentItem.getQuantity());
				htmlString.append("</td>");
				htmlString.append("<td>");
	
				if(company.getId() == CompanyConstants.HBC)
					htmlString.append(currentItem.getItemDetail().getSku());
				else
					htmlString.append(currentItem.getItemDetail().getName());
				
				htmlString.append("</td>");
				if(company.getName().equalsIgnoreCase("hiprecisiononlinestore")){
					htmlString.append("<td>");
					htmlString.append(currentItem.getItemDetail().getPrice());
					htmlString.append("</td>");
					
					if(itemDetail.getDiscount() > 0){
						dis = String.format("%s", itemDetail.getDiscount());
					}
					
					
				}else{
					htmlString.append("<td>");
					htmlString.append(CartOrderUtil.isDiscountItem(currentItem)
						? "(" + numberFormatter.format(itemDetail.getDiscount()) + ")"
						: numberFormatter.format(itemPrice));
					htmlString.append("</td>");
				}
				htmlString.append("<td>");
				htmlString.append(CartOrderUtil.isDiscountItem(currentItem)
					? "(" + numberFormatter.format(itemDetail.getDiscount()) + ")"
					: numberFormatter.format(qty * itemPrice));
				htmlString.append("</td>");
				htmlString.append("<td>");
				htmlString.append(currentItem.getStatus());
				htmlString.append("</td>");
				htmlString.append("</tr>");
			}
		}

			
		if(company.getName().equalsIgnoreCase("purenectar")){
			htmlString.append("<tr>");
			htmlString.append("  <td colspan=\"3\">");
			htmlString.append("  Shipping Price: P" + cartOrder.getTotalShippingPrice2());
			htmlString.append("   </td></tr>");
		}
		
		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{}
		else
		{
			htmlString.append("<tr>");
			htmlString.append("  <td colspan=\"3\">");
			htmlString.append("Comments or special instructions about this order : ");
			if(cartOrder.getComments() != null)
			{
				htmlString.append(cartOrder.getComments());
			}
			htmlString.append("   </td></tr>");
		}
		if(company.getName().equalsIgnoreCase("hiprecisiononlinestore")){
			htmlString.append("<tr>");
			htmlString.append("  <td colspan=\"3\">");
			htmlString.append("Discount : ");
			htmlString.append(dis + "%");
			htmlString.append("   </td>");
			htmlString.append("</tr>");
		}
		htmlString.append("<tr>");
		htmlString.append("  <td colspan=\"3\">");
		htmlString.append("Total Price : ");
		if(company != null && (company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2")) || company.getName().equalsIgnoreCase("PURPLETAG2"))
		{
			htmlString.append(cartOrder.getTotalPriceFormatted());
		}
		else if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA)
		{
			htmlString.append(totalPrice);
		}
		else if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{
			htmlString.append(NumberUtil.format(cartOrder.getTotalPrice() - cartOrder.getTotalDiscount()));
		}
		else if(company.getName().equalsIgnoreCase("purenectar")){
			htmlString.append(NumberUtil.format(cartOrder.getTotalPrice() + cartOrder.getTotalShippingPrice2()));
		}
		else
		{
			htmlString.append(cartOrder.getTotalPriceOkFormatted());
		}
		htmlString.append("   </td>");
		htmlString.append("</tr></table>");
		
		if(company.getId() == CompanyConstants.TOMATO) {
			htmlString.append("<br/>");
			htmlString.append("<table><tr>");
			int ctr = 0;
			for(final CartOrderItem currentItem : itemList)
			{
				String image = currentItem.getItemDetail().getImage();
				
				if(image != null) {
					showDiyScreenshot(currentItem, htmlString);
					
					ctr++;
					if(ctr == 2) {
						htmlString.append("</tr>");
						htmlString.append("<tr>");
						ctr = 0;
					}
				}
			}
			htmlString.append("</tr></table>");
		}
		
		if(company.getId() == CompanyConstants.SWAPCANADA) {
			String image = "";
			htmlString.append("<br/>");
			htmlString.append("<table><tr>");
			
			for(final CartOrderItem currentItem : itemList)
			{
				image = currentItem.getItemDetail().getImage();
				
				if(image != null && StringUtils.contains(image, PNG_EXTENSION)) {
					showDiyScreenshot(currentItem, htmlString);
					
					htmlString.append("</tr>");
					htmlString.append("<tr>");
				}
				else if(image != null) {
					showSwapScreenshot(currentItem, htmlString);
					
					htmlString.append("</tr>");
					htmlString.append("<tr>");
				}
			}
			htmlString.append("</tr></table>");
		}

		return htmlString.toString();
	}
	
	private void showDiyScreenshot(CartOrderItem currentItem, StringBuilder htmlString) {
		htmlString.append("<td align='center' valign='bottom' width='25%'>");
		htmlString.append("Hand Color: " + currentItem.getOtherDetail().getHandColor() + "<br/>");
		htmlString.append("<a href='http://"+company.getServerName()+"/images/screenshots/"+currentItem.getItemDetail().getImage()+"' download>");
		htmlString.append("<img width='170' src='http://"+company.getServerName()+"/images/screenshots/"+currentItem.getItemDetail().getImage()+"'/>" );
		htmlString.append("</a>");
		htmlString.append("<br/>");
		htmlString.append("<a href='http://"+company.getServerName()+"/images/screenshots/"+currentItem.getItemDetail().getImage()+"' download>");
		htmlString.append("<input type='button' value='Download'/>" );
		htmlString.append("</a><br/><br/>");
		htmlString.append("</td>");
		htmlString.append("<td align='center' valign='bottom' width='25%'>");
		htmlString.append("<a href='http://"+company.getServerName()+"/images/screenshots/face-"+currentItem.getItemDetail().getImage()+"' download>");
		htmlString.append("<img width='170' src='http://"+company.getServerName()+"/images/screenshots/face-"+currentItem.getItemDetail().getImage()+"'/>" );
		htmlString.append("</a>");
		htmlString.append("<br/><br/><br/>");
		htmlString.append("<a href='http://"+company.getServerName()+"/images/screenshots/face-"+currentItem.getItemDetail().getImage()+"' download>");
		htmlString.append("<input type='button' value='Download'/>" );
		htmlString.append("</a><br/><br/>");
		htmlString.append("</td>");
	}
	
	private void showSwapScreenshot(CartOrderItem currentItem, StringBuilder htmlString) {
		CategoryItem categoryItem = categoryItemDelegate.find(currentItem.getItemDetail().getRealID());
		
		htmlString.append("<td align='center' valign='bottom' width='25%'>");
		htmlString.append("<a href='http://"+company.getServerName()+"/images/items/"+categoryItem.getImages().get(0).getOriginal()+"' download>");
		htmlString.append("<img width='170' src='http://"+company.getServerName()+"/images/items/"+categoryItem.getImages().get(0).getOriginal()+"'/>" );
		htmlString.append("</a>");
		htmlString.append("<br/>");
		htmlString.append("<a href='http://"+company.getServerName()+"/images/items/"+categoryItem.getImages().get(0).getOriginal()+"' download>");
		htmlString.append("<input type='button' value='Download'/>" );
		htmlString.append("</a><br/><br/>");
		htmlString.append("</td>");
	}
	
	private Double showDiyItem(CartOrderItem currentItem, StringBuilder htmlString, Double totalPrice, NumberFormat numberFormatter, Double itemPrice, Integer qty) {
		String faceName = !currentItem.getOtherDetail().getFace().getName().equals(UPLOADED_PHOTO) ? currentItem.getOtherDetail().getFace().getName() : DIY;
		Double facePrice = currentItem.getOtherDetail().getFace().getPrice();
		String face = faceName + SINGLE_SPACE + FACE;
		String strap = currentItem.getItemDetail().getName() + SINGLE_SPACE + STRAP;
		String fmtFacePrice = numberFormatter.format(facePrice); 
		String fmtFaceSubtotal = numberFormatter.format(qty * facePrice);
		String fmtStrapPrice = numberFormatter.format(itemPrice);
		String fmtStrapSubtotal = numberFormatter.format(qty * itemPrice);
		String status = currentItem.getStatus();
		
		displayItem(htmlString, qty, face, fmtFacePrice, fmtFaceSubtotal, status);
		displayItem(htmlString, qty, strap, fmtStrapPrice, fmtStrapSubtotal, status);	
		
		totalPrice += qty * facePrice;
		totalPrice += qty * itemPrice;
		
		return totalPrice;
	}
	
	private Double showSwapItem(CartOrderItem currentItem, StringBuilder htmlString, Double totalPrice, NumberFormat numberFormatter, Double itemPrice, Integer qty) {
		String name = currentItem.getItemDetail().getName();
		String price = numberFormatter.format(itemPrice);
		String subtotal = numberFormatter.format(itemPrice * qty);
		String status = currentItem.getStatus();
		String[] image = currentItem.getItemDetail().getImage().split(",");
		CategoryItem strap1 = null; 
		CategoryItem strap2 = null;
		
		displayItem(htmlString, qty, name, price, subtotal, status);
		
		if(image != null && image.length > 0 && StringUtils.isNotEmpty(image[0])) {
			strap1 = categoryItemDelegate.findSKU(company, image[0]);
			displayItem(htmlString, qty, INDENT + strap1.getName(), ZERO, ZERO, status);
		}
		
		if(image != null && image.length > 1 && StringUtils.isNotEmpty(image[1])) {
			strap2 = categoryItemDelegate.findSKU(company, image[1]);
			displayItem(htmlString, qty, INDENT + strap2.getName(), ZERO, ZERO, status);
		}
		
		totalPrice += qty * itemPrice;
		
		return totalPrice;
	}
	
	
	private void displayItem(StringBuilder htmlString, Integer quantity, String itemName, String price, String subtotal, String status) {
		htmlString.append("<tr>");
		htmlString.append("<td>");
		htmlString.append(quantity);
		htmlString.append("</td>");
		htmlString.append("<td>");
		htmlString.append(itemName);
		htmlString.append("</td>");
		htmlString.append("<td>");
		htmlString.append(price);
		htmlString.append("</td>");
		htmlString.append("<td>");
		htmlString.append(subtotal);
		htmlString.append("</td>");
		htmlString.append("<td>");
		htmlString.append(status);
		htmlString.append("</td>");
		htmlString.append("</tr>");	
	}

	/**
	 * Returns a successful status if order status was successfully updated,
	 * otherwise false.
	 *
	 * @param cartOrderID
	 *            - id of the order containing the items, must not be empty or null
	 * @param orderStatus
	 *            - status of the order, can be new, pending, cancelled or completed
	 * @return - a successful status if order status was successfully updated,
	 *         otherwise false
	 */
	public String updateOrderStatus(String cartOrderID, String orderStatus)
	{
		try
		{
			// get cart order
			final Long orderID = Long.parseLong(cartOrderID);
			CartOrder cartOrder = cartOrderDelegate.find(orderID);
			final OrderStatus currentStatus = cartOrder.getStatus();

			// update enum status
			OrderStatus updateStatus = null;
			for(final OrderStatus status : OrderStatus.values())
			{
				if(status.toString().equalsIgnoreCase(orderStatus))
				{
					updateStatus = status;
					cartOrder.setStatus(status);
				}
			}
			
			if(CompanyConstants.PURE_NECTAR.equals(company.getName())){
				
				if(!OrderStatus.CANCELLED.equals(currentStatus) &&  OrderStatus.CANCELLED.equals(updateStatus)){
					StringBuffer content = new StringBuffer();
					content.append("<center><img src='http://purenectar.co/images/logo-black-160px.png' /></center><br><br>");
					content.append("Greetings from Pure Nectar<br><br>");
					content.append("This is to inform you that your order <b style='color:red'>" + cartOrder.getId() + "</b> was cancelled.<br><br>");
					content.append("<p>If you paid for your order through Bank Deposit, Credit Card or Debit Card, the full amount will be refunded. Refunds cant take up to 3-5 buesiness days to reflect on your account depending on your bank or card issuer's policy.<br><br>");
					content.append("Thank you very much!<br><br>");
					content.append("Pure Nectar Team");
					
					Member customer = cartOrder.getMember();
	
					EmailSenderAction emailSenderAction = new EmailSenderAction();
					emailSenderAction.sendEmailConfirmationPureNectar(company, customer.getEmail(), "Pure Nectar - Cancelled Order", content, null);
				
				}
			}

			if(company.getName().equalsIgnoreCase("apc"))
			{
				final List<MemberFile> memberFileList = cartOrder.getMemberFileList();
				if(!memberFileList.isEmpty())
				{

					for(final MemberFile memberFile : memberFileList)
					{
						if(orderStatus.equalsIgnoreCase("completed"))
						{
							memberFile.setStatus("REDEEMED");
						}
						else if(orderStatus.equalsIgnoreCase("new") || orderStatus.equalsIgnoreCase("pending"))
						{
							memberFile.setStatus("Waiting to be Redeemed");
						}
						
						// update uploaded receipt status
						memberFileDelegate.update(memberFile);
					}
				}
			}
			
			if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
			{
				final List<CartOrderItem> items = cartOrder.getItems();
				final Member member = memberDelegate.find(cartOrder.getMember().getId());
				
				if(updateStatus.compareTo(OrderStatus.COMPLETED) == 0)
				{
					Thread.sleep(1000);
					cartOrder = cartOrderDelegate.find(orderID);
					if(cartOrder.getPaymentStatus().compareTo(PaymentStatus.PAID) != 0 || cartOrder.getShippingStatus().compareTo(ShippingStatus.DELIVERED) != 0)
					{
						return "Order Status cannot be updated. Order status can only be updated if Payment Status is PAID and Shipping Status is DELIVERED.";
					}
				}

				if(currentStatus.compareTo(updateStatus) != 0 && currentStatus.compareTo(OrderStatus.NEW) != 0)
				{
					Boolean isSubtractItem = null;
					Boolean isSubtractPoints = null;
					boolean doTwice = false;

					switch(updateStatus)
					{
						case CANCELLED:
							/*
							 * if(currentStatus.compareTo(OrderStatus.COMPLETED) == 0)
							 * {
							 * isSubtractItem = Boolean.FALSE;
							 * isSubtractPoints = Boolean.TRUE;
							 * doTwice = true;
							 * }
							 */
							if(currentStatus.compareTo(OrderStatus.PENDING) == 0)
							{
								isSubtractItem = Boolean.FALSE;
								// isSubtractPoints = Boolean.TRUE;
								
							}else{
								if(CompanyConstants.PURE_NECTAR.equals(company.getName())){
									StringBuffer content = new StringBuffer();
									content.append("Greetings from Pure Nectar<br><br>");
									content.append("This is to inform you that your order " + cartOrder.getId() + " was cancelled.<br><br>");
									content.append("<p>If you paid for your order through Bank Deposit, Credit Card or Debit Card, the full amount will be refunded. Refunds cant take up to 3-5 buesiness days to reflect on your account depending on your bank or card issuer's policy.<br><br>");
									content.append("Thank you very much!<br><br>");
									content.append("Pure Nectar Team");
									
									Member customer = cartOrder.getMember();

									EmailSenderAction emailSenderAction = new EmailSenderAction();
									emailSenderAction.sendEmailConfirmationPureNectar(company, customer.getEmail(), "Pure Nectar - Cancelled Order", content, null);
									
								}
							}
							break;

						case COMPLETED:
							/*
							 * if(currentStatus.compareTo(OrderStatus.CANCELLED) == 0)
							 * {
							 * isSubtractItem = Boolean.TRUE;
							 * isSubtractPoints = Boolean.FALSE;
							 * doTwice = true;
							 * }
							 */
							Boolean isFirstOrder = false;
							if(! cartOrder.getPaymentType().equals(PaymentType.COD))   isFirstOrder = cartOrderDelegate.findIfFirstPurchase(cartOrder, member);
							cartOrder.setFlag1(isFirstOrder);
							updateMemberPoints(member, cartOrder);
							
							if(currentStatus.compareTo(OrderStatus.PENDING) == 0)
							{
								// isSubtractItem = Boolean.TRUE;
								isSubtractPoints = Boolean.FALSE;
							}
							break;

						case PENDING:
							/*
							 * if(currentStatus.compareTo(OrderStatus.CANCELLED) == 0)
							 * {
							 * isSubtractItem = Boolean.TRUE;
							 * isSubtractPoints = Boolean.FALSE;
							 * }
							 * if(currentStatus.compareTo(OrderStatus.COMPLETED) == 0)
							 * {
							 * isSubtractItem = Boolean.FALSE;
							 * isSubtractPoints = Boolean.TRUE;
							 * }
							 */
							break;

						default:
							isSubtractItem = null;
							isSubtractPoints = null;
							doTwice = false;
							break;
					}

					isSubtractItem = 
							(cartOrder.getPaymentStatus().equals(PaymentStatus.PAID) 
									&& cartOrder.getShippingStatus().equals(ShippingStatus.DELIVERED) 
									&& OrderStatus.COMPLETED.equals(updateStatus)) 
							|| 
							(OrderStatus.CANCELLED.equals(updateStatus));
					
					if(isSubtractItem != null && items != null && !items.isEmpty())
					{
						for(int i = 0; i < (doTwice
							? 2
							: 1); i++)
						{
							for(final CartOrderItem coi : items)
							{
								final CategoryItem item = CategoryItemUtil.getItemFromCartOrder(coi);
								
							}
						}
					}
					
					if(isSubtractPoints != null && member != null)
					{
						for(int i = 0; i < (doTwice
							? 2
							: 1); i++)
						{
							//GurkkaMemberUtil.updateMemberPointsFromCart(company, member, items, isSubtractPoints);
						}
					}
				}
			}
			
			// update order
			cartOrderDelegate.update(cartOrder);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
			return "Order Status not updated.";
		}
		return "Order Status successfully updated.";
	}

	private void updateMemberPoints(Member member, CartOrder cartOrder) {
		try{
			member = memberDelegate.find(member.getId());
			Double currentPoint = 0D;
			if(member.getInfo5() != null){
				try{
					currentPoint += Double.parseDouble(member.getInfo5());
				}catch(Exception e){}
			}
			
			currentPoint += MemberAction.computeMemberPoints(cartOrder, member);
			
			member.setInfo5("" + currentPoint);
			
			memberDelegate.update(member);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String updatePaymentStatus(String cartOrderID, String paymentStatus)
	{
		try
		{
			// get cart order
			final Long orderID = Long.parseLong(cartOrderID);
			final CartOrder cartOrder = cartOrderDelegate.find(orderID);
			PaymentStatus curPayStat = cartOrder.getPaymentStatus();
			PaymentStatus newPayStat = cartOrder.getPaymentStatus();
			Boolean isSuccessUpdate = false;
			if(cartOrder.getCompany().getName().equalsIgnoreCase("gurkkatest")){
				setCartOrder(cartOrder);
			}
			// update enum status
			for(final PaymentStatus currentPaymentStatus : PaymentStatus.values())
			{
				if(currentPaymentStatus.toString().equalsIgnoreCase(paymentStatus))
				{
					cartOrder.setPaymentStatus(currentPaymentStatus);
					newPayStat = currentPaymentStatus;
				}
			}
			// update order
			isSuccessUpdate = cartOrderDelegate.update(cartOrder);
			if(isSuccessUpdate && cartOrder.getCompany().getName().equalsIgnoreCase("gurkkatest")){
				if(curPayStat == PaymentStatus.PENDING && newPayStat == PaymentStatus.PAID && cartOrder.getPaymentType() != PaymentType.COPU && cartOrder.getPaymentType() != PaymentType.COD){
					sendEmail(cartOrder.getMember(), cartOrder);
				}
			}
			
			
			if(isSuccessUpdate && newPayStat == PaymentStatus.PAID && !PaymentStatus.PAID.equals(curPayStat)){
				if(company.getName().equalsIgnoreCase("purenectar")){
					String paymentType = cartOrder.getInfo1();
					Member member = cartOrder.getMember();
					StringBuffer content = new StringBuffer();
					List<CartOrderItem> cartOrderItems = cartOrder.getItems();
					content.append("<center><img src='http://purenectar.co/images/logo-black-160px.png' /></center><br><br>");
					if("BDO Deposit".equals(paymentType) || "BPI Deposit".equals(paymentType)){
						
						content.append("Greetings from Pure Nectar<br><br>");
						content.append("<p>This is to confirm that your payment for your order " + cartOrder.getId() + " was succesfully completed through Bank Deposit");
						content.append("</p><br><p>For payment specific inquiries, you may e-mail <u>verge@fruitmagic.com.ph</u>.<br>");
						content.append("For product specific inquiries or questions regarding the status of your order, please email <u>melissa@fruitmagic.com.ph</u>.<br><br>");
						content.append("<br>Thank you very much and we hope you enjoy your Pure Nectar juices!<br><br>");
						content.append("Pure Nectar Team");
					}else{
						content.append("Dear " + member.getFirstname());
						content.append("<br/><br/>");
						content.append("Your payment was successful. Your order number is " + cartOrder.getId() + ", please quote this in any correspondence with us.");
						content.append("<br/><br/>");
						content.append("Below is a summary of what you have ordered.<br/></br>");
						
						
						content.append("ORDER DETAILS");
						content.append("<br/><br/>");
						content.append("<table padding=5>");
						content.append("<tr>");
						content.append("<th>ITEM</th>");
						content.append("<th>QUANTITY</th>");
						content.append("<th>PRICE</th>");
						content.append("</tr>");
						
						
						for(CartOrderItem cartOrderItem : cartOrderItems) {
							content.append("<tr>");
							content.append("<td><img style='width:42px;height:42px;' src=\"http://"+company.getServerName()+"/images/items/"+cartOrderItem.getCategoryItem().getImages().get(0).getOriginal()+"\"> ");
							content.append(cartOrderItem.getCategoryItem().getParent().getName() + "<br/> " + cartOrderItem.getCategoryItem().getName());
							content.append("</td>");
							content.append("<td>"+cartOrderItem.getQuantity()+"</td>");
							content.append("<td>Php "+DEFAULT_DECIMAL_FORMAT.format(cartOrderItem.getCategoryItem().getPrice())+"</td>");
							content.append("</tr>");
						}

						content.append("<tr>");
						content.append("<td>Total</td>");
						content.append("<td>Php "+DEFAULT_DECIMAL_FORMAT.format(cartOrder.getTotalPrice())+"</td>");
						content.append("</tr>");
						content.append("<tr>");
						content.append("<td>Payment Method</td>");
						content.append("<td>"+cartOrder.getInfo1()+"</td>");
						content.append("</tr>");		
						content.append("</table>");
						
						content.append("<br/><br/>");
						content.append("<b>DELIVER ADDRESS</b>");
						content.append("<br/><br/>");
						content.append("Address 1: "+ cartOrder.getAddress1());
						content.append("<br/");
						content.append("Address 2: "+ cartOrder.getAddress2());
						content.append("<br/><br/>");
						
						content.append("Cheers!<br/>");
						content.append("Fruit Magic Pure Nectar Team");
					}
					
					EmailSenderAction emailSenderAction = new EmailSenderAction();
					emailSenderAction.sendEmailConfirmationPureNectar(company, member.getEmail(), "Pure Nectar - Paid(" + paymentType + ")", content, null);
				}
			}
		
			
		}
		catch(final Exception e)
		{
			return "Payment Status not updated.";
		}
		return "Payment Status successfully updated.";
	}

	public String updateShippingStatus(String cartOrderID, String shippingStatus)
	{
		try
		{
			// get cart order
			final Long orderID = Long.parseLong(cartOrderID);
			final CartOrder cartOrder = cartOrderDelegate.find(orderID);
			Boolean isSuccessUpdate = false;
			ShippingStatus curShipStat = cartOrder.getShippingStatus();
			ShippingStatus newShipStat = cartOrder.getShippingStatus();
			// update enum status
			for(final ShippingStatus currentShippingStatus : ShippingStatus.values())
			{
				if(currentShippingStatus.toString().equalsIgnoreCase(shippingStatus))
				{
					cartOrder.setShippingStatus(currentShippingStatus);
				}
			}
			// update order
			isSuccessUpdate = cartOrderDelegate.update(cartOrder);
			
			
			if(isSuccessUpdate && newShipStat == ShippingStatus.DELIVERED){
				if(company.getName().equalsIgnoreCase("purenectar")){
					StringBuffer content = new StringBuffer();
					Member member = cartOrder.getMember();
					List<CartOrderItem> cartOrderItems = cartOrder.getItems();
					
					content.append("<center><img src='http://purenectar.co/images/logo-black-160px.png' /></center><br><br>");
					content.append("Dear " + member.getFirstname());
					content.append("<br/><br/>");
					content.append("Your order number is " + cartOrder.getId() + "at Fruit Magic Pure Nectar has been successfully delivered to the address:");
					
					content.append("<br/><br/>");
					content.append("<b>DELIVER ADDRESS</b>");
					content.append("<br/><br/>");
					content.append("Address: "+ cartOrder.getAddress1());
					content.append("<br/><br/>");
					
					content.append("Enjoy your juices! Wishing you a very happy and healthy body, mind and soul.!<br/>");
					content.append("<br/><br/>");
					
					content.append("Cheers!<br/>");
					content.append("Fruit Magic Pure Nectar Team");
					
					EmailSenderAction emailSenderAction = new EmailSenderAction();
					emailSenderAction.sendEmailConfirmationPureNectar(company, member.getEmail(), "Pure Nectar - Delivered", content, null);
				}
		}
		}catch(final Exception e)
		{
			return "Shipping Status not updated.";
		}
		return "Shipping Status successfully updated.";
	}

	/**
	 * added by Glenn Allen Sapla
	 *
	 * @param cartOrderID
	 * @param orderStatus
	 * @return
	 */
	public String updateOrderItemStatus(String cartOrderID, String[] orderStatus)
	{
		try
		{
			// get cart order
			final Long orderID = Long.parseLong(cartOrderID);
			final CartOrder cartOrder = cartOrderDelegate.find(orderID);
			final List<CartOrderItem> cartItems = cartOrder.getItems();
			final int mod = orderStatus.length;
			Boolean isSuccessfulUpdate;
			
			// update order Item status
			for(int index = 0; index < orderStatus.length; index++)
			{
				final CartOrderItem item = cartItems.get((index) % mod);
				item.setStatus(orderStatus[(index) % mod]);
				cartItems.set((index) % mod, item);
				cartOrder.setItems(cartItems);
			}

			final Member m = new Member();
			if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2")))
			{
				m.setId(0L);
				cartOrder.setMember(m);
			}

			// cartOrder.setStatus(OrderStatus.COMPLETED);
			// update order
			isSuccessfulUpdate = cartOrderDelegate.update(cartOrder);

			
			
			return " " + cartOrder.getTotalPriceOkFormatted();

		}
		catch(final Exception e)
		{
			return "Order Item Status not updated." + e;
		}

	}

	/**
	 * added by Glenn Allen Sapla
	 *
	 * @param cartOrderID
	 * @param orderStatus
	 * @return
	 */
	public String updateOrderName(String cartOrderID, String orderName, String orderAddress1, String orderCity, String orderCountry, String orderZipCode, String orderPhoneNumber,
			String orderEmailAddress)
	{
		String debug = "";
		try
		{

			// get cart order
			final Long orderID = Long.parseLong(cartOrderID);
			final CartOrder cartOrder = cartOrderDelegate.find(orderID);
			debug += "1";
			debug += "2";
			MemberShippingInfo shipi = new MemberShippingInfo();

			if(cartOrder.getShippingInfo() != null)
			{
				shipi = cartOrder.getShippingInfo();
			}

			ShippingInfo shipi2 = new ShippingInfo();

			if(shipi.getShippingInfo() != null)
			{
				shipi2 = shipi.getShippingInfo();
			}

			shipi2.setName(orderName);
			shipi2.setAddress1(orderAddress1);
			shipi2.setCity(orderCity);
			shipi2.setCountry(orderCountry);
			shipi2.setZipCode(orderZipCode);
			shipi2.setPhoneNumber(orderPhoneNumber);
			shipi2.setEmailAddress(orderEmailAddress);

			shipi.setShippingInfo(shipi2);
			cartOrder.setShippingInfo(shipi);

			// update order
			cartOrderDelegate.update(cartOrder);
			debug += "3";
		}
		catch(final Exception e)
		{
			return "Item details not updated." + e + " : " + debug;
		}
		return "Item details successfully updated.";
	}

	/**
	 * displays the shipping information of the cart order
	 * added by Samiel Gerard C. Santos
	 *
	 * @param cartOrderID
	 * @return Shipping Information
	 */
	public String getShippingInfo(String cartOrderID)
	{

		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		// get cart order id
		final Long orderID = Long.parseLong(cartOrderID);
		// load order
		final CartOrder cartOrder = cartOrderDelegate.find(orderID);
		cartOrderItemDelegate.findAll(cartOrder).getList();

		// generate html string containing list
		final StringBuilder htmlString = new StringBuilder();
		
		htmlString.append("<h5>ORDER ID : ");
		htmlString.append(orderID);
		htmlString.append("</h5>");
		htmlString.append("<table>");
		htmlString.append("<tr><font color=\"red\">SHIPPING INFORMATION</font></tr>");

		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Name</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(cartOrder.getName());
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		if(company.getId() != CompanyConstants.TOMATO) 
		{
			htmlString.append("<strong>Address1</strong>");
		}
		else 
		{
			htmlString.append("<strong>Address</strong>");
		}
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(cartOrder.getAddress1());
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		if(company.getId() != CompanyConstants.TOMATO) 
		{
			htmlString.append("<strong>Address2</strong>");
		}
		else 
		{
			htmlString.append("<strong>ZIP Code or Landmarks</strong>");
		}
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		if(cartOrder.getAddress2() != null)
		{
			htmlString.append(cartOrder.getAddress2());
		}
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>City</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		if(cartOrder.getCity() != null)
		{
			htmlString.append(cartOrder.getCity());
		}
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>State</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		if(cartOrder.getState() != null)
		{
			htmlString.append(cartOrder.getState());
		}
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Country</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		if(cartOrder.getCountry() != null)
		{
			htmlString.append(cartOrder.getCountry());
		}
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		if(company.getId() != CompanyConstants.TOMATO)
		{
			htmlString.append("<tr>");
			htmlString.append("  <td>");
			htmlString.append("<strong>Zip Code</strong>");
			htmlString.append("   </td>");
			htmlString.append("  <td>");
			if(cartOrder.getZipCode() != null)
			{
				htmlString.append(cartOrder.getZipCode());
			}
			htmlString.append("   </td>");
			htmlString.append("   </tr>");
		}
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Phone Number</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		if(cartOrder.getPhoneNumber() != null)
		{
			htmlString.append(cartOrder.getPhoneNumber());
		}
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Email Address</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(cartOrder.getEmailAddress());
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("   <tr>");
		htmlString.append("   <td>");
		htmlString.append("<strong>Shipping Status</strong>");
		htmlString.append("   </td>");
		htmlString.append("   <td>");
		htmlString.append(cartOrder.getShippingStatus());
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td colspan=\"3\">");
		htmlString.append("Comments or special instructions about this order : ");
		htmlString.append("   </td></tr><tr><td colspan=\"2\">");
		if(cartOrder.getComments() != null)
		{
			htmlString.append(cartOrder.getComments());
		}
		htmlString.append("   </td>");
		htmlString.append("</tr></table>");

		// return generated html string
		return htmlString.toString();
	}
	
	public String getHiPreOnlineStoreShippingInfo(String cartOrderID) {
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		final Long orderID = Long.parseLong(cartOrderID);
		final CartOrder cartOrder = cartOrderDelegate.find(orderID);
		final StringBuilder htmlString = new StringBuilder();
		
		System.out.println("company is hiprecisiononlinestore");
		htmlString.append("<h5>ORDER NUMBER : ");
		htmlString.append(cartOrder.getTransactionNumber());
		htmlString.append("</h5>");
		htmlString.append("<table>");
		htmlString.append("<tr><font color=\"red\">SHIPPING INFORMATION</font></tr>");

		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Name</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(cartOrder.getName());
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Address</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(cartOrder.getAddress1());
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("<tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Email Address</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(cartOrder.getEmailAddress());
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("   <tr>");
		htmlString.append("   <td>");
		htmlString.append("<strong>Shipping Status</strong>");
		htmlString.append("   </td>");
		htmlString.append("   <td>");
		htmlString.append(cartOrder.getShippingStatus());
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("</table>");
		
		htmlString.append("<table>");
		htmlString.append("<tr><font color=\"red\">DELIVERY DETAILS</font></tr>");
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Delivery Option </strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(cartOrder.getShippingType() != null ? cartOrder.getShippingType() : "");
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		
		if(cartOrder.getShippingType() != null) {
			if(cartOrder.getShippingType().equals(ShippingType.PICKUP)) {
				htmlString.append("<tr>");
				htmlString.append("  <td>");
				htmlString.append("<strong>Branch Name </strong>");
				htmlString.append("   </td>");
				htmlString.append("  <td>");
				htmlString.append(cartOrder.getShippingInfo() != null ? cartOrder.getShippingInfo().getShippingInfo().getAddress1() : "");
				htmlString.append("   </td>");
				htmlString.append("   </tr>");
			}
		}
		
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Branch Address </strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(cartOrder.getShippingInfo() != null ? cartOrder.getShippingInfo().getShippingInfo().getAddress2() : "");
		htmlString.append("   </td>");
		htmlString.append("   </tr>");
		htmlString.append("</table>");
		
		return htmlString.toString();
	}
	
	
	public String getSwapShippingInfo(String cartOrderID) {
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		final Long orderID = Long.parseLong(cartOrderID);
		final CartOrder cartOrder = cartOrderDelegate.find(orderID);
		final StringBuilder htmlString = new StringBuilder();
		
		htmlString.append("<h5>ORDER ID : ");
		htmlString.append(orderID);
		htmlString.append("</h5>");
		htmlString.append("<table>");
		htmlString.append("<tr>");
		htmlString.append("<td>Email</td>");
		htmlString.append("<td>" + cartOrder.getEmailAddress() + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr>");
		htmlString.append("<td>Contact Number</td>");
		htmlString.append("<td>" + cartOrder.getPhoneNumber() + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr><td></td></tr>");
		htmlString.append("<tr><td><font color=\"red\">BILLING ADDRESS</font></td></tr>");
		
		setSwapShippingInfo(htmlString, cartOrder.getBillingCountry(), cartOrder.getBillingName(), cartOrder.getBillingBusiness(), 
				cartOrder.getBillingAddress1(), cartOrder.getBillingAddress2(), cartOrder.getBillingCity(), cartOrder.getBillingState(),
				cartOrder.getBillingZipCode());
		
		htmlString.append("<tr>");
		htmlString.append("<td>&nbsp;</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr><td><font color=\"red\">SHIPPING ADDRESS</font></td></tr>");
		
		setSwapShippingInfo(htmlString, cartOrder.getCountry(), cartOrder.getName(), cartOrder.getBusiness(), 
				cartOrder.getAddress1(), cartOrder.getAddress2(), cartOrder.getCity(), cartOrder.getState(),
				cartOrder.getZipCode());

		htmlString.append("</table>");
		
		return htmlString.toString();
	}
	
	private void setSwapShippingInfo(StringBuilder htmlString, String usTerritory, String name, String business, 
			String floor, String poBox, String city, String state, String zipCode) {
		htmlString.append("<tr>");
		htmlString.append("<td>United States / Canada</td>");
		htmlString.append("<td>" + usTerritory + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr>");
		htmlString.append("<td>Name</td>");
		htmlString.append("<td>" + name + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr>");
		htmlString.append("<td>Business or C/O</td>");
		htmlString.append("<td>" + business + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr>");
		htmlString.append("<td>APT, Suite or Floor</td>");
		htmlString.append("<td>" + floor + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr>");
		htmlString.append("<td>P.O. Box</td>");
		htmlString.append("<td>" + poBox + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr>");
		htmlString.append("<td>City</td>");
		htmlString.append("<td>" + city + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr>");
		htmlString.append("<td>State / Province</td>");
		htmlString.append("<td>" + state + "</td>");
		htmlString.append("</tr>");
		htmlString.append("<tr>");
		htmlString.append("<td>Zip / Postal Code</td>");
		htmlString.append("<td>" + zipCode + "</td>");
		htmlString.append("</tr>");
	}

	private List<CartOrder> orderList;

	public String findMembersOrders(String memberId)
	{
		final StringBuilder htmlString = new StringBuilder();

		final Long m = Long.parseLong(memberId);
		final Member member = memberDelegate.find(m);
		if(m == 0L){
			//orderList = cartOrderDelegate.findAll(company, member);
			//cartOrderDelegate.listAllOrders(company, ITEMS_PER_PAGE, pageNumber-1);
			orderList = cartOrderDelegate.listAllOrders(company, Constant.ITEMS_PER_PAGE, 0);
		}
		else{
			orderList = cartOrderDelegate.findAll(company, member);
		}

		int counter = 0;

		htmlString.append(" <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"8\" class=\"companiesTable\">");

		if(orderList != null && orderList.size() > 0)
		{
			htmlString.append("	<tr >");
			htmlString.append("		<th > Order Date </th>");
			htmlString.append("		<th > ID </th>");
			htmlString.append("		<th > Customer </th>");
			htmlString.append("		<th > Shipping Info </th>");
			htmlString.append("		<th > Items Price </th>");
			htmlString.append("		<th > Shipping Info</th>");
			htmlString.append("		<th > Status </th>");
			htmlString.append("		<th> Action </th>");
			htmlString.append("	</tr>");
			/*
			if(m != 0L){
				htmlString.append("	<tr >");
				htmlString.append("	<td colspan='8'>" + "<div style='float:right;width:21%'><form action='' method='post'>" + "<input type='hidden' name='member_Id' value='" + member.getId() + "'>"
						+ "<input type='submit' value='Download Order details of this Member' class='btnBlue'>" + "</form></div>" + "</td>");
				htmlString.append("	</tr >");
			}
			*/
			for(final CartOrder cartOrder : orderList)
			{
				htmlString.append("<tr " + ((counter % 2 == 1)
					? " class= 'oddRow'"
					: "") + ">");
				counter++;
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getCreatedOn());
				htmlString.append("	</td> ");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getId());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getName());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append("	<a href=\"#\" onClick=\"getShippingInfo(" + cartOrder.getId() + ")\">View</a>");
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getTotalPriceOkFormatted());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getTotalShippingPrice2());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getStatus());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				
				final Integer companyID = company.getId().intValue();
				htmlString.append("	<a href=\"#\" onclick=\"showOrderList(" + cartOrder.getId() + ")\">View</a> | <a href=\"orderdetail.do?order_id=" + cartOrder.getId() + "\" >Edit</a> | ");
				switch(companyID)
				{
					case 152:
						htmlString.append("<a href=\"HPDSdownloadOrder.do?cart_order_id=" + cartOrder.getId() + "\">");
						break;
					case CompanyConstants.GURKKA:
						htmlString.append("<a href=\"downloadOrder.do?cart_order_id=" + cartOrder.getId() + "\" target=\"_blank\">");
						break;
					default:
						htmlString.append("<a href=\"downloadOrder.do?cart_order_id=" + cartOrder.getId() + "\">");
						break;
				}
				htmlString.append("PDF</a> ");

				htmlString.append("	</td>");
				htmlString.append("</tr>");
			}

		}
		else
		{
			htmlString.append("	<tr >");
			htmlString.append("		<th colspan=\"7\"> <strong>No Orders found.</strong></th>");
			htmlString.append("	</tr>");
		}
		htmlString.append("</table>");

		return htmlString.toString();
	}

	public String findMembersOrdersByMemberAndRewardStatusNotes(String memberId, String statusNotes)
	{
		
		final StringBuilder htmlString = new StringBuilder();
		System.out.println("### Status Notes value : "+statusNotes);
		final Long m = Long.parseLong(memberId);
		final Member member = memberDelegate.find(m);
		if(m == 0L){
			//orderList = cartOrderDelegate.findAll(company, member);
			//cartOrderDelegate.listAllOrders(company, ITEMS_PER_PAGE, pageNumber-1);
			if(!statusNotes.equalsIgnoreCase("")){
//				//orderList = cartOrderDelegate.listAllOrdersByStatusNotes(company, GurkkaConstants.REWARD, false, Constant.ITEMS_PER_PAGE, 0);
			}
			else{
				//orderList = cartOrderDelegate.listAllOrdersByStatusNotes(company, GurkkaConstants.REWARD, true, Constant.ITEMS_PER_PAGE, 0);
				
			}
			
		}
		else{
			
		}

		int counter = 0;

		htmlString.append(" <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"8\" class=\"companiesTable\">");

		if(orderList != null && orderList.size() > 0)
		{
			htmlString.append("	<tr >");
			htmlString.append("		<th > Order Date </th>");
			htmlString.append("		<th > ID </th>");
			htmlString.append("		<th > Customer </th>");
			htmlString.append("		<th > Shipping Info </th>");
			htmlString.append("		<th > Items Price </th>");
			htmlString.append("		<th > Shipping Info</th>");
			htmlString.append("		<th > Status </th>");
			htmlString.append("		<th> Action </th>");
			htmlString.append("	</tr>");
			/*
			if(m != 0L){
				htmlString.append("	<tr >");
				htmlString.append("	<td colspan='8'>" + "<div style='float:right;width:21%'><form action='' method='post'>" + "<input type='hidden' name='member_Id' value='" + member.getId() + "'>"
						+ "<input type='submit' value='Download Order details of this Member' class='btnBlue'>" + "</form></div>" + "</td>");
				htmlString.append("	</tr >");
			}
			*/
			for(final CartOrder cartOrder : orderList)
			{
				htmlString.append("<tr " + ((counter % 2 == 1)
					? " class= 'oddRow'"
					: "") + ">");
				counter++;
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getCreatedOn());
				htmlString.append("	</td> ");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getId());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getName());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append("	<a href=\"#\" onClick=\"getShippingInfo(" + cartOrder.getId() + ")\">View</a>");
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getTotalPriceOkFormatted());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getTotalShippingPrice2());
				htmlString.append("	</td>");
				htmlString.append("	<td>");
				htmlString.append(cartOrder.getStatus());
				htmlString.append("	</td>");
				htmlString.append("	<td>");

				final Integer companyID = company.getId().intValue();
				htmlString.append("	<a href=\"#\" onclick=\"showOrderList(" + cartOrder.getId() + ")\">View</a> | <a href=\"orderdetail.do?order_id=" + cartOrder.getId() + "\" >Edit</a> | ");
				switch(companyID)
				{
					case 152:
						htmlString.append("<a href=\"HPDSdownloadOrder.do?cart_order_id=" + cartOrder.getId() + "\">");
						break;
					case CompanyConstants.GURKKA:
						htmlString.append("<a href=\"downloadOrder.do?cart_order_id=" + cartOrder.getId() + "\" target=\"_blank\">");
						break;
					default:
						htmlString.append("<a href=\"downloadOrder.do?cart_order_id=" + cartOrder.getId() + "\">");
						break;
				}
				htmlString.append("PDF</a> ");

				htmlString.append("	</td>");
				htmlString.append("</tr>");
			}

		}
		else
		{
			htmlString.append("	<tr >");
			htmlString.append("		<th colspan=\"7\"> <strong>No Orders found.</strong></th>");
			htmlString.append("	</tr>");
		}
		htmlString.append("</table>");

		return htmlString.toString();
	}
	
	private String generateVoucher(Member member, CartOrder cartOrder) throws JRException, IOException
	{return "";}
	
	
	private String sendEmail(Member member, CartOrder cartOrder) throws JRException, IOException
	{return "";}
	
	private void generateOrderProperties(Map<String, Object> parameters) {
		try{
			Properties props = new Properties();
			props.setProperty("cartOrderId", cartOrder.getId() + "" );
			Hashtable<String, Object> ht = new Hashtable<String, Object>();
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			    String key = entry.getKey();
			    Object value = entry.getValue();
			    if(key != null && value != null){
			    	ht.put(key, "" + value);
			    }else if(key != null && value == null){
			    	ht.put(key, "");
			    }
			}
			
			props.putAll(ht);
			/*System.out.println(props.toString());
			String propsPath = servletContext.getRealPath("");
			propsPath += File.separator + "companies" + File.separator + "gurkkatest" + File.separator + "props";
			File f = new File(propsPath + File.separator + cartOrder.getId() + ".xml");
			OutputStream out = new FileOutputStream(f);
			props.store(out, "Cart Order Properties");*/
			StringWriter writer = new StringWriter();
			props.store(new PrintWriter(writer), "");
			cartOrder.setInfo5(writer.getBuffer().toString());
			cartOrderDelegate.update(cartOrder);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public CartOrder getCartOrder() {
		return cartOrder;
	}
	
	public void setCartOrder(CartOrder cartOrder) {
		this.cartOrder = cartOrder;
	}
	
	private List<CategoryItem> listOfPromoBasketItem(CategoryItem _categoryItem, Integer quantity) {return null;}
	
	public Map<String, CategoryItem> getCategoryItemMap() {
		final List<CategoryItem> listitem = categoryItemDelegate
				.findAllEnabled(company).getList();
		Map<String, CategoryItem> categoryItemMap = new HashMap<String, CategoryItem>();

		for (CategoryItem item : listitem) {
			categoryItemMap.put(String.valueOf(item.getId()), item);
		}

		return categoryItemMap;
	}
}
