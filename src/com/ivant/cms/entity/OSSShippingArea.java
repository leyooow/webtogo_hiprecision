package com.ivant.cms.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity(name="OSSShippingArea")
@Table(name="oss_shipping_area")
public class OSSShippingArea extends BaseObject {
	private String areaName;
	private String description;
	private Company company;
	private List<OSSShippingLocation> ossShippingLocation;
	
	@Basic
	@Column(name="area_name", nullable=false)
	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Basic
	@Column(name="description", nullable=true)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(targetEntity = Company.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	@OneToMany(targetEntity = OSSShippingLocation.class, mappedBy = "ossShippingArea", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy(clause = "createdOn asc")
	public List<OSSShippingLocation> getOssShippingLocation()
	{
		return ossShippingLocation;
	}

	public void setOssShippingLocation(List<OSSShippingLocation> ossShippingLocation)
	{
		this.ossShippingLocation = ossShippingLocation;
	}
}
