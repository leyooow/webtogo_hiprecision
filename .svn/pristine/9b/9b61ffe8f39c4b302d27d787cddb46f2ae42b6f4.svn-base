package com.ivant.cms.entity;

import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interfaces.CartAware;
import com.ivant.cms.interfaces.CartItemAware;

@Entity
@Table(name="cart_order_item")
public class CartOrderItem
		extends CompanyBaseObject
		implements CartItemAware
{
	
	private CartOrder order;	
	private ItemDetail itemDetail;
	private OtherDetail otherDetail;
	private Integer quantity;
	private String status;//status added 
	private int downloads;
	private Map<String, String> itemDetailMap;
	private CategoryItem categoryItem;
	
	@ManyToOne(targetEntity=CartOrder.class, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public CartOrder getOrder() {
		return order;
	}
	
	public void setOrder(CartOrder order) {
		this.order = order;
	}
	
	@Override
	@Embedded
	public ItemDetail getItemDetail() {
		return itemDetail;
	}
	
	@Override
	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}
	
	@ManyToOne(targetEntity = OtherDetail.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "other_detail", nullable=true)
	@NotFound(action = NotFoundAction.IGNORE) 
	public OtherDetail getOtherDetail() {
		return otherDetail;
	}
	
	public void setOtherDetail(OtherDetail otherDetail) {
		this.otherDetail = otherDetail;
	}

	@ManyToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "category_item_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	@Override
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	@Override
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	@Override
	@Basic
	@Column(name="quantity", nullable=true)
	public Integer getQuantity() {
		return quantity;
	}
	
	@Override
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * @param downloads the downloads to set
	 */
	@Basic
	@Column(name="downloads")
	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

	/**
	 * @return the downloads
	 */
	public int getDownloads() {
		return downloads;
	}
	
	public void setItemDetailMap(Map<String, String> itemDetailMap) {
		// TODO Auto-generated method stub
		this.itemDetailMap = itemDetailMap;
	}
	
    @CollectionOfElements
    public Map<String, String> getItemDetailMap() {
    	return itemDetailMap;
    }
	
    @SuppressWarnings("rawtypes")
	@Override
    @Transient
    public CartAware getParent()
    {
    	return getOrder();
    }
}
