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

@Entity(name="ItemAttribute")
@Table(name="item_attribute")
public class ItemAttribute extends BaseObject{
	
	private String value;
	private boolean disabled;
	private Attribute attribute;
	private CategoryItem categoryItem;
	
	public ItemAttribute() {
	}
	
	@Basic
	@Column(name = "value", length=2147483647)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Basic
	@Column(name = "disabled", length=2147483647)	
	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@ManyToOne(targetEntity = Attribute.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "attribute_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
}
