package com.ivant.cart.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.PreOrderDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.WishlistDelegate;
import com.ivant.cms.delegate.WishlistTypeDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.PreOrder;
import com.ivant.cms.entity.PreOrderItem;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.Wishlist;
import com.ivant.cms.entity.WishlistType;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.constants.CompanyConstants;

/**
 * Action for wishlist items create, update, and delete.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class WishlistAction extends AbstractBaseAction {

	private Logger logger = LoggerFactory.getLogger(WishlistAction.class);
	private static final long serialVersionUID = -7088841530292112458L;

	/** Object responsible for company product CRUD tasks */
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	
	/** Object responsible for wishlist item CRUD tasks */
	private WishlistDelegate wishlistDelegate = WishlistDelegate.getInstance();
	
	private WishlistTypeDelegate wishlistTypeDelegate = WishlistTypeDelegate.getInstance();
	
	/** Item added by the member to the wishlist */
	private Wishlist wishlistItem;
	
	/** The item added to the wishlist */
	private CategoryItem categoryItem;
	
	/** List of wishlist items, can be 0 or more */
	private List<Wishlist> wishlistItems;
	
	private Long itemID;
	
	private Long category_id;
	
	private String brand_id;
	
	private String price_range;
	
	private String keyword;
	
	private Long wishlistID;
	
	private Long id;
	
	private PreOrder preOrder;
	
	private PreOrderItem preOrderItem;
	
	private InputStream inputStream;
	
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private PreOrderDelegate preOrderDelegate = PreOrderDelegate.getInstance();
	private ShoppingCartItemDelegate cartItemDelegate = ShoppingCartItemDelegate.getInstance();
	private ShoppingCartDelegate cartDelegate = ShoppingCartDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private ShoppingCart shoppingCart;
	private CompanySettings companySettings;
	private static final List<String> ALLOWED_PAGES;
	
	static {
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}
	
	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	
	public Long getItemID() {
		return itemID;
	}

	public void setItemID(Long itemID) {
		this.itemID = itemID;
	}

	public Long getCategory_id() {
		return category_id;	
	}
	
	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getPrice_range() {
		return price_range;
	}

	public void setPrice_range(String price_range) {
		this.price_range = price_range;
	}
	
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getWishlistID() {
		return wishlistID.toString();
	}	
	
	public void setWishlistID(Long wishlistID) 
	{
		this.wishlistID = wishlistID;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public PreOrder getPreOrder()
	{
		return preOrder;
	}
	
	public void setPreOrder(PreOrder preOrder)
	{
		this.preOrder = preOrder;
	}
	
	public PreOrderItem getPreOrderItem()
	{
		return preOrderItem;
	}
	
	public void setPreOrderItem(PreOrderItem preOrderItem)
	{
		this.preOrderItem = preOrderItem;
	}

	@Override
	public void prepare() throws Exception {	
		companySettings = company.getCompanySettings();		
		
		loadAllRootCategories();
		initHttpServerUrl();
		loadFeaturedPages(companySettings.getMaxFeaturedPages());
		loadFeaturedSinglePages(companySettings.getMaxFeaturedSinglePages());
		loadMenu();

		//add to logs current member
		logCurrentMember();		
		//populate current category item information
		initCategoryItemInformation();
		//populate current wishlist item information
		initWishlistItemInformation();
		
		loadAllRootCategories();
		getCartSize();
		//populate server URL to be redirected to
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}	
	
	/**
	 * Returns {@code SUCCESS} if wishlist item is successfully added to
	 * database, otherwise {@code ERROR}.
	 * 
	 * @return - {@code SUCCESS} if wishlist item is successfully added to
	 *         database, otherwise {@code ERROR}
	 */
	
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
	
	public String addToWishlist(){
		// user must log in in order to add item to wishlist
		if (isNull(member)){
			updateNotificationMessage("Please login to add items to wishlist.");
			return INPUT;
		}
		
		//check if wishlist item already exists
		wishlistItem = wishlistDelegate.find(company, member, categoryItem);
		
		if(company.getName().equalsIgnoreCase("ecommerce"))
		{
			session.put("itemName", categoryItem.getName());
			session.put("itemPrice", categoryItem.getPrice());
			
			if(categoryItem.getImages().size() > 0)
				session.put("itemImage", categoryItem.getImages().get(0).getImage3());
		}
		
		//update wishlist item if it exist otherwise insert new wishlist
		if(isNull(wishlistItem)){
			//initialize new wishlist item
			initWishlistItem();
			
			//add wishlist to database
			wishlistDelegate.insert(wishlistItem);
		} else {
			if(wishlistItem.getIsValid()){
				notificationMessage = "";
				notificationMessage += categoryItem.getName() + " already added in wishlist.";
				
				request.getSession().setAttribute("notificationMessage", notificationMessage);
				return SUCCESS;
			}
			//restore wishlist item
			updateWishlistItem();
		}		
		notificationMessage = "";
		notificationMessage += categoryItem.getName() + " successfully added to wishlist.";
		
		request.getSession().setAttribute("notificationMessage", notificationMessage);
		return SUCCESS;
	}
	
	/**
	 * Returns {@code SUCCESS} if all wishlist items are successfully loaded and
	 * {@code ERROR} if user is not logged in.
	 * 
	 * @return - {@code SUCCESS} if all wishlist items are successfully loaded
	 *         and {@code ERROR} if user is not logged in.
	 */
	public String loadWishlist() {
		if(company.getId() == CompanyConstants.ECOMMERCE)
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
		}
		
		// user must log in in order to load items
		if (isNull(member))
			return ERROR;

		// get all wishlist items for current member
		ObjectList<Wishlist> objectList = wishlistDelegate.findAll(company, member);
		wishlistItems = objectList.getList(); 
		
		// notify user if wishlist is empty
		if (isNull(wishlistItems) || wishlistItems.isEmpty()) {			
			addActionMessage("Wishlist is empty.");			
		}

		return SUCCESS;
	}
	
	/**
	 * Returns {@code SUCCESS} if wishlist item was successfully removed,
	 * otherwise {@code ERROR}.
	 * 
	 * @return - {@code SUCCESS} if wishlist item was successfully removed,
	 * otherwise {@code ERROR}
	 */
	public String deleteWishlistItem() {
		
		//notify user when wishlist item is already removed
		if(null == wishlistItem){
			addActionMessage("Item already removed.");
			
			notificationMessage += "Item already removed.";
			return loadWishlist();
		}
		
		//current item must only be deleted by owner
		if(!isWishlistItemOwned()){
			addActionError("Item Deletion Restricted." +
					" Item does not belong to you.");			
			return loadWishlist();
		}		
		
		String itemName = wishlistItem.getItem().getName();
		
		//notify user when item is not removed
		if(!wishlistDelegate.delete(wishlistItem)){			
			notificationMessage = "";
			notificationMessage += itemName + " was not removed.";
			return loadWishlist();
		}
		notificationMessage = "";
		notificationMessage += itemName + " successfully removed.";
		return loadWishlist();
	}

	/**
	 * Adds current member information to logs.
	 */
	private void logCurrentMember() {
		try {
			//log current user information
			logger.debug("Current Member : " + member);
		} catch (Exception e) {
			logger.debug("No current member found.");
		}
	}

	/**
	 * Populates {@code categoryItem} based on the session parameters.
	 */
	private void initCategoryItemInformation() {
		try {
			Long categoryItemID = Long.parseLong(request.getParameter("id"));
			this.id = categoryItemID;
			categoryItem = categoryItemDelegate.find(categoryItemID);
			itemID = categoryItemID;
			
			//log category item information
			logger.debug("Current selected category item : " + categoryItem);
		} catch (Exception e) {
			logger.debug("No current category item found.");
		}
	}

	/**
	 * Populates {@code wishlistItem} based on the session parameters.
	 */
	private void initWishlistItemInformation() {
		try {
			wishlistID = Long.parseLong(request.getParameter("wishlist_id"));
			wishlistItem = wishlistDelegate.find(wishlistID);
			
			//log wishlist item information
			logger.debug("Current selected wishlist : " + wishlistItem);
		} catch (Exception e) {
			logger.debug("No wishlist item found.");
		}
	}
	
	/**
	 * Populates new wishlist item with current wishlist item information.
	 */
	private void initWishlistItem() {
		wishlistItem = new Wishlist();
		wishlistItem.setCompany(company);
		wishlistItem.setMember(member);
		wishlistItem.setItem(categoryItem);
		wishlistItem.setIsValid(true);		
		wishlistItem.setCreatedOn(new Date());
	}
	
	/**
	 * Restores wishlist item.
	 */
	private void updateWishlistItem() {
		wishlistItem.setIsValid(true);
		wishlistItem.setCreatedOn(new Date());
		wishlistDelegate.update(wishlistItem);
	}
	
	/**
	 * Returns true if current session member owns the wishlist item, otherwise false.
	 * 
	 * @return - true if current session member owns the wishlist item, otherwise false
	 */
	private boolean isWishlistItemOwned() {		
		return wishlistItem.getMember().getId().equals(member.getId());
	}
	
	/**
	 * Returns {@code wishlistItems} property value.
	 * 
	 * @return - {@code wishlistItems} property value
	 */
	public List<Wishlist> getWishlistItems() {
		return wishlistItems;
	}
	
	/**
	 * Returns {@code wishlistItem} property value.
	 * 
	 * @return - {@code wishlistItem} property value
	 */
	public Wishlist getWishlistItem() {
		return wishlistItem;
	}
	
	/**
	 * Returns shoppingCart property value.
	 * 
	 * @return - shoppingCart property value
	 */
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	
	private void loadMenu() {
		try {
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
			for(Group group : groupList) {
				String jspName = group.getName().toLowerCase();
				Menu menu = new Menu(group.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
			}
			
			// get the link to the allowed pages
			for(String s: ALLOWED_PAGES) {
				String jspName = s.toLowerCase();
				Menu menu = new Menu(jspName, httpServer + "/" + jspName + ".do"); 
				menuList.put(jspName, menu);
			}
			
			request.setAttribute("menu", menuList); 
		}
		catch(Exception e) {
		}
	}
	
	private void loadFeaturedPages(int max) {
		Map<String, Object> featuredPages = new HashMap<String, Object>();
		List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();
		
		for(MultiPage mp : featuredMultiPage) {
			if(!mp.getHidden()) {
				featuredPages.put(mp.getName(), mp);
			}
		}
		
		//System.out.println("size:::: " + featuredMultiPage.size());
		request.setAttribute("featuredPages", featuredPages);
	}
	
	private void loadFeaturedSinglePages(int max) {
		Map<String, Object> featuredSinglePages = new HashMap<String, Object>();
		List<SinglePage> featuredSinglePage = singlePageDelegate.findAllFeatured(company).getList();
		
		for(SinglePage mp : featuredSinglePage) {
			if(!mp.getHidden()) {
				featuredSinglePages.put(mp.getName(), mp);
			}
		}
		
		request.setAttribute("featuredSinglePages", featuredSinglePages);
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	@SuppressWarnings("unchecked")
	public String savewishlist() {
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		String requestSItemId = request.getParameter("item_id");
		String requestSWishlistTypeId = request.getParameter("wishlist_type_id");
		Long requestLItemId = 0L;
		Long requestLWishlistTypeId = 0L;
		Wishlist wishList = new Wishlist();
		WishlistType wishlistType = new WishlistType();
		try{
			requestLItemId = Long.parseLong(requestSItemId);
		}
		catch(Exception e){
			
		}
		
		try{
			requestLWishlistTypeId = Long.parseLong(requestSWishlistTypeId);
		}
		catch(Exception e){
			
		}
		if(member != null){
			CategoryItem item = categoryItemDelegate.find(requestLItemId);
			wishlistType = wishlistTypeDelegate.find(requestLWishlistTypeId);
			if(item!=null){
				if(wishlistType != null){
					wishList = wishlistDelegate.findByMemberItemAndType(company, member, item, wishlistType);
				}
				else {
					wishList = wishlistDelegate.find(company, member, item);
				}
				if(wishList == null){
					wishList = new Wishlist();
					wishList.setCompany(company);
					wishList.setMember(member);
					wishList.setItem(item);
					wishlistDelegate.insert(wishList);
					
					obj.put("success", true);
					obj.put("isAddedToWishList", true);
					obj.put("wishListContent", countWishListWithFormat(company, member));
					objList.add(obj);
					obj2.put("listAddToWishListDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				}
				else{
					//delete this item to wishlist (only when it is an existing item)
					wishlistDelegate.delete(wishList);
					obj.put("success", true);
					obj.put("isAddedtoWishList", false);
					obj.put("wihListContent", countWishListWithFormat(company, member));
					objList.add(obj);
					obj2.put("listAddToWishListDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				}
			}
			else{
				obj.put("errorMessage", "Invalid Item! This item was not available in the database!" );
				obj.put("isAddedToWishList", false);
				obj.put("wishListContent", countWishListWithFormat(company, member));
				objList.add(obj);
				obj2.put("listAddToWishListDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
			}
		}
		else{
			obj.put("errorMessage", "You are not a recognized user! Please log-in first before adding this item to compare!");
			obj.put("isAddedToWishList", false);
			obj.put("compareWishList", countWishListWithFormat(company, member));
			objList.add(obj);
			obj2.put("listAddToWishListDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
		}
		return SUCCESS;
	}
	
	public String countWishListWithFormat(Company company, Member member){
		Integer totalCount = 0;
		DecimalFormat myFormatter = new DecimalFormat("###,###");
		totalCount = Integer.parseInt(String.valueOf(wishlistDelegate.getMemberWishlistCount(company, member)));
		return myFormatter.format(totalCount);
	}
}
