package com.ivant.cms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name = "order")
public class Order extends CompanyBaseObject {
	
	private String orderNumber;
	
	private Date orderedOn;
	
	private ShippingInfo shippingInfo;
	private List<OrderItem> orderItemList;
	
	private String payerID;
	private String tokenID;
	
	@Transient
	public double getTotalPrice() {	
		double totalPrice = 0D;
		
		for (OrderItem orderItem : orderItemList) {
			totalPrice += orderItem.getTotalPrice();
		}
		
		return totalPrice;
	}
	
	@Basic
	@Column(name = "token_id")
	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	@Basic
	@Column(name = "payer_id")
	public String getPayerID() {
		return payerID;
	}

	public void setPayerID(String payerID) {
		this.payerID = payerID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ordered_on", nullable = false)
	public Date getOrderedOn() {
		return orderedOn;
	}
	
	@Basic
	@Column(name = "order_number", nullable = false)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "name", column = @Column(name = "si_name")),
			@AttributeOverride(name = "address1", column = @Column(name = "si_address1")),
			@AttributeOverride(name = "address2", column = @Column(name = "si_address2")),
			@AttributeOverride(name = "city", column = @Column(name = "si_city")),
			@AttributeOverride(name = "state", column = @Column(name = "si_state")),
			@AttributeOverride(name = "country", column = @Column(name = "si_country")),
			@AttributeOverride(name = "zipCode", column = @Column(name = "si_zipcode")),
			@AttributeOverride(name = "phoneNumber", column = @Column(name = "si_phone_number")),
			@AttributeOverride(name = "emailAddress", column = @Column(name = "si_email_address"))
	})
	public ShippingInfo getShippingInfo() {
		return shippingInfo;
	}
	
	@OneToMany(
		targetEntity = OrderItem.class, 
		fetch = FetchType.LAZY, 
		mappedBy = "order", 
		cascade = CascadeType.ALL
	)
	@Where(clause = "valid=1")
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	
	public void setOrderedOn(Date orderedOn) {
		this.orderedOn = orderedOn;
	}
	public void setShippingInfo(ShippingInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
}