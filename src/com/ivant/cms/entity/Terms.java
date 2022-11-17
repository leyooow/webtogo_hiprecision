package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity
@Table(name="terms")
public class Terms extends CompanyBaseObject {

	private String ipAddress;
	private User user;
	
	@Basic
	@Column(name="ip_address", length=33, nullable=false)
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@OneToOne(targetEntity = User.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public User getUser() { 
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
