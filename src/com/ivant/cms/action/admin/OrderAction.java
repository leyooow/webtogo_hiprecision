package com.ivant.cms.action.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cart.action.CheckoutAction;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CartOrderStatusHistoryDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CartOrderStatusHistory;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.ShippingStatus;
import com.ivant.cms.enums.ShippingType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.constants.Constant;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
//import com.ivant.cms.entity.Order;

/**
 * Backend action for creating, viewing and editing orders from companies.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class OrderAction extends ActionSupport 
	implements Preparable, ServletRequestAware, CompanyAware, UserAware, ServletContextAware{
	
	private static Logger logger = LoggerFactory.getLogger(OrderAction.class);
	private static final long serialVersionUID = 3617798953897716137L;

	private static int ITEMS_PER_PAGE = Constant.ITEMS_PER_PAGE;
	
	/** Object responsible for orders CRUD tasks */
	private CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	private MemberFileDelegate memberFileDelegate = MemberFileDelegate.getInstance();
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	protected GroupDelegate groupDelegate = GroupDelegate.getInstance();
	protected CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private CartOrderStatusHistoryDelegate cartOrderStatusHistoryDelegate = CartOrderStatusHistoryDelegate.getInstance();
	
	
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	
	
	protected Member member;
	
	protected ServletContext servletContext;
	
	/** Current webtogo admin user, must not be null*/
	@SuppressWarnings("unused")
	private User user;
	/** Data that is passed through the session */
	private HttpServletRequest request;
	/** Current session webtogo company, must not be null*/
	private Company company;
	
	/** Url to redirect if action is executed successfully */
	private String successUrl;
	/** Current page number for paging, can be null*/
	public Integer pageNumber;
	
	
	/** List of orders by the company, can be 0 or more*/
	private List<CartOrder> orderList;
	private List<CartOrder> orderListAll;
	private List<CartOrder> orderListWithNoErrorStatus=new ArrayList<CartOrder>();
	
	private List<Member> members;
	
	
	//private UserDelegate userDelegate = UserDelegate.getInstance();
	private List<SinglePage> singlePages;
	
	
	
	private InputStream inputStream;

	public String notificationMessage;
	
	@Override
	public void prepare() throws Exception {
		//initialize paging
		/*may 07,2012, 
		 * 
		 * may bug tong gantong declaration ng paging.. try using the same
		 * implementation katulad nung sa members.java
		 * may iba pang page na ganto din ung implementation like sa wishlist
		 * 
		 */
		setPageNumber();
		//get number of retrieved messages for current company for paging purposes
		BigInteger itemCount = new BigInteger("0");
		if(company.getName().equalsIgnoreCase("wendys")){
			ITEMS_PER_PAGE = 10;
			itemCount = cartOrderDelegate.getPaidOrderCount(company);
		}
		else{
			itemCount = cartOrderDelegate.getOrderCount(company);
		}
				
		if(itemCount == null) 
			itemCount = new BigInteger("0");
		
			members=memberDelegate.findAll(company).getList();
			
			
			if(company.getName().equalsIgnoreCase("wendys")){
				String orderStatus = "";
				String paymentStatus = "";
				String shippingType = "";
				String branch = "";
				String branch_location = "comments";
				Boolean isForInfo1 = false;
				String branch_comments = "";
				String branch_info1 = "";
				
				if(request.getParameter("orderstatus")!=null){
					orderStatus = request.getParameter("orderstatus");
				}
				if(request.getParameter("paymentstatus")!=null){
					paymentStatus = request.getParameter("paymentstatus");
				}
				else{
					//paymentStatus = "paid";
					paymentStatus = "";
				}
				if(request.getParameter("ordertype")!=null){
					shippingType = request.getParameter("ordertype");
				}
				
				
				
				if(request.getParameter("branch")!=null && request.getParameter("branch")!=""){
					branch = request.getParameter("branch");
					branch_info1 = String.valueOf(categoryItemDelegate.findByName(company, branch).getId());
					branch_comments = "Prefferred Store: " + branch;
					
				}
				
				//for(int i=0; i<100000;i++){
				//	System.out.println("#############"+branch_info1+"################"+branch_comments);
				//}
				
				if(request.getParameter("memberid")!=null && request.getParameter("memberid")!=""){
					Long m_id = Long.parseLong(request.getParameter("memberid"));
					member = memberDelegate.find(m_id);
					orderList = cartOrderDelegate.listAllPaidOrders(member, company, ITEMS_PER_PAGE, pageNumber-1,orderStatus, paymentStatus, shippingType, branch_comments,branch_info1);
					orderListAll = cartOrderDelegate.listAllPaidOrders(member, company, -1, -1, orderStatus, paymentStatus, shippingType, branch_comments, branch_info1);
				}
				else{
					orderList = cartOrderDelegate.listAllPaidOrders(null, company, ITEMS_PER_PAGE, pageNumber-1,orderStatus, paymentStatus, shippingType, branch_comments,branch_info1);
					orderListAll = cartOrderDelegate.listAllPaidOrders(null, company, -1, -1, orderStatus, paymentStatus, shippingType, branch_comments, branch_info1);
				}
				
				itemCount = BigInteger.valueOf(orderListAll.size());
			}
			else{
				if(company.getName().equalsIgnoreCase("gurkkatest")){}else{
					orderList = cartOrderDelegate.listAllOrders(company, ITEMS_PER_PAGE, pageNumber-1);
					orderListAll = cartOrderDelegate.listAllOrders(company, -1, -1);
				}
			}
			
			//itemCount = BigInteger.valueOf(orderList.size());
			//cartOrderDelegate.findAll(company, member)
			if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2"))){
				int i=0;
				for(CartOrder cartOrder:orderList){
					if(cartOrder.getStatus().toString().equalsIgnoreCase(ERROR)){
						orderList.get(i).setIsValid(false);
					}
					i++;
				}
			}
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.HIPRECISION_ONLINE_STORE)) {
				
				if(request.getParameter("branch") != null) {
					ITEMS_PER_PAGE = 1000;
					List<CartOrder> list = new ArrayList<CartOrder>();
					String branch = (String) request.getParameter("branch");
					for(CartOrder cartOrder : orderList) {
						if(cartOrder.getShippingInfo() != null
								&& cartOrder.getShippingInfo().getShippingInfo() != null
									&& cartOrder.getShippingInfo().getShippingInfo().getAddress1().equalsIgnoreCase(branch)) {
							list.add(cartOrder);
						}
					}
					orderList = list;
					itemCount = BigInteger.valueOf(orderList.size());
				}
				
				MultiPage multiPage = multiPageDelegate.findJsp(company, "Branch");
				singlePages = multiPage.getItems();
			}
			
			setPaging(itemCount.intValue(), ITEMS_PER_PAGE);
			
	}
	
	@Override
	public String execute() throws Exception {
		int year = new DateTime().getYear();
		request.setAttribute("year", year);
		
		logger.info("Executing OrderAction .......");
		return SUCCESS;
	}
	
	final Map<Long, List<CartOrderItem>> wendysOrderItemMap = new HashMap<Long, List<CartOrderItem>>();
	
	public Map<Long, List<CartOrderItem>> getWendysOrderItemMap(){
		if(wendysOrderItemMap.isEmpty()){
		/*	final Map<Long, List<CartOrderItem>> wendysOrderItemMap = new HashMap<Long, List<CartOrderItem>>();
			final List<CartOrder> listCartOrder = cartOrderDelegate.listAllOrders(company, 10, pageNumber);*/
			for(CartOrder order : orderList){
				wendysOrderItemMap.put(order.getId(), cartOrderItemDelegate.findAll(order).getList());
			}
		}
		return wendysOrderItemMap;
	}
	
	public Map<Long, Double> getWendysOrderItemTotalPriceMap(){
		
		final Map<Long, Double> wendysOrderItemTotalPriceMap = new HashMap<Long, Double>();
		//try{
			
			
			final List<CartOrder> listCartOrder = cartOrderDelegate.findAllByCompany(company);
			for(CartOrder order : listCartOrder){
				Double dPriceTotal = 0.0;
				for(CartOrderItem orderItem : order.getItems()){
					if(orderItem.getStatus().equalsIgnoreCase("ok")){
						dPriceTotal = dPriceTotal + (orderItem.getItemDetail().getPrice()*orderItem.getQuantity());
					}
				}
				wendysOrderItemTotalPriceMap.put(order.getId(), dPriceTotal);
			}
			/*for(int i = 0; i <100000; i++){
				System.out.println("#######################################################");
				System.out.println("#######################################################");
				System.out.println(wendysOrderItemTotalPriceMap);
				System.out.println("#######################################################");
				System.out.println("#######################################################");
			}
			*/
		//}
		//catch(Exception e){
			//for(int i = 0; i <1000000; i++){
		//		System.out.println(e);
			//}
		//}
		return wendysOrderItemTotalPriceMap;
	}
	
	public String addOrderComment()
	{
		try
		{
			final String orderID = request.getParameter("orderID");
			final CartOrder cartOrder = cartOrderDelegate.find(Long.valueOf(orderID));
			final String comments = request.getParameter("comments");
			cartOrder.setComments(comments);
			cartOrderDelegate.update(cartOrder);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public Map<String, String> getNewPickupCountMap(){
		
		Map<String, String> newPickupCount = new HashMap<String, String>();
		List<CategoryItem> itemList = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem itm_ : itemList){
			newPickupCount.put(itm_.getName(), String.valueOf(cartOrderDelegate.getNewPickupCount(company, itm_.getName())));
			
			
		}
		return newPickupCount;
	}
	
	public Map<String, String> getPendingPickupCountMap(){
		Map<String, String> pendingPickupCount = new HashMap<String, String>();
		List<CategoryItem> itemList = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem itm_ : itemList){
			pendingPickupCount.put(itm_.getName(), String.valueOf(cartOrderDelegate.getPendingPickupCount(company, itm_.getName())));
		}
		return pendingPickupCount;
	}
	
	public Map<String, String> getCompletedPickupCountMap(){
		Map<String, String> completedPickupCount = new HashMap<String, String>();
		List<CategoryItem> itemList = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem itm_ : itemList){
			completedPickupCount.put(itm_.getName(), String.valueOf(cartOrderDelegate.getCompletedPickupCount(company, itm_.getName())));
		}
		return completedPickupCount;
	}
	
	public Map<String, String> getCancelledPickupCountMap(){
		Map<String, String> cancelledPickupCount = new HashMap<String, String>();
		List<CategoryItem> itemList = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem itm_ : itemList){
			cancelledPickupCount.put(itm_.getName(), String.valueOf(cartOrderDelegate.getCancelledPickupCount(company, itm_.getName())));
		}
		return cancelledPickupCount;
	}
	
	public Map<String, String> getNewDeliveryCountMap(){
		Map<String, String> newDeliveryCount = new HashMap<String, String>();
		List<CategoryItem> itemList = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem itm_ : itemList){
			newDeliveryCount.put(itm_.getName(), String.valueOf(cartOrderDelegate.getNewDeliveryCount(company, String.valueOf(itm_.getId()))));
		}
		return newDeliveryCount;
	}
	
	public Map<String, String> getPendingDeliveryCountMap(){
		Map<String, String> pendingDeliveryCount = new HashMap<String, String>();
		List<CategoryItem> itemList = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem itm_ : itemList){
			pendingDeliveryCount.put(itm_.getName(), String.valueOf(cartOrderDelegate.getPendingDeliveryCount(company, String.valueOf(itm_.getId()))));
		}
		return pendingDeliveryCount;
	}
	
	public Map<String, String> getCompletedDeliveryCountMap(){
		Map<String, String> completedDeliveryCount = new HashMap<String, String>();
		List<CategoryItem> itemList = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem itm_ : itemList){
			completedDeliveryCount.put(itm_.getName(), String.valueOf(cartOrderDelegate.getCompletedDeliveryCount(company, String.valueOf(itm_.getId()))));
		}
		return completedDeliveryCount;
	}
	
	public Map<String, String> getCancelledDeliveryCountMap(){
		Map<String, String> cancelledDeliveryCount = new HashMap<String, String>();
		List<CategoryItem> itemList = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem itm_ : itemList){
			cancelledDeliveryCount.put(itm_.getName(), String.valueOf(cartOrderDelegate.getCancelledDeliveryCount(company, String.valueOf(itm_.getId()))));
		}
		return cancelledDeliveryCount;
	}
	
	public CartOrder getLastOrder() {
		CartOrder cart_Order_Item = null;
		if(request.getParameter("memberid") != null){
			try{
				member = memberDelegate.find(Long.parseLong(request.getParameter("memberid")));
				cart_Order_Item = cartOrderDelegate.findLastCartOrder(company, member, new Order[] { Order.desc("createdOn")});
			}
			catch(Exception e){
				e.printStackTrace();
				cart_Order_Item = null;
			}
		}
		return cart_Order_Item;
	}
	
	public MemberFile getLastRedeem(){
		MemberFile memberFile = null;
		if(request.getParameter("memberid") != null) {
			try{
				member = memberDelegate.find(Long.parseLong(request.getParameter("memberid")));
				memberFile = memberFileDelegate.findLastByApprovedDate(company, member);
				
			}
			catch(Exception e){
				e.printStackTrace();
				memberFile = null;
			}
		}
		return memberFile;
	}
	
	public List<CategoryItem> getMemberEarnedPoints(){
		Group earnedPointsGroup = groupDelegate.find(company, "Earned Points");
		Category earnedPointsCat = categoryDelegate.find(company, "Earned Points", earnedPointsGroup);
		Member member = null;
		String memberId = request.getParameter("memberid");
		if(memberId != null){
			member = memberDelegate.find(Long.parseLong(memberId));
		}
		List<CategoryItem> memberEarnedPoints = new ArrayList<CategoryItem>();
		if(member != null){
			for(int i = 0 ; i < earnedPointsCat.getItems().size() ; i++){
				CategoryItem earnedPoints = earnedPointsCat.getItems().get(i);
				for(int j = 0 ; j < earnedPoints.getCategoryItemOtherFields().size() ; j++){}
			}
		}
		
		//request.setAttribute("memberEarnedPoints", memberEarnedPoints);
		return memberEarnedPoints;
	}
	
	public String updateOrderStatus()
	{
		try
		{
			successUrl = (request.getParameter("successUrl") != null
					? request.getParameter("successUrl")
					: "");
				
			final String orderID = request.getParameter("orderID");
			final CartOrder cartOrder = cartOrderDelegate.find(Long.valueOf(orderID));
			final String strOrderStatus = request.getParameter("orderStatus");
			final String strPaymentStatus = request.getParameter("paymentStatus");
			String strShippingStatus = "";
			CartOrder oldCartOrder = cartOrder;
			try{
				if(request.getParameter("shippingStatus") == null || request.getParameter("shippingStatus") == ""){
					strShippingStatus = "delivered";
				}
				else{
					strShippingStatus = request.getParameter("shippingStatus");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			OrderStatus orderStatus = OrderStatus.NEW;
			PaymentStatus paymentStatus = PaymentStatus.PENDING;
			ShippingStatus shippingStatus = ShippingStatus.PENDING;
			
			final String orderItemStatus = request.getParameter("orderItemStatus");
			final List<CartOrderItem> cartItem = cartOrderItemDelegate.findAll(cartOrder).getList();
			String tempStatus = "";
			Map<String,String> strItemStatusMap = new HashMap<String,String>();
			
			for(String s1 : orderItemStatus.split(";"))
			{
				String strKey = "";
				String strVal = "";
				int counter1 = 0;
				for(String s2 : s1.split(":"))
				{
					if(counter1 == 0)
					{
						strKey = s2;
					}
					else
					{
						strVal = s2;
					}
					counter1++;
				}
				strItemStatusMap.put(strKey.trim(), strVal.trim());
			}
			for(CartOrderItem orderItem : cartItem)
			{
				orderItem.setStatus(strItemStatusMap.get(String.valueOf(orderItem.getId())));
				cartOrderItemDelegate.update(orderItem);
				
			}
			
			if(strOrderStatus.equalsIgnoreCase("new")){
				orderStatus = OrderStatus.NEW;
			}
			else if(strOrderStatus.equalsIgnoreCase("Pending")){
				orderStatus = OrderStatus.PENDING;
			}
			else if(strOrderStatus.equalsIgnoreCase("Completed")){
				orderStatus = OrderStatus.COMPLETED;
			}
			else if(strOrderStatus.equalsIgnoreCase("Delivery In Transit")){
				orderStatus = OrderStatus.DELIVERY_IN_TRANSIT;
			}
			else{
				orderStatus = OrderStatus.CANCELLED;
			}
			
			if(strPaymentStatus.equalsIgnoreCase("pending")){
				paymentStatus = PaymentStatus.PENDING;
			}
			else{
				paymentStatus = PaymentStatus.PAID;
			}
			
			if(strShippingStatus.equalsIgnoreCase("pending")){
				shippingStatus = ShippingStatus.PENDING;
			}
			else if(strShippingStatus.equalsIgnoreCase("in transit")){
				shippingStatus = ShippingStatus.IN_TRANSIT;
			}
			else{
				shippingStatus = ShippingStatus.DELIVERED;
			}
			try{
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
				//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				/*
				if(cartOrder.getStatus() == OrderStatus.NEW && orderStatus == OrderStatus.DELIVERY_IN_TRANSIT){
					cartOrder.setInfo2(String.valueOf(formatter.print((new DateTime()))));
				}
				else if(cartOrder.getStatus() == OrderStatus.DELIVERY_IN_TRANSIT && orderStatus == OrderStatus.COMPLETED){
					cartOrder.setInfo3(String.valueOf(formatter.print((new DateTime()))));
				}
				*/
				if(cartOrder.getStatus() == OrderStatus.PENDING && orderStatus == OrderStatus.DELIVERY_IN_TRANSIT){
					cartOrder.setInfo2(String.valueOf(formatter.print((new DateTime()))));
				}
				else if(cartOrder.getStatus() == OrderStatus.DELIVERY_IN_TRANSIT && orderStatus == OrderStatus.COMPLETED){
					cartOrder.setInfo3(String.valueOf(formatter.print((new DateTime()))));
				}
				else if(cartOrder.getStatus() == OrderStatus.NEW && orderStatus == OrderStatus.COMPLETED){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Long totalNewToDeliveredMilliSecondAverage = 0L;
					Duration durationFromNewToDelivered = new Duration(new DateTime(simpleDateFormat.parse(String.valueOf(cartOrder.getCreatedOn()))),new DateTime());
					totalNewToDeliveredMilliSecondAverage = durationFromNewToDelivered.getMillis()/2L;
					
					cartOrder.setInfo2(String.valueOf(simpleDateFormat.format((((DateUtils.addMilliseconds(cartOrder.getCreatedOn(),Integer.parseInt(String.valueOf(totalNewToDeliveredMilliSecondAverage)))))))));
					
					//cartOrder.setInfo2(String.valueOf(formatter.print((new DateTime()))));
					cartOrder.setInfo3(String.valueOf(formatter.print((new DateTime()))));
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			///////////////////////////////////////////////////
			// Update Order Status Logs
			if(!(orderStatus.equals(cartOrder.getStatus()))){
				CartOrderStatusHistory cartOrderStatusHistory = new CartOrderStatusHistory();
				
				cartOrderStatusHistory.setRemarks("Order status was change from " + cartOrder.getStatus().getName() + " into " + orderStatus.getName());
				
				cartOrderStatusHistory.setCompany(company);
				cartOrderStatusHistory.setCartOrder(cartOrder);
				cartOrderStatusHistory.setUser(user);
				
				cartOrderStatusHistoryDelegate.insert(cartOrderStatusHistory);
			}
			
			/////////////////////////////////////////////////////////
			cartOrder.setStatus(orderStatus);
			
			cartOrder.setPaymentStatus(paymentStatus);
			cartOrder.setShippingStatus(shippingStatus);
			
			if(request.getParameter("info1") != null){
				cartOrder.setInfo1(request.getParameter("info1"));
			}
			//if(request.getParameter("info2") != null){
			//	cartOrder.setInfo2(request.getParameter("info2"));
			//}
			
			cartOrderDelegate.update(cartOrder);
			
			//----EARNED POINTS UPDATE----
			if(cartOrder.getPaymentType().equals(PaymentType.COD)){
				if((!oldCartOrder.getPaymentStatus().equals(PaymentStatus.PAID)) && cartOrder.getPaymentStatus().equals(PaymentStatus.PAID)){
					CheckoutAction checkoutAction = new CheckoutAction();
					checkoutAction.wendysAssignPoints(company, cartOrder, cartOrder.getTotalPrice() + cartOrder.getTotalShippingPrice2());
				}
			}
			//-----
			
			System.out.println("###Pagenumber: "+request.getParameter("pageNumber"));
			setPageNumber();
		}
		catch(Exception e)
		{
			System.err.println(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * Parses specified request specified page number to local variable {@code pageNumber}.
	 * Returns the parsed specified page number if successful, otherwise 1.
	 * 
	 * @return - the parsed specified page number if successful, otherwise 1.
	 */
	public int setPageNumber() {
		//get the specified page number
		String page = request.getParameter("pageNumber");
 		try{
 			//parse specified page
 			pageNumber = Integer.parseInt(page);
		} catch(Exception e){
			//log unparsed value and return to 1st page
			logger.info("Cannot parse {} as page number" , page);
			pageNumber = 1;
			return pageNumber;
		}
		return pageNumber;
	}
	
	/*--------  Get Wendys Branch ---------------*/
	 
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
	
	public Map<String, CategoryItem> getCategoryItemById(){
		Map<String, CategoryItem> listById = new HashMap<String, CategoryItem>();
		List<CategoryItem> listItem = categoryItemDelegate.findAllByGroup(company, groupDelegate.find(company, "stores")).getList();
		for(CategoryItem item : listItem){
			listById.put(String.valueOf(item.getId()), item);
		}
		
		return listById;
	}
	
	/**
	 * Loads the items based on the specified items per page.
	 * 
	 * @param itemSize 
	 * 			- total number of items
	 * @param itemsPerPage 
	 * 			- number of items shown per page
	 */
	public void setPaging(int itemSize, int itemsPerPage) {
		if (company.getName().equalsIgnoreCase("wendys")) {
			int pageDisplay = (itemSize/itemsPerPage) > 5 ? 5 : (itemSize/itemsPerPage);
			request.setAttribute("pagingUtil", new PagingUtil(itemSize, itemsPerPage, pageNumber, pageDisplay));
		}
		else {
			request.setAttribute("pagingUtil", new PagingUtil(itemSize, itemsPerPage, pageNumber, (itemSize/itemsPerPage)));
		}
		
	}
	
	/**
	 * Returns the successUrl property value.
	 *
	 * @return the successUrl
	 */
	public String getSuccessUrl() {
		return successUrl;
	}

	/**
	 * Returns the orderList property value.
	 *
	 * @return the orderList
	 */
	public List<CartOrder> getOrderList() {
		return orderList;
	}
	
	public List<CartOrder> getOrderListAll() {
		return orderListAll;
	}

	/**
	 * Sets the successUrl property value with the specified successUrl.
	 *
	 * @param successUrl the successUrl to set
	 */
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	/**
	 * Returns the request property value.
	 *
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Sets the request property value with the specified request.
	 *
	 * @param request the request to set
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Sets the user property value with the specified user.
	 *
	 * @param user the user to set
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Sets the company property value with the specified company.
	 *
	 * @param company the company to set
	 */
	@Override
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@SuppressWarnings("unchecked")
	public String searchUsernameWendys() throws Exception, IOException {
		System.out.println("#########################     1     ############################");
		try{
			String username = request.getParameter("user_name");
			System.out.println("#########################    2      ############################");
			List<Member> memberList = memberDelegate.findAllByLikeUsername(company, username).getList();
			System.out.println("#########################     3     ############################");
			//JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			
			System.out.println("#########################      4    ############################");
			JSONArray objList = new JSONArray();
			System.out.println("#########################     5     ############################");
			
			for(Member memberItem : memberList) {
				//System.out.println("#########################     "+userItem.getUsername()+"     ############################");
				JSONObject obj = new JSONObject();
				obj.put("username", memberItem.getUsername());
				obj.put("id", memberItem.getId());
				obj.put("firstname", memberItem.getFirstname());
				obj.put("lastname", memberItem.getLastname());
				
				objList.add(obj);
			}
			obj2.put("listWendysUsername", objList);
			
			System.out.println("#########################     7: " + obj2.toJSONString() +"     ############################");
			setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
			System.out.println("#########################     8: " +inputStream+"     ############################");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveneworder() {
		try {
		
		CartOrder cartOrder = new CartOrder();
		ItemDetail itemDetail = new ItemDetail();
		CartOrderItem cartOrderItem = new CartOrderItem();
		
		if(request.getParameter("member_id") != null) {
			Member member = memberDelegate.find(Long.parseLong(request.getParameter("member_id")));
			if(member != null){
				try{
					String str_Comments_ = "";
					String paymenttype = "";
					String shippingtype = "";
					Double price = 0.0;
					int previousOrderCount = 0;
					int currentOrderCount = 0;
					int countOfWendysOrderReward = 0;
					
					try{
						//previousOrderCount = cartOrderDelegate.findAll(company, member).size();
						previousOrderCount = Integer.parseInt(String.valueOf(cartOrderDelegate.getMemberOrderCount(company, member)));
					}
					catch(Exception e){
						previousOrderCount = 0;
					}
					
					try{
						countOfWendysOrderReward = CompanyConstants.WENDYS_ORDER_REWARDS.length;// categoryDelegate.find(8967L).getEnabledItems().size();
					}
					catch(Exception e){
						countOfWendysOrderReward = 0;
					}
					
					if(request.getParameter("specialInstruction") != null && request.getParameter("specialInstruction") != ""){
						str_Comments_ = str_Comments_ + "Special Instruction:  "+ request.getParameter("specialInstruction");
						
					}
					else{
						str_Comments_ = str_Comments_ + "Special Instruction:  ";
					}
					System.out.println("###" +str_Comments_);
					
					if(request.getParameter("paymenttype")!=null && request.getParameter("paymenttype")!=""){
						if(request.getParameter("paymenttype").equals("COD")){
							cartOrder.setPaymentType(PaymentType.COD);
							//str_Comments_ = str_Comments_ + "Change For:  "+ request.getParameter("changeFor");
							
							if(request.getParameter("changeFor") != null && request.getParameter("changeFor") != ""){
								str_Comments_ = str_Comments_ + "Change For:  "+ request.getParameter("changeFor");
								
							}
							else{
								str_Comments_ = str_Comments_ + "Change For:  0.0";
							}
							
							System.out.println("###" +str_Comments_);
						}
						else{
							cartOrder.setPaymentType(PaymentType.I_PAY88);
						}
					}
					else{
						cartOrder.setPaymentType(PaymentType.I_PAY88);
					}
					
					
					if(request.getParameter("shippingtype")!=null && request.getParameter("shippingtype")!=""){
						if(request.getParameter("shippingtype").equals("PICKUP")){
							cartOrder.setShippingType(ShippingType.PICKUP);
							//str_Comments_ = str_Comments_ + "Prefferred Store:  "+ request.getParameter("prefferredStore");
							//str_Comments_ = str_Comments_ + "Preffered Date:  "+ request.getParameter("prefferedDate");
							//str_Comments_ = str_Comments_ + "Preffered Time:  "+ request.getParameter("prefferedTime");
							
							if(request.getParameter("prefferredStore") != null && request.getParameter("prefferredStore") != ""){
								str_Comments_ = str_Comments_ + "Prefferred Store:  "+ request.getParameter("prefferredStore");
								
							}
							else{
								str_Comments_ = str_Comments_ + "Prefferred Store:  0.0";
							}
							
							if(request.getParameter("prefferedDate") != null && request.getParameter("prefferedDate") != ""){
								str_Comments_ = str_Comments_ + "Preffered Date:  "+ request.getParameter("prefferedDate");
								
							}
							else{
								str_Comments_ = str_Comments_ + "Preffered Date:  .";
							}
							
							if(request.getParameter("prefferedTime") != null && request.getParameter("prefferedTime") != ""){
								str_Comments_ = str_Comments_ + "Preffered Time:  "+ request.getParameter("prefferedTime");
								
							}
							else{
								str_Comments_ = str_Comments_ + "Preffered Time:  .";
							}
							
							
						}
						else{
							cartOrder.setShippingType(ShippingType.DELIVERY);
							
							
						}
					}
					else{
						cartOrder.setShippingType(ShippingType.PICKUP);
						
					}
					
					if(request.getParameter("orderprice") != null){
						try{
							
							price = Double.parseDouble(request.getParameter("orderprice"));
						}
						catch(Exception e){
							e.printStackTrace();
							price = 0.0;
						}
					}
					else{
						price = 0.0;
					}
					cartOrder.setMember(member);
					cartOrder.setComments(str_Comments_);
					cartOrder.setAddress1(member.getAddress1());
					cartOrder.setCity(member.getCity());
					cartOrder.setCountry(member.getInfo2());
					cartOrder.setName(member.getFirstname() + " " + member.getLastname());
					cartOrder.setPhoneNumber(member.getMobile());
					cartOrder.setState(member.getProvince());
					cartOrder.setPaymentStatus(PaymentStatus.PAID);
					cartOrder.setShippingStatus(ShippingStatus.DELIVERED);
					cartOrder.setStatus(OrderStatus.COMPLETED);
					cartOrder.setCompany(company);
					//cartOrder.setPaymentType(PaymentType.CASH);
					//cartOrder.setShippingType(ShippingType.DELIVERY);
					
					Long order_ID = cartOrderDelegate.insert(cartOrder);
					cartOrder = cartOrderDelegate.find(order_ID);
					cartOrderItem.setOrder(cartOrder);
					cartOrderItem.setQuantity(1);
					cartOrderItem.setCompany(company);
					cartOrderItem.setStatus("OK");
					
					
					itemDetail.setPrice(price);
					
					itemDetail.setName("Transaction No. : " + request.getParameter("transactionnumber"));
					cartOrderItem.setItemDetail(itemDetail);
					
					Long ci_Id = cartOrderItemDelegate.insert(cartOrderItem);
					currentOrderCount = Integer.parseInt(String.valueOf(cartOrderDelegate.getMemberOrderCount(company, member)));
					//currentOrderCount = cartOrderDelegate.findAll(company, member).size();
					//if new order is successfull, then find a certain rewards
					System.out.println("###### Current Order Count :" + currentOrderCount);
					System.out.println("###### Previous Order Count :" + previousOrderCount);
					System.out.println("###### Count Order Count :" + countOfWendysOrderReward);
					if(currentOrderCount > previousOrderCount){
						
						if(countOfWendysOrderReward >= (currentOrderCount % countOfWendysOrderReward)){
							Category category = new Category();
							category = categoryDelegate.find(CompanyConstants.WENDYS_ORDER_REWARDS[((currentOrderCount-1) % (countOfWendysOrderReward-0))]);
							Double priceCat_ = 0D;
							try{
								priceCat_ = Double.parseDouble(category.getPrice());
							}
							catch(Exception e){
								priceCat_ = 0D;
							}
								if(priceCat_!=0.0 && priceCat_ <= cartOrderItem.getItemDetail().getPrice()){
									
									
									MemberFile memberFile = new MemberFile();
									cartOrderItem = cartOrderItemDelegate.find(ci_Id);
									
									memberFile.setCategory(category);
									memberFile.setApprovedDate(new Date());
									memberFile.setRemarks(category.getName());
									memberFile.setMember(member);
									memberFile.setCartOrder(cartOrder);
									memberFile.setCompany(company);
									//memberFile.setValue(category.getShortDescriptionWithoutTags());
									//memberFile.setValue(String.valueOf(cartOrderItem.getItemDetail().getPrice()));
									memberFile.setStatus("Not yet Redeemed");
									memberFileDelegate.insert(memberFile);
								
								}
										
							
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
					notificationMessage = "Order was not saved!";
					return ERROR;
				}
			}
			else{
				notificationMessage = "Order was not saved!";
				return ERROR;
			}
		}
		else{
			notificationMessage = "Order was not saved!";
			return ERROR;
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
			notificationMessage = "Order was not saved!";
			return ERROR;
		}
		notificationMessage = "New order was saved!";
		return SUCCESS;
	}
	
	public String redeemReward() {
		
		
		
		
		if(request.getParameter("member_file_id") != null) {
			MemberFile memberFile = memberFileDelegate.find(Long.parseLong(request.getParameter("member_file_id")));
			
				try{
					if(memberFile != null){
					memberFile.setStatus("Already Redeemed");
					memberFileDelegate.update(memberFile);
					notificationMessage = "Reward successfully redeemed!";
					}
					else
					{
						notificationMessage = "Reward was not redeemed!";
						return ERROR;
					}
				}
				catch(Exception e){
					e.printStackTrace();
					notificationMessage = "Reward was not redeemed!";
					return ERROR;
				}
				
				
			}
				
		return SUCCESS;
	}
	
	
	/**
	 * @return the inputStream
	 */
	
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	
	@SuppressWarnings("unchecked")
	public String submitshippingaddress(){
		
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		Boolean isNewOrder = Boolean.FALSE;
		OrderStatus orderStatus = OrderStatus.NEW;
		CartOrder cartOrder = new CartOrder();
		Long cartOrderId = 0L;
		List<CartOrder> listCartOrder = new ArrayList<CartOrder>();
		
		String member_info6 = request.getParameter("member_info6");
		String member_address = request.getParameter("member_address");
		String member_area = request.getParameter("member_area");
		String member_zipcode = request.getParameter("member_zipcode");
		String member_location = request.getParameter("member_location");
		String member_mobilenumber = request.getParameter("member_mobilenumber");
		String member_telephonenumber = request.getParameter("member_telephonenumber");
		String member_faxnumber = request.getParameter("member_faxnumber");
		String member_emailaddress = request.getParameter("member_emailaddress");
		String member_receiver = request.getParameter("member_receiver");
		String info4Content = request.getParameter("info4");
		
		if(member == null){
			Long memberid = 0L;
			try{
				memberid = Long.parseLong(request.getParameter("member_id"));
			}catch(Exception e){}
			member = memberDelegate.find(memberid);
		}
		
		if(member != null){
			listCartOrder = cartOrderDelegate.findAllByMemberAndOrderStatus(company, member, orderStatus, -1, -1, new Order[]{Order.desc("createdOn")}).getList();
			
			//this line of code is use to fix bug on Reward and Order mixed-up
			for(CartOrder c_o_ : listCartOrder){
				/*
				if(!(c_o_.getStatusNotes().toLowerCase().equalsIgnoreCase("Reward"))){
					cartOrder = c_o_;
					break;
				}
				*/
				if((c_o_.getStatusNotes()!="Reward")){
					cartOrder = c_o_;
					break;
				}
			}
			
			if(listCartOrder != null && cartOrder != null){
				if(listCartOrder.size() > 0){
					
					isNewOrder = false;
					//cartOrder = listCartOrder.get(0);
					//set details
					cartOrder.setInfo6(member_info6);
					cartOrder.setAddress1(member_address);
					cartOrder.setCity(member_location);
					cartOrder.setInfo1(member_area);
					cartOrder.setInfo2(member_mobilenumber);
					cartOrder.setPhoneNumber(member_telephonenumber);
					cartOrder.setInfo3(member_faxnumber);
					cartOrder.setEmailAddress(member_emailaddress);
					cartOrder.setZipCode(member_zipcode);
					cartOrder.setName(member_receiver);
					cartOrder.setInfo4(info4Content);
					//set default details in payment status, payment type, shipping status and shipping type
					cartOrder.setPaymentStatus(PaymentStatus.PENDING);
					cartOrder.setPaymentType(PaymentType.COD);
					cartOrder.setShippingStatus(ShippingStatus.PENDING);
					cartOrder.setShippingType(ShippingType.DELIVERY);
					cartOrderDelegate.update(cartOrder);
					obj.put("success", true);
					obj.put("isAddedToCart", true);
					objList.add(obj);
					obj2.put("listAddToCartDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				}
				else{
					
					isNewOrder = true;
					cartOrder = new CartOrder();
					cartOrder.setCompany(company);
					cartOrder.setMember(member);
					cartOrder.setStatus(OrderStatus.NEW);
					//set details
					cartOrder.setInfo6(member_info6);
					cartOrder.setAddress1(member_address);
					cartOrder.setCity(member_location);
					cartOrder.setInfo1(member_area);
					cartOrder.setInfo2(member_mobilenumber);
					cartOrder.setPhoneNumber(member_telephonenumber);
					cartOrder.setInfo3(member_faxnumber);
					cartOrder.setEmailAddress(member_emailaddress);
					cartOrder.setZipCode(member_zipcode);
					cartOrder.setName(member_receiver);
					cartOrder.setInfo4(info4Content);
					//set default details in payment status, payment type, shipping status and shipping type
					cartOrder.setPaymentStatus(PaymentStatus.PENDING);
					cartOrder.setPaymentType(PaymentType.COD);
					cartOrder.setShippingStatus(ShippingStatus.PENDING);
					cartOrder.setShippingType(ShippingType.DELIVERY);
					cartOrderId = cartOrderDelegate.insert(cartOrder);
					cartOrder = cartOrderDelegate.find(cartOrderId);
					
					obj.put("success", true);
					obj.put("isAddedToCart", true);
					objList.add(obj);
					obj2.put("listAddToCartDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				}
			}
		}
		else{
			obj.put("errorMessage", "You are not a valid user");
			obj.put("isAddedToCart", false);
			objList.add(obj);
			obj2.put("listAddToCartDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String submitrewardshippinginfo() {
		logger.info(" FLAG HERE 1");
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		Boolean isNewOrder = Boolean.FALSE;
		OrderStatus orderStatus = OrderStatus.NEW;
		CartOrder cartOrder = new CartOrder();
		Long cartOrderId = 0L;
		List<CartOrder> listCartOrder = new ArrayList<CartOrder>();
		CategoryItem item = new CategoryItem();
		Long categoryItemId = 0L;
		Long cartOrderItemId = 0L;
		ShippingType shippingType = ShippingType.DELIVERY;
		CartOrderItem cartOrderItem = new CartOrderItem();
		logger.info(" FLAG HERE 2");
		
		String shipping_selected_reward = request.getParameter("shipping_selected_reward");
		String shipping_selected_shipping_type = request.getParameter("shipping_selected_shipping_type");
		
		String shipping_authorizedreceiver = request.getParameter("shipping_authorizedreceiver");
		
		String shipping_companyname = request.getParameter("shipping_companyname");
		
		String shipping_useprofileaddress = request.getParameter("shipping_useprofileaddress");
		String shipping_address = request.getParameter("shipping_address");
		String shipping_province2 = request.getParameter("province2");
		String shipping_city2 = request.getParameter("city2");
		String shipping_zipcode = request.getParameter("shipping_zipcode");
		String shipping_mobile = request.getParameter("shipping_mobile");
		String shipping_telephonenumber = request.getParameter("shipping_telephonenumber");
		String shipping_faxnumber = request.getParameter("shipping_faxnumber");
		String shipping_emailaddress = request.getParameter("shipping_emailaddress");
		String shipping_cycle_coverage = request.getParameter("shipping_cycle_coverage");
		
		logger.info(" FLAG HERE 3");
		try{
			
			categoryItemId = Long.parseLong(shipping_selected_reward);
			item = categoryItemDelegate.find(categoryItemId);
			logger.info(" FLAG HERE 4");
		}catch(Exception e){
			
		}
		if(shipping_selected_shipping_type.equalsIgnoreCase("delivery")){
			shippingType = ShippingType.DELIVERY;
			logger.info(" FLAG HERE 5");
		}
		else{
			shippingType = ShippingType.PICKUP;
			logger.info(" FLAG HERE 6");
		}
		
		if(member == null){
			Long memberid = 0L;
			try{
				memberid = Long.parseLong(request.getParameter("member_id"));
			}catch(Exception e){}
			member = memberDelegate.find(memberid);
			logger.info(" FLAG HERE 7");
		}
		
		if(member != null){
			logger.info(" FLAG HERE 8");
			cartOrder = new CartOrder();
			cartOrder.setStatus(OrderStatus.PENDING);
			cartOrder.setShippingStatus(ShippingStatus.PENDING);
			cartOrder.setPaymentType(PaymentType.COD);
			cartOrder.setPaymentStatus(PaymentStatus.PAID);
			cartOrder.setTotalShippingPrice2(0.0);
			cartOrder.setCompany(company);
			cartOrder.setMember(member);
			
			if(shippingType.equals(ShippingType.DELIVERY)){
				logger.info(" FLAG HERE 9");
				cartOrder.setShippingType(shippingType);
				cartOrder.setName(shipping_authorizedreceiver);
				if(shipping_useprofileaddress==null){
					shipping_useprofileaddress = "false";
				}
				if(shipping_useprofileaddress.equalsIgnoreCase("true")) {
					//use member's address
					
					cartOrder.setAddress1(member.getAddress1());
					cartOrder.setInfo1(member.getProvince());
					cartOrder.setCity(member.getCity());
					cartOrder.setZipCode(member.getZipcode());
					cartOrder.setInfo2(member.getMobile());
					cartOrder.setPhoneNumber(member.getLandline());
					cartOrder.setInfo3(member.getFax());
					cartOrder.setEmailAddress(member.getEmail());
					logger.info(" FLAG HERE 10");
				}
				else {
					
					cartOrder.setAddress1(shipping_address);
					cartOrder.setInfo1(shipping_province2);
					cartOrder.setCity(shipping_city2);
					cartOrder.setZipCode(shipping_zipcode);
					cartOrder.setInfo2(shipping_mobile);
					cartOrder.setPhoneNumber(shipping_telephonenumber);
					cartOrder.setInfo3(shipping_faxnumber);
					cartOrder.setEmailAddress(shipping_emailaddress);
					logger.info(" FLAG HERE 11");
				}
				
			}
			else{
				
				cartOrder.setShippingType(shippingType);
				cartOrder.setName(member.getFirstname() + " " + member.getLastname());
				//use member's address
				cartOrder.setAddress1(member.getAddress1());
				cartOrder.setInfo1(member.getProvince());
				cartOrder.setCity(member.getCity());
				cartOrder.setZipCode(member.getZipcode());
				cartOrder.setInfo2(member.getMobile());
				cartOrder.setPhoneNumber(member.getLandline());
				cartOrder.setInfo3(member.getFax());
				cartOrder.setEmailAddress(member.getEmail());
				logger.info(" FLAG HERE 12");
			}
			//save Cart Order
			cartOrderId = cartOrderDelegate.insert(cartOrder);
			cartOrder = cartOrderDelegate.find(cartOrderId);
			if(cartOrder != null){
				ItemDetail itemDetail = new ItemDetail();
				cartOrderItem = new CartOrderItem();
				cartOrderItem.setOrder(cartOrder);
				cartOrderItem.setCategoryItem(item);
				cartOrderItem.setStatus("OK");
				cartOrderItem.setQuantity(1);
				cartOrderItem.setCompany(company);
				cartOrderItem.setDownloads(0);

				
				itemDetail.setName(item.getName() + " (Reward)");
				itemDetail.setPrice(item.getPrice());
				itemDetail.setDiscount(0.0);
				itemDetail.setSku("Unclaimed");
				itemDetail.setDescription(shipping_cycle_coverage);
				
				
				cartOrderItem.setItemDetail(itemDetail);
				
				cartOrderItemId = cartOrderItemDelegate.insert(cartOrderItem);
				
				obj.put("success", true);
				obj.put("isAddedToCart", true);
				objList.add(obj);
				obj2.put("listAddToCartDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				try {
					String s = sendEmailForGurkkaReward(member, cartOrder);
					
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//memberDelegate.update(member);
				logger.info(" FLAG HERE 13");
			}
		}
		else{
			obj.put("errorMessage", "You are not a valid user");
			obj.put("isAddedToCart", false);
			objList.add(obj);
			obj2.put("listAddToCartDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
			logger.info(" FLAG HERE 14");
		}
		logger.info(" FLAG HERE 15");
		return SUCCESS;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public String checklatestorderid(){
		try{
			logger.info("ORDER ID : " + request.getParameter("order_id_1"));
		}catch(Exception e){}
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		
		
		
		
		
		
		
		
		
		
		
		setPageNumber();
		//get number of retrieved messages for current company for paging purposes
		BigInteger itemCount = new BigInteger("0");
		if(company.getName().equalsIgnoreCase("wendys")){
			ITEMS_PER_PAGE = 10;
			itemCount = cartOrderDelegate.getPaidOrderCount(company);
		}
		else{
			itemCount = cartOrderDelegate.getOrderCount(company);
		}
				
		if(itemCount == null) 
			itemCount = new BigInteger("0");
		
			members=memberDelegate.findAll(company).getList();
			
			
			if(company.getName().equalsIgnoreCase("wendys")){
				String orderStatus = "";
				String paymentStatus = "";
				String shippingType = "";
				String branch = "";
				String branch_location = "comments";
				Boolean isForInfo1 = false;
				String branch_comments = "";
				String branch_info1 = "";
				
				if(request.getParameter("orderstatus")!=null){
					orderStatus = request.getParameter("orderstatus");
				}
				if(request.getParameter("paymentstatus")!=null){
					paymentStatus = request.getParameter("paymentstatus");
				}
				else{
					//paymentStatus = "paid";
					paymentStatus = "";
				}
				if(request.getParameter("ordertype")!=null){
					shippingType = request.getParameter("ordertype");
				}
				
				
				
				if(request.getParameter("branch")!=null && request.getParameter("branch")!=""){
					branch = request.getParameter("branch");
					branch_info1 = String.valueOf(categoryItemDelegate.findByName(company, branch).getId());
					branch_comments = "Prefferred Store: " + branch;
					
				}
				
				//for(int i=0; i<100000;i++){
				//	System.out.println("#############"+branch_info1+"################"+branch_comments);
				//}
				
				if(request.getParameter("memberid")!=null && request.getParameter("memberid")!=""){
					Long m_id = Long.parseLong(request.getParameter("memberid"));
					member = memberDelegate.find(m_id);
					orderList = cartOrderDelegate.listAllPaidOrders(member, company, ITEMS_PER_PAGE, pageNumber-1,orderStatus, paymentStatus, shippingType, branch_comments,branch_info1);
					orderListAll = cartOrderDelegate.listAllPaidOrders(member, company, -1, -1, orderStatus, paymentStatus, shippingType, branch_comments, branch_info1);
				}
				else{
					orderList = cartOrderDelegate.listAllPaidOrders(null, company, ITEMS_PER_PAGE, pageNumber-1,orderStatus, paymentStatus, shippingType, branch_comments,branch_info1);
					orderListAll = cartOrderDelegate.listAllPaidOrders(null, company, -1, -1, orderStatus, paymentStatus, shippingType, branch_comments, branch_info1);
				}
				
				itemCount = BigInteger.valueOf(orderListAll.size());
			}
			else{
				orderList = cartOrderDelegate.listAllOrders(company, ITEMS_PER_PAGE, pageNumber-1);
				orderListAll = cartOrderDelegate.listAllOrders(company, -1, -1);
			}
			
			//itemCount = BigInteger.valueOf(orderList.size());
			//cartOrderDelegate.findAll(company, member)
			if((company.getName().equalsIgnoreCase("PURPLETAG") || company.getName().equalsIgnoreCase("PURPLETAG2"))){
				int i=0;
				for(CartOrder cartOrder:orderList){
					if(cartOrder.getStatus().toString().equalsIgnoreCase(ERROR)){
						orderList.get(i).setIsValid(false);
					}
					i++;
				}
			}
			setPaging(itemCount.intValue(), ITEMS_PER_PAGE);
		
		
		
		
		
		
		
		
		
		if(orderList.size() > 0){
			obj.put("success", true);
			obj.put("latestOrderId", String.valueOf(orderList.get(0).getId()));
			objList.add(obj);
			obj2.put("listCheckLatestOrderIdDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
		}
		else{
			obj.put("success", true);
			obj.put("latestOrderId", String.valueOf(request.getParameter("order_id_1")));
			objList.add(obj);
			obj2.put("listCheckLatestOrderIdDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
		}
		
		
		
		return SUCCESS;
	}
	
	private String generateVoucherForGurkkaReward(Member member, CartOrder cartOrder)
			throws JRException, IOException {return null;}
	
	private String sendEmailForGurkkaReward(Member member, CartOrder cartOrder) throws JRException, IOException{

		logger.info("Sending email for Gurkka Reward... Member Id : " + member.getId());
		final String MONEY_FORMAT = "##,###,##0.00";
		final String attachment = generateVoucherForGurkkaReward(member, cartOrder);

		EmailUtil.connectViaCompanySettings(company);
		final String[] cc = { member.getEmail(), "maryann@ivant.com",
				"edgar@ivant.com", "teo@ivant.com", "info@gurkka.com" };
		final StringBuilder content = new StringBuilder();

		final String receiverName = StringUtils
				.trimToNull(cartOrder != null ? cartOrder.getName() : "");
		final String receiverAddress = StringUtils
				.trimToNull(cartOrder != null ? cartOrder.getAddress1() : "");
		final String receiverContact = StringUtils
				.trimToNull(cartOrder != null ? cartOrder.getPhoneNumber() : "");
		final String receiverArea = StringUtils
				.trimToNull(cartOrder != null ? cartOrder.getInfo1() : "");
		final String receiverLocation = StringUtils
				.trimToNull(cartOrder != null ? cartOrder.getCity() : "");

		final String name = !StringUtils.isEmpty(member.getReg_companyName()) ? member
				.getReg_companyName() : member.getFirstname() + " "
				+ member.getLastname();

		
		logger.info("flag 1 for sending Gurkka Confirmation email "+": Member Id : "+member.getId());
		
		content.append("<h4>Customer Details: </h4>");
		content.append("Member Name: " + name + "<br/>");
		content.append("Email: " + member.getEmail() + "<br/>");
		content.append("<br/>");
		content.append("<h4>Shipping Details:</h4>");
		

		if (receiverName != null) {
			content.append("Receiver's Name: " + receiverName + "<br/>");
		}
		if (receiverAddress != null) {
			content.append("Receiver's Address: " + receiverAddress + "<br/>");
		}
		if (receiverLocation != null) {
			content.append("Receiver's Location: " + receiverLocation + "<br/>");
		}
		if (receiverArea != null) {
			content.append("Receiver's Area: " + receiverArea + "<br/>");
		}

		if (receiverContact != null) {
			content.append("Receiver's Contact No.: " + receiverContact
					+ "<br/>");
		}

		logger.info("flag 2 for sending Gurkka Confirmation email "+": Member Id : "+member.getId());
		
		final String address = !StringUtils.isEmpty(member
				.getReg_companyAddress()) ? member.getReg_companyAddress()
				: member.getAddress1();
		content.append("Address: " + address + "<br/>");
		content.append("Area: " + member.getProvince() + "<br/>");
		content.append("Location: " + member.getCity() + "<br/>");
		content.append("Zip Code: " + member.getZipcode() + "<br/>");
		content.append("Mobile Number: " + member.getMobile() + "<br/>");
		content.append("Landline Number: " + member.getLandline() + "<br/>");
		content.append("Fax: " + member.getFax() + "<br/>");
		content.append("Email: " + member.getEmail() + "<br/><br/>");

		// PRODUCT ITEMS
		List<CartOrderItem> productItems = cartOrderItemDelegate.findAll(cartOrder).getList();
		if(productItems != null){
			if (productItems.size() > 0) {
				content.append("<h4>BREAKDOWN OF ITEMS</h4>");
				content.append("<table border='0'>");
				content.append("<thead>");
				content.append("<tr>");
				content.append("<th style='min-width: 200px;'>Reward Name</th>");
				/*
				content.append("<th style='min-width: 60px;'>Size</th>");
				content.append("<th style='min-width: 60px;'>Price</th>");
				content.append("<th style='min-width: 60px;'>Discount</th>");
				content.append("<th style='min-width: 60px;'>Qty</th>");
				content.append("<th style='min-width: 60px;'>Total Price</th>");
				*/
				content.append("</tr>");
				content.append("</thead>");
				content.append("<tbody>");
	
				Double subTotal = Double.valueOf(0);
	
				for (CartOrderItem item : productItems) {
					
	
					content.append("<tr>");
	
					content.append("<td>");
					content.append(item.getCategoryItem().getName());
					content.append("</td>");
					
					content.append("</tr>");
				}
	
				content.append("</tbody>");
				content.append("</table>");
			}
		}

		logger.info("flag 3 for sending Gurkka Confirmation email "+": Member Id : "+member.getId());
		
		content.append("<br><br><strong style='color:blue'>Please print the confirmation voucher attached herewith and present during delivery or pick-up of reward items.</strong>");
		content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email. For inquiries, feedback and comments, email us at customerservice@gurkka.com or call us at (02)708-2080.</strong>");
		company = companyDelegate.find(346L);
		/*EmailUtil.sendWithHTMLFormatWithCC(company.getCompanySettings().getEmailUserName(),
				cc, "Gurkka Confirmation Reward Voucher", content.toString(),
				attachment);

		*/
		
		Boolean isEmailSuccess = false;
		for(int icount = 0; icount < 5; icount++){
			try{
				isEmailSuccess = EmailUtil.sendWithHTMLFormatWithCC(company.getCompanySettings().getEmailUserName(), cc, "Gurkka Confirmation Reward Voucher", content.toString(), attachment);
				if(isEmailSuccess){
					break;
				}
			}catch(Exception e){
				isEmailSuccess = false;
			}
		}
		//generateOrderProperties(parameters);

		logger.info("flag 4 for sending Gurkka Confirmation email "+": Member Id : "+member.getId());
		
		return attachment;
	
	}

	@Override
	public void setServletContext(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}
	
	public List<SinglePage> getSinglePages() {
		return singlePages;
	}

	public void setSinglePages(List<SinglePage> singlePages) {
		this.singlePages = singlePages;
	}
	
}
