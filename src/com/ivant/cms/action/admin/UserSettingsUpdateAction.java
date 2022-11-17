package com.ivant.cms.action.admin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;

public class UserSettingsUpdateAction extends ActionSupport implements SessionAware, UserAware, CompanyAware {

	private static final long serialVersionUID = -1454592623983679252L;
	
	private static final Logger logger = Logger.getLogger(UserSettingsUpdateAction.class);
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	
	private String userId;
	private String email;
	private String firstname;
	private String lastname;
	private String itemsPerPage;
	private Map session;
	
	public String getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(String itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	private User user;
	private Company company;

	@Override
	public String execute() throws Exception {
		final User user = userDelegate.find(Long.valueOf(userId));
		if(!user.equals(this.user)) {
			addActionError(getText("UserSetting.informationUpdateFailed"));
			return ERROR;
		} 
		
		user.setEmail(email);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setItemsPerPage(Integer.parseInt(itemsPerPage));
		//System.out.println("user.setItemsPerPage " + user.getItemsPerPage());
		userDelegate.update(user);
		this.user = user;
		
		addActionMessage(getText("UserSetting.informationUpdated"));
		
		if (company!=null)
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return SUCCESS;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}	
	
	public void setSession(Map arg0) {
		this.session = arg0;
	}
} 
