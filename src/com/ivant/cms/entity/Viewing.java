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

import com.ivant.cms.entity.baseobjects.BaseObject;

/**
 * 
 * @author Anjerico D. Gutierrez
 * @since October 21, 2015
 */

@Entity(name = "Viewing")
@Table(name = "viewing")
public class Viewing extends BaseObject implements Cloneable{
	private Company company;
	private CategoryItem categoryItem;
	private Member member;
	private String remarks;
	
	public void setCompany(Company company){
		this.company = company;
	}
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}	
	
	public void setCategoryItem(CategoryItem categoryItem){
		this.categoryItem = categoryItem;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_item_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getMember() {
		return member;
	}
	
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	
	@Basic
	@Column(name = "remarks", nullable = true)
	public String getRemarks() {
		return remarks;
	}
	
	
}
