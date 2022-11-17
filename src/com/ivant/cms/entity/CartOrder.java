package com.ivant.cms.entity;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CategoryItemOtherFieldDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.OrderStatus;
import com.ivant.cms.enums.PaymentStatus;
import com.ivant.cms.enums.PaymentType;
import com.ivant.cms.enums.ShippingStatus;
import com.ivant.cms.enums.ShippingType;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interfaces.CartAware;

@Entity
@Table(name="cart_order")
public class CartOrder extends CompanyBaseObject implements CartAware<CartOrderItem>{
	
	/** Cents decimal format */
	private static final NumberFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat ("#.00");
	private static final String PNG_EXTENSION = ".png";
	private static final String SHIPPING_COST = "Shipping Cost";
	private static final String DISCOUNT = "Discount";

	CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	CategoryItemOtherFieldDelegate categoryItemOtherFieldDelegate = CategoryItemOtherFieldDelegate.getInstance();
	HttpServletRequest request;
	ServletContext servletContext;
	
	/** Member that owns the cart, must not be null */
	private Member member;
	/** List of purchased items, can be 0 or more */
	private List<CartOrderItem> items;
	/** Status of the ordered items */
	private OrderStatus status;
	private PaymentStatus paymentStatus;
	private ShippingStatus shippingStatus;
	private PaymentType paymentType;
	private ShippingType shippingType;
	/** Address where the ordered items will be shipped */
	private MemberShippingInfo shippingInfo;
	private transient double totalPrice;
	private transient double totalPriceOk=0.0;
	private transient String totalPriceOkFormatted;
	private List<MemberFile> memberFileList;
	private transient double totalShippingPrice;
	
	// comments or special address
	private String comments;
	
	// additional fields or attributes for each order
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String phoneNumber;
	private String emailAddress;
	private double totalShippingPrice2;
	private String statusNotes;
	private Double otherCharges;
	private String transactionNumber;
	private String approvalCode;
	private String prescription;
	private String business;
	
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	private String info5;
	private String info6;
	
	//billing address
	private Boolean shipToBillingAddress;
	private String billingCountry;
	private String billingName;
	private String billingBusiness;
	private String billingAddress1;
	private String billingAddress2;
	private String billingCity;
	private String billingState;
	private String billingZipCode;
	
	private Boolean flag1;
	
	private CartOrderPromoCode cartOrderPromoCode;
	
