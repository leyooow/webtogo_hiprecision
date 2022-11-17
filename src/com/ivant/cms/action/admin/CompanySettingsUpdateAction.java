package com.ivant.cms.action.admin;

import org.apache.log4j.Logger;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.LastUpdatedDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;

public class CompanySettingsUpdateAction extends ActionSupport implements UserAware, CompanyAware {
	
	private static final long serialVersionUID = -2631175369856333954L;
	private static final Logger logger = Logger.getLogger(CompanySettingsUpdateAction.class);
	
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private static final LastUpdatedDelegate lastUpdatedDelegate = LastUpdatedDelegate.getInstance();
	
	private long companyId;
	private String nameEditable;
	private String email;
	private String address;
	private String address2;
	private String state;
	private String phone;
	private String cellphone;
	private String fax;
	private String contactPerson;
	private String title;
	

	private User user;
	private Company company;
	
	@Override
	public String execute() throws Exception {
		if(!company.equals(user.getCompany())) {
			addActionError(getText("CompanySettingsUpdateAction.updateFailed"));
		}
		
		Company company = companyDelegate.find(companyId);
		company.setEmail(email);
		company.setAddress(address);
		company.setAddress2(address2);
		company.setPhone(phone);
		company.setContactPerson(contactPerson);
		company.setNameEditable(nameEditable);
		company.setState(state);
		company.setCellphone(cellphone);
		company.setFax(fax);
		company.setTitle(title);
		
		companyDelegate.update(company);
		
		this.company = company;
		
		lastUpdatedDelegate.saveLastUpdated(company);
		
		return SUCCESS;
	}	
	
	// getters / setters
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public void setNameEditable(String nameEditable) {
		this.nameEditable = nameEditable;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
