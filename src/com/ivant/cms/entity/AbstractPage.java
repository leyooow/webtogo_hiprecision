package com.ivant.cms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.entity.interfaces.Page;

@MappedSuperclass
public abstract class AbstractPage extends CompanyBaseObject implements Page {

	protected String name;
	protected String title;
	private String jsp;
	private User createdBy;
	private Date validFrom;
	private Date validTo;
	private boolean hidden;
	private boolean loginRequired;
	private int sortOrder = 0;
	private Date datePosted;
	protected transient Language language;
	//setting for HTML META tags
	private String meta_keywords;
	private String meta_title;
	private String meta_description;
	private String meta_author;
	private String meta_copyright;
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	@Transient
	public Language getLanguage() {
		return language;
	}

	
	@Basic
	@Column(name = "meta_author", nullable = true)
	public String getMeta_author() {
		return meta_author;
	}

	public void setMeta_author(String meta_author) {
		this.meta_author = meta_author;
	}
	
	@Basic
	@Column(name = "meta_copyright", nullable = true)
	public String getMeta_copyright() {
		return meta_copyright;
	}

	public void setMeta_copyright(String meta_copyright) {
		this.meta_copyright = meta_copyright;
	}
	
	@Basic
	@Column(name = "meta_keywords", nullable = true, columnDefinition="text")
	public String getMeta_keywords() {
		return meta_keywords;
	}

	public void setMeta_keywords(String meta_keywords) {
		this.meta_keywords = meta_keywords;
	}
	
	@Basic
	@Column(name = "meta_title", length = 50, nullable = true, columnDefinition="text")
	public String getMeta_title() {
		return meta_title;
	}

	public void setMeta_title(String meta_title) {
		this.meta_title = meta_title;
	}
	
	@Basic
	@Column(name = "meta_description", nullable = true, columnDefinition="text")
	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}

	
	
	public AbstractPage() {
		hidden = false;
		loginRequired = false;
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
	@Column(name="name", length=255, nullable=false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_posted")
	public Date getDatePosted() {
		return datePosted;
	} 
	
	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}
	
	@Basic
	@Column(name="jsp", length=30, nullable=true)
	public String getJsp() {
		return jsp;
	}
	
	public void setJsp(String jsp) {
		this.jsp = jsp;
	}
	
	@ManyToOne(targetEntity = User.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public User getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="valid_from")
	public Date getValidFrom() {
		return validFrom;
	}
	
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="valid_to")
	public Date getValidTo() {
		return validTo;
	}
	
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	
	@Basic
	@Column(name="sort_order")
	public int getSortOrder() {
		return sortOrder; 
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Basic
	@Column(name="hidden", nullable=false)
	public boolean getHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Basic
	@Column(name="login_required", nullable=false)
	public boolean getLoginRequired() {
		return loginRequired;
	}
	
	public void setLoginRequired(boolean loginRequired) {
		this.loginRequired = loginRequired;
	}
}
