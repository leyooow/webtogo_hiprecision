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

/**
 * @author Isaac Arenas Pichay
 * @since Apr 8, 2014
 */
@Entity(name = "CategoryItemOtherFieldLanguage")
@Table(name = "category_item_other_field_language")
public class CategoryItemOtherFieldLanguage extends BaseObject implements
		Cloneable {
	private Long id;
	private String content;
	private CategoryItemOtherField defaultCategoryItemOtherField;
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
	@Column(name = "content", length = 8000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(targetEntity = CategoryItemOtherField.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultCategoryItemOtherField")
	@NotFound(action = NotFoundAction.IGNORE) 
	public CategoryItemOtherField getDefaultCategoryItemOtherField() {
		return defaultCategoryItemOtherField;
	}

	public void setDefaultCategoryItemOtherField(
			CategoryItemOtherField defaultCategoryItemOtherField) {
		this.defaultCategoryItemOtherField = defaultCategoryItemOtherField;
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
	public void cloneOf(CategoryItemOtherField categoryItemOtherField){

		categoryItemOtherField.setLanguage(null);
		this.setContent(categoryItemOtherField.getContent());
		
		this.setIsValid(true);
		this.setCreatedOn(new Date());
		this.setUpdatedOn(new Date());
		
	}

}
