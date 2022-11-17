package com.ivant.cms.entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="WebtogoExcludedEmails")
@Table(name="excluded_emails")
public class WebtogoExcludedEmails extends CompanyBaseObject {

	private String email,type;
	
	public WebtogoExcludedEmails() {
		
	}
	
	@Basic
	@Column(name="email", nullable=false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Basic
	@Column(name="type", nullable=true)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
