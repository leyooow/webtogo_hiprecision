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

@Entity(name="Component")
@Table(name="component")
public class Component 
	extends CompanyBaseObject{

	public String name;
	public ComponentCategory category;
	
	
	@Basic
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@ManyToOne(targetEntity = ComponentCategory.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@ForeignKey(name = "FK_COMPONENT_CATEGORY")
	@NotFound(action = NotFoundAction.IGNORE)
	public ComponentCategory getCategory() {
		return category;
	}

	public void setCategory(ComponentCategory category) {
		this.category = category;
	}
	
	
}
