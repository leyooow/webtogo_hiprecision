package com.ivant.cms.interceptors;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.utils.EmailUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class EmailInterceptor implements Interceptor {

	private static final long serialVersionUID = 3436972944441513001L;
	private Logger logger = Logger.getLogger(EmailInterceptor.class);

	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate
			.getInstance();

	private List<SavedEmail> emailsIn24hrs;
	private List<SavedEmail> emailsIn7days;
	private Company company;

	private String smtp;
	private String portNumber;
	private String mailerUserName;
	private String mailerPassword;

	public void destroy() {
		logger.debug("DESTROYING EMAIL INTERCEPTOR...");
	}

	public void init() {
		logger.debug("CREATING EMAIL INTERCEPTOR...");
	}

	@SuppressWarnings("deprecation")
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		logger.debug("EMAIL INTERCEPTOR CALLED....");
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName,
				mailerPassword);
		company = (Company) request
				.getAttribute(FrontCompanyInterceptor.COMPANY_REQUEST_KEY);

		// emails retrival for new_webtogo project
		if (company != null) {
			if (company.getId() == 76) {
				Date fromDate = new Date();
				Date toDate = new Date();

				fromDate.setHours(fromDate.getHours()- 24);
				emailsIn24hrs = savedEmailDelegate.findEmailByDate(fromDate, toDate).getList();
				Collections.reverse(emailsIn24hrs);
				fromDate.setDate(fromDate.getDate()- 7);
				logger.debug("DATE"+ company.getId());;
				emailsIn7days = savedEmailDelegate.findEmailByDate(fromDate, toDate).getList();
				emailsIn7days = extractMessage(emailsIn7days);

				request.setAttribute("emailsIn24hrs", emailsIn24hrs);
				request.setAttribute("emailsIn7days", emailsIn7days);
				request.setAttribute("totalEmailsIn24hrs", emailsIn24hrs.size());
				request.setAttribute("totalEmailsIn7days", emailsIn7days.size());
			}
		}
		return invocation.invoke();
	}

	private List<SavedEmail> extractMessage(List<SavedEmail> emailList) {
		String message;
		String[] strArray = { "", "" };

		for (int i = 0; i < emailList.size(); i++) {
			message = emailList.get(i).getEmailContent();
			if (message.indexOf("Message :") > 0) {
				strArray = message.split("Message :");
				strArray[1] = strArray[1].replaceAll(
						"<br/><br/></p></body></html>", "");
			} else if (message.indexOf("Remarks :") > 0) {
				strArray = message.split("Remarks :");
				strArray[1] = strArray[1].replaceAll(
						"<br/><br/></p></body></html>", "");
			}
			emailList.get(i).setEmailContent(strArray[1]);
		}
		Collections.reverse(emailList);
		return emailList;
	}

	public List<SavedEmail> getEmailsIn24hrs() {
		return emailsIn24hrs;
	}

	public void setEmailsIn24hrs(List<SavedEmail> emailsIn24hrs) {
		this.emailsIn24hrs = emailsIn24hrs;
	}

	public List<SavedEmail> getEmailsIn7days() {
		return emailsIn7days;
	}

	public void setEmailsIn7days(List<SavedEmail> emailsIn7days) {
		this.emailsIn7days = emailsIn7days;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	/* for Custom Email Settings */

	public void setEmailSettings() {
		if (company != null) {
			if (company.getCompanySettings().getEmailUserName() != null && !company.getCompanySettings().getEmailUserName().equals("")) {
				setMailerUserName(company.getCompanySettings().getEmailUserName());
			} else {
				setMailerUserName(EmailUtil.DEFAULT_USERNAME);
			}
			if (company.getCompanySettings().getEmailPassword() != null && !company.getCompanySettings().getEmailPassword().equals("")) {
				setMailerPassword(company.getCompanySettings().getEmailPassword());
			} else {
				setMailerPassword(EmailUtil.DEFAULT_PASSWORD);
			}
			if (company.getCompanySettings().getSmtp() != null
					&& !company.getCompanySettings().getSmtp().equals("")) {
				setSmtp(company.getCompanySettings().getSmtp());
			} else {
				setSmtp("smtp.gmail.com");
			}
			if (company.getCompanySettings().getPortNumber() != null
					&& !company.getCompanySettings().getPortNumber().equals("")) {
				setPortNumber(company.getCompanySettings().getPortNumber());
			} else {
				setPortNumber("25");
			}
		}
		else{
			/*
			 * 	For interceptor, this is the default mail settings
			 * 
			 * */
			setMailerUserName(EmailUtil.DEFAULT_USERNAME);
			setMailerPassword(EmailUtil.DEFAULT_PASSWORD);
			setSmtp("ivant.com");
			setPortNumber("25");
		}
			

	}

	/* Custom Email Settings */

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public void setMailerUserName(String mailerUserName) {
		this.mailerUserName = mailerUserName;
	}

	public void setMailerPassword(String mailerPassword) {
		this.mailerPassword = mailerPassword;
	}

}
