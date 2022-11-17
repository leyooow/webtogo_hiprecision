package com.ivant.cms.action.order;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.ivant.cms.delegate.ItemVariationDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.ItemVariation;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.order.Cart;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;

public class CartItemAction implements SessionAware, CompanyAware, Preparable {
	private static final Logger log = Logger.getLogger(CartItemAction.class);
	private static final boolean IS_DEBUG = log.isDebugEnabled();
	
	private Company company;
	
	@SuppressWarnings("unchecked")
	private Map session;
	
	private ItemVariationDelegate itemVariationDelegate = ItemVariationDelegate.getInstance();
	
	private Cart cart;
	private ItemVariation itemVariation;
	
	private int quantity;
	private String sku;
	
	public void setQuantity(int q) {
		this.quantity = q;
	}
	
	@SuppressWarnings("unchecked")
	public void setSession(Map arg0) {
		session = arg0;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@SuppressWarnings("unchecked")
	public String addToCart() throws Exception {
		log.debug("addToCart was invoked");
		
		int quantityToAdd = quantity < 1 ? 1 : quantity;
		//System.out.println("ItemVar: " + itemVariation);
		int curQuantity = cart.getQuantity(itemVariation);
		//int curQuantity = 1;
		if(IS_DEBUG) log.debug("add " + quantityToAdd + " to " + curQuantity + "of " + itemVariation.getName());
		
		cart.setQuantity(itemVariation, curQuantity + quantityToAdd);
		
		log.debug("item added to cart succesfully");
		
		return Action.SUCCESS; 
	}
	
	public ItemVariation getItemVariation() {
		return itemVariation;
	}

	public void setItemVariation(ItemVariation itemVariation) {
		this.itemVariation = itemVariation;
	}

	@SuppressWarnings("unchecked")
	public String removeToCart() throws Exception {
		log.debug("removeToCart was invoked");
		
		cart.setQuantity(itemVariation, 0);
		
		log.debug("item removed to cart succesfully");
		
		return Action.SUCCESS;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	@SuppressWarnings("unchecked")
	public void prepare() throws Exception {
		log.debug("aquiring cart in session.");
		cart = (Cart) session.get(CartAction.CART);
		
		if(cart == null) {
			log.debug("no cart found. creating a new cart. ");
			cart = new Cart(company);
			session.put(CartAction.CART, cart);
		}
		
		log.debug("retrieve category item in db.");
		if(IS_DEBUG) {
			log.debug("sku: " + sku);
			log.debug("company: " + company);
		}
		//System.out.println("Company: " + company.getId());
		//System.out.println("sku: " + sku);
		itemVariation = itemVariationDelegate.findBySKU(company, sku);
		
		if(itemVariation == null) {
			log.warn("item variation item was not found");
		}
	}
	
	public ItemVariation getItem() {
		return itemVariation;
	}
}