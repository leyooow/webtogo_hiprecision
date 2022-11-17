package com.ivant.cms.action.company.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.action.EmailSenderAction;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.utils.EmailUtil;
import com.opensymphony.xwork2.Action;

public class NoelleJSONAction extends AbstractBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5498079804491413699L;
	
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	private InputStream inputStream;
	
	private CompanySettings companySettings;
	
	private String smtp;
	private String portNumber;
	private String mailerUserName;
	private String mailerPassword;
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public String sendContact() {
		try {
			
			JSONObject obj = new JSONObject();
			obj.put("success", true);
			obj.put("message", "Success");
			
			String subject = request.getParameter("email_subject");
			String content = request.getParameter("email_content");
			String to = request.getParameter("to");
			
			String fullName = request.getParameter("full_name");
			String phoneNumber = request.getParameter("phone_number");
			String emailAddress = request.getParameter("email_address");
			String message = request.getParameter("message");
			
			if(!StringUtils.isEmpty(fullName) && !StringUtils.isEmpty(phoneNumber) && !StringUtils.isEmpty(emailAddress) && !StringUtils.isEmpty(message)) {
				EmailSenderAction emailSenderAction = new EmailSenderAction();
				emailSenderAction.setCompany(company);
				boolean emailSent = emailSenderAction.sendContactEmailToNoelle(subject, content, to, fullName, phoneNumber, emailAddress, message);
				if(!emailSent)
					obj.put("success", false);
			} else {
				obj.put("success", false);
				obj.put("message", "All fields are required.");
			}
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String sendBookSchedule() {
		try {
			
			JSONObject obj = new JSONObject();
			obj.put("success", true);
			obj.put("message", "Success");
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			// EmailUtil.connect("smtp.gmail.com", 25);

			String emailMessage = request.getParameter("acknowledgementContent");
			String emailContent = request.getParameter("emailContent");
			String program = request.getParameter("programName");
			String date = request.getParameter("0b|date");
			String time = request.getParameter("0c|time");
			String name= request.getParameter("1a|name");
			String location = request.getParameter("1b|location");
			String contactNumber = request.getParameter("1c|contact_number");
			String email = request.getParameter("1d|email");
			String message = request.getParameter("1d|1e|message");
			
			if(emailMessage != null && !emailMessage.equals("")) {
				
				StringBuffer content = new StringBuffer();
				content.append(
						bookingEmailContent.replace("--PROGRAM--", program)
						.replace("--DATE--", date).replace("--TIME--", time).replace("--PLACE--", location));
				
				String from = "noreply@webtogo.com.ph";
	
				if (companySettings.getEmailUserName() != null
						&& !company.getCompanySettings().getEmailUserName()
								.equals("")) {
					from = companySettings.getEmailUserName();
				}
	
				if (EmailUtil.sendWithHTMLFormat(from, email, "Book Scheduling - "+program, content.toString(), null)) {
					content = new StringBuffer();
					content.append(emailContent);
					String to = request.getParameter("to");
					if (EmailUtil.sendWithHTMLFormat(from, to, "Book Scheduling - "+program, content.toString(), null)) {
							String formName = request.getParameter("se_formName");
							String sender = request.getParameter("se_sender");
							String senderEmail = request.getParameter("se_email");
							String senderPhone = request.getParameter("se_phone");
							String testimonial = request.getParameter("se_testimonial");

							SavedEmail savedEmail = new SavedEmail();
							savedEmail.setCompany(company);
							savedEmail.setSender(sender);
							savedEmail.setEmail(senderEmail);
							savedEmail.setPhone(senderPhone);
							savedEmail.setFormName(formName);
							savedEmail.setEmailContent(emailContent);
							savedEmail.setTestimonial(testimonial);
							savedEmail.setOtherField1(request.getParameter("programName"));
							savedEmail.setOtherField2(request.getParameter("programId"));
							savedEmailDelegate.insert(savedEmail);
					}
				} else {
					obj.put("success", false);
					obj.put("message", "Error booking. Please contact System Administrator!");
				}
				
			} else {
			
				obj.put("success", false);
				obj.put("message", "All fields are required.");
				
			}
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public void setEmailSettings() {
		companySettings = company.getCompanySettings();
		initHttpServerUrl();
		if (company.getCompanySettings().getEmailUserName() != null
				&& !company.getCompanySettings().getEmailUserName().equals("")) {
			setMailerUserName(company.getCompanySettings().getEmailUserName());
		} else {
			setMailerUserName(EmailUtil.DEFAULT_USERNAME);
		}
		if (company.getCompanySettings().getEmailPassword() != null
				&& !company.getCompanySettings().getEmailPassword().equals("")) {
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
			setPortNumber("587");
		}
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getMailerUserName() {
		return mailerUserName;
	}

	public void setMailerUserName(String mailerUserName) {
		this.mailerUserName = mailerUserName;
	}

	public String getMailerPassword() {
		return mailerPassword;
	}

	public void setMailerPassword(String mailerPassword) {
		this.mailerPassword = mailerPassword;
	}

	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}
	
	private final String bookingEmailContent = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
			"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
			"<head>"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
			"<title>Untitled Document</title>"+
			"</head>"+
			"<body>"+
				"<div style=\"width:600px; height: 418px; background: url(http://noellerodriguez.com/images/noelleBookedBg.jpg) no-repeat; font-family:Arial, Helvetica, sans-serif; font-size: 12px\">"+
					"<div style=\"position: relative; top: 164px;\">"+
						"<div style=\"height:164px\"></div>"+
						"<div style=\"background: #fff; width: 462px; padding: 1px 0; margin: 0 auto\">"+
							"<ul style=\"list-style:none; font-size: 14px; letter-spacing: 1px; color: #727272; font-family: Georgia, 'Times New Roman', Times, serif\">"+
								"<li style=\"margin: 4px 0; padding: 5px 0;\">Program: --PROGRAM-- </li>"+
								"<li style=\"margin: 4px 0; padding: 5px 0;\">Date: --DATE-- </li>"+
								"<li style=\"margin: 4px 0; padding: 5px 0;\">Time: --TIME-- </li>"+
								"<li style=\"margin: 4px 0; padding: 5px 0;\">Place: --PLACE--</li>"+
							"</ul>"+
						"</div>"+
					"</div>"+
				"</div>"+
			"</body>"+
			"</html>";

}
