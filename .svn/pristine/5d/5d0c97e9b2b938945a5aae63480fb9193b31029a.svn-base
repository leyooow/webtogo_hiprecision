package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interfaces.MemberAware;

@Entity(name = "WishlistType")
@Table(name="wishlist_type")
public class WishlistType extends CompanyBaseObject implements MemberAware {
	private String name;
	private String description;
	private Member member;
	//private List<Wishlist> wishlists;
	
	@ManyToOne(targetEntity = Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	
	@Override
	public void setMember(Member member) {
		this.member = member;
	}
	
	@Basic
	@Column(name = "name")
	public String getName() {
		return name.replace("'", "&rsquo;").replace("’", "&rsquo;");
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	/*
	@OneToMany(targetEntity = Wishlist.class, fetch = FetchType.LAZY, mappedBy = "wishlistType")
	@Where(clause = "valid = 1")
	@OrderBy("id asc")
	public List<Wishlist> getWishlists() {
		return wishlists;
	}
	
	public void setWishlists(List<Wishlist> wishlists){
		this.wishlists = wishlists;
	}
	*/
}
