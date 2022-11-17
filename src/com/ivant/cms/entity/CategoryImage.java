package com.ivant.cms.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity(name = "CategoryImage")
@Table(name = "category_images")
public class CategoryImage extends BaseImage {
	private Category category;
	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}