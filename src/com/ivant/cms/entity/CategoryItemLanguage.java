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

@Entity(name = "CategoryItemLanguage")
@Table(name = "category_item_language")
public class CategoryItemLanguage extends BaseObject implements Cloneable {
	private Long id;
	private String name;
	private String shortDescription;
	private String description;
	private String otherDetails;
	private CategoryItem defaultCategoryItem;
	private Language language;
	
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
	@Column(name="name", nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Basic
	@Column(name="short_description", length=4000)
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	@Basic
	@Column(name = "description", length=2147483647)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Basic
	@Column(name = "other_details", length=2147483647)
	public String getOtherDetails() {
		return otherDetails;
	}
	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}
	
	@ManyToOne(targetEntity = CategoryItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultCategoryItem")
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItem getDefaultCategoryItem() {
		return defaultCategoryItem;
	}
	public void setDefaultCategoryItem(CategoryItem defaultCategoryItem) {
		this.defaultCategoryItem = defaultCategoryItem;
	}
	
	@ManyToOne(targetEntity = Language.class, fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")	
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	@Transient
	public void cloneOf(CategoryItem categoryItem){

		categoryItem.setLanguage(null);
		this.setDescription(categoryItem.getDescription());
		this.setShortDescription(categoryItem.getShortDescription());
		this.setOtherDetails(categoryItem.getOtherDetails());
		this.setName(categoryItem.getName());
		
		this.setIsValid(true);
		this.setCreatedOn(new Date());
		this.setUpdatedOn(new Date());
		
	}
	
}
