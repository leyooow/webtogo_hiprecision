package com.ivant.cms.action.admin;

import org.apache.log4j.Logger;
import com.ivant.cms.delegate.UserDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ForgotPasswordAction extends ActionSupport implements CompanyAware {

	private static final long serialVersionUID = 2775644259880977527L;
	private static final Logger logger = Logger.getLogger(ForgotPasswordAction.class);
	private static final UserDelegate userDelegate = UserDelegate.getInstance();
	
	private Company company;
	private String email;
	boolean submitted = false;
	
	@Override
	public String execute() throws Exception {
		if(submitted) {
			User user = userDelegate.findEmail(company, email);
			if(user == null) {
				addActionError("The email address you entered does not exist.");
				return ERROR;
			}
			 
			String newPass = StringUtil.generateRandomString();
			
			user.setPassword(Encryption.hash(newPass));
			userDelegate.update(user);
			
			logger.debug(newPass);
			
			if(!sendEmail(user, newPass)) {
				addActionError("The account was not sent properly. Please contact support@webtogo.com.ph. Thank you");
				return ERROR;
			}
			else {
				return SUCCESS;
			}
		}
		
		
		
		return INPUT;
	}
	
	private boolean sendEmail(final User user, String newPassword) {
		EmailUtil.connect("smtp.gmail.com", 25); 
		
		StringBuffer content = new StringBuffer();
		content.append("\nPassword Recovery From WebToGo.com.ph\n\n");
		content.append("Username: " + user.getUsername());
		content.append("\nPassword: " + newPassword);	
		content.append("\n\nDont forget to change your email after you sign-in.\n");
		content.append("You can use this link to login http://" + company.getServerName() + "/admin");
		content.append("\n\n\nThank You Very Much, \n\n");
		content.append("WebToGo Team");
		  
		return EmailUtil.send("noreply@webtogo.com.ph", email, "Password Recovery From WebToGo.com.ph", content.toString());
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	
	public Company getCompany(){
			return this.company;
	}
}
