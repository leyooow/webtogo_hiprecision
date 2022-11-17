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

@Entity(name="OSSShippingLocation")
@Table(name="oss_shipping_location")
public class OSSShippingLocation extends BaseObject {
	private OSSShippingArea ossShippingArea;
	private String locationName;
	private String description;
	private Company company;
	private List<OSSShippingRate> ossShippingRate;
	
	@ManyToOne(targetEntity=OSSShippingArea.class, fetch=FetchType.LAZY)
	@JoinColumn(name="oss_shipping_area_id")
	public OSSShippingArea getOssShippingArea() {
		return ossShippingArea;
	}

	public void setOssShippingArea(OSSShippingArea ossShippingArea) {
		this.ossShippingArea = ossShippingArea;
	}	
	
	@Basic
	@Column(name="location_name", nullable=false)
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
	
	@OneToMany(targetEntity = OSSShippingRate.class, mappedBy = "ossShippingLocation", fetch = FetchType.LAZY)
	@Where(clause = "valid=1")
	@OrderBy(clause = "weight asc")
	public List<OSSShippingRate> getOssShippingRate()
	{
		return ossShippingRate;
	}
	
	public void setOssShippingRate(List<OSSShippingRate> ossShippingRate)
	{
		this.ossShippingRate = ossShippingRate;
	}
}
