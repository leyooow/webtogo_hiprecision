/**
 *
 */
package com.ivant.cms.action.registration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.beans.KuysenClientBean;
import com.ivant.cms.beans.KuysenSalesTransactionBean;
import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberLogDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.entity.Category;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.constants.KuysenSalesConstants;
import com.ivant.utils.CompanyUtil;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.StringUtil;

/**
 * @author Edgar S. Dacpano
 *
 */
public class NeltexQuoteSystemRegistrationAction
		extends AbstractBaseAction
{
	private static final long serialVersionUID = 1L;
	
	/** Delegates */
	private final CompanySettingsDelegate companySettingsDelegate = CompanySettingsDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final MemberLogDelegate memberLogDelegate = MemberLogDelegate.getInstance();
	private final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private final RegistrationItemOtherFieldDelegate registrationItemOtherFieldDelegate = RegistrationItemOtherFieldDelegate.getInstance();
	private final CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	
	private final PasswordEncryptor encryptor = new PasswordEncryptor();
	
	private KuysenSalesTransactionBean kuysenSalesTransactionBean;
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy");
	
	/**
	 * K: Display Name
	 */
	private Map<String, String> memberInfoMap;
	
	private String kaptcha;
	private String activation;
	
	private String username;
	private String password;
	private String passwordConfirm;
	private String firstName;
	private String lastName;
	private String email;
	private String position;
	private String employeeNumber;
	private String contactNumber;
	
	//General Information
		
		//Client
		private String ref;
		private String date;
		private String clientName;
		private String clientAddress;
		private String clientEmail;
		private String clientTelephone;
		private String clientFax;
		private String clientMobile;
		private String deliveryAddress;
		private String status;
	
		//Contact Person
		private String contactPersonName;
		private String contactPersonEmail;
		private String contactPersonTelephone;
		private String contactPersonFax;
		private String contactPersonMobile;
		
	//Other Information
	
		//Architect
		private String architectName;
		private String architectEmail;
		private String architectContact;
		private String architectContractor;
		
		//Interior Designer
		private String interiorDesignerName;
		private String interiorDesignerEmail;
		private String interiorDesignerContact;
		
		//How did you hear about us
		private Boolean isOldClient;
		private Boolean isReferredBy;
		private String referredByName;
		private Boolean isByAdvertisingMaterial;
		private Boolean isByOthers;
		
		private String registrationPathConfirmed;
	
	public NeltexQuoteSystemRegistrationAction()
	{
		super();
	}
	
	@Override
	public void prepare() throws Exception
	{
		logger.info("prepare in: " + getClass().getSimpleName());
		initHttpServerUrl();
		CompanyUtil.loadMenu(company, httpServer, request, null, logger, servletContext, isLocal);
	}
	
	@Override
	public String execute() throws Exception
	{
		logger.info("execute in: " + getClass().getSimpleName());
		
		String result = super.execute();
		
		if(member == null)
		{
			setNotificationMessage("Please login.");
			result = LOGIN;
		}
		
		return result;
	}
	
	public String login()
	{
		logger.info("login in " + getClass().getSimpleName());
		if(StringUtil.areNotEmpty(getUsername(), getPassword()))
		{
			final String encryptedPassword = encryptor.encrypt(getPassword());
			
			member = memberDelegate.findAccount(getUsername(), encryptedPassword, company);
			if(member != null)
			{
				if(!member.getActivated())
				{
					setNotificationMessage("Account is not yet activated.");
					return ERROR;
				}
				
				member.setLastLogin(new Date());
				if(memberDelegate.update(member))
				{
					final MemberLog memberLog = new MemberLog();
					memberLog.setCompany(company);
					memberLog.setMember(member);
					memberLog.setRemarks("Logged In");
					
					memberLogDelegate.insert(memberLog);
					
					session.put(MemberInterceptor.MEMBER_SESSION_KEY, member.getId());
					session.put(UserInterceptor.USER_SESSION_KEY, member.getId());
					
					loadMemberInfoMap(member);
					
					return SUCCESS;
				}
			}
			else
			{
				setNotificationMessage("Invalid Username / Password");
				return ERROR;
			}
		}
		
		return LOGIN;
	}
	
	public String register()
	{
		String actionError = null;
		try
		{
			if(!StringUtils.equalsIgnoreCase(password, passwordConfirm))
			{
				actionError = "Password do not match.";
			}
			else if(StringUtils.isNotEmpty(kaptcha))
			{
				final String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
				if(kaptcha.equalsIgnoreCase(kaptchaExpected))
				{
					final Member usernameMember = memberDelegate.findAccount(username, company);
					final Member emailMember = memberDelegate.findEmail(company, email);
					
					if(usernameMember != null)
					{
						actionError = "Username already exists.";
					}
					else if(emailMember != null)
					{
						actionError = "Email address already used.";
					}
					else if(StringUtil.areNotEmpty(username, email, password))
					{
						member = new Member();
						member.setCompany(company);
						
						member.setUsername(username);
						member.setPassword(encryptor.encrypt(password));
						member.setEmail(email);
						member.setNewsletter(false);
						member.setActivationKey(Encryption.hash(company + email + username + password));
						member.setActivated(false);
						member.setDateJoined(new Date());
						
						member.setFirstname(firstName);
						member.setLastname(lastName);
						member.setMobile(contactNumber);
						
						member.setReg_companyPosition(position);
						member.setInfo1(employeeNumber);
						
						final Long id = memberDelegate.insert(member);
						if(id != null)
						{
							final Member member = memberDelegate.find(id);
							if(member != null)
							{
								sendEmailToMember(member);
								sendEmailToCompany(member);
							}
						}
					}
					else
					{
						actionError = "Some fields are empty";
					}
				}
				else
				{
					actionError = "Invalid characters in captcha image.";
				}
			}
			else
			{
				return NONE;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actionError = "Registration error.";
		}
		if(StringUtils.isNotEmpty(actionError))
		{
			addActionError(actionError);
			return ERROR;
		}
		else
		{
			return SUCCESS;
		}
	}
	
	/**
	 * Activation for the member
	 * 
	 * @return
	 */
	public String activate()
	{
		try
		{
			final Member member = memberDelegate.findActivationKey(activation);
			if(member != null)
			{
				member.setActivated(true);
				memberDelegate.update(member);
				request.setAttribute("notificationMessage", "Your account is activated. Please login to continue.");
			}
			else
			{
				request.setAttribute("notificationMessage", "Invalid activation code.");
			}
			return SUCCESS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			addActionError("Error activating account.");
			return ERROR;
		}
	}
	
	private void loadMemberInfoMap(Member member)
	{
		if(member != null)
		{
			if(getMemberInfoMap() == null)
			{
				setMemberInfoMap(new HashMap<String, String>());
			}
			
			memberInfoMap.put("First Name", member.getFirstname());
			memberInfoMap.put("Last Name", member.getLastname());
			memberInfoMap.put("Email", member.getEmail());
			memberInfoMap.put("Positon", member.getReg_companyPosition());
			memberInfoMap.put("Employee Number", member.getInfo1());
			memberInfoMap.put("Contact Information", member.getMobile());
			memberInfoMap.put("Username", member.getUsername());
		}
	}
	
	/**
	 * Sends an email to the member with the activation code.
	 * 
	 * @param member
	 */
	private void sendEmailToMember(Member member)
	{
		try
		{
			if(member != null)
			{
				final String to = member.getEmail();
				final String subject = member.getCompany().getNameEditable() + " Employee/Member Registration";
				final String memberName = member.getFirstname() + " " + member.getLastname();
				final String nextLine = " \r\n\r\n";
				
				final StringBuffer content = new StringBuffer();
				content.append("Hi " + memberName + "," + nextLine);
				content.append("<br/><br/> Welcome to " + company.getNameEditable() + "!,");
				content.append("<br/> Please click on the link below to activate your account.");
				content.append(httpServer + "/activate.do?activation=" + member.getActivationKey());
				content.append("<br/><br/> Thank you for registering.");
				content.append("<br/> All the Best,");
				content.append("<br/> The " + company.getNameEditable() + " Team");
				
				if(!StringUtils.isEmpty(content.toString()))
				{
					EmailUtil.connectViaCompanySettings(member.getCompany());
					EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, subject, content.toString(), null);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends email to the company if there is a newly registered member.
	 * 
	 * @param member
	 */
	private void sendEmailToCompany(Member member)
	{
		try
		{
			EmailUtil.connectViaCompanySettings(company);
			final String[] to = company.getEmail().split(",");
			if(member != null)
			{
				StringBuffer content = new StringBuffer();
				content.append("The following are the details for the newly registered Member: \r\n\r\n");
				
				content.append("Firstname: " + member.getFirstname() + "\r\n\r\n");
				content.append("Lastname: " + member.getLastname() + "\r\n\r\n");
				content.append("Email: " + member.getEmail() + "\r\n\r\n");
				content.append("Username: " + member.getUsername() + "\r\n\r\n");
				
				EmailUtil.send("noreply@webtogo.com.ph", to, company.getNameEditable() + " Member Registration", content.toString(), null);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String register_client() {
		
		KuysenClientBean kuysenClientBean = null;
		
		if(!StringUtils.isNotBlank(registrationPathConfirmed)) {
			setNotificationMessage("Invalid page access");
			return ERROR;
		}
		
		if(kuysenSalesTransactionBean == null) {
			kuysenSalesTransactionBean = new KuysenSalesTransactionBean();
		}
		
		if(kuysenSalesTransactionBean.getClient() == null) {
			kuysenClientBean = new KuysenClientBean();
		} else {
			kuysenClientBean = kuysenSalesTransactionBean.getClient();
		}
		
		kuysenClientBean.setRef(ref);
		kuysenClientBean.setDate(formatter.parseDateTime(date));
		kuysenClientBean.setClientName(clientName);
		kuysenClientBean.setClientAddress(clientAddress);
		kuysenClientBean.setClientEmail(clientEmail);
		kuysenClientBean.setClientTelephone(clientTelephone);
		kuysenClientBean.setClientFax(clientFax);
		kuysenClientBean.setClientMobile(clientMobile);
		kuysenClientBean.setDeliveryAddress(deliveryAddress);
		kuysenClientBean.setContactPersonName(contactPersonName);
		kuysenClientBean.setContactPersonEmail(contactPersonEmail);
		kuysenClientBean.setContactPersonTelephone(contactPersonTelephone);
		kuysenClientBean.setContactPersonFax(contactPersonFax);
		kuysenClientBean.setContactPersonMobile(contactPersonMobile);
		kuysenClientBean.setStatus(status);
		kuysenClientBean.setArchitectName(architectName);
		kuysenClientBean.setArchitectEmail(architectEmail);
		kuysenClientBean.setArchitectContractor(architectContractor);
		kuysenClientBean.setArchitectContact(architectContact);
		kuysenClientBean.setInteriorDesignerName(interiorDesignerName);
		kuysenClientBean.setInteriorDesignerEmail(interiorDesignerEmail);
		kuysenClientBean.setInteriorDesignerContact(interiorDesignerContact);
		
		kuysenClientBean.setIsByAdvertisingMaterial(isByAdvertisingMaterial);
		kuysenClientBean.setIsReferredBy(isReferredBy);
		if(isReferredBy != null && isReferredBy) {
			kuysenClientBean.setReferredByName(referredByName);
		}
		kuysenClientBean.setIsOldClient(isOldClient);
		kuysenClientBean.setIsByOthers(isByOthers);
		
		kuysenSalesTransactionBean.setClient(kuysenClientBean);
		session.put(KuysenSalesConstants.KUYSEN_TRANSACTION, kuysenSalesTransactionBean);
		
		return SUCCESS;
	}
	
	// GETTERS
	public Map<String, String> getMemberInfoMap()
	{
		return memberInfoMap;
	}
	
	public String getKaptcha()
	{
		return kaptcha;
	}
	
	public String getActivation()
	{
		return activation;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getPasswordConfirm()
	{
		return passwordConfirm;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPosition()
	{
		return position;
	}
	
	public String getEmployeeNumber()
	{
		return employeeNumber;
	}
	
	public String getContactNumber()
	{
		return contactNumber;
	}
	
	// SETTERS
	public void setMemberInfoMap(Map<String, String> memberInfoMap)
	{
		this.memberInfoMap = memberInfoMap;
	}
	
	public void setKaptcha(String kaptcha)
	{
		this.kaptcha = kaptcha;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setPasswordConfirm(String passwordConfirm)
	{
		this.passwordConfirm = passwordConfirm;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setPosition(String position)
	{
		this.position = position;
	}
	
	public void setEmployeeNumber(String employeeNumber)
	{
		this.employeeNumber = employeeNumber;
	}
	
	public void setContactNumber(String contactNumber)
	{
		this.contactNumber = contactNumber;
	}
	
	public void setActivation(String activation)
	{
		this.activation = activation;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getClientTelephone() {
		return clientTelephone;
	}

	public void setClientTelephone(String clientTelephone) {
		this.clientTelephone = clientTelephone;
	}

	public String getClientFax() {
		return clientFax;
	}

	public void setClientFax(String clientFax) {
		this.clientFax = clientFax;
	}

	public String getClientMobile() {
		return clientMobile;
	}

	public void setClientMobile(String clientMobile) {
		this.clientMobile = clientMobile;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}

	public String getContactPersonTelephone() {
		return contactPersonTelephone;
	}

	public void setContactPersonTelephone(String contactPersonTelephone) {
		this.contactPersonTelephone = contactPersonTelephone;
	}

	public String getContactPersonFax() {
		return contactPersonFax;
	}

	public void setContactPersonFax(String contactPersonFax) {
		this.contactPersonFax = contactPersonFax;
	}

	public String getContactPersonMobile() {
		return contactPersonMobile;
	}

	public void setContactPersonMobile(String contactPersonMobile) {
		this.contactPersonMobile = contactPersonMobile;
	}

	public String getArchitectName() {
		return architectName;
	}

	public void setArchitectName(String architectName) {
		this.architectName = architectName;
	}

	public String getArchitectEmail() {
		return architectEmail;
	}

	public void setArchitectEmail(String architectEmail) {
		this.architectEmail = architectEmail;
	}

	public String getArchitectContact() {
		return architectContact;
	}

	public void setArchitectContact(String architectContact) {
		this.architectContact = architectContact;
	}

	public String getArchitectContractor() {
		return architectContractor;
	}

	public void setArchitectContractor(String architectContractor) {
		this.architectContractor = architectContractor;
	}

	public String getInteriorDesignerName() {
		return interiorDesignerName;
	}

	public void setInteriorDesignerName(String interiorDesignerName) {
		this.interiorDesignerName = interiorDesignerName;
	}

	public String getInteriorDesignerEmail() {
		return interiorDesignerEmail;
	}

	public void setInteriorDesignerEmail(String interiorDesignerEmail) {
		this.interiorDesignerEmail = interiorDesignerEmail;
	}

	public String getInteriorDesignerContact() {
		return interiorDesignerContact;
	}

	public void setInteriorDesignerContact(String interiorDesignerContact) {
		this.interiorDesignerContact = interiorDesignerContact;
	}

	public Boolean getIsOldClient() {
		return isOldClient;
	}

	public void setIsOldClient(Boolean isOldClient) {
		this.isOldClient = isOldClient;
	}

	public Boolean getIsReferredBy() {
		return isReferredBy;
	}

	public void setIsReferredBy(Boolean isReferredBy) {
		this.isReferredBy = isReferredBy;
	}

	public String getReferredByName() {
		return referredByName;
	}

	public void setReferredByName(String referredByName) {
		this.referredByName = referredByName;
	}

	public Boolean getIsByAdvertisingMaterial() {
		return isByAdvertisingMaterial;
	}

	public void setIsByAdvertisingMaterial(Boolean isByAdvertisingMaterial) {
		this.isByAdvertisingMaterial = isByAdvertisingMaterial;
	}

	public Boolean getIsByOthers() {
		return isByOthers;
	}

	public void setIsByOthers(Boolean isByOthers) {
		this.isByOthers = isByOthers;
	}

	public CompanySettingsDelegate getCompanySettingsDelegate() {
		return companySettingsDelegate;
	}

	public String getRegistrationPathConfirmed() {
		return registrationPathConfirmed;
	}

	public void setRegistrationPathConfirmed(String registrationPathConfirmed) {
		this.registrationPathConfirmed = registrationPathConfirmed;
	}
	
	public KuysenClientBean getClient() {
		return this.kuysenSalesTransactionBean.getClient();
	}
	
	public List<CategoryItem> getClientStatus() {
		List<Category> categories = categoryDelegate.findAll(company).getList();
		List<CategoryItem> client_status = null;

		for(Category category : categories) {
			System.out.println(category.getName() + " this is category name");
			if(category.getName().equalsIgnoreCase("Client Status")) {
				for(CategoryItem cat : category.getItems()) {
					System.out.println(cat.getName() + "this is category item name");
				}
				client_status = category.getItems();
			}
		}
		
		return client_status;
	}
	

	public void setKuysenSalesTransactionBean(KuysenSalesTransactionBean kuysenSalesTransactionBean) {
		this.kuysenSalesTransactionBean = kuysenSalesTransactionBean;
	}
	
	public Boolean getLoginMember() {
		return session.containsKey(MemberInterceptor.MEMBER_SESSION_KEY);
	}
}
