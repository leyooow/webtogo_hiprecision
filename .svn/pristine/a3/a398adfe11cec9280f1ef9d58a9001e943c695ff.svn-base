package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interceptors.CompanyInterceptor;

@Entity
@Table(name="pre_order_item")
public class PreOrderItem extends CompanyBaseObject {
	
	private PreOrder preOrder;
	private CategoryItem categoryItem;
	private Integer quantity;
	
	CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	HttpServletRequest request;
	ServletContext servletContext;

	@ManyToOne(targetEntity=PreOrder.class, fetch=FetchType.LAZY)
	@JoinColumn(name="pre_order_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public PreOrder getPreOrder() {
		return preOrder;
	}	

	public void setPreOrder(PreOrder preOrder) {
		this.preOrder = preOrder;
	}
	
	@ManyToOne(targetEntity=CategoryItem.class, fetch=FetchType.LAZY)
	@JoinColumn(name="category_item_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem()
	{
		return categoryItem;
	}
	
	public void setCategoryItem(CategoryItem categoryItem)
	{
		this.categoryItem = categoryItem;
	}

	@Basic
	@Column(name="quantity", nullable=true)
	public Integer getQuantity() {
		return quantity;
	}	

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}	
}
