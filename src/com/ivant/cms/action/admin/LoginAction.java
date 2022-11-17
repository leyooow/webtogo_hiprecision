package com.ivant.cms.action.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.User;
import com.ivant.cms.interceptors.CompanyInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.constants.CompanyConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ivant.cms.enums.UserType;

public class LoginAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware, CompanyAware {

	private static final long serialVersionUID = -100325022519476647L;
	private static final String FIRST = "first";	//for terms
	private static final String USERGROUP = "userGroup";	//for agian user group admin
	private static final String USERSUBGROUP = "userSubGroup";	//for agian user sub group admin
	private static final String HPDOSSTAFF = "hpdosStaff";	//for hiprecisiononlinestore company staff
	
	private Logger logger = Logger.getLogger(getClass());
	private UserDelegate userDelegate = UserDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
		
	private Map session;
	private HttpServletRequest request;
	
	private String username;
	private String password;
	private boolean login = false;
	
	private Company company;
	
	@Override
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		boolean terms = false;
		if(login) {
			logger.debug("LOGGING IN...");
			logger.debug("COMPANY: " + company);
			
			if(username != null && password != null) {
				User user = userDelegate.findAccount(username, password, company);
				
				// if user is null, try to check if the account entered is a root account
				// if yes, allow the user to continue provided they entered a correct information
				if(user == null) {
					user = userDelegate.findAccount(username, password, company, true);
				}
				if(user == null) {
					user = userDelegate.findAccount(username, password, company, false , true);
				}
								
				if(user != null) {	
					if(user.getLastLogin() == null){	//for terms
						terms = true;
					}
					user.setLastLogin(new Date());
					userDelegate.update(user);
					if (user.getUserType()==UserType.SUPER_USER || user.getUserType()==UserType.SUPER_USER)
						user.setCompany(null);
					
					session.put(UserInterceptor.USER_SESSION_KEY, user.getId());
				
					if(terms){		//for terms
						return FIRST;
					}
					
					if(user.getUserType()==UserType.USER_GROUP_ADMINISTRATOR){
						return USERGROUP;
					}
					
					if(user.getUserType()==UserType.USER_SUB_GROUP_ADMINISTRATOR){
						return USERSUBGROUP;
					}
					
					if(user.getUserType()==UserType.COMPANY_STAFF && company.getName().equalsIgnoreCase("hiprecisiononlinestore")){
						return HPDOSSTAFF;
					}
					return Action.SUCCESS;
				}
			} 
			addActionError("Invalid Username / Password"); 
		}
		
		
		if(company != null){
			if(company.getName().equalsIgnoreCase("kuysencms")){
				session.put("selectedKuysenCompany", CompanyConstants.KUYSEN_ID);
			}		
		}
		
		
					
		return Action.LOGIN;
	}

	public void prepare() throws Exception {
		if(request.getParameter("login") != null) {
			login = true;
		}
		username = request.getParameter("username");
		password = request.getParameter("password");
	}
		
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setSession(Map arg0) {
		this.session = arg0;
	}
}
