package com.ivant.cms.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.ShoppingCartDelegate;
import com.ivant.cms.delegate.ShoppingCartItemDelegate;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.CartAware;
import com.ivant.constants.CompanyConstants;

@Entity
@Table(name="cart")
public class ShoppingCart extends CompanyBaseObject implements CartAware<ShoppingCartItem> {
	
	/** Cents decimal format */
	private static final NumberFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat ("#.0#");
	
	/** Member that owns the cart, must not be null */
	private Member member;
	/** List of purchased items, can be 0 or more */
	private List<ShoppingCartItem> items;
	
	CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	MemberShippingInfoDelegate memberShippingInfoDelegate = MemberShippingInfoDelegate.getInstance();
	
	GroupDelegate groupDelegate = GroupDelegate.getInstance();
	ShoppingCartDelegate shoppingCartDelegate = ShoppingCartDelegate.getInstance();
	ShoppingCartItemDelegate shoppingCartItemDelegate = ShoppingCartItemDelegate.getInstance();

	HttpServletRequest request;
	ServletContext servletContext;
	
	private transient double totalShippingPrice;
	private transient String formattedTotalShippingPrice;
	private transient Double orderWeight = 0.0D;
	private transient Double shippingCost = 0.0D;
	
	private transient Double totalWeight;
	private transient Double totalValuation = 0.01D;
	private transient Double totalVat = 0.12D;
	DecimalFormat df = new DecimalFormat("##.##");
	
	private transient Long anthonyId;
	@Transient
	public Integer getItemCount(){
		//validate item list before sending item count
		if(null != items && !items.isEmpty())
			return items.size();
		
		//return an empty list count by default
		return 0;
	}
	
	@Transient
	public Integer getTotalCartQuantity(){
		int total = 0;
		
		if(null != items && !items.isEmpty())
		{
			for(int i=0; i<items.size(); i++)
			{
				if(!items.get(i).getItemDetail().getName().equals("Windows") && !items.get(i).getItemDetail().getName().equals("Shipping Cost") && !items.get(i).getItemDetail().getName().equals("Less 20% Discount"))
					total = total + items.get(i).getQuantity();
			}
			
			return total;
		}
		
		//return an empty list count by default
		return 0;
	}
	
	@Transient
	public double getTotalCartPrice(){
		double total = 0;
		
		if(null != items && !items.isEmpty())
		{
			for(int i=0; i<items.size(); i++)
			{
				if(!items.get(i).getItemDetail().getName().equals("Windows") && !items.get(i).getItemDetail().getName().equals("Shipping Cost") && !items.get(i).getItemDetail().getName().equals("Less 20% Discount"))
					total = total + items.get(i).getQuantity() * items.get(i).getItemDetail().getPrice();
			}
			
			return total;
		}
		
		//return an empty list count by default
		return 0;
	}

