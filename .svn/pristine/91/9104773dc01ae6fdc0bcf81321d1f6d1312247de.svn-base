package com.ivant.cms.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.BaseObject;

@Entity
@Table(name="rates")
public class Rates extends BaseObject {
	private String name;
	private Company company;
	private double value;
	private Rates parent;
	private Date date;
	private User user;
	
	@Basic
	@Column(name = "name", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name = "date", nullable = true)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
		
	@Basic
	@ManyToOne()
	@JoinColumn(name = "company_id", nullable = false)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Basic
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "updated_by", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Basic
	@Column(name = "value")
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	@ManyToOne(targetEntity = Rates.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "parent", nullable=true)
	@NotFound(action = NotFoundAction.IGNORE) 
	public Rates getParent() {
		return parent;
	}

	public void setParent(Rates parent) {
		this.parent = parent;
	}
	
}