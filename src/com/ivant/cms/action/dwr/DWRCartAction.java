package com.ivant.cms.action.dwr;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.ivant.cart.action.Paypal;
import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.action.admin.dwr.AbstractDWRAction;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.ItemDetail;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OtherDetail;
import com.ivant.cms.entity.PreOrderItem;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.ShoppingCart;
import com.ivant.cms.entity.ShoppingCartItem;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.InventoryUtil;

public class DWRCartAction
		extends AbstractDWRAction
{
	
	private WebContext ctx = WebContextFactory.get();
	private HttpServletRequest req = ctx.getHttpServletRequest();
	private HttpSession session = req.getSession();
	
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
	
	/** Cents decimal format */
	private static final NumberFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	
	private static final Logger logger = Logger.getLogger(DWRCartAction.class);
	
	public String deleteCartItems(String idList) throws Exception
	{
		StringTokenizer stringTokenizer = new StringTokenizer(idList, "[,]");
		boolean hasChecked = false;
		try
		{
			
			// traverse id list
			while(stringTokenizer.hasMoreElements())
			{
				final Long currentIndex = Long.parseLong(stringTokenizer.nextElement().toString());
				
				final ShoppingCartItem item = shoppingCartItemDelegate.find(currentIndex);
				
				if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
				{
					//GurkkaMemberUtil.removeDiscountItem(company, item.getShoppingCart().getMember(), item);
				}
				
				shoppingCartItemDelegate.delete(item);
				
				hasChecked = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Item(s) not Deleted";
		}
		
		if(!hasChecked)
			return "No Selected Item.";
		else
			return "Item(s) Successfully Deleted.";
	}
	
	public String deleteCartItem(String cartItemId) throws Exception
	{
		boolean hasChecked = false;
		try
		{
			
			Long currentIndex = 0l;
			currentIndex = Long.parseLong(cartItemId);
			shoppingCartItemDelegate.delete(shoppingCartItemDelegate.find(currentIndex));
			hasChecked = true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Item(s) not Deleted";
		}
		
		if(!hasChecked)
			return "No Selected Item.";
		else
			return "Item(s) Successfully Deleted.";
	}
	
	public String updateCartItem(String cartItemId, String quantity)
	{
		Double totalPrice = 0.0D;
		try
		{
			// update cart item quantity value
			ShoppingCartItem cartItem = shoppingCartItemDelegate.find(Long.parseLong(cartItemId));
			Integer newQuantity = Integer.parseInt(quantity);
			if(null != newQuantity & newQuantity != cartItem.getQuantity())
			{
				cartItem.setQuantity(newQuantity);
				cartItem.setUpdatedOn(new Date());
				
				// update cart item
				shoppingCartItemDelegate.update(cartItem);
			}
			
			// get total price
			totalPrice = shoppingCartDelegate.find(cartItem.getShoppingCart().getId()).getTotalPrice();
			// System.out.println("TOTAL PRICE IN CART "+totalPrice);
		}
		catch(Exception e)
		{
			// e.printStackTrace();
		}
		
		// return total price
		return DEFAULT_DECIMAL_FORMAT.format(totalPrice);
	}
	
	public String updateCartItems(String[] cartItemIdArray, String[] quantityArray)
	{
		Double totalPrice = 0.0D;
		for(int i = 0; i < cartItemIdArray.length; i++)
		{
			String cartItemId = cartItemIdArray[i];
			String quantity = quantityArray[i];
			
			try
			{
				// update cart item quantity value
				ShoppingCartItem cartItem = shoppingCartItemDelegate.find(Long.parseLong(cartItemId));
				Integer newQuantity = Integer.parseInt(quantity);
				if(null != newQuantity & newQuantity != cartItem.getQuantity())
				{
					cartItem.setQuantity(newQuantity);
					cartItem.setUpdatedOn(new Date());
					
					// update cart item
					shoppingCartItemDelegate.update(cartItem);
				}
				
				// get total price
				totalPrice += shoppingCartDelegate.find(cartItem.getShoppingCart().getId()).getTotalPrice();
				// System.out.println("TOTAL PRICE IN CART "+totalPrice);
			}
			catch(Exception e)
			{
				// e.printStackTrace();
			}
		}
		// System.out.println("MEMO "+totalPrice);
		// return total price
		return DEFAULT_DECIMAL_FORMAT.format(totalPrice);
	}
	
	public String updateNoLogInCartItems(String[] cartItemIdArray, String[] quantityArray)
	{
		Double totalPrice = 0.0D;
		ArrayList<CategoryItem> noLogInCartItems = new ArrayList<CategoryItem>();
		noLogInCartItems.addAll((List<CategoryItem>) req.getSession().getAttribute("noLogInCartItems"));
		
		for(int i = 0; i < cartItemIdArray.length; i++)
		{
			String cartItemId = cartItemIdArray[i];
			String quantity = quantityArray[i];
			
			try
			{
				Integer newQuantity = Integer.parseInt(quantity);
				if(null != newQuantity)
				{
					noLogInCartItems.get(i).setOtherDetails(quantity);
				}
				
			}
			catch(Exception e)
			{
				// e.printStackTrace();
			}
		}
		req.getSession().removeAttribute("noLogInCartItems");
		req.getSession().setAttribute("noLogInCartItems", noLogInCartItems);
		// return total price
		return DEFAULT_DECIMAL_FORMAT.format(totalPrice);
	}
	
	public String updateHiPreOnlineStoreCart(String cartItemId, String quantity)
	{
		Double totalPrice = 0.0D;
		ArrayList<CategoryItem> noLogInCartItems = new ArrayList<CategoryItem>();
		noLogInCartItems.addAll((List<CategoryItem>) req.getSession().getAttribute("noLogInCartItems"));
		
		for(CategoryItem categoryItem : noLogInCartItems)
		{
			if(categoryItem.getId().toString().equals(cartItemId)) {
				Integer newQuantity = Integer.parseInt(quantity);
				if(null != newQuantity)
				{
					categoryItem.setOtherDetails(quantity);
				}
			}
		}
		req.getSession().setAttribute("noLogInCartItems", noLogInCartItems);
		// return total price
		return DEFAULT_DECIMAL_FORMAT.format(totalPrice);
	}
	
	
	
	public String deletePreOrderItems(String idList) throws Exception
	{
		StringTokenizer stringTokenizer = new StringTokenizer(idList, "[,]");
		boolean hasChecked = false;
		try
		{
			
			Long currentIndex = 0l;
			// traverse id list
			while(stringTokenizer.hasMoreElements())
			{
				
				currentIndex = Long.parseLong(stringTokenizer.nextElement().toString());
				// System.out.println("currentIndex : " + currentIndex);
				preOrderItemDelegate.delete(preOrderItemDelegate.find(currentIndex));
				hasChecked = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Item(s) not Deleted";
		}
		
		if(!hasChecked)
			return "No Selected Item.";
		else
			return "Item(s) Successfully Deleted.";
	}
	
	public String updatePreOrderItem(String preOrderItemId, String quantity)
	{
		Double totalPrice = 0.0D;
		try
		{
			// update preoredr item quantity value
			PreOrderItem preOrderItem = preOrderItemDelegate.find(Long.parseLong(preOrderItemId));
			Integer newQuantity = Integer.parseInt(quantity);
			if(null != newQuantity & newQuantity != preOrderItem.getQuantity())
			{
				preOrderItem.setQuantity(newQuantity);
				preOrderItem.setUpdatedOn(new Date());
				
				// update cart item
				preOrderItemDelegate.update(preOrderItem);
			}
			
			// get total price
			// totalPrice = preOrderDelegate.find(preOrderItem.getShoppingCart().getId()).getTotalPrice();
			// System.out.println("TOTAL PRICE IN CART "+totalPrice);
		}
		catch(Exception e)
		{
			// e.printStackTrace();
		}
		
		// return total price
		return DEFAULT_DECIMAL_FORMAT.format(totalPrice);
	}
	
	public String updateInventory(String shoppingCartId)
	{
		
		ShoppingCart shoppingCart = shoppingCartDelegate.find(new Long(shoppingCartId));
		
		// shoppingCart.getItems().get(0).getItemDetail();
		
		// System.out.println("SHOPPING CART  = "+(shoppingCart == null));
		
		try
		{
			logger.debug("Current shopping cart : " + shoppingCart);
			logger.debug("Current shopping cart items : " + shoppingCart.getItems());
			logger.debug("Current shopping cart total : " + shoppingCart.getTotalPrice());
		}
		catch(Exception e)
		{
			logger.debug("Shopping cart is currently empty.");
		}
		
		try
		{
			// ObjectList<ShoppingCartItem> tempCartItems = shoppingCartItemDelegate.findAll(shoppingCart);
			// List<ShoppingCartItem> cartItemList = tempCartItems.getList();
			
			if(shoppingCart.getItems().size() > 0)
			{
				
				Boolean inventoryWasUpdated = InventoryUtil.updateInventory(company, shoppingCart, categoryItemDelegate, Boolean.TRUE);
				// System.out.println("INVENTORY WAS SUCCESSFULLY UPDATED   "+inventoryWasUpdated +"    "+shoppingCart.getItems().size());
				
			}
			
		}
		catch(Exception e)
		{
			logger.debug("No cart items retrieved.");
		}
		
		return "SUCCESS";
		
	}
	
	public String[] addToShoppingCart(Long itemId, Integer quantity)
	{
		String message = "";
		Integer totalCartItem = 0;
		ArrayList<CategoryItem> oldNoLogInCartItems = (ArrayList<CategoryItem>) session.getAttribute("mrAirConNoLogInCartItems");
		ArrayList<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
		
		CategoryItem newItem = categoryItemDelegate.find(itemId);
		newItem.setOrderQuantity(quantity);
		
		boolean isNewItem = true;
		
		if(oldNoLogInCartItems != null)
		{
			updatedNoLogInCartItems.addAll(oldNoLogInCartItems);
			for(CategoryItem oldItem : oldNoLogInCartItems)
			{
				/*
				 * boolean isOldItemCustomOnly = false;
				 * boolean isNewItemCustomOnly = false;
				 */
				Integer oldOrderQuantity = oldItem.getOrderQuantity();
				
				oldItem = categoryItemDelegate.find(oldItem.getId());
				oldItem.setOrderQuantity(oldOrderQuantity);
				
				/*
				 * if (oldItem.getCategoryItemOtherFieldMap().get("Custom Only") != null) {
				 * isOldItemCustomOnly = oldItem
				 * .getCategoryItemOtherFieldMap().get("Custom Only")
				 * .getContent().equalsIgnoreCase("yes") ? true
				 * : false;
				 * }
				 * if (newItem.getCategoryItemOtherFieldMap().get("Custom Only") != null) {
				 * isNewItemCustomOnly = newItem
				 * .getCategoryItemOtherFieldMap().get("Custom Only")
				 * .getContent().equalsIgnoreCase("yes") ? true
				 * : false;
				 * }
				 */
				if(oldItem.getId().longValue() == newItem.getId().longValue())
				{
					// add old quantity if oldItem.getId()==newItem.getId()
					newItem.setOrderQuantity(oldOrderQuantity + quantity);
					
					updatedNoLogInCartItems.remove(oldItem);
					updatedNoLogInCartItems.add(newItem);
					message += newItem.getName() + " successfully updated in cart.";
					isNewItem = false;
				}
				
				/*
				 * if(isOldItemCustomOnly == isNewItemCustomOnly){
				 * if(oldItem.getId().longValue()==newItem.getId().longValue()){
				 * //add old quantity if oldItem.getId()==newItem.getId()
				 * newItem.setOrderQuantity(oldItem.getOrderQuantity()+newItem.getOrderQuantity());
				 * updatedNoLogInCartItems.remove(oldItem);
				 * updatedNoLogInCartItems.add(newItem);
				 * message+=newItem.getName() + " successfully updated in cart.";
				 * isNewItem = false;
				 * }
				 * }else{
				 * message = isNewItemCustomOnly?"Can't mix <b style='color:red'>"+ newItem.getName() +"</b> with basic only installation charge.":
				 * "Can't mix <b style='color:red'>"+ newItem.getName() +"</b> with custom only installation charge.";
				 * isNewItem = false;
				 * break;
				 * }
				 */
				
			}
			
		}
		
		if(isNewItem)
		{
			updatedNoLogInCartItems.add(newItem);
			message += newItem.getName() + " successfully added to cart.";
		}
		for(CategoryItem item : updatedNoLogInCartItems)
		{
			totalCartItem += item.getOrderQuantity();
		}
		
		String[] data = new String[2];
		data[0] = message;
		data[1] = totalCartItem + "";
		
		session.setAttribute("mrAirConNoLogInCartItems", updatedNoLogInCartItems);
		return data;
	}
	
	public String[] addToCompare(Long itemId)
	{
		String message = "";
		int maxAddToCompare = 4;
		session.setAttribute("maxAddToCompare", maxAddToCompare);
		
		ArrayList<CategoryItem> oldAddToCompareItems = (ArrayList<CategoryItem>) session.getAttribute("mrAirConCompareItems");
		ArrayList<CategoryItem> updatedCompareItems = new ArrayList<CategoryItem>();
		
		CategoryItem newItem = categoryItemDelegate.find(itemId);
		
		boolean isNewItem = true;
		
		if(oldAddToCompareItems != null)
		{
			updatedCompareItems.addAll(oldAddToCompareItems);
			for(CategoryItem oldItem : oldAddToCompareItems)
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
				message += "Only " + maxAddToCompare + " items are allowed in add to compare!";
			}
			else
			{
				updatedCompareItems.add(newItem);
				message += newItem.getName() + " successfully added in compare.";
			}
		}
		else
		{
			message += newItem.getName() + " already added in compare.";
		}
		
		session.setAttribute("mrAirConCompareItems", updatedCompareItems);
		
		String[] data = new String[2];
		data[0] = message;
		data[1] = updatedCompareItems.size() + "";
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public String[] addToIIEEShoppingCart(Long itemId){
		
		String message = "";
		Integer totalCartItem = 0;
		ArrayList<CategoryItem> oldNoLogInCartItems = (ArrayList<CategoryItem>) session.getAttribute(CompanyConstants.IIEE_ORG_PHILS_CART);
		ArrayList<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
		
		CategoryItem newItem = categoryItemDelegate.find(itemId);
		newItem.setOrderQuantity(1);
		
		boolean isNewItem = true;
		
		if(oldNoLogInCartItems != null)
		{
			updatedNoLogInCartItems.addAll(oldNoLogInCartItems);
			for(CategoryItem oldItem : oldNoLogInCartItems)
			{
				Integer oldOrderQuantity = oldItem.getOrderQuantity();
				
				oldItem = categoryItemDelegate.find(oldItem.getId());
				oldItem.setOrderQuantity(oldOrderQuantity);
				
				if(oldItem.getId().longValue() == newItem.getId().longValue())
				{
					// add old quantity if oldItem.getId()==newItem.getId()
					newItem.setOrderQuantity(oldOrderQuantity + 1);
					
					updatedNoLogInCartItems.remove(oldItem);
					updatedNoLogInCartItems.add(newItem);
					message += newItem.getName() + " successfully updated in cart.";
					isNewItem = false;
				}
			}
			
		}
		
		if(isNewItem)
		{
			updatedNoLogInCartItems.add(newItem);
			message += newItem.getName() + " successfully added to cart.";
		}
		for(CategoryItem item : updatedNoLogInCartItems)
		{
			totalCartItem += item.getOrderQuantity();
		}
		
		String[] data = new String[2];
		data[0] = message;
		data[1] = totalCartItem + "";
		
		session.setAttribute(CompanyConstants.IIEE_ORG_PHILS_CART, updatedNoLogInCartItems);
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public String deleteCartItemsFromSunnySunday(String cartItemName) throws Exception
	{
		boolean hasChecked = false;
		try
		{
			final List<CategoryItem> catItems = (List<CategoryItem>) req.getSession().getAttribute("noLogInCartItems");
			for(int index=0; index<catItems.size(); index++) {
				String itemName = catItems.get(index).getName().replaceAll(" ", "") + "-" + catItems.get(index).getDescription().replaceAll(" ", "");
				if(itemName.equalsIgnoreCase(cartItemName)) {
					catItems.remove(index);
					break;
				}
			}
			
			boolean hasProduct = false;
			for(CategoryItem catItem : catItems) {
				if((!catItem.getName().equalsIgnoreCase("Shipping Cost")) 
						&& (!catItem.getName().equalsIgnoreCase("Less 15% Discount")) 
						&& (catItem.getName().indexOf("Discount") == -1)&&  (catItem.getName().indexOf("Windows") == -1)) {
					hasProduct = true;
					break;
				}
			}
			if(hasProduct)
				req.getSession().setAttribute("noLogInCartItems" , catItems);
			else
				req.getSession().removeAttribute("noLogInCartItems");
				
			hasChecked = true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Item(s) not Deleted";
		}
		
		if(!hasChecked)
			return "No Selected Item.";
		else
			return "Item(s) Successfully Deleted.";
	}
	
	@SuppressWarnings("unchecked")
	public String updateCartItemsFromSunnySunday(String[] cartItemNameArray, String[] quantityArray)
	{
		Double totalPrice = 0.0D;
		final List<CategoryItem> catItems = (List<CategoryItem>) req.getSession().getAttribute("noLogInCartItems");
		
		for(int i = 0; i < cartItemNameArray.length; i++)
		{
			String cartItemName = cartItemNameArray[i];
			String quantity = quantityArray[i];
			for(CategoryItem catItem : catItems) {
				String itemName = catItem.getName().replaceAll(" ", "") + "-" + catItem.getDescription().replaceAll(" ", "");
				if(itemName.equalsIgnoreCase(cartItemName)) {
					Integer newQuantity = Integer.parseInt(quantity);
					if(null != newQuantity)
					{
						catItem.setOtherDetails(quantity);
					}
					break;
				}
			}
		}
		req.getSession().setAttribute("noLogInCartItems", catItems);
		// return total price
		return DEFAULT_DECIMAL_FORMAT.format(totalPrice);
	}
	
	public String[] addToLifeCart(Long itemId, Integer quantity, String itemName, Double itemPrice, String type, String code, String memberId,
			String fName, String lName, String mobile, String email, String address, boolean isMember, boolean isFreeEvent, String eventTime, String eventPlace) throws Exception {
		
		String[] data = new String[4];
		
		try {
			
			Member member = new Member();
			
			if(isMember)
				member = memberDelegate.find(Long.valueOf(memberId));//if login
			
			String message = "";
			Integer totalCartItem = 0;
			Double totalPrice = 0.00;
			
			List<CategoryItem> oldNoLogInCartItems = (List<CategoryItem>) session.getAttribute("noLogInCartItems");
			List<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
			
			
			int itemSessionId = 1;
			if(oldNoLogInCartItems != null)
			{
				for(final CategoryItem cI : oldNoLogInCartItems) {
					cI.setSku("" + itemSessionId);
					itemSessionId++;
				}
			}	
				
			boolean isNewItem = true;
			boolean isClass = true;
			
			CategoryItem newItem = new CategoryItem();
			if(type.equals("item")) {
				newItem = categoryItemDelegate.find(itemId);
				isClass = false;
			} else if(type.equals("class")) {
				newItem.setName(itemName);
				newItem.setPrice(itemPrice);
				newItem.setSku("" + itemSessionId);
				newItem.setOtherDetails(""+quantity);
			} else {
				newItem.setId(Long.valueOf(itemSessionId));
				newItem.setName(itemName);
				newItem.setPrice(itemPrice);
				totalPrice += totalPrice + newItem.getPrice();
				if(code != null && !code.equals("")) {
					PromoCode promoCode = promoCodeDelegate.findByCode(company, code);
					Double promoDiscount = promoCode.getDiscount();
					Double discountedPrice = totalPrice*(promoDiscount/100);
					totalPrice = totalPrice-discountedPrice;
					newItem.setPrice(totalPrice);
				} else {
					code = "n/a";
				}
				newItem.setSku("" + itemSessionId);
				newItem.setOtherDetails(""+quantity);
				//totalPrice += totalPrice + itemPrice;
			}
			
			if(oldNoLogInCartItems != null)
			{
				updatedNoLogInCartItems.addAll(oldNoLogInCartItems);
				if(type.equals("item")) {
					for(CategoryItem oldItem : oldNoLogInCartItems) {
						
					}
				} else if(type.equals("class")) {
					
				} else {
					for(CategoryItem oldItem : oldNoLogInCartItems) {
						if(oldItem.getName().equals(newItem.getName())) {
							newItem.setOtherDetails(""+Integer.valueOf(oldItem.getOtherDetails())+quantity);
							
							updatedNoLogInCartItems.remove(oldItem);
							updatedNoLogInCartItems.add(newItem);
							message += newItem.getName() + " successfully updated in cart.";
							isNewItem = false;
						}
					}
				}
			}
			
			
			if(isNewItem)
			{
				updatedNoLogInCartItems.add(newItem);
				message += newItem.getName() + " successfully added to cart.";
				
			}
			
			final CartOrder cartOrder = new CartOrder();
			cartOrder.setStatus(OrderStatus.PENDING);
			cartOrder.setCompany(company);
			if(isMember) {
				cartOrder.setName(member.getFirstname() + " " +member.getLastname());
				cartOrder.setEmailAddress(member.getEmail());
				cartOrder.setPhoneNumber(member.getMobile());
				cartOrder.setAddress1(member.getAddress1());
				cartOrder.setInfo2(member.getInfo2()); // mindbodyonline ID /// client ID 
				cartOrder.setMember(member);
			} else {
				cartOrder.setName(fName + " " +lName);
				cartOrder.setEmailAddress(email);
				cartOrder.setPhoneNumber(mobile);
				cartOrder.setAddress1(address);
			}
			
			if(type.equalsIgnoreCase("Events")) {
				cartOrder.setInfo5("Event Name=="+itemName+"&&Event Time=="+eventTime+"&&Event Place=="+eventPlace);
			}
			
			final List<CategoryItem> catItems = updatedNoLogInCartItems;
			
			final List<CartOrderItem> cartOrderItems = new ArrayList<CartOrderItem>();
			
			for(final CategoryItem cI : catItems) {
				final CartOrderItem cartOrderItem = new CartOrderItem();
				final ItemDetail itemDetail = new ItemDetail();
	
				//itemDetail.setDescription(cI.getDescription());
				itemDetail.setName(cI.getName());
				itemDetail.setPrice(cI.getPrice());
				//totalPrice += totalPrice + cI.getPrice();
				itemDetail.setPrice(totalPrice);
				cartOrderItem.setItemDetail(itemDetail);
				cartOrderItem.setQuantity(Integer.parseInt(cI.getOtherDetails()));
				//cartOrderItem.setStatus(OrderStatus.PENDING.toString());
				cartOrderItem.setCompany(company);
				cartOrderItem.setOrder(cartOrder);
				cartOrderItems.add(cartOrderItem);
			}
			
			List<CartOrder> cartOrders = cartOrderDelegate.findAllByCompany(company);
			int transactionNumber = cartOrders.size()+1;
			cartOrder.setTransactionNumber(String.valueOf(transactionNumber));
			cartOrder.setInfo1(code);
			cartOrder.setInfo3(type);//type
			cartOrder.setItems(cartOrderItems);
			cartOrder.setTotalPrice(totalPrice);
			cartOrder.setTotalPriceOkFormatted(Double.toString(totalPrice));
			cartOrder.setPaymentType(PaymentType.PAYPAL);
			cartOrder.setStatus(OrderStatus.PENDING);
			cartOrder.setPaymentStatus(PaymentStatus.PENDING);
			final long orderId = cartOrderDelegate.insert(cartOrder);
			cartOrderItemDelegate.batchInsert(cartOrderItems);
			logger.info("----"+isFreeEvent+"---"+type);
			if(isFreeEvent && (type.equalsIgnoreCase("Events") || type.equalsIgnoreCase("Promos"))) {
				CartOrder cOrder = cartOrderDelegate.find(orderId);
				cOrder.setStatus(OrderStatus.COMPLETED);
				cOrder.setPaymentStatus(PaymentStatus.PAID);
				cartOrderDelegate.update(cOrder);
				final EmailSenderAction emailSender = new EmailSenderAction();
				emailSender.sendEmailPaymentInformationFromLife(company, cOrder, member, true);
				data[0] = "FREE";
				data[1] = totalCartItem + "";
			} else {
			
				Paypal paypal = null;
				if(isMember) {
					paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl() + "?memberId="
						+ member.getId() + "&orderId=" + orderId, company.getPalCancelUrl());
				} else {
					paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl() + "?memberId="
							+ "111111111111111111111111" + "&orderId=" + orderId, company.getPalCancelUrl());
				}
				
				paypal.setCategoryItems(catItems);
				
				String result = paypal.setExpressCheckoutRequest(DEFAULT_DECIMAL_FORMAT.format(totalPrice), company.getPalCurrencyType(), null, null);
				
				//Paypal paypal = new Paypal(company.getPalUsername(), company.getPalPassword(), company.getPalSignature(), "live", company.getPalSuccessUrl(), company.getPalCancelUrl());
				//String result = paypal.setExpressCheckoutRequest(DEFAULT_DECIMAL_FORMAT.format(totalPrice), company.getPalCurrencyType(), null, newItem);
				
				data[0] = message;
				data[1] = totalCartItem + "";
				
				if(result.equalsIgnoreCase("Failure"))
				{
					data[0] = "Error Paypal";
					return data;
				}
				
				String pToken = paypal.getToken();
				String checkoutUrl = paypal.getPaypalUrl();
				
				data[2] = pToken;
				data[3] = checkoutUrl;
			}
			
			//session.remove("noLogInCartItems");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public boolean isPromoAvailable(String code, String name) {
		
		boolean isAvailable = true;
		List<CartOrder> cartOrders = cartOrderDelegate.findAllByCompany(company);
		for(CartOrder cartOrder : cartOrders) {
			if(cartOrder.getInfo1().equalsIgnoreCase(code) && cartOrder.getName().equalsIgnoreCase(name) && cartOrder.getStatus().equals(OrderStatus.COMPLETED)) {
				isAvailable = false;
				break; 
			}
		}
		if(isAvailable) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean isPromoAvailableByEmail(String code, String email) {
		
		boolean isAvailable = true;
		List<CartOrder> cartOrders = cartOrderDelegate.findAllByCompany(company);
		for(CartOrder cartOrder : cartOrders) {
			if(cartOrder.getInfo1().equalsIgnoreCase(code) && cartOrder.getEmailAddress().equalsIgnoreCase(email) && cartOrder.getStatus().equals(OrderStatus.COMPLETED)) {
				isAvailable = false;
				break; 
			}
		}
		if(isAvailable) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public String[] addToCart(Long itemId, Integer quantity) {
		
		String message = "";
		Integer totalCartItem = 0;
		List<CategoryItem> oldNoLogInCartItems = (List<CategoryItem>) session.getAttribute("noLogInCartItems");
		List<CategoryItem> updatedNoLogInCartItems = new ArrayList<CategoryItem>();
		
		CategoryItem newItem = categoryItemDelegate.find(itemId);
		newItem.setOrderQuantity(quantity);
		if(newItem.getImages() != null && newItem.getImages().size() > 0) {
			OtherDetail otherDetail = new OtherDetail();
			otherDetail.setPathDetail(newItem.getImages().get(0).getOriginal());
			newItem.setOtherDetail(otherDetail);
		}
		
		boolean isNewItem = true;
		
		if(oldNoLogInCartItems != null)
		{
			updatedNoLogInCartItems.addAll(oldNoLogInCartItems);
			for(CategoryItem oldItem : oldNoLogInCartItems)
			{
				/*
				 * boolean isOldItemCustomOnly = false;
				 * boolean isNewItemCustomOnly = false;
				 */
				Integer oldOrderQuantity = oldItem.getOrderQuantity();
				
				
				oldItem = categoryItemDelegate.find(oldItem.getId());
				oldItem.setOrderQuantity(oldOrderQuantity);
				
				if(oldItem.getId().longValue() == newItem.getId().longValue())
				{
					// add old quantity if oldItem.getId()==newItem.getId()
					newItem.setOrderQuantity(oldOrderQuantity + quantity);
					
					updatedNoLogInCartItems.remove(oldItem);
					updatedNoLogInCartItems.add(newItem);
					message += newItem.getName() + " successfully updated in cart.";
					isNewItem = false;
				}
				
			}
			
		}
		
		if(isNewItem)
		{
			updatedNoLogInCartItems.add(newItem);
			message += newItem.getName() + " successfully added to cart.";
			
		}
		for(CategoryItem item : updatedNoLogInCartItems)
		{
			totalCartItem += item.getOrderQuantity();
		}
		
		String[] data = new String[2];
		data[0] = message;
		data[1] = totalCartItem + "";
		
		session.setAttribute("noLogInCartItems", updatedNoLogInCartItems);
		return data;
		
	}
	
	public String[] addItemsToMemberCart(String itemIds, String quantities) {
		
		String[] itemIdArr = itemIds.split(",");
		String[] quantityArr = quantities.split(",");
		String[] message = null;
		for(int i=0; i<itemIdArr.length; i++) {
			Long itemId = new Long(itemIdArr[i]);
			Integer quantity = new Integer(quantityArr[i]);
			message = addToMemberCart(itemId, quantity);
		}
		return message;
	}
	
	public String[] addToMemberCart(Long itemId, Integer quantity) {
		
		String message = "";
		Integer totalCartItem = 0;
		
		if(company.getName().equalsIgnoreCase(CompanyConstants.AYROSOHARDWARE) 
				|| company.getName().equals(CompanyConstants.PURE_NECTAR)) {
			Object sessMember = session.getAttribute(MemberInterceptor.MEMBER_REQUEST_KEY);
			member = (Member) sessMember;			
		}
		
		shoppingCart = shoppingCartDelegate.find(company, member);
		
		if(shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			shoppingCart.setCompany(company);
			shoppingCart.setMember(member);
			shoppingCart.setCreatedOn(new Date());
			shoppingCartDelegate.insert(shoppingCart);
			session.setAttribute("shoppingCart", shoppingCart);
		}
		
		currentCategoryItem = categoryItemDelegate.find(company, itemId);
		
		itemDetail = currentCategoryItem.getItemDetail();
		itemDetail.setRealID(itemId);
		itemDetail.setPrice(currentCategoryItem.getPrice());
		itemDetail.setShippingPrice(currentCategoryItem.getShippingPrice());
		currentCategoryItem.getItemDetail().setRealID(itemId);
		
		if(!hasDuplicateCartItem(itemDetail))
		{
			createNewShoppingCartItem(quantity);
			
			message += currentCategoryItem.getName() + " successfully added to cart.";
			
		}else {
			if(!isCartItemRemoved())
			{
				updateQuantity(quantity);

				message += currentCategoryItem.getName() + "'s quantity has been updated in the cart.";
			}
		}
		
		shoppingCart = shoppingCartDelegate.find(company, member);
		
		for(ShoppingCartItem item : shoppingCart.getItems()) {
			totalCartItem += item.getQuantity();
		}
		
		String[] data = new String[2];
		data[0] = message;
		data[1] = totalCartItem + "";
		
		session.setAttribute("shoppingCart", shoppingCart);
		session.setAttribute("cartItemList", cartItemList);
		return data;
		
	}
	
	private boolean hasDuplicateCartItem(ItemDetail itemDetail)
	{
		// get cart item with the same detail
		currentCartItem = shoppingCartItemDelegate.find(shoppingCart, itemDetail);

		if(isNull(currentCartItem))
		{
			// lookup deleted cart item values
			currentCartItem = shoppingCartItemDelegate.find(shoppingCart, itemDetail, false);
			if(isNull(currentCartItem))
			{
				return false;
			}
		}
		// has duplicate value
		return true;
	}
	
	private void createNewShoppingCartItem(Integer quantity)
	{
		// create a new cart item entry
		currentCartItem = new ShoppingCartItem();
		initNewShoppingCartItem(quantity);
		currentCartItem.setCreatedOn(new Date());
		// add item to cart
		currentCartItem.setShoppingCart(shoppingCart);
		currentCartItem.setCategoryItem(currentCategoryItem);

		shoppingCartItemDelegate.insert(currentCartItem);
	}
	
	protected boolean isNull(Object param) {
		return null == param;
	}
	
	private Boolean isCartItemRemoved()
	{
		return !currentCartItem.getIsValid();
	}
	
	private void updateQuantity(Integer quantity)
	{
		currentCartItem.setUpdatedOn(new Date());
		currentCartItem.setQuantity(currentCartItem.getQuantity() + quantity);
		shoppingCartItemDelegate.update(currentCartItem);
	}
	
	private void initNewShoppingCartItem(Integer quantity)
	{
		currentCartItem.setCompany(company);
		currentCartItem.setItemDetail(itemDetail);

		final String temp = currentCategoryItem.getItemDetail().getName();
		currentCartItem.getItemDetail().setName(temp);

		currentCartItem.getItemDetail().setPrice(currentCategoryItem.getPrice());

		currentCartItem.setQuantity(quantity);
		
		currentCartItem.setIsValid(true);
	}

}
