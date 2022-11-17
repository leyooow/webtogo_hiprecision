package com.ivant.cart.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.hibernate.criterion.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CartOrderItemDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemFileDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.OSSShippingAreaDelegate;
import com.ivant.cms.delegate.OSSShippingLocationDelegate;
import com.ivant.cms.delegate.OtherDetailDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.PreOrderDelegate;
import com.ivant.cms.delegate.PreOrderItemDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.WishlistDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.CategoryItemOtherField;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.ItemFile;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.OSSShippingArea;
import com.ivant.cms.entity.OSSShippingLocation;
import com.ivant.cms.entity.OtherDetail;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.PreOrder;
import com.ivant.cms.entity.PreOrderItem;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileImageUtil;
import com.ivant.utils.FileUtil;
import com.opensymphony.xwork2.Action;

/**
 * Action for shopping cart and shopping cart items create, update, and delete.
 *
 * @author Mark Kenneth M. Ra?osa
 */
public class CartAction
		extends AbstractBaseAction
{

	private final Logger logger = LoggerFactory.getLogger(CartAction.class);
	private static final long serialVersionUID = 6860916547686589519L;
	private static final String DISCOUNT = "Discount";
	private final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private final MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private final FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final PreOrderDelegate preOrderDelegate = PreOrderDelegate.getInstance();
	private final PreOrderItemDelegate preOrderItemDelegate = PreOrderItemDelegate.getInstance();
	private final OSSShippingLocationDelegate locationDelegate = OSSShippingLocationDelegate.getInstance();
	private final OSSShippingAreaDelegate areaDelegate = OSSShippingAreaDelegate.getInstance();
	private final OtherDetailDelegate otherDetailDelegate = OtherDetailDelegate.getInstance();
	private final WishlistDelegate wishlistDelegate = WishlistDelegate.getInstance();
	private final ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	private final ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();
	
	private OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate
			.getInstance();
	
	private CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	
	
	CartOrderItemDelegate cartOrderItemDelegate = CartOrderItemDelegate.getInstance();

	/** Object responsible for category item CRUD tasks */
	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();

	/** Object responsible for shopping cart CRUD tasks */
	private final ShoppingCartDelegate cartDelegate = ShoppingCartDelegate.getInstance();

	/** Object responsible for shopping cart item CRUD tasks */
	private final ShoppingCartItemDelegate cartItemDelegate = ShoppingCartItemDelegate.getInstance();

	private final ItemFileDelegate itemFileDelegate = ItemFileDelegate.getInstance();

	CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();

	/** Shopping cart items, can be 0 or more */
	private List<ShoppingCartItem> cartItemList;

	/** Currently selected site product/service, can be null */
	private CategoryItem currentCategoryItem;

	/** Currently selected shopping cart item, can be null */
	private ShoppingCartItem currentCartItem;

	/** User's shopping cart for storing items bought */
	private ShoppingCart shoppingCart;

	/** Particulars about the selected product/service */
	private ItemDetail itemDetail;

	private PreOrder preOrder;

	private PreOrderItem preOrderItem;

	private PreOrderItem currentPreOrderItem;

	private List<PreOrderItem> preOrderItemList;

	private final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();

	private Long itemID;
	private String price_range;
	private List<String> filename = new ArrayList<String>();

	private Long wishlistID;
	private ArrayList<CategoryItem> catItem = new ArrayList<CategoryItem>();

	public String getWishlistID()
	{
		return this.wishlistID.toString();
	}

	private ArrayList<String> quote = new ArrayList<String>();
	private ArrayList<Integer> quoteNum = new ArrayList<Integer>();
	private ArrayList<CategoryItem> quoteItems = new ArrayList<CategoryItem>();
	private ArrayList<String> quoteItemsNum = new ArrayList<String>();
	private final ArrayList<String> quotation = new ArrayList<String>();
	private final ArrayList<String> quotationNum = new ArrayList<String>();
	private String quantity = "2";
	private String shippingFee;
	private String keyword;
	private Long pageID;

	private final ArrayList<CategoryItem> noLogInCartItems = new ArrayList<CategoryItem>();
	private final ArrayList<CategoryItem> GameplacePromoShoppingCartItems = new ArrayList<CategoryItem>();

	private List<OSSShippingArea> ossShippingAreas;
	private List<OSSShippingLocation> ossShippingLocations;
	private String areas = "";
	private String locations = "";
	private String tempArea = "";
	private String gcIds;
	private static final String ATTACHMENT_FOLDER = "message_attachments";
	private String view;
	private String pickupSelect = "";
	private String optionSelect = "";
	private String shippingLocation = "";
	private String accessKey = "";
	private String item_count = "";
	private CategoryItem item_selected = null;
	private Long item_selected_id = 0L;
	private InputStream inputStream;
	private Date now;
	private Integer currentPage;
	
	private File fileForPromo;

	public File getFileForPromo() {
		return fileForPromo;
	}

	public void setFileForPromo(File fileForPromo) {
		this.fileForPromo = fileForPromo;
	}

	public String getItemID()
	{
		return this.itemID.toString();
	}

	@Override
	public void prepare() throws Exception
	{
		// get member shopping cart information
		initShoppingCart();

		// get selected category item
		initCategoryItem();

		loadAllRootCategories();

		getCartSize();
		
		loadCartOrder();

		// populate server URL to be redirected to
		initHttpServerUrl();

		if(session.get("quote") != null)
		{
			loadQuotation();
		}

		prepareMenu();

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
	}

	private void loadCartOrder() {
		// TODO Auto-generated method stub
		if(member != null) {
			List<CartOrder> cartOrder = cartOrderDelegate.findAll(company, member);
			request.setAttribute("cartOrders", cartOrder);
		}
	}

	@Override
	public String execute() throws Exception
	{
		return SUCCESS;
	}

	/**
	 * Adding the an item to cart from wishlist.
	 *
	 * @author Samiel Gerard C. Santos
	 */
	public String addToCartFromWishlist()
	{

		this.wishlistID = Long.parseLong(request.getParameter("wishID").toString());

		// validate current user, must not be empty
		if(isNull(member))
		{
			return INPUT;
		}

		// check if user has shopping cart, use existing otherwise create a cart
		// for user
		validateShoppingCart();

		// check if selected category item already exists in the cart
		if(!isSelectedItemValid())
		{
			return ERROR;
		}

		// notify user that cart item was successfully added
		updateNotificationMessage(currentCategoryItem.getName() + " successfully added in cart.");

		return SUCCESS;
	}

	public String addToPreOrder()
	{
		if(company.getName().equalsIgnoreCase("ecommerce"))
		{
			session.put("itemName", currentCategoryItem.getName());
			session.put("itemPrice", currentCategoryItem.getPrice());

			if(currentCategoryItem.getImages().size() > 0)
			{
				session.put("itemImage", currentCategoryItem.getImages().get(0).getImage3());
			}
		}

		if(isNull(member))
		{
			return INPUT;
		}

		validatePreOrder();

		if(!hasDuplicatePreOrderItem(currentCategoryItem))
		{
			preOrderItem = new PreOrderItem();
			preOrderItem.setCategoryItem(currentCategoryItem);
			preOrderItem.setCompany(company);
			preOrderItem.setCreatedOn(new Date());
			preOrderItem.setPreOrder(preOrder);
			preOrderItem.setQuantity(1);
			preOrderItem.setUpdatedOn(new Date());
			preOrderItemDelegate.insert(preOrderItem);

			updateNotificationMessage(currentCategoryItem.getName() + " successfully added in pre-order list.");
		}
		else
		{
			updateNotificationMessage(currentCategoryItem.getName() + " is already in your pre-order list.");
		}

		return SUCCESS;
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

	public void prepareRootCategories()
	{
		final Order[] orders = { Order.asc("sortOrder") };
		final List<Category> rootCategories = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		final List<Category> orderedlist = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		request.setAttribute("rootCategories", rootCategories);

		orderedlist.clear();

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

	public void getCartSize()
	{
		ObjectList<ShoppingCartItem> tempCartItems;
		ShoppingCart shoppingCart1;
		try
		{
			shoppingCart1 = cartDelegate.find(company, member);
			tempCartItems = cartItemDelegate.findAll(shoppingCart1);
			final int cartSize = tempCartItems.getList().size();
			request.setAttribute("cartSize", cartSize);
		}
		catch(final Exception e)
		{
			logger.debug("Shopping cart is currently empty.");
		}

	}

	private void loadAllRootCategories()
	{
		final Order[] orders = { Order.asc("sortOrder") };
		final List<Category> rootCategories = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		final List<Category> orderedlist = categoryDelegate.findAllRootCategories(company, true, orders).getList();
		request.setAttribute("rootCategories", rootCategories);

		orderedlist.clear();

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

		Collections.sort(orderedlist, new Comparator<Category>()
		{

			@Override
			public int compare(Category cat1, Category cat2)
			{
				return cat1.getName().compareToIgnoreCase(cat2.getName());
			}

		});

		request.setAttribute("orderedCategories", orderedlist);
		
		List<CategoryItem> allItemsOfThisGroup = null;
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.AYROSOHARDWARE)) {
			allItemsOfThisGroup = categoryItemDelegate.getAllItems(company, groupDelegate.find(Long.parseLong("445")), -1, -1, Order.desc("itemDate")).getList();
			request.setAttribute("allItemsOfThisGroup", allItemsOfThisGroup);
		}
	}

	/**
	 * Returns {@code SUCCESS} if selected category is added to cart, {@code INPUT} if member is null, and {@code ERROR} if an error occurred
	 * during
	 * the process.
	 *
	 * @return - {@code SUCCESS} if selected category is added to cart, {@code INPUT} if member is null, and {@code ERROR} if an error
	 *         occurred
	 *         during the process
	 */
	public String addToCart()
	{
		// validate current user, must not be empty
		if(company.getId() == 192)
		{

		}
		else
		{
			if(isNull(member))
			{
				updateNotificationMessage("Please login to add items to cart.");
				return INPUT;
			}
		}
		if(request.getParameter("keyword") != null)
		{
			keyword = request.getParameter("keyword").toString();
		}

		// check if user has shopping cart, use existing otherwise create a cart
		// for user
		validateShoppingCart();
		/**
		 * This part is for Rewards program.
		 */
		if(company.getId() == 222 || company.getName().equalsIgnoreCase("westerndigital"))
		{ // APC  //westerndigital
			itemDetail = currentCategoryItem.getItemDetail();
			Double val;
			if(member.getValue() == null)
			{
				val = 0D;
			}
			else
			{
				val = new Double(member.getValue());
			}

			if(new Double(itemDetail.getPrice() + getTotalPrice()).compareTo(val) > 0)
			{
				updateNotificationMessage("Not enough approved points. Item not added to cart.");
				return ERROR;
			}
		}

		// check if selected category item already exists in the cart
		if(!isSelectedItemValid())
		{
			return ERROR;
		}

		// notify user that cart item was successfully added
		if(company.getId() == 152)
		{
			updateNotificationMessage(currentCategoryItem.getName() + " successfully added to quote.");
		}
		else
		{
			updateNotificationMessage(currentCategoryItem.getName() + " successfully added in cart.");
		}

		return SUCCESS;
	}

	public String webtogoBuy()
	{

		final CategoryItem categoryItem = categoryItemDelegate.find(Long.parseLong(request.getParameter("item_id")));
		request.setAttribute("catItem", categoryItem);

		return SUCCESS;
	}

	Member tempMember = new Member();

	public String itmId;

	public String addToQuotation()
	{

		if(company.getName().equalsIgnoreCase("ecommerce"))
		{
			session.put("itemName", currentCategoryItem.getName());
			session.put("itemPrice", currentCategoryItem.getPrice());

			if(currentCategoryItem.getImages().size() > 0)
			{
				session.put("itemImage", currentCategoryItem.getImages().get(0).getImage3());
			}
		}

		// validate current user, must not be empty
		if(isNull(member))
		{
			if(session.get("quote") != null)
			{
				quote = new ArrayList<String>();
				quoteNum = new ArrayList<Integer>();
				quote.addAll((List<String>) session.get("quote"));
				quoteNum.addAll((List<Integer>) session.get("quoteNum"));
				if(!selectedItemValid())
				{
					return ERROR;
				}
			}
			quote.add(currentCategoryItem.getId().toString());
			if(company.getName().equalsIgnoreCase("drugasia") || company.getName().equalsIgnoreCase("korphilippines") || company.getName().equalsIgnoreCase("apc")
					|| company.getName().equalsIgnoreCase("mdt"))
			{
				quoteNum.add(Integer.parseInt(request.getParameter("quantity")));
			}
			else
			{
				quoteNum.add(1);
			}
			session.put("quote", quote);
			session.put("quoteNum", quoteNum);
		}
		else
		{
			// check if user has shopping cart, use existing otherwise create a cart
			// for user
			validateShoppingCart();
			// check if selected category item already exists in the cart
			if(!isSelectedItemValid() && !company.getName().equalsIgnoreCase("apc"))
			{
				return ERROR;
			}
		}
		// notify user that cart item was successfully added
		updateNotificationMessage(currentCategoryItem.getName() + " successfully added in cart.");
		return SUCCESS;
	}

	public String addToShoppingCart()
	{
		// validate current user, must not be empty
		if(isNull(member))
		{
			if(CompanyConstants.MR_AIRCON.equalsIgnoreCase(company.getName()))
			{
				addToMrAirConNoLogInCart();
			}
			else
			{
				addToNoLogInCart();
			}
		}
		else
		{
			// check if user has shopping cart, use existing otherwise create a cart
			// for user
			validateShoppingCart();
			// check if selected category item already exists in the cart
			if(!isSelectedItemValid())
			{
				return ERROR;
			}
		}
		// notify user that cart item was successfully added
		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{
			session.remove("shoppingCartCount");
			updateNotificationMessage("Items have been added to your shopping cart. Click My Shopping Cart at the top to view list, edit list or check out.");
		}
		else
		{
			updateNotificationMessage(currentCategoryItem.getName() + " successfully added in cart.");
		}
		return SUCCESS;
	}

	private boolean selectedItemValid()
	{
		int counter = 0;
		for(final String itemID : quote)
		{
			if(currentCategoryItem.getId().toString().equals(itemID.toString()))
			{
				if(company.getId() != 247)
				{
					if(company.getName().equalsIgnoreCase("drugasia") || company.getName().equalsIgnoreCase("korphilippines"))
					{
						quoteNum.set(counter, quoteNum.get(counter) + Integer.parseInt(request.getParameter("quantity")));
					}
					else
					{
						quoteNum.set(counter, quoteNum.get(counter) + 1);
					}

					session.put("quoteNum", quoteNum);
					updateNotificationMessage(currentCategoryItem.getName() + "'s quantity has been updated in the cart.");
				}
				else
				{
					updateNotificationMessage(currentCategoryItem.getName() + " is already in your cart.");
				}
				return false;
			}
			counter++;
		}
		return true;
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
	public String loadCartItems()
	{
		// validate current user, must not be empty
		if(isNull(member))
		{
			return INPUT;
		}

		session.remove("name");
		session.remove("phoneNumber");
		session.remove("email");
		session.remove("comments");

		final List<Category> categories = categoryDelegate.findAllPublished(company, null, null, true, Order.asc("sortOrder")).getList();
		request.setAttribute("categoryMenu", categories);

		// check if user has shopping cart, use existing otherwise create a cart
		// for user
		validateShoppingCart();

		// get cart items
		initCartItems();

		return SUCCESS;
	}

	/**
	 * update the value of the items in the quotation
	 */
	public String updateQuotedQuantity()
	{
		if(session.get("quoteNum") != null)
		{
			final List<Integer> tempNum = new ArrayList<Integer>();
			final List<String> tempQuote = new ArrayList<String>();
			tempNum.addAll((List<Integer>) session.get("quoteNum"));
			tempQuote.addAll((List<String>) session.get("quote"));

			final String quantity[] = request.getParameterValues("quantity");
			for(int i = 0; i < tempNum.size(); i++)
			{
				tempNum.set(i, Integer.parseInt(quantity[i]));
			}

			final Integer index = Integer.parseInt(request.getParameter("index"));
			session.put("quoteNum", tempNum);
			categoryItemDelegate.find(Long.parseLong(tempQuote.get(index)));
			this.setNotificationMessage("Quantity of item(s) has been updated in the cart.");
		}
		else
		{
			return ERROR;
		}

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

		if(company.getId() == CompanyConstants.DRUGASIA)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
				session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
			}
		}

		if(company.getId() == CompanyConstants.MDT)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
				session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
			}
		}

		return SUCCESS;
	}

	public String loadQuotedCartItems()
	{
		// validate current user, if empty load quotation items
		final List<CategoryItem> quotation = new ArrayList<CategoryItem>();
		if(isNull(member))
		{
			if(session.get("quote") != null)
			{
				quote = new ArrayList<String>();
				quoteNum = new ArrayList<Integer>();
				quote.addAll((List<String>) session.get("quote"));
				quoteNum.addAll((List<Integer>) session.get("quoteNum"));
				for(final String temp : quote)
				{
					quotation.add(categoryItemDelegate.find(Long.parseLong(temp)));
				}
				request.setAttribute("quotation", quotation);
				request.setAttribute("quoteNum", quoteNum);
			}
		}
		else
		{
			if(session.get("quote") != null)
			{
				quote = new ArrayList<String>();
				quoteNum = new ArrayList<Integer>();
				quote.addAll((List<String>) session.get("quote"));
				quoteNum.addAll((List<Integer>) session.get("quoteNum"));
				int counter = 0;
				for(final String tempQuote : quote)
				{
					itemID = Long.parseLong(tempQuote);
					currentCategoryItem = categoryItemDelegate.find(itemID);
					quantity = quoteNum.get(counter).toString();
					System.out.println(addToQuotation());
					counter++;
				}
				session.remove("quote");
				session.remove("quoteNum");
			}

			// check if user has shopping cart, use existing otherwise create a cart
			// for user
			validateShoppingCart();

			// get cart items
			initCartItems();
		}

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

		if(company.getId() == CompanyConstants.DRUGASIA || company.getId() == CompanyConstants.MDT || company.getId() == CompanyConstants.ONLINE_DEPOT || company.getId() == CompanyConstants.GURKKA
				|| company.getId() == CompanyConstants.GURKKA_TEST)
		{
			if(!isNull(member) && session.get("noLogInCartItems") != null)
			{
				quoteItems = new ArrayList<CategoryItem>();
				quoteItems.addAll((List<CategoryItem>) session.get("noLogInCartItems"));
				for(final CategoryItem tempQuote : quoteItems)
				{
					itemID = tempQuote.getId();
					currentCategoryItem = categoryItemDelegate.find(itemID);
					quantity = tempQuote.getOtherDetails();
					if(itemID > 0)
					{
						System.out.println(addToShoppingCart());
					}
				}
				session.remove("noLogInCartItems");
			}

			// check if user has shopping cart, use existing otherwise create a cart
			// for user
			validateShoppingCart();

			// get cart items
			initCartItems();

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

		if(company.getId() == CompanyConstants.KORPHILIPPINES)
		{
			if(getShoppingCart() != null)
			{
				session.put("shoppingcartcount", getShoppingCart().getTotalCartQuantity());
				session.put("shoppingcarttotprice", getShoppingCart().getTotalCartPrice());
			}
		}

		return SUCCESS;
	}

	public String deleteItem()
	{
		quote = new ArrayList<String>();
		quoteNum = new ArrayList<Integer>();
		quote.addAll((List<String>) session.get("quote"));
		quoteNum.addAll((List<Integer>) session.get("quoteNum"));

		final int index = Integer.parseInt(request.getParameter("index"));
		quote.remove(index);
		quoteNum.remove(index);

		session.put("quote", quote);
		session.put("quoteNum", quoteNum);

		return SUCCESS;
	}

	public String loadPreOrder()
	{
		if(isNull(member))
		{
			return INPUT;
		}

		preOrder = preOrderDelegate.find(company, member);

		preOrderItemList = preOrderItemDelegate.findAll(company, preOrder);

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

		return SUCCESS;
	}

	/**
	 * Checks if current user has a shopping cart, if found the cart is loaded
	 * otherwise a new one is created.
	 */
	private void validateShoppingCart()
	{
		// check if cart exists for current member
		if(isNull(shoppingCart))
		{
			// generate a new shopping cart for user

			createNewShoppingCart();

			// re initialize empty shopping cart information
			initShoppingCart();
		}
	}

	private void validatePreOrder()
	{
		preOrder = preOrderDelegate.find(company, member);

		if(isNull(preOrder))
		{
			createNewPreOrder();
		}
	}

	/**
	 * Returns true if added category item does not exist in the cart, otherwise
	 * false.
	 *
	 * @return - true if added category item does not exist in the cart,
	 *         otherwise false
	 */
	private Boolean isSelectedItemValid()
	{
		// copy item detail of current categoryItem
		itemDetail = currentCategoryItem.getItemDetail();
		itemDetail.setRealID(itemID);
		itemDetail.setShippingPrice(currentCategoryItem.getShippingPrice());
		currentCategoryItem.getItemDetail().setRealID(itemID);

		// for Drugasia prescription
		if(company.getId() == 272)
		{}

		// lookup for cart item with the same detail
		if(!hasDuplicateCartItem(itemDetail))
		{
			createNewShoppingCartItem();
		}
		else
		{
			if(!isCartItemRemoved())
			{
				if(company.getId() != 136 && company.getId() != 152 && company.getId() != 199 && company.getId() != 194 && company.getId() != 247)
				{
					updateQuantity();

					updateNotificationMessage(currentCategoryItem.getName() + "'s quantity has been updated in the cart.");
				}
				else
				{
					if(company.getId() == 152)
					{
						updateNotificationMessage(currentCategoryItem.getName() + " is already in your quote.");
					}
					else
					{
						updateNotificationMessage(currentCategoryItem.getName() + " is already in your cart.");
					}
				}

				return false;
			}
			else
			{
				reuseShoppingCartItem();
			}
		}
		return true;
	}

	/*
	 * @author Rodelyn and Sam
	 */
	private void updateQuantity()
	{
		currentCartItem.setUpdatedOn(new Date());
		if(request.getParameter("quantity") != null)
		{
			final String qtyString = request.getParameter("quantity");
			final Integer quantity = Integer.parseInt(qtyString.replaceAll("[^\\d]", ""));
			currentCartItem.setQuantity(currentCartItem.getQuantity() + quantity);
		}
		else if(currentCartItem.getQuantity() != null && quantity != null)
		{
			final Integer quantity = Integer.parseInt(this.quantity.replaceAll("[^\\d]", ""));
			currentCartItem.setQuantity(currentCartItem.getQuantity() + quantity);
		}
		cartItemDelegate.update(currentCartItem);
	}

	/**
	 * Returns true if {@code currentCartItem} is temporarily removed from the
	 * database, otherwise false.
	 *
	 * @return - true if {@code currentCartItem} is temporarily removed from the
	 *         database, otherwise false
	 */
	private Boolean isCartItemRemoved()
	{
		return !currentCartItem.getIsValid();
	}

	/**
	 * Inserts a new shopping cart item to the database.
	 */
	private void createNewShoppingCartItem()
	{
		// create a new cart item entry
		currentCartItem = new ShoppingCartItem();
		initNewShoppingCartItem();
		currentCartItem.setCreatedOn(new Date());
		// add item to cart
		currentCartItem.setShoppingCart(shoppingCart);

		cartItemDelegate.insert(currentCartItem);
	}

	/**
	 * Restores used shopping cart item as a new shopping cart item entry.
	 */
	private void reuseShoppingCartItem()
	{
		initNewShoppingCartItem();
		currentCartItem.setUpdatedOn(new Date());

		cartItemDelegate.update(currentCartItem);
	}

	/**
	 * Initializes a new shopping cart properties.
	 */
	private void initNewShoppingCartItem()
	{
		currentCartItem.setCompany(company);
		currentCartItem.setItemDetail(itemDetail);

		if(request.getParameter("priceName") != null)
		{
			final String temp = currentCategoryItem.getItemDetail().getName();
			currentCartItem.getItemDetail().setName(temp + " (" + request.getParameter("priceName").toString() + ")");
		}

		if(request.getParameter("itemPrice") != null)
		{
			currentCartItem.getItemDetail().setPrice(Double.parseDouble(request.getParameter("itemPrice")));
		}

		if(request.getParameter("quantity") != null)
		{
			final String qtyString = request.getParameter("quantity");
			final Integer quantity = Integer.parseInt(qtyString.replaceAll("[^\\d]", ""));
			currentCartItem.setQuantity(quantity);
		}
		else if(quantity != null)
		{
			final Integer quantity = Integer.parseInt(this.quantity.replaceAll("[^\\d]", ""));
			currentCartItem.setQuantity(quantity);
		}
		currentCartItem.setIsValid(true);
	}

	/**
	 * Returns true if a duplicate shopping cart item is found, otherwise false.
	 *
	 * @param itemDetail
	 *            - detail of the item that has been added to the cart
	 * @return - true if a duplicate shopping cart item is found, otherwise
	 *         false
	 */
	private boolean hasDuplicateCartItem(ItemDetail itemDetail)
	{
		// get cart item with the same detail
		currentCartItem = cartItemDelegate.find(shoppingCart, itemDetail);

		if(isNull(currentCartItem))
		{
			// lookup deleted cart item values
			currentCartItem = cartItemDelegate.find(shoppingCart, itemDetail, false);
			if(isNull(currentCartItem))
			{
				return false;
			}
		}
		// has duplicate value
		return true;
	}

	private boolean hasDuplicatePreOrderItem(CategoryItem categoryItem)
	{
		currentPreOrderItem = preOrderItemDelegate.find(preOrder, categoryItem);

		if(isNull(currentPreOrderItem))
		{
			return false;
		}

		return true;
	}

	private void loadQuotation()
	{
		quoteItems = new ArrayList<CategoryItem>();
		quoteItemsNum = new ArrayList<String>();
		quotation.addAll((List<String>) session.get("quote"));
		quotationNum.addAll((List<String>) session.get("quoteNum"));
		for(final String x : quotation)
		{
			final CategoryItem item = categoryItemDelegate.find(Long.parseLong(x));
			quoteItems.add(item);
		}
		quoteItemsNum = quotationNum;
	}

	/**
	 * Generates a new shopping cart for user.
	 */
	private void createNewShoppingCart()
	{
		shoppingCart = new ShoppingCart();
		shoppingCart.setCompany(company);
		shoppingCart.setMember(member);
		shoppingCart.setCreatedOn(new Date());
		cartDelegate.insert(shoppingCart);
	}

	/**
	 * Generates a new shopping cart for user.
	 */
	private void createNewPreOrder()
	{
		preOrder = new PreOrder();
		preOrder.setCompany(company);
		preOrder.setMember(member);
		preOrder.setCreatedOn(new Date());
		preOrderDelegate.insert(preOrder);
	}

	/**
	 * Populates local category item information.
	 */
	private void initCategoryItem()
	{
		try
		{
			String parameterIdName = "";
			if(company.getName().equalsIgnoreCase("gurkkatest")){
				parameterIdName = "item_id";
			}
			else{
				parameterIdName = "id";
			}
			final Long categoryItemID = Long.parseLong(request.getParameter(parameterIdName));
			itemID = categoryItemID;
			currentCategoryItem = categoryItemDelegate.find(company, categoryItemID);
		}
		catch(final Exception e)
		{
			logger.debug("No category Item found.");
		}
	}

	/**
	 * Populates shopping cart with cart items.
	 */
	private void initCartItems()
	{
		try
		{
			final ObjectList<ShoppingCartItem> tempCartItems = cartItemDelegate.findAll(shoppingCart);
			cartItemList = tempCartItems.getList();

			int cartCounter = 0;

			for(final ShoppingCartItem item : cartItemList)
			{
				final ItemFile itemFile = itemFileDelegate.findItemFileID(company, item.getItemDetail().getRealID());
				if(itemFile != null)
				{
					filename.add(FileImageUtil.getFileImage(itemFile.getFileName()));
				}

				final CategoryItem currentItem = categoryItemDelegate.find(item.getItemDetail().getRealID());

				catItem.add(currentItem);

				if(company != null)
				{
					cartItemList.get(cartCounter).getItemDetail().setIsOutOfStock(currentItem.getIsOutOfStock());
					cartItemList.get(cartCounter).getItemDetail().setAvailableQuantity(currentItem.getAvailableQuantity());
					if(currentItem.getImages() != null) {
						cartItemList.get(cartCounter).getItemDetail().setImage(currentItem.getImages().get(0).getOriginal());
					}

				}

				cartCounter++;
			}
			request.setAttribute("fileIcon", filename);
		}
		catch(final Exception e)
		{
			logger.debug("No cart items retrieved.");
		}
	}

	/**
	 * Returns shopping cart based on current session parameters.
	 */
	private void initShoppingCart()
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

	public PreOrderItem getCurrentPreOrderItem()
	{
		return currentPreOrderItem;
	}

	public void setCurrentPreOrderItem(PreOrderItem currentPreOrderItem)
	{
		this.currentPreOrderItem = currentPreOrderItem;
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
	 * Returns current shopping cart total price.
	 *
	 * @return - current shopping cart total price
	 */
	public Double getTotalPrice()
	{
		if(null == shoppingCart)
		{
			initShoppingCart();
		}

		return shoppingCart.getTotalPrice();
	}

	public String getNotificationMessage()
	{
		return notificationMessage;
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
	 * @param quote the quote to set
	 */
	public void setQuote(ArrayList<String> quote)
	{
		this.quote = quote;
	}

	/**
	 * @return the quote
	 */
	public ArrayList<String> getQuote()
	{
		return quote;
	}

	/**
	 * @param quoteItems the quoteItems to set
	 */
	public void setQuoteItems(ArrayList<CategoryItem> quoteItems)
	{
		this.quoteItems = quoteItems;
	}

	/**
	 * @return the quoteItems
	 */
	public ArrayList<CategoryItem> getQuoteItems()
	{
		return quoteItems;
	}

	/**
	 * @param quoteItemsNum the quoteItemsNum to set
	 */
	public void setQuoteItemsNum(ArrayList<String> quoteItemsNum)
	{
		this.quoteItemsNum = quoteItemsNum;
	}

	/**
	 * @return the quoteItemsNum
	 */
	public ArrayList<String> getQuoteItemsNum()
	{
		return quoteItemsNum;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword()
	{
		return keyword;
	}

	public String getCategory_id()
	{
		return category_id;
	}

	public void setCategory_id(String category_id)
	{
		this.category_id = category_id;
	}

	public String getBrand_id()
	{
		return brand_id;
	}

	public void setBrand_id(String brand_id)
	{
		this.brand_id = brand_id;
	}

	public String getPrice_range()
	{
		return price_range;
	}

	public void setPrice_range(String price_range)
	{
		this.price_range = price_range;
	}

	public String getItem_id()
	{
		return item_id;
	}

	public void setItem_id(String item_id)
	{
		this.item_id = item_id;
	}

	private String category_id;
	private String item_id;
	private String brand_id;
	private String discount;
	private String p_totalAmount;

	public String sendCustomInstallationDetails()
	{					
		final String custom_title = request.getParameter("custom_title");
		final String custom_survey_address = request.getParameter("custom_survey_address");
		final String custom_first_name = request.getParameter("custom_first_name");
		final String custom_city = request.getParameter("custom_city");
		final String custom_last_name = request.getParameter("custom_last_name");
		final String custom_region = request.getParameter("custom_region");
		final String custom_email_address = request.getParameter("custom_email_address");
		final String custom_zip_code = request.getParameter("custom_zip_code");
		final String custom_tel_no = request.getParameter("custom_tel_no");
		final String custom_mobile_no = request.getParameter("custom_mobile_no");
		final String custom_additional_instruction = request.getParameter("custom_additional_instruction");
		final String custom_company = request.getParameter("custom_company");
		final String custom_survey_date = request.getParameter("custom_survey_date");
		
		String name = custom_title +" "+custom_first_name + " " + custom_last_name;

		final String contactNum = custom_mobile_no + "/" + custom_tel_no;
		final String address = custom_survey_address + " ," + custom_city + " ," + custom_region +" ,"+custom_zip_code;

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
		try
		{
			final EmailSenderAction emailSender = new EmailSenderAction();

			company.setEmail(company.getEmail());
			emailSender.setEmail(custom_email_address);
			String message = "";

			message += "<tr>";
			message += "<td style=\"padding: 0 50px;\">";
			message += "<p>";

			emailSender.setModeOfPayment("bank");
			emailSender.setMessage(message);
			emailSender.setSubject("The " + company.getNameEditable() + " Order Form Submission Pay via Bank.");
			emailSender.setCompany(company);

			String filepath = "";
			if(session.get("filepath") != null)
			{
				filepath = session.get("filepath").toString();
			}
			emailSender.sendEmailPaymentInformation(name, custom_last_name,
					contactNum, address, catItems, filepath,
					custom_additional_instruction, custom_company,
					custom_survey_date);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
		session.remove("mrAirConNoLogInCartItems");
		return SUCCESS;
	}

	public String confirmNoLogInCartContents()
	{
		if(session == null)
		{
			session = (Map) request.getSession(true);
		}
		
		String name = "", email = "", contactNumber = "", address = "", province = "";
		String address2 = "", city = "", zipcode = "", phonenumber = "";
		String shippingCostAmount = request.getParameter("shippingCostAmount");
		boolean flag = false;

		if(request.getParameter("1a|name") != null && request.getParameter("1b|email") != null && request.getParameter("1c|contact_number") != null && request.getParameter("1d|address") != null
				&& request.getParameter("otherCityLocation") != null)
		{
			flag = true;
		}
		else if(request.getParameter("1a|name") != null && request.getParameter("1b|email") != null && request.getParameter("1c|contact_number") != null && request.getParameter("1d|address") != null
				&& request.getParameter("1e|province") != null)
		{
			flag = true;
		}
		
		if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA) {
			Boolean discountFound = false;

			now = new Date();

			request.setAttribute("country", request.getParameter("country"));
			request.setAttribute("landmark", request.getParameter("landmark"));
			request.setAttribute("promo", request.getParameter("promo"));
			
			if(request.getParameter("paymenttype") != null)
			{
				request.setAttribute("paymenttype", request.getParameter("paymenttype"));
			}
			
			List<CategoryItem> catItems = (List<CategoryItem>) session.get("tomatoNoLogInCartItems");
			
			final String quantity[] = request.getParameterValues("quantity");
			
			CategoryItem ci = new CategoryItem();
			for(int i = 0; i < quantity.length; i++) {
				ci = catItems.get(i);
				ci.setOtherDetails(quantity[i]);
				
				if(ci.getName().indexOf("Discount") != -1 && discount != null)
				{
					if(Math.abs(Double.parseDouble(discount)) > 0.0)
					{
						discountFound = true;
						ci.setPrice(Double.parseDouble(discount));
						ci.setName("Discount");
					}
					else
						discountFound = false;
				}				
				categoryItemDelegate.update(ci);
			}
			
			if(discount != null && (!discountFound) && (Math.abs(Double.parseDouble(discount)) > 0.0))
			{
				final CategoryItem catItem = new CategoryItem();
				catItem.setId(0L);
				catItem.setSku("0");
				catItem.setName("Discount");
				catItem.setDescription("");
				catItem.setPrice(Double.parseDouble(discount));
				catItem.setOtherDetails("1");
				catItems.add(catItem);
			}
		}

		if(company.getName().equalsIgnoreCase("drugasia"))
		{
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
			}
		}

		if(flag)
		{
			name = request.getParameter("1a|name");
			email = request.getParameter("1b|email");
			contactNumber = request.getParameter("1c|contact_number");
			address = request.getParameter("1d|address");
			province = request.getParameter("1e|province");
			if(company.getName().equals("adeventsmanila"))
				address = province;
			if(request.getParameter("otherCityCheckBox") != null && request.getParameter("otherCityCheckBox").equalsIgnoreCase("otherLocation"))
			{
				province = request.getParameter("otherCityLocation");
			}

			if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2")) && request.getParameter("allFieldsRequired") != null)
			{
				if(name.equalsIgnoreCase("") || email.equalsIgnoreCase("") || contactNumber.equalsIgnoreCase("") || address.equalsIgnoreCase("") || province.equalsIgnoreCase(""))
				{
					return ERROR;
				}
			}

			// for double validation
			String selectedProvince = province;
			if(selectedProvince != null)
			{
				final String manilaCities = "Manila";

				if((company.getId() == 197) && (manilaCities.indexOf(selectedProvince) != -1) && (Double.parseDouble(getP_totalAmount()) < 650.0))
				{
					selectedProvince = "150.00";
				}
				else if(company.getId() == CompanyConstants.DRUGASIA)
				{
					selectedProvince = request.getParameter("shippingCostAmount");
				}
				else if(company.getId() == CompanyConstants.TOMATO || company.getId() == CompanyConstants.SWAPCANADA) {
					selectedProvince = request.getParameter("shippingCostAmount");
				}
				else if(company.getId() == CompanyConstants.ONLINE_DEPOT)
				{
					if(!province.equals("Metro Manila"))
					{
						selectedProvince = "150.0";
					}
					else
					{
						selectedProvince = "0.00";
					}
				}
				else if((company.getName().equalsIgnoreCase("purpletag2")) && (manilaCities.indexOf(selectedProvince) != -1) && (Double.parseDouble(getP_totalAmount()) < 600.0))
				{
					selectedProvince = "150.00";
				}
				else if(manilaCities.indexOf(selectedProvince) != -1)
				{
					selectedProvince = "0.00";
				}
				else if(request.getParameter("otherCityCheckBox") != null && request.getParameter("otherCityCheckBox").equalsIgnoreCase("otherLocation"))
				{
					if(company.getName().equals("adeventsmanila"))
						selectedProvince = "100.00";
					else
						selectedProvince = "250.00";
				}
				else
				{
					selectedProvince = "150.00";
				}

				if(province.equalsIgnoreCase("sample"))
				{
					selectedProvince = "2.00";
				}

				shippingCostAmount = selectedProvince;
			}
		}
		else
		{
			return ERROR;
		}

		if(shippingCostAmount == null)
		{
			request.setAttribute("errorMessage", "Shipping Cost was not properly computed. Please try to enter your Information again. Thanks.");
			return ERROR;
		}

		tempMember.setValue(shippingCostAmount);
		tempMember.setFirstname(name);
		tempMember.setEmail(email);
		tempMember.setPassword("");
		tempMember.setUsername(email);
		tempMember.setCompany(company);
		tempMember.setMobile(contactNumber);
		tempMember.setReg_companyAddress(address);
		tempMember.setZipcode(zipcode);
		tempMember.setNewsletter(true);
		session.put("tempMember", tempMember);
		request.setAttribute("modeOfPayment", "Pay Via Paypal");

		if(request.getParameter("payViaBank") != null)
		{
			request.setAttribute("modeOfPayment", "Pay Via Bank");
		}
		else if(request.getParameter("payViaPaypal") != null)
		{
			final Member m = memberDelegate.findAccount(email, company);

			if(m != null)
			{
				m.setFirstname(name);
				m.setUsername(email);
				m.setEmail(email);
				m.setReg_companyAddress(province + " : " + address);
				m.setMobile(contactNumber);
				m.setValue(shippingCostAmount);
				memberDelegate.update(m);
				request.setAttribute("memId", m.getId());
			}
			else
			{
				final long memId = memberDelegate.insert(tempMember);
				request.setAttribute("memId", memId);
			}

			final List<CategoryItem> catItems = (List<CategoryItem>) session.get("noLogInCartItems");

			if(catItems != null)
			{
				boolean shippingFound = false;
				/** DISCOUNT **/
				boolean discountFound = false;
				boolean valid;
				for(final CategoryItem cI : catItems)
				{
					valid = true;
					if(cI.getName().indexOf("Shipping Cost") != -1)
					{
						shippingFound = true;
						cI.setPrice(Double.parseDouble(shippingCostAmount));
					}
					/** DISCOUNT **/
					if(cI.getOtherDetails().isEmpty() || cI.getName().equals("Windows"))
					{
						valid = false;
					}

					/** DISCOUNT **/
					if(cI.getName().indexOf("Discount") != -1)
					{
						if(discount != null)
						{
							if(Math.abs(Double.parseDouble(discount)) > 0.0)
							{
								discountFound = true;
								cI.setPrice(Double.parseDouble(discount));

								if(company.getId() == CompanyConstants.PURPLETAG || company.getId() == CompanyConstants.PURPLETAG2) {
									cI.setName("Less 30 Percent Discount");
								}
								else {
									cI.setName("Less 10 Percent Discount");
								}
							}
							else
							{
								discountFound = false;
								valid = false;
							}
						}
					}

					if(valid)
					{
						noLogInCartItems.add(cI);
					}
				}

				if(!shippingFound)
				{
					String hasShipping = (request.getParameter("hasShipping") != null ? request.getParameter("hasShipping") : "");
					if((company.getName().equals("adeventsmanila") && hasShipping.equalsIgnoreCase("false"))
							|| (company.getName().equals("hiprecisiononlinestore") && hasShipping.equalsIgnoreCase("false"))) 
					{
						request.setAttribute("hasShipping", "false");
					}
					else {
						request.setAttribute("hasShipping", "true");
						final CategoryItem catItem = new CategoryItem();
						catItem.setId(0L);
						catItem.setSku("0");
						catItem.setName("Shipping Cost");
						catItem.setDescription("");
						catItem.setPrice(Double.parseDouble(shippingCostAmount));
						catItem.setOtherDetails("1");
						noLogInCartItems.add(catItem);
					}
				}
				
				/** DISCOUNT **/
				if(discount != null)
				{
					if((!discountFound) && (Math.abs(Double.parseDouble(discount)) > 0.0))
					{
						final CategoryItem catItem = new CategoryItem();
						catItem.setId(0L);
						catItem.setSku("0");
						
						if(company.getId() == CompanyConstants.PURPLETAG || company.getId() == CompanyConstants.PURPLETAG2) {
							catItem.setName("Less 30 Percent Discount");
						}
						else {
							catItem.setName("Less 10 Percent Discount");
						}
						
						catItem.setDescription("");
						catItem.setPrice(Double.parseDouble(discount));
						catItem.setOtherDetails("1");
						noLogInCartItems.add(catItem);
					}
				}
			}

			final List<CategoryItem> catItems1 = (List<CategoryItem>) session.get("noLogInCartItems");

			session.remove("noLogInCartItems");
			session.put("noLogInCartItems", noLogInCartItems);

			final List<CategoryItem> catItems2 = noLogInCartItems;
		}

		request.setAttribute("name", name);
		request.setAttribute("email", email);
		request.setAttribute("contactNumber", contactNumber);
		request.setAttribute("address", address);
		request.setAttribute("province", province);
		request.setAttribute("shippingCostAmount", shippingCostAmount);

		if(company.getName().equalsIgnoreCase("drugasia") || company.getName().equalsIgnoreCase("onlinedepot"))
		{
			address2 = request.getParameter("address2");
			city = request.getParameter("city");
			zipcode = request.getParameter("zipcode");
			phonenumber = request.getParameter("phonenumber");

			request.setAttribute("address2", address2);
			request.setAttribute("city", city);
			request.setAttribute("zipcode", zipcode);
			request.setAttribute("phonenumber", phonenumber);
		}

		/** DISCOUNT **/
		if(discount != null)
		{
			request.setAttribute("discount", Double.parseDouble(discount));
		}

		if(request.getParameter("save") != null)
		{
			address += " ,     You are from " + province;

			final CartOrder cartOrder = new CartOrder();
			cartOrder.setAddress1(address);
			cartOrder.setStatus(OrderStatus.PENDING);
			cartOrder.setCompany(company);
			cartOrder.setName(name);
			cartOrder.setEmailAddress(email);
			cartOrder.setPhoneNumber(contactNumber);

			if(company.getName().equalsIgnoreCase("drugasia") || company.getName().equalsIgnoreCase("onlinedepot"))
			{
				cartOrder.setAddress2(address2);
				cartOrder.setCity(city);
				cartOrder.setCountry("Philippines");
				cartOrder.setZipCode(zipcode);
			}

			final List<CategoryItem> catItems = (List<CategoryItem>) session.get("noLogInCartItems");
			new ArrayList<CartOrderItem>();
			final Member mem = new Member();
			mem.setId(0L);
			cartOrder.setMember(mem);

			final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
			boolean isShippingFound = false;
			/** DISCOUNT **/
			boolean isDiscountFound = false;
			boolean isValid;
			for(final CategoryItem cI : catItems)
			{
				isValid = true;
				if(cI.getName().indexOf("Shipping Cost") != -1)
				{
					cI.setPrice(Double.parseDouble(shippingCostAmount));
					isShippingFound = true;
				}
				/** DISCOUNT **/
				if(cI.getName().indexOf("Discount") != -1)
				{
					if(discount != null)
					{
						if(Math.abs(Double.parseDouble(discount)) > 0.0)
						{
							cI.setPrice(Double.parseDouble(discount));
							isDiscountFound = true;
						}
						else
						{
							isDiscountFound = true;
							isValid = false;
						}
					}
				}
				if(isValid)
				{
					final CartOrderItem cartOrderItem = new CartOrderItem();
					final ItemDetail itemDetail = new ItemDetail();

					itemDetail.setDescription(cI.getDescription());
					itemDetail.setName(cI.getName() + "(" + cI.getDescription() + ")");
					itemDetail.setPrice(cI.getPrice());

					cartOrderItem.setItemDetail(itemDetail);
					cartOrderItem.setQuantity(Integer.parseInt(cI.getOtherDetails()));
					cartOrderItem.setStatus(OrderStatus.PENDING.toString());
					cartOrderItem.setCompany(company);
					cartOrderItem.setOrder(cartOrder);
					cartOrderItems.add(cartOrderItem);
				}
			}

			if(!isShippingFound)
			{
				final ItemDetail itemDetail = new ItemDetail();
				itemDetail.setName("Shipping Cost");
				itemDetail.setPrice(Double.parseDouble(shippingCostAmount));

				final CartOrderItem cartOrderItem = new CartOrderItem();
				cartOrderItem.setItemDetail(itemDetail);
				cartOrderItem.setQuantity(Integer.parseInt("1"));
				cartOrderItem.setStatus(OrderStatus.PENDING.toString());
				cartOrderItem.setCompany(company);
				cartOrderItem.setOrder(cartOrder);
				cartOrderItems.add(cartOrderItem);

				final CategoryItem catItem = new CategoryItem();
				catItem.setId(0L);
				catItem.setSku("0");
				catItem.setName("Shipping Cost");
				catItem.setDescription("");
				catItem.setPrice(Double.parseDouble(shippingCostAmount));
				catItem.setOtherDetails("1");
				catItems.add(catItem);
			}
			/** DISCOUNT **/
			if(!isDiscountFound)
			{
				final ItemDetail itemDetail = new ItemDetail();
				
				if(company.getId() == CompanyConstants.PURPLETAG || company.getId() == CompanyConstants.PURPLETAG2) {
					itemDetail.setName("Less 30% Discount");
				}
				else {
					itemDetail.setName("Less 10% Discount");
				}

				itemDetail.setPrice(Double.parseDouble(discount));

				final CartOrderItem cartOrderItem = new CartOrderItem();
				cartOrderItem.setItemDetail(itemDetail);
				cartOrderItem.setQuantity(Integer.parseInt("1"));
				cartOrderItem.setStatus(OrderStatus.PENDING.toString());
				cartOrderItem.setCompany(company);
				cartOrderItem.setOrder(cartOrder);
				cartOrderItems.add(cartOrderItem);

				final CategoryItem catItem = new CategoryItem();
				catItem.setId(0L);
				catItem.setSku("0");
				
				if(company.getId() == CompanyConstants.PURPLETAG || company.getId() == CompanyConstants.PURPLETAG2) {
					catItem.setName("Less 30% Discount");
				}
				else {
					catItem.setName("Less 10% Discount");
				}
				
				catItem.setDescription("");
				catItem.setPrice(Double.parseDouble(discount));
				catItem.setOtherDetails("1");
				catItems.add(catItem);
			}

			if(company.getName().equalsIgnoreCase("korphilippines"))
			{
				cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));
			}
			if(company.getName().equalsIgnoreCase("drugasia"))
			{
				cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));
			}
			if(company.getName().equalsIgnoreCase("onlinedepot"))
			{
				cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));
			}
			if(company.getName().equalsIgnoreCase("gurkka") || company.getId() == CompanyConstants.GURKKA_TEST)
			{
				cartOrder.setTotalShippingPrice2(Double.parseDouble(request.getParameter("shippingCostAmount")));
			}

			cartOrder.setItems(cartOrderItems);
			final long orderId = cartOrderDelegate.insert(cartOrder);
			cartOrderItemDelegate.batchInsert(cartOrderItems);

			try
			{
				final EmailSenderAction emailSender = new EmailSenderAction();
				company.setEmail(company.getEmail());
				emailSender.setEmail(email);
				String message = "";

				message += "<tr>";
				message += "<td style=\"padding: 0 50px;\">";
				message += "<p>";

				if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2")))
				{
					message += "<strong>*If you selected the Pay via bank option please make the payment and email to info@sunnysunday.com.ph, the scanned copy of the validated deposit slip.</strong>";
					message += "<br /><br />";
					message += "<strong>Payment Method :</strong>";
					message += "<table cellpadding=\"0\" cellspacing=\"0\">";
					message += "	<tr>";
					message += "	<td width=\"100px\">Account Holder</td>";
					message += "	<td width=\"10px\">:</td>";
					message += "	<td>sunny sunday corp</td>";
					message += "</tr>";
					message += "<tr>";
					message += "<td>Account Number</td>";
					message += "<td>:</td>";
					message += "<td>1050150102</td>";
					message += "</tr>";
					message += "</table>";
					message += "	</p>";
					message += "	</td>";
					message += "</tr>";
				}
				else if(company.getName().equalsIgnoreCase("korphilippines"))
				{
					message += "<strong>*If you selected the Pay via bank option please make the payment and email to info@korwater.ph, the scanned copy of the validated deposit slip.</strong>";
				}

				emailSender.setModeOfPayment("bank");
				emailSender.setMessage(message);
				emailSender.setSubject("The " + company.getNameEditable() + " Order Form Submission Pay via Bank. Order ID NO: " + orderId);
				emailSender.setCompany(company);

				String filepath = "";
				if(session.get("filepath") != null)
				{
					filepath = session.get("filepath").toString();
				}
				emailSender.sendEmailPaymentInformation(name, email, contactNumber, address, catItems, filepath,"","","");
			}
			catch(final Exception e)
			{
				e.printStackTrace();
			}
			session.remove("noLogInCartItems");
			return Action.SUCCESS;
		}
		return Action.NONE;
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

	public String cartHistory()
	{
		if(member == null)
		{
			return Action.ERROR;
		}

		shoppingCart = cartDelegate.find(company, member);

		return Action.SUCCESS;
	}

	public String addToNoLogInCart()
	{
		boolean alreadyInNoLogInCart = false;
		String id = request.getParameter("id");
		final String removeId = request.getParameter("removeId");
		if(removeId != null)
		{
			id = removeId;
		}

		final String color = (request.getParameter("color") == null) ? "" : request.getParameter("color");
		String quantity = request.getParameter("quantity") == null ? "" : request.getParameter("quantity");
		String size = "";
		
		if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2")))
			size = request.getParameter("size") == null ? "" : request.getParameter("size");

		final Long itemId = Long.parseLong(id);
		final CategoryItem item = categoryItemDelegate.find(itemId);
		CategoryItem catItem = new CategoryItem();
		CategoryItem catItem2 = new CategoryItem();
		
		if(company.getName().equalsIgnoreCase("purpletag2"))
			item.setPrice(new Double(request.getParameter("price")));

		catItem.setId(itemId);
		catItem.setDescription(color);
		catItem.setName(item.getName() + " " +size);
		catItem.setPrice(item.getPrice());
		catItem.setParentGroup(item.getParentGroup());
		catItem.setOtherDetails(quantity);
		if(company.getName().equals("hiprecisiononlinestore")) {
			//catItem.setSku(new String(itemId.toString()));
		}
		
		if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2"))){
			if(item.getParent() != null){
				catItem.setParentGroup(item.getParentGroup());
				catItem.setParent(item.getParent());
				catItem.setShortDescription((request.getParameter("itemImage") != null ? request.getParameter("itemImage") : ""));
				if(item.getCategoryItemOtherFieldMap().get("Percent Discount") != null && !item.getCategoryItemOtherFieldMap().get("Percent Discount").getContent().equalsIgnoreCase("")) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("Discount", item.getCategoryItemOtherFieldMap().get("Percent Discount").getContent());
					catItem.setItemDetailMap(map);
				}
			}
		}
			
		if(company.getName().equalsIgnoreCase("drugasia"))
		{
			for(int i = 0; i < item.getCategoryItemOtherFields().size(); i++)
			{}
		}

		updateNotificationMessage(item.getName() + " has been successfully added in cart.");

		catItem.getPrice();

		// there is a cart
		if(session.get("cartForNoLogIn") != null)
		{

			final List<CategoryItem> catItems = (List<CategoryItem>) session.get("noLogInCartItems");

			// CHECKING IF items is already in cart
			int itemSessionId = 1;

			if(catItems != null)
			{
				for(final CategoryItem cI : catItems)
				{
					// for remove
					if(cI.getSku().equalsIgnoreCase(removeId))
					{
						itemSessionId++;
						alreadyInNoLogInCart = true;
						continue;
					}
					// for update
					if(cI.getId().toString().equalsIgnoreCase(catItem.getId().toString()))
					{
						if(company.getName().equalsIgnoreCase("purpletag2"))
						{
							if((cI.getName().equalsIgnoreCase(catItem.getName())) && (cI.getDescription().equalsIgnoreCase(catItem.getDescription()))) {
								alreadyInNoLogInCart = true;
								cI.setDescription(color);
								cI.setName(catItem.getName());
								cI.setPrice(item.getPrice());
								cI.setOtherDetails(quantity);
							}
						}
						else if(cI.getDescription().equalsIgnoreCase(catItem.getDescription()))
						{
							alreadyInNoLogInCart = true;
							cI.setDescription(color);
							cI.setName(item.getName());
							cI.setPrice(item.getPrice());

							if(company.getName().equalsIgnoreCase("drugasia"))
							{
								final Integer temp = Integer.parseInt(quantity) + Integer.parseInt(cI.getOtherDetails());
								quantity = temp.toString();
							}

							cI.setOtherDetails(quantity);
						}
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

			if(noLogInCartItems.size() == 1 && noLogInCartItems.get(0).getName().indexOf("Shipping Cost") != -1)
			{
				session.put("noLogInCartItems", null);
				return NONE;
			}
			/** DISCOUNT **/
			if(noLogInCartItems.size() == 2 && ((noLogInCartItems.get(1).getName().indexOf("Discount") != -1) || (noLogInCartItems.get(1).getName().indexOf("Windows") != -1)))
			{
				session.put("noLogInCartItems", null);
				return NONE;
			}
		}
		else
		{
			// first time cart
			catItem.setSku("1");
			
			if(company.getName().equals("hiprecisiononlinestore")) {
				//catItem.setSku(new String(itemId.toString()));
			}
			
			noLogInCartItems.add(catItem);
			session.put("cartForNoLogIn", true);
			session.put("tempMember", tempMember);

			if(!company.getName().equals("hiprecisiononlinestore")) {
				catItem = new CategoryItem();
				catItem.setId(0L);
				catItem.setSku("0");
				catItem.setName("Shipping Cost");
				catItem.setDescription("");
				catItem.setPrice(0);
				catItem.setOtherDetails("1");
				noLogInCartItems.add(catItem);
	
				/** DISCOUNT **/
				catItem2 = new CategoryItem();
				catItem2.setId(1L);
				catItem2.setSku("0");
				catItem2.setName("Less 10% Discount");
				catItem2.setDescription("");
				catItem2.setPrice(0);
				catItem2.setOtherDetails("1");
				noLogInCartItems.add(catItem2);
			}
		}

		this.itmId = id;

		session.put("noLogInCartItems", noLogInCartItems);

		if(noLogInCartItems.size() == 0)
		{
			session.put("noLogInCartItems", null);
		}

		if(removeId != null)
		{
			return NONE;
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked" })
	public String addToAdEventsNoLogInCart() {
		
		String id = request.getParameter("distanceItems");
		
		final List<CategoryItem> catItems = (List<CategoryItem>) session.get("noLogInCartItems");

		// CHECKING IF items is already in cart
		int itemSessionId = 1;
		if(catItems != null)
		{
			for(final CategoryItem cI : catItems) {
				cI.setSku("" + itemSessionId);
			cI.getPrice();
			noLogInCartItems.add(cI);
			itemSessionId++;
			}
		}
		
		ArrayList<CategoryItem> noLogInCartItems = (ArrayList<CategoryItem>) session.get("noLogInCartItems");
		
		String category = request.getParameter("category");
		
		String lastName = request.getParameter("last_name").toUpperCase();
		String firstName = request.getParameter("first_name").toUpperCase();
		String middleName = request.getParameter("middle_name").toUpperCase();
		String name = lastName + ", " + firstName + ", " + middleName;
		
		String building = request.getParameter("building");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		String contactNumber = request.getParameter("business/mobile_number");
		String emailAddress = request.getParameter("email_address");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String occupation = request.getParameter("occupation");
		
		String teamName = request.getParameter("company/school/team").toUpperCase();
		String contactPerson = request.getParameter("contact_person");
		
		String shirtSize = request.getParameter("sizes");
		
		String isInvestingInStocks = request.getParameter("isInvestingInStocks");
		String investingStockComment = request.getParameter("investing_stocks_comment");
		String currentInvestments = request.getParameter("current_investments");
		String howDidUHearBullrun = request.getParameter("how_did_u_hear_bullrun");
		
		Map<String, String> itemDetailMap = new HashMap<String, String>();
		itemDetailMap.put("category", category);
		itemDetailMap.put("name", name);
		itemDetailMap.put("lastName", lastName);
		itemDetailMap.put("firstName", firstName);
		itemDetailMap.put("middleName", middleName);
		
		itemDetailMap.put("building", building);
		itemDetailMap.put("street", street);
		itemDetailMap.put("city", city);
		itemDetailMap.put("province", province);
		itemDetailMap.put("contactNumber", contactNumber);
		itemDetailMap.put("emailAddress", emailAddress);
		itemDetailMap.put("gender", gender);
		itemDetailMap.put("birthday", birthday);
		itemDetailMap.put("occupation", occupation);
		itemDetailMap.put("teamName", teamName);
		itemDetailMap.put("contactPerson", contactPerson);
		
		itemDetailMap.put("shirtSize", shirtSize);
		
		itemDetailMap.put("isInvestingInStocks", isInvestingInStocks);
		itemDetailMap.put("investingStockComment", investingStockComment);
		itemDetailMap.put("currentInvestments", currentInvestments);
		itemDetailMap.put("howDidUHearBullrun", howDidUHearBullrun);
		
		CategoryItem newItem = categoryItemDelegate.find(Long.parseLong(id));
		
		newItem.setSku("" + itemSessionId);
		
		newItem.setDescription(name);
		newItem.setSku(newItem.getId().toString());
		newItem.setOtherDetails("1");
		newItem.setItemDetailMap(itemDetailMap);
		
		if(noLogInCartItems != null) {
			noLogInCartItems.add(newItem);
		}
		else {
			noLogInCartItems = new ArrayList<CategoryItem>();
			noLogInCartItems.add(newItem);
		}
			
		session.put("noLogInCartItems", noLogInCartItems);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String addToMrAirConNoLogInCart()
	{
		final String id = request.getParameter("id");
		final Integer quantity = Integer.parseInt(request.getParameter("quantity") == null ? "" : request.getParameter("quantity"));
		final ArrayList<CategoryItem> oldNoLogInCartItems = (ArrayList<CategoryItem>) session.get("mrAirConNoLogInCartItems");
		final ArrayList<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
		final Long itemId = Long.parseLong(id);
		final CategoryItem newItem = categoryItemDelegate.find(itemId);
		
		newItem.setOrderQuantity(quantity);

		boolean isNewItem = true;

		if(oldNoLogInCartItems != null)
		{
			updatedNoLogInCartItems.addAll(oldNoLogInCartItems);
			for(final CategoryItem oldItem : oldNoLogInCartItems)
			{
				if(oldItem.getId().longValue() == newItem.getId().longValue())
				{
					// add old quantity 
					newItem.setOrderQuantity(oldItem.getOrderQuantity() + newItem.getOrderQuantity());
					updatedNoLogInCartItems.remove(oldItem);
					updatedNoLogInCartItems.add(newItem);
					isNewItem = false;
				}
			}

		}

		if(isNewItem)
		{
			updatedNoLogInCartItems.add(newItem);
		}

		session.put("mrAirConNoLogInCartItems", updatedNoLogInCartItems);
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String addToSwapCanadaNoLogInCart()
	{
		
		JSONObject json = new JSONObject();
		
		final String id = request.getParameter("id");
		final Integer quantitySwap = Integer.parseInt(request.getParameter("quantity") == null || request.getParameter("quantity") == ""  ? "0" : request.getParameter("quantity"));
		final String strap1 = request.getParameter("strap1") == null ? "" : request.getParameter("strap1");
		final String strap2 = request.getParameter("strap2") == null ? "" : request.getParameter("strap2");	
		
		String faceId = null;
		String text = null;
		String textColor = null;
		Integer textSize = null;
		String textFont = null;
		String handColor = null;
		String description = null;
		String quantity = null;
		if(request.getParameter("faceId") != null){
			 faceId = request.getParameter("faceId");
			 text = request.getParameter("text");
			 textColor = request.getParameter("textColor");
			 textSize = Integer.parseInt(request.getParameter("textSize"));
			 textFont = request.getParameter("textFont");
			 handColor = request.getParameter("handColor");
			 description = request.getParameter("description");
			 quantity = "1";
		}
		final ArrayList<CategoryItem> oldNoLogInCartItems = (ArrayList<CategoryItem>) session.get("swapCanadaNoLogInCartItems");
		final ArrayList<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
		final Long itemId = Long.parseLong(id);
		final CategoryItem newItem = categoryItemDelegate.find(itemId);
		
		CategoryItem faceItem = null;
		final int MIN_INT = 0;
		final int MAX_INT = 1000000;
		Random random = new Random();
		String rand = Integer.toString(random.nextInt((MAX_INT - MIN_INT) + 1) + MIN_INT);
		
		if(!StringUtils.isEmpty(faceId)) {
			faceItem = categoryItemDelegate.find(Long.parseLong(faceId));
		}
				
		OtherDetail otherDetail = new OtherDetail();
		otherDetail.setCompany(company);
		otherDetail.setFace(faceItem);
		otherDetail.setStrap(newItem);
		otherDetail.setText(text);
		otherDetail.setTextColor(textColor);
		otherDetail.setTextSize(textSize);
		otherDetail.setTextFont(textFont);
		otherDetail.setHandColor(handColor);
		otherDetail.setPathDetail(description);
		otherDetail = otherDetailDelegate.find(otherDetailDelegate.insert(otherDetail));
		
		if(faceId == null) {
			newItem.setOtherDetails(quantitySwap + "");
			newItem.setShortDescription(strap1 + "," + strap2);
		}else{
			newItem.setOtherDetails(quantity);
			newItem.setShortDescription(saveTomatoImage());
			newItem.setSku(rand);
		}
		
		
		newItem.setOtherDetail(otherDetail);
		
		boolean isNewItem = true;

		/*----- ADD NEW QUANTIY TO OLD QUANTITY
		if(oldNoLogInCartItems != null)
		{
			updatedNoLogInCartItems.addAll(oldNoLogInCartItems);
			for(final CategoryItem oldItem : oldNoLogInCartItems)
			{
				if(oldItem.getId().longValue() == newItem.getId().longValue())
				{
					// add old quantity 
					newItem.setOrderQuantity(Integer.parseInt(oldItem.getOtherDetails()) + Integer.parseInt(newItem.getOtherDetails()));
					updatedNoLogInCartItems.remove(oldItem);
					updatedNoLogInCartItems.add(newItem);
					isNewItem = false;
				}
			}

		}*/
		if(oldNoLogInCartItems != null)
		{
			updatedNoLogInCartItems.addAll(oldNoLogInCartItems);
		}
		if(isNewItem)
		{
			updatedNoLogInCartItems.add(newItem);
		}

		session.put("swapCanadaNoLogInCartItems", updatedNoLogInCartItems);
		
		json.put("message", "Item successfully added to cart.");
		json.put("url", newItem.getShortDescription());
		inputStream = new StringBufferInputStream(json.toJSONString());
		
		
		
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public String addToTomatoNoLogInCart()
	{
		JSONObject json = new JSONObject();
		
		final String faceId = request.getParameter("faceId");
		final String id = request.getParameter("id");
		final String text = request.getParameter("text");
		final String textColor = request.getParameter("textColor");
		final Integer textSize = Integer.parseInt(request.getParameter("textSize"));
		final String textFont = request.getParameter("textFont");
		final String handColor = request.getParameter("handColor");
		final String description = request.getParameter("description");
		final String quantity = "1";
		final ArrayList<CategoryItem> oldNoLogInCartItems = (ArrayList<CategoryItem>) session.get("tomatoNoLogInCartItems");
		final ArrayList<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
		final CategoryItem newItem = categoryItemDelegate.find(Long.parseLong(id));
		CategoryItem faceItem = null;
		final int MIN_INT = 0;
		final int MAX_INT = 1000000;
		Random random = new Random();
		String rand = Integer.toString(random.nextInt((MAX_INT - MIN_INT) + 1) + MIN_INT);
		
		if(!StringUtils.isEmpty(faceId)) {
			faceItem = categoryItemDelegate.find(Long.parseLong(faceId));
		}
				
		OtherDetail otherDetail = new OtherDetail();
		otherDetail.setCompany(company);
		otherDetail.setFace(faceItem);
		otherDetail.setStrap(newItem);
		otherDetail.setText(text);
		otherDetail.setTextColor(textColor);
		otherDetail.setTextSize(textSize);
		otherDetail.setTextFont(textFont);
		otherDetail.setHandColor(handColor);
		otherDetail.setPathDetail(description);
		otherDetail = otherDetailDelegate.find(otherDetailDelegate.insert(otherDetail));

		newItem.setOtherDetails(quantity);
		newItem.setShortDescription(saveTomatoImage());
		newItem.setSku(rand);
		newItem.setOtherDetail(otherDetail);

		if(oldNoLogInCartItems != null)
		{
			updatedNoLogInCartItems.addAll(oldNoLogInCartItems);
		}

		updatedNoLogInCartItems.add(newItem);
		
		session.put("tomatoNoLogInCartItems", updatedNoLogInCartItems);
		
		json.put("url", newItem.getShortDescription());
		inputStream = new StringBufferInputStream(json.toJSONString());
		
		return SUCCESS;
	}	
	
	public String saveTomatoImage() {
		String uuid = UUID.randomUUID().toString();
		String screenshotUrl = "";
		
		try {
			final String isMobile = request.getParameter("isMobile");
			
	    	// remove data:image/png;base64, and then take rest sting
			String img64 = request.getParameter("fileName").substring(22);
	    	byte[] decodedBytes = DatatypeConverter.parseBase64Binary(img64 );
	    	BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(decodedBytes));    
	    	File outputfile = new File("saved.png");
	    	ImageIO.write(bfi , "png", outputfile);
	    	bfi.flush();
	    	String destinationPath = "";
	    	if(company.getId() == CompanyConstants.TOMATO){
	    		FileUtil.createDirectory(servletContext.getRealPath("companies/tomato/images/screenshots"));
	    		destinationPath = servletContext.getRealPath("companies/tomato/images/screenshots");
	    	}else if(company.getId() == CompanyConstants.SWAPCANADA){
	    		FileUtil.createDirectory(servletContext.getRealPath("companies/swapcanada/images/screenshots"));
	    		destinationPath = servletContext.getRealPath("companies/swapcanada/images/screenshots");
	    	}
			String newFileName = destinationPath + File.separator + uuid + ".png";
			File newFile = new File(newFileName);
			FileUtil.copyFile(outputfile, newFile);
			
			BufferedImage outImage = ImageIO.read(newFile);
			BufferedImage cropped;
			if(isMobile.equals("true")){
				cropped = outImage.getSubimage(38, 120, 135, 135);
			}else{
				cropped = outImage.getSubimage(72, 192, 244, 244);
			}
			
			String faceNewFileName = newFileName = destinationPath + File.separator + "face-" + uuid + ".png";
			File faceOutputfile = new File("saved.png");
			ImageIO.write(cropped , "png", faceOutputfile);
			File faceNewFile = new File(faceNewFileName);
			FileUtil.copyFile(faceOutputfile, faceNewFile);
			
			screenshotUrl = uuid + ".png";
		} catch(Exception e) {  
	          //Implement exception code    
		}			
		
		return screenshotUrl;
	}

	@SuppressWarnings("unchecked")
	public String addToCompare()
	{
		final String id = request.getParameter("id");
		final int maxAddToCompare = 4;
		session.put("maxAddToCompare", maxAddToCompare);

		final ArrayList<CategoryItem> oldAddToCompareItems = (ArrayList<CategoryItem>) session.get("mrAirConCompareItems");
		final ArrayList<CategoryItem> updatedCompareItems = new ArrayList<CategoryItem>();

		final Long itemId = Long.parseLong(id);
		final CategoryItem newItem = categoryItemDelegate.find(itemId);

		boolean isNewItem = true;

		if(oldAddToCompareItems != null)
		{
			updatedCompareItems.addAll(oldAddToCompareItems);
			for(final CategoryItem oldItem : oldAddToCompareItems)
			{
				if(oldItem.getId().longValue() == newItem.getId().longValue())
				{
					isNewItem = false;
				}
			}
		}

		if(isNewItem)
		{
			if(updatedCompareItems.size() >= maxAddToCompare)
			{
				updateNotificationMessage("EXCEEDED");
			}
			else
			{
				updatedCompareItems.add(newItem);
				updateNotificationMessage(currentCategoryItem.getName() + " successfully added in compare.");
			}
		}
		else
		{
			updateNotificationMessage(currentCategoryItem.getName() + " already added in compare.");
		}

		// if from viewing item
		if(null != request.getParameter("isFromViewItem"))
		{
			setItem_id(request.getParameter("id"));
		}

		session.put("mrAirConCompareItems", updatedCompareItems);

		return SUCCESS;
	}

	public String deleteToNoLogInCart()
	{
		noLogInCartItems.addAll((List<CategoryItem>) session.get("noLogInCartItems"));
		final String[] delete = request.getParameterValues("removeId");
		if(delete != null)
		{
			for(int i = 0; i < delete.length; i++)
			{
				for(int j = 0; j < noLogInCartItems.size(); j++)
				{
					if(company.getName().equals(CompanyConstants.AYROSOHARDWARE)) {
						if(noLogInCartItems.get(j).getId().equals(new Long(delete[i])))
							noLogInCartItems.remove(j);
					} else {
						if(noLogInCartItems.get(j).getSku().equals(delete[i].toString()))
							noLogInCartItems.remove(j);
					}
				}
			}
		}

		if(noLogInCartItems.size() == 1 && !company.getName().equalsIgnoreCase("hiprecisiononlinestore") && !company.getName().equalsIgnoreCase("ayrosohardware"))
			session.remove("noLogInCartItems");
		else if(noLogInCartItems.size() == 0)
			session.remove("noLogInCartItems");
		else
			session.put("noLogInCartItems", noLogInCartItems);

		updateNotificationMessage("Item(s) Successfully Deleted.");

		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String deleteSwapNoLogInCart(){
		
		JSONObject json = new JSONObject();
		
		noLogInCartItems.addAll((List<CategoryItem>) session.get("swapCanadaNoLogInCartItems"));
		final String removeId = request.getParameter("removeId");
		if(removeId != null){
			if(!removeId.equals("all")){
				for(int j = 0; j < noLogInCartItems.size(); j++){
					System.out.println(noLogInCartItems.get(j).getId() + " == " + removeId);
					if(noLogInCartItems.get(j).getSku().equals(removeId)){
						noLogInCartItems.remove(j);
					}
				}
			}else{
				noLogInCartItems.clear();
				
			}
		}

		if(noLogInCartItems.size() == 0){
			session.remove("swapCanadaNoLogInCartItems");
		}else{
			session.put("swapCanadaNoLogInCartItems", noLogInCartItems);
		}
		json.put("message", "Item(s) successfully removed to cart.");
		inputStream = new StringBufferInputStream(json.toJSONString());

		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String updateSwapNoLogInCart(){
		JSONObject json = new JSONObject();
		noLogInCartItems.addAll((List<CategoryItem>) session.get("swapCanadaNoLogInCartItems"));
		final String id = request.getParameter("id");
		final String quantity = request.getParameter("quantity");
		
		System.out.println("id = " + id);
		System.out.println("quantity = " + quantity);
		
		List<String> idList = Arrays.asList(id.split(","));
		List<String> quantityList = Arrays.asList(quantity.split(","));
		
		for(int j = 0; j < noLogInCartItems.size(); j++){
			for(int i = 0 ; i < idList.size() ; i++){
				if(noLogInCartItems.get(j).getSku().equals(idList.get(i))){
					noLogInCartItems.get(j).setOtherDetails(Integer.parseInt(quantityList.get(i)) + "");
				}
			}
		}
		
		if(discount != null && (Math.abs(Double.parseDouble(discount)) > 0.0))
		{
			if(discountNotFound()){
				final CategoryItem catItem = new CategoryItem();
				catItem.setId(0L);
				catItem.setSku("0");
				catItem.setName("Discount");
				catItem.setDescription("");
				catItem.setPrice(Double.parseDouble(discount));
				catItem.setOtherDetails("1");
				noLogInCartItems.add(catItem);
			}
		}
		else 
		{
			for(int j = 0; j < noLogInCartItems.size(); j++) {
				if(noLogInCartItems.get(j).getName().equals(DISCOUNT)) {
					noLogInCartItems.remove(j);
				}
			}
		}
		
		session.put("swapCanadaNoLogInCartItems", noLogInCartItems);
		
		json.put("message", "Cart successfully updated.");
		inputStream = new StringBufferInputStream(json.toJSONString());
		return SUCCESS;
	}
	
	public Boolean discountNotFound(){
		boolean notfound = true;
		for(int j = 0; j < noLogInCartItems.size(); j++) {
			if(noLogInCartItems.get(j).getName().equals(DISCOUNT)) {
				notfound = false;
			}
		}
		return notfound;
	}
	
	public String deleteTomatoNoLogInCart()
	{
		JSONObject json = new JSONObject();
		final String DISCOUNT = "Discount";

		noLogInCartItems.addAll((List<CategoryItem>) session.get("tomatoNoLogInCartItems"));
		final String delete = request.getParameter("removeId");
		if(delete != null)
		{
			for(int j = 0; j < noLogInCartItems.size(); j++)
			{
				if(noLogInCartItems.get(j).getSku().equals(delete))
					noLogInCartItems.remove(j);
			}
		}

		if(noLogInCartItems.size() == 0)
			session.remove("tomatoNoLogInCartItems");
		else if(noLogInCartItems.size() == 1 && noLogInCartItems.get(0).getName().equals(DISCOUNT))
			session.remove("tomatoNoLogInCartItems");
		else
			session.put("tomatoNoLogInCartItems", noLogInCartItems);

		updateNotificationMessage("Item(s) Successfully Deleted.");

		inputStream = new StringBufferInputStream(json.toJSONString());
		
		return SUCCESS;
	}	

	public String updateToNoLogInCart()
	{
		session.get("noLogInCartItems");
		noLogInCartItems.addAll((List<CategoryItem>) session.get("noLogInCartItems"));
		final String sku = request.getParameter("sku");
		final String quantity = request.getParameter("quantity");

		for(int i = 0; i < noLogInCartItems.size(); i++)
		{
			if(noLogInCartItems.get(i).getSku().equals(sku))
			{
				noLogInCartItems.get(i).setOtherDetails(quantity);
			}
		}

		session.put("noLogInCartItems", noLogInCartItems);

		updateNotificationMessage("Item(s) Successfully Updated.");

		return SUCCESS;
	}

	public String updateToLogInCart()
	{
		final Long cartItemID = Long.valueOf(request.getParameter("cartItemID"));
		final Integer quantity = Integer.valueOf(request.getParameter("quantity"));
		final ShoppingCartItem shoppingCartItem = cartItemDelegate.find(cartItemID);

		if(shoppingCartItem != null)
		{
			shoppingCartItem.setQuantity(quantity);
			cartItemDelegate.update(shoppingCartItem);
			session.remove("shoppingCartCount");
		}

		updateNotificationMessage("Item(s) Successfully Updated.");

		return SUCCESS;
	}

	/** DISCOUNT **/
	public void setDiscount(String discount)
	{
		this.discount = discount;
	}

	/** DISCOUNT **/
	public String getDiscount()
	{
		if(discount == null || discount.length() == 0)
		{
			return "0";
		}
		return discount;
	}

	public String getP_totalAmount()
	{
		return p_totalAmount;
	}

	public void setP_totalAmount(String amount)
	{
		p_totalAmount = amount;
	}

	public void setGcIds(String gcIds)
	{
		this.gcIds = gcIds;
	}

	public String getGcIds()
	{
		return gcIds;
	}

	public String getView()
	{
		return view;
	}

	public void setView(String view)
	{
		this.view = view;
	}

	public void setItemID(Long itemID) {
		this.itemID = itemID;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(String shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Long getPageID() {
		return pageID;
	}

	public void setPageID(Long pageID) {
		this.pageID = pageID;
	}
	
	public String putItemToGameplacePromoShoppingCart() {
		for(int ctr = 0;ctr < GameplacePromoShoppingCartItems.size();ctr++) {
			GameplacePromoShoppingCartItems.remove(ctr);
		}
		
		CategoryItem item = categoryItemDelegate.find(itemID);
		item.setPrice(item.getPrice());
		item.setOtherDetails(this.getQuantity());
		item.setSku("1");
		GameplacePromoShoppingCartItems.add(item);
		
		String shippingLocation = "";
		
		if(optionSelect.equalsIgnoreCase("delivery")) {
			CategoryItem shippingCost = new CategoryItem();
			shippingCost.setId(0L);
			shippingCost.setSku("2");
			shippingCost.setName("Shipping Cost");
			shippingCost.setDescription("");
			String price = shippingFee.split(":")[0];
			shippingLocation = shippingFee.split(":")[1];
			shippingCost.setPrice(Double.parseDouble(price));
			shippingCost.setOtherDetails("1");
			GameplacePromoShoppingCartItems.add(shippingCost);
		} else {
			shippingFee = "0";
		}
		
		session.put("GameplacePromoShoppingCartItems", GameplacePromoShoppingCartItems);
		session.put("optionSelect", optionSelect);
		session.put("pickupSelect", pickupSelect);
		session.put("shippingLocation", shippingLocation);
		session.put("tempMember", tempMember);
		
		return SUCCESS;
	}
	
	public String addToColumnbusCart() {
		Long item_selected_id = Long.parseLong(request.getParameter("item_id"));
		accessKey = request.getParameter("access_key");
		item_count = request.getParameter("item_count");
		Integer item_quantity = Integer.parseInt(request.getParameter("item_quantity"));
		Integer value = 0;
		boolean found = false;
		
		item_selected = categoryItemDelegate.find(item_selected_id);
		
		List<CategoryItem> columnbusCartItems = (List<CategoryItem>) session.get("columnbusCartItems");
		
		if(columnbusCartItems == null) {
			columnbusCartItems = new ArrayList<CategoryItem>();
		}
		
		for(CategoryItem catItem: columnbusCartItems) {
			if(catItem.getName().equalsIgnoreCase(item_selected.getName())) {
				value = item_quantity - catItem.getOrderQuantity();
					if(value >= 0) {
						catItem.setOrderQuantity(catItem.getOrderQuantity() + value);
					} else {
						value = Math.abs(value);
						if(catItem.getOrderQuantity() - value <= 0) {
							columnbusCartItems.remove(catItem);
						} else {
							catItem.setOrderQuantity(catItem.getOrderQuantity() - value);
						}
					}
				
				catItem.setImages(catItem.getImages());
				found = true;
				break;
			}
		}
		
		if(!found) {
			item_selected.setOrderQuantity(item_quantity);
			item_selected.setSearchTags(item_count);
			columnbusCartItems.add(item_selected);
		}
		
		if(columnbusCartItems.size() == 0) {
			columnbusCartItems = null;
		}
		
		session.put("columnbusCartItems", columnbusCartItems);
		
		return SUCCESS;
	}
	
	public String removeToColumnbusCart() {
		item_selected_id = Long.parseLong(request.getParameter("item_id"));
		accessKey = request.getParameter("access_key");
		item_count = request.getParameter("item_count");

		item_selected = categoryItemDelegate.find(item_selected_id);
		
		List<CategoryItem> columnbusCartItems = (List<CategoryItem>) session.get("columnbusCartItems");
		
		if(columnbusCartItems == null) {
			columnbusCartItems = new ArrayList<CategoryItem>();
		}
		
		for(CategoryItem catItem: columnbusCartItems) {
			if(catItem.getName().equalsIgnoreCase(item_selected.getName())) {
				columnbusCartItems.remove(catItem);
				break;
			}
		}
		
		if(columnbusCartItems.size() == 0) {
			columnbusCartItems = null;
		}

		session.put("columnbusCartItems", columnbusCartItems);
		
		return SUCCESS;
	}
	
	public String doPaymentConfirmation() {

		if(session == null)
		{
			session = (Map) request.getSession(true);
		}
		
		System.out.println("\n\n\n" + request.getParameter("optionSelect") + "This is Daniel Testing this app \n\n\n");

		String name = "", email = "", address = "", province = "";
		String city = "", zipcode = "", phonenumber = "";
		
		final List<CategoryItem> promoCartItems = (List<CategoryItem>) session.get("GameplacePromoShoppingCartItems");
		String shippingCostAmount = request.getParameter("shippingCostAmount");
		
		name = request.getParameter("gpfname") + " " + request.getParameter("gplname");
		email = request.getParameter("gpemail");
		address = request.getParameter("gpaddress");
		province = request.getParameter("gpprovince");
		city = request.getParameter("gpcity");
		zipcode = request.getParameter("gpzcode");
		phonenumber = request.getParameter("gpnumber");

		tempMember.setFirstname(name);
		tempMember.setEmail(email);
		tempMember.setPassword("");
		tempMember.setUsername(email);
		tempMember.setCompany(company);
		tempMember.setMobile(phonenumber);
		tempMember.setReg_companyAddress(address);
		tempMember.setZipcode(zipcode);
		tempMember.setNewsletter(true);
		tempMember.setCity(city);
		tempMember.setAddress1(address);
		tempMember.setProvince(province);

		request.setAttribute("modeOfPayment", "Pay Via Paypal");

		if(request.getParameter("paymentOption").equalsIgnoreCase("paypal"))
		{
			final Member m = memberDelegate.findAccount(email, company);

			if(m != null)
			{
				m.setFirstname(name);
				m.setUsername(email);
				m.setEmail(email);
				m.setReg_companyAddress(province + " : " + address);
				m.setMobile(phonenumber);
				m.setValue(shippingCostAmount);
				m.setZipcode(zipcode);
				m.setCity(city);
				m.setAddress1(address);
				m.setProvince(province);
				memberDelegate.update(m);
				session.put("memId", m.getId());
			}
			else
			{
				final long memId = memberDelegate.insert(tempMember);
				session.put("memId", memId);
			}
			
			session.put("shippingLocation", shippingLocation);
			session.put("optionSelect", optionSelect);
			session.put("pickupSelect", pickupSelect);
		
			return "PAYPAL_SUCCESS";
		
		} else {
			
			
			Long orderId = saveNoLogInCartOrderBank(promoCartItems);
			
			session.put("name", name);
			session.put("orderId", orderId);
			session.put("phonenumber", phonenumber);
			session.put("itemquantity", promoCartItems.get(0).getOtherDetails());
			pickupSelect = pickupSelect.replace("<div>","");
			pickupSelect = pickupSelect.replace("</div>","");
			pickupSelect = pickupSelect.trim();
			pickupSelect = pickupSelect.replace("�", " ");
			session.put("pickupSelect", pickupSelect);
			this.setItem_id(Long.toString(promoCartItems.get(0).getId()));
			
			CategoryItem cur_item = categoryItemDelegate.find(promoCartItems.get(0).getId());
			
			if(cur_item == null) {
				logger.error("CUR ITEM IS NULL");
			}
			session.put("itemImage", cur_item.getImages().get(0).getOriginal());
			session.put("itemImageTitle", cur_item.getImages().get(0).getTitle());
			
			//send email to user and company when bank is set as mode of payment
			try
			{
				request.setAttribute("modeOfPayment", "Pay Via Bank");
				
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
				message += 			"Items will be prepared at <span><b>" + location + "</b></span> for " + optionSelect + " once payment has been confirmed. ";
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
				
				String filepath = this.saveImagePath(tempMember, request, "paymentAttachment");

				EmailUtil.connect("smtp.gmail.com", 587, EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);

				EmailUtil.sendWithHTMLFormat("system@ivant.com", email,"Order Confirmation",message ,null);
				
			}
			catch(final Exception e)
			{
				e.printStackTrace();
				return Action.ERROR;
			}
			return "BANK_SUCCESS";
		}
	}
	
	public Long saveNoLogInCartOrderBank(List<CategoryItem> catItems)
	{
		Double totalPrice = 0.0;
		String name = "", email = "", address = "", province = "";
		String city = "", zipcode = "", phonenumber = "";
		
		name = request.getParameter("gpfname") + " " + request.getParameter("gplname");
		email = request.getParameter("gpemail");
		address = request.getParameter("gpaddress");
		province = request.getParameter("gpprovince");
		city = request.getParameter("gpcity");
		zipcode = request.getParameter("gpzcode");
		phonenumber = request.getParameter("gpnumber");
		
		Member tempMember = new Member();
		tempMember.setFullName(name);
		tempMember.setUsername(name);
		tempMember.setPassword("");
		tempMember.setEmail(email);
		tempMember.setAddress1(address);
		tempMember.setProvince(province);
		tempMember.setCity(city);
		tempMember.setZipcode(zipcode);
		tempMember.setMobile(phonenumber);
		tempMember.setNewsletter(true);
		
		Long memberID = memberDelegate.insert(tempMember);
		tempMember.setId(memberID);

		final CartOrder cartOrder = new CartOrder();
		cartOrder.setAddress1(address);
		cartOrder.setStatus(OrderStatus.IN_PROCESS);
		cartOrder.setCompany(company);
		cartOrder.setName(name);
		cartOrder.setEmailAddress(email);
		cartOrder.setPhoneNumber(phonenumber);
		cartOrder.setPaymentStatus(PaymentStatus.PENDING);
		cartOrder.setPaymentType(PaymentType.BANK);

		cartOrder.setMember(tempMember);

		final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
		if(catItems != null)
		{
			for(final CategoryItem cI : catItems)
			{
				if(cI.getName().indexOf("Shipping Cost") != -1)
				{
					cartOrder.setTotalShippingPrice2(cI.getPrice());
				}
				// noLogInCartItems.add(cI);
				final CartOrderItem cartOrderItem = new CartOrderItem();
				final ItemDetail itemDetail = new ItemDetail();
				itemDetail.setName(cI.getName());
				itemDetail.setDescription(cI.getShortDescription());
				itemDetail.setPrice(cI.getPrice());
				cartOrderItem.setItemDetail(itemDetail);
				cartOrderItem.setQuantity(Integer.parseInt(cI.getOtherDetails()));
				cartOrderItem.setStatus(OrderStatus.NEW.toString());
				cartOrderItem.setCompany(company);
				cartOrderItem.setOrder(cartOrder);
				cartOrderItems.add(cartOrderItem);
				
				totalPrice = totalPrice + (cI.getPrice() * cartOrderItem.getQuantity());
			}
		}
		cartOrder.setTotalPriceOkFormatted(Double.toString(totalPrice));
		cartOrder.setTotalPrice(totalPrice);
		cartOrder.setItems(cartOrderItems);
		Long orderId = cartOrderDelegate.insert(cartOrder);
		cartOrderItemDelegate.batchInsert(cartOrderItems);
		
		return orderId;
	}
	
	private String saveImagePath(Member member, HttpServletRequest request, String inputName)
	{
		final String GAMEPLACE_ROOT_FOLDER = File.separator + "companies" + File.separator + "gameplace";
		final String ATTACHMENT_FOLDER = "attachments";
		String path = "";
		String photoFileName = null;
		try
		{
			final MultiPartRequestWrapper mprw = (MultiPartRequestWrapper) request;
			final File[] file = mprw.getFiles(inputName);
			final String[] filename = mprw.getFileNames(inputName);
			if((file != null && file.length > 0) && (filename != null && filename.length > 0))
			{
				path = 
						ServletActionContext.getServletContext().getRealPath(GAMEPLACE_ROOT_FOLDER) 
						+ File.separator
						+ ATTACHMENT_FOLDER
						+ File.separator;
				final File uploadedFileDestination = new File(path);
				
				if(!uploadedFileDestination.exists())
				{
					uploadedFileDestination.mkdirs();
				}
				
				photoFileName = (member.getUsername() + "_" + member.getEmail() + "_" + filename[0]).replaceAll("\\s+", "_");
				final File destination = new File(path + photoFileName);
				
				FileUtil.copyFile(file[0], destination);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return path + photoFileName;
	}

	public String getPickupSelect() {
		return pickupSelect;
	}

	public void setPickupSelect(String pickupSelect) {
		this.pickupSelect = pickupSelect;
	}

	public String getOptionSelect() {
		return optionSelect;
	}

	public void setOptionSelect(String optionSelect) {
		this.optionSelect = optionSelect;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getItem_count() {
		return item_count;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setItem_count(String item_count) {
		this.item_count = item_count;
	}

	public CategoryItem getItem_selected() {
		return item_selected;
	}

	public void setItem_selected(CategoryItem item_selected) {
		this.item_selected = item_selected;
	}

	public Long getItem_selected_id() {
		return item_selected_id;
	}

	public void setItem_selected_id(Long item_selected_id) {
		this.item_selected_id = item_selected_id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String getShippingLocation() {
		return shippingLocation;
	}

	public void setShippingLocation(String shippingLocation) {
		this.shippingLocation = shippingLocation;
	}

	public Integer getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage)
	{
		this.currentPage = currentPage;
	}
	
	@SuppressWarnings("unchecked")
	public String savecart() {return null;}
	
	public String countCartWithFormat(Company company, ShoppingCart shoppingCart) {
		Integer totalCount = 0;
		DecimalFormat myFormatter = new DecimalFormat("###,###");
		totalCount = Integer.parseInt(String.valueOf(shoppingCartItemDelegate.findCartCountByOrder(company, shoppingCart)));
		return myFormatter.format(totalCount);
	}
	
	public String countCartWithFormat(Company company, ShoppingCart shoppingCart, Boolean countByQuantity) {
		if(countByQuantity){
			Integer totalCount = 0;
			DecimalFormat myFormatter = new DecimalFormat("###,###");
			totalCount = Integer.parseInt(String.valueOf(shoppingCartItemDelegate.findCartCountByOrder(company, shoppingCart, countByQuantity)));
			return myFormatter.format(totalCount);
		}else return countCartWithFormat(company, shoppingCart);
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

	
