package com.ivant.cms.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name = "SinglePageLanguage")
@Table(name = "single_page_language")
public class SinglePageLanguage extends BaseObject implements Cloneable {

	private String subTitle;
	private String title;
	private String name;
	private String content;
	private Language language;
	private SinglePage defaultPage;
	private Long id;
	//private MultiPageLanguage parent;

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
	@Column(name="subtitle", nullable=true)
	public String getSubTitle() {
		return subTitle;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	@Basic
	@Column(name="content", length=2147483647, nullable=false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
		

	@ManyToOne(targetEntity = SinglePage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultPage")
	@NotFound(action = NotFoundAction.IGNORE) 
	public SinglePage getDefaultPage() {
		return defaultPage;
	}
	
	public void setDefaultPage(SinglePage defaultPage) {
		this.defaultPage=defaultPage;
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

	public void setTitle(String title) {
		this.title = title;
	}
	@Basic
	@Column(name="title", nullable=true)
	public String getTitle() {
		return title;
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
	public void cloneOf(SinglePage singlePage){
		//SinglePageLanguage spLanguage = new SinglePageLanguage();
		//this.setId(singlePage.getSinglePageLanguage().getId());
		singlePage.setLanguage(null);
		this.setContent(singlePage.getContent());
		this.setName(singlePage.getName());
		this.setSubTitle(singlePage.getSubTitle());
		this.setTitle(singlePage.getTitle());
		
		
		this.setIsValid(true);
		this.setCreatedOn(new Date());
		this.setUpdatedOn(new Date());
		//return spLanguage;
	}
}
