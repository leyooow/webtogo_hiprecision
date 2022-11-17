package com.ivant.cms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity(name = "OrderItemFile")
@Table(name = "order_item_file")
public class OrderItemFile extends AbstractFile {

	private CategoryItem item; 
	private boolean disabled = false;
	private long itemFileID;
	private long cartOrderItemID;
	private Date expiryDate;

	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "real_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getItem() {
		return item;
	}

	public void setItem(CategoryItem item) {
		this.item = item;
	}


	@Basic
	@Column(name="is_disabled")
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @param itemFileID the itemFileID to set
	 */
	public void setItemFileID(long itemFileID) {
		this.itemFileID = itemFileID;
	}

	/**
	 * @return the itemFileID
	 */
	@Basic
	@Column(name="item_file_id")
	public long getItemFileID() {
		return itemFileID;
	}

	/**
	 * @param cartOrderItemID the cartOrderItemID to set
	 */
	public void setCartOrderItemID(long cartOrderItemID) {
		this.cartOrderItemID = cartOrderItemID;
	}

	/**
	 * @return the cartOrderItemID
	 */
	@Basic
	@Column(name="cart_order_item_id", nullable = false)
	public long getCartOrderItemID() {
		return cartOrderItemID;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the expiryDate
	 */
	@Basic
	@Column(name="expiry_date")
	public Date getExpiryDate() {
		if(expiryDate == null)
			return new Date();
		return expiryDate;
	}

}