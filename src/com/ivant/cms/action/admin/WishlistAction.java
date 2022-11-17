package com.ivant.cms.action.admin;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.WishlistDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.entity.Wishlist;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.PagingUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Backend action for creating, viewing and editing wishlist items created by companies.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class WishlistAction extends ActionSupport 
implements Preparable, ServletRequestAware, CompanyAware, UserAware { 
	
	private static Logger logger = LoggerFactory.getLogger(WishlistAction.class);
	private static final long serialVersionUID = 3617798953897716137L;

	private final static int ITEMS_PER_PAGE = 20;
	
	/** Object responsible for wishlist item CRUD tasks */
	private WishlistDelegate wishlistDelegate = WishlistDelegate.getInstance();
	
	
	/** Current webtogo admin user, must not be null*/
	@SuppressWarnings("unused")
	private User user;
	
	/** Data that is passed through the session */
	private HttpServletRequest request;
	
	/** Current session webtogo company, must not be null*/
	private Company company;
	
	
	/** Current page number for paging, can be null*/
	private Integer pageNumber;
	
	/** List of wishlist items by the company, can be 0 or more*/
	private List<Wishlist> wishList;
	
	
	@Override
	public void prepare() throws Exception {
		//initialize paging
		setPageNumber();
		
		//get number of retrieved messages for current company for paging purposes
		BigInteger itemCount = wishlistDelegate.getWishlistCount(company);
		if(itemCount == null) 
			itemCount = new BigInteger("0");
		
		wishList = wishlistDelegate.listAllWishlist(company, ITEMS_PER_PAGE, pageNumber-1);
		setPaging(itemCount.intValue(), ITEMS_PER_PAGE);		
	}
	
	@Override
	public String execute() throws Exception {
		logger.info("Executing WishlistAction .......");
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

	public List<Wishlist> getWishList() {
		return wishList;
	}
}
