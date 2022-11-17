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

@Entity(name = "CategoryFile")
@Table(name = "category_file")
public class CategoryFile extends AbstractFile {

	private Category category; 
	private boolean disabled = false;
	private String title;
	private String caption;
	private String description; 
	private Integer sortOrder = 1;


	@Basic
	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Basic
	@Column(name="caption", length=65536)
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}


	@Basic
	@Column(name="description", length=65536)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	@Basic
	@Column(name="is_disabled")
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	@Basic 
	@Column(name = "sort_order")
	public Integer getSortOrder() {
		if(this.sortOrder == null) {
			this.sortOrder = Integer.valueOf(1);
		}
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}