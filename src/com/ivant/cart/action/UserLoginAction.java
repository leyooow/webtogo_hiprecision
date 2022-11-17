package com.ivant.cart.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.ItemCommentDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberLogDelegate;
import com.ivant.cms.delegate.MemberPollDelegate;
import com.ivant.cms.delegate.MemberShippingInfoDelegate;
import com.ivant.cms.delegate.MicePhilippinesMemeberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.delegate.WishlistTypeDelegate;
import com.ivant.cms.entity.AplicMember;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.ItemComment;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.MemberPoll;
import com.ivant.cms.entity.MemberShippingInfo;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MicePhilippinesMember;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.ShippingInfo;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.WishlistType;
import com.ivant.cms.enums.AccountType;
import com.ivant.cms.enums.ReferralEnum;
import com.ivant.cms.enums.SavedEmailType;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.StringUtil;
import com.opensymphony.xwork2.Action;
/*import com.ivant.cms.entity.WishlistType;*/

/**
 * Responsible for user authentication.
 * 
 * @author Mark Kenneth M. Ra�osa
 */
public class UserLoginAction extends AbstractBaseAction {
	CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();

	public SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	private CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	
	private WishlistTypeDelegate wishlistTypeDelegate = WishlistTypeDelegate.getInstance();

	private final Logger logger = Logger.getLogger(UserLoginAction.class);
	private static final String INACTIVE = "inactive";
	private static final long serialVersionUID = -8596655179935716737L;

	private String myEmail;

	private static final List<String> ALLOWED_PAGES;

	static {
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}

	private final MultiPageDelegate multiPageDelegate = MultiPageDelegate
			.getInstance();
	private final SinglePageDelegate singlePageDelegate = SinglePageDelegate
			.getInstance();
	private final FormPageDelegate formPageDelegate = FormPageDelegate
			.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final MemberLogDelegate memberLogDelegate = MemberLogDelegate
			.getInstance();
	private final MemberPollDelegate memberPollDelegate = MemberPollDelegate
			.getInstance();

	private final CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate
			.getInstance();
	private final ItemCommentDelegate itemCommentDelegate = ItemCommentDelegate
			.getInstance();

	private CompanySettings companySettings;

	/** Object responsible for member shipping info CRUD tasks */
	private AplicMember aplicMember;
	private final MicePhilippinesMemeberDelegate micePhilippinesMemeberDelegate = MicePhilippinesMemeberDelegate
			.getInstance();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MemberShippingInfo memberShippingInfo;
	private final MemberShippingInfoDelegate memberShippingInfoDelegate = MemberShippingInfoDelegate
			.getInstance();
	private ShippingInfo shippingInfo;

	private MicePhilippinesMember micePhilippinesMember;

	/** Utility for encrypting and decrypting password */
	private PasswordEncryptor encryptor;

	/** Utility for generating a random password */
	private StringUtil passwordGenerator;

	/** Account name, must not be null */
	private String username;

	/** Account password, must not be null */
	private String password;

	/** Member account email address, must not be null */
	private String email;

	/** Member account given first name, must not be null */
	private String firstname;

	/** Member account paternal name, must not be null */
	private String lastname;

	/** Member account subscription for newsletter, must not be null */
	private Boolean newsletter;

	// Mice Philippines

	private String companyName;
	private String companyAddress;
	private String accompanyingPerson;
	private String telephoneNumbers;

	// Aplic
	private String salutation;
	private String designation;
	private String insuranceCompany;
	private String country;
	private String noOfYears;
	private String birthdate;
	private String mailingAddress;
	private String officeTel;
	private String fax;
	private String mobileNo;

	// Clickbox
	private String address1;
	private String address2;
	private String city;
	private String region;
	private String province;
	private String landline;

	private String pmcMemberPass;
	private String promoCode;
	private String billingNumber;
	private String reg_companyAddress;
	private String reg_companyName;
	private String zipcode;

	// Ecommerce
	private String referredByName;
	private String referredByNumber;

	private String smtp;
	private String portNumber;
	private String mailerUserName;
	private String mailerPassword;

	// Other Member fields
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	private String value;
	private String value2;
	private String value3;

	private String successUrl;
	private String errorUrl;

	// pocketpons
	private String lastName;
	private String firstName;
	private String mobile;
	private Long memberId;
	private String oldPassword;
	private String notificationMessage;
	
	//
	private String pageModule;
	private String pageModuleUsername;

	private InputStream inputStream;

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {

		logger.debug("LOGGING IN...");
		logger.debug("COMPANY: " + company);

		successUrl = (request.getParameter("successUrl") != null ? request
				.getParameter("successUrl") : "");
		setErrorUrl((request.getParameter("errorUrl") != null ? request
				.getParameter("errorUrl") : ""));

		if (!isNull(username) && !isNull(password)) {
			// get user account
			encryptor = new PasswordEncryptor();
			logger.info("username " + username);
			logger.info("password " + password);
			logger.info("company " + company);

			member = memberDelegate.findAccount(username,
					encryptor.encrypt(password), company);
			
			if(pageModule != null)
				if(company.getName().equalsIgnoreCase(CompanyConstants.MUNDIPHARMA2)) {
					member = memberDelegate.findByPageModuleAndPagemoduleUsername(pageModule, pageModuleUsername,
							encryptor.encrypt(password), company);
				}
			
			logger.info("ENCRYPTED PASSWORD: " + encryptor.encrypt(password));
			logger.info("MEMBER " + member);

			if (!isNull(member)) {

				if (member.getCompany().getId() != company.getId()) {

					this.setNotificationMessage("Invalid Username.");
					request.setAttribute("errorMessage",
							"Invalid Username / Password");

					return ERROR;
				}

				if (member.getActivated() == false) {

					logger.debug(username);

					return INACTIVE;
				}

				if (member.getVerified() == false
						&& CompanyConstants.SILVERFINANCE == company.getId()) {
					this.setNotificationMessage("Account is not yet verified by the admin");
					request.setAttribute("errorMessage",
							"Account is not yet verified by the admin");
					return ERROR;
				}

				member.setLastLogin(new Date());


				memberDelegate.update(member);
				session.put(MemberInterceptor.MEMBER_SESSION_KEY,
						member.getId());

				// add user to session
				session.put(UserInterceptor.USER_SESSION_KEY, member.getId());
				//if(company.getId() != CompanyConstants.GURKKA_TEST){
					request.setAttribute("member", member);
					session.put("member", member);
					try{
						session.put("memberFullGivenName", (member.getFirstname() + " " + member.getLastname()).trim());  
						session.put("memberFullCompanyName", (member.getReg_companyName()).trim()); 
					}catch(Exception e){}
					
					logger.info("This company is not gurkkatest");
				//}
				//else{
				//	logger.info("This company is for gurkkatest");
				//}
					
				// log the member
				final MemberLog memberLog = new MemberLog();
				memberLog.setCompany(company);
				memberLog.setMember(member);
				memberLog.setRemarks("Logged In");
				memberLogDelegate.insert(memberLog);

				if (company.getName().equals("clickbox")) {
					memberShippingInfo = memberShippingInfoDelegate.find(
							company, member);
					session.put("memberShippingInfo", memberShippingInfo);
				}


				return SUCCESS;
			} else {
				this.setNotificationMessage("Invalid Username / Password");
				request.setAttribute("errorMessage",
						"Invalid Username / Password");

				return ERROR;
			}
		}

		if (isValidUsernameInput(username) || isValidPasswordInput(password)) {
			addActionError("Invalid Username / Password");
		}

		return INPUT;
	}

	@Override
	public void prepare() throws Exception {
		companySettings = company.getCompanySettings();
		// initialize login values

		initLoginInformation();
		loadAllRootCategories();
		// populate server URL to be redirected to
		initHttpServerUrl();

		loadFeaturedPages(companySettings.getMaxFeaturedPages());
		loadFeaturedSinglePages(companySettings.getMaxFeaturedSinglePages());

		loadMenu();
	}

	/**
	 * Returns {@code SUCCESS} when action is called.
	 * 
	 * @return - {@code SUCCESS} when action is called.
	 */
	public String newuser() {
		// do nothing

		return SUCCESS;
	}

	private void loadAllRootCategories() {
		final Order[] orders = { Order.asc("sortOrder") };
		final List<Category> rootCategories = categoryDelegate
				.findAllRootCategories(company, true, orders).getList();
		request.setAttribute("rootCategories", rootCategories);
	}

