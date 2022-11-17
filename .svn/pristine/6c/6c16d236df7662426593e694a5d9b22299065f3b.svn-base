package com.ivant.cms.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.Order;
import org.json.simple.JSONObject;

import com.ivant.cms.company.utils.AgianUtilities;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.delegate.ReferralDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.TruecareTestimonialDelegate;
import com.ivant.cms.delegate.WebtogoExcludedEmailsDelegate;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CartOrderItem;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.PromoCode;
import com.ivant.cms.entity.Referral;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.WebtogoExcludedEmails;
import com.ivant.cms.entity.list.ObjectList;
import com.ivant.cms.enums.ReferralStatus;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.GenerateRandomkeyUtil;
import com.ivant.utils.LifeEmailUtil;
import com.ivant.utils.PasswordEncryptor;
import com.opensymphony.xwork2.Action;

//import com.ivant.utils.CaptchaResponse;

public class EmailSenderAction implements Action, ServletRequestAware,
		CompanyAware, ServletContextAware {

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(EmailSenderAction.class);
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private static final String INDENT = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String PNG_EXTENSION = ".png";
	private static final Double ZERO = 0d;

	private HttpServletRequest request;
	protected ServletContext servletContext;
	private Company company;

	private String successUrl;
	private String errorUrl;
	private PasswordEncryptor encryptor;
	
	private int maxFileSize;
	private File file;
	private String contentType;
	private String filename;
	private String myEmail;
	private List<SavedEmail> savedEmailList;
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate
			.getInstance();
	private ReferralDelegate referralDelegate = ReferralDelegate.getInstance();
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate.getInstance();
	private AgianUtilities agianUtilities ;

	
	/*
	 * Bluewarner / Panasonic
	 */
	PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	
	/*
	 * Truecare
	 */
	TruecareTestimonialDelegate truecareDelegate = TruecareTestimonialDelegate.getInstance();
	/*
	 * Truecare
	 */
	
	/**
	 * Elc
	 */
	
	
	/*Webtogo*/
	WebtogoExcludedEmailsDelegate webtogoExcludedEmailsDelegate = WebtogoExcludedEmailsDelegate.getInstance();
	
	
	private CategoryItem catItem;
	private String totalPrice;
	private String finalPrice;


	private String fullName;
	private String certificate;
	private String address;
	private String contact;
	private String email = "";
	private String site;
	private String lotPrice;
	private String locId;
	private String product;
	private String classification;
	private String pcf;
	private String paymentOption;
	private String monthly;
	private String contractPrice;
	private String productName;
	private String classificationName;
	private String optionName;
	private String atNeedPrice;

	private Boolean updateReferralToRequested = Boolean.FALSE;
	private Long referralId;
	private String referralReward;

	private String rewardsToClaim[];
	private Long referralsId[];

	private String error;

	private String smtp;
	private String portNumber;
	private String mailerUserName;
	private String mailerPassword;
	
	private InputStream inputStream;
	
	private String emailContent;
	private String acknowledgementContent;
	private Boolean isForAcknowledgement = Boolean.FALSE;
	
	private String notificationMessage;
	
	private String resrId;
	private String confirmationNum;
	
	private String wtgKaptcha = "";

	
	//---end file uploads----
	
	@SuppressWarnings("unchecked")
	private TreeMap tm = new TreeMap();
	
	
	public String executeViaAjax(){
		String result = "error";
		try{
			result = execute();
		}catch(Exception e){
			
		}
		setInputStream(new ByteArrayInputStream(result.getBytes()));
		return SUCCESS;
	}
	
	public String execute() throws Exception { 
		if (file != null) {
			long maxFileSizeInMB = maxFileSize * 1048576;
			if (file.length() > maxFileSizeInMB) {
				return ERROR;
			}
		}

		
		if(company == null)
		{
			company = companyDelegate.find(Long.parseLong(request.getParameter("companyId")));
		}
		
		if (company != null) {
			if (company.getSecondaryCompany() != null) {
				System.out.println("<<<<<<<<< Secondary company was called");
				company = company.getSecondaryCompany();
				if (company != null) {
					System.out.println("\n<<<<<<<<<<<<<");
					System.out.println(company.getId() + " << " + company.getName());
					System.out.println(">>>>>>>>>>>>>>>\n");
				}
			} 
		}
		
		// kaptcha
		final String kaptchaReceived = (String) request.getParameter("kaptcha");
		
		//System.out.println("kaptchaReceived: " + kaptchaReceived);
		if (kaptchaReceived != null) {
			String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			//System.out.println("kaptchaExpected: " + kaptchaExpected);
			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				setError("invalid captcha");
				errorUrl = request.getParameter("errorUrl");
				if(company.getId() == CompanyConstants.FROSCH)
				{
					errorUrl += ("&error=" + getError());
				}
				return Action.ERROR;
			}
			wtgKaptcha = kaptchaExpected + " = " + kaptchaReceived;
		}
		
		
		final String recap = (String) request.getParameter("g-recaptcha-response");
		String secretKey = "";
		
		if(recap != null) {
			if(company.getId() == CompanyConstants.PILIPINAS_BRONZE)
				secretKey = "6LdBgQATAAAAAOd1XYYrj3NgotUq9fSI7IV0ols1";
			
			URL url = new URL("https://www.google.com/recaptcha/api/siteverify?secret="+secretKey+"&response="+recap+"&remoteip="+request.getRemoteAddr());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        String line, outputString = "";
	        BufferedReader reader = new BufferedReader(
	                new InputStreamReader(conn.getInputStream()));
	        while ((line = reader.readLine()) != null) {
	            outputString += line;
	        }
		}
		
		if("elcorp".equals(company.getName())){
			return sendEmailByElcorp();
		}
		
		if(company.getName().equalsIgnoreCase("truecare") && request.getParameter("se_formName").equals("Testimonial")){
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			String name = request.getParameter("hidden_name");
			String testimonial = request.getParameter("testimonial");
			String email = request.getParameter("hidden_email");
			int rating = Integer.parseInt(request.getParameter("rating"));
			
			System.out.print(name + " " + email + " " + testimonial + " " + rating);
			ItemComment itemComment = new ItemComment();
			itemComment.setCreatedOn(new Date());
			itemComment.setCompany(company);
			itemComment.setFirstname(request.getParameter("hidden_name"));
			itemComment.setEmail("");
			itemComment.setPublished(false);
			itemComment.setLastname(request.getParameter("rating"));
			itemComment.setContent(request.getParameter("testimonial"));
			itemComment.setIsValid(true);
			Long id = itemCommentDelegate.insert(itemComment);
			System.out.println(request.getParameter("hidden_name") + ", "
					+ request.getParameter("rating") + ", "
					+ request.getParameter("testimonial"));
		}
		

		if(company.getName().equalsIgnoreCase("truecare") && request.getParameter("se_formName").equals("Newsletter")){
			SavedEmail exist = savedEmailDelegate.findByTruecareEmail(company, request.getParameter("se_email"), "Newsletter");
			if(exist != null){
				errorUrl = request.getParameter("errorUrl");
				errorUrl += "usedemail";
				return Action.ERROR;
			}
		}
		
		/*
		 * Bluewarner
		 */
		if(company.getName().equalsIgnoreCase("bluewarner") && request.getParameter("se_formName").equalsIgnoreCase("Home")) {
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			
			PromoCode promo_code = promoCodeDelegate.findByCode(company, request.getParameter("1f|promo_code"));
			
			if(promo_code != null){
				
				if(promo_code.getIsDisabled() == true){
					errorUrl += "alreadysubmitted";
					return ERROR;
				}else{
					// make the promo not valid
					//promo_code.setIsValid(false);
					promo_code.setIsDisabled(true);
					promoCodeDelegate.update(promo_code);
				}
				
			
			}else{
				errorUrl += "invalid";
				return ERROR;
			}
		}
		/*
		 * Bluewarner
		 */
		
		
		/*
		 * Panasonic
		 */
		if(company.getName().equalsIgnoreCase("panasonic")) {
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			
			PromoCode model_num = promoCodeDelegate.findByNote(company, request.getParameter("1f|model_number"));
			if(model_num != null){
				PromoCode promo_code = promoCodeDelegate.findByCode(company, request.getParameter("1e|promo_code"));
				
				if(promo_code != null){
					
					if(promo_code.getIsDisabled() == true){
						errorUrl += "alreadysubmitted";
						return ERROR;
					}else{
						// make the promo not valid
						//promo_code.setIsValid(false);
						promo_code.setIsDisabled(true);
						promoCodeDelegate.update(promo_code);
					}
					
				
				}else{
					errorUrl += "invalid";
					return ERROR;
				}
			}else{
				errorUrl += "modelinvalid";
				return ERROR;
			}
			
			
			
		}
		/*
		 * Panasonic
		 */
		
		
		
		if(company.getId() == CompanyConstants.EASTERNWIRE)
		{
			final String remoteAddr = request.getRemoteAddr();
			final String responseCaptcha = (String)request.getParameter("recaptcha_response_field");
			final String challengeCaptcha = (String)request.getParameter("recaptcha_challenge_field");
			final ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
			reCaptcha.setPrivateKey("6Le-WuMSAAAAAIQkRQWj22fks6-Hl4FlgcEjYLKM");
			
			final ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challengeCaptcha, responseCaptcha);
			if (!reCaptchaResponse.isValid())
			{
				servletContext.setAttribute("errorCaptcha", (String) "Invalid Captcha");
				errorUrl = (String)request.getParameter("errorUrl");
				return Action.ERROR;
			}
			
		}

		if (request.getParameter("1i|Memorial Product") != null
				|| request.getParameter("1x|PNPA#") != null) {
			submitInfo();
		}

		if (company.getName().equalsIgnoreCase("luap")) {
			if (sendEmailToLUAPMember() != Action.ERROR) {
				return Action.SUCCESS;
			}
		}
		
		if (company.getName().equalsIgnoreCase("smartdocs")) {
			if (sendEnquiryToSmartDocs() != Action.ERROR) {
				return Action.SUCCESS;
			}
		}
		
		if(company.getName().equals("totalqueue2")) {
			if (sendFromTotalQueueContactUs()) {
				return Action.SUCCESS;
			} else {
				return Action.ERROR;
			}
		}
		
		if (request.getParameter("se_formName").equalsIgnoreCase("Mr Aircon Blog Form")) {
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			ItemComment itemComment = new ItemComment();
			itemComment.setCreatedOn(new Date());
			itemComment.setCompany(company);
			itemComment.setPage(singlePageDelegate.find(Long.parseLong(request.getParameter("se_page"))));
			itemComment.setMember(null);
			itemComment.setFirstname(request.getParameter("se_sender"));
			itemComment.setLastname("");
			itemComment.setEmail(request.getParameter("se_email"));
			itemComment.setContent(request.getParameter("se_testimonial"));
			itemComment.setIsValid(true);
			itemCommentDelegate.insert(itemComment);
			return Action.SUCCESS;
		}
		
		if (request.getParameter("se_formName").equalsIgnoreCase("Press Comments Form")) {
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			ItemComment itemComment = new ItemComment();
			itemComment.setCreatedOn(new Date());
			itemComment.setCompany(company);
			itemComment.setPage(singlePageDelegate.find(Long.parseLong(request.getParameter("se_page"))));
			itemComment.setMember(null);
			itemComment.setFirstname(request.getParameter("se_sender"));
			itemComment.setLastname("");
			itemComment.setEmail(request.getParameter("se_email"));
			itemComment.setContent(request.getParameter("se_testimonial"));
			itemComment.setIsValid(true);
			itemCommentDelegate.insert(itemComment);
			return Action.SUCCESS;
		}
		
		String ajaxSubmit = request.getParameter("ajax_submit");
		JSONObject obj = new JSONObject();
		obj.put("success", true);

		if (!sendEmail()) {
			setError("An error has occured.");
			
			if(ajaxSubmit != null && ajaxSubmit.equalsIgnoreCase("true")) {
				obj.put("success", false);
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				return "stream";
			}
			
			return Action.ERROR;
		}
		replyInquirer();
		
		//for fb contact form
		String fname = request.getParameter("fb_firstName");
		String lname = request.getParameter("fb_lastName");

		request.setAttribute("firstName", fname);
		request.setAttribute("lastName", lname);
		
		if(ajaxSubmit != null && ajaxSubmit.equalsIgnoreCase("true")) {
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			return "stream";
		}

		return Action.SUCCESS;
	} // end email
	
	/*private String sentTestimonial(){
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			String name = request.getParameter("hidden_name");
			String testimonial = request.getParameter("testimonial");
			String email = request.getParameter("hidden_email");
			int rating = Integer.parseInt(request.getParameter("rating"));
			
			System.out.print(name + " " + email + " " + testimonial + " " + rating);
			try{
					ItemComment itemComment = new ItemComment();
					itemComment.setCreatedOn(new Date());
					itemComment.setCompany(company);
					itemComment.setFirstname(request.getParameter("hidden_name"));
					itemComment.setEmail("");
					itemComment.setPublished(false);
					itemComment.setLastname(request.getParameter("rating"));
					itemComment.setContent(request.getParameter("testimonial"));
					itemComment.setIsValid(true);
					Long id = itemCommentDelegate.insert(itemComment);
					System.out.println(request.getParameter("hidden_name") + ", "
							+ request.getParameter("rating") + ", "
							+ request.getParameter("testimonial"));
					
			}catch(Exception e){
				return ERROR;
			}
	}*/
	
	public String sendAgianInquiry(){
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		String contactName = request.getParameter("1a|name");
		String contactEmail = request.getParameter("1b|contact_email");
		String contactCompany = request.getParameter("1c|company");
		String contactMessage = request.getParameter("1d|message");
		String contentSubject = "AGIAN Online Inquiry Submission";
		String contentTo = request.getParameter("to");
		
		StringBuilder content = new StringBuilder("");
		content.append("<br><br>Message: " + contactMessage + "<br><br>Name: ")
			.append("" + contactName + "<br>Company: " + contactCompany + "<br>Email: " + contactEmail);
		
		if(AgianUtilities.sendMemberAccountDetails("admin_agian@ayala.com.ph",contentTo,contentSubject,content.toString(),null)){
			SavedEmail savedEmail;
			savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(contentTo);
			savedEmail.setEmail(contactEmail);
			savedEmail.setFormName("AGIAN Online Inquiry");
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			
			content = new StringBuilder("");
			content.append("This is to acknowledge that we have received your inquiry to AGIAN Online Inquiry. <br><br>Message: " + contactMessage + "<br><br>")
				.append("Kindly wait for the reply. Thank you and have a nice day!");
			
			if(AgianUtilities.sendMemberAccountDetails("admin_agian@ayala.com.ph",contactEmail,"AGIAN Online Inquiry Acknowledgement",content.toString(),null)){
				return SUCCESS;
			}else{
				return ERROR;
			}
			
		}else{
			return ERROR;
		}
		
	}
	
	public String sendAgianForgetPassword() {
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		String contactName = request.getParameter("forgot_email");
		Member mem = memberDelegate.findEmail(company, contactName);
		/* Hidden inputs */
		if(mem != null){
			String contactEmailTo = request.getParameter("forgot_email");
			String contactEmailSender = request.getParameter("from");
			String contactSubject = request.getParameter("subject");
			
			/*char[] chars = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();*/
			
			String output = AgianUtilities.generatePassword(8);
			System.out.println(output);
			//String contactCompany = "",contactEmail ="",contactContact="";
			
			/*setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);*/
			StringBuilder content = new StringBuilder("");
			content.append("Email: " + contactName + "<br><br>")
				.append("Code: " + output + "<br><br>");
			
			System.out.println(content);
			successUrl += contactName +"&forgot=yes";
			
			
			/*AgianUtilities.sendMemberAccountDetails(from, to, contactSubject, content, attachment)*/
			if(AgianUtilities.sendMemberAccountDetails("admin_agian@ayala.com.ph",contactEmailTo,contactSubject,content.toString(),null)){
				SavedEmail savedEmail;
				savedEmail = savedEmailDelegate.findByFormNameAndEmail(company, contactEmailTo, "AGIAN Forgot Password");
				if(savedEmail != null){
					savedEmail.setEmailContent(content.toString());
					savedEmail.setTestimonial(output);
					savedEmailDelegate.update(savedEmail);
				}else{
					savedEmail = new SavedEmail();
					savedEmail.setCompany(company);
					savedEmail.setSender(contactEmailTo);
					savedEmail.setEmail(contactEmailTo);
					savedEmail.setFormName("AGIAN Forgot Password");
					savedEmail.setEmailContent(content.toString());
					savedEmail.setTestimonial(output);
					savedEmailDelegate.insert(savedEmail);
				}
				return SUCCESS;
			}
		}
		return ERROR;
		
	}
	
	public String sendAgianForgetCode(){
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		String contactEmail = request.getParameter("forgot_email");
		String code = request.getParameter("forgot_code");
		
		SavedEmail savedEmail = savedEmailDelegate.findByFormNameAndEmail(company, contactEmail, "AGIAN Forgot Password");
		
		if(savedEmail != null){
			if(savedEmail.getTestimonial().equalsIgnoreCase(code)){
				Member mem = memberDelegate.findEmail(company, contactEmail);
				if(mem != null){
					encryptor = new PasswordEncryptor();
					String new_password = encryptor.encrypt(request.getParameter("forgot_password_confirm"));
					mem.setPassword(new_password);
					memberDelegate.update(mem);
					savedEmailDelegate.delete(savedEmail);
				}
				
			}else{
				errorUrl += contactEmail + "&forgot=yes&code=invalid";
				return ERROR;
			}
		}
		return SUCCESS;
	}
		
	
	private String sendEmailByElcorp() {
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		String contactName = StringUtils.trimToEmpty(request.getParameter("contact-name"));
		String contactCompany = StringUtils.trimToEmpty(request.getParameter("contact-company"));
		String contactContact  = StringUtils.trimToEmpty(request.getParameter("contact-contact"));
		String contactEmail = StringUtils.trimToEmpty(request.getParameter("contact-email"));
		String contactMessage = StringUtils.trimToEmpty(request.getParameter("contact-message"));
		
		/* Hidden inputs */
		String contactEmailSender = StringUtils.trimToEmpty(request.getParameter("contact-email-sender"));
		String contactEmailTo = StringUtils.trimToEmpty(request.getParameter("contact-email-to"));
		contactEmailSender = contactEmailSender.isEmpty() ? "noreply@webtogo.com.ph" : contactEmailSender;
		String contactSubject = StringUtils.trimToEmpty(request.getParameter("contact-subject"));
		
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber),
				mailerUserName, mailerPassword);
		StringBuilder content = new StringBuilder("");
		content.append("Name: " + contactName + "<br><br>")
			.append("Company: " + contactCompany + "<br><br>")
			.append("Contact Number: " + contactContact + "<br><br>")
			.append("Email: " + contactEmail + "<br><br>")
			.append("Message: " + contactMessage + "<br><br>");
		
		if(EmailUtil.sendWithHTMLFormat(contactEmailSender, contactEmailTo, contactSubject, content.toString(), null)){
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(contactName);
			savedEmail.setEmail(contactEmail);
			savedEmail.setPhone(contactContact);
			savedEmail.setFormName("Contact Us");
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			return SUCCESS;
		}
		
		return ERROR;
	}
	
	public String putMyHomeTherapistHeaderFooter(String content)
	{
		String url = "http://myhometherapist.ph";
		StringBuilder buffer = new StringBuilder(10000);
		buffer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
			  .append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
			  .append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />")
			  .append("<title>Untitled Document</title></head>")
			  .append("<body><img src='").append(url).append("/images/email/header.jpg' border='0' />")
			  .append("<div style='width: 720px; background: #fff; font: 13px normal Arial, Helvetica, sans-serif;'>")
			  .append("<div style='padding: 35px;'>")
			  .append(content)
			  .append("</div></div><img src='").append(url).append("/images/email/footer.jpg' border='0' /><br /></body></html>");
		
		return buffer.toString();
	}
	public String requestAccessKey(){
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		if(company.getName().equalsIgnoreCase(CompanyConstants.COLUMNBUS)){
			final String kaptchaReceived = request.getParameter("kaptcha");
			if(kaptchaReceived != null)
			{
				final String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

				if(!kaptchaReceived.equalsIgnoreCase(kaptchaExpected))
				{
					setError("invalid captcha");
					return ERROR;
				}
			}
			
			String name = request.getParameter("name");
			String email_address = request.getParameter("email_address");
			String contact = request.getParameter("contact");
			String personal_message = request.getParameter("personal_message");
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			
			StringBuffer content = new StringBuffer();						
			content.append(name+" is requesting for an access key.<br>");
			content.append("Email: "+ email_address +"<br>");
			content.append("Contact Number: "+ contact +"<br>");
			content.append("Message: "+ personal_message +"<br>");
			
			final String[] to = company.getEmail().split(",");
			saveEmailInformation(content.toString());
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, "Access Key", content.toString(), null)){
				
				//sending access key
				int random = (int) (((Math.random()*100))%CompanyConstants.COLUMNBUS_ACCESS_KEY.length);
				
				String accessKey = CompanyConstants.COLUMNBUS_ACCESS_KEY[random];
				content = new StringBuffer();	
				content.append("Hi "+name+"!<br><br>");
				content.append("Here is your access key " + accessKey);
				content.append("<br><br>");
				content.append("This is a system generated email. Do not reply.<br><br>");
				content.append("All the best,<br>");
				content.append(company.getNameEditable());
				
				final String[] requestorEmail = {email_address};
				
				if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", requestorEmail, "Access Key", content.toString(), null)){
					return Action.SUCCESS;
				}				
			}
			
		}
	
		return Action.ERROR;
	}
	
	public String requestSoftwareDownload(){
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		if(company.getName().equalsIgnoreCase(CompanyConstants.TOTAL_QUEUE)){
			final String kaptchaReceived = request.getParameter("kaptcha");
			if(kaptchaReceived != null)
			{
				final String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

				if(!kaptchaReceived.equalsIgnoreCase(kaptchaExpected))
				{
					setError("invalid captcha");
					return ERROR;
				}
			}
			
			String name = request.getParameter("first_name") +" "+ request.getParameter("last_name");
			String email = request.getParameter("email");
			String softwareVersion =  request.getParameter("softwareVersion");
			String industry =  request.getParameter("industry");
			String country =  request.getParameter("country");
			String formName =  request.getParameter("formName");
			
			String url = request.getRequestURL().toString();
			url = url.replace(url.split("/")[url.split("/").length - 1],"");
			
			String downloadLink = url+"downloadsoftware.do?requestKey="+GenerateRandomkeyUtil.generateAccessKey(50);
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			
			StringBuffer content = new StringBuffer();						
			content.append("Hi " +name +",<br><br>")
			.append("Thank you very much for your interest in downloading the demo version of our TotalQueue")
			.append("Software. Our software offers a complete solution to your customer queuing needs.<br><br>")
			.append("The installation software can be downloaded here: "+ downloadLink+"<br><br>")
			.append("Please note there is a 5 day limit on the validity for the download link.<br><br>")
			.append("If you have any questions on the software please visit our website www.totalqueue.com or you") 					
			.append("can contact us via support@totalqueue.com<br><br>")
			.append("Thank you very much!<br><br>")
			.append("The TotalQueue Support Team<br><br>")
			.append("Email accounts:<br>");
			
			
			final String[] companyEmails = company.getEmail().split(",");
			
			for(int index=0;index<companyEmails.length;index++){
				content.append("<br>"+companyEmails[index]);
			}
			
			
			saveTotalQueueEmailInformation(content.toString(),name,formName,email,downloadLink,industry,softwareVersion,country);
			EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", email, formName , content.toString(), null);
			
		}
		return SUCCESS;
	}
	
	public boolean sendEmailToGemMember(Company company, String senderEmail, String subject, StringBuffer content, String fileName) {
		this.company = company;
		setEmailSettings();
		/*EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName,
				mailerPassword);*/
		EmailUtil.connect("smtp.gmail.com", 587, "gem.mdgph@ivant.com",
				"mundi-pharma#886!!");
		/*String from = "GEM@mundipharma.com.ph";*/
		String from = "gem.mdgph@ivant.com";
		boolean emailSent = EmailUtil.sendWithHTMLFormat(
				from, senderEmail,  subject,content.toString(), fileName);
		
		return emailSent;
	}
	
	public String sendMessage(){
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		if(company.getId() == CompanyConstants.MY_HOME_THERAPIST){
			String your_name = request.getParameter("your_name");
			String email_addresses = request.getParameter("email_addresses");
			String personal_message = request.getParameter("personal_message");
			
			final String kaptchaReceived = (String) request.getParameter("kaptcha");
			//System.out.println("kaptchaReceived: " + kaptchaReceived);
			if (kaptchaReceived != null) {
				String kaptchaExpected = (String) request
						.getSession()
						.getAttribute(
								com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
				//System.out.println("kaptchaExpected: " + kaptchaExpected);
				if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
					setError("invalid captcha");
					errorUrl = request.getParameter("errorUrl");
					if(company.getId() == CompanyConstants.FROSCH)
					{
						errorUrl += ("&error=" + getError());
					}
					return Action.ERROR;
				}
			}
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			
			StringBuffer content = new StringBuffer();
			
			content.append("Greetings!" + ",<br><br> ");
			content.append("You've been invited by " + your_name );
			content.append(" to register at My Home Therapist Website!<br><br>");
			
			content.append("This website is an online directory of ");
			content.append("professional and licensed physical therapists, occupational therapists, and speech therapists who render ");
			content.append("home service and treatment to their patients.<br><br>");
		
			content.append("Should you find this interesting, please visit the site at " );
			content.append("<a href='http://myhometherapist.ph'>http://myhometherapist.ph</a> or ");
			content.append("you may directly register at <a href='http://myhometherapist.ph/register.do'>");
			content.append(	"http://myhometherapist.ph/register.do</a><br><br>");
					
			content.append("We look forward to seeing your name in the list.<br><br>");
			if(!StringUtils.isBlank(personal_message)){
				content.append(your_name+ "'s Message: ");
				content.append("<i>" +personal_message + "</i><br><br>");
			}			
			content.append("Kindly disregard this email if you are already a member.<br><br>");
			content.append("All the best,<br><br>");
			content.append("My Home Therapist");
			
			content = new StringBuffer(putMyHomeTherapistHeaderFooter(content.toString()));
			final String[] to = email_addresses.split(",");
	
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, "Welcome to " + company.getNameEditable() + "!", content.toString(), null)){
				return Action.SUCCESS;
			}
		}
	
		return Action.ERROR;						
	}
	
	private String sendEnquiryToSmartDocs() {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String email = request.getParameter("email");
		String companyInstitution = request.getParameter("companyInstitution");
		String audioFiles = request.getParameter("audioFiles");
		
		successUrl = request.getParameter("successUrl");
		
		String dictation = request.getParameter("dictation");
		String interview = request.getParameter("interview");
		String focusGroup = request.getParameter("focusGroup");
		String presentation = request.getParameter("presentation");
		String hearing = request.getParameter("courtHearings");
		String panel = request.getParameter("panelDiscussion");
		String meeting = request.getParameter("meeting");
		String voices = request.getParameter("numberOfVoices");
		
		dictation = (dictation == "" ? "Yes" : "No");
		interview = (interview == "" ? "Yes" : "No");
		focusGroup = (focusGroup == "" ? "Yes" : "No");
		presentation = (presentation == "" ? "Yes" : "No");
		hearing = (hearing == "" ? "Yes" : "No");
		panel = (panel == "" ? "Yes" : "No");
		meeting = (meeting == "" ? "Yes" : "No");
		
		String unclear = request.getParameter("unclear");
		String volume = request.getParameter("lowVolume");
		String accents = request.getParameter("strongAccents");
		String noise = request.getParameter("backgroundNoise");
		String complex = request.getParameter("complexTerms");
		
		unclear = (unclear == "" ? "Yes" : "No");
		volume = (volume == "" ? "Yes" : "No");
		accents = (accents == "" ? "Yes" : "No");
		noise = (noise == "" ? "Yes" : "No");
		complex = (complex == "" ? "Yes" : "No");
		
		String deadline = request.getParameter("requiredDeadline");
		String howDidUFind = request.getParameter("howYouFindSmartDocs");
		
		if (email != null) {
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);

			String emailMessage = request.getParameter("emailMessage");
			StringBuffer content = new StringBuffer();
			StringBuffer content2 = new StringBuffer();
			
			content.append("Hi " + name + ", "
					+ "thank you for submitting your information.");
			if (emailMessage != null)
				content.append(emailMessage);
			content.append("The SmartDocs Team");
			content.append("This is a system generated email. Do not reply..\r\n\r\n<br/><br/>");
			content.append("The " + company.getNameEditable() + " Team");
			
			String subject = request.getParameter("subject");
			String to = request.getParameter("to");
			
			content2.append("Name: " +name+ "<br />"
			+ "Telephone: " + telephone + "<br />"
			+ "Email : " + email + "<br />"
			+ "Company/Institution : " + companyInstitution + "<br />"
			+ "Number of Audio Files : " + audioFiles + "<br />"
			+ "Work Type : <br />"
			+ "&nbsp;&nbsp;&nbsp; Dictation : " + dictation + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Interview : " + interview + "<br />"
			+ "&nbsp;&nbsp;&nbsp; FocusGroup : " + focusGroup + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Presentation : " + presentation + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Court Hearings : " + hearing + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Panel Discussion : " + panel + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Meeting : " + meeting + "<br />"
			+ "Number of Voices : " + voices + "<br />"
			+ "Audio Quality Issues : <br />"
			+ "&nbsp;&nbsp;&nbsp; Unclear : " + unclear + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Low Volume : " + volume + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Strong Accents : " + accents + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Background Noise : " + noise + "<br />"
			+ "&nbsp;&nbsp;&nbsp; Complex Terms : " + complex + "<br />"
			+ "Required Deadline : " + deadline + "<br />"
			+ "How did you find SmartDocs : " + howDidUFind);
			
			String[] to2 = new String[1];
			to2[0] = company.getEmail();
			
			
			saveEmailInformation(content2.toString());
			
			if (EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to,
					subject+" - "+name, content2.toString(), null)) {
			}
		}
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String sendSubscription() throws Exception, IOException {
		
		try {
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			String from = request.getParameter("from");
			String subject = request.getParameter("subject");
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			
			String to = email;
			
			StringBuffer content = new StringBuffer();
			
			/*
			content.append("Hi "+name+",<br/><br/>");
			content.append("We're ecstatic you're here.<br/><br/>");
			
			JSONObject obj = new JSONObject();
			if (EmailUtil.sendWithHTMLFormat(from, to,
					subject, content.toString(), null)) {
				obj.put("isSuccess", true);
			}*/
			
			JSONObject obj = new JSONObject();
			
			content = new StringBuffer();
			//content.append("Name : "+name+" <br/>");
			content.append("Email : "+email+" <br/>");
			String[] companyEmails = company.getEmail().split(",");
			if (companyEmails != null && companyEmails.length != 0) {
				for (String companyEmail : companyEmails) {
						EmailUtil.sendWithHTMLFormat(from, companyEmail.trim(),
						subject, content.toString(), null);
				}
				obj.put("isSuccess", true);
			}
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(name);
			savedEmail.setEmail(email);
			savedEmail.setPhone("");
			savedEmail.setFormName(subject);
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
		
	}
	
	@SuppressWarnings("unchecked")
	public String sendSubscriptionByLife() throws Exception, IOException {
		
		try {
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			String from = request.getParameter("from");
			String subject = request.getParameter("subject");
			
			successUrl = "home.do";
			errorUrl = "home.do";
			
			StringBuffer content = new StringBuffer();
			content.append("Hi "+name+",<br/><br/>");
			
			content.append("<img src='http://lifeyogacenter.com/images/LifeTop2.jpg'/> <br/>");
			content.append("<img src='http://lifeyogacenter.com/images/LifeBottom2.jpg'/> <br/><br/>");
			
			/*
			content.append("We're ecstatic you're here.<br/><br/>");
			content.append("We were just talking about life the other day. I mean, how could you not? Almost everything can connect back to life.<br/><br/>");
			content.append("So we played a game. A game of word association. Here are some of the answers we got back:<br/><br/>");
			content.append("Life<br/>Love<br/>Laughter<br/>A box of chocolates<br/>Family and friends<br/>Music<br/>Good vibes<br/>Transformation<br/>Adventure<br/>A gift<br/><br/>");
			content.append("We're not going to lie. This list ran quite long. And, it was in reading it that we got the subtle feeling that...life is about celebration.<br/><br/>");
			content.append("Yes, that's it! Life is celebration! It's in the ups and downs and in the upside-downs. &#9786;<br/><br/>");
			content.append("We are here to feel good and then keep on feelin' good!<br/><br/>");
			content.append("That's what we are all about. We are all about celebrating L!FE!<br/><br/>");
			content.append("So, in that spirit, we would like to leave you with a free &#34;10-Day Strategy to Amp Up Your L!FE&#34; right now. On top of that, we have attached a voucher for a FREE class just so you can try it now!<br/><br/>");
			content.append("Because NOW is the only time we've got.<br/><br/>");
			content.append("And, we hope that it leaves you feeling just as good as it did for us! &#9786;<br/><br/>");
			content.append("We have big things planned for you just around the corner. Expect a lot of freebies, fun tips and surprises along with this newsletter.<br/><br/>");
			content.append("Stay tuned for more updates coming your way!<br/><br/>");
			content.append("With lotsa love,<br/>");
			content.append("The L!FE Team");
			*/
			
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "companies" + File.separatorChar + "life" + File.separatorChar;
			new File(locationPath).mkdir();
			String fileName="LIFE Opt in.pdf";
			String filePath =  locationPath + "files"+  File.separatorChar  + fileName;
			
			String[] fileNames = new String[2];
			fileNames[0] = filePath;
//			fileNames[1] = locationPath + "files"+  File.separatorChar + "Class Voucher.jpg";
			
			setEmailSettings();
			LifeEmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			
			String to = email;
			
			JSONObject obj = new JSONObject();
			
			if (LifeEmailUtil.sendWithManyAttachments(from, to,
					subject, content.toString(), fileNames)) {
				obj.put("isSuccess", true);
			}
			
			content = new StringBuffer();
			content.append("Name : "+name+" <br/>");
			content.append("Email : "+email+" <br/>");
			String[] companyEmails = company.getEmail().split(",");
			if (companyEmails != null && companyEmails.length != 0)
				for (String companyEmail : companyEmails) {
						if(LifeEmailUtil.sendWithHTMLFormat(from, companyEmail.trim(),
						subject, content.toString(), null))
							obj.put("isSuccess", true);
				}
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(name);
			savedEmail.setEmail(email);
			savedEmail.setPhone("");
			savedEmail.setFormName(subject);
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
			request.setAttribute("successSubscription", "true");
			setNotificationMessage("Success Subscription");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String fileUpload(String filePath){
		
		if(file!=null){	
			
			final File directory = new File(filePath);

			if (!directory.exists()) {
				directory.mkdir();	
			}

			File fileDestination = new File(filePath,filename);
			try {
				FileUtil.copyFile(file, fileDestination);	
				
			} catch (Exception e) {	
				e.printStackTrace();
			}	
			
			return fileDestination.getPath().toString();
		}else{
			return null;
		}
				

	}
	
	
	public String sendCareerFormByHHAsia () throws Exception, IOException{
		
		try{
			String name = request.getParameter("name");
			String lname = request.getParameter("lname");
			String email = request.getParameter("email");
			int age = Integer.parseInt(request.getParameter("age"));
			String city = request.getParameter("city");
			String position = request.getParameter("position");
			double salary = Double.parseDouble(request.getParameter("salary"));

			StringBuffer content = new StringBuffer();
			
			content.append("Name: " + name + " " + lname + "<br/>");
			content.append("Email: " + email + "<br/>");
			content.append("Age: " + age + "<br/>");
			content.append("City: " + city + "<br/>" );
			content.append("Position: " + position + "<br/>");
			content.append("Expected Salary: " + salary + "<br/>");
			content.append("<br/>Here is my application.<br/></br>Hoping for hearing from you.");
			content.append("<br/><br/>Regards,<br/><br/>" + name );
			
			
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "companies" + File.separatorChar + "hhasia2" + File.separatorChar + "file";	
			
			String filePath = fileUpload(locationPath);
			content.append("<br/>Attachment is from " + this.filename);
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			
			//email to send to: k.cayco@hhasiatrading.com,ren@ivant.com
			
			EmailUtil.sendWithHTMLFormat(email, "cedrick@ivant.com","Career Submission Test", content.toString(),this.filename);
			
			return SUCCESS;
			
		}catch(Exception ex){
			
			return ERROR;
		}
		
		
	}
	
	public boolean sendContactEmailToNoelle(String subject, String content, String to, String name, String phone, String email, String message) {
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber),
				mailerUserName, mailerPassword);

		String from = email;
		
		boolean emailSent = EmailUtil.sendWithHTMLFormat(from, to,
						subject, content, null);
				
		return emailSent;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String sendSubscriptionByNoelle() throws Exception, IOException {
		
		try {
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			String from = request.getParameter("from");
			String subject = request.getParameter("subject");
			
			StringBuffer content = new StringBuffer();
			content.append("Hi "+name+",<br/><br/>");
			
			content.append("<img src='http://noellerodriguez.com/images/NoelleWelcome.png'/> <br/>");
			
			/*content.append("I'm so glad you're here.<br/><br/>");
			content.append("A few days ago, I was rummaging through old notebooks and came across a list I had written some years back.<br/><br/>The question was glaring. In more ways that one, it's an age-old question that has been asked time and time again.<br/><br/>\"What does Success look like to you?\" <br/><br/>Or, in other words... \"What do you hold important in your life?\"<br/><br/>A nice house<br/>Traveling the world<br/>A high-paying job<br/><br/>As the list got longer, it got more heartfelt.<br/><br/>Time with family<br/>Friends I can count on<br/>Happiness <br/>Health <br/>A job I love<br/><br/>I saw that when it gets down to the bones, it's the simple things that matter. We all just want to be happy, at the end of the day.<br/><br/>Here, our focus is on that. We place emphasis on what it feels like -- Body, mind and soul!<br/><br/>We will radiate from the inside-out! <br/><br/>In that light, I've put together some tips to help you get in the spirit of it! <br/><br/>Here are \"10 Ways to Find your Inner (and Outer) Glow!\"<br/><br/>I hope that you have just as much fun going through it as I did!<br/><br/>I'm so excited to share with you what's in store. Expect a lot more fun surprises, freebies and updates coming your way!<br/><br/>");
			content.append("With lotsa love,<br/>");
			content.append("Noelle Rodriguez");*/
			
			String basePath = servletContext.getRealPath("");
			String locationPath = basePath +  File.separatorChar + "companies" + File.separatorChar + "noelle" + File.separatorChar;
			new File(locationPath).mkdir();
			String fileName="Noelle Rodriguez.png";
			String filePath =  locationPath + "file"+  File.separatorChar  + fileName;
			
			String[] filenames = new String[2];
			filenames[0] = filePath;
			filenames[1] = locationPath + "file"+  File.separatorChar + "ManilaMan - Noelle Opt In Document.pdf";
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			
			String to = email;
			
			JSONObject obj = new JSONObject();
			
			if (EmailUtil.sendWithManyAttachments(from, to,
					subject, content.toString(), filenames)) {
				obj.put("isSuccess", true);
			}
			
			content = new StringBuffer();
			content.append("Name : "+name+" <br/>");
			content.append("Email : "+email+" <br/>");
			String[] companyEmails = company.getEmail().split(",");
			/*if (companyEmails != null && companyEmails.length != 0)
				for (String companyEmail : companyEmails) {
						if(EmailUtil.sendWithHTMLFormat(from, companyEmail.trim(),
						subject, content.toString(), null))
							obj.put("isSuccess", true);
				}
			*/
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(name);
			savedEmail.setEmail(email);
			savedEmail.setPhone("");
			savedEmail.setFormName(subject);
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));

			request.setAttribute("successSubscription", "true");
			setNotificationMessage("Success Subscription");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	

	
	
	public String requestDemoSoftwareDownload() throws Exception, IOException {
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		try {
			
			String name = request.getParameter("se_sender");
			String email = request.getParameter("se_email");
			String from = request.getParameter("from");
			String subject = request.getParameter("subject");
			String dtype = request.getParameter("dtype");
			String dlink = request.getParameter("dlink");
			String country = request.getParameter("country");
			String contact = request.getParameter("se_phone");
			
			System.out.println("PARAMS\n" + name + "\n" + email + "\n" + from + "\n" + subject  + "\n" + dtype  + "\n" + dlink);
			
			StringBuffer content = new StringBuffer();
			content.append("Hi " +name +",<br><br>")
			.append("Thank you for requesting the TotalQueue " + dtype + " Software Installation module.<br><br>")
			.append("You can download the software from this link: " + dlink + "<br><br>")
			.append("Please find the User's Manual attached in this email. If you have questions on our software you can always contact us at support@totalqueue.com <br><br>")
			.append("Thank you!<br><br>")
			.append("The TotalQueue Team<br><br><img src=\"http://totalqueue.com/images/TotalQueue.png\" alt=\" \">");
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			String to = email;
			
			
			String attachmentModule = null;
			
			try{
				//MultiPageDelegate multiPageDelegate = new MultiPageDelegate().getInstance();
				String basePath = servletContext.getRealPath("");
				String locationPath = basePath +  File.separatorChar + "companies" + File.separatorChar + company.getName() + File.separatorChar;
				//new File(locationPath).mkdir();
				
				if(dtype.equalsIgnoreCase("basic")){
					MultiPage multiPage = MultiPageDelegate.getInstance().find(company, "downloadlink");
					attachmentModule = locationPath + "multipage_uploads" + File.separatorChar + multiPage.getId() + File.separatorChar + multiPage.getItems().get(0).getId() + File.separatorChar + multiPage.getItems().get(0).getMultiPageFiles().get(0).getFileName();
				}else if(dtype.equalsIgnoreCase("standard")){
					MultiPage multiPage = MultiPageDelegate.getInstance().find(company, "downloadlink");
					attachmentModule = locationPath + "multipage_uploads" + File.separatorChar + multiPage.getId() + File.separatorChar + multiPage.getItems().get(1).getId() + File.separatorChar + multiPage.getItems().get(1).getMultiPageFiles().get(0).getFileName();
				}else if(dtype.equalsIgnoreCase("business")){
					MultiPage multiPage = MultiPageDelegate.getInstance().find(company, "downloadlink");
					attachmentModule = locationPath + "multipage_uploads" + File.separatorChar + multiPage.getId() + File.separatorChar + multiPage.getItems().get(2).getId() + File.separatorChar + multiPage.getItems().get(2).getMultiPageFiles().get(0).getFileName();
				}
				
				System.out.println("\n\nAttachment is "+attachmentModule + "\n\n");
			}catch(Exception e){
				System.out.println("Error locating file...... Sending email without attachment");
			}
			
			String[] companyEmails = company.getEmail().split(",");
			EmailUtil.sendWithHTMLFormatWithBCC(from, to,subject, content.toString(), attachmentModule == null ? null : attachmentModule, companyEmails);
			
			
			content = new StringBuffer();
			content.append("Name : "+name+" <br/><br/>");
			content.append("Location : "+country+" <br/><br/>");
			content.append("Contact : "+contact+" <br/><br/>");
			content.append("Email : "+email+" <br/><br>");
			content.append("Software Download: TotalQueue "+dtype+" Software Demo<br/>");
			
			/*String[] companyEmails = company.getEmail().split(",");
			if (companyEmails != null && companyEmails.length != 0)
			for (String companyEmail : companyEmails) {
				EmailUtil.sendWithHTMLFormat(from, companyEmail.trim(),
						"TotalQueue Demo Software Download", content.toString(), null);
			}*/
			
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(name);
			savedEmail.setEmail(email);
			savedEmail.setPhone(contact);
			savedEmail.setFormName(dtype + " Demo");
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String sendNewsLetter() throws Exception, IOException {
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		try {
			String subject;
			String email;
			
			StringBuffer content = new StringBuffer();
			if(company.getName().equals("truecare")){
				subject = request.getParameter("subjectNewsletter");
				email = request.getParameter("emailNewsletter");
				content.append("Welcome to Truecare")
					.append("Thank you for subscribing");
			}else{
				subject = request.getParameter("subject");
				email = request.getParameter("inputNewsletter");
				content.append("Dear Ma'am/Sir,<br><br>")
				.append("Good news! PocketPons, a mobile promotions platform, is FREE for you to use for your products and/or store! Reach over 100 million Filipinos through mobile coupons to build and increase foot traffic, brand awareness and sales! This is open to all Globe and Smart subscribers! http://pocketpons.com/about.do <br><br>")
				.append("Setting up is quick and easy, simply email us at info@pocketpons.com, and we will send you the details. Here at PocketPons, we believe in great service and creative solutions, and we are committed in helping you structure an offer that's tailored to your unique business needs.<br><br>")
				.append("We look forward to a successful working relationship soon.<br><br>")
				.append("Best Regards,<br><br>PocketPons Team")
				.append("<br><a href='http://pocketpons.com/'><img src='http://pocketpons.com/images/pocketpons.png' /></a>");
			}
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			String to = email;
			
			logger.debug("Sending newsletter to " + to);
			EmailUtil.sendWithHTMLFormat(mailerUserName, to, subject, content.toString(), null);
			
			content = new StringBuffer();
			content.append("Newsletter was sent to " + email);
			
			try{
				String[] companyEmails = company.getEmail().split(",");
				if (companyEmails != null && companyEmails.length != 0)
				for (String companyEmail : companyEmails) {
					logger.debug("Sending newsletter notif to company email: " + companyEmail.trim());
					EmailUtil.sendWithHTMLFormat(mailerUserName, companyEmail.trim(),
							subject, content.toString(), null);
				}
			}catch(Exception e){
				logger.fatal("Cannot send email notif to company email");
				e.printStackTrace();
			}
			
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender("Newsletter");
			savedEmail.setEmail(email);
			savedEmail.setPhone("");
			savedEmail.setFormName("Newsletter");
			savedEmail.setEmailContent("");
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		request.setAttribute("newsletter", true);
		return SUCCESS;
	}
	
	public Boolean sendFromTotalQueueContactUs() throws Exception, IOException {
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		Boolean emailSent = true;
		try {
			
			String name = request.getParameter("se_sender");
			String email = request.getParameter("se_email");
			String from = request.getParameter("from");
			String subject = request.getParameter("subject");
			String dtype = request.getParameter("dtype");
			String dlink = request.getParameter("dlink");
			String country = request.getParameter("countries");
			String contact = request.getParameter("se_phone");
			String message = request.getParameter("message");
			
			System.out.println("PARAMS\n" + name + "\n" + email + "\n" + from + "\n" + subject  + "\n" + dtype  + "\n" + dlink);
			
			StringBuffer content = new StringBuffer();
			content.append("Hi " +name +",<br><br>")
			.append("This is to acknowledge that you sent an online inquiry with the following details:<br><br>")
			.append("Name: " + name + "<br>")
			.append("Location: " + country + "<br>")
			.append("Contact Number: " + contact + "<br>")
			.append("Email Address: " + email + "<br>")
			.append("Message: <span style='font: 100% verdana, sans-serif;'>" + message + "</span><br><br>")
			.append("The TotalQueue Team<br><br><img src=\"http://totalqueue.com/images/TotalQueue.png\" alt=\" \">");
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			String to = email;
			
			emailSent = EmailUtil.sendWithHTMLFormat(from, to, subject, content.toString(), null);
			
			content = new StringBuffer();
			content.append("Name: " + name + "<br>")
			.append("Location: " + country + "<br>")
			.append("Contact Number: " + contact + "<br>")
			.append("Email Address: " + email + "<br>")
			.append("Message: <span style='font: 100% verdana, sans-serif;';>" + message + "</span><br><br>");
			
			String[] companyEmails = company.getEmail().split(",");
//			if (companyEmails != null && companyEmails.length != 0)
//			for (String companyEmail : companyEmails) {
//				EmailUtil.sendWithHTMLFormat(from, companyEmail.trim(),
//						"Inquiry", content.toString(), null);
//			}
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			emailSent = EmailUtil.sendWithHTMLFormat(from, companyEmails,
					"Inquiry", content.toString(), null);
			
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(name);
			savedEmail.setEmail(email);
			savedEmail.setPhone(contact);
			savedEmail.setFormName("Inquiry");
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
			savedEmailDelegate.insert(savedEmail);
			
		} catch (Exception e) {
			e.printStackTrace();
			return emailSent;
		}
		return emailSent;
	}
	
	@SuppressWarnings("unchecked")
	public String sendMDGIssuanceTracker() throws Exception, IOException {
		
		try {
			
			String beginningNumber = request.getParameter("beginningNumber");
			String endingNumber = request.getParameter("endingNumber");
			String familyName = request.getParameter("familyName");
			String firstName = request.getParameter("firstName");
			String location = request.getParameter("location");
			
			String from = request.getParameter("from");
			String subject = request.getParameter("subject");
			String formName = request.getParameter("se_formName");
			
			StringBuffer content = new StringBuffer();
			content.append("Beginning Number : "+beginningNumber+"<br/><br/>");
			content.append("Ending Number : "+endingNumber+"<br/><br/>");
			content.append("Family Name : "+familyName+"<br/><br/>");
			content.append("First Name : "+firstName+"<br/><br/>");
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			
//			String to = email;
			
			JSONObject obj = new JSONObject();
			obj.put("isSuccess", false);
			
//			if (EmailUtil.sendWithHTMLFormat(from, to,
//					subject, content.toString(), null)) {
				obj.put("isSuccess", true);
				
				content = new StringBuffer();
				content.append("Beginning Number:"+beginningNumber+"###");
				content.append("Ending Number:"+endingNumber+"###");
				content.append("Family Name:"+familyName+"###");
				content.append("First Name:"+firstName+"###");
				
				SavedEmail savedEmail = new SavedEmail();
				savedEmail.setCompany(company);
				savedEmail.setSender("");
				savedEmail.setEmail("");
				savedEmail.setPhone("");
				savedEmail.setMember(memberDelegate.find(Long.parseLong(request.getParameter("member_id"))));
				savedEmail.setFormName(formName);
				savedEmail.setEmailContent(content.toString());
				savedEmail.setTestimonial("");
				if(beginningNumber.equals("")) {
					savedEmail.setIsValid(false);
				}
				savedEmail.setOtherField1(String.valueOf(0000+savedEmailDelegate.findEmailByFormName(company, formName, Order.desc("createdOn")).getSize()));
				savedEmailDelegate.insert(savedEmail);
//			}
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String updateMDGIssuanceTracker() throws Exception, IOException {
		
		try {
			
			String trackerId = request.getParameter("tracker_id");
			String beginningNumber = request.getParameter("beginningNumber");
			String endingNumber = request.getParameter("endingNumber");
			String familyName = request.getParameter("familyName");
			String firstName = request.getParameter("firstName");
			String formName = request.getParameter("se_formName");
			
			StringBuffer content = new StringBuffer();
			content.append("Beginning Number : "+beginningNumber+"<br/><br/>");
			content.append("Ending Number : "+endingNumber+"<br/><br/>");
			content.append("Family Name : "+familyName+"<br/><br/>");
			content.append("First Name : "+firstName+"<br/><br/>");
			
			JSONObject obj = new JSONObject();
			obj.put("isSuccess", true);
			
			content = new StringBuffer();
			content.append("Beginning Number:"+beginningNumber+"###");
			content.append("Ending Number:"+endingNumber+"###");
			content.append("Family Name:"+familyName+"###");
			content.append("First Name:"+firstName+"###");
			
			SavedEmail savedEmail = savedEmailDelegate.find(Long.valueOf(trackerId));
			savedEmail.setEmailContent(content.toString());
			savedEmail.setTestimonial("");
				
			savedEmail.setOtherField1(String.valueOf(0000+savedEmailDelegate.findEmailByFormName(company, formName, Order.desc("createdOn")).getSize()));
			savedEmailDelegate.update(savedEmail);
			
			setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			
			List<SavedEmail> trackerList = (List<SavedEmail>) request.getSession().getAttribute("sortedList");
			for(SavedEmail se : trackerList) {
				if(se.getId().equals(savedEmail.getId())) {
					se.setEmailContent(savedEmail.getEmailContent());
					break;
				}
			}
			request.getSession().removeAttribute("sortedList");
			request.getSession().setAttribute("sortedList", trackerList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	public String testimonials()
	{
		return Action.SUCCESS;
	}

	public String submitInfo() {
		if (request.getParameter("1i|Memorial Product") != null) {
			List<String> options = new ArrayList<String>();
			options.add("At Need");
			options.add("Spot Cash");
			options.add("Amortization (12 mos.)");
			options.add("Amortization (36 mos.)");
			options.add("Amortization (60 mos.)");
			options.add("Installment Plan w/DP (12 mos.)");
			options.add("Installment Plan w/DP (36 mos.)");
			options.add("Installment Plan w/DP (60 mos.)");
			CategoryItem ci = new CategoryItem();
			Category cat = new Category();
			catItem = categoryItemDelegate.find(Long.parseLong(request
					.getParameter("1i|Memorial Product")));
			totalPrice = request.getParameter("1p|Final Price");
			finalPrice = request.getParameter("1p|Final Price");
			atNeedPrice = request.getParameter("AtNeedPrice");
			fullName = request.getParameter("1a|FullName");
			;
			certificate = request.getParameter("1b|Name on Certificate");
			;
			address = request.getParameter("1c|Address");
			;
			contact = request.getParameter("1d|Contact No.");
			;
			email = request.getParameter("1e|Email");
			locId = request.getParameter("1g|Location");
			cat = categoryDelegate.find(Long.parseLong(request
					.getParameter("1g|Location")));
			site = cat.getName();
			lotPrice = request.getParameter("1j|Lot Price with VAT");
			product = request.getParameter("1h|Product Name");
			classification = request.getParameter("1i|Memorial Product");
			pcf = request.getParameter("1k|Perpetual Care");
			paymentOption = request.getParameter("1o|Payment Option");
			monthly = request.getParameter("1r|Monthly Amortization");
			contractPrice = request.getParameter("1q|Contract Price");
			cat = categoryDelegate.find(Long.parseLong(product));
			productName = cat.getName();
			ci = categoryItemDelegate.find(company, Long
					.parseLong(classification));
			classificationName = ci.getName();
			optionName = options.get(Integer.parseInt(paymentOption));
		} else if (!(request.getParameter("1x|PNPA#") == null)) {
			CategoryItem it = new CategoryItem();
			it.setCompany(company);
			it.setName(request.getParameter("1x|PNPA#"));
			it.setPrice(Double.parseDouble(request
					.getParameter("1y|Amount to be Paid")));
			it.setIsValid(Boolean.TRUE);
			it.setParent(categoryDelegate.find(Long.parseLong("3063")));
			it.setParentGroup(groupDelegate.find(Long.parseLong("189")));

			catItem = categoryItemDelegate
					.find(categoryItemDelegate.insert(it));
			totalPrice = request.getParameter("1y|Amount to be Paid");
			finalPrice = request.getParameter("1y|Amount to be Paid");

		}

		return SUCCESS;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	private String message = "";

	public void setMessage(String message) {
		this.message = message;
	}

	private String otherEmail = "";
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	private static final FormPageDelegate formPageDelegate = FormPageDelegate
			.getInstance();
	private FormPage formPage;

	public boolean getContentInForm(String FormName) {

		formPage = formPageDelegate.find(company, FormName);
		return sendEmail(formPage);

	}

	public boolean sendEmail(FormPage formPage) {

		if (formPage == null)
			return false;

		StringBuffer content = new StringBuffer();
		content.append(formPage.getTopContent());
		content.append("<BR><BR>");

		if (message != null) {
			content.append(message);
			content.append("<BR><BR>");
		}

		content = referralContent(content, "<BR/>");

		if (company.getName().equalsIgnoreCase(CompanyConstants.CANCUN)
				&& referralsId != null) {

			List<Referral> refList = referralDelegate.findAllByIds(referralsId,
					company);

			String messageToReferrer = content.toString();

			String claims = "";

			for (String cl : rewardsToClaim) {
				claims += ", " + cl;
			}

			String intro = refList.get(0).getReferredBy().getFullName()
					+ " wish to redeem " + claims + " from this Referral"
					+ ((refList.size() > 1) ? "s" : "") + "<br><br>";

			String lastMessage = "<br>Thank you. <br><br>All the Best,<br<br>"
					+ company.getNameEditable() + " Team.";

			messageToReferrer = intro + messageToReferrer + lastMessage;

			boolean emailSent = EmailUtil.sendWithHTMLFormat(
					"noreply@webtogo.com.ph", company.getEmail().split(","),
					formPage.getTitle(), messageToReferrer, null);

			return emailSent;

		}

		content.append(formPage.getBottomContent());
		content.toString();

		boolean emailSent = EmailUtil.sendWithHTMLFormat(
				"noreply@webtogo.com.ph", company.getEmail().split(","),
				formPage.getTitle(), content.toString(), null);

		return emailSent;
	}

	public boolean sendEmail(Company company, FormPage formPage) {
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName,
				mailerPassword);
		if (formPage == null)
			return false;

		StringBuffer content = new StringBuffer();
		content.append(formPage.getTopContent());
		content.append("<BR><BR>");

		if (message != null) {
			content.append(message);
			content.append("<BR><BR>");
		}

		content.append(formPage.getBottomContent());
		content.toString();

		boolean emailSent = EmailUtil.sendWithHTMLFormat(
				"noreply@webtogo.com.ph", company.getEmail().split(","),
				formPage.getTitle(), content.toString(), null);
		return emailSent;
	}

	private String modeOfPayment;

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public boolean sendEmailPaymentInformation(String name, String email,
			String contactNumber, String address, List<CategoryItem> catItems,
			String filepath,String additionalInstruction, String companyName, String surveyDate ) {
		
		boolean emailSent = false;
		
		try {
			StringBuffer content = new StringBuffer();
			setEmailSettings();
			EmailUtil.connect("smtp.gmail.com", 587, EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);
			String message = "";
			message += "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
			message += "<head>";
			message += "</head>";
			message += "<body style='font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 16px;'>";
			if("life".equalsIgnoreCase(company.getName())){
				message += "<table cellpadding=\"0\" cellspacing=\"0\" width=\"710px\">";
			} else {
				message += "<table cellpadding=\"0\" cellspacing=\"0\" width=\"710px\" background=\"https://sunnysunday.com.ph/images/bg-email.jpg\">";
			}
			message += "<tr>";
	
			boolean isPurpleTag = false;
			boolean isAdEventsManila = false;
			if(company.getName().equals("adeventsmanila"))
				isAdEventsManila = true;
			if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2")))
			{
				isPurpleTag = true;
				if (this.message != null && modeOfPayment != null
				&& modeOfPayment.equalsIgnoreCase("paypal"))
					message += "<td valign=\"top\"><img src=\"https://sunnysunday.com.ph/images/bgHeader-paypal.jpg\" border=\"0\" /></td>";
	
				if (this.message != null && modeOfPayment != null
				&& modeOfPayment.equalsIgnoreCase("bank"))
					message += "<td valign=\"top\"><img src=\"https://sunnysunday.com.ph/images/bgHeader-bank.jpg\" border=\"0\" /></td>";
			}
			else if(company.getName().equalsIgnoreCase("korphilippines"))
			{
				if (this.message != null && modeOfPayment != null
				&& modeOfPayment.equalsIgnoreCase("paypal"))
					message += "<td valign=\"top\"><img src=\"http://www.korwater.ph/images/bgHeader-paypal.jpg\" border=\"0\" /></td>";
	
				if (this.message != null && modeOfPayment != null
				&& modeOfPayment.equalsIgnoreCase("bank"))
					message += "<td valign=\"top\"><img src=\"http://www.korwater.ph/images/bgHeader-bank.jpg\" border=\"0\" /></td>";
			}
			
			message += "</tr>";
			message += "<tr>";
			message += "<td style=\"padding: 0 50px;\">";
			message += "<table cellpadding=\"1\" cellspacing=\"0\">";
			message += "<tr>";
			message += "<td width=\"100px\">Name</td>";
			message += "<td width=\"10px\">:</td>";
			message += "<td><strong>" + name + "</strong></td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td>Email</td>";
			message += "<td>:</td>";
			message += "<td><strong>" + email + "</strong></td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td>Contact Number</td>";
			message += "<td>:</td>";
			message += "<td><strong>" + contactNumber + "</strong></td>";
			message += "</tr>";
			if(!address.contains("null") && company.getName().equals("adeventsmanila")) {
				message += "<tr>";
				message += "<td>Address</td>";
				message += "<td>:</td>";
				message += "<td><strong>" + address + "</strong></td>";
				message += "</tr>";
			} else {
				message += "<tr>";
				message += "<td>Address</td>";
				message += "<td>:</td>";
				message += "<td><strong>" + address + "</strong></td>";
				message += "</tr>";
			}
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.MR_AIRCON)){
				if(!StringUtils.isEmpty(companyName)){
					message += "<tr>";
					message += "<td>Company</td>";
					message += "<td>:</td>";
					message += "<td><strong>" + companyName + "</strong></td>";
					message += "</tr>";			
				}
				if(!StringUtils.isEmpty(surveyDate)){
					message += "<tr>";
					message += "<td>Survey Date</td>";
					message += "<td>:</td>";
					message += "<td><strong>" + surveyDate + "</strong></td>";
					message += "</tr>";			
				}
				if(!StringUtils.isEmpty(additionalInstruction)){
					message += "<tr>";
					message += "<td>Additional Instruction</td>";
					message += "<td>:</td>";
					message += "<td><strong>" + additionalInstruction + "</strong></td>";
					message += "</tr>";			
				}
			}
			
			message += "</table>";
			message += "<br />";
			message += "</td>";
			message += "</tr>";
			
			if (this.message != null && modeOfPayment != null
					&& !modeOfPayment.equalsIgnoreCase("paypal")) {
				message += this.message;
			}
			message += "<tr>";
			message += "<td style=\"padding: 0 50px;\">";
			message += "<table width=\"100%\" cellpadding=\"2\" style=\"padding: 3px; background: #fff; border: 1px solid #dddddd;\">";
			message += "<tr>";
			message += "<th colspan=\"4\">ITEM DETAILS</th>";
			message += "</tr>";
			message += "<TR>";
			
			//header
			if(isAdEventsManila) {
				message += "<th align=\"center\" width=\"40%\" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\">Name</th>";
				message += "<th align=\"center\" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\">Distance</th>";
				message += "<th align=\"center\" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\"><strong>Shirt Size</strong></th>";
				message += "<th align=\"center\" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\"><strong>Price</strong></th>";
				message += "</TR>";
			}
			else {
				message += "<th align=\"center\" "+(isPurpleTag ? "width=\"50%\"" : "width=\"40%\"")+" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\">Item</th>";
				if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2"))){
					message += "<th align=\"center\" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\">Color</th>";
				}
				message += "<th align=\"center\" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\">QTY</th>";
				message += "<th align=\"center\" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\"><strong>Unit Price</strong></th>";
				message += "<th align=\"center\" style=\"background: #e8e969; height: 25px; border-bottom: 1px solid #dddddd;\"><strong>Subtotal</strong></th>";
				message += "</TR>";
			}
			
	
			Double totalAmount = 0.0;
			NumberFormat formatter = new DecimalFormat("#0.00");
			String shippingCost = "";
	
			/** DISCOUNT **/
			String discount = "";
			int discountTotalQuantity = 0;
	
			if (catItems != null) {
				for (CategoryItem cI : catItems) {
					/** DISCOUNT **/
					if (cI.getName().indexOf("Discount") != -1) {
						if (Math.abs(cI.getPrice()) > 0.0) {
							discount += "<TR>";
							discount += "<td align=\"left\">Less 10% Discount </td>";
							if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2"))){
								discount += "<td align=\"center\"></td>";					
							}
							discount += "<td align=\"center\"></td>";
							discount += "<td align=\"right\"></td>";
							discount += "<td align=\"right\"><strong>"
									+ formatter.format(Math.abs(cI.getPrice()))
									+ "</strong></td>";
							discount += "</TR>	";
							
							totalAmount += cI.getPrice();
						}
					} else if (cI.getName().indexOf("Shipping Cost") != -1) {
						totalAmount += cI.getPrice()
								* Double.parseDouble(cI.getOtherDetails());
						shippingCost += "<TR>";
						shippingCost += "<td align=\"left\">Shipping Price </td>";
						if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2"))){
							shippingCost += "<td align=\"center\"></td>";					
						}
						shippingCost += "<td align=\"center\"></td>";
						shippingCost += "<td align=\"right\"></td>";
						shippingCost += "<td align=\"right\"><strong>"
								+ formatter.format((cI.getPrice()))
								+ "</strong></td>";
						shippingCost += "</TR>";
					} else {
						message += "<TR>";
						
						if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2"))){
							message += "<td align=\"left\">" + cI.getName()+ "</td>";
							message += "<td align=\"left\">"
							+ (cI.getDescription()!=null?cI.getDescription():"") + "</td>";
						}else{
							message += "<td align=\"left\">" + cI.getName() + "("
							+ cI.getDescription() + ")" + "</td>";
						}
						message += "<td align=\"center\">" + cI.getOtherDetails()
								+ "</td>";
						
						//new add discount until june 30, 2014 only
						// start
						if((company.getName().equalsIgnoreCase("purpletag")) && (cI.getParentGroup().getId() == 214) 
								&& (!cI.getParent().getName().contains("KOR AURA") && !cI.getParent().getName().contains("KOR MESA") && !cI.getParent().getName().contains("KOR NAVA") && !cI.getParent().getName().contains("REPLACEMENT PARTS"))){
							message += "<td align=\"right\">Less 10% <del>"+formatter.format(cI.getPrice())+"</del> <strong>"
									+ formatter.format(cI.getPrice() * 0.90)
									+ "</strong></td>";
							message += "<td align=\"right\"><strong>"
								+ formatter.format((cI.getPrice() * Double
										.parseDouble(cI.getOtherDetails()) * 0.90))
								+ "</strong></td>";
							message += "</TR>	";
							
						}
						else if((company.getName().equalsIgnoreCase("purpletag2")) && (cI.getItemDetailMap() != null && cI.getItemDetailMap().get("Discount") != null && !cI.getItemDetailMap().get("Discount").equalsIgnoreCase("") && !cI.getItemDetailMap().get("Discount").equalsIgnoreCase("0"))) {
							Double percentDiscount = new Double("0."+cI.getItemDetailMap().get("Discount"));
							message += "<td align=\"right\">Less "+cI.getItemDetailMap().get("Discount")+"% <del>"+formatter.format(cI.getPrice())+"</del> <strong>"
									+ formatter.format(cI.getPrice() - (cI.getPrice() * percentDiscount))
									+ "</strong></td>";
									message += "<td align=\"right\"><strong>"
										+ formatter.format((cI.getPrice() - (cI.getPrice() * percentDiscount)) * Double.parseDouble(cI.getOtherDetails()))
										+ "</strong></td>";
									message += "</TR>	";
						}
						//end
						else if((company.getName().equalsIgnoreCase("purpletag2")) && (cI.getParentGroup().getId() == 325 || cI.getParentGroup().getId() == 313)
								&& (!cI.getName().contains("KOR AURA") && !cI.getName().contains("KOR MESA") && !cI.getName().contains("KOR NAVA") && !cI.getName().contains("KOR SPORT") && !cI.getParent().getName().contains("REPLACEMENT PARTS"))){
							message += "<td align=\"right\">Less 10% <del>"+formatter.format(cI.getPrice())+"</del> <strong>"
							+ formatter.format(cI.getPrice() * 0.90)
							+ "</strong></td>";
							message += "<td align=\"right\"><strong>"
								+ formatter.format((cI.getPrice() * Double
										.parseDouble(cI.getOtherDetails()) * 0.90))
								+ "</strong></td>";
							message += "</TR>	";
							
						}
						
						else {
							message += "<td align=\"right\"><strong>"
								+ formatter.format(cI.getPrice())
								+ "</strong></td>";
							message += "<td align=\"right\"><strong>"
								+ formatter.format((cI.getPrice() * Double
										.parseDouble(cI.getOtherDetails())))
								+ "</strong></td>";
							message += "</TR>	";
							
						}
						
						totalAmount += cI.getPrice() * Double.parseDouble(cI.getOtherDetails());
	
						/** DISCOUNT **/
						if (cI.getPrice() > 1349)
							discountTotalQuantity += Integer.parseInt(cI.getOtherDetails());
					}
				}
				
				System.out.println("total Price : " + totalAmount);
				
				message += shippingCost;
	
				/** DISCOUNT **/
				if(!(company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2"))) {
					if (discountTotalQuantity >= 2) {
						message += discount;
					}
				}
	
				message += "<TR>";
				message += "<td align=\"left\"  style=\"border-top: 1px solid #dddddd;\"></td>";
				if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2"))){
					message += "<td align=\"center\"  style=\"border-top: 1px solid #dddddd;\"></td>";				
				}
				message += "<td align=\"center\"  style=\"border-top: 1px solid #dddddd;\"></td>";
				message += "<td align=\"right\"  style=\"border-top: 1px solid #dddddd;\"><strong>Total :</strong></td>";
				message += "<td align=\"right\"  style=\"border-top: 1px solid #dddddd; color:#0066CC\"><strong>"
						+ formatter.format(totalAmount) + "</strong></td>";
				message += "</TR>		";
			} else if (this.message != null && modeOfPayment != null
					&& modeOfPayment.equalsIgnoreCase("paypal")) {
				message += this.message;
			}
	
			if("life".equalsIgnoreCase(company.getName())) {
				
			}
			else {
				
				message += "<tr>";
				message += "<td style=\"padding: 0 50px;\">";
				message += "<p><strong>Shipping Details :</strong>";
				message += "<br />";
				
				if(company.getName().equals("adeventsmanila")) {
					message += "Items will be shipped once payment has been confirmed.  Shipping fee is shouldered by the buyer. "+company.getNameEditable()+" is not liable for any delay in shipment, damage or loss of package.";
				} else {
					message += "Items will be shipped once payment has been confirmed.  Shipping fee is shouldered by the buyer. "+company.getNameEditable()+" is not liable for any delay in shipment, damage or loss of package. A tracking number will be sent to you via email or text.";
				}
				message += "</p>		";
				
				if(company.getName().equals("adeventsmanila")) {
					message += "Delivery time : <strong>1-2 days for standard</strong>";
				} else {
					message += "Via : <strong>WIDE WIDE WORLD EXPRESS</strong><br />";
					message += "Delivery time : <strong>1-2 days for standard</strong>";
				}
			}
			
			message += "</td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td style=\"padding: 0 50px;\">";
			message += "<br /><br />";
			message += "<p>";
			message += "Thanks,<br /><br />";
			message += "<span style=\"color:#9933CC\">"+company.getNameEditable()+"</span>";
			message += "	</p>";
	
			message += "</td>";
			message += "</tr>";
			message += "</table>";
	
			if((company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2")))
				message += "<strong><p><font color='red'>*This email confirmation is an auto-generated message. Replies to automated messages are not monitored. For questions and inquiries kindly email it to info@sunnysunday.com.ph</font></p></strong>";
			else if(company.getName().equalsIgnoreCase("korphilippines"))
				message += "<strong><p><font color='red'>*This email confirmation is an auto-generated message. Replies to automated messages are not monitored. For questions and inquiries kindly email it to info@korwater.ph</font></p></strong>";
			
			message += "</td>";
			message += "</tr>";
			
			if("life".equalsIgnoreCase(company.getName())){
				company.setEmail("info@lifeyogacenter.com,ren@ivant.com");
			} else {
				message += "<tr>";
				message += "<td><img src=\"https://sunnysunday.com.ph/images/bgFooter.jpg\" /></td>";
				message += "</tr>";
			}
	
			message += "</p>	";
			message += "</td>";
			message += "</tr>";
			message += "</table>";
			message += "</body>";
			message += "</html>";
	
			if(!filepath.equals(""))
			{
				String[] companyEmails = company.getEmail().split(",");
				if (companyEmails != null && companyEmails.length != 0){
					for (String companyEmail : companyEmails) {
						if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
							setEmailSettings();
							EmailUtil.connect("smtp.gmail.com", 587, EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);
						}
						
						emailSent = EmailUtil.sendWithHTMLFormat(
							"noreply@webtogo.com.ph", companyEmail.trim(),
							this.subject, message, filepath);
					}
				}
					
			}
			else
			{
				String[] companyEmails = company.getEmail().split(",");
				if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
					logger.info("Company Emails : " + company.getEmail());
				}
				if (companyEmails != null && companyEmails.length != 0){
					for (String companyEmail : companyEmails) {
						if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
							setEmailSettings();
							EmailUtil.connect("smtp.gmail.com", 587, EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);
						}
						
						emailSent = EmailUtil.sendWithHTMLFormat(
							"noreply@webtogo.com.ph", companyEmail.trim(),
							this.subject, message, null);
					}
				}
					
			}
	
			if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
				logger.info("Customer Email : " + this.email);
			}
			if (this.email.length() != 0)
				if(company.getName().equalsIgnoreCase("PURPLETAG2")) {
					setEmailSettings();
					EmailUtil.connect("smtp.gmail.com", 587, EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);
				}
				emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph",
						this.email, this.subject, message, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return emailSent;
	}
	
	public boolean sendEmailPaymentInformationFromLife(Company company, CartOrder cartOrder, Member member, boolean freeEvent) {
		boolean emailSent = false;
		try {
			logger.info("SENDING EMAIL PAYMENT FROM LIFE");
			String message = "";
			message+="Name: "+cartOrder.getName()+"<br/><br/>";
			message+="Email: "+cartOrder.getEmailAddress()+"<br/><br/>";
			message+="Contact: "+cartOrder.getPhoneNumber()+"<br/><br/>";
			message+="Address: "+cartOrder.getAddress1()+"<br/><br/>";
			message+="Purchased Item <br/><br/>";
			message+=""+cartOrder.getItems().get(0).getItemDetail().getName()+" P"+cartOrder.getItems().get(0).getItemDetail().getPrice()+"<br/><br/>";

			String subject = "LiFE Yoga Payment Confirmation";
			
			String content = message;
			
			if(cartOrder.getInfo3().equalsIgnoreCase("Packages")) {
				message="<br/><br/><img src='http://lifeyogacenter.com/images/LifeTopv2.jpg'/>";
				message+="<br/><img src='http://lifeyogacenter.com/images/Life1Mid.png'/>";
				message+="<br/><a href='http://www.lifeyogacenter.com/#schedules'><img src='http://lifeyogacenter.com/images/Life1Bottom.png'/></a><br/><br/>";
			}
			
			if(cartOrder.getInfo3().equalsIgnoreCase("Events")) {
				String[] eventDetail = cartOrder.getInfo5().split("&&");
				message = eventEmailContentFromLife.replace("--NAME OF EVENT--", eventDetail[0].split("==")[1])
						.replace("--SCHEDULE--", eventDetail[1].split("==")[1])
						.replace("--PLACE--", eventDetail[2].split("==")[1]);
				subject = "LiFE Event Payment Confirmation";
				
				if(freeEvent)
					subject = "LiFE Event Confirmation";
			}
			
			if(cartOrder.getInfo3().equalsIgnoreCase("Promos")) {
				subject = "LiFE Promo Payment Confirmation";
				message="<br/><br/><img src='http://lifeyogacenter.com/images/LifeTopv2.jpg'/>";
				message+="<br/><img src='http://lifeyogacenter.com/images/Life1Mid.png'/>";
				message+="<br/><a href='http://www.lifeyogacenter.com/#schedules'><img src='http://lifeyogacenter.com/images/Life1Bottom.png'/></a><br/><br/>";
				if(freeEvent)
					subject = "LiFE Event Confirmation";
			}
			
			
			String email = cartOrder.getEmailAddress()+","+"events@lifeyogacenter.com";
			
			if (cartOrder.getInfo3().equalsIgnoreCase("Promos")) {
				email = cartOrder.getEmailAddress() + "," + "info@lifeyogacenter.com";
			}
			
			final String[] cc = email.split(",");
			this.company = company;
			setEmailSettings();
			LifeEmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
			emailSent = LifeEmailUtil.sendWithHTMLFormatWithCCFromLife("info@lifeyogacenter.com", cc, subject, message, null);
			
			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(cartOrder.getName());
			savedEmail.setMember(member);
			savedEmail.setEmail(cartOrder.getEmailAddress());
			savedEmail.setPhone(cartOrder.getPhoneNumber());
			savedEmail.setFormName(cartOrder.getInfo3());
			savedEmail.setOtherField1(cartOrder.getItems().get(0).getItemDetail().getName());
			savedEmail.setEmailContent(content);
			savedEmail.setTestimonial("");
			
			savedEmail.setReceipt("");
			savedEmail.setPromo(cartOrder.getInfo1());//promo code
			
			if(cartOrder.getInfo3().equalsIgnoreCase("Events")) {
				savedEmail.setOtherField3(cartOrder.getInfo5());
			}
			
			savedEmailDelegate.insert(savedEmail);
			if(member != null) {
				
			} else {
				setNotificationMessage("PAYMENT SUCCESSFUL (Non-Member)");
			}
			logger.info("SUCCESS--- SENDING EMAIL PAYMENT FROM LIFE");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return emailSent;
	}

	public boolean sendBookingScheduleFromLife(Company company, String senderEmail, String subject, StringBuffer content, String fileName) {
		final String[] cc = { senderEmail, "info@lifeyogacenter.com"};
		this.company = company;
		setEmailSettings();
		LifeEmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		boolean emailSent = LifeEmailUtil.sendWithHTMLFormatWithCCFromLife("info@lifeyogacenter.com", cc, subject, content.toString(), null);
		return emailSent;
	}
	
	public boolean sendEmailSaltswim(Company company, String email, String subject, StringBuffer content, String fileName) {
		String emails = company.getEmail() + ", " + email;
		String[] to = emails.split(",");
		this.company = company;
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);

		boolean emailSent = EmailUtil.sendWithHTMLFormatWithCC(EmailUtil.DEFAULT_USERNAME, to, subject, content.toString(), null);
		return emailSent;
	}
	// send confirmation to client
	public boolean sendEmailConfirmationPureNectar(Company company, String to, String subject, StringBuffer content, String fileName) {
		String emails = to;
		
		if(emails.equals("")){
			emails =  company.getEmail();
		}else{
			emails =  emails + ", " + company.getEmail();
		}
		
		String[] sendTo = emails.split(",");
		this.company = company;
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);

		boolean isEmailSent = EmailUtil.sendWithHTMLFormatWithCC(mailerUserName, sendTo, subject, content.toString(), null);

		return isEmailSent;
		
	}
	
	@SuppressWarnings("unchecked")
	public String sendInquiryToPurenectar(){
		
		JSONObject obj = new JSONObject();
		
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");

		StringBuffer content = new StringBuffer();
		String to = company.getEmail() + ", " + request.getParameter("1b|email");
		subject = request.getParameter("subject");
		String title = request.getParameter("title");
		String name = request.getParameter("1a|name");
		String email = request.getParameter("1b|email");
		String inquiry = request.getParameter("1c|message");
		
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);
		
		content.append("<center><img src='http://purenectar.co/images/logo-black-160px.png' /></center><br><br>");
		content.append("Greetings from Pure Nectar!<br><br>");
		
		content.append("<p>We appreciate your interest in franchising Pure Nectar! ");
		content.append("Please email mayette@fruitmagic.com.ph for the following details below to begin the process.</p><br><br>");
		
		content.append("<p style='margin-left:20px;'>Full Name: <br>");
		content.append("Home Address: <br>");
		content.append("Mobile Number: <br>");
		content.append("E-mail Address: <br>");
		content.append("Area/Location Proposed for Franchised Outlet: (Pls. describe) <br>");
		content.append("Business Background: <br><br></p>");
		
		content.append("<p>To request for a business meeting and/or presentation, please provide two (2) date and time options and venues as well.</p><br><br>");
		
		content.append("Thank you very much!<br><br>");
		
		content.append("Pure Nectar Team");
		
		EmailSenderAction emailSenderAction = new EmailSenderAction();
		emailSenderAction.sendEmailConfirmationPureNectar(company, email, title, content, null);
		
		
		obj.put("success", SUCCESS);
		obj.put("message", "success");
		
		
		setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		return SUCCESS;
	}
	
	public boolean sendEmailPaymentManyAttachments(String invoiceDate, String name, String email, String mobile, String country, 
			String invoiceNumber, String city, String address, String landmarks, String promo, List<CategoryItem> catItems, 
			String[] filepath, String additionalInstruction, String companyName, String surveyDate ) {
		final String UPLOADED_PHOTO = "Uploaded Photo";
		final String DIY = "DIY";
		final Double VAT = 0.12d;
		final Double VATABLE = 100d / 112d;
		logger.info("INSIDE sendEmailPaymentManyAttachments...");
		boolean emailSent = false;
		logger.info("BEFORE setEmailSettings()...");
		setEmailSettings();
		logger.info("AFTER setEmailSettings()...");
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		logger.info("AFTER EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);...");
		String message = "";
		message += "<html xmlns='http://www.w3.org/1999/xhtml'>";
		message += "<head>";
		message += "</head>";
		message += "<body style='font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 16px;'>";
		message += "<table cellpadding='0' cellspacing='0' width='710px'>";
		message += "<tr>";
		message += "<td valign='top'><img src='http://tomato.webtogo.com.ph/images/email-header.jpg' border='0' /></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='padding: 0 50px;'>";
		message += "<table cellpadding='1' cellspacing='0'>";
		message += "<tr>";
		message += "<td align='right' width='100px'>Invoice Date</td>";
		message += "<td width='10px'>:</td>";
		message += "<td><strong>" + invoiceDate + "</strong></td>";
		message += "<td align='right' width='100px'>Invoice Number</td>";
		message += "<td width='10px'>:</td>";
		message += "<td><strong>" + invoiceNumber + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Name</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + name + "</strong></td>";
		message += "<td align='right'>Area/Province</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + city + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Email</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + email + "</strong></td>";
		message += "<td align='right' rowspan='2'>Shipping Address</td>";
		message += "<td rowspan='2'>:</td>";
		message += "<td rowspan='2'><strong>" + address + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Mobile Number</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + mobile + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Country</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + country + "</strong></td>";
		message += "<td align='right'>Landmarks</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + landmarks + "</strong></td>";
		message += "</tr>";
		message += "</table>";
		message += "<br />";
		message += "</td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='padding: 0 50px;'>";
		message += "<table width='100%' cellpadding='2' style='padding: 3px; background: #fff; border: 1px solid #dddddd;'>";
		message += "<tr>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>SKU</th>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>ITEM</th>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>QTY</th>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>PRICE</th>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>AMOUNT</th>";
		message += "</tr>";
		logger.info("BEFORE content of email....");
		Double totalAmount = 0.0;
		Double discountAmount = 0.0;
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		String shippingCost = "";
		String discountCost = "";

		if (catItems != null) {
			for (CategoryItem cI : catItems) {
				if (cI.getName().indexOf("Shipping Cost") != -1) {
					totalAmount += cI.getPrice() * Double.parseDouble(cI.getOtherDetails());
					shippingCost += "<tr>";
					shippingCost += "<td align='right' colspan='4'>Shipping:</td>";
					shippingCost += "<td align='right'>";
					shippingCost += formatter.format((cI.getPrice()));
					shippingCost += "</td>";
					shippingCost += "</tr>";
				} else if (cI.getName().indexOf("Discount") != -1) {
					discountAmount = cI.getPrice();
					discountCost += "<tr>";
					discountCost += "<td align='right' colspan='4'>Discount:</td>";
					discountCost += "<td align='right'>";
					discountCost += formatter.format((cI.getPrice()));
					discountCost += "</td>";
					discountCost += "</tr>";
				} else {
					String faceSku = cI.getOtherDetail().getFace() != null ? cI.getOtherDetail().getFace().getSku() : "";
					String faceName = !cI.getOtherDetail().getFace().getName().equals(UPLOADED_PHOTO) ? cI.getOtherDetail().getFace().getName() : DIY;
					Double facePrice = cI.getOtherDetail().getFace().getPrice();
					String quantity = cI.getOtherDetails();
					
					message += "<tr>";
					message += "<td align='left'>" + faceSku + "</td>";
					message += "<td align='left'>" + faceName + " Face</td>";
					message += "<td align='center'>" + quantity + "</td>";
					message += "<td align='right'>";
					message += formatter.format(facePrice);
					message += "</strong></td>";
					message += "<td align='right'>";
					message += formatter.format(facePrice * Double.parseDouble(quantity));
					message += "</td>";
					message += "</tr>	";

					message += "<tr>";
					message += "<td align='left'>" + cI.getSku() + "</td>";
					message += "<td align='left'>" + cI.getName() + " Strap</td>";
					message += "<td align='center'>" + quantity + "</td>";
					message += "<td align='right'>";
					message += formatter.format(cI.getPrice());
					message += "</td>";
					message += "<td align='right'>";
					message += formatter.format(((cI.getPrice()) * Double.parseDouble(quantity)));
					message += "</td>";
					message += "</tr>	";

					totalAmount += facePrice * Double.parseDouble(quantity);
					totalAmount += cI.getPrice() * Double.parseDouble(quantity);
				}
			}

			message += shippingCost;
			message += "<tr>";
			message += "<td align='right' colspan='4'>Subtotal:</td>";
			message += "<td align='right'>" + formatter.format(totalAmount) + "</td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td align='right' colspan='4'>Promo Code:</td>";
			message += "<td align='right'>" + promo + "</td>";
			message += "</tr>";
			message += discountCost;
			totalAmount = totalAmount - discountAmount;
			Double vatableTotal = totalAmount * VATABLE;
			Double vat = vatableTotal * VAT;
			message += "<tr>";
			message += "<td align='right' colspan='4'>Amount Due:</td>";
			message += "<td align='right'>" + formatter.format(totalAmount) + "</td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td align='right' colspan='4'>12% VAT:</td>";
			message += "<td align='right'>" + formatter.format(vat) + "</td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td align='right' colspan='4'>Vatable Total:</td>";
			message += "<td align='right'>" + formatter.format(vatableTotal) + "</td>";
			message += "</tr>";
		} 
		
		message += "<tr><td>&nbsp;</td></tr>";
		message += "<tr>";
		message += "<td colspan='5' style='padding: 0 50px;'>";
		message += "Thanks for ordering from www.swapDIY.com! We are now processing your order. If you selected the 1st payment option, please make your payment and email ";
		message += "scanned copy of your validated deposit slip to swapDIY@tomato.ph or Viber to 0918-886-6286 and indicate your DIY Invoice Number. Please email your proof of ";
		message += "payment within the next 2 working days to avoid cancellation of your order.";
		message += "</td>";
		message += "</tr>";
		message += "<tr><td>&nbsp;</td></tr>";
		message += "<tr>";
		
		
		
		/*
		message += "<td colspan='2' style='padding: 0 50px;' nowrap>";
		message += "<strong>BPI</strong><br/>Account Name: PrimeBrands Inc.<br/>Savings Account No. 1881-0462-41 ";
		message += "</td>";
		
		message += "<td colspan='2' style='padding: 0 50px;' nowrap>";
		message += "<strong>BDO</strong><br/>Account Name: PrimeBrands Inc.<br/>Savings Account No. 488-005-3905";
		message += "</td>";
		*/
		
		message += "<td colspan='2' style='padding: 0 50px;' nowrap>";
		message += "<strong>BPI</strong><br/>Account Name: Primesphere Inc.<br/>Account No. 1881-0464-97 ";
		message += "</td>";
		
		message += "<td colspan='2' style='padding: 0 50px;' nowrap>";
		message += "<strong>BDO</strong><br/>Account Name: Primesphere Inc.<br/>Account No. 488-007-5216";
		message += "</td>";
		
		message += "</tr>";
		message += "<tr><td>&nbsp;</td></tr>";
		/*
		message += "<tr>";
		message += "<td colspan='2' style='padding: 0 50px;' nowrap>";
		message += "<strong>GCash</strong><br/>Account Name: PrimeBrands Inc.<br/>Account No. 0917-537-9438";
		message += "</td>";
		message += "<td colspan='2' style='padding: 0 50px;' valign='top' nowrap>";
		message += "<strong>LBC Send & Swipe Card</strong><br/>Card No. 4215-8500-2340-8496<br/>Consignee Name: Daniel T. Dionisio, Jr.<br/>Address: TOMATO by PrimeBrands, Inc., Makati, M.M.<br/>Note: payments via Instant Pera Padala will not be accepted.";
		message += "</td>";
		message += "</tr>";
		*/
		
		
		
		message += "<tr><td>&nbsp;</td></tr>";
		message += "<tr>";
		message += "</tr>";
		message += "<tr><td>&nbsp;</td></tr>";
		message += "<tr>";
		message += "<td colspan='5' style='padding: 0 50px;'>";
		message += "Items will be shipped once payment has been confirmed. Our office hours are Mondays - Fridays, 8:00 AM - 5:00 PM. Payments and inquiries sent during weekends and holidays will be processed the next working day. If you have any questions, please feel free to contact us.";
		message += "</td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='padding: 0 50px;'>";
		message += "<br/>Thanks,<br /><br />";
		message += "<span style='color:#f1592a'>"+company.getNameEditable()+"</span>";
		message += "</td>";
		message += "</tr>";
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "</p>	";
		message += "</td>";
		message += "</tr>";
		message += "</table>";
		message += "</body>";
		message += "</html>";
		logger.info("AFTER generating email content...");
		String[] companyEmails = company.getEmail().split(",");
		if (companyEmails != null && companyEmails.length != 0) {
			for (String companyEmail : companyEmails) {
				logger.info("EMAIL WILL BE SEND TO : "+companyEmail);
				emailSent = EmailUtil.sendWithHTMLFormatManyAttachments("noreply@webtogo.com.ph", companyEmail.trim(), this.subject, message, filepath);
				if(emailSent){
					logger.info("EMAIL TO "+companyEmail+" WAS SUCCESSFUL...");
				}
				else{
					logger.info("EMAIL TO "+companyEmail+" FAILED...");
				}
			}
		}

		if (this.email.length() != 0){
			
			EmailUtil.connectViaWebtogo();
			emailSent = EmailUtil.sendWithHTMLFormatManyAttachments("noreply@webtogo.com.ph", this.email, this.subject, message, filepath);
			logger.info("EMAIL TO "+email+" IS " + emailSent);
		}
		return emailSent;
	}
	
	public boolean sendSwapCanadaEmailPayment(String invoiceDate, String invoiceNumber, String usTerritory, String name, 
			String business, String floor, String poBox, String city, String state, String zipcode, String contact, String promo, 
			List<CategoryItem> catItems, String[] filepath) {
		final String DIY = "DIY";
		final String UPLOADED_PHOTO = "Uploaded Photo";
		final Double VAT = 0.12d;
		final Double VATABLE = 100d / 112d;
		boolean emailSent = false;
		String message = "";
		
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		
		message += "<html xmlns='http://www.w3.org/1999/xhtml'>";
		message += "<head>";
		message += "</head>";
		message += "<body style='font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 16px;'>";
		message += "<table cellpadding='0' cellspacing='0' width='710px'>";
		message += "<tr>";
		message += "<td valign='top'><img src='http://tomato.webtogo.com.ph/images/email-header.jpg' border='0' /></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='padding: 0 50px;'>";
		message += "<table cellpadding='1' cellspacing='0'>";
		message += "<tr>";
		message += "<td align='right' width='100px'>Invoice Date</td>";
		message += "<td width='10px'>:</td>";
		message += "<td><strong>" + invoiceDate + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right' width='100px'>Invoice Number</td>";
		message += "<td width='10px'>:</td>";
		message += "<td><strong>" + invoiceNumber + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right' width='100px'>United States / Canada</td>";
		message += "<td width='10px'>:</td>";
		message += "<td><strong>" + usTerritory + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Name</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + name + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Business or C/O</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + business + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>APT, Suite or Floor</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + floor + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>P.O. Box</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + poBox + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>City</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + city + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>State / Province</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + state + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Zip / Postal Code</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + zipcode + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Email</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + email + "</strong></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td align='right'>Contact Number</td>";
		message += "<td>:</td>";
		message += "<td><strong>" + contact + "</strong></td>";
		message += "</tr>";
		message += "</table>";
		message += "<br />";
		message += "</td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='padding: 0 50px;'>";
		message += "<table width='100%' cellpadding='2' style='padding: 3px; background: #fff; border: 1px solid #dddddd;'>";
		message += "<tr>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>PRODUCT ITEM</th>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>UNIT PRICE</th>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>QTY</th>";
		message += "<th align='center' style='background: #f1592a; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>AMOUNT</th>";
		message += "</tr>";

		Double totalAmount = 0.0;
		Double discountAmount = 0.0;
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		String shippingCost = "";
		String discountCost = "";
		String faceName = "";
		Double facePrice = 0d;
		String quantity = "";
		String image = "";

		if (catItems != null) {
			for (CategoryItem cI : catItems) {
				if (cI.getName().indexOf("Shipping Cost") != -1) {
					totalAmount += cI.getPrice() * Double.parseDouble(cI.getOtherDetails());
					shippingCost += "<tr>";
					shippingCost += "<td align='right' colspan='3'>Shipping:</td>";
					shippingCost += "<td align='right'>";
					shippingCost += formatter.format((cI.getPrice()));
					shippingCost += "</td>";
					shippingCost += "</tr>";
				} else if (cI.getName().indexOf("Discount") != -1) {
					discountAmount = cI.getPrice();
					discountCost += "<tr>";
					discountCost += "<td align='right' colspan='3'>Discount:</td>";
					discountCost += "<td align='right'>";
					discountCost += formatter.format((cI.getPrice()));
					discountCost += "</td>";
					discountCost += "</tr>";
				} else {
					image = cI.getShortDescription();
					quantity = cI.getOtherDetails();

					if(image != null && StringUtils.contains(image, PNG_EXTENSION)) {
						faceName = "";
						facePrice = 0d;
						
						if(cI.getOtherDetail() != null && cI.getOtherDetail().getFace() != null) {
							faceName = !cI.getOtherDetail().getFace().getName().equals(UPLOADED_PHOTO) ? cI.getOtherDetail().getFace().getName() : DIY;
							facePrice = cI.getOtherDetail().getFace().getPrice();
						
							message += displaySwapCanadaItem(faceName, facePrice, quantity);
						}
	
						message += displaySwapCanadaItem(cI.getName(), cI.getPrice(), quantity);
	
						totalAmount += facePrice * Double.parseDouble(quantity);
					}
					else if(image != null) {
						String[] img = image.split(",");
						CategoryItem strap1 = null;
						CategoryItem strap2 = null;
						
						message += displaySwapCanadaItem(cI.getName(), cI.getPrice(), quantity);
						
						if(img != null && img.length > 0 && StringUtils.isNotEmpty(img[0])) {
							strap1 = categoryItemDelegate.findSKU(company, img[0]);
							message += displaySwapCanadaItem(INDENT + strap1.getName(), ZERO, quantity);
						}
						
						if(img != null && img.length > 1 && StringUtils.isNotEmpty(img[1])) {
							strap2 = categoryItemDelegate.findSKU(company, img[1]);
							message += displaySwapCanadaItem(INDENT + strap2.getName(), ZERO, quantity);
						}
					}
					
					totalAmount += cI.getPrice() * Double.parseDouble(quantity);
				}
			}

			if(promo == null) {
				promo = ""; 
			}
			
			message += shippingCost;
			message += "<tr>";
			message += "<td align='right' colspan='3'>Subtotal:</td>";
			message += "<td align='right'>" + formatter.format(totalAmount) + "</td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td align='right' colspan='3'>Promo Code:</td>";
			message += "<td align='right'>" + promo + "</td>";
			message += "</tr>";
			message += discountCost;
			totalAmount = totalAmount - discountAmount;
			Double vatableTotal = totalAmount * VATABLE;
			Double vat = vatableTotal * VAT;
			message += "<tr>";
			message += "<td align='right' colspan='3'>Amount Due:</td>";
			message += "<td align='right'>" + formatter.format(totalAmount) + "</td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td align='right' colspan='3'>12% VAT:</td>";
			message += "<td align='right'>" + formatter.format(vat) + "</td>";
			message += "</tr>";
			message += "<tr>";
			message += "<td align='right' colspan='3'>Vatable Total:</td>";
			message += "<td align='right'>" + formatter.format(vatableTotal) + "</td>";
			message += "</tr>";
		} 
		
		message += "<tr><td>&nbsp;</td></tr>";
		message += "<tr>";
		message += "<td colspan='5' style='padding: 0 50px;'>";
		message += "Thanks for ordering from www.swapcanada.com! We are now processing your order. If you selected the 1st payment option, please make your payment and email ";
		message += "scanned copy of your validated deposit slip to swapDIY@tomato.ph or Viber to 0918-886-6286 and indicate your DIY Invoice Number. Please email your proof of ";
		message += "payment within the next 2 working days to avoid cancellation of your order.";
		message += "</td>";
		message += "</tr>";
		message += "<tr><td>&nbsp;</td></tr>";
		message += "<tr>";
		message += "<td colspan='5' style='padding: 0 50px;'>";
		message += "Items will be shipped once payment has been confirmed. Our office hours are Mondays - Fridays, 8:00 AM - 5:00 PM. Payments and inquiries sent during weekends and holidays will be processed the next working day. If you have any questions, please feel free to contact us.";
		message += "</td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='padding: 0 50px;'>";
		message += "<br/>Thanks,<br /><br />";
		message += "<span style='color:#f1592a'>"+company.getNameEditable()+"</span>";
		message += "</td>";
		message += "</tr>";
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "</p>	";
		message += "</td>";
		message += "</tr>";
		message += "</table>";
		message += "</body>";
		message += "</html>";

		String[] companyEmails = company.getEmail().split(",");
		if (companyEmails != null && companyEmails.length != 0) {
			for (String companyEmail : companyEmails) {
				emailSent = EmailUtil.sendWithHTMLFormatManyAttachments("noreply@webtogo.com.ph", companyEmail.trim(), this.subject, message, filepath);
			}
		}

		if (this.email.length() != 0)
			emailSent = EmailUtil.sendWithHTMLFormatManyAttachments("noreply@webtogo.com.ph", this.email, this.subject, message, filepath);

		return emailSent;
	}	
	
	private String displaySwapCanadaItem(String name, Double price, String quantity) {
		String message = "";
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		
		message += "<tr>";
		message += "<td align='left'>" + name + " Face</td>";
		message += "<td align='right'>";
		message += formatter.format(price);
		message += "</strong></td>";
		message += "<td align='center'>" + quantity + "</td>";
		message += "<td align='right'>";
		message += formatter.format(price * Double.parseDouble(quantity));
		message += "</td>";
		message += "</tr>";
		
		return message;
	}
	
	public boolean sendWendysOrderConfirmation(String shippingType, String orderId, String name, String preferredStore, String preferredDate, String preferredTime, String address, String city, String province, String country, String date, String phoneNumber, String paymentType, String orderStatus, String paymentStatus, List<CartOrderItem> cartOrderItems, String deliveryCharge, String totalPrice, String grandTotalPrice, String emailAddr) {
		boolean emailSent = false;
		try{
			
		company = companyDelegate.find(Long.parseLong("404"));
		String message = "";
		CartOrder cart_order_ = CartOrderDelegate.getInstance().find(Long.parseLong(orderId));
		
		String strReceiver = cart_order_.getComments();
		String strLimitter1 = "";
		String strLimitter2 = "";
		
		strLimitter1 = "Special Instruction: ";
		 
		 if((strReceiver.indexOf("Change For:") >= 0) && (strReceiver.indexOf("Special Instruction:") >= 0)){
			 //when special instruction and 'Change for' were specified, use 'Change For' as Delimitter
			 strLimitter2 = "Change For:";
			 strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
			
		 }
		 else if((strReceiver.indexOf("Prefferred Store:") >= 0)  && (strReceiver.indexOf("Special Instruction:") >= 0)){
			 //when special instruction and 'Prefferred Store' were specified, use 'Prefferred Store' as delimitter
			 strLimitter2 = "Prefferred Store:";
			 strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
			 
		 }
		 else if((strReceiver.indexOf("Change For:") < 0) && (strReceiver.indexOf("Prefferred Store:") < 0)  && (strReceiver.indexOf("Special Instruction:") >= 0)){
			 strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.length()).trim();
			
		 }
		 else if((strReceiver.indexOf("Special Instruction:") < 0)){
			 //when special instruction  is not specified, do the following code
			 strLimitter1 = "";
			 
		 }
		 
		// strLimitter2 = "Preffered Date: ";
		// strLimitter3 = "Preffered Time: ";
		
		//strLimitter1 = strReceiver.substring(strReceiver.indexOf(strLimitter1)+(strLimitter1.length()-1),strReceiver.indexOf(strLimitter2)).trim();
		//strLimitter2 = strReceiver.substring(strReceiver.indexOf(strLimitter2)+(strLimitter2.length()-1),strReceiver.indexOf(strLimitter3)).trim();
		//strLimitter3 = strReceiver.substring(strReceiver.indexOf(strLimitter3)+(strLimitter3.length()-1),strReceiver.length()).trim();
		
		
		
		
		
		
		
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		//EmailUtil.connect("smtp.gmail.com", 587, "system@ivant.com","ivanttechnologies2009");
		
		//EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		message += "<html xmlns='http://www.w3.org/1999/xhtml'>";
		message += "<head>";
		message += "</head>";
		message += "<body style='font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 16px;'>";
		message += "<table cellpadding='0' cellspacing='0' width='710px'>";
		message += "<tr>";
		message += "<td valign='top'><center><img src='http://wendys.webtogo.com.ph/images/"+company.getLogo()+"' border='0' align='center' /></center></td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='padding: 0 50px;'>";
		message += "<table cellpadding='1' cellspacing='0'>";
		
		message += "<tr>";
		message += "<td align='right' width='100px' style = 'text-align:right;'>Order Date :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + date + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='100px' style = 'text-align:right;'>Order ID :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + orderId + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Name :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + name + "</strong></td>";
		message += "</tr>";
		
		if(shippingType.equalsIgnoreCase("delivery")){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Address :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + address + "</strong></td>";
			message += "</tr>";
			
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>City :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + city + "</strong></td>";
			message += "</tr>";
			
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Province :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + province + "</strong></td>";
			message += "</tr>";
			
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Country :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + country + "</strong></td>";
			message += "</tr>";
			
		}
		else{
			
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Preferred Store :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + preferredStore + "</strong></td>";
			message += "</tr>";
			
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Preferred Date :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + preferredDate + "</strong></td>";
			message += "</tr>";
			
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Preferred Time :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + preferredTime + "</strong></td>";
			message += "</tr>";
			
			
		}
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Phone Number :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + phoneNumber + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Payment Type :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + paymentType + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Order Status :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + orderStatus + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Payment Status :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + paymentStatus + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='100px' style = 'text-align:right;'>Special Instruction :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + strLimitter1 + "</strong></td>";
		message += "</tr>";
		
		message += "</table>";
		message += "<br />";
		message += "</td>";
		message += "</tr>";
		message += "<tr>";
		message += "<td style='padding: 0 50px;'>";
		message += "<table width='100%' cellpadding='2' style='padding: 3px; background: #fff; border: 1px solid #dddddd;'>";
		message += "<tr>";
		message += "<th align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>ORDER ITEM</th>";
		message += "<th align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>STATUS</th>";
		message += "<th align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>QUANTITY</th>";
		message += "<th align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>UNIT PRICE</th>";
		message += "<th align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'>SUB-TOTAL</th>";
		message += "</tr>";
		
		Double totalAmount = 0.0;
		Double discountAmount = 0.0;
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		String shippingCost = "";
		String discountCost = "";
		String faceName = "";
		Double facePrice = 0d;
		String quantity = "";
		String image = "";
		
		if (cartOrderItems != null) {
			for (CartOrderItem cartOrderItem : cartOrderItems) {
				
				message += "<tr>";
				message += "<td align='right' style = 'text-align:center;'>"+cartOrderItem.getItemDetail().getName()+"</td>";
				message += "<td style = 'text-align:center;'>"+cartOrderItem.getStatus()+"</td>";
				message += "<td style = 'text-align:right;'>"+cartOrderItem.getQuantity()+"</td>";
				message += "<td style = 'text-align:right;'>"+cartOrderItem.getItemDetail().getPrice()+"</td>";
				message += "<td style = 'text-align:right;'>"+cartOrderItem.getQuantity()*cartOrderItem.getItemDetail().getPrice()+"</td>";
				message += "</tr>";
				
			}

			
			
			//message += "<tr>";
			//message += "<td align='right' colspan='4' style = 'text-align:right;'>Subtotal:</td>";
			//message += "<td align='right'>" + totalPrice + "</td>";
			//message += "</tr>";
			
			
			if(shippingType.equalsIgnoreCase("delivery")){
				
				message += "<tr>";
				message += "<td align='right' colspan='4' style = 'text-align:right;'>Total:</td>";
				message += "<td align='right' style = 'text-align:right;'>" + totalPrice + "</td>";
				message += "</tr>";
				
				message += "<tr>";
				message += "<td align='right' colspan='4' style = 'text-align:right;'>Delivery Charge :</td>";
				message += "<td align='right' style = 'text-align:right;'>" + deliveryCharge + "</td>";
				message += "</tr>";
				
				message += "<tr>";
				message += "<td align='right' colspan='4' style = 'text-align:right;'>Grand Total:</td>";
				message += "<td align='right' style = 'text-align:right;'><strong>" + grandTotalPrice + "</strong></td>";
				message += "</tr>";
				
			}
			else{
				
				message += "<tr>";
				message += "<td align='right' colspan='4' style = 'text-align:right;'>Grand Total:</td>";
				message += "<td align='right' style = 'text-align:right;'><strong>" + grandTotalPrice + "</strong></td>";
				message += "</tr>";
				
			}
			
		} 
		
		message += "<tr><td>&nbsp;</td></tr>";
		
		message += "<tr>";
		message += "<td colspan='5' style='padding: 0 0px;'>";
		message += "<center><h4>Thank you for using the Wendy's mobile app! We are now processing your order. Please be reminded that Delivery will be base on the time agreed by both customer and wendy's store.</h4></center>";
		message += "</td>";
		message += "</tr>";
		
		message += "<tr><td>&nbsp;</td></tr>";
		
		message += "<tr>";
		message += "<td colspan='5' style='padding: 0 0px;'>";
		message += "<center><strong><a href = 'http://www.facebook.com/wendysphilippines' target = '_blank'><img src='http://wendys.webtogo.com.ph/images/iFacebook.gif'/> facebook.com/wendysphilippines</a> <a href='http://intagram.com/WendysPH' target = '_blank'><img src='http://wendys.webtogo.com.ph/images/instagram.gif'/> instagram.com/WendysPH</a></strong></center>";
		message += "</td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td colspan='5' style='padding: 0 0px;'>";
		message += "<center><strong>For feedback, you may contact us at <a href = 'mailto:feedback@wendys.com.ph'>feedback@wendys.com.ph</a></strong></center>";
		message += "</td>";
		message += "</tr>";
		
		
		
		
		
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "</p>	";
		message += "</td>";
		message += "</tr>";
		message += "</table>";
		message += "</body>";
		message += "</html>";
		company = companyDelegate.find(Long.parseLong("404"));
		String tempEmail = company.getEmail();
		if(emailAddr.length() != 0){
			tempEmail = emailAddr + "," + company.getEmail();
		}
		String[] companyEmails = tempEmail.split(",");//company.getEmail().split(",");
		if (companyEmails != null && companyEmails.length != 0) {
			emailSent = EmailUtil.sendWithHTMLFormatWithCC("noreply@webtogo.com.ph", companyEmails, "Wendy's Order Confirmation", message, null);
			
			//for (String companyEmail : companyEmails) {
				//emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", companyEmail.trim(), "Wendy's Order Confirmation", message, null);
				if(emailSent){
					
					SavedEmail savedEmail = new SavedEmail();
					savedEmail.setCompany(company);
					savedEmail.setSender("noreply@webtogo.com.ph");
					savedEmail.setEmail(tempEmail);
					savedEmail.setFormName("Wendy's Order Confirmation");
					savedEmail.setEmailContent(message);

					savedEmailDelegate.insert(savedEmail);
					
				}
			//}
			
		}
		/*
		if (emailAddr.length() != 0)
		{	
			emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", emailAddr, "Wendy's Order Confirmation", message, null);
			
			if(emailSent){
				
				SavedEmail savedEmail = new SavedEmail();
				savedEmail.setCompany(company);
				savedEmail.setSender("noreply@webtogo.com.ph");
				savedEmail.setEmail(emailAddr);
				savedEmail.setFormName("Wendy's Order Confirmation");
				savedEmail.setEmailContent(message);

				savedEmailDelegate.insert(savedEmail);
				
			}
			
		}
		*/
		}
		catch(Exception e){
			System.out.println("===========================================================");
			System.out.println(e.toString());
			System.out.println("===========================================================");
		}
		return emailSent;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String sendPlascardPrepaid() {
		try{
			successUrl = (request.getParameter("successUrl") != null ? request
					.getParameter("successUrl") : "");
			errorUrl = ((request.getParameter("errorUrl") != null ? request
					.getParameter("errorUrl") : ""));
		}catch(Exception e){}
		boolean emailSent = false;
		try{
		//company = companyDelegate.find(400L);//plascard company ID
		String message = "";
		
		
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		//EmailUtil.connect("smtp.gmail.com", 587, "system@ivant.com","ivanttechnologies2009");
		
		//EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		message += "<html xmlns='http://www.w3.org/1999/xhtml'>";
		message += "<head>";
		message += "</head>";
		message += "<body style='font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 16px;'>";
		
		
		message += "<div style='width:100%;'><center><img src='http://plascard.webtogo.com.ph/images/"+company.getLogo()+"' border='0' align='center' /></center></div>";
		
		
		message += "<br />";
		
		message += "<table width='100%' cellpadding='2' style='padding: 3px; background: #fff; border: 1px solid #dddddd;'>";
		
		
		message += "<tr>";
		message += "<td colspan='3' align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'><h3>PERSONAL INFORMATION</h3></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Name :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("name") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Position :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("position") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Company :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("company") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Address :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("address") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Telephone Number :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("telephone") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Mobile Number :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("mobile") + "</strong></td>";
		message += "</tr>";
			
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Email Address :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("email") + "</strong></td>";
		message += "</tr>";
		
		/*message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Card Type :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + "Prepaid Card" + "</strong></td>";
		message += "</tr>"; */
		
		message += "<tr>";
		message += "<td colspan='3' align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'><h3>REQUEST DETAILS</h3></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Card Type :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + "Prepaid Card" + "</strong></td>";
		message += "</tr>";
		
		if(request.getParameter("prepaid_quantity")!=null){
			message += "<tr>";
			message += "<td align='right' width='250px' style = 'text-align:right;'>Quantity :</td>";
			message += "<td width='10px'>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("prepaid_quantity") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("prepaid_material")!=null){
			message += "<tr>";
			message += "<td align='right' width='250px' style = 'text-align:right;'>Material :</td>";
			message += "<td width='10px'>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("prepaid_material") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("prepaid_size") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Size :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("prepaid_size") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("prepaid_printing") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Printing :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("prepaid_printing") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("prepaid_inclusiveof1") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Inclusive Of :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("prepaid_inclusiveof1") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("prepaid_inclusiveof2") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>If Inclusive of Scratch Off, how many? :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + (request.getParameter("prepaid_inclusiveof_scratchoff_number") == null ? "0" : request.getParameter("prepaid_inclusiveof_scratchoff_number")) + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("prepaid_inclusiveof3") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Inclusive Of :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("prepaid_inclusiveof3") + "</strong></td>";
			message += "</tr>";
		}
		
		
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "</p>	";
		message += "</td>";
		message += "</tr>";
		message += "</table>";
		message += "</body>";
		message += "</html>";
		String[] companyEmails = company.getEmail().split(",");
			if (companyEmails != null && companyEmails.length != 0) {
				//for (String companyEmail : companyEmails) {
				//	if(companyEmail.length()>0){
						//emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", companyEmail.trim(), "Plascard Quotation Request", message, null);
				//	}
				//}
				emailSent = EmailUtil.sendWithHTMLFormatWithCC("noreply@webtogo.com.ph", companyEmails, "Plascard Quotation Request", message, null);
				
			}
		
			/*if (company.getEmail().length() != 0){
				emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", company.getEmail(), "Plascard Quotation Request", message, null);
			}*/
		
		}
		catch(Exception e){
			System.out.println("===========================================================");
			System.out.println(e.toString());
			System.out.println("===========================================================");
		}
		
		if (emailSent){
			return SUCCESS;
		}
		else{
			return ERROR;
		}
	}
	
	
	
	
	
	public String sendPlascardIdentificationCard() {
		try{
			successUrl = (request.getParameter("successUrl") != null ? request
					.getParameter("successUrl") : "");
			errorUrl = ((request.getParameter("errorUrl") != null ? request
					.getParameter("errorUrl") : ""));
		}catch(Exception e){}
		boolean emailSent = false;
		try{
		//company = companyDelegate.find(400L);//plascard company ID
		String message = "";
		
		
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		//EmailUtil.connect("smtp.gmail.com", 587, "system@ivant.com","ivanttechnologies2009");
		
		//EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		message += "<html xmlns='http://www.w3.org/1999/xhtml'>";
		message += "<head>";
		message += "</head>";
		message += "<body style='font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 16px;'>";
		
		
		message += "<div style='width:100%;'><center><img src='http://plascard.webtogo.com.ph/images/"+company.getLogo()+"' border='0' align='center' /></center></div>";
		
		
		message += "<br />";
		
		message += "<table width='100%' cellpadding='2' style='padding: 3px; background: #fff; border: 1px solid #dddddd;'>";
		
		
		message += "<tr>";
		message += "<td colspan='3' align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'><h3>PERSONAL INFORMATION</h3></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Name :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("name") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Position :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("position") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Company :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("company") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Address :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("address") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Telephone Number :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("telephone") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Mobile Number :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("mobile") + "</strong></td>";
		message += "</tr>";
			
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Email Address :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("email") + "</strong></td>";
		message += "</tr>";
		/*
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Card Type :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + "Prepaid Card" + "</strong></td>";
		message += "</tr>";*/
		
		message += "<tr>";
		message += "<td colspan='3' align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'><h3>REQUEST DETAILS</h3></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Card Type :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + "Identification Card" + "</strong></td>";
		message += "</tr>";
		
		if(request.getParameter("identification_quantity")!=null){
			message += "<tr>";
			message += "<td align='right' width='250px' style = 'text-align:right;'>Quantity :</td>";
			message += "<td width='10px'>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_quantity") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_material")!=null){
			message += "<tr>";
			message += "<td align='right' width='250px' style = 'text-align:right;'>Material :</td>";
			message += "<td width='10px'>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_material") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_size") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Size :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_size") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_printing") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Printing :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_printing") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_idlace") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Has ID Lace? :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_idlace") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_idlacetype") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>ID Lace Type :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_idlacetype") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_lacewidth") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>ID Lace Width :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_lacewidth") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_lacecolor") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>ID Lace Color :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_lacecolor") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_printingcolor") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Printing Color :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_printingcolor") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_logopin") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Logo Pin :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_logopin") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_rubberemblem") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Rubber Emblem :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_rubberemblem") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_idclip") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>ID Clip :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_idclip") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("identification_holderquantity") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>ID Holder Quantity :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("identification_holderquantity") + "</strong></td>";
			message += "</tr>";
		}
		
		
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "</p>	";
		message += "</td>";
		message += "</tr>";
		message += "</table>";
		message += "</body>";
		message += "</html>";
		String[] companyEmails = company.getEmail().split(",");
			if (companyEmails != null && companyEmails.length != 0) {
				//for (String companyEmail : companyEmails) {
				//	if(companyEmail.length()>0){
				//		emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", companyEmail.trim(), "Plascard Quotation Request", message, null);
				//	}
				//}
				
				//sendWithHTMLFormatWithCC
				emailSent = EmailUtil.sendWithHTMLFormatWithCC("noreply@webtogo.com.ph", companyEmails, "Plascard Quotation Request", message, null);
				
			}
		
			/*if (company.getEmail().length() != 0){
				emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", company.getEmail(), "Plascard Quotation Request", message, null);
			}*/
		
		}
		catch(Exception e){
			System.out.println("===========================================================");
			System.out.println(e.toString());
			System.out.println("===========================================================");
		}
		
		if (emailSent){
			return SUCCESS;
		}
		else{
			return ERROR;
		}
	}
	
	
	
	public String sendPlascardPlasticCard() {
		try{
			successUrl = (request.getParameter("successUrl") != null ? request
					.getParameter("successUrl") : "");
			errorUrl = ((request.getParameter("errorUrl") != null ? request
					.getParameter("errorUrl") : ""));
		}catch(Exception e){}
		boolean emailSent = false;
		try{
		//company = companyDelegate.find(400L);//plascard company ID
		String message = "";
		
		
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		//EmailUtil.connect("smtp.gmail.com", 587, "system@ivant.com","ivanttechnologies2009");
		
		//EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		message += "<html xmlns='http://www.w3.org/1999/xhtml'>";
		message += "<head>";
		message += "</head>";
		message += "<body style='font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 16px;'>";
		
		
		message += "<div style='width:100%;'><center><img src='http://plascard.webtogo.com.ph/images/"+company.getLogo()+"' border='0' align='center' /></center></div>";
		
		
		message += "<br />";
		
		message += "<table width='100%' cellpadding='2' style='padding: 3px; background: #fff; border: 1px solid #dddddd;'>";
		
		
		message += "<tr>";
		message += "<td colspan='3' align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'><h3>PERSONAL INFORMATION</h3></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Name :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("name") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Position :</td>";
		message += "<td width='10px'>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("position") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Company :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("company") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' width='250px' style = 'text-align:right;'>Address :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("address") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Telephone Number :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("telephone") + "</strong></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Mobile Number :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("mobile") + "</strong></td>";
		message += "</tr>";
			
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Email Address :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + request.getParameter("email") + "</strong></td>";
		message += "</tr>";
		
		/*message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Card Type :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + "Prepaid Card" + "</strong></td>";
		message += "</tr>";*/
		
		message += "<tr>";
		message += "<td colspan='3' align='center' style='background: #B60927; height: 25px; border-bottom: 1px solid #dddddd;color: #fff;'><h3>REQUEST DETAILS</h3></td>";
		message += "</tr>";
		
		message += "<tr>";
		message += "<td align='right' style = 'text-align:right;'>Card Type :</td>";
		message += "<td>:</td>";
		message += "<td style = 'text-align:left;'><strong>" + "Plastic Printed Card" + "</strong></td>";
		message += "</tr>";
		
		if(request.getParameter("plastic_type")!=null){
			message += "<tr>";
			message += "<td align='right' width='250px' style = 'text-align:right;'>Plastic Card Type :</td>";
			message += "<td width='10px'>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_type") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_quantity")!=null){
			message += "<tr>";
			message += "<td align='right' width='250px' style = 'text-align:right;'>Quantity :</td>";
			message += "<td width='10px'>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_quantity") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_material")!=null){
			message += "<tr>";
			message += "<td align='right' width='250px' style = 'text-align:right;'>Material :</td>";
			message += "<td width='10px'>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_material") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_size") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Size :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_size") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_printing") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Printing :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_printing") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_lamination") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Lamination :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_lamination") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_signaturepanel") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Signature Panel :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_signaturepanel") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_linenumber") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>How many lines? :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_linenumber") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_signaturepaneloverprint") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Signature Panel Overprint? :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_signaturepaneloverprint") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_magneticstripe") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Magnetic Stripe :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_magneticstripe") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_magneticstripetype") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Magnetic Stripe Type :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_magneticstripetype") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_magneticencoding") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Magnetic Encoding :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_magneticencoding") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_prenumbering") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Pre Numbering :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_prenumbering") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_thermalcolor") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Thermal Color :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_thermalcolor") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_embosing") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Embossing :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_embosing") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_embosing_linenumber") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Embosing total line number :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_embosing_linenumber") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_tipping") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Tipping :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_tipping") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_tippingcolor") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Tipping Color :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_tippingcolor") + "</strong></td>";
			message += "</tr>";
		}
		
		if(request.getParameter("plastic_personalization") != null){
			message += "<tr>";
			message += "<td align='right' style = 'text-align:right;'>Personalization :</td>";
			message += "<td>:</td>";
			message += "<td style = 'text-align:left;'><strong>" + request.getParameter("plastic_personalization") + "</strong></td>";
			message += "</tr>";
		}
		
		
		
		message += "</table>";
		message += "</td>";
		message += "</tr>";
		message += "</p>	";
		message += "</td>";
		message += "</tr>";
		message += "</table>";
		message += "</body>";
		message += "</html>";
		String[] companyEmails = company.getEmail().split(",");
			if (companyEmails != null && companyEmails.length != 0) {
				//for (String companyEmail : companyEmails) {
				//	if(companyEmail.length()>0){
				//		emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", companyEmail.trim(), "Plascard Quotation Request", message, null);
				//	}
				//}
				emailSent = EmailUtil.sendWithHTMLFormatWithCC("noreply@webtogo.com.ph", companyEmails, "Plascard Quotation Request", message, null);
				
			}
		
			/*if (company.getEmail().length() != 0){
				emailSent = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", company.getEmail(), "Plascard Quotation Request", message, null);
			}*/
		
		}
		catch(Exception e){
			System.out.println("===========================================================");
			System.out.println(e.toString());
			System.out.println("===========================================================");
		}
		
		if (emailSent){
			return SUCCESS;
		}
		else{
			return ERROR;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@SuppressWarnings("unchecked")
	public boolean sendEmail() { 
		boolean emailSent = false;
		StringBuffer content = new StringBuffer();

		try {
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");

			String subject = request.getParameter("subject");
			
			String title = request.getParameter("title");
			String to = request.getParameter("to");
			String from = request.getParameter("from");
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>TO : "  + to + " <<<<<<<<<<<<<<<<<<<<");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FROM : "  + from + " <<<<<<<<<<<<<<<<<<<<");
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.ELITETRANSLATIONS)&&title.equalsIgnoreCase("Request a Free Quote")){
				Date fromDate = new Date();
				fromDate =  new Date(fromDate.getYear(), fromDate.getMonth(), fromDate.getDate(), 0, 0);
				Date toDate = new Date();
				String formName = request.getParameter("se_formName");
				ObjectList<SavedEmail> saveEmails = savedEmailDelegate.findEmailByDateAndFormName(company, fromDate, toDate,formName);
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");				
				Integer inquiryCount = 1;
				if(saveEmails!=null){
					inquiryCount+=saveEmails.getTotal();
				}
				int inquiryCountLength = inquiryCount.toString().length();
				String quotationInquiryNumber = "Q"+df.format(fromDate)+String.format("%0"+ (inquiryCountLength>2?inquiryCountLength:2) +"d",inquiryCount);
				subject+= String.format("%03d",inquiryCount)+" - "+quotationInquiryNumber;
			}
			
			if (company.getId() == CompanyConstants.PUREGOLD || company.getId() == CompanyConstants.PUREGOLD_TEST) {
				String topic = request.getParameter("topic");
				String[] temp = to.split(",");
				if (topic.equals("Marketing Concerns")) {
					to = temp[0];
				} else if (topic.equals("Leasing Concerns"))  {
					to = temp[2];
				} else {
					to = temp[1];
				}
			}
			
			List<String> ignored = new ArrayList<String>();
			ignored.add("subject");
			ignored.add("to");
			ignored.add("from");
			ignored.add("title");
			ignored.add("submit");
			ignored.add("successurl");
			ignored.add("errorurl");
			ignored.add("se_formname");
			ignored.add("se_sender");
			ignored.add("se_email");
			ignored.add("se_phone");
			ignored.add("se_items");
			ignored.add("maxfilesize");
			ignored.add("companyId");

			Iterator<Map.Entry<String, String[]>> iterator = request
					.getParameterMap().entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry<String, String[]> entry = iterator.next();
				if (entry.getKey().contains("|")) {
					tm.put(entry.getKey(), entry.getValue());
				}
				if (request.getParameter("se_hasDelimiter") == null
						|| !(request.getParameter("se_hasDelimiter")
								.equalsIgnoreCase("true"))) {
					if (entry != null) {
						if (!ignored.contains(entry.getKey().toLowerCase())) {
							content.append(decodeKey(entry.getKey())).append(
									" : ").append(
									request.getParameter(entry.getKey()));
							content.append("<br/><br/>");

						}
					} else {
						break;
					}
				}
			}

			content = referralContent(content, "<br/>");

			if ((request.getParameter("se_hasDelimiter") != null && request
					.getParameter("se_hasDelimiter").equalsIgnoreCase("true"))) {

				Set set = tm.entrySet();
				Iterator i = set.iterator();
				while (i.hasNext()) {
					Map.Entry me = (Map.Entry) i.next();
					String requestStr = me.getKey().toString();
					String fieldName = decodeKey(me.getKey().toString()
							.substring(me.getKey().toString().indexOf("|") + 1));

					if (fieldName.equalsIgnoreCase("name")
							|| me.getKey().toString().equalsIgnoreCase("name")) {
						if (!request.getParameter(requestStr).equals("")) {
							subject = subject + " - "
									+ request.getParameter(requestStr) + " (" + company.getNameEditable() + ")";
						}
					}

					if (fieldName.equalsIgnoreCase("BillingNumber")) {
						fieldName = "Booking Number";
					}
					
					if(fieldName.equalsIgnoreCase("Personal Information")) {
						fieldName = "<strong>I. "+fieldName.toUpperCase()+"</strong>";
					}
					if(fieldName.equalsIgnoreCase("Family Background")) {
						fieldName = "<strong>II. "+fieldName.toUpperCase()+"</strong>";
					}
					if(fieldName.equalsIgnoreCase("Educational Background")) {
						fieldName = "<strong>III. "+fieldName.toUpperCase()+"</strong>";
					}
					
					if(fieldName.equalsIgnoreCase("Elementary") || fieldName.equalsIgnoreCase("Secondary") || fieldName.equalsIgnoreCase("Vocational / Trade Course") ||
							fieldName.equalsIgnoreCase("Tertiary") || fieldName.equalsIgnoreCase("Graduate Studies - Diploma") || fieldName.equalsIgnoreCase("Graduate Studies - Masters") || 
								fieldName.equalsIgnoreCase("Graduate Studies - Doctorate") || fieldName.equalsIgnoreCase("*Non-degree Course")) {
						fieldName = "<strong>"+fieldName+"</strong>";
					}
					content.append(fieldName);

					if (requestStr.equalsIgnoreCase("1g|Location")) {
						// content.append(" : "
						// +categoryDelegate.find(Long.parseLong(request.getParameter(requestStr))).getName());
						content.append(" : ").append(
								categoryDelegate.find(
										Long.parseLong(request
												.getParameter(requestStr)))
										.getName());
					} else if (requestStr.equalsIgnoreCase("1h|Product Name")) {
						// content.append(" : "
						// +categoryDelegate.find(Long.parseLong(request.getParameter(requestStr))).getName());
						content.append(" : ").append(
								categoryDelegate.find(
										Long.parseLong(request
												.getParameter(requestStr)))
										.getName());
					} else if (requestStr
							.equalsIgnoreCase("1i|Memorial Product")) {
						// content.append(" : "
						// +categoryItemDelegate.find(company,Long.parseLong(request.getParameter(requestStr))).getName());
						content.append(" : ").append(
								categoryItemDelegate.find(
										company,
										Long.parseLong(request
												.getParameter(requestStr)))
										.getName());
					} else if(requestStr.equalsIgnoreCase("1k|services_needed")) {
						String[] services = request.getParameterValues("1k|services_needed");
						StringBuffer strServices = new StringBuffer();
						strServices.append(" : ");
						int count = 0;
						for(int j=0;j<services.length;j++)
							if(services[j] != null) {
								count++;
								strServices.append("("+(count)+") "+services[j]+" ");
							}
									
						content.append(strServices.toString());
					} else {
						// content.append(" : " +
						// request.getParameter(requestStr));

						content.append(" : ").append(
								request.getParameter(requestStr));
					}
					
					if(requestStr.equalsIgnoreCase("7b|within_the_third_degree_local") && 
							request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
						content.append("<br/>Particulars : ").append(request.getParameter("question_b"));
					}
					
					if(requestStr.equalsIgnoreCase("7c|have_ever_been_declared_guilty") && 
							request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
						content.append("<br/>Offense Details : ").append(request.getParameter("question_c"));
					}
					
					if(requestStr.equalsIgnoreCase("7d|have_ever_been_convicted") && 
							request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
						content.append("<br/>Details : ").append(request.getParameter("question_d"));
					}
					
					if(requestStr.equalsIgnoreCase("7e|have_ever_been_forced_to_resign") && 
							request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
						content.append("<br/>Reasons : ").append(request.getParameter("question_e"));
					}
					
					if(requestStr.equalsIgnoreCase("7f|have_ever_been_candidate_in_a_election") && 
							request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
						content.append("<br/>Date of election and other particulars : ").append(request.getParameter("question_f"));
					}
					
					if(requestStr.equalsIgnoreCase("7g|is_member_of_any_indegenouse group") && 
							request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
						content.append("<br/>Details : ").append(request.getParameter("question_g1"));
					}
					if(requestStr.equalsIgnoreCase("7h|is_differently_abled") && 
							request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
						content.append("<br/>Details : ").append(request.getParameter("question_g2"));
					}
					if(requestStr.equalsIgnoreCase("7i|is_solo_parent") && 
							request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
						content.append("<br/>Details : ").append(request.getParameter("question_g3"));
					}
					content.append("<br/><br/>");
				}
				if(company.getName().equalsIgnoreCase("truecare") && request.getParameter("se_formName").equals("Newsletter")){
					content.append(request.getParameter("emailNewsletter") + " has subscribed to Truecare newsletter.");
				}
				
			}
			if(company.getName().equalsIgnoreCase("truecare") && request.getParameter("se_formName").equals("Testimonial")){
				content.replace(0,content.length(),("New testimonial received. Please check at the truecare CMS."));
			}
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);

			if(emailContent != null && !emailContent.equals("")) {
				content = new StringBuffer();
				content.append(emailContent);
			}
			
			if (this.email.length() != 0) {
				content.append("<br/><br/><br/>");
				content.append(this.message);
				content.append("<br/>");
			}

			if (company.getId() == 350) {
				subject = request.getParameter("subject") + " - " + request.getParameter("1|inquiry") +
							" - " + request.getParameter("1a|name");
			}
			
			if (company.getName()
					.equalsIgnoreCase(CompanyConstants.HIPRECISION))
				if (request.getParameter("hpBranchName") != null)
					subject = request.getParameter("hpBranchName")
							.concat(" : ").concat(subject);

			if (filename != null) {
				File dest = renameTempFile(file, filename);
				//content.append("destinations: ");
				//content.append(dest);
				emailSent = EmailUtil.sendWithHTMLFormat(from, to, subject,
						content.toString(), dest.getAbsolutePath());
			} else {
				
				if(CompanyConstants.OFFICEMAN_COMPANY_NAME.equals(company.getName())){
					// new officeman contact email implementation
					emailSent = EmailUtil.sendWithHTMLFormatWithBCC(from, this.email, subject, content.toString(), null, to.split(","));
				
				}else if(CompanyConstants.GURKKATEST_COMPANY_NAME.equals(company.getName())){
					List<String> rcpts = new ArrayList<String>(Arrays.asList(to.split(",")));
					if(rcpts != null){
						if(rcpts.size() > 0){
							emailSent = EmailUtil.sendWithHTMLFormatWithBCC(from, rcpts.remove(0), subject, content.toString(), null, rcpts.toArray(new String[0]));
						}
					}				
				}
				
				// default template
				else{
					
					String ajaxSubmit = request.getParameter("ajax_submit");
					String email_content = request.getParameter("email_content");
					if(email_content != null && ajaxSubmit != null) {
						content = new StringBuffer();
						content.append(email_content);
					}
						
					setEmailSettings();
					/*EmailUtil.connect(smtp, Integer.parseInt(portNumber),
							mailerUserName, mailerPassword);
					emailSent = EmailUtil.sendWithHTMLFormat(from, to.split(","),
							subject, content.toString(), null);
					
					if (this.email.length() != 0) {
						// emailSent = EmailUtil.send(from, this.email , subject,
						// content.toString(), null);
						emailSent = EmailUtil.sendWithHTMLFormat(from, this.email,
								subject, content.toString(), null);
					}*/
					int sendingTrial = 0;
					while(!emailSent){ //second email trial
						sendingTrial += 1;
						if(sendingTrial == 5){
							return emailSent;
						}
						Random rand = new Random();
						int  n = rand.nextInt(4);
						Logger logger = Logger.getLogger(EmailUtil.class);
						logger.info("- - - - - - - - - - > > Email Sender: " + EmailUtil.OTHER_USERNAMES[n]);
						if(!(mailerUserName == "") && !(mailerPassword == "")){
							EmailUtil.connect(smtp, Integer.parseInt(portNumber),
									mailerUserName, mailerPassword);
						}else{
							/*if(company.getName().equalsIgnoreCase("mundipharma2")){
								EmailUtil.connect(smtp, Integer.parseInt(portNumber),
										EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);
							}else{
								EmailUtil.connect(smtp, Integer.parseInt(portNumber),
										EmailUtil.OTHER_USERNAMES[n], EmailUtil.OTHER_PASSWORDS[n]);
							}*/
							EmailUtil.connect(smtp, Integer.parseInt(portNumber),
								EmailUtil.OTHER_USERNAMES[n], EmailUtil.OTHER_PASSWORDS[n]);
						}
						boolean excluded = false;
						List<WebtogoExcludedEmails> em = webtogoExcludedEmailsDelegate.findAll();
						for(WebtogoExcludedEmails ss : em){
							if(to.contains(ss.getEmail())){
								excluded = true;
							}
							if(content.toString().contains(ss.getEmail())){
								excluded = true;
							}
							if(subject.contains(ss.getEmail())){
								excluded = true;
							}
							if(this.email.contains(ss.getEmail())){
								excluded = true;
							}
							if(content.toString().isEmpty()){
								excluded = true;
							}
						}
						if(!excluded){
							emailSent = EmailUtil.sendWithHTMLFormat(from, to.split(","),
									subject, content.toString(), null);
							
							if (this.email.length() != 0) {
								// emailSent = EmailUtil.send(from, this.email , subject,
								// content.toString(), null);
								emailSent = EmailUtil.sendWithHTMLFormat(from, this.email,
										subject, content.toString(), null);
							}
						}else{
							return emailSent=false;
						}
						
					}
				}
			}
			
			
			//Rosehall
			String rosehall_message = request.getParameter("rosehall_message");
			if (company.getId() == CompanyConstants.ROSEHALL) {
				EmailUtil.sendWithHTMLFormat(from, request.getParameter("1d|email"), subject, rosehall_message.toString(), null);
			}
			
			// Zunic Brochure Download
			if (company.getId() == 68) {
				String email = request.getParameter("1b|email");
				String sender = request.getParameter("1a|firstname");
				StringBuffer senderBuffer = new StringBuffer();

				char firstLetter = Character.toUpperCase(sender.charAt(0));
				senderBuffer.append(firstLetter);
				senderBuffer.append(sender.substring(1, sender.length()));
				sender = senderBuffer.toString();

				String destinationPath = "companies";
				FileUtil.createDirectory(servletContext
						.getRealPath(destinationPath));
				destinationPath += File.separator + company.getName();
				FileUtil.createDirectory(servletContext
						.getRealPath(destinationPath));
				destinationPath += File.separator + "pdf";
				FileUtil.createDirectory(servletContext
						.getRealPath(destinationPath));

				filename = "ZunicBrochure.pdf";
				File file = new File(servletContext.getRealPath(destinationPath
						+ File.separator + filename));

				content = new StringBuffer();
				content.append("Dear " + sender + ",\n\n");
				content.append("Thank you for requesting our brochure.\n");
				content
						.append("Below is the attached Brochure. Please see for more details about our Services.\n\n");
				content.append("Thank you.\n\nSincerely,\n\n");
				content.append("Zunic Slimming & Body Sculpting.\n");

				emailSent = EmailUtil.sendWithHTMLFormat(from, email, subject,
						content.toString(), file.getAbsolutePath());
			}
		} catch (Exception e) {
			logger.debug("failed to send email in " + company.getName()
					+ "... " + e);
		}
		if((!company.getName().equalsIgnoreCase("truecare") && !request.getParameter("se_formName").equals("Testimonial")) 
			&& (!company.getName().equalsIgnoreCase("truecare") && !request.getParameter("se_formName").equals("Inquiry"))){
			saveEmailInformation(content.toString());
		}

		requestedToClaimReferralRewards();

		if (company.getName().equalsIgnoreCase(CompanyConstants.CANCUN)
				&& referralsId != null) {

			List<Referral> refList = referralDelegate.findAllByIds(referralsId,
					company);

			String messageToReferrer = content.toString();

			String intro = "Hi <strong>"
					+ refList.get(0).getReferredBy().getFullName()
					+ "</strong><br><br>";

			// intro+="You wish to redeem "+claims+" from this Referral"+((refList.size()>1)?"s":"")+"<br><br>";

			String lastMessage = "<br>Thank you. <br><br>All the Best,<br<br>"
					+ company.getNameEditable() + " Team.";

			messageToReferrer = intro + messageToReferrer + lastMessage;

			subject = "Rewards Claim Request";

			if (refList != null)
				emailSent = EmailUtil.sendWithHTMLFormat(
						"noreply@webtogo.com.ph", refList.get(0)
								.getReferredBy().getEmail(), subject,
						messageToReferrer, null);
		}
		
		if(company.getId() != CompanyConstants.PILIPINAS_BRONZE && company.getId() != CompanyConstants.OFFICEMAN 
				&& !(company.getName().equalsIgnoreCase("truecare") && !request.getParameter("se_formName").equals("Testimonial"))) {
			if (!send_confirmation_message()) {
				setError("An error has occured.");
			}
		}
		
		return emailSent;
	}
	
	

	private StringBuffer referralContent(StringBuffer content, String newLine) {

		if (company.getName().equalsIgnoreCase(CompanyConstants.CANCUN)
				&& referralsId != null) {

			List<Referral> refList = referralDelegate.findAllByIds(referralsId,
					company);

			String claims = "";

			for (String cl : rewardsToClaim) {

				claims += cl + ", ";
			}

			content.append(newLine + "<strong>Rewards Claim Request:</strong>");

			content.append(newLine + claims);

			content.append(newLine + newLine);

			content.append(newLine + "<strong>Referral Details</strong>");
			for (Referral ref : refList)
				if (ref != null) {

					content
							.append(newLine + "Referral Id:      "
									+ ref.getId());
					// content.append(newLine+newLine+"Requested Reward: "+getReferralReward());
					content.append(newLine + "Client Full Name: "
							+ ref.getFullname());
					content.append(newLine + "Contact Number:   "
							+ ref.getContactNumber());
					content.append(newLine + "Email:            "
							+ ref.getEmail());
					content.append(newLine + "Date Approved:    "
							+ ref.getDateApproved() + newLine + newLine);
					content.append(newLine);

				}

			content.append("<strong>Referrer Details:</strong>");
			content.append(newLine + newLine);

		}
		return content;
	}

	private String requestedToClaimReferralRewards() {

		if (getUpdateReferralToRequested()
				&& company.getCompanySettings().getHasReferrals()) {
			// update referral statuses to REQUETED
			String rewards = Arrays.toString(getRewardsToClaim());
			// rewardsToClaim
			Long requestId = getIcrementedRequestId();

			for (Long refId : referralsId) {
				Referral ref = referralDelegate.find(refId);
				if (ref != null) {

					ref.setStatus(ReferralStatus.REQUESTED);

					ref.setRequestId(requestId);

					ref.setReward(rewards);

					referralDelegate.update(ref);

				}

			}
			// Member m = new Member();
			// m.setStatus("");
			// for Cancun Only
			Boolean redeemedFlorida = Boolean.FALSE;
			for (String cl : rewardsToClaim)
				if (cl.equalsIgnoreCase("Florida 3D2N Package")) {
					redeemedFlorida = Boolean.TRUE;
					break;
				}

			if (redeemedFlorida && referralsId != null
					&& referralsId.length != 0) {
				Member m = referralDelegate.find(referralsId[0])
						.getReferredBy();
				m.setStatus("Florida 3D2N Package");
				memberDelegate.update(m);
			}

		}

		return Action.SUCCESS;

	}

	private Long getIcrementedRequestId() {
		Long lastRequestedId = referralDelegate.findLastRequestedId(company);
		return lastRequestedId + 1;
	}

	public String sendEmailToLUAPMember() {
		String salutation = request.getParameter("1a|salutation");
		String applicationType = request.getParameter("1b|application_type");
		String surName = request.getParameter("1c|surname");
		String firstName = request.getParameter("1d|first_name");
		String middleName = request.getParameter("1e|middle_name");
		String gender = request.getParameter("1f|gender");
		String civilStatus = request.getParameter("1g|civil_status");
		String nationality = request.getParameter("1h|nationality");
		String dateOfBirth = request.getParameter("1i|date_of_birth");
		String numberOffice = request.getParameter("1j|tel_no_office");
		String addressHome = request.getParameter("1k|address");
		String numberHome = request.getParameter("1l|tel_no_home");
		String emailOffice = request.getParameter("1m|email_office");
		String emailPersonal = request.getParameter("1o|email_personal");
		
		String contact_number_mobile = request.getParameter("1n|mobile_number");
		String contact_number_mobile2 = request.getParameter("1p|mobile_number2");
		String education_attainment = request.getParameter("1q|highest_educational_attainment");
		
		String luap_affiliation	 = request.getParameter("1r|chapter_affiliation");
		String office_tel = request.getParameter("1s|affiliation_office_tel_no");
		String affiliate_company = request.getParameter("1t|affiliation_company");
		
		String fax_affiliate = request.getParameter("1u|affiliation_fax");
		String branch_agency = request.getParameter("1v|affiliation_branch_agency");
		String email_office	= request.getParameter("1w|affiliation_email_office");
		String address_affiliate = request.getParameter("1x|affiliation_address");
		String address_personal	= request.getParameter("1y|affiliation_email_personal");

		if (emailPersonal != null) {
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);

			String emailMessage = request.getParameter("emailMessage");
			StringBuffer content = new StringBuffer();
			StringBuffer content2 = new StringBuffer();
			
			content.append("Hi " + firstName + " " + surName + ", "
					+ "thank you for submitting your information.");
			if (emailMessage != null)
				content.append(emailMessage);
			content.append("The LUAP Team");
			content
					.append("This is a system generated email. Do not reply. For inquiries, please refer to this link: <a href='http://luap.com.ph/contact.do'>Contact LUAP</a>.\r\n\r\n<br/><br/>");
			content.append("The " + company.getNameEditable() + " Team");
			String[] to = new String[2];
			to[0] = emailPersonal;
			to[1] = company.getEmail();
			
			content2.append(salutation + "." + firstName + " " + middleName + " " + surName + "<br />"
			+ "Application Type: " + applicationType + "<br />"
			+ "Gender : " + gender + "<br />"
			+ "Civil Status : " + civilStatus + "<br />"
			+ "Nationality : " + nationality + "<br />"
			+ "Date of Birth : " + dateOfBirth + "<br />"
			+ "Office Number: " + numberOffice + "<br />"
			+ "Personal Address : " + addressHome + "<br />"
			+ "Home Number: " + numberHome + "<br />"
			+ "Personal Email : " + emailPersonal + "<br />"
			+ "Personal Number : " + contact_number_mobile2 + "<br />"
			+ "Affiliated Company : " + affiliate_company + "<br />"
			+ "Affiliated Luap Chapter : " + luap_affiliation + "<br />");
			
			String[] to2 = new String[1];
			to2[0] = company.getEmail();
			StringBuffer content3 = new StringBuffer();
			
			List<String> ignored = new ArrayList<String>();
			ignored.add("subject");
			ignored.add("to");
			ignored.add("from");
			ignored.add("title");
			ignored.add("submit");
			ignored.add("successurl");
			ignored.add("errorurl");
			ignored.add("se_formname");
			ignored.add("se_sender");
			ignored.add("se_email");
			ignored.add("se_phone");
			ignored.add("se_items");
			ignored.add("maxfilesize");

			Iterator<Map.Entry<String, String[]>> iterator = request
					.getParameterMap().entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry<String, String[]> entry = iterator.next();
				if (entry.getKey().contains("|")) {
					tm.put(entry.getKey(), entry.getValue());
				}
				if (request.getParameter("se_hasDelimiter") == null
						|| !(request.getParameter("se_hasDelimiter")
								.equalsIgnoreCase("true"))) {
					if (entry != null) {
						if (!ignored.contains(entry.getKey().toLowerCase())) {
							content.append(decodeKey(entry.getKey())).append(
									" : ").append(
									request.getParameter(entry.getKey()));
							content.append("<br/><br/>");

						}
					} else {
						break;
					}
				}
			}

			content = referralContent(content, "<br/>");			
			
			if ((request.getParameter("se_hasDelimiter") != null && request
					.getParameter("se_hasDelimiter").equalsIgnoreCase("true"))) {

				Set set = tm.entrySet();
				Iterator i = set.iterator();
				
				while (i.hasNext()) {
					Map.Entry me = (Map.Entry) i.next();
					String requestStr = me.getKey().toString();
					String fieldName = decodeKey(me.getKey().toString()
							.substring(me.getKey().toString().indexOf("|") + 1));

					if (fieldName.equalsIgnoreCase("name")
							|| me.getKey().toString().equalsIgnoreCase("name")) {
						if (!request.getParameter(requestStr).equals("")) {
							subject = subject + " - "
									+ request.getParameter(requestStr) + " (" + company.getNameEditable() + ")";
						}
					}

					if (fieldName.equalsIgnoreCase("BillingNumber")) {
						fieldName = "Booking Number";
					}
					content3.append(fieldName);

					if (requestStr.equalsIgnoreCase("1g|Location")) {
						content3.append(" : ").append(
								categoryDelegate.find(
										Long.parseLong(request
												.getParameter(requestStr)))
										.getName());
					} else if (requestStr.equalsIgnoreCase("1h|Product Name")) {
						content3.append(" : ").append(
								categoryDelegate.find(
										Long.parseLong(request
												.getParameter(requestStr)))
										.getName());
					} else if (requestStr
							.equalsIgnoreCase("1i|Memorial Product")) {
						content3.append(" : ").append(
								categoryItemDelegate.find(
										company,
										Long.parseLong(request
												.getParameter(requestStr)))
										.getName());
					} else {
						content3.append(" : ").append(
								request.getParameter(requestStr));
					}
					content3.append("<br/><br/>");
				}
			}		
			
			saveEmailInformation(content3.toString());
			
			if (EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to,
					"Welcome to " + company.getNameEditable() + "!", content
							.toString(), null)) {
			}
			
			if (EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph",  company.getEmail().split(","),
					"Welcome to " + company.getNameEditable() + "!", content2
							.toString(), null)) {
				return Action.SUCCESS;
			}
		}
		
		
		return Action.ERROR;
	}
	
	public boolean sendNotification(Company company, String senderEmail, String subject, StringBuffer content, String fileName) {
		this.company = company;
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName,
				mailerPassword);
		String from = (company.getCompanySettings().getEmailUserName() != null&& !company.getCompanySettings().getEmailUserName().equals("")
				? company.getCompanySettings().getEmailUserName() : "noreply@webtogo.com.ph");
		boolean emailSent = EmailUtil.sendWithHTMLFormat(
				from, senderEmail,  subject,content.toString(), fileName);
		
		return emailSent;
	}
	
	public boolean sendQuotationEmail(Company company, String senderEmail, String subject, StringBuffer content, String fileName) {
		// TODO Auto-generated method stub
		this.company = company;
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName,
				mailerPassword);

		String from = (mailerUserName.equalsIgnoreCase("system@ivant.com") ? "noreply@webtogo.com.ph" : mailerUserName);
		boolean emailSent = EmailUtil.sendWithHTMLFormat(from, senderEmail,  subject,content.toString(), fileName);
		
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName,
				mailerPassword);
		emailSent = EmailUtil.sendWithHTMLFormat(from, company.getEmail().split(","),  subject,content.toString(), fileName);
		
		
		return emailSent;
	}
	
	
	public String sendInquiryToIvant() {
	
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
		
		String inquiry_firstname = request.getParameter("inquiry_fname");
		String inquiry_lastname = request.getParameter("inquiry_lname");
		String inquiry_email = request.getParameter("inquiry_email");
		String inquiry_phone = request.getParameter("inquiry_phone");
		String inquiry_company = request.getParameter("inquiry_company");
		String inquiry = request.getParameter("inquiry");
			
		StringBuffer content = new StringBuffer();


		if(EmailUtil.sendWithHTMLFormatWithCC("noreply@webtogo.com.ph", email, "Inquiry", content.toString(), null)){
			return Action.SUCCESS;
		}
		
	
		return Action.ERROR;						
	}
	
	
	
	
