
package com.ivant.cms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gdata.util.common.base.CharMatcher;
import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.action.admin.TrustProductStatisticsAction;
import com.ivant.cms.component.Component;
import com.ivant.cms.component.EventCalendarComponent;
import com.ivant.cms.component.QuoteComponent;
import com.ivant.cms.delegate.BrandDelegate;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CategoryItemPackageDelegate;
import com.ivant.cms.delegate.CategoryItemPriceDelegate;
import com.ivant.cms.delegate.CommentDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.EventGroupDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.ItemFileDelegate;
import com.ivant.cms.delegate.LanguageDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.MemberImagesDelegate;
import com.ivant.cms.delegate.MemberLogDelegate;
import com.ivant.cms.delegate.MemberPollDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.MemberTypeDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.MultiPageFileDelegate;
import com.ivant.cms.delegate.NotificationDelegate;
import com.ivant.cms.delegate.OSSShippingAreaDelegate;
import com.ivant.cms.delegate.OSSShippingLocationDelegate;
import com.ivant.cms.delegate.OSSShippingRateDelegate;
import com.ivant.cms.delegate.OrderItemFileDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.PackageDelegate;
import com.ivant.cms.delegate.PageFileDelegate;
import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.delegate.PortalActivityLogDelegate;
import com.ivant.cms.delegate.PreOrderDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.delegate.RatesDelegate;
import com.ivant.cms.delegate.ReferralDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.WishlistDelegate;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.CategoryItemPackage;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.EventGroup;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.IPackage;
import com.ivant.cms.entity.ItemAttribute;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.MemberImages;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.MemberPoll;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.MultiPageFile;
import com.ivant.cms.entity.Notification;
import com.ivant.cms.entity.OSSShippingArea;
import com.ivant.cms.entity.OSSShippingLocation;
import com.ivant.cms.entity.OSSShippingRate;
import com.ivant.cms.entity.OrderItemFile;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.OtherFieldValue;
import com.ivant.cms.entity.PageFile;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.PreOrder;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.Rates;
import com.ivant.cms.entity.Referral;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.YQuote;
import com.ivant.cms.entity.YesPayments;
import com.ivant.cms.entity.interfaces.Page;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.PageType;
import com.ivant.cms.enums.ReferralEnum;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interceptors.LanguageInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.LanguageAware;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.cms.interfaces.MobileAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.constants.Constant;
import com.ivant.utils.CategoryItemPackageWrapper;
import com.ivant.utils.CompanyUtil;
import com.ivant.utils.FileImageUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.PagingUtil;
import com.ivant.utils.SiteMapGenerator;
import com.ivant.utils.SortingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;


