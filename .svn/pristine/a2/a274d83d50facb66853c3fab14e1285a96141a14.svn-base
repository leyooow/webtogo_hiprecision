
package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="mice_philippines_member")
public class MicePhilippinesMember extends CompanyBaseObject{

	private String memberID;
	private String designation;		
	private String companyName;
	private String companyAddress;
	private String country;
	private String accompanyingPerson;

	
	@Basic
	@Column(name = "member_id")
	public String getMemberID() {
		return memberID;
	}
	
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	
	
	@Basic
	@Column(name = "designation")
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}	
	

	@Basic
	@Column(name = "country")
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Basic
	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	@Basic
	@Column(name = "company_address")
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}


	@Basic
	@Column(name = "accompanying_person")
	public String getAccompanyingPerson() {
		return accompanyingPerson;
	}

	public void setAccompanyingPerson(String accompanyingPerson) {
		this.accompanyingPerson = accompanyingPerson;
	}
	

}
