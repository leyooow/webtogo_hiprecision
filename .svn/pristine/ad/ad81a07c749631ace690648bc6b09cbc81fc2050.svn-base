package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interfaces.CartAware;
import com.ivant.cms.interfaces.CartItemAware;

@Entity
@Table(name="cart_item")
public class ShoppingCartItem
		extends CompanyBaseObject
		implements CartItemAware
{
	
	private ShoppingCart shoppingCart;	
	private ItemDetail itemDetail;
	private Integer quantity;
	private CategoryItem categoryItem;
	/*
	private Boolean isItemStillAvaliable = Boolean.FALSE;
	private Boolean isOutOfStock;
	*/
	//private Integer items
	
	
	@ManyToOne(targetEntity=ShoppingCart.class, fetch=FetchType.LAZY)
	@JoinColumn(name="cart_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	@Embedded	
	@Override
	public ItemDetail getItemDetail() {
		return itemDetail;
	}
	
	@Override
	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}
	
	@Override
	@Basic
	@Column(name="quantity", nullable=true)
	public Integer getQuantity() {
		if(quantity == null)
		{
			quantity = 0;
		}
		return quantity;
	}
	
	@Override
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public int hashCode() {		
		return itemDetail.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ShoppingCartItem))
			return false;
		
		//shopping cart items are equal if they have the same item detail
		return ((ShoppingCartItem)obj).getItemDetail() == getItemDetail();
	}
/*
	public void setIsItemStillAvaliable(Boolean isItemStillAvaliable) {
		this.isItemStillAvaliable = isItemStillAvaliable;
	}
	@Transient
	public Boolean getIsItemStillAvaliable() {
		return isItemStillAvaliable;
	}


	public void setIsOutOfStock(Boolean isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}
	@Transient
	public Boolean getIsOutOfStock() {
		return isOutOfStock;
	}
	*/
	
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

    @SuppressWarnings("rawtypes")
	@Override
    @Transient
	public CartAware getParent()
	{
		return getShoppingCart();
	}

}