public class PageDispatcherAction
		extends AbstractBaseAction
		/* ActionSupport */implements Action, Preparable, ServletRequestAware, ServletResponseAware, ServletContextAware, CompanyAware, MemberAware, SessionAware, LanguageAware, MobileAware
{
	
	private static final long serialVersionUID = -4707179782774190947L;
	 
	protected static final MemberFileItemDelegate receiptImageDelegate = MemberFileItemDelegate.getInstance();
	protected static final List<String> ALLOWED_PAGES;
	protected static final String MOBILE_ACTION = "mobile";
	protected Logger logger = Logger.getLogger(getClass());
	protected MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	protected SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	protected FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	protected GroupDelegate groupDelegate = GroupDelegate.getInstance();
	protected CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	protected CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	protected BrandDelegate brandDelegate = BrandDelegate.getInstance();
	protected OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	protected CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	protected PageFileDelegate pageFileDelegate = PageFileDelegate.getInstance();
	protected LanguageDelegate languageDelegate = LanguageDelegate.getInstance();
	protected ItemFileDelegate itemFileDelegate = ItemFileDelegate.getInstance();
	protected PageImageDelegate pageImageDelegate = PageImageDelegate.getInstance();
	protected MemberDelegate memberDelegate = MemberDelegate.getInstance();
	protected MemberTypeDelegate memberTypeDelegate = MemberTypeDelegate.getInstance();
	protected EventDelegate eventDelegate = EventDelegate.getInstance();
	protected EventGroupDelegate eventGroupDelegate = EventGroupDelegate.getInstance();
	protected ShoppingCartItemDelegate cartItemDelegate = ShoppingCartItemDelegate.getInstance();
	protected ShoppingCartDelegate cartDelegate = ShoppingCartDelegate.getInstance();
	protected MultiPageFileDelegate multiPageFileDelegate = MultiPageFileDelegate.getInstance();
	protected ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	protected RatesDelegate ratesDelegate = RatesDelegate.getInstance();
	protected CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	protected CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	protected OrderItemFileDelegate orderItemFileDelegate = OrderItemFileDelegate.getInstance();
	protected CategoryItemPackageDelegate categoryItemPackageDelegate = CategoryItemPackageDelegate.getInstance();
	protected PackageDelegate packageDelegate = PackageDelegate.getInstance();
	protected RegistrationItemOtherFieldDelegate registrationDelegate = RegistrationItemOtherFieldDelegate.getInstance();
	protected MemberLogDelegate memberLogDelegate = MemberLogDelegate.getInstance();
	protected PortalActivityLogDelegate portalActivityLogDelegate = PortalActivityLogDelegate.getInstance();
	protected ReferralDelegate referralDelegate = ReferralDelegate.getInstance();
	protected OSSShippingAreaDelegate areaDelegate = OSSShippingAreaDelegate.getInstance();
	protected OSSShippingLocationDelegate locationDelegate = OSSShippingLocationDelegate.getInstance();
	protected OSSShippingRateDelegate rateDelegate = OSSShippingRateDelegate.getInstance();
	protected PreOrderDelegate preOrderDelegate = PreOrderDelegate.getInstance();
	protected PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	protected SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	protected ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	protected ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
	protected WishlistDelegate wishlistDelegate = WishlistDelegate.getInstance();
	
	
	// -- Comment Secion for AGIAN --//
	
	protected CommentDelegate commentDelegate = CommentDelegate.getInstance();
	protected NotificationDelegate notificationDelegate = NotificationDelegate.getInstance();
	
	
	//-- End of AGIAN --- //
	
	
	
	protected MemberImagesDelegate memberImagesDelegate = MemberImagesDelegate.getInstance();
	
	private final MemberPollDelegate memberPollDelegate = MemberPollDelegate.getInstance();
	
	private static final CategoryItemPriceDelegate categoryItemPriceDelegate = CategoryItemPriceDelegate.getInstance();
	
	protected MemberShippingInfoDelegate memberShippingInfoDelegate = MemberShippingInfoDelegate.getInstance();
	
	protected CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	
	protected WebContext ctx = WebContextFactory.get();
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession mobileHttpSession;
	protected Company company;
	protected Member member;
	Member user;
	protected ShoppingCart shoppingCart;
	/** Shopping cart items, can be 0 or more */
	protected List<ShoppingCartItem> cartItemList;
	protected List<String> filename = new ArrayList<String>();
	protected ArrayList<CategoryItem> catItem = new ArrayList<CategoryItem>();
	protected CompanySettings companySettings;
	protected ServletContext servletContext;
	protected YQuote quote;
	protected Component component;
	protected EventGroup eventGroup;
	protected ItemAttribute itemAttribute;
	protected ItemComment currentItemComment;
	protected String servletContextName;
	protected String pageName;
	protected String httpServer;
	protected String myEmail;
	protected String commentcontent;
	protected String fileName;
	protected String keyword;
	protected String notificationMessage;
	protected String memberType;
	protected boolean isLocal;
	protected boolean pageIsGroup = false;
	protected boolean loginRequired = false;
	protected boolean hasLoggedOn = false;
	protected boolean isInDescription;
	protected List<Category> breadcrumbs;
	protected List<MultiPageFile> multiPageFiles;
	protected List<Group> groups;
	protected List<CategoryItem> searchList;
	protected List<CategoryItem> orderItems;
	protected List<Event> events;
	protected FileInputStream fInStream;
	protected FileInputStream fileInputStream;
	protected InputStream inputStream;
	
	
	protected long contentLength;
	protected Long fileSize;
	protected CartOrderItem cartOrderItem;
	protected OrderItemFile orderItemFile;
	protected Map session;
	protected ArrayList<String> quotation = new ArrayList<String>();
	protected ArrayList<String> quotationNum = new ArrayList<String>();
	protected List<CategoryItem> quoteItems;
	protected List<String> quoteItemsNum;
	protected ObjectList<CategoryItemPackage> categoryItemPackages;
	protected List<RegistrationItemOtherField> info;
	protected List<RegistrationItemOtherField> infoRepeating;
	protected Map<String, String> mapFieldValues;
	protected Map<String, String> mapRepeatingFieldValues;
	protected List<CategoryItem> nktiItems;
	protected List<OSSShippingArea> ossShippingAreas;
	protected List<OSSShippingLocation> ossShippingLocations;
	protected List<OSSShippingRate> rates;
	protected OSSShippingLocation location;
	protected String areas = "";
	protected String locations = "";
	protected String tempArea = "";
	protected Double rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8;
	protected PreOrder preOrder;
	protected List<SavedEmail> savedEmailList;
	protected List<SavedEmail> savedEmailListPanasonic;
	//notificationList
	protected List<Notification> notificationList;
	
	//kuysen CMS
	protected List<Brand> kuysenCMSBrandList;
	
	//for Wilcon E-Commerce
	protected List<CartOrder> cartOrderWilcon;
	protected List<ShoppingCartItem> wilconCartItems;
	
	
	
	protected YesPayments yesPayments;
	
	protected Language language;
	
	protected String branch;
	
	protected String promoCode;
	
	protected String referralItemToClaim;
	
	protected String referralId;
	
	protected String actionMode;
	
	protected Long referralsToBeRedeemed[];
	
	protected String rewardToClaim[];
	
	protected String searchItemKeyword;
	
	protected String cat_id;
	protected String subCat_id;
	
	protected String HIPRESubCategory;
	
	protected String conditionTags[];
	
	protected int page;
	
	protected int maxResults;
	
	protected PagingUtil pagingUtil;
	
	protected PagingUtil gurkkaMainProductsPagingUtil;
	protected PagingUtil gurkkaGuestProductsPagingUtil;
	
	protected String m;
	
	String amount;
	
	protected Integer currentPage;
	
	public String successUrl;
	public String errorUrl;
	public Long memberid;
	public String result_;
	public Long itemid;
	public String message;
	
	
	private int formType;
	
	
	protected String getBranch()
	{
		return branch;
	}
	
	protected void setBranch(String branch)
	{
		this.branch = branch;
		
	}
	
	static
	{
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("prinbrandsterfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void prepare() throws Exception
	{
		
		companySettings = company.getCompanySettings();
		pageName = parsePageName();
		servletContextName = servletContext.getServletContextName();
		isLocal = (request.getRequestURI().startsWith("/" + servletContextName))
			? true
			: false;
		httpServer = (isLocal)
			? ("http://" + request.getServerName() + ":" + request.getServerPort() + "/" + servletContextName + "/companies/" + company.getName())
			: ("http://" + request.getServerName());
		
		if(company != null && StringUtils.isNotEmpty(httpServer))
		{
			loadMenu();
		}
		
		//START --- ELCORP_COMPANY_NAME 
		/*
		if(CompanyConstants.ELCORP_COMPANY_NAME.equalsIgnoreCase(company.getName())){
			loadElcResources();
		}
		*/
		//END --- ELCORP_COMPANY_NAME 
		
		session.put(CompanyInterceptor.COMPANY_REQUEST_KEY, company.getId());
		session.put("itemPerPage", company.getCompanySettings().getProductsPerPage());
		
		//START --- IIEE_ORG_PHILS
		/*
		if(CompanyConstants.IIEE_ORG_PHILS.equalsIgnoreCase(company.getName()))
		{
			if(pageName.equalsIgnoreCase("order"))
			{
				ArrayList<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
				final String iieeItemId = request.getParameter("iieeItem");
				CategoryItem iieeItem = new CategoryItem();
				if(iieeItemId != null)
				{
					final Long id = Long.parseLong(iieeItemId);
					iieeItem = categoryItemDelegate.find(id);
					iieeItem.setOrderQuantity(1);
					iieeItem.setOtherDetails("1");
					updatedNoLogInCartItems.add(iieeItem);
				}
				session.put(CompanyConstants.IIEE_ORG_PHILS_CART, updatedNoLogInCartItems);
			}
			if(pageName.equalsIgnoreCase("cancel")){
				session.remove(CompanyConstants.IIEE_ORG_PHILS_CART);
				session.remove(CompanyConstants.IIEE_ORG_PHILS_TEMP_MEMBER);
			}
			
		}
		*/
		//END --- IIEE_ORG_PHILS
		
		//START --- COLUMNBUS
		/*
		if(CompanyConstants.COLUMNBUS.equalsIgnoreCase(company.getName()))
		{
			if(pageName.equalsIgnoreCase("products"))
			{
				boolean hasValidAccessKey = false;
				if(session.get("hasValidAccessKey") != null)
				{
					hasValidAccessKey = session.get("hasValidAccessKey").equals(true)
						? true
						: false;
				}
				if(!hasValidAccessKey)
				{
					String accessKey = request.getParameter("accessKey");
					if(accessKey != null)
					{
						String[] accessKeys = CompanyConstants.COLUMNBUS_ACCESS_KEY;
						for(int index = 0; index < accessKeys.length; index++)
						{
							if(accessKey.equals(accessKeys[index]))
							{
								session.put("hasValidAccessKey", true);
								return;
							}
						}
					}
				}
			}
		}
		*/
		//END --- COLUMNBUS
		
		//START --- PFA
		/*
		if(CompanyConstants.PFA.equalsIgnoreCase(company.getName())){
			
			List<CategoryItem> companyItems = new ArrayList<CategoryItem>();
			Group memberGroup = new Group();
			memberGroup = groupDelegate.find(company, "Members Directory");
			companyItems = categoryItemDelegate.getAllItems(company, groupDelegate.find(memberGroup.getId()), -1, -1, Order.desc("itemDate")).getList();
			request.setAttribute("companyItems", companyItems);
			
			if(pageName.equalsIgnoreCase("members") || pageName.equalsIgnoreCase("company_search")){
				String groupName = request.getParameter("groupName") != null ? request
						.getParameter("groupName")
						: "Members Directory";
				String businessType = request.getParameter("businessType");
				String invesmentLevel = request.getParameter("invesmentLevel");
				String brandName = request.getParameter("brandName");
				String filterBy = request.getParameter("filterBy");
				
				
				if("Members Directory".equalsIgnoreCase(groupName)){
					Group group = new Group();
					group = groupDelegate.find(company, "Members Directory");
					
					final Map<String, List<OtherFieldValue>> otherFieldMap = new HashMap<String, List<OtherFieldValue>>();
					for(final OtherField otherField : group.getOtherFields())
					{
						otherFieldMap.put(otherField.getName(), otherField.getOtherFieldValueList());
					}
					
					final List<OtherFieldValue> invesmentLevels = new ArrayList<OtherFieldValue>();
					
					invesmentLevels.addAll(otherFieldMap.get("Investment Level"));
					
					request.setAttribute("invesmentLevels", invesmentLevels);
					
					List <CategoryItem> items = new ArrayList<CategoryItem>(); 
					for(Category category: group.getCategories()){
						for(CategoryItem item: category.getItems()){
							Boolean addItem = Boolean.TRUE;
							
							
							if(businessType!=null && !StringUtils.isEmpty(businessType)){
								if(!businessType.equalsIgnoreCase(item.getParent().getId().toString())){
									addItem = Boolean.FALSE;
								}
							}
							
							if(addItem){
								if(invesmentLevel!=null && !StringUtils.isEmpty(invesmentLevel)){
									String itemInvestmentLevel = ""; 
									
									for(CategoryItemOtherField categoryItemOtherField:item.getCategoryItemOtherFields()){
										if(categoryItemOtherField.getOtherField().getName().equalsIgnoreCase("Investment Level")){
											itemInvestmentLevel = categoryItemOtherField.getContent();
										}
									}
									if (!invesmentLevel.equalsIgnoreCase(itemInvestmentLevel)) {
										addItem = Boolean.FALSE;
									} 
								}
							}
							
							if(addItem){
								if(brandName!=null && !StringUtils.isEmpty(brandName)){
									if (!item.getBrand().getName().toUpperCase().contains(brandName.toUpperCase())) {
										addItem = Boolean.FALSE;
									} 
								}
							}
							
							if(addItem){
								if(filterBy!=null && !StringUtils.isEmpty(filterBy) ){
									String regex = "";
									if(filterBy.equalsIgnoreCase("1")){
										regex = "ABC123456789#";										
									}
									if(filterBy.equalsIgnoreCase("2")){
										regex = "DEFGH";
									}
									if(filterBy.equalsIgnoreCase("3")){
										regex = "IJKLM";
									}
									if(filterBy.equalsIgnoreCase("4")){
										regex = "NOPQRS";
									}
									if(filterBy.equalsIgnoreCase("5")){
										regex = "TUVWXYZ";
									}
									if (!CharMatcher.anyOf(regex).matches(
											item.getBrand() != null ? item
													.getBrand().getName()
													.toUpperCase()
													.charAt(0) : item
													.getName()
													.toUpperCase()
													.charAt(0))) {
										addItem = Boolean.FALSE;
									}
								}
							}
							
							
							if(addItem){
								items.add(item);
							}
							
						}
					}
					
					request.setAttribute("membersDirectoryItems", items);				
				}else{
					Group group = new Group();
					group = groupDelegate.find(company, "ALLIED SERVICES AND SUPPLIERS");
					List <CategoryItem> items = new ArrayList<CategoryItem>(); 
					for(Category category: group.getCategories()){
						for(CategoryItem item: category.getItems()){
							Boolean addItem = Boolean.TRUE;
							
							if(addItem){
								if(filterBy!=null && !StringUtils.isEmpty(filterBy) ){
									String regex = "";
									if(filterBy.equalsIgnoreCase("1")){
										regex = "ABC123456789#";										
									}
									if(filterBy.equalsIgnoreCase("2")){
										regex = "DEFGH";
									}
									if(filterBy.equalsIgnoreCase("3")){
										regex = "IJKLM";
									}
									if(filterBy.equalsIgnoreCase("4")){
										regex = "NOPQRS";
									}
									if(filterBy.equalsIgnoreCase("5")){
										regex = "TUVWXYZ";
									}
									if (!CharMatcher.anyOf(regex).matches(
											item.getName().toUpperCase()
													.charAt(0))) {
										addItem = Boolean.FALSE;
									}
								}
							}
							
							if(addItem){
								items.add(item);
							}
						}
					}
					request.setAttribute("alliedServicesAndSuppliersItems", items);		
				}
			}
		}
		*/
		//END --  PFA
		
		//START --- MR_AIRCON
		/*
		if(CompanyConstants.MR_AIRCON.equalsIgnoreCase(company.getName()))
		{
			if(pageName.equalsIgnoreCase("shopping-cart"))
			{
				final ArrayList<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
				if(null != session.get("mrAirConNoLogInCartItems"))
				{
					final String removeToCart = request.getParameter("removeToCart");
					CategoryItem itemToRemove = new CategoryItem();
					if(removeToCart != null)
					{
						final Long id = Long.parseLong(removeToCart);
						itemToRemove = categoryItemDelegate.find(id);
					}
					
					final ArrayList<CategoryItem> oldNoLogInCartItems = (ArrayList<CategoryItem>) session.get("mrAirConNoLogInCartItems");
					oldNoLogInCartItems.remove(itemToRemove);
					
					boolean isNewItem = true;
					final String addTocart = request.getParameter("addTocart");
					CategoryItem newItem = null;
					if(addTocart != null)
					{
						newItem = new CategoryItem();
						final Long itemId = Long.parseLong(addTocart);
						newItem = categoryItemDelegate.find(itemId);
						newItem.setOrderQuantity(1);
					}
					
					boolean isCustomOnly = false;
					for(final CategoryItem oldItem : oldNoLogInCartItems)
					{
						final CategoryItem updatedItem = categoryItemDelegate.find(oldItem.getId());
						// set old quantity
						updatedItem.setOrderQuantity(oldItem.getOrderQuantity());
						
						if(updatedItem.getCategoryItemOtherFieldMap().get("Custom Only") != null)
						{
							if(updatedItem.getCategoryItemOtherFieldMap().get("Custom Only").getContent().equalsIgnoreCase("yes"))
							{
								isCustomOnly = true;
							}
							
						}
						
						// if new item already exist add its quantity
						if(newItem != null)
						{
							if(oldItem.getId().longValue() == newItem.getId().longValue())
							{
								updatedItem.setOrderQuantity(oldItem.getOrderQuantity() + 1);
								isNewItem = false;
							}
						}
						updatedNoLogInCartItems.add(updatedItem);
					}
					
					if(isNewItem && addTocart != null)
					{
						boolean isNewItemCustomOnly = false;
						if(newItem.getCategoryItemOtherFieldMap().get("Custom Only") != null)
						{
							if(newItem.getCategoryItemOtherFieldMap().get("Custom Only").getContent().equalsIgnoreCase("yes"))
							{
								isCustomOnly = true;
							}
						}
						request.setAttribute("isCustomOnly", isNewItemCustomOnly);
						updatedNoLogInCartItems.add(newItem);
					}
					request.setAttribute("isCustomOnly", isCustomOnly);
				}
				else
				{
					final String addTocart = request.getParameter("addTocart");
					if(addTocart != null)
					{
						final Long itemId = Long.parseLong(addTocart);
						final CategoryItem newItem = categoryItemDelegate.find(itemId);
						newItem.setOrderQuantity(1);
						boolean isNewItemCustomOnly = false;
						if(newItem.getCategoryItemOtherFieldMap().get("Custom Only") != null)
						{
							isNewItemCustomOnly = newItem.getCategoryItemOtherFieldMap().get("Custom Only").getContent().equalsIgnoreCase("yes")
								? true
								: false;
						}
						request.setAttribute("isCustomOnly", isNewItemCustomOnly);
						updatedNoLogInCartItems.add(newItem);
					}
				}
				ossShippingAreas = areaDelegate.findAll(company);
				
				Comparator<OSSShippingArea> shippingAreaDisplayOrder = new Comparator<OSSShippingArea>()
				{
					@Override
					public int compare(OSSShippingArea area1, OSSShippingArea area2)
					{
						if(area1.getAreaName().equalsIgnoreCase("Metro Manila"))
						{
							Long id1 = area1.getId();
							Long id2 = area2.getId();
							return id1.compareTo(id2);
						}
						return area1.getAreaName().compareTo(area2.getAreaName());
					}
				};
				Collections.sort(ossShippingAreas, shippingAreaDisplayOrder);
				
				ossShippingLocations = locationDelegate.findAll(company);
				
				String areaAndlocations = "";
				for(int i = 0; i < ossShippingLocations.size(); i++)
				{
					areaAndlocations += ossShippingLocations.get(i).getLocationName() + Constant.delimeter + ossShippingLocations.get(i).getOssShippingArea().getAreaName();
					if(i != ossShippingLocations.size() - 1)
					{
						areaAndlocations += Constant.object_delimeter;
					}
					
				}
				request.setAttribute("object_delimeter", Constant.object_delimeter);
				request.setAttribute("delimeter", Constant.delimeter);
				request.setAttribute("areaAndlocations", areaAndlocations);
				request.setAttribute("regions", ossShippingAreas);
				request.setAttribute("cities", ossShippingLocations);
				session.put("mrAirConNoLogInCartItems", updatedNoLogInCartItems);
			}
			if(pageName.equalsIgnoreCase("compare"))
			{
				final ArrayList<CategoryItem> updatedCompareItems = new ArrayList<CategoryItem>();
				if(null != session.get("mrAirConCompareItems"))
				{
					final String removeToCompare = request.getParameter("removeToCompare");
					CategoryItem itemToRemove = new CategoryItem();
					if(removeToCompare != null)
					{
						final Long id = Long.parseLong(removeToCompare);
						itemToRemove = categoryItemDelegate.find(id);
					}
					
					final ArrayList<CategoryItem> oldCompareItems = (ArrayList<CategoryItem>) session.get("mrAirConCompareItems");
					oldCompareItems.remove(itemToRemove);
					
					for(final CategoryItem oldItem : oldCompareItems)
					{
						final CategoryItem updatedItem = categoryItemDelegate.find(oldItem.getId());
						updatedCompareItems.add(updatedItem);
						
					}
				}
				session.put("mrAirConCompareItems", updatedCompareItems);
			}
			
			if(pageName.equalsIgnoreCase("checkout"))
			{
				final ArrayList<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
				final String paymentMethod = request.getParameter("paymentMethod");
				final String installationType = request.getParameter("installationType");
				
				// shipping details
				final String title = request.getParameter("title");
				final String first_name = request.getParameter("first_name");
				final String last_name = request.getParameter("last_name");
				final String shipping_address = request.getParameter("shipping_address");
				final String email_address = request.getParameter("email_address");
				final String shipping_city = request.getParameter("shipping_city");
				final String tel_no = request.getParameter("tel_no");
				final String shipping_region = request.getParameter("shipping_region");
				final String mobile_no = request.getParameter("mobile_no");
				final String shipping_zip_code = request.getParameter("shipping_zip_code");
				final String shipping_company = request.getParameter("shipping_company");
				
				final String billing_address = request.getParameter("billing_address");
				final String additional_delivery_instruction = request.getParameter("additional_delivery_instruction");
				final String billing_city = request.getParameter("billing_city");
				final String billing_region = request.getParameter("billing_region");
				final String billing_zip_code = request.getParameter("billing_zip_code");
				
				final String nationality = request.getParameter("nationality");
				final String dob_month = request.getParameter("dob_month");
				final String dob_day = request.getParameter("dob_day");
				final String dob_year = request.getParameter("dob_year");
				
				Date bdate = new Date(dob_month + "/" + dob_day + "/" + dob_year);
				SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
				String dob = df.format(bdate);
				
				String delivery_date = request.getParameter("delivery_date");
				
				if(delivery_date != null && delivery_date != "")
				{
					Date ddate = new Date(delivery_date);
					delivery_date = df.format(ddate);
				}
				
				final String idAndQuantity = request.getParameter("idAndQuantity");
				if(idAndQuantity != null)
				{
					final String[] idAndQuantityArray = idAndQuantity.split(",");
					for(int i = 0; i < idAndQuantityArray.length; i++)
					{
						final Long id = Long.parseLong(idAndQuantityArray[i].split(":")[0]);
						final Integer qty = Integer.parseInt(idAndQuantityArray[i].split(":")[1]);
						final CategoryItem updatedItem = categoryItemDelegate.find(id);
						updatedItem.setOrderQuantity(qty);
						updatedNoLogInCartItems.add(updatedItem);
					}
					
				}
				
				request.setAttribute("paymentMethod", paymentMethod);
				request.setAttribute("installationType", installationType);
				
				request.setAttribute("customer_title", title);
				request.setAttribute("first_name", first_name);
				request.setAttribute("last_name", last_name);
				request.setAttribute("shipping_address", shipping_address);
				request.setAttribute("email_address", email_address);
				request.setAttribute("shipping_city", shipping_city);
				request.setAttribute("tel_no", tel_no);
				request.setAttribute("shipping_region", shipping_region);
				request.setAttribute("mobile_no", mobile_no);
				request.setAttribute("shipping_zip_code", shipping_zip_code);
				request.setAttribute("shipping_company", shipping_company);
				request.setAttribute("delivery_date", delivery_date);
				request.setAttribute("billing_address", billing_address);
				request.setAttribute("additional_delivery_instruction", additional_delivery_instruction);
				request.setAttribute("billing_city", billing_city);
				request.setAttribute("billing_region", billing_region);
				request.setAttribute("billing_zip_code", billing_zip_code);
				request.setAttribute("nationality", nationality);
				request.setAttribute("dob", dob);
				
				session.put("mrAirConNoLogInCartItems", updatedNoLogInCartItems);
			}
			
			if(pageName.equalsIgnoreCase("store"))
			{
				// if item_id equal to null display all items with pagination
				if(null == request.getParameter("item_id") || StringUtils.isEmpty(request.getParameter("item_id")))
				{
					int pageNumber = 1;
					int itemPerPage = 10;
					if(null != request.getParameter("pageNumber"))
					{
						pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
					}
					if(null != request.getParameter("itemPerPage"))
					{
						itemPerPage = Integer.parseInt(request.getParameter("itemPerPage"));
					}
					request.setAttribute("pageNumber", pageNumber);
					request.setAttribute("itemPerPage", itemPerPage);
					
					Group group = new Group();
					group = groupDelegate.find(company, "Products");
					
					// get other field for value of dropdown in search
					final Order[] orders = { Order.asc("sortOrder"), Order.asc("name") };
					
					final List<Brand> brand_list = brandDelegate.findAll(company, group, orders).getList();
					request.setAttribute("brand_list", brand_list);
					
					final Map<String, List<OtherFieldValue>> otherFieldMap = new HashMap<String, List<OtherFieldValue>>();
					for(final OtherField otherField : group.getOtherFields())
					{
						otherFieldMap.put(otherField.getName(), otherField.getOtherFieldValueList());
					}
					
					final List<OtherFieldValue> cooling_capacity_list = new ArrayList<OtherFieldValue>();
					
					cooling_capacity_list.addAll(otherFieldMap.get("Cooling Capacity Filter"));
					
					request.setAttribute("cooling_capacity_list", cooling_capacity_list);
					
					final List<CategoryItem> priceRanges = new ArrayList<CategoryItem>();
					
					final Double[] priceStarts = { 1.00, 25000.00, 50000.00, 100000.00, 150000.00 };
					final Double[] priceEnds = { 24999.99, 49999.99, 99999.99, 149999.99, null };
					
					for(Integer index = 0; index < priceStarts.length; index++)
					{
						final CategoryItem priceRange = new CategoryItem();
						priceRange.setName(index.toString());
						
						final NumberFormat nf = NumberFormat.getInstance();
						final String priceStart = nf.format(priceStarts[index]);
						final String priceEnd = priceEnds[index] != null
							? " - " + nf.format(priceEnds[index])
							: " and above";
						final String description = "PHP " + priceStart + priceEnd;
						priceRange.setDescription(description);
						priceRanges.add(priceRange);
					}
					request.setAttribute("priceRanges", priceRanges);
				}
			}
			if(pageName.equalsIgnoreCase("about"))
			{
				Group group = new Group();
				group = groupDelegate.find(company, "Products");
				
				final Order[] orders = { Order.asc("sortOrder"), Order.asc("name") };
				final List<Brand> brand_list = brandDelegate.findAll(company, group, orders).getList();
				request.setAttribute("brand_list", brand_list);
			}
		}
		
		if(company.getName().equalsIgnoreCase("rhi")) {
			final String ipaddress = request.getRemoteAddr();
			String ip2LocationQuery = "http://api.ipinfodb.com/v3/ip-country/?format=json&";
			ip2LocationQuery += "key=" + "aab008877eb3dbcb4b2d0ab1f66a31dd2985d89346038885eb93d482011818a6";
			ip2LocationQuery += "&ip=" + ipaddress;
			 
			
			
			final PostMethod postMethod = new PostMethod(ip2LocationQuery);
			new HttpClient().executeMethod(postMethod);
			boolean fileNotAvailable = false;
			String responseString = postMethod.getResponseBodyAsString();
			
			try
			{
				org.json.JSONObject obj = new org.json.JSONObject(responseString);
				String countryCode = obj.getString("countryCode");
				
				if(countryCode.equalsIgnoreCase("PH"))
				{
					request.setAttribute("fileNotAvailable", fileNotAvailable);
				} else {
					fileNotAvailable = true;
					request.setAttribute("fileNotAvailable", fileNotAvailable);
				}
				request.setAttribute("countryCode", countryCode);				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		*/
		//END --- MR_AIRCON
		
		/*
		if(CompanyConstants.TRANSLATIONS == company.getId() || httpServer.equalsIgnoreCase("www.translations.ph") || httpServer.equalsIgnoreCase("www.translations.com.ph"))
		{
			final String ipaddress = request.getRemoteAddr();
			String ip2LocationQuery = "http://api.ip2location.com/?";
			ip2LocationQuery += "key=" + "E2D2ECE89F";
			ip2LocationQuery += "&ip=" + ipaddress;
			
			final PostMethod postMethod = new PostMethod(ip2LocationQuery);
			new HttpClient().executeMethod(postMethod);
			
			if(postMethod.getResponseBodyAsString().equalsIgnoreCase("PH"))
			{
				final String redirectURL = "http://www.translations.ph/";
				response.sendRedirect(redirectURL);
			}
			else if(postMethod.getResponseBodyAsString().equalsIgnoreCase("AU"))
			{
				final String redirectURL = "http://www.Translations-e.au/";
				response.sendRedirect(redirectURL);
			}
			
			else if(postMethod.getResponseBodyAsString().equalsIgnoreCase("-"))
			{
				final String redirectURL = "http://www.google.com";
				response.sendRedirect(redirectURL);
			}
			else if(postMethod.getResponseBodyAsString().equalsIgnoreCase("HK"))
			{
				final String redirectURL = "http://www.translations-e.hk/";
				response.sendRedirect(redirectURL);
			}
			else if(postMethod.getResponseBodyAsString().equalsIgnoreCase("JP"))
			{
				final String redirectURL = "http://www.translations-e.jp/";
				response.sendRedirect(redirectURL);
			}
			
			else if(postMethod.getResponseBodyAsString().equalsIgnoreCase("KP"))
			{
				final String redirectURL = "http://www.translations.kr/";
				response.sendRedirect(redirectURL);
			}
			
			else if(postMethod.getResponseBodyAsString().equalsIgnoreCase("MY"))
			{
				final String redirectURL = "http://www.translations.my/";
				response.sendRedirect(redirectURL);
			}
			else if(postMethod.getResponseBodyAsString().equalsIgnoreCase("SG"))
			{
				final String redirectURL = "http://www.translations.sg/";
				response.sendRedirect(redirectURL);
			}
			else
			{
				final String redirectURL = "http://www.translations.ph/";
				response.sendRedirect(redirectURL);
			}
			
		}
		
		if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA) 
		{
			final int maxResult = 8;
			final String STRAP = "Strap";
			final String SOLID = "Solid";
			final String FACE = "Face";
			final String ABSTRACT = "Abstract";
			final String UPLOADED_PHOTO = "Uploaded Photo";
			Integer solidLength = 0;
			Integer abstractLength = 0;
			List<Group> tempGroup = groupDelegate.findAll(company).getList();
			List<PromoCode> promoCodes = promoCodeDelegate.findAllWithinExpiryDate(company).getList();
			
			request.setAttribute("promoCodes", promoCodes);
			
			for(Group group : tempGroup) {
				if(group.getName().equals(STRAP)) {
					for(Category category : group.getCategories()) {
						if(category.getName().equals(SOLID)) {
							solidLength = category.getAvailableItems().size() - 1;
						}
					}
				}
				if(group.getName().equals(FACE)){
					for(Category category : group.getCategories()){
						if(category.getName().equals(ABSTRACT)) {
							abstractLength = category.getItems().size() - 1;
						}
						if(category.getName().equals(UPLOADED_PHOTO)){
							for(CategoryItem categoryItem : category.getItems()){
								if(categoryItem.getName().equals(UPLOADED_PHOTO)){
									request.setAttribute("uploadId", categoryItem.getId());
									request.setAttribute("uploadPrice", categoryItem.getPrice());
								}
							}
						}
					}
				}
			}
			
			Random random = new Random();
			Integer randomNumber = random.nextInt((solidLength - 0) + 1) + 0;
			request.setAttribute("randomNumber", randomNumber);
			Integer randomFaceNumber = random.nextInt((abstractLength - 0) + 1) + 0;
			request.setAttribute("randomFaceNumber", randomFaceNumber);
			
			if(StringUtils.isNotEmpty(request.getParameter("faceId")) && request.getParameter("faceId") != null && request.getParameter("strapId") != null) {
				final Long faceId = Long.parseLong(request.getParameter("faceId"));
				final Long strapId = Long.parseLong(request.getParameter("strapId"));
				final String text = request.getParameter("text");
				final String textFont = request.getParameter("textFont");
				final String textSize = request.getParameter("textSize");
				final String textColor = request.getParameter("textColor");
				final String handColor = request.getParameter("handColor");
				
				CategoryItem faceItem = categoryItemDelegate.find(faceId);
				CategoryItem strapItem = categoryItemDelegate.find(strapId);
				
				request.setAttribute("faceItem", faceItem);
				request.setAttribute("strapItem", strapItem);
				request.setAttribute("text", text);
				request.setAttribute("textFont", textFont);
				request.setAttribute("textSize", textSize);
				request.setAttribute("textColor", textColor);
				request.setAttribute("handColor", handColor);
			}
			List<CartOrderItem> cartOrderItemList = cartOrderItemDelegate.findAllByCompany(company, maxResult);
			List<CartOrderItem> cartOrderItemListForGallery = cartOrderItemDelegate.findAllByCompany(company, 100);

			request.setAttribute("cartOrderItemList", cartOrderItemList);
			request.setAttribute("cartOrderItemListForGallery", cartOrderItemListForGallery);
		}
		
		if(company.getId() == CompanyConstants.THEAPPARELAUTHORITY){
			List<Group> tempGroup = groupDelegate.findAll(company).getList();
			List<CategoryItem> newsList = null;
			final String NEWS = "News";
			final String SERVICES = "Services";
			for(Group group : tempGroup){
				if(group.getName().equals(NEWS)) {
					for(Category category : group.getCategories()) {
						if(category.getName().equals(NEWS)) {
							newsList = category.getItems();
						}
					}
				}
				if(group.getName().equals(SERVICES)) {
					List<List> categoryTypeList = new ArrayList<List>();
					for(Category category : group.getCategories()) {
						List<List> childCategoryList = new ArrayList<List>();
						for(Category childCategory : category.getChildrenCategory()){
							List<String> typeList = new ArrayList<String>();
							typeList.add(childCategory.getName());
							for(CategoryItem item : childCategory.getItems()){}
							
							
							categoryTypeList.add(typeList);
						}
						
					}
					request.setAttribute("categoryTypeList", categoryTypeList);
				}
			}
			
			
			//Collections.sort(newsList, new Comparator<CategoryItem>(){
			//	public int compare(CategoryItem item1, CategoryItem item2){
			//        return item1.getItemDate().compareTo(item2.getItemDate()); // Compare by itemdate
			//    }
			//});
			
			//request.setAttribute("sortedNewsListByDateAsc", newsList);
			//Collections.sort(newsList, new Comparator<CategoryItem>(){
			//	public int compare(CategoryItem item1, CategoryItem item2){
			//        return -1; 
			//    }
			//});
			//request.setAttribute("sortedNewsListByDateDesc", newsList);
		}
		

		if(company.getId() == CompanyConstants.CITADEL){}
		
		if(company.getId() == CompanyConstants.WENDYS){}
		*/
		
		// load all category
		loadAllCategoryByProducts();
		
		/*
		// load all products
		if(company.getId() == CompanyConstants.BOYSEN){
			loadAllProducts();
		}
			
		if(company.getName().equals("boysen-pc-desktop")){
			
			CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
			loadAllProducts();
			
			System.out.println("PAGENAME ===== " + pageName);
			if(pageName.equals("msds") || pageName.equals("choosepaint") || pageName.equals("products")){
				Company comp = companyDelegate.find((long) 51);
				setCompany(comp);
				System.out.println("company1 ===== " + company);
			}
			//else if(pageName.contains("konstrukt")){
			//	Company comp = companyDelegate.find((long) 137);
			//	setCompany(comp);
			//	System.out.println("company1 ===== " + company);
			//}

		}
		
		
		
		if(company.getName().equalsIgnoreCase("mundipharma2")) {
			List<Member> memberList = new ArrayList<Member>();
			List<Member> memberDigitalMktgList = new ArrayList<Member>();
			for(Member e : memberDelegate.findAll(company).getList()){
				if(e.getValue2() != null && e.getValue2().contains("Digital Marketing"))
					memberDigitalMktgList.add(e);
				else
					memberList.add(e);
			}
			request.setAttribute("memberList", memberList);
			request.setAttribute("memberDigitalMktgList", memberDigitalMktgList);
			
			List<Member> members = memberDelegate.findAllByPageModule("GEM", company).getList();
			request.setAttribute("members", members);
		}
		
		if(company.getName().equalsIgnoreCase("agian")) {
			request.setAttribute("memberList", memberDelegate.findAll(company).getList());

		}
		
		if(company.getName().equals("bluelivetofeel")){
			List<MemberImages> list = memberImagesDelegate.findAll(company).getList();
			request.setAttribute("memberImages", list);
		}
		*/
		
		if(company != null)
		{
			//if(company.getId() == CompanyConstants.GURKKA)// || company.getId() == CompanyConstants.GURKKA_TEST)
			//{
			//	final List<CategoryItem> allItems = categoryItemDelegate.findAll(company).getList();
			//	prepareOtherFields(allItems);
			//}
		}
		
		request.setAttribute("local", this.isLocal);
		
		final Map<String, Company> companyMap = new HashMap<String, Company>();
		for(Company c : CompanyDelegate.getInstance().findAll())
		{
			companyMap.put(c.getName(), c);
			companyMap.put(String.valueOf(c.getId()), c);
		}
		request.setAttribute("companyMap", companyMap);
		
		setHasLoggedOn(false);
		initShoppingCart();
		logger.debug("End of prepare().");
	}
	
	private void loadAllCategoryByProducts() {
		// TODO Auto-generated method stub
		final Group group = groupDelegate.find(company, "Products");
		if(group != null)
		{
			List<Category> categories = categoryDelegate.findAll(company,group).getList();
			if(categories != null) {
				request.setAttribute("allProductCategory", categories);
			}
		}
	}

	public void initializeLanguage()
	{
		final String languageString = request.getParameter("language");
		if(StringUtils.isNotEmpty(languageString))
		{
			Language languageTemp;
			
			if(!languageString.equals("default"))
			{
				languageTemp = languageDelegate.find(languageString, company);
				if(languageTemp != null)
				{
					request.getSession().setAttribute(LanguageInterceptor.LANGUAGE_SESSION_KEY, languageTemp.getId());
					language = languageTemp;
				}
			}
			else
			{
				language = null;
				request.getSession().setAttribute(LanguageInterceptor.LANGUAGE_SESSION_KEY, null);
			}
			
		}
	}
	
	public String getAmount()
	{
		return amount;
	}
	
	public void setAmount(String amount)
	{
		this.amount = amount;
	}
	
	public String returnSuccess()
	{
		final YesPayments yp = new YesPayments();
		yp.setCrcyCd("PHP");
		request.setAttribute("yesPayments", yp);
		return Action.SUCCESS;
	}
	
	public String yesPayments()
	{
		loadCartItems();
		
		if(company.getId() == CompanyConstants.ONLINE_DEPOT)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
				session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
			}
			
			// details for shipping information
			session.put("deliverydate", request.getParameter("deliveryDate"));
			session.put("name", request.getParameter("name"));
			session.put("address1", request.getParameter("address1"));
			session.put("address2", request.getParameter("address2"));
			session.put("province", request.getParameter("province"));
			session.put("city", request.getParameter("city"));
			session.put("zipcode", request.getParameter("zipcode"));
			session.put("mobilenumber", request.getParameter("mobilenumber"));
			session.put("phonenumber", request.getParameter("phonenumber"));
			session.put("email", request.getParameter("email"));
			
			// to populate areas and location
			// setShippingAreas();
			
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
		}
		
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
		// for the checkbox
		String[] title = new String[1];
		String[] cost = new String[1];
		String[] eventReg = new String[1];
		
		if(request.getParameterValues("eventcheckbox") != null)
		{
			String selectedItems = "";
			final String select[] = request.getParameterValues("eventcheckbox");
			
			if(select != null && select.length != 0)
			{
				title = new String[select.length];
				cost = new String[select.length];
				eventReg = new String[select.length];
				
				for(int i = 0; i < select.length; i++)
				{
					if(select[i].indexOf("membership") >= 0)
					{
						yesPayments.setMembershipFee(true);
					}
					select[i] = select[i].replace("membership___", "");
					select[i] = select[i].replace("event___", "");
					
					final String[] splitByNameAndCost = select[i].split("___");
					title[i] = splitByNameAndCost[0] + "";
					cost[i] = splitByNameAndCost[1] + " " + splitByNameAndCost[2];
					eventReg[i] = splitByNameAndCost[3];
					
					selectedItems += select[i].replace("___", "     ");
				}
				
				session.put("eventReg", eventReg);
				
				yesPayments.setNotes(selectedItems);
				yesPayments.setEvents(title);
			}
		}
		
		request.setAttribute("title", title);
		request.setAttribute("cost", cost);
		
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
		if(request.getParameter("TransactionID") != null && company.getId() == CompanyConstants.ONLINE_DEPOT)
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
		if(yesPayments.generateNewMerchantReference())
		{
			System.out.println("A NEW MECHANT REFERENCE IS GENERATED " + yesPayments.getMerchantReference());
		}
		else
		{
			System.out.println("NOTHING HAS CHANGE IN MERCHANT REFERENCE" + yesPayments.getMerchantReference());
		}
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
			System.out.println("EMAIL WAS SUCCESSFULLY SENT TO " + yesPayments.getCardHolderEmail());
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
	
	public void loadConditionItems()
	{
		if(conditionTags != null && conditionTags.length > 0)
		{
			final List<CategoryItem> items = new ArrayList<CategoryItem>();
			final List<Category> categories = categoryDelegate.findAll(company).getList();
			List<Category> matchCategories = new ArrayList<Category>();
			if(categories != null)
			{
				for(final Category parentCat : categories)
				{
					boolean match = false;
					
					if(parentCat != null)
					{
						final String catName = parentCat.getName();
						match = findCondition(catName);
						
						if(match)
						{
							matchCategories.add(parentCat);
						}
					}
				}
				
				final int max = 6;
				final int nMatch = matchCategories.size();
				if(nMatch > 6)
				{
					matchCategories = matchCategories.subList(0, 6);
				}
				final int ans = max / nMatch; // no. of items to be displayed from category
				
				new HashMap<Integer, List<CategoryItem>>();
				final List<CategoryItem> remainingContainer = new ArrayList<CategoryItem>();
				int cntAdded = 0;
				for(final Category parentCat : matchCategories)
				{
					if(parentCat != null)
					{
						final Order[] orders = { Order.asc("name") };
						final List<CategoryItem> itemList = categoryItemDelegate.findAllWithPaging(company, parentCat, -1, -1, true, orders).getList();
						if(itemList != null && !itemList.isEmpty())
						{
							final int size = itemList.size();
							if(size >= ans)
							{
								items.addAll(itemList.subList(0, ans));
								cntAdded += ans;
								remainingContainer.addAll(itemList.subList(ans, itemList.size()));
							}
							else
							{
								items.addAll(itemList);
								cntAdded += itemList.size();
							}
						}
					}
				}
				if(cntAdded < 6)
				{
					final int left = max - cntAdded;
					if(remainingContainer != null && !remainingContainer.isEmpty())
					{
						logger.info("cnt Added to items: " + cntAdded + " left items " + left);
						items.addAll(remainingContainer.subList(0, left));
					}
				}
			}
			Collections.sort(items, new Comparator<CategoryItem>()
			{
				@Override
				public int compare(CategoryItem m1, CategoryItem m2)
				{
					return m1.getName().compareTo(m2.getName());
				}
			});
			
			request.setAttribute("conditionItems", items);
		}
	}
	
	
	public boolean findCondition(String spltDesc)
	{
		boolean found = false;
		for(final String condition : conditionTags)
		{
			logger.info("condition " + condition + " category name " + spltDesc + " boolean: " + condition.replaceAll(" ", "").equalsIgnoreCase(spltDesc.replaceAll(" ", "")));
			if(condition.replaceAll(" ", "").equalsIgnoreCase(spltDesc.replaceAll(" ", "")))
			{
				logger.info("FOUND SET TO TRUE");
				found = true;
				break;
			}
		}
		return found;
	}
	
	// for hpds online search
	public String onlineSearch() throws Exception
	{
		return SUCCESS;
	}
	
	@Override
	public String execute() throws Exception
	{
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
		
		final Company baseCompany = company;
		if(company != null)
		{
			initializeLanguage();
				
			if(company.getSecondaryCompany() != null)
			{
				company = company.getSecondaryCompany();
				companySettings = company.getCompanySettings();
			}
		}
		if(company.getId() == CompanyConstants.ROBINSONSBANK)
		{
			loadStatisticalRates();
		}
		if(company.getId() == CompanyConstants.DRUGASIA)
		{
			loadConditionItems();
		}
		// if(company.getId() == CompanyConstants.SUBZERO2) {
		String isEnablePopUp = (String) request.getSession().getAttribute("isEnablePopupAD");
		request.setAttribute("isEnablePopupAD", isEnablePopUp);
		// }
		
		request.setAttribute("id", request.getParameter("id"));
		
		// load all items by tags
		if(request.getParameter("id") != null)
		{
			loadAllItemsByTags();
		}
		
		// load all cart items
		if(member != null)
		{
			loadCartItems();
		}
		
		// load all descendant category items
		loadAllDescItems();
		
		// load all category items
		loadAllItems();
		
		// load all products
		if(company.getId() == CompanyConstants.BOYSEN)
		{
			loadAllProducts();
		}
		
		getCartSize();
		
		if(company == null)
		{
			return Action.ERROR;
		}
		createSitemapGenerator();
		
		// load common functionalities
		
		final String displayPage = getDisplayPage(); 
		logger.debug("Display Page: " + displayPage);
		final String result = StringUtils.isNotBlank(displayPage) ? displayPage : CompanyConstants.DEFAULT_COMPANY_PAGE;
		
		loadMenu();
		quote = new YQuote();
		
		final String qid = request.getParameter("qid");
		if(qid != null)
		{
			removeQuotation(qid);
		}
		
		// load request a quotation from eplus.
		if(session.get("quote") != null)
		{
			loadQuotation();
		}
		
		// load a component; if null, no component will be loaded.
		logger.debug(loadComponent());
		component = loadComponent();
		if(component != null)
		{
			logger.debug("LOADING COMPONENT: " + component.getName());
			component.prepareComponent(request);
		}
		
		// load functionalities depending on the company settings
		if(companySettings.getHasFeaturedPages())
		{
			loadFeaturedPages(companySettings.getMaxFeaturedPages());
			loadFormPages(companySettings.getMaxFeaturedPages());
		}
		
		// load featured single pages
		if(companySettings.getHasFeaturedSinglePages())
		{
			loadFeaturedSinglePages(companySettings.getMaxFeaturedSinglePages());
		}
		
		if(companySettings.getHasCategoryMenu())
		{
			loadCategories(companySettings.getMaxCategoryMenu());
		}
		
		if(companySettings.getHasFeaturedProducts())
		{
			loadFeaturedProducts(companySettings.getMaxFeaturedProducts());
		}
		
		if(companySettings.getHasBestSellers())
		{
			loadBestSellers(companySettings.getMaxBestSellers());
		}
		
		if(companySettings.getHasProducts())
		{
			if(pageIsGroup)
			{
				loadProducts(companySettings.getProductsPerPage());
			}
		}
		
		if(companySettings.getHasPackages())
		{
			loadPackages(companySettings.getPackagesPerPage());
		}
		
		if(companySettings.getHasEventCalendar())
		{
			loadEvents();
		}
		
		// load some functionalities based on the parameters given
		if(request.getParameter("item_id") != null)
		{
			findSelectedItem();
		}
		
		if(request.getParameter("item_id") != null && (company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2")))
		{
			final CategoryItem ctItm = categoryItemDelegate.find(Long.parseLong(request.getParameter("item_id")));
			if(ctItm != null)
			{
				checkNoLogInCartContents(ctItm);
				request.setAttribute("group", ctItm.getParentGroup());
				prepareOtherFields(ctItm);
			}
		}
		
		if(company.getName().equals("neltex"))
		{
			loadNoLoginCartItems();
		}
		
		if(request.getParameter("item_id") != null && (company.getId() == CompanyConstants.GURKKA))// || company.getId() == CompanyConstants.GURKKA_TEST))
		{
			final CategoryItem categoryItem = categoryItemDelegate.find(Long.parseLong(request.getParameter("item_id")));
			if(categoryItem != null)
			{
				request.setAttribute("group", categoryItem.getParentGroup());
				prepareOtherFields(categoryItem);
			}
			
			
		}
		
		if(request.getParameter("memberId") != null && request.getParameter("memberId") != "")
		{
			int totalHours = registrationDelegate.findTotalHours(company);
			request.setAttribute("totalHours", totalHours);
			
			Member mem = memberDelegate.find(Long.parseLong(request.getParameter("memberId")));
			
			if(mem == null)
			{
				mem = memberDelegate.find(member.getId());
			}
			
			setInfo(new ArrayList<RegistrationItemOtherField>());
			setInfo(mem.getRegistrationItemOtherFields());
			SortingUtil.sortBaseObject("otherField.sortOrder", true, info);
			if(member != null)
			{// member is logged in
				if(request.getParameter("year") != null)
				{
					info = registrationDelegate.findAll(company, member, request.getParameter("year"), null).getList();
				}
				setMapFieldValues(new TreeMap<String, String>());
				for(final RegistrationItemOtherField e : info)
				{
					getMapFieldValues().put(e.getOtherField().getName(), e.getContent());
				}
				mapRepeatingFieldValues = new TreeMap<String, String>();
				infoRepeating = registrationDelegate.findAllWithIndexing(company, member, request.getParameter("year")).getList();
				if(request.getParameter("year") != null)
				{
					for(final RegistrationItemOtherField e : infoRepeating)
					{
						mapRepeatingFieldValues.put(e.getOtherField().getName() + e.getIndex(), e.getContent());
					}
				}
				else
				{
					totalHours = registrationDelegate.findTotalHours(company);
					request.setAttribute("totalHours", totalHours);
					
					infoRepeating = registrationDelegate.findAllYearWithIndexing(company, request.getParameter("year")).getList();
					int i = 0;
					int j = 0;
					for(final RegistrationItemOtherField e : infoRepeating)
					{
						mapRepeatingFieldValues.put(e.getOtherField().getName() + i, e.getContent());
						j++;
						if(j > 5)
						{
							i++;
							j = 0;
						}
					}
				}
			}
		}
		
		if(company.getId() == CompanyConstants.ECOMMERCE)
		{
			// details for shipping information
			session.put("deliverydate", request.getParameter("deliveryDate"));
			session.put("name", request.getParameter("name"));
			session.put("address1", request.getParameter("address1"));
			session.put("address2", request.getParameter("address2"));
			session.put("province", request.getParameter("province"));
			session.put("city", request.getParameter("city"));
			session.put("zipcode", request.getParameter("zipcode"));
			session.put("mobilenumber", request.getParameter("mobilenumber"));
			session.put("phonenumber", request.getParameter("phonenumber"));
			session.put("email", request.getParameter("email"));
			
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
				session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
			}
			
			if(member != null)
			{
				validatePreOrder();
				
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
			
			// to populate areas and location
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
						case 16:
							cartItemList.get(i).getItemDetail().setShippingPrice(rate1);
							break;
						case 27:
							cartItemList.get(i).getItemDetail().setShippingPrice(rate2);
							break;
						case 37:
							cartItemList.get(i).getItemDetail().setShippingPrice(rate3);
							break;
						case 52:
							cartItemList.get(i).getItemDetail().setShippingPrice(rate4);
							break;
						case 111:
							cartItemList.get(i).getItemDetail().setShippingPrice(rate5);
							break;
						case 117:
							cartItemList.get(i).getItemDetail().setShippingPrice(rate6);
							break;
						case 264:
							cartItemList.get(i).getItemDetail().setShippingPrice(rate7);
							break;
						case 626:
							cartItemList.get(i).getItemDetail().setShippingPrice(rate8);
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
				session.put("shippingPrice", shippingPrice);
			}
		}
		
		if(company.getId() == CompanyConstants.DRUGASIA || company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
				session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
			}
			
			// to populate areas and location
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
			request.setAttribute("ossShippingAreas", ossShippingAreas);
			request.setAttribute("ossShippingLocations", ossShippingLocations);
			request.setAttribute("areas", areas);
			request.setAttribute("locations", locations);
		}
		
		if(company.getId() == CompanyConstants.MDT)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
				session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
			}
			
			// to populate areas and location
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
		
		if(company.getId() == CompanyConstants.ONLINE_DEPOT)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
			}
			
			// to populate areas and location
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
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.AYROSOHARDWARE)) {
			
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
		
		if(company.getId() == CompanyConstants.GIFTCARD)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
			}
		}
		
		if(company.getId() == CompanyConstants.KORPHILIPPINES)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
				session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
			}
			
			if(request.getParameter("item_id") != null)
			{
				final CategoryItem categoryItem = categoryItemDelegate.find(Long.parseLong(request.getParameter("item_id")));
				final Category category = categoryDelegate.find(categoryItem.getParent().getId());
				
				request.setAttribute("parentCategory", category);
			}
		}
		
		if(company.getId() == CompanyConstants.IAVE && request.getParameter("memberId") == null)
		{
			final int totalHours = registrationDelegate.findTotalHours(company);
			request.setAttribute("totalHours", totalHours);
			final Member mem = memberDelegate.findIaveMember(company);
			setInfo(new ArrayList<RegistrationItemOtherField>());
			setInfo(mem.getRegistrationItemOtherFields());
			SortingUtil.sortBaseObject("otherField.sortOrder", true, info);
			if(request.getParameter("year") != null)
			{
				info = registrationDelegate.findAll(company, member, request.getParameter("year"), null).getList();
			}
			setMapFieldValues(new TreeMap<String, String>());
			for(final RegistrationItemOtherField e : info)
			{
				getMapFieldValues().put(e.getOtherField().getName(), e.getContent());
			}
			mapRepeatingFieldValues = new TreeMap<String, String>();
			infoRepeating = registrationDelegate.findAllYearWithIndexing(company, request.getParameter("year")).getList();
			int i = 0;
			int j = 0;
			for(final RegistrationItemOtherField e : infoRepeating)
			{
				mapRepeatingFieldValues.put(e.getOtherField().getName() + i, e.getContent());
				j++;
				if(j > 5)
				{
					i++;
					j = 0;
				}
			}
		}
		
		if(company.getId() == CompanyConstants.EVEREST)
		{
			if(request.getParameter("itemPackage") != null)
			{
				confirmInfo();
			}
			if(request.getParameter("productPackage") != null)
			{
				submitInfo();
			}
			
		}
		if(request.getParameter("searchkeyword") != null && request.getParameter("in") != null)
		{
			// search category items in group
			final Category cat = new Category();
			final String searchkeyword = request.getParameter("searchkeyword").trim().replaceAll("\\s+", " ");
			final long id = Long.parseLong(request.getParameter("in"));
			final Group group = groupDelegate.find(id);
			final List<CategoryItem> resultlist = categoryItemDelegate.searchByGroup(searchkeyword, company, group).getList();
			cat.setItems(resultlist);
			cat.setEnabledItems(resultlist);
			
			Collections.sort(cat.getItems(), new Comparator<CategoryItem>()
			{
				@Override
				public int compare(CategoryItem cat1, CategoryItem cat2)
				{
					return cat1.getName().compareToIgnoreCase(cat2.getName());
				}
			});
			
			
			request.setAttribute("category", cat);
			request.setAttribute("searchcategory", cat);
			request.setAttribute("group", group);
			
			if(cat.getEnabledItems().size() > 0) {
				final int pageNumber = getPageNumber();
				int itemsPerPage = 8;
				final List<CategoryItem> searchCategoryItems = new ArrayList<CategoryItem>();
				int count = 1;
				for(int i = (pageNumber - 1) * itemsPerPage; i < cat.getEnabledItems().size(); i++)
				{
					if(count <= itemsPerPage)
						searchCategoryItems.add(cat.getEnabledItems().get(i));
					count++;
				}
				request.setAttribute("searchCategoryItems", searchCategoryItems);
				final PagingUtil searchCategoryItemsPagingUtil = new PagingUtil(cat.getEnabledItems().size(), itemsPerPage, pageNumber, itemsPerPage);
				request.setAttribute("searchCategoryItemsPagingUtil", searchCategoryItemsPagingUtil);
			}
			
		}
		
		if(request.getParameter("group_id") != null)
		{
			findSelectedGroup();
		}
		
		// FIXME: groupItems is the parameter used for group id when there is a
		// parameter being used in the code for group id; this is confusing
		if(request.getParameter("groupItems") != null)
		{
			final long id = Long.parseLong(request.getParameter("groupItems"));
			final Group group = groupDelegate.find(id);
			if(group.getLoginRequired() && member == null)
			{
				return Action.LOGIN;
			}
			else if(group.getLoginRequired() && member != null)
			{
				logger.debug("Already logged in");
			}
		}
		
		
		if(request.getParameter("category_id") != null)
		{
			try
			{
				final long id = Long.parseLong(request.getParameter("category_id"));
				final Category category = categoryDelegate.find(id);
				final List<CategoryItem> childrenItems = new ArrayList<CategoryItem>();
				
				if(!company.getName().equalsIgnoreCase("hoegh")) {
					Collections.sort(category.getEnabledItems(), new Comparator<CategoryItem>()
					{
						@Override
						public int compare(CategoryItem cat1, CategoryItem cat2)
						{
							return cat1.getName().compareToIgnoreCase(cat2.getName());
						}
					});
				}
				
				if(category.getCompany().equals(company) && !category.getHidden())
				{
					final Order[] orders = { Order.asc("sortOrder") };
					final List<CategoryItem> catItem = categoryItemDelegate.findAll(company, category, orders, true).getList();
					request.setAttribute("catEnabledItems", catItem);
					request.setAttribute("category", category);
					
					// list of the items of the subcategories of the category
					for(int i = 0; i < category.getChildrenCategory().size(); i++)
					{
						for(int j = 0; j < category.getChildrenCategory().get(i).getEnabledItems().size(); j++)
						{
							childrenItems.add(category.getChildrenCategory().get(i).getEnabledItems().get(j));
						}
					}
					request.setAttribute("childrenItems", childrenItems);
					
					
					if(company.getName().equals(CompanyConstants.KUYSEN)) {
						final int pageNumber = getPageNumber();
						int itemsPerPage = 8;
						// default item list
						List<CategoryItem> categoryItem = new ArrayList<CategoryItem>();
						if(category.getChildrenCategory().size() > 0) {
							if(category.getChildrenCategory().get(0).getChildrenCategory().size() > 0) {
								List<Category> children = category.getChildrenCategory().get(0).getChildrenCategory();
								for(Category child : children) {
									categoryItem.addAll(child.getEnabledItems());
								}
							} else {
								categoryItem = category.getChildrenCategory().get(0).getEnabledItems();
							}
						}
						//category item
						final List<CategoryItem> newCategoryItem = new ArrayList<CategoryItem>();
						for(CategoryItem item : categoryItem) {
							if(!item.getName().trim().isEmpty()) {
								newCategoryItem.add(item);
							}
						}
						
						Collections.sort(newCategoryItem, new Comparator<CategoryItem>() {
							public int compare(CategoryItem c1, CategoryItem c2) {
								return c1.getDescriptionWithoutTags().compareTo(c2.getDescriptionWithoutTags());
							}
						});
						
						if(newCategoryItem.size() > 0) {
							final List<CategoryItem> newChildrenItems = new ArrayList<CategoryItem>();
							int count = 1;
							for(int i = (pageNumber - 1) * itemsPerPage; i < newCategoryItem.size(); i++)
							{
								if(count <= itemsPerPage)
									newChildrenItems.add(newCategoryItem.get(i));
								count++;
							}
							request.setAttribute("defaultChildrenItems", newChildrenItems);
							final PagingUtil defaultChildrenPagingUtil = new PagingUtil(newCategoryItem.size(), itemsPerPage, pageNumber, itemsPerPage);
							request.setAttribute("defaultChildrenPagingUtil", defaultChildrenPagingUtil);
						}
						
						//category item
						final List<CategoryItem> newCatItem = new ArrayList<CategoryItem>();
						for(CategoryItem item : catItem) {
							if(!item.getName().trim().isEmpty()) {
								newCatItem.add(item);
							}
						}
						
						Collections.sort(newCatItem, new Comparator<CategoryItem>() {
							public int compare(CategoryItem c1, CategoryItem c2) {
								return c1.getDescriptionWithoutTags().compareTo(c2.getDescriptionWithoutTags());
							}
						});
						
						if(newCatItem.size() > 0) {
							final List<CategoryItem> newCatEnabledItems = new ArrayList<CategoryItem>();
							int count = 1;
							for(int i = (pageNumber - 1) * itemsPerPage; i < newCatItem.size(); i++)
							{
								if(count <= itemsPerPage)
									newCatEnabledItems.add(newCatItem.get(i));
								count++;
							}
							request.setAttribute("newCatEnabledItems", newCatEnabledItems);
							final PagingUtil newCatEnabledItemsPagingUtil = new PagingUtil(newCatItem.size(), itemsPerPage, pageNumber, itemsPerPage);
							request.setAttribute("newCatEnabledItemsPagingUtil", newCatEnabledItemsPagingUtil);
						}
						
						//item of subcategory of category
						final List<CategoryItem> newChildrenItem = new ArrayList<CategoryItem>();
						for(CategoryItem item : childrenItems) {
							if(!item.getName().trim().isEmpty()) {
								newChildrenItem.add(item);
							}
						}
						
						Collections.sort(newChildrenItem, new Comparator<CategoryItem>() {
							public int compare(CategoryItem c1, CategoryItem c2) {
								return c1.getDescriptionWithoutTags().compareTo(c2.getDescriptionWithoutTags());
							}
						});
						
						if(newChildrenItem.size() > 0) {
							final List<CategoryItem> newChildrenItems = new ArrayList<CategoryItem>();
							int count = 1;
							for(int i = (pageNumber - 1) * itemsPerPage; i < newChildrenItem.size(); i++)
							{
								if(count <= itemsPerPage)
									newChildrenItems.add(newChildrenItem.get(i));
								count++;
							}
							request.setAttribute("newChildrenItems", newChildrenItems);
							final PagingUtil newChildrenPagingUtil = new PagingUtil(newChildrenItem.size(), itemsPerPage, pageNumber, itemsPerPage);
							request.setAttribute("newChildrenPagingUtil", newChildrenPagingUtil);
						}
					}
					
				}
				
				if(request.getParameter("searchkeyword") != null)
				{
					// search within items of category in name and description
					request.setAttribute("category", searchItemsInCategory(category));
				}
				
				if(category.getParentGroup().getLoginRequired())
				{
					if(member == null)
					{
						return Action.LOGIN;
					}
					else
					{
						logger.debug("Already logged in");
					}
				}
				
				if(category.getChildrenCategory() != null)
				{
					final int pageNumber = getPageNumber();
					int itemSize = 0;
					final int max = companySettings.getProductsPerPage();
					
					itemSize = categoryDelegate.findAllChildrenOfChildrenCategory(company, category, -1, -1, Order.asc("name")).getSize();
					final List<Category> childrenOfChildrenCategory = categoryDelegate.findAllChildrenOfChildrenCategory(company, category, max, pageNumber - 1, Order.asc("name")).getList();
					
					request.setAttribute("cocCategory", childrenOfChildrenCategory);
					if(itemSize > max)
					{
						final PagingUtil pagingChildCatUtilDesc = new PagingUtil(itemSize, max, pageNumber, 5);
						request.setAttribute("pagingChildCatUtilDesc", pagingChildCatUtilDesc);
						request.setAttribute("categoryId", request.getParameter("category_id"));
					}
				}
			}
			catch(final Exception e)
			{
				logger.fatal("Cannot find category. ", e);
			}
		}
		
		if(request.getParameter("searchTag") != null)
		{
			findItemsByTags();
		}
		
		if(request.getParameter("pageKeyword") != null)
		{
			findPages();
		}
		if(request.getParameter("name") != null)
		{
			searchName();
		}
		
		if(request.getParameter("brand_id") != null)
		{
			findSelectedBrand();
		}
		
		if(request.getParameter("package_id") != null)
		{
			findSelectedPackage();
		}
		/* search page and items */
		if(request.getParameter("keywordAll") != null)
		{
			searchAll();
		}
		
		// load all featured brands
		if(companySettings.getShowBrands())
		{
			loadFeaturedBrands();
			loadAllBrands(companySettings.getProductsPerPage());
		}
		// request.setAttribute("title", company.getTitle());
		loadAllRootCategories();
		loadEventGroups();
		boolean isInDescription = false;
		if(!StringUtils.isEmpty(request.getParameter("isInDescription")))
		{
			if(request.getParameter("isInDescription").equals("true"))
			{
				isInDescription = true;
			}
		}
		
		if(keyword != null && keyword.trim().length() != 0)
		{
			// knoxout2 system search 
			if (request.getParameter("keyNational") != null && request.getParameter("keyNational").equals("International")) {
				if (company.getName().equals("knoxout2")) {
					isInDescription = true;
				}
			}	
			
			if (company.getName().equals("hiprecisiononlinestore")) {
				isInDescription = true;
			}
			
			searchList = categoryItemDelegate.search(keyword.trim(), company, isInDescription, true);
			
			List<CategoryItem> categoryItemImageResult = categoryItemDelegate.findAllByImageKeyword(company, keyword);
			
			request.setAttribute("categoryItemImageResult", categoryItemImageResult);
			
			if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
			{ /** remove cola. not searchable daw*/
				final Iterator<CategoryItem> iterator = searchList.iterator();
				while(iterator.hasNext())
				{
					final CategoryItem item = (CategoryItem) iterator.next();
					if(StringUtils.containsIgnoreCase("1 liter cola", item.getName()))
					{
						iterator.remove();
					}
				}
			}
			
			if(company.getId() == CompanyConstants.DRUGASIA)
			{
				searchList = categoryItemDelegate.searchByCatSubCatClass(keyword.trim(), company);
				final HashSet hs = new HashSet();
				hs.addAll(searchList);
				searchList.clear();
				searchList.addAll(hs);
			}
			
			if(company.getId() == CompanyConstants.ECOMMERCE)
			{
				searchList = categoryItemDelegate.searchByBrand(keyword.trim(), company);
				final HashSet hs = new HashSet();
				hs.addAll(searchList);
				searchList.clear();
				searchList.addAll(hs);
			}
			loadSearchListWithPaging(isInDescription);
		}
		
		if(request.getParameter("regionkey") != null)
		{
			final String branchKey = request.getParameter("branchkey");
			final String regionKey = request.getParameter("regionkey");
			
			if(company.getId() == CompanyConstants.PUREGOLD)
			{
				final Category priceClub = categoryDelegate.find(3068L);
				final Category junior = categoryDelegate.find(3069L);
				final Category extra = categoryDelegate.find(3070L);
				final List<String> keywords = new ArrayList<String>();
				
				if(StringUtils.isNotBlank(branchKey))
				{
					keywords.add(branchKey.replace("+", " "));
				}
				
				if(StringUtils.isNotBlank(regionKey))
				{
					keywords.add(regionKey.replace("+", " "));
				}
				
				final List<CategoryItem> priceClubItems = categoryItemDelegate.search(company, priceClub, keywords);
				final List<CategoryItem> juniorItems = categoryItemDelegate.search(company, junior, keywords);
				final List<CategoryItem> extraItems = categoryItemDelegate.search(company, extra, keywords);
				
				request.setAttribute("priceClubItems", priceClubItems);
				request.setAttribute("juniorItems", juniorItems);
				request.setAttribute("extraItems", extraItems);
			}
			
			if(company.getId() == CompanyConstants.PUREGOLD_TEST)
			{
				final Category priceClub = categoryDelegate.find(6064L);
				final Category junior = categoryDelegate.find(6065L);
				final Category extra = categoryDelegate.find(6066L);
				final List<String> keywords = new ArrayList<String>();
				
				if(StringUtils.isNotBlank(branchKey))
				{
					keywords.add(branchKey.replace("+", " "));
				}
				
				if(StringUtils.isNotBlank(regionKey))
				{
					keywords.add(regionKey.replace("+", " "));
				}
				
				final List<CategoryItem> priceClubItems = categoryItemDelegate.search(company, priceClub, keywords);
				final List<CategoryItem> juniorItems = categoryItemDelegate.search(company, junior, keywords);
				final List<CategoryItem> extraItems = categoryItemDelegate.search(company, extra, keywords);
				
				request.setAttribute("priceClubItems", priceClubItems);
				request.setAttribute("juniorItems", juniorItems);
				request.setAttribute("extraItems", extraItems);
			}
		}
		
		company = baseCompany;
		
		final List<CategoryItem> itemList = categoryItemDelegate.findAllSortedByDate(company).getList();
		request.setAttribute("itemList", itemList);
		
		try
		{
			final String categoryIdString = request.getParameter("category_id");
			final long categoryId = Long.parseLong(categoryIdString);
			final Category category = categoryDelegate.find(categoryId);
			final List<CategoryItem> itemListByCategory = categoryItemDelegate.getAllCatItemsNoPaging(company, category, null).getList();
			
			if(category.getCompany().equals(company))
			{
				request.setAttribute("itemListByCategory", itemListByCategory);
			}
		}
		catch(final Exception e)
		{
		}
		
		try
		{
			final String groupIdString = request.getParameter("group_id");
			final long groupId = Long.parseLong(groupIdString);
			final Group group = groupDelegate.find(groupId);
			final List<CategoryItem> itemListByGroup = categoryItemDelegate.findAllByGroup(company, group).getList();
			
			if(group.getCompany().equals(company))
			{
				request.setAttribute("itemListByGroup", itemListByGroup);
			}
		}
		catch(final Exception e)
		{
		}
		
		try
		{
			final List<CategoryItem> allItems = categoryItemDelegate.findAll(company).getList();
			
			if(company.getId() == CompanyConstants.GURKKA)// || company.getId() == CompanyConstants.GURKKA_TEST)
			{
				SortingUtil.sortBaseObject("name", true, allItems);
			}
			
			request.setAttribute("allItems", allItems);
		}
		catch(final Exception e)
		{
		}
		
		try
		{
			final String brandIdString = request.getParameter("brand_id");
			final long brandId = Long.parseLong(brandIdString);
			final Brand brand = brandDelegate.find(brandId);
			final List<CategoryItem> itemListByBrand = categoryItemDelegate.findAllByBrand(company, brand);
			
			if(brand.getCompany().equals(company))
			{
				request.setAttribute("itemListByBrand", itemListByBrand);
			}
		}
		catch(final Exception e)
		{
		}
		
		try
		{
			breadcrumbs = categoryDelegate.findAll(company).getList();
			request.setAttribute("breadcrumbs", breadcrumbs);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
		
		if(company.getName().equalsIgnoreCase("uniorient"))
		{
			final List<CategoryItem> itemList2 = categoryItemDelegate.findAll(company).getList();
			request.setAttribute("itemList", itemList2);
		}
		
		if(company.getName().equalsIgnoreCase("APC") && pageName.equalsIgnoreCase("asap") && member != null)
		{
			showAllReceipts();
		}
		
		if(company.getName().equalsIgnoreCase("westerndigital") && member != null)
		{
			showAllReceipts();
		}
		
		/* The method below was moved to RhiDispatcherAction.java */
		
		//if(company.getName().equalsIgnoreCase("rhi"))
		//{
//			groupDelegate.find(Long.parseLong("364"));
		//	final List<Category> categoriesList = categoryDelegate.findAll();
		//	request.setAttribute("categoriesList", categoriesList);
		//	
		//	final List<ItemFile> itemFile = itemFileDelegate.findAll();
		//	request.setAttribute("itemFilesList", itemFile);
		//}
		
		if(company.getName().equalsIgnoreCase("APC") && member == null)
		{
			if(pageName.equalsIgnoreCase("promo") || pageName.equalsIgnoreCase("thankyou"))
			{
				pageName = "asap";
			}
			boolean allowed = false;
			final String allowedPageToView[] = { "index", "home", "asap", "contact", "registration", "mechanics", "redemption" };// when the user is not loggedd In
			for(final String page : allowedPageToView)
			{
				if(page.equalsIgnoreCase(pageName))
				{
					allowed = true;
				}
			}
			if(!allowed)
			{
				return ERROR;
			}
			else
			{
				return SUCCESS;
			}
		}
		
		if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2")))
		{
			request.setAttribute("latestNews", multiPageDelegate.findJsp(company, "news"));
		}
		
		if(company.getName().equalsIgnoreCase("hpdsked"))
		{
			
			groupDelegate.find(Long.parseLong("205"));
			final List<Category> categories = categoryDelegate.findAll();
			final List<Category> categoriesList = categoryDelegate.findAll();
			final List<Category> sortedCategoriesList = sortList(categoriesList);// sort categoriesList
			final List<CategoryItem> items = categoryItemDelegate.findAll(company).getList();
			request.setAttribute("allItems", items);
			request.setAttribute("allCategories", categories);
			request.setAttribute("sortedCategories", sortedCategoriesList);
			
		}
		
		// if the current referral status is APPROVED and the client request to claim his reward
		if(company.getName().equalsIgnoreCase(CompanyConstants.CANCUN))
		{
			if(actionMode != null && referralId != null)
			{
				
				final Referral ref = referralDelegate.find(new Long(referralId));
				
				if(ref != null)
				{
					if(actionMode.equalsIgnoreCase("delete"))
					{
						referralDelegate.delete(ref);
						
					}
				}
				
			}
		}
		if(company.getName().equalsIgnoreCase("pinoyhomecoming"))
		{
			weatherForecastForManila();
		}
		logger.debug("execute() result: " + result);
		return result;
	}
	
	private void loadNoLoginCartItems() {
		// TODO Auto-generated method stub
		
		List<CategoryItem> catItems = (List<CategoryItem>) session.get("noLoginCartItems");
		session.put("noLoginCartItems", catItems);
		
	}

	public List<Category> sortList(List<Category> toBeSorted)
	{
		Category temp;
		for(int i = 1; i < toBeSorted.size(); i++)
		{
			for(int j = 0; j < toBeSorted.size() - i; j++)
			{
				if(0 < toBeSorted.get(j).getName().compareTo(toBeSorted.get(j + 1).getName()))
				{
					temp = toBeSorted.get(j);
					toBeSorted.set(j, toBeSorted.get(j + 1));
					toBeSorted.set(j + 1, temp);
				}
			}
		}
		return toBeSorted;
	}
	
	protected void prepareOtherFields(CategoryItem item)
	{
		final List<OtherField> otherFields = item.getParentGroup().getOtherFields();
		final HashMap<Long, String> temp = new HashMap<Long, String>();
		if(otherFields != null)
		{
			if(otherFields.size() > 0)
			{
				for(int i = 0; i < otherFields.size(); i++)
				{
					final CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
					final CategoryItemOtherField otherField = categoryItemOtherFieldDelegate.findByCategoryItemOtherField(company, item, otherFields.get(i));
					if(otherField != null)
					{
						temp.put(otherFields.get(i).getId(), otherField.getContent());
					}
					else
					{
						temp.put(otherFields.get(i).getId(), "");
					}
				}
			}
			request.setAttribute("otherFields", temp);
		}
	}
	
	protected void prepareOtherFields(List<CategoryItem> items)
	{
		final HashMap<Long, Integer> temp = new HashMap<Long, Integer>();
		for(final CategoryItem item : items)
		{
			final Set<CategoryItemOtherField> itemOtherFields = item.getCategoryItemOtherFields();
			if(itemOtherFields != null && !itemOtherFields.isEmpty())
			{
				for(final CategoryItemOtherField itemOtherField : itemOtherFields)
				{
					final OtherField otherField = otherFieldDelegate.find(itemOtherField.getOtherField().getId());
				}
			}
		}
		request.setAttribute("inventoryQty", temp);
	}
	
	protected List<MemberFileItems> showAllReceipts()
	{
		List<MemberFileItems> receiptList;
		receiptList = new ArrayList<MemberFileItems>();
		List<MemberFile> memberFile = null;
		final MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
		
		memberFile = memberFileDelegate.findAll(member);
		for(final MemberFile memFile : memberFile)
		{
			final MemberFileItems fileInfo = receiptImageDelegate.findMemberFileItem(company, memFile.getId());
			if(fileInfo != null)
			{
				receiptList.add(fileInfo);
			}
		}
		Collections.reverse(receiptList);
		request.setAttribute("receiptList", receiptList);
		return receiptList;
	}
	
	protected void checkNoLogInCartContents(CategoryItem ctItm)
	{
		if(session.get("cartForNoLogIn") != null)
		{
			final List<CategoryItem> catItems = (List<CategoryItem>) session.get("noLogInCartItems");
			final ArrayList<CategoryItem> currentItem = new ArrayList<CategoryItem>();
			// List<CategoryItem>
			if(catItems != null && catItems.size() != 0)
			{
				for(final CategoryItem cI : catItems)
				{
					// This code is for update
					if(cI.getId().toString().equalsIgnoreCase(ctItm.getId().toString()))
					{
						request.setAttribute("message", "This Item is already in Cart");
						currentItem.add(cI);
					}
				}
			}
			request.setAttribute("currentItem", currentItem);
		}
	}
	
	protected void searchName()
	{
		final String keyname = request.getParameter("name");
		request.setAttribute("name", keyname);
		final List<CategoryItem> firstList = categoryItemDelegate.searchByName(keyname, "first", company);
		final List<CategoryItem> lastList = categoryItemDelegate.searchByName(keyname, "last", company);
		request.setAttribute("firstResults", firstList);
		request.setAttribute("lastResults", lastList);
		
	}
	
	protected void findItemsByTags()
	{
		final String tag = request.getParameter("searchTag");
		request.setAttribute("searchTag", tag);
		final List<CategoryItem> listItems = categoryItemDelegate.searchbySearchTags(tag, company).getList();
		request.setAttribute("searchResults", listItems);
	}
	
	protected void findPages()
	{
		final String keyword = request.getParameter("pageKeyword").trim().replaceAll("\\s+", " ");
		request.setAttribute("pageKeyword", keyword);
		
		final List<MultiPage> multiResults = multiPageDelegate.findPageByKeyWord(keyword, company).getList();
		final List<SinglePage> singleResults = new ArrayList<SinglePage>();
		List<SinglePage> tmpPages = null;
		
		if(multiResults != null)
		{
			for(final MultiPage mp : multiResults)
			{
				tmpPages = singlePageDelegate.findByKeyword(company, mp, keyword).getList();
				
				if(tmpPages != null)
				{
					for(final SinglePage sp : tmpPages)
					{
						singleResults.add(sp);
					}
				}
			}
		}
		
		tmpPages = singlePageDelegate.findByKeyword(company, null, keyword).getList();
		
		if(tmpPages != null)
		{
			for(final SinglePage sp : tmpPages)
			{
				singleResults.add(sp);
			}
		}
		request.setAttribute("pageResults", singleResults);
	}
	
	protected void loadSearchListWithPaging(boolean isInDescription)
	{
		try
		{
			int itemSize = 0;
			final int max = companySettings.getProductsPerPage();
			final int pageNumber = getPageNumber();
			final String tag = request.getParameter("tag");
			
			if(StringUtils.isEmpty(tag))
			{
				if(companySettings.getHasPackages())
				{
					final List<CategoryItemPackageWrapper> searchListIncludePackageWithPaging = categoryItemDelegate.searchIncludePackageWithPaging(pageNumber - 1, max, keyword, company,
							isInDescription, true);
					itemSize = categoryItemDelegate.countSearchIncludePackage(keyword, company, isInDescription);
					request.setAttribute("searchListIncludePackageWithPaging", searchListIncludePackageWithPaging);
					
					if(itemSize < 1 && company.getId() == CompanyConstants.HIPRECISION_DATA_SEARCH)
					{
						ObjectList<CategoryItem> searchListWithPaging = categoryItemDelegate.searchWithPaging(pageNumber - 1, max, keyword.trim(), company, isInDescription, true);
						searchListWithPaging = categoryItemDelegate.findAllByGroupAndKeywordWithPaging(-1, -1, keyword, company, false, null, null, null, null);
						
						itemSize = searchListWithPaging.getTotal();
						
						request.setAttribute("searchListWithPaging", searchListWithPaging.getList());
					}
				}
				else
				{
					ObjectList<CategoryItem> searchListWithPaging = categoryItemDelegate.searchWithPaging(pageNumber - 1, max, keyword.trim(), company, isInDescription, true);
					
					if(company.getId() == CompanyConstants.HIPRECISION_DATA_SEARCH)
					{
						searchListWithPaging = categoryItemDelegate.findAllByGroupAndKeywordWithPaging(-1, -1, keyword, company, false, null, null, null, null);
					}
					
					itemSize = searchListWithPaging.getTotal();
					
					request.setAttribute("searchListWithPaging", searchListWithPaging.getList());
				}
			}
			else
			{
				request.setAttribute("tag", tag.trim());
				ObjectList<CategoryItem> searchListWithPaging = null;
				searchListWithPaging = categoryItemDelegate.searchByTagAndName(pageNumber - 1, max, keyword.trim(), tag.trim(), company, isInDescription);
				
				if(company.getId() == CompanyConstants.HIPRECISION_DATA_SEARCH)
				{
					searchListWithPaging = categoryItemDelegate.findAllByGroupAndKeywordWithPaging(-1, -1, keyword, company, false, null, null, null, null);
				}
				
				itemSize = searchListWithPaging.getTotal();
				request.setAttribute("searchListWithPaging", searchListWithPaging.getList());
			}
			final PagingUtil pagingUtilDesc = new PagingUtil(itemSize, max, pageNumber, 5);
			request.setAttribute("pagingUtilDesc", pagingUtilDesc);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadStatisticalRates()
	{
		final TrustProductStatisticsAction tpsa = new TrustProductStatisticsAction();
		final List<Rates> parentRates = ratesDelegate.getParentFund(company).getList();
		
		for(int i = 0; i < parentRates.size(); i++)
		{
			try
			{
				request.setAttribute("blueFundXML", tpsa.setBlueFundXML());
				request.setAttribute("greenFundXML", tpsa.setGreenFundXML());
				request.setAttribute("balancedFundXML", tpsa.setBalancedFundXML());
				request.setAttribute("moneyMarketFundXML", tpsa.setMoneyMarketFundXML());
			}
			catch(final Exception e)
			{
				logger.debug("Cannot find parent rates...");
			}
		}
		
		Rates rates, parent;
		parent = ratesDelegate.getParentById(Long.parseLong("1"));
		rates = ratesDelegate.getLatestByParent(parent, Order.desc("createdOn"));
		request.setAttribute("blueFund", rates);
		
		parent = ratesDelegate.getParentById(Long.parseLong("2"));
		rates = ratesDelegate.getLatestByParent(parent, Order.desc("createdOn"));
		request.setAttribute("greenFund", rates);
		
		parent = ratesDelegate.getParentById(Long.parseLong("3"));
		rates = ratesDelegate.getLatestByParent(parent, Order.desc("createdOn"));
		request.setAttribute("balancedFund", rates);
		
		parent = ratesDelegate.getParentById(Long.parseLong("12939"));
		rates = ratesDelegate.getLatestByParent(parent, Order.desc("createdOn"));
		request.setAttribute("moneyMarketFund", rates);
		
		final Rates parentBuy = ratesDelegate.getParentById(Long.parseLong("4"));
		final Rates parentSell = ratesDelegate.getParentById(Long.parseLong("5"));
		final String[] currencies = { "USD", "CNY", "EUR", "GBP", "JPY", "SGD", "HKD" };
		
		for(int i = 0; i < currencies.length; i++)
		{
			if(ratesDelegate.getLatestRates(parentBuy, "CNY") != null)
			{
				rates = ratesDelegate.getLatestRates(parentBuy, currencies[i]);
				request.setAttribute("buy" + currencies[i], rates.getValue());
				
				rates = ratesDelegate.getLatestRates(parentSell, currencies[i]);
				request.setAttribute("sell" + currencies[i], rates.getValue());
			}
		}
		
		final Date foreignExchangeDate = ratesDelegate.getLatestRates(parentBuy, "USD").getCreatedOn();
		request.setAttribute("foreignExchangeDate", foreignExchangeDate);
		
		Date trustProductDate = new Date();
		
		parent = ratesDelegate.getParentById(Long.parseLong("1"));
		rates = ratesDelegate.getLatestByParent(parent, Order.desc("createdOn"));
		trustProductDate = rates.getCreatedOn();
		
		parent = ratesDelegate.getParentById(Long.parseLong("2"));
		rates = ratesDelegate.getLatestByParent(parent, Order.desc("createdOn"));
		if(trustProductDate.before(rates.getCreatedOn()))
		{
			trustProductDate = rates.getCreatedOn();
		}
		
		parent = ratesDelegate.getParentById(Long.parseLong("3"));
		rates = ratesDelegate.getLatestByParent(parent, Order.desc("createdOn"));
		if(trustProductDate.before(rates.getCreatedOn()))
		{
			trustProductDate = rates.getCreatedOn();
		}
		
		parent = ratesDelegate.getParentById(Long.parseLong("12939"));
		rates = ratesDelegate.getLatestByParent(parent, Order.desc("createdOn"));
		if(rates != null && trustProductDate.before(rates.getCreatedOn()))
		{
			trustProductDate = rates.getCreatedOn();
		}
		request.setAttribute("trustProductDate", trustProductDate);
	}
	
	protected Category searchItemsInCategory(Category category)
	{
		final String searchkeyword = request.getParameter("searchkeyword");
		for(int i = 0; i < category.getItems().size(); i++)
		{
			if(!(category.getItems().get(i).getName().toUpperCase().contains(searchkeyword.toUpperCase()) || category.getItems().get(i).getDescription().toUpperCase()
					.contains(searchkeyword.toUpperCase())))
			{
				category.getItems().remove(category.getItems().get(i));
				i--;
			}
		}
		return category;
	}
	
	protected Component loadComponent()
	{
		if(pageName != null) {
		if(pageName.equals("calendarevents"))
		{
			
			return new EventCalendarComponent(company);
		}
		if(pageName.equals("investor") && company.getName().equals("terra"))
		{
			
			final QuoteComponent quoteComponent = new QuoteComponent(company);
			quoteComponent.prepareComponent(request);
			quote = quoteComponent.getQuote();
			return quoteComponent;
		}
		}
		return null;
	}
	
	protected String parsePageName()
	{
		final String uri = request.getRequestURI();
		final String[] uriSeparated = uri.split("/");
		final String last = uriSeparated[uriSeparated.length - 1];
		pageName = last.toLowerCase();
		pageName = pageName.replace(".do", "");
		
		logger.debug("Page Name: " + pageName);
		
		return pageName;
	}
	
	protected void createSitemapGenerator()
	{
		logger.debug("createSitemapGenerator");
		request.setAttribute("sitemapGenerator", new SiteMapGenerator(company));
	}
	
	protected String getDisplayPage()
	{
		final Map<String, String> meta = new HashMap<String, String>();
		final MemberLog memberLog = new MemberLog();
		PageType pageType = PageType.SINGLEPAGE;
		Page page = null;
		SinglePage multiPageItem = null;
		
		// search for a single page
		page = singlePageDelegate.findJsp(company, pageName);
		
		if(company.getId() == CompanyConstants.SUBZERO2)
		{
			String feature = "";
			String options = "";
			String specs = "";
			String acc = "";
			String[] temp;
			final String delim = "<p><strong>";
			if(request.getParameter("item_id") != null)
			{
				final CategoryItem it = categoryItemDelegate.find(Long.parseLong(request.getParameter("item_id")));
				
				temp = it.getDescription().split(delim);
				for(int i = 0; i < temp.length; i++)
				{
					if(temp[i].contains("Features"))
					{
						feature += "<p><strong>" + temp[i];
						request.setAttribute("features", feature);
					}
					if(temp[i].contains("Options"))
					{
						options = "<p><strong>" + temp[i];
						request.setAttribute("options", options);
					}
					if(temp[i].contains("Specification"))
					{
						specs = "<p><strong>" + temp[i];
						request.setAttribute("specs", specs);
					}
					if(temp[i].contains("Accessories"))
					{
						acc = "<p><strong>" + temp[i];
						request.setAttribute("acc", acc);
					}
				}
			}
		}
		// search for a multi page if not a single page
		if(page == null)
		{
			page = multiPageDelegate.findJsp(company, pageName);
			pageType = PageType.MULTIPAGE;
		}
		
		// search for a form page if not a multi page
		if(page == null)
		{
			page = formPageDelegate.findJsp(company, pageName);
			pageType = PageType.FORMPAGE;
		}
		
		// search for a group. assume that a group is a single page so we can display it on the front end
		if(page == null)
		{
			final Group group = groupDelegate.find(company, pageName);
			if(group != null)
			{
				pageIsGroup = true;
				page = new SinglePage();
				page.setJsp(group.getName().toLowerCase());
				page.setName(group.getName());
				page.setTitle("");
			}
			pageType = PageType.SINGLEPAGE;
		}
		
		// allow some pages even if they dont exist on the admin page
		if(page == null)
		{
			if(ALLOWED_PAGES.contains(pageName))
			{
				page = new SinglePage();
				if(pageName.equals("printerfriendly"))
				{
					page.setJsp("../../../" + pageName);
				}
				else
				{
					page.setJsp(pageName);
				}
				page.setName(pageName);
				page.setTitle("");
			}
			pageType = PageType.SINGLEPAGE;
		}
		
		// search for the default home page
		if(page == null)
		{
			final List<SinglePage> pages = singlePageDelegate.findAll(company).getList();
			for(final SinglePage s : pages)
			{
				if(s.getIsHome())
				{
					page = s;
					pageType = PageType.SINGLEPAGE;
					break;
				}
			}
		}
		
		if(page == null)
		{
			final List<SinglePage> allSP = singlePageDelegate.findAll(company).getList();
			if(CollectionUtils.isNotEmpty(allSP))
			{
				for(SinglePage singlePage : allSP)
				{
					if(CompanyConstants.DEFAULT_COMPANY_PAGE.equalsIgnoreCase(singlePage.getName()))
					{
						page = singlePage;
						pageType = PageType.SINGLEPAGE;
						break;
					}
				}
				/** if 'home' page is not found. get the first element. */
				if(page == null)
				{
					page = allSP.get(0);
					pageType = PageType.SINGLEPAGE;
				}
			}
		}
		
		if(page != null)
		{
			if(page.getJsp() != null && page.getJsp().trim().length() > 0)
			{
				pageName = page.getJsp();
			}
			else
			{
				pageName = page.getName();
			}
			
			if(company.getName().equals("truecare")) {
				List<ItemComment> comments = itemCommentDelegate.getTruecareComments(company, Order.desc("updatedOn")).getList();
				request.setAttribute("comments", comments);
			}
			
			if(company.getName().equals("wilcon")){
				member = (Member) session.get("member");
				wilconCartItems = getWilconCartItems();
				cartOrderWilcon = getCartOrderWilcon();
			}
			
			if(company.getName().equals("kuysencms")){
				kuysenCMSBrandList = getKuysenCMSBrandList();
			}
			
			if(!page.getHidden())
			{
				if(pageType == PageType.FORMPAGE)
				{
					meta.put("title", page.getName());
					if(language != null)
					{
						page.setLanguage(language);
					}
					if(language != null)
					{
						if(("default".equals(request.getParameter("language"))))
						{
							page.setLanguage(null);
						}
					}
					request.setAttribute("page", page);
					
					// new added by aldren
					if(request.getParameter("id") != null) {
						final long id = Long.parseLong(request.getParameter("id"));
						multiPageItem = singlePageDelegate.find(id);
						if(multiPageItem.getCompany().equals(company) && !multiPageItem.getHidden())
						{
							request.setAttribute("selectedPage", multiPageItem);
						}
					}
					
				}
				else if(pageType == PageType.MULTIPAGE)
				{
					final MultiPage multiPage = (MultiPage) page;
					
					ObjectList<SinglePage> publishedItems = null;
					ObjectList<SinglePage> publishedItemsSkechersGallery = null;
					
					meta.put("title", page.getName());
					
					try
					{
						final long id = Long.parseLong(request.getParameter("id"));
						multiPageItem = singlePageDelegate.find(id);
						/* Log the action of member */
						if(member != null)
						{
							memberLog.setCompany(company);
							memberLog.setMember(member);
							memberLog.setRemarks("Viewed page: " + multiPageItem.getParent().getName() + " -> " + multiPageItem.getName());
							memberLogDelegate.insert(memberLog);
						}
						if(multiPageItem.getCompany().equals(company) && !multiPageItem.getHidden())
						{
							request.setAttribute("selectedPage", multiPageItem);
							if(company.getId()==CompanyConstants.MR_AIRCON_ID || company.getId()==CompanyConstants.ELITE_TRANSLATIONS) {
								List<ItemComment> comments = itemCommentDelegate.getCommentsByPage(multiPageItem, Order.asc("createdOn")).getList();
								request.setAttribute("comments", comments);
							}
							
							if(company.getId() == CompanyConstants.LIFE) {
								List<ItemComment> comments = itemCommentDelegate.findAllParentCommentsBySinglePagePublished(multiPageItem, Order.asc("createdOn")).getList();
								request.setAttribute("comments", comments);
							}
							
							if(multiPageItem.getItemDate() != null)
							{
								request.setAttribute("selectedPageMonth", DateTimeFormat.forPattern("MMMM").print(new DateTime(multiPageItem.getItemDate())));
								request.setAttribute("selectedPageDay", new DateTime(multiPageItem.getItemDate()).getDayOfMonth());
								request.setAttribute("selectedPageYear", new DateTime(multiPageItem.getItemDate()).getYear());
							}
							meta.put("title", page.getName() + " - " + multiPageItem.getName());
							
							if(multiPageItem.getCompany().getId() == CompanyConstants.HBC)
							{
								if(member != null)
								{
									request.setAttribute("isLoggedIn", true);
								}
								else
								{
									request.setAttribute("isLoggedIn", false);
								}
								
								// add a new comment
								if((getCommentContent() != null) && member != null)
								{ // AND if member != null
								
									final List<ItemComment> commentsList = itemCommentDelegate.getCommentsByPage(multiPageItem, Order.desc("createdOn")).getList();
									boolean saveVar = true;
									
									for(final ItemComment ivar : commentsList)
									{
										if(ivar.getCompany().equals(company) && ivar.getContent().equals(commentcontent) && ivar.getMember().equals(member))
										{
											saveVar = false;
											break;
										}
									}
									if(saveVar)
									{
										createNewPageComment(multiPageItem);
										setCommentContent(null);
										
									}
								}
								// displays comments for selected singlepage
								final List<ItemComment> pagecomments = itemCommentDelegate.getCommentsByPage(multiPageItem, Order.desc("createdOn")).getList();
								request.setAttribute("pagecomments", pagecomments);
							}
						}
					}
					catch(final Exception e2)
					{
						final int pageNumber = getPageNumber();
						final int itemsPerPage = multiPage.getItemsPerPage();
						final int singlepagePerPageforSkechers = 2;
						
						publishedItems = singlePageDelegate.findAllPublishedWithPaging(company, multiPage, itemsPerPage, pageNumber - 1, Order.desc("createdOn"));
						publishedItemsSkechersGallery = singlePageDelegate.findAllPublishedWithPaging(company, multiPage, singlepagePerPageforSkechers, pageNumber - 1, Order.desc("createdOn"));
						
						final PagingUtil mpPagingUtil = new PagingUtil(publishedItems.getTotal(), itemsPerPage, pageNumber, (company.getId() == CompanyConstants.MSP)
							? 5
							: -1);
						final PagingUtil mpPagingUtilSkechers = new PagingUtil(publishedItems.getTotal(), singlepagePerPageforSkechers, pageNumber);
						request.setAttribute("multiPagePagingUtil", mpPagingUtil);
						request.setAttribute("multiPagePagingUtilSkechers", mpPagingUtilSkechers);
						request.setAttribute("multiPageItems", publishedItems.getList());
						
						if(company.getId() == CompanyConstants.MSP)
						{
							final List<SinglePage> multiPageItems = publishedItems.getList();
							if(multiPageItems != null && !multiPageItems.isEmpty())
							{
								SortingUtil.sortBaseObject("createdOn", false, multiPageItems);
								request.setAttribute("multiPageItems", multiPageItems);
							}
						}
						
						request.setAttribute("multiPageItemsSkechersGallery", publishedItemsSkechersGallery.getList());
						if(request.getParameter("id") != null)
						{
							final long id = Long.parseLong(request.getParameter("id"));
							multiPageItem = singlePageDelegate.find(id);
							if(multiPageItem != null)
							{
								final List<ItemComment> pagecomments = itemCommentDelegate.getCommentsByPage(multiPageItem, Order.desc("createdOn")).getList();
								request.setAttribute("pagecomments", pagecomments);
							}
						}
					}
					
					if(company != null && multiPage != null && multiPageItem != null)
					{
						multiPageFiles = multiPageFileDelegate.findAllSinglePageFiles(company, multiPage, multiPageItem);
						filterMultiPageFiles(multiPage, multiPageFiles);
						request.setAttribute("multiPageFileItems", multiPageFiles);
					}
					
					if(multiPage.getHasArchive())
					{
						if(request.getParameter("year") != null)
						{
							final int yearInt = Integer.parseInt(request.getParameter("year"));
							multiPage.getYearItems(yearInt);
						}
						
						if(request.getParameter("past") != null)
						{
							final String past = request.getParameter("past");
							if(past.equalsIgnoreCase("1"))
							{
								request.setAttribute("past", 1);
								multiPage.getPastItems(multiPage.getArchiveDays());
							}
						}
						else
						{
							multiPage.getCurrentItems(multiPage.getArchiveDays());
						}
					}
					
					if(request.getParameter("year") != null)
					{
						request.setAttribute("year", request.getParameter("year"));
					}
					
					if(request.getParameter("past") != null)
					{
						request.setAttribute("past", request.getParameter("past"));
					}
					if(language != null)
					{
						multiPage.setLanguage(language);
					}
					if(request.getParameter("language") != null)
					{
						if((request.getParameter("language").equalsIgnoreCase("english")))
						{
							multiPage.setLanguage(null);
						}
					}
					
//					if(company.getId() == CompanyConstants.IBP_MAKATI || company.getId() == CompanyConstants.BOYSEN || company.getId() == CompanyConstants.ELITE_TRANSLATIONS
//							|| CompanyConstants.ICASIANO.equalsIgnoreCase(company.getName()) || company.getName().equals("purpletag2"))
//					{
						
						final MultiPage publicMulti = multiPage;
						int pageNumber = getPageNumber();
						int itemsPerPage = publicMulti.getItemsPerPage();
						int maxDescription = 150;
						
						if(CompanyConstants.ICASIANO.equalsIgnoreCase(company.getName())){
							itemsPerPage = 5;
							maxDescription = 250;
						}
						
						if(company.getId() == 333) {
							maxDescription = 10;
						}
						
						List<SinglePage> pageItems = new ArrayList<SinglePage>();
						
						if(company.getId() == CompanyConstants.BOYSEN)
						{
							String year = request.getParameter("year");
							
							itemsPerPage = publicMulti.getItemsPerPage();
							if(multiPage.getName().equals("Downloads"))
							{
								itemsPerPage = 8;
							} else if(multiPage.getName().equals("Videos")) {
								maxDescription = 80;
								itemsPerPage = 4;
							}
							
							MultiPage newsMultipage = multiPageDelegate.findJsp(company, "news");
							ObjectList<SinglePage> newItems = singlePageDelegate.findAllPublished(company, newsMultipage, Order.desc("datePublished"));
							int lastYear = 0;
							for(SinglePage single : newItems.getList())
							{
								if(single.getDatePublished() != null)
								{
									int currentYear = 0;
									if(lastYear == 0)
									{
										lastYear = Integer.parseInt(single.getDatePublished().toString().substring(0, 4));
									}
									else
									{
										currentYear = Integer.parseInt(single.getDatePublished().toString().substring(0, 4));
										if(currentYear < lastYear)
										{
											lastYear = currentYear;
										}
									}
								}
							}
							request.setAttribute("yearListSearch", lastYear);
							
							if(multiPage.getName().equals("news")){
								ObjectList<SinglePage> pageItems1 = null;
								
								pageItems1 = singlePageDelegate.findAllPublished(company, publicMulti, Order.desc("datePublished"));
								
								if(year != null)
								{
									for(SinglePage singlePage : pageItems1.getList())
									{
										String datePosted = "0";
										if(singlePage.getDatePublished() != null)
										{
											datePosted = singlePage.getDatePublished().toString();
											datePosted = datePosted.substring(0, 4);
										}
										if(Integer.parseInt(datePosted) == Integer.parseInt(year))
										{
											pageItems.add(singlePage);
										}
									}
								}
								else
								{
									pageItems = pageItems1.getList();
								}
							} else {
								pageItems = publicMulti.getSortedItems();
							}
						}
						
						if(company.getId() == CompanyConstants.IBP_MAKATI)
						{
							itemsPerPage = 3;
							int size = singlePageDelegate.findAll(company, publicMulti).getSize();
							// pageItems = singlePageDelegate.findAllPublishedWithPaging(company, publicMulti, itemsPerPage, pageNumber - 1, Order.desc("createdOn"));
							pageItems = publicMulti.getSortedItems();
							List<SinglePage> newPageItems = new ArrayList<SinglePage>();
							int count = 1;
							for(int i = (pageNumber - 1) * itemsPerPage; i < pageItems.size(); i++)
							{
								if(count <= itemsPerPage)
									newPageItems.add(pageItems.get(i));
								count++;
							}
							for(SinglePage ab : newPageItems)
							{
								String b = ab.getContentWithoutTags();
								if(b.length() > 300)
								{
									for(int x = 300; x < b.length(); x++)
									{
										String c = b.substring(300, x);
										if(c.contains(".") || c.contains("?") || c.contains("?"))
										{
											b = b.substring(0, x);
											b = b.replace("View Newsletter", "");
											ab.setPreviewContent(b);
											break;
										}
									}
								}
								else
								{
									b = b.replace("View Newsletter", "");
									ab.setPreviewContent(b);
								}
							}
							request.setAttribute("ibpPageItems", newPageItems);
							final PagingUtil multiPageItemUtil = new PagingUtil(size, itemsPerPage, pageNumber, itemsPerPage);
							request.setAttribute("ibpPagingUtil", multiPageItemUtil);
						}
						
						if(company.getId() == CompanyConstants.BOYSEN)
						{}else{pageItems = publicMulti.getSortedItems();};
						List<SinglePage> newPageItems = new ArrayList<SinglePage>();
						int count = 1;
						for(int i = (pageNumber - 1) * itemsPerPage; i < pageItems.size(); i++)
						{
							if(count <= itemsPerPage)
								newPageItems.add(pageItems.get(i));
							count++;
						}
						for(SinglePage ab : newPageItems)
						{
							String b = ab.getContentWithoutTags();
							if(b.length() > maxDescription)
							{
								for(int x = maxDescription; x < b.length(); x++)
								{
									String c = b.substring(maxDescription, x);
									if(c.contains("."))
									{
										b = b.substring(0, x);
										ab.setPreviewContent(b);
										break;
									}
								}
							}
							else
							{
								ab.setPreviewContent(b);
							}
						}
						
						//setting preview content that ends with '.'
						request.setAttribute("newPageItems", newPageItems);
						final PagingUtil newsPagingUtil = new PagingUtil(pageItems.size(), itemsPerPage, pageNumber, itemsPerPage);
						request.setAttribute("newPagingUtil", newsPagingUtil);
//					}
					
					//multipage pagination
					List<SinglePage> multiPageItems = new ArrayList<SinglePage>();
					multiPageItems = multiPage.getSortedItems();
					pageNumber = getPageNumber();
					itemsPerPage = multiPage.getItemsPerPage();
					List<SinglePage> newMultiPageItems = new ArrayList<SinglePage>();
					count = 1;
					for(int i = (pageNumber - 1) * itemsPerPage; i < multiPageItems.size(); i++)
					{
						if(count <= itemsPerPage)
							newMultiPageItems.add(multiPageItems.get(i));
						count++;
					}
					request.setAttribute("newMultiPageItems", newMultiPageItems);
					final PagingUtil newMultiPagePagingUtil = new PagingUtil(multiPageItems.size(), itemsPerPage, pageNumber, itemsPerPage);
					request.setAttribute("newMultiPagePagingUtil", newMultiPagePagingUtil);
					
					request.setAttribute("page", multiPage);
					
					if(CompanyConstants.LIFE == company.getId()) {
						
						if(page.getName().equalsIgnoreCase("Blog")) {
							MultiPage blogPage = (MultiPage) page;
							List<SinglePage> list = blogPage.getItems();
							
							Collections.sort(list, new Comparator<SinglePage>() {
								public int compare(SinglePage s1, SinglePage s2) {
									return s1.getDatePublished().compareTo(s2.getDatePublished());
								}
							});
							
							List<SinglePage> filteredList = new ArrayList<SinglePage>();
							
							if(request.getParameter("year") != null && request.getParameter("month") != null) {
								String paramDate = request.getParameter("month")+"-"+request.getParameter("year");
								for(SinglePage sp : list) {
									String spDate = new SimpleDateFormat("MMMM-yyyy").format(sp.getDatePublished());
									if(paramDate.equalsIgnoreCase(spDate)) {
										filteredList.add(sp);
									}
								}
							} else if(request.getParameter("year") != null) {
								String paramDate = request.getParameter("year");
								for(SinglePage sp : list) {
									String spDate = new SimpleDateFormat("yyyy").format(sp.getDatePublished());
									if(paramDate.equalsIgnoreCase(spDate)) {
										filteredList.add(sp);
									}
								}
							}
							
							request.setAttribute("filteredList", filteredList);
							
						}
						
					}
					
				}
				else
				{
					String metaTitle = page.getName();
					if(language != null)
					{
						page.setLanguage(language);
					}
					if(request.getParameter("language") != null)
					{
						if((request.getParameter("language").equalsIgnoreCase("english")))
						{
							page.setLanguage(null);
						}
					}
					request.setAttribute("page", page);
					
					if(page != null && page.getTitle() != null && page.getTitle().trim().length() > 0 && !metaTitle.equals(page.getTitle()))
					{
						metaTitle += " - " + page.getTitle();
					}
					
					meta.put("title", metaTitle);
					
					// handle the retrieving of data from pages such as printer friendly and etc..
					if(request.getParameter("id") != null)
					{
						if(request.getParameter("past") != null)
						{
							request.setAttribute("past", 1);
						}
						
						final long pageId = Long.parseLong(request.getParameter("id"));
						
						Page selectedPage = singlePageDelegate.find(pageId);
						if(selectedPage != null && !((SinglePage) selectedPage).getCompany().equals(company))
						{
							selectedPage = null;
						}
						
						// if null try to look for that page on the multipage
						if(selectedPage == null)
						{
							selectedPage = multiPageDelegate.find(pageId);
							if(selectedPage != null && !((MultiPage) selectedPage).getCompany().equals(company))
							{
								selectedPage = null;
							}
						}
						
						// if null try to see if it is a form page
						if(selectedPage == null)
						{
							selectedPage = formPageDelegate.find(pageId);
							if(selectedPage != null && !((FormPage) selectedPage).getCompany().equals(company))
							{
								selectedPage = null;
							}
						}
						if(selectedPage != null)
						{
							if(selectedPage instanceof SinglePage)
							{
								final SinglePage singlePage = (SinglePage) selectedPage;
								request.setAttribute("selectedPage", singlePage);
							}
							else if(selectedPage instanceof MultiPage)
							{
								final MultiPage multiPage = (MultiPage) selectedPage;
								request.setAttribute("selectedPage", multiPage);
							}
							else if(selectedPage instanceof FormPage)
							{
								final FormPage formPage = (FormPage) selectedPage;
								request.setAttribute("selectedPage", formPage);
							}
						}
					}
				}
				request.setAttribute("title", company.getTitle());
				request.setAttribute("meta", meta);
			}
			else
			{
				session.put("companyEmail", company.getEmail());
				session.put("companyLogo", company.getLogo());
				return "pagehide";
			}
		}
		
		// check if the requested page requires user login
		if(multiPageItem != null)
		{
			if(page.getLoginRequired() == false)
			{
				loginRequired = multiPageItem.getLoginRequired();
			}
			else
			{
				loginRequired = page.getLoginRequired();
			}
		}
		else if(page != null)
		{
			loginRequired = page.getLoginRequired();
		}
		
		if(loginRequired)
		{
			if(member == null)
			{
				return Action.LOGIN;
			}
			else
			{
				logger.debug("Already logged in");
			}
		}
		
		if(member == null)
		{
			setHasLoggedOn(false);
		}
		else
		{
			setHasLoggedOn(true);
		}
		
		if(page != null && (page instanceof SinglePage || page instanceof MultiPage))
		{
			filterSinglePageFiles(page);
		}
		
		/**** START for BOYSEN.COM.PH and Pilipinas Bronze only ****/
		//System.out.println("-->START<--\n" + request.getHeader("user-agent").toLowerCase() +"\nCompany name: " + company.getName() + "\nTrue or False: " + company.getName().equalsIgnoreCase("boysen") + "\nHas mobile site? " + CompanyUtil.hasMobileSite(company));
		if(company.getName().equalsIgnoreCase("boysen") || company.getName().equalsIgnoreCase("boysennewdesign"))
		{
			return Action.SUCCESS;
		}
		
		if(company.getName().equalsIgnoreCase("boysen-old"))
		{
			final String userAgent = request.getHeader("user-agent").toLowerCase();
			if(m == null && userAgent.contains("mobile") && (userAgent.contains("iphone") || userAgent.contains("android")) && !userAgent.contains("ipad") )
			{
				mobileHttpSession = request.getSession();
				
				final Boolean isFullSite = mobileHttpSession.getAttribute("fullSite") != null
					// if the "fullsite" attribute is not set
					? (Boolean) mobileHttpSession.getAttribute("fullSite")
					: Boolean.FALSE;
				if(StringUtils.containsIgnoreCase(request.getParameter("view"), "fullSite") || isFullSite)
				{
					/** set the session of the user to view the full site ONLY */
					mobileHttpSession.setAttribute("fullSite", Boolean.TRUE);
				}
				else
				{
					mobileHttpSession.setAttribute("fullSite", Boolean.FALSE);
				}
				
				if(StringUtils.containsIgnoreCase(request.getParameter("view"), "m") ){
					mobileHttpSession.setAttribute("fullSite", Boolean.FALSE);
				}
				
				if(!(Boolean) mobileHttpSession.getAttribute("fullSite"))
				{
					return "fullSite";
				}else{
					return "fullSite";
				}
			}else{ 
				return Action.SUCCESS;
			}
		}
		/**** END for BOYSEN.COM.PH and Pilipinas Bronze only ****/
		
		if(CompanyUtil.hasMobileSite(company))
		{
			final String userAgent = request.getHeader("user-agent").toLowerCase();
			if(m == null && userAgent.contains("mobile") && (userAgent.contains("iphone") || userAgent.contains("android") || userAgent.contains("ipad")))
			{
				mobileHttpSession = request.getSession();
				
				final Boolean isFullSite = mobileHttpSession.getAttribute("fullSite") != null
					// if the "fullsite" attribute is not set
					? (Boolean) mobileHttpSession.getAttribute("fullSite")
					: Boolean.FALSE;
				if(StringUtils.containsIgnoreCase(request.getParameter("view"), "fullSite") || isFullSite)
				{
					/** set the session of the user to view the full site ONLY */
					mobileHttpSession.setAttribute("fullSite", Boolean.TRUE);
				}
				else
				{
					mobileHttpSession.setAttribute("fullSite", Boolean.FALSE);
				}
				
				
				if(!(Boolean) mobileHttpSession.getAttribute("fullSite"))
				{
					return MOBILE_ACTION;
					/** the result name for mobile */
				}
			}
			// else return success
		}
		return Action.SUCCESS;
	}
	
	protected void filterSinglePageFiles(Page page)
	{
		if(member != null && Boolean.TRUE.equals(companySettings.getHasPageFileRights()) && page instanceof SinglePage)
		{
			((SinglePage) page).setMemberType(member.getMemberType());
		}
		else if(member != null && Boolean.TRUE.equals(companySettings.getHasPageFileRights()) && page instanceof MultiPage)
		{
			((MultiPage) page).setMemberType(member.getMemberType());
			
			final List<SinglePage> items = ((MultiPage) page).getItems();
			
			if(items != null)
			{
				for(final SinglePage item : items)
				{
					item.setMemberType(member.getMemberType());
				}
			}
		}
	}
	
	protected void filterMultiPageFiles(MultiPage multiPage, List<MultiPageFile> multiPageFiles)
	{
		if(multiPage != null && multiPageFiles != null && Boolean.TRUE.equals(companySettings.getHasPageFileRights()) && member != null && member.getMemberType() != null)
		{
			final MemberType memberType = member.getMemberType();
			final List<MultiPageFile> removeList = new ArrayList<MultiPageFile>();
			
			for(final MultiPageFile file : multiPageFiles)
			{
				if(!Boolean.TRUE.equals(file.getMemberTypeAccess().get(memberType.getId())))
				{
					removeList.add(file);
				}
			}
			
			for(final MultiPageFile file : removeList)
			{
				multiPageFiles.remove(file);
			}
		}
	}
	
	protected void loadMenu()
	{
		try
		{
			final Map<String, Menu> menuList = new HashMap<String, Menu>();
			
			// get the single pages
			final List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
			for(final SinglePage singlePage : singlePageList)
			{
				//System.out.println("Page Type : " + singlePage.getPageType().getName());
				singlePage.setLanguage(language);
				final String jspName = singlePage.getJsp();
				//final Menu menu = new Menu(singlePage.getName(), PageType.SINGLEPAGE.getName(), httpServer + "/" + jspName + ".do");
				final Menu menu = new Menu(singlePage, httpServer + "/" + jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}
			
			// get the multi pages
			final List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
			request.setAttribute("multiPageList", multiPageList);
			for(final MultiPage multiPage : multiPageList)
			{
				multiPage.setLanguage(language);
				final List<SinglePage> single = multiPage.getItems();
				for(final SinglePage s : single)
				{
					s.setLanguage(language);
				}
				final String jspName = multiPage.getJsp();
				//final Menu menu = new Menu(multiPage.getName(), PageType.MULTIPAGE.getName(), httpServer + "/" + jspName + ".do");
				final Menu menu = new Menu(multiPage, httpServer + "/" + jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}
			
			// get the form Pages
			final List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
			for(final FormPage formPage : formPageList)
			{
				formPage.setLanguage(language);
				final String jspName = formPage.getJsp();
				//final Menu menu = new Menu(formPage.getName(), PageType.FORMPAGE.getName(), httpServer + "/" + jspName + ".do");
				final Menu menu = new Menu(formPage, httpServer + "/" + jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}
			
			// get the groups
			final List<Group> groupList = groupDelegate.findAll(company).getList();
			final Map<String, Group> groupMap = new HashMap<String, Group>();
			final Map<Long, Group> groupIdMap = new HashMap<Long, Group>();
			for(final Group group : groupList)
			{
				final String jspName = group.getName().toLowerCase();
				group.setLanguage(language);
				final Menu menu = new Menu(group, httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
				
				groupMap.put(group.getName(), group);
				groupMap.put(jspName, group);
				groupIdMap.put(group.getId(), group);
			}
			request.setAttribute("groupList", groupList);
			request.setAttribute("groupMap", groupMap);
			request.setAttribute("groupIdMap", groupIdMap);
			
			// get the link to the allowed pages
			for(final String s : ALLOWED_PAGES)
			{
				final String jspName = s.toLowerCase();
				final Menu menu = new Menu(jspName, httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
			}
			request.setAttribute("menu", menuList);
		}
		catch(final Exception e)
		{
			logger.fatal("problem intercepting action in FrontMenuInterceptor. " + e);
		}
	}
	
	protected void loadFeaturedPages(int max)
	{
		final Map<String, Object> featuredPages = new HashMap<String, Object>();
		final List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();
		int maxDescription = 250;
		
		if(company.getId() == 333)
			maxDescription = 10;
		
		for(final MultiPage mp : featuredMultiPage)
		{
			if(!mp.getHidden())
			{
				
				for(SinglePage singlePage : mp.getItems()) {
					String content = (singlePage.getContentWithoutTags() != null ? singlePage.getContentWithoutTags() : "");
					if(content.length() > maxDescription)
					{
						for(int index = maxDescription; index < content.length(); index++)
						{
							String currentContent = content.substring(maxDescription, index);
							if(currentContent.contains("."))
							{
								content = content.substring(0, index);
								singlePage.setPreviewContent(content);
								break;
							}
						}
					}
					else
					{
						singlePage.setPreviewContent(content);
					}
				}
				
				mp.setLanguage(language);
				featuredPages.put(mp.getName(), mp);
				featuredPages.put(mp.getJsp(), mp);
				
				if(company.getId() == CompanyConstants.IBP_MAKATI)
				{
					if(mp.getName().equalsIgnoreCase("Home"))
					{
						
						final MultiPage publicMulti = mp;
						
						final int pageNumber = getPageNumber();
						final int itemsPerPage = 3;
						// ObjectList<SinglePage> pageItems = null;
						int size = singlePageDelegate.findAll(company, publicMulti).getSize();
						
						List<SinglePage> pageItems = publicMulti.getSortedItems();
						List<SinglePage> newPageItems = new ArrayList<SinglePage>();
						int count = 1;
						for(int i = (pageNumber - 1) * itemsPerPage; i < pageItems.size(); i++)
						{
							if(count <= itemsPerPage)
								newPageItems.add(pageItems.get(i));
							count++;
						}
						
						// pageItems = singlePageDelegate.findAllPublishedWithPaging(company, publicMulti, itemsPerPage, pageNumber - 1);
						for(SinglePage ab : newPageItems)
						{
							String b = ab.getContentWithoutTags();
							if(b.length() > 800)
							{
								for(int x = 800; x < b.length(); x++)
								{
									String c = b.substring(800, x);
									if(c.contains("."))
									{
										b = b.substring(0, x);
										b = b.replace("View Newsletter", "");
										ab.setPreviewContent(b);
										break;
									}
								}
							}
							else
							{
								b = b.replace("View Newsletter", "");
								ab.setPreviewContent(b);
							}
						}
						request.setAttribute("ibpHomePageItems", newPageItems);
						final PagingUtil multiPageItemUtil = new PagingUtil(size, itemsPerPage, pageNumber, itemsPerPage);
						request.setAttribute("ibpHomePagingUtil", multiPageItemUtil);
					}
				}
			}
		}
		
		// logger.debug("\nFEATURED PAGES:"+featuredPages.get("rescue updates")+"\n\n");
		request.setAttribute("featuredPages", featuredPages);
		
		for(final MultiPage mp : featuredMultiPage)
		{
			mp.setLanguage(language);
			final List<SinglePage> multiPageItems = new ArrayList<SinglePage>();
			multiPageItems.addAll(mp.getItems());
			boolean isNull = false;
			for(final SinglePage sp : multiPageItems)
			{
				sp.setLanguage(language);
				if(sp.getItemDate() == null)
				{
					isNull = true;
				}
			}
			if(!isNull)
			{
				SortingUtil.sortBaseObject("itemDate", false, multiPageItems);
				request.setAttribute("featuredPageItems", multiPageItems);
			}
		}
	}
	
	protected void loadFormPages(int max)
	{
		final Map<String, Object> formPages = new HashMap<String, Object>();
		final List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
		final int listSize = formPageList.size();
		final int ctr = (listSize >= max)
			? max
			: listSize;
		
		if(formPageList != null && !formPageList.isEmpty())
		{
			for(int i = 0; i < ctr; i++)
			{
				final FormPage formPage = formPageList.get(i);
				final String formPageName = formPage.getName();
				if(formPage.getIsValid())
				{
					formPage.setLanguage(language);
					formPages.put(formPageName, formPage);
				}
			}
			request.setAttribute("formPages", formPages);
		}
	}
	
	protected void loadFeaturedProducts(int max)
	{
		List<CategoryItem> items;
		List<CategoryItem> featuredCategoryItems; // added by pol
		final int pageNumber = getPageNumber();
		int itemSize = 0;
		int featuredCatItemsSize;
		try
		{
			final int companyId = company.getId().intValue();
			
			switch(companyId)
			{
				case CompanyConstants.EARTHANDSTYLE:
					items = categoryItemDelegate.findAllFeaturedProductsWithPaging(company, max, 0, Order.desc("id")).getList();
					break;
					
				case CompanyConstants.HBC:
					final Group productgroup = groupDelegate.find(company, "Products");
					
					itemSize = categoryItemDelegate.getLatestMonthProducts(company, -1, -1, Order.desc("createdOn")).getList().size();
					items = categoryItemDelegate.getLatestMonthProductsWithPaging(company, productgroup, max, pageNumber - 1, Order.desc("createdOn")).getList();
					// featuredCatItemsSize = categoryItemDelegate.findAllFeaturedByGroup(company, productgroup, max, 0, Order.desc("id")).getList().size();
					featuredCatItemsSize = categoryItemDelegate.findAllFeaturedByGroup(company, productgroup, Order.desc("id")).getList().size();
					
					// featuredCategoryItems = categoryItemDelegate.findAllFeaturedProductsWithPaging(company, max, 0, Order.desc("id")).getList();
					featuredCategoryItems = categoryItemDelegate.findAllFeaturedByGroupWithPaging(company, productgroup, max, pageNumber - 1, Order.desc("id")).getList();
					
					if(itemSize != 0)
					{
						itemSize = categoryItemDelegate.getLatestProducts(company, -1, -1, Order.desc("createdOn")).getList().size();
						items = categoryItemDelegate.getLatestProductsWithPaging(company, productgroup, max, pageNumber - 1, Order.desc("createdOn")).getList();
					}
					
					/* LATEST PRODUCTS */
					request.setAttribute("featuredItems", items);
					
					if(itemSize > max)
					{
						final PagingUtil pagingUtilDesc = new PagingUtil(itemSize, max, pageNumber, 5);
						request.setAttribute("pagingUtilDesc2", pagingUtilDesc);
						request.setAttribute("featuredItems", items);
					}
					
					if(featuredCatItemsSize > 0)
					{
						final PagingUtil pagingUtilFeat = new PagingUtil(featuredCatItemsSize, max, pageNumber, 5);
						request.setAttribute("pagingUtilFeat", pagingUtilFeat);
						request.setAttribute("featuredCategoryItems", featuredCategoryItems);
					}
					break;
					
				default:
					items = categoryItemDelegate.findAllFeaturedProductsWithPaging(company, max, 0, Order.desc("updatedOn")).getList();
					break;
			}
			
			// sorting of items after query
			switch(companyId)
			{
				case CompanyConstants.RENEW_PLACENTA:
					if(CollectionUtils.isNotEmpty(items))
					{
						SortingUtil.sortBaseObject("name", true, items);
					}
					break;
				
				
				// Gurkka or Gurkka Test
				case CompanyConstants.GURKKA:
				/*case CompanyConstants.GURKKA_TEST:
					if(CollectionUtils.isNotEmpty(items))
					{
						final Iterator<CategoryItem> it = items.iterator();
						while(it.hasNext())
						{
							final CategoryItem i = (CategoryItem) it.next();
							if(i.getParent() != null)
							{
								if(StringUtils.containsIgnoreCase(i.getParent().getName(), "Other"))
								{
									// remove featuredItems under "Other" category
									it.remove();
								}
							}
						}
						
						SortingUtil.sortBaseObject("name", true, items);
					}
					break;
				*/
				default:
					break;
			}
			
			request.setAttribute("featuredItems", items);
		}
		catch(final Exception e)
		{
			logger.fatal("failed to load featured products... " + e);
		}
	}
	
	protected void loadBestSellers(int max)
	{
		List<CategoryItem> items;
		getPageNumber();
		try
		{
			items = categoryItemDelegate.findAllBestSellersWithPaging(company, max, 0, Order.desc("updatedOn")).getList();
			request.setAttribute("bestSellerItems", items);
		}
		catch(final Exception e)
		{
			logger.fatal("failed to load featured products... " + e);
		}
	}
	
	protected void loadCategories(int max)
	{
		try
		{
			// Order[] orders = {Order.asc("sortOrder"), Order.asc("name")};
			if(company.getId() == 259)
			{
				final List<Category> categories = categoryDelegate.findAllPublished(company, null, null, true, Order.asc("name")).getList();
				request.setAttribute("categoryMenu", categories);
			}
			
			if(company.getId() != 259)
			{
				final List<Category> categories = categoryDelegate.findAllPublished(company, null, null, true, Order.asc("sortOrder")).getList();
				request.setAttribute("categoryMenu", categories);
			}
			
			List<CategoryItem> itemResults = null;
			
			if(searchItemKeyword != null)
			{
				Category parentCat = null;
				Category itemParentCat = null;
				Brand brand = null;
				
				if((getCat_id() == null || getCat_id().length() == 0) && (getSubCat_id() != null && getSubCat_id().length() != 0))
				{
					
					brand = brandDelegate.find(Long.parseLong(getSubCat_id()));
					itemResults = categoryItemDelegate.searchByParentCategoryBrand(company, brand, searchItemKeyword);
					
				}
				else
				{
					if(getCat_id() != null && getCat_id().length() != 0)
					{
						parentCat = categoryDelegate.find(Long.parseLong(getCat_id()));
					}
					if(getSubCat_id() != null && getSubCat_id().length() != 0)
					{
						itemParentCat = categoryDelegate.find(Long.parseLong(getSubCat_id()));
					}
					itemResults = categoryItemDelegate.search(company, parentCat, itemParentCat, searchItemKeyword);
					
				}
				request.setAttribute("itemsResult", itemResults);
			}
			
			final Group exclusive = groupDelegate.find(company, "Exclusive Hits");
			if(company.getId() == CompanyConstants.HBC)
			{
				final List<Category> exclusivehits = categoryDelegate.findAllPublished(company, null, exclusive, true, Order.asc("sortOrder")).getList();
				final int exclusivehitsSize = exclusivehits.size();
				List<CategoryItem> allExclusiveHits = exclusivehits.get(0).getItems();
				
				for(int i = 1; i < exclusivehitsSize; i++)
				{
					allExclusiveHits.addAll(exclusivehits.get(i).getItems());
				}
				
				/* allExclusiveHits - List of CategoryItems for Exclusive Hits */
				
				final List<CategoryItem> ehItems = new ArrayList<CategoryItem>();
				
				for(final CategoryItem item : allExclusiveHits)
				{
					if(!item.getIsOutOfStock())
					{
						ehItems.add(item);
					}
				}
				
				allExclusiveHits = ehItems;
				
				request.setAttribute("exclusivehits", allExclusiveHits);
			}
			
			final int pageNumber2 = getPageNumber();
			final int pageNumber3 = getPageNumber();
			int itemSize2 = 0;
			int itemSize3 = 0;
			
			final String groupId = request.getParameter("groupItems");
			final String catId = request.getParameter("category_id");
			
			List<CategoryItem> allItemsOfThisGroup = null;
			
			List<CategoryItem> allItemsOfThisCategory = null;
			
			if(request.getParameter("groupItems") != null)
			{
				itemSize2 = categoryItemDelegate.getAllItems(company, groupDelegate.find(Long.parseLong(groupId)), -1, -1, Order.desc("itemDate")).getList().size();
				
				allItemsOfThisGroup = categoryItemDelegate.getAllItems(company, groupDelegate.find(Long.parseLong(groupId)), 10, pageNumber2 - 1, Order.desc("itemDate")).getList();
				
				request.setAttribute("allItemsOfThisGroup", allItemsOfThisGroup);
				request.setAttribute("itemSize", itemSize2);
				
				if(itemSize2 > max)
				{
					final PagingUtil pagingUtil2 = new PagingUtil(itemSize2, pageNumber2);
					pagingUtil2.setItemsPerPage(10);
					pagingUtil2.setTotalPages(itemSize2 / 10);
					request.setAttribute("itemsPagingUtil2", pagingUtil2);
					request.setAttribute("groupId", request.getParameter("groupItems"));
				}
			}
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.AYROSOHARDWARE)) {
				allItemsOfThisGroup = categoryItemDelegate.getAllItems(company, groupDelegate.find(Long.parseLong("445")), -1, -1, Order.desc("itemDate")).getList();
				request.setAttribute("allItemsOfThisGroup", allItemsOfThisGroup);
			}
			
			if(request.getParameter("R") != null && request.getParameter("category_id") != null)
			{
				itemSize3 = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(catId)), -1, -1, Order.desc("itemDate")).getList().size();
				allItemsOfThisCategory = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(catId)), 10, pageNumber3 - 1, Order.desc("itemDate")).getList();
				request.setAttribute("allItemsOfThisCategory", allItemsOfThisCategory);
				request.setAttribute("itemSize3", itemSize3);
				
				if(itemSize3 > max)
				{
					final PagingUtil pagingUtil3 = new PagingUtil(itemSize3, pageNumber3);
					pagingUtil3.setItemsPerPage(10);
					pagingUtil3.setTotalPages(itemSize2 / 10);
					request.setAttribute("itemsPagingUtil3", pagingUtil3);
					request.setAttribute("catId", request.getParameter("category_id"));
				}
			}
			
			if(company.getId() == CompanyConstants.RENEW_PLACENTA && request.getParameter("category_id") != null)
			{
				int itemsPerPage = companySettings.getProductsPerPage();
				itemSize3 = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(catId)), -1, -1, Order.asc("name")).getList().size();
				allItemsOfThisCategory = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(catId)), itemsPerPage, pageNumber3 - 1, Order.asc("name")).getList();
				request.setAttribute("allItemsOfThisCategory", allItemsOfThisCategory);
				request.setAttribute("itemSize3", itemSize3);
				
				final PagingUtil categoryItems = new PagingUtil(itemSize3, itemsPerPage, pageNumber3, itemsPerPage);
				request.setAttribute("categoryItems", categoryItems);
				request.setAttribute("catId", request.getParameter("category_id"));
				
			}
			
		}
		catch(final Exception e)
		{
			logger.fatal("failed to load categories... " + e);
		}
	}
	
	protected void loadEventGroups()
	{
		try
		{
			final List<EventGroup> eventGroups = eventGroupDelegate.findAll(company);
			
			request.setAttribute("eventGroups", eventGroups);
			
			final String egid = request.getParameter("eventGroupId");
			if(egid != null)
			{
				eventGroup = eventGroupDelegate.find(new Long(egid));
			}
			
		}
		catch(final Exception e)
		{
			logger.fatal("failed to load eventGroups... " + e);
		}
	}
	
	protected void loadEvents()
	{
		try
		{
			List<Event> events = null;
			String filter = "PACEOS";
			if(company.getId() == CompanyConstants.PACEOS)
			{
				filter = request.getParameter("filter");
				if(filter == null || filter.isEmpty())
				{
					filter = "PACEOS";
				}
				final EventGroup grp = eventGroupDelegate.find(company, filter);
				events = eventDelegate.findAll(grp, company, null);
			}
			
			else
			{
				events = eventDelegate.findAll(company);
			}
			logger.debug("filter = " + filter);
			request.setAttribute("filter", filter);
			request.setAttribute("events", events);
			
		}
		catch(final Exception e)
		{
			logger.fatal("failed to load categories... " + e);
		}
	}
	
	protected void loadProducts(int max)
	{
		try
		{
			final int pageNumber = getPageNumber();
			final ObjectList<CategoryItem> items = categoryItemDelegate.findAllActiveItemsWithPaging(company, null, max, pageNumber - 1, true, Order.asc("name"));
			final ObjectList<CategoryItem> allItems = categoryItemDelegate.findAll(company);
			
			logger.debug("PAGE NUMBER: " + pageNumber);
			
			if(items.getTotal() > max)
			{
				final PagingUtil pagingUtil = new PagingUtil(allItems.getList().size(), pageNumber);
				request.setAttribute("itemsPagingUtil", pagingUtil);
			}
			
			request.setAttribute("items", items.getList());
		}
		catch(final Exception e)
		{
			logger.debug("failed to load item products...");
		}
	}
	
	protected void loadPackages(int max)
	{
		try
		{
			final int pageNumber = getPageNumber();
			//was final ObjectList<IPackage> packages = packageDelegate.findAllActiveItemsWithPaging(company, max, pageNumber - 1, Order.asc("name"));
			final ObjectList<IPackage> packages = packageDelegate.findAll(company, max, pageNumber - 1);
			
			logger.debug("PAGE NUMBER: " + pageNumber);
			
			if(packages.getTotal() > max)
			{
				final PagingUtil pagingUtil = new PagingUtil(packages.getTotal(), max, pageNumber);
				request.setAttribute("packagesPagingUtil", pagingUtil);
			}
			
			request.setAttribute("packages", packages.getList());
			try
			{
				final int partition = (int) StrictMath.round(packages.getList().size() / 3.0);
				request.setAttribute("packageList1", packages.getList().subList(0, partition));
				request.setAttribute("packageList2", packages.getList().subList(partition, partition * 2));
				request.setAttribute("packageList3", packages.getList().subList(partition * 2, packages.getList().size()));
			}
			catch(final Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(final Exception e)
		{
			logger.debug("failed to load packages...");
		}
	}
	
	protected void loadFeaturedBrands()
	{
		final Order[] orders = { Order.asc("name") };
		final List<Brand> featuredBrands = brandDelegate.findAllFeatured(company, true, orders).getList();
		request.setAttribute("featuredBrands", featuredBrands);
	}
	
	protected void loadAllBrands(int max)
	{
		final int pageNumber = getPageNumber();
		int itemSize = 0;
		
		final Order[] orders = { Order.asc("name") };
		final List<Brand> brands = brandDelegate.findAllEnabled(company, true, orders).getList();
		// List<Brand> enabledBrands = brandDelegate.findAllEnabled(company, true, orders).getList();
		
		final List<OtherField> otherField = otherFieldDelegate.find(company);
		
		final List<CategoryItemOtherField> categoryItemOtherField = categoryItemOtherFieldDelegate.findAll(company).getList();
		
		List<Brand> enabledBrandsWithPaging;
		List<Brand> enabledBrands;
		List<Brand> featuredBrands;
		
		/* This is for the loading of the categories */
		
		final ArrayList<String> series = new ArrayList<String>();
		final List<String[]> models = new ArrayList<String[]>();
		
		int i = 0;
		
		final String cat_id = request.getParameter("category_id");
		
		for(i = 0; i < categoryItemOtherField.size(); i++)
		{
			try
			{
				final String[] mod = new String[2];
				if(cat_id != null && Integer.parseInt(cat_id) == categoryItemOtherField.get(i).getCategoryItem().getParent().getId())
				{
					
					if(categoryItemOtherField.get(i).getOtherField().getName().toLowerCase().trim().equals("series"))
					{
						if(series.isEmpty())
						{
							series.add(categoryItemOtherField.get(i).getContent().trim().toLowerCase());
						}
						if(!series.isEmpty() && series.indexOf(categoryItemOtherField.get(i).getContent().trim().toLowerCase()) == -1)
						{
							series.add(categoryItemOtherField.get(i).getContent().trim().toLowerCase());
						}
					}
					if(categoryItemOtherField.get(i).getOtherField().getName().toLowerCase().trim().equals("model"))
					{
						mod[0] = categoryItemOtherField.get(i).getId().toString().trim();
						mod[1] = categoryItemOtherField.get(i).getContent().trim();
						models.add(mod);
					}
				}
			}
			catch(final Exception e)
			{
				continue;
			}
		}
		
		/* End of series and categories */
		
		featuredBrands = brandDelegate.findAllFeatured(company, true, orders).getList();
		
		enabledBrandsWithPaging = brandDelegate.findAllEnabledWithPaging(company, max, pageNumber, true, orders).getList();
		enabledBrands = brandDelegate.findAll(company, true, orders).getList();
		
		request.setAttribute("otherField", otherField);
		request.setAttribute("models", models);
		request.setAttribute("series", series);
		request.setAttribute("brands", brands);
		request.setAttribute("enabledBrandsWithPaging", enabledBrandsWithPaging);
		request.setAttribute("enabledBrands", enabledBrands);
		request.setAttribute("featuredBrands", featuredBrands);
		
		itemSize = brandDelegate.findAllEnabled(company, true, orders).getSize();
		if(itemSize > max)
		{
			final PagingUtil paging = new PagingUtil(itemSize, max, pageNumber, 5);
			request.setAttribute("itemUtil", paging);
			request.setAttribute("brandMax", itemSize);
		}
		
		final String brandId = request.getParameter("brand_id");
		List<CategoryItem> allItemsOfThisBrand = null;
		
		if(request.getParameter("brand_id") != null)
		{
			
			itemSize = categoryItemDelegate.getAllBrandItems(company, brandDelegate.find(Long.parseLong(brandId)), -1, -1, Order.desc("itemDate")).getList().size();
			
			if(company.getId() == CompanyConstants.HBC)
			{
				allItemsOfThisBrand = categoryItemDelegate.getAllBrandItemsWithPaging(company, max, pageNumber - 1, brandDelegate.find(Long.parseLong(brandId)), Order.asc("sortOrder"),
						Order.asc("name")).getList();
			}
			else
			{
				allItemsOfThisBrand = categoryItemDelegate.getAllBrandItemsWithPaging(company, max, pageNumber - 1, brandDelegate.find(Long.parseLong(brandId)), Order.desc("itemDate")).getList();
			}
			
			request.setAttribute("allItemsOfThisBrand", allItemsOfThisBrand);
			request.setAttribute("itemSize", itemSize);
			if(itemSize > max)
			{
				final PagingUtil pagingUtil = new PagingUtil(itemSize, max, pageNumber, 5);
				request.setAttribute("itemsPagingUtil", pagingUtil);
				request.setAttribute("brandId", request.getParameter("brand_id"));
				request.setAttribute("allItemsOfThisBrand", allItemsOfThisBrand);
			}
		}
	}
	
	protected void loadAllDescItems()
	{
		List<Category> listCat = new ArrayList<Category>();
		Category category;
		List<CategoryItem> allDesItems;
		List<CategoryItem> sortedDesItems;
		final int pageNumber = getPageNumber();
		int itemSize = 0;
		final int max = companySettings.getProductsPerPage();
		if(request.getParameter("category_id") != null)
		{
			category = categoryDelegate.find(new Long(request.getParameter("category_id")));
			listCat = category.getDesCategory2();
			itemSize = categoryItemDelegate.findDescItemsSize(company, listCat, -1, -1, Order.desc("itemDate"));
			if(company.getId() == CompanyConstants.EPLUS)
			{
				allDesItems = categoryItemDelegate.findDescItemsWithPaging(company, listCat, max, pageNumber - 1, Order.asc("sortOrder")).getList();
			}
			else
			{
				allDesItems = categoryItemDelegate.findDescItemsWithPaging(company, listCat, max, pageNumber - 1, Order.desc("itemDate")).getList();
			}
			if(company.getId() == CompanyConstants.HBC)
			{
				sortedDesItems = categoryItemDelegate.findDescItemsWithPaging(company, listCat, max, pageNumber - 1, Order.asc("sortOrder"), Order.asc("name")).getList();
			}
			else
			{
				sortedDesItems = categoryItemDelegate.findDescItemsWithPaging(company, listCat, max, pageNumber - 1, Order.asc("brand")).getList();
				// itemSize = allDesItems.size();
			}
			
			request.setAttribute("sortedDesItems", sortedDesItems);
			request.setAttribute("allDesItems", allDesItems);
			request.setAttribute("allItemSize", itemSize);
			if(itemSize > max)
			{
				final PagingUtil pagingUtilDesc = new PagingUtil(itemSize, max, pageNumber, 5);
				request.setAttribute("pagingUtilDesc", pagingUtilDesc);
				request.setAttribute("categoryId", request.getParameter("category_id"));
			}
		}
	}
	
	protected void loadAllItemsByTags()
	{
		try
		{
			int itemSize = 0;
			final int max = companySettings.getProductsPerPage();
			final int pageNumber = getPageNumber();
			final long id = Long.parseLong(request.getParameter("id"));
			// long id = Long.parseLong(request.getParameter("id"));
			SinglePage pageID = null;
			
			// get PageById
			pageID = singlePageDelegate.find(id);
			itemSize = categoryItemDelegate.findAllByTags(company, pageID.getName()).getList().size();
			final List<CategoryItem> tagItems = categoryItemDelegate.findTagItemsWithPaging(company, pageID.getName(), max, pageNumber - 1, Order.desc("itemDate")).getList();
			
			request.setAttribute("tagItems", tagItems);
			if(itemSize > max)
			{
				final PagingUtil pagingUtilDesc = new PagingUtil(itemSize, max, pageNumber, 5);
				request.setAttribute("pagingUtilTag", pagingUtilDesc);
				request.setAttribute("id", request.getParameter("id"));
			}
		}
		catch(final Exception e)
		{
			
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void removeQuotation(String qid)
	{
		final List<String> quote = (List<String>) session.get("quote");
		final List<String> quoteNum = (List<String>) session.get("quoteNum");
		
		List newQuote, newQuoteNum;
		newQuote = new ArrayList<String>();
		newQuoteNum = new ArrayList<String>();
		
		for(int i = 0; i < quote.size(); i++)
		{
			if(!quote.get(i).equals(qid))
			{
				newQuote.add(quote.get(i));
				newQuoteNum.add(quoteNum.get(i));
			}
		}
		request.setAttribute("qid", qid);
		
		session.put("quote", newQuote);
		session.put("quoteNum", newQuoteNum);
	}
	
	protected void loadQuotation()
	{
		quoteItems = new ArrayList<CategoryItem>();
		quoteItemsNum = new ArrayList<String>();
		// session.get("quote");
		// session.get("quoteNum");
		quotation.addAll((List<String>) session.get("quote"));
		quotationNum.addAll((List<String>) session.get("quoteNum"));
		for(final String x : quotation)
		{
			final CategoryItem item = categoryItemDelegate.find(Long.parseLong(x));
			quoteItems.add(item);
		}
		quoteItemsNum = quotationNum;
	}
	
	protected void loadAllItems()
	{
		
		List<CategoryItem> allCatItems = null;
		List<CategoryItem> allEnabledItemsBySortOrder;
		List<CategoryItem> sortedCatItems;
		List<CategoryItem> sortedAscItems;
		List<CategoryItem> itemsByGroupAndByCategory;
		
		final String selected_model = request.getParameter("select_models");
		final String selected_series = request.getParameter("select_series");
		final String selected_brand = request.getParameter("select_brand");
		boolean searched = false;
		
		final int pageNumberItem = getPageNumber();
		int itemSizeItem = 0;
		final int max = request.getParameter("max") == null
			? companySettings.getProductsPerPage()
			: Integer.parseInt(request.getParameter("max"));
		
		if(request.getParameter("groupId") != null)
		{
			itemSizeItem = categoryItemDelegate.findAllByGroupWithPaging(company, groupDelegate.find(Long.parseLong(request.getParameter("groupId"))), -1, -1, Order.asc("name")).getList().size();
			if(company.getId() == 240)
			{
				sortedAscItems = categoryItemDelegate.findAllByGroupWithPaging(company, groupDelegate.find(Long.parseLong(request.getParameter("groupId"))), max, pageNumberItem - 1,
						Order.asc("sortOrder")).getList();
			}
			else
			{
				sortedAscItems = categoryItemDelegate
						.findAllByGroupWithPaging(company, groupDelegate.find(Long.parseLong(request.getParameter("groupId"))), max, pageNumberItem - 1, Order.asc("name")).getList();
			}
			request.setAttribute("sortedAscItems", sortedAscItems);
			request.setAttribute("groupId", request.getParameter("groupId"));
		}
		
		if(request.getParameter("category_id") != null)
		{
			
			itemSizeItem = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), -1, -1, Order.asc("itemDate")).getList().size();
			if(company.getId() == 133)
			{
				allCatItems = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), max, pageNumberItem - 1, Order.asc("sortOrder"))
						.getList();
			}
			if(company.getId() == 194)
			{
				allCatItems = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), max, pageNumberItem - 1, Order.asc("name"))
						.getList();
			}
			else
			{
				/* SORTING */
				Order[] orders = { Order.asc("sortOrder") };
				final Order[] orders1 = { Order.asc("parent"), Order.asc("sortOrder"), Order.asc("name") };
				final Order[] orders2 = { Order.asc("parent"), Order.asc("name") };
				final Order[] orders3 = { Order.asc("parent"), Order.asc("createdOn"), Order.asc("name") };
				try
				{
					final long id = Long.parseLong(request.getParameter("category_id"));
					final Category category = categoryDelegate.find(id);
					if(category.getParentGroup().getSortType() != null)
					{
						if(category.getParentGroup().getSortType().equals("manualSort"))
						{
							orders = orders1;
						}
						else if(category.getParentGroup().getSortType().equals("alphabeticalSort"))
						{
							orders = orders2;
						}
						else if(category.getParentGroup().getSortType().equals("dateCreatedSort"))
						{
							orders = orders3;
						}
					}
					
				}
				catch(final Exception e)
				{
					
				}
				
				allCatItems = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), max, pageNumberItem - 1, orders).getList();
				// end
			}
			allEnabledItemsBySortOrder = categoryItemDelegate.getAllCatItemsNoPaging(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), Order.asc("sortOrder"))
					.getList();
			
			// sortedCatItems = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), max, pageNumberItem-1,
			// Order.asc("sortOrder")).getList();
			
			/* for brand, series and model filtering */
			
			if(company.getId() == 255)
			{
				final List<CategoryItem> temp = new ArrayList<CategoryItem>();
				final List<CategoryItem> temp2 = categoryItemDelegate.getAllCatItemsNoPaging(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))),
						Order.asc("sortOrder")).getList();
				int i, j, bflag = 0, sflag = 0;
				
				if(selected_brand != null && !selected_brand.equals("0"))
				{
					for(i = 0; i < temp2.size(); i++)
					{
						if(temp2.get(i).getBrand().getId() == Integer.parseInt(selected_brand.trim()))
						{
							temp.add(temp2.get(i));
						}
					}
					bflag = 1;
					searched = true;
					allCatItems = temp;
					for(i = 0; i < allCatItems.size(); i++)
					{
					}
				}
				
				if(selected_series != null && !selected_series.trim().equals("0"))
				{
					if(bflag == 1)
					{
						temp2.clear();
						temp2.addAll(allCatItems);
					}
					temp.clear();
					for(i = 0; i < temp2.size(); i++)
					{
						for(j = 0; j < temp2.get(i).getCategoryItemOtherFields().size(); j++)
						{}
					}
					sflag = 1;
					searched = true;
					allCatItems = temp;
					for(i = 0; i < allCatItems.size(); i++)
					{
					}
				}
				if(selected_model != null && !selected_model.trim().equals("0"))
				{
					if(sflag == 1 || bflag == 1)
					{
						temp2.clear();
						temp2.addAll(allCatItems);
					}
					temp.clear();
					for(i = 0; i < temp2.size(); i++)
					{
						for(j = 0; j < temp2.get(i).getCategoryItemOtherFields().size(); j++)
						{}
					}
					searched = true;
					allCatItems = temp;
					for(i = 0; i < allCatItems.size(); i++)
					{
					}
				}
			}
			String br = "0";
			String mod = "0";
			String ser = "0";
			
			if(selected_model != null)
			{
				mod = selected_model.trim().toLowerCase();
			}
			if(selected_series != null)
			{
				ser = selected_series.trim().toLowerCase();
			}
			if(selected_brand != null)
			{
				br = selected_brand.trim().toLowerCase();
			}
			
			/* end filtering */
			
			if(company.getId() == 104)
			{
				sortedCatItems = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), max, pageNumberItem - 1,
						Order.asc("sortOrder")).getList();
			}
			else
			{
				sortedCatItems = categoryItemDelegate.getAllCatItemsNoPaging(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), Order.asc("sortOrder")).getList();
			}
			request.setAttribute("model_option", mod);
			request.setAttribute("series_option", ser);
			request.setAttribute("brand_option", br);
			request.setAttribute("searched", searched);
			request.setAttribute("sortedCatItems", sortedCatItems);
			request.setAttribute("allCatItems", allCatItems);
			request.setAttribute("allEnabledItemsBySortOrder", allEnabledItemsBySortOrder);
			request.setAttribute("allItemSize", itemSizeItem);
		}
		
		if(request.getParameter("search_class") != null)
		{
			// allCatItems = categoryItemDelegate.getAllCatItems(company, categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), max, pageNumberItem-1, orders).getList();
			// searchList = categoryItemDelegate.search(keyword.trim(), company, isInDescription, true );
			final int pageNumber = getPageNumber();
			keyword = request.getParameter("search_class").toString();
			itemSizeItem = categoryItemDelegate.searchClass(keyword.trim(), company).getList().size();
			allCatItems = categoryItemDelegate.searchClassWithPaging(pageNumber - 1, max, keyword.trim(), company, false, false).getList();
			final List<CategoryItem> allCatClassItems = categoryItemDelegate.searchClass(keyword.trim(), company).getList();
			request.setAttribute("allCatItems", allCatItems);
			request.setAttribute("allCatClassItems", allCatClassItems);
		}
		
		if(request.getParameter("groupId") != null && request.getParameter("category_id") != null)
		{
			itemsByGroupAndByCategory = categoryItemDelegate.findAllByGroupAndByCategoryWithPaging(company, groupDelegate.find(Long.parseLong(request.getParameter("groupId"))),
					categoryDelegate.find(Long.parseLong(request.getParameter("category_id"))), max, pageNumberItem - 1, null).getList();
			request.setAttribute("itemsByGroupAndByCategory", itemsByGroupAndByCategory);
		}
		
		if(searched)
		{
			itemSizeItem = allCatItems.size();
		}
		// if(itemSizeItem > max) {
		if(100 > max)
		{
			final PagingUtil pagingUtilItem = new PagingUtil(itemSizeItem, max, pageNumberItem, 5);
			request.setAttribute("pagingUtilItem", pagingUtilItem);
		}
	}
	
	// BOYSEN INTERACTIVE/ALL PRODUCTS DATA RETRIEVAL
	protected void loadAllProducts()
	{
		System.out.println("LAP Company ==== " + company);
		Company tempComp = company;
		this.company = CompanyDelegate.getInstance().find("boysen");
		
		ObjectList<CategoryItem> allProductsList;
		final List<CategoryItem> allExterior = new ArrayList<CategoryItem>();
		final List<CategoryItem> allInterior = new ArrayList<CategoryItem>();
		final List<CategoryItem> allDecorative = new ArrayList<CategoryItem>();
		final List<CategoryItem> allWood = new ArrayList<CategoryItem>();
		final List<CategoryItem> allMetal = new ArrayList<CategoryItem>();
		final List<CategoryItem> allFloor = new ArrayList<CategoryItem>();
		final List<CategoryItem> allRoof = new ArrayList<CategoryItem>();
		ObjectList<CategoryItem> allRoofInt;
		ObjectList<CategoryItem> allWallHouse;
		ObjectList<CategoryItem> allChildren;
		ObjectList<CategoryItem> allKitchen;
		ObjectList<CategoryItem> allStudy;
		ObjectList<CategoryItem> allGate;
		ObjectList<CategoryItem> allWallPerimeter;
		ObjectList<CategoryItem> allBedroom;
		ObjectList<CategoryItem> allCourt;
		ObjectList<CategoryItem> allSteel;
		ObjectList<CategoryItem> allFloorInt;
		ObjectList<CategoryItem> allFence;
		ObjectList<CategoryItem> allFurniture;
		ObjectList<CategoryItem> allCeiling;
		ObjectList<CategoryItem> allParking;
		
		List<CategoryItem> allProductsList2;
		
		// Only this page has this sort by title so far
		SinglePage singlePage = singlePageDelegate.find(2200l);
		if(singlePage != null) {
			final List<PageImage> sortedImagesByTitle = PageImageDelegate.getInstance().findAllSortedByTitle(singlePage);
			request.setAttribute("sortedImagesByTitle", sortedImagesByTitle);
		}
		
		final long id = 170;
		final Group group = groupDelegate.find(id);
		if(group.getCompany().equals(company))
		{
			allProductsList2 = categoryItemDelegate.findAllByGroup(company, group).getList();
			request.setAttribute("allProds", allProductsList2);
			
			for(int i = 0; i < allProductsList2.size(); i++)
			{
				final String name = allProductsList2.get(i).getParent().getName();
				if(name.compareTo("EXTERIOR CONCRETE") == 0)
				{
					allExterior.add(allProductsList2.get(i));
				}
				else if(name.compareTo("INTERIOR CONCRETE") == 0)
				{
					allInterior.add(allProductsList2.get(i));
				}
				else if(name.compareTo("INTERIOR DECORATIVE") == 0)
				{
					allDecorative.add(allProductsList2.get(i));
				}
				else if(name.compareTo("FLOOR") == 0)
				{
					allFloor.add(allProductsList2.get(i));
				}
				else if(name.compareTo("METAL") == 0)
				{
					allMetal.add(allProductsList2.get(i));
				}
				else if(name.compareTo("ROOF") == 0)
				{
					allRoof.add(allProductsList2.get(i));
				}
				else if(name.compareTo("WOOD") == 0)
				{
					allWood.add(allProductsList2.get(i));
				}
			}
		}
		
		allProductsList = categoryItemDelegate.findAll(company);
		
		allRoofInt = categoryItemDelegate.findAllByTags(company, "roof_int");
		allWallHouse = categoryItemDelegate.findAllByTags(company, "wall_house");
		allChildren = categoryItemDelegate.findAllByTags(company, "children");
		allKitchen = categoryItemDelegate.findAllByTags(company, "kitchen");
		allStudy = categoryItemDelegate.findAllByTags(company, "study");
		allGate = categoryItemDelegate.findAllByTags(company, "gate");
		allWallPerimeter = categoryItemDelegate.findAllByTags(company, "wall_perimeter");
		allBedroom = categoryItemDelegate.findAllByTags(company, "bedroom");
		allCourt = categoryItemDelegate.findAllByTags(company, "court");
		allSteel = categoryItemDelegate.findAllByTags(company, "steel");
		allFloorInt = categoryItemDelegate.findAllByTags(company, "floor_int");
		allFence = categoryItemDelegate.findAllByTags(company, "fence");
		allFurniture = categoryItemDelegate.findAllByTags(company, "furniture");
		allCeiling = categoryItemDelegate.findAllByTags(company, "ceiling");
		allParking = categoryItemDelegate.findAllByTags(company, "parking");
		
		// ALL PRODUCT PAGE
		request.setAttribute("allProducts", allProductsList.getList());
		request.setAttribute("allExterior", allExterior);
		request.setAttribute("allInterior", allInterior);
		request.setAttribute("allDecorative", allDecorative);
		request.setAttribute("allWood", allWood);
		request.setAttribute("allMetal", allMetal);
		request.setAttribute("allFloor", allFloor);
		request.setAttribute("allRoof", allRoof);
		
		// BOYSEN INTERACTIVE
		request.setAttribute("allRoofInt", allRoofInt.getList());
		request.setAttribute("allWallHouse", allWallHouse.getList());
		request.setAttribute("allChildren", allChildren.getList());
		request.setAttribute("allKitchen", allKitchen.getList());
		request.setAttribute("allStudy", allStudy.getList());
		request.setAttribute("allGate", allGate.getList());
		request.setAttribute("allWallPerimeter", allWallPerimeter.getList());
		request.setAttribute("allLiving", allBedroom.getList());
		request.setAttribute("allCourt", allCourt.getList());
		request.setAttribute("allSteel", allSteel.getList());
		request.setAttribute("allFloorInt", allFloorInt.getList());
		request.setAttribute("allFence", allFence.getList());
		request.setAttribute("allFurniture", allFurniture.getList());
		request.setAttribute("allCeiling", allCeiling.getList());
		request.setAttribute("allParking", allParking.getList());
		
		this.company = tempComp;
	}
	
	protected void loadAllRootCategories()
	{
		final Order[] orders = { Order.asc("sortOrder") };
		final Order[] ordersName = { Order.asc("name") };
		final Order[] ordersName2 = { Order.desc("name") };
		List<Category> rootCategories;
		List<Category> careerCategories = new ArrayList<Category>();
		nktiItems = new ArrayList<CategoryItem>();
		if(company.getId() == 133)
		{
			rootCategories = categoryDelegate.findAllRootCategories(company, true, ordersName).getList();
		}
		else if(company.getId() == 170)
		{
			careerCategories = categoryDelegate.findAllRootCategories(company, true, ordersName2).getList();
			rootCategories = categoryDelegate.findAllRootCategories(company, true, orders).getList();
			logger.info("NKTI rootcategories size " + rootCategories.size());
		}
		
		else
		{
			rootCategories = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		}
		final List<Category> orderedlist = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		
		// if language is not null it will convert all item to the parameter language.. thank you!!
		if(language != null)
		{
			List<Category> updatedRootCategories = new ArrayList<Category>();
			for(Category category : rootCategories)
			{
				category.setLanguage(language);
				List<CategoryItem> updatedCategoryItems = new ArrayList<CategoryItem>();
				for(CategoryItem categoryItem : category.getItems())
				{
					categoryItem.setLanguage(language);
					updatedCategoryItems.add(categoryItem);
				}
				category.setItems(updatedCategoryItems);
				updatedRootCategories.add(category);
			}
			if(company.getId() == CompanyConstants.GURKKA) {
				for(Category cat : updatedRootCategories) {
					for(Brand brand : cat.getParentGroup().getBrands()) {
						Brand newbrand = brandDelegate.find(brand.getId());
						if(newbrand.getCompany().equals(company) && !newbrand.isDisabled())
						{
							brand.setItemsbyname(newbrand.getItemsbyname());
						}
					}
				}
			}
			request.setAttribute("rootCategories", updatedRootCategories);
		}
		else
		{
			if(company.getId() == CompanyConstants.GURKKA) {
				for(Category cat : rootCategories) {
					for(Brand brand : cat.getParentGroup().getBrands()) {
						Brand newbrand = brandDelegate.find(brand.getId());
						if(newbrand.getCompany().equals(company) && !newbrand.isDisabled())
						{
							brand.setItemsbyname(newbrand.getItemsbyname());
						}
					}
				}
			}
			request.setAttribute("rootCategories", rootCategories);
		}
		
		request.setAttribute("careerCategories", careerCategories);
		
		orderedlist.clear();
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.KUYSEN) && request.getParameter("category_id") == null){
			Category category = new Category();
			final List<CategoryItem> childrenItems = new ArrayList<CategoryItem>();
			for(Category cat : rootCategories) {
				if(cat.getParentGroup().getName().equalsIgnoreCase("Products")) {
					for(Category children : cat.getChildrenCategory()) {
						if(children.getParentCategory().getName().equalsIgnoreCase("PRODUCTS") && children.getName().equalsIgnoreCase("Bath Fixtures")) {
							for(Category child : children.getChildrenCategory()) {
								if(child.getName().equalsIgnoreCase("Washbasin")) {
									category = child;
									break;
								}
							}
						}
					}
				}
			}
			
			// list of the items of the subcategories of the category
			for(int i = 0; i < (CollectionUtils.isEmpty(category.getChildrenCategory()) ? 0 : category.getChildrenCategory().size()); i++)
			{
				for(int j = 0; j < category.getChildrenCategory().get(i).getEnabledItems().size(); j++)
				{
					childrenItems.add(category.getChildrenCategory().get(i).getEnabledItems().get(j));
				}
			}
			final int pageNumber = getPageNumber();
			int itemsPerPage = 10;
			if(childrenItems.size() > 0) {
				final List<CategoryItem> newChildrenItems = new ArrayList<CategoryItem>();
				int count = 1;
				for(int i = (pageNumber - 1) * itemsPerPage; i < childrenItems.size(); i++)
				{
					if(count <= itemsPerPage)
						newChildrenItems.add(childrenItems.get(i));
					count++;
				}
				request.setAttribute("newChildrenItems", newChildrenItems);
				final PagingUtil newChildrenPagingUtil = new PagingUtil(childrenItems.size(), itemsPerPage, pageNumber, itemsPerPage);
				request.setAttribute("newChildrenPagingUtil", newChildrenPagingUtil);
			}
			
		}
		
		for(int i = 0; i < rootCategories.size(); i++)
		{
			if(rootCategories.get(i).getChildrenCategory().size() == 0)
			{
				orderedlist.add(rootCategories.get(i));
			}
			else
			{
				for(int j = 0; j < rootCategories.get(i).getChildrenCategory().size(); j++)
				{
					orderedlist.add(rootCategories.get(i).getChildrenCategory().get(j));
				}
			}
		}
		if(CompanyConstants.NKTI == company.getId())
		{
			
			for(final Category cat : careerCategories)
			{
				if(cat.getParentGroup().getId() == 179)
				{
					for(final CategoryItem catItem : cat.getItems())
					{
						nktiItems.add(catItem);
					}
				}
			}
			request.setAttribute("careerItems", nktiItems);
		}
		
		Collections.sort(orderedlist, new Comparator<Category>()
		{
			@Override
			public int compare(Category cat1, Category cat2)
			{
				return cat1.getName().compareToIgnoreCase(cat2.getName());
			}
		});
		request.setAttribute("orderedCategories", orderedlist);
	}
	
	/**
	 * Returns {@code SUCCESS} if all shopping cart items are successfully
	 * loaded, {@code INPUT} if member is null, and {@code ERROR} if an error
	 * occurred during the process.
	 * 
	 * @return - {@code SUCCESS} if all shopping cart items are successfully
	 *         loaded, {@code INPUT} if member is null, and {@code ERROR} if an
	 *         error occurred during the process
	 */
	protected void loadCartItems()
	{
		// validate current user, must not be empty
		if(member != null)
		{
			if(session.get("discount") != null)
			{
				request.setAttribute("discount", session.get("discount"));
				session.remove("discount");
				session.remove("totalPrice");
			}
			if(session.get("homeService") != null)
			{
				request.setAttribute("homeService", session.get("homeService"));
				session.remove("homeService");
			}
			// check if user has shopping cart, use existing otherwise create a cart
			// for user
			validateShoppingCart();
			// get cart items
			initCartItems();
		}
	}
	
	/**
	 * Checks if current user has a shopping cart, if found the cart is loaded
	 * otherwise a new one is created.
	 */
	protected void validateShoppingCart()
	{
		// check if cart exists for current member
		if(shoppingCart == null)
		{
			// generate a new shopping cart for user
			createNewShoppingCart();
			// re initialize empty shopping cart information
			// initShoppingCart();
		}
	}
	
	/**
	 * Populates shopping cart with cart items.
	 */
	protected void initCartItems()
	{
		try
		{
			final ObjectList<ShoppingCartItem> tempCartItems = cartItemDelegate.findAll(shoppingCart);
			cartItemList = tempCartItems.getList();
			
			for(final ShoppingCartItem item : cartItemList)
			{
				final ItemFile itemFile = itemFileDelegate.findItemFileID(company, item.getItemDetail().getRealID());
				if(itemFile != null)
				{
					filename.add(FileImageUtil.getFileImage(itemFile.getFileName()));
				}
				catItem.add(categoryItemDelegate.find(item.getItemDetail().getRealID()));
			}
			request.setAttribute("fileIcon", filename);
			request.setAttribute("cartItemList", cartItemList);
		}
		catch(final Exception e)
		{
			logger.debug("No cart items retrieved.");
		}
	}
	
	/**
	 * Generates a new shopping cart for user.
	 */
	protected void createNewShoppingCart()
	{
		shoppingCart = new ShoppingCart();
		shoppingCart.setCompany(company);
		shoppingCart.setMember(member);
		shoppingCart.setCreatedOn(new Date());
		cartDelegate.insert(shoppingCart);
	}
	
	/**
	 * Returns shopping cart based on current session parameters.
	 */
	protected void initShoppingCart()
	{
		try
		{
			shoppingCart = cartDelegate.find(company, member);
		}
		catch(final Exception e)
		{
			logger.debug("Shopping cart is currently empty.");
		}
	}
	
	protected void validatePreOrder()
	{
		preOrder = preOrderDelegate.find(company, member);
		if(preOrder == null)
		{
			createNewPreOrder();
		}
	}
	
	protected void createNewPreOrder()
	{
		preOrder = new PreOrder();
		preOrder.setCompany(company);
		preOrder.setMember(member);
		preOrder.setCreatedOn(new Date());
		preOrderDelegate.insert(preOrder);
	}
	
	protected int getPageNumber()
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
	
	protected void findSelectedItem()
	{
		final String itemid = request.getParameter("item_id");
		CategoryItem item = null;
		if(itemid != null && itemid.trim().length() != 0)
		{
			try
			{
				final long id = Long.parseLong(request.getParameter("item_id"));
				item = categoryItemDelegate.find(id);
				categoryItemPackages = categoryItemPackageDelegate.findAllWithPaging(company, id, -1, -1, Order.asc("iPackage.name"));
				if(item != null)
				{
					if(item.getCompany().equals(company) && !item.getDisabled())
					{
						request.setAttribute("item", item);
						
						boolean test = true;
						for(int ctr = 0; ctr < item.getCategoryItemOtherFields().size(); ctr++)
						{}
						
						if(item.getCategoryItemOtherFields() != null && item.getCategoryItemOtherFields().size() > 0 && test == true)
						{
							// SortingUtil.sortBaseObject("otherField.sortOrder", Boolean.TRUE, item.getCategoryItemOtherFields());
						}
						
						if(item.getCompany().getId() == CompanyConstants.HBC)
						{
							if(member != null)
							{
								request.setAttribute("isLoggedIn", true);
							}
							else
							{
								request.setAttribute("isLoggedIn", false);
							}
							// add a new comment
							logger.debug("\n---------------------------------COMMENT1:" + getCommentContent());
							if((getCommentContent() != null) && member != null)
							{ // AND if member != null
								createNewItemComment(item, getCommentContent());
								setCommentContent(null);
								request.setAttribute("commentContent", null);
								request.removeAttribute("commentContent");
							}
							logger.debug("\n---------------------------------COMMENT2:" + getCommentContent());
							// displays item comments for HBC
							final List<ItemComment> itemcomments = itemCommentDelegate.getCommentsByItem(item, Order.desc("createdOn")).getList();
							request.setAttribute("itemcomments", itemcomments);
						}
					}
				}
			}
			catch(final Exception e)
			{
				logger.fatal("Cannot find item. " + item);
				logger.fatal("Cannot find item. " + item.getId());
				logger.fatal("Cannot find item. ", e);
			}
		}
	}
	
	/**
	 * Inserts a new item comment to the database.
	 */
	protected void createNewItemComment(CategoryItem item, String msg)
	{
		// create a new itemComment entry
		currentItemComment = new ItemComment();
		currentItemComment.setCreatedOn(new Date());
		currentItemComment.setCompany(company);
		currentItemComment.setItem(item);
		currentItemComment.setMember(member);
		currentItemComment.setFirstname(member.getFirstname());
		currentItemComment.setLastname(member.getLastname());
		currentItemComment.setContent(msg);
		currentItemComment.setIsValid(true);
		itemCommentDelegate.insert(currentItemComment);
		this.commentcontent = null;
	}
	
	/**
	 * Inserts a new page comment to the database.
	 */
	protected void createNewPageComment(SinglePage page)
	{
		// create a new pageComment entry
		currentItemComment = new ItemComment();
		currentItemComment.setCreatedOn(new Date());
		currentItemComment.setCompany(company);
		currentItemComment.setPage(page);
		currentItemComment.setMember(member);
		currentItemComment.setFirstname(member.getFirstname());
		currentItemComment.setLastname(member.getLastname());
		currentItemComment.setContent(commentcontent);
		currentItemComment.setIsValid(true);
		itemCommentDelegate.insert(currentItemComment);
		this.commentcontent = null;
	}
	
	protected void findSelectedGroup()
	{
		try
		{
			final long id = Long.parseLong(request.getParameter("group_id"));
			final Group group = groupDelegate.find(id);
			if(group.getCompany().equals(company))
			{
				request.setAttribute("group", group);
			}
		}
		catch(final Exception e)
		{
			logger.fatal("Cannot find category. ", e);
		}
	}
	
	protected void loadFeaturedSinglePages(int max)
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
	
	protected void findSelectedBrand()
	{
		try
		{
			final long id = Long.parseLong(request.getParameter("brand_id"));
			
			final Brand brand = brandDelegate.find(id);
			if(brand.getCompany().equals(company) && !brand.isDisabled())
			{
				request.setAttribute("selectedBrand", brand);
				
				if(brand.getItems() != null) {
					setBrandItemsPagination(brand);
				}
			}
		}
		catch(final Exception e)
		{
			logger.fatal("Cannot find brand", e);
		}
	}
	
	private void setBrandItemsPagination(Brand brand) {
		// TODO Auto-generated method stub
		int pageNumber = getPageNumber();
		int itemsPerPage = 15;
		List<CategoryItem> brandItems = new ArrayList<CategoryItem>();
		int count = 1;
		
		if(company.getName().equalsIgnoreCase("kuysen")) {
			List<CategoryItem> list = brand.getItems();
			Collections.sort(list, new Comparator<CategoryItem>() {
				public int compare(CategoryItem c1, CategoryItem c2) {
					return c1.getDescriptionWithoutTags().compareTo(c2.getDescriptionWithoutTags());
				}
			});
			brand.setItems(list);
		}
		
		for(int i = (pageNumber - 1) * itemsPerPage; i < brand.getItems().size(); i++)
		{
			if(count <= itemsPerPage)
				brandItems.add(brand.getItems().get(i));
			count++;
		}
		request.setAttribute("brandItems", brandItems);
		final PagingUtil brandItemPagingUtil = new PagingUtil(brand.getItems().size(), itemsPerPage, pageNumber, itemsPerPage);
		request.setAttribute("brandItemPagingUtil", brandItemPagingUtil);
	}

	protected void findSelectedPackage()
	{
		try
		{
			final long id = Long.parseLong(request.getParameter("package_id"));
			
			final IPackage iPackage = packageDelegate.find(id);
			if(iPackage.getCompany().equals(company))
			{
				request.setAttribute("selectedPackage", iPackage);
			}
		}
		catch(final Exception e)
		{
			logger.fatal("Cannot find package", e);
		}
	}
	
	public String updatePuregoldAccounts()
	{
		//readMemberAccounts();
		request.setAttribute("notificationMessage", "Puregold accounts updated successfully.");
		
		return SUCCESS;
	}
	
	@SuppressWarnings("deprecation")
	public void readMemberAccounts()
	{}
	
	public String updateHBCProducts()
	{
		readHBCProducts();
		request.setAttribute("notificationMessage", "HBC Products updated successfully.");
		
		return SUCCESS;
	}
	
	public void readHBCProducts()
	{
		
	}
	
	public String downloadFile() throws Exception
	{
		ItemFile itemFile = null;
		PageImage pageImagefile = null;
		MultiPageFile multiPageFile = null;
		PageFile pageFile = null;
		// try to get the item file
		try
		{
			if(StringUtils.isNotBlank(request.getParameter("mpfid")))
			{
				multiPageFile = multiPageFileDelegate.find(company, Long.parseLong(request.getParameter("mpfid")));
			}
			
			if(StringUtils.isNotBlank(request.getParameter("pfid")))
			{
				pageFile = pageFileDelegate.find(company, Long.parseLong(request.getParameter("pfid")));
			}
			
			if(StringUtils.isNotBlank(request.getParameter("file_id")))
			{
				final long itemFileId = Long.parseLong(request.getParameter("file_id"));
				itemFile = itemFileDelegate.find(itemFileId);
			}
			if(StringUtils.isNotBlank(request.getParameter("imagefile_id")))
			{
				final long pageImagefileId = Long.parseLong(request.getParameter("imagefile_id"));
				pageImagefile = pageImageDelegate.find(pageImagefileId);
			}
		}
		catch(final Exception e)
		{
			logger.debug("no item file...");
			e.printStackTrace();
		}
		
		if(isLoginRequiredForFileDownload(itemFile) && member == null)
		{
			loadMenu();
			return Action.LOGIN;
		}
		
		if(itemFile != null)
		{
			String destinationPath = "companies";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + company.getName();
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			
			fileName = itemFile.getFileName();
			fileSize = itemFile.getFileSize();
			final File file = new File(servletContext.getRealPath(destinationPath + File.separator + "images" + File.separator + "items" + File.separator + itemFile.getFilePath()));
			logger.info("DOWNloaddddd : " + servletContext.getRealPath(destinationPath + File.separator + "images" + File.separator + "items" + File.separator + itemFile.getFilePath()));
			
			if(!file.exists())
			{
				logger.fatal("Unabled to locate file: ");
			}
			
			fInStream = new FileInputStream(file);
			fileInputStream = new FileInputStream(file);
			inputStream = new FileInputStream(file);
			contentLength = file.length();
		}
		
		if(multiPageFile != null)
		{
			String destinationPath = "companies";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + company.getName();
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			
			fileName = multiPageFile.getFileName();
			fileSize = multiPageFile.getFileSize();
			final File file = new File(servletContext.getRealPath(destinationPath + File.separator + multiPageFile.getFilePath()));
			logger.info("DOWNloaddddd : " + servletContext.getRealPath(destinationPath + File.separator + multiPageFile.getFilePath()));
			
			if(!file.exists())
			{
				logger.fatal("Unabled to locate file: ");
			}
			
			fInStream = new FileInputStream(file);
			fileInputStream = new FileInputStream(file);
			inputStream = new FileInputStream(file);
			contentLength = file.length();
		}
		
		if(pageFile != null)
		{
			String destinationPath = "companies";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + company.getName();
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			
			fileName = pageFile.getFileName();
			fileSize = pageFile.getFileSize();
			final File file = new File(servletContext.getRealPath(destinationPath + File.separator + pageFile.getFilePath()));
			logger.info("DOWNloaddddd : " + servletContext.getRealPath(destinationPath + File.separator + pageFile.getFilePath()));
			
			if(!file.exists())
			{
				logger.fatal("Unabled to locate file: ");
			}
			
			fInStream = new FileInputStream(file);
			fileInputStream = new FileInputStream(file);
			inputStream = new FileInputStream(file);
			contentLength = file.length();
		}
		
		if(pageImagefile != null)
		{
			String destinationPath = "companies";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + company.getName();
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			
			fileName = pageImagefile.getFilename();
			
			final File imagefile = new File(servletContext.getRealPath(destinationPath + File.separator + "images" + File.separator + "pages" + File.separator + pageImagefile.getOriginal()));
			logger.info("DOWNloaddddd : " + servletContext.getRealPath(destinationPath + File.separator + "images" + File.separator + "pages" + File.separator + pageImagefile.getOriginal()));
			
			if(!imagefile.exists())
			{
				logger.fatal("Unabled to locate file: ");
			}
			
			fInStream = new FileInputStream(imagefile);
			fileInputStream = new FileInputStream(imagefile);
			inputStream = new FileInputStream(imagefile);
			contentLength = imagefile.length();
		}
		
		return SUCCESS;
	}
	
	public String downloadLoginFile() throws FileNotFoundException
	{
		String filepath = "";
		String addpath = "";
		ItemFile iFile = null;
		MultiPageFile mpfile = null;
		
		if(member == null)
		{
			return LOGIN;
		}
		
		if(StringUtils.isNotBlank(request.getParameter("mpfid")))
		{
			// pFile = pageFileDelegate.find(company, Long.parseLong(request.getParameter("mpfid")));
			mpfile = multiPageFileDelegate.find(company, Long.parseLong(request.getParameter("mpfid")));
		}
		
		if(StringUtils.isNotBlank(request.getParameter("ifid")))
		{
			iFile = itemFileDelegate.findItemFileID(company, Long.parseLong(request.getParameter("ifid")));
		}
		
		if(iFile == null && mpfile == null)
		{
			return ERROR;
		}
		
		if(iFile != null)
		{
			fileName = iFile.getFileName();
			iFile.getFileSize();
			filepath = iFile.getFilePath();
			addpath = "images" + File.separator + "items";
		}
		
		if(mpfile != null)
		{
			fileName = mpfile.getFileName();
			mpfile.getFileSize();
			filepath = mpfile.getFilePath();
			// addpath = "multipage_upload" + File.separator + "items";
		}
		
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		final File file = new File(servletContext.getRealPath(destinationPath + File.separator + addpath + File.separator + filepath));
		logger.info("DOWNloaddddd : "+ servletContext.getRealPath(destinationPath + File.separator + addpath + File.separator + filepath));
		
		if(!file.exists())
		{
			logger.fatal("Unabled to locate file: ");
		}
		
		fInStream = new FileInputStream(file);
		fileInputStream = new FileInputStream(file);
		inputStream = new FileInputStream(file);
		contentLength = file.length();
		
		return SUCCESS;
	}
	
	public String downloadPaidFile() throws Exception
	{
		ItemFile itemFile = null;
		PageImage pageImagefile = null;
		// try to get the item file
		try
		{
			if(StringUtils.isNotBlank(request.getParameter("file_id")))
			{
				final long orderID = Long.parseLong(request.getParameter("cartOrderItemID"));
				final long orderItemFileID = Long.parseLong(request.getParameter("orderItemFileID"));
				final long itemFileId = Long.parseLong(request.getParameter("file_id"));
				
				orderItemFile = orderItemFileDelegate.find(orderItemFileID);
				itemFile = itemFileDelegate.find(itemFileId);
				cartOrderItem = cartOrderItemDelegate.find(orderID);
				
			}
			if(StringUtils.isNotBlank(request.getParameter("imagefile_id")))
			{
				final long pageImagefileId = Long.parseLong(request.getParameter("imagefile_id"));
				pageImagefile = pageImageDelegate.find(pageImagefileId);
			}
		}
		catch(final Exception e)
		{
			logger.debug("no item file...");
			e.printStackTrace();
		}
		
		if(isLoginRequiredForFileDownload(itemFile) && member == null)
		{
			loadMenu();
			return Action.LOGIN;
		}
		
		// check if item files has been set to expire in company settings
		if(company.getCompanySettings().getWillExpire())
		{
			if(new Date().after(orderItemFile.getExpiryDate()))
			{
				return ERROR;
			}
		}
		
		// check if item files has a download limit set in the company settings
		if(company.getCompanySettings().getLimitDownloads())
		{
			if(!(cartOrderItem.getDownloads() > 0))
			{
				return ERROR;
			}
			else
			{
				cartOrderItem.setDownloads(cartOrderItem.getDownloads() - 1);
				cartOrderItemDelegate.update(cartOrderItem);
			}
		}
		
		if(itemFile != null)
		{
			String destinationPath = "companies";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + company.getName();
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			
			fileName = itemFile.getFileName();
			fileSize = itemFile.getFileSize();
			final File file = new File(servletContext.getRealPath(destinationPath + File.separator + "images" + File.separator + "items" + File.separator + itemFile.getFilePath()));
			logger.info("DOWNloaddddd : " + servletContext.getRealPath(destinationPath + File.separator + "images" + File.separator + "items" + File.separator + itemFile.getFilePath()));
			
			if(!file.exists())
			{
				logger.fatal("Unabled to locate file: ");
			}
			
			fInStream = new FileInputStream(file);
			fileInputStream = new FileInputStream(file);
			inputStream = new FileInputStream(file);
			contentLength = file.length();
		}
		
		if(pageImagefile != null)
		{
			String destinationPath = "companies";
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			destinationPath += File.separator + company.getName();
			FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
			
			fileName = pageImagefile.getFilename();
			
			final File imagefile = new File(servletContext.getRealPath(destinationPath + File.separator + "images" + File.separator + "pages" + File.separator + pageImagefile.getOriginal()));
			logger.info("DOWNloaddddd : " + servletContext.getRealPath(destinationPath + File.separator + "images" + File.separator + "pages" + File.separator + pageImagefile.getOriginal()));
			
			if(!imagefile.exists())
			{
				logger.fatal("Unabled to locate file: ");
			}
			
			fInStream = new FileInputStream(imagefile);
			fileInputStream = new FileInputStream(imagefile);
			inputStream = new FileInputStream(imagefile);
			contentLength = imagefile.length();
		}
		
		return SUCCESS;
	}
	
	/*
	 * gets the current size of the member's shopping cart
	 */
	public void getCartSize()
	{
		ObjectList<ShoppingCartItem> tempCartItems;
		try
		{
			shoppingCart = cartDelegate.find(company, member);
			tempCartItems = cartItemDelegate.findAll(shoppingCart);
			final int cartSize = tempCartItems.getList().size();
			request.setAttribute("cartSize", cartSize);
			int cartItemSize = 0;
			for(ShoppingCartItem item : tempCartItems) {
				cartItemSize += item.getQuantity();
			}
			request.setAttribute("cartItemSize", cartItemSize);
		}
		catch(final Exception e)
		{
			logger.debug("Shopping cart is currently empty.");
		}
		
	}
	
	/**
	 * @param itemFile
	 * @return true if login is required before downloading a file, false
	 *         otherwise
	 */
	protected boolean isLoginRequiredForFileDownload(ItemFile itemFile)
	{
		// If item file is null, download will not proceed, there's no need to
		// require login
		if(itemFile == null)
		{
			return false;
		}
		
		// If category item is null, login required field cannot be traced, so
		// return false
		final CategoryItem item = itemFile.getItem();
		if(item == null)
		{
			return false;
		}
		
		// If category is null, login required field cannot be traced, so return
		// false
		final Category category = item.getParent();
		if(category == null)
		{
			return false;
		}
		
		// If group is not null and its loginRequired attribute is set to true,
		// login is automatically considered required
		final Group group = category.getParentGroup();
		if(group != null && group.getLoginRequired())
		{
			return true;
		}
		
		// If group is not null and its loginRequired attribute is set to false,
		// but login is required in its category, login will still be considered
		// required
		return category.getLoginRequired();
	}
	
	public String search()
	{
		searchList = categoryItemDelegate.search(keyword, company);
		
		/** remove cola. not searchable daw*/
		if(company.getId() == CompanyConstants.GURKKA)// || company.getId() == CompanyConstants.GURKKA_TEST)
		{
			final Iterator<CategoryItem> iterator = searchList.iterator();
			while(iterator.hasNext())
			{
				final CategoryItem item = (CategoryItem) iterator.next();
				if(StringUtils.containsIgnoreCase("1 liter cola", item.getName()))
				{
					iterator.remove();
				}
			}
		}
		
		return SUCCESS;
	}
	
	public String searchPerson()
	{
		final String name = StringUtils.trimToNull(request.getParameter("txtName"));
		final String practiceArea = StringUtils.trimToNull(request.getParameter("txtPractice"));
		final String position = StringUtils.trimToNull(request.getParameter("txtPosition"));
		final javax.servlet.http.HttpSession session = request.getSession();
		
		if(name != null)
		{
			session.setAttribute("name", name);
		}
		if(practiceArea != null)
		{
			session.setAttribute("practiceArea", practiceArea);
		}
		if(position != null)
		{
			session.setAttribute("position", position);
		}
		if(name == null && practiceArea == null && position == null)
		{
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String searchAll()
	{
		final String keyword = request.getParameter("keywordAll").trim().replaceAll("\\s+", " ");
		searchList = categoryItemDelegate.search(keyword, company);
		request.setAttribute("pageKeyword", keyword);
		final List<MultiPage> multiResults = multiPageDelegate.findPageByKeyWord(keyword, company).getList();
		final List<SinglePage> singleResults = new ArrayList<SinglePage>();
		final List<SinglePage> pageImageResult = singlePageDelegate.findAllByImageKeyword(company, keyword);
		
		if(multiResults != null)
		{
			for(final MultiPage mp : multiResults)
			{
				final List<SinglePage> tmpPages = singlePageDelegate.findByKeyword(company, mp, keyword).getList();
				if(tmpPages != null && !tmpPages.isEmpty())
				{
					for(final SinglePage sp : tmpPages)
					{
						singleResults.add(sp);
					}
				}
			}
		}
		request.setAttribute("keyword", keyword);
		request.setAttribute("pageResults", singleResults);
		request.setAttribute("pageImageResult", pageImageResult);
		
		List<MultiPage> allMultiPage = multiPageDelegate.findAll(company).getList();
		final List<SinglePage> singlePageResults = new ArrayList<SinglePage>();
		
		if(allMultiPage != null)
		{
			for(final MultiPage mp : allMultiPage)
			{
				final List<SinglePage> tmpPages = singlePageDelegate.findByKeyword(company, mp, keyword).getList();
				if(tmpPages != null && !tmpPages.isEmpty())
				{
					for(final SinglePage sp : tmpPages)
					{
						singlePageResults.add(sp);
					}
				}
			}
		}
		request.setAttribute("singlePageResults", singlePageResults);
		
		return SUCCESS;
	}
	
	public String submitInfo()
	{
		final CategoryItem catItem = categoryItemDelegate.find(Long.parseLong(request.getParameter("productPackage")));
		request.setAttribute("itemPackage", catItem);
		request.setAttribute("totalPrice", request.getParameter("totalPrice"));
		request.setAttribute("finalPrice", request.getParameter("finalPrice"));
		return SUCCESS;
	}
	
	public String confirmInfo()
	{
		final CategoryItem catItem = categoryItemDelegate.find(Long.parseLong(request.getParameter("itemPackage")));
		request.setAttribute("comments", request.getParameter("comments"));
		request.setAttribute("itemPackage", catItem);
		request.setAttribute("finalPrice", request.getParameter("finalPrice"));
		request.setAttribute("totalPrice", request.getParameter("totalPrice"));
		return SUCCESS;
	}
	
	public String properties()
	{ // for Landsystems
		try
		{
			loadCategories(companySettings.getMaxCategoryMenu());
			loadMenu();
			orderItems = categoryItemDelegate.findAll(company).getList();
			return SUCCESS;
		}
		catch(final Exception e)
		{
			return ERROR;
		}
	}
	/* -----------  For Coupon -------------------*/
	public List<CategoryItem> getCoupons()
	{
		final Group coupons = groupDelegate.find(company, "coupons");
		if(coupons != null)
		{
			return categoryItemDelegate.findAllEnabledWithPaging(company, coupons, -1, -1, null).getList();
		}
		return null;
	}
	
	
	/*---------- Get Wendys Promos ------------- */
	public List<CategoryItem> getPromos()
	{
		final Group promos = groupDelegate.find(company, "PROMOS");
		if(promos != null)
		{
			return categoryItemDelegate.findAllEnabledWithPaging(company, promos, -1, -1, null).getList();
		}
		return null;
	}
	/*
	 * public void error503() throws Exception
	 * {
	 * System.out.println("response.sendError(503);");
	 * response.sendError(503);
	 * }
	 */
	
	private void loadElcResources(){
		CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
		Map<String, Object> map = new HashMap<String, Object>();
		
		final List<Group> groupList = groupDelegate.findAll(  companyDelegate.find(CompanyConstants.ELC_COMPANY_NAME) ).getList();
		final Map<String, Group> groupMap = new HashMap<String, Group>();
		final Map<Long, Group> groupIdMap = new HashMap<Long, Group>();
		for(final Group group : groupList)
		{
			final String jspName = group.getName().toLowerCase();
			group.setLanguage(language);
			
			groupMap.put(group.getName(), group);
			groupMap.put(jspName, group);
			groupIdMap.put(group.getId(), group);
		}
		map.put("groupMap", groupMap);
		
		request.setAttribute("elcResources", map);
	}
	
	
	@Override
	public void setMember(Member member)
	{
		this.member = member;
	}
	
	public Member getMember(Member member)
	{
		return member;
	}
	
	public void setHasLoggedOn(boolean hasLoggedOn)
	{
		this.hasLoggedOn = hasLoggedOn;
	}
	
	public boolean isHasLoggedOn()
	{
		return hasLoggedOn;
	}
	
	public List<Group> getGroups()
	{
		return groups;
	}
	
	public void setGroups(List<Group> groups)
	{
		this.groups = groups;
	}
	
	public void setItemAttribute(ItemAttribute itemAttribute)
	{
		this.itemAttribute = itemAttribute;
	}
	
	public ItemAttribute getItemAttribute()
	{
		return itemAttribute;
	}
	
	public ItemComment getCurrentItemComment()
	{
		return currentItemComment;
	}
	
	public void setCurrentItemComment(ItemComment currentItemComment)
	{
		this.currentItemComment = currentItemComment;
	}
	
	public void setCommentContent(String commentcontent)
	{
		this.commentcontent = commentcontent;
	}
	
	public String getCommentContent()
	{
		return commentcontent;
	}
	
	public String getMyEmail()
	{
		return myEmail;
	}
	
	public void setMyEmail(String myEmail)
	{
		this.myEmail = myEmail;
	}
	
	public EventGroup getEventGroup()
	{
		return eventGroup;
	}
	
	public void setEventGroup(EventGroup eventGroup)
	{
		this.eventGroup = eventGroup;
	}
	
	public List<CategoryItem> getSearchList()
	{
		return searchList;
	}
	
	public void setSearchList(List<CategoryItem> searchList)
	{
		this.searchList = searchList;
	}
	
	public String getKeyword()
	{
		return keyword;
	}
	
	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
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
	
	public boolean getIsInDescription()
	{
		return isInDescription;
	}
	
	public void setInDescription(boolean isInDescription)
	{
		this.isInDescription = isInDescription;
	}
	
	public FileInputStream getFInStream()
	{
		return fInStream;
	}
	
	public void setFInStream(FileInputStream inStream)
	{
		fInStream = inStream;
	}
	
	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public long getContentLength()
	{
		return contentLength;
	}
	
	public void setContentLength(long contentLength)
	{
		this.contentLength = contentLength;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public Long getFileSize()
	{
		return fileSize;
	}
	
	public void setFileSize(Long fileSize)
	{
		this.fileSize = fileSize;
	}
	
	public String getHttpServer()
	{
		return httpServer;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}
	
	@Override
	public void setCompany(Company company)
	{
		this.company = company;
	}
	
	public String getPageName()
	{
		return pageName;
	}
	
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public YQuote getQuote()
	{
		return quote;
	}
	
	public void setQuote(YQuote quote)
	{
		this.quote = quote;
	}
	
	public List<Category> getBreadcrumbs()
	{
		return breadcrumbs;
	}
	
	public void setBreadcrumbs(List<Category> breadcrumbs)
	{
		this.breadcrumbs = breadcrumbs;
	}
	
	protected HttpServletRequest getHttpServletRequest()
	{
		return request;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	
	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(List<CategoryItem> orderItems)
	{
		this.orderItems = orderItems;
	}
	
	/**
	 * @return the orderItems
	 */
	public List<CategoryItem> getOrderItems()
	{
		return orderItems;
	}
	
	/**
	 * @param quotation the quotation to set
	 */
	public void setQuotation(ArrayList<String> quotation)
	{
		this.quotation = quotation;
	}
	
	/**
	 * @return the quotation
	 */
	public ArrayList<String> getQuotation()
	{
		return quotation;
	}
	
	@Override
	public void setSession(Map session)
	{
		// TODO Auto-generated method stub
		this.session = session;
	}
	
	/**
	 * @param quoteItems the quoteItems to set
	 */
	public void setQuoteItems(List<CategoryItem> quoteItems)
	{
		this.quoteItems = quoteItems;
	}
	
	/**
	 * @return the quoteItems
	 */
	public List<CategoryItem> getQuoteItems()
	{
		return quoteItems;
	}
	
	/**
	 * @param quoteItemsNum the quoteItemsNum to set
	 */
	public void setQuoteItemsNum(List<String> quoteItemsNum)
	{
		this.quoteItemsNum = quoteItemsNum;
	}
	
	/**
	 * @return the quoteItemsNum
	 */
	public List<String> getQuoteItemsNum()
	{
		return quoteItemsNum;
	}
	
	/**
	 * @return the categoryItemPackages
	 */
	public List<CategoryItemPackage> getCategoryItemPackages()
	{
		return categoryItemPackages.getList();
	}
	
	public ArrayList<CategoryItem> getCatItem()
	{
		return catItem;
	}
	
	public void setCatItem(ArrayList<CategoryItem> catItem)
	{
		this.catItem = catItem;
	}
	
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(List<String> filename)
	{
		this.filename = filename;
	}
	
	/**
	 * @return the filename
	 */
	public List<String> getFilename()
	{
		return filename;
	}
	
	/**
	 * Returns shopping cart items.
	 * 
	 * @return - shopping cart items
	 */
	public List<ShoppingCartItem> getCartItemList()
	{
		return cartItemList;
	}
	
	/**
	 * Returns shoppingCart property value.
	 * 
	 * @return - shoppingCart property value
	 */
	public ShoppingCart getShoppingCart()
	{
		return shoppingCart;
	}
	
	public void setInfo(List<RegistrationItemOtherField> info)
	{
		this.info = info;
	}
	
	public List<RegistrationItemOtherField> getInfo()
	{
		return info;
	}
	
	public void setMapFieldValues(Map<String, String> mapFieldValues)
	{
		this.mapFieldValues = mapFieldValues;
	}
	
	public Map<String, String> getMapFieldValues()
	{
		return mapFieldValues;
	}
	
	public List<CategoryItem> getNktiItems()
	{
		return nktiItems;
	}
	
	public void setNktiItems(List<CategoryItem> nktiItems)
	{
		this.nktiItems = nktiItems;
	}
	
	public void setMapRepeatingFieldValues(Map<String, String> mapRepeatingFieldValues)
	{
		this.mapRepeatingFieldValues = mapRepeatingFieldValues;
	}
	
	public Map<String, String> getMapRepeatingFieldValues()
	{
		return mapRepeatingFieldValues;
	}
	
	public void setInfoRepeating(List<RegistrationItemOtherField> infoRepeating)
	{
		this.infoRepeating = infoRepeating;
	}
	
	public List<RegistrationItemOtherField> getInfoRepeating()
	{
		return infoRepeating;
	}
	
	public void setLanguageDelegate(LanguageDelegate languageDelegate)
	{
		this.languageDelegate = languageDelegate;
	}
	
	public LanguageDelegate getLanguageDelegate()
	{
		return languageDelegate;
	}
	
	public String getLanguage()
	{
		return language.getLanguage();
	}
	
	@Override
	public void setLanguage(Language language)
	{
		this.language = language;
	}
	
	/* So far for Aplic Only */
	protected String registrationTitle;
	
	protected String registrationBySet;
	
	public String getRegistrationBySet()
	{
		return registrationBySet;
	}
	
	public void setRegistrationBySet(String registrationBySet)
	{
		this.registrationBySet = registrationBySet;
	}
	
	protected String registrationParticipants;
	
	public RegistrationItemOtherFieldDelegate getRegistrationDelegate()
	{
		return registrationDelegate;
	}
	
	public void setRegistrationDelegate(RegistrationItemOtherFieldDelegate registrationDelegate)
	{
		this.registrationDelegate = registrationDelegate;
	}
	
	public String getRegistrationTitle()
	{
		return registrationTitle;
	}
	
	public void setRegistrationTitle(String registrationTitle)
	{
		this.registrationTitle = registrationTitle;
	}
	
	public String getRegistrationParticipants()
	{
		return registrationParticipants;
	}
	
	public void setRegistrationParticipants(String registrationParticipants)
	{
		this.registrationParticipants = registrationParticipants;
	}
	
	public void setPromoCode(String promoCode)
	{
		this.promoCode = promoCode;
	}
	
	public String getPromoCode()
	{
		if(promoCode != null)
		{
			return promoCode;
		}
		
		if(company.getCompanySettings().getHasReferrals())
		{
			if(session.get(ReferralEnum.PROMOCODE_INSESSION.getValue()) != null)
			{
				return session.get(ReferralEnum.PROMOCODE_INSESSION.getValue()).toString();
			}
			
			return null;
		}
		return promoCode;
	}
	
	public void setReferralItemToClaim(String referralItemToClaim)
	{
		this.referralItemToClaim = referralItemToClaim;
	}
	
	public String getReferralItemToClaim()
	{
		return referralItemToClaim;
	}
	
	public void setReferralId(String referralId)
	{
		this.referralId = referralId;
	}
	
	public String getReferralId()
	{
		return referralId;
	}
	
	public Referral getReferral()
	{
		if(referralId == null || referralId.length() == 0)
		{
			return null;
		}
		Referral referral = new Referral();
		referral = referralDelegate.find(new Long(referralId));
		
		return referral;
	}
	
	public void setActionMode(String actionMode)
	{
		this.actionMode = actionMode;
	}
	
	public String getActionMode()
	{
		return actionMode;
	}
	
	public void setReferralsToBeRedeemed(Long referralsToBeRedeemed[])
	{
		this.referralsToBeRedeemed = referralsToBeRedeemed;
	}
	
	public List<Referral> getReferralList()
	{
		final List<Referral> refList = new ArrayList<Referral>();
		for(final Long refId : referralsToBeRedeemed)
		{
			final Referral ref = referralDelegate.find(refId);
			if(ref != null)
			{
				refList.add(ref);
			}
		}
		return refList;
	}
	
	public Long[] getReferralsToBeRedeemed()
	{
		
		return referralsToBeRedeemed;
	}
	
	public void setRewardToClaim(String rewardToClaim[])
	{
		this.rewardToClaim = rewardToClaim;
	}
	
	public String[] getRewardToClaim()
	{
		return rewardToClaim;
	}
	
	public void setSearchItemKeyword(String searchItemKeyword)
	{
		this.searchItemKeyword = searchItemKeyword;
	}
	
	public String getSearchItemKeyword()
	{
		return searchItemKeyword;
	}
	
	public void setCat_id(String cat_id)
	{
		this.cat_id = cat_id;
	}
	
	public String getCat_id()
	{
		return cat_id;
	}
	
	public void setSubCat_id(String subCat_id)
	{
		this.subCat_id = subCat_id;
	}
	
	public String getSubCat_id()
	{
		return subCat_id;
	}
	
	public Category getHIPRESubCategory()
	{
		if(cat_id != null)
		{
			return categoryDelegate.find(Long.parseLong(cat_id));
		}
		return null;
	}
	
	public PreOrder getPreOrder()
	{
		return preOrder;
	}
	
	public void setPreOrder(PreOrder preOrder)
	{
		this.preOrder = preOrder;
	}
	
	public List<ShoppingCartItem> getWilconCartItems()
	{
		/*ShoppingCart wilconCart = shoppingCartDelegate.find(company, member);*/
		wilconCartItems = shoppingCartItemDelegate.findAllByCompany(company).getList();
		return wilconCartItems;
	}
	
	public void setWilconCartItems(List<ShoppingCartItem> wilconCartItems)
	{
		this.wilconCartItems = wilconCartItems;
	}
	
	public List<Brand> getKuysenCMSBrandList()
	{
		long kid = Long.parseLong(session.get("selectedKuysenCompany").toString());
		Company kuysenCompany = companyDelegate.find(kid);
		if(kuysenCompany != null){
			kuysenCMSBrandList = brandDelegate.findAll(kuysenCompany).getList();
		}
		return kuysenCMSBrandList;
	}
	
	public void setKuysenCMSBrandList(List<Brand> kuysenCMSBrandList)
	{
		this.kuysenCMSBrandList = kuysenCMSBrandList;
	}
	
	public List<SavedEmail> getSavedEmailList()
	{
		savedEmailList = savedEmailDelegate.findAll(company).getList();
		return savedEmailList;
	}
	
	public void setSavedEmailList(List<SavedEmail> savedEmailList)
	{
		this.savedEmailList = savedEmailList;
	}
	
	public List<Notification> getNotificationList()
	{
		notificationList = notificationDelegate.findAll(company).getList();
		return notificationList;
	}
	
	public void setNotificationList(List<Notification> notificationList)
	{
		this.notificationList = notificationList;
	}
	
	/*panasonic*/
	public List<SavedEmail> getSavedEmailListPanasonic()
	{
		savedEmailListPanasonic = savedEmailDelegate.findAllPanasonic(company, Order.desc("updatedOn")).getList();
		return savedEmailListPanasonic;
	}
	
	public void setSavedEmailListPanasonic(List<SavedEmail> savedEmailListPanasonic)
	{
		this.savedEmailListPanasonic = savedEmailListPanasonic;
	}
	/*panasonic*/
	
	//wilcon cartorder
	public List<CartOrder> getCartOrderWilcon(){
		cartOrderWilcon = cartOrderDelegate.findAll(company, member);
		return cartOrderWilcon;
	}
	
	public void setCartOrderWilcon(List<CartOrder> cartOrderWilcon){
		this.cartOrderWilcon = cartOrderWilcon;
	}
	//wilcon cartOrder
		
	
	public String[] getConditionTags()
	{
		return conditionTags;
	}
	
	public void setConditionTags(String[] conditionTags)
	{
		this.conditionTags = conditionTags;
	}
	
	/* So far for Aplic Only */
	
	public List<CategoryItem> getAllCategoryItems()
	{
		
		final Company company = this.company.getSecondaryCompany() != null
			? this.company.getSecondaryCompany()
			: this.company;
		
		final List<CategoryItem> products = new ArrayList<CategoryItem>();
		final String groupid = request.getParameter("group_id");
		if(maxResults == 0)
		{
			maxResults = 9;
		}
		final Group parentGroup = (StringUtils.isNotEmpty(groupid))
			? groupDelegate.find(company, Long.parseLong(groupid))
			: null;
		final ObjectList<CategoryItem> list = categoryItemDelegate.findAll(page > 0
			? page - 1
			: page, maxResults, null, parentGroup, company);
		
		products.addAll(list.getList());
		
		pagingUtil = new PagingUtil(list.getTotal(), maxResults, page < 1
			? page + 1
			: page, maxResults);
		
		return products;
	}
	
	// For PinoyHomecoming
	public void weatherForecastForManila()
	{
		
		InputStream inputXml = null;
		
		try
		{
			inputXml = new URL("http://weather.yahooapis.com/forecastrss?w=1199477&u=c").openConnection().getInputStream();
			
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document doc = builder.parse(inputXml);
			final NodeList yCondition = doc.getElementsByTagName("yweather:condition");
			final NodeList yForecast = doc.getElementsByTagName("yweather:forecast");
			
			// Weather Today
			if(yCondition.getLength() > 0)
			{
				final Element n = (Element) yCondition.item(0);
				final String condition = n.getAttribute("text");
				final String temperature = n.getAttribute("temp");
				
				request.setAttribute("weatherCondition", condition);
				request.setAttribute("currentTemperature", temperature);
			}
			
			// Weather Forecast Tomorrow
			if(yForecast.getLength() > 0)
			{
				final Element n1 = (Element) yForecast.item(0);
				// current date
				final String date = n1.getAttribute("day") + ", " + n1.getAttribute("date");
				
				final Element n2 = (Element) yForecast.item(1);
				final String forecastCondition = n2.getAttribute("text");
				final String forecastTempHigh = n2.getAttribute("high");
				final String forecastTempLow = n2.getAttribute("low");
				
				request.setAttribute("curDate", date);
				request.setAttribute("forecastCondition", forecastCondition);
				request.setAttribute("forecastTempHigh", forecastTempHigh);
				request.setAttribute("forecastTempLow", forecastTempLow);
			}
		}
		catch(final Exception ex)
		{
			System.out.println("WEATHER FORECAST ERROR : " + ex.getMessage());
		}
		finally
		{
			try
			{
				if(inputXml != null)
				{
					inputXml.close();
				}
			}
			catch(final IOException ex)
			{
				System.out.println("WEATHER FORECAST CLOSING ERROR : " + ex.getMessage());
			}
		}
	}
	
	//For nissan
	public String clickLink() throws Exception{
		
		return SUCCESS;
	}
	 /* Get Wendys wendys Categories */
	public List<Category> getPromosCategory()
	{
		final Group promosCategory = groupDelegate.find(company, "PROMOS");
		if(promosCategory != null)
		{
			return categoryDelegate.findAll(company,promosCategory).getList();
		}
		return null;
	}
	
	 /* Get  Categories Map */
		public Map<String, List<Category>> getListCategoryMap()
		{
			Map<String, List<Category>> categoryMap = new HashMap<String, List<Category>>();
			final List<Group> listCategories = groupDelegate.findAll(company).getList();
			for(Group g : listCategories){
				categoryMap.put(g.getName(),categoryDelegate.findAll(company, g).getList());
			}
			return categoryMap;
		}
		
		public Map<String, List<CategoryItem>> getCategoryByNameMap() {
			Map<String, List<CategoryItem>> category_itemMap = new HashMap<String, List<CategoryItem>>();
			final List<Category> listCategories = categoryDelegate.findAll(company, groupDelegate.find(company, "combos")).getList();
			for(Category category : listCategories) {
				category_itemMap.put(category.getName(), categoryItemDelegate.findAll(company, category, Boolean.TRUE).getList());
			}
			return category_itemMap;
		}
		
		/* Get Category Item Map  */
		public Map<String, List<CategoryItem>> getListCategoryItemMap(){
			Map<String, List<CategoryItem>> categoryItemMap = new HashMap<String, List<CategoryItem>>();
			Order[] orders = { Order.asc("name") };
			final List<Group> listCategories = groupDelegate.findAll(company).getList();
			for(Group g: listCategories){
				if(/*company.getId() == CompanyConstants.GURKKA_TEST || */"montero".equalsIgnoreCase(company.getName()) ){
					categoryItemMap.put(g.getName(), categoryItemDelegate.findAllEnabledWithPaging(company, g, -1, -1, orders).getList());
				}
				else{
					categoryItemMap.put(g.getName(), categoryItemDelegate.findAllEnabledWithPaging(company, g, -1, -1, null).getList());
				}
			}
			return categoryItemMap;
		}
		
		public Map<String,CategoryItem> getCategoryItemMap()
		{
			final List<CategoryItem> listitem = categoryItemDelegate.findAll(company).getList();
			Map<String, CategoryItem> categoryItemMap = new HashMap<String, CategoryItem>();
			
			for(CategoryItem item : listitem){
				categoryItemMap.put(String.valueOf(item.getId()), item);
			}
			
			return categoryItemMap;
		}
		
		public Map<String, Member> getMemberInformationMap(){
			final List<Member> listMember = memberDelegate.findAll(company).getList();
			Map<String, Member> memberInformationMap = new HashMap<String, Member>();
			for(Member memberitem : listMember){
				memberInformationMap.put(String.valueOf(memberitem.getId()), memberitem);
			}
			return memberInformationMap;
		}
	/*--------  Get Wendys Branch(es) ---------------*/
	 
	public List<CategoryItem> getStores()
	{
		final Group stores = groupDelegate.find(company, "stores");
		
		if(stores != null)
		{
			List<CategoryItem> tempItem = categoryItemDelegate.findAllEnabledWithPaging(company, stores, -1, -1, null).getList();
			if(tempItem.size()>0){
				Collections.sort(tempItem, new Comparator<CategoryItem>(){
					@Override
					public int compare(final CategoryItem object1, final CategoryItem object2){
						return object1.getName().compareTo(object2.getName());
					}
				});
			}
			return tempItem;
		}
		return null;
	}
	
	public List<CategoryItem> getSearchOtherFields()
	{
		
		final List<CategoryItem> result = null;
		
		return result;
	}
	
	public void setPage(int page)
	{
		this.page = page;
	}
	
	public void setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
	}
	
	public PagingUtil getPagingUtil()
	{
		return pagingUtil;
	}
	
	
	
	public String getM()
	{
		return m;
	}
	
	@Override
	public void setM(String m)
	{
		this.m = m;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse arg0)
	{
		this.response = arg0;
	}

	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage()
	{
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(Integer currentPage)
	{
		this.currentPage = currentPage;
	}
	
	
	
	public String submitsurvey()
	{
		try{
			successUrl = (request.getParameter("successUrl") != null
					? request.getParameter("successUrl")
					: "");
				setErrorUrl((request.getParameter("errorUrl") != null
					? request.getParameter("errorUrl")
					: ""));
			
			
			//Long id = Long.parseLong(request.getParameter("memberid"));
			String strResult = result_;
			
			member = memberDelegate.find(memberid);
			if(member != null){
				
				ItemComment itemComment = new ItemComment();
				itemComment.setCreatedOn(new Date());
				itemComment.setCompany(company);
				/* itemComment.setPage(singlePageDelegate.find(Long.parseLong(request.getParameter("se_page")))); */
				itemComment.setMember(member);
				itemComment.setFirstname("");
				itemComment.setLastname("");
				itemComment.setEmail("");
				itemComment.setContent(message);
				itemComment.setIsValid(true);
				itemComment.setValue(0D);
				/*
				if(itemid != null && itemid > 0){
					CategoryItem storeItem = categoryItemDelegate.find(itemid);
					itemComment.setItem(storeItem);
				}
				*/
				itemComment = itemCommentDelegate.find(itemCommentDelegate.insert(itemComment));
				
				
				for(String s1 : strResult.split(";")){
					String strCategoryItem = "";
					String strRemarks = "";
					String indivComment = "";
					int counter1 = 0;
					for(String s2 : s1.split(":")){
						if(s2.trim().length() > 0){
							if(counter1 == 0){
								strCategoryItem = s2.trim();
							}
							else if(counter1 == 1){
								strRemarks = s2.trim();
							}
							else if(counter1 == 2){
								indivComment = s2.trim();
							}
							CategoryItem item = categoryItemDelegate.find(Long.parseLong(strCategoryItem));
							MemberPoll poll = memberPollDelegate.find(member, item, "");
							boolean update = true;
							if(poll == null){
								update = false;
								poll = new MemberPoll();
							}
							poll.setCategoryItem(item);
							poll.setCompany(company);
							poll.setMember(member);
							poll.setRemarks(strRemarks);
							poll.setDescription(indivComment);
							poll.setItemComment(itemComment);
							if(update){
								memberPollDelegate.update(poll);
							}
							else{
								memberPollDelegate.insert(poll);
							}
							counter1++;
						}
					}
				}
				
				
				
			}
			else{
				return ERROR;
			}
			
		}
		catch(Exception e){
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String getSuccessUrl()
	{
		return successUrl;
	}
	
	public void setSuccessUrl(String successUrl)
	{
		this.successUrl = successUrl;
	}
	
	public String getErrorUrl()
	{
		return errorUrl;
	}
	
	public void setErrorUrl(String errorUrl)
	{
		this.errorUrl = errorUrl;
	}

	public Long getMemberid() {
		return memberid;
	}

	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public String getResult_() {
		return result_;
	}

	public void setResult_(String result_) {
		this.result_ = result_;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	
	public Map<String, OtherField> getOtherFieldMapByName() {
		List<OtherField> tempListOtherField = otherFieldDelegate.find(company);
		Map<String, OtherField> tempOFMap = new HashMap<String, OtherField>();
		if(tempListOtherField!=null){
					for(OtherField ot_fi : tempListOtherField){
					if(ot_fi != null){
						tempOFMap.put(ot_fi.getName(), ot_fi);
					}
			}
		}
		
		return tempOFMap;
	}

	public String getSecurityQuestion(){
		String temp = "";
		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST){
			
		}
		return temp;
	}
	
	
	
	//for HPDS ONLINE NOTIF DURATION: REMOTE SAVE- Causing conflict in CompanyAction
		@SuppressWarnings("rawtypes")
		public void remoteSaveNotif(){
			//Company companyNotif = companyDelegate.find(Str.CompanyConstants.HIPRECISION_DATA_SEARCH);
			try{
				Company companyNotif = companyDelegate.find(Long.valueOf(CompanyConstants.HIPRECISION_DATA_SEARCH));
				logger.info("Entering remoteSaveNotif...");
				String notifDuration = request.getParameter("notifDuration");
				companyNotif.setNotifDuration(Integer.parseInt(notifDuration));
				
				companyDelegate.update(companyNotif);
				logger.info("remoteSaveNotif: SUCCESS---------");
			}
			catch(Exception e){
				logger.info("remoteSaveNotif: ERROR---------");
			}
		}

		
		
		public int getFormType() {
			return formType;
		}

		public void setFormType(int formType) {
			this.formType = formType;
		}

		
		
	
	
	/*
	
	public List<CategoryItem> getGurkkaMainProducts() {
		
		List<CategoryItem> tempListCategoryItem = null;
		int totalItemCount = 0;
		if(company.getId() == CompanyConstants.GURKKA_TEST){
			Group group = groupDelegate.find(company, "Main Products");
			Brand brand = brandDelegate.find(company, group, "Main");
			Order[] orders = {Order.asc("name")};
			//get Pagenumber
			String page = request.getParameter("mainPageNumber");
			int pageNumTemp = -1;
			int maxResultTemp = 9;
			try{
				pageNumTemp = Integer.parseInt(page);
			}
			catch(Exception e){
				pageNumTemp = 1;
			}
			//with paging
			//tempListCategoryItem = categoryItemDelegate.findAllByBrandSortByNameIsFreebiesWithPaging(company, brand, Boolean.FALSE, pageNumTemp - 1, maxResultTemp);
			//without paging
			tempListCategoryItem = categoryItemDelegate.findAllByBrandSortByNameIsFreebiesWithPaging(company, brand, Boolean.FALSE, orders, - 1, -1);
			//tempListCategoryItem = categoryItemDelegate.findAllEnabledNotFreebiesWithPaging(company, group, "Main Products", -1, -1, orders).getList();
		}
		return tempListCategoryItem;
	}
	
	
	public PagingUtil getGurkkaMainProductsPagingUtil()
	{
		PagingUtil tempPagingUtil = null;
		if(company.getId() == CompanyConstants.GURKKA_TEST){
			
			int totalItemCount = 0;
			Group group = groupDelegate.find(company, "Main Products");
			Brand brand = brandDelegate.find(company, group, "Main");
			Order[] orders = {Order.asc("name")};
			//get Pagenumber
			String page = request.getParameter("mainPageNumber");
			int pageNumTemp = -1;
			int maxResultTemp = 9;
			try{
				pageNumTemp = Integer.parseInt(page);
			}
			catch(Exception e){
				pageNumTemp = 1;
			}
			//totalItemCount = categoryItemDelegate.findAllByBrandSortByNameWithPaging(company, brand, - 1, -1).size();
			totalItemCount = categoryItemDelegate.findAllByBrandSortByNameIsFreebiesWithPaging(company, brand, Boolean.FALSE, orders, - 1, -1).size();
			
			return new PagingUtil(totalItemCount, maxResultTemp, pageNumTemp, (totalItemCount/maxResultTemp));
		}
		
		return tempPagingUtil;
	}
	
	public List<CategoryItem> getGurkkaCocktails() {
		List<CategoryItem> tempListCategoryItem = null;
		int totalItemCount = 0;
		//request.setAttribute("testinglangto123", "the quick brown fox");
		if(company.getId() == CompanyConstants.GURKKA_TEST){
			Group group = groupDelegate.find(company, "Cocktails");
			Order[] orders = {Order.asc("name")};
			//get PageNumber
			String page = request.getParameter("cocktailsPageNumber");
			int pageNumTemp = -1;
			int maxResultTemp = 9;
			try{
				pageNumTemp = Integer.parseInt(page);
			}
			catch(Exception e){
				pageNumTemp = 1;
			}
			tempListCategoryItem = categoryItemDelegate.findAllEnabledByGroupWithPaging(company, group, -1, -1, orders).getList();
		}
		return tempListCategoryItem;
	}
	
	public PagingUtil getGurkkaCocktailsPagingUtil()
	{
		PagingUtil tempPagingUtil = null;
		if(company.getId() == CompanyConstants.GURKKA_TEST){
			int totalItemCount = 0;
			Group group = groupDelegate.find(company, "Cocktails");
			Order[] orders = {Order.asc("name")};
			//get Pagenumber
			String page = request.getParameter("cocktailsPageNumber");
			int pageNumTemp = -1;
			int maxResultTemp = 9;
			try{
				pageNumTemp = Integer.parseInt(page);
			}
			catch(Exception e){
				pageNumTemp = 1;
			}
			totalItemCount = categoryItemDelegate.findAllEnabledByGroupWithPaging(company, group, -1, -1, orders).getList().size();
			
			return new PagingUtil(totalItemCount, maxResultTemp, pageNumTemp, (totalItemCount/maxResultTemp));
		}
		
		return tempPagingUtil;
	}
	
	public List<CategoryItem> getGurkkaGuestProducts() {
		List<CategoryItem> tempListCategoryItem = null;
		int totalItemCount = 0;
		if(company.getId() == CompanyConstants.GURKKA_TEST){	
			Group group = groupDelegate.find(company, "Main Products");
			Brand brand = brandDelegate.find(company, group, "Guest");
			Order[] orders = {Order.asc("name")};
			//get Pagenumber
			String page = request.getParameter("guestPageNumber");
			int pageNumTemp = -1;
			int maxResultTemp = 9;
			try{
				pageNumTemp = Integer.parseInt(page);
			}
			catch(Exception e){
				pageNumTemp = 1;
			}
		
			//with paging
			//tempListCategoryItem = categoryItemDelegate.findAllByBrandSortByNameIsFreebiesWithPaging(company, brand, Boolean.FALSE, pageNumTemp - 1, maxResultTemp);
			//without paging
			tempListCategoryItem = categoryItemDelegate.findAllByBrandSortByNameIsFreebiesWithPaging(company, brand, Boolean.FALSE, orders, - 1, -1);
			//tempListCategoryItem = categoryItemDelegate.findAllEnabledSKUWithPaging(company, group, "Guest Products", -1, -1, orders).getList();
			
		}
		return tempListCategoryItem;
	}
	
	public PagingUtil getGurkkaGuestProductsPagingUtil()
	{
		PagingUtil tempPagingUtil = null;
		if(company.getId() == CompanyConstants.GURKKA_TEST){
			
			int totalItemCount = 0;
			Group group = groupDelegate.find(company, "Main Products");
			Brand brand = brandDelegate.find(company, group, "Guest");
			Order[] orders = {Order.asc("name")};
			//get Pagenumber
			String page = request.getParameter("guestPageNumber");
			int pageNumTemp = -1;
			int maxResultTemp = 9;
			try{
				pageNumTemp = Integer.parseInt(page);
			}
			catch(Exception e){
				pageNumTemp = 1;
			}
			totalItemCount = categoryItemDelegate.findAllByBrandSortByNameIsFreebiesWithPaging(company, brand, Boolean.FALSE, orders, - 1, -1).size();
			//totalItemCount = categoryItemDelegate.findAllEnabledSKUWithPaging(company, group, "Guest Products", -1, -1, orders).getList().size();
			
			return new PagingUtil(totalItemCount, maxResultTemp, pageNumTemp, (totalItemCount/maxResultTemp));
		}
		
		return tempPagingUtil;
	}
	
	public List<CategoryItem> getGurkkaFreebies() {
		List<CategoryItem> tempListCategoryItem = null;
		int totalItemCount = 0;
		if(company.getId() == CompanyConstants.GURKKA_TEST){	
			Group group = groupDelegate.find(company, "Main Products");
			//Brand brand = brandDelegate.find(company, group, "Guest");
			//get Pagenumber
			String page = request.getParameter("freebiesPageNumber");
			int pageNumTemp = -1;
			int maxResultTemp = 9;
			try{
				pageNumTemp = Integer.parseInt(page);
			}
			catch(Exception e){
				pageNumTemp = 1;
			}
		
			//with paging
			//tempListCategoryItem = categoryItemDelegate.findAllByBrandSortByNameWithPaging(company, brand, pageNumTemp - 1, maxResultTemp);
			//without paging
			Order[] orders = { Order.asc("itemDate") };
			//tempListCategoryItem = categoryItemDelegate.findAllEnabledByGroupAndItemDate(company, group, orders, - 1, -1);
			tempListCategoryItem = categoryItemDelegate.findAllByBrandSortByNameIsFreebiesWithPaging(company, null, Boolean.TRUE, orders, - 1, -1);
			
		}
		return tempListCategoryItem;
	}
	
	public PagingUtil getGurkkaFreebiesPagingUtil()
	{
		PagingUtil tempPagingUtil = null;
		if(company.getId() == CompanyConstants.GURKKA_TEST){
			
			int totalItemCount = 0;
			Group group = groupDelegate.find(company, "Main Products");
			//Brand brand = brandDelegate.find(company, group, "Guest");
			//get Pagenumber
			String page = request.getParameter("freebiesPageNumber");
			int pageNumTemp = -1;
			int maxResultTemp = 9;
			try{
				pageNumTemp = Integer.parseInt(page);
			}
			catch(Exception e){
				pageNumTemp = 1;
			}
			Order[] orders = { Order.asc("itemDate") };
			totalItemCount = categoryItemDelegate.findAllByBrandSortByNameIsFreebiesWithPaging(company, null, Boolean.TRUE, orders, - 1, -1).size();
			
			return new PagingUtil(totalItemCount, maxResultTemp, pageNumTemp, (totalItemCount/maxResultTemp));
		}
		
		return tempPagingUtil;
	}

	public String doCocktails() {
		
		String id = request.getParameter("category_id");
		if(id != null){
			
			
			Category category = categoryDelegate.find(Long.parseLong(id));
			if(category != null){
				request.setAttribute("category", category);
				Group tempGroup = groupDelegate.find(company, GurkkaConstants.COCKTAILS_GROUP_NAME );
				
				
				String tempCategoryTag = "";
				//String tempFilterTags = "";
				
				Order[] orders = { Order.asc("name") };
				
				Map<Long, String> CocktailItemCategoryInStringFormatMap = new HashMap<Long, String>();
				List<String> listGlassOptions = new ArrayList<String>();
				Map<String, Integer> cocktailCategoryMap = new HashMap<String, Integer>();
				List<Category> listCocktailCategory = new ArrayList<Category>();
				
				List<CategoryItem> listCocktailCategoryItem = new ArrayList<CategoryItem>();
				
				List<CategoryItem> listCocktailFilterTags = new ArrayList<CategoryItem>();
				//List<CategoryItem> listCocktailsItemFilterTags = new ArrayList<CategoryItem>();
				
				List<Member> listMember = memberDelegate.findAll(company).getList();
				Map<String, Member> memberInformationMap = new HashMap<String, Member>();
				
				//get Cocktail Category Item
				for(CategoryItem item : categoryItemDelegate.findAllEnabledByGroupWithPaging(company, tempGroup, -1, -1, orders)){
					CategoryItemOtherField tempOF = item.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_CATEGORYTAG_NAME);
					if(tempOF != null){
						if(tempOF.getContent().indexOf(id+";") >= 0){
							listCocktailCategoryItem.add(item);
						}
					}
					
					
				}
				request.setAttribute("listCocktailCategoryItem", listCocktailCategoryItem);
			
				//get Map of Cocktail Category 
				for(Category tempCateg : tempGroup.getCategories()){
					if(!tempCateg.getId().equals(GurkkaConstants.COCKTAILS_CATEGORY_COCKTAILITEM_ID)){
						cocktailCategoryMap.put(tempCateg.getName(),countOfContainingOtherFieldIdOnOtherField(tempGroup, tempCateg.getId()));
					}
				}
				request.setAttribute("cocktailCategoryMap", cocktailCategoryMap);
				
				//get List of Cocktail Category
				for(Category tempCateg : tempGroup.getCategories()){
					if(!tempCateg.getId().equals(GurkkaConstants.COCKTAILS_CATEGORY_COCKTAILITEM_ID)){
						listCocktailCategory.add(tempCateg);
					}
				}
				request.setAttribute("listCocktailCategory", listCocktailCategory);
				
				
				
				request.setAttribute("cocktailsItemCategoryTag", tempCategoryTag);
				//get List of Cocktails Filter Tag
				tempGroup = groupDelegate.find(company, GurkkaConstants.COCKTAILSFILTERTAG_GROUP_NAME);
				if(tempGroup != null){
					listCocktailFilterTags = categoryItemDelegate.findAllEnabledByGroupWithPaging(company, tempGroup, -1, -1, orders).getList();
				}
				request.setAttribute("listCocktailFilterTags", listCocktailFilterTags);
				
				//get Cocktail Category String Map
				//abc
				for(CategoryItem catItem : categoryItemDelegate.findAllEnabledByGroupWithPaging(company, tempGroup, -1, -1, orders)){
					CocktailItemCategoryInStringFormatMap.put(catItem.getId(), procCategoryInStringFormat(catItem));
				}
				request.setAttribute("CocktailItemCategoryInStringFormatMap", CocktailItemCategoryInStringFormatMap);
				
				
				
				
				
				
				
				
				
				
				
				
				for(Member memberitem : listMember){
					memberInformationMap.put(String.valueOf(memberitem.getId()), memberitem);
				}
				request.setAttribute("memberInformationMap", memberInformationMap);
			}
		}
		return SUCCESS;
	}

	public String doCocktailsItem(){
		
		String id = request.getParameter("item_id");
		CategoryItem item = new CategoryItem();
		Group tempGroup = groupDelegate.find(company, GurkkaConstants.COCKTAILS_GROUP_NAME );
		
		String tempRelatedCocktails = "";
		String tempRelatedProducts = "";
		String tempRelatedPromoBaskets = "";
		String tempRelatedTrivias = "";
		String tempGlassOptions = "";
		String tempCategoryTag = "";
		String tempFilterTags = "";
		
		Order[] orders = { Order.asc("name") };
		
		List<CategoryItem> listRelatedCocktails = new ArrayList<CategoryItem>();
		List<CategoryItem> listRelatedProducts = new ArrayList<CategoryItem>();
		List<CategoryItem> listRelatedPromoBaskets = new ArrayList<CategoryItem>();
		List<SinglePage> listRelatedTrivias = new ArrayList<SinglePage>();
		List<CategoryItem> listRecipeSubmitted = new ArrayList<CategoryItem>();
		List<String> listGlassOptions = new ArrayList<String>();
		Map<String, Integer> cocktailCategoryMap = new HashMap<String, Integer>();
		List<Category> listCocktailCategory = new ArrayList<Category>();
		List<CategoryItem> listCocktailFilterTags = new ArrayList<CategoryItem>();
		List<CategoryItem> listCocktailsItemFilterTags = new ArrayList<CategoryItem>();
		Map<Long, String> CocktailItemCategoryInStringFormatMap = new HashMap<Long, String>();
		List<ItemComment> listCocktailsItemItemComment = new ArrayList<ItemComment>();
		
		
		List<Member> listMember = memberDelegate.findAll(company).getList();
		Map<String, Member> memberInformationMap = new HashMap<String, Member>();
		
		
		CategoryItemOtherField catItemOtherField_RelatedCocktails = new CategoryItemOtherField();
		CategoryItemOtherField catItemOtherField_RelatedProducts = new CategoryItemOtherField();
		CategoryItemOtherField catItemOtherField_RelatedPromoBaskets = new CategoryItemOtherField();
		CategoryItemOtherField catItemOtherField_RelatedTrivias = new CategoryItemOtherField();
		CategoryItemOtherField catItemOtherField_GlassOptions = new CategoryItemOtherField();
		
		
		if(id != null){
			item = categoryItemDelegate.find(Long.parseLong(id));
			request.setAttribute("item", item);
			//get related cocktails
			//tempRelatedCocktails = item.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_RELATEDCOCKTAILS_NAME).getContent();
			catItemOtherField_RelatedCocktails = item.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_RELATEDCOCKTAILS_NAME);
			if(catItemOtherField_RelatedCocktails != null){
				tempRelatedCocktails = catItemOtherField_RelatedCocktails.getContent();
				for(String s : tempRelatedCocktails.split(";")){
					try{
						if(!StringUtils.isEmpty(s)){
							CategoryItem tempCategoryItem = categoryItemDelegate.find(Long.parseLong(s));
							listRelatedCocktails.add(tempCategoryItem);
						}
					}
					catch(Exception e){
						
					}
				}
				request.setAttribute("listRelatedCocktails", listRelatedCocktails);
				//member information MAP
			}
			
			//get related products
			catItemOtherField_RelatedProducts = item.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_RELATEDPRODUCTS_NAME);
			if(catItemOtherField_RelatedProducts != null){
				tempRelatedProducts = catItemOtherField_RelatedProducts.getContent();
				for(String s: tempRelatedProducts.split(";")){
					try{
						if(!StringUtils.isEmpty(s)){
							CategoryItem tempCategoryItem = categoryItemDelegate.find(Long.parseLong(s));
							listRelatedProducts.add(tempCategoryItem);
						}
					}
					catch(Exception e){
						
					}
				}
				request.setAttribute("listRelatedProducts", listRelatedProducts);
			}
			
			//get related promo basket
			catItemOtherField_RelatedPromoBaskets = item.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_RELATEDPROMOBASKETS_NAME);
			if(catItemOtherField_RelatedPromoBaskets != null){
				tempRelatedPromoBaskets = catItemOtherField_RelatedPromoBaskets.getContent();
				for(String s : tempRelatedPromoBaskets.split(";")){
					try{
						if(!StringUtils.isEmpty(s)){
							CategoryItem tempCategoryItem = categoryItemDelegate.find(Long.parseLong(s));
							listRelatedPromoBaskets.add(tempCategoryItem);
						}
					}
					catch(Exception e){
						
					}
				}
				request.setAttribute("listRelatedPromoBaskets", listRelatedPromoBaskets);
				
			}
			
			//get related trivia
			catItemOtherField_RelatedTrivias = item.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_RELATEDTRIVIAS_NAME);
			if(catItemOtherField_RelatedTrivias != null){
				tempRelatedTrivias =catItemOtherField_RelatedTrivias.getContent();
				for(String s : tempRelatedTrivias.split(";")){
					try{
						if(!StringUtils.isEmpty(s)){
							SinglePage tempSinglePage = singlePageDelegate.find(Long.parseLong(s));
							listRelatedTrivias.add(tempSinglePage);
						}
					}
					catch(Exception e){
						
					}
				}
				request.setAttribute("listRelatedTrivias", listRelatedTrivias);
			}
			
			//get Recipe Submitted
			
			listRecipeSubmitted = categoryItemDelegate.findAll(company, tempGroup, item.getSku()).getList();
			request.setAttribute("listRecipeSubmitted", listRecipeSubmitted);
			
			//get Glass Option
			catItemOtherField_GlassOptions = item.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_GLASSOPTION_NAME);
			if(catItemOtherField_GlassOptions != null){
				tempGlassOptions = catItemOtherField_GlassOptions.getContent();
				for(String s : tempGlassOptions.split(";")){
					try{
						if(!StringUtils.isEmpty(s)){
							listGlassOptions.add(s);
						}
					}
					catch(Exception e){
						
					}
				}
				request.setAttribute("listGlassOptions", listGlassOptions);
			}
			
			//get Map of Cocktail Category 
			for(Category tempCateg : tempGroup.getCategories()){
				if(!tempCateg.getId().equals(GurkkaConstants.COCKTAILS_CATEGORY_COCKTAILITEM_ID)){
					cocktailCategoryMap.put(tempCateg.getName(),countOfContainingOtherFieldIdOnOtherField(tempGroup, tempCateg.getId()));
				}
			}
			request.setAttribute("cocktailCategoryMap", cocktailCategoryMap);
			
			//get List of Cocktail Category
			for(Category tempCateg : tempGroup.getCategories()){
				if(!tempCateg.getId().equals(GurkkaConstants.COCKTAILS_CATEGORY_COCKTAILITEM_ID)){
					listCocktailCategory.add(tempCateg);
				}
			}
			request.setAttribute("listCocktailCategory", listCocktailCategory);
			
			//get Cocktail Item Category in String
			tempCategoryTag = procCategoryInStringFormat(item);
			request.setAttribute("cocktailsItemCategoryTag", tempCategoryTag);
			
			
			//get Cocktail Category String Map
			//abc
			for(CategoryItem catItem : categoryItemDelegate.findAllEnabledByGroupWithPaging(company, tempGroup, -1, -1, orders)){
				CocktailItemCategoryInStringFormatMap.put(catItem.getId(), procCategoryInStringFormat(catItem));
			}
			request.setAttribute("CocktailItemCategoryInStringFormatMap", CocktailItemCategoryInStringFormatMap);
			
			//get List of Cocktails Filter Tag
			tempGroup = groupDelegate.find(company, GurkkaConstants.COCKTAILSFILTERTAG_GROUP_NAME);
			if(tempGroup != null){
				listCocktailFilterTags = categoryItemDelegate.findAllEnabledByGroupWithPaging(company, tempGroup, -1, -1, orders).getList();
			}
			request.setAttribute("listCocktailFilterTags", listCocktailFilterTags);
			
			//get Item Cocktails Filter Tag
			tempFilterTags = item.getSearchTags();
			for(String s : tempFilterTags.split(";")){
				if(!StringUtils.isEmpty(s)){
					CategoryItem tempCatItem_  = categoryItemDelegate.find(Long.parseLong(s));
					if(tempCatItem_ != null){
						listCocktailsItemFilterTags.add(tempCatItem_);
					}
				}
			}
			request.setAttribute("listCocktailsItemFilterTags", listCocktailsItemFilterTags);
			
			//get cocktails item ItemComment List
			Order[] tempOrders = { Order.asc("createdOn")};
			//listCocktailsItemItemComment = itemCommentDelegate.getCommentsByItem(item, tempOrders).getList();
			listCocktailsItemItemComment = itemCommentDelegate.findAllParentCommentsByItem(item, tempOrders).getList();
			request.setAttribute("listCocktailsItemItemComment", listCocktailsItemItemComment);
			System.out.println("############# comment :: "+ listCocktailsItemItemComment);
			
			
			
			
			
			
			
			
			for(Member memberitem : listMember){
				memberInformationMap.put(String.valueOf(memberitem.getId()), memberitem);
			}
			request.setAttribute("memberInformationMap", memberInformationMap);
			
		}
		return SUCCESS;
	}
	
	private Integer countOfContainingOtherFieldIdOnOtherField(Group tempGroup, Long otherFieldId){
		Integer totalCount = 0;
		List<CategoryItem> listCategoryItem = categoryItemDelegate.findAllByGroup(company, tempGroup).getList();
		for(CategoryItem catItem : listCategoryItem){
			CategoryItemOtherField catItemOF = catItem.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_CATEGORYTAG_NAME);
			if(catItemOF != null){
				if(catItemOF.getContent().indexOf(String.valueOf(otherFieldId)+";") >=0){
					totalCount++;
				}
			}
		}
		return totalCount;
	}
	
	private String procCategoryInStringFormat(CategoryItem item){
		
		String tempCategoryTag = "";
		CategoryItemOtherField catItemOtherField_CategoryTag = new CategoryItemOtherField();
		
		catItemOtherField_CategoryTag =  item.getCategoryItemOtherFieldMap().get(GurkkaConstants.COCKTAILS_OTHERFIELD_CATEGORYTAG_NAME);
		if(catItemOtherField_CategoryTag != null){
			
			for(String s : catItemOtherField_CategoryTag.getContent().split(";")){
				if(!StringUtils.isEmpty(s)){
					CategoryItem tempCatItem_  = categoryItemDelegate.find(Long.parseLong(s));
					if(tempCatItem_ != null){
						tempCategoryTag = tempCategoryTag + tempCatItem_.getName() + ", ";
					}
				}
			}
			
		}
		 
		return tempCategoryTag;
	}
	
	public String submitreview() {
		String id = request.getParameter("item_id");
		String memberid = request.getParameter("member_id");
		
		String value = request.getParameter("value");
		String comment = request.getParameter("comment");
		double dblValue = 0.0;
		CategoryItem item = new CategoryItem();
		
		successUrl = (request.getParameter("successUrl") != null
				? request.getParameter("successUrl")
				: "");
			setErrorUrl((request.getParameter("errorUrl") != null
				? request.getParameter("errorUrl")
				: ""));
			
		member = memberDelegate.find(Long.parseLong(memberid));
		if(member != null){
			
			if(id != null)	{
				
				item = categoryItemDelegate.find(Long.parseLong(id));
				if(item != null){
					
					try{
						dblValue = Double.parseDouble(value);
					}
					catch(Exception e){
						dblValue = 0.0;
						e.printStackTrace();
					}
					if(itemCommentDelegate.findCommentsByValueAndContent(company, item, dblValue, comment, member).getList().size() == 0){
						
						ItemComment itemComment = new ItemComment();
						itemComment.setCreatedOn(new Date());
						itemComment.setCompany(company);
						itemComment.setMember(member);
						itemComment.setFirstname("");
						itemComment.setLastname("");
						itemComment.setEmail("");
						itemComment.setContent(comment);
						itemComment.setIsValid(true);
						itemComment.setItem(item);
						
						itemComment.setValue(dblValue);
						itemCommentDelegate.insert(itemComment);
						
					}
					else
					{
						return ERROR;
					}
				}
			}
		
		}
		else{
			
			return ERROR;
		}
		
		return SUCCESS;
	}
	*/
	
}
