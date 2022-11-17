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

@Entity(name = "FormPageLanguage")
@Table(name = "form_page_language")
public class FormPageLanguage extends BaseObject implements Cloneable {
	
	private Long id;
	private String name;
	private String title;
	private String topContent;
	private String bottomContent;
	private FormPage defaultFormPage;
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
	@Column(name="title", length=255)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Basic
	@Column(name="top_content", length=65536, nullable=false)
	public String getTopContent() {
		return topContent;
	}
	public void setTopContent(String topContent) {
		this.topContent = topContent;
	}
	
	@Basic
	@Column(name="bottom_content", length=65536, nullable=false)
	public String getBottomContent() {
		return bottomContent;
	}
	public void setBottomContent(String bottomContent) {
		this.bottomContent = bottomContent;
	}
	
	@ManyToOne(targetEntity = FormPage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultFormPage")
	@NotFound(action = NotFoundAction.IGNORE) 
	public FormPage getDefaultFormPage() {
		return defaultFormPage;
	}
	public void setDefaultFormPage(FormPage defaultFormPage) {
		this.defaultFormPage = defaultFormPage;
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
	public void cloneOf(FormPage formPage){

		formPage.setLanguage(null);
		this.setTopContent(formPage.getTopContent());
		this.setBottomContent(formPage.getBottomContent());
		this.setTitle(formPage.getTitle());
		this.setName(formPage.getName());
		
		this.setIsValid(true);
		this.setCreatedOn(new Date());
		this.setUpdatedOn(new Date());
		
	}
	
}
