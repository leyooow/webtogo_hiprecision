package com.ivant.cart.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;

/**
 * Action for member shipping information create, update, and delete.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class ShippingInfoAction extends AbstractBaseAction {
	
	private Logger logger = LoggerFactory.getLogger(ShippingInfoAction.class);
	private static final long serialVersionUID = 2808055987687260742L;
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();	
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	
	/** Object responsible for member shipping info CRUD tasks */
	private MemberShippingInfoDelegate shippingInfoDelegate = MemberShippingInfoDelegate.getInstance();
	
	/** Object responsible for shopping cart CRUD tasks */
	private ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	
	/** Object responsible for shopping cart items CRUD tasks */
	private ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
	
	
	/** Particulars about where a member ordered item will be delivered */
	private MemberShippingInfo shippingInfo;
	
	/** Contains the information regarding the address where the orders will be dropped off*/
	private ShippingInfo info;
	
	/** CRUD action type, can be edit, save or load */
	private String actionType;
	
	/** Shopping cart of the member */
	private ShoppingCart shoppingCart;
	
	/** Items in the shopping cart, can be 0 or more */
	private List<ShoppingCartItem> cartItemList;
	
	private double totalPrice;
	CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private ShoppingCartItemDelegate cartItemDelegate = ShoppingCartItemDelegate.getInstance();
	private ShoppingCartDelegate cartDelegate = ShoppingCartDelegate.getInstance();
	
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();	
	private ArrayList<CategoryItem> catItem = new ArrayList<CategoryItem>();
	private Long[] cartItem;
	
	@Override
	public void prepare() throws Exception {
		
		//check if action member action is save or edit
		actionType = request.getParameter("action");
		if (isNull(actionType)) {
			actionType = LOAD;
		}
		List<Category> categories = categoryDelegate.findAllPublished(company, null, null, true, Order.asc("sortOrder")).getList();
		request.setAttribute("categoryMenu", categories);	
		
		Map<String, Object> featuredPages = new HashMap<String, Object>();

		List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();
		
		for(MultiPage mp : featuredMultiPage) {
			if(!mp.getHidden()) {
				featuredPages.put(mp.getName(), mp);
			}
		}
		
		request.setAttribute("featuredPages", featuredPages);
		initShippingInfo();
		
		logger.debug("current action type : " + actionType);
		
		//init shopping cart
		try {
			shoppingCart = shoppingCartDelegate.find(company, member);
			cartItemList = shoppingCartItemDelegate.findAll(shoppingCart).getList();
		} catch (Exception e) {
			logger.debug("No items found.");
		}
		
		loadAllRootCategories();
		getCartSize();
		
		//populate server URL to be redirected to
		initHttpServerUrl();
		
		prepareMenu();
		
	}
	
	@Override
	public String execute() throws Exception {
		//validate current user, must not be empty
		if(isNull(member)){
			this.setNotificationMessage("Log in to check out items");
			return INPUT;
		}

		//update values
		if(SAVE.equals(actionType)){			
			
			info = new ShippingInfo();
			
			//get all newly edited values			
			info.setName(request.getParameter("name"));
			info.setAddress1(request.getParameter("address1"));
			info.setAddress2(request.getParameter("address2"));
			info.setCity(request.getParameter("city"));
			info.setState(request.getParameter("state"));
			info.setCountry(request.getParameter("country"));
			info.setZipCode(request.getParameter("zipCode"));
			info.setPhoneNumber(request.getParameter("phoneNumber"));
			info.setEmailAddress(request.getParameter("emailAddress"));		
			
			//update member shipping info
			
			shippingInfo.setShippingInfo(info);
			logger.debug(shippingInfo.getShippingInfo().getName());
			shippingInfo.setUpdatedOn(new Date());
			
			logger.debug(String.valueOf(shippingInfoDelegate.update(shippingInfo)));
			addActionMessage("Shipping information successfully updated.");
			initShippingInfo();

			actionType = LOAD;
		}
		ignoreInfo();
		////////////////////
		List<Category> categories = categoryDelegate.findAllPublished(company, null, null, true, Order.asc("sortOrder")).getList();
		request.setAttribute("categoryMenu", categories);	
		//System.out.println("Is car null? "+cartItem);
		ObjectList<ShoppingCartItem> tempCartItems = cartItemDelegate
		.findAll(shoppingCart);
		cartItemList = tempCartItems.getList();
		for( ShoppingCartItem item : cartItemList)	
			catItem.add(categoryItemDelegate.find(item.getItemDetail().getRealID()));
//		shoppingCart.setItems(cartItemList);	
		///////////////////
		
		//System.out.println("actionType------------"+actionType);
		if(session.get("shippingName")!=null){
			request.setAttribute("shippingName", session.get("shippingName"));
			session.remove("shippingName");
		}
		
		if(session.get("phoneNumber")!=null){
			request.setAttribute("phoneNumber", session.get("phoneNumber"));
			session.remove("phoneNumber");
		}
		
		if(session.get("email")!=null){
			request.setAttribute("email", session.get("email"));
			session.remove("email");
		}
		
		if(session.get("comments")!=null)
			request.setAttribute("comments", session.get("comments"));
		request.setAttribute("editCheck", 1);
		return SUCCESS;
	}
	
	public void ignoreInfo()
	{
		if(company.getId() == 136 || company.getId() == 152|| company.getId() == 194)
		{
			info = new ShippingInfo();
		
			//set values			
			if(request.getParameter("shippingName") == null)
				info.setName(member.getFullName());
			else
				info.setName(request.getParameter("shippingName").toString());
			info.setAddress1("-");
			info.setAddress2("-");
			info.setCity("-");
			info.setState("-");
			info.setCountry("-");
			info.setZipCode("-");
			info.setPhoneNumber("-");
			info.setEmailAddress(member.getEmail());
		
			shippingInfo.setShippingInfo(info);
			logger.debug(shippingInfo.getShippingInfo().getName());
			shippingInfo.setUpdatedOn(new Date());
		
			logger.debug(String.valueOf(shippingInfoDelegate.update(shippingInfo)));
			//addActionMessage("Shipping information successfully updated.");
			initShippingInfo();
			//System.out.println(company.getId());
			actionType = LOAD;
		}
	}
	
	public void prepareMenu(){
		Map<String, Menu> menuList = new HashMap<String, Menu>();

		// get the single pages
		List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
		for(SinglePage singlePage : singlePageList) {
			String jspName = singlePage.getJsp(); 
			Menu menu = new Menu(singlePage.getName(), httpServer + "/" + jspName + ".do");
			menuList.put(singlePage.getJsp(), menu);
		}
		// get the multi pages
		List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
		for(MultiPage multiPage : multiPageList) {
			String jspName = multiPage.getJsp();
			Menu menu = new Menu(multiPage.getName(), httpServer + "/" + jspName + ".do");
			menuList.put(multiPage.getJsp(), menu);
		}

		// get the form Pages
		List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
		for(FormPage formPage : formPageList) {
			String jspName = formPage.getJsp();
			Menu menu = new Menu(formPage.getName(), httpServer + "/" + jspName + ".do");
			menuList.put(formPage.getJsp(), menu);
		}

		// get the groups
		List<Group> groupList = groupDelegate.findAll(company).getList();
		request.setAttribute("groupList", groupList);
		for(Group group : groupList) {
			String jspName = group.getName().toLowerCase();
			Menu menu = new Menu(group.getName(), httpServer + "/" + jspName + ".do");
			menuList.put(jspName, menu);
		}

		request.setAttribute("menu", menuList); 
	}
	
	public String shipping(){
		//validate current user, must not be empty
		if(isNull(member))
			return INPUT;
				
		
		//update values
		if(SAVE.equals(actionType)){			
			
			info = new ShippingInfo();
			
			//get all newly edited values			
			info.setName(request.getParameter("name"));
			info.setAddress1(request.getParameter("address1"));
			info.setAddress2(request.getParameter("address2"));
			info.setCity(request.getParameter("city"));
			info.setState(request.getParameter("state"));
			info.setCountry(request.getParameter("country"));
			info.setZipCode(request.getParameter("zipCode"));
			info.setPhoneNumber(request.getParameter("phoneNumber"));
			info.setEmailAddress(request.getParameter("emailAddress"));		
			
			//update member shipping info
			
			shippingInfo.setShippingInfo(info);
			logger.debug(shippingInfo.getShippingInfo().getName());
			shippingInfo.setUpdatedOn(new Date());
			
			logger.debug(String.valueOf(shippingInfoDelegate.update(shippingInfo)));
			addActionMessage("Shipping information successfully updated.");
			initShippingInfo();

			actionType = LOAD;
		}
		
		////////////////////
		
		//System.out.println("Is car null? "+cartItem);
		ObjectList<ShoppingCartItem> tempCartItems = cartItemDelegate
		.findAll(shoppingCart, cartItem);
		cartItemList = tempCartItems.getList();
		for( ShoppingCartItem item : cartItemList)	
			catItem.add(categoryItemDelegate.find(item.getItemDetail().getRealID()));
		shoppingCart.setItems(cartItemList);	
		///////////////////
		request.setAttribute("editCheck", 1);
		return SUCCESS;
	}
	
	public void getCartSize() {
		ObjectList<ShoppingCartItem> tempCartItems;
		try {
			shoppingCart = cartDelegate.find(company, member);
			tempCartItems = cartItemDelegate.findAll(shoppingCart);
			int cartSize = tempCartItems.getList().size();
			request.setAttribute("cartSize", cartSize);
		} catch (Exception e) {
			logger.debug("Shopping cart is currently empty.");
		}
		
	}
	
	private void loadAllRootCategories()
	{
		Order[] orders = {Order.asc("sortOrder")};
		List<Category> rootCategories = categoryDelegate.findAllRootCategories(company,true,orders).getList();
		request.setAttribute("rootCategories", rootCategories);		
	}
	
	/**
	 * Populates member shipping information. 
	 */
	private void initShippingInfo() {
		try {
			shippingInfo = shippingInfoDelegate.find(company, member);

			if(isNull(shippingInfo)){
				//generate an new shippingInfo
				createNewMemberShippingInfo();
			}
			else {
				if(shippingInfo.getShippingInfo()==null) {
					if(!actionType.equals(SAVE)) {
						actionType = EDIT;
						addActionMessage("Please fill out information form to proceed");
						this.setNotificationMessage("Please fill out Information Form to proceed.");
					}
				}
				else info = shippingInfo.getShippingInfo();
				
			}
		} catch (Exception e) {
			logger.debug("No shipping info found.");
		}
	}

	/**
	 * Generates a new member shipping info database entry
	 */
	private void createNewMemberShippingInfo() {
		ShippingInfo newShippingInfo = new ShippingInfo();				
		shippingInfo = new MemberShippingInfo();
		shippingInfo.setCompany(company);
		shippingInfo.setMember(member);
		shippingInfo.setShippingInfo(newShippingInfo);
		shippingInfo.setCreatedOn(new Date());
		shippingInfo.setIsValid(true);
		
		shippingInfoDelegate.insert(shippingInfo);
		actionType = EDIT;
		addActionMessage("Please fill out information form to proceed.");
		this.setNotificationMessage("Please fill out Information Form to proceed.");
	}
	
	/**
	 * Returns shipping info property value.
	 * 
	 * @return - shipping info property value
	 */
	public ShippingInfo getInfo() {
		return info;
	}
	
	/**
	 * Returns action type property value. Action type can be edit, save or load.
	 * 
	 * @return - action type property value. Action type can be edit, save or load
	 */
	public String getActionType() {
		return actionType;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public List<ShoppingCartItem> getCartItemList() {
		return cartItemList;
	}
	
	public String getNotificationMessage() {
		return notificationMessage;
	}

	public ArrayList<CategoryItem> getCatItem() {
		return catItem;
	}

	public void setCatItem(ArrayList<CategoryItem> catItem) {
		this.catItem = catItem;
	}

	public Long[] getCartItem() {
		return cartItem;
	}

	public void setCartItem(Long[] cartItem) {
		this.cartItem = cartItem;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}
	
	
}
