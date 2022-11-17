package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="CategoryItemPrice")
@Table(name="category_item_price")
public class CategoryItemPrice
		extends CompanyBaseObject
{
	private CategoryItem categoryItem;
	private String name;
	private String code;
	private Double amount;
	private CategoryItemPriceName categoryItemPriceName;
	
	/**
	 * Get the category item.
	 * 
	 * @return the category item
	 */
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_item_id", insertable = true, updatable = true)
	@ForeignKey(name = "FK_CATEGORYITEM_CATEGORYITEMPRICE")
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	/**
	 * Set the category item.
	 * 
	 * @param categoryItem the category item
	 */
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	/**
	 * Get the name.
	 * 
	 * @return the name
	 */
	@Basic
	@Column(name = "name", length = 255, nullable = true)
	public String getName() {
		return name;
	}

	/**
	 * Set the name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the code.
	 * 
	 * @return the code
	 */
	@Basic
	@Column(name = "code", length = 255)
	public String getCode() {
		return code;
	}

	/**
	 * Set the code.
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Get the amount.
	 * 
	 * @return the amount
	 */
	@Basic
	@Column(name = "amount", nullable = false)
	public Double getAmount()
	{
		if(amount == null)
		{
			amount = 0.0;
		}
		return amount;
	}
	
	/**
	 * Set the amount.
	 * 
	 * @param amount
	 */
	public void setAmount(Double amount)
	{
		this.amount = amount;
	}

	/**
	 * Get the category item price name.
	 * 
	 * @return the category item price name
	 */
	@ManyToOne(targetEntity = CategoryItemPriceName.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_item_price_name_id", insertable = true, updatable = true)
	@ForeignKey(name = "FK_CATEGORYITEMPRICENAME_CATEGORYITEMPRICE")
	@NotFound(action = NotFoundAction.IGNORE)
	public CategoryItemPriceName getCategoryItemPriceName() {
		return categoryItemPriceName;
	}

	public void setCategoryItemPriceName(CategoryItemPriceName categoryItemPriceName) {
		this.categoryItemPriceName = categoryItemPriceName;
	}
	
	
}
