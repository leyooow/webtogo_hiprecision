package com.ivant.cms.action.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.TermsDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Terms;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.UserType;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;

public class TermsAction extends ActionSupport implements ServletRequestAware, UserAware, CompanyAware {

	private static final long serialVersionUID = -1840100785273956085L;
	private static final Logger logger = Logger.getLogger(TermsAction.class);
	private static final TermsDelegate termsDelegate = TermsDelegate.getInstance();
	private static final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	private static final String DECLINED = "declined";
	
	private static final String USERGROUP = "userGroup";	//for agian user group admin
	private static final String USERSUBGROUP = "userSubGroup";	//for agian user sub group admin
	
	private User user;
	private Company company;
	private HttpServletRequest request;
	
	private String agree;
	
	@Override
	public String execute() throws Exception {	
		if(agree != null) {
			Terms terms = new Terms();
			terms.setCompany(company);
			
			User persistedUser = userDelegate.load(user.getId());
			persistedUser.setLastLogin(new Date());	//
			userDelegate.update(persistedUser);	//
			terms.setUser(persistedUser);
			terms.setIpAddress(request.getRemoteAddr());
			
			termsDelegate.insert(terms);
			
			if(user.getUserType()==UserType.USER_GROUP_ADMINISTRATOR){
				return USERGROUP;
			}
			
			if(user.getUserType()==UserType.USER_SUB_GROUP_ADMINISTRATOR){
				return USERSUBGROUP;
			}
			
			return SUCCESS;
		}
		else{
			user.setLastLogin(null);
			userDelegate.update(user);
		}
		return INPUT;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setAgree(String agree) {
		this.agree = agree;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public String declined(){
		//System.out.println(user.getLastLogin()+"-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		//System.out.println("=======================abot d2");
		user.setLastLogin(null);
		userDelegate.update(user);
		return DECLINED;
	}
}
