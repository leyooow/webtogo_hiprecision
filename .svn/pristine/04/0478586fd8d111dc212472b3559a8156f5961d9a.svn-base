package com.ivant.cms.entity.interfaces;

import java.util.Date;

import javax.persistence.Transient;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.PageType;

public interface Page {
	
	public Company getCompany();
	public void setCompany(Company company);
	
	public String getName();
	public void setName(String name);

	public String getTitle();
	public void setTitle(String title);
	
	public String getJsp();
	public void setJsp(String jsp);
	 
	public User getCreatedBy();
	public void setCreatedBy(User createdBy);
	
	public Date getValidFrom();
	public void setValidFrom(Date date);
	
	public Date getValidTo();
	public void setValidTo(Date date);
	
	public boolean getHidden();
	public void setHidden(boolean hidden);
	
	public boolean getLoginRequired();
	public void setLoginRequired(boolean loginRequired);
	
	public int getSortOrder();
	public void setSortOrder(int sortOrder);
	
	public Date getDatePosted();
	public void setDatePosted(Date date);
	
	public void setLanguage(Language language);
	public Language getLanguage();
	
	public PageType getPageType();

}