	@Transient
	public Integer getItemCount(){
		//validate item list before sending item count
		if(null != items && !items.isEmpty())
			return items.size();
		
		//return an empty list count by default
		return 0;
	}
	@Transient
	public String getTotalPriceFormatted(){	
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		
		//return in used price format
		return numberFormatter.format(Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(getTotalPrice())));
	}
	
	
	@Transient
	public Double getTotalPrice(){		
		BigDecimal totalPrice = new BigDecimal(0.0D);
		BigDecimal itemTotalPrice = new BigDecimal(0.0D);
		Double currentItemPrice = 0.0D;
		Integer itemQuantity = 0;
		//System.out.println("Id of this cartOrder: "+getId());
		//System.out.println("Is item null? "+items);
		//sum up order item prices
		for(CartOrderItem currentCartOrderItem : getItems()){
				//get item price
				currentItemPrice = currentCartOrderItem.getItemDetail().getPrice();
				itemQuantity = currentCartOrderItem.getQuantity();
				
				if(currentCartOrderItem.getItemDetail().getName().indexOf(SHIPPING_COST)!=-1)
					continue;
				else if(currentCartOrderItem.getItemDetail().getName().indexOf(DISCOUNT)!=-1)
					continue;
				
				//ensure price precision down to the last cents			 
				itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
				totalPrice = totalPrice.add(itemTotalPrice);
		}
		
		//return in used price format
		this.totalPrice = Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice));
		return this.totalPrice;
	}
	
	@Transient
	public String getTomatoTotalPriceFormatted(){
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		
		Double totalPrice = 0d;
		Double itemTotalPrice = 0d;
		Double currentFacePrice = 0d;
		Double currentStrapPrice = 0d;
		Integer itemQuantity = 0;
		//sum up order item prices
		for(CartOrderItem currentCartOrderItem : getItems()){
			if(currentCartOrderItem.getItemDetail().getName().indexOf(SHIPPING_COST)!=-1) {
				continue;
			}
			else if(currentCartOrderItem.getItemDetail().getName().indexOf(DISCOUNT)!=-1) {
				continue;
			}
				
			//get item price
			currentFacePrice =  currentCartOrderItem.getOtherDetail().getFace().getPrice();
			currentStrapPrice = currentCartOrderItem.getItemDetail().getPrice();
			itemQuantity = currentCartOrderItem.getQuantity();
			
			//ensure price precision down to the last cents			 
			itemTotalPrice = currentFacePrice * itemQuantity;
			itemTotalPrice += currentStrapPrice * itemQuantity;
			totalPrice += itemTotalPrice;
		}
		
		//return in used price format
		return numberFormatter.format(Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice)));
	}
		
	@Transient
	public String getSwapCanadaTotalPriceFormatted(){
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		
		Double totalPrice = 0d;
		Double itemTotalPrice = 0d;
		Double currentFacePrice = 0d;
		Double currentStrapPrice = 0d;
		Integer itemQuantity = 0;
		String image = "";
		
		//sum up order item prices
		for(CartOrderItem currentCartOrderItem : getItems()){
			image = currentCartOrderItem.getItemDetail().getImage();
			
			if(currentCartOrderItem.getItemDetail().getName().indexOf(SHIPPING_COST)!=-1) {
				continue;
			}
			else if(currentCartOrderItem.getItemDetail().getName().indexOf(DISCOUNT)!=-1) {
				continue;
			}
			else if(image != null && StringUtils.contains(image, PNG_EXTENSION)) {
				//get item price
				currentFacePrice =  currentCartOrderItem.getOtherDetail().getFace().getPrice();
				currentStrapPrice = currentCartOrderItem.getItemDetail().getPrice();
				itemQuantity = currentCartOrderItem.getQuantity();
				
				//ensure price precision down to the last cents			 
				itemTotalPrice = currentFacePrice * itemQuantity;
				itemTotalPrice += currentStrapPrice * itemQuantity;
				totalPrice += itemTotalPrice;
			}
			else if(image != null) {
				itemQuantity = currentCartOrderItem.getQuantity();
				totalPrice += currentCartOrderItem.getItemDetail().getPrice() * itemQuantity;
			}
		}
		
		//return in used price format
		return numberFormatter.format(Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice)));
	}
	
	
	
	
	/*
	 * gets only total price of status "ok" order items 
	 */
	@Transient
	public Double getTotalPriceOk(){		
		BigDecimal totalPrice = new BigDecimal(0.0D);
		BigDecimal itemTotalPrice = new BigDecimal(0.0D);
		Double currentItemPrice = 0.0D;
		Integer itemQuantity = 0;
		
		//sum up order item prices
		for(CartOrderItem currentCartOrderItem : getItems()){
			if(currentCartOrderItem.getStatus()==null){
				//get item price
				currentItemPrice = currentCartOrderItem.getItemDetail().getPrice();
				itemQuantity = currentCartOrderItem.getQuantity();
				
				//ensure price precision down to the last cents			 
				itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
				totalPrice = totalPrice.add(itemTotalPrice);
			}
			else if(currentCartOrderItem.getStatus().equalsIgnoreCase("OK")){
				//get item price
				currentItemPrice = currentCartOrderItem.getItemDetail().getPrice();
				itemQuantity = currentCartOrderItem.getQuantity();
				
				//ensure price precision down to the last cents			 
				itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
				totalPrice = totalPrice.add(itemTotalPrice);
			}
		}
		
		//return in used price format
		this.totalPriceOk = Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice));
		return this.totalPriceOk;
	}
	
	@Transient
	public final Double getTotalDiscount()
	{
		Double totalDiscount = 0.00;
		try
		{
			final List<CartOrderItem> items = getItems();
			if(items != null && !items.isEmpty())
			{
				for(CartOrderItem coi : items)
				{
					final ItemDetail itemDetail = coi.getItemDetail();
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
	
	@Transient
	public Double getTotalShippingPrice(){	
		request = ServletActionContext.getRequest();
		Company company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		//get shipping price with status ok
		BigDecimal totalPrice = new BigDecimal(0.0D);
		BigDecimal itemTotalPrice = new BigDecimal(0.0D);
		Double currentItemPrice = 0.0D;		
		Integer itemQuantity = 0;	
		Double totalOrderWeight = 0.0D;
		Double currentItemWeight = 0.0D;
		Double itemTotalWeight = 0.0D;
		
		
		if(company.getCompanySettings().getShippingType().equalsIgnoreCase("none")){
			return this.totalShippingPrice;
		}else{
			if(company.getCompanySettings().getShippingType().equalsIgnoreCase("individual")){
				//sum up order item prices
				for(CartOrderItem currentCartOrderItem : getItems()){
					if(currentCartOrderItem.getStatus()==null){
						//get item price
						currentItemPrice = currentCartOrderItem.getItemDetail().getShippingPrice();
						itemQuantity = currentCartOrderItem.getQuantity();
						
						//ensure price precision down to the last cents			 
						itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
						totalPrice = totalPrice.add(itemTotalPrice);
					}
					else if(currentCartOrderItem.getStatus().equalsIgnoreCase("OK")){
						//get item price
						currentItemPrice = currentCartOrderItem.getItemDetail().getShippingPrice();
						itemQuantity = currentCartOrderItem.getQuantity();
						
						//ensure price precision down to the last cents			 
						itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
						totalPrice = totalPrice.add(itemTotalPrice);
					}
				}
				this.totalShippingPrice = Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice));
			}
			//if shippingtype is "ranged"
			else if(company.getCompanySettings().getShippingType().equalsIgnoreCase("ranged")){
				for(ShippingTable x : company.getShippingTable()){
					this.totalShippingPrice = x.getShippingPrice();
					if(x.getFromPrice()<=this.getTotalPriceOk() && this.getTotalPriceOk()<=x.getToPrice()){
						//this.totalShippingPrice = x.getShippingPrice();
						return this.totalShippingPrice;
					}
				}
				
				return this.totalShippingPrice;
			}
			else if(company.getCompanySettings().getShippingType().equalsIgnoreCase("fixed")){//if "fixed"			
				this.totalShippingPrice = company.getFlatRateShippingPrice();
			}
			else{
				return 0d;
			}
			//return in used price format
		}
		
		
		return this.totalShippingPrice;
	}
	
	@Transient
	public String getTotalPriceOkFormatted(){		
		BigDecimal totalPrice = new BigDecimal(0.0D);
		BigDecimal itemTotalPrice = new BigDecimal(0.0D);
		Double currentItemPrice = 0.0D;
		Integer itemQuantity = 0;
		
		//sum up order item prices
		for(CartOrderItem currentCartOrderItem : getItems()){
			if(currentCartOrderItem.getStatus()==null){
				//get item price
				currentItemPrice = currentCartOrderItem.getItemDetail().getPrice();
				itemQuantity = currentCartOrderItem.getQuantity();
				
				//ensure price precision down to the last cents			 
				itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
				totalPrice = totalPrice.add(itemTotalPrice);
			}
			else if(currentCartOrderItem.getStatus().equalsIgnoreCase("OK")){
				//get item price
				currentItemPrice = currentCartOrderItem.getItemDetail().getPrice();
				itemQuantity = currentCartOrderItem.getQuantity();
				
				//ensure price precision down to the last cents			 
				itemTotalPrice = new BigDecimal(currentItemPrice).multiply(new BigDecimal(itemQuantity));			
				totalPrice = totalPrice.add(itemTotalPrice);
			}
		}
		
		NumberFormat numberFormatter;
		numberFormatter = NumberFormat.getInstance(Locale.ENGLISH);
		numberFormatter.setMinimumFractionDigits(2);
		
		//return in used price format
		this.totalPriceOkFormatted = numberFormatter.format(Double.parseDouble(DEFAULT_DECIMAL_FORMAT.format(totalPrice)));
		return this.totalPriceOkFormatted;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	@Override
	@OneToMany(targetEntity=CartOrderItem.class, fetch=FetchType.LAZY, mappedBy="order")
	@Where(clause="valid=1")
	@OrderBy("createdOn DESC")
	public List<CartOrderItem> getItems() {
		return items;
	}
	
	@Override
	public void setItems(List<CartOrderItem> items) {
		this.items = items;
	}
	
	@Basic
	@Column(name = "status")
	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@Basic
	@Column(name = "payment_status")
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Basic
	@Column(name = "shipping_status")
	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	@Basic
	@Column(name = "payment_type")
	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	@Basic
	@Column(name = "shipping_type")
	public ShippingType getShippingType() {
		return shippingType;
	}

	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
	}

	@ManyToOne(targetEntity=MemberShippingInfo.class, fetch=FetchType.LAZY)
	@JoinColumn(name="shipping_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public MemberShippingInfo getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(MemberShippingInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public void setTotalPriceOkFormatted(String totalPriceOkFormatted) {
		this.totalPriceOkFormatted = totalPriceOkFormatted;
	}

	// getter setter for comments
	@Basic
	@Column(name="comments", length=500, nullable=true)
	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

/*
 * Additional fields or attributes (Shipping Information) of CartOrder entity mapped to database
 */
	@Basic
	@Column(name="name", length=250, nullable=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name="address1", length=200, nullable=true)
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Basic
	@Column(name="address2", length=200, nullable=true)
	public String getAddress2() {
		return address2;
	}
	
	@Basic
	@Column(name="info1", length=500, nullable=true)
	public String getInfo1() {
		return info1;
	}
	
	@Basic
	@Column(name="info2", length=500, nullable=true)
	public String getInfo2() {
		return info2;
	}
	
	@Basic
	@Column(name="info3", length=500, nullable=true)
	public String getInfo3() {
		return info3;
	}
	
	@Basic
	@Column(name="info4", length=500, nullable=true)
	public String getInfo4() {
		return info4;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	
	public void setInfo3(String info3) {
		this.info3 = info3;
	}
	
	public void setInfo4(String info4) {
		this.info4 = info4;
	}

	@Basic
	@Column(name="city", length=40, nullable=true)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Basic
	@Column(name="state", length=30, nullable=true)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Basic
	@Column(name="country", length=50, nullable=true)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Basic
	@Column(name="zipCode", length=15, nullable=true)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Basic
	@Column(name="phoneNumber", length=50, nullable=true)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Basic
	@Column(name="emailAddress", length=50, nullable=true)
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setTotalShippingPrice(double totalShippingPrice) {
		this.totalShippingPrice = totalShippingPrice;
	}

	public void setTotalShippingPrice2(double totalShippingPrice2) {
		this.totalShippingPrice2 = totalShippingPrice2;
	}

	@Basic
	@Column(name="totalShippingPrice2", length=50, nullable=true)
	public double getTotalShippingPrice2() {
		return totalShippingPrice2;
	}

	@Basic
	@Column(name="status_notes", length=50, nullable=true)
	public String getStatusNotes() {
		return statusNotes;
	}

	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}

	/**
	 * @param otherCharges the otherCharges to set
	 */
	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}

	/**
	 * @return the otherCharges
	 */
	@Basic
	@Column(name="other_charges", nullable=true)
	public Double getOtherCharges() {
		return otherCharges;
	}
	/**
	 * @param transactionNumber the transactionNumber to set
	 */
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	/**
	 * @return the transactionNumber
	 */
	@Basic
	@Column(name="transactionNumber", nullable=true)
	public String getTransactionNumber() {
		return transactionNumber;
	}
	/**
	 * @param transactionReference the transactionReference to set
	 */


	/**
	 * @param approvalCode the approvalCode to set
	 */
	@Basic
	@Column(name="approvalCode", nullable=true)
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}
	/**
	 * @return the approvalCode
	 */
	public String getApprovalCode() {
		return approvalCode;
	}
	
	/**
	 * @param prescription the prescription to set
	 */
	@Basic
	@Column(name="prescription", nullable=true)
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	/**
	 * @return the prescription
	 */
	public String getPrescription() {
		return prescription;
	}
	
	@Basic
	@Column(name="business", length=200, nullable=true)
	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}
	
	@Basic
	@Column(name="ship_to_billing_address", nullable=true)
	public Boolean getShipToBillingAddress() {
		return shipToBillingAddress;
	}

	public void setShipToBillingAddress(Boolean shipToBillingAddress) {
		this.shipToBillingAddress = shipToBillingAddress;
	}
	
	@Basic
	@Column(name="billing_country", length=50, nullable=true)
	public String getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	
	@Basic
	@Column(name="billing_name", length=250, nullable=true)
	public String getBillingName() {
		return billingName;
	}

	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}
	
	@Basic
	@Column(name="billing_business", length=200, nullable=true)
	public String getBillingBusiness() {
		return billingBusiness;
	}
	
	public void setBillingBusiness(String billingBusiness) {
		this.billingBusiness = billingBusiness;
	}
	
	@Basic
	@Column(name="billing_address1", length=200, nullable=true)
	public String getBillingAddress1() {
		return billingAddress1;
	}
	
	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}
	
	@Basic
	@Column(name="billing_address2", length=200, nullable=true)
	public String getBillingAddress2() {
		return billingAddress2;
	}
	
	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}
	
	@Basic
	@Column(name="billing_city", length=40, nullable=true)
	public String getBillingCity() {
		return billingCity;
	}
	
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	
	@Basic
	@Column(name="billing_state", length=30, nullable=true)
	public String getBillingState() {
		return billingState;
	}
	
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	
	@Basic
	@Column(name="billing_zipCode", length=15, nullable=true)
	public String getBillingZipCode() {
		return billingZipCode;
	}
	
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}
	
	@OneToMany(targetEntity=MemberFile.class, fetch=FetchType.LAZY, mappedBy="cartOrder")
	@Where(clause="valid=1")
	public List<MemberFile> getMemberFileList() {
		return memberFileList;
	}
	public void setMemberFileList(List<MemberFile> memberFileList) {
		this.memberFileList = memberFileList;
	}
	
	
	@Transient
	public String getMemberName() {
		if(member != null) 
			return member.getFullName();
		return "";
	}
	
	
	@Basic
	@Column(name="info5", length=2147483647, nullable=true)
	public String getInfo5() {
		return info5;
	}
	public void setInfo5(String info5) {
		this.info5 = info5;
	}
	
	@Basic
	@Column(name="info6", length=500, nullable=true)
	public String getInfo6() {
		return info6;
	}
	
	public void setInfo6(String info6) {
		this.info6 = info6;
	}
	
	@Transient
	public Properties getProperties(){
		Properties p = new Properties();
		try{
			if(info5 != null){
				p.load(new StringReader(info5));
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return p;
	}
	
	@Basic
	@Column(name="flag_1")
	public Boolean getFlag1() {
		return flag1;
	}
	public void setFlag1(Boolean flag1) {
		this.flag1 = flag1;
	}
	
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="cartOrder")
	@NotFound(action=NotFoundAction.IGNORE)
	public CartOrderPromoCode getCartOrderPromoCode() {
		return cartOrderPromoCode;
	}
	public void setCartOrderPromoCode(CartOrderPromoCode cartOrderPromoCode) {
		this.cartOrderPromoCode = cartOrderPromoCode;
	}
	
}
