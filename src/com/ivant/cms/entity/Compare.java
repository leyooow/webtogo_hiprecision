package com.ivant.cms.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interfaces.MemberAware;

@Entity(name="Compare")
@Table(name="compare")
public class Compare extends CompanyBaseObject implements MemberAware {
	private CategoryItem categoryItem;
	private Member member;
	
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
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}

}
