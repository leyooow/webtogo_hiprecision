package com.ivant.cms.action.admin;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.CategoryItemUtil;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Backend action for creating, viewing and editing ordered items by companies.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class OrderDetailAction extends ActionSupport 
	implements Preparable, ServletRequestAware, CompanyAware, UserAware {

	private static Logger logger = LoggerFactory.getLogger(OrderAction.class);
	private static final long serialVersionUID = 8897070761094309147L;

	private final static int ITEMS_PER_PAGE = 20;
	
	/** Object responsible for orders CRUD tasks */
	private CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	
	/** Object responsible for ordered items CRUD tasks */
	private CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();
	
	
	/** Current webtogo admin user, must not be null*/
	@SuppressWarnings("unused")
	private User user;
	
	/** Data that is passed through the session */
	private HttpServletRequest request;
	
	/** Current session webtogo company, must not be null*/
	@SuppressWarnings("unused")
	private Company company;
	
	/** Url to redirect if action is executed successfully */
	@SuppressWarnings("unused")
	private String successUrl;
	
	/** Current page number for paging, can be null*/
	private Integer pageNumber;
	
	/** An order from a member of the company */
	private CartOrder cartOrder;
	
	/** List if items contained in the order, must be 1 or more */
	private List<CartOrderItem> itemList;
	
	private Map<Long, Double> discountMap;
	
	@Override
	public void prepare() throws Exception {
		//initialize paging
		setPageNumber();
		
		//get selected cart order
		Long cartOrderID = Long.parseLong(request.getParameter("order_id"));
		cartOrder = cartOrderDelegate.find(cartOrderID);
		/*
		if(cartOrder.getTotalPriceOk().toString().indexOf(",")!=-1){
			String rerpL=cartOrder.getTotalPriceOk().toString().replace(",", "");
			cartOrder.setTotalPriceOkFormatted(rerpL);
		}*/
		
		
		//get number of retrieved messages for current company for paging purposes
		BigInteger itemCount = cartOrderItemDelegate.getItemCount(cartOrder);
		if(itemCount == null) 
			itemCount = new BigInteger("0");
		
		cartOrder.setTotalPrice(Double.parseDouble(cartOrder.getTotalPrice().toString().replace(",", "")));
		cartOrder.setTotalPriceOkFormatted(cartOrder.getTotalPriceOkFormatted().toString().replace(",", ""));
		//cartOrder.setT
		//cartOrder
		
		itemList = cartOrderItemDelegate.listAllOrders(cartOrder, ITEMS_PER_PAGE, pageNumber-1);
		
		final Map<Long, Double> discountMap = new HashMap<Long, Double>();
		if(itemList != null && !itemList.isEmpty())
		{
			//System.out.println("if(items != null && !items.isEmpty())");
			for(CartOrderItem cia : itemList)
			{
				final CategoryItem item = CategoryItemUtil.getItemFromCartItem(cia);
				if(item != null)
				{
					final Long itemId = item.getId();
					final Double discount = 0D;
					discountMap.put(itemId, discount);
				}
			}
		}
		//System.out.println("### Before discounMap Print ###");
		if(discountMap.size() > 0){
			request.setAttribute("discountMap",discountMap);
			//System.out.println("### discountMap : " + discountMap);
		}
		
		setPaging(itemCount.intValue(), ITEMS_PER_PAGE);		
	}
	
	@Override
	public String execute() throws Exception {
		logger.info("Executing OrderAction .......");
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
		String page = request.getParameter("page");
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
	
	/**
	 * Loads the items based on the specified items per page.
	 * 
	 * @param itemSize 
	 * 			- total number of items
	 * @param itemsPerPage 
	 * 			- number of items shown per page
	 */
	public void setPaging(int itemSize, int itemsPerPage) {
		request.setAttribute("pagingUtil", new PagingUtil(itemSize, itemsPerPage, pageNumber, (itemSize/itemsPerPage)));
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

	public CartOrder getCartOrder() {
		return cartOrder;
	}

	public List<CartOrderItem> getItemList() {
		return itemList;
	}
	
	public Map<Long, Double> getDiscountMap() {
		return discountMap;
	}
}
