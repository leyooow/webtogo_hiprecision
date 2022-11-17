package com.ivant.cms.action.admin;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;

public class SettingsAction implements Action, UserAware {

	private User user;
	private Company company;
	
	public String execute() throws Exception {
		company = user.getCompany();
		return Action.INPUT;
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
}
