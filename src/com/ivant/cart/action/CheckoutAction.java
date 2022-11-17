package com.ivant.cart.action;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;

import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.action.MessageManagementAction;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CategoryItemPackageDelegate;
import com.ivant.cms.delegate.CategoryItemPriceDelegate;
import com.ivant.cms.delegate.CategoryItemPriceNameDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemFileDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.MemberLogDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.OrderItemFileDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.PackageDelegate;
import com.ivant.cms.delegate.PreOrderDelegate;
import com.ivant.cms.delegate.PreOrderItemDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.CategoryItemPrice;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.OrderItemFile;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.PreOrder;
import com.ivant.cms.entity.PreOrderItem;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.YesPayments;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.SavedEmailType;
import com.ivant.cms.enums.ShippingStatus;
import com.ivant.cms.enums.ShippingType;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.cms.ws.rest.client.ClientConfigurationServlet;
import com.ivant.cms.ws.rest.client.SmsClient;
import com.ivant.constants.CompanyConstants;
import com.ivant.constants.PaymentConstants;
import com.ivant.utils.CartOrderUtil;
import com.ivant.utils.CategoryItemUtil;
import com.ivant.utils.CompanyUtil;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.GurkkaEmailUtil;
import com.ivant.utils.NumberUtil;
import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opensymphony.xwork2.Action;
import com.paypal.sdk.exceptions.PayPalException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@SuppressWarnings("deprecation")
public class CheckoutAction
		extends AbstractBaseAction
		implements ServletRequestAware, SessionAware, MemberAware
{

	private static final long serialVersionUID = -6581744371763755201L;
	private static final Logger logger = Logger.getLogger(CheckoutAction.class);
    private static final String SECURE_SECRET = "FA98E031C355F612B74AB5D674B82644";
    private static final String MONERIS = "moneris";
    private static final String GLOBAL_PAY = "globalpay";
    private static final String DRAGON_PAY = "dragonpay";
    private static final String BANK = "bank";
    private static final String SHIPPING_COST = "Shipping Cost";
    private static final String DISCOUNT = "Discount";
    private static final String SPACE = " ";
    private static final String PNG_EXTENSION = ".png";
	public HttpServletRequest request;
	private final NumberFormat formatter = new DecimalFormat("#0.00");
	private final ItemFileDelegate itemFileDelegate = ItemFileDelegate.getInstance();
	private final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private Map<Object, Object> sessionMap;
	private Paypal paypal;
	private String checkoutUrl;
	private String pToken;
	private String cartItem;
	private float vat;
	private Boolean iagree;
	private Boolean amtothers;
	private String uom[];
	private CartOrder cartOrder;
	private CartOrderItem cartOrderItem;
	private ShoppingCart shoppingCart;
	private List<ShoppingCartItem> cartItemList;
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	private final MemberShippingInfoDelegate memberShippingInfoDelegate = MemberShippingInfoDelegate.getInstance();
	private final MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
	private final MemberFileItemDelegate memberFileItemDelegate = MemberFileItemDelegate.getInstance();
	private final ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
	private final CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	private final ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	private final OrderItemFileDelegate orderItemFileDelegate = OrderItemFileDelegate.getInstance();
	private static final LogDelegate logDelegate = LogDelegate.getInstance();
	private final PreOrderDelegate preOrderDelegate = PreOrderDelegate.getInstance();
	private final PreOrderItemDelegate preOrderItemDelegate = PreOrderItemDelegate.getInstance();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private final CategoryItemPriceDelegate categoryItemPriceDelegate = CategoryItemPriceDelegate.getInstance();
	private final CategoryItemPriceNameDelegate categoryItemPriceNameDelegate = CategoryItemPriceNameDelegate.getInstance();
	private final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	private final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private final PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	private static final RegistrationItemOtherFieldDelegate registrationDelegate = RegistrationItemOtherFieldDelegate.getInstance();
	private final MemberLogDelegate memberLogDelegate = MemberLogDelegate.getInstance();
	private final CompanySettingsDelegate companySettingsDelegate = CompanySettingsDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	
	private final PackageDelegate packageDelegate = PackageDelegate.getInstance();
	private final CategoryItemPackageDelegate categoryItemPackageDelegate = CategoryItemPackageDelegate.getInstance();
	
	private CategoryItem categoryItem;
	private OrderItemFile orderItemFile;
	private ItemFile itemFile;
	private ItemDetail itemDetail;
	private String emailBody = "";
	private String notificationMessage;
	private String errorMessage;
	private String orderType;
	private String[] itemDescription = new String[0];
	private String[] itemIds = new String[0];
	private ArrayList<CategoryItem> itemList = new ArrayList<CategoryItem>();
	private double itemTotalPrice = 0;
	private double totalPrice = 0;
	private int transactionNumber = 0;
	private PreOrder preOrder;
	private List<PreOrderItem> preOrderItemList;
	private String gcIds;
	private long cartId;
	private YesPayments yesPayments;
	private String dragonpayURL;
	private String globalpayURL;
	private ObjectList<CartOrderItem> cartOrderItems;
	private PaymentType paymentType;
	private ShippingType shippingType;
	private Long cartOrderId = 0L;
	private InputStream inputStream;
	
	private static final NumberFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	
	
	// This is an array for creating hex chars
	private static final char[] HEX_TABLE = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	@Override
	public void prepare() throws Exception
	{
		if(!StringUtils.isEmpty(request.getParameter("deliverytype")))
		{
			final String shippingType = StringUtils.trimToNull(request.getParameter("deliverytype"));
			if(shippingType != null)
			{
				if(shippingType.equalsIgnoreCase("d"))
				{
					this.shippingType = ShippingType.DELIVERY;
				}
				else if(shippingType.equalsIgnoreCase("p"))
				{
					this.shippingType = ShippingType.PICKUP;
				}
			}
		}
		initShoppingCart();
	}

	public String noLogInCartCheckOut()
	{
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getExpressCheckout()
	{
		logger.info("-------------------getExpressCheckout----------------------------");
		try {
		List<CategoryItem> catItems1 = new ArrayList<CategoryItem>();
		double totalAmount = 0.00;
		CartOrder cartOrder = null;
		
		if(request.getParameter("orderId") != null)
		{
			cartOrder = cartOrderDelegate.find(Long.parseLong(request.getParameter("orderId")));
		}
		
		//wilcon
		if(company.getName().equalsIgnoreCase(CompanyConstants.WILCON)){
			cartOrder = (CartOrder) request.getSession().getAttribute("cartOrder");
			long orderId = cartOrder.getId();
			cartOrder = cartOrderDelegate.find(orderId);
			List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
			for(CartOrderItem item : cartOrder.getItems()) {
				item.setOrder(cartOrder);
				cartOrderItems.add(item);
			}
			cartOrderItemDelegate.batchInsert(cartOrderItems);
			cartOrder.setItems(cartOrderItems);
			
		}
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE) || company.getName().equalsIgnoreCase("saltswim")){
			cartOrder = (CartOrder) request.getSession().getAttribute("cartOrder");
	
			memberShippingInfoDelegate.insert(cartOrder.getShippingInfo());
			cartOrderDelegate.insert(cartOrder);
			
			List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
			for(CartOrderItem item : cartOrder.getItems()) {
				item.setOrder(cartOrder);
				cartOrderItems.add(item);
			}
			cartOrderItemDelegate.batchInsert(cartOrderItems);
			cartOrder.setItems(cartOrderItems);
			
			System.out.println("-----------------------------Saltswim Cart Order Items-------------------");
		}
		
		if(company.getName().equalsIgnoreCase("purenectar")) {
			logger.info("-----------------------------Purenectar Cart Order Items-------------------");
			
			cartOrder = (CartOrder) request.getSession().getAttribute("cartOrder");
			
			cartOrder.setTotalShippingPrice2(150.00);
			cartOrder.setTotalShippingPrice(150.00);
			memberShippingInfoDelegate.insert(cartOrder.getShippingInfo());
			
			logger.info("-------------------------------Cart Order Shipping Info Inserted------------------");
			
			long orderId = cartOrderDelegate.insert(cartOrder);
			
			
			logger.info("-------------------------------Cart Order #" + orderId + " Inserted------------------");
			cartOrder = cartOrderDelegate.find(orderId);
			
			List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
			
			int itemSessionId = 1;
			
			for(CartOrderItem item : cartOrder.getItems()) {
				logger.info("--------------------------Cart Order Item Name: " + item.getCategoryItem().getName());
				item.setOrder(cartOrder);
				cartOrderItems.add(item);
				catItems1.add(item.getCategoryItem());
				itemSessionId++;
			}
			
			CategoryItem catItem = new CategoryItem();
			catItem.setId(0L);
			catItem.setName("Shipping Fee");
			catItem.setPrice(Double.parseDouble("150.00"));
			catItem.setOtherDetails("1");
			catItem.setDescription("Shipping Fee");	
			catItem.setSku("" + itemSessionId);
		
			catItems1.add(catItem);
			
			cartOrderItemDelegate.batchInsert(cartOrderItems);
			cartOrder.setItems(cartOrderItems);
			
			totalAmount += 150.00;
			
			
			logger.info("-----------------------------Purenectar Total Amount: PHP " + totalAmount + "-------------------");
		}
		
		if(cartOrder != null)
		{
			catItems1 = getAllCategoryItems(cartOrder);
		}
		

		if(catItems1 != null && catItems1.size() != 0)
		{
			for(final CategoryItem catItem : catItems1)
			{
				totalAmount += (catItem.getPrice() * Double.parseDouble(catItem.getOtherDetails()));
			}
			
			
		}

		PaypalAction paypalAction = null;
		String paypalEnvironment = "";
		if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE)) {
			paypalEnvironment = (String) request.getSession().getAttribute("paypal_environment");
			String username = (String) request.getSession().getAttribute("sandbox_username");
			String password = (String) request.getSession().getAttribute("sandbox_password");
			String signature = (String) request.getSession().getAttribute("sandbox_signature");
			if(paypalEnvironment.equalsIgnoreCase("sandbox")) {
				paypalAction = new PaypalAction(username, password, signature, paypalEnvironment);
			} else {
				paypal = new Paypal(company);
				//paypalAction = new PaypalAction(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), paypalEnvironment);
			}
		} else if(company.getName().equalsIgnoreCase("purenectar") || company.getName().equalsIgnoreCase("saltswim")) {
			paypalAction = new PaypalAction(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), paypalEnvironment);
	
			System.out.println("----------------------------- Paypal Action is " + paypalAction + "-------------------");
		}else if(company.getName().equalsIgnoreCase(CompanyConstants.WILCON)) {//wilcon
			paypalAction = new PaypalAction(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), paypalEnvironment);
		} else {
			paypal = new Paypal(company);
			if(company.getName().equals("totalqueue2") && request.getSession().getAttribute("testOnly") != null) {
				Boolean env = (Boolean) request.getSession().getAttribute("testOnly");
				if(env) {
					paypal = new Paypal(company, "sandbox");
				}
			}
		}
		cartItem = null;

		if(request.getParameter("token") != null)
		{
			logger.debug(" TOKEN: " + request.getParameter("token"));
			
			HashMap result = null;
			if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE) && paypalEnvironment.equalsIgnoreCase("sandbox")) {
				result = paypalAction.GetShippingDetails(request.getParameter("token"));
			} else if(company.getName().equalsIgnoreCase(CompanyConstants.PURE_NECTAR) || company.getName().equalsIgnoreCase("saltswim")) {
				
				result = paypalAction.GetShippingDetails(request.getParameter("token"));
				cartItemList = new ArrayList<ShoppingCartItem>();
				logger.info("----------------------------- Paypal Results: " + result + "   -------------------");
			} else if(company.getName().equalsIgnoreCase(CompanyConstants.WILCON)) {//wilcon
				result = paypalAction.GetShippingDetails(request.getParameter("token"));
				cartItemList = new ArrayList<ShoppingCartItem>();
			} else {
				result = paypal.GetShippingDetails(request.getParameter("token"));
			}
			
			HashMap doResponse = new HashMap();
			if(result.get("ACK").toString().equalsIgnoreCase("Success"))
			{
				String strAck = result.get("ACK").toString();
				logger.info("(strAck !=null && (strAck.equalsIgnoreCase(\"Success\")))===" + (strAck != null && (strAck.equalsIgnoreCase("Success"))));
				if(strAck != null && (strAck.equalsIgnoreCase("Success")))
				{
					logger.info("THE CHECKOUT STATUS IS---------------------->");
					logger.info(result.get("CHECKOUTSTATUS").toString());
					request.setAttribute("checkOutStatus", result.get("CHECKOUTSTATUS").toString());
					logger.info(" FINAL response result: " + result.toString());
					logger.info("THE PAYMENT VALUE IS " + (String) sessionMap.get("paymentvalue"));

					if(sessionMap.get("paymentvalue") != null || totalAmount > 0.0 || company.getName().equalsIgnoreCase("Aplic") || company.getName().equalsIgnoreCase("Aplic2")
							|| request.getParameter("paymentvalue") != null)
					{
						logger.info("--------------(sessionMap.get(\"paymentvalue\"))" + sessionMap.get("paymentvalue") + "_________totalAmount___" + totalAmount);
						String payment;
						if(totalAmount > 0)
						{// for purple tag Only
							payment = "" + totalAmount;
						}
						else
						{
							payment = (String) sessionMap.remove("paymentvalue");
						}

						if(sessionMap.get("cartSingleItem") != null){
						
							cartItem = sessionMap.remove("cartSingleItem").toString();
						}

						if(company.getPalCurrencyType() != null)
						{
							if(cartItem != null || company.getName().equalsIgnoreCase("Aplic") || company.getName().equalsIgnoreCase("Aplic2"))
							{
								CategoryItem catItem;
								// this company is for registration.
								if(company != null && company.getName().equalsIgnoreCase("Aplic"))
								{
									payment = request.getParameter("paymentvalue");
									catItem = getRegistrationDetails();
								}
								else if(company != null && company.getName().equalsIgnoreCase("Aplic2"))
								{
									payment = request.getParameter("paymentvalue");
									catItem = getRegistrationDetails();
								}
								else
								{
									catItem = categoryItemDelegate.find(Long.parseLong(cartItem));
								}

								doResponse = paypal.ConfirmPayment(formatter.format(Double.parseDouble(payment)), result, company.getPalCurrencyType(), null, catItem);
							}
							else
							{
								if(cartItemList.size() > 0)
								{
									doResponse = paypal.ConfirmPayment(formatter.format(Double.parseDouble(payment)), result, company.getPalCurrencyType(), cartItemList, null);
								}
								else
								{
									if(catItems1 != null && catItems1.size() != 0)
									{
										if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE) && paypalEnvironment.equalsIgnoreCase("sandbox")) {
											paypalAction.setCategoryItems(catItems1);
											doResponse = paypalAction.ConfirmPayment(formatter.format(Double.parseDouble(payment)), result, company.getPalCurrencyType());
										} else if(company.getName().equalsIgnoreCase("purenectar") || company.getName().equalsIgnoreCase("saltswim")) {
											paypalAction.setCategoryItems(catItems1);
											doResponse = paypalAction.ConfirmPayment(formatter.format(Double.parseDouble(payment)), result, company.getPalCurrencyType());
										}else if(company.getName().equalsIgnoreCase(CompanyConstants.WILCON)) {//wilcon
											paypalAction.setCategoryItems(catItems1);
											doResponse = paypalAction.ConfirmPayment(formatter.format(Double.parseDouble(payment)), result, company.getPalCurrencyType());
										} else {
											paypal.setCategoryItems(catItems1);
											doResponse = paypal.ConfirmPayment(formatter.format(Double.parseDouble(payment)), result, company.getPalCurrencyType(), null, null);
										}
									}
								}
							}
						}

						logger.debug("Test PHP " + company.getPalCurrencyType());

						if(doResponse.get("ACK") != null)
						{
							strAck = doResponse.get("ACK").toString();
						}

						if(strAck != null && (strAck.equalsIgnoreCase("Success")))
						{
							// insert payment transaction
							logger.info(" FINAL do response: " + doResponse.toString());

							addPaymentTransaction(doResponse);

							sessionMap.remove("paymentvalue");
							sessionMap.remove("cartSingleItem");
						}

						if(company.getId() == CompanyConstants.VISIONARY || company.getId() == CompanyConstants.GAMEPLACE)
						{
							if(company.getId() == CompanyConstants.VISIONARY) {
							// create new order
								createOrder();

							// create order item list
								initOrderItemList();
							} 
							
							if(company.getId() == CompanyConstants.GAMEPLACE) {
								
								List<CategoryItem> catItems = new ArrayList<CategoryItem>();
								catItems = (List<CategoryItem>) sessionMap.get("GameplacePromoShoppingCartItems");
								final Member mem = memberDelegate.find(Long.parseLong(request.getParameter("memberId")));
								cartOrder.setStatus(OrderStatus.COMPLETED);
								cartOrderDelegate.update(cartOrder);
								sendMailToGameplacePromoCustomer(catItems,mem,cartOrder.getId());
								
								return SUCCESS;
							}
							
						}

						if(company.getId() == CompanyConstants.ECOMMERCE || company.getId() == CompanyConstants.MDT || company.getId() == CompanyConstants.GIFTCARD || company.getId() == CompanyConstants.POLIKINETIC)
						{
							createOrder();
							initOrderItemList();
						}

						if((company.getId() == CompanyConstants.ONLINE_DEPOT || company.getId() == CompanyConstants.DRUGASIA || company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST) && member != null)
						{
							String name = "";
							String email = "";
							String address1 = "";
							String phonenumber = "";

							if(sessionMap.get("name") != null)
							{
								name = sessionMap.get("name").toString();
							}

							if(sessionMap.get("email") != null)
							{
								email = sessionMap.get("email").toString();
							}

							if(sessionMap.get("address1") != null)
							{
								address1 = sessionMap.get("address1").toString();
							}

							if(sessionMap.get("phonenumber") != null)
							{
								phonenumber = sessionMap.get("phonenumber").toString();
							}

							final EmailSenderAction emailSender = new EmailSenderAction();
							company.setEmail(company.getEmail());
							emailSender.setEmail(email);
							emailSender.setSubject("The " + company.getNameEditable() + " Order Form Submission Pay via Paypal.");
							emailSender.setCompany(company);
							emailSender.setModeOfPayment("paypal");
							String message = "";
							message += "";

							totalAmount = 0.00;
							String shippingCost = "";
							String discount = "";
							for(final ShoppingCartItem cI : cartItemList)
							{
								if(cI.getItemDetail().getName().indexOf("Discount") != -1)
								{
									if(Math.abs(cI.getItemDetail().getPrice()) > 0.0)
									{
										discount += "<tr>";
										discount += "<td align=\"left\">Senior Citizen Discount (20%) </td>";
										discount += "<td align=\"center\"></td>";
										discount += "<td align=\"right\"></td>";
										discount += "<td align=\"right\"><strong>" + formatter.format(Math.abs(cI.getItemDetail().getPrice())) + "</strong></td>";
										discount += "</TR>	";
										totalAmount += cI.getItemDetail().getPrice();
									}
								}
								else if(cI.getItemDetail().getName().indexOf("Shipping Cost") == -1)
								{
									message += "<TR>";
									message += "<td align=\"left\">" + cI.getItemDetail().getName() + "</td>";
									message += "<td align=\"center\">" + cI.getQuantity() + "</td>";
									message += "<td align=\"right\"><strong>" + formatter.format(cI.getItemDetail().getPrice()) + "</strong></td>";
									message += "<td align=\"right\"><strong>" + formatter.format((cI.getItemDetail().getPrice() * cI.getQuantity())) + "</strong></td>";
									message += "</TR>	";
									totalAmount += cI.getItemDetail().getPrice() * cI.getQuantity();
								}
								else
								{
									totalAmount += cI.getItemDetail().getPrice();
									shippingCost += "<TR>";
									shippingCost += "<td align=\"left\">Shipping Price </td>";
									shippingCost += "<td align=\"center\"></td>";
									shippingCost += "<td align=\"right\"></td>";
									shippingCost += "<td align=\"right\"><strong>" + formatter.format((cI.getItemDetail().getPrice())) + "</strong></td>";
									shippingCost += "</TR>	";
								}
							}
							message += shippingCost;
							message += discount;

							message += "<tr>";
							message += "<td align=\"left\"  style=\"border-top: 1px solid #dddddd;\"></td>";
							message += "<td align=\"center\"  style=\"border-top: 1px solid #dddddd;\"></td>";
							message += "<td align=\"right\"  style=\"border-top: 1px solid #dddddd;\"><strong>Total :</strong></td>";
							message += "<td align=\"right\"  style=\"border-top: 1px solid #dddddd; color:#0066CC\"><strong>" + formatter.format(totalAmount) + "</strong></td>";
							message += "</tr>";

							emailSender.setMessage(message);

							String filepath = "";

							// workaround for inconsistency of session//
							if(session != null && session.get("filepath") != null)
							{
								filepath = session.get("filepath").toString();
							}
							else if(sessionMap != null && sessionMap.get("filepath") != null)
							{
								filepath = sessionMap.get("filepath").toString();
							}

							emailSender.sendEmailPaymentInformation(name, email, phonenumber, address1, null, filepath,"","","");

							paymentType = PaymentType.PAYPAL;
							createOrder();
							initOrderItemList();
							if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
							{
								sendGurkkaEmailShipping(member, 0D, "NEWPAYPAL");

								Double savings = 0D;
								final ObjectList<CartOrderItem> items = cartOrderItemDelegate.findAll(cartOrder);
								for(final CartOrderItem item : items)
								{
									if(item.getItemDetail().getPrice() == 0 && item.getId() > 0)
									{
										final CategoryItem i = categoryItemDelegate.find(item.getItemDetail().getRealID());
										if(i != null)
										{
											savings += i.getPrice() * item.getQuantity();
										}
									}
								}
								sendGurkkaEmailShipping(member, savings, "Paypal");
							}
						}

						if(("purpletag".equalsIgnoreCase(company.getName()) || "purpletag2".equalsIgnoreCase(company.getName())))
						{
							logger.info("Saving Order");
							return saveToOrder(catItems1); // It will only return SUCCESS;
						}
						
						if(("adeventsmanila".equalsIgnoreCase(company.getName())))
						{
							logger.info("Saving Order");
							return saveToOrder(catItems1); // It will only return SUCCESS;
						}
						
						if(("life".equalsIgnoreCase(company.getName())))
						{
							cartOrder.setIsValid(true);
							cartOrder.setStatus(OrderStatus.COMPLETED);
							cartOrder.setPaymentStatus(PaymentStatus.PAID);
							cartOrderDelegate.update(cartOrder);
							//logger.info("Saving Order");
							return saveToOrder(catItems1); // It will only return SUCCESS;
						}
						
						if(("hiprecisiononlinestore".equalsIgnoreCase(company.getName()))) {
							cartOrder.setIsValid(true);
							cartOrder.setStatus(OrderStatus.COMPLETED);
							cartOrder.setPaymentStatus(PaymentStatus.PAID);
							cartOrderDelegate.update(cartOrder);
							request.getSession().removeAttribute("noLogInCartItems");
							request.getSession().removeAttribute("order-id");
							
							request.getSession().removeAttribute("cartOrder");
							request.getSession().removeAttribute("paypal_environment");
							
						}
						//wilcon
						if(company.getName().equalsIgnoreCase(CompanyConstants.WILCON)){
							
							String name = (String) request.getSession().getAttribute("name_");
							String email = (String) request.getSession().getAttribute("email_");
							String contactNumber = (String) request.getSession().getAttribute("mobile_");
							String address = (String) request.getSession().getAttribute("address_");
							String additional_info = (String) request.getSession().getAttribute("additionalinfo_");
							String branch = (String) request.getSession().getAttribute("branch_");
							String datetime = (String) request.getSession().getAttribute("datetime_");
							
							cartOrder.setIsValid(true);
							cartOrder.setStatus(OrderStatus.COMPLETED);
							cartOrder.setPaymentStatus(PaymentStatus.PAID);
							cartOrderDelegate.update(cartOrder);
							
							List<SavedEmail> num = savedEmailDelegate.findEmailByFormName(company, "Wilcon Order Confirmation Email", Order.desc("updatedOn")).getList();
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
							
							orderContent.append("<strong>PAYER INFO</strong><br>")
										.append("Name: " + name + "<br>")
										.append("Email: " + email + "<br>")
										.append("Contact number: " + contactNumber + "<br>")
										.append("Address: " + address + "<br>")
										.append("Preferred Branch for pick up: " + branch)
										.append("Preferred date and time of pick up: " + datetime)
										.append("Additional Information: " + additional_info + "<br><br>");
							
							orderContent.append("<strong>ORDER ITEMS</strong></br>")
										.append("<table class='order_items_list' style='border: 1px solid #000;'><tr><th width='150' style='border: 1px solid #000;padding: 10px;'>Name</th>")
										.append("<th width='150' style='border: 1px solid #000;padding: 10px;'>Price</th>")
										.append("<th width='150' style='border: 1px solid #000;padding: 10px;'>Quantity</th>")
										.append("<th width='150' align='right' style='border: 1px solid #000;padding: 10px;'>Total</th>")
										.append("</tr>");
							
							for(CategoryItem item : catItems1) {
								CategoryItem del = categoryItemDelegate.find(item.getId());
								int stock = Integer.parseInt(del.getCategoryItemOtherFieldMap().get("Stock").getContent());
								boolean haveSale = false;
								boolean haveB1T1 = false;
								
								Double itemPrice = item.getPrice();
								Double originalPrice = item.getPrice();
								Double discountedPrice = item.getPrice();
								Double itemTotalPrice = item.getPrice() * Integer.parseInt(item.getOtherDetails());
								Double dis = 0.0;
								
								/*List<CategoryItemOtherField> cof = del.getCategoryItemOtherFields();
								if(cof.size() > 0){
									for(int x = 0; x < cof.size(); x++){
										if(cof.get(x).getOtherField().getName().equals("Sale")){
											haveSale = true;
										}
										if(cof.get(x).getOtherField().getName().equals("Buy 1 Take 1")){
											haveB1T1 = true;
										}
									}
								}*/
								if(isNull(del.getInfo1())){
									orderContent.append("<tr>")
									.append("<td style='border: 1px solid #000;padding: 10px;'>" + item.getName());
									if(haveB1T1){
										if(!del.getCategoryItemOtherFieldMap().get("Buy 1 Take 1").getContent().equals("")){
											orderContent.append("<br><br>Buy 1 Take 1 (*1 product quantity consists of 2 items)");
										}
									}
									orderContent.append("</td>");
									if(haveSale){
										dis = Double.parseDouble(del.getCategoryItemOtherFieldMap().get("Sale").getContent()) * Integer.parseInt(item.getOtherDetails());
										if(!del.getCategoryItemOtherFieldMap().get("Sale").getContent().equals("")){
											orderContent.append("<td align='center' style='border: 1px solid #000;padding: 10px;'>" + item.getPrice() + "<br>&#8369;" + dis + " Off</td>");
										}else{
											orderContent.append("<td align='center' style='border: 1px solid #000;padding: 10px;'>" + item.getPrice() + "</td>");
										}
									}else{
										orderContent.append("<td align='center' style='border: 1px solid #000;padding: 10px;'>" + item.getPrice() + "</td>");
									}
									discountedPrice = itemTotalPrice - dis;
									orderContent.append("<td align='center' style='border: 1px solid #000;padding: 10px;'>" + item.getOtherDetails() + "</td>")
										.append("<td align='right' style='border: 1px solid #000;padding: 10px;'>" + discountedPrice + "</td>")
										.append("</tr>");
									
									if(haveB1T1){
										if(!del.getCategoryItemOtherFieldMap().get("Buy 1 Take 1").getContent().equals("")){
											stock = stock - (Integer.parseInt(item.getOtherDetails()) * 2);
											del.getCategoryItemOtherFieldMap().get("Stock").setContent(String.format("%s", stock));
										}else{
											stock = stock - Integer.parseInt(item.getOtherDetails());
											del.getCategoryItemOtherFieldMap().get("Stock").setContent(String.format("%s", stock));
										}
									}else{
										stock = stock - Integer.parseInt(item.getOtherDetails());
										del.getCategoryItemOtherFieldMap().get("Stock").setContent(String.format("%s", stock));
									}
									
									
									
									
									
									
								}else{
									orderContent.append("<tr style='border: 1px solid #000;padding: 10px;'>")
										.append("<td style='border: 1px solid #000;padding: 10px;'>" + item.getName());
									orderContent.append("<ul>");
									IPackage newPackage = packageDelegate.find(Long.parseLong(item.getInfo1()));
									List<CategoryItemPackage> packItems = categoryItemPackageDelegate.findAll(newPackage.getId()).getList();
									double totalSupposePrice = 0.0;
									for(CategoryItemPackage cip : packItems){
										CategoryItem itm = cip.getCategoryItem();
										orderContent.append("<li>" + itm.getName() + "</li>");
										totalSupposePrice += itm.getPrice();
									}
									double totalDiscount = totalSupposePrice - item.getPrice();
									discountedPrice = itemTotalPrice - dis;
									orderContent.append("</ul></td>");
									orderContent.append("<td align='center' style='border: 1px solid #000;padding: 10px;'>" + item.getPrice() + "</td>");
									orderContent.append("<td align='center' style='border: 1px solid #000;padding: 10px;'>" + item.getOtherDetails() + "</td>")
										.append("<td align='right' style='border: 1px solid #000;padding: 10px;'><strong>&#8369;" + discountedPrice + "</strong> (Saved &#8369;" + totalDiscount + ")</td>")
										.append("</tr>");
									
									stock = stock - Integer.parseInt(item.getOtherDetails());
									del.getCategoryItemOtherFieldMap().get("Stock").setContent(String.format("%s", stock));
								}
								categoryItemDelegate.update(del);
								totalPrice += discountedPrice;
							}
							orderContent.append("<tr style='border: 1px solid #000;padding: 10px;'>")
								.append("<td style='border: 1px solid #000;padding: 10px;'></td>")
								.append("<td style='border: 1px solid #000;padding: 10px;'></td>")
								.append("<td align='right' style='border: 1px solid #000;padding: 10px;'>Total: </td>")
								.append("<td align='right' style='border: 1px solid #000;padding: 10px;'><strong>&#8369;" + Double.toString(totalPrice) + "</strong></td>")
								.append("</tr></table><br><br>");
							
							sendOrderEmail(orderContent.toString());
							sendConfirmEmail(name, email, orderContent.toString());
							
							
							request.getSession().removeAttribute("cartOrder");
							Member wilconMember = cartOrder.getMember();
							List<ShoppingCartItem> sci = shoppingCartItemDelegate.findAllByCompany(companyDelegate.find(CompanyConstants.WILCON)).getList();
							for(ShoppingCartItem ss : sci){
								if(ss.getItemDetail().getUOM().equals(String.format("%s", wilconMember.getId()))){
									shoppingCartItemDelegate.delete(ss);
								}
							}
						}
						
						if(company.getName().equalsIgnoreCase("purenectar")) {
							cartOrder.setIsValid(true);
							cartOrder.setStatus(OrderStatus.COMPLETED);
							cartOrder.setPaymentStatus(PaymentStatus.PAID);
							cartOrder.setTotalShippingPrice2(150.00);
							cartOrder.setTotalShippingPrice(150.00);
							if(cartOrderDelegate.update(cartOrder)){
								logger.info("-------------------Update Purenectar Cart Order--------------");
								
							}
							request.getSession().setAttribute("cartOrder", cartOrder);
							shoppingCart = shoppingCartDelegate.find(company, member);
							cartItemList = shoppingCartItemDelegate.findAllByCartAndCompany(shoppingCart, company).getList();
							for(ShoppingCartItem shoppingCartItem : cartItemList)
								shoppingCartItemDelegate.delete(shoppingCartItem);
							
							logger.info("Saving Order");
							sendConfirmationEmail(cartOrder);
							
						}
						
						if(company.getName().equalsIgnoreCase("saltswim")) {
							
							Long cartOrderId = cartOrder.getId();
							
							cartOrder.setIsValid(true);
							cartOrder.setStatus(OrderStatus.COMPLETED);
							cartOrder.setPaymentStatus(PaymentStatus.PAID);
							
							cartOrderDelegate.update(cartOrder);
							
							cartOrder = cartOrderDelegate.find(cartOrderId);
							
							request.getSession().removeAttribute("noLogInCartItems");
							request.getSession().removeAttribute("order-id");
							
							request.getSession().removeAttribute("cartOrder");
							request.getSession().removeAttribute("paypal_environment");
							System.out.println("-----------------------------Saltswim Removing Items-------------------");

							sendConfirmationEmailSaltswim(cartOrder, cartOrder.getItems(), 0.00);
						}

						if("korphilippines".equalsIgnoreCase(company.getName()))
						{
							return saveToOrder(catItems1); // It will only return SUCCESS;
						}

						if((("drugasia".equalsIgnoreCase(company.getName()) && member == null) || company.getName().equalsIgnoreCase("onlinedepot") || 
								((company.getName().equalsIgnoreCase("gurkka") || company.getId() == CompanyConstants.GURKKA_TEST) && member == null) && member == null)
								&& member == null)
						{
							return saveToOrder(catItems1); // It will only return SUCCESS;
						}

						if(company.getId() == CompanyConstants.RAMGO)
						{
							final CartOrder co = cartOrderDelegate.find(Long.parseLong(request.getParameter("orderId")));
							co.setIsValid(true);
							co.setStatus(OrderStatus.PENDING);
							cartOrderDelegate.update(co);
						}
						
						System.out.println("CheckoutAction check total queue 1");
						if(company.getId() == CompanyConstants.TOTAL_QUEUE2)
						{
							System.out.println("CheckoutAction check total queue 2");
							postTotalQueueLicense(cartOrder);
							System.out.println("CheckoutAction check total queue 3");
							cartOrder.setIsValid(true);
							cartOrder.setStatus(OrderStatus.COMPLETED);
							cartOrder.setPaymentStatus(PaymentStatus.PAID);
							cartOrderDelegate.update(cartOrder);
						}

						// create log for Company with Product Inventory
						if(company.getCompanySettings().getHasProductInventory() != null)
						{
							if(company.getCompanySettings().getHasProductInventory())
							{
								createProductQuantityLogs(catItems1);
							}
						}

						return SUCCESS;
					}

					logger.info("NO PAYMENT VALUE was FOUND");
				}
				else
				{
					logger.info("STR ACK FAILURE");
				}
			}
			else
			{
				logger.info("FAILURE Result");
			}

			String ErrorCode = "";
			String ErrorLongMsg = "";
			String ErrorSeverityCode = "";
			boolean encounterAnyError = false;

			if(result.get("L_ERRORCODE0") != null)
			{
				encounterAnyError = true;
				ErrorCode = result.get("L_ERRORCODE0").toString();
			}

			if(result.get("L_SHORTMESSAGE0") != null)
			{
				encounterAnyError = true;
				result.get("L_SHORTMESSAGE0").toString();
			}

			if(result.get("L_LONGMESSAGE0") != null)
			{
				encounterAnyError = true;
				ErrorLongMsg = result.get("L_LONGMESSAGE0").toString();
			}

			if(result.get("L_SEVERITYCODE0") != null)
			{
				encounterAnyError = true;
				ErrorSeverityCode = result.get("L_SEVERITYCODE0").toString();
			}

			notificationMessage = "PAYPAL RESPONSE: [getExpressCheckout] " + ErrorCode + " " + ErrorLongMsg + " " + ErrorSeverityCode;

			logger.info("Notification message: " + notificationMessage);

			if(!encounterAnyError)
			{
				if(("purpletag".equalsIgnoreCase(company.getName()) || "purpletag2".equalsIgnoreCase(company.getName())))
				{
					return saveToOrder(catItems1); // It will only return SUCCESS;
				}
				if("korphilippines".equalsIgnoreCase(company.getName()))
				{
					return saveToOrder(catItems1); // It will only return SUCCESS;
				}
				if((("drugasia".equalsIgnoreCase(company.getName()) && member == null) || company.getName().equalsIgnoreCase("onlinedepot") || ((company.getName().equalsIgnoreCase("gurkka") || company
						.getId() == CompanyConstants.GURKKA_TEST) && member == null)) && member == null)
				{
					return saveToOrder(catItems1); // It will only return SUCCESS;
				}
				if("saltswim".equalsIgnoreCase(company.getName()))
				{
					return saveToOrder(catItems1); // It will only return SUCCESS;
				}
				if("purenectar".equalsIgnoreCase(company.getName()))
				{
					return saveToOrder(catItems1); // It will only return SUCCESS;
				}
				return SUCCESS;
			}

			logger.info("THE RETURN OF GETEXPRESSCHECKOUT IS ERROR(nOT ENCOUNTER any ERROR)");
			return ERROR;
		}

		logger.info("THE RETURN OF GETEXPRESSCHECKOUT IS ERROR because TOKEN IS NULL");
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
			
		}
		
		return ERROR;
	}
	
	@SuppressWarnings("unchecked")
	public void sendOrderEmail(String content){
		try {
			JSONObject obj = new JSONObject();
			String emailContent = "";
			String to = company.getEmail();
			String fullName = "";
			String subjectMessage = "";
			String message = "";
			//setEmailSettings();
			
			List<SavedEmail> num = savedEmailDelegate.findEmailByFormName(company, "Wilcon Order Email", Order.desc("updatedOn")).getList();
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
			
			
			boolean emailSent = false;
			int sendingTrial = 0;
			while(!emailSent){
				sendingTrial += 1;
				if(sendingTrial == 5){
					obj.put("success", false);
					obj.put("message", "Failed to send order email!");
				}
				Random rand = new Random();
				int  n = rand.nextInt(4);
				Logger logger = Logger.getLogger(EmailUtil.class);
				String from = EmailUtil.OTHER_USERNAMES[n];
				logger.info("- - - - - - - - - - > > Wilcon Email Sender: " + EmailUtil.OTHER_USERNAMES[n]);
				/*setEmailSettings();*/
				EmailUtil.connect("smtp.gmail.com", 587,
						EmailUtil.OTHER_USERNAMES[n], EmailUtil.OTHER_PASSWORDS[n]);
				emailSent = EmailUtil.sendWithHTMLFormat(from, company.getEmail(), "Wilcon Order Confirmation", content.toString(), null);
				if(emailSent){
					SavedEmail savedEmail = new SavedEmail();
					savedEmail.setCompany(company);
					savedEmail.setSender("Wilcon Online Store");
					savedEmail.setEmail(to);
					savedEmail.setPhone("");
					savedEmail.setFormName("Wilcon Order Email");
					savedEmail.setEmailContent(content.toString());
					savedEmail.setTestimonial(String.format("%s", finalOrderNumber));
					savedEmailDelegate.insert(savedEmail);
					obj.put("success", true);
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void sendConfirmEmail(String name, String email, String content){
	/*public String sendConfirmEmail(){*/
		try {
			JSONObject obj = new JSONObject();
			String emailContent = "";
			String to = email;
			String fullName = name;
			/*String emailAddress = "store@hi-precision.com.ph";*/
			String subjectMessage = "";
			String message = "";
			//setEmailSettings();
			
			List<SavedEmail> num = savedEmailDelegate.findEmailByFormName(company, "Wilcon Order Confirmation Email", Order.desc("updatedOn")).getList();
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
			
			
			StringBuilder contentEmail = new StringBuilder();
			contentEmail.append("Thank you for using Wilcon Online Store.<br><br>")
			
			 .append(content)
			
			.append("<br><br>Thank you,<br>")
			.append("Wilcon Depot");
			
			
			
			boolean emailSent = false;
			int sendingTrial = 0;
			while(!emailSent){
				sendingTrial += 1;
				if(sendingTrial == 5){
					obj.put("success", false);
					obj.put("message", "Failed to send confirmation email!");
				}
				Random rand = new Random();
				int  n = rand.nextInt(4);
				Logger logger = Logger.getLogger(EmailUtil.class);
				String from = EmailUtil.OTHER_USERNAMES[n];
				logger.info("- - - - - - - - - - > > Wilcon Email Sender: " + EmailUtil.OTHER_USERNAMES[n]);
				EmailUtil.connect("smtp.gmail.com", 587,
						EmailUtil.OTHER_USERNAMES[n], EmailUtil.OTHER_PASSWORDS[n]);
				emailSent = EmailUtil.sendWithHTMLFormat(from, to, "Wilcon Order Confirmation", contentEmail.toString(), null);
				if(emailSent){
					SavedEmail savedEmail = new SavedEmail();
					savedEmail.setCompany(company);
					savedEmail.setSender("Wilcon Online Store");
					savedEmail.setEmail(email);
					savedEmail.setPhone("");
					savedEmail.setFormName("Wilcon Order Confirmation Email");
					savedEmail.setEmailContent(contentEmail.toString());
					savedEmail.setTestimonial(String.format("%s", finalOrderNumber));
					savedEmailDelegate.insert(savedEmail);
					obj.put("success", true);
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*return SUCCESS;*/
	}
	
	

	public void sendConfirmationEmail(CartOrder cartOrder) {
		
		List<CartOrderItem> coItems = cartOrder.getItems();
		
		Double total = 0.00;
		
		StringBuffer content = new StringBuffer();
		content.append("Your paypal is confirmed!");
		content.append("<br/><br/>");
		content.append("Hello " +member.getFirstname());
		content.append("<br/><br/>");
		content.append("The summary of your order is :");
		content.append("<br/>");
		content.append("Order Number: "+cartOrder.getId()+"<br/>");
		content.append("Order Total: Php"+DEFAULT_DECIMAL_FORMAT.format(cartOrder.getTotalPrice() + cartOrder.getTotalShippingPrice2())+"<br/>");
		content.append("<br/>");
		content.append("Payment Method: "+cartOrder.getInfo1());
		content.append("<br/><br/>");
		content.append("BLIING & DELIVERY INFORMATION");
		content.append("<br/><br/>");
		content.append("Delivery Address: " + cartOrder.getAddress1());
		content.append("<br/><br/>");
		
		content.append("ORDER INFORMATION");
		content.append("<br/><br/>");
		content.append("<table style='width:100%;' padding=5>");
		content.append("<tr>");
		content.append("<th colspan=2 style='background-color:gray;color:black;font-weight:bold;'>Product Name</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Price</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Quantity</th>");
		content.append("<th style='background-color:gray;color:black;font-weight:bold;'>Total</th>");
		content.append("</tr>");
		
		for(CartOrderItem cartOrderItem : coItems) {
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
			
			total += cartOrderItem.getCategoryItem().getPrice() * cartOrderItem.getQuantity();
			
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
	
		total += cartOrder.getTotalShippingPrice2();
		
		
		content.append("<tr>");
		content.append("<td colspan='4' style='background-color:gray;color:black;font-weight:bold;text-align:right;'>Total PHP incl. VAT</td>");
		content.append("<td style='background-color:gray;color:black;font-weight:bold;text-align:right;'>Php "+DEFAULT_DECIMAL_FORMAT.format(total)+"</td>");
		content.append("</tr>");
				
		content.append("</table>");
		
		content.append("<br><br>If you have any questions, feel free to contact us. Our office hours are on Mondays through Fridays 8:00 AM - 5:00 PM. Payments and inquiries sent during weekends will be processed the next working day.");
		content.append("<br><br>Thank you,<br>Pure Nectar");
		
		EmailSenderAction emailSenderAction = new EmailSenderAction();
		emailSenderAction.sendEmailConfirmationPureNectar(company, member.getEmail(), "Pure Nectar - Order Information", content, null);
		
	}
	
	public void sendConfirmationEmailSaltswim(CartOrder cartOrder, List<CartOrderItem> cartOrderItems, Double shippingPrice) {
		
		StringBuffer content = new StringBuffer();
		
		content.append("<b>Customer Details:</b><br><br>");
		
		content.append("Name: " )
			   .append("<br>Email: ");
		
		content.append("<br><br><b>Shipping Details:</b>")
			   .append("<br>Mode of Payment: " + cartOrder.getPaymentType())
			   .append("<br>Street Address: " + cartOrder.getAddress1())
			   .append("<br>Zip Code: " + cartOrder.getZipCode())
			   .append("<br>Mobile Number: " + cartOrder.getPhoneNumber())
			   .append("<br>Email: " + cartOrder.getEmailAddress());
	
		content.append("<br/><br/>");
		
		content.append("<b>BREAKDOWN OF ITEMS</b>");
		content.append("<br/><br/>");
		content.append("<table>");
		content.append("<tr>");
		content.append("<th>PRODUCT NAME</th>");
		content.append("<th>PRICE</th>");
		content.append("<th>QUANTITY</th>");
		content.append("<th>TOTAL</th>");
		content.append("</tr>");
		
		for(CartOrderItem cartOrderItem : cartOrderItems) {
			ItemDetail itemDetails = cartOrderItem.getItemDetail();
			
			content.append("<tr>");
			content.append("<td>");
			content.append(itemDetails.getName());
			content.append("</td>");
			content.append("<td>Php "+ DEFAULT_DECIMAL_FORMAT.format(itemDetails.getDiscountedPrice()) +"</td>");
			content.append("<td>"+ cartOrderItem.getQuantity()+"</td>");
			content.append("<td>Php "+DEFAULT_DECIMAL_FORMAT.format(itemDetails.getDiscountedPrice() * cartOrderItem.getQuantity())+"</td>");
			content.append("</tr>");
		
		}
		
		content.append("<tr>");
		content.append("</tr>");
		
		content.append("<tr>");
		content.append("<td>Cart Subtotal</td>");
		content.append("<td>Php "+DEFAULT_DECIMAL_FORMAT.format(cartOrder.getTotalPrice())+"</td>");
		content.append("</tr>");
		content.append("<tr>");
		content.append("<td>Shipping and Handling</td>");
		content.append("<td>Php "+DEFAULT_DECIMAL_FORMAT.format(shippingPrice)+"</td>");
		content.append("</tr>");
		content.append("<tr>");
		content.append("<td>Order Total</td>");
		content.append("<td>Php "+DEFAULT_DECIMAL_FORMAT.format(cartOrder.getTotalPrice() + shippingPrice)+"</td>");
		content.append("</tr>");
				
		content.append("</table>");
		
		content.append("<br><br>");
		
		content.append("<b color='red'>This is a system generated email. Please do not reply to this email. For inquiries, feedback and comments, email us at <a href='#'>staysalty@saltswim.com.ph</a> or call us at 0922-878-5181.");
		
		System.out.println("-----------------------------Saltswim Email Sending-------------------");
		
		EmailSenderAction emailSenderAction = new EmailSenderAction();
		emailSenderAction.sendEmailSaltswim(company, cartOrder.getEmailAddress(), "Saltswim - Order Confirmation", content, null);
		
	}

	private void postTotalQueueLicense(CartOrder co) throws IOException 
	{		
		String name = co.getName();
		String company = co.getInfo1();
		String industry = co.getInfo2() ;
		String email = co.getEmailAddress();
		String country = co.getAddress1();
		String phone = co.getPhoneNumber();
		String macAddress = co.getInfo3();
		String licenseType = co.getInfo4();
		
		/*if(sessionMap.get("name") != null) {
			name = sessionMap.get("name").toString();
			company = sessionMap.get("company").toString();
			industry = sessionMap.get("industry").toString();
			email = sessionMap.get("emailAddress").toString();
			country = sessionMap.get("country").toString();
			phone = sessionMap.get("phone").toString();
			macAddress = sessionMap.get("macAddress").toString();
			licenseType = sessionMap.get("licenseType").toString();
		} else {
			Map<Object, Object> sessionMap = (Map<Object, Object>) request.getSession().getAttribute("sessionMap");
			name = sessionMap.get("name").toString();
			company = sessionMap.get("company").toString();
			industry = sessionMap.get("industry").toString();
			email = sessionMap.get("emailAddress").toString();
			country = sessionMap.get("country").toString();
			phone = sessionMap.get("phone").toString();
			macAddress = sessionMap.get("macAddress").toString();
			licenseType = sessionMap.get("licenseType").toString();
		}*/
		
		/*System.out.println("postTotalQueueLicense 2");
		
		URL url = new URL("https://licenses.totalqueue.com/licenserequest.do?");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write("name="+name+"&company="+company+"&industry="+industry+"&email="+email+"&country="+country+"&phone="+phone+"&macAddress="+macAddress+"&licenseType="+licenseType);
		writer.flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println("postTotalQueueLicense line = " + line);
		}
		
		writer.close();
		reader.close();
		System.out.println("postTotalQueueLicense 3");*/
		
		try{
			Client client = Client.create();
			WebResource webResource = client.resource("https://licenses.totalqueue.com/licenserequest.do");
			MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			params.add("name", name);
			params.add("company", company);
			params.add("industry", industry);
			params.add("email", email);		
			params.add("country", country);
			params.add("phone", phone);
			params.add("macAddress", macAddress);
			params.add("licenseType", licenseType);
		
			ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, params);
			if(response.getStatus() == HttpServletResponse.SC_OK || response.getStatus() == HttpServletResponse.SC_NO_CONTENT){
				logger.info("response okay ..." + response.getEntity(String.class));
			}else{
				logger.info("response not okay");
			}
		}catch(Exception e){
			logger.error("error was caught in postTotalQueueLicense", e);
		}
	}
	
	// Create Product Quantity Log for Company with Product Inventory
	private void createProductQuantityLogs(List<CategoryItem> catItems)
	{
		CategoryItem catItem;
		Log categoryLog;
		for(final CategoryItem item : catItems)
		{
			catItem = categoryItemDelegate.find(item.getId());
			categoryLog = new Log();
			categoryLog.setCompany(company);
			categoryLog.setEntityID(catItem.getId());
			categoryLog.setEntityType(EntityLogEnum.CATEGORY_ITEM);
			categoryLog.setTransactionType("ORDER");
			categoryLog.setAvailableQuantity(catItem.getAvailableQuantity());
			categoryLog.setQuantity(catItem.getOrderQuantity());

			logDelegate.insert(categoryLog);
		}
	}

	private void initShoppingCart()
	{
		shoppingCart = shoppingCartDelegate.find(company, member);
		setShoppingCart(shoppingCart);
		try
		{
			logger.debug("Current shopping cart : " + shoppingCart);
			logger.debug("Current shopping cart items : " + shoppingCart.getItems());
			logger.debug("Current shopping cart total : " + shoppingCart.getTotalPrice());
		}
		catch(final Exception e)
		{
			logger.debug("Shopping cart is currently empty.");
		}

		// get cart items
		initCartItems();
	}

	private void initCartItems()
	{
		try
		{
			final ObjectList<ShoppingCartItem> tempCartItems = shoppingCartItemDelegate.findAll(shoppingCart);
			cartItemList = tempCartItems.getList();
		}
		catch(final Exception e)
		{
			logger.debug("No cart items retrieved.");
		}
	}

	// TODO
	public void addPaymentTransactionDetails(HashMap paymentDetails)
	{

	}

	public void addPaymentTransaction(HashMap paymentDetails)
	{

	}

	public String checkoutPreOrder()
	{
		if(company.getName().equalsIgnoreCase("ECOMMERCE"))
		{
			preOrder = preOrderDelegate.find(company, member);

			preOrderItemList = preOrderItemDelegate.findAll(company, preOrder);

			boolean isShippingCostFound = false;
			for(final PreOrderItem cI : preOrderItemList)
			{
				if(cI.getCategoryItem().getName().equals("Shipping Cost"))
				{
					isShippingCostFound = true;
				}
			}

			if(!isShippingCostFound)
			{
				final PreOrderItem preOrderItem = new PreOrderItem();
				categoryItem = new CategoryItem();
				categoryItem.setDescription("");
				categoryItem.setName("Shipping Cost");
				categoryItem.setPrice(new Double(request.getParameter("shippingvalue")));
				preOrderItem.setId(0L);
				preOrderItem.setCompany(company);
				preOrderItem.setQuantity(0);
				preOrderItem.setCategoryItem(categoryItem);

				preOrderItemList.add(preOrderItem);
			}
			else
			{
				for(int i = 0; i < preOrderItemList.size(); i++)
				{
					if(preOrderItemList.get(i).getCategoryItem().getName().equals("Shipping Cost"))
					{
						preOrderItemList.get(i).getCategoryItem().setPrice(new Double(request.getParameter("shippingvalue")));
					}
				}
			}

			sendEcommerceEmailPreOrder(member);
		}

		return SUCCESS;
	}
	
	public String checkoutGamePlacePromo() throws PayPalException, Exception {
		String testError = "";
		long orderId = 0;
		List<CategoryItem> catItems = new ArrayList<CategoryItem>();
		catItems = (List<CategoryItem>) sessionMap.get("GameplacePromoShoppingCartItems");

		
		paymentType = PaymentType.searchPaymentType(StringUtils.trimToEmpty(request.getParameter("paymenttype")));

		request.setAttribute("comments", "this is my comment");
		
		try
		{
			String totalPrice = "";

			if(request.getParameter("paymentvalue") == null || request.getParameter("paymentvalue").toString().trim().length() == 0)
			{
				this.setNotificationMessage("price is null : " + request.getParameter("paymentvalue"));
				return ERROR;
			}

			else
			{
				totalPrice = request.getParameter("paymentvalue").toString();

				sessionMap.put("paymentvalue", totalPrice);
				// check if float
				try
				{
					final float amt = Float.parseFloat(totalPrice);

					if(amt <= 0)
					{
						amtothers = false;
						this.setNotificationMessage("price is < 0 : " + request.getParameter("paymentvalue"));
						return ERROR;
					}
				}
				catch(final Exception e)
				{
					this.setNotificationMessage(e.toString());
					return ERROR;
				}
			}
			sessionMap.put("paymentvalue", totalPrice);
			logger.debug("CHECKOUT \n\npaymentvalue amt: " + totalPrice);

			if(company.getName().equalsIgnoreCase("purpletag2"))
				for(CategoryItem cI : catItems){
					cI.setName(cI.getName() +" - "+cI.getDescription());
				}
					
			orderId = saveNoLogInCartTempOrder(catItems);
			
			if(company.getPalUsername() != null && company.getPalPassword() != null && company.getPalSignature() != null)
			{

				paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl() + "?memberId="
							+ request.getParameter("memberId") + "&orderId=" + Long.toString(orderId), company.getPalCancelUrl());

				if(paypal == null) {
					this.setNotificationMessage("PayPal is NUll");
					return ERROR;
				}
			}
			
			if(request.getParameter("token") == null)
			{
				String result = "";
				if(company.getPalCurrencyType() != null)
				{	
					paypal.setCategoryItems(catItems);
					result = paypal.setExpressCheckoutRequest(formatter.format(Double.parseDouble(totalPrice)), company.getPalCurrencyType(), null, null);
				}

				if(result.equalsIgnoreCase("Failure"))
				{
					this.setNotificationMessage(testError + " Failure");
					return ERROR; //this should be error
				}
				pToken = paypal.getToken();
				checkoutUrl = paypal.getPaypalUrl();
			}

		}
		catch(final Exception e)
		{
			final Map parameters = request.getParameterMap();

			for(final Object key : parameters.keySet())
			{
				addActionError(key + ": " + parameters.get(key));
			}

			addActionError("Error : " + e.toString());
			e.printStackTrace();
			return ERROR;

		}

		checkoutUrl += pToken + "&memberId=111111111111111111111111";
		
		final Member mem = memberDelegate.find(Long.parseLong(request.getParameter("memberId")));
		//sendMailToGameplacePromoCustomer(catItems,mem,orderId);
		
		sessionMap.put("name", mem.getFirstname());
		sessionMap.put("orderId", orderId);
		sessionMap.put("phonenumber", mem.getMobile());
		sessionMap.put("itemquantity", catItems.get(0).getOtherDetails());
		CategoryItem item = categoryItemDelegate.find(catItems.get(0).getId());
		sessionMap.put("item", item);
		sessionMap.put("itemImage", item.getImages().get(0).getOriginal());
		sessionMap.put("itemImageTitle", item.getImages().get(0).getTitle());
		sessionMap.put("itemPrice", catItems.get(0).getPrice());
		sessionMap.put("pickupSelect", request.getParameter("pickupSelect"));
		sessionMap.put("optionSelect", request.getParameter("optionSelect"));
		sessionMap.put("shippingLocation", request.getParameter("shippingLocation"));
		
		return SUCCESS;
	
	}
	
	public void createColumnbusTempMember() {
		Member tempMember = new Member();
		
		tempMember.setFirstname(request.getParameter("fname"));
		tempMember.setLastname(request.getParameter("lname"));
		tempMember.setFullName(request.getParameter("fname") + " " + request.getParameter("lname"));
		tempMember.setAddress1(request.getParameter("address"));
		tempMember.setCompany(company);
		tempMember.setEmail(request.getParameter("email"));
		tempMember.setUsername(tempMember.getFullName());
		tempMember.setPassword("");
		tempMember.setMobile(request.getParameter("contactno"));
		tempMember.setZipcode(request.getParameter("zipcode"));
		tempMember.setCity(request.getParameter("city"));
		tempMember.setProvince(request.getParameter("province"));
		tempMember.setNewsletter(true);
		
		final Member m = memberDelegate.findAccount(request.getParameter("email"), company);
		
		if(m != null)
		{
			m.setFirstname(request.getParameter("fname") + " " + request.getParameter("lname"));
			m.setUsername(request.getParameter("email"));
			m.setEmail(request.getParameter("email"));
			m.setReg_companyAddress(request.getParameter("address"));
			m.setMobile(request.getParameter("contactno"));
			m.setZipcode(request.getParameter("zipcode"));
			m.setCity(request.getParameter("city"));
			m.setAddress1(request.getParameter("address"));
			m.setProvince(request.getParameter("province"));
			memberDelegate.insert(m);
		}
		else
		{
			memberDelegate.insert(tempMember);
		}
	}
	
	public String checkoutColumnbusCart() throws PayPalException, Exception {
		
		long orderId = 0L;
		long mamberId = 0L;
		double totalPrice = 0.0;
		List<CategoryItem> columnbusCartItems = (List<CategoryItem>) sessionMap.get("columnbusCartItems");
			
			for(CategoryItem i: columnbusCartItems) {
				totalPrice = totalPrice + i.getPrice() * (double ) i.getOrderQuantity();
				i.setOtherDetails(Integer.toString(i.getOrderQuantity()));
			}	

			if(totalPrice <= 0)
			{
				return ERROR;
			}
			
			createColumnbusTempMember();
			orderId = saveNoLogInCartTempOrder(columnbusCartItems);
	try {
			
			if(company.getPalUsername() != null && company.getPalPassword() != null && company.getPalSignature() != null)
			{

				paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl() + "?memberId="
							+ Long.toString(mamberId) + "&orderId=" + Long.toString(orderId), company.getPalCancelUrl());

				if(paypal == null) {
					return ERROR;
				}
			}
			

			String result = "";
			if(company.getPalCurrencyType() != null)
			{	
				paypal.setCategoryItems(columnbusCartItems);
				result = paypal.setExpressCheckoutRequest(formatter.format(totalPrice), company.getPalCurrencyType(), null, null);
			}

			if(result.equalsIgnoreCase("Failure"))
			{
				return ERROR;
			}
			pToken = paypal.getToken();
			checkoutUrl = paypal.getPaypalUrl();


		}
		catch(final Exception e)
		{
			final Map parameters = request.getParameterMap();

			for(final Object key : parameters.keySet())
			{
				addActionError(key + ": " + parameters.get(key));
			}

			addActionError("Error : " + e.toString());
			e.printStackTrace();
			return ERROR;

		}

		checkoutUrl += pToken + "&memberId=111111111111111111111111";
		
		return SUCCESS;
	
	}
	
	public void sendMailToGameplacePromoCustomer(List<CategoryItem> promoCartItems,Member mem,Long orderId) {
		String name = "", email = "", address = "", province = "";
		String city = "", zipcode = "", phonenumber = "";
		
		name = mem.getFullName();
		email = mem.getEmail();
		address = mem.getAddress1();
		province = mem.getProvince();
		city = mem.getCity();
		zipcode = mem.getZipcode();
		phonenumber = mem.getMobile();
		
		CategoryItem cur_item = categoryItemDelegate.find(promoCartItems.get(0).getId());
		String pickupSelect = request.getParameter("pickupSelect");
		if(pickupSelect == null) {
			pickupSelect = (String) sessionMap.get("pickupSelect");
			pickupSelect.replace("?", " ");
		}
		String optionSelect = (String) sessionMap.get("optionSelect");
		String shippingLocation = (String) sessionMap.get("shippingLocation");
		
		try
		{
			request.setAttribute("modeOfPayment", "Pay Via PayPal");
			
			String message = "";
			
			message += "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
			message += 		"<head>";
			message += 		"</head>";
			
			message += 		"<body>";
			
			message += 		"<h3>Dear " + name + ",</h3>";
			
			message += 		"<p>";
			message += 			"Thank you for your shopping. Your order confirmation number is : #" + orderId + " <br/>";
			message += 			"Your contact number is: " + phonenumber + "<br/>";
			message += 			promoCartItems.get(0).getOtherDetails() + " - " + promoCartItems.get(0).getName();
			message += 		"</p>";
			
			message += 		"<p>";
			message += 			"<img src=\"http://www.gameplace.ph/images/items/" + cur_item.getImages().get(0).getOriginal() + "\" alt=\"item image\" title=\"item image\" width=\"312\" />";
			message += 		"</p>";
			
			message += 		"<div style=\"width: 312px;text-align: center;\">";
			message += 			"P" + Double.toString(promoCartItems.get(0).getPrice());
			message += 		"</div>";
			
			String location = (optionSelect.equals("pickup") ? pickupSelect : shippingLocation);
			
			message += 		"<p>";
			message += 			"Items will be prepared at <span><b>" + location + "</b></span> for "+ optionSelect +" once payment has been confirmed. ";
			message += 			" This notification will be your preference on your order.";
			message += 		"</p>";
			
			message +=		"<p>";
			message +=			"Payment Instructions :";
			message +=		"</p>";
			
			message +=		"<p>";
			message +=			"You may deposit your payment to GamePlace Inc.  Corporate Bank Account and email the scanned copy of the Deposit Slip ";
			message +=			"togppimarketing14@gmail.com. We will then process your order once payment has been verified.";
			message +=		"</p>";
			
			message +=		"<p>";
			message +=			"BDO <br/>";
			message +=			"Account Name: GAMEPLACE INC <br/>";
			message +=			"Account Number: 500198950 <br/><br/>";
			message +=			"METRO BANK <br/>";
			message +=			"Account Name: GAMEPLACE INC <br/>";
			message +=			"Account Number: 0773077413023 <br/><br/>";
			message +=			"EASTWEST <br/>";
			message +=			"Account Name: GAMEPLACE INC <br/>";
			message +=			"Account Number: 011402000858";
			message += 		"</p>";
			
			message +=		"<p>";
			message +=			"For Delivered Items :";
			message +=		"</p>";
			
			message += 		"<p>";
			message += 			"If you anticipate that you will not be able to receive your order, please provide the recipient with a valid copy of your ID along ";
			message += 			"with the signed authorization letter or a printout of your order confirmation email.";
			message += 		"</p>";
			
			message += 		"<p>";
			message += 			"For questions or concerns, you may contact us at www.gameplace.ph. Monday to Sunday 10:00 am to 7:00 pm ";
			message += 			"or send us an email at <a href=\"mailto:info@gameplace.com.ph\" target=\"_blank\">info@gameplace.com.ph</a>";
			message += 		"</p>";
			
			message += 		"<p>";
			message += 			"To stay updated on the latest promotions, deals and events. please subscribe to our Newsletter, like";
			message += 			"us on <a href=\"http://www.facebook.com/pages/GamePlace-Philippines/159561760740617\" target=\"_blank\">Facebook</a>.";
			message += 		"</p>";
			
			message += 		"<br/><span>Kind regards,</span>";
			
			message += 		"<br/><br/><h3><span style=\"color: #f97916;\">Game Place, Game On</span></h3>";
			
			message += 		"</body>";
			
			message += "</html>";
			
			//

			EmailUtil.connect("smtp.gmail.com", 587, EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);

			EmailUtil.sendWithHTMLFormat("system@ivant.com", email,"Order Confirmation",message ,null);
			//EmailUtil.send("system@ivant.com", email, "Order Confirmation", message);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

	@SuppressWarnings("unchecked")
	public String checkout() throws PayPalException, Exception
	{
		logger.info("checkout() invoked....");
		long orderId = 0;
		List<CategoryItem> catItems = new ArrayList<CategoryItem>();
		
		if(!company.getName().equalsIgnoreCase("gameplace")) {
			catItems = (List<CategoryItem>) sessionMap.get("noLogInCartItems");
		} else {
			catItems = (List<CategoryItem>) sessionMap.get("GameplacePromoShoppingCartItems");
		}
		paymentType = PaymentType.searchPaymentType(StringUtils.trimToEmpty(request.getParameter("paymenttype")));

		request.setAttribute("comments", sessionMap.get("comments"));
		
		if(company.getName().equalsIgnoreCase("ECOMMERCE"))
		{
			boolean isShippingCostFound = false;
			for(final ShoppingCartItem cI : cartItemList)
			{
				if(cI.getItemDetail().getName().equals("Shipping Cost"))
				{
					isShippingCostFound = true;
				}
			}

			if(!isShippingCostFound)
			{
				cartItemList.add(getEcommerceShippingCost());
			}
			else
			{
				for(int i = 0; i < cartItemList.size(); i++)
				{
					if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
					{
						cartItemList.get(i).getItemDetail().setPrice(new Double(request.getParameter("shippingvalue")));
					}
				}
			}

			if(request.getParameter("shippingvalue") != null)
			{
				sessionMap.put("shippingvalue", request.getParameter("shippingvalue"));
			}

			sendEcommerceEmailShipping(member);
		}

		// yes
		if(company.getName().equalsIgnoreCase("GIFTCARD"))
		{
			boolean isShippingCostFound = false;
			for(final ShoppingCartItem cI : cartItemList)
			{
				if(cI.getItemDetail().getName().equals("Shipping Cost"))
				{
					isShippingCostFound = true;
				}
			}

			if(request.getParameter("shippingtype").equals("delivery"))
			{
				shippingType = ShippingType.DELIVERY;
				if(!isShippingCostFound)
				{
					cartItemList.add(getGiftcardShippingCost());
				}
				else
				{
					for(int i = 0; i < cartItemList.size(); i++)
					{
						if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
						{
							cartItemList.get(i).getItemDetail().setPrice(new Double(request.getParameter("shippingvalue")));
						}
					}
				}
			}

			final CategoryItem catItem = categoryItemDelegate.find(cartItemList.get(0).getItemDetail().getRealID());
			sessionMap.put("catItem", catItem);

			if(request.getParameter("shippingvalue") != null)
			{
				sessionMap.put("shippingvalue", request.getParameter("shippingvalue"));
			}

			// sendGiftcardEmailShipping(member);

			// saveGiftcardTransaction();
		}

		// IIEE ORG PHILS
		if(company.getName().equalsIgnoreCase(CompanyConstants.IIEE_ORG_PHILS))
		{
			//final List iieeOrgItems = new ArrayList<CategoryItem>();
			List iieeOrgItems = (List<CategoryItem>) sessionMap.get(CompanyConstants.IIEE_ORG_PHILS_CART);
			
			/*final CategoryItem categoryItem = new CategoryItem();

			categoryItem.setName(request.getParameter("item"));
			categoryItem.setDescription(request.getParameter("item_type"));
			categoryItem.setPrice(Double.parseDouble(request.getParameter("amount")));
			categoryItem.setOrderQuantity(1);
			categoryItem.setCreatedOn(new Date());

			iieeOrgItems.add(categoryItem);*/

			Member tempMember = (Member) sessionMap.get(CompanyConstants.IIEE_ORG_PHILS_TEMP_MEMBER);

			/*String address_1 = request.getParameter("house_number")+" "+ 
					request.getParameter("street");
			String address_2 = request.getParameter("barangay");
			
			tempMember.setFirstname(request.getParameter("first_name"));
			tempMember.setLastname(request.getParameter("last_name"));
			tempMember.setMiddlename(request.getParameter("middle_name"));
			tempMember.setAddress1(address_1);
			tempMember.setAddress2(address_2);
			tempMember.setLandline(request.getParameter("phone_no"));
			tempMember.setMobile(request.getParameter("mobile_no"));
			tempMember.setCity(request.getParameter("city"));			
			tempMember.setZipcode(request.getParameter("zipcode"));
			tempMember.setEmail(request.getParameter("email")+","+request.getParameter("secondary_email"));
			tempMember.setInfo1("Licence Number: "
					+ request.getParameter("licence_number") + ", Birth Date: "
					+ request.getParameter("birth_date"));
			tempMember.setInfo2(request.getParameter("country"));			
			tempMember.setProvince(request.getParameter("province"));*/
			
			

			final PaymentType paymentType = PaymentType.searchPaymentType("CREDIT_CARD");

			orderId = saveOrder(iieeOrgItems, tempMember, paymentType);
			sessionMap.remove(CompanyConstants.IIEE_ORG_PHILS_CART);
			sessionMap.remove(CompanyConstants.IIEE_ORG_PHILS_TEMP_MEMBER);

			final String name = tempMember.getFirstname()+" "+tempMember.getMiddlename()+" "+tempMember.getLastname();
			final String description = "Order from " + name;

			cartOrder = cartOrderDelegate.find(orderId);
			cartOrder.setMember(tempMember);
			
			EmailSenderAction emailSender = new EmailSenderAction();
			company.setEmail(company.getEmail());
			emailSender.setEmail(cartOrder.getEmailAddress());
			emailSender.setSubject("The " + company.getNameEditable() + " Order Form Submission Pay via Dragon Pay. Order ID NO: " + cartOrder.getId());
			emailSender.setCompany(company);
			
			emailSender.sendEmailPaymentInformation(name,
					tempMember.getEmail(), tempMember.getLandline(), tempMember
							.getAddress1(), iieeOrgItems, "", "", "", "");

			dragonpayURL = dragonpay(PaymentConstants.IIEE_DRAGONPAY_MERCHANT_ID, description, PaymentConstants.IIEE_DRAGONPAY_SECRET_KEY);

			
			return "dragonpay";

		}

		// Mr. Aircon
		if(company.getName().equalsIgnoreCase(CompanyConstants.MR_AIRCON))
		{
			catItems = (List<CategoryItem>) sessionMap.get("mrAirConNoLogInCartItems");

			final Date date = new Date();

			final CategoryItem installationCharge = new CategoryItem();

			if(installationCharge != null)
			{
				installationCharge.setName("Installation Price");
				installationCharge.setPrice(Double.parseDouble(request.getParameter("installationCharge")));
				installationCharge.setDescription(request.getParameter("installationType"));
				installationCharge.setOrderQuantity(1);
				installationCharge.setCreatedOn(date);
			}

			if(catItems != null && catItems.size() != 0)
			{
				catItems.add(installationCharge);

				if(!isShippingFound(catItems))
				{
					catItems.add(getShippingCost());
				}

				final Member tempMember = new Member();

				totalPrice = Double.parseDouble(request.getParameter("paymentvalue"));

				tempMember.setInfo1(request.getParameter("title"));
				tempMember.setInfo2(request.getParameter("additional_delivery_instruction"));
				tempMember.setInfo3(request.getParameter("nationality"));
				tempMember.setInfo4(request.getParameter("delivery_date"));
				tempMember.setReg_companyName(request.getParameter("shipping_company"));
				tempMember.setValue(request.getParameter("dob"));
				tempMember.setFirstname(request.getParameter("first_name"));
				tempMember.setLastname(request.getParameter("last_name"));
				tempMember.setAddress1(request.getParameter("billing_address"));
				tempMember.setAddress2(request.getParameter("shipping_address"));
				tempMember.setMobile(request.getParameter("mobile_no"));
				tempMember.setLandline(request.getParameter("tel_no"));
				tempMember.setCity(request.getParameter("shipping_city"));
				tempMember.setZipcode(request.getParameter("shipping_zip_code"));
				tempMember.setEmail(request.getParameter("email_address"));

//				final PaymentType paymentType = PaymentType.searchPaymentType(request.getParameter("paymentMethod"));
				
				paymentType = PaymentType.searchPaymentType(request.getParameter("paymentMethod"));

				orderId = saveNoLogInCartTempOrder(catItems, tempMember, paymentType);

				sessionMap.remove("mrAirConNoLogInCartItems");

				cartOrder = cartOrderDelegate.find(orderId);

				if(cartOrder.getPaymentType().equals(PaymentType.COD) || cartOrder.getPaymentType().equals(PaymentType.BANK_DEPOSIT))
				{
					// Email the user and company
					sendMrAirconShipping(cartOrder);

					setNotificationMessage("CASH_SUCCESS");

					return SUCCESS;
				}
				else if(cartOrder.getPaymentType().equals(PaymentType.CREDIT_CARD))
				{
					// Proceed to PesoPay
					/*final PesoPay pesoPay = new PesoPay(PaymentConstants.MR_PESOPAY_MERCHANT_ID, cartOrder.getId().toString(), totalPrice, PaymentConstants.MR_PESOPAY_CURRENCY,
							PaymentConstants.MR_PESOPAY_LANG, PaymentConstants.MR_PESOPAY_URL, PaymentConstants.MR_PESOPAY_PAYTYPE, PaymentConstants.MR_PESOPAY_PAYMETHOD);
					request.setAttribute("pesoPay", pesoPay);

					return "pesopay";*/
					
					
					final String name = tempMember.getFirstname() + " " + tempMember.getLastname();

					final String merchantCode = PaymentConstants.MR_IPAY88_MERCHANT_CODE;
					final String merchantKey = PaymentConstants.MR_IPAY88_MERCHANT_KEY;
					final String responseURL = "";
					final String backEndURL = "";
					final String desc = "Order from " + name;
	
					final String ipay88Return = iPay88(merchantCode, merchantKey, desc, responseURL, backEndURL);
	
					return ipay88Return;
					
				}
				else
				{
					return ERROR;
				}
			}
		}
		
		if(company.getId() == CompanyConstants.SWAPCANADA) 
		{
			final Member tempMember = new Member();
			final List<String> filePathList = new ArrayList<String>();
			final String resource = servletContext.getRealPath("companies");
			final String SCREENSHOT_PATH = resource + "/swapcanada/images/screenshots/";
			final String IMAGE_PATH = resource + "/swapcanada/images/items/";
			final String SHIP_TO_BILLING_ADDRESS = "ship_to_billing_address";
			final String SHIP_TO_DIFFERENT_ADDRESS = "ship_to_different_address";
			final Date now = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			final String invoiceDate = sdf.format(now);
			final String billingUsTerritory = request.getParameter("1a|us_territory");
			final String billingName = request.getParameter("1b|first_name") + SPACE + request.getParameter("1c|last_name"); 
			final String billingBusiness = request.getParameter("1d|business");
			final String billingFloor = request.getParameter("1e|floor");
			final String billingPoBox = request.getParameter("1f|po_box");
			final String billingCity = request.getParameter("1g|city");
			final String billingState = request.getParameter("1h|state");
			final String billingZipCode = request.getParameter("1i|zipcode");
			final String shippingUsTerritory = request.getParameter("2a|us_territory");
			final String shippingName = request.getParameter("2b|first_name") + SPACE + request.getParameter("2c|last_name");
			final String shippingBusiness = request.getParameter("2d|business");
			final String shippingFloor = request.getParameter("2e|floor");
			final String shippingPoBox = request.getParameter("2f|po_box");
			final String shippingCity = request.getParameter("2g|city");
			final String shippingState = request.getParameter("2h|state");
			final String shippingZipCode = request.getParameter("2i|zipcode");
			final String contact = request.getParameter("1j|contact_no");
			final String promo = request.getParameter("promo");
			final String shippingAddress = request.getParameter("shippingAddress");
			CategoryItem catItem = null;
			orderType = request.getParameter("orderType");
			catItems = (List<CategoryItem>) sessionMap.get("swapCanadaNoLogInCartItems");

			if(catItems != null) {
				for(CategoryItem cI : catItems) {
					if(StringUtils.contains(cI.getShortDescription(), PNG_EXTENSION) && !cI.getName().equals(SHIPPING_COST) && !cI.getName().equals(DISCOUNT)) {
						filePathList.add(SCREENSHOT_PATH + cI.getShortDescription());
					}
					else if(!cI.getName().equals(SHIPPING_COST) && !cI.getName().equals(DISCOUNT)) {
						catItem = categoryItemDelegate.find(cI.getId());
						filePathList.add(IMAGE_PATH + catItem.getImages().get(0).getOriginal());
					}
				}
			}
			
			if(catItems != null && catItems.size() != 0)
			{
				CategoryItem item = new CategoryItem();
				item.setId(0L);
				item.setSku("-1");
				item.setName(SHIPPING_COST);
				item.setDescription("");
				item.setPrice(Double.parseDouble(request.getParameter("shippingvalue")));
				item.setOtherDetails("1");
				item.setOrderQuantity(1);
				catItems.add(item);

				tempMember.setFirstname(request.getParameter("1a|name"));
				tempMember.setEmail(request.getParameter("1b|email"));
				tempMember.setAddress1(request.getParameter("1d|address"));
				tempMember.setLandline(request.getParameter("1c|contact_number"));

				orderId = saveNoLogInCartTempOrder(catItems);

				CartOrder co = cartOrderDelegate.find(orderId);

				co.setBillingCountry(billingUsTerritory);
				co.setBillingName(billingName);
				co.setBillingBusiness(billingBusiness);
				co.setBillingAddress1(billingFloor);
				co.setBillingAddress2(billingPoBox);
				co.setBillingCity(billingCity);
				co.setBillingState(billingState);
				co.setBillingZipCode(billingZipCode);
				
				if(shippingAddress.equals(SHIP_TO_BILLING_ADDRESS)) {
					co = setSwapShippingDetails(co, billingUsTerritory, billingName, billingBusiness,
							billingFloor, billingPoBox, billingCity, billingState, billingZipCode, Boolean.TRUE);
				}
				else if(shippingAddress.equals(SHIP_TO_DIFFERENT_ADDRESS)) {
					co = setSwapShippingDetails(co, shippingUsTerritory, shippingName, shippingBusiness,
							shippingFloor, shippingPoBox, shippingCity, shippingState, shippingZipCode, Boolean.FALSE);
				}
				
				co.setPhoneNumber(request.getParameter("1j|contact_no"));
				co.setEmailAddress(request.getParameter("1k|email"));
				co.setShippingStatus(ShippingStatus.PENDING);
				co.setPaymentStatus(PaymentStatus.PENDING);
				co.setApprovalCode(promo);
				
				if(request.getParameter("paymenttype").equalsIgnoreCase(MONERIS)) {
					co.setPaymentType(PaymentType.MONERIS);
				}
				
				cartOrderDelegate.update(co);

				co.setMember(tempMember);
				setCartOrder(co);

				sessionMap.remove("swapCanadaNoLogInCartItems");
			}

			final String[] filePathArray = new String[filePathList.size()];
			
			String paymenttype = "";
				
			if(request.getParameter("paymenttype").equalsIgnoreCase(MONERIS)) {
				paymenttype = "Pay via Moneris";
			}
				
			EmailSenderAction emailSender = new EmailSenderAction();
			company.setEmail(company.getEmail());
			emailSender.setEmail(cartOrder.getEmailAddress());
			emailSender.setSubject("The " + company.getNameEditable() + " Order Form Submission " + paymenttype + ". Order ID NO: " + cartOrder.getId());
			emailSender.setCompany(company);
					
			if(shippingAddress.equals(SHIP_TO_BILLING_ADDRESS)) {
				emailSender.sendSwapCanadaEmailPayment(invoiceDate, String.valueOf(orderId), billingUsTerritory, 
						billingName, billingBusiness, billingFloor, billingPoBox, billingCity, billingState,
						billingZipCode, contact, promo, catItems, filePathList.toArray(filePathArray));
			}
			else if(shippingAddress.equals(SHIP_TO_DIFFERENT_ADDRESS)) {
				emailSender.sendSwapCanadaEmailPayment(invoiceDate, String.valueOf(orderId), shippingUsTerritory, 
						shippingName, shippingBusiness, shippingFloor, shippingPoBox, shippingCity, shippingState, 
						shippingZipCode, contact, promo, catItems, filePathList.toArray(filePathArray));
			}

			return SUCCESS;
		}

		if(company.getId() == CompanyConstants.TOMATO) 
		{
			logger.info("inside tomato company....");
			final Member tempMember = new Member();
			final List<String> filePathList = new ArrayList<String>();
			final String resource = servletContext.getRealPath("companies");
			final String SCREENSHOT_PATH = resource + "/tomato/images/screenshots/";
			final Date now = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			final String invoiceDate = sdf.format(now);
			final String name = request.getParameter("1a|name");
			final String email = request.getParameter("1b|email");
			final String mobile = request.getParameter("1c|contact_number");
			final String country = request.getParameter("country");
			final String city = request.getParameter("1e|province");
			final String address = request.getParameter("1d|address");
			final String landmark = request.getParameter("landmark");
			final String promo = request.getParameter("promo");
			final String transactionNumber = request.getParameter("vpc_OrderInfo");
					
			catItems = (List<CategoryItem>) sessionMap.get("tomatoNoLogInCartItems");
			logger.info("flag for tomato company...");
			if(catItems != null) {
				for(CategoryItem cI : catItems) {
					if(!cI.getName().equals(SHIPPING_COST) && !cI.getName().equals(DISCOUNT))
						filePathList.add(SCREENSHOT_PATH + cI.getShortDescription());
				}
			}
			
			if(catItems != null && catItems.size() != 0)
			{
				logger.info("cat items is  not null for tomato company....");
				CategoryItem item = new CategoryItem();
				item.setId(0L);
				item.setSku("-1");
				item.setName(SHIPPING_COST);
				item.setDescription("");
				item.setPrice(Double.parseDouble(request.getParameter("shippingvalue")));
				item.setOtherDetails("1");
				item.setOrderQuantity(1);
				catItems.add(item);

				tempMember.setFirstname(request.getParameter("1a|name"));
				tempMember.setEmail(request.getParameter("1b|email"));
				tempMember.setAddress1(request.getParameter("1d|address"));
				tempMember.setLandline(request.getParameter("1c|contact_number"));

				orderId = saveNoLogInCartTempOrder(catItems);

				CartOrder co = cartOrderDelegate.find(orderId);
				co.setName(request.getParameter("1a|name"));
				co.setEmailAddress(request.getParameter("1b|email"));
				co.setPhoneNumber(request.getParameter("1c|contact_number"));
				co.setAddress1(request.getParameter("1d|address"));
				co.setAddress2(request.getParameter("landmark"));
				co.setCity(request.getParameter("1e|province"));
				co.setCountry(request.getParameter("country"));
				co.setShippingStatus(ShippingStatus.PENDING);
				co.setPaymentStatus(PaymentStatus.PENDING);
				co.setApprovalCode(promo);
				co.setTransactionNumber(transactionNumber);
				
				if(!StringUtils.isEmpty(promo)) {
					PromoCode promoCode = promoCodeDelegate.findByCode(company, promo);
					promoCode.setIsDisabled(Boolean.TRUE);
					promoCodeDelegate.update(promoCode);
				}
				
				if(request.getParameter("paymenttype").equalsIgnoreCase(DRAGON_PAY))
					co.setPaymentType(PaymentType.DRAGON_PAY);
				else if(request.getParameter("paymenttype").equalsIgnoreCase(BANK))
					co.setPaymentType(PaymentType.BANK);
				else if(request.getParameter("paymenttype").equalsIgnoreCase(GLOBAL_PAY))
					co.setPaymentType(PaymentType.GLOBAL_PAY);
				
				cartOrderDelegate.update(co);

				co.setMember(tempMember);
				setCartOrder(co);

				sessionMap.remove("tomatoNoLogInCartItems");
			}

			final String[] filePathArray = new String[filePathList.size()];
			
			if(request.getParameter("paymenttype").equalsIgnoreCase("dragonpay"))
			{
				final String description = "Order from " + name;
				
				EmailSenderAction emailSender = new EmailSenderAction();
				company.setEmail(company.getEmail());
				emailSender.setEmail(cartOrder.getEmailAddress());
				emailSender.setSubject("The " + company.getNameEditable() + " Order Form Submission Pay via Dragon Pay. Order ID NO: " + cartOrder.getId());
				emailSender.setCompany(company);
				
				emailSender.sendEmailPaymentManyAttachments(invoiceDate, name,
						email, mobile, country, String.valueOf(orderId), city, address, landmark,
						promo, catItems, filePathList.toArray(filePathArray), "", "", "");

				dragonpayURL = dragonpay(PaymentConstants.TO_DRAGONPAY_MERCHANT_ID, description, PaymentConstants.TO_DRAGONPAY_SECRET_KEY);

				return DRAGON_PAY;
			}
			else {
				String paymenttype = "";
				
				if(request.getParameter("paymenttype").equalsIgnoreCase(GLOBAL_PAY)) {
					paymenttype = "Pay via Global Pay";
				}
				else if(request.getParameter("paymenttype").equalsIgnoreCase(BANK)) {
					paymenttype = "Pay via Bank";
				}
				
				EmailSenderAction emailSender = new EmailSenderAction();
				company.setEmail(company.getEmail());
				emailSender.setEmail(cartOrder.getEmailAddress());
				emailSender.setSubject("The " + company.getNameEditable() + " Order Form Submission " + paymenttype + ". Order ID NO: " + cartOrder.getId());
				emailSender.setCompany(company);
				logger.info("EMAIL SENDER SUBJECT : " + "The " + company.getNameEditable() + " Order Form Submission " + paymenttype + ". Order ID NO: " + cartOrder.getId());
				emailSender.sendEmailPaymentManyAttachments(invoiceDate, name,
						email, mobile, country, String.valueOf(orderId), city, address, landmark,
						promo, catItems, filePathList.toArray(filePathArray), "", "", "");
				
				if(request.getParameter("paymenttype").equalsIgnoreCase(GLOBAL_PAY)) {
					globalpay();
					return GLOBAL_PAY;
				}

				return SUCCESS;
			}
		}
		
		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST
				|| company.getName().equalsIgnoreCase("gurkka"))
		{

			for(final ShoppingCartItem sci : cartItemList)
			{
				final CategoryItem ci = CategoryItemUtil.getItemFromShoppingCart(sci);
				if(ci != null)
				{
				}
			}

			final String area = StringUtils.trimToNull(request.getParameter("province2")) != null
				? request.getParameter("province2")
				: member.getProvince();

			Integer totalQuantity = 0;
			Double shippingPrice = 0.00;
			Double shippingFee = 0.00;
			if(!StringUtils.isEmpty(area))
			{
				if(area.equalsIgnoreCase("ncr"))
				{
					shippingPrice = 0.00;
				}
				if(area.equalsIgnoreCase("luzon"))
				{
					shippingPrice = 50.00;
				}

				if(area.equalsIgnoreCase("visayas"))
				{
					shippingPrice = 65.00;
				}
				if(area.equalsIgnoreCase("mindanao"))
				{
					shippingPrice = 75.00;
				}
			}
			for(final ShoppingCartItem shoppingCartItem : cartItemList)
			{
				final ItemDetail itemDetail = shoppingCartItem.getItemDetail();
				final String name = itemDetail.getName();
				final Integer itemQuantity = shoppingCartItem.getQuantity();
				if(!StringUtils.containsIgnoreCase(name, "Freebie") && itemQuantity > 0)
				{
					totalQuantity += itemQuantity;
				}
			}

			if(request.getParameter("deliverytype").equalsIgnoreCase("p"))
			{
				shippingFee = Double.valueOf(request.getParameter("shippingvalue"));
			}
			else
			{
				shippingFee = (totalQuantity * shippingPrice);
			}

			sessionMap.put("shippingvalue", shippingFee);

			boolean isShippingCostFound = false;
			for(final ShoppingCartItem cI : cartItemList)
			{
				if(cI.getItemDetail().getName().equals("Shipping Cost"))
				{
					final ItemDetail itemDetail = cI.getItemDetail();
					itemDetail.setPrice(new Double(shippingFee));
					cI.setItemDetail(itemDetail);
					cI.setShoppingCart(getShoppingCart());
					shoppingCartItemDelegate.update(cI);
					isShippingCostFound = true;
				}
				if(cI.getItemDetail().getName().equals("Windows"))
				{
					cartItemList.remove(cI);
				}
			}

			final ShoppingCart sc = getShoppingCart();
			if(!isShippingCostFound)
			{
				cartItemList.add(getGurkkaShippingCost());
				sc.setItems(cartItemList);
				shoppingCartDelegate.update(sc);
			}
			else
			{
				for(int i = 0; i < cartItemList.size(); i++)
				{
					if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
					{
						final ShoppingCartItem item = cartItemList.get(i);
						final ItemDetail itemDetail = item.getItemDetail();
						itemDetail.setPrice(shippingFee);
						item.setItemDetail(itemDetail);
						item.setShoppingCart(sc);
						shoppingCartItemDelegate.update(cartItemList.get(i));
					}
				}
			}

			setShoppingCart(sc);

			/* Get session from the member. */
			final HttpSession session = request.getSession(true);
			/* Get the freebies selected by the member. */
			final String freebiesCode = StringUtils.trimToNull(request.getParameter("freebiesCode"));
			
			
			/* Add freebies to ShoppingCart */

			CompanyUtil.loadMenu(company, httpServer, request, null, logger, servletContext, isLocal);

			for(final ShoppingCartItem sci : cartItemList)
			{}

			if(request.getParameter("paymenttype").equalsIgnoreCase("delivery"))
			{
				paymentType = PaymentType.CASH;

				createOrderBasic();
				initOrderItemList();

				if(cartOrder.getShippingType() == ShippingType.DELIVERY)
				{
					cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));
					cartOrder.setAddress1(request.getParameter("address2"));
					cartOrder.setState(request.getParameter("province2"));
					cartOrder.setCity(request.getParameter("city2"));
					cartOrder.setName(member.getFullName());
					cartOrder.setPhoneNumber(request.getParameter("mobilenumber2"));
				}

				orderId = cartOrder.getId();

				if(request.getParameter("deliverytype").equalsIgnoreCase("d"))
				{
					shippingType = ShippingType.DELIVERY;
				}
				else
				{
					shippingType = ShippingType.PICKUP;
				}

				final String type = paymentType.getValue() + " on " + shippingType.getValue();

				sendGurkkaEmailShipping(member, 0D, "NEW");

				final String success = sendGurkkaEmailShipping(member, 0D, type);
				if(StringUtils.containsIgnoreCase(success, SUCCESS))
				{
					// update quantity after voucher sent
					final CartOrder co = cartOrderDelegate.find(orderId);
					final List<CartOrderItem> items = co.getItems();
					if(CollectionUtils.isNotEmpty(items))
					{
						for(CartOrderItem coi : items)
						{
							final CategoryItem item = CategoryItemUtil.getItemFromCartOrder(coi);
							if(item != null)
							{}
						}
					}
					return success + "delivery";
				}
				else
				{
					return ERROR;
				}
			}

			if(request.getParameter("paymenttype").equalsIgnoreCase("dragonpay"))
			{
				paymentType = PaymentType.DRAGON_PAY;
				createOrderBasic();

				initOrderItemList();

				if(cartOrder.getShippingType() == ShippingType.DELIVERY)
				{
					cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));
					cartOrder.setAddress1(request.getParameter("address2"));
					cartOrder.setState(request.getParameter("province2"));
					cartOrder.setCity(request.getParameter("city2"));
					cartOrder.setName(member.getFullName());
					cartOrder.setPhoneNumber(request.getParameter("mobilenumber2"));
				}

				cartOrder.setStatus(OrderStatus.PENDING);
				cartOrderDelegate.update(cartOrder);

				final String name = !StringUtils.isEmpty(member.getReg_companyName())
					? member.getReg_companyName()
					: member.getFirstname() + " " + member.getLastname();

				final String description = "Order from " + name;

				dragonpayURL = dragonpay(PaymentConstants.GR_DRAGONPAY_MERCHANT_ID, description, PaymentConstants.GR_DRAGONPAY_SECRET_KEY);

				return "dragonpay";
			}

			if(request.getParameter("paymenttype").equalsIgnoreCase("ipay"))
			{
				paymentType = PaymentType.I_PAY88;
				createOrderBasic();
				initOrderItemList();

				if(cartOrder.getShippingType() == ShippingType.DELIVERY)
				{
					cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));
					cartOrder.setAddress1(request.getParameter("address2"));
					cartOrder.setState(request.getParameter("province2"));
					cartOrder.setCity(request.getParameter("city2"));
					cartOrder.setName(member.getFullName());
					cartOrder.setPhoneNumber(request.getParameter("mobilenumber2"));
				}

				cartOrder.setStatus(OrderStatus.PENDING);
				cartOrderDelegate.update(cartOrder);

				final String name = !StringUtils.isEmpty(member.getReg_companyName())
					? member.getReg_companyName()
					: member.getFirstname() + " " + member.getLastname();

				// Gurkka only
				final String merchantCode = PaymentConstants.GR_IPAY88_MERCHANT_CODE;
				final String merchantKey = PaymentConstants.GR_IPAY88_MERCHANT_KEY;
				final String responseURL = "";
				final String backEndURL = "";
				final String prodDesc = "Order from " + name;

				final String ipay88Return = iPay88(merchantCode, merchantKey, prodDesc, responseURL, backEndURL);

				return ipay88Return;
			}

			if(request.getParameter("paymenttype").equalsIgnoreCase("paypal"))
			{

				String modeOfDelivery = "For Pick Up";
				if(request.getParameter("deliverytype").equalsIgnoreCase("d"))
				{
					modeOfDelivery = "For Delivery";
				}
				member.setPurpose(modeOfDelivery);
				memberDelegate.update(member);

			}
		}
		
		if(company.getId() == CompanyConstants.WENDYS) 
		{
			cartOrder = cartOrderDelegate.find(cartId);
			//set cartOrder Status to Cancelled so that, 
			//when transactions was not successful, order will not be reflected on the CMS
			cartOrder.setStatus(OrderStatus.CANCELLED);
			cartOrderDelegate.update(cartOrder);
			//reQuery to update the content of cartOrder
			cartOrder = cartOrderDelegate.find(cartId);
			
			if(request.getParameter("paymenttype").equalsIgnoreCase("ipay"))
			{
				paymentType = PaymentType.I_PAY88;

				final String merchantCode = PaymentConstants.WE_IPAY88_MERCHANT_CODE;
				final String merchantKey = PaymentConstants.WE_IPAY88_MERCHANT_KEY;
				final String responseURL = "";
				final String backEndURL = "";
				final String prodDesc = "Order from " + "";

				final String ipay88Return = iPay88(merchantCode, merchantKey, prodDesc, responseURL, backEndURL);

				return ipay88Return;
			}			
		}

		if(company.getName().equalsIgnoreCase("DRUGASIA") && member != null)
		{
			boolean isShippingCostFound = false;
			for(final ShoppingCartItem cI : cartItemList)
			{
				if(cI.getItemDetail().getName().equals("Shipping Cost"))
				{
					isShippingCostFound = true;
				}
				if(cI.getItemDetail().getName().equals("Windows"))
				{
					cartItemList.remove(cI);
				}
			}

			if(!isShippingCostFound)
			{
				cartItemList.add(getDrugasiaShippingCost());
			}
			else
			{
				for(int i = 0; i < cartItemList.size(); i++)
				{
					if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
					{
						cartItemList.get(i).getItemDetail().setPrice(new Double(request.getParameter("shippingvalue")));
					}
				}
			}

			if(member != null && member.getSeniorCitizen() != null && member.getSeniorCitizen() == true)
			{
				cartItemList.add(getDrugasiaDiscount());
			}

			if(request.getParameter("shippingvalue") != null)
			{
				sessionMap.put("shippingvalue", request.getParameter("shippingvalue"));
			}
		}

		if(company.getName().equalsIgnoreCase("MDT"))
		{
			boolean isShippingCostFound = false;
			for(final ShoppingCartItem cI : cartItemList)
			{
				if(cI.getItemDetail().getName().equals("Shipping Cost"))
				{
					isShippingCostFound = true;
				}
			}

			if(!isShippingCostFound)
			{
				cartItemList.add(getMdtShippingCost());
			}
			else
			{
				for(int i = 0; i < cartItemList.size(); i++)
				{
					if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
					{
						cartItemList.get(i).getItemDetail().setPrice(new Double(request.getParameter("shippingvalue")));
					}
				}
			}
			sendMdtEmailShipping(member);
		}

		if(company.getName().equalsIgnoreCase("ONLINEDEPOT") && member != null)
		{
			boolean isShippingCostFound = false;
			for(final ShoppingCartItem cI : cartItemList)
			{
				if(cI.getItemDetail().getName().equals("Shipping Cost"))
				{
					isShippingCostFound = true;
				}
			}

			if(!isShippingCostFound)
			{
				cartItemList.add(getOnlinedepotShippingCost());
			}
			else
			{
				for(int i = 0; i < cartItemList.size(); i++)
				{
					if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
					{
						cartItemList.get(i).getItemDetail().setPrice(new Double(request.getParameter("shippingvalue")));
					}
				}
			}

			if(request.getParameter("shippingvalue") != null)
			{
				sessionMap.put("shippingvalue", request.getParameter("shippingvalue"));
			}
		}
		
		if(company.getName().equalsIgnoreCase("micephilippines"))
		{
			paymentForRegistration(request.getParameter("paymentvalue"));
			return SUCCESS;
		}
		else if(company.getName().equalsIgnoreCase("APLIC"))
		{
			paymentForRegistration(request.getParameter("paymentvalue"));
			return SUCCESS;
		}
		else if(company.getName().equalsIgnoreCase("APLIC2"))
		{
			paymentForRegistration(request.getParameter("paymentvalue"));
			return SUCCESS;
		}
		else if(((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2")) || company.getName().equalsIgnoreCase("PURPLETAG2")) || company.getName().equalsIgnoreCase("KORPHILIPPINES") || (company.getName().equalsIgnoreCase("DRUGASIA") && member == null)
				|| ((company.getName().equalsIgnoreCase("GURKKA") || company.getId() == CompanyConstants.GURKKA_TEST) && member == null)
				|| (company.getName().equalsIgnoreCase("ONLINEDEPOT") && member == null)
				|| (company.getName().equals("adeventsmanila") && member == null
				|| (company.getName().equals("hiprecisiononlinestore") && member == null))
				|| (company.getName().equals("saltswim")))
		{
			if(catItems != null && catItems.size() != 0)
			{
				if(!isShippingFound(catItems))
				{
					catItems.add(getShippingCost());
				}
				orderId = saveNoLogInCartTempOrder(catItems);
			}
		}
		else if(company.getId() == CompanyConstants.TOTAL_QUEUE2) 
		{
			sessionMap.put("name", request.getParameter("name"));
			sessionMap.put("company", request.getParameter("company"));
			sessionMap.put("industry", request.getParameter("industry"));
			sessionMap.put("emailAddress", request.getParameter("emailAddress"));
			sessionMap.put("country", request.getParameter("country"));
			sessionMap.put("phone", request.getParameter("phone"));
			sessionMap.put("macAddress", request.getParameter("macAddress"));
			sessionMap.put("licenseType", request.getParameter("licenseType"));
			
			request.getSession().setAttribute("sessionMap", sessionMap);
			
			
			logger.info("saving totalqueue purchase info ...");
			StringBuffer content = new StringBuffer("");
			content.append("Name: " + request.getParameter("name") + "<br><br>")
				.append("Company: " + request.getParameter("company") + "<br><br>")
				.append("Industry: " + request.getParameter("industry") + "<br><br>")
				.append("Email: " + request.getParameter("emailAddress") + "<br><br>")
				.append("Country: " + request.getParameter("country") + "<br><br>")
				.append("Phone: " + request.getParameter("phone") + "<br><br>")
				.append("MAC Address: " + request.getParameter("macAddress") + "<br><br>")
				.append("License Type: " + request.getParameter("licenseType") + "<br><br>");
			
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(request.getParameter("name"));
			savedEmail.setEmail(request.getParameter("emailAddress"));
			savedEmail.setPhone(request.getParameter("phone"));
			savedEmail.setFormName("Purchase " + request.getParameter("licenseType"));
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			
			catItems = new ArrayList<CategoryItem>();
			categoryItem = new CategoryItem();
			categoryItem.setName(request.getParameter("licenseType"));
			categoryItem.setPrice(Double.valueOf(request.getParameter("paymentvalue")));
			categoryItem.setOtherDetails("1");
			categoryItem.setSku("SKU");
			catItems.add(categoryItem);
			
			orderId = saveNoLogInCartTempOrder(catItems);
		}
		else if(company.getId() != CompanyConstants.EVEREST)
		{
			// sets the look-ahead count of orders
			this.setOrderCount(Integer.parseInt(cartOrderDelegate.getOrderCount(company).toString()) + 1);
			// validate current user, must not be empty except for webtogo company
			if(isNull(member) && company.getId() != 76)
			{
				return INPUT;
			}
			logger.debug("addToOrder : company  = " + company);
		}

		try
		{
			String totalPrice = "";

			if(request.getParameter("paymentvalue") == null || request.getParameter("paymentvalue").toString().trim().length() == 0)
			{
				return ERROR;
				
			}

			else
			{
				totalPrice = request.getParameter("paymentvalue").toString();

				sessionMap.put("paymentvalue", totalPrice);
				// check if float
				try
				{
					final float amt = Float.parseFloat(totalPrice);

					if(amt <= 0)
					{
						amtothers = false;
						return ERROR;
					}
				}
				catch(final Exception e)
				{
					return ERROR;
				}
			}
			sessionMap.put("paymentvalue", totalPrice);
			logger.debug("CHECKOUT \n\npaymentvalue amt: " + totalPrice);

			if(request.getParameter("itemPackage") != null)
			{
				sessionMap.put("cartSingleItem", request.getParameter("itemPackage"));
			}
			
			if(company.getPalUsername() != null && company.getPalPassword() != null && company.getPalSignature() != null)
			{
				if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2")) || company.getName().equalsIgnoreCase("korphilippines") || (company.getName().equalsIgnoreCase("drugasia") && member == null)
						|| ((company.getName().equalsIgnoreCase("gurkka") || company.getId() == CompanyConstants.GURKKA_TEST) && member == null)
						|| (company.getName().equalsIgnoreCase("onlinedepot") && member == null)
						|| (company.getName().equals("adeventsmanila") && member == null)
						|| (company.getName().equals("hiprecisiononlinestore") && member == null)
						|| (company.getName().equals("totalqueue2"))
						|| (company.getName().equals("saltswim")))
				{
					
					paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl() + "?memberId="
							+ request.getParameter("memberId") + "&orderId=" + orderId, company.getPalCancelUrl());
					
					if(company.getName().equals("totalqueue2") && request.getParameter("testOnly") != null && request.getParameter("testOnly").equals("true")) {
						request.getSession().setAttribute("testOnly", true);
						paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "sandbox", company.getPalSuccessUrl() + "?memberId="
								+ request.getParameter("memberId") + "&orderId=" + orderId, company.getPalCancelUrl());
					}
						
				}
				else
				{
					paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl(), company.getPalCancelUrl());
				}

				// ramgo
				if(company.getId() == CompanyConstants.RAMGO)
				{
					createOrder();
					initOrderItemList();
					orderId = cartOrder.getId();
					paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl() + "?orderId=" + orderId,
							company.getPalCancelUrl());
				}
			}
			
			if(request.getParameter("token") == null)
			{
				String result = "";
				if(company.getPalCurrencyType() != null)
				{
					if(request.getParameter("itemPackage") != null)
					{
						final CategoryItem catItem = categoryItemDelegate.find(Long.parseLong(request.getParameter("itemPackage")));
						result = paypal.setExpressCheckoutRequest(formatter.format(Double.parseDouble(totalPrice)), company.getPalCurrencyType(), null, catItem);
					}
					else
					{
						if(cartItemList.size() > 0)
						{
							result = paypal.setExpressCheckoutRequest(formatter.format(Double.parseDouble(totalPrice)), company.getPalCurrencyType(), cartItemList, null);
						}
						else
						{
							if(catItems != null && catItems.size() != 0)
							{
								paypal.setCategoryItems(catItems);
							}
							result = paypal.setExpressCheckoutRequest(formatter.format(Double.parseDouble(totalPrice)), company.getPalCurrencyType(), null, null);
						}
					}
				}
				if(result.equalsIgnoreCase("Failure"))
				{
					return ERROR;
				}
				pToken = paypal.getToken();
				checkoutUrl = paypal.getPaypalUrl();

			}
			else if((request.getParameter("token") != null && request.getParameter("PAYERID") != null))
			{
				final HashMap result = paypal.GetShippingDetails(request.getParameter("token"));
				request.setAttribute("TOKEN", result.get("TOKEN").toString());
				logger.debug(" \n\n\n\n\nresult TOKen: " + result.get("TOKEN").toString());
			}

		}
		catch(final Exception e)
		{
			final Map parameters = request.getParameterMap();

			for(final Object key : parameters.keySet())
			{
				addActionError(key + ": " + parameters.get(key));
			}

			addActionError("Error : " + e.toString());
			e.printStackTrace();
			return ERROR;

		}
		if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2")))
		{
			checkoutUrl += pToken + "&memberId=111111111111111111111111";
		}
		if(company.getName().equalsIgnoreCase("KORPHILIPPINES"))
		{
			checkoutUrl += pToken + "&memberId=111111111111111111111111";
		}
		if(company.getName().equalsIgnoreCase("DRUGASIA") && member == null)
		{
			checkoutUrl += pToken + "&memberId=111111111111111111111111";
		}
		if(company.getName().equalsIgnoreCase("ONLINEDEPOT") && member == null)
		{
			checkoutUrl += pToken + "&memberId=111111111111111111111111";
		}
		if(company.getId() == CompanyConstants.TOTAL_QUEUE2) 
		{
			checkoutUrl += pToken + "&memberId=111111111111111111111111";			
		}
		if((company.getName().equalsIgnoreCase("gurkka") || company.getId() == CompanyConstants.GURKKA_TEST) && member == null)
		{
			checkoutUrl += pToken + "&memberId=111111111111111111111111";
		}
		if(company.getName().equals("adeventsmanila") || company.getName().equals("hiprecisiononlinestore") || (company.getName().equals("saltswim"))) {
			checkoutUrl += pToken + "&memberId=111111111111111111111111";
		}

		return SUCCESS;
	}
	
	private String globalpay() throws IOException {
		// retrieve all the parameters into a hash map
		Map fields = new HashMap();
		for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
			String fieldName = (String) enumeration.nextElement();
			String fieldValue = request.getParameter(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				fields.put(fieldName, fieldValue);
			}
		}
		
		// no need to send the vpc url, EnableAVSdata and submit button to the vpc
		String vpcURL = (String) fields.remove("virtualPaymentClientURL");
		fields.remove("SubButL");
		
	    // Retrieve the order page URL from the incoming order page and add it to 
	    // the hash map. This is only here to give the user the easy ability to go 
		// back to the Order page. This would not be required in a production system
	    // NB. Other merchant application fields can be added in the same manner
		String againLink = request.getHeader("Referer");
		fields.put("AgainLink", againLink);
	
		// Create MD5 secure hash and insert it into the hash map if it was created
		// created. Remember if SECURE_SECRET = "" it will not be created
		if (SECURE_SECRET != null && SECURE_SECRET.length() > 0) {
			String secureHash = hashAllFields(fields);
			fields.put("vpc_SecureHash", secureHash);
		}
		
		// Create a redirection URL
		StringBuffer buf = new StringBuffer();
		buf.append(vpcURL).append('?');
		appendQueryFields(buf, fields);
	
		// Redirect to Virtual PaymentClient
		globalpayURL = buf.toString();