//	public String sendGroupMessage(List<NissanReservation> nissanReservationList, String groupServiceType) {
//		NissanReservation nissanReservation = nissanReservationList.get(0);
//		NissanReservation reservation = new NissanReservation();
//		String email = nissanReservation.getBookerEmail();
//		String transactionId = "";
//		StringBuffer content = new StringBuffer();
//		
//		setEmailSettings();
//		EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
//		
//		content.append("<h3>Dear Sir/Ma'am, </h3>");
//		content.append("This is the acknowledgement of your booking with the following details:");
//		content.append("<h3>Booker Information</h3>");
//		content.append("<table>");
//		content.append("<tr>");
//		content.append("<td>Booker's Name:</td>");
//		content.append("<td>" + nissanReservation.getBookerLastName() + "</td>");
//		content.append("</tr>");
//		content.append("<tr>");
//		content.append("<td>Contact Details:</td>");
//		content.append("<td>" + nissanReservation.getBookerPhone() + " / " + nissanReservation.getBookerMobile() + "</td>");
//		content.append("</tr>");
//		content.append("<tr>");
//		content.append("<td>Email Address</td>");
//		content.append("<td>" + nissanReservation.getBookerEmail() + "</td>");
//		content.append("</tr>");
//		content.append("<tr>");
//		content.append("<td>Company Name:</td>");
//		content.append("<td>" + nissanReservation.getCompanyName() + "</td>");
//		content.append("</tr>");
//		content.append("<tr>");
//		content.append("<td>Company Address.:</td>");
//		content.append("<td>" + nissanReservation.getCompanyAdd() + "</td>");
//		content.append("</tr>");
//		content.append("<tr>");
//		content.append("<td>Company No.:</td>");
//		content.append("<td>" + nissanReservation.getCompanyPhone() + "</td>");
//		content.append("</tr>");
//		content.append("<tr>");
//		content.append("<td>Special Instruction:</td>");
//		content.append("<td>" + nissanReservation.getBookerSpecialInstruction() + "</td>");
//		content.append("</tr>");
//		content.append("</table>");
//		content.append("<h3>Renter Information</h3>");
//		content.append("<table>");
//		content.append("<tr>");
//		content.append("<td>Last Name</td>");
//		content.append("<td>Given Name</td>");
//		content.append("<td>Middle Initial</td>");
//		content.append("<td>Mobile Number</td>");
//		content.append("<td>Landline</td>");
//		content.append("<td>Gender</td>");
//		content.append("<td>Nationality</td>");
//		content.append("<td>Vehicle</td>");
//		
//		if(groupServiceType.equals(SERVICE_TYPE_ARRIVAL)) {
//			content.append("<td>Flight Number</td>");
//			content.append("<td>Arrival Date</td>");
//			content.append("<td>ETA</td>");
//			content.append("<td>Airport Location</td>");
//			content.append("<td>Destination</td>");
//			content.append("<td>Nearest Landmark</td>");
//			content.append("<td>Mode of Payment</td>");
//			content.append("<td>Remarks</td>");
//		}
//		else if(groupServiceType.equals(SERVICE_TYPE_DEPARTURE)) {
//			content.append("<td>Flight Number</td>");
//			content.append("<td>Departure Date</td>");
//			content.append("<td>Departure Time</td>");
//			content.append("<td>Airport Location</td>");
//			content.append("<td>Pick Up Date</td>");
//			content.append("<td>Pick Up Time</td>");
//			content.append("<td>Pick Up Address</td>");
//			content.append("<td>Nearest Landmark</td>");
//			content.append("<td>Mode of Payment</td>");
//			content.append("<td>Remarks</td>");
//		}
//		else if(groupServiceType.equals(SERVICE_TYPE_INTERCITY)) {
//			content.append("<td>Pick Up Date</td>");
//			content.append("<td>Pick Up Time</td>");
//			content.append("<td>Pick Up Address</td>");
//			content.append("<td>Drop-off</td>");
//			content.append("<td>Nearest Landmark</td>");
//			content.append("<td>Mode of Payment</td>");
//			content.append("<td>Remarks</td>");
//		}
//		
//		content.append("</tr>");
//		
//		for(int i=0; i<nissanReservationList.size(); i++) {
//			reservation = nissanReservationList.get(i);
//			transactionId += String.format("%06d", reservation.getId());
//
//			if(nissanReservationList.size() != i+1) {
//				transactionId += COMMA;
//			}
//			
//			content.append("<tr>");
//			content.append("<td>" + reservation.getLname() + "</td>");
//			content.append("<td>" + reservation.getFname() + "</td>");
//			content.append("<td>" + reservation.getMname() + "</td>");
//			content.append("<td>" + reservation.getMobileNo() + "</td>");
//			content.append("<td>" + reservation.getLandline() + "</td>");
//			content.append("<td>" + reservation.getSex() + "</td>");
//			content.append("<td>" + reservation.getNationality() + "</td>");
//			content.append("<td>" + reservation.getVehicleType() + "</td>");
//
//			if(groupServiceType.equals(SERVICE_TYPE_ARRIVAL)) {
//				content.append("<td>" + reservation.getArrivalFlightNum() + "</td>");
//				content.append("<td>" + reservation.getPickDate() + "</td>");
//				content.append("<td>" + reservation.getPickHour() + ":" + reservation.getPickMinute() + "</td>");
//				content.append("<td>" + reservation.getArrivalTerminal() + "</td>");
//				content.append("<td>" + reservation.getPickStreet() + "</td>");
//				content.append("<td>" + reservation.getLandmark() + "</td>");
//				content.append("<td>" + reservation.getPaymentType() + "</td>");
//				content.append("<td>" + reservation.getRemarks() + "</td>");
//			}
//			else if(groupServiceType.equals(SERVICE_TYPE_DEPARTURE)) {
//				content.append("<td>" + reservation.getArrivalFlightNum() + "</td>");
//				content.append("<td>" + reservation.getPickDate() + "</td>");
//				content.append("<td>" + reservation.getPickHour() + ":" + reservation.getPickMinute() + "</td>");
//				content.append("<td>" + reservation.getArrivalTerminal() + "</td>");
//				content.append("<td>" + reservation.getReturnDate() + "</td>");
//				content.append("<td>" + reservation.getReturnHour() + ":" + reservation.getReturnMinute() + "</td>");
//				content.append("<td>" + reservation.getPickStreet() + "</td>");
//				content.append("<td>" + reservation.getLandmark() + "</td>");
//				content.append("<td>" + reservation.getPaymentType() + "</td>");
//				content.append("<td>" + reservation.getRemarks() + "</td>");
//			}
//			else if(groupServiceType.equals(SERVICE_TYPE_INTERCITY)) {
//				content.append("<td>" + reservation.getPickDate() + "</td>");
//				content.append("<td>" + reservation.getPickHour() + ":" + reservation.getPickMinute() + "</td>");
//				content.append("<td>" + reservation.getPickStreet() + "</td>");
//				content.append("<td>" + reservation.getReturnStreet() + "</td>");
//				content.append("<td>" + reservation.getLandmark() + "</td>");
//				content.append("<td>" + reservation.getPaymentType() + "</td>");
//				content.append("<td>" + reservation.getRemarks() + "</td>");
//			}
//			
//			content.append("</tr>");
//		}
//		
//		content.append("<tr>");
//		content.append("<td></td>");
//		content.append("</tr>");
//		content.append("</table>");
//		content.append("Your transaction ID number is/are <strong>" + transactionId + "</strong>. Please note that this is not your booking confirmation number.<br><br>");
//		content.append("We will now review your requirement and shall send thru your e-mail your booking confirmation details.<br><br>");
//		content.append("Please contact our Reservation Center for any concerns regarding your booking.<br><br>");
//		content.append(" Nissan Car Lease Philippines, Inc. (Nissan Rent A Car)<br>");
//		content.append("Telephone Numbers: (+632) 886 9931-39; (+632) 854 7099; (+632) 854 8331<br>");
//		content.append("Email Address: reservations@nissanrentacar.com<br>");
//		content.append("Mobile Numbers: (+063) 917 808 2144<br><br>");
//		content.append("Thank you for booking with Nissan Car Lease Philippines, Inc. (Nissan Rent A Car)<br><br>");
//		content.append("We look forward to serving you.</p><br><br>");
//		content.append("To confirm your transaction please click the following link(s):</p></br>");
//		
//		for(NissanReservation res : nissanReservationList) {
//			content.append("<center><a href='http://"+company.getServerName()+"/confirmed.do?transactionId=" + res.getId() + "'>http://"+company.getServerName()+"/confirmed.do?transactionId=" + res.getId() + "</a></center>");
//		}
//		
//		request.setAttribute("transactionId", transactionId);
//		
//		if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", email, "Nissan Rent A Car Online Reservation", content.toString(), null)){
//			return Action.SUCCESS;
//		}
//
//		return Action.ERROR;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	public String sendCourseApplication(){
		successUrl = request.getParameter("successUrl");
		errorUrl = request.getParameter("errorUrl");
		
		String enrollee = request.getParameter("enrollee");
		String rank = request.getParameter("rank");
		String contact = request.getParameter("contact");
		String email = request.getParameter("email");
		String course = request.getParameter("course");
		String schedule = request.getParameter("schedule");
		String comEmail = request.getParameter("to");
		
		StringBuffer content = new StringBuffer();
		content.append("Thank you for applying to " + course + ".<br/><br/>");
		
		content.append("<b>Course Application Details:</b><br><br>");

		content.append("Enrollee: " + enrollee + "<br/>");
		content.append("Rank: " + rank + " <br/>");
		content.append("Contact: " + contact + " <br/>");
		content.append("Email: " + email + " <br/>");
		content.append("Schedule: " + schedule + " <br/>");
		
		setEmailSettings();
		/*EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);*/
		String to = email;
		boolean emailSent = false;
		int sendingTrial = 0;
		while(!emailSent){ //second email trial
			sendingTrial += 1;
			if(sendingTrial == 5){
				return ERROR;
			}
			Random rand = new Random();
			int  n = rand.nextInt(4);
			Logger logger = Logger.getLogger(EmailUtil.class);
			logger.info("- - - - - - - - - - > > Email Sender: " + EmailUtil.OTHER_USERNAMES[n]);
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					EmailUtil.OTHER_USERNAMES[n], EmailUtil.OTHER_PASSWORDS[n]);

			emailSent = EmailUtil.sendWithHTMLFormat(EmailUtil.OTHER_USERNAMES[n], to, "ACKNOWLEDGEMENT: Sandigan Maritime Training - Course Application", content.toString(), null);
	
		}
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber), EmailUtil.DEFAULT_USERNAME, EmailUtil.DEFAULT_PASSWORD);
		
		String from = EmailUtil.DEFAULT_USERNAME;
		
		String[] tooo = comEmail.split(",");
		emailSent = EmailUtil.sendWithHTMLFormatWithCC(from, tooo, "Sandigan Maritime Training - Course Application", content.toString(), null);
		if(!emailSent){
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	
	
	
	public String sendNewPassword(String username, String password){
		setEmailSettings();
		EmailUtil.connect(smtp, Integer.parseInt(portNumber),
				mailerUserName, mailerPassword);
		
		System.out.println("user>>"+username);
		StringBuffer content = new StringBuffer();
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		content.append("<html xmlns= 'http://www.w3.org/1999/xhtml' >");
		content.append("<head>");
		content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
		content.append("<link href='css/css.css' rel='stylesheet' type='text/css' />");
		content.append("</head><body>");
		
		content.append("<br> Good day!");
		content.append("<br><br>Your temporary password is " +password);
		content.append("<br><br>Please change it immediately after you have logged in.");
		content.append("<br><br> Thank you.");
		content.append("<br><br> <a href=\"http://pocketpons.com/login.do\">http://pocketpons.com/login.do</a> ");

		if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", username, "Forgot Password Request", content.toString(), null)){
			return Action.SUCCESS;
		}
		
		return Action.SUCCESS;
	}

	private File renameTempFile(File source, String fn) {
		String[] decoded = fn.split("[.]");

		try {
			File dest = File.createTempFile("atatchment", "."
					+ decoded[decoded.length - 1]);
			FileUtil.copyFile(source, dest);
			return dest;
		} catch (IOException ioe) {
			logger.fatal("unable to rename temp file", ioe);
		}
		return null;
	}
	
	public String pdfLoanApplication(){
		JSONObject obj = new JSONObject();
		
		String dateOfApplication = (String) request.getParameter("dateOfApplication");
		String amount = (String) request.getParameter("amount");
		String term = (String) request.getParameter("term");
		String purpose = (String) request.getParameter("purpose");
		
		String lname = (String) request.getParameter("lname");
		String fname = (String) request.getParameter("fname");
		String mname = (String) request.getParameter("mname");
		String maidenName = (String) request.getParameter("maidenName");
		String birthday = (String) request.getParameter("birthday");
		String birthplace = (String) request.getParameter("birthplace");
		String civil = (String) request.getParameter("civil");
		String age = (String) request.getParameter("age");
		String nationality = (String) request.getParameter("nationality");
		String numDep = (String) request.getParameter("numDep");
		
		String presentAdd = (String) request.getParameter("presentAdd");
		String presentZipCode = (String) request.getParameter("presentZipCode");
		String presentStay = (String) request.getParameter("presentStay");
		String permaAdd = (String) request.getParameter("permaAdd");
		String permaZipCode = (String) request.getParameter("permaZipCode");
		String permaStay = (String) request.getParameter("permaStay");
		String residence = (String) request.getParameter("residence");
		String tin = (String) request.getParameter("tin");
		String sss = (String) request.getParameter("sss");
		String telephone = (String) request.getParameter("telephone");
		String fax = (String) request.getParameter("fax");
		String facebook = (String) request.getParameter("facebook");
		String email = (String) request.getParameter("email");
		
		String employer = (String) request.getParameter("employer");
		String employment = (String) request.getParameter("employment");
		String position = (String) request.getParameter("position");
		String businessAdd = (String) request.getParameter("businessAdd");
		String industry = (String) request.getParameter("industry");
		String businessTel = (String) request.getParameter("businessTel");
		
		
		obj.put("Date of Application", dateOfApplication);
		obj.put("Amount", amount);
		obj.put("Term", term);
		obj.put("Purpose", purpose);
		
		obj.put("Last Name", lname);
		obj.put("First Name", fname);
		obj.put("Middle Name", mname);
		obj.put("Mother's Maiden Name", maidenName);
		obj.put("Birthday", birthday);
		obj.put("Birthplace", birthplace);
		obj.put("Civil", civil);
		obj.put("Age", age);
		obj.put("Nationality", nationality);
		obj.put("Number of Dependents", numDep);
		
		obj.put("Present Address", presentAdd);
		obj.put("Present Zip Code", presentZipCode);
		obj.put("Present Length of Stay", presentStay);
		obj.put("Permanent Address", permaAdd);
		obj.put("Permanent Zip Code", permaZipCode);
		obj.put("Permanent Length of Stay", permaStay);
		obj.put("Residence", residence);
		obj.put("TIN", tin);
		obj.put("SSS/GSIS", sss);
		obj.put("Telephone", telephone);
		obj.put("Fax", fax);
		obj.put("Email Address", email);
		obj.put("Facebook", facebook);
		
		obj.put("Employer", employer);
		obj.put("Employed", employment);
		obj.put("Position", position);
		obj.put("Business Address", businessAdd);
		obj.put("Industry", industry);
		obj.put("Business Telephone", businessTel);
		
		
		setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		return SUCCESS;
		
	}
	
	public String sendRaffleEntry() throws IOException {
		
		JSONObject obj = new JSONObject();
		
		String name = (String) request.getParameter("1a|name");
		String address = (String) request.getParameter("1b|address");
		String emailAdd = (String) request.getParameter("1c|email");
		String contactNo = (String) request.getParameter("1d|contact_no.");
		String officialReceipt = (String) request.getParameter("1e|official_receipt#");
		String branch = (String) request.getParameter("1f|branch");
		String raffleCode = (String) request.getParameter("1g|raffle_code").trim();
		
		String basePath = servletContext.getRealPath("");
		String locationPath = basePath +  File.separatorChar + "companies" + File.separatorChar + "skechers" + File.separatorChar;
		new File(locationPath).mkdir();
		String fileName="10000 Random Codes_Demi World Tour.xls";
		String filePath =  locationPath + "files"+  File.separatorChar  + fileName;
		//filePath =  locationPath + fileName;
		logger.debug("filepath (member) : " + filePath);		
		
		FileInputStream fileInputStream = new FileInputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook.getSheetAt(0);
		
		int rows = worksheet.getPhysicalNumberOfRows();
		int count = 1;
		boolean isValidRaffleCode = false;
		for(int i = 0; i < rows; i++) {
			if(i>4) //start of raffle code
			{
				String uniqueCode = worksheet.getRow(i).getCell((short) 1).getStringCellValue();
				if(uniqueCode.equalsIgnoreCase(raffleCode)) {
					isValidRaffleCode = true;
					break;
				}
				count++;
			}
		}
		
		List<SavedEmail> list = savedEmailDelegate.findEmailByFormName(company, "Raffle entry form", Order.desc("createdOn")).getList();
		boolean isUsedRaffleCode = false;
		for(SavedEmail se : list) {
			if(raffleCode.equalsIgnoreCase(se.getOtherDetailMap().get("raffleCode"))) {
				isUsedRaffleCode = true;
				break;
			}
		}
		
		if(!isValidRaffleCode) {
			obj.put("valid", false);
		} else if(isUsedRaffleCode) {
			obj.put("isDuplicate", true);
		} else {
			obj.put("valid", true);
			if(!sendEmail()) {
				return Action.ERROR;
			}
		}
		
		setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		
		return Action.SUCCESS;
		
	}
	
	


	private void saveEmailInformation(String emailContent) {
		String formName = "";
		String sender = "";
		String senderEmail = "";
		String senderPhone = "";
		String testimonial = "";
		String receipt = "";
		String promo = "";
		String model = "";
		System.out
				.println("saving email information...................................");
		try {
			formName = request.getParameter("se_formName");
			sender = request.getParameter("se_sender");
			senderEmail = request.getParameter("se_email");
			senderPhone = request.getParameter("se_phone");
			testimonial = request.getParameter("se_testimonial");
			
			
			
			if(company.getName().equalsIgnoreCase("panasonic")){
				model = request.getParameter("1f|receipt_number");
				promo = request.getParameter("1e|promo_code");
			}else{
				receipt = request.getParameter("1e|receipt_number");
				promo = request.getParameter("1f|promo_code");
			}

			//System.out.println("sender: " + sender);
			//System.out.println("senderEmail: " + senderEmail);
			if(company.getName().equalsIgnoreCase("noelle") && isForAcknowledgement)
				formName = "Acknowledgement - "+formName;

			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(sender);
			savedEmail.setEmail(senderEmail);
			savedEmail.setPhone(senderPhone);
			savedEmail.setFormName(formName);
			savedEmail.setEmailContent(emailContent);
			savedEmail.setTestimonial(testimonial);
			savedEmail.setKaptcha(wtgKaptcha);
			
			/*
			 * Bluewarner
			 */
			if(company.getName().equalsIgnoreCase("panasonic")){
				savedEmail.setReceipt(model);
				savedEmail.setPromo(promo);
			}else{
				savedEmail.setReceipt(receipt);
				savedEmail.setPromo(promo);
			}
			
			/*
			 * Bluewarner
			 */
			
			if(company.getName().equalsIgnoreCase("noelle")) {
				savedEmail.setOtherField1(request.getParameter("programName"));
				savedEmail.setOtherField2(request.getParameter("programId"));
			}

			if(company.getName().equalsIgnoreCase("skechers") && request.getParameter("se_formName").equalsIgnoreCase("Raffle entry form")) {
				Map<String, String> otherDetailMap = new HashMap<String, String>();
				otherDetailMap.put("name", request.getParameter("1a|name"));
				otherDetailMap.put("address", request.getParameter("1b|address"));
				otherDetailMap.put("email", request.getParameter("1c|email"));
				otherDetailMap.put("contactNo", request.getParameter("1d|contact_no."));
				otherDetailMap.put("officialReceipt", request.getParameter("1e|official_receipt#"));
				otherDetailMap.put("branch", request.getParameter("1f|branch"));
				otherDetailMap.put("raffleCode", request.getParameter("1g|raffle_code"));
				savedEmail.setOtherDetailMap(otherDetailMap);
			}
			
			/*
			 * Bluewarner
			 */
			
			if(company.getName().equalsIgnoreCase("bluewarner") && request.getParameter("se_formName").equalsIgnoreCase("Home")) {
				
					
					Map<String, String> otherDetailMap = new HashMap<String, String>();
					otherDetailMap.put("name", request.getParameter("1a|name"));
					otherDetailMap.put("address", request.getParameter("1b|address"));
					otherDetailMap.put("email", request.getParameter("1c|email"));
					otherDetailMap.put("contactNo", request.getParameter("1d|cnumber"));
					otherDetailMap.put("receipt", request.getParameter("1e|receipt_number"));
					otherDetailMap.put("promo", request.getParameter("1f|promo_code"));
					savedEmail.setOtherDetailMap(otherDetailMap);
				
				
			}
			
			
			/*
			 * Bluewarner
			 */
			
			/*
			 * Panasonic
			 */
			
			if(company.getName().equalsIgnoreCase("panasonic")) {
				
					
					Map<String, String> otherDetailMap = new HashMap<String, String>();
					otherDetailMap.put("name", request.getParameter("1a|name"));
					otherDetailMap.put("address", request.getParameter("1b|address"));
					otherDetailMap.put("email", request.getParameter("1c|contact_number"));
					otherDetailMap.put("contactNo", request.getParameter("1d|email"));
					otherDetailMap.put("receipt", request.getParameter("1f|model_number"));
					otherDetailMap.put("promo", request.getParameter("1e|promo_code"));
					savedEmail.setOtherDetailMap(otherDetailMap);
				
				
			}
			
			
			/*
			 * Panasonic
			 */
			
			if (!(company.getName().equalsIgnoreCase("purpletag") || company.getName().equalsIgnoreCase("purpletag2")))
				savedEmailDelegate.insert(savedEmail);

			//System.out.println("sender: " + savedEmail.getSender());
			//System.out.println("senderEmail: " + savedEmail.getEmail());
			myEmail = savedEmail.getEmail();

		} catch (Exception e) {
			logger.debug("failed to save the email information for "
					+ company.getName());
		}
	}
	
	private void saveTotalQueueEmailInformation(String emailContent,
			String sender, String formName, String email, String downloadLink,
			String industry ,String softwareVersion,String country) {
		
		System.out
				.println("saving  total queue email information...................................");
		try {

			SavedEmail savedEmail = new SavedEmail();
			savedEmail.setCompany(company);
			savedEmail.setSender(sender);
			savedEmail.setEmail(email);
			savedEmail.setFormName(formName);
			savedEmail.setEmailContent(emailContent);
			savedEmail.setDownloadLink(downloadLink);
			savedEmail.setOtherField1(softwareVersion);
			savedEmail.setOtherField2(industry);
			savedEmail.setOtherField3(country);
			
			savedEmailDelegate.insert(savedEmail);

		} catch (Exception e) {
			logger.debug("failed to save the email information for "
					+ company.getName());
		}
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * @param certificate
	 *            the certificate to set
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	private String decodeKey(String key) {
		return firstLetterWordUpperCase(key.replace("_", " "));
	}

	private String firstLetterWordUpperCase(String s) {
		char[] chars = s.trim().toLowerCase().toCharArray();
		boolean found = false;

		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i])) {
				found = false;
			}
		}

		return String.valueOf(chars);
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public void setUpload(File file) {
		this.file = file;
	}

	public void setUploadContentType(String contentType) {
		this.setContentType(contentType);
	}

	public void setUploadFileName(String filename) {
		this.filename = filename;
	}

	public File getUpload() {
		return file;
	}

	public String getUploadFileName() {
		return filename;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public CategoryItem getCatItem() {
		return catItem;
	}

	public void setCatItem(CategoryItem catItem) {
		this.catItem = catItem;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getContentType() {
		return contentType;
	}

	public String getMyEmail() {
		return myEmail;
	}

	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}
	
	public List<SavedEmail> getSavedEmailList() {
		savedEmailList = savedEmailDelegate.findAll(company).getList();
		return savedEmailList;
	}

	public void setSavedEmailList(List<SavedEmail> savedEmailList) {
		this.savedEmailList = savedEmailList;
	}	

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getLotPrice() {
		return lotPrice;
	}

	public void setLotPrice(String lotPrice) {
		this.lotPrice = lotPrice;
	}

	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getPcf() {
		return pcf;
	}

	public void setPcf(String pcf) {
		this.pcf = pcf;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}

	public String getMonthly() {
		return monthly;
	}

	public void setMonthly(String monthly) {
		this.monthly = monthly;
	}

	public String getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(String contractPrice) {
		this.contractPrice = contractPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getAtNeedPrice() {
		return atNeedPrice;
	}

	public void setAtNeedPrice(String atNeedPrice) {
		this.atNeedPrice = atNeedPrice;
	}

	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		this.servletContext = context;
	}

	public void setUpdateReferralToRequested(Boolean updateReferralToRequested) {
		this.updateReferralToRequested = updateReferralToRequested;
	}

	public Boolean getUpdateReferralToRequested() {
		return updateReferralToRequested;
	}

	public void setReferralId(Long referralId) {
		this.referralId = referralId;
	}

	public Long getReferralId() {
		return referralId;
	}

	public void setReferralReward(String referralReward) {
		this.referralReward = referralReward;
	}

	public String getReferralReward() {
		return referralReward;
	}

	public void setRewardsToClaim(String rewardsToClaim[]) {
		this.rewardsToClaim = rewardsToClaim;
	}

	public String[] getRewardsToClaim() {
		return rewardsToClaim;
	}

	public void setReferralsId(Long referralsId[]) {
		this.referralsId = referralsId;
	}

	public Long[] getReferralsId() {
		return referralsId;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	/* for Custom Email Settings 
	
	 * this function is called to check and set the email settings of 
	 * a company.
	 *
	 *
	 */
	public void setEmailSettings() {
		
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

	/* Custom Email Settings */
	
	/*nagsesend ng notification message dun sa naginquire*/
	public void replyInquirer(){
		
		boolean sent = false;
		
		if(company.getCompanySettings().getAutoAnswer()){
			
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName,
					mailerPassword);
			
			String message = company.getCompanySettings().getMessage();
			String subject = company.getCompanySettings().getSubject();
			
			
			sent = EmailUtil.sendWithHTMLFormat(mailerUserName, request.getParameter("se_email"), subject, message,null);
			
		}
		
		
	}
	/*==========---------------===========*/

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
			
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean send_confirmation_message() {
		boolean emailSent = false;
		StringBuffer content = new StringBuffer();
		String downloadFor = request.getParameter("download_for");
		if(company.getName().equalsIgnoreCase("skechers") && request.getParameter("se_formName").equalsIgnoreCase("Raffle entry form")) {
			content.append("This is to acknowledge that we have received "+request.getParameter("subject").toLowerCase()+".<br/>");
		} else {
			content.append("This is to acknowledge that we have received your "+request.getParameter("subject")+".<br/>");
			if(company.getName().equalsIgnoreCase("fas") && (downloadFor != null)) {
				content.append("You have downloaded a file for "+downloadFor+".<br/>");
			}
		}
		
		
		
		/*
		 * Bluewarner
		 */
		if(company.getName().equalsIgnoreCase("bluewarner") && request.getParameter("se_formName").equalsIgnoreCase("Home")){
			
			content.replace(0,content.length(),("<br><br>Hi!<br><br>" +
					"Thank you for registering for the B'lue Family Mart Batman v Superman Dawn of Justice Promo." +
					" This counts as one raffle entry that give you a chance to win awesome Batman v Superman Dawn of Justice " +
					"premium items. Buy more B'lue to get more chances to win.<br><br>" +
					"Have a great day! <br><br>"));
			
			
		}
		/*
		 * Bluewarner
		 */
		
		/*
		 * panasonic
		 */
		if(company.getName().equalsIgnoreCase("panasonic")){
			
			content.replace(0,content.length(),("Thank your joining the Panasonic basketball dreams promo. This acknowledges that we have received your entry."));
			
			
		}
		/*
		 * panasonic
		 */
		
		/*
		 * Truecare
		 */
		
		if(company.getName().equalsIgnoreCase("truecare") && request.getParameter("se_formName").equals("Newsletter")){
			content.replace(0,content.length(),("<br><br>Hi!<br><br>" +
					"Thank you for subscribing to our newsletter."));
		}
		
		/*
		 * Truecare
		 */
		
		
		isForAcknowledgement = true;
		if(!company.getName().equalsIgnoreCase("bluewarner") && !company.getName().equalsIgnoreCase("panasonic") && !request.getParameter("se_formName").equals("Newsletter")){
			content.append("---------------------------------------------------------------------------------------<br/>");
		}
		try {
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			//System.out.println("success URL::::  " + successUrl);
			
			
			String subject = "ACKNOWLEDGMENT : "+request.getParameter("subject")/* + " - " + company.getNameEditable()*/;
			
			if(company.getName().equalsIgnoreCase("bluewarner")){
				subject = "B'lue Family Mart Batman v Superman Dawn of Justice Promo";
			}
			
			if(company.getName().equalsIgnoreCase("panasonic")){
				subject = "Panasonic and NBA All-Star Promo";
			}
			
			if(company.getName().equalsIgnoreCase("truecare") && request.getParameter("se_formName").equals("Newsletter")){
				subject = request.getParameter("subject");
			}
			
			
			if(company.getName().equalsIgnoreCase("skechers")) {
				//subject = "Your online registration was successfully registered to our database (SKechers PH)";
			}
			
			String title = request.getParameter("title");
			String to = request.getParameter("se_email");
			String from = request.getParameter("from");
			
			
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.ELITETRANSLATIONS)&&title.equalsIgnoreCase("Request a Free Quote")){
				Date fromDate = new Date();
				fromDate =  new Date(fromDate.getYear(), fromDate.getMonth(), fromDate.getDate(), 0, 0);
				Date toDate = new Date();
				String formName = request.getParameter("se_formName");
				ObjectList<SavedEmail> saveEmails = savedEmailDelegate.findEmailByDateAndFormName(company, fromDate, toDate,formName);
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");				
				Integer inquiryCount = 1;
				if(saveEmails!=null){
				inquiryCount+=saveEmails.getTotal();
				}
				int inquiryCountLength = inquiryCount.toString().length();
				String quotationInquiryNumber = "Q"+df.format(fromDate)+String.format("%0"+ (inquiryCountLength>2?inquiryCountLength:2) +"d",inquiryCount);
				subject+= String.format("%03d",inquiryCount)+" - "+quotationInquiryNumber;
			}
			
			
			
			
			
			if (company.getId() == CompanyConstants.PUREGOLD || company.getId() == CompanyConstants.PUREGOLD_TEST) {
			String topic = request.getParameter("topic");
			String[] temp = to.split(",");
			if (topic.equals("Marketing Concerns")) {
			to = temp[0];
			} else if (topic.equals("Leasing Concerns"))  {
			to = temp[2];
			} else {
			to = temp[1];
			}
			}
			
			List<String> ignored = new ArrayList<String>();
			ignored.add("subject");
			ignored.add("to");
			ignored.add("from");
			ignored.add("title");
			ignored.add("submit");
			ignored.add("successurl");
			ignored.add("errorurl");
			ignored.add("se_formname");
			ignored.add("se_sender");
			ignored.add("se_email");
			ignored.add("se_phone");
			ignored.add("se_items");
			ignored.add("maxfilesize");
			ignored.add("companyId");
			
			Iterator<Map.Entry<String, String[]>> iterator = request
			.getParameterMap().entrySet().iterator();
			
			String ajaxSubmit = request.getParameter("ajax_submit");
			String email_content = request.getParameter("email_content");
			boolean ajax_submit = false;
			if(email_content != null && ajaxSubmit != null) {
				content.append(email_content);
				ajax_submit = true;
			}
			
			while (iterator.hasNext() && !company.getName().equalsIgnoreCase("bluewarner") && !company.getName().equalsIgnoreCase("panasonic") && !ajax_submit) {
				Map.Entry<String, String[]> entry = iterator.next();
				//System.out.println("------entry-------" + entry);
				if (entry.getKey().contains("|")) {
					tm.put(entry.getKey(), entry.getValue());
				}
				if (request.getParameter("se_hasDelimiter") == null
				|| !(request.getParameter("se_hasDelimiter")
				.equalsIgnoreCase("true"))) {
					if (entry != null) {
						//System.out.println("ENTRY KEY: " + entry.getKey());
						if (!ignored.contains(entry.getKey().toLowerCase())) {
						/*
						* content =
						* content.append(decodeKey(entry.getKey()) + " : "
						* + request.getParameter(entry.getKey()) + "\n\n");
						*/
						content.append(decodeKey(entry.getKey())).append(
						" : ").append(
						request.getParameter(entry.getKey()));
						// content.append("\r\n\r\n");
						content.append("<br/><br/>");
						
						}
					} else {
					break;
					}
				}
			}
			
			content = referralContent(content, "<br/>");
			
			if ((request.getParameter("se_hasDelimiter") != null && request
			.getParameter("se_hasDelimiter").equalsIgnoreCase("true")) && !company.getName().equalsIgnoreCase("bluewarner") && !company.getName().equalsIgnoreCase("panasonic") 
					&& !ajax_submit) {
			
				Set set = tm.entrySet();
				Iterator i = set.iterator();
				while (i.hasNext()) {
					Map.Entry me = (Map.Entry) i.next();
					String requestStr = me.getKey().toString();
					String fieldName = decodeKey(me.getKey().toString()
					.substring(me.getKey().toString().indexOf("|") + 1));
					
					/*
					* if(subject.equalsIgnoreCase("Online Inquiry Submission"))
					* { subject = "Website Online Inquiry"; }
					* System.out.println("me.getKey() : " +
					* me.getKey().toString());
					* System.out.println("FIELD NAME : " + fieldName);
					*/
					if (fieldName.equalsIgnoreCase("name")
					|| me.getKey().toString().equalsIgnoreCase("name")) {
					if (!request.getParameter(requestStr).equals("")) {
					subject = subject + " - "
					+ request.getParameter(requestStr) + " (" + company.getNameEditable() + ")";
					}
					}
					
					// content = content.append(fieldName);
					
					if (fieldName.equalsIgnoreCase("BillingNumber")) {
					fieldName = "Booking Number";
					}
					
					if(fieldName.equalsIgnoreCase("Personal Information")) {
					fieldName = "<strong>I. "+fieldName.toUpperCase()+"</strong>";
					}
					if(fieldName.equalsIgnoreCase("Family Background")) {
					fieldName = "<strong>II. "+fieldName.toUpperCase()+"</strong>";
					}
					if(fieldName.equalsIgnoreCase("Educational Background")) {
					fieldName = "<strong>III. "+fieldName.toUpperCase()+"</strong>";
					}
					
					if(fieldName.equalsIgnoreCase("Elementary") || fieldName.equalsIgnoreCase("Secondary") || fieldName.equalsIgnoreCase("Vocational / Trade Course") ||
					fieldName.equalsIgnoreCase("Tertiary") || fieldName.equalsIgnoreCase("Graduate Studies - Diploma") || fieldName.equalsIgnoreCase("Graduate Studies - Masters") || 
					fieldName.equalsIgnoreCase("Graduate Studies - Doctorate") || fieldName.equalsIgnoreCase("*Non-degree Course")) {
					fieldName = "<strong>"+fieldName+"</strong>";
					}
					content.append(fieldName);
					
					if (requestStr.equalsIgnoreCase("1g|Location")) {
					// content.append(" : "
					// +categoryDelegate.find(Long.parseLong(request.getParameter(requestStr))).getName());
					content.append(" : ").append(
					categoryDelegate.find(
					Long.parseLong(request
					.getParameter(requestStr)))
					.getName());
					} else if (requestStr.equalsIgnoreCase("1h|Product Name")) {
					// content.append(" : "
					// +categoryDelegate.find(Long.parseLong(request.getParameter(requestStr))).getName());
					content.append(" : ").append(
					categoryDelegate.find(
					Long.parseLong(request
					.getParameter(requestStr)))
					.getName());
					} else if (requestStr
					.equalsIgnoreCase("1i|Memorial Product")) {
					// content.append(" : "
					// +categoryItemDelegate.find(company,Long.parseLong(request.getParameter(requestStr))).getName());
					content.append(" : ").append(
					categoryItemDelegate.find(
					company,
					Long.parseLong(request
					.getParameter(requestStr)))
					.getName());
					} else if(requestStr.equalsIgnoreCase("1k|services_needed")) {
					String[] services = request.getParameterValues("1k|services_needed");
					StringBuffer strServices = new StringBuffer();
					strServices.append(" : ");
					int count = 0;
					for(int j=0;j<services.length;j++)
					if(services[j] != null) {
					count++;
					strServices.append("("+(count)+") "+services[j]+" ");
					}
					
					content.append(strServices.toString());
					} else {
					// content.append(" : " +
					// request.getParameter(requestStr));
					
					content.append(" : ").append(
					request.getParameter(requestStr));
					}
					
					if(requestStr.equalsIgnoreCase("7b|within_the_third_degree_local") && 
					request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
					content.append("<br/>Particulars : ").append(request.getParameter("question_b"));
					}
					
					if(requestStr.equalsIgnoreCase("7c|have_ever_been_declared_guilty") && 
					request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
					content.append("<br/>Offense Details : ").append(request.getParameter("question_c"));
					}
					
					if(requestStr.equalsIgnoreCase("7d|have_ever_been_convicted") && 
					request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
					content.append("<br/>Details : ").append(request.getParameter("question_d"));
					}
					
					if(requestStr.equalsIgnoreCase("7e|have_ever_been_forced_to_resign") && 
					request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
					content.append("<br/>Reasons : ").append(request.getParameter("question_e"));
					}
					
					if(requestStr.equalsIgnoreCase("7f|have_ever_been_candidate_in_a_election") && 
					request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
					content.append("<br/>Date of election and other particulars : ").append(request.getParameter("question_f"));
					}
					
					if(requestStr.equalsIgnoreCase("7g|is_member_of_any_indegenouse group") && 
					request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
					content.append("<br/>Details : ").append(request.getParameter("question_g1"));
					}
					if(requestStr.equalsIgnoreCase("7h|is_differently_abled") && 
					request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
					content.append("<br/>Details : ").append(request.getParameter("question_g2"));
					}
					if(requestStr.equalsIgnoreCase("7i|is_solo_parent") && 
					request.getParameter(requestStr).equalsIgnoreCase("yes") ) {
					content.append("<br/>Details : ").append(request.getParameter("question_g3"));
					}
					// content.append("\n\n");
					// content.append("\r\n\r\n");
					content.append("<br/><br/>");
					
					//System.out.print(me.getKey() + ": ");
					//System.out.println(me.getValue());
				}
			}
			// --------------------------------------
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
			mailerUserName, mailerPassword);
			
			if (this.email.length() != 0) {
			content.append("<br/><br/><br/>");
			content.append(this.message);
			content.append("<br/>");
			}
			
			if (company.getId() == 350) {
			subject = request.getParameter("subject") + " - " + request.getParameter("1|inquiry") +
			" - " + request.getParameter("1a|name");
			}
			
			if (company.getName()
			.equalsIgnoreCase(CompanyConstants.HIPRECISION))
			if (request.getParameter("hpBranchName") != null) {
			  subject = request.getParameter("hpBranchName")
			                   .concat(" : ").concat(subject);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + request.getParameter("hpBranchName") + "<<<<<<<<<<<<<<<<<<<<");
			System.out.println(">>>>>>>>>>>>>>>>>>"+to+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			}
			
			/*
			if(company.getName().equalsIgnoreCase("noelle")) {
				content = new StringBuffer();
				content.append("Hi there!<br/>Thank you for booking a session with me! Below are the deatils of your schedule.");
				content.append("<br/>");
				content.append(acknowledgementContent);
				content.append("<br/>Looking forward to speaking with you.<br/><3 Noelle ");
			}*/
			
			if(acknowledgementContent != null && !acknowledgementContent.equals("")) {
				content = new StringBuffer();
				content.append(acknowledgementContent);
			}
			
			if (filename != null) {
				File dest = renameTempFile(file, filename);
				//content.append("destinations: ");
				//content.append(dest);
				emailSent = EmailUtil.sendWithHTMLFormat(from, to, subject,
				content.toString(), dest.getAbsolutePath());
			} else {
				int sendingTrial = 0;
				while(!emailSent){ //second email trial
					sendingTrial += 1;
					if(sendingTrial == 5){
						return emailSent;
					}
					Random rand = new Random();
					int  n = rand.nextInt(4);
					Logger logger = Logger.getLogger(EmailUtil.class);
					logger.info("- - - - - - - - - - > > Email Sender: " + EmailUtil.OTHER_USERNAMES[n]);
					if(!(mailerUserName == "") && !(mailerPassword == "")){
						EmailUtil.connect(smtp, Integer.parseInt(portNumber),
								mailerUserName, mailerPassword);
					}else{
						EmailUtil.connect(smtp, Integer.parseInt(portNumber),
								EmailUtil.OTHER_USERNAMES[n], EmailUtil.OTHER_PASSWORDS[n]);
					}
					boolean excluded = false;
					List<WebtogoExcludedEmails> em = webtogoExcludedEmailsDelegate.findAll();
					for(WebtogoExcludedEmails ss : em){
						if(to.contains(ss.getEmail())){
							excluded = true;
						}
						if(content.toString().contains(ss.getEmail())){
							excluded = true;
						}
						if(subject.contains(ss.getEmail())){
							excluded = true;
						}
						if(this.email.contains(ss.getEmail())){
							excluded = true;
						}
						if(content.toString().isEmpty()){
							excluded = true;
						}
					}
					if(!excluded){
						emailSent = EmailUtil.sendWithHTMLFormat(from, to,
								subject, content.toString(), null);
						
						if (this.email.length() != 0) {
							// emailSent = EmailUtil.send(from, this.email , subject,
							// content.toString(), null);
							emailSent = EmailUtil.sendWithHTMLFormat(from, this.email,
									subject, content.toString(), null);
						}
					}else{
						return emailSent=false;
					}
					
					
				}
				/*
				emailSent = EmailUtil.sendWithHTMLFormat(from, to.split(","),
				subject, content.toString(), null);
				if (this.email.length() != 0) {
				// emailSent = EmailUtil.send(from, this.email , subject,
				// content.toString(), null);
				emailSent = EmailUtil.sendWithHTMLFormat(from, this.email,
				subject, content.toString(), null);
				}*/
			}
			
			//Rosehall
			String rosehall_message = request.getParameter("rosehall_message");
			if (company.getId() == CompanyConstants.ROSEHALL) {
				EmailUtil.sendWithHTMLFormat(from, request.getParameter("1d|email"), subject, rosehall_message.toString(), null);
			}
			
			// Zunic Brochure Download
			if (company.getId() == 68) {
				String email = request.getParameter("1b|email");
				String sender = request.getParameter("1a|firstname");
				StringBuffer senderBuffer = new StringBuffer();
				
				char firstLetter = Character.toUpperCase(sender.charAt(0));
				senderBuffer.append(firstLetter);
				senderBuffer.append(sender.substring(1, sender.length()));
				sender = senderBuffer.toString();
				
				String destinationPath = "companies";
				FileUtil.createDirectory(servletContext
				.getRealPath(destinationPath));
				destinationPath += File.separator + company.getName();
				FileUtil.createDirectory(servletContext
				.getRealPath(destinationPath));
				destinationPath += File.separator + "pdf";
				FileUtil.createDirectory(servletContext
				.getRealPath(destinationPath));
				
				filename = "ZunicBrochure.pdf";
				File file = new File(servletContext.getRealPath(destinationPath
				+ File.separator + filename));
				
				content = new StringBuffer();
				content.append("Dear " + sender + ",\n\n");
				content.append("Thank you for requesting our brochure.\n");
				content
				.append("Below is the attached Brochure. Please see for more details about our Services.\n\n");
				content.append("Thank you.\n\nSincerely,\n\n");
				content.append("Zunic Slimming & Body Sculpting.\n");
				
				emailSent = EmailUtil.sendWithHTMLFormat(from, email, subject,
				content.toString(), file.getAbsolutePath());
			}
			
			
			/*
			 * Bluewarner
			 
			
			if(company.getName().equalsIgnoreCase("bluewarner") && request.getParameter("se_formName").equalsIgnoreCase("Home")){
				content = new StringBuffer();
				subject = "B�lue Family Mart Batman v Superman Dawn of Justice Promo";
				content.append("Hi!<br><br>" +
						"Thank you for registering for the B�lue Family Mart Batman v Superman Dawn of Justice Promo." +
						" This counts as one raffle entry that give you a chance to win awesome Batman v Superman Dawn of Justice " +
						"premium items. Buy more B�lue to get more chances to win.<br><br>" +
						"Have a great day! <br><br>");	
				emailSent = EmailUtil.sendWithHTMLFormat(from, request.getParameter("email"), subject,
							content.toString(), null);
				
			}
			
			
			 * Bluewarner
			 */
			
			
			
			
		} catch (Exception e) {
		logger.debug("failed to send email in " + company.getName()
		+ "... " + e);
		}
		
		if(!company.getName().equalsIgnoreCase("skechers") && !company.getName().equalsIgnoreCase("bluewarner") && !company.getName().equalsIgnoreCase("panasonic") 
				&& (company.getName().equalsIgnoreCase("truecare") && !request.getParameter("se_formName").equals("Inquiry"))) 
			saveEmailInformation(content.toString());
		
		requestedToClaimReferralRewards();
		
		if (company.getName().equalsIgnoreCase(CompanyConstants.CANCUN)
		&& referralsId != null) {
		
		List<Referral> refList = referralDelegate.findAllByIds(referralsId,
		company);
		
		String messageToReferrer = content.toString();
		
		String intro = "Hi <strong>"
		+ refList.get(0).getReferredBy().getFullName()
		+ "</strong><br><br>";
		
		// intro+="You wish to redeem "+claims+" from this Referral"+((refList.size()>1)?"s":"")+"<br><br>";
		
		String lastMessage = "<br>Thank you. <br><br>All the Best,<br<br>"
		+ company.getNameEditable() + " Team.";
		
		messageToReferrer = intro + messageToReferrer + lastMessage;
		
		subject = "Rewards Claim Request";
		
		if (refList != null)
		emailSent = EmailUtil.sendWithHTMLFormat(
		"noreply@webtogo.com.ph", refList.get(0)
		.getReferredBy().getEmail(), subject,
		messageToReferrer, null);
		
		//System.out.println("\n\n\n\n\n content: " + content + " \n\n\n\n\n ");
		}
		
		return emailSent;
	}
	
	private final String eventEmailContentFromLife = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
			"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
			"<head>"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
			"<title>Untitled Document</title>"+
			"</head>"+
			"<body>"+
				"<div style=\"width:600px; height: 363px; background: url(http://www.lifeyogacenter.com/images/LifeEventpaceBg.jpg) no-repeat; font-family:Arial, Helvetica, sans-serif; font-size: 12px\">"+
					"<div style=\"position: relative; top: 164px;\">"+
						"<div style=\"height:164px\"></div>"+
						"<div style=\"background: #fff; width: 600px; padding: 1px 0; opacity: 0.9\">"+
							"<ul style=\"list-style:none; font-size: 11px; letter-spacing: 1px; font-weight: 800;color: #444444;\">"+
								"<li style=\"margin: 4px 0\">EVENT: --NAME OF EVENT-- </li>"+
								"<li style=\"margin: 4px 0\">DATE/TIME: --SCHEDULE-- </li>"+
								"<li style=\"margin: 4px 0\">PLACE: --PLACE-- </li>"+
							"</ul>"+
						"</div>"+
						"<div style=\"background: #d8d8d8;color: #1e788e;width: 517px; margin: 0 auto; font-size: 8px; padding: 6px; text-align: center; text-transform: uppercase;font-weight: 800;letter-spacing: 1px; position: relative;top: -6px;\">"+
							"Any comments or questions drop us a line at events@lifeyogacenter.com"+
						"</div>"+
						"<p style=\"font-size: 8px; color: #fff; text-align: center; width: 528px; margin: 5px auto; letter-spacing: 1px; line-height: 12px\">"+
							"Note: First-timers will be briefed 15 minutes before class begins. If you need to cancel your booking, please do so at least two (2) days before the event. You can cancel online or by calling the studio."+
						"</p>"+
					"</div>"+
					"<div style=\"position: relative; top: 166px; width: 600px; text-align: center;\">"+
						"<a href=\"/\"><img src=\"http://www.lifeyogacenter.com/images/igLink.png\" /></a>"+
						"<a href=\"/\"><img src=\"http://www.lifeyogacenter.com/images/fbLink.png\" /></a>"+
					"</div>"+
				"</div>"+
			"</body>"+
			"</html>";
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getAcknowledgementContent() {
		return acknowledgementContent;
	}
	public void setAcknowledgementContent(String acknowledgementContent) {
		this.acknowledgementContent = acknowledgementContent;
	}
	public Boolean getIsForAcknowledgement() {
		return isForAcknowledgement;
	}
	public void setIsForAcknowledgement(Boolean isForAcknowledgement) {
		this.isForAcknowledgement = isForAcknowledgement;
	}
	public String getResrId(){
		return resrId;
	}
	public void setResrId(){
		this.resrId = resrId;
	}
	public String getConfirmationNum(){
		return confirmationNum;
	}
	public void setConfirmationNum(){
		this.confirmationNum = confirmationNum;
	}
	
	public Company getCompany(){
		return company;
	}
	public String getSmtp(){
		return smtp;
	}
	public String getPortNumber(){
		return portNumber;
	}
	
}
