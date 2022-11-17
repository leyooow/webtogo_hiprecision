package com.ivant.cart.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemPriceDelegate;
import com.ivant.cms.delegate.CategoryItemPriceNameDelegate;
import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemFileDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.OSSShippingAreaDelegate;
import com.ivant.cms.delegate.OSSShippingLocationDelegate;
import com.ivant.cms.delegate.OSSShippingRateDelegate;
import com.ivant.cms.delegate.OrderItemFileDelegate;
import com.ivant.cms.delegate.PreOrderDelegate;
import com.ivant.cms.delegate.PreOrderItemDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.WishlistDelegate;
import com.ivant.cms.email.template.HBCSuccessfulPaymentTemplate;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.OSSShippingArea;
import com.ivant.cms.entity.OSSShippingLocation;
import com.ivant.cms.entity.OSSShippingRate;
import com.ivant.cms.entity.OrderItemFile;
import com.ivant.cms.entity.PreOrder;
import com.ivant.cms.entity.PreOrderItem;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.CategoryItemUtil;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.InventoryUtil;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.Action;

/**
 * Action for ordered shopping cart items create, update, and delete.
 *
 * @author Mark Kenneth M. Ra?osa
 */
public class OrderAction
		extends AbstractBaseAction
{

	private final Logger logger = LoggerFactory.getLogger(OrderAction.class);
	private static final long serialVersionUID = -7353617770904058249L;
	private final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private final FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final CategoryItemPriceDelegate categoryItemPriceDelegate = CategoryItemPriceDelegate.getInstance();
	private final CategoryItemPriceNameDelegate categoryItemPriceNameDelegate = CategoryItemPriceNameDelegate.getInstance();
	private final CompanySettingsDelegate companySettingsDelegate = CompanySettingsDelegate.getInstance();
	private static final BrandDelegate brandDelegate = BrandDelegate.getInstance();

	/** Object responsible for shopping cart CRUD tasks */
	private final ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();

	/** Object responsible for shopping cart items CRUD tasks */
	private final ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();

	/** Object responsible for order CRUD tasks */
	private final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();

	/** Object responsible for ordered items CRUD tasks */
	private final CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();

	/** Object responsible for member shipping info CRUD tasks */
	private final MemberShippingInfoDelegate memberShippingInfoDelegate = MemberShippingInfoDelegate.getInstance();

	private final WishlistDelegate wishlistDelegate = WishlistDelegate.getInstance();

	private final PreOrderDelegate preOrderDelegate = PreOrderDelegate.getInstance();

	private final PreOrderItemDelegate preOrderItemDelegate = PreOrderItemDelegate.getInstance();

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
	private List<ShoppingCartItem> cartItemList;

	/** List of orders by the member of the company, can be 0 or more */
	private List<CartOrder> orderList;

	/** List of ordered items, can be 0 or more */
	private List<CartOrderItem> orderItemList;

	private PreOrder preOrder;

	private List<PreOrderItem> preOrderItemList;

	CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private final ShoppingCartItemDelegate cartItemDelegate = ShoppingCartItemDelegate.getInstance();
	private final ShoppingCartDelegate cartDelegate = ShoppingCartDelegate.getInstance();
	private final ItemFileDelegate itemFileDelegate = ItemFileDelegate.getInstance();
	private final OrderItemFileDelegate orderItemFileDelegate = OrderItemFileDelegate.getInstance();
	private final OSSShippingAreaDelegate areaDelegate = OSSShippingAreaDelegate.getInstance();
	private final OSSShippingLocationDelegate locationDelegate = OSSShippingLocationDelegate.getInstance();
	private final OSSShippingRateDelegate rateDelegate = OSSShippingRateDelegate.getInstance();
	private final HBCPaymentInput paymentInput = new HBCPaymentInput();
	// look-ahead count of orders
	private int orderCount;
	private String comments;
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private ArrayList<CategoryItem> catItem = new ArrayList<CategoryItem>();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private List<Long> fileID;
	private List<Long> orderItemFileID;
	private List<Boolean> expired;
	private String emailBody;
	private String hashValue;
	private Long[] cartItem;
	private String approvalcode;
	private String memberInformation;
	private String stringMemberInformation;
	private String stringShippingInfo;
	private String transactionNumber;
	private String transactionReference;
	private String uom[];
	private InputStream inputStream;
	private List<OSSShippingArea> ossShippingAreas;
	private List<OSSShippingLocation> ossShippingLocations;
	private List<OSSShippingRate> rates;
	private OSSShippingLocation location;
	private String areas = "";
	private String locations = "";
	private String tempArea = "";
	private Double rate1, rate2, rate3, rate4, rate5, rate6, rate7;
	private Double rate8, rate9, rate10, rate11, rate12, rate13, rate14;
	private static final String ATTACHMENT_FOLDER = "message_attachments";
	private String notificationMessage;
	private String voucherFileName;

	@Override
	public void prepare() throws Exception
	{
		Long cartOrderID;
		// get member shopping cart information
		initShoppingCart();

		// load all orders by member
		initOrderList();

		// load all orders with paging
		loadOrdersWithPaging();
		final List<Category> categories = categoryDelegate.findAllPublished(company, null, null, true, Order.asc("sortOrder")).getList();
		request.setAttribute("categoryMenu", categories);
		final Map<String, Object> featuredPages = new HashMap<String, Object>();

		final List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();

		for(final MultiPage mp : featuredMultiPage)
		{
			if(!mp.getHidden())
			{
				featuredPages.put(mp.getName(), mp);
			}
		}

		request.setAttribute("featuredPages", featuredPages);
		try
		{
			if(isNull(request.getParameter("Ref")))
			{
				cartOrderID = Long.parseLong(request.getParameter("cart_order_id"));
			}
			else
			{
				cartOrderID = Long.parseLong(request.getParameter("Ref"));
			}

			cartOrder = cartOrderDelegate.find(cartOrderID);
			this.setOrderCount(cartOrder.getId().intValue());
			if(!isNull(request.getParameter("Ref")))
			{
				cartOrder.setStatus(OrderStatus.CANCELLED);
				for(final CartOrderItem x : cartOrder.getItems())
				{
					x.setStatus("CANCELLED");
				}
				cartOrderDelegate.update(cartOrder);
			}
			logger.debug("Total Price : " + cartOrder.getTotalPrice());

			orderItemList = cartOrderItemDelegate.findAll(cartOrder).getList();

			getFileID();

			if(company.getCompanySettings().getWillExpire())
			{
				updateExpiryDate(orderItemFileID);
			}
			// System.out.println("is download limited?: "+company.getCompanySettings().getWillExpire());
			// System.out.println("number of downloads: "+company.getCompanySettings().getExpiryDate());
			request.setAttribute("fileID", fileID);
			request.setAttribute("orderItemFileID", orderItemFileID);
			request.setAttribute("expired", expired);
			if(request.getParameter("notificationMessage") != null)
			{
				this.setNotificationMessage(request.getParameter("notificationMessage"));
			}
		}
		catch(final Exception e)
		{
			logger.debug("No cart order id specified");
		}

		loadAllRootCategories();
		getCartSize();

		// populate server URL to be redirected to
		initHttpServerUrl();

		loadFeaturedPages(company.getCompanySettings().getMaxFeaturedPages());
		loadFeaturedSinglePages(company.getCompanySettings().getMaxFeaturedSinglePages());

		prepareMenu();

		paymentInput.setCancelUrl(httpServer + paymentInput.getCancelUrl());
		paymentInput.setFailUrl(httpServer + paymentInput.getFailUrl());
		paymentInput.setSuccessUrl(httpServer + paymentInput.getSuccessUrl());
		request.setAttribute("paymentInput", paymentInput);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception
	{
		// validate current user, must not be empty
		if(isNull(member))
		{
			return INPUT;
		}

		return super.execute();
	}

	public String computeEcommerceShipping()
	{
		if(getShoppingCart() != null)
		{
			session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
			session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
		}

		if(member != null)
		{
			preOrder = preOrderDelegate.find(company, member);

			if(preOrder != null)
			{
				session.put("preordercount", getPreOrder().getTotalPreOrderQuantity());
				session.put("preordertotprice", getPreOrder().getTotalPreOrderPrice());
			}
			else
			{
				session.put("preordercount", "0");
				session.put("preordertotprice", "0");
			}

			session.put("wishlistcount", wishlistDelegate.getMemberWishlistCount(company, member));
		}

		// details for shipping information
		setShippingInfo();

		// to populate areas and location
		setShippingAreas();

		if(request.getParameter("city") != null)
		{
			location = locationDelegate.find(company, request.getParameter("city"));
			rates = rateDelegate.findAll(company, location, Order.asc("weight"));

			rate1 = rates.get(0).getRate();
			rate2 = rates.get(1).getRate();
			rate3 = rates.get(2).getRate();
			rate4 = rates.get(3).getRate();
			rate5 = rates.get(4).getRate();
			rate6 = rates.get(5).getRate();
			rate7 = rates.get(6).getRate();
			rate8 = rates.get(7).getRate();
			rate9 = rates.get(8).getRate();
			rate10 = rates.get(9).getRate();
			rate11 = rates.get(10).getRate();
			rate12 = rates.get(11).getRate();
			rate13 = rates.get(12).getRate();
			rate14 = rates.get(13).getRate();

			// to populate shipping price
			for(int i = 0; i < cartItemList.size(); i++)
			{
				int weight = 0;
				if(cartItemList.get(i).getItemDetail().getWeight() != null)
				{
					weight = (int) Math.floor(cartItemList.get(i).getItemDetail().getWeight());
				}

				switch(weight)
				{
					case 1:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate1);
						break;
					case 2:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate2);
						break;
					case 3:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate3);
						break;
					case 4:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate4);
						break;
					case 5:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate5);
						break;
					case 6:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate6);
						break;
					case 16:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate7);
						break;
					case 27:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate8);
						break;
					case 37:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate9);
						break;
					case 52:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate10);
						break;
					case 111:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate11);
						break;
					case 117:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate12);
						break;
					case 264:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate13);
						break;
					case 626:
						cartItemList.get(i).getItemDetail().setShippingPrice(rate14);
						break;
				}

				cartItemDelegate.update(cartItemList.get(i));
			}

			try
			{
				final ObjectList<ShoppingCartItem> tempCartItems = shoppingCartItemDelegate.findAllByPrice(shoppingCart);
				cartItemList = tempCartItems.getList();
			}
			catch(final Exception e)
			{
				logger.debug("No cart items retrieved.");
			}

			Double shippingPrice = 0.0;
			int quantity = 0;

			for(int i = 0; i < cartItemList.size(); i++)
			{
				shippingPrice = shippingPrice + (cartItemList.get(i).getItemDetail().getShippingPrice() * cartItemList.get(i).getQuantity());
				quantity = quantity + cartItemList.get(i).getQuantity();
			}

			if(quantity > 1)
			{
				shippingPrice = shippingPrice - cartItemList.get(0).getItemDetail().getShippingPrice();
				shippingPrice = shippingPrice * 0.70 + cartItemList.get(0).getItemDetail().getShippingPrice();
			}

			shippingPrice = 0.0;

			session.put("shippingPrice", shippingPrice);
		}

		preOrder = preOrderDelegate.find(company, member);

		preOrderItemList = preOrderItemDelegate.findAll(company, preOrder);

		return SUCCESS;
	}

	public String computeDrugAsiaShipping()
	{
		if(getShoppingCart() != null)
		{
			session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
			session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
		}

		// details for shipping information
		setShippingInfo();

		// to populate areas and location
		setShippingAreas();

		if(request.getParameter("city") != null)
		{
			final Double shippingPrice = Double.valueOf(request.getParameter("shippingCostAmount"));

			session.put("shippingPrice", shippingPrice);
			session.put("shippingCostAmount", shippingPrice);
		}

		if(company.getName().equalsIgnoreCase("drugasia"))
		{
			member.setVerified(true);
			memberDelegate.update(member);
			if(request.getParameter("prescription") != null)
			{
				request.setAttribute("prescription", "prescription");
			}

			final MultiPartRequestWrapper r = (MultiPartRequestWrapper) request;
			final File[] file = r.getFiles("1h|file_upload");
			final String[] filename = r.getFileNames("1h|file_upload");
			File dest = new File("");

			if((file != null && file.length > 0) && (filename != null && filename.length > 0))
			{
				final String path = getRealPath() + ATTACHMENT_FOLDER + File.separator;
				final File uploadedFileDestination = new File(path);

				if(!uploadedFileDestination.exists())
				{
					uploadedFileDestination.mkdirs();
				}

				dest = new File(path + filename[0]);
				FileUtil.copyFile(file[0], dest);
				session.put("filepath", dest.getAbsolutePath());
				session.put("filename", filename[0]);
				// System.out.println("dest.getAbsolutePath() = " + dest.getAbsolutePath());
			}
		}

		return SUCCESS;
	}

	public String computeGurkkaShipping() throws Exception
	{
		try
		{
			session.put("name", request.getParameter("name"));
			session.put("address1", request.getParameter("address1"));
			// session.put("address2", request.getParameter("address2"));
			session.put("zipcode", request.getParameter("zipcode"));
			session.put("contactnumber", request.getParameter("contactnumber"));
			session.put("email", request.getParameter("email"));
			session.put("paymenttype", request.getParameter("paymenttype"));
			session.put("receiver", request.getParameter("receiver"));
			session.put("deliverytype", request.getParameter("deliverytype"));

			// details for shipping information
			setShippingInfo();

			// to populate areas and location
			setShippingAreas();

			Double shippingPrice = 0.00;
			if(request.getParameter("deliverytype").equalsIgnoreCase("p"))
			{
				final String prov = StringUtils.trimToEmpty(member.getProvince());
				// System.out.println("prov " + prov);
				if(prov != "" && prov != null && prov.length() > 1)
				{
					if(prov.equalsIgnoreCase("ncr"))
					{
						shippingPrice = 0.00d;
					}

					if(prov.equalsIgnoreCase("luzon"))
					{
						shippingPrice = 50.00d;
					}

					if(prov.equalsIgnoreCase("visayas"))
					{
						shippingPrice = 65.00d;
					}
					if(prov.equalsIgnoreCase("mindanao"))
					{
						shippingPrice = 75.00d;
					}
				}
			}

			session.put("shippingPrice", shippingPrice);
			session.put("shippingCostAmount", shippingPrice);

			
			final ShoppingCart cart = shoppingCartDelegate.find(getShoppingCart().getId());
			setShoppingCart(cart);
			
			if(getShoppingCart() != null)
			{}
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String claimGurkkaReward()
	{
		session.put("name", request.getParameter("name"));
		session.put("address1", request.getParameter("address1"));
		// session.put("address2", request.getParameter("address2"));
		session.put("zipcode", request.getParameter("zipcode"));
		session.put("contactnumber", request.getParameter("contactnumber"));
		session.put("email", request.getParameter("email"));
		session.put("paymenttype", request.getParameter("paymenttype"));
		session.put("receiver", request.getParameter("receiver"));
		session.put("deliverytype", request.getParameter("deliverytype"));

		// details for shipping information
		setShippingInfo();

		// to populate areas and location
		setShippingAreas();

		if(member.getProvince() != null)
		{
			final String prov = StringUtils.trimToEmpty(member.getProvince());
			// System.out.println("prov " + prov);
			Double shippingPrice = 0d;
			if(prov != "" && prov != null && prov.length() > 1)
			{
				if(prov.equalsIgnoreCase("ncr"))
				{
					shippingPrice = 0.00d;
				}

				if(prov.equalsIgnoreCase("luzon"))
				{
					shippingPrice = 50.00d;
				}

				if(prov.equalsIgnoreCase("visayas"))
				{
					shippingPrice = 65.00d;
				}
				if(prov.equalsIgnoreCase("mindanao"))
				{
					shippingPrice = 75.00d;
				}
			}

			session.put("shippingPrice", shippingPrice);
			session.put("shippingCostAmount", shippingPrice);
		}

		final String itemIDString = StringUtils.trimToNull(request.getParameter("selectedItem"));
		if(itemIDString != null)
		{
			if(getShoppingCart() == null)
			{
				return INPUT;
			}
			final HttpSession httpSession = request.getSession();
			final ShoppingCart sc = shoppingCartDelegate.find(getShoppingCart().getId());
			final Long id = Long.valueOf(itemIDString);
			final CategoryItem item = categoryItemDelegate.find(id);
			final ShoppingCartItem rewardItem = new ShoppingCartItem();
			final ItemDetail itemDetail = item.getItemDetail();
			final String name = itemDetail.getName();

			final Category memberTypeCategory = categoryDelegate.find(item.getParent().getId());
			final MemberType previousMemberType = (MemberType) httpSession.getAttribute("previousMemberType");
			if(memberTypeCategory != null)
			{}

			itemDetail.setName(name + " (Reward)");
			itemDetail.setPrice(0.00);
			itemDetail.setRealID(id);

			rewardItem.setCompany(company);
			rewardItem.setItemDetail(itemDetail);
			rewardItem.setQuantity(1);
			rewardItem.setShoppingCart(sc);

			cartItemList = new ArrayList<ShoppingCartItem>();
			cartItemList.add(rewardItem);

			session.put("rewardCartItem", rewardItem);
			session.put("rewardItem", item);
		}

		return SUCCESS;
	}

	public String claimGurkkaVoucher()
	{
		// System.out.println("CLAIMING" + member.getFullName());

		if(member == null)
		{
			return LOGIN;
		}

		final String orderid = request.getParameter("orderid");

		if(StringUtils.isNotEmpty(orderid))
		{
			final Long id = Long.parseLong(orderid);
			cartOrder = cartOrderDelegate.find(id);

			if(cartOrder.getMember() != member)
			{
				setNotificationMessage("Sorry you can't claim this voucher");
				return ERROR;
			}
		}
		voucherFileName = "Voucher_" + cartOrder.getId() + ".pdf";

		final String pdfFile = servletContext.getRealPath("voucherpdf") + "/" + voucherFileName;

		try
		{
			inputStream = new FileInputStream(new File(pdfFile));

		}
		catch(final Exception e)
		{
			setNotificationMessage("Sorry the voucher were not printed due to system error. " + "Please try again later or contact us.");

			return ERROR;
		}

		return SUCCESS;
	}

	public String computeMDTShipping()
	{
		if(getShoppingCart() != null)
		{
			session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
			session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
		}

		// details for shipping information
		setShippingInfo();

		// to populate areas and location
		setShippingAreas();

		if(request.getParameter("city") != null)
		{
			final Double shippingPrice = 200.0;

			session.put("shippingPrice", shippingPrice);
		}

		return SUCCESS;
	}

	public String computeOnlineDepotShipping()
	{
		if(getShoppingCart() != null)
		{
			session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
			session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
		}

		// details for shipping information
		setShippingInfo();

		// to populate areas and location
		setShippingAreas();

		final String province = request.getParameter("province");
		Double shippingPrice = 0d;

		if(province != null)
		{
			if(!province.equals("Metro Manila"))
			{
				shippingPrice = 150.0;
			}
			else
			{
				shippingPrice = 0.0;
			}

			session.put("shippingPrice", shippingPrice);
			session.put("shippingCostAmount", shippingPrice);
		}

		return SUCCESS;
	}

	public String computeGiftcardShipping()
	{
		// details for shipping information
		session.put("shippingtype", request.getParameter("shippingtype"));
		session.put("name", request.getParameter("name"));
		session.put("address1", request.getParameter("address1"));
		session.put("address2", request.getParameter("address2"));
		session.put("city", request.getParameter("city"));
		session.put("zipcode", request.getParameter("zipcode"));
		session.put("contactnumber", request.getParameter("contactnumber"));
		session.put("email", request.getParameter("email"));
		session.put("paymenttype", request.getParameter("paymenttype"));

		Double totalPrice = 0.0;
		Double shippingPrice = 0.0;

		for(int i = 0; i < cartItemList.size(); i++)
		{
			totalPrice = totalPrice + (cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity());
		}

		if(totalPrice < 8000)
		{
			shippingPrice = 100 + (totalPrice * 0.02);
		}
		else if(totalPrice > 7999 && totalPrice < 18000)
		{
			shippingPrice = 120 + (totalPrice * 0.02);
		}

		session.put("shippingPrice", shippingPrice);

		return SUCCESS;
	}

	public void setShippingInfo()
	{
		session.put("deliverydate", request.getParameter("deliveryDate"));
		session.put("name", request.getParameter("name"));
		session.put("address1", request.getParameter("address1"));
		session.put("province", request.getParameter("province"));
		session.put("city", request.getParameter("city"));
		session.put("mobilenumber", request.getParameter("mobilenumber"));
		session.put("phonenumber", request.getParameter("phonenumber"));
		session.put("email", request.getParameter("email"));

		if(company.getId() != CompanyConstants.GURKKA || company.getId() != CompanyConstants.GURKKA_TEST)
		{
			session.put("address2", request.getParameter("address2"));
			session.put("zipcode", request.getParameter("zipcode"));
		}
	}

	public void setShippingAreas()
	{
		ossShippingAreas = areaDelegate.findAll(company);

		for(int i = 0; i < ossShippingAreas.size(); i++)
		{
			areas = areas + ossShippingAreas.get(i).getAreaName();

			if(i != ossShippingAreas.size() - 1)
			{
				areas = areas + "+";
			}
		}

		ossShippingLocations = locationDelegate.findAll(company);

		for(int i = 0; i < ossShippingLocations.size(); i++)
		{
			if(!tempArea.equals(ossShippingLocations.get(i).getOssShippingArea().getAreaName()) && i != 0)
			{
				locations = locations + "+";
			}

			locations = locations + ossShippingLocations.get(i).getLocationName() + "--";
			tempArea = ossShippingLocations.get(i).getOssShippingArea().getAreaName();
		}

		request.setAttribute("areas", areas);
		request.setAttribute("locations", locations);
	}

	private void loadOrdersWithPaging()
	{
		try
		{
			int itemSize = 0;
			final int max = 20;
			final int pageNumber = getPageNumber();

			// get PageById

			final ObjectList<CartOrder> orderListPaging = cartOrderDelegate.findAllWithPaging(company, member, max, pageNumber - 1, Order.desc("id"));
			itemSize = orderListPaging.getTotal();
			// System.out.println("itemsSize: "+ itemSize);

			request.setAttribute("orderListPaging", orderListPaging.getList());
			if(itemSize > max)
			{
				final PagingUtil pagingUtilDesc = new PagingUtil(itemSize, max, pageNumber, 5);
				request.setAttribute("pagingUtilDesc", pagingUtilDesc);
				// System.out.println("itemSize > max");
			}
			// System.out.println("load orders with paging success");
		}
		catch(final Exception e)
		{

		}
	}

	private int getPageNumber()
	{
		int pageNumber = 1;

		try
		{
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			logger.debug("page number is " + pageNumber);
		}
		catch(final Exception e)
		{
			logger.debug("setting page to 1");
		}

		return pageNumber;
	}

	public void getFileID()
	{
		fileID = new ArrayList<Long>();
		orderItemFileID = new ArrayList<Long>();
		expired = new ArrayList<Boolean>();
		for(final CartOrderItem item : orderItemList)
		{
			fileID.add(orderItemFileDelegate.findItemFileID(company, item.getId()).getItemFileID());
			orderItemFileID.add(orderItemFileDelegate.findItemFileID(company, item.getId()).getId());
			expired.add(new Date().after(orderItemFileDelegate.findItemFileID(company, item.getId()).getExpiryDate()));
		}
	}

	public void updateExpiryDate(List<Long> orderItemFileID)
	{
		for(final Long order : orderItemFileID)
		{
			OrderItemFile orderItemFile = null;
			orderItemFile = orderItemFileDelegate.find(order);
			// System.out.println(new DateTime(orderItemFile.getCreatedOn()).plusDays(company.getCompanySettings().getExpiryDate()).toDate());
			orderItemFile.setExpiryDate(new DateTime(orderItemFile.getCreatedOn()).plusDays(company.getCompanySettings().getExpiryDate()).toDate());
			orderItemFileDelegate.update(orderItemFile);
		}
	}

	public String saveComments()
	{
		final List<Category> categories = categoryDelegate.findAllPublished(company, null, null, true, Order.asc("sortOrder")).getList();
		request.setAttribute("categoryMenu", categories);
		if(isNull(member))
		{
			return INPUT;
		}
		this.comments = request.getParameter("comments").toString();
		request.setAttribute("name", request.getParameter("shippingName"));
		request.setAttribute("phoneNumber", request.getParameter("phoneNumber"));
		request.setAttribute("email", request.getParameter("email"));
		request.setAttribute("totalPrice", request.getParameter("totalPrice"));

		// update shippinginfo
		if(!company.getName().equalsIgnoreCase("pmc"))
		{
			updateShippingInfo();
		}
		if(this.comments != null)
		{

		}
		// //////////////////
		final ObjectList<ShoppingCartItem> tempCartItems = cartItemDelegate.findAll(shoppingCart);
		cartItemList = tempCartItems.getList();

		for(final ShoppingCartItem item : cartItemList)
		{
			catItem.add(categoryItemDelegate.find(item.getItemDetail().getRealID()));
		}

		session.put("shippingName", request.getParameter("shippingName"));
		session.put("phoneNumber", request.getParameter("phoneNumber"));
		session.put("email", request.getParameter("email"));
		shoppingCart.setItems(tempCartItems.getList());
		// /////////////////
		session.put("comments", this.comments);

		if(company.getName().equals("ramgo"))
		{
			sendEmailToMember(member);
			sendEmailToCompanyAccount(member);
		}

		return SUCCESS;
	}

	private void setEmailBody()
	{
		emailBody = "<table>";
		emailBody += "<tr><td align='center'><strong>Quantity</strong></td><td align='center'><strong>Item</strong></td><td align='center'><strong>Price</strong></td><td align='center'><strong>Subtotal</strong></td></tr>";

		for(int i = 0; i < cartItemList.size(); i++)
		{
			emailBody += "<tr>";
			emailBody += "<td valign='top' align='center' style='padding:0px 10px;'>" + cartItemList.get(i).getQuantity() + "</td>";
			emailBody += "<td valign='top' style='padding:0px 10px;'>" + catItem.get(i).getParent().getName() + ", " + catItem.get(i).getBrand() + " - "
					+ cartItemList.get(i).getItemDetail().getName() + "</td>";
			emailBody += "<td valign='top' align='right' style='padding:0px 10px;'>" + cartItemList.get(i).getItemDetail().getPrice() + "</td>";
			emailBody += "<td valign='top' align='right' style='padding:0px 10px;'>" + cartItemList.get(i).getItemDetail().getPrice() * cartItemList.get(i).getQuantity() + "</td>";
			emailBody += "</tr>";
		}
		emailBody += "<tr><td colspan='4' align='right' style='padding:0px 10px;'><strong>Total Price: " + shoppingCart.getTotalPrice() + "</strong></td></tr>";
		emailBody += "</table>";
	}

	public String sendEmailToMember(Member member)
	{
		String emailTitle;

		setEmailBody();

		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();
			content.append("Hi " + member.getFirstname() + " " + member.getLastname());

			emailTitle = "Ordered Items - " + member.getFullName();
			content.append(",<br><br>" + "Item details:<br>");

			content.append(emailBody);
			content.append("<br>Thank you.<br><br>All the Best, <br><br>");
			content.append("The " + company.getNameEditable() + " Team");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	private void sendEmailToMemberWithTemplate(Member member)
	{

		if(member != null)
		{

			String emailTitle = "Successful Order";

			if(cartOrder != null && cartItemList.size() > 0 && catItem != null)
			{

				final HBCSuccessfulPaymentTemplate hbcEmail = new HBCSuccessfulPaymentTemplate(company, member, cartOrder, cartItemList, shoppingCart, catItem);

				EmailUtil.connect("smtp.gmail.com", 587);

				if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), emailTitle + " from " + company.getNameEditable(), hbcEmail.getMessage(), null))
				{
				}

				// send email to hbc upon successful order.. aside from email in company settings.
				final String companyEmails[] = company.getEmail().split(",");
				// String companyEmails[] = {"mddeguzman@hbc.com.ph","corpcomm@hbc.com.ph"};
				// String companyEmails[] = {"anthony@ivant.com","anthony@ivant.com"};

				emailTitle = "Email copy of " + emailTitle + " ";

				if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", companyEmails, emailTitle + " from " + company.getNameEditable(), hbcEmail.getMessage(), null))
				{
				}

			}

		}

	}

	public String sendEmailToCompanyAccount(Member member)
	{
		String emailTitle;
		final String emailTo[] = company.getEmail().split(",");

		if(company.getName().equals("ramgo"))
		{
			for(int i = 0; i < emailTo.length; i++)
			{
				emailTo[i] = "";
			}

			emailTo[0] = "onlinesales@ramgoseeds.com";
		}

		setEmailBody();
		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();
			request.getParameter("to");

			content.append(member.getFirstname() + " " + member.getLastname());

			emailTitle = "Ordered Items - " + member.getFullName();
			content.append(" has ordered items.<br><br>");
			content.append(" <strong>Shipping Details:</strong><br>");

			final MemberShippingInfo shippingInfo = memberShippingInfoDelegate.find(company, member);

			content.append("<table>");
			content.append("<tr><td>Name:</td><td>" + shippingInfo.getShippingInfo().getName() + "</td></tr>");
			content.append("<tr><td>Address 1:</td><td>" + shippingInfo.getShippingInfo().getAddress1() + "</td></tr>");
			content.append("<tr><td>Address 2:</td><td>" + shippingInfo.getShippingInfo().getAddress2() + "</td></tr>");
			content.append("<tr><td>City:</td><td>" + shippingInfo.getShippingInfo().getCity() + "</td></tr>");
			content.append("<tr><td>State:</td><td>" + shippingInfo.getShippingInfo().getState() + "</td></tr>");
			content.append("<tr><td>Country:</td><td>" + shippingInfo.getShippingInfo().getCountry() + "</td></tr>");
			content.append("<tr><td>Zip Code:</td><td>" + shippingInfo.getShippingInfo().getZipCode() + "</td></tr>");
			content.append("<tr><td>Phone Number:</td><td>" + shippingInfo.getShippingInfo().getPhoneNumber() + "</td></tr>");
			content.append("<tr><td>Email Address:</td><td>" + shippingInfo.getShippingInfo().getEmailAddress() + "</td></tr>");
			content.append("<tr><td>&nbsp;</td></tr>");
			content.append("<tr><td>Item Details:</td></tr>");
			content.append("</table>");

			content.append(emailBody);

			if(company.getId().equals(CompanyConstants.HBC))
			{
				content.append("<table>");
				// String cartContentsMessage = "";
				// cartContentsMessage += "<tr><td colspan=\"4\" ></td></tr>";
				// cartContentsMessage += "<tr>";
				// cartContentsMessage += "	<td colspan=\"2\"><strong>Total Item Price:</strong></td>";
				// cartContentsMessage += "	<td></td>"; // formattedTotalItemsPrice
				// cartContentsMessage += "	<td><span style=\"float: right;\"><strong>$</strong>" + shoppingCart.getFormattedTotalItemsPrice() + "</span></td>";
				// cartContentsMessage += "</tr>";
				// cartContentsMessage += "<tr>";
				// cartContentsMessage += "	<td colspan=\"2\"><strong>Shipping Price :</strong></td>";
				// cartContentsMessage += "	<td></td>"; // formattedTotalShippingPrice
				// cartContentsMessage += "	<td><div id=\"totalPrice\"> <span style=\"float: right;\"><strong>$</strong>" + shoppingCart.getFormattedTotalShippingPrice() + "</span></div></td>";
				// cartContentsMessage += "</tr>";
				// cartContentsMessage += "<tr>";
				// cartContentsMessage += "	<td colspan=\"2\"><strong>Total Price :</strong></td>";
				// cartContentsMessage += "	<td></td>";
				// cartContentsMessage += "	<td><div id=\"totalPrice\"><span style=\"float: right;\"> <strong>$</strong>" + shoppingCart.getFormattedTotalPrice() + "</span></div></td>";
				// cartContentsMessage += "</tr>";
				// cartContentsMessage += "	<tr><td>&nbsp;</td></tr>";
				// cartContentsMessage += "<tr><td class=\"comment\" colspan=\"4\"><strong>Comments or special instructions about this order:</strong></td></tr>";
				// cartContentsMessage += "<tr><td colspan=\"4\"><br><br>";
				// cartContentsMessage += "</td>";
				// cartContentsMessage += "</tr>";
				// content.append(cartContentsMessage);
				content.append("</table>");
			}
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			// String to = company.getEmail().concat(","+cc);
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", emailTo, emailTitle + member.getFirstname() + " " + member.getLastname(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;
	}

	public void prepareMenu()
	{
		final Map<String, Menu> menuList = new HashMap<String, Menu>();

		// get the single pages
		final List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
		for(final SinglePage singlePage : singlePageList)
		{
			final String jspName = singlePage.getJsp();
			final Menu menu = new Menu(singlePage.getName(), httpServer + "/" + jspName + ".do");
			menuList.put(singlePage.getJsp(), menu);
		}
		// get the multi pages
		final List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
		for(final MultiPage multiPage : multiPageList)
		{
			final String jspName = multiPage.getJsp();
			final Menu menu = new Menu(multiPage.getName(), httpServer + "/" + jspName + ".do");
			menuList.put(multiPage.getJsp(), menu);
		}

		// get the form Pages
		final List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
		for(final FormPage formPage : formPageList)
		{
			final String jspName = formPage.getJsp();
			final Menu menu = new Menu(formPage.getName(), httpServer + "/" + jspName + ".do");
			menuList.put(formPage.getJsp(), menu);
		}

		// get the groups
		final List<Group> groupList = groupDelegate.findAll(company).getList();
		request.setAttribute("groupList", groupList);
		for(final Group group : groupList)
		{
			final String jspName = group.getName().toLowerCase();
			final Menu menu = new Menu(group.getName(), httpServer + "/" + jspName + ".do");
			menuList.put(jspName, menu);
		}

		request.setAttribute("menu", menuList);
	}

	/**
	 * Returns {@code SUCCESS} if order was successfully added, {@code INPUT} if member is null, and {@code ERROR} if an error was encountered.
	 *
	 * @return - {@code SUCCESS} if order was successfully added, {@code INPUT} if member is null, and {@code ERROR} if an error was encountered
	 * @throws Exception - indicates conditions that a reasonable application might want to catch
	 */
	public String addToOrder() throws Exception
	{

		Boolean isHBCSuccessPayment = Boolean.FALSE;

		// sets the look-ahead count of orders
		this.setOrderCount(Integer.parseInt(cartOrderDelegate.getOrderCount(company).toString()) + 1);

		if(company.getName().equalsIgnoreCase("hbc"))
		{
			// String memberInformation = reques
			if(member == null && getStringMemberInformation() != null && getStringMemberInformation().length() != 0)
			{

				String email = "";
				final String replacement = "--";

				String info = getStringMemberInformation();
				info = info.replaceAll("\\(", "").replaceAll("\\)", "");

				if(info.indexOf(replacement) != -1)
				{
					email = info.split(replacement)[0];
				}

				member = memberDelegate.findEmail(company, email);

			}

			if(transactionNumber != null && approvalcode != null)
			{
				if(approvalcode.equalsIgnoreCase("1"))
				{
					isHBCSuccessPayment = Boolean.TRUE;
				}
				else
				{
					isHBCSuccessPayment = Boolean.FALSE;
				}
			}

		}

		// validate current user, must not be empty
		if(isNull(member))
		{
			return INPUT;
		}

		logger.debug("addToOrder : company  = " + company);

		try
		{
			// create new order

			if(company.getName().equalsIgnoreCase("hbc") && isHBCSuccessPayment)
			{
				createOrder();
				initOrderItemList();

			}
			else if(!company.getName().equalsIgnoreCase("hbc"))
			{
				createOrder();
				initOrderItemList();
			}

		}
		catch(final Exception e)
		{

			addActionError("Error : " + e.toString());
			e.printStackTrace();
			return ERROR;
		}

		if(company != null && company.getName().equalsIgnoreCase("hbc"))
		{

			if(!isHBCSuccessPayment && cartOrder != null && cartOrder.getItemCount() > 0)
			{

				InventoryUtil.updateInventory(company, cartOrder, categoryItemDelegate, Boolean.FALSE);

				// System.out.println("inventoryWasUpdated    ---- >   " + inventoryWasUpdated);

			}
			else if(cartOrder != null && cartOrder.getItemCount() > 0)
			{
				// Uncomment this
				sendEmailToMemberWithTemplate(member);

				sendEmailToCompanyAccount(member);

			}
			return SUCCESS;
		}

		return SUCCESS;
	}

	public String addToOrderThenSubmit() throws Exception
	{

		// sets the look-ahead count of orders
		this.setOrderCount(Integer.parseInt(cartOrderDelegate.getOrderCount(company).toString()) + 1);

		// validate current user, must not be empty
		if(isNull(member))
		{
			return INPUT;
		}

		logger.debug("addToOrder : company  = " + company);

		try
		{

			// create new order
			createOrder();
			// create order item list
			initOrderItemList();
		}
		catch(final Exception e)
		{

			addActionError("Error : " + e.toString());
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * updates shipping info
	 */
	private void updateShippingInfo()
	{
		final MemberShippingInfo shippingInfo = memberShippingInfoDelegate.find(company, member);
		if(shippingInfo.getShippingInfo() == null)
		{
			final ShippingInfo shipping = new ShippingInfo();
			shippingInfo.setShippingInfo(shipping);
		}
		if(request.getParameter("shippingName") != null)
		{
			shippingInfo.getShippingInfo().setName(request.getParameter("shippingName"));
			if(request.getParameter("phoneNumber") != null)
			{
				shippingInfo.getShippingInfo().setPhoneNumber(request.getParameter("phoneNumber"));
			}
			if(request.getParameter("email") != null)
			{
				shippingInfo.getShippingInfo().setEmailAddress(request.getParameter("email"));
			}
			if(shippingInfo.getId() != null)
			{
				memberShippingInfoDelegate.update(shippingInfo);
			}
			else
			{
				memberShippingInfoDelegate.insert(shippingInfo);
			}

		}
	}

	/**
	 * Converts shopping cart into a new order.
	 */
	private void createOrder()
	{
		cartOrder = new CartOrder();
		cartOrder.setMember(member);
		cartOrder.setStatus(OrderStatus.NEW);
		cartOrder.setCompany(company);
		cartOrder.setCreatedOn(new Date());
		cartOrder.setIsValid(true);
		//
		if(request.getParameter("comments") != null)
		{
			cartOrder.setComments(request.getParameter("comments").toString());
		}
		else if(session.get("comments") != null)
		{
			cartOrder.setComments(session.get("comments").toString());
		}

		// set shipping info
		cartOrder.setShippingInfo(memberShippingInfoDelegate.find(company, member));

		// for storage of shipping address for each order
		final ShippingInfo orderShippingInfo = memberShippingInfoDelegate.find(company, member).getShippingInfo();

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

		if(company.getName().equalsIgnoreCase("hbc"))
		{
			cartOrder.setTransactionNumber(transactionNumber);// REFERENCE NUMBER
			cartOrder.setApprovalCode(approvalcode);
			// cartOrder.setTransactionReference(transactionReference);//REFERENCE FOR TRANSACTION
		}

		// cartOrder

		cartOrder = cartOrderDelegate.find(cartOrderDelegate.insert(cartOrder));
		// System.out.println("New CartOrder: "+cartOrder.getId());
	}
	
	@SuppressWarnings("unchecked")
	public String savecart() {
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		OrderStatus orderStatus = OrderStatus.NEW;
		//Boolean isNewOrder = false;
		String requestSItemId = request.getParameter("item_id");
		Long requestLItemId = 0L;
		CategoryItem item = new CategoryItem();
		Long cOrderId = 0L;
		CartOrder cOrder = new CartOrder();
		CartOrderItem cOrderItem = new CartOrderItem();
		
		List<CartOrder> listCOrder = new ArrayList<CartOrder>();
		
		if(member != null){
			listCOrder = cartOrderDelegate.findAllByMemberAndOrderStatus(company, member, orderStatus, -1, -1, new Order[]{Order.desc("createdOn")}).getList();
			try{
				requestLItemId = Long.parseLong(requestSItemId);
			}
			catch(Exception e){
				
			}
			item = categoryItemDelegate.find(requestLItemId);
			if(item != null){
				
				if(listCOrder != null && listCOrder.size() > 0){
					//isNewOrder = false;
					cOrder = listCOrder.get(0);
				}
				else{
					//this should be a new order
					//isNewOrder = true;
					cOrder = new CartOrder();
					cOrder.setCompany(company);
					cOrder.setMember(member);
					cOrder.setStatus(OrderStatus.NEW);
					
					cOrderId = cartOrderDelegate.insert(cOrder);
					cOrder = cartOrderDelegate.find(cOrderId);
				}
				
				cOrderItem = cartOrderItemDelegate.findByStatusAndCategoryItem(company, cOrder, item, "OK");
				if(cOrderItem == null){
					cOrderItem = new CartOrderItem();
					cOrderItem.setCompany(company);
					cOrderItem.setStatus("OK");
					cOrderItem.setCategoryItem(item);
					cOrderItem.setOrder(cOrder);
					cOrderItem.setQuantity(0);
					cartOrderItemDelegate.insert(cOrderItem);
					
					obj.put("success", true);
					obj.put("isAddedToCart", true);
					obj.put("cartContent", countCartWithFormat(company, cOrder));
					objList.add(obj);
					obj2.put("listAddToCartDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				}
				else{
					//delete/remove the existing cartorder item when it is already available from the cartorderitem table
					cartOrderItemDelegate.delete(cOrderItem);
					obj.put("success", true);
					obj.put("isAddedToCart", false);
					obj.put("cartContent", countCartWithFormat(company, cOrder));
					objList.add(obj);
					obj2.put("listAddToCartDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				}
			}
			else{
				obj.put("errorMessage", "Invalid Item! This item was already deleted!");
				obj.put("isAddedToCart", false);
				obj.put("cartContent", countCartWithFormat(company, cOrder));
				objList.add(obj);
				obj2.put("listAddToCartDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
			}
		}
		else{
			obj.put("errorMessage", "You are not a recognized user! Please log-in first before adding this item to your cart!");
			obj.put("isAddedToCart", false);
			
			objList.add(obj);
			obj2.put("listAddToCartDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
		}
		return SUCCESS;
	}
	
	public String countCartWithFormat(Company company, CartOrder cOrder) {
		Integer totalCount = 0;
		DecimalFormat myFormatter = new DecimalFormat("###,###");
		totalCount = Integer.parseInt(String.valueOf(cartOrderItemDelegate.findCartCountByOrder(company, cOrder)));
		return myFormatter.format(totalCount);
	}

	/**
	 * Populates orders from the company.
	 */
	private void initOrderList()
	{
		try
		{
			orderList = cartOrderDelegate.findAll(company, member);
			final List<String> date = new ArrayList<String>();
			logger.debug("Order List : " + orderList);
			for(final CartOrder order : orderList)
			{
				date.add(new DateTime(order.getCreatedOn()).toYearMonthDay().toString());
			}
			request.setAttribute("date", date);
		}
		catch(final Exception e)
		{
			logger.debug("No orders found.");
		}
	}

	/**
	 * Populates order items, added items are the items from the shopping cart.
	 */
	private void initOrderItemList()
	{
		// add shopping cart items to new order lists
		final List<CartOrderItem> items = new ArrayList<CartOrderItem>();
		final List<ShoppingCartItem> shoppingCartItems = shoppingCartItemDelegate.findAll(shoppingCart).getList();
		// System.out.println("number of items in shopping cart: "+shoppingCart.getItems().size());

		// for( ShoppingCartItem item : cartItemList)
		// catItem.add(categoryItemDelegate.find(item.getItemDetail().getRealID()));

		for(final ShoppingCartItem currentCartItem : shoppingCartItems)
		{
			// convert cart item into cart order item
			catItem.add(categoryItemDelegate.find(currentCartItem.getItemDetail().getRealID()));
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
			items.add(cartOrderItem);
		}

		// shoppingCart.getItems().get(0).getItemDetail().getName()

		cartOrder.setItems(items);
		cartOrderDelegate.update(cartOrder);

		if(company.getName().equalsIgnoreCase("hbc"))
		{
			request.setAttribute("_shoppingCart", shoppingCart);
			request.setAttribute("_catItem", catItem);
			request.setAttribute("_approvalCode", approvalcode);
			request.setAttribute("_transactionNumber", transactionNumber);
		}

		// shoppingCart.getItems().removeAll(shoppingCartItems);
		// shoppingCartDelegate.update(shoppingCart);
		// System.out.println("Total cart order items: "+cartOrder.getItems().size());
	}

	/**
	 * Returns shopping cart based on current session parameters.
	 */
	private void initShoppingCart()
	{
		shoppingCart = shoppingCartDelegate.find(company, member);
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

	/**
	 * Populates shopping cart with cart items.
	 */
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

	/**
	 * Returns orderList property value.
	 *
	 * @return - orderList property value
	 */
	public List<CartOrder> getOrderList()
	{
		return orderList;
	}

	/**
	 * Returns orderItemList property value.
	 *
	 * @return - orderItemList property value
	 */
	public List<CartOrderItem> getOrderItemList()
	{
		return orderItemList;
	}

	public PreOrder getPreOrder()
	{
		return preOrder;
	}

	public void setPreOrder(PreOrder preOrder)
	{
		this.preOrder = preOrder;
	}

	public List<PreOrderItem> getPreOrderItemList()
	{
		return preOrderItemList;
	}

	public void setPreOrderItemList(List<PreOrderItem> preOrderItemList)
	{
		this.preOrderItemList = preOrderItemList;
	}

	/**
	 * Returns cartOrder property value.
	 *
	 * @return - cartOrder property value
	 */
	public CartOrder getCartOrder()
	{
		return cartOrder;
	}

	public String edit()
	{
		return Action.SUCCESS;
	}

	public void getCartSize()
	{
		ObjectList<ShoppingCartItem> tempCartItems;
		try
		{
			shoppingCart = cartDelegate.find(company, member);
			tempCartItems = cartItemDelegate.findAll(shoppingCart);
			final int cartSize = tempCartItems.getList().size();
			request.setAttribute("cartSize", cartSize);
		}
		catch(final Exception e)
		{
			shoppingCart = null;
			logger.debug("Shopping cart is currently empty.");
		}

	}

	private void loadAllRootCategories()
	{
		final Order[] orders = { Order.asc("sortOrder") };
		final List<Category> rootCategories = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		request.setAttribute("rootCategories", rootCategories);
	}

	private void loadFeaturedPages(int max)
	{
		final Map<String, Object> featuredPages = new HashMap<String, Object>();
		final List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();

		for(final MultiPage mp : featuredMultiPage)
		{
			if(!mp.getHidden())
			{
				featuredPages.put(mp.getName(), mp);
			}
		}

		// System.out.println("size:::: " + featuredMultiPage.size());
		request.setAttribute("featuredPages", featuredPages);
	}

	private void loadFeaturedSinglePages(int max)
	{
		final Map<String, Object> featuredSinglePages = new HashMap<String, Object>();
		final List<SinglePage> featuredSinglePage = singlePageDelegate.findAllFeatured(company).getList();

		for(final SinglePage mp : featuredSinglePage)
		{
			if(!mp.getHidden())
			{
				featuredSinglePages.put(mp.getName(), mp);
			}
		}

		request.setAttribute("featuredSinglePages", featuredSinglePages);
	}

	public ShoppingCart getShoppingCart()
	{
		return shoppingCart;
	}

	/*
	 * Getter-setters for shopping cart, order count and comments.
	 */
	public void setShoppingCart(ShoppingCart shoppingCart)
	{
		this.shoppingCart = shoppingCart;
	}

	public int getOrderCount()
	{
		return orderCount;
	}

	public void setOrderCount(int orderCount)
	{
		this.orderCount = orderCount;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public ArrayList<CategoryItem> getCatItem()
	{
		return catItem;
	}

	public void setCatItem(ArrayList<CategoryItem> catItem)
	{
		this.catItem = catItem;
	}

	public String getHashValue()
	{
		return hashValue;
	}

	public void setHashValue(String hashValue)
	{
		this.hashValue = hashValue;
	}

	public Long[] getCartItem()
	{
		return cartItem;
	}

	public void setCartItem(Long[] cartItem)
	{
		this.cartItem = cartItem;
	}

	public List<ShoppingCartItem> getCartItemList()
	{
		return cartItemList;
	}

	public void setStringShippingInfo(String stringShippingInfo)
	{
		this.stringShippingInfo = stringShippingInfo;
	}

	public String getStringShippingInfo()
	{
		return stringShippingInfo;
	}

	public void setUom(String uom[])
	{
		this.uom = uom;
	}

	public String[] getUom()
	{
		return uom;
	}

	public void setApprovalcode(String approvalcode)
	{
		this.approvalcode = approvalcode;
	}

	public String getApprovalcode()
	{
		return approvalcode;
	}

	public void setMemberInformation(String memberInformation)
	{
		this.memberInformation = memberInformation;
	}

	public String getMemberInformation()
	{
		return memberInformation;
	}

	public void setStringMemberInformation(String stringMemberInformation)
	{
		this.stringMemberInformation = stringMemberInformation;
	}

	public String getStringMemberInformation()
	{
		return stringMemberInformation;
	}

	/**
	 * @param transactionNumber the transactionNumber to set
	 */
	public void setTransactionNumber(String transactionNumber)
	{
		this.transactionNumber = transactionNumber;
	}

	/**
	 * @return the transactionNumber
	 */
	public String getTransactionNumber()
	{
		return transactionNumber;
	}

	/**
	 * @param transactionReference the transactionReference to set
	 */
	public void setTransactionReference(String transactionReference)
	{
		this.transactionReference = transactionReference;
	}

	/**
	 * @return the transactionReference
	 */
	public String getTransactionReference()
	{
		return transactionReference;
	}

	private String getRealPath()
	{
		final ServletContext servCont = ServletActionContext.getServletContext();
		return servCont.getRealPath(getCompanyImageFolder()) + File.separator;
	}

	private String getCompanyImageFolder()
	{
		return "/companies/" + company.getName();
	}

	/**
	 * @return the notificationMessage
	 */
	public String getNotificationMessage()
	{
		return notificationMessage;
	}

	/**
	 * @param notificationMessage the notificationMessage to set
	 */
	@Override
	public void setNotificationMessage(String notificationMessage)
	{
		this.notificationMessage = notificationMessage;
	}

	public String getVoucherFileName()
	{
		return voucherFileName;
	}

	public void setVoucherFileName(String voucherFileName)
	{
		this.voucherFileName = voucherFileName;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}
}
