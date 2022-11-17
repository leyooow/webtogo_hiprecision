package com.ivant.cms.action.registration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.text.*;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.PageImageDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.MemberFileItems;

import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.PasswordEncryptor;
import com.opensymphony.xwork2.Action;
/**
 * Responsible for user authentication.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class memberRegistration extends AbstractBaseAction {
	
	CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();

	private Logger logger = Logger.getLogger(memberRegistration.class);
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
	
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	private SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
	private EventDelegate eventDelegate = EventDelegate.getInstance();
	private FormPageDelegate formPageDelegate = FormPageDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private CompanySettings companySettings;
	
	/** Object responsible for member shipping info CRUD tasks */
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	/** Utility for encrypting and decrypting password */
	private PasswordEncryptor encryptor;
	
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

	 private String middlename;
	
	
	private String landline;
	private String mobile;
	private String reg_companyName;
	private String returnThisMessage="";



	private String reg_companyAddress;
	private String reg_companyPosition;



	private int yearOfMembership;
	private List<Event> events;

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
	
		logger.debug("LOGGING IN...");
		logger.debug("COMPANY: " + company);
		
		
		
		if(!isNull(username) && !isNull(password)) {
			
			//get user account
			encryptor = new PasswordEncryptor();
			logger.info("username " + username);
			logger.info("password " + password);
			logger.info("company " + company);
			member = memberDelegate.findAccount(username, 
					encryptor.encrypt(password), company);
			logger.info("ENCRYPTED PASSWORD: " + encryptor.encrypt(password));
			logger.info("MEMBER " + member);
			if(!isNull(member)) {
				if(member.getCompany().getId() != company.getId()){
					this.setNotificationMessage("Invalid Username.");
					return ERROR;
				}
				
				if (member.getActivated() == false){
					logger.debug(username);
					//System.out.println("INACTIVE");
					
					return "inactive";
				}
				
				//update user login information
				member.setLastLogin(new Date());
				memberDelegate.update(member);
				session.put(MemberInterceptor.MEMBER_SESSION_KEY, member.getId());
				
				//add user to session
				session.put(UserInterceptor.USER_SESSION_KEY, member.getId());
				//System.out.println("SUCCESS");
				return SUCCESS;
			}
			//----------------------------------------------------------------------------------
			else {
				this.setNotificationMessage("Invalid Username / Password");
				return ERROR;
			}
		} 
		
		
		
		if(isValidUsernameInput(username) || isValidPasswordInput(password)){
			addActionError("Invalid Username / Password");
		}
		//System.out.println("INPUT");
		return INPUT;
		
		
		
	}
	
	@Override
	public void prepare() throws Exception {
		companySettings = company.getCompanySettings();		
		//initialize login values
		
		initLoginInformation();
		loadAllRootCategories();
		//populate server URL to be redirected to
		initHttpServerUrl();
				
		loadFeaturedPages(companySettings.getMaxFeaturedPages());
				
		loadMenu();
		
	}
	
	/**
	 * Returns {@code SUCCESS} when action is called.
	 * 
	 * @return - {@code SUCCESS} when action is called. 
	 */
	public String newuser(){
		//do nothing	
		return SUCCESS;
		}	
	private void loadAllRootCategories()
	{
		Order[] orders = {Order.asc("sortOrder")};
		List<Category> rootCategories = categoryDelegate.findAllRootCategories(company,true,orders).getList();
		request.setAttribute("rootCategories", rootCategories);		
	}
	/**
	 * Registers a new member. Returns SUCCESS if new member successfully registers,
	 * otherwise ERROR.
	 * 
	 * @return - SUCCESS if new member successfully registers, otherwise ERROR
	 */
	public String update(){
		initMemberInformation();
		initializeMemberInformation();
		Member checkUsername=memberDelegate.findAccount(username, company);
		Member checkEmail=memberDelegate.findEmail(company, email);
		
		//System.out.println("THE VALIDATION IS "+(checkUsername!=null&&checkUsername.getId()==member.getId()&&checkUsername!=null&&checkUsername.getId()==member.getId()));
		
		if(checkUsername!=null&&checkUsername.getId()!=member.getId())
		{
			//System.out.println("--NOT SUCCESSFULLY UPDATED");
			this.returnThisMessage="this username is not available";
				request.setAttribute("member_1", member);
				request.setAttribute("errorMessageInvalidEmailAddress",this.returnThisMessage);
			return ERROR;
		}
		if(checkEmail!=null&&checkEmail.getId()!=member.getId())
		{
			//System.out.println("--NOT SUCCESSFULLY UPDATED");
			this.returnThisMessage="this email already in used.";
				request.setAttribute("member_1", member);
				request.setAttribute("errorMessageInvalidEmailAddress",this.returnThisMessage);
			return ERROR;
		}

		member.setPassword(request.getParameter("password"));
		
		request.setAttribute("member_1", member);
		request.setAttribute("profile", "true");
		request.setAttribute("errorMessageInvalidEmailAddress","Account information was successfully updated");
		memberDelegate.update(member);
		//System.out.println("--SUCCESSFULLY UPDATED");
		return SUCCESS;
	}
	
	
	
	
	public String register(){		
		//System.out.println("Company name is--------------------- "+company.getName());
		
		if(email==null&&username==null&&password==null)
			return ERROR;
		//get user information specified information
		initMemberInformation();
		
		//populate new member information		
		initializeMemberInformation();
		
		//new members can only be added
		if(!isMemberNew()){
			request.setAttribute("errorMessageInvalidEmailAddress",this.returnThisMessage);
			request.setAttribute("member_1", member);
			return ERROR;
		}
			
			

		//insert new user		
		if(isNull(memberDelegate.insert(member))){
			request.setAttribute("errorMessageInvalidEmailAddress","Unexpected Error occur");
			return ERROR;
		}
		

		events = eventDelegate.findAll(company);
		request.setAttribute("events", events);
	    
		//send user registration notification through mail
		
		Member member = memberDelegate.findAccount(username, company);
		//not --APC--  
		if(!company.getName().equalsIgnoreCase("apc")){//for exception only
			if(company.getName().equalsIgnoreCase("westerndigital")){
				sendEmailWithFormPageContent(member,company);
			}else
				sendEmail(member);
			sendEmailToCompanyAccount(member);	
		}else{
			//FOR APC ONLY
			sendAccountVerification(member);
		}
		return SUCCESS;
	}
	
	private String sendEmailWithFormPageContent(Member member, Company company) {
		FormPage registrationEmail=formPageDelegate.find(company, company.getName()+"-registrationEmail");
		
		if (member != null && registrationEmail !=null){
			EmailUtil.connect("smtp.gmail.com", 25);
			StringBuffer content = new StringBuffer();
			content.append(registrationEmail.getTopContent());
			String activationlink = httpServer + "/activate.do?activation=" + member.getActivationKey();
			
			String emailMessage= content.toString();
			
			//reference for this is COMPANY-Westerndigital
			emailMessage=emailMessage.replace("(name of the user)", member.getFirstname()+" "+member.getLastname());
			emailMessage=emailMessage.replace("(member.username)", member.getUsername());
			emailMessage=emailMessage.replace("(member.password)", password);
			emailMessage=emailMessage.replace("(activationlink)", activationlink);
			
			
			//from, to , subject, content, attachment
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), 
					registrationEmail.getTitle(), emailMessage,null)){
				return Action.SUCCESS;
			}
		}
		return Action.ERROR; 
		
		
		
	}

	/**
	 * Returns {@code SUCCESS} if member account is successfully activated, 
	 * otherwise {@code ERROR}.
	 * 
	 * @return - {@code SUCCESS} if member account is successfully activated, 
	 * otherwise {@code ERROR}
	 */
	public String activate(){
		String activationKey;
		activationKey = request.getParameter("activation");
		Member member = memberDelegate.findActivationKey(activationKey); 
		if( member != null){
			member.setActivated(true);
			memberDelegate.update(member);
			return Action.SUCCESS;
		}
		return Action.ERROR;
	}
	
	public String sendAccountVerification(Member member) {	
		
		
		if (member != null){
			EmailUtil.connect("smtp.gmail.com", 25);

			StringBuffer content = new StringBuffer();
			content.append("Hi, <strong>" + member.getFirstname() + " " + member.getLastname()+"</strong><br>");
			content.append("Welcome to APC by Schneider Electric Salesman Awards Program<br>");
			content.append("<table width=\"20%\">");
			content.append("<tr>");
			content.append("<td colspan=\"2\">Account Information:</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td width=\"50%\">Username</td><td  width=\"50%\">"+member.getUsername()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td>Email</td><td>"+member.getEmail()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("	<td>Company Information:</td></td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td>Company Name</td><td>"+member.getReg_companyName()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("	<td>Company Address:</td><td>"+member.getReg_companyAddress()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("	<td></td><td></td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("	<td colspan=\"2\">Personal Information:</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("	<td>First Name:</td><td>"+member.getFirstname()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("	<td>Last Name:</td><td>"+member.getLastname()+"</td>");
			content.append("</tr>");
			content.append("</table>");
			content.append("<br><br><br>");
			content.append("Thank you for registering.<br>");
			content.append("<strong>The APC by Schneider Electric Salesman Awards Program Team</strong><br>");
			content.append("<font color=\"red\">");
			content.append("Please note that this is a system generated email. Do not reply to this message.");
			content.append("</font>");
			
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), 
					"Welcome to APC by Schneider Electric Salesman Awards Program"
					, content.toString().replaceAll("\n", "<br>"),null)){
				return Action.SUCCESS;
			}
		}
		
		return Action.ERROR; 
	}
	
	
	
	/**
	 * Returns {@code SUCCESS} if mail was successfully sent to the user, 
	 * otherwise {@code ERROR}.
	 * 
	 * @return - {@code SUCCESS} if mail was successfully sent to the user, 
	 * otherwise {@code ERROR}
	 */
	public String sendEmail(Member member) {	
		if (member != null){
			EmailUtil.connect("smtp.gmail.com", 25);
			String emailMessage = request.getParameter("emailMessage");
			StringBuffer content = new StringBuffer();
			content.append("Hi! " + member.getFirstname() + " " + member.getLastname() + ",\n\n" +
					"Welcome to "+company.getNameEditable()+"! ");
			if(emailMessage !=null)
			content.append(emailMessage);
			//System.out.println(emailMessage);
			content.append("\n\nPlease click on the link below to activate your account. \n\n");
			content.append(httpServer + "/activate.do?activation=" 
					+ member.getActivationKey());
			content.append("\n\n\nThank you for registering.\n\n\nAll the Best, \n\n");
			content.append("The "+company.getNameEditable()+" Team");
			if(EmailUtil.send("noreply@webtogo.com.ph", member.getEmail(), 
					"Welcome to " + company.getNameEditable() 
					+ "!", content.toString())){
				return Action.SUCCESS;
			}
		}
		return Action.ERROR; 
	}

	/**
	 * Populates new member database account information
	 */
	public void initializeMemberInformation() {
		
		if(member!=null){
			// this code is for update
		}else{
		//create new member
			member = new Member();
			member.setDateJoined(new Date());
		}
		//populate new member information
		member.setUsername(username);	
		member.setEmail(email);
		member.setFirstname(firstname);
		member.setLastname(lastname);
		member.setCompany(company);
		member.setNewsletter(newsletter==null ? false : true);
		reg_companyName=request.getParameter("reg_companyName");
		reg_companyAddress=request.getParameter("reg_companyAddress");
		member.setReg_companyPosition(reg_companyPosition);
		member.setReg_companyName(reg_companyName);
		member.setReg_companyAddress(reg_companyAddress);
		member.setLandline(landline);
		member.setMobile(mobile);
		member.setMiddlename(middlename);
		//encrypt given password
		encryptor = new PasswordEncryptor();
		member.setPassword(encryptor.encrypt(password));
		logger.info("SAVED ENCRYPTED PASSWORD: " + encryptor.encrypt(password));
		//System.out.println("THE encripted PASSWORD IS "+member.getPassword());
		//System.out.println("THE DECRIPTED PASSWORD IS "+encryptor.decrypt(member.getPassword()));
		//initialize activation key
		//member.setActivationKey(activationKey);
		if(company.getName().equalsIgnoreCase("apc"))
			member.setActivated(true);
		else{
		//	member.setActivationKey(Encryption.hash(company + email + username + password));
		//	member.setActivated(false);
			member.setActivated(true);
		}
	}

	/**
	 * Returns true if does not exist in local database, otherwise false.
	 * 
	 * @return - true if does not exist in local database, otherwise false
	 */
	private boolean isMemberNew() {
		//check for username duplicates
		if(usernameExists()){
			this.returnThisMessage="Registration was not successful, Reason:(Username already exists.)";
			addActionError("Username already exists.");
			return false;
		}
		
		//check for email duplicates
		if(emailExists()){
			this.returnThisMessage="Registration was not successful, Reason:(Email address already used.)";
			addActionError("Email address already used.");
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
		Member mem=memberDelegate.findAccount(username, company);
		if(mem!=null && mem.getCompany().getName().equalsIgnoreCase(company.getName())){
			return true;
		}
		return false;
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
	public void initMemberInformation() {
		initLoginInformation();
		email = request.getParameter("email");
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		landline = request.getParameter("landline");
		middlename = request.getParameter("middlename");
		reg_companyPosition=request.getParameter("reg_companyPosition");
		Calendar cal = Calendar.getInstance();
		yearOfMembership = cal.get(Calendar.YEAR);
		request.setAttribute("email",email );
		request.setAttribute("firstname",firstname);
		request.setAttribute("lastname",lastname);
	}

	/**
	 * Initialize username and password.
	 */
	private void initLoginInformation() {
		username = request.getParameter("username");
		password = request.getParameter("password");
		request.setAttribute("username",username);
		request.setAttribute("password",password);
	}
	
	/**
	 * Returns true if input specified username is valid, otherwise false.
	 * A valid username must not be null and must not be empty.
	 * 
	 * @param username
	 * 			- username for user account, must not be null
	 * 
	 * @return - true if input specified username is valid, otherwise false
	 */
	public boolean isValidUsernameInput(String username){
		if(null == username || username.length() == 0)
			return false;
		
		return true;
	}
	
	/**
	 * Returns true if input specified password is valid, otherwise false.
	 * A valid password must not be null and must not be empty.
	 * 
	 * @param password
	 * 			- password for user account, must not be null
	 * 
	 * @return - true if input specified password is valid, otherwise false
	 */
	public boolean isValidPasswordInput(String password){
		if(null == password || password.length() == 0)
			return false;
		
		return true;
	}
	
	public String getNotificationMessage() {
		return notificationMessage;
	}
	
	private void loadMenu() {
		try {
			Map<String, Menu> menuList = new HashMap<String, Menu>();

			// get the single pages
			List<SinglePage> singlePageList = singlePageDelegate.findAll(company).getList();
			for(SinglePage singlePage : singlePageList) {
				String jspName = singlePage.getJsp(); 
				Menu menu = new Menu(singlePage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(singlePage.getJsp(), menu);
			}
			
			// get the multi pages
			List<MultiPage> multiPageList = multiPageDelegate.findAll(company).getList();
			for(MultiPage multiPage : multiPageList) {
				String jspName = multiPage.getJsp();
				Menu menu = new Menu(multiPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(multiPage.getJsp(), menu);
			}
			
			// get the form Pages
			List<FormPage> formPageList = formPageDelegate.findAll(company).getList();
			for(FormPage formPage : formPageList) {
				String jspName = formPage.getJsp();
				Menu menu = new Menu(formPage.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(formPage.getJsp(), menu);
			}
			
			// get the groups
			List<Group> groupList = groupDelegate.findAll(company).getList();
			for(Group group : groupList) {
				String jspName = group.getName().toLowerCase();
				Menu menu = new Menu(group.getName(), httpServer + "/" + jspName + ".do");
				menuList.put(jspName, menu);
			}
			
			// get the link to the allowed pages
			for(String s: ALLOWED_PAGES) {
				String jspName = s.toLowerCase();
				Menu menu = new Menu(jspName, httpServer + "/" + jspName + ".do"); 
				menuList.put(jspName, menu);
			}
			
			request.setAttribute("menu", menuList); 
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action in FrontMenuInterceptor. " + e);
		}
	}
	
	private void loadFeaturedPages(int max) {
		Map<String, Object> featuredPages = new HashMap<String, Object>();
		List<MultiPage> featuredMultiPage = multiPageDelegate.findAllFeatured(company).getList();
		
		for(MultiPage mp : featuredMultiPage) {
			if(!mp.getHidden()) {
				featuredPages.put(mp.getName(), mp);
			}
		}
		
		//System.out.println("size:::: " + featuredMultiPage.size());
		request.setAttribute("featuredPages", featuredPages);
	}
		
	private String sendEmailToCompanyAccount(Member member) {
		
		String[] to = company.getEmail().split(",");
		String newsletter = request.getParameter("newsletter");
		
		//System.out.println("newsletter-----------" + newsletter);
		
		if (member != null){
			EmailUtil.connect("smtp.gmail.com", 25); 
			
			StringBuffer content = new StringBuffer();
			content.append("The following are user details for Downloads Section: \n\n");
			content.append("Firstname: " + member.getFirstname() + "\n\n" + 
					       "Lastname: " + member.getLastname() + "\n\n" +
					       "Email: " + member.getEmail() + "\n\n" +
					       "Username: " + member.getUsername() + "\n\n"	
					       );		
							if(newsletter != null  && !newsletter.isEmpty()){
							content.append("Subscribe to Newsletter: Yes \n\n");
							}
							else{
							content.append("Subscribe to Newsletter: No \n\n");	
							}
			if(EmailUtil.send("noreply@webtogo.com.ph", to, "Download Section Signup Submission", content.toString(), null)){
				return Action.SUCCESS;
			}
		}
		
		return Action.ERROR; 
		
	}

	
	
	/**
	 * @param myEmail the myEmail to set
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
	public String getReg_companyName() {
		return reg_companyName;
	}

	public void setReg_companyName(String reg_companyName) {
		this.reg_companyName = reg_companyName;
	}

	public String getReg_companyAddress() {
		return reg_companyAddress;
	}

	public void setReg_companyAddress(String reg_companyAddress) {
		this.reg_companyAddress = reg_companyAddress;
	}
	
	public String getReg_companyPosition() {
		return reg_companyPosition;
	}

	public void setReg_companyPosition(String reg_companyPosition) {
		this.reg_companyPosition = reg_companyPosition;
	}
	

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

}

