package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="PromoCode")
@Table(name="promo_code")
public class PromoCode extends CompanyBaseObject implements JSONAware{
	
	/** The code for this promo code. */
	private String code;
	
	private Double discount;
	
	/** The starting date of validity of this promo code */
	private DateTime fromDate;
	
	/** The expiration(valid until) date of this promo code */
	private DateTime toDate;
	
	private String note;
	
	private Boolean isDisabled;
	
	private String items;
	
	private String promoName;
	
	private String promoType; // PERCENTAGE or CASH
	
	private Double minimumRequirement;
	
	private String minimumRequirementUnit; // PESOS or BOTTLES
	
	private String membershipLevel;
	
	private Integer maxUsage;
	
	private String brand; // Main, Guest
	
	private Integer currentUsage; // how may times the promocode has been used
	
	private String promoSpecs;
	
	private String promoFor;
	
	@Basic
	@Column(name = "code", length = 5000, nullable = true)
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Basic
	@Column(name = "discount")
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Basic
	@Column( name="from_date", nullable=true)
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(DateTime fromDate) {
		this.fromDate = fromDate;
	}

	@Basic
	@Column( name="to_date", nullable=true)
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getToDate() {
		return toDate;
	}

	public void setToDate(DateTime toDate) {
		this.toDate = toDate;
	}
	
	@Basic
	@Column(name="note", length=255, nullable=true)
	public String getNote()
	{
		return note;
	}
	
	public void setNote(String note)
	{
		this.note = note;
	}
	
	@Basic
	@Column(name = "is_disabled")
	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	
	@Basic
	@Column(name = "items", length = 5000, nullable = true)
	public String getItems() {
		return items;
	}
	
	public void setItems(String items) {
		this.items = items;
	}
	
	@Basic
	@Column(name = "promo_name", length = 5000, nullable = true)
	public String getPromoName() {
		return promoName;
	}
	
	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}
	
	@Basic
	@Column(name = "promo_type")
	public String getPromoType() {
		return promoType;
	}

	public void setPromoType(String promoType) {
		this.promoType = promoType;
	}
	
	@Basic
	@Column(name = "minimum_requirement")
	public Double getMinimumRequirement() {
		return minimumRequirement;
	}

	public void setMinimumRequirement(Double minimumRequirement) {
		this.minimumRequirement = minimumRequirement;
	}
	
	@Basic
	@Column(name = "minimum_requirement_unit")
	public String getMinimumRequirementUnit() {
		return minimumRequirementUnit;
	}

	public void setMinimumRequirementUnit(String minimumRequirementUnit) {
		this.minimumRequirementUnit = minimumRequirementUnit;
	}
	
	@Basic
	@Column(name = "membership_level")
	public String getMembershipLevel() {
		return membershipLevel;
	}

	public void setMembershipLevel(String membershipLevel) {
		this.membershipLevel = membershipLevel;
	}
	
	
	@Basic
	@Column(name = "max_usage")
	public Integer getMaxUsage() {
		return maxUsage;
	}

	public void setMaxUsage(Integer maxUsage) {
		this.maxUsage = maxUsage;
	}
	
	@Basic
	@Column(name = "brand")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Basic
	@Column(name = "current_usage")
	public Integer getCurrentUsage() {
		return currentUsage;
	}

	public void setCurrentUsage(Integer currentUsage) {
		this.currentUsage = currentUsage;
	}
	
	@Basic
	@Column( name="promo_specs" )
	public String getPromoSpecs() {
		return promoSpecs;
	}

	public void setPromoSpecs(String promoSpecs) {
		this.promoSpecs = promoSpecs;
	}
	
	@Basic
	@Column( name="promo_for" )
	public String getPromoFor() {
		return promoFor;
	}

	public void setPromoFor(String promoFor) {
		this.promoFor = promoFor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("code", code);
		json.put("discount", discount);
		json.put("promoType", promoType);
		return json.toJSONString();
	}
}
