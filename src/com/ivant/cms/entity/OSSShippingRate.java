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

@Entity(name="OSSShippingRate")
@Table(name="oss_shipping_rate")
public class OSSShippingRate extends BaseObject {
	private OSSShippingLocation ossShippingLocation;
	private Double weight;
	private Double rate;
	private Company company;
	
	@ManyToOne(targetEntity=OSSShippingLocation.class, fetch=FetchType.LAZY)
	@JoinColumn(name="oss_shipping_location_id")
	public OSSShippingLocation getOssShippingLocation()
	{
		return ossShippingLocation;
	}
	
	public void setOssShippingLocation(OSSShippingLocation ossShippingLocation)
	{
		this.ossShippingLocation = ossShippingLocation;
	}

	@Basic
	@Column(name="weight", nullable=false)
	public Double getWeight() {
		return weight;
	}
	
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@Basic
	@Column(name="rate", nullable=false)
	public Double getRate() {
		return rate;
	}
	
	public void setRate(Double rate) {
		this.rate = rate;
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
}
