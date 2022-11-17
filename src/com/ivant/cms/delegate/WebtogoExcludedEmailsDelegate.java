package com.ivant.cms.delegate;

import org.hibernate.criterion.Order;

import com.ivant.cms.db.WebtogoExcludedEmailsDAO;
import com.ivant.cms.entity.Brand;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.WebtogoExcludedEmails;
import com.ivant.cms.entity.list.ObjectList;

public class WebtogoExcludedEmailsDelegate extends AbstractBaseDelegate<WebtogoExcludedEmails, WebtogoExcludedEmailsDAO>{

private static WebtogoExcludedEmailsDelegate instance = new WebtogoExcludedEmailsDelegate();
	
	public static WebtogoExcludedEmailsDelegate getInstance() {
		return instance;
	}
	
	public WebtogoExcludedEmailsDelegate() {
		super(new WebtogoExcludedEmailsDAO());
	}
	
	public WebtogoExcludedEmails find(String email) {
		return dao.find(email);
	}
	
	public ObjectList<WebtogoExcludedEmails> findAllByIdAndGroupName(Company company, String group) {
		return dao.findAllByIdAndGroupName(company, group);
	}
	
	public ObjectList<WebtogoExcludedEmails> findAll(Company company) {
		return dao.findAll(company);
	}
	
	public ObjectList<WebtogoExcludedEmails> findAll(Company company, Group group) {
		return dao.findAll(company, group);
	}
	
	public ObjectList<WebtogoExcludedEmails> findAll(Company company, Group group, Order...orders) {
		return dao.findAll(company, group, orders);
	}
	
	public ObjectList<WebtogoExcludedEmails> findAll(Company company, boolean showAll, Order...orders) {
		return dao.findAll(company, showAll, orders);
	}
}
