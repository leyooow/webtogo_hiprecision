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

@Entity(name="BrandImage")
@Table(name="brand_images")
public class BrandImage extends BaseImage {
	
	private Brand brand;
	private Integer sortOrder = 1;

	@ManyToOne(targetEntity = Brand.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id", nullable=false)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Brand getBrand() {
		return brand;
	} 

	public void setBrand(Brand brand) {
		this.brand = brand;
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

}
