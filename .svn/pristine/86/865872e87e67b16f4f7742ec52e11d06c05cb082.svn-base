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

@Entity(name="CategoryItemComponent")
@Table(name="category_item_component")
public class CategoryItemComponent 
	extends BaseObject{

	private CategoryItem categoryItem;
	private Component component;
	private Double value;
	private String variable;
	private String equation;
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_item")
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getCategoryItem() {
		return categoryItem;
	}
	public void setCategoryItem(CategoryItem categoryItem) {
		this.categoryItem = categoryItem;
	}
	
	@ManyToOne(targetEntity = Component.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "component")
	@NotFound(action = NotFoundAction.IGNORE) 
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	
	@Basic
	@Column(name="value")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@Basic
	@Column(name="variable")
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	@Basic
	@Column(name="equation")
	public String getEquation() {
		if(equation == null && value != null) {
			return "AREA / "+value;
		}
		return equation;
	}
	public void setEquation(String equation) {
		this.equation = equation;
	}
	
	
	
}
