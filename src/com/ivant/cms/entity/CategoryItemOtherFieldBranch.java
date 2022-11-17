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

@Entity(name="CategoryItemOtherFieldBranch")
@Table(name="category_item_other_field_branch")
public class CategoryItemOtherFieldBranch
		extends CompanyBaseObject implements Cloneable
{

	private CategoryItem categoryItem;
	private CategoryItem categoryItemBranch;
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
	@JoinColumn(name = "category_item_id_branch", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getCategoryItemBranch() {
		return categoryItemBranch;
	}
	
	public void setCategoryItemBranch(CategoryItem categoryItemBranch) {
		this.categoryItemBranch = categoryItemBranch;
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
	protected CategoryItemOtherFieldBranch clone() throws RuntimeException {
		CategoryItemOtherFieldBranch categoryItemOtherField = null;
		try {
			categoryItemOtherField = (CategoryItemOtherFieldBranch)super.clone();
		}
		catch(CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
		return categoryItemOtherField;
	}
	
	

	
}