	/**
	 * Registers a new member. Returns SUCCESS if new member successfully
	 * registers, otherwise ERROR.
	 * 
	 * @return - SUCCESS if new member successfully registers, otherwise ERROR
	 */
	public String register() {

		// get user information specified information
		initMemberInformation();

		// resend activation email
		if (!isMemberNew()
				&& company.getName().equalsIgnoreCase("pinoyhomecoming")
				&& userNotActivated()) {
			return "inactive";
		}

		if (!isMemberNew()
				&& (company.getName().equalsIgnoreCase("gurkka") || company
						.getId() == CompanyConstants.GURKKA_TEST)
				&& userNotActivated()) {
			return "inactive";
		}

		// new members can only be added
		if (!isMemberNew()) {
			return ERROR;
		}
		// populate new member information
		initializeMemberInformation();

		initializeOtherInfo();

		// kaptcha
		final String kaptchaReceived = request.getParameter("kaptcha");
		if (kaptchaReceived != null) {
			final String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				addActionError("Invalid characters in image.");
				return ERROR;
			}
		}
		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			return ERROR;
		}

		logger.info("username: " + username + " | company: "
				+ company.getName());
		final Member member = memberDelegate.findAccount(username, company);

		if (company.getName().equalsIgnoreCase("pinoyhomecoming")) {
			sendPinoyHomecomingEmail(member);
		} else {
			sendEmail(member);
		}

		if (company.getName().equalsIgnoreCase(CompanyConstants.AYROSOHARDWARE)) {
			sendAyrosoHardwareEmail(member);
		} else {
			sendEmailToCompanyAccount(member);
		}

		return SUCCESS;
	}

	public String register_politiker() {
		successUrl = (request.getParameter("successUrl") != null ? request
				.getParameter("successUrl") : "");
		setErrorUrl((request.getParameter("errorUrl") != null ? request
				.getParameter("errorUrl") : ""));

		// get user information specified information
		initMemberInformation();

		// resend activation email

		// new members can only be added
		if (!isMemberNew()) {
			this.setNotificationMessage("Username already exists.");
			request.setAttribute("errorMessage", "Username already exists.");
			return ERROR;
		}
		// populate new member information
		initializeMemberInformation();

		initializeOtherInfo();

		// kaptcha
		final String kaptchaReceived = request.getParameter("kaptcha");
		if (kaptchaReceived != null) {
			final String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				notificationMessage = "Invalid characters in image.";
				addActionError("Invalid characters in image.");
				return ERROR;
			}
		}
		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			return ERROR;
		}

		logger.info("username: " + username + " | company: "
				+ company.getName());
		final Member member = memberDelegate.findAccount(username, company);

		if (company.getName().equalsIgnoreCase("pinoyhomecoming")) {
			sendPinoyHomecomingEmail(member);
		} else {
			sendEmail(member);
		}
		sendEmailToCompanyAccount(member);

		return SUCCESS;
	}
	
	

	public String registerAplic() {

		// get user information specified information
		initAplicMemberInformation();

		// new members can only be added
		if (!isMemberNew()) {
			return ERROR;
		}

		// populate new member information
		initializeAplicMemberInformation();

		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			return ERROR;
		}

		aplicMember.setMemberID(member.getId().toString());


		username = member.getUsername();

		// send user registration notification through mail
		final Member member = memberDelegate.findAccount(username, company);

		sendAplicEmail(member);
		sendAplicEmailToCompanyAccount(member);
		return SUCCESS;
	}

	public String registerMicePhilippines() {
		String kaptchaReceived = (String) request.getParameter("kaptcha");
		if (kaptchaReceived != null) {
			String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				addActionError("Invalid characters in image.");
				return ERROR;
			}
		} else {
			addActionError("Invalid characters in image.");
			return ERROR;
		}

		// get user information specified information
		initMicePhilippinesMemberInformation();

		// new members can only be added
		if (!isMemberNew()) {
			return ERROR;
		}

		// populate new member information
		initializeMicePhilippinesMemberInformation();

		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			return ERROR;
		}

		micePhilippinesMember.setMemberID(member.getId().toString());

		if (isNull(micePhilippinesMemeberDelegate.insert(micePhilippinesMember))) {
			return ERROR;
		}

		username = member.getUsername();

		// send user registration notification through mail
		final Member member = memberDelegate.findAccount(username, company);

		sendMicePhilippinesEmail(member);
		sendMicePhilippinesEmailToCompanyAccount(member);
		return SUCCESS;
	}

	public String registerClickbox() {
		// get user information specified information
		initClickboxMemberInformation();

		// new members can only be added
		if (!isMemberNew()) {
			return ERROR;
		}

		// populate new member information
		initializeClickboxMemberInformation();

		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			return ERROR;
		}

		if (isNull(memberShippingInfoDelegate.insert(memberShippingInfo))) {
			return ERROR;
		}

		username = member.getUsername();

		// send user registration notification through mail
		final Member member = memberDelegate.findAccount(username, company);

		sendEmail(member);
		sendEmailToCompanyAccount(member);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String registerBlueLiveToFeel() {
		try {
			
			JSONObject obj = new JSONObject();
			obj.put("success", false);
			initMemberInformation();
			
			// new members can only be added
			if (!isMemberNew()) {
				obj.put("message", notificationMessage);
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			} else {
				// populate new member information
				initializeMemberInformation();
	
				initializeOtherInfo();
	
				// insert new user
				if (isNull(memberDelegate.insert(member))) {
					obj.put("message", "An error was occured. Please review your registration details.");
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				} else {
					obj.put("success", true);
					final Member member = memberDelegate.findAccount(username, company);
					
					sendEmail(member);
		
					sendEmailToCompanyAccount(member);
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				}
	
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String registerByAjax() {
		try {
			
			JSONObject obj = new JSONObject();
			obj.put("success", false);
			initMemberInformation();
			
			// new members can only be added
			if (!isMemberNew()) {
				obj.put("message", notificationMessage);
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			} else {
				// populate new member information
				initializeMemberInformation();
	
				initializeOtherInfo();
	
				// insert new user
				if (isNull(memberDelegate.insert(member))) {
					obj.put("message", "An error was occured. Please review your registration details.");
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				} else {
					obj.put("success", true);
					final Member member = memberDelegate.findAccount(username, company);
					
					sendEmail(member);
		
					sendEmailToCompanyAccount(member);
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				}
	
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String editBlueLivetoFeel() {
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		address1 = request.getParameter("address1");
		mobileNo = request.getParameter("mobile"); 
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setAddress1(address1);
		member.setMobile(mobileNo);
		if (isNull(memberDelegate.update(member))) {
			addActionError("Error encountered.");
			return ERROR;
		}
		addActionMessage("Edit account successful.");
		return SUCCESS;
	}

	public String editClickbox() {
		// get user information specified information
		initClickboxMemberInformation();

		// populate member information
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);
		memberShippingInfo = memberShippingInfoDelegate.find(company, member);
		shippingInfo = memberShippingInfo.getShippingInfo();

		// populate new member information
		member.setEmail(email);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setLandline(landline);
		member.setMobile(mobileNo);

		shippingInfo.setAddress1(address1);
		shippingInfo.setAddress2(address2);
		shippingInfo.setCity(city);
		shippingInfo.setRegion(region);
		shippingInfo.setProvince(province);

		memberShippingInfo.setShippingInfo(shippingInfo);

		session.put("member", member);
		session.put("memberShippingInfo", memberShippingInfo);

		// update user
		if (isNull(memberDelegate.update(member))) {
			addActionError("Error encountered.");
			return ERROR;
		}

		// update shipping info
		if (isNull(memberShippingInfoDelegate.update(memberShippingInfo))) {
			addActionError("Error encountered.");
			return ERROR;
		}

		addActionMessage("Edit account successful.");

		return SUCCESS;
	}

	
	public String checkLoginAGIAN(){
		logger.debug("LOGGING IN...");
		logger.debug("COMPANY: " + company);

		
		String result = "";
		successUrl = (request.getParameter("successUrl") != null ? request
				.getParameter("successUrl") : "");
		setErrorUrl((request.getParameter("errorUrl") != null ? request
				.getParameter("errorUrl") : ""));

		if (!isNull(email) && !isNull(password)) {
			// get user account
			encryptor = new PasswordEncryptor();
			logger.info("email " + email);
			logger.info("password " + password);
			logger.info("company " + company);

			member = memberDelegate.findAccountByEmail(email, encryptor.encrypt(password), company);
			
			if(pageModule != null)
				if(company.getName().equalsIgnoreCase(CompanyConstants.MUNDIPHARMA2)) {
					member = memberDelegate.findByPageModuleAndPagemoduleUsername(pageModule, pageModuleUsername,
							encryptor.encrypt(password), company);
				}
			
			logger.info("ENCRYPTED PASSWORD: " + encryptor.encrypt(password));
			logger.info("MEMBER " + member);

			if (!isNull(member)) {

				if (isNull(member.getLastLogin()) && isNull(request.getParameter("accepted"))){
					result = "FIRST";
					
				}else{
					if(!member.getVerified()){
						member.setVerified(Boolean.TRUE);
						final MemberLog memberLog = new MemberLog();
						memberLog.setCompany(company);
						memberLog.setMember(member);
						memberLog.setRemarks("Verified Account");
						memberLogDelegate.insert(memberLog);
					}
					result = "SUCCESS";
					member.setLastLogin(new Date());

					memberDelegate.update(member);
					
					
					if(!isNull(request.getParameter("accepted")) && company.getName().equalsIgnoreCase("agian")){
						StringBuffer content = new StringBuffer();
						
						content.append("Hi " + member.getFirstname() );
						content.append("<br/><br/>");
						content.append("Your email address("+ member.getEmail() + ") was used to sign in to AGIAN Web Portal.");
						content.append("<br/><br/>");
						
						content.append("Date and Time: " + member.getLastLogin());
						content.append("<br/>");
						content.append("--------------------------------------------------------------------------");
						content.append("<br/>");
						
						content.append("To reset your password, just log in to your account and reset your password under Profile page.");
						content.append("<br/><br/>");
						
						content.append("Thank you!");
					
						setEmailSettings();
						EmailUtil.connect(smtp, Integer.parseInt(portNumber), mailerUserName, mailerPassword);
						EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), "AGIAN WEb Portal - Initial Login", content.toString(), null);
					}
					
					session.put(MemberInterceptor.MEMBER_SESSION_KEY,
							member.getId());

					// add user to session
					session.put(UserInterceptor.USER_SESSION_KEY, member.getId());
					//if(company.getId() != CompanyConstants.GURKKA_TEST){
						request.setAttribute("member", member);
						session.put("member", member);
						try{
							session.put("memberFullGivenName", (member.getFirstname() + " " + member.getLastname()).trim());  
							session.put("memberFullCompanyName", (member.getReg_companyName()).trim()); 
						}catch(Exception e){}
						
						logger.info("This company is not gurkkatest");
					//}
					//else{
					//	logger.info("This company is for gurkkatest");
					//}
						
					// log the member
					final MemberLog memberLog = new MemberLog();
					memberLog.setCompany(company);
					memberLog.setMember(member);
					memberLog.setRemarks("Logged In");
					memberLogDelegate.insert(memberLog);
				}
	
				
			} else {
				result = "FAILED";
				this.setNotificationMessage("Invalid Email / Password");
				request.setAttribute("errorMessage",
						"Invalid Email / Password");
			}
		}

		if (isValidUsernameInput(username) || isValidPasswordInput(password)) {
			addActionError("Invalid Email / Password");
		}

		setInputStream(new ByteArrayInputStream(result.getBytes()));
		
		return SUCCESS;
		
	}
	
	public String editDrugasia() {
		initDrugasiaMemberInformation();
		// populate member information
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);

		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setEmail(email);
		member.setMobile(mobileNo);
		member.setLandline(landline);
		member.setFax(fax);
		member.setAddress1(address1);
		member.setAddress2(address2);
		member.setProvince(province);
		member.setCity(city);
		member.setZipcode(zipcode);

		// update user
		if (isNull(memberDelegate.update(member))) {
			notificationMessage = "Error encountered.";
			return ERROR;
		}

		notificationMessage = "Edit account successful.";

		return SUCCESS;
	}

	public String editGiftCard() {
		initGiftCardMemberInformation();
		// populate member information
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);

		member.setAddress1(address1);
		member.setAddress2(address2);
		member.setCity(city);
		member.setZipcode(zipcode);

		// update user
		if (isNull(memberDelegate.update(member))) {
			addActionError("Error encountered.");
			return ERROR;
		}

		addActionMessage("Edit account successful.");

		return SUCCESS;
	}

	public String editGreenfield() {
		initGreenfieldMemberInformation();
		// populate member information
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);

		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setMobile(mobileNo);
		member.setEmail(email);
		member.setAddress1(address1);

		// update user
		if (isNull(memberDelegate.update(member))) {
			notificationMessage = "Error encountered.";
			return ERROR;
		}

		notificationMessage = "Edit account successful.";

		return SUCCESS;
	}

	public String editOnlineDepot() {
		initDrugasiaMemberInformation();
		// populate member information
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);

		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setEmail(email);
		member.setMobile(mobileNo);
		member.setLandline(landline);
		member.setFax(fax);
		member.setAddress1(address1);
		member.setAddress2(address2);
		member.setProvince(province);
		member.setCity(city);
		member.setZipcode(zipcode);

		// update user
		if (isNull(memberDelegate.update(member))) {
			notificationMessage = "Error encountered.";
			return ERROR;
		}

		notificationMessage = "Edit account successful.";

		return SUCCESS;
	}

	public String editPolitiker() {
		initPolitikerMemberInformation();
		// populate member information
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);
		System.out.println(member);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setEmail(email);
		member.setMobile(mobileNo);
		member.setLandline(landline);
		member.setFax(fax);
		member.setAddress1(address1);
		member.setAddress2(address2);
		member.setProvince(province);
		member.setCity(city);
		member.setZipcode(zipcode);

		member.setValue(request.getParameter("value"));// gender
		member.setValue2(request.getParameter("value2"));// is registered voter
		member.setInfo1(request.getParameter("info1"));// language
		member.setInfo2(request.getParameter("info2"));// occupation
		member.setInfo3(request.getParameter("info3"));// educational attainment
		// update user
		if (isNull(memberDelegate.update(member))) {
			notificationMessage = "Error encountered.";
			return ERROR;
		}

		notificationMessage = "Changes on Profile successfully saved.";

		return SUCCESS;
	}
	
	public String editSpcmc() {
		initPolitikerMemberInformation();
		// populate member information
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);
		System.out.println(member);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setEmail(email);
		member.setMobile(mobileNo);
		member.setLandline(landline);
		member.setFax(fax);
		member.setAddress1(address1);
		member.setAddress2(address2);
		member.setProvince(province);
		member.setCity(city);
		member.setZipcode(zipcode);

		member.setValue(request.getParameter("value"));// gender
		//member.setValue2(request.getParameter("value2"));// is registered voter
		member.setInfo1(request.getParameter("info1"));// language
		member.setInfo2(request.getParameter("info2"));// occupation
		member.setInfo3(request.getParameter("info3"));// educational attainment
		// update user
		if (isNull(memberDelegate.update(member))) {
			notificationMessage = "Error encountered.";
			return ERROR;
		}
		try{
			//member = memberDelegate.find(member.getId());
			//request.setAttribute("member", member);
			session.put("member", member);
		}catch(Exception e){
			
		}
		notificationMessage = "Changes on Profile successfully saved.";

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String loginGurkka() throws Exception, IOException {
		try {
			//session.put("overEighteen", true);
			final HttpSession session = request.getSession(true);
			// notificationMessage =
			// "You are not allowed to enter this site.";

			session.setAttribute("overEighteen", true);
			JSONArray objList = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			String resultExecution = execute();
			if (resultExecution.equals(SUCCESS)) {

				if (member != null) {
					obj.put("success", true);
					obj.put("member_id_", String.valueOf(member.getId()));
					objList.add(obj);
					obj2.put("listLoginDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));

					notificationMessage = "Successfully log in.";
					//set wishlist type to session
					
					List<WishlistType> wishlistType = new ArrayList<WishlistType>();
					wishlistType = wishlistTypeDelegate.findAll(company, member).getList();
					HttpSession sessionwishlist = request.getSession(true);
					
				} else {
					obj.put("errorMessage", "Invalid Username / Password");
					objList.add(obj);
					obj2.put("listLoginDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));

					System.out.println(obj2.toJSONString());
					notificationMessage = "Invalid Username / Password.";
				}
			} else if (resultExecution.equals(INACTIVE)) {
				obj.put("errorMessage", "This account is not yet activated.");
				objList.add(obj);
				obj2.put("listLoginDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));

				notificationMessage = "An error was occured while processing your log in credentials.";
			} else if (resultExecution.equals(ERROR)) {
				obj.put("errorMessage", notificationMessage);
				objList.add(obj);
				obj2.put("listLoginDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));

				notificationMessage = "An error was occured while processing your log in credentials.";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String updateGurkkaSecurityInformation() throws Exception,
			IOException {
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		initGurkkaSecurityInformation();
		try {
			final Long id = Long.parseLong(request.getParameter("id"));
			member = memberDelegate.find(id);
			member.setInfo1(info1);
			if (isNull(memberDelegate.update(member))) {
				notificationMessage = "Error encountered.";

				return ERROR;
			}
			obj.put("success", true);
			objList.add(obj);
			obj2.put("listSecurityInfoDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString()
					.getBytes()));
			notificationMessage = "Changes on Security Q & A successfully saved.";

		} catch (Exception e) {
			e.printStackTrace();
			notificationMessage = "Error Encountered.";
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String updateGurkkaAccountInformation() throws Exception,
			IOException {
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();

		String welcomeGreetings = "";
		try {
			encryptor = new PasswordEncryptor();
			String tempPassword = "";
			final Long id = Long.parseLong(request.getParameter("id"));
			member = memberDelegate.find(id);

			if (request.getParameter("accountType").equals("corporate")) {
				initGurkkaAccountInformation(AccountType.CORPORATE);
				welcomeGreetings = "Welcome " + reg_companyName;
			} else {
				initGurkkaAccountInformation(AccountType.PERSONAL);
				welcomeGreetings = "Welcome " + firstname + " " + lastname
						+ ",";
			}
			tempPassword = member.getPassword();

			member.setFirstname(firstname);
			member.setLastname(lastname);
			member.setInfo2(info2);
			member.setInfo3(info3);
			member.setReg_companyName(reg_companyName);
			if (request.getParameter("changeYourPassword").equals("Yes")) {
				if (tempPassword.equals(encryptor.encrypt(request
						.getParameter("currentPassword")))) {
					member.setPassword(encryptor.encrypt(request
							.getParameter("newPassword")));
				} else {
					notificationMessage = "Invalid current password.";

					obj.put("errorMessage", "Invalid current password.");

					objList.add(obj);
					obj2.put("listAccountInfoDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));

					return SUCCESS;
				}
			}

			if (isNull(memberDelegate.update(member))) {
				notificationMessage = "Error. Account Information was not saved!";
				return ERROR;
			} else {
				notificationMessage = "Account Information was successfully saved!";
			}

			obj.put("success", true);
			obj.put("welcomeGreetings", welcomeGreetings);
			objList.add(obj);
			obj2.put("listAccountInfoDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString()
					.getBytes()));

			notificationMessage = "Edit account successful.";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String updateGurkkaProfileInformation() throws Exception,
			IOException {
		try {
			String welcomeGreetings = "";
			initGurkkaProfileInformation();
			// populate member information

			final Long id = Long.parseLong(request.getParameter("id"));
			member = memberDelegate.find(id);

			if (!StringUtils.isEmpty(firstname)) {
				member.setFirstname(firstname);
			}

			if (!StringUtils.isEmpty(lastname)) {
				member.setLastname(lastname);
			}

			if (!StringUtils.isEmpty(username)) {
				member.setUsername(username);
			}

			if (!StringUtils.isEmpty(email)) {
				member.setEmail(email);
			}

			if (!StringUtils.isEmpty(mobileNo)) {
				member.setMobile(mobileNo);
			}

			if (!StringUtils.isEmpty(fax)) {
				member.setFax(fax);
			}

			if (!StringUtils.isEmpty(address1)) {
				member.setAddress1(address1);
			}

			if (!StringUtils.isEmpty(province)) {
				member.setProvince(province);
			}

			if (!StringUtils.isEmpty(city)) {
				member.setCity(city);
			}

			if (!StringUtils.isEmpty(zipcode)) {
				member.setZipcode(zipcode);
			}

			if (!StringUtils.isEmpty(landline)) {
				member.setLandline(landline);
			}

			if (!StringUtils.isEmpty(info1)) {
				member.setInfo1(info1);
			}

			if (!StringUtils.isEmpty(info2)) {
				member.setInfo2(info2);
			}

			if (!StringUtils.isEmpty(info3)) {
				member.setInfo3(info3);
			}

			if (!StringUtils.isEmpty(reg_companyName)) {
				member.setReg_companyName(reg_companyName);
				member.setFirstname(reg_companyName);
			}

			if (!StringUtils.isEmpty(reg_companyAddress)) {
				member.setReg_companyAddress(reg_companyAddress);
				member.setAddress1(reg_companyAddress);
			}

			// update user
			if (isNull(memberDelegate.update(member))) {
				notificationMessage = "Error encountered.";
				return ERROR;
			}

			if (reg_companyName.length() > 0) {
				welcomeGreetings = "Welcome " + reg_companyName;
			} else {
				welcomeGreetings = "Welcome " + firstname + " " + lastname
						+ ",";
			}

			JSONObject obj2 = new JSONObject();
			JSONArray objList = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.put("success", true);
			obj.put("welcomeGreetings", welcomeGreetings);
			objList.add(obj);
			obj2.put("listProfileInfoDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString()
					.getBytes()));

			notificationMessage = "Edit profile successful.";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String editGurkka() {
		initGurkkaMemberInformation();
		// populate member information
		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);

		if (!StringUtils.isEmpty(firstname)) {
			member.setFirstname(firstname);
		}

		if (!StringUtils.isEmpty(lastname)) {
			member.setLastname(lastname);
		}

		if (!StringUtils.isEmpty(username)) {
			member.setUsername(username);
		}

		if (!StringUtils.isEmpty(email)) {
			member.setEmail(email);
		}

		if (!StringUtils.isEmpty(mobileNo)) {
			member.setMobile(mobileNo);
		}

		if (!StringUtils.isEmpty(fax)) {
			member.setFax(fax);
		}

		if (!StringUtils.isEmpty(address1)) {
			member.setAddress1(address1);
		}

		if (!StringUtils.isEmpty(province)) {
			member.setProvince(province);
		}

		if (!StringUtils.isEmpty(city)) {
			member.setCity(city);
		}

		if (!StringUtils.isEmpty(zipcode)) {
			member.setZipcode(zipcode);
		}

		if (!StringUtils.isEmpty(landline)) {
			member.setLandline(landline);
		}

		if (!StringUtils.isEmpty(info1)) {
			member.setInfo1(info1);
		}

		if (!StringUtils.isEmpty(info2)) {
			member.setInfo2(info2);
		}

		if (!StringUtils.isEmpty(info3)) {
			member.setInfo3(info3);
		}

		if (!StringUtils.isEmpty(reg_companyName)) {
			member.setReg_companyName(reg_companyName);
		}

		if (!StringUtils.isEmpty(reg_companyAddress)) {
			member.setReg_companyAddress(reg_companyAddress);
		}

		// update user
		if (isNull(memberDelegate.update(member))) {
			notificationMessage = "Error encountered.";
			return ERROR;
		}

		notificationMessage = "Edit account successful.";

		return SUCCESS;
	}

	public String registerGiftCard() {
		initGiftCardMemberInformation();

		// new members can only be added
		if (!isMemberNew()) {
			return ERROR;
		}

		// populate new member information
		initializeGiftCardMemberInformation();

		// kaptcha
		final String kaptchaReceived = request.getParameter("kaptcha");
		if (kaptchaReceived != null) {
			final String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				addActionError("Invalid characters in image.");
				return ERROR;
			}
		}

		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			return ERROR;
		}

		username = member.getUsername();

		// send user registration notification through mail
		final Member member = memberDelegate.findAccount(username, company);

		sendEmail(member);
		sendEmailToCompanyAccount(member);
		return SUCCESS;
	}

	public String registerGreenfield() {
		initGreenfieldMemberInformation();

		// new members can only be added
		if (!isMemberNew()) {
			return ERROR;
		}

		// populate new member information
		initializeGreenfieldMemberInformation();

		// kaptcha
		final String kaptchaReceived = request.getParameter("kaptcha");
		if (kaptchaReceived != null) {
			final String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				addActionError("Invalid characters in image.");
				return ERROR;
			}
		}

		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			return ERROR;
		}

		username = member.getUsername();

		// send user registration notification through mail
		final Member member = memberDelegate.findAccount(username, company);

		sendEmail(member);
		sendEmailToCompanyAccount(member);
		return SUCCESS;
	}

	public String registerOnlineDepot() throws Exception {
		initMemberInformation();

		mobileNo = request.getParameter("mobile");
		landline = request.getParameter("phone");
		address1 = request.getParameter("address1");
		zipcode = request.getParameter("zipcode");

		// new members can only be added
		if (!isMemberNew()) {
			return ERROR;
		}

		member = new Member();

		// populate new member information
		member.setDateJoined(new Date());
		member.setUsername(username);
		member.setEmail(email);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setCompany(company);
		member.setMobile(mobileNo);
		member.setLandline(landline);
		member.setAddress1(address1);
		member.setZipcode(zipcode);
		member.setNewsletter(newsletter == null ? false : true);

		// encrypt given password
		encryptor = new PasswordEncryptor();
		member.setPassword(encryptor.encrypt(password));
		logger.info("SAVED ENCRYPTED PASSWORD: " + encryptor.encrypt(password));

		// initialize activation key
		member.setActivationKey(Encryption.hash(company + email + username
				+ password));
		member.setActivated(false);

		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			return ERROR;
		}

		return SUCCESS;
	}

	public String registerGurkka() {
		if (!Boolean.parseBoolean(StringUtils.trimToEmpty(request
				.getParameter("corporateaccount")))) {
			if (verifyAge().equalsIgnoreCase("error")) {
				final HttpSession session = request.getSession(true);
				// notificationMessage =
				// "You are not allowed to enter this site.";

				session.setAttribute("overEighteen", false);
				return "warning";
			}
			else{
				
			}
		}

		if (!isMemberNew() && userNotActivated()) {
			return "inactive";
		}

		if (!isMemberNew()) {
			return ERROR;
		}

		initializeMemberInformation();
		initMemberInformation();
		initializeOtherInfo();
		initGurkkaMemberInformation();

		email = username;
		member.setEmail(username);
		request.setAttribute("email", email);

		if (!isMemberNew()) {
			return ERROR;
		}

		final String kaptchaReceived = request.getParameter("kaptcha");
		if (kaptchaReceived != null) {
			final String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				addActionError("Invalid characters in image.");
				notificationMessage = "Invalid characters in image.";
				return ERROR;
			}
		}

		// insert new user
		if (isNull(memberDelegate.insert(member))) {
			notificationMessage = "Member was not saved.";
			return ERROR;
		}
		System.out.println("### (2) Value of Address1 : " + member.getAddress1());
		username = member.getEmail();

		// send user registration notification through mail
		final Member member = memberDelegate.findAccount(username, company);

		sendEmail(member);
		sendEmailToGurkka(member);
		setMemberId(member.getId());
		//System.out.println("### Gurkka Member ID : "+member.getId());
		return SUCCESS;
	}

	/**
	 * Returns {@code SUCCESS} if member account is successfully activated,
	 * otherwise {@code ERROR}.
	 * 
	 * @return - {@code SUCCESS} if member account is successfully activated,
	 *         otherwise {@code ERROR}
	 */
	public String activate() {
		String activationKey;
		activationKey = request.getParameter("activation");
		final Member member = memberDelegate.findActivationKey(activationKey);
		if (member != null) {
			member.setActivated(true);
			memberDelegate.update(member);
			notificationMessage = "Your account is activated.";
			request.setAttribute("notificationMessage", notificationMessage);
			return Action.SUCCESS;
		}
		return Action.ERROR;
	}

	public String activateWithLogin() {
		String activationKey;
		activationKey = request.getParameter("activation");
		final Member member = memberDelegate.findActivationKey(activationKey);
		if (member != null && member.getActivated() == false) {
			member.setActivated(true);
			memberDelegate.update(member);
			notificationMessage = "Your account is activated.";

			// update user login information
			member.setLastLogin(new Date());
			memberDelegate.update(member);
			session.put(MemberInterceptor.MEMBER_SESSION_KEY, member.getId());

			// add user to session
			session.put(UserInterceptor.USER_SESSION_KEY, member.getId());
			request.setAttribute("member", member);

			// log the member
			final MemberLog memberLog = new MemberLog();
			memberLog.setCompany(company);
			memberLog.setMember(member);
			memberLog.setRemarks("Logged In");
			memberLogDelegate.insert(memberLog);

			return Action.SUCCESS;
		}
		return Action.ERROR;
	}

	/**
	 * Returns {@code SUCCESS} if mail was successfully sent to the user,
	 * otherwise {@code ERROR}.
	 * 
	 * @return - {@code SUCCESS} if mail was successfully sent to the user,
	 *         otherwise {@code ERROR}
	 */
	public String sendEmail(Member member) {
		if (member != null) {

			if (company.getId().intValue() == CompanyConstants.GURKKA
					|| company.getId().intValue() == CompanyConstants.GURKKA_TEST) {
				try {
					/*
					EmailUtil.connect(company.getCompanySettings().getSmtp(),
							Integer.parseInt(company.getCompanySettings()
									.getPortNumber()), company
									.getCompanySettings().getEmailUserName(),
							company.getCompanySettings().getEmailPassword());
					*/
					EmailUtil.connectViaGurkka();
					
				} catch (Exception e) {
					EmailUtil.connect("smtp.gmail.com", 25);
					e.printStackTrace();
				}
			} else {
				setEmailSettings();
				EmailUtil.connect(smtp, Integer.parseInt(portNumber),
						mailerUserName, mailerPassword);
				// EmailUtil.connect("smtp.gmail.com", 25);
			}

			final String emailMessage = request.getParameter("emailMessage");
			StringBuffer content = new StringBuffer("<html><body style=' padding: 20px; background: aliceblue;'><div style='border: solid #6DBEE4 1px;border-radius: 10px;padding: 20px;width: 400px;'><p><b>");
			
			String memberName = "";

			if (company.getId().intValue() == CompanyConstants.GURKKA
					|| company.getId().intValue() == CompanyConstants.GURKKA_TEST) {
				if (!StringUtils.isEmpty(member.getReg_companyName())) {
					memberName = member.getReg_companyName();
				} else {
					memberName = member.getFirstname() + " "
							+ member.getLastname();
				}
			} else {
				memberName = member.getFirstname() + " " + member.getLastname();
			}

			content.append("Hi " + memberName + ",</b><p style='margin-top: 10px;'>" + "Welcome to "
					+ company.getNameEditable() + "! </p>");
			if (emailMessage != null) {
				content.append("<p style='margin-top: 10px;'>" + emailMessage + "</p>");
			}
			
			content.append("<p style='margin-top: 10px;'>Please click on the link below to activate your account.</p><p><a href='");
			content.append(httpServer + "/activate.do?activation="
					+ member.getActivationKey());
			content.append("' target='_blank'>");
			content.append(httpServer + "/activate.do?activation="
					+ member.getActivationKey());
			
			content.append("</a></p><p style='margin-top: 10px;'>Thank you for registering.</p><p style='margin-top: 10px;'>All the Best, <br><b>");
			content.append("The " + company.getNameEditable() + " Team<b></p></div></body></html>");
			
			if(company.getName().equalsIgnoreCase(CompanyConstants.BLUE_LIVE_TO_FEEL)
					|| company.getName().equals(CompanyConstants.PURE_NECTAR)) {
				content = new StringBuffer("<html><body style=' padding: 20px; background: aliceblue;'><div style='border: solid #6DBEE4 1px;border-radius: 10px;padding: 20px;width: 400px;'><p><b>");
				content.append("Hi " + memberName + ",</b><p style='margin-top: 10px;'>" + "Welcome to "+ company.getNameEditable() + "! </p>");
				content.append("<p style='margin-top: 10px;'>Thank you for registering.</p><p style='margin-top: 10px;'>All the Best, <br><b>");
				content.append("The " + company.getNameEditable() + " Team<b></p></div></body></html>");
			}
			
			final String[] to = member.getEmail().split(" ");

			String from = "noreply@webtogo.com.ph";

			if (companySettings.getEmailUserName() != null
					&& !company.getCompanySettings().getEmailUserName()
							.equals("")) {
				from = companySettings.getEmailUserName();
			}
			if (company.getId().intValue() == CompanyConstants.GURKKA
					|| company.getId().intValue() == CompanyConstants.GURKKA_TEST) {
				final String[] bcc = { "maryann@ivant.com",
						"edgar@ivant.com", "teo@ivant.com", "info@gurkka.com", "customerservice@gurkka.com", "ariebroncano@yahoo.com" };
				
				if (EmailUtil.sendWithHTMLFormatWithBCC(from, to[0],
						"Welcome to " + company.getNameEditable() + "!",
						content.toString(), null, bcc)) {
					SavedEmail savedEmail = new SavedEmail();
					savedEmail.setCompany(company);
					savedEmail.setSender(company.getCompanySettings().getEmailUserName());
					savedEmail.setEmail(member.getEmail());
					//savedEmail.setPhone(contactContact);
					savedEmail.setFormName("Gurkka REGISTRATION");
					savedEmail.setEmailContent(content.toString());
					savedEmail.setTestimonial(StringUtils.join(bcc, ","));
					
					
					savedEmail.setSavedEmailType(SavedEmailType.REGISTRATION);
					savedEmailDelegate.insert(savedEmail);
					
					return Action.SUCCESS;
				}
				else{
					SavedEmail savedEmail = new SavedEmail();
					savedEmail.setCompany(company);
					savedEmail.setSender(company.getCompanySettings().getEmailUserName());
					savedEmail.setEmail(member.getEmail()+","+StringUtils.join(bcc, ","));
					//savedEmail.setPhone(contactContact);
					savedEmail.setFormName("Gurkka REGISTRATION");
					savedEmail.setEmailContent(content.toString());
					savedEmail.setTestimonial("");
					
					
					savedEmail.setSavedEmailType(SavedEmailType.FAILED);
					savedEmailDelegate.insert(savedEmail);
				}
			}
			else{
				if (EmailUtil.sendWithHTMLFormat(from, to,
						"Welcome to " + company.getNameEditable() + "!",
						content.toString(), null)) {
					return Action.SUCCESS;
				}
			}
		}

		return Action.ERROR;
	}

	public String sendAplicEmail(Member member) {
		if (member != null) {
			EmailUtil.connect("smtp.gmail.com", 25);

			final String emailMessage = request.getParameter("emailMessage");
			final StringBuffer content = new StringBuffer();
			content.append("Hi "
					+ member.getFirstname()
					+ " "
					+ member.getLastname()
					+ ", "
					+ "thank you for submitting your information. To complete the registration, "
					+ "please pay online via your Credit Card or via Paypal.");
			if (emailMessage != null) {
				content.append(emailMessage);
			}

			content.append("<br/><br/>\r\n\r\nSee you in APLIC 2013 in Manila!\r\n\r\n<br/><br/>");
			content.append("This is a system generated email. Do not reply. For inquiries, please refer to this link: <a href='http://aplic2013.com/contact.do'>Contact APLIC2013 Inquiries</a>.\r\n\r\n<br/><br/>");
			content.append("The " + company.getNameEditable() + " Team");
			final String[] to = member.getEmail().split(" ");

			if (EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to,
					"Welcome to " + company.getNameEditable() + "!",
					content.toString(), null)) {
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;
	}

	public String sendMicePhilippinesEmail(Member member) {
		if (member != null) {
			EmailUtil.connect("smtp.gmail.com", 587);

			final String emailMessage = request.getParameter("emailMessage");
			final StringBuffer content = new StringBuffer();
			content.append("Hi " + member.getFirstname() + " "
					+ member.getLastname() + ",<br><br> ");
			content.append("Thank you for submitting your information to us. To complete the registration, ");
			content.append("please pay online via your Credit Card or via Paypal.<br><br>");
			if (emailMessage != null) {
				content.append(emailMessage);
			}

			content.append("This is a system generated email, do not reply to this email.<br><br>");
			content.append("Thank you very much!<br><br>");
			content.append("The " + company.getNameEditable() + " Team");
			final String[] to = member.getEmail().split(" ");

			if (EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to,
					"Welcome to " + company.getNameEditable() + "!",
					content.toString(), null)) {
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;
	}

	public String sendPinoyHomecomingEmail(Member member) {
		logger.info("Send pinoy homecoming email to " + member);
		if (member != null) {
			setEmailSettings();

			logger.info("Mailer user name: " + mailerUserName);
			logger.info("Mailer password : " + mailerPassword);
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);

			request.getParameter("emailMessage");
			final StringBuffer content = new StringBuffer();

			content.append("<title>Pinoy Homecoming</title>\n");
			content.append("<div style='background: #0381b6; width: 710px; padding: 20px; font-size: 14px; font-family: Arial;	line-height: 18px; color: #000000; border-right: 1px solid #e4e4e4; border-left: 1px solid #e4e4e4;'>");
			content.append("<div style='width: 680px; margin: 0 auto 0 auto; background: #ffffff; border-radius: 10px; padding: 10px;'>");
			content.append("<table cellpadding='10' cellspacing='0'>");
			content.append("<tr>");
			content.append("<td valign='top'><img src='http://www.pinoyhomecoming.com.ph/images/logo-pinoyhomecoming.jpg' border='0' /></td>");
			content.append("<td valign='top'>");
			content.append("<span style='font: bold 18px Arial; color:#006cb0;'>");
			content.append("It feels great to be a Filipino these days!");
			content.append("</span>");
			content.append("<br /><br />");
			content.append("<span style='font: bold 16px Arial; color:#006cb0;'>");
			content.append("Now, it's time for you to reconnect and know why homecomings are more fun in the Philippines!");
			content.append("</span>");
			content.append("<div style='border-top: 2px solid #c5e6f6; margin: 10px 0 0 0; padding: 10px 0 0 0; color: #006cb0; font: normal 12px Arial;'>");
			content.append("Click The Link To Confirm Your Free Membership To<br />Pinoy Homecoming");
			content.append("</div>");
			content.append("</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td valign='top' colspan='2'>");
			content.append("<div style='background: #ec971d; color: #ffffff; font: bold 16px Arial; padding: 4px; text-align:center;'>");
			content.append("Confirm Your Email Address");
			content.append("</div>");
			content.append("<br />");
			content.append("<p align='center'>");
			content.append("In order to receive news, features, perks and privileges from <strong>PINOY HOMECOMING</strong>,<br /> we need you to confirm ");
			content.append("your email address. Please click the button below. ");
			content.append("<br /><br />");
			content.append("<a href='"
					+ httpServer
					+ "/activate.do?activation="
					+ member.getActivationKey()
					+ "'><img src='http://www.pinoyhomecoming.com.ph/images/btnConfirm.jpg' border='0' /></a>");
			content.append("</p>");
			content.append("<p align='center' style='font-size:10px;'>");
			content.append("<br /><br />");
			content.append("If you can't see the button, click the link below or copy and paste it into your browser to confirm: <br />");
			content.append("<a href='" + httpServer
					+ "/activate.do?activation=" + member.getActivationKey()
					+ "' style='color: #0097d9;'>" + httpServer
					+ "/activate.do?activation=" + member.getActivationKey()
					+ "</a>");
			content.append("</p>");
			content.append("<br />");
			content.append("<div style='background: #f3f1eb; color: #000000; font: italic 10px Arial; text-align:center; padding: 6px; border: 1px solid #d8d8d8;'>");
			content.append("Be sure to add <a href='/' style='color: #0097d9;'>askus@pinoyhomecoming.com.ph</a> to your address book or safe senders list so our emails get to your inbox. ");
			content.append("</div>");
			content.append("<br /><br />");
			content.append("<div style='border-top: 5px solid #ebebeb; padding: 8px 0; font-size:10px;'>");
			content.append("<table width='100%'>");
			content.append("<tr><td colspan='3'>If you have questions or comments, please contact us:</td></tr>");
			content.append("<tr>");
			content.append("<td><a href='http://www.facebook.com/pages/Pinoy-Homecoming/120554771343629#!/pages/Pinoy-Homecoming/120554771343629' target='_blank' style='color: #000000; text-decoration:none; font: bold 11px Arial;'>");
			content.append("<img src='http://www.pinoyhomecoming.com.ph/images/iFB2.jpg' border='0' align='absmiddle' />&nbsp;");
			content.append("facebook.com/PinoyHomecoming");
			content.append("</a></td>");
			content.append("<td><a href='http://www.twitter.com/BalikbayanPH' target='_blank' style='color: #000000; text-decoration:none; font: bold 11px Arial;'>");
			content.append("<img src='http://www.pinoyhomecoming.com.ph/images/iTwitter.jpg' border='0' align='absmiddle' />&nbsp;");
			content.append("twitter.com/BalikbayanPH");
			content.append("</a></td>");
			content.append("<td align='right'><a href='http://www.pinoyhomecoming.com.ph/' target='_blank' style='color: #000000; text-decoration:none; font: bold 11px Arial;'>");
			content.append("<img src='http://www.pinoyhomecoming.com.ph/images/iPH.png' border='0' align='absmiddle' />");
			content.append("www.pinoyhomecoming.com.ph");
			content.append("</a></td>");
			content.append("</tr>");
			content.append("</table>");
			content.append("</div>");
			content.append("</td>");
			content.append("</tr>");
			content.append("</table>");
			content.append("</div>");
			content.append("</div>");

			logger.info("Content: " + content.toString());
			final String[] to = member.getEmail().split(" ");
			for (final String sendTo : to) {
				if (EmailUtil.sendWithHTMLFormat(
						"noreply@webtogo.com.ph",
						sendTo,
						"Hello " + member.getFirstname() + " "
								+ member.getLastname() + ", welcome to "
								+ company.getNameEditable() + "!",
						content.toString(), null)) {
					logger.info("Email sent to : " + member.getEmail());

					member.setEmailNotification(Boolean.TRUE);
					member.setEmailNotificationDate(new Date());
					memberDelegate.update(member);
				}
			}

			return Action.SUCCESS;
		}

		return Action.ERROR;
	}

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
			setPortNumber("25");
		}
	}

	/**
	 * Populates new member database account information
	 */
	private void initializeMemberInformation() {
		// create new member
		member = new Member();

		// populate new member information
		member.setDateJoined(new Date());
		member.setUsername(username);
		if (company.getName().equalsIgnoreCase("pinoyhomecoming")) {
			member.setUsername(email);
			username = email;
		}
		member.setEmail(email);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setCompany(company);
		member.setNewsletter(newsletter == null ? false : true);

		// encrypt given password
		encryptor = new PasswordEncryptor();

		member.setPassword(encryptor.encrypt(password));
		logger.info("SAVED ENCRYPTED PASSWORD: " + encryptor.encrypt(password));
		// initialize activation key
		member.setActivationKey(Encryption.hash(company + email + username
				+ password));
		member.setActivated(false);

		if (company.getName().equals("ecommerce")
				|| company.getName().equals("drugasia")
				|| company.getName().equals("mdt")
				|| company.getName().equals("onlinedepot")
				|| company.getName().equals("politiker")
				|| company.getName().equals("gurkka")
				|| company.getName().equals("spcmc")
				|| company.getId() == CompanyConstants.GURKKA_TEST) {
			mobileNo = request.getParameter("mobile");
			landline = request.getParameter("phone");
			fax = request.getParameter("fax");
			/*
			if(company.getName().equalsIgnoreCase("gurkkatest")){
				if (!Boolean.parseBoolean(StringUtils.trimToEmpty(request
						.getParameter("corporateaccount")))) {
					address1 = request.getParameter("company_address");
				}
				else{
					address1 = request.getParameter("address1");
				}
			}
			else{
				address1 = request.getParameter("address1");
			}
			*/
			address1 = request.getParameter("address1");
			address2 = request.getParameter("address2");
			province = request.getParameter("province");
			city = request.getParameter("city");
			zipcode = request.getParameter("zipcode");
			referredByName = request.getParameter("referredbyname");
			referredByNumber = request.getParameter("referredbynumber");

			member.setMobile(mobileNo);
			member.setLandline(landline);
			member.setFax(fax);
			System.out.println("### (1) Value of Address1 : " + address1);
			member.setAddress1(address1);
			member.setAddress2(address2);
			member.setProvince(province);
			member.setCity(city);
			member.setZipcode(zipcode);
			member.setReferredByName(referredByName);
			member.setReferredByNumber(referredByNumber);
			member.setSeniorCitizen(false);
		}
		
		if(company.getName().equals(CompanyConstants.BLUE_LIVE_TO_FEEL)) {
			mobileNo = request.getParameter("mobile");
			address1 = request.getParameter("address1");
			member.setMobile(mobileNo);
			member.setAddress1(address1);
			member.setActivated(true);
		}
		
		if(company.getName().equals(CompanyConstants.PURE_NECTAR)) {
			String birthday = request.getParameter("birthday");
			address1 = request.getParameter("shipping_address");
			mobileNo = request.getParameter("mobile");
			city = request.getParameter("city");
			province = request.getParameter("state_province");
			member.setInfo1(birthday);
			member.setAddress1(address1);
			member.setMobile(mobileNo);
			member.setCity(city);
			member.setProvince(province);
			member.setActivated(true);
		}
		
	}

	private void initializeAplicMemberInformation() {
		// create new member
		member = new Member();
		aplicMember = new AplicMember();

		final Random ran = new Random();
		ran.nextInt();
		// populate new member information
		member.setDateJoined(new Date());
		member.setUsername(ran.nextInt() + "_" + username);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setEmail(email);
		member.setCompany(company);
		member.setLandline(officeTel);
		member.setMobile(mobileNo);
		member.setNewsletter(newsletter == null ? false : true);

		aplicMember.setCompany(company);
		aplicMember.setSalutation(salutation);
		aplicMember.setDesignation(designation);
		aplicMember.setInsuranceCompany(insuranceCompany);
		aplicMember.setMailingAddress(mailingAddress);
		aplicMember.setCountry(country);
		aplicMember.setFax(fax);
		aplicMember.setNoOfYears(noOfYears);
		aplicMember.setBirthdate(birthdate);

		// encrypt given password
		encryptor = new PasswordEncryptor();
		member.setPassword(encryptor.encrypt(password));
		logger.info("SAVED ENCRYPTED PASSWORD: " + encryptor.encrypt(password));

		// initialize activation key
		member.setActivationKey(Encryption.hash(company + email + username
				+ password));
		member.setActivated(true);
	}

	private void initializeMicePhilippinesMemberInformation() {
		// create new member
		member = new Member();
		micePhilippinesMember = new MicePhilippinesMember();

		final Random ran = new Random();
		ran.nextInt();
		// populate new member information
		member.setDateJoined(new Date());
		member.setUsername(ran.nextInt() + "_" + username);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setEmail(email);
		member.setCompany(company);
		member.setLandline(telephoneNumbers);
		member.setMobile(mobileNo);
		member.setFax(fax);
		member.setNewsletter(newsletter == null ? false : true);

		micePhilippinesMember.setCompany(company);
		micePhilippinesMember.setCompanyAddress(companyAddress);
		micePhilippinesMember.setCompanyName(companyName);
		micePhilippinesMember.setCountry(country);
		micePhilippinesMember.setDesignation(designation);
		micePhilippinesMember.setAccompanyingPerson(accompanyingPerson);

		// encrypt given password
		encryptor = new PasswordEncryptor();
		member.setPassword(encryptor.encrypt(password));
		logger.info("SAVED ENCRYPTED PASSWORD: " + encryptor.encrypt(password));

		// initialize activation key
		member.setActivationKey(Encryption.hash(company + email + username
				+ password));
		member.setActivated(true);
	}

	private void initializeClickboxMemberInformation() {
		// create new member
		member = new Member();
		memberShippingInfo = new MemberShippingInfo();
		shippingInfo = new ShippingInfo();

		// populate new member information
		member.setDateJoined(new Date());
		member.setUsername(username);
		member.setEmail(email);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setCompany(company);
		member.setLandline(landline);
		member.setMobile(mobileNo);
		member.setNewsletter(newsletter == null ? false : true);

		shippingInfo.setAddress1(address1);
		shippingInfo.setAddress2(address2);
		shippingInfo.setCity(city);
		shippingInfo.setRegion(region);
		shippingInfo.setProvince(province);

		memberShippingInfo.setCompany(company);
		memberShippingInfo.setMember(member);
		memberShippingInfo.setShippingInfo(shippingInfo);

		// encrypt given password
		encryptor = new PasswordEncryptor();
		member.setPassword(encryptor.encrypt(password));
		logger.info("SAVED ENCRYPTED PASSWORD: " + encryptor.encrypt(password));

		// initialize activation key
		member.setActivationKey(Encryption.hash(company + email + username
				+ password));
		member.setActivated(false);
	}

	public void initializeGiftCardMemberInformation() {
		// create new member
		member = new Member();

		member.setDateJoined(new Date());
		member.setUsername(email);
		member.setEmail(email);
		member.setPassword(password);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setCompany(company);
		member.setMobile(mobileNo);
		member.setAddress1(address1);
		member.setAddress2(address2);
		member.setCity(city);
		member.setZipcode(zipcode);
		member.setNewsletter(newsletter == null ? false : true);

		// encrypt given password
		encryptor = new PasswordEncryptor();
		member.setPassword(encryptor.encrypt(password));
		logger.info("SAVED ENCRYPTED PASSWORD: " + encryptor.encrypt(password));

		// initialize activation key
		member.setActivationKey(Encryption.hash(company + email + username
				+ password));
		member.setActivated(false);
	}

	public void initializeGreenfieldMemberInformation() {
		// create new member
		member = new Member();

		member.setDateJoined(new Date());
		member.setCompany(company);
		member.setUsername(username);
		member.setPassword(password);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setMobile(mobileNo);
		member.setEmail(email);
		member.setAddress1(address1);
		member.setNewsletter(newsletter == null ? false : true);

		// encrypt given password
		encryptor = new PasswordEncryptor();
		member.setPassword(encryptor.encrypt(password));
		logger.info("SAVED ENCRYPTED PASSWORD: " + encryptor.encrypt(password));

		// initialize activation key
		member.setActivationKey(Encryption.hash(company + email + username
				+ password));
		member.setActivated(false);
	}

	/**
	 * Returns true if does not exist in local database, otherwise false.
	 * 
	 * @return - true if does not exist in local database, otherwise false
	 */
	private boolean isMemberNew() {
		// check for username duplicates
		if (company.getName().equalsIgnoreCase("APLIC")) {
			return true; // this is for no log in transaction
		}

		if (usernameExists()) {
			addActionError("Username already exists.");
			notificationMessage = "Username already exists.";

			return false;
		}
		// check for email duplicates
		if (emailExists()) {
			addActionError("Email address already used.");
			notificationMessage = "Email address already used.";
			return false;
		}
		return true;
	}

	/**
	 * Returns true if username exists in local database, otherwise false.
	 * 
	 * @return - true if username exists in local database, otherwise false
	 */
	private boolean usernameExists() {
		return !isNull(memberDelegate.findAccount(username, company));
	}

	/**
	 * Returns true if email address exists in local database, otherwise false.
	 * 
	 * @return - true if email address exists in local database, otherwise false
	 */
	private boolean emailExists() {
		return !isNull(memberDelegate.findEmail(company, email));
	}

	/**
	 * Initialize member account information.
	 */
	private void initMemberInformation() {
		initLoginInformation();
		email = request.getParameter("email");
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		request.setAttribute("email", email);
		request.setAttribute("firstname", firstname);
		request.setAttribute("lastname", lastname);
	}

	private void initializeOtherInfo() {
		info1 = request.getParameter("info1") != null ? request.getParameter(
				"info1").trim() : null;
		info2 = request.getParameter("info2") != null ? request.getParameter(
				"info2").trim() : null;
		info3 = request.getParameter("info3") != null ? request.getParameter(
				"info3").trim() : null;

		value = request.getParameter("value") != null ? request.getParameter(
				"value").trim() : null;
		value2 = request.getParameter("value2") != null ? request.getParameter(
				"value2").trim() : null;
		value3 = request.getParameter("value3") != null ? request.getParameter(
				"value3").trim() : null;

		reg_companyAddress = request.getParameter("company_address") != null ? request
				.getParameter("company_address").trim() : null;
		reg_companyName = !StringUtils.isEmpty(request
				.getParameter("reg_companyName")) ? request
				.getParameter("reg_companyName") : null;
		if(company.getId() == CompanyConstants.GURKKA_TEST){
			if(request.getParameter("corporateaccount").equalsIgnoreCase("true")){
				member.setFirstname(reg_companyName);
				member.setAddress1(reg_companyAddress);
				logger.info("Company Address 1 : " + reg_companyAddress);
			}
		}
		logger.info("Company Address 2 : " + reg_companyAddress);
		member.setInfo1(info1);
		member.setInfo2(info2);
		member.setInfo3(info3);

		member.setValue(value);
		member.setValue2(value2);
		member.setValue3(value3);

		member.setReg_companyAddress(reg_companyAddress);
		member.setReg_companyName(reg_companyName);

		if (company.getId() != CompanyConstants.GURKKA
				|| company.getId() != CompanyConstants.GURKKA_TEST) {
			info4 = request.getParameter("info4") != null ? request
					.getParameter("info4").trim() : null;
			member.setInfo4(info4);
		}
	}

	private void initAplicMemberInformation() {
		initLoginInformation();

		salutation = request.getParameter("1a|salutation");
		firstname = request.getParameter("1b|firstname");
		lastname = request.getParameter("1c|lastname");
		designation = request.getParameter("1d|designation");
		insuranceCompany = request.getParameter("1e|insurancecompany");
		country = request.getParameter("1f|country");
		noOfYears = request.getParameter("1g|noofyears");
		email = request.getParameter("email");
		birthdate = request.getParameter("birthdate");
		mailingAddress = request.getParameter("1h|mailingaddress");
		officeTel = request.getParameter("1i|officetel");
		mobileNo = request.getParameter("1j|mobileno");

		request.setAttribute("email", email);
		request.setAttribute("firstname", firstname);
		request.setAttribute("lastname", lastname);
	}

	private void initMicePhilippinesMemberInformation() {
		initLoginInformation();

		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		designation = request.getParameter("designation");
		companyName = request.getParameter("companyName");
		country = request.getParameter("country");
		email = request.getParameter("email");
		companyAddress = request.getParameter("companyAddress");
		telephoneNumbers = request.getParameter("telephoneNumbers");
		mobileNo = request.getParameter("mobileno");
		fax = request.getParameter("fax");
		accompanyingPerson = request.getParameter("accompanyingPerson");

		request.setAttribute("email", email);
		request.setAttribute("firstname", firstname);
		request.setAttribute("lastname", lastname);
	}

	private void initClickboxMemberInformation() {

		initLoginInformation();

		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		address1 = request.getParameter("address1");
		address2 = request.getParameter("address2");
		city = request.getParameter("city");
		region = request.getParameter("region");
		province = request.getParameter("province");
		landline = request.getParameter("landline");
		mobileNo = request.getParameter("mobileno");
		email = request.getParameter("email");
	}

	private void initDrugasiaMemberInformation() {
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		email = request.getParameter("email");
		mobileNo = request.getParameter("mobile");
		landline = request.getParameter("phone");
		fax = request.getParameter("fax");
		address1 = request.getParameter("address1");
		address2 = request.getParameter("address2");
		province = request.getParameter("province");
		city = request.getParameter("city");
		zipcode = request.getParameter("zipcode");
	}

	private void initPolitikerMemberInformation() {
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		email = request.getParameter("email");
		mobileNo = request.getParameter("mobile");
		landline = request.getParameter("phone");
		fax = request.getParameter("fax");
		address1 = request.getParameter("address1");
		address2 = request.getParameter("address2");
		province = request.getParameter("province");
		city = request.getParameter("city");
		zipcode = request.getParameter("zipcode");
	}

	private void initEcommerceMemberInformation() {
		email = request.getParameter("email");
		mobileNo = request.getParameter("mobile");
		landline = request.getParameter("phone");
		fax = request.getParameter("fax");
		address1 = request.getParameter("address1");
		address2 = request.getParameter("address2");
		province = request.getParameter("province");
		city = request.getParameter("city");
		zipcode = request.getParameter("zipcode");
	}

	private void initGiftCardMemberInformation() {
		username = request.getParameter("email");
		password = request.getParameter("password");

		request.setAttribute("username", username);
		request.setAttribute("password", password);

		email = request.getParameter("email");
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		mobileNo = request.getParameter("mobile");
		address1 = request.getParameter("address1");
		address2 = request.getParameter("address2");
		city = request.getParameter("city");
		zipcode = request.getParameter("zipcode");
	}

	private void initGreenfieldMemberInformation() {
		username = request.getParameter("username");
		password = request.getParameter("password");

		request.setAttribute("username", username);
		request.setAttribute("password", password);

		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		mobileNo = request.getParameter("mobile");
		email = request.getParameter("email");
		address1 = request.getParameter("address1");
	}

	private void initGurkkaMemberInformation() {
		String s_mobile_country_code = request.getParameter("txt-mobile-country-code");
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		username = request.getParameter("username");
		email = username;
		if(s_mobile_country_code !=null){
			mobileNo = s_mobile_country_code + "-" + request.getParameter("mobile");
		}
		else{
			mobileNo = request.getParameter("mobile");
		}
		landline = request.getParameter("landline");
		fax = request.getParameter("fax");
		address1 = request.getParameter("address1");
		province = request.getParameter("province");
		city = request.getParameter("city");
		zipcode = request.getParameter("zipcode");
		landline = request.getParameter("landline");

		info1 = request.getParameter("info1");
		info2 = request.getParameter("info2");
		info3 = request.getParameter("info3");

		value = request.getParameter("value");
		value2 = request.getParameter("value2");
		value3 = request.getParameter("value3");

		reg_companyName = request.getParameter("reg_companyName");
		reg_companyAddress = request.getParameter("reg_companyAddress");
		
		
	}

	private void initGurkkaAccountInformation(AccountType accountType) {
		if (accountType == AccountType.CORPORATE) {
			// clear
			firstname = request.getParameter("reg_companyName");
			lastname = "";
			info2 = "";
			// initialize
			reg_companyName = request.getParameter("reg_companyName");
			info3 = request.getParameter("info3");

		} else {
			// initialize
			firstname = request.getParameter("firstname");
			lastname = request.getParameter("lastname");
			info2 = request.getParameter("info2");
			// clear
			reg_companyName = "";
			info3 = "";
		}
	}

	private void initGurkkaSecurityInformation() {
		info1 = request.getParameter("info1");
	}

	private void initGurkkaProfileInformation() {
		String s_mobile_country_code = request.getParameter("txt-mobile-country-code");
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		username = request.getParameter("username");
		email = username;
		//mobileNo = request.getParameter("mobile");
		
		if(s_mobile_country_code !=null){
			mobileNo = s_mobile_country_code + "-" + request.getParameter("mobile");
		}
		else{
			mobileNo = request.getParameter("mobile");
		}
		
		landline = request.getParameter("landline");
		fax = request.getParameter("fax");
		address1 = request.getParameter("address1");
		province = request.getParameter("province");
		city = request.getParameter("city");
		zipcode = request.getParameter("zipcode");
		landline = request.getParameter("landline");

		info1 = request.getParameter("info1");
		info2 = request.getParameter("info2");
		info3 = request.getParameter("info3");

		value = request.getParameter("value");
		value2 = request.getParameter("value2");
		value3 = request.getParameter("value3");

		reg_companyName = request.getParameter("reg_companyName");
		reg_companyAddress = request.getParameter("reg_companyAddress");
	}

	/**
	 * Initialize username and password.
	 */
	private void initLoginInformation() {

		username = request.getParameter("username");
		password = request.getParameter("password");

		if (company.getName().equalsIgnoreCase("pinoyhomecoming")) {
			passwordGenerator = new StringUtil();
			password = passwordGenerator.generateRandomPassword();
		}

		request.setAttribute("username", username);
		request.setAttribute("password", password);

	}

	/**
	 * Returns true if input specified username is valid, otherwise false. A
	 * valid username must not be null and must not be empty.
	 * 
	 * @param username
	 *            - username for user account, must not be null
	 * @return - true if input specified username is valid, otherwise false
	 */
	public boolean isValidUsernameInput(String username) {
		if (null == username || username.length() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * Returns true if input specified password is valid, otherwise false. A
	 * valid password must not be null and must not be empty.
	 * 
	 * @param password
	 *            - password for user account, must not be null
	 * @return - true if input specified password is valid, otherwise false
	 */
	public boolean isValidPasswordInput(String password) {
		if (null == password || password.length() == 0) {
			return false;
		}

		return true;
	}

	private void loadMenu() {
		try {
			final Map<String, Menu> menuList = new HashMap<String, Menu>();

			// get the single pages
			final List<SinglePage> singlePageList = singlePageDelegate.findAll(
					company).getList();
			for (final SinglePage singlePage : singlePageList) {
				final String jspName = singlePage.getJsp();
				final Menu menu = new Menu(singlePage.getName(), httpServer
						+ "/" + jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}

			// get the multi pages
			final List<MultiPage> multiPageList = multiPageDelegate.findAll(
					company).getList();
			for (final MultiPage multiPage : multiPageList) {
				final String jspName = multiPage.getJsp();
				final Menu menu = new Menu(multiPage.getName(), httpServer
						+ "/" + jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}

			// get the form Pages
			final List<FormPage> formPageList = formPageDelegate.findAll(
					company).getList();
			for (final FormPage formPage : formPageList) {
				final String jspName = formPage.getJsp();
				final Menu menu = new Menu(formPage.getName(), httpServer + "/"
						+ jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}

			// get the groups
			final List<Group> groupList = groupDelegate.findAll(company)
					.getList();
			for (final Group group : groupList) {
				final String jspName = group.getName().toLowerCase();
				final Menu menu = new Menu(group.getName(), httpServer + "/"
						+ jspName + ".do");
				menuList.put(jspName, menu);
			}

			// get the link to the allowed pages
			for (final String s : ALLOWED_PAGES) {
				final String jspName = s.toLowerCase();
				final Menu menu = new Menu(jspName, httpServer + "/" + jspName
						+ ".do");
				menuList.put(jspName, menu);
			}

			request.setAttribute("menu", menuList);
		} catch (final Exception e) {
			logger.fatal("problem intercepting action in FrontMenuInterceptor. "
					+ e);
		}
	}

	private void loadFeaturedPages(int max) {
		final Map<String, Object> featuredPages = new HashMap<String, Object>();
		final List<MultiPage> featuredMultiPage = multiPageDelegate
				.findAllFeatured(company).getList();

		for (final MultiPage mp : featuredMultiPage) {
			if (!mp.getHidden()) {
				featuredPages.put(mp.getName(), mp);
			}
		}

		request.setAttribute("featuredPages", featuredPages);
	}

	private void loadFeaturedSinglePages(int max) {
		final Map<String, Object> featuredSinglePages = new HashMap<String, Object>();
		final List<SinglePage> featuredSinglePage = singlePageDelegate
				.findAllFeatured(company).getList();

		for (final SinglePage mp : featuredSinglePage) {
			if (!mp.getHidden()) {
				featuredSinglePages.put(mp.getName(), mp);
			}
		}

		request.setAttribute("featuredSinglePages", featuredSinglePages);
	}

	private String sendEmailToCompanyAccount(Member member) {

		final String[] to = request.getParameter("to").split(",");
		final String newsletter = request.getParameter("newsletter");

		if (member != null) {
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			// EmailUtil.connect("smtp.gmail.com", 25);

			final StringBuffer content = new StringBuffer();
			content.append("The following are user details for Downloads Section: \r\n\r\n");
			content.append("Firstname: " + member.getFirstname() + "\r\n\r\n"
					+ "Lastname: " + member.getLastname() + "\r\n\r\n"
					+ "Email: " + member.getEmail() + "\r\n\r\n" + "Username: "
					+ member.getUsername() + "\r\n\r\n");
			if (newsletter != null && !newsletter.isEmpty()) {
				content.append("Subscribe to Newsletter: Yes \r\n\r\n");
			} else {
				content.append("Subscribe to Newsletter: No \r\n\r\n");
			}

			String from = "noreply@webtogo.com.ph";

			if (companySettings.getEmailUserName() != null
					&& !company.getCompanySettings().getEmailUserName()
							.equals("")) {
				from = companySettings.getEmailUserName();
			}

			if (EmailUtil.send(from, to, "Download Section Signup Submission",
					content.toString(), null)) {
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;

	}

	private String sendAyrosoHardwareEmail(Member member) {

		final String[] to = request.getParameter("to").split(",");

		if (member != null) {
			setEmailSettings();
			EmailUtil.connect(smtp, Integer.parseInt(portNumber),
					mailerUserName, mailerPassword);
			// EmailUtil.connect("smtp.gmail.com", 25);

			final StringBuffer content = new StringBuffer();
			content.append("The following are user details for signup submission: \r\n\r\n");
			content.append("Firstname: " + member.getFirstname() + "\r\n\r\n"
					+ "Lastname: " + member.getLastname() + "\r\n\r\n"
					+ "Email: " + member.getEmail() + "\r\n\r\n" + "Username: "
					+ member.getUsername() + "\r\n\r\n");

			String from = "noreply@webtogo.com.ph";

			if (companySettings.getEmailUserName() != null
					&& !company.getCompanySettings().getEmailUserName()
							.equals("")) {
				from = companySettings.getEmailUserName();
			}

			if (EmailUtil.send(from, to, "Signup Submission",
					content.toString(), null)) {
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;

	}

	private String sendEmailToGurkka(Member member) {
		//company = companyDelegate.find(346L);
		final String[] to = request.getParameter("to").split(",");
		final String[] bcc = { "maryann@ivant.com",
				"edgar@ivant.com", "teo@ivant.com", "info@gurkka.com", "customerservice@gurkka.com", "ariebroncano@yahoo.com" };
		if (member != null) {
			/*
			EmailUtil.connect(company.getCompanySettings().getSmtp(), Integer
					.parseInt(company.getCompanySettings().getPortNumber()),
					company.getCompanySettings().getEmailUserName(), company
							.getCompanySettings().getEmailPassword());
			*/
			EmailUtil.connectViaGurkka();

			final StringBuffer content = new StringBuffer();
			content.append("The following are the details for the newly registered Member: \r\n\r\n");

			if (!StringUtils.isEmpty(member.getInfo2())) {
				content.append("Firstname: " + member.getFirstname()
						+ "\r\n\r\n");
				content.append("Lastname: " + member.getLastname() + "\r\n\r\n");
			} else {
				content.append("Company Name: " + member.getReg_companyName()
						+ "\r\n\r\n");
			}

			content.append("Email: " + member.getEmail() + "\r\n\r\n");
			content.append("Username: " + member.getUsername() + "\r\n\r\n");

			if (EmailUtil.send("noreply@webtogo.com.ph", to,
					"Member Registration", content.toString(), null)) {
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;

	}

	private String sendAplicEmailToCompanyAccount(Member member) {


		final String[] to = request.getParameter("to").split(",");
		request.getParameter("newsletter");

		if (member != null) {
			EmailUtil.connect("smtp.gmail.com", 25);

			final StringBuffer content = new StringBuffer();
			content.append("The following are details of the new registrant: \r\n\r\n");
			content.append("Salutation: " + aplicMember.getSalutation()
					+ "\r\n\r\n" + "Firstname: " + member.getFirstname()
					+ "\r\n\r\n" + "Lastname: " + member.getLastname()
					+ "\r\n\r\n");

			if (!aplicMember.getDesignation().equals("")) {
				content.append("Designation: " + aplicMember.getDesignation()
						+ "\r\n\r\n");
			}

			if (!aplicMember.getInsuranceCompany().equals("")) {
				content.append("Insurance Company: "
						+ aplicMember.getInsuranceCompany() + "\r\n\r\n");
			}

			if (!aplicMember.getCountry().equals("")) {
				content.append("Country: " + aplicMember.getCountry()
						+ "\r\n\r\n");
			}

			if (!aplicMember.getNoOfYears().equals("")) {
				content.append("No. of Years: " + aplicMember.getNoOfYears()
						+ "\r\n\r\n");
			}

			if (!member.getEmail().equals("")) {
				content.append("Email Address: " + member.getEmail()
						+ "\r\n\r\n");
			}

			if (!aplicMember.getBirthdate().equals("")) {
				content.append("Birthday: " + aplicMember.getBirthdate()
						+ "\r\n\r\n");
			}

			if (!aplicMember.getMailingAddress().equals("")) {
				content.append("Mailing Address: "
						+ aplicMember.getMailingAddress() + "\r\n\r\n");
			}

			if (!member.getLandline().equals("")) {
				content.append("Office Tel.: " + member.getLandline()
						+ "\r\n\r\n");
			}

			if (!member.getMobile().equals("")) {
				content.append("Mobile No.: " + member.getMobile() + "\r\n\r\n");
			}

			if (EmailUtil.send("noreply@webtogo.com.ph", to, "Registration",
					content.toString(), null)) {
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;
	}

	private String sendMicePhilippinesEmailToCompanyAccount(Member member) {

		micePhilippinesMember = micePhilippinesMemeberDelegate
				.findMicePhilippinesMember(member.getId().toString());

		final String[] to = request.getParameter("to").split(",");
		request.getParameter("newsletter");

		if (member != null) {
			EmailUtil.connect("smtp.gmail.com", 587);

			final StringBuffer content = new StringBuffer();
			content.append("The following are details of the new registrant: \r\n\r\n");
			content.append("Firstname: " + member.getFirstname() + "\r\n\r\n"
					+ "Lastname: " + member.getLastname() + "\r\n\r\n");

			if (!micePhilippinesMember.getDesignation().equals("")) {
				content.append("Designation: "
						+ micePhilippinesMember.getDesignation() + "\r\n\r\n");
			}

			if (!micePhilippinesMember.getCompanyName().equals("")) {
				content.append("Company Name: "
						+ micePhilippinesMember.getCompanyName() + "\r\n\r\n");
			}

			if (!micePhilippinesMember.getCompanyAddress().equals("")) {
				content.append("Company Address: "
						+ micePhilippinesMember.getCompanyAddress()
						+ "\r\n\r\n");
			}

			if (!micePhilippinesMember.getCountry().equals("")) {
				content.append("Country: " + micePhilippinesMember.getCountry()
						+ "\r\n\r\n");
			}

			if (!member.getLandline().equals("")) {
				content.append("Telephone Numbers: " + member.getLandline()
						+ "\r\n\r\n");
			}

			if (!member.getFax().equals("")) {
				content.append("Fax: " + member.getFax() + "\r\n\r\n");
			}

			if (!member.getMobile().equals("")) {
				content.append("Mobile No.: " + member.getMobile() + "\r\n\r\n");
			}

			if (!member.getEmail().equals("")) {
				content.append("Email Address: " + member.getEmail()
						+ "\r\n\r\n");
			}

			if (!micePhilippinesMember.getAccompanyingPerson().equals("")) {
				content.append("Accompanying Person.: "
						+ micePhilippinesMember.getAccompanyingPerson()
						+ "\r\n\r\n");
			}

			if (EmailUtil.send("noreply@webtogo.com.ph", to, "Registration",
					content.toString(), null)) {
				return Action.SUCCESS;
			}
		}

		return Action.ERROR;
	}

	public String viewEcommerceAccount() {
		if (member == null) {
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String editEcommerce() {
		if (member == null) {
			return Action.INPUT;
		}

		initEcommerceMemberInformation();

		final Long id = Long.parseLong(request.getParameter("id"));
		member = memberDelegate.find(id);

		member.setEmail(email);
		member.setMobile(mobileNo);
		member.setLandline(landline);
		member.setFax(fax);
		member.setAddress1(address1);
		member.setAddress2(address2);
		member.setProvince(province);
		member.setCity(city);
		member.setZipcode(zipcode);

		// update user
		if (isNull(memberDelegate.update(member))) {
			addActionError("Error encountered.");
			return ERROR;
		}

		addActionMessage("Edit account successful.");

		return Action.SUCCESS;
	}

	public String verifyPromoCode() {

		final SinglePageDelegate singlePageDelegate = SinglePageDelegate
				.getInstance();

		final SinglePage promoCodeSetting = singlePageDelegate.find(company,
				ReferralEnum.PROMOCODE_SETTINGPAGE.getValue());

		String currentPromocode = promoCodeSetting.getContent()
				.replaceAll("<p>", "").replaceAll("</p>", "");

		if (promoCodeSetting != null) {

			currentPromocode = currentPromocode.trim();

			if (currentPromocode.equals(promoCode)) {
				session.put(ReferralEnum.PROMOCODE_INSESSION.getValue(),
						currentPromocode);
			}

		}

		return Action.SUCCESS;

	}

	public String verifyAge() {

		final String goBack = StringUtils.trimToNull(request
				.getParameter("back"));
		if (goBack != null && goBack.length() > 0) {
			session.remove("overEighteen");
			return ERROR;
		}

		final String dateString = StringUtils.trimToNull(request
				.getParameter("birthday")) != null ? StringUtils
				.trimToNull(request.getParameter("birthday")) : StringUtils
				.trimToNull(request.getParameter("info2"));
		if (dateString == null) {
			notificationMessage = "Please input your date of birth.";
			return ERROR;
		}
		String dateString2 = "";
		//System.out.println("######### "+dateString+"#############");
		dateString2 = dateString.replace("-", "/");
		
		//System.out.println("######### "+dateString2+"#############");
		final DateTimeFormatter format = DateTimeFormat
				.forPattern("mm/dd/yyyy");
		final DateTime birthDate = format.parseDateTime(dateString2);
		final LocalDate birthDay = new LocalDate(birthDate.toLocalDate());
		final LocalDate now = new LocalDate();
		final int age = Years.yearsBetween(birthDay, now).getYears();

		if (age >= 18) {
			session.put("overEighteen", true);
			return Action.SUCCESS;
		} else {
			notificationMessage = "You are not allowed to enter this site.";
			session.put("overEighteen", false);
			return ERROR;
		}
	}

	private boolean userNotActivated() {
		if (memberDelegate.findEmail(company, email) != null) {
			if (memberDelegate.findEmail(company, email).getActivated()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public String resendActivationLink() {
		final String kaptchaReceived = request.getParameter("kaptcha");
		if (kaptchaReceived != null) {
			final String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				request.setAttribute("message", "Invalid characters in image.");
				return Action.ERROR;
			}
		}

		member = memberDelegate.findEmail(company, email);
		if (member == null) {
			request.setAttribute("message", "Email does not exist");
			return Action.ERROR;
		} else {
			sendPinoyHomecomingEmail(member);
		}

		return Action.SUCCESS;
	}

	public String loginViaBillingNumber() throws Exception {

		logger.debug("LOGGING IN...");
		logger.debug("COMPANY: " + company);

		if (!isNull(billingNumber)) {
			logger.info("billingNumber " + billingNumber);
			logger.info("company " + company);

			member = memberDelegate.findAccount(billingNumber, company);

			logger.info("MEMBER " + member);
			if (!isNull(member)) {
				if (member.getCompany().getId() != company.getId()) {
					this.setNotificationMessage("Invalid Billing Number.");
					request.setAttribute("errorMessage",
							"Invalid Billing Number.");
					return ERROR;
				}

				// update user login information
				member.setLastLogin(new Date());
				memberDelegate.update(member);
				session.put(MemberInterceptor.MEMBER_SESSION_KEY,
						member.getId());

				request.setAttribute("member", member);

				// add user to session
				session.put(UserInterceptor.USER_SESSION_KEY, member.getId());

				// log the member
				final MemberLog memberLog = new MemberLog();
				memberLog.setCompany(company);
				memberLog.setMember(member);
				memberLog.setRemarks("Logged In");
				memberLogDelegate.insert(memberLog);

				return SUCCESS;
			}
			// ----------------------------------------------------------------------------------
			else {
				this.setNotificationMessage("Invalid Username / Password");
				request.setAttribute("errorMessage",
						"Invalid Username / Password");
				return ERROR;
			}
		}

		if (isValidUsernameInput(username) || isValidPasswordInput(password)) {
			addActionError("Invalid Username / Password");
		}

		return INPUT;

	}

	/**
	 * @param myEmail
	 *            the myEmail to set
	 */
	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}

	/**
	 * @return the myEmail
	 */
	public String getMyEmail() {
		return myEmail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPmcMemberPass() {
		return pmcMemberPass;
	}

	public void setPmcMemberPass(String pmcMemberPass) {
		this.pmcMemberPass = pmcMemberPass;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public String getPromoCodeInSession() {

		if (session.get(ReferralEnum.PROMOCODE_INSESSION.getValue()) != null) {
			return session.get(ReferralEnum.PROMOCODE_INSESSION.getValue())
					.toString();
		}

		return null;
	}

	public void setBillingNumber(String billingNumber) {
		this.billingNumber = billingNumber;
	}

	public String getBillingNumber() {
		return billingNumber;
	}

	public String getSmtp() {
		return smtp;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public String getMailerUserName() {
		return mailerUserName;
	}

	public String getMailerPassword() {
		return mailerPassword;
	}

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

	public void setMicePhilippinesMember(
			MicePhilippinesMember micePhilippinesMember) {
		this.micePhilippinesMember = micePhilippinesMember;
	}

	public MicePhilippinesMember getMicePhilippinesMember() {
		return micePhilippinesMember;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public void setAccompanyingPerson(String accompanyingPerson) {
		this.accompanyingPerson = accompanyingPerson;
	}

	public String getAccompanyingPerson() {
		return accompanyingPerson;
	}

	public void setTelephoneNumbers(String telephoneNumbers) {
		this.telephoneNumbers = telephoneNumbers;
	}

	public String getTelephoneNumbers() {
		return telephoneNumbers;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	@SuppressWarnings("unchecked")
	public String submitreviewagree() throws Exception, IOException {
		try {} catch (Exception e) {

			return ERROR;
		}

		return SUCCESS;
	}

	public String countMemberPollWithFormat(Company company, String pollType,
			String remarks, ItemComment itemComment) {
		Integer totalCount = 0;
		DecimalFormat myFormatter = new DecimalFormat("###,###");
		totalCount = memberPollDelegate.findCount(company, pollType, remarks,
				itemComment);
		return myFormatter.format(totalCount);
	}

	public String countMemberPollWithFormat(Company company, String pollType,
			String remarks, CategoryItem item) {
		Integer totalCount = 0;
		DecimalFormat myFormatter = new DecimalFormat("###,###");
		totalCount = memberPollDelegate.findCount(company, pollType, remarks,
				item);
		return myFormatter.format(totalCount);
	}

	public String countMemberPollWithFormat(Company company, String pollType,
			String remarks, SinglePage singlePage) {
		Integer totalCount = 0;
		DecimalFormat myFormatter = new DecimalFormat("###,###");
		totalCount = memberPollDelegate.findCount(company, pollType, remarks,
				singlePage);
		return myFormatter.format(totalCount);
	}

	@SuppressWarnings("unchecked")
	public String deletereview() throws Exception, IOException {
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();

		try {
			String itemCommentid = request.getParameter("itemComment_id");
			Long longItemCommentId = 0L;
			try {
				longItemCommentId = Long.parseLong(itemCommentid);
			} catch (Exception e) {

			}

			ItemComment itemComment = itemCommentDelegate
					.find(longItemCommentId);
			if (itemComment != null) {
				if (member != null) {
					if (member == itemComment.getMember()) {

						itemCommentDelegate.delete(itemComment);
						obj.put("success", true);
						objList.add(obj);
						obj2.put("listDeleteReviewDetails", objList);
						setInputStream(new ByteArrayInputStream(obj2
								.toJSONString().getBytes()));
					} else {

						obj.put("errorMessage",
								"You are not an authorized person to delete this review!");
						objList.add(obj);
						obj2.put("listDeleteReviewDetails", objList);
						setInputStream(new ByteArrayInputStream(obj2
								.toJSONString().getBytes()));
					}
				} else {

					obj.put("errorMessage",
							"Please log-in first before deleting this review!");
					objList.add(obj);
					obj2.put("listDeleteReviewDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));
				}
			} else {

				obj.put("errorMessage",
						"Invalid review! This item was already deleted!");
				objList.add(obj);
				obj2.put("listDeleteReviewDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
			}
		} catch (Exception e) {

			obj.put("errorMessage",
					"An error was encountered while updating the review!");
			objList.add(obj);
			obj2.put("listDeleteReviewDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString()
					.getBytes()));
		}

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String updatereview() throws Exception, IOException {
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();

		try {

			String itemCommentid = request.getParameter("itemComment_id");
			String value = request.getParameter("value");
			String comment = request.getParameter("comment");
			double dblValue = 0D;
			Long longItemCommentId = 0L;
			try {
				dblValue = Double.parseDouble(value);
			} catch (Exception e) {
				dblValue = 0D;
			}
			try {
				longItemCommentId = Long.parseLong(itemCommentid);
			} catch (Exception e) {

			}

			ItemComment itemComment = itemCommentDelegate
					.find(longItemCommentId);
			if (itemComment != null) {
				itemComment.setValue(dblValue);
				itemComment.setContent(comment);
				itemCommentDelegate.update(itemComment);

				obj.put("success", true);
				objList.add(obj);
				obj2.put("listUpdateReviewDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
			} else {

				obj.put("errorMessage", "This review was already deleted!");
				objList.add(obj);
				obj2.put("listUpdateReviewDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
			}

		} catch (Exception e) {
			obj.put("errorMessage",
					"An error was encountered while updating the review!");
			objList.add(obj);
			obj2.put("listUpdateReviewDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString()
					.getBytes()));

		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String submitcomment() throws Exception, IOException {
		try{
			System.out.println("######################  Inside   1   ");
			JSONArray objList = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			SinglePage singlePage = new SinglePage();
			String id = request.getParameter("singlePage_id");
			String comment = request.getParameter("comment");
			System.out.println("######################  Inside   2   ");
			if(member  != null){
				System.out.println("######################  Inside  3    ");
				if(id != null){
					System.out.println("######################  Inside    4  ");
					singlePage = singlePageDelegate.find(Long.parseLong(id));
					if(singlePage != null){
						System.out.println("######################  Inside   5   ");
						if(itemCommentDelegate.findCommentsByContent(company, singlePage, comment, member).getList().size() == 0) {
							System.out.println("######################  Inside   6   ");
							ItemComment itemComment = new ItemComment();
							itemComment.setCreatedOn(new Date());
							itemComment.setCompany(company);
							itemComment.setMember(member);
							itemComment.setFirstname("");
							itemComment.setLastname("");
							itemComment.setEmail("");
							itemComment.setContent(comment);
							itemComment.setIsValid(true);
							itemComment.setPage(singlePage);
							itemComment.setValue(0D);//set default value
							itemCommentDelegate.insert(itemComment);
							
							//put result in JSON
							obj.put("success", true);
							objList.add(obj);
							obj2.put("listCommentDetails",  objList);
							setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
							notificationMessage = "Comment was successfully submitted.";
							
							
						}
						else{
							System.out.println("######################  Inside   7   ");
							obj.put("errorMessage", "A similar comment on this item was already submitted!");
							objList.add(obj);
							obj2.put("listCommentDetails", objList);
							setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
							notificationMessage = "A similar comment on this item was already submitted.";
							return SUCCESS;
							
							
						}
					}
				}
			}
			else{
				System.out.println("######################  Inside  8    ");
				obj.put("errorMessage",  "You are not a recognized member! You are not allowed to submit a comment!");
				objList.add(obj);
				obj2.put("listCommentDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString().getBytes()));
				notificationMessage = "You are not a recognized member! You are not allowed to submit a comment!";
				return SUCCESS;
			}
			
		}
		catch(Exception e){
			System.out.println("######################  Inside   9   ");
			e.printStackTrace();
		}
		System.out.println("######################  Inside   10   ");
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String submitreview() throws Exception, IOException {
		try {
			JSONArray objList = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();

			String id = request.getParameter("item_id");
			String value = request.getParameter("value");
			String comment = request.getParameter("comment");
			double dblValue = 0.0;
			CategoryItem item = new CategoryItem();

			successUrl = (request.getParameter("successUrl") != null ? request
					.getParameter("successUrl") : "");
			setErrorUrl((request.getParameter("errorUrl") != null ? request
					.getParameter("errorUrl") : ""));

			if (member != null) {

				if (id != null) {

					item = categoryItemDelegate.find(Long.parseLong(id));
					if (item != null) {

						try {
							dblValue = Double.parseDouble(value);
						} catch (Exception e) {
							dblValue = 0.0;
						}
						if (itemCommentDelegate
								.findCommentsByValueAndContent(company, item,
										dblValue, comment, member).getList()
								.size() == 0) {

							ItemComment itemComment = new ItemComment();
							itemComment.setCreatedOn(new Date());
							itemComment.setCompany(company);
							itemComment.setMember(member);
							itemComment.setFirstname("");
							itemComment.setLastname("");
							itemComment.setEmail("");
							itemComment.setContent(comment);
							itemComment.setIsValid(true);
							itemComment.setItem(item);

							itemComment.setValue(dblValue);
							itemCommentDelegate.insert(itemComment);

							obj.put("success", true);
							objList.add(obj);
							obj2.put("listReviewDetails", objList);
							setInputStream(new ByteArrayInputStream(obj2
									.toJSONString().getBytes()));
							notificationMessage = "Review was successfully submitted.";

						} else {
							obj.put("errorMessage",
									"A similar review on this item was already submitted!");
							objList.add(obj);
							obj2.put("listReviewDetails", objList);
							setInputStream(new ByteArrayInputStream(obj2
									.toJSONString().getBytes()));
							notificationMessage = "A similar review on this item was already submitted!";
							return SUCCESS;
						}
					}
				}

			} else {
				obj.put("errorMessage",
						"You are not a recognized member! You are not allowed to submit a review!");
				objList.add(obj);
				obj2.put("listReviewDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
				notificationMessage = "You are not a recognized member! You are not allowed to submit a review!";
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String submitreply() throws Exception, IOException {
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		try {

			String id = request.getParameter("itemComment_id");
			String comment = request.getParameter("comment");

			ItemComment itemComment = new ItemComment();
			CategoryItem item = new CategoryItem();

			successUrl = (request.getParameter("successUrl") != null ? request
					.getParameter("successUrl") : "");
			setErrorUrl((request.getParameter("errorUrl") != null ? request
					.getParameter("errorUrl") : ""));

			if (member != null) {

				if (id != null) {

					itemComment = itemCommentDelegate.find(Long.parseLong(id));
					if (itemComment != null) {

						if (itemCommentDelegate
								.findChildCommentsByValueAndContent(company,
										item, itemComment, 0D, comment, member)
								.getList().size() == 0) {

							ItemComment itemComment2 = new ItemComment();
							itemComment2.setCreatedOn(new Date());
							itemComment2.setCompany(company);
							itemComment2.setMember(member);
							itemComment2.setFirstname("");
							itemComment2.setLastname("");
							itemComment2.setEmail("");
							itemComment2.setContent(comment);
							itemComment2.setIsValid(true);
							itemComment2.setItem(item);
							itemComment2.setParentItemComment(itemComment);
							itemComment.setValue(0D);
							itemCommentDelegate.insert(itemComment2);

							obj.put("success", true);
							objList.add(obj);
							obj2.put("listReplyDetails", objList);
							setInputStream(new ByteArrayInputStream(obj2
									.toJSONString().getBytes()));
							notificationMessage = "Reply was successfully submitted.";

						} else {

							obj.put("errorMessage",
									"A similar reply on this item was already submitted!");
							objList.add(obj);
							obj2.put("listReplyDetails", objList);
							setInputStream(new ByteArrayInputStream(obj2
									.toJSONString().getBytes()));
							notificationMessage = "A similar reply on this item was already submitted!";
							return SUCCESS;
						}
					} else {

						obj.put("errorMessage",
								"Error encountered! The item that you are going to reply was already deleted!");
						objList.add(obj);
						obj2.put("listReplyDetails", objList);
						setInputStream(new ByteArrayInputStream(obj2
								.toJSONString().getBytes()));
						notificationMessage = "A similar review on this item was already submitted!";
						return SUCCESS;
					}
				}

			} else {

				obj.put("errorMessage",
						"You are not a recognized member! You are not allowed to submit a reply!");
				objList.add(obj);
				obj2.put("listReplyDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
				notificationMessage = "You are not a recognized member! You are not allowed to submit a reply!";
				return SUCCESS;
			}
		} catch (Exception e) {

			obj.put("errorMessage",
					"An error was encountered! Reply was not submitted!");
			objList.add(obj);
			obj2.put("listReplyDetails", objList);
			setInputStream(new ByteArrayInputStream(obj2.toJSONString()
					.getBytes()));
			notificationMessage = "An error was encountered! Reply was not submitted!";
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String reportcomment() throws Exception, IOException {return null;}

	@SuppressWarnings("unchecked")
	public String addtofavorite() throws Exception, IOException {return null;}

	@SuppressWarnings("unchecked")
	public String addtofavoritepage() throws Exception, IOException {return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String addtorecommend() throws Exception, IOException {return SUCCESS;
	}
	
	public String addtocomparelist(){return SUCCESS;
	}
	
	public Long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	@SuppressWarnings("unchecked")
	public String register_spcmc() throws Exception, IOException {
		JSONArray objList = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		try {

			//aaa
			
			initMemberInformation();
			if (!isMemberNew()) {
				obj.put("errorMessage", "Username already exists.");
				objList.add(obj);
				obj2.put("listSignupDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
				return SUCCESS;
			}
			initializeMemberInformation();

			initializeOtherInfo();
			final String kaptchaReceived = request.getParameter("kaptcha");
			if (kaptchaReceived != null) {
				final String kaptchaExpected = (String) request
						.getSession()
						.getAttribute(
								com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

				if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
					obj.put("errorMessage", "Invalid characters in image.");
					objList.add(obj);
					obj2.put("listSignupDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));
					return SUCCESS;
				}
			}
			
			if (isNull(memberDelegate.insert(member))) {
				obj.put("errorMessage", "Member was not created.");
				objList.add(obj);
				obj2.put("listSignupDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));
				return SUCCESS;
			}
			
			logger.info("username: " + username + " | company: "
					+ company.getName());
			final Member member = memberDelegate.findAccount(username, company);

			sendEmail(member);
			sendEmailToCompanyAccount(member);

		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.put("success", true);
		obj.put("member_id_", String.valueOf(member.getId()));
		objList.add(obj);
		obj2.put("listSignupDetails", objList);
		setInputStream(new ByteArrayInputStream(obj2.toJSONString()
				.getBytes()));
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String loginSpcmc() throws Exception, IOException {
		try {

			JSONArray objList = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			String resultExecution = execute();
			if (resultExecution.equals(SUCCESS)) {

				if (member != null) {
					obj.put("success", true);
					obj.put("member_id_", String.valueOf(member.getId()));
					objList.add(obj);
					obj2.put("listLoginDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));

					notificationMessage = "Successfully log in.";
				} else {
					obj.put("errorMessage", "Invalid Username / Password");
					objList.add(obj);
					obj2.put("listLoginDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));

					System.out.println(obj2.toJSONString());
					notificationMessage = "Invalid Username / Password.";
				}
			} else if (resultExecution.equals(INACTIVE)) {
				obj.put("errorMessage", "This account is not yet activated.");
				objList.add(obj);
				obj2.put("listLoginDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));

				notificationMessage = "An error was occured while processing your log in credentials.";
			} else if (resultExecution.equals(ERROR)) {
				obj.put("errorMessage", notificationMessage);
				objList.add(obj);
				obj2.put("listLoginDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));

				notificationMessage = "An error was occured while processing your log in credentials.";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String loginMundipharmaGem() throws Exception, IOException {
		try {
			JSONArray objList = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject obj2 = new JSONObject();
			String resultExecution = execute();
			if (resultExecution.equals(SUCCESS)) {

				if (member != null) {
					obj.put("success", true);
					obj.put("member_id_", String.valueOf(member.getId()));
					obj.put("member", member.getFirstname() + member.getLastname());
					obj.put("memberWithEmail", member.getLastname() + ", " + member.getFirstname() + "==" +member.getEmail());
					objList.add(obj);
					obj2.put("listLoginDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));

					notificationMessage = "Successfully log in.";
				} else {
					obj.put("errorMessage", "Invalid Username / Password");
					objList.add(obj);
					obj2.put("listLoginDetails", objList);
					setInputStream(new ByteArrayInputStream(obj2.toJSONString()
							.getBytes()));

					System.out.println(obj2.toJSONString());
					notificationMessage = "Invalid Username / Password.";
				}
			} else if (resultExecution.equals(INACTIVE)) {
				obj.put("errorMessage", "This account is not yet activated.");
				objList.add(obj);
				obj2.put("listLoginDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));

				notificationMessage = "An error was occured while processing your log in credentials.";
			} else if (resultExecution.equals(ERROR)) {
				obj.put("errorMessage", notificationMessage);
				objList.add(obj);
				obj2.put("listLoginDetails", objList);
				setInputStream(new ByteArrayInputStream(obj2.toJSONString()
						.getBytes()));

				notificationMessage = "An error was occured while processing your log in credentials.";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String loginByAjax() {
		try {
			JSONObject obj = new JSONObject();
			String resultExecution = execute();
			if (resultExecution.equals(SUCCESS)) {
				if (member != null) {
					obj.put("success", true);
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				} else {
					obj.put("message", "Invalid Username / Password");
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				}
			} else if (resultExecution.equals(ERROR)) {
				obj.put("message", notificationMessage);
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String loginBlueLiveToFeel() {
		try {
			JSONObject obj = new JSONObject();
			String resultExecution = execute();
			if (resultExecution.equals(SUCCESS)) {
				if (member != null) {
					obj.put("success", true);
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				} else {
					obj.put("message", "Invalid Username / Password");
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
				}
			} else if (resultExecution.equals(ERROR)) {
				obj.put("message", notificationMessage);
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String loginPanasonic() {
		try {
			JSONObject obj = new JSONObject();
			username = request.getParameter("username");
			password = request.getParameter("password");
			String resultExecution = execute();
			successUrl = request.getParameter("successUrl");
			errorUrl = request.getParameter("errorUrl");
			if (resultExecution.equals(SUCCESS)) {
				if (member != null) {
					/*obj.put("success", true);
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));*/
					return SUCCESS;
				} else {
					/*obj.put("message", "Invalid Username / Password");
					setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));*/
					errorUrl += "invalid";
					return ERROR;
				}
			} else if (resultExecution.equals(ERROR)) {
				/*obj.put("message", notificationMessage);
				setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));*/
				errorUrl += "invalid";
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getPageModule() {
		return pageModule;
	}

	public void setPageModule(String pageModule) {
		this.pageModule = pageModule;
	}

	public String getPageModuleUsername() {
		return pageModuleUsername;
	}

	public void setPageModuleUsername(String pageModuleUsername) {
		this.pageModuleUsername = pageModuleUsername;
	}
	
	public String logoutGurkka() throws Exception {
		final HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute("overEighteen", false);
		session.put("overEighteen", false);
		httpSession.invalidate();
		return Action.LOGIN;
	}
	
	public String redirectFromGurkkaToGoogle() throws Exception {
		final HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute("overEighteen", false);
		session.put("overEighteen", false);
		httpSession.invalidate();
		return SUCCESS;
	}
	
}
