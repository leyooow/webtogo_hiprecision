package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.ivant.cms.enums.PageType;

@Entity(name="BasePage")
@Table(name="pages")
@Inheritance(strategy = InheritanceType.JOINED)
public class BasePage extends AbstractPage {

	private List<PageImage> images;
	private List<PageImage> sortedImages;
	
	@OneToMany(targetEntity = PageImage.class, mappedBy = "page", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("id asc")
	public List<PageImage> getImages() {
		return images;
	}
	 
	public void setImages(List<PageImage> images) {
		this.images = images;
	}
	
	@OneToMany(targetEntity = PageImage.class, mappedBy = "page", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy("sortOrder asc")
	public List<PageImage> getSortedImages() {
		return sortedImages;
	}
	 
	public void setSortedImages(List<PageImage> images) {
		this.sortedImages = images;
	}

	public String providePageType() {
		return "default";
	}
	
	@Transient
	@Override
	public PageType getPageType()
	{
		return PageType.DEFAULT;
	}

}
