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

@Entity(name="CategoryItemOtherFieldMachine")
@Table(name="category_item_other_field_machine")
public class CategoryItemOtherFieldMachine
		extends CompanyBaseObject implements Cloneable
{

	private CategoryItem categoryItem;
	private CategoryItem categoryItemMachine;
	private String content;
	
	
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_item_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_item_id_machine", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getCategoryItemMachine() {
		return categoryItemMachine;
	}
	
	public void setCategoryItemMachine(CategoryItem categoryItemMachine) {
		this.categoryItemMachine = categoryItemMachine;
	}
	
	/**
	 * Get the content.
	 * 
	 * @return the content
	 */
	@Basic
	@Column(name = "content", length=255)
	public String getContent()
	{
		return content;
	}
	
	/**
	 * Set the content.
	 * 
	 * @param content
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
	
	@Override
	protected CategoryItemOtherFieldMachine clone() throws RuntimeException {
		CategoryItemOtherFieldMachine categoryItemOtherField = null;
		try {
			categoryItemOtherField = (CategoryItemOtherFieldMachine)super.clone();
		}
		catch(CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
		return categoryItemOtherField;
	}
	
	

	
}
