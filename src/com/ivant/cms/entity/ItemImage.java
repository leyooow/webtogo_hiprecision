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

@Entity(name = "ItemImage")
@Table(name = "item_images")
public class ItemImage extends BaseImage {

	private CategoryItem item;
	private int sortOrder = 1;

	@Basic 
	@Column(name = "sort_order", nullable=false)
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getItem() {
		return item;
	}
	
	public void setItem(CategoryItem item) {
		this.item = item;
	}
}