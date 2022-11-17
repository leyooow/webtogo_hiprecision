package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;
import com.ivant.cms.enums.AppearanceType;
import com.ivant.cms.enums.FormType;

@Entity(name="Language")
@Table(name="language")
public class Language
		extends CompanyBaseObject
{

	private String language;
	

	@Basic
	@Column(name = "language")
	public String getLanguage()
	{		
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}


}
