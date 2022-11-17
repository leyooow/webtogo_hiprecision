package com.ivant.cms.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="member_shipping_info")
public class MemberShippingInfo extends CompanyBaseObject{
	
	private Member member;
	private ShippingInfo shippingInfo;
	
	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	@Embedded
	public ShippingInfo getShippingInfo() {
		return shippingInfo;
	}
	
	public void setShippingInfo(ShippingInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	
	
}