	@Transient
	public Double getTotalPrice(){		
		BigDecimal totalPrice = new BigDecimal(0.0D);
		BigDecimal itemTotalPrice = new BigDecimal(0.0D);
		Double currentItemPrice = 0.0D;
		Integer itemQuantity = 0;
		
		//sum up cart item prices
		for(ShoppingCartItem currentCartItem : getItems()){
			//get item price
			currentItemPrice = currentCartItem.getItemDetail().getPrice();
			//System.out.println("Item PRice "+currentItemPrice);
			itemQuantity = currentCartItem.getQuantity();
			//System.out.println("Item Quantity "+itemQuantity);
			//ensure price precision down to the last cents			 
			itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
			totalPrice = totalPrice.add(itemTotalPrice);
		}
		
		//return in used price format
		return Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice));
	}
	
	@Transient
	public Double getTotalPriceWithCategoryItem() {
		BigDecimal totalPrice = new BigDecimal(0.0D);
		BigDecimal itemTotalPrice = new BigDecimal(0.0D);
		Double currentItemPrice = 0.0D;
		Integer itemQuantity = 0;
		// sum up cart item prices
		List<ShoppingCartItem> listShoppingCartItem = new ArrayList<ShoppingCartItem>();
		Group group = new Group();
		if(getCompany().getId().equals(CompanyConstants.GURKKA_TEST)){}
		else{
			listShoppingCartItem = getItems();
		}
		//for(ShoppingCartItem currentCartItem : getItems()) {
		for(ShoppingCartItem currentCartItem : listShoppingCartItem){
			//get Item Price
			currentItemPrice = currentCartItem.getCategoryItem().getPrice();
			itemQuantity = currentCartItem.getQuantity();
			itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));
			totalPrice = totalPrice.add(itemTotalPrice);
		}
		
		//return in used price format
		return Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice));
	}
	
	@Transient
	public Double getTotalPriceWithCategoryItemPROMOBASKET() {
		BigDecimal totalPrice = new BigDecimal(0.0D);
		BigDecimal itemTotalPrice = new BigDecimal(0.0D);
		Double currentItemPrice = 0.0D;
		Integer itemQuantity = 0;
		List<ShoppingCartItem> listShoppingCartItem = new ArrayList<ShoppingCartItem>();
		Group group = new Group();
		if(getCompany().getId().equals(CompanyConstants.GURKKA_TEST)){}
		else{
			listShoppingCartItem = getItems();
		}
		for(ShoppingCartItem currentCartItem : listShoppingCartItem){
			currentItemPrice = currentCartItem.getCategoryItem().getPrice();
			itemQuantity = currentCartItem.getQuantity();
			itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));
			totalPrice = totalPrice.add(itemTotalPrice);
		}
		return Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice));
	}
	
	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}	
	
	@Transient
	public Double getTotalShippingPrice(){	
		request = ServletActionContext.getRequest();
		Company company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		anthonyId = (Long) request.getSession().getAttribute("user_id");
		//get shipping price with status ok
		BigDecimal totalPrice = new BigDecimal(0.0D);
		BigDecimal itemTotalPrice = new BigDecimal(0.0D);
		Double currentItemPrice = 0.0D;
		Integer itemQuantity = 0;
		Double totalOrderWeight = 0.0D;
		Double currentItemWeight = 0.0D;
		Double itemTotalWeight = 0.0D;
		
		if(new String("individual").equalsIgnoreCase(company.getCompanySettings().getShippingType())){
			//sum up order item prices
			for(ShoppingCartItem currentCartOrderItem : this.getItems()){
					//get item price
					currentItemPrice = currentCartOrderItem.getItemDetail().getShippingPrice();
					itemQuantity = currentCartOrderItem.getQuantity();
					
					//ensure price precision down to the last cents	
					itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
					totalPrice = totalPrice.add(itemTotalPrice);
			}
			this.totalShippingPrice = Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice));
		}
		//if shippingtype is "weight"
		else if(new String("weight").equalsIgnoreCase(company.getCompanySettings().getShippingType())){
			//sum up order item prices
			for(ShoppingCartItem currentCartOrderItem : this.getItems()){
					//get item price
					currentItemWeight = currentCartOrderItem.getItemDetail().getWeight();
					itemQuantity = currentCartOrderItem.getQuantity();
					//ensure price precision down to the last cents						
					//computation of Total Weight per item	
					itemTotalWeight = currentItemWeight * itemQuantity;			
					totalOrderWeight = totalOrderWeight + itemTotalWeight;	
			}
			orderWeight = totalOrderWeight;
			//System.out.println("MEMO: "+totalOrderWeight);
			//get the equivalent HBC Shipping Rates
			if(company.getId() == 104 && company.getName().equalsIgnoreCase("hbc")) {
				totalPrice = getHBCEquivalentShippingCost(totalOrderWeight, memberShippingInfoDelegate.find(company, member));
				totalWeight = totalOrderWeight;
//				return Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(0.01));
			}
			//System.out.println("FLAG: totalPrice = "+totalPrice);
			this.totalShippingPrice = Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice));
		}
		//if shippingtype is "ranged"
		else if(new String("ranged").equalsIgnoreCase(company.getCompanySettings().getShippingType())){
			for(ShippingTable x : company.getShippingTable()){
				//System.out.println("################################"+x.getFromPrice());
				//System.out.println("################################"+this.getTotalPrice());
				//System.out.println("################################"+x.getToPrice());
				this.totalShippingPrice = x.getShippingPrice();
				if(x.getFromPrice()<=this.getTotalPrice() && this.getTotalPrice()<=x.getToPrice()){
					//this.totalShippingPrice = x.getShippingPrice();
					return Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(this.totalShippingPrice));
				}
			}
			
			return Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(this.totalShippingPrice));
		}
		//if "fixed"	
		else if(new String("fixed").equalsIgnoreCase(company.getCompanySettings().getShippingType())){		
			this.totalShippingPrice = company.getFlatRateShippingPrice();
		}
		else{
			return Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(0d));
		}
		//return in used price format
		
		return Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(this.totalShippingPrice));
	}
	
	// For HBC by MEMO
	@Transient
	private BigDecimal getHBCEquivalentShippingCost(Double totalWeight, MemberShippingInfo memberShippingInfo) {
		ShippingInfo shippingInfo= memberShippingInfo.getShippingInfo();
		BigDecimal hbcShippingCost = new BigDecimal(0.0D);
		BigDecimal subTotalShippingCost = new BigDecimal(0.0D);	
		BigDecimal valuation = new BigDecimal(0.01D);
		BigDecimal vat = new BigDecimal(0.12D);
		BigDecimal documentStamp = new BigDecimal(0.25D);
		Double weightDifference = 0.0;
		
		DecimalFormat format = new DecimalFormat("#.##");
		
		/** Do not delete this - might be useful in the future of mankind
		//Metro Manila and Luzon Area
		if("PH".equals(shippingInfo.getCountry()) || "PHL".equals(shippingInfo.getCountry())) {
			
			if(totalWeight < 10.0) {
				weightDifference = totalWeight - 5.0;
				hbcShippingCost = new BigDecimal(330.0D);								
			}
			else if(totalWeight >= 10.0 && totalWeight < 20.0) {
				weightDifference = totalWeight - 10.0;
				hbcShippingCost = new BigDecimal(600.0D);
			}
			else if(totalWeight > 20.0) {
				weightDifference = totalWeight - 20.0;
				hbcShippingCost = new BigDecimal(1000.0D);				
			}
			
			if(weightDifference > 0) {
				hbcShippingCost = hbcShippingCost.add(new BigDecimal(weightDifference * 45.0));
			}
		}
		//Visayas and Mindanao Area
		else if("PHV".equals(shippingInfo.getCountry())) {
			
			if(totalWeight < 10.0) {
				weightDifference = totalWeight - 5.0;
				hbcShippingCost = new BigDecimal(480.0D);								
			}
			else if(totalWeight >= 10.0 && totalWeight < 20.0) {
				weightDifference = totalWeight - 10.0;
				hbcShippingCost = new BigDecimal(800.0D);
			}
			else if(totalWeight > 20.0) {
				weightDifference = totalWeight - 20.0;
				hbcShippingCost = new BigDecimal(1350.0D);				
			}
			if(weightDifference > 0) {
				hbcShippingCost = hbcShippingCost.add(new BigDecimal(weightDifference * 90.0));
			}
		}**/
		
		/**
		 * As of March 15, 2012
		 * First 5kgs (min) = USD8.00
		 * In excess(per kg) = USD1.30
		 */
		
		Double absoluteValueOfTotalWeight = Math.floor(totalWeight);
		BigDecimal excess = new BigDecimal(0.0D);
		
		hbcShippingCost = new BigDecimal(8.00);
		
		// Computation for excess weight
		if(absoluteValueOfTotalWeight > 5) {
		
			for(int i = 1; i <= (absoluteValueOfTotalWeight-5); i++) {
				//excess = 1.30 + excess;
				excess = excess.add(new BigDecimal(1.30D));
			}			
		}
		
		//System.out.println("INITIAL SHIPPING COST : " + hbcShippingCost);
		hbcShippingCost = hbcShippingCost.add(excess);
		
		if(memberShippingInfo.getShippingInfo().getCountry().equals("CA") || memberShippingInfo.getShippingInfo().getCountry().equals("US"))
		{
			hbcShippingCost = new BigDecimal(0.00);
			if(absoluteValueOfTotalWeight < 2)
			{
				hbcShippingCost = hbcShippingCost.add(new BigDecimal(41.23D));
			}
			else if(absoluteValueOfTotalWeight < 3)
			{
				hbcShippingCost = hbcShippingCost.add(new BigDecimal(51.44D));
			}
			else if(absoluteValueOfTotalWeight < 4)
			{
				hbcShippingCost = hbcShippingCost.add(new BigDecimal(61.00D));
			}
			else if(absoluteValueOfTotalWeight < 5)
			{
				hbcShippingCost = hbcShippingCost.add(new BigDecimal(70.23D));
			}
		}

		//System.out.println("TOTAL WEIGHT : " + totalWeight + " PRICE OF EXCESS WEIGHT : " + excess);
		//System.out.println("TOTAL SHIPPING COST : " + hbcShippingCost);
		shippingCost = hbcShippingCost.doubleValue();
		subTotalShippingCost = hbcShippingCost;
		
		
		//System.out.println("LOL: "+shippingCost);
		//System.out.println("LOL: "+totalVat);
		
		/* for shipping info */
		totalValuation = shippingCost * totalValuation;
		totalVat = shippingCost * totalVat;
		
		valuation = subTotalShippingCost.multiply(valuation);		
		hbcShippingCost = hbcShippingCost.add(valuation);
		//System.out.println("VALUATION : " + hbcShippingCost);
		
		vat = subTotalShippingCost.multiply(vat);
		hbcShippingCost = hbcShippingCost.add(vat);
		//System.out.println("VAT : " + hbcShippingCost);
		
		hbcShippingCost = hbcShippingCost.add(documentStamp);
		//shippingCost.setScale(2, BigDecimal.ROUND_HALF_UP);
		//System.out.println("DOCUMENT STAMP : " + hbcShippingCost);		
		
		return hbcShippingCost;
	}
	
	@Transient
	public String getFormattedTotalShippingPrice(){	
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		return numberFormatter.format(getTotalShippingPrice());
	}
	
	@Transient
	public String getFormattedTotalItemsPrice(){	
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		return numberFormatter.format(getTotalPrice());
	}
	
	@Transient
	public String getFormattedTotalPrice(){	
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		//System.out.println("ANTHONY ID : " + anthonyId);
		if(anthonyId == null)
			anthonyId = Long.parseLong("0");
		
		if(anthonyId == 2081)
			return numberFormatter.format(getTotalPrice());
		
		return numberFormatter.format(getTotalPrice()+getTotalShippingPrice());
	}
	
	@Transient
	public final Double getTotalDiscount()
	{
		Double totalDiscount = 0.00;
		try
		{
			final List<ShoppingCartItem> items = getItems();
			if(items != null && !items.isEmpty())
			{
				for(ShoppingCartItem sci : items)
				{
					final ItemDetail itemDetail = sci.getItemDetail();
					final Double discount = itemDetail.getDiscount() == null
						? 0.00
						: itemDetail.getDiscount();
					totalDiscount += discount;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return totalDiscount;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	@Override
	@OneToMany(targetEntity=ShoppingCartItem.class, fetch=FetchType.LAZY, mappedBy="shoppingCart")
	@Where(clause="valid=1")
	public List<ShoppingCartItem> getItems() {
		return items;
	}
	
	@Override
	public void setItems(List<ShoppingCartItem> items) {
		this.items = items;
	}

	public void setFormattedTotalShippingPrice(
			String formattedTotalShippingPrice) {
		this.formattedTotalShippingPrice = formattedTotalShippingPrice;
	}

	@Transient
	public Double getOrderWeight() {
		return orderWeight;
	}

	@Transient
	public Double getShippingCost() {
		return shippingCost;
	}
	
	@Transient
	public Double getValuation() {
		return (getShippingCost() * 0.01);
	}
	
	@Transient
	public Double getVat() {
		return (getValuation() * 0.12);
	}
	
	@Transient
	public Double getDocumentaryStamp() {
		return 0.25;
	}
	
	@Transient
	public Double getTotalWeight() {
		if(totalWeight != null)
			return Double.parseDouble(df.format(totalWeight));
		return totalWeight;
	}
	
	@Transient
	public Double getTotalValuation() {
		return totalValuation;
	}
	
	@Transient
	public Double getTotalVat() {		
		if(totalVat != null)
			return Double.parseDouble(df.format(totalVat));
		
		return totalVat;
	}
	
}
