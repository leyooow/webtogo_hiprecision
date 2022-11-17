package com.ivant.cms.entity;

import java.util.Collections;
import java.util.LinkedList;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.interceptors.FrontCompanyInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.constants.CompanyConstants;

@Entity(name = "MultiPageLanguage")
@Table(name = "multi_page_language")
public class MultiPageLanguage extends BaseObject{
 
	private String description;
	private MultiPage defaultPage;
	private Language language;
	private Long id;
	private String title;
	private String name;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable=false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	@ManyToOne(targetEntity = Language.class, fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language=language;
	}
	
	@ManyToOne(targetEntity = MultiPage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultPage")
	@NotFound(action = NotFoundAction.IGNORE) 
	public MultiPage getDefaultPage() {
		return defaultPage;
	}
	
	public void setDefaultPage(MultiPage defaultPage) {
		this.defaultPage=defaultPage;
	}
	
	
	@Basic
	@Column(name = "description", length=5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Transient
	public void cloneOf(MultiPage multiPage)
	{
		multiPage.setLanguage(null);
		this.setDescription(multiPage.getDescription());
		this.setName(multiPage.getName());
		this.setTitle(multiPage.getTitle());
	}
}
