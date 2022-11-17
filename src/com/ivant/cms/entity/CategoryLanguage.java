package com.ivant.cms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name = "CategoryLanguage")
@Table(name = "category_language")
public class CategoryLanguage extends BaseObject implements Cloneable {	
	private String name;
	private String description;
	private Language language;
	private Category defaultCategory;
	private Long id;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable=false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name = "description", length=2147483647)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultCategory")
	@NotFound(action = NotFoundAction.IGNORE) 
	public Category getDefaultCategory() {
		return defaultCategory;
	}

	public void setDefaultCategory(Category defaultCategory) {
		this.defaultCategory = defaultCategory;
	}

	@ManyToOne(targetEntity = Language.class, fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language=language;
	}


	public void setName(String name) {
		this.name = name;
	}
	@Basic
	@Column(name="name", nullable=true)
	public String getName() {
		return name;
	}
	
	@Transient
	public void cloneOf(Category category){

		category.setLanguage(null);
		this.setDescription(category.getDescription());
		this.setName(category.getName());
		
		this.setIsValid(true);
		this.setCreatedOn(new Date());
		this.setUpdatedOn(new Date());
		
	}
}
