package com.ivant.cms.entity.baseobjects;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.Company;
import com.ivant.cms.interfaces.CompanyAware;

/**
 * The super class of entities that must belong to a company.
 * 
 * The company entries in this class <b>cannot</b> contain <b>null</b> value.
 * 
 * @author Jobert
 * @author Lim, Vincent Ray U.
 */
@MappedSuperclass
public abstract class CompanyBaseObject extends BaseObject implements CompanyAware {
	private Company company;

	/*
	 * (non-Javadoc)
	 * @see com.ivant.cms.entity.interfaces.CompanyAware#getCompany()
	 */
	@ManyToOne(targetEntity = Company.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ivant.cms.entity.interfaces.CompanyAware#setCompany(com.ivant.cms.entity.Company)
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
}
