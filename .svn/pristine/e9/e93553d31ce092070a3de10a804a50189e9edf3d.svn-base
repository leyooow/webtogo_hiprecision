package com.ivant.cms.action.admin;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.Encryption;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateUserPasswordAction extends ActionSupport implements UserAware, CompanyAware {

	private static final long serialVersionUID = -1258178187963225973L;
	private static final Logger logger = Logger.getLogger(UpdateUserPasswordAction.class);
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	
	private String userId;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private User user;
	private Company company;
	
	@Override
	public String execute() throws Exception {
		final User user = userDelegate.find(Long.parseLong(userId));
		
		if(Encryption.hash(oldPassword).equals(user.getPassword())) {
			if(newPassword.equals(confirmPassword)) {
				
				user.setPassword(Encryption.hash(newPassword));
				userDelegate.update(user);
				addActionMessage(getText("UserSetting.passwordUpdated"));
				
				lastUpdatedDelegate.saveLastUpdated(company);
				
				return SUCCESS;
			}
		} 
		addActionError(getText("UserSetting.passwordUpdateFailed"));
		return ERROR;
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
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