//	    response.sendRedirect(buf.toString());
	    
	    return SUCCESS;
	}
	
   /**
    * This method is for sorting the fields and creating an MD5 secure hash.
    *
    * @param fields is a map of all the incoming hey-value pairs from the VPC
    * @param buf is the hash being returned for comparison to the incoming hash
    */
	private String hashAllFields(Map fields) {
        
        // create a list and sort it
    	List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        
        // create a buffer for the md5 input and add the secure secret first
        StringBuffer buf = new StringBuffer();
        buf.append(SECURE_SECRET);
        
        // iterate through the list and add the remaining field values
        Iterator itr = fieldNames.iterator();
        
		while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                buf.append(fieldValue);
            }
 	    }
             
        MessageDigest md5 = null;
        byte[] ba = null;
        
        // create the md5 hash and UTF-8 encode it
        try {
            md5 = MessageDigest.getInstance("MD5");
            ba = md5.digest(buf.toString().getBytes("UTF-8"));
         } catch (Exception e) {} // wont happen
       
        return hex(ba);
	} 
	
    /**
     * Returns Hex output of byte array
     */
	private static String hex(byte[] input) {
    	// create a StringBuffer 2x the size of the hash array
        StringBuffer sb = new StringBuffer(input.length * 2);

        // retrieve the byte array data, convert it to hex
        // and add it to the StringBuffer
        for (int i = 0; i < input.length; i++) {
        	sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
            sb.append(HEX_TABLE[input[i] & 0xf]);
        }
        return sb.toString();
    }	
	
   /**
    * This method is for creating a URL query string.
    *
    * @param buf is the inital URL for appending the encoded fields to
    * @param fields is the input parameters from the order page
    */
    // Method for creating a URL query string
    private void appendQueryFields(StringBuffer buf, Map fields) {
        
        // create a list
    	List fieldNames = new ArrayList(fields.keySet());
        Iterator itr = fieldNames.iterator();
        
        // move through the list and create a series of URL key/value pairs
        while (itr.hasNext()) {
        	String fieldName = (String)itr.next();
            String fieldValue = (String)fields.get(fieldName);
            
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // append the URL parameters
            	buf.append(URLEncoder.encode(fieldName));
                buf.append('=');
                buf.append(URLEncoder.encode(fieldValue));
            }

            // add a '&' to the end if we have more fields coming.
            if (itr.hasNext()) {
                buf.append('&');
            }
        }
    } 	

	private CategoryItem getShippingCost()
	{
		final CategoryItem catItem = new CategoryItem();
		catItem.setId(0L);
		catItem.setSku("0");
		catItem.setName("Shipping Cost");
		catItem.setDescription("");

		String shippingCostAmount = "0.00";
		String selectedProvince = request.getParameter("1e|province");
		if(selectedProvince != null)
		{
			final String manilaCities = "Manila";

			if(manilaCities.indexOf(selectedProvince) != -1)
			{
				selectedProvince = "0.00";
			}
			else
			{
				selectedProvince = "150.00";
			}

			if(request.getParameter("otherCityCheckBox") != null && request.getParameter("otherCityCheckBox").equalsIgnoreCase("otherLocation"))
			{
				selectedProvince = "250.00";
			}

			if(request.getParameter("1e|province").equalsIgnoreCase("sample"))
			{
				selectedProvince = "2.00";
			}

			shippingCostAmount = selectedProvince;
		}

		catItem.setPrice(new Double(shippingCostAmount));
		catItem.setOtherDetails("1");
		return catItem;
	}

	private ShoppingCartItem getEcommerceShippingCost()
	{
		final ShoppingCartItem cartItem = new ShoppingCartItem();
		itemDetail = new ItemDetail();
		itemDetail.setDescription("");
		itemDetail.setName("Shipping Cost");
		itemDetail.setPrice(new Double(request.getParameter("shippingvalue")));
		cartItem.setId(0L);
		cartItem.setCompany(company);
		cartItem.setQuantity(0);
		cartItem.setItemDetail(itemDetail);

		return cartItem;
	}

	// yes
	private ShoppingCartItem getGiftcardShippingCost()
	{
		final ShoppingCartItem cartItem = new ShoppingCartItem();
		itemDetail = new ItemDetail();
		itemDetail.setDescription("");
		itemDetail.setName("Shipping Cost");
		itemDetail.setPrice(new Double(request.getParameter("shippingvalue")));
		cartItem.setId(0L);
		cartItem.setCompany(company);
		cartItem.setQuantity(0);
		cartItem.setItemDetail(itemDetail);

		return cartItem;
	}

	private ShoppingCartItem getDrugasiaShippingCost()
	{
		final ShoppingCartItem cartItem = new ShoppingCartItem();
		itemDetail = new ItemDetail();
		itemDetail.setDescription("");
		itemDetail.setName("Shipping Cost");
		itemDetail.setPrice(new Double(request.getParameter("shippingvalue")));
		cartItem.setId(0L);
		cartItem.setCompany(company);
		cartItem.setQuantity(0);
		cartItem.setItemDetail(itemDetail);

		return cartItem;
	}

	private ShoppingCartItem getGurkkaShippingCost()
	{
		final ShoppingCartItem cartItem = new ShoppingCartItem();
		itemDetail = new ItemDetail();
		itemDetail.setDescription("Shipping Cost");
		itemDetail.setName("Shipping Cost");
		itemDetail.setPrice(new Double((Double) (sessionMap.get("shippingvalue") != null
			? sessionMap.get("shippingvalue")
			: 0.00)));
		cartItem.setCompany(company);
		cartItem.setQuantity(1);
		cartItem.setItemDetail(itemDetail);
		cartItem.setShoppingCart(getShoppingCart());
		shoppingCartItemDelegate.insert(cartItem);

		return cartItem;
	}

	private ShoppingCartItem getDrugasiaDiscount()
	{
		final ShoppingCartItem cartItem = new ShoppingCartItem();
		itemDetail = new ItemDetail();
		itemDetail.setDescription("");
		itemDetail.setName("Discount");
		itemDetail.setPrice(new Double(request.getParameter("discount")));
		cartItem.setId(0L);
		cartItem.setCompany(company);
		cartItem.setQuantity(0);
		cartItem.setItemDetail(itemDetail);

		return cartItem;
	}

	private ShoppingCartItem getMdtShippingCost()
	{
		final ShoppingCartItem cartItem = new ShoppingCartItem();
		itemDetail = new ItemDetail();
		itemDetail.setDescription("");
		itemDetail.setName("Shipping Cost");
		itemDetail.setPrice(new Double(request.getParameter("shippingvalue")));
		cartItem.setId(0L);
		cartItem.setCompany(company);
		cartItem.setQuantity(0);
		cartItem.setItemDetail(itemDetail);

		return cartItem;
	}

	private ShoppingCartItem getOnlinedepotShippingCost()
	{
		final ShoppingCartItem cartItem = new ShoppingCartItem();
		itemDetail = new ItemDetail();
		itemDetail.setDescription("");
		itemDetail.setName("Shipping Cost");
		itemDetail.setPrice(new Double(request.getParameter("shippingvalue")));
		cartItem.setId(0L);
		cartItem.setCompany(company);
		cartItem.setQuantity(0);
		cartItem.setItemDetail(itemDetail);

		return cartItem;
	}

	private boolean isShippingFound(List<CategoryItem> catItems)
	{
		boolean isShippingCostFound = false;
		for(final CategoryItem cI : catItems)
		{
			if(cI.getName().indexOf("Shipping Cost") != -1)
			{
				isShippingCostFound = true;
			}
		}
		return isShippingCostFound;
	}

	boolean hasRewardsProgram = false;

	public String checkoutNoPaypal() throws Exception
	{
		try
		{
			initShoppingCart();

			hasRewardsProgram = company.getName().equalsIgnoreCase("westerndigital");

			if(hasRewardsProgram)
			{
				if(new Double(shoppingCart.getTotalPrice()).compareTo(new Double(member.getValue())) > 0)
				{
					this.setNotificationMessage("Approved points not enough for cart items.");
					return ERROR;
				}
			}

			if(cartItemList != null)
			{
				int cartCounter = 0;
				for(final ShoppingCartItem item : cartItemList)
				{
					itemDetail = item.getItemDetail();
					if(hasRewardsProgram)
					{
						emailBody += "<br><br>Item: " + itemDetail.getName();
						emailBody += "<br>Qty: " + item.getQuantity();
						emailBody += "<br>Points: " + itemDetail.getPrice();
						emailBody += "<br>Subtotal: " + itemDetail.getPrice() * item.getQuantity();
						emailBody += "<br><br>TOTAL\t\t" + shoppingCart.getTotalPrice() + " points";

					}
					else
					{
						if(cartCounter == 0)
						{
							emailBody += "<tr>";
							emailBody += "<td align='center'>Product Code</td>";
							emailBody += "<td align='center'>Item#</td>";
							emailBody += "<td align='center'>Name</td>";
							emailBody += "<td align='center'>Quantity</td>";
							emailBody += "<td align='center'>Unit of Measurement</td>";
							emailBody += "</tr>";
						}
						emailBody += "<tr>";
						emailBody += "<td align='center'>" + itemDetail.getSku() + "</td>";
						emailBody += "<td align='center'>" + item.getId() + "</td>";
						emailBody += "<td align='center'>" + itemDetail.getName() + "</td>";
						emailBody += "<td align='center'>" + item.getQuantity() + "</td>";
						if(company.getName().equalsIgnoreCase("PMC"))
						{
							final CategoryItem categoryItem = categoryItemDelegate.find(itemDetail.getRealID());
							if(categoryItem != null)
							{
								emailBody += "<td align='center'>" + categoryItem.getShortDescription() + "</td>";
							}
							else
							{
								emailBody += "<td align='center'></td>";
							}
						}
						emailBody += "</tr></td>";
					}
					cartCounter++;
				}

				if(hasRewardsProgram)
				{
					// email notifications
					sendEmailToMember(member);
					sendEmailToCompanyAccount(member);

					// update points
					final MemberDelegate memberDelegate = MemberDelegate.getInstance();
					final Member currentMember = memberDelegate.find(member.getId());
					if(currentMember != null)
					{
						currentMember.setValue(Double.toString(new Double(member.getValue()) - shoppingCart.getTotalPrice()));
						memberDelegate.update(currentMember);
					}
					this.setNotificationMessage("Reward redemption request submitted.");
				}
				else
				{
					emailBody = "<table width='70%'>" + emailBody + "</table>";
					sendEmailToMember(member);
					sendEmailToCompanyAccount(member);
					this.setNotificationMessage("Request a 	Quote submitted.");
				}

				// create new order
				createOrderBasic();
				// create order item list
				cartId = cartOrder.getId();
				initOrderItemList();

				// clear shopping cart
				final ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
				for(final ShoppingCartItem item : cartItemList)
				{
					itemDetail = item.getItemDetail();
					shoppingCartItemDelegate.delete(shoppingCartItemDelegate.find(shoppingCart, itemDetail));
				}
			}
			else
			{
				this.setNotificationMessage(ERROR);
				return ERROR;
			}

		}
		catch(final Exception e)
		{
			addActionError("Error : " + e.toString());
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	public String checkoutBank()
	{
		final CategoryItem catItem = categoryItemDelegate.find(cartItemList.get(0).getItemDetail().getRealID());
		sessionMap.put("catItem", catItem);

		sendGiftcardEmailShipping(member);

		saveGiftcardTransaction();

		// no
		// giftcard
		if(company.getId() == 268)
		{
			createOrder();
			initOrderItemList();
		}

		return SUCCESS;
	}

	private String paymentForRegistration(String totalamount)
	{
		final String additionalDetails = "&registrationTitle=" + request.getParameter("registrationTitle") + "&registrationBySet=" + request.getParameter("registrationBySet")
				+ "&registrationParticipants=" + request.getParameter("registrationParticipants");
		paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl() + "?paymentvalue=" + totalamount + additionalDetails,
				company.getPalCancelUrl());

		final CategoryItem membershipRegistration = getRegistrationDetails();

		final String result = paypal.setExpressCheckoutRequest(formatter.format(Double.parseDouble(totalamount)), company.getPalCurrencyType(), null, membershipRegistration);
		if(result.equalsIgnoreCase("Failure"))
		{
			return ERROR;
		}
		pToken = paypal.getToken();
		checkoutUrl = paypal.getPaypalUrl();
		return SUCCESS;
	}

	private CategoryItem getRegistrationDetails()
	{
		final CategoryItem membershipRegistration = new CategoryItem();
		String additionalDetails = "\n" + request.getParameter("registrationTitle");
		if(request.getParameter("registrationBySet") != null && request.getParameter("registrationBySet").equalsIgnoreCase("Individual"))
		{
			additionalDetails += "(Individual)";
		}
		else if(request.getParameter("registrationBySet") != null && request.getParameter("registrationBySet").equalsIgnoreCase("Group"))
		{
			additionalDetails += "(Group-(" + request.getParameter("registrationParticipants") + "))";
		}

		membershipRegistration.setName("Registration Payment for " + company.getNameEditable() + additionalDetails);
		membershipRegistration.setId(1L);
		return membershipRegistration;
	}

	/**
	 * Returns {@code SUCCESS} if mail was successfully sent to the user,
	 * otherwise {@code ERROR}.
	 *
	 * @return - {@code SUCCESS} if mail was successfully sent to the user,
	 *         otherwise {@code ERROR}
	 */
	public String sendEmailToMember(Member member)
	{
		String emailTitle;
		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			// String emailMessage = request.getParameter("emailMessage");
			final StringBuffer content = new StringBuffer();
			content.append("Hi " + member.getFirstname() + " " + member.getLastname());

			if(hasRewardsProgram)
			{
				content.append(",<br><br>" + "You have requested to redeem the following reward item(s):");
				emailTitle = "Reward items redemption";

			}
			else
			{
				emailTitle = "Request a Quote";
				content.append(",<br><br>" + "Quote details:<br>");
			}

			content.append(emailBody);
			content.append("<br><br><br>Thank you.<br><br><br>All the Best, <br><br>");
			content.append("The " + company.getNameEditable() + " Team");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	public String sendEmailToCompanyAccount(Member member)
	{
		String emailTitle;
		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();
			final String cc = request.getParameter("to");

			content.append(member.getFirstname() + " " + member.getLastname());
			if(hasRewardsProgram)
			{
				emailTitle = "Redemption of reward item(s): ";
				content.append(" has requested to redeem the following reward items:<br><br>");
				content.append("Firstname: " + member.getFirstname() + "<br>" + "Lastname: " + member.getLastname() + "<br>" + "Email: " + member.getEmail() + "<br>" + "Username: "
						+ member.getUsername() + "<br>");
			}
			else
			{
				emailTitle = "Request a Quote ";
				content.append(" has requested a Quote<br><br>");
				if(company.getName().equalsIgnoreCase("PMC"))
				{
				}
				else
				{
					content.append("Company Name: " + member.getCompany() + "<br>");
				}
				content.append("Name: " + member.getFullName() + "<br>");
				content.append("Email: " + member.getEmail() + "<br>");
				content.append("Contact Number: " + member.getLandline() + "<br><br>");

			}
			content.append(emailBody);
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			final String to = company.getEmail().concat("," + cc);
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to.split(","), emailTitle + member.getFirstname() + " " + member.getLastname(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;
	}

	@SuppressWarnings("unchecked")
	public String sendEcommerceEmailShipping(Member member)
	{
		String emailTitle;
		final String to[] = { "", "" };
		to[0] = member.getEmail();
		to[1] = "orders@onestopshopmarket.com";

		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();

			emailTitle = "Shipping Information";

			content.append("Customer Details:");
			content.append("Name: " + member.getFirstname() + " " + member.getLastname() + "<br/>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("<br/><br/>");
			content.append("Shipping Details:<br/>");

			request.setAttribute("deliverydate", request.getParameter("deliverydate"));
			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("address1", request.getParameter("address1"));
			request.setAttribute("address2", request.getParameter("address2"));
			request.setAttribute("city", request.getParameter("city"));
			request.setAttribute("zipcode", request.getParameter("zipcode"));
			request.setAttribute("mobilenumber", request.getParameter("mobilenumber"));
			request.setAttribute("phonenumber", request.getParameter("phonenumber"));
			request.setAttribute("email", request.getParameter("email"));

			content.append("Preferred Delivery Date: " + request.getParameter("deliverydate") + "<br/>");
			content.append("Recipient Name: " + request.getParameter("name") + "<br/>");
			content.append("Address 1: " + request.getParameter("address1") + "<br/>");
			content.append("Address 2: " + request.getParameter("address2") + "<br/>");
			content.append("Area: " + request.getParameter("province") + "<br/>");
			content.append("Location: " + request.getParameter("city") + "<br/>");
			content.append("Zip Code: " + request.getParameter("zipcode") + "<br/>");
			content.append("Mobile Number: " + request.getParameter("mobilenumber") + "<br/>");
			content.append("Phone Number: " + request.getParameter("phonenumber") + "<br/>");
			content.append("Email: " + request.getParameter("email") + "<br/><br/>");

			content.append("<table border='0'>");
			content.append("<tr>");
			content.append("<td align='center'>#</td>");
			content.append("<td align='center'>Name</td>");
			content.append("<td align='center'>Quantity</td>");
			content.append("<td align='center'>Price</td>");
			content.append("<td align='center'>Subtotal</td>");
			content.append("</tr>");
			itemTotalPrice = 0;
			totalPrice = 0;

			for(int i = 0; i < cartItemList.size(); i++)
			{
				itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity();

				if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
				{
					itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice();
				}

				totalPrice = itemTotalPrice + totalPrice;

				content.append("<tr>");
				content.append("<td width='8%' align='center'>");
				content.append((i + 1) + ".&nbsp;");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getItemDetail().getName());
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getQuantity());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(cartItemList.get(i).getItemDetail().getPrice());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(itemTotalPrice);
				content.append("</td>");
				content.append("</tr>");
			}

			content.append("<tr><td colspan='5' align='right'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='5' align='right'>Total Price: " + totalPrice + "</td></tr>");
			content.append("</table>");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	@SuppressWarnings("unchecked")
	public String sendEcommerceEmailPreOrder(Member member)
	{
		String emailTitle;
		final String to[] = { "", "" };
		to[0] = member.getEmail();
		to[1] = "orders@onestopshopmarket.com";

		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();

			emailTitle = "Pre-order Shipping Information";

			content.append("Customer Details:");
			content.append("Name: " + member.getFirstname() + " " + member.getLastname() + "<br/>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("<br/><br/>");
			content.append("Shipping Details:<br/>");

			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("address1", request.getParameter("address1"));
			request.setAttribute("address2", request.getParameter("address2"));
			request.setAttribute("city", request.getParameter("city"));
			request.setAttribute("zipcode", request.getParameter("zipcode"));
			request.setAttribute("mobilenumber", request.getParameter("mobilenumber"));
			request.setAttribute("phonenumber", request.getParameter("phonenumber"));
			request.setAttribute("email", request.getParameter("email"));

			content.append("Recipient Name: " + request.getParameter("name") + "<br/>");
			content.append("Address 1: " + request.getParameter("address1") + "<br/>");
			content.append("Address 2: " + request.getParameter("address2") + "<br/>");
			content.append("Area: " + request.getParameter("province") + "<br/>");
			content.append("Location: " + request.getParameter("city") + "<br/>");
			content.append("Zip Code: " + request.getParameter("zipcode") + "<br/>");
			content.append("Mobile Number: " + request.getParameter("mobilenumber") + "<br/>");
			content.append("Phone Number: " + request.getParameter("phonenumber") + "<br/>");
			content.append("Email: " + request.getParameter("email") + "<br/><br/>");

			content.append("<table border='0'>");
			content.append("<tr>");
			content.append("<td align='center'>#</td>");
			content.append("<td align='center'>Name</td>");
			content.append("<td align='center'>Quantity</td>");
			content.append("<td align='center'>Price</td>");
			content.append("<td align='center'>Subtotal</td>");
			content.append("</tr>");
			itemTotalPrice = 0;
			totalPrice = 0;

			for(int i = 0; i < preOrderItemList.size(); i++)
			{
				itemTotalPrice = preOrderItemList.get(i).getCategoryItem().getPrice() * preOrderItemList.get(i).getQuantity();

				if(preOrderItemList.get(i).getCategoryItem().getName().equals("Shipping Cost"))
				{
					itemTotalPrice = preOrderItemList.get(i).getCategoryItem().getPrice();
				}

				totalPrice = itemTotalPrice + totalPrice;

				content.append("<tr>");
				content.append("<td width='8%' align='center'>");
				content.append((i + 1) + ".&nbsp;");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(preOrderItemList.get(i).getCategoryItem().getName());
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(preOrderItemList.get(i).getQuantity());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(preOrderItemList.get(i).getCategoryItem().getPrice());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(itemTotalPrice);
				content.append("</td>");
				content.append("</tr>");
			}

			content.append("<tr><td colspan='5' align='right'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='5' align='right'>Total Price: " + totalPrice + "</td></tr>");
			content.append("</table>");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	// yes
	@SuppressWarnings("unchecked")
	public String sendGiftcardEmailShipping(Member member)
	{
		String emailTitle;
		final String to[] = { "", "" };
		to[0] = member.getEmail();
		to[1] = company.getEmail();

		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();

			emailTitle = "Order Information";

			content.append("Thank you for choosing Gift Card Co., the perfect gift anytime! ");

			if(request.getParameter("paymenttype").equals("bankdeposit"))
			{
				content.append("Please upload the deposit slip before we can process your order.");
			}

			content.append("<br/><br/>Customer Details:");
			content.append("Name: " + member.getFirstname() + " " + member.getLastname() + "<br/>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("<br/><br/>");

			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("address1", request.getParameter("address1"));
			request.setAttribute("address2", request.getParameter("address2"));
			request.setAttribute("city", request.getParameter("city"));
			request.setAttribute("zipcode", request.getParameter("zipcode"));
			request.setAttribute("contactnumber", request.getParameter("contactnumber"));
			request.setAttribute("email", request.getParameter("email"));

			if(request.getParameter("shippingtype").equals("delivery"))
			{
				shippingType = ShippingType.DELIVERY;
				content.append("Shipping Details:<br/>");
				content.append("Recipient Name: " + request.getParameter("name") + "<br/>");
				content.append("Address 1: " + request.getParameter("address1") + "<br/>");
				content.append("Address 2: " + request.getParameter("address2") + "<br/>");
				content.append("City: " + request.getParameter("city") + "<br/>");
				content.append("Zip Code: " + request.getParameter("zipcode") + "<br/>");
				content.append("Contact Number: " + request.getParameter("contactnumber") + "<br/>");
				content.append("Email: " + request.getParameter("email") + "<br/><br/>");
			}

			content.append("<table border='0'>");
			content.append("<tr>");
			content.append("<td align='center'>#</td>");
			content.append("<td align='center'>Name</td>");
			content.append("<td align='center'>Quantity</td>");
			content.append("<td align='center'>Price</td>");
			content.append("<td align='center'>Subtotal</td>");
			content.append("</tr>");
			itemTotalPrice = 0;
			totalPrice = 0;
			int i = 0;

			for(i = 0; i < cartItemList.size(); i++)
			{
				itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity();

				if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
				{
					itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice();
				}

				totalPrice = itemTotalPrice + totalPrice;

				content.append("<tr>");
				content.append("<td width='8%' align='center'>");
				content.append((i + 1) + ".&nbsp;");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getItemDetail().getName());
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getQuantity());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(cartItemList.get(i).getItemDetail().getPrice());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(itemTotalPrice);
				content.append("</td>");
				content.append("</tr>");
			}

			if(request.getParameter("paymenttype").equals("bankdeposit") && request.getParameter("shippingtype").equals("delivery"))
			{
				shippingType = ShippingType.DELIVERY;
				content.append("<tr>");
				content.append("<td width='8%' align='center'>");
				content.append((i + 1) + ".&nbsp;");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append("Shipping Cost");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append("0");
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(request.getParameter("shippingvalue"));
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(request.getParameter("shippingvalue"));
				content.append("</td>");
				content.append("</tr>");

				totalPrice = totalPrice + Double.parseDouble(request.getParameter("shippingvalue"));
			}

			content.append("<tr><td colspan='5' align='right'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='5' align='right'>Total Price: " + totalPrice + "</td></tr>");
			content.append("</table>");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	// no
	public int saveGiftcardTransaction()
	{
		int maxInvoice = memberFileItemDelegate.findMaxMemberFileItem(company);
		maxInvoice = maxInvoice + 1;
		transactionNumber = maxInvoice;

		final MemberFile memberFile = new MemberFile();
		final MemberFileItems memberFileItems = new MemberFileItems();

		memberFile.setMember(member);
		memberFile.setStatus("Pending");
		memberFileDelegate.insert(memberFile);

		memberFileItems.setMemberFile(memberFile);
		memberFileItems.setDescription(request.getParameter("paymenttype"));
		memberFileItems.setCompany(company);
		memberFileItems.setInvoiceNumber(Integer.toString(maxInvoice));
		memberFileItems.setAmount(Double.valueOf(request.getParameter("paymentvalue")));
		memberFileItemDelegate.insert(memberFileItems);

		return maxInvoice;
	}

	@SuppressWarnings("unchecked")
	public String sendDrugasiaEmailShipping(Member member)
	{
		String emailTitle;
		final String to[] = { "", "" };
		to[0] = member.getEmail();
		to[1] = company.getEmail();

		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();

			emailTitle = "Shipping Information";

			content.append("Customer Details:");
			content.append("Name: " + member.getFirstname() + " " + member.getLastname() + "<br/>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("<br/><br/>");
			content.append("Shipping Details:<br/>");

			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("address1", request.getParameter("address1"));
			request.setAttribute("address2", request.getParameter("address2"));
			request.setAttribute("city", request.getParameter("city"));
			request.setAttribute("zipcode", request.getParameter("zipcode"));
			request.setAttribute("mobilenumber", request.getParameter("mobilenumber"));
			request.setAttribute("phonenumber", request.getParameter("phonenumber"));
			request.setAttribute("email", request.getParameter("email"));

			content.append("Recipient Name: " + request.getParameter("name") + "<br/>");
			content.append("Address 1: " + request.getParameter("address1") + "<br/>");
			content.append("Address 2: " + request.getParameter("address2") + "<br/>");
			content.append("Area: " + request.getParameter("province") + "<br/>");
			content.append("Location: " + request.getParameter("city") + "<br/>");
			content.append("Zip Code: " + request.getParameter("zipcode") + "<br/>");
			content.append("Mobile Number: " + request.getParameter("mobilenumber") + "<br/>");
			content.append("Phone Number: " + request.getParameter("phonenumber") + "<br/>");
			content.append("Email: " + request.getParameter("email") + "<br/><br/>");

			content.append("<table border='0'>");
			content.append("<tr>");
			content.append("<td align='center'>#</td>");
			content.append("<td align='center'>Name</td>");
			content.append("<td align='center'>Quantity</td>");
			content.append("<td align='center'>Price</td>");
			content.append("<td align='center'>Subtotal</td>");
			content.append("</tr>");
			itemTotalPrice = 0;
			totalPrice = 0;

			for(int i = 0; i < cartItemList.size(); i++)
			{
				itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity();

				if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
				{
					itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice();
				}

				totalPrice = itemTotalPrice + totalPrice;

				content.append("<tr>");
				content.append("<td width='8%' align='center'>");
				content.append((i + 1) + ".&nbsp;");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getItemDetail().getName());
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getQuantity());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(cartItemList.get(i).getItemDetail().getPrice());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(itemTotalPrice);
				content.append("</td>");
				content.append("</tr>");
			}

			content.append("<tr><td colspan='5' align='right'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='5' align='right'>Total Price: " + totalPrice + "</td></tr>");
			content.append("</table>");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	public String sendGurkkaEmailShipping(Member member, Double freebiesSavings, String type)
	{
		// Don't include any parameters from request.
		// This method is used by dragonpay, ipay88 and paypal return methods.
		String emailTitle;
		final String[] toMember = { "" };
		toMember[0] = member.getEmail();
		if(company == null)
		{
			company = companyDelegate.find(Long.valueOf(CompanyConstants.GURKKA_TEST));
		}

		final String[] toComp = company.getEmail().split(",");

		ArrayUtils.addAll(toMember, toComp);

		final CompanySettings companySettings = companySettingsDelegate.find(company);
		String emailUsername = null;
		String emailPassword = null;
		String smtpHost = null;
		int smtpPort = 25;
		if(companySettings != null)
		{
			emailUsername = companySettings.getEmailUserName();
			emailPassword = companySettings.getEmailPassword();
			smtpHost = companySettings.getSmtp();
			try
			{
				smtpPort = Integer.parseInt(companySettings.getPortNumber());
			}
			catch(final Exception e)
			{
				e.printStackTrace();
			}
		}

		if(member != null)
		{
			final String receiverName = StringUtils.trimToNull(cartOrder != null
				? cartOrder.getAddress2()
				: "");
			final String receiverAddress = StringUtils.trimToNull(cartOrder != null
				? cartOrder.getAddress1()
				: "");
			final String receiverContact = StringUtils.trimToNull(cartOrder != null
				? cartOrder.getPhoneNumber()
				: "");
			final String receiverArea = StringUtils.trimToNull(cartOrder != null
				? cartOrder.getState()
				: "");
			final String receiverLocation = StringUtils.trimToNull(cartOrder != null
				? cartOrder.getCity()
				: "");

			if(!StringUtils.isEmpty(emailUsername) && !StringUtils.isEmpty(emailPassword) && !StringUtils.isEmpty(smtpHost))
			{
				logger.info("Connecting by Email Setting");
				EmailUtil.connect(smtpHost, smtpPort, emailUsername, emailPassword);
			}
			else
			{
				// EmailUtil.connect("smtp.gmail.com", 587, "system@ivant.com","ivanttechnologies2009");// LOCAL TEST
				logger.info("Connecting by WebToGo Default Setting");
				EmailUtil.connect("smtp.gmail.com", 25);
			}
			final StringBuffer content = new StringBuffer();

			if(type.equalsIgnoreCase("REWARD"))
			{
				emailTitle = "Rewards Redemption by " + (!StringUtils.isEmpty(member.getFullName())
					? member.getFullName()
					: member.getReg_companyName());
			}
			else if(type.equalsIgnoreCase("NEW") || type.equalsIgnoreCase("NEWPAYPAL"))
			{
				emailTitle = "New Order from Customer " + (!StringUtils.isEmpty(member.getFullName())
					? member.getFullName()
					: member.getReg_companyName());

			}
			else
			{
				emailTitle = "Shipping Information";
			}

			String modeOfDelivery = "";

			if(type.equalsIgnoreCase("NEWPAYPAL") || type.equalsIgnoreCase("PAYPAL"))
			{
				modeOfDelivery = member.getPurpose();
			}
			else
			{
				if(cartOrder != null)
				{
					modeOfDelivery = cartOrder.getShippingType().getValue();
				}
			}

			final String name = !StringUtils.isEmpty(member.getReg_companyName())
				? member.getReg_companyName()
				: member.getFirstname() + " " + member.getLastname();

			content.append("Customer Details:");
			content.append("Name: " + name + "<br/>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("<br/><br/>");
			content.append("Shipping Details:<br/>");
			content.append("Mode of Delivery: " + modeOfDelivery + "<br/>");

			if(receiverName != null)
			{
				content.append("Receiver's Information: " + "<br/>");
				content.append("Receiver's Name: " + receiverName + "<br/>");
			}
			if(receiverAddress != null)
			{
				content.append("Receiver's Address: " + receiverAddress + "<br/>");
			}
			if(receiverContact != null)
			{
				content.append("Receiver's Contact No.: " + receiverContact + "<br/>");
			}
			if(receiverArea != null)
			{
				content.append("Receiver's Area: " + receiverArea + "<br/>");
			}
			if(receiverLocation != null)
			{
				content.append("Receiver's Location: " + receiverLocation + "<br/>");
			}

			if(StringUtils.containsIgnoreCase(type, PaymentType.CASH.getValue()) || type.equalsIgnoreCase("NEW"))
			{
				boolean toUpdate = false;
				if(StringUtils.isNotEmpty(request.getParameter("name")) && !request.getParameter("name").equalsIgnoreCase(member.getFullName()))
				{
					member.setFullName(request.getParameter("name"));
					toUpdate = true;
				}
				if(StringUtils.isNotEmpty(request.getParameter("address1")) && !request.getParameter("address1").equalsIgnoreCase(member.getAddress1()))
				{
					member.setAddress1(request.getParameter("address1"));
					toUpdate = true;
				}
				/*
				 * if(StringUtils.isNotEmpty(request.getParameter("address2")) && !request.getParameter("address2").equalsIgnoreCase(member.getAddress2())){
				 * member.setAddress2(request.getParameter("address2"));
				 * toUpdate = true;
				 * }
				 */
				if(StringUtils.isNotEmpty(request.getParameter("city")) && !request.getParameter("city").equalsIgnoreCase(member.getCity()))
				{
					member.setCity(request.getParameter("city"));
					toUpdate = true;
				}
				if(StringUtils.isNotEmpty(request.getParameter("zipcode")) && !request.getParameter("zipcode").equalsIgnoreCase(member.getZipcode()))
				{
					member.setZipcode(request.getParameter("zipcode"));
					toUpdate = true;
				}
				if(StringUtils.isNotEmpty(request.getParameter("mobilenumber")) && !request.getParameter("mobilenumber").equalsIgnoreCase(member.getMobile()))
				{
					member.setMobile(request.getParameter("mobilenumber"));
					toUpdate = true;
				}
				if(StringUtils.isNotEmpty(request.getParameter("phonenumber")) && !request.getParameter("phonenumber").equalsIgnoreCase(member.getLandline()))
				{
					member.setLandline(request.getParameter("phonenumber"));
					toUpdate = true;
				}
				if(StringUtils.isNotEmpty(request.getParameter("email")) && !request.getParameter("email").equalsIgnoreCase(member.getEmail()))
				{
					member.setEmail(request.getParameter("email"));
					toUpdate = true;
				}
				if(StringUtils.isNotEmpty(request.getParameter("receiver")) && !request.getParameter("receiver").equalsIgnoreCase(member.getInfo4()))
				{
					member.setInfo4(request.getParameter("receiver"));
					toUpdate = true;
				}
				if(toUpdate == true)
				{
					memberDelegate.update(member);
				}

			}

			final String address = !StringUtils.isEmpty(member.getReg_companyAddress())
				? member.getReg_companyAddress()
				: member.getAddress1();
			// content.append("Recipient Name: " + (member.getInfo4() != null ? member.getInfo4() : member.getFullName()) + "<br/>");
			content.append("Address: " + address + "<br/>");
			// content.append("Address 2: " + member.getAddress2() + "<br/>");
			content.append("Area: " + member.getProvince() + "<br/>");
			content.append("Location: " + member.getCity() + "<br/>");
			content.append("Zip Code: " + member.getZipcode() + "<br/>");
			content.append("Mobile Number: " + member.getMobile() + "<br/>");
			content.append("Landline Number: " + member.getLandline() + "<br/>");
			content.append("Fax: " + member.getFax() + "<br/>");
			content.append("Email: " + member.getEmail() + "<br/><br/>");

			content.append("<table border='0'>");
			content.append("<tr>");
			content.append("<td align='center'>#</td>");
			content.append("<td align='center'>Name</td>");
			content.append("<td align='center'>Quantity</td>");
			content.append("<td align='center'>Price</td>");
			content.append("<td align='center'>Subtotal</td>");
			content.append("</tr>");
			itemTotalPrice = 0;
			totalPrice = 0;

			/*
			 * if(type.equalsIgnoreCase("COD/COPU") || type.equalsIgnoreCase("NEW")){
			 * for(int i=0; i<cartItemList.size();i++)
			 * {
			 * itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity();
			 * if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
			 * {
			 * itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice();
			 * }
			 * if(cartItemList.get(i).getQuantity() == null || cartItemList.get(i).getQuantity() <= 0
			 * && !cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost")
			 * && !StringUtils.containsIgnoreCase(cartItemList.get(i).getItemDetail().getName(), "freebie")
			 * )
			 * {
			 * continue;
			 * }
			 * totalPrice = itemTotalPrice + totalPrice;
			 * content.append("<tr>");
			 * content.append("<td width='8%' align='center'>");
			 * content.append((i+1) + ".&nbsp;");
			 * content.append("</td>");
			 * content.append("<td width='18%' align='center'>");
			 * content.append(cartItemList.get(i).getItemDetail().getName());
			 * content.append("</td>");
			 * content.append("<td width='18%' align='center'>");
			 * content.append(cartItemList.get(i).getQuantity());
			 * content.append("</td>");
			 * content.append("<td width='18%' align='right'>");
			 * content.append(cartItemList.get(i).getItemDetail().getPrice());
			 * content.append("</td>");
			 * content.append("<td width='18%' align='right'>");
			 * content.append(itemTotalPrice);
			 * content.append("</td>");
			 * content.append("</tr>");
			 * }
			 * if(cartItemList != null && !cartItemList.isEmpty())
			 * {
			 * ShoppingCart shoppingCart = null;
			 * final Iterator<ShoppingCartItem> iterator = cartItemList.iterator();
			 * while (iterator.hasNext() && shoppingCart == null)
			 * {
			 * ShoppingCartItem shoppingCartItem = (ShoppingCartItem) iterator.next();
			 * if(shoppingCartItem.getShoppingCart() != null)
			 * {
			 * shoppingCart = shoppingCartDelegate.find(shoppingCartItem.getShoppingCart().getId());
			 * }
			 * }
			 * if(shoppingCart != null)
			 * {
			 * final HttpSession httpSession = request.getSession(true);
			 * httpSession.setAttribute("shoppingCartCount", Integer.valueOf(0));
			 * }
			 * }
			 * }else {
			 */
			// freebiesSavings = GurkkaMemberUtil.computeMemberPointsFromCart(company, member, cartOrder);
			final ObjectList<CartOrderItem> cartOrderItems = cartOrderItemDelegate.findAll(cartOrder);
			int c = 0;
			for(final CartOrderItem i : cartOrderItems)
			{
				final Double price = i.getItemDetail().getPrice();
				final Integer quantity = i.getQuantity();
				final boolean isDiscount = CartOrderUtil.hasDiscount(i);
				
				if(!isDiscount)
				{
					itemTotalPrice = price * quantity;
				}
				else
				{
					itemTotalPrice = -(i.getItemDetail().getDiscount());
				}

				/*
				 * if (i.getQuantity() == null || i.getQuantity() <= 0
				 * && !i.getItemDetail().getName().equals("Shipping Cost")
				 * && !StringUtils.containsIgnoreCase(i.getItemDetail().getName(), "freebie"))
				 * {
				 * continue;
				 * }
				 */

				totalPrice += itemTotalPrice;

				content.append("<tr>");
				content.append("<td width='8%' align='center'>");
				content.append((c + 1) + ".&nbsp;");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(i.getItemDetail().getName());
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(quantity > 0 ? quantity : "");
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(isDiscount ? "" : ("&#x20b1; " + NumberUtil.format(price, "##,###,##0.00")));
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append("&#x20b1; " + (isDiscount ? "(" + NumberUtil.format(i.getItemDetail().getDiscount()) + ")" : NumberUtil.format(itemTotalPrice, "##,###,##0.00")));
				content.append("</td>");
				content.append("</tr>");
				c++;
			}
			/* } */
			content.append("<tr><td colspan='5' align='right'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='5' align='right'>Total Price: &#x20b1; " + NumberUtil.format((totalPrice), "##,###,##0.00") + "</td></tr>");
			content.append("</table>");
			if(!type.equalsIgnoreCase("NEW") && !type.equalsIgnoreCase("NEWPAYPAL"))
			{
				content.append("<br><br><strong style='color:blue'>Please print the confirmation voucher attached herewith and present during delivery or pick-up of purchased items.</strong>");
			}
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email. For inquiries, feedback and comments, email us at customerservice@gurkka.com or call us at (02)708-2080.</strong>");

			if(type.equalsIgnoreCase("NEW") || type.equalsIgnoreCase("NEWPAYPAL"))
			{
				if(EmailUtil.sendWithHTMLFormatToMany("noreply@webtogo.com.ph", toComp, emailTitle, content.toString(), null))
				{
					return Action.SUCCESS;
				}

			}
			else
			{
				final String pdf = createVoucherGurkka(type, cartOrder, freebiesSavings);

				if((EmailUtil.sendWithHTMLFormatToMany("noreply@webtogo.com.ph", toMember, emailTitle + " from " + company.getNameEditable(), content.toString(), pdf))
						&& (EmailUtil.sendWithHTMLFormatToMany("noreply@webtogo.com.ph", toComp, emailTitle + " from " + company.getNameEditable(), content.toString(), pdf)))
				{
					return Action.SUCCESS;
				}
				else
				{
				}
			}
		}
		return Action.ERROR;
	}

	@SuppressWarnings("unchecked")
	public String sendMdtEmailShipping(Member member)
	{
		String emailTitle;
		final String to[] = { "", "" };
		to[0] = member.getEmail();
		to[1] = company.getEmail();

		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();

			emailTitle = "Shipping Information";

			content.append("Customer Details:");
			content.append("Name: " + member.getFirstname() + " " + member.getLastname() + "<br/>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("<br/><br/>");
			content.append("Shipping Details:<br/>");

			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("address1", request.getParameter("address1"));
			request.setAttribute("address2", request.getParameter("address2"));
			request.setAttribute("city", request.getParameter("city"));
			request.setAttribute("zipcode", request.getParameter("zipcode"));
			request.setAttribute("mobilenumber", request.getParameter("mobilenumber"));
			request.setAttribute("phonenumber", request.getParameter("phonenumber"));
			request.setAttribute("email", request.getParameter("email"));

			content.append("Recipient Name: " + request.getParameter("name") + "<br/>");
			content.append("Address 1: " + request.getParameter("address1") + "<br/>");
			content.append("Address 2: " + request.getParameter("address2") + "<br/>");
			content.append("Area: " + request.getParameter("province") + "<br/>");
			content.append("Location: " + request.getParameter("city") + "<br/>");
			content.append("Zip Code: " + request.getParameter("zipcode") + "<br/>");
			content.append("Mobile Number: " + request.getParameter("mobilenumber") + "<br/>");
			content.append("Phone Number: " + request.getParameter("phonenumber") + "<br/>");
			content.append("Email: " + request.getParameter("email") + "<br/><br/>");

			content.append("<table border='0'>");
			content.append("<tr>");
			content.append("<td align='center'>#</td>");
			content.append("<td align='center'>Name</td>");
			content.append("<td align='center'>Quantity</td>");
			content.append("<td align='center'>Price</td>");
			content.append("<td align='center'>Subtotal</td>");
			content.append("</tr>");
			itemTotalPrice = 0;
			totalPrice = 0;

			for(int i = 0; i < cartItemList.size(); i++)
			{
				itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity();

				if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
				{
					itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice();
				}

				totalPrice = itemTotalPrice + totalPrice;

				content.append("<tr>");
				content.append("<td width='8%' align='center'>");
				content.append((i + 1) + ".&nbsp;");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getItemDetail().getName());
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getQuantity());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(cartItemList.get(i).getItemDetail().getPrice());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(itemTotalPrice);
				content.append("</td>");
				content.append("</tr>");
			}

			content.append("<tr><td colspan='5' align='right'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='5' align='right'>Total Price: " + totalPrice + "</td></tr>");
			content.append("</table>");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	@SuppressWarnings("unchecked")
	public String sendOnlinedepotEmailShipping(Member member)
	{
		String emailTitle;
		final String to[] = { "", "" };
		to[0] = member.getEmail();
		to[1] = company.getEmail();

		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();

			emailTitle = "Shipping Information";

			content.append("Customer Details:");
			content.append("Name: " + member.getFirstname() + " " + member.getLastname() + "<br/>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("<br/><br/>");
			content.append("Shipping Details:<br/>");

			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("address1", request.getParameter("address1"));
			request.setAttribute("address2", request.getParameter("address2"));
			request.setAttribute("city", request.getParameter("city"));
			request.setAttribute("zipcode", request.getParameter("zipcode"));
			request.setAttribute("mobilenumber", request.getParameter("mobilenumber"));
			request.setAttribute("phonenumber", request.getParameter("phonenumber"));
			request.setAttribute("email", request.getParameter("email"));

			content.append("Recipient Name: " + request.getParameter("name") + "<br/>");
			content.append("Address 1: " + request.getParameter("address1") + "<br/>");
			content.append("Address 2: " + request.getParameter("address2") + "<br/>");
			content.append("Area: " + request.getParameter("province") + "<br/>");
			content.append("Location: " + request.getParameter("city") + "<br/>");
			content.append("Zip Code: " + request.getParameter("zipcode") + "<br/>");
			content.append("Mobile Number: " + request.getParameter("mobilenumber") + "<br/>");
			content.append("Phone Number: " + request.getParameter("phonenumber") + "<br/>");
			content.append("Email: " + request.getParameter("email") + "<br/><br/>");

			content.append("<table border='0'>");
			content.append("<tr>");
			content.append("<td align='center'>#</td>");
			content.append("<td align='center'>Name</td>");
			content.append("<td align='center'>Quantity</td>");
			content.append("<td align='center'>Price</td>");
			content.append("<td align='center'>Subtotal</td>");
			content.append("</tr>");
			itemTotalPrice = 0;
			totalPrice = 0;

			for(int i = 0; i < cartItemList.size(); i++)
			{
				itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity();

				if(cartItemList.get(i).getItemDetail().getName().equals("Shipping Cost"))
				{
					itemTotalPrice = cartItemList.get(i).getItemDetail().getPrice();
				}

				totalPrice = itemTotalPrice + totalPrice;

				content.append("<tr>");
				content.append("<td width='8%' align='center'>");
				content.append((i + 1) + ".&nbsp;");
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getItemDetail().getName());
				content.append("</td>");
				content.append("<td width='18%' align='center'>");
				content.append(cartItemList.get(i).getQuantity());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(cartItemList.get(i).getItemDetail().getPrice());
				content.append("</td>");
				content.append("<td width='18%' align='right'>");
				content.append(itemTotalPrice);
				content.append("</td>");
				content.append("</tr>");
			}

			content.append("<tr><td colspan='5' align='right'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='5' align='right'>Total Price: " + totalPrice + "</td></tr>");
			content.append("</table>");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	public String sendMrAirconShipping(CartOrder order)
	{
		String emailTitle;
		final String[] toMember = { "" };
		toMember[0] = order.getEmailAddress();

		cartOrderItems = cartOrderItemDelegate.findAll(order);

		if(company == null)
		{
			company = companyDelegate.find(CompanyConstants.MR_AIRCON);
		}

		final String[] toComp = {"order@mraircon.ph", "jona@ivant.com", "isaac@ivant.com", "teo@ivant.com", "ren@ivant.com"};
		final String[] companyToComp = company.getEmail().split(",");

		
		//final String[] toAll = (String[]) ArrayUtils.addAll(toMember, toComp , companyToComp);
		final String[] toAllSet1 = ArrayUtils.addAll(toComp,companyToComp);
		final String[] toAll = ArrayUtils.addAll(toMember, toAllSet1);

		final StringBuffer content = new StringBuffer();

		emailTitle = "Order Confirmation ";

		content.append("<font face='sans-serif'>Dear ").append(order.getName()).append(",<br/><br/>")
			.append("Thank you for your inquiry! Our customer representative will contact you within")
			.append(" 1 business day to confirm your order and delivery/installation schedule. ")
			.append("Please see the customer information and order details you have submitted below:")
			.append("<br/><br/>");
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String telephoneNo = "";
		String mobileNo = "";
		String companyStr = "";
		String deliveryDate = "";
		String nationality = "";
		String dateOfBirth = "";
				
		try{
			telephoneNo = (order.getPhoneNumber().split("/"))[0];
			mobileNo = (order.getPhoneNumber().split("/"))[1];
			
			String[] infos = order.getPrescription().split("[:;]");
			companyStr = infos[1];
			deliveryDate = infos[3];
			dateOfBirth = infos[5];
			nationality = infos[7];
			
			if(companyStr == null || (companyStr != null && companyStr.trim() == "null")){
				companyStr = "";
			}
			
			if(deliveryDate == null || (deliveryDate != null && deliveryDate.trim() == "null")){
				deliveryDate = "";
			}
			
			if(dateOfBirth == null || (dateOfBirth != null && dateOfBirth.trim() == "null")){
				dateOfBirth = "";
			}
			
			if(nationality == null || (nationality != null && nationality.trim() == "null")){
				nationality = "";
			}
		}catch(Exception e){
			
		}
		
		content.append("<table width='100%' style='border:2px solid black' cellspacing='0' cellpadding='0'><tr style='background-color:#374E92; color:#ffffff; height:35px;'><th colspan='6' style='border-bottom:2px solid black; font-size: 120%'><strong>ORDER REFERENCE NO. &nbsp;&nbsp;")
			.append(order.getId())
			.append("</strong></th></tr>")
			.append("<tr>")
				.append("<td width='15%' style='padding-top: 10px; padding-left: 10px'>").append("Customer Name ").append("</td>")
				.append("<td width='5%' style='padding-top: 10px;'>").append(":").append("</td>")
				.append("<td width='30%' style='padding-top: 10px;'>").append(order.getName()).append("</td>")
				.append("<td width='15%' style='padding-top: 10px;'>").append("E-mail Address ").append("</td>")
				.append("<td width='5%' style='padding-top: 10px;'>").append(":").append("</td>")
				.append("<td width='30%' style='padding-top: 10px;'>").append(order.getEmailAddress()).append("</td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='padding-left: 10px'>").append("Telephone No. ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(telephoneNo).append("</td>")
				.append("<td>").append("Mobile No. ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(mobileNo).append("</td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='padding-left: 10px'>").append("Date of Birth ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(dateOfBirth).append("</td>")
				.append("<td>").append("Nationality ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(nationality).append("</td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='padding-left: 10px'>").append("Company ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(companyStr).append("</td>")
				.append("<td>").append("</td>")
				.append("<td>").append("</td>")
				.append("<td>").append("</td>")
			.append("</tr>")
			.append("<tr>").append("<td colspan='6'></td>").append("</tr>")
			.append("<tr>")
				.append("<td style='padding-top: 20px; padding-left: 10px'>").append("Billing Address ").append("</td>")
				.append("<td style='padding-top: 20px; '>").append(":").append("</td>")
				.append("<td style='padding-top: 20px; '>").append(order.getAddress1()).append("</td>")
				.append("<td style='padding-top: 20px; '>").append("Delivery Address").append("</td>")
				.append("<td style='padding-top: 20px; '>").append(":").append("</td>")
				.append("<td style='padding-top: 20px; '>").append(order.getAddress2()).append("</td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='padding-top: 20px; padding-left: 10px'>").append("Preferred Delivery Date ").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append(":").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append(deliveryDate).append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append("Additional Delivery Instructions").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append(":").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append(order.getComments()).append("</td>")
			.append("</tr>").append("</table>");
		
		content.append("<br/><br/>");
		
		String paymentMethod = paymentType.equals(PaymentType.COD)?"CASH or CHEQUE": paymentType.getValue();
		
		content.append("<strong style='font-size: 120%'>PAYMENT METHOD : ").append(paymentMethod.toUpperCase()).append("</strong>");

		/*content.append("<strong style='font-size: 120%'>PAYMENT METHOD : ").append(cartOrder.getPaymentType().getValue().toUpperCase()).append("</strong>");*/
		
		content.append("<br/><br/>");
		
		itemTotalPrice = 0;
		totalPrice = 0;

		int counter = 0;

		final DecimalFormat dc = new DecimalFormat("0.##");

		content.append("<table width='100%' style='border:2px solid black' cellspacing='0' cellpadding='0'><tr style='background-color:#374E92; color:#ffffff; height:35px; font-size: 120%'>")
			.append("<th style='border-bottom:2px solid black'><strong>ITEM DETAILS</strong></th>")
			.append("<th colspan='2' style='border-bottom:2px solid black' align='left'><strong>UNIT PRICE</strong></th>")
			.append("<th style='border-bottom:2px solid black' algin='right'><strong>QTY</strong></th>")
			.append("<th colspan='2' style='border-bottom:2px solid black'><strong>TOTAL</strong></th>")
			.append("</tr>");
	
		DecimalFormat fmt = new DecimalFormat("#,##0.00");
		
		for(final CartOrderItem cartOrderItem : cartOrderItems)
		{
			Integer quantity = cartOrderItem.getQuantity();
			if(quantity == null)
			{
				quantity = 1;
			}
			itemTotalPrice = cartOrderItem.getItemDetail().getPrice() * quantity;

			if(cartOrderItem.getItemDetail().getName().equals("Shipping Cost"))
			{
				itemTotalPrice = cartOrderItem.getItemDetail().getPrice();
			}

			totalPrice += itemTotalPrice;

			CategoryItem categoryItem = categoryItemDelegate.find(cartOrderItem.getItemDetail().getRealID());
			
			String desc1 = "";
			String desc2 = "";
			String outdoorModelNo = "";
			String indoorModelNo = "";
			
			try{
				desc1 = categoryItem.getCategoryItemOtherFieldMap().get("Description Line 1").getContent();
				desc2 = categoryItem.getCategoryItemOtherFieldMap().get("Description Line 2").getContent();
				indoorModelNo = categoryItem.getCategoryItemOtherFieldMap().get("Indoor Model No.").getContent();
				outdoorModelNo = "(" + categoryItem.getCategoryItemOtherFieldMap().get("Outdoor Model No.").getContent() + ")";
				
			}catch(Exception e){
				System.err.println(e.getMessage());
			}
			
			if(cartOrderItem.getItemDetail().getName().equalsIgnoreCase("Installation Price(Basic Installation)")){
				
					content.append("<tr>")
						.append("<td style='padding: 5px 10px'><strong>BASIC INSTALLATION CHARGE</strong></td>")
						.append("<td colspan='4'></td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td style='border-bottom:2px solid black; padding: 10px 13px'>Basic Installation Charge only covers 10 ft. of Piping <br/>Length.")
						.append("The distance between the Indoor Unit and <br/>the Outdoor Unit ")
						.append("MUST NOT EXCEED 10 FEET.<br/>Additional charge may be")
						.append("applied per foot in <br/>excess of 10 ft.</td>")
						.append("<td colspan='3' style='border-bottom:2px solid black'></td>")
						.append("<td style='border-bottom:2px solid black' align='right'>PHP</td>")
						.append("<td style='border-bottom:2px solid black; padding-right: 15px' align='right'>").append(fmt.format(itemTotalPrice)).append("</td>")
					.append("</tr>");
					
			}else if(cartOrderItem.getItemDetail().getName().equalsIgnoreCase("Installation Price(No Installation)")){	
					content.append("<tr>")
						.append("<td style='padding: 5px 10px'><strong>NO INSTALLATION</strong></td>")
						.append("<td colspan='4'></td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td style='border-bottom:2px solid black; padding: 10px 13px'>Warranty is void if installed by an unaccredited installer.")
						.append("</td>")
						.append("<td colspan='3' style='border-bottom:2px solid black'></td>")
						.append("<td style='border-bottom:2px solid black' align='right'>PHP</td>")
						.append("<td style='border-bottom:2px solid black; padding-right: 15px' align='right'>").append(fmt.format(itemTotalPrice)).append("</td>")
				.append("</tr>");
				
			}else if(cartOrderItem.getItemDetail().getName().equals("Shipping Cost()")){
				//Do nothing
				
			}else{
				content.append("<tr>")
					.append("<td style='padding: 5px 10px; color: #516EC7'>").append(categoryItem.getName()).append("</td>")
					.append("<td colspan='5'></td>")
				.append("</tr>")
				.append("<tr>")
					.append("<td width='50%' style='padding: 0 10px;'>").append(desc1).append("</td>")
					.append("<td width='8%'>PHP</td>")
					.append("<td width='10%' align='right' style='padding-right: 20px'>").append(fmt.format((cartOrderItem.getItemDetail().getPrice()))).append("</td>")
					.append("<td width='10%' align='center'>").append(quantity).append("</td>")
					.append("<td width='8%' align='right'>PHP</td>")
					.append("<td width='10%' align='right' style='padding-right: 15px'>").append(fmt.format(itemTotalPrice)).append("</td>")
				.append("</tr>")
				.append("<tr>")
					.append("<td style='padding: 0 10px;'>").append(desc2).append("</td>")
					.append("<td colspan='5'></td>")
				.append("</tr>")
				.append("<tr>")
					.append("<td style='border-bottom:2px solid black; padding-bottom: 10px;padding-left: 10px;'>").append(indoorModelNo)
					.append(outdoorModelNo)
					.append("</td>")
					.append("<td colspan='5' style='border-bottom:2px solid black'></td>")
				.append("</tr>");
			}
						
		}
		
		content.append("<tr>")
			.append("<td style='padding-top: 5px; padding-left: 10px'>&#x2713; Free Delivery Within Metro Manila</td>")
			.append("<td colspan='3' style='color: #516EC7; font-size: 150%; padding-top: 5px;'><strong>GRAND TOTAL</strong></td>")
			.append("<td style='color: #516EC7; font-size: 150%; padding-top: 5px;'><strong>PHP</strong></td>")
			.append("<td style='color: #516EC7; font-size: 150%; padding-top: 5px; padding-right: 15px' align='right'><strong>").append(fmt.format(totalPrice)).append("</strong></td>")
		.append("</tr>")
		.append("<tr>")
			.append("<td style='padding-top: 5px; padding-bottom: 5px; padding-left: 10px'>&#x2713; For Deliveries Outside Metro Manila,<br>&nbsp;&nbsp;&nbsp;&nbsp;SHIPPING FEE IS NOT YET INCLUDED")
			.append("</td>")
		.append("</tr>")
		.append("</table>").append("<br>");
		
		content.append("<p align='justify'><strong>For Cash Deposits. </strong>")
			.append("Please deposit the grand total amount to BDO ACCOUNT # 2030114533. ")
			.append("Once the deposit has been made, please email us a copy of the deposit slip at order@mraircon.ph with the subject DEPOSIT SLIP - ")
			.append(order.getId())
			.append("<br/><br/>");
			
		content.append("<strong>For Cheque Payments. </strong>")
			.append("Please make cheques payable to HAKSAN INTERNATIONAL PHILIPPINES INC. ")
			.append("and deposit to BDO ACCOUNT #2030114533. Please allow 3 business days to allow for cheque clearing.")
			.append("<br/><br/>");
		
		content.append("<strong>Delivery Details. </strong>")
			.append("Please allow us 3 to 7 days to process and deliver your order. ")
			.append("We will contact you regarding the exact date of the delivery and installation. ")
			.append("Please present this voucher and a valid government issued ID upon delivery. ")
			.append("If the customer is not available at the time of the delivery, the customer must provide us with ")
			.append("a legally binding letter authorizing a representative to act on the customer's ")
			.append("behalf along with a copy of a valid government issued ID of both the customer and the representative.")
			.append("Please visit www.mraircon.ph/FAQ to get a sample template of the authorization letter.")
			.append("<br/><br/>");
		
		content.append("<strong>Terms and Conditions. </strong>")
			.append("For our complete policies regarding ordering, payment, delivery, installation, returns & exchanges, and ")
			.append("cancellation, please visit www.mraircon.ph/FAQ")
			.append("<br/><br/>");
		
		content.append("For any other concerns, please feel free to reach us at (+632) 477-1111. 477-8906, or 477-8910. ")
			.append("You may also email us at inquire@mraircon.ph.<br/><br/></p>");
		
		content.append("Thank you.").append("<br><br>").append("Sincerely,<br/><br/>").append("Mr. Aircon Philippines");

		EmailUtil.connectViaCompanySettings(company);
		//EmailUtil.connect("smtp.gmail.com", 587);
		
		if(EmailUtil.sendWithHTMLFormat(company.getCompanySettings().getEmailUserName(), toAll, emailTitle + " from " + company.getNameEditable(), content.toString(), null))
		{
			return Action.SUCCESS;
		}

		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String sendIIEEPaymentEmail(CartOrder order)
	{
		String emailTitle;
		final String[] toMember = { "" };
		toMember[0] = order.getEmailAddress();

		cartOrderItems = cartOrderItemDelegate.findAll(order);

		if(company == null)
		{
			company = companyDelegate.find(Long.valueOf(CompanyConstants.IIEE));
		}

		final String[] toComp = company.getEmail().split(",");

		final String[] toAll = (String[]) ArrayUtils.addAll(toMember, toComp);

		EmailUtil.connect("smtp.gmail.com", 587);

		final StringBuffer content = new StringBuffer();

		emailTitle = "Payment Information ";

		content.append("<strong>Customer Details:</strong><br/>");
		content.append("Name: " + order.getName() + "<br/>");
		content.append("Email: " + order.getEmailAddress() + "<br/>");
		content.append("<br/><br/>");
		content.append("Payment Type: " + cartOrder.getPaymentType().getValue() + "<br/>");
		content.append("Address 1: " + order.getAddress1() + "<br/>");
		content.append("Address 2: " + order.getAddress2() + "<br/>");
		content.append("City: " + order.getCity() + "<br/>");
		content.append("ZipCode: " + order.getZipCode() + "<br/>");
		content.append("Phone Number: " + order.getPhoneNumber() + "<br/><br/>");

		content.append("<table border='0'>");
		content.append("<tr>");
		content.append("<td align='center'>#</td>");
		content.append("<td align='center'>Name</td>");
		content.append("<td align='center'>Description</td>");
		content.append("<td align='center'>Price</td>");
		content.append("</tr>");
		itemTotalPrice = 0;
		totalPrice = 0;

		int counter = 0;

		final DecimalFormat dc = new DecimalFormat("0.##");

		for(final CartOrderItem cartOrderItem : cartOrderItems)
		{
			Integer quantity = cartOrderItem.getQuantity();
			if(quantity == null)
			{
				quantity = 1;
			}
			itemTotalPrice = cartOrderItem.getItemDetail().getPrice() * quantity;

			if(cartOrderItem.getItemDetail().getName().equals("Shipping Cost"))
			{
				itemTotalPrice = cartOrderItem.getItemDetail().getPrice();
			}

			totalPrice = itemTotalPrice + totalPrice;

			content.append("<tr>");
			content.append("<td width='8%' align='center'>");
			content.append((counter + 1) + ".&nbsp;");
			content.append("</td>");
			content.append("<td width='18%' align='center'>");
			content.append(cartOrderItem.getItemDetail().getName());
			content.append("</td>");
			content.append("<td width='18%' align='center'>");
			content.append(cartOrderItem.getItemDetail().getDescription());
			content.append("</td>");
			content.append("<td width='18%' align='right'>");
			content.append(dc.format(cartOrderItem.getItemDetail().getPrice()));
			content.append("</td>");
			content.append("</tr>");
			counter++;
		}

		content.append("<tr><td colspan='5' align='right'>&nbsp;</td></tr>");

		content.append("<tr><td colspan='5' align='right'>Total Price: " + "Php " + dc.format(totalPrice) + "</td></tr>");
		content.append("</table>");
		content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

		if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", toAll, emailTitle + " from " + company.getNameEditable(), content.toString(), null))
		{
			return Action.SUCCESS;
		}

		return Action.SUCCESS;
	}

	public String result() throws Exception
	{
		confirmation();
		confirm();

		return SUCCESS;
	}

	public String confirmation() throws Exception
	{
		paypal.getExpressCheckoutDetail(pToken);
		return SUCCESS;
	}

	public String confirm() throws Exception
	{
		if(request.getParameter("paymentvalue") == null)
		{
			return ERROR;
		}

		request.getParameter("paymentvalue").toString();

		// Map<String, String> result = paypal.doExpressCheckoutPayment("id", pToken, totalPrice);
		return SUCCESS;
	}

	private void createOrder()
	{
		member = shoppingCart.getMember();
		cartOrder = new CartOrder();
		cartOrder.setMember(member);
		cartOrder.setStatus(OrderStatus.NEW);
		cartOrder.setPaymentStatus(PaymentStatus.PENDING);
		cartOrder.setShippingStatus(ShippingStatus.PENDING);
		cartOrder.setCompany(company);
		if(paymentType != null)
		{
			cartOrder.setPaymentType(paymentType);
		}
		if(shippingType != null)
		{
			cartOrder.setShippingType(shippingType);
		}

		if(company.getName().equals("ecommerce"))
		{
			cartOrder.setPaymentStatus(PaymentStatus.PAID);

			if(sessionMap.get("shippingvalue") != null)
			{
				cartOrder.setTotalShippingPrice2(Double.parseDouble(sessionMap.get("shippingvalue").toString()));
			}
		}

		// yes
		if(company.getName().equals("giftcard"))
		{
			if(request.getParameter("shippingvalue") != null && !request.getParameter("shippingvalue").equals("") && request.getParameter("shippingtype").equals("delivery"))
			{
				shippingType = ShippingType.DELIVERY;
				cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingvalue")));
			}
			else if(session.get("shippingvalue") != null && !session.get("shippingvalue").equals("") && session.get("shippingtype").equals("delivery"))
			{
				shippingType = ShippingType.DELIVERY;
				cartOrder.setTotalShippingPrice2(Double.parseDouble(session.get("shippingvalue").toString()));
			}
			else
			{
				cartOrder.setTotalShippingPrice2(0.00);
			}
		}

		if(company.getName().equals("drugasia") || company.getName().equals("onlinedepot") || company.getName().equals("gurkka") || company.getId() == CompanyConstants.GURKKA_TEST)
		{
			cartOrder.setPaymentStatus(PaymentStatus.PENDING);
			if(sessionMap.get("shippingvalue") != null)
			{
				cartOrder.setTotalShippingPrice2(Double.parseDouble(sessionMap.get("shippingvalue").toString()));
			}
			if(session != null && session.get("filename") != null)
			{
				cartOrder.setPrescription(session.get("filename").toString());
			}
			else if(sessionMap != null && sessionMap.get("filename") != null)
			{
				cartOrder.setPrescription(sessionMap.get("filename").toString());
			}

			if(!StringUtils.isEmpty(request.getParameter("shippingCostAmount")))
			{
				cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));

			}
		}

		if(transactionNumber != 0)
		{
			cartOrder.setTransactionNumber(Integer.toString(transactionNumber));
		}
		//
		if(request.getParameter("comments") != null)
		{
			cartOrder.setComments(request.getParameter("comments").toString());
		}
		else
		{
			if(sessionMap.get("comments") != null)
			{
				cartOrder.setComments(sessionMap.get("comments").toString());
			}
		}

		// set shipping info
		cartOrder.setShippingInfo(memberShippingInfoDelegate.find(company, member));

		// for storage of shipping address for each order
		final MemberShippingInfo tempInfo = memberShippingInfoDelegate.find(company, member);
		if(tempInfo != null)
		{
			final ShippingInfo orderShippingInfo = tempInfo.getShippingInfo();

			cartOrder.setTotalShippingPrice2(shoppingCart.getTotalShippingPrice());
			cartOrder.setAddress1(orderShippingInfo.getAddress1());
			cartOrder.setAddress2(orderShippingInfo.getAddress2());
			cartOrder.setCity(orderShippingInfo.getCity());
			cartOrder.setCountry(orderShippingInfo.getCountry());
			cartOrder.setEmailAddress(orderShippingInfo.getEmailAddress());
			cartOrder.setName(orderShippingInfo.getName());
			cartOrder.setPhoneNumber(orderShippingInfo.getPhoneNumber());
			cartOrder.setState(orderShippingInfo.getState());
			cartOrder.setZipCode(orderShippingInfo.getZipCode());
		}

		if(company.getName().equals("ecommerce"))
		{
			if(sessionMap.get("deliverydate") != null)
			{
				cartOrder.setComments(sessionMap.get("deliverydate").toString());
			}

			if(sessionMap.get("name") != null)
			{
				cartOrder.setName(sessionMap.get("name").toString());
			}

			if(sessionMap.get("email") != null)
			{
				cartOrder.setEmailAddress(sessionMap.get("email").toString());
			}

			if(sessionMap.get("address1") != null)
			{
				cartOrder.setAddress1(sessionMap.get("address1").toString());
			}

			if(sessionMap.get("address2") != null)
			{
				cartOrder.setAddress2(sessionMap.get("address2").toString());
			}

			if(sessionMap.get("city") != null)
			{
				cartOrder.setCity(sessionMap.get("city").toString());
			}

			if(sessionMap.get("name") != null)
			{
				cartOrder.setCountry("Philippines");
			}

			if(sessionMap.get("zipcode") != null)
			{
				cartOrder.setZipCode(sessionMap.get("zipcode").toString());
			}

			if(sessionMap.get("phonenumber") != null)
			{
				cartOrder.setPhoneNumber(sessionMap.get("phonenumber").toString());
			}
		}

		// yes
		if(company.getName().equals("giftcard"))
		{
			if(sessionMap.get("paymenttype").toString().equals("paypal"))
			{
				cartOrder.setPaymentStatus(PaymentStatus.PAID);
				cartOrder.setPaymentType(PaymentType.PAYPAL);
			}
			else
			{
				cartOrder.setPaymentType(PaymentType.BANK);
			}

			if(sessionMap.get("shippingtype").toString().equals("delivery"))
			{
				cartOrder.setShippingType(ShippingType.DELIVERY);
				cartOrder.setName(sessionMap.get("name").toString());
				cartOrder.setEmailAddress(sessionMap.get("email").toString());
				cartOrder.setAddress1(sessionMap.get("address1").toString());
				cartOrder.setAddress2(sessionMap.get("address2").toString());
				cartOrder.setCity(sessionMap.get("city").toString());
				cartOrder.setCountry("Philippines");
				cartOrder.setZipCode(sessionMap.get("zipcode").toString());
				cartOrder.setPhoneNumber(sessionMap.get("contactnumber").toString());
			}
			else
			{
				cartOrder.setShippingType(ShippingType.PICKUP);
				cartOrder.setName(member.getFullName());
				cartOrder.setEmailAddress(member.getEmail());
				cartOrder.setAddress1(member.getAddress1());
				cartOrder.setAddress2(member.getAddress2());
				cartOrder.setCity(member.getCity());
				cartOrder.setCountry("Philippines");
				cartOrder.setZipCode(member.getZipcode());
				cartOrder.setPhoneNumber(member.getMobile());
			}
		}

		if(company.getName().equals("drugasia") || company.getName().equals("onlinedepot") || company.getName().equals("gurkka") || company.getId() == CompanyConstants.GURKKA_TEST)
		{
			if(sessionMap.get("name") != null)
			{
				cartOrder.setName(sessionMap.get("name").toString());
			}

			if(sessionMap.get("email") != null)
			{
				cartOrder.setEmailAddress(sessionMap.get("email").toString());
			}

			if(sessionMap.get("address1") != null)
			{
				cartOrder.setAddress1(sessionMap.get("address1").toString());
			}

			if(sessionMap.get("city") != null)
			{
				cartOrder.setCity(sessionMap.get("city").toString());
			}

			if(sessionMap.get("name") != null)
			{
				cartOrder.setCountry("Philippines");
			}

			if(sessionMap.get("phonenumber") != null)
			{
				cartOrder.setPhoneNumber(sessionMap.get("phonenumber").toString());
			}

			if(sessionMap.get("zipcode") != null)
			{
				cartOrder.setZipCode(sessionMap.get("zipcode").toString());
			}

			if(company.getId() != CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
			{
				if(sessionMap.get("address2") != null)
				{
					cartOrder.setAddress2(sessionMap.get("address2").toString());
				}
			}
		}

		cartOrder = cartOrderDelegate.find(cartOrderDelegate.insert(cartOrder));
	}

	private void createOrderBasic()
	{
		member = getShoppingCart().getMember();
		cartOrder = new CartOrder();
		cartOrder.setMember(member);
		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{
			cartOrder.setStatus(OrderStatus.PENDING);
		}
		else
		{
			cartOrder.setStatus(OrderStatus.NEW);
		}
		cartOrder.setPaymentStatus(PaymentStatus.PENDING);
		cartOrder.setShippingStatus(ShippingStatus.PENDING);
		cartOrder.setCompany(company);
		cartOrder.setName(member.getFullName());
		cartOrder.setEmailAddress(member.getEmail());
		if(company.getId() != CompanyConstants.GURKKA || company.getId() != CompanyConstants.GURKKA_TEST)
		{
			cartOrder.setAddress1(member.getCompany().getName() + " - " + member.getCompany().getAddress());
		}
		if(paymentType != null)
		{
			cartOrder.setPaymentType(paymentType); 
		}
		if(shippingType != null)
		{
			cartOrder.setShippingType(shippingType); 
		}

		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{
			if(!StringUtils.isEmpty(request.getParameter("shippingCostAmount")))
			{
				cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));
			}
			if(!StringUtils.isEmpty(member.getReg_companyName()))
			{
				cartOrder.setName(member.getReg_companyName());
			}
			else
			{
				cartOrder.setName(member.getFullName());
			}

			cartOrder.setAddress2(StringUtils.trimToNull(request.getParameter("receiver")));
			cartOrder.setAddress1(StringUtils.trimToNull(request.getParameter("address2")));
			cartOrder.setPhoneNumber(StringUtils.trimToNull(request.getParameter("mobilenumber2")));
			cartOrder.setState(StringUtils.trimToNull(request.getParameter("province2")));
			cartOrder.setCity(StringUtils.trimToNull(request.getParameter("city2")));
		}

		cartOrder = cartOrderDelegate.find(cartOrderDelegate.insert(cartOrder));
	}
	
	private CartOrder setSwapShippingDetails(CartOrder cartOrder, String usTerritory, String name, String business, 
			String floor, String poBox, String city, String state, String zipCode, Boolean shipToBillingAddress) {
		cartOrder.setCountry(usTerritory);
		cartOrder.setName(name);
		cartOrder.setBusiness(business);
		cartOrder.setAddress1(floor);
		cartOrder.setAddress2(poBox);
		cartOrder.setCity(city);
		cartOrder.setState(state);
		cartOrder.setZipCode(zipCode);
		cartOrder.setShipToBillingAddress(shipToBillingAddress);
		
		return cartOrder;
	}

	// add shopping cart items to new order lists
	private void initOrderItemList()
	{
		try
		{
			itemFile = new ItemFile();
			final List<CartOrderItem> items = new ArrayList<CartOrderItem>();
			final List<OrderItemFile> orderItems = new ArrayList<OrderItemFile>();
			final List<ShoppingCartItem> shoppingCartItems = shoppingCartItemDelegate.findAll(shoppingCart).getList();
			for(final ShoppingCartItem currentCartItem : shoppingCartItems)
			{
				/*
				 * if((currentCartItem.getQuantity() == null || currentCartItem.getQuantity() <= 0)
				 * && (company.getId() == CompanyConstants.GURKKA)
				 * && (!StringUtils.containsIgnoreCase(currentCartItem.getItemDetail().getName(), "freebie"))
				 * && (!StringUtils.containsIgnoreCase(currentCartItem.getItemDetail().getName(), "shipping")))
				 * continue;
				 */

				// convert cart item into cart order item
				cartOrderItem = new CartOrderItem();
				cartOrderItem.setOrder(cartOrder);
				cartOrderItem.setCompany(company);
				cartOrderItem.setCreatedOn(new Date());
				cartOrderItem.setIsValid(true);
				cartOrderItem.setItemDetail(currentCartItem.getItemDetail());
				cartOrderItem.setQuantity(currentCartItem.getQuantity());
				cartOrderItem.setStatus("OK");

				// update current cart item set it to invalid
				currentCartItem.setIsValid(false);
				currentCartItem.setUpdatedOn(new Date());
				shoppingCartItemDelegate.update(currentCartItem);
				cartOrderItem = cartOrderItemDelegate.find(cartOrderItemDelegate.insert(cartOrderItem));

				if(company.getId() == 247)
				{
					final CategoryItem catItem = categoryItemDelegate.find(currentCartItem.getItemDetail().getRealID());
					final Integer availableQuantity = catItem.getAvailableQuantity() - currentCartItem.getQuantity();
					catItem.setAvailableQuantity(availableQuantity);
					categoryItemDelegate.update(catItem);
				}

				if(company.getId() == 136)
				{
					// ------------copy item_file data to order_item_file database--------------
					itemFile = itemFileDelegate.find(itemFileDelegate.findFileID(company, cartOrderItem.getItemDetail().getRealID()).getId());
					orderItemFile = new OrderItemFile();
					orderItemFile.setCompany(company);
					orderItemFile.setIsValid(true);
					orderItemFile.setFileName(itemFile.getFileName());
					orderItemFile.setFilePath(itemFile.getFilePath());
					orderItemFile.setFileSize(itemFile.getFileSize());
					orderItemFile.setFileType(itemFile.getFileType());
					orderItemFile.setItemFileID(itemFile.getId());
					orderItemFile.setItem(itemFile.getItem());
					orderItemFile.setCreatedOn(new Date());
					orderItemFile.setCartOrderItemID(cartOrderItem.getId());
					if(company.getCompanySettings().getWillExpire())
					{
						orderItemFile.setExpiryDate(new DateTime(orderItemFile.getCreatedOn()).plusDays(company.getCompanySettings().getExpiryDate()).toDate());
					}
					if(company.getCompanySettings().getLimitDownloads())
					{
						cartOrderItem.setDownloads(company.getCompanySettings().getDownloads());
					}
					;
					orderItemFileDelegate.insert(orderItemFile);
					orderItems.add(orderItemFile);
				}
				items.add(cartOrderItem);
			}
			cartOrder.setItems(items);
			cartOrderDelegate.update(cartOrder);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
	}

	public String saveToOrder(List<CategoryItem> catItems) throws Exception
	{

		if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
			logger.info("Save Order");
		}
		try {
			
		final CartOrder cartOrder = cartOrderDelegate.find(Long.parseLong(request.getParameter("orderId")));
		if(cartOrder != null)
		{
			if(session != null && session.get("filename") != null)
			{
				cartOrder.setPrescription(session.get("filename").toString());
			}
			else if(sessionMap != null && sessionMap.get("filename") != null)
			{
				cartOrder.setPrescription(sessionMap.get("filename").toString());
			}

			cartOrder.setIsValid(true);
			cartOrder.setStatus(OrderStatus.COMPLETED);

			if(company.getName().equalsIgnoreCase("drugasia") || company.getName().equalsIgnoreCase("gurkka") || company.getId() == CompanyConstants.GURKKA_TEST)
			{
				cartOrder.setStatus(OrderStatus.NEW);
				cartOrder.setPaymentStatus(PaymentStatus.PENDING);
			}
			cartOrderDelegate.update(cartOrder);
			
			if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
				logger.info("Cart Order has been updated");
			}

			try
			{
				final EmailSenderAction emailSender = new EmailSenderAction();
				company.setEmail(company.getEmail());
				emailSender.setEmail(cartOrder.getEmailAddress());
				emailSender.setSubject("The " + company.getNameEditable() + " Order Form Submission Pay via Paypal. Order ID NO: " + cartOrder.getId());
				emailSender.setCompany(company);
				String message = "";
				message += "";

				Double totalAmount = 0.00;
				String shippingCost = "";
				String discount = "";
				
				if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
					logger.info("Company Email : " + company.getEmail());
				}
				
				for(final CategoryItem cI : catItems)
				{
					if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
						logger.info("Category Item : " + cI.getName());
					}
					if(cI.getName().indexOf("Discount") != -1)
					{
						if(Math.abs(cI.getPrice()) > 0.0)
						{
							discount += "<TR>";
							discount += "<td align=\"left\">Less 10% Discount </td>";
							if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2"))){
								discount += "<td align=\"center\"></td>";					
							}
							discount += "<td align=\"center\"></td>";
							discount += "<td align=\"right\"></td>";
							discount += "<td align=\"right\"><strong>" + formatter.format(Math.abs(cI.getPrice())) + "</strong></td>";
							discount += "</TR>	";
							
							totalAmount += cI.getPrice();
						}
					}
					else if(cI.getName().indexOf("Shipping Cost") != -1)
					{
						totalAmount += cI.getPrice() * Double.parseDouble(cI.getOtherDetails());
						shippingCost += "<TR>";
						shippingCost += "<td align=\"left\">Shipping Price </td>";
						if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2"))){
							shippingCost += "<td align=\"center\"></td>";					
						}
						shippingCost += "<td align=\"center\"></td>";
						shippingCost += "<td align=\"right\"></td>";
						shippingCost += "<td align=\"right\"><strong>" + formatter.format((cI.getPrice())) + "</strong></td>";
						shippingCost += "</TR>	";
					}
					else
					{
						// message+=cI.getName()+"("+cI.getDescription()+")\t\t"+cI.getOtherDetails()+"\t\t"+formatter.format(cI.getPrice())+"\t\t"+formatter.format((cI.getPrice()*Double.parseDouble(cI.getOtherDetails())))+"\n";

						message += "<TR>";
						// message+="<td align=\"left\">"+cI.getName()+"("+cI.getDescription()+")"+"</td>";
						
						if(company.getName().equals("adeventsmanila")) {
							message += "<td align=\"left\">" + cI.getItemDetailMap().get("name")+ "</td>";
							message += "<td align=\"center\">" + cI.getName() + "</td>";
							message += "<td align=\"center\">" + cI.getItemDetailMap().get("shirtSize")+ "</td>";
							message += "<td align=\"right\"><strong>" + formatter.format(cI.getPrice()) + "</strong></td>";
						}
						else {
							message += "<td align=\"left\">" + cI.getName()+ "</td>";
							if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2"))){							
								message += "<td align=\"left\">"+ (cI.getDescription()!=null?cI.getDescription():"") + "</td>";
							}
							message += "<td align=\"center\">" + cI.getOtherDetails() + "</td>";
							
							if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2"))){
								CategoryItem catItem = categoryItemDelegate.find(cI.getId());
								if((company.getName().equalsIgnoreCase("PURPLETAG")) &&  (catItem.getParentGroup().getId() == 214) 
										&& (!catItem.getParent().getName().contains("KOR AURA") && !catItem.getParent().getName().contains("KOR MESA") && !catItem.getParent().getName().contains("KOR NAVA") && !catItem.getParent().getName().contains("REPLACEMENT PARTS"))){
									message += "<td align=\"right\">Less 10% <del>"+formatter.format(cI.getPrice())+"</del> <strong>"
											+ formatter.format(cI.getPrice() * 0.90)
											+ "</strong></td>";
									message += "<td align=\"right\"><strong>"
										+ formatter.format((cI.getPrice() * Double
												.parseDouble(cI.getOtherDetails()) * 0.90))
										+ "</strong></td>";
									message += "</TR>	";
									
								} else if((company.getName().equalsIgnoreCase("purpletag2")) && (catItem.getCategoryItemOtherFieldMap() != null && catItem.getCategoryItemOtherFieldMap().get("Percent Discount") != null && !catItem.getCategoryItemOtherFieldMap().get("Percent Discount").getContent().equalsIgnoreCase(""))) {
									String percent = "0."+catItem.getCategoryItemOtherFieldMap().get("Percent Discount").getContent();
									Double percentDiscount = new Double(percent);
									message += "<td align=\"right\">Less "+catItem.getCategoryItemOtherFieldMap().get("Percent Discount").getContent()+"% <del>"+formatter.format(cI.getPrice())+"</del> <strong>"
											+ formatter.format(cI.getPrice() - (cI.getPrice() * percentDiscount))
											+ "</strong></td>";
											message += "<td align=\"right\"><strong>"
												+ formatter.format((cI.getPrice() - (cI.getPrice() * percentDiscount)) * Double.parseDouble(cI.getOtherDetails()))
												+ "</strong></td>";
											message += "</TR>	";
								} else if((company.getName().equalsIgnoreCase("PURPLETAG2")) && (catItem.getParentGroup().getId() == 325 || catItem.getParentGroup().getId() == 313)
										&& (!catItem.getName().contains("KOR AURA") && !catItem.getName().contains("KOR MESA") && !catItem.getName().contains("KOR NAVA") && !catItem.getName().contains("KOR SPORT") && !catItem.getParent().getName().contains("REPLACEMENT PARTS"))){
									message += "<td align=\"right\">Less 10% <del>"+formatter.format(cI.getPrice())+"</del> <strong>"
									+ formatter.format(cI.getPrice() * 0.90)
									+ "</strong></td>";
									message += "<td align=\"right\"><strong>"
										+ formatter.format((cI.getPrice() * Double
												.parseDouble(cI.getOtherDetails()) * 0.90))
										+ "</strong></td>";
									message += "</TR>	";
									
								} else {
									message += "<td align=\"right\"><strong>" + formatter.format(cI.getPrice()) + "</strong></td>";
									message += "<td align=\"right\"><strong>" + formatter.format((cI.getPrice() * Double.parseDouble(cI.getOtherDetails()))) + "</strong></td>";
									message += "</TR>	";
									
								}
							} else {
								message += "<td align=\"right\"><strong>" + formatter.format(cI.getPrice()) + "</strong></td>";
								message += "<td align=\"right\"><strong>" + formatter.format((cI.getPrice() * Double.parseDouble(cI.getOtherDetails()))) + "</strong></td>";
								message += "</TR>	";
								
							}
						}
						
						totalAmount += cI.getPrice() * Double.parseDouble(cI.getOtherDetails());
						
					}
				}
				message += shippingCost;
				if(!(company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2")))
						message += discount;

				message += "<TR>";
				message += "<td align=\"left\"  style=\"border-top: 1px solid #dddddd;\"></td>";
				if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2"))){
					message += "<td align=\"center\"  style=\"border-top: 1px solid #dddddd;\"></td>";				
				}
				message += "<td align=\"center\"  style=\"border-top: 1px solid #dddddd;\"></td>";
				message += "<td align=\"right\"  style=\"border-top: 1px solid #dddddd;\"><strong>Total :</strong></td>";
				message += "<td align=\"right\"  style=\"border-top: 1px solid #dddddd; color:#0066CC\"><strong>" + formatter.format(totalAmount) + "</strong></td>";
				message += "</TR>		";

				if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
					logger.info("Total Amount : " + totalAmount);
				}
				
				emailSender.setModeOfPayment("paypal");
				emailSender.setMessage(message);

				String filepath = "";

				// workaround for inconsistency of session//
				if(session != null && session.get("filepath") != null)
				{
					filepath = session.get("filepath").toString();
				}
				else if(sessionMap != null && sessionMap.get("filepath") != null)
				{
					filepath = sessionMap.get("filepath").toString();
				}

				if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
					logger.info("Sending Email Payment Information");
				}
				if("life".equalsIgnoreCase(company.getName())){
					if(member!=null) { 
						if(member.getAddress1() != null)
							cartOrder.setAddress1(member.getAddress1());
						else
							cartOrder.setAddress1("");
					}
					this.cartOrder = cartOrder;
					setNotificationMessage("Thank you for purchasing "+catItems.get(0).getName());
					emailSender.sendEmailPaymentInformationFromLife(company, cartOrder, (member!=null?member:new Member()), false);
				}
				if(company.getId() != CompanyConstants.LIFE) {
					emailSender.sendEmailPaymentInformation(cartOrder.getName(), cartOrder.getEmailAddress(), cartOrder.getPhoneNumber(), cartOrder.getAddress1(), null, filepath,"","","");
				}
			}
			catch(final Exception e)
			{
				e.printStackTrace();
			}
		}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		sessionMap.remove("noLogInCartItems");
		return SUCCESS;
	}
	
	public long saveNoLogInCartTempOrder(List<CategoryItem> catItems)
	{
		Double totalPrice = 0.0;
		String name = "", email = "", contactNumber = "", address = "", comments = "";
		String info1 = ""; // company
		String info2 = ""; // industry
		String info3 = ""; // macAddress
		String info4 = ""; // licenseType
		
		Member tempMember = new Member();

		if(request.getParameter("memberId") != null)
		{

			tempMember = memberDelegate.find(Long.parseLong(request.getParameter("memberId")));
			name = tempMember.getFirstname();
			email = tempMember.getEmail();
			contactNumber = tempMember.getMobile();
			address = tempMember.getReg_companyAddress();

		}
		
		if(CompanyConstants.TOTAL_QUEUE2 == company.getId()){
			name = request.getParameter("name");
			email = request.getParameter("emailAddress");
			contactNumber = request.getParameter("phone");
			address = request.getParameter("country");
			info1 =  request.getParameter("company");
			info2 =  request.getParameter("industry");
			info3 =  request.getParameter("macAddress");
			info4 =  request.getParameter("licenseType");
			
			StringBuffer content = new StringBuffer("");
			content.append("Name: " + request.getParameter("name") + "<br><br>")
				.append("Company: " + request.getParameter("company") + "<br><br>")
				.append("Industry: " + request.getParameter("industry") + "<br><br>")
				.append("Email: " + request.getParameter("emailAddress") + "<br><br>")
				.append("Country: " + request.getParameter("country") + "<br><br>")
				.append("Phone: " + request.getParameter("phone") + "<br><br>")
				.append("MAC Address: " + request.getParameter("macAddress") + "<br><br>")
				.append("License Type: " + request.getParameter("licenseType") + "<br><br>");
			comments = content.toString();
		}

		final CartOrder cartOrder = new CartOrder();
		cartOrder.setAddress1(address);
		cartOrder.setStatus(OrderStatus.IN_PROCESS);
		cartOrder.setCompany(company);
		cartOrder.setName(name);
		cartOrder.setEmailAddress(email);
		cartOrder.setPhoneNumber(contactNumber);
		cartOrder.setPaymentStatus(PaymentStatus.PENDING);
		cartOrder.setPaymentType(PaymentType.PAYPAL);
		cartOrder.setComments(comments);
		cartOrder.setInfo1(info1);
		cartOrder.setInfo2(info2);
		cartOrder.setInfo3(info3);
		cartOrder.setInfo4(info4);
		
		if(tempMember.getId() != null)
		{
			cartOrder.setMember(tempMember);
		}

		final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
		if(catItems != null)
		{
			for(final CategoryItem cI : catItems)
			{
				if(cI.getName().indexOf("Shipping Cost") != -1)
				{
					cartOrder.setTotalShippingPrice2(cI.getPrice());
				}

				final CartOrderItem cartOrderItem = new CartOrderItem();
				final ItemDetail itemDetail = new ItemDetail();
				// set real id
				itemDetail.setRealID(cI.getId());
				itemDetail.setName(cI.getName());
				itemDetail.setDescription(cI.getShortDescription());
				
				if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA) {
					itemDetail.setImage(cI.getShortDescription());
					itemDetail.setDescription(cI.getDescription());
					cartOrderItem.setOtherDetail(cI.getOtherDetail());
				}
				
				if(("purpletag".equalsIgnoreCase(company.getName()) || "purpletag2".equalsIgnoreCase(company.getName()))&&StringUtils.isNotBlank(cI.getDescription())){
					itemDetail.setName(cI.getName()+"("+cI.getDescription()+")");
					itemDetail.setDescription(cI.getDescription());
				}	
				
				itemDetail.setPrice(cI.getPrice());
				cartOrderItem.setItemDetail(itemDetail);
				cartOrderItem.setQuantity(Integer.parseInt(cI.getOtherDetails()));
				cartOrderItem.setStatus(OrderStatus.NEW.toString());
				cartOrderItem.setCompany(company);
				cartOrderItem.setOrder(cartOrder);
				if(company.getName().equals("adeventsmanila"))
					cartOrderItem.setItemDetailMap(cI.getItemDetailMap());
				cartOrderItems.add(cartOrderItem);
				
				totalPrice = totalPrice + (cI.getPrice() * cartOrderItem.getQuantity());
			}
		}
		else
		{
		}
		cartOrder.setTotalPrice(totalPrice);
		cartOrder.setItems(cartOrderItems);
		final long orderId = cartOrderDelegate.insert(cartOrder);
		if(cartOrder.getMember() != null && cartOrder.getMember().getId() == null)
		{
			cartOrder.setMember(null);
		}
		cartOrderItemDelegate.batchInsert(cartOrderItems);
		return orderId;
	}

	public long saveNoLogInCartTempOrder(List<CategoryItem> catItems, Member tempMember, PaymentType paymentType)
	{

		final CartOrder cartOrder = new CartOrder();
		cartOrder.setAddress1(tempMember.getAddress1());
		cartOrder.setAddress2(tempMember.getAddress2());
		cartOrder.setStatus(OrderStatus.NEW);
		cartOrder.setCompany(company);
		cartOrder.setName(tempMember.getInfo1() + " " + tempMember.getFirstname() + " " + tempMember.getLastname());
		cartOrder.setEmailAddress(tempMember.getEmail());
		cartOrder.setPhoneNumber(tempMember.getLandline());
		cartOrder.setPaymentType(paymentType);
		cartOrder.setCity(tempMember.getCity());
		cartOrder.setZipCode(tempMember.getZipcode());
		
		cartOrder.setPhoneNumber(tempMember.getLandline() + " / " + tempMember.getMobile());
		cartOrder.setComments(tempMember.getInfo2());
		cartOrder.setPrescription("Company: " + tempMember.getReg_companyName() + "; Delivery Date: " + tempMember.getInfo4()
				+ "; Date of Birth: " + tempMember.getValue() + "; Nationality: " + tempMember.getInfo3());
		
		final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();

		if(catItems != null)
		{
			for(final CategoryItem cI : catItems)
			{
				if(cI.getName().indexOf("Shipping Cost") != -1)
				{
					cartOrder.setTotalShippingPrice2(cI.getPrice());
				}
				final CartOrderItem cartOrderItem = new CartOrderItem();
				final ItemDetail itemDetail = new ItemDetail();
				itemDetail.setName(cI.getName() + "(" + cI.getDescription() + ")");
				itemDetail.setDescription(cI.getDescription());
				itemDetail.setRealID(cI.getId());
				
				if(company.getName().equalsIgnoreCase(CompanyConstants.MR_AIRCON))
				{
					if(paymentType == PaymentType.COD)
					{
						if(!StringUtils.contains(cI.getName(), "Shipping") && !StringUtils.contains(cI.getName(), "Installation Price"))
						{
							final String codDiscount = cI.getCategoryItemOtherFieldMap().get("COD Discount").getContent();
							itemDetail.setPrice(cI.getPrice() - (cI.getPrice() * (Double.parseDouble(codDiscount) / 100)));
						}
						else
						{
							itemDetail.setPrice(cI.getPrice());
						}
					}
					else
					{
						itemDetail.setPrice(cI.getPrice());
					}
				}
				else
				{
					itemDetail.setPrice(cI.getPrice());
				}

				cartOrderItem.setItemDetail(itemDetail);
				cartOrderItem.setQuantity(cI.getOrderQuantity());
				cartOrderItem.setStatus(OrderStatus.NEW.toString());
				cartOrderItem.setCompany(company);
				cartOrderItem.setOrder(cartOrder);
				cartOrderItems.add(cartOrderItem);
			}
		}
		else
		{
		}
		cartOrder.setItems(cartOrderItems);
		final long orderId = cartOrderDelegate.insert(cartOrder);

		cartOrderItemDelegate.batchInsert(cartOrderItems);

		return orderId;

	}

	public long saveOrder(List<CategoryItem> catItems, Member tempMember, PaymentType paymentType)
	{

		final CartOrder cartOrder = new CartOrder();
		cartOrder.setAddress1(tempMember.getAddress1());
		cartOrder.setAddress2(tempMember.getAddress2());
		cartOrder.setStatus(OrderStatus.NEW);
		cartOrder.setCompany(company);
		cartOrder.setName(tempMember.getFirstname()+" " +tempMember.getMiddlename() +" " +tempMember.getLastname());
		cartOrder.setEmailAddress(tempMember.getEmail());
		cartOrder.setPhoneNumber(tempMember.getLandline()+","+tempMember.getMobile());
		cartOrder.setPaymentType(paymentType);
		cartOrder.setCity(tempMember.getCity());
		cartOrder.setComments(tempMember.getInfo1());
		cartOrder.setZipCode(tempMember.getZipcode());
		cartOrder.setCountry(tempMember.getInfo2());
		cartOrder.setState(tempMember.getProvince());

		final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();

		if(catItems != null)
		{
			for(final CategoryItem cI : catItems)
			{
				if(cI.getName().indexOf("Shipping Cost") != -1)
				{
					cartOrder.setTotalShippingPrice2(cI.getPrice());
				}
				final CartOrderItem cartOrderItem = new CartOrderItem();
				final ItemDetail itemDetail = new ItemDetail();
				itemDetail.setName(cI.getName() + "-" + cI.getDescription());
				if(CompanyConstants.IIEE_ORG_PHILS.equalsIgnoreCase(company.getName())){
					itemDetail.setName(cI.getName());
				}
				itemDetail.setDescription(cI.getDescription());

				itemDetail.setPrice(cI.getPrice());

				cartOrderItem.setItemDetail(itemDetail);
				cartOrderItem.setQuantity(cI.getOrderQuantity());
				cartOrderItem.setStatus(OrderStatus.NEW.toString());
				cartOrderItem.setCompany(company);
				cartOrderItem.setOrder(cartOrder);
				cartOrderItems.add(cartOrderItem);
			}
		}
		else
		{
		}
		cartOrder.setItems(cartOrderItems);
		final long orderId = cartOrderDelegate.insert(cartOrder);

		cartOrderItemDelegate.batchInsert(cartOrderItems);

		return orderId;

	}

	private List<CategoryItem> getAllCategoryItems(CartOrder cartOrder2)
	{
		final List<CategoryItem> catItems = new ArrayList<CategoryItem>();
		new ItemDetail();
		final List<CartOrderItem> cartOrderItems = cartOrder2.getItems();

		for(final CartOrderItem cartOrderItem : cartOrderItems)
		{
			final CategoryItem cI = new CategoryItem();
			cI.setName(cartOrderItem.getItemDetail().getName());
			cI.setPrice(cartOrderItem.getItemDetail().getPrice());
			cI.setOtherDetails(cartOrderItem.getQuantity().toString());
			cI.setSku(cartOrderItem.getItemDetail().getSku());
			cI.setId(cartOrderItem.getItemDetail().getRealID());
			cI.setOrderQuantity(cartOrderItem.getQuantity());
			if(company.getName().equals("adeventsmanila"))
				cI.setItemDetailMap(cartOrderItem.getItemDetailMap());
			catItems.add(cI);
		}

		if(catItems != null && catItems.size() > 0)
		{
			return catItems;
		}

		return null;
	}

	public String yesPayments()
	{
		// temporary testing in localhost Email
		yesPayments = new YesPayments();
		if(request.getParameter("MerchantGroup") != null)
		{
			yesPayments.setMerchantGroup(request.getParameter("MerchantGroup"));
		}
		if(request.getParameter("MerchantCode") != null)
		{
			yesPayments.setMerchantCode(request.getParameter("MerchantCode"));
		}
		// these request.getParameter MERCHANT REFERENCE is from YesPayments.com.ph and will Just be true upont theis POSTING
		if(request.getParameter("MerchantReference") != null)
		{
			yesPayments.setMerchantReference(request.getParameter("MerchantReference"));
			if(yesPayments.getMerchantReference().indexOf("M") != -1)
			{
				yesPayments.setMembershipFee(true);
			}
		}
		if(request.getParameter("TransactionType") != null)
		{
			yesPayments.setTransactionType(request.getParameter("TransactionType"));
		}
		if(request.getParameter("CardID") != null)
		{
			yesPayments.setCardId(request.getParameter("CardID"));
		}
		if(request.getParameter("CustomerAccountNumber") != null)
		{
			final String accountNum = request.getParameter("CustomerAccountNumber");
			yesPayments.setCustomerAccountNumber(accountNum);
		}
		if(request.getParameter("donationAmount") != null)
		{
			yesPayments.setDonation(request.getParameter("donationAmount"));
		}
		if(request.getParameter("BillNbr") != null)
		{
			yesPayments.setBillNumber(request.getParameter("BillNbr"));
		}
		if(request.getParameter("CardBin") != null)
		{
			yesPayments.setCardBin(request.getParameter("CardBin"));
		}
		if(request.getParameter("HashValue") != null)
		{
			yesPayments.setHashValue(request.getParameter("HashValue"));
		}
		if(request.getParameter("CrcyCd") != null)
		{
			yesPayments.setCrcyCd(request.getParameter("CrcyCd"));
		}
		if(request.getParameter("Amount") != null)
		{
			yesPayments.setAmount(request.getParameter("Amount"));
		}
		if(member != null)
		{
			yesPayments.setCardHolderEmail(member.getEmail());
		}
		if(request.getParameter("CardHolderEmail") != null)
		{
			yesPayments.setCardHolderEmail(request.getParameter("CardHolderEmail"));
		}
		if(request.getParameter("Notes") != null)
		{
			yesPayments.setNotes(request.getParameter("Notes"));
		}
		if(request.getParameter("RealTime") != null)
		{
			yesPayments.setRealTime(request.getParameter("RealTime"));
		}
		if(request.getParameter("LangCd") != null)
		{
			yesPayments.setLangCd(request.getParameter("LangCd"));
		}
		if(request.getParameter("SecretKey") != null)
		{
			yesPayments.setSecretKey(request.getParameter("SecretKey"));
		}
		// POST RESULT V2
		if(request.getParameter("TransactionID") != null && company.getId() == CompanyConstants.ONLINE_DEPOT)
		{
			yesPayments.setTransactionId(request.getParameter("TransactionID"));
			createOrder();
			initOrderItemList();
		}
		else if(request.getParameter("TransactionID") != null)
		{
			yesPayments.setTransactionId(request.getParameter("TransactionID"));
		}
		if(request.getParameter("ResponseCode") != null)
		{
			yesPayments.setResponseCode(request.getParameter("ResponseCode"));
		}
		if(request.getParameter("ResponseDescription") != null)
		{
			yesPayments.setResponseDescription(request.getParameter("ResponseDescription"));
		}
		if(request.getParameter("CardNbrMasked") != null)
		{
			yesPayments.setCardNbrMasked(request.getParameter("CardNbrMasked"));
		}
		if(request.getParameter("CardHolderNm") != null)
		{
			yesPayments.setCardHolderNm(request.getParameter("CardHolderNm"));
		}
		if(request.getParameter("ExpYYYY") != null)
		{
			yesPayments.setExpYYYY(request.getParameter("ExpYYYY"));
		}
		if(request.getParameter("ExpMM") != null)
		{
			yesPayments.setExpMM(request.getParameter("ExpMM"));
		}
		if(request.getParameter("WarningArray") != null)
		{
			yesPayments.setWarningArray(request.getParameter("WarningArray"));
		}
		// this method should only return true if the Client is a new Member
		// this part would generate The HashValue
		yesPayments.setHashValue("generateHashValuePlease");// /--> generateHashValuePlease is just a trigger in generating a HASH VALUE
		// hash value is always set to the last part,
		request.setAttribute("yesPayments", yesPayments);
		request.setAttribute("cartItemList", cartItemList);

		// this code will be executed when the transaction is from YesPayments Form
		if(yesPayments.getTransactionId() != null)
		{
			// membershipFee is set to true if there is a flag that a member is NEW
			final MessageManagementAction msg = new MessageManagementAction();
			msg.emailClientForSuccessPayment(yesPayments);
		}
		if(request.getParameter("modeOfPayment") != null)
		{
			request.setAttribute("modeOfPayment", request.getParameter("modeOfPayment"));
		}
		if(request.getParameter("redirectionPage") != null)
		{
			if(request.getParameter("redirectionPage").equalsIgnoreCase("verifyPayment"))
			{
				return Action.SUCCESS;
			}
			if(request.getParameter("redirectionPage").equalsIgnoreCase("paymentForm"))
			{
				return Action.ERROR;
			}
		}

		return Action.INPUT;
	}

	public String dragonpay(String merchantId, String description, String secretKey)
	{
		final Dragonpay dp = new Dragonpay();

		Double shippingFee = 0.00;
		boolean found = false;

		final List<CartOrderItem> cartOrderItems = cartOrder.getItems();
		for(final CartOrderItem i : cartOrderItems)
		{
			if(i.getItemDetail().getName().equals("Shipping Cost"))
			{
				shippingFee = i.getItemDetail().getPrice();
				found = true;
				break;
			}
		}
		if(!found)
		{
			final ShoppingCartItem shippingCost = getGurkkaShippingCost();
			if(shippingCost != null)
			{
				shippingFee = shippingCost.getItemDetail().getPrice();
			}
		}

		
		String URL = "";
		if(company.getId() == CompanyConstants.TOMATO) {
			totalPrice = 0;
			
			for(CartOrderItem i : cartOrderItems) {
				if(!i.getItemDetail().getName().equals(SHIPPING_COST) && !i.getItemDetail().getName().equals(DISCOUNT)) {
					totalPrice += i.getItemDetail().getPrice() * i.getQuantity();
					totalPrice += i.getOtherDetail().getFace().getPrice() * i.getQuantity();
				}
				else if(i.getItemDetail().getName().equals(DISCOUNT)) {
					totalPrice = totalPrice - i.getItemDetail().getPrice();
				}
			}
			
			URL = dp.payWithDragonPay(merchantId, cartOrder.getId().toString(), (totalPrice + shippingFee), "PHP", description, cartOrder.getMember().getEmail(), secretKey);
		}
		else {
			URL = dp.payWithDragonPay(merchantId, cartOrder.getId().toString(), (cartOrder.getTotalPrice() + shippingFee), "PHP", description, cartOrder.getMember().getEmail(), secretKey);
		}

		return URL;
	}

	public String dragonpayTest(String merchantId, String description, String secretKey)
	{
		final Dragonpay dp = new Dragonpay();

		Double shippingFee = 0.00;
		boolean found = false;

		final List<CartOrderItem> cartOrderItems = cartOrder.getItems();
		for(final CartOrderItem i : cartOrderItems)
		{
			if(i.getItemDetail().getName().equals("Shipping Cost"))
			{
				shippingFee = i.getItemDetail().getPrice();
				found = true;
				break;
			}
		}
		if(!found)
		{
			final ShoppingCartItem shippingCost = getGurkkaShippingCost();
			if(shippingCost != null)
			{
				shippingFee = shippingCost.getItemDetail().getPrice();
			}
		}

		final String URL = dp.payWithDragonPayTest(merchantId, cartOrder.getId().toString(), (cartOrder.getTotalPrice() + shippingFee), "PHP", description, cartOrder.getMember().getEmail(), secretKey);

		return URL;
	}
	
	//Gurkka update dragonpay transactions
	public void updateDragonpayTransaction() throws JRException, IOException
	{	
		final String txnid = request.getParameter("txnid");
		final String refno = request.getParameter("refno");
		final String status = request.getParameter("status");
		final String digest = request.getParameter("digest");

		updateOrder(txnid, refno, status, digest, CompanyConstants.GURKKA_TEST);

	}
	
	//IIEE update dragonpay transactions
	public void updateIIEEDragonpayTransaction()
	{	
		final String txnid = request.getParameter("txnid");
		final String refno = request.getParameter("refno");
		final String status = request.getParameter("status");
		final String digest = request.getParameter("digest");

		updateOrder(txnid, refno, status, digest, CompanyConstants.IIEE);
		
	}

	public void updateOrder(String txnid, String refno, String status, String digest, int companyId)
	{
		String secretkey = null;
		
		final Dragonpay dp = new Dragonpay();
		
		if(companyId == CompanyConstants.GURKKA_TEST)
		{
			secretkey = PaymentConstants.GR_DRAGONPAY_SECRET_KEY;
		}
		else if(companyId == CompanyConstants.IIEE)
		{
			secretkey = PaymentConstants.IIEE_DRAGONPAY_SECRET_KEY;
		}
		else if(companyId == CompanyConstants.TOMATO)
		{
			secretkey = PaymentConstants.TO_DRAGONPAY_SECRET_KEY;
		}
		
		String message = "";
		try
		{
			message = URLDecoder.decode(request.getParameter("message"), "UTF-8");
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
		
		final boolean valid = dp.isTransactionValid(txnid, refno, status, message, secretkey, digest);
		
		final Long id = Long.parseLong(txnid);
		cartOrder = cartOrderDelegate.find(id);
		
		if(valid == true)
		{
			cartOrderItems = cartOrderItemDelegate.findAll(cartOrder);
			
			if(company == null)
			{
				company = companyDelegate.find(Long.valueOf(companyId));
			}
			
			if(member == null && companyId == CompanyConstants.GURKKA_TEST)
			{
				member = memberDelegate.findEmail(company, cartOrder.getMember().getEmail());
			}
			
			if(status.equalsIgnoreCase("P"))
			{
				cartOrder.setPaymentStatus(PaymentStatus.PENDING);
			}
			else if(status.equalsIgnoreCase("S"))
			{
				cartOrder.setPaymentStatus(PaymentStatus.PAID);
				cartOrder.setStatus(OrderStatus.PENDING);
				
				if(company.getName().equalsIgnoreCase(CompanyConstants.GURKKATEST_COMPANY_NAME)) {
					CartOrder cartOrder = cartOrderDelegate.find(new Long(txnid));
					try {
						sendEmail(member, cartOrder);
						cartOrder.setPaymentType(PaymentType.DRAGON_PAY);
						cartOrder.setPaymentStatus(PaymentStatus.PAID);
						cartOrderDelegate.update(cartOrder);
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(companyId == CompanyConstants.GURKKA)
				{
					cartOrder.setPaymentType(PaymentType.DRAGON_PAY);
					cartOrder.setPaymentStatus(PaymentStatus.PAID);
					final Double savings = 0D;//GurkkaMemberUtil.getFreebiesSavingsFromCart(company, member, cartOrderItems.getList());
					cartOrderItems = cartOrderItemDelegate.findAll(cartOrder);
					
					if(companyId == CompanyConstants.GURKKA) {
						sendGurkkaEmailShipping(member, 0D, "NEW");
						sendGurkkaEmailShipping(member, savings, "OTC/Online Banking");
					}
					
					for(final CartOrderItem coi : cartOrderItems)
					{
						final CategoryItem item = CategoryItemUtil.getItemFromCartOrder(coi);
						final Integer quantity = Math.abs(coi.getQuantity());
						
						if(companyId == CompanyConstants.GURKKA_TEST)
						{
							if(item != null)
							{
								//update promo basket item
								Group pg = new Group();
								
								pg = groupDelegate.find(company, "");
								if(item.getParentGroup() == pg){
									List<CategoryItem> listPromoItem = new ArrayList<CategoryItem>();
									listPromoItem = listOfPromoBasketItem(item, 0);
									for(CategoryItem itm : listPromoItem){
										Integer promoItemRemainingInventory = 0;
										Integer promoItemQuantity = 0;
										try{
											
										}catch(Exception e){}
										try{
										}catch(Exception e){}
										
										
									}
								}
							}
						}
					}
					
				}
				else if(companyId == CompanyConstants.IIEE)
				{
					sendIIEEPaymentEmail(cartOrder);
				}
				
			}
			else
			{
				if(cartOrder.getStatus() != OrderStatus.CANCELLED)
				{}
			}
		}
		else
		{
			if(cartOrder.getStatus() != OrderStatus.CANCELLED)
			{}
		}
		cartOrderDelegate.update(cartOrder);
	}
	
	//Gurkka client side dragonpay return
	public String dragonpayReturn() throws JRException, IOException
	{
		final String status = request.getParameter("status");
		final String txnid = request.getParameter("txnid");
		final String refno = request.getParameter("refno");
		final String digest = request.getParameter("digest");

		processDragonpayReturn(txnid, refno, status, digest, CompanyConstants.GURKKA_TEST);
		
		return SUCCESS;

	}
	
	private String sendEmail(Member member, CartOrder cartOrder) throws JRException, IOException
	{return null;}
	
	/**
	 * returns the absolute path of the generated voucher
	 * 
	 * @param member
	 * @param cartOrder
	 * @return
	 * @throws JRException 
	 * @throws IOException 
	 */
	private String generateVoucher(Member member, CartOrder cartOrder) throws JRException, IOException
	{return null;}
	
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
	
	//IIEE client side dragonpay return
	public String dragonpayIIEEReturn()
	{
		final String status = request.getParameter("status");
		final String txnid = request.getParameter("txnid");
		final String refno = request.getParameter("refno");
		final String digest = request.getParameter("digest");

		processDragonpayReturn(txnid, refno, status, digest, CompanyConstants.IIEE);
		
		return SUCCESS;

	}
	
	public String processDragonpayReturn(String txnid, String refno, String status, String digest, int companyId)
	{
		String secretkey = null;
		
		if(companyId == CompanyConstants.GURKKA_TEST){
			secretkey = PaymentConstants.GR_DRAGONPAY_SECRET_KEY;
		}else if(companyId == CompanyConstants.IIEE){
			secretkey = PaymentConstants.IIEE_DRAGONPAY_SECRET_KEY;
		}else if(companyId == CompanyConstants.TOMATO){
			secretkey = PaymentConstants.TO_DRAGONPAY_SECRET_KEY;
		}
		
		String message = "";
		try
		{
			message = URLDecoder.decode(request.getParameter("message"), "UTF-8");
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}

		final Dragonpay dp = new Dragonpay();

		final boolean valid = dp.isTransactionValid(txnid, refno, status, message, secretkey, digest);

		if(valid == true)
		{
			if(companyId == CompanyConstants.GURKKA_TEST){
				if(status.equalsIgnoreCase("S"))
				{
				}
				else if(status.equalsIgnoreCase("P"))
				{
				}
				else
				{
				}
			}else{
				if(status.equalsIgnoreCase("S"))
				{
					setNotificationMessage(PaymentConstants.PAYMENT_NOTIFICATION_SUCCESS);
				}
				else if(status.equalsIgnoreCase("P"))
				{
					setNotificationMessage(PaymentConstants.PAYMENT_NOTIFICATION_PENDING);
				}
				else
				{
					setNotificationMessage(PaymentConstants.PAYMENT_NOTIFICATION_INVALID);
				}
			}
			
		}
		else
		{
			setNotificationMessage(PaymentConstants.PAYMENT_NOTIFICATION_INVALID);
		}

		return SUCCESS;

	}

	public String iPay88(String merchantCode, String merchantKey, String prodDesc, String responseURL, String backendURL)
	{
		Double shippingFee = 0.00;
		boolean found = false;

		final List<CartOrderItem> cartOrderItems = cartOrder.getItems();
		for(final CartOrderItem i : cartOrderItems)
		{
			if(i.getItemDetail().getName().equals("Shipping Cost"))
			{
				shippingFee = i.getItemDetail().getPrice();
				found = true;
				break;
			}
		}
		if(!found && company.getId() == CompanyConstants.GURKKA)
		{
			final ShoppingCartItem shippingCost = getGurkkaShippingCost();
			if(shippingCost != null)
			{
				shippingFee = shippingCost.getItemDetail().getPrice();
			}
		}
		
		if(CompanyConstants.WENDYS == company.getId().intValue()){
			shippingFee += cartOrder.getTotalShippingPrice2();
		}
		
		final iPay88 ip88 = new iPay88(merchantKey, merchantCode, cartOrder.getId().toString(), cartOrder.getTotalPrice() + shippingFee, PaymentConstants.PHP_IPAY88_CURRENCY);

		final String signature = ip88.getIPay88Signature();

		String username = cartOrder.getMember() != null ? cartOrder.getMember().getUsername() : cartOrder.getName();
		String useremail = cartOrder.getMember() != null ? cartOrder.getMember().getEmail() : cartOrder.getEmailAddress();
		String usercontact = cartOrder.getMember() != null ? cartOrder.getMember().getMobile() : cartOrder.getPhoneNumber();
		
		request.setAttribute("merchantCode", merchantCode);
		request.setAttribute("refNo", cartOrder.getId().toString());
		request.setAttribute("userName", username);
		request.setAttribute("userEmail", useremail);
		request.setAttribute("userContact", usercontact);
		request.setAttribute("remark", cartOrder.getComments());
		request.setAttribute("signature", signature);

		final DecimalFormat df = new DecimalFormat("#.00");
		request.setAttribute("amount", df.format(cartOrder.getTotalPrice() + shippingFee));

		if(company.getId() == CompanyConstants.MR_AIRCON_ID){
			JSONObject obj = new JSONObject();
			obj.put("merchantCode", merchantCode);
			obj.put("refNo", cartOrder.getId().toString());
			obj.put("userName", username);
			obj.put("userEmail", useremail);
			obj.put("userContact", usercontact);
			obj.put("remark", cartOrder.getComments());
			obj.put("signature", signature);
			obj.put("amount", df.format(cartOrder.getTotalPrice() + shippingFee));
			
			inputStream = new StringBufferInputStream(obj.toJSONString().replaceAll("\'", "'"));

			return "json";
		}
		

		
		return "ipay88";
	}

	public String updateIPay88Transaction()
	{

		final String refno = request.getParameter("RefNo");
		final String status = request.getParameter("Status");
		final String amount = request.getParameter("Amount");
		final String currency = request.getParameter("Currency");
		final String signature = request.getParameter("Signature");
		final String merchantCode = request.getParameter("MerchantCode");

		final Long id = Long.parseLong(refno);
		final CartOrder order = cartOrderDelegate.find(id);
		cartOrder = order;
		cartOrderItems = cartOrderItemDelegate.findAll(order);

		if(company == null)
		{
			if(merchantCode.equalsIgnoreCase(PaymentConstants.GR_IPAY88_MERCHANT_CODE)){
				//company = companyDelegate.find(Long.valueOf(CompanyConstants.GURKKA));
				company = companyDelegate.find(Long.valueOf(CompanyConstants.GURKKA_TEST));
			}else if(merchantCode.equalsIgnoreCase(PaymentConstants.MR_IPAY88_MERCHANT_CODE)){
				company = companyDelegate.find(Long.valueOf(CompanyConstants.MR_AIRCON_ID));
			}else if(merchantCode.equalsIgnoreCase(PaymentConstants.WE_IPAY88_MERCHANT_CODE)){
				company = companyDelegate.find(Long.valueOf(CompanyConstants.WENDYS));
			}
		}

		iPay88 ipay88 = null;
		
		if(merchantCode.equalsIgnoreCase(PaymentConstants.GR_IPAY88_MERCHANT_CODE)){
			ipay88 = new iPay88(PaymentConstants.GR_IPAY88_MERCHANT_KEY, PaymentConstants.GR_IPAY88_MERCHANT_CODE, PaymentConstants.GR_IPAY88_PAYMENT_ID, refno, amount,
						PaymentConstants.PHP_IPAY88_CURRENCY, status);
			
			final boolean isValid = ipay88.isTransactionValid(signature);

			if(member == null)
			{
				member = memberDelegate.findEmail(company, cartOrder.getMember().getEmail());
			}
			
			if(isValid && status.equalsIgnoreCase("1"))
			{
				if(order.getPaymentStatus() != PaymentStatus.PAID){
					
					if(company.getName().equalsIgnoreCase(CompanyConstants.GURKKATEST_COMPANY_NAME)) {
						CartOrder cartOrder = order;
						try {
							sendEmail(member, cartOrder);
							cartOrder.setPaymentType(PaymentType.I_PAY88);
							cartOrder.setPaymentStatus(PaymentStatus.PAID);
							cartOrderDelegate.update(cartOrder);
						} catch (JRException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						final Double savings = 0D;//GurkkaMemberUtil.getFreebiesSavingsFromCart(company, member, cartOrderItems.getList());
						
						sendGurkkaEmailShipping(member, 0D, "NEW");
						sendGurkkaEmailShipping(member, savings, "Credit Card");
					}
					
				}

				order.setPaymentStatus(PaymentStatus.PAID);

			}
			else
			{
				if(order.getStatus() != OrderStatus.CANCELLED)
				{
					
					
					for(final CartOrderItem coi : cartOrderItems)
					{
						final CategoryItem item = CategoryItemUtil.getItemFromCartOrder(coi);
						final Integer quantity = Math.abs(coi.getQuantity());
						if(item != null)
						{}
					}
					order.setStatus(OrderStatus.CANCELLED);
					
				}

			}
			cartOrderDelegate.update(order);
			
		}else if(merchantCode.equalsIgnoreCase(PaymentConstants.MR_IPAY88_MERCHANT_CODE) || merchantCode.equalsIgnoreCase(PaymentConstants.WE_IPAY88_MERCHANT_CODE)){
			if(merchantCode.equalsIgnoreCase(PaymentConstants.MR_IPAY88_MERCHANT_CODE)) {
				ipay88 = new iPay88(PaymentConstants.MR_IPAY88_MERCHANT_KEY, PaymentConstants.MR_IPAY88_MERCHANT_CODE, PaymentConstants.MR_IPAY88_PAYMENT_ID, refno, amount,
					PaymentConstants.PHP_IPAY88_CURRENCY, status);
			}
			else if(merchantCode.equalsIgnoreCase(PaymentConstants.WE_IPAY88_MERCHANT_CODE)) {
				ipay88 = new iPay88(PaymentConstants.WE_IPAY88_MERCHANT_KEY, PaymentConstants.WE_IPAY88_MERCHANT_CODE, PaymentConstants.WE_IPAY88_PAYMENT_ID, refno, amount,
					PaymentConstants.PHP_IPAY88_CURRENCY, status);
			}
			
			final boolean isValid = ipay88.isTransactionValid(signature);

			if(isValid && status.equalsIgnoreCase("1"))
			{
				if(merchantCode.equalsIgnoreCase(PaymentConstants.MR_IPAY88_MERCHANT_CODE)) {
					sendMrAirconShipping(cartOrder);
				}
				
				cartOrder.setPaymentStatus(PaymentStatus.PAID);
			}
			else
			{
				order.setStatus(OrderStatus.CANCELLED);
			}
			
			cartOrderDelegate.update(order);
		}
		
		
		
		return SUCCESS;
	}

	public String iPay88Return()
	{
		final String refno = request.getParameter("RefNo");
		final String status = request.getParameter("Status");
		final String amount = request.getParameter("Amount");
		final String currency = request.getParameter("Currency");
		final String signature = request.getParameter("Signature");
		final String merchantCode = request.getParameter("MerchantCode");
		final String errDesc = request.getParameter("ErrDesc");
		Boolean mustDeleteOrder = false;
		System.out.println("kleanneat errDesc = " + errDesc);
		System.out.println("kleanneat refno = " + refno);
		System.out.println("kleanneat status = " + status);
		System.out.println("kleanneat amount = " + amount);

		if(company == null)
		{
			if(merchantCode.equalsIgnoreCase(PaymentConstants.GR_IPAY88_MERCHANT_CODE)){
				//company = companyDelegate.find(Long.valueOf(CompanyConstants.GURKKA));
				company = companyDelegate.find(Long.valueOf(CompanyConstants.GURKKA_TEST));
			}else if(merchantCode.equalsIgnoreCase(PaymentConstants.MR_IPAY88_MERCHANT_CODE)){
				company = companyDelegate.find(Long.valueOf(CompanyConstants.MR_AIRCON_ID));
			}else if(merchantCode.equalsIgnoreCase(PaymentConstants.WE_IPAY88_MERCHANT_CODE)){
				company = companyDelegate.find(Long.valueOf(CompanyConstants.WENDYS));
			}
			
		}

		if(StringUtils.trimToNull(refno) != null)
		{
			final Long id = Long.parseLong(refno);
			final CartOrder order = cartOrderDelegate.find(id);
			cartOrder = order;

			cartOrderItems = cartOrderItemDelegate.findAll(order);

			iPay88 ipay88 = null;
			
			if(merchantCode.equalsIgnoreCase(PaymentConstants.GR_IPAY88_MERCHANT_CODE)){
				ipay88 = new iPay88(PaymentConstants.GR_IPAY88_MERCHANT_KEY, PaymentConstants.GR_IPAY88_MERCHANT_CODE, PaymentConstants.GR_IPAY88_PAYMENT_ID, refno, amount,
							PaymentConstants.PHP_IPAY88_CURRENCY, status);
				boolean isValid = ipay88.isTransactionValid(signature);
				
				System.out.println("is transation valid = " + isValid);
				
				if(member == null)
				{
					member = memberDelegate.findEmail(company, cartOrder.getMember().getEmail());
				}
				
				if(isValid)
				{}
				else
				{
					if(order.getStatus() != OrderStatus.CANCELLED)
					{
						for(final CartOrderItem coi : cartOrderItems)
						{
							final CategoryItem item = CategoryItemUtil.getItemFromCartOrder(coi);
							final Integer quantity = Math.abs(coi.getQuantity());
							if(item != null)
							{}
						}
						order.setStatus(OrderStatus.CANCELLED);
					}
				}
			}else if(merchantCode.equalsIgnoreCase(PaymentConstants.MR_IPAY88_MERCHANT_CODE) || merchantCode.equalsIgnoreCase(PaymentConstants.WE_IPAY88_MERCHANT_CODE)){
				if(merchantCode.equalsIgnoreCase(PaymentConstants.MR_IPAY88_MERCHANT_CODE)) {
					ipay88 = new iPay88(PaymentConstants.MR_IPAY88_MERCHANT_KEY, PaymentConstants.MR_IPAY88_MERCHANT_CODE, PaymentConstants.MR_IPAY88_PAYMENT_ID, refno, amount,
						PaymentConstants.PHP_IPAY88_CURRENCY, status);
				}
				else if(merchantCode.equalsIgnoreCase(PaymentConstants.WE_IPAY88_MERCHANT_CODE)) {
					ipay88 = new iPay88(PaymentConstants.WE_IPAY88_MERCHANT_KEY, PaymentConstants.WE_IPAY88_MERCHANT_CODE, PaymentConstants.WE_IPAY88_PAYMENT_ID, refno, amount,
						PaymentConstants.PHP_IPAY88_CURRENCY, status);
				}
				
				boolean isValid = ipay88.isTransactionValid(signature);
				
				if(isValid)
				{
					if(status.equalsIgnoreCase("1"))
					{
						order.setPaymentStatus(PaymentStatus.PAID);
						order.setStatus(OrderStatus.NEW);
						setNotificationMessage("CC_SUCCESS");
						
					}
					else
					{
						order.setStatus(OrderStatus.CANCELLED);
						if(company.getName().equalsIgnoreCase("wendys")){
							mustDeleteOrder = true;
						}
						
						setNotificationMessage("FAILED");
						setErrorMessage(errDesc);
					}
				}
				else
				{
					order.setStatus(OrderStatus.CANCELLED);
					if(company.getName().equalsIgnoreCase("wendys")){
						mustDeleteOrder = true;
					}
					setNotificationMessage("FAILED");
				}
			}
			if(mustDeleteOrder){
				for(CartOrderItem orderitem : cartOrderItemDelegate.findAll(order)){
					cartOrderItemDelegate.delete(orderitem);
				}
				cartOrderDelegate.delete(order);
			}
			else{
				cartOrderDelegate.update(order);
			}
		}
		
		
		try{
			
			if(company.getName().equalsIgnoreCase("wendys")) {
			// Send SMS to Store
			String strReceiver = cartOrder.getComments();
			String strLimitter1 = "Prefferred Store: ";
			String strLimitter2 = "Preffered Date: ";
			String strLimitter3 = "Preffered Time: ";
			if(cartOrder.getShippingType()==ShippingType.PICKUP && company.getName().equalsIgnoreCase("wendys")){
				
				
				strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
				strLimitter2 = strReceiver.substring(strReceiver.indexOf(strLimitter2)+(strLimitter2.length()-1),strReceiver.indexOf(strLimitter3)).trim();
				strLimitter3 = strReceiver.substring(strReceiver.indexOf(strLimitter3)+(strLimitter3.length()-1),strReceiver.length()).trim();
				
				CategoryItem itemStore = categoryItemDelegate.findByName(company, strLimitter1.trim());
				String strCellphoneNumber = itemStore.getCategoryItemOtherFieldMap().get("Cellphone Number").getContent();
				if(strCellphoneNumber.indexOf("+63")<0){
					strCellphoneNumber = "+639166681005";
				}
				String strMessage = "WENDY'S ORDER DETAILS: " + "REF. ID: "+ cartOrder.getId() +
						" NAME: " + cartOrder.getName() + " CONTACT #: "+cartOrder.getPhoneNumber();
				System.out.println("###Wendys receiver:"+strCellphoneNumber.trim()+"##");
				try{
				final String result = SmsClient.getInstance().sendSms("https://sms.ivant.com",
						"QUEUEPAD123456", "1", strCellphoneNumber.trim(), strMessage, "PH", "WTGWENDYS", "wendys",
						ClientConfigurationServlet.getInstance().getWendysPrivateKey2(),
						ClientConfigurationServlet.getInstance().getWendysHeaderNamePublicKey2(),
						ClientConfigurationServlet.getInstance().getWendysPublicKey2(),
						ClientConfigurationServlet.getInstance().getWendysHeaderNameSharedSecret2());
				}catch(Exception e){logger.info(e);}
			}
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
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
			wendysAssignPoints(company, cartOrder, dPriceTotal + cartOrder.getTotalShippingPrice2());
			
			//EmailSenderAction wendysEmailSender = new EmailSenderAction();
			//wendysEmailSender.sendWendysOrderConfirmation(cartOrder.getShippingType()== ShippingType.DELIVERY ? "Delivery" : "Pickup", String.valueOf(cartOrder.getId()), cartOrder.getName(), strLimitter1, strLimitter2, strLimitter3, cartOrder.getAddress1(), cartOrder.getCity(), cartOrder.getState(), cartOrder.getCountry(), billingDate2, cartOrder.getPhoneNumber(), cartOrder.getPaymentType() == PaymentType.COD ? "Cash on Delivery" : "Credit Card", cartOrder.getStatus().getName(), cartOrder.getPaymentStatus() == PaymentStatus.PAID ? "PAID" : "PENDING", cartOrderItemDelegate.findAll(cartOrder).getList(), String.valueOf(cartOrder.getTotalShippingPrice2()),  String.valueOf(dPriceTotal), String.valueOf(cartOrder.getTotalShippingPrice2() + dPriceTotal), cartOrder.getMember().getEmail());
			if(cartOrder.getCompany().getName().equalsIgnoreCase("wendys") && cartOrder.getPaymentType()!= PaymentType.COD){
				try{
				logger.info("ShoppingCartResource::: Inside Wendys Email Confirmation ");
				strReceiver = cartOrder.getComments();
				
				strLimitter1 = "";
				strLimitter2 = "";
				strLimitter3 = "";
				if(cartOrder.getShippingType() == ShippingType.PICKUP){
					 strLimitter1 = "Prefferred Store: ";
					 strLimitter2 = "Preffered Date: ";
					 strLimitter3 = "Preffered Time: ";
				
					strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
					strLimitter2 = strReceiver.substring(strReceiver.indexOf(strLimitter2)+(strLimitter2.length()-1),strReceiver.indexOf(strLimitter3)).trim();
					strLimitter3 = strReceiver.substring(strReceiver.indexOf(strLimitter3)+(strLimitter3.length()-1),strReceiver.length()).trim();
				}
				
				dateFormat = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
				date2 = 
					(cartOrder.getCreatedOn() != null)
						? cartOrder.getCreatedOn()
						: new Date();
				billingDate2 = dateFormat.format(date2);
				dPriceTotal = 0.0;
				for(CartOrderItem orderItem : cartOrder.getItems()){
					if(orderItem.getStatus().equalsIgnoreCase("ok")){
						dPriceTotal = dPriceTotal + (orderItem.getItemDetail().getPrice()*orderItem.getQuantity());
					}
				}
				
				logger.info("CHECKOUTACTION::: Email Address: " + cartOrder.getMember().getEmail());
				EmailSenderAction wendysEmailSender = new EmailSenderAction();
				wendysEmailSender.sendWendysOrderConfirmation(cartOrder.getShippingType()== ShippingType.DELIVERY ? "Delivery" : "Pickup", String.valueOf(cartOrder.getId()), cartOrder.getName(), strLimitter1, strLimitter2, strLimitter3, cartOrder.getAddress1(), cartOrder.getCity(), cartOrder.getState(), cartOrder.getCountry(), billingDate2, cartOrder.getPhoneNumber(), cartOrder.getPaymentType() == PaymentType.COD ? "Cash on Delivery" : "Credit Card", cartOrder.getStatus().getName(), cartOrder.getPaymentStatus() == PaymentStatus.PAID ? "PAID" : "PENDING", cartOrderItemDelegate.findAll(cartOrder).getList(), String.valueOf(cartOrder.getTotalShippingPrice2()),  String.valueOf(dPriceTotal), String.valueOf(cartOrder.getTotalShippingPrice2() + dPriceTotal), cartOrder.getMember().getEmail());
				
				//-------ASSIGN POINTS--------
				System.out.println("***ENTERING ASSIGNING POINTS***");
				wendysAssignPoints(company, cartOrder, dPriceTotal + cartOrder.getTotalShippingPrice2());
				
				}
				catch(Exception e){
					logger.info("WENDYS ERROR ::: " + e.toString());
				}
			}
			

			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("###Wendys ERROR:"+e.getMessage());
		}
		
		return SUCCESS;
	}

	public String updatePesoPayTransaction()
	{
		request.getParameter("src");
		request.getParameter("prc");
		request.getParameter("Ord");
		request.getParameter("Holder");
		final String successCode = request.getParameter("successcode");
		final String ref = request.getParameter("Ref");
		final String payRef = request.getParameter("PayRef");
		request.getParameter("Amt");
		request.getParameter("Cur");
		request.getParameter("remark");
		request.getParameter("AuthId");
		request.getParameter("eci");
		request.getParameter("payerAuth");
		request.getParameter("sourceIp");
		request.getParameter("ipCountry");
		request.getParameter("AlertCode");
		request.getParameter("mSchPayId");
		request.getParameter("dSchPayId");

		cartOrder = cartOrderDelegate.find(Long.parseLong(ref));

		if(cartOrder != null)
		{
			if(successCode.trim().equalsIgnoreCase("0"))
			{

				cartOrder.setPaymentStatus(PaymentStatus.PAID);
				cartOrder.setTransactionNumber(payRef);
				cartOrderDelegate.update(cartOrder);

				// Email Shipping Information
				sendMrAirconShipping(cartOrder);
			}
			else
			{
				cartOrder.setStatus(OrderStatus.CANCELLED);
				cartOrder.setTransactionNumber(payRef);
				cartOrderDelegate.update(cartOrder);
			}

		}
		return SUCCESS;

	}

	public String createVoucherGurkka(String type, CartOrder cartOrder, Double savings)
	{

		final ObjectList<CartOrderItem> cartItemList = cartOrderItemDelegate.findAll(cartOrder);

		Double shippingFee = 0.00;
		boolean found = false;
		for(final CartOrderItem i : cartItemList)
		{
			if(i.getItemDetail().getName().equals("Shipping Cost"))
			{
				shippingFee = i.getItemDetail().getPrice();
				found = true;
				break;
			}
		}
		if(!found)
		{
			final ShoppingCartItem shippingCost = getGurkkaShippingCost();
			if(shippingCost != null)
			{
				shippingFee = shippingCost.getItemDetail().getPrice();
			}
		}

		final Document voucher = new Document();

		final String directory = servletContext.getRealPath("voucherpdf");
		final String resource = servletContext.getRealPath("companies");

		String location = "";
		try
		{}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
		return location;

	}

	private static void PlaceChunck(PdfWriter writer, String type, String text, float x, float y)
	{
		final PdfContentByte cb = writer.getDirectContent();
		BaseFont bf = null;

		final int fontSize = 8;

		cb.saveState();

		try
		{
			bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

			cb.beginText();
			cb.moveText(x, y);
			cb.setFontAndSize(bf, fontSize);
			cb.showText(text);
			cb.endText();

		}
		catch(final Exception e)
		{

			e.printStackTrace();
		}
	}

	public String checkoutGurkkaReward()
	{
		try
		{
			if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
			{
				if(member != null)
				{
					final HttpSession httpSession = request.getSession();
					final String itemIDString = StringUtils.trimToNull(request.getParameter("selectedItem"));
					if(itemIDString != null)
					{
						final ShoppingCart sc = new ShoppingCart();
						sc.setCompany(company);
						sc.setMember(member);
						shoppingCartDelegate.insert(sc);

						final Long id = Long.valueOf(itemIDString);
						final CategoryItem item = categoryItemDelegate.find(id);
						final ItemDetail itemDetail = item.getItemDetail();
						final String name = itemDetail.getName();

						itemDetail.setName(name + " (Reward)");
						itemDetail.setPrice(0.00);
						itemDetail.setRealID(id);

						final ShoppingCartItem cartItem = new ShoppingCartItem();
						cartItem.setCompany(company);
						cartItem.setItemDetail(itemDetail);
						cartItem.setQuantity(1);
						cartItem.setShoppingCart(sc);

						final List<ShoppingCartItem> cartItemList = new ArrayList<ShoppingCartItem>();
						cartItemList.add(cartItem);

						shoppingCartItemDelegate.insert(cartItem);

						sc.setItems(cartItemList);
						shoppingCartDelegate.update(sc);

						setShoppingCart(sc);

						createOrderBasic();
						initOrderItemList();

						final MemberType previous = (MemberType) httpSession.getAttribute("previousMemberType");
						/** Which cycle to set to true - previous or current */

						final MemberLog memberLog = new MemberLog();
						memberLog.setCompany(company);
						memberLog.setMember(member);
						/*
						 * memberLog.setRemarks(
						 * "DATE:" + new Date().toString() + "|" +
						 * "NAME:" + name);
						 */
						memberLog.setRemarks(new Date() + " " + itemDetail.getName());
						memberLogDelegate.insert(memberLog);

						httpSession.setAttribute("rewardSuccess", true);
						return sendGurkkaEmailShipping(member, (double) 0, "REWARD");
					}
				}
				return INPUT;
			}
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
		return ERROR;
	}

	public String getCheckoutUrl()
	{
		return this.checkoutUrl;
	}

	public void setPaypal(Paypal paypal)
	{
		this.paypal = paypal;
	}

	public String getPToken()
	{
		return pToken;
	}

	public void setPToken(String token)
	{
		pToken = token;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}

	@Override
	public void setSession(Map arg0)
	{
		this.sessionMap = arg0;
	}

	public float getVat()
	{
		return vat;
	}

	public void setVat(float vat)
	{
		this.vat = vat;
	}

	public Boolean getIagree()
	{
		return iagree;
	}

	public void setIagree(Boolean iagree)
	{
		this.iagree = iagree;
	}

	public Boolean getAmtothers()
	{
		return amtothers;
	}

	public void setAmtothers(Boolean amtothers)
	{
		this.amtothers = amtothers;
	}

	public void setOrderCount(int orderCount)
	{
	}

	public String getNotificationMessage()
	{
		return notificationMessage;
	}

	@Override
	public void setNotificationMessage(String notificationMessage)
	{
		this.notificationMessage = notificationMessage;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	public String[] getItemDescription()
	{
		return itemDescription;
	}

	public void setItemDescription(String[] itemDescription)
	{
		this.itemDescription = itemDescription;
	}

	public String[] getItemIds()
	{
		return itemIds;
	}

	public void setItemIds(String[] itemIds)
	{
		this.itemIds = itemIds;
	}

	public ArrayList<CategoryItem> getItemList()
	{
		return itemList;
	}

	public void setItemList(ArrayList<CategoryItem> itemList)
	{
		this.itemList = itemList;
	}

	public String getCartItem()
	{
		return cartItem;
	}

	public void setCartItem(String cartItem)
	{
		this.cartItem = cartItem;
	}

	public void setUom(String uom[])
	{
		this.uom = uom;
	}

	public String[] getUom()
	{
		return uom;
	}

	public void setGcIds(String gcIds)
	{
		this.gcIds = gcIds;
	}

	public String getGcIds()
	{
		return gcIds;
	}

	public Member getMember()
	{
		return this.member;
	}

	public void setCartId(long cartId)
	{
		this.cartId = cartId;
	}

	public long getCartId()
	{
		return cartId;
	}

	public String getDragonpayURL()
	{
		return dragonpayURL;
	}

	public void setDragonpayURL(String dragonpayURL)
	{
		this.dragonpayURL = dragonpayURL;
	}

	public String getGlobalpayURL()
	{
		return globalpayURL;
	}

	public void setGlobalpayURL(String globalpayURL)
	{
		this.globalpayURL = globalpayURL;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(PaymentType paymentType)
	{
		this.paymentType = paymentType;
	}

	/**
	 * @return the paymentType
	 */
	public PaymentType getPaymentType()
	{
		return paymentType;
	}

	/**
	 * @param shippingType the shippingType to set
	 */
	public void setShippingType(ShippingType shippingType)
	{
		this.shippingType = shippingType;
	}

	/**
	 * @return the shippingType
	 */
	public ShippingType getShippingType()
	{
		return shippingType;
	}

	/**
	 * @return the shoppingCart
	 */
	public ShoppingCart getShoppingCart()
	{
		return shoppingCart;
	}

	/**
	 * @param shoppingCart the shoppingCart to set
	 */
	public void setShoppingCart(ShoppingCart shoppingCart)
	{
		this.shoppingCart = shoppingCart;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String prepareMrAirconCustomInstallationDetails(){					
		
		final String custom_title = request.getParameter("custom_title");
		final String custom_first_name = request.getParameter("custom_first_name");
		final String custom_last_name = request.getParameter("custom_last_name");
		final String custom_email_address = request.getParameter("custom_email_address");
		final String custom_tel_no = request.getParameter("custom_tel_no");
		final String custom_mobile_no = request.getParameter("custom_mobile_no");
		
		final String custom_company = request.getParameter("custom_company");
		final String custom_survey_date = request.getParameter("custom_survey_date");
		
		final String custom_survey_address = request.getParameter("custom_survey_address");
		final String custom_region = request.getParameter("custom_region");
		final String custom_city = request.getParameter("custom_city");
		final String custom_zip_code = request.getParameter("custom_zip_code");
		
		final String custom_billing_address = request.getParameter("custom_billing_address");
		final String custom_billing_region = request.getParameter("custom_billing_region");
		final String custom_billing_city = request.getParameter("custom_billing_city");
		final String custom_billing_zip_code = request.getParameter("custom_billing_zip_code");

		final String custom_nationality = request.getParameter("custom_nationality");
		final String custom_dob_month = request.getParameter("custom_dob_month");
		final String custom_dob_day = request.getParameter("custom_dob_day");
		final String custom_dob_year = request.getParameter("custom_dob_year");
		
		final String address_1 = custom_survey_address+", "+custom_city+", "+custom_region+", "+custom_zip_code;
		final String address_2 = custom_billing_address+", "+custom_billing_city+", "+custom_billing_region+", "+custom_billing_zip_code;
		
		final List<CategoryItem> catItems = new ArrayList<CategoryItem>();
		final String idAndQuantity = request.getParameter("idAndQuantity");
		if(idAndQuantity != null)
		{
			final String[] idAndQuantityArray = idAndQuantity.split(",");
			for(int i = 0; i < idAndQuantityArray.length; i++)
			{
				final Long id = Long.parseLong(idAndQuantityArray[i].split(":")[0]);
				final Integer qty = Integer.parseInt(idAndQuantityArray[i].split(":")[1]);
				CategoryItem catItem = new CategoryItem();
				catItem = categoryItemDelegate.find(id);
				catItem.setOrderQuantity(qty);
				catItem.setOtherDetails(qty + "");
				catItems.add(catItem);
			}

		}
		
		if(catItems != null && catItems.size() != 0)
		{
			final Member tempMember = new Member();

			tempMember.setReg_companyName(custom_company);
			tempMember.setInfo1(custom_title);
			tempMember.setInfo2("Survey date: "+custom_survey_date);
			tempMember.setInfo3(custom_nationality);
			tempMember.setInfo4("");
			tempMember.setFirstname(custom_first_name);			
			tempMember.setLastname(custom_last_name);
			tempMember.setAddress1(address_1);
			tempMember.setAddress2(address_2);
			tempMember.setMobile(custom_mobile_no);
			tempMember.setLandline(custom_tel_no);
			tempMember.setCity(custom_city);			
			tempMember.setZipcode(custom_zip_code);
			tempMember.setEmail(custom_email_address);
			
			Date dob = new Date(custom_dob_month+"/"+custom_dob_day+"/"+custom_dob_year);
			tempMember.setValue(dob+"");
			

			final PaymentType paymentType = PaymentType.searchPaymentType(request.getParameter("paymentMethod"));

			saveNoLogInCartTempOrder(catItems, tempMember, paymentType);

			sessionMap.remove("mrAirConNoLogInCartItems");

			sendMrAirconCustomInstallationDetails(catItems,tempMember,paymentType);

			return SUCCESS;
			
		}
		
				
		return SUCCESS;
	}
	
	public void sendMrAirconCustomInstallationDetails(List<CategoryItem> categoryItems,Member tempMember,PaymentType paymentType){
		
		String emailTitle;
		final String[] toMember = { "" };
		toMember[0] = tempMember.getEmail();

		
		String name = tempMember.getInfo1()+" "+tempMember.getFirstname()+" "+tempMember.getLastname();
		
		if(company == null)
		{
			company = companyDelegate.find(CompanyConstants.MR_AIRCON);
		}

		final String[] toComp = {"order@mraircon.ph", "jona@ivant.com", "isaac@ivant.com", "teo@ivant.com"};
		final String[] companyToComp = company.getEmail().split(",");

		//final String[] toAll = (String[]) ArrayUtils.addAll(toMember, toComp , companyToComp);
		final String[] toAllSet1 = ArrayUtils.addAll(toComp,companyToComp);
		final String[] toAll = ArrayUtils.addAll(toMember, toAllSet1);

		final StringBuffer content = new StringBuffer();

		emailTitle = "Survey Details ";

		content.append("<font face='sans-serif'>Dear ").append(name).append(",<br/><br/>")
			.append("Thank you for your inquiry! Our customer representative will contact you within")
			.append(" 1 business day to confirm your survey schedule. ")
			.append("Please see the customer information and order details you have submitted below:")
			.append("<br/><br/>");
		
		SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
		String dob = df.format(tempMember.getDateJoined());
		
		content.append("<table width='100%' style='border:2px solid black' cellspacing='0' cellpadding='0'>")
			.append("<tr style='background-color:#374E92; color:#ffffff; height:35px;'><th colspan='6' style='border-bottom:2px solid black; font-size: 120%'><strong>CUSTOMER INFORMATION</strong></th></tr>")
			.append("<tr>")
				.append("<td width='15%' style='padding-top: 10px; padding-left: 10px'>").append("Customer Name ").append("</td>")
				.append("<td width='5%' style='padding-top: 10px;'>").append(":").append("</td>")
				.append("<td width='30%' style='padding-top: 10px;'>").append(name).append("</td>")
				.append("<td width='15%' style='padding-top: 10px;'>").append("Email Adrress ").append("</td>")
				.append("<td width='5%' style='padding-top: 10px;'>").append(":").append("</td>")
				.append("<td width='30%' style='padding-top: 10px;'>").append(tempMember.getEmail()).append("</td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='padding-left: 10px'>").append("Telephone No. ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(tempMember.getLandline()).append("</td>")
				.append("<td>").append("Mobile No. ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(tempMember.getMobile()).append("</td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='padding-left: 10px'>").append("Date of Birth ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(dob).append("</td>")
				.append("<td>").append("Nationality ").append("</td>")
				.append("<td>").append(":").append("</td>")
				.append("<td>").append(tempMember.getInfo2()).append("</td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='padding-top: 20px; padding-left: 10px''>").append("Company ").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append(":").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append(tempMember.getInfo3()).append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append("Preffered Date").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append(":").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px'>").append(tempMember.getInfo4()).append("</td>")
			.append("</tr>")
			.append("<tr>").append("<td colspan='6'></td>").append("</tr>")
			.append("<tr>")
				.append("<td style='padding-top: 20px; padding-left: 10px; vertical-align:top;'>").append("Survey Address ").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px; vertical-align:top;'>").append(":").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px; vertical-align:top;'>").append(tempMember.getAddress1()).append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px; vertical-align:top;'>").append("Billing Address").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px; vertical-align:top;'>").append(":").append("</td>")
				.append("<td style='padding-top: 20px; padding-bottom: 10px; vertical-align:top;'>").append(tempMember.getAddress2()).append("</td>")
			.append("</tr>")
		.append("</table>");
		
		content.append("<br/><br/>");

		String paymentMethod = paymentType.equals(PaymentType.COD)?"CASH or CHEQUE": paymentType.getValue();
		
		content.append("<strong style='font-size: 120%'>PAYMENT METHOD : ").append(paymentMethod.toUpperCase()).append("</strong>");
		
		content.append("<br/><br/>");
		
		itemTotalPrice = 0;
		totalPrice = 0;

		int counter = 0;

		final DecimalFormat dc = new DecimalFormat("0.##");

		content.append("<table width='100%' style='border:2px solid black' cellspacing='0' cellpadding='0'><tr style='background-color:#374E92; color:#ffffff; height:35px; font-size: 120%'>")
			.append("<th style='border-bottom:2px solid black'><strong>ITEM DETAILS</strong></th>")
			.append("<th colspan='2' style='border-bottom:2px solid black' align='left'><strong>UNIT PRICE</strong></th>")
			.append("<th style='border-bottom:2px solid black' algin='right'><strong>QTY</strong></th>")
			.append("<th colspan='2' style='border-bottom:2px solid black'><strong>TOTAL</strong></th>")
			.append("</tr>");
	
		DecimalFormat fmt = new DecimalFormat("#,##0.00");
		
		for(final CategoryItem categoryItem : categoryItems)
		{
			Integer quantity = categoryItem.getOrderQuantity();
			if(quantity == null)
			{
				quantity = 1;
			}
			double discountedItemPice = categoryItem.getItemDetail().getPrice();
			double codItemPice = categoryItem.getItemDetail().getPrice();
			double creditItemPice = categoryItem.getItemDetail().getPrice();
			String codDiscountStr = categoryItem.getCategoryItemOtherFieldMap().get("COD Discount").getContent();
			double codDiscount = Double.parseDouble(StringUtils.isNotBlank(codDiscountStr)?codDiscountStr:"0");
			String creditDiscountStr = categoryItem.getCategoryItemOtherFieldMap().get("Credit Discount").getContent();
			double creditDiscount = Double.parseDouble(StringUtils.isNotBlank(creditDiscountStr)?creditDiscountStr:"0");
			for(CategoryItemPrice categoryItemPrice: categoryItem.getCategoryItemPrices()){
				if(categoryItemPrice.getCategoryItemPriceName().getName().equalsIgnoreCase("COD Price")){
					if(categoryItemPrice.getAmount()>0){
						codItemPice = categoryItemPrice.getAmount();
					}else if(codDiscount>0){
						codItemPice = discountedItemPice - (discountedItemPice * (codDiscount/100));
					}
				}else if(categoryItemPrice.getCategoryItemPriceName().getName().equalsIgnoreCase("Credit Price")){
					if(categoryItemPrice.getAmount()>0){
						creditItemPice = categoryItemPrice.getAmount();
					}else if(creditDiscount>0){
						creditItemPice = discountedItemPice - (discountedItemPice * (creditDiscount/100));
					}
				}
			}
			if(paymentType.equals(PaymentType.COD)){
				discountedItemPice = codItemPice;
			}else if(paymentType.equals(PaymentType.CREDIT_CARD)){
				discountedItemPice = creditItemPice;
			}
			itemTotalPrice = discountedItemPice * quantity;

			totalPrice += itemTotalPrice;
			
			
			String desc1 = "";
			String desc2 = "";
			String outdoorModelNo = "";
			String indoorModelNo = "";
			
			try{
				desc1 = categoryItem.getCategoryItemOtherFieldMap().get("Description Line 1").getContent();
				desc2 = categoryItem.getCategoryItemOtherFieldMap().get("Description Line 2").getContent();
				indoorModelNo = categoryItem.getCategoryItemOtherFieldMap().get("Indoor Model No.").getContent();
				outdoorModelNo = "(" + categoryItem.getCategoryItemOtherFieldMap().get("Outdoor Model No.").getContent() + ")";
				
			}catch(Exception e){
				System.err.println(e.getMessage());
			}
			
			
			content.append("<tr>")
				.append("<td style='padding: 5px 10px; color: #516EC7'>").append(categoryItem.getName()).append("</td>")
				.append("<td colspan='5'></td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td width='50%' style='padding: 0 10px;'>").append(desc1).append("</td>")
				.append("<td width='8%'>PHP</td>")
				.append("<td width='10%' align='right' style='padding-right: 20px'>").append(fmt.format((categoryItem.getItemDetail().getPrice()))).append("</td>")
				.append("<td width='10%' align='center'>").append(quantity).append("</td>")
				.append("<td width='8%' align='right'>PHP</td>")
				.append("<td width='10%' align='right' style='padding-right: 15px'>").append(fmt.format(itemTotalPrice)).append("</td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='padding: 0 10px;'>").append(desc2).append("</td>")
				.append("<td colspan='5'></td>")
			.append("</tr>")
			.append("<tr>")
				.append("<td style='border-bottom:2px solid black; padding-bottom: 10px;padding-left: 10px;'>").append(indoorModelNo)
				.append(outdoorModelNo)
				.append("</td>")
				.append("<td colspan='5' style='border-bottom:2px solid black'></td>")
			.append("</tr>");
			
						
		}
		
		content.append("<tr>")
			.append("<td style='padding-top: 5px; padding-left: 10px'>&#x2713; Free Delivery Within Metro Manila</td>")
			.append("<td colspan='3' style='color: #516EC7; font-size: 150%; padding-top: 5px;'><strong>GRAND TOTAL</strong></td>")
			.append("<td style='color: #516EC7; font-size: 150%; padding-top: 5px;'><strong>PHP</strong></td>")
			.append("<td style='color: #516EC7; font-size: 150%; padding-top: 5px; padding-right: 15px' align='right'><strong>").append(fmt.format(totalPrice)).append("</strong></td>")
		.append("</tr>")
		.append("<tr>")
			.append("<td style='padding-top: 5px; padding-bottom: 5px; padding-left: 10px'>&#x2713; For Deliveries Outside Metro Manila,<br>&nbsp;&nbsp;&nbsp;&nbsp;SHIPPING FEE IS NOT YET INCLUDED")
			.append("</td>")
		.append("</tr>")
		.append("</table>").append("<br>");
		
			
		content.append("<p align='justify'>")
			.append("Once Survey has been conducted, we will email you our Final Quotation and Payment Instructions.")
			.append("<br/><br/>");
		
		content.append("<strong>Terms and Conditions. </strong>")
			.append("For our complete policies regarding ordering, payment, delivery, installation, returns & exchanges, and")
			.append("cancellation, please visit www.mraircon.ph/FAQ.")			
			.append("<br/><br/>");
		
		content.append("For any other concerns, please feel free to reach us at (+632) 477-1111. 477-8906, or 477-8910.")
			.append("You may also email us at inquire@mraircon.ph.")
			.append("<br/><br/>");
		
		content.append("Thank you.").append("<br><br>").append("Sincerely,<br/><br/>").append("Mr. Aircon Philippines");

		EmailUtil.connectViaCompanySettings(company);
		
		String companyEmailUserName = company.getCompanySettings().getEmailUserName();
		
		EmailUtil.sendWithHTMLFormat(
				!StringUtils.isEmpty(companyEmailUserName)?companyEmailUserName:"noreply@webtogo.com.ph", 
						toAll, emailTitle + " from " + company.getNameEditable(), content.toString(), null);
		
	}

	public Long getCartOrderId() {
		return cartOrderId;
	}

	public void setCartOrderId(Long cartOrderId) {
		this.cartOrderId = cartOrderId;
	}

	public CartOrder getCartOrder() {
		return cartOrder;
	}

	public void setCartOrder(CartOrder cartOrder) {
		this.cartOrder = cartOrder;
	}
	
	public String payment(){
		if(CompanyConstants.IIEE_ORG_PHILS.equalsIgnoreCase(company.getName())){
			boolean fromOrder = request.getParameter("fromOrder")!=null;
			if(fromOrder){
				sessionMap.remove(CompanyConstants.IIEE_ORG_PHILS_TEMP_MEMBER);
			}
		
			if(sessionMap.get(CompanyConstants.IIEE_ORG_PHILS_TEMP_MEMBER)==null){
				String first_name = request.getParameter("first_name");
				String last_name = request.getParameter("last_name");
				String middle_name = request.getParameter("middle_name");
				String house_number = request.getParameter("house_number");
				String street = request.getParameter("street");
				String barangay = request.getParameter("barangay");			
				String city = request.getParameter("city");
				String province = request.getParameter("province");
				String country = request.getParameter("country");
				String zipcode = request.getParameter("zipcode");
				String phone_no = request.getParameter("phone_no");
				String mobile_no = request.getParameter("mobile_no");
				String email = request.getParameter("email");
				String secondary_email = request.getParameter("secondary_email");
				String licence_number = request.getParameter("licence_number");
				String birth_date = request.getParameter("birth_date");
				Boolean isMemberShip =  request.getParameter("isMemberShip")!=null;
				
				String [] requiredField = {first_name,last_name,street
						,city,province,country,mobile_no ,birth_date};
				if(isMemberShip){
					requiredField = new String[] {first_name,last_name,street
						,city,province,country,mobile_no, birth_date,
						licence_number};
				}
				
				for (int i = 0; i < requiredField.length; i++) {
					if(requiredField[i] == null || StringUtils.isBlank(requiredField[i])){
						return Action.ERROR;
					}
				}
				Member tempMember = new Member();

				String address_1 = house_number+" "+ 
						street;
				String address_2 = barangay;
				
				tempMember.setFirstname(first_name);
				tempMember.setLastname(last_name);
				tempMember.setMiddlename(middle_name);
				tempMember.setAddress1(address_1);
				tempMember.setAddress2(address_2);
				tempMember.setLandline(phone_no);
				tempMember.setMobile(mobile_no);
				tempMember.setCity(city);			
				tempMember.setZipcode(zipcode);
				tempMember.setEmail(email);				
				tempMember.setInfo1("Licence Number: "
						+ licence_number + ", Birth Date: "
						+ birth_date + ", Seconday Email: "
						+ secondary_email);
				tempMember.setInfo2(country);			
				tempMember.setProvince(province);
				sessionMap.put(CompanyConstants.IIEE_ORG_PHILS_TEMP_MEMBER, tempMember);
			}
		}
		
		return Action.SUCCESS;
	}
	
	public String wendysAssignPoints(Company company, CartOrder order, double totalPrice) throws ParseException{
		
		System.out.println("***ENTERED ASSIGNING POINTS***");
		
		GroupDelegate groupDelegate = GroupDelegate.getInstance();
		CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
		
		List<CategoryItem> categoryItemList = new ArrayList<CategoryItem>();
		
		Group groupPoints = groupDelegate.find(company, "Points");
		for(int i = 0 ; i < groupPoints.getCategories().size() ; i++){
			Category categoryPoints = groupPoints.getCategories().get(i);
			for(int j = 0 ; j < categoryPoints.getItems().size() ; j++){
				CategoryItem itemPoints = categoryPoints.getItems().get(j);
				categoryItemList.add(itemPoints);
			}
		}
		
		Category category = categoryDelegate.find(company, "Earned Points", groupDelegate.find(company, "Earned Points"));
		
		if(order.getPaymentType().equals(PaymentType.COD)){
		
		CategoryItem newItem = new CategoryItem();
		newItem.setParent(category);
		newItem.setParentGroup(groupDelegate.find(company, "Earned Points"));
		newItem.setName(order.getMember().getFullName() + "-" + order.getId());
		newItem.setCompany(company);
		newItem.setSortOrder(0);
		newItem.setDisabled(false);
		newItem.setFeatured(false);
		newItem.setBestSeller(false);
		newItem.setIsOutOfStock(false);
		
		
		long id = categoryItemDelegate.insert(newItem);

		newItem = categoryItemDelegate.find(company, id);

		for(int i = 0 ; i < categoryItemList.size() ; i++){
			CategoryItem categoryItem =  categoryItemList.get(i);
			
			String strValidFrom = this.categoryItemOtherFieldDelegate.findByOtherFieldName(company, categoryItem, "Valid From").getContent();
			String strValidUntil = this.categoryItemOtherFieldDelegate.findByOtherFieldName(company, categoryItem, "Valid Until").getContent();
			String amountFrom = this.categoryItemOtherFieldDelegate.findByOtherFieldName(company, categoryItem, "Amount From").getContent();
			String amountTo = this.categoryItemOtherFieldDelegate.findByOtherFieldName(company, categoryItem, "Amount To").getContent();
			String transaction = this.categoryItemOtherFieldDelegate.findByOtherFieldName(company, categoryItem, "Transaction").getContent();
			String point = categoryItem.getSku();
			double dAmountFrom = Double.parseDouble(amountFrom);
			double dAmountTo = Double.parseDouble(amountTo);
			boolean isDelivery = false;
			boolean isPickUp = false;
			
			
			Date dateNow = new Date();
			//Date validFrom = new Date(strValidFrom);
			//Date validUntil = new Date(strValidUntil);
			
			DateFormat formatter ; 	    
      Calendar c1 = Calendar.getInstance();
      Calendar c2 = Calendar.getInstance();
      formatter = new SimpleDateFormat("MM-dd-yyyy");
      c1.setTime(formatter.parse(strValidFrom));
      //this line of code is use to add days on the range of report date
      c1.add(Calendar.DATE, 0);
      c2.setTime(formatter.parse(strValidUntil));
      c2.add(Calendar.DATE, 1);
      strValidFrom = formatter.format(c1.getTime());
      strValidUntil = formatter.format(c2.getTime());
      Date validFrom = (Date)formatter.parse(strValidFrom);
      Date validUntil = (Date)formatter.parse(strValidUntil); 
			
			if(order.getShippingType().equals(ShippingType.DELIVERY) && (transaction.equals("Delivery") || transaction.equals("All"))){
				isDelivery = true;
			}else if(order.getShippingType().equals(ShippingType.PICKUP) && (transaction.equals("Dine In / Pick Up") || transaction.equals("All"))){
				isPickUp = true;
			}
			
			if((dateNow.compareTo(validFrom) > 0) && (dateNow.compareTo(validUntil) < 0)){
				if(totalPrice >= dAmountFrom && totalPrice <= dAmountTo){
					List<OtherField> otherFields = newItem.getParent().getParentGroup().getOtherFields();
					
					for(int j = 0; j < otherFields.size(); j++){
						OtherField of = otherFields.get(j);
						CategoryItemOtherField newOtherField = new CategoryItemOtherField();
						
						if(of.getName().equals("Member ID")){
							newOtherField.setContent(order.getMember().getId().toString());
						}else if(of.getName().equals("Order ID")){
							newOtherField.setContent(order.getId().toString());
						}else if(of.getName().equals("Receipt Date")){
							newOtherField.setContent(formatter.format(dateNow));
						}else if(of.getName().equals("Order Type")){
							if(isDelivery){
								newOtherField.setContent("Delivery");
							}else if(isPickUp){
								newOtherField.setContent("Dine In / Pick Up");
							}
						}else if(of.getName().equals("Expiry Date")){
							newOtherField.setContent(strValidUntil);
						}else if(of.getName().equals("Earned Points")){
							newOtherField.setContent(point);
						}else if(of.getName().equals("Is Redeemed")){
							newOtherField.setContent("false");
						}else if(of.getName().equals("Is Expired")){
							newOtherField.setContent("false");
						}else if(of.getName().equals("Is Available")){
							newOtherField.setContent("true");
						}else if(of.getName().equals("Remaining Points")){
							newOtherField.setContent(point);
						}
						
						newOtherField.setCategoryItem(newItem);
						newOtherField.setOtherField(of);
						newOtherField.setCompany(company);
						newOtherField.setIsValid(Boolean.TRUE);
						
						categoryItemOtherFieldDelegate.insert(newOtherField);
				
						
					}
				}
			}
			
		}
		
		}
		
		return Action.SUCCESS;
	}
	
	public String testcheckout(){
		Long cid = Long.parseLong("118049");
		CartOrder cartOrder = cartOrderDelegate.find(cid);
		try {
			sendEmail(cartOrder.getMember(), cartOrder);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
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
