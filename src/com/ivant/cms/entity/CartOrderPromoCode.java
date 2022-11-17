package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name="CartPromoCode")
@Table(name="cart_order_promo_code")
public class CartOrderPromoCode extends BaseObject{
	
	private PromoCode promoCode;
	private Double appliedDiscount;
	private CartOrder cartOrder;
	
	public CartOrderPromoCode() {
		super();
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="promo_code_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public PromoCode getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(PromoCode promoCode) {
		this.promoCode = promoCode;
	}
	
	
	@Basic
	@Column(name="applied_discount")
	public Double getAppliedDiscount() {
		return appliedDiscount;
	}

	public void setAppliedDiscount(Double appliedDiscount) {
		this.appliedDiscount = appliedDiscount;
	}
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cart_order_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public CartOrder getCartOrder() {
		return cartOrder;
	}

	public void setCartOrder(CartOrder cartOrder) {
		this.cartOrder = cartOrder;
	}
	
	
}
