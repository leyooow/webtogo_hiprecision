package com.ivant.cms.entity;

import java.util.List;

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
import com.ivant.cms.interfaces.CompanyAware;

/**
 * 
 * @author Anjerico D. Gutierrez
 * @since October 12, 2015
 */
@Entity(name = "CategoryItemHistory")
@Table(name = "category_item_history")
public class CategoryItemHistory extends BaseObject implements Cloneable, CompanyAware{
	
	private Company company;
	private CategoryItem categoryItem;
	private User user;
	private String remarks;
	
	
	@Override
	public void setCompany(Company company) {
		// TODO Auto-generated method stub
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
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public User getUser() {
		return user;
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
