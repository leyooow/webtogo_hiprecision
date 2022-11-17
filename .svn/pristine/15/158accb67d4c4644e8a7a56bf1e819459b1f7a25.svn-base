package com.ivant.cms.entity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;
import com.ivant.cms.enums.ReferralStatus;
import com.ivant.cms.interfaces.CompanyAware;



@Entity(name = "Referral")
@Table(name = "referral")
public class Referral extends BaseObject implements Cloneable, CompanyAware {
	
	
	private Company company;
	private Member referredBy;
	private String contactNumber;
	private String firstname;
	private String lastname;
	private String email;
	private Date dateApproved;
	private ReferralStatus status;
	
	private Long requestId;
	
	//for hardcoded rewards store it here "rewards"
	private String reward;
	
	
	
	@ManyToOne(targetEntity = Company.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;
		
	}

	public void setReferredBy(Member referredBy) {
		this.referredBy = referredBy;
	}

	@ManyToOne(targetEntity = Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "referredBy", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Member getReferredBy() {
		return referredBy;
	}

	@Basic
	@Column(name = "contact")
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Basic
	@Column(name = "first_name")
	public String getFirstname() {
		return firstname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	@Basic
	@Column(name = "last_name")
	public String getLastname() {
		return lastname;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	@Basic
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="date_approved", nullable=false)
	public Date getDateApproved() {
		return dateApproved;
	}

	
	public void setStatus(ReferralStatus status) {
		this.status = status;
	}

	@Basic
	@Column(name = "status")
	public ReferralStatus getStatus() {
		return status;
	}
	
	
	
	@Transient
	public String getFullname() {
		StringBuilder sb = new StringBuilder(50);
		if (lastname != null)
			sb.append(lastname + ", ");
		if (firstname != null)
			sb.append(firstname);
		return sb.toString();
	}

	

	public void setReward(String reward) {
		this.reward = reward;
	}
	
	@Basic
	@Column(name = "reward")
	public String getReward() {
		return reward;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	@Basic
	@Column(name = "request_id")
	public Long getRequestId() {
		return requestId;
	}

	

}
