package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interfaces.MemberAware;

@Entity(name="Wishlist")
@Table(name="wishlist")
public class Wishlist extends CompanyBaseObject implements MemberAware{
	
	private CategoryItem item;
	private Member member;
	private Integer quantity;
	private WishlistType wishlistType;
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public CategoryItem getItem() {
		return item;
	}


	public void setItem(CategoryItem item) {
		this.item = item;
	}

	@ManyToOne(targetEntity = Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}


	@Override
	public void setMember(Member member) {
		this.member = member;
	}

	
	@Basic
	@Column(name = "quantity")
	public Integer getQuantity() {
		if(quantity == null) return 1;
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		if(quantity == null) this.quantity = 1;
		else if(quantity < 1) this.quantity = 1;
		else this.quantity = quantity;
	}
	
	@ManyToOne(targetEntity = WishlistType.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "wishlist_type_id", nullable = true)
	@NotFound(action=NotFoundAction.IGNORE)
	public WishlistType getWishlistType() {
		return wishlistType;
	}
	
	public void setWishlistType(WishlistType wishlistType){
		this.wishlistType = wishlistType;
	}
	
}
