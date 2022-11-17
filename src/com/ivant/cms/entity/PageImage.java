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

@Entity(name = "PageImages")
@Table(name = "page_images")
public class PageImage extends BaseImage {

	private BasePage page;
	private String pageType;
	private Integer sortOrder = 1;
		
	@ManyToOne(targetEntity = BasePage.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "page_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public BasePage getPage() {
		return page;
	}
	public void setPage(BasePage page) {
		this.page = page;
	}
	@Basic
	@Column(name="page_type", length=10, nullable=false)
	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	
	@Basic 
	@Column(name = "sort_order")
	public Integer getSortOrder() {
		if(this.sortOrder == null) {
			this.sortOrder = Integer.valueOf(1);
		}
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	 
	public PageImage() { 
	}
}
