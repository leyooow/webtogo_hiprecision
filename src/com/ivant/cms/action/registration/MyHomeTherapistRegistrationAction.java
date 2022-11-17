/**
 *
 */
package com.ivant.cms.action.registration;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.hibernate.criterion.Restrictions;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.delegate.CompanySettingsDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberLogDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.constants.MyHomeTherapistConstants;
import com.ivant.utils.CompanyUtil;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.ivant.utils.PasswordEncryptor;

/** @author Edgar S. Dacpano */
public class MyHomeTherapistRegistrationAction extends AbstractBaseAction
{
	
	/** Serialization purposes. */
	private static final long serialVersionUID = 1L;
	
	/** Delegates */
	private final CompanySettingsDelegate companySettingsDelegate = CompanySettingsDelegate.getInstance();
	private final GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final MemberLogDelegate memberLogDelegate = MemberLogDelegate.getInstance();
	private final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private final RegistrationItemOtherFieldDelegate registrationItemOtherFieldDelegate = RegistrationItemOtherFieldDelegate.getInstance();
	
	private final Logger logger = Logger.getLogger(MyHomeTherapistRegistrationAction.class);
	private final PasswordEncryptor encryptor = new PasswordEncryptor();
	
	private final String inactive = "inactive";
	
	private final String imageFolder = MyHomeTherapistConstants.IMAGE_ATTACHMENT_FOLDER;
	private final String thumbnailImageFolder = MyHomeTherapistConstants.IMAGE_THUMBNAIL_ATTACHMENT_FOLDER;
	private final String thumbnailToken = MyHomeTherapistConstants.IMAGE_THUMBNAIL_TOKEN;
	
	/** 
	 * K: The {@link OtherField#getName()} (parent of {@link RegistrationItemOtherField}). <br>
	 * V: {@link RegistrationItemOtherField#getContent()}  
	 */
	private final Map<String, String> registrationItemOtherFieldsMap = new HashMap<String, String>();;
	
	private Set<String> registrationOtherFields = new HashSet<String>(); 
	{
		/** Basic fields */
		//registrationOtherFields.add("profession");
		registrationOtherFields.add("license");
		registrationOtherFields.add("licenseValidUntil");
		registrationOtherFields.add("school");
		registrationOtherFields.add("yearGraduated");
		registrationOtherFields.add("coursesAttended");
		registrationOtherFields.add("personalDescription");
		//registrationOtherFields.add("schedule");
		//registrationOtherFields.add("province");
		
		/** Multi-Part fields */
		registrationOtherFields.add("photo");
	};
	
	/**
	 * <b>K: the name of the field/property of {@link OtherField#name}</b> <br>
	 * <b>V: the default value of the key.</b>
	 * 
	 * @return <tt>{@literal HashMap<String, Object>}</tt>
	 */
	//private final Map<String, Map<FormType, String[]>> registrationFieldsMap = new LinkedHashMap<String, Map<FormType, String[]>>();
	
	private final Set<String> professions = new TreeSet<String>(Arrays.asList(MyHomeTherapistConstants.PROFESSIONS));
	private final Set<String> schedules = new TreeSet<String>(Arrays.asList(MyHomeTherapistConstants.SCHEDULES));
	private final Set<String> locations = new TreeSet<String>(Arrays.asList(MyHomeTherapistConstants.LOCATIONS));
	
	@Override
	public void prepare() throws Exception
	{
		initHttpServerUrl();
		CompanyUtil.loadMenu(company, httpServer, request, null, logger, servletContext, isLocal);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception
	{
		final String username = request.getParameter("username");
		final String passwordParameter = request.getParameter("password");
		final String password = 
			StringUtils.isNotEmpty(passwordParameter)
			? encryptor.encrypt(passwordParameter)
			: null;
			
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password))
		{
			member = memberDelegate.findAccount(username, password, company);
			if(member != null)
			{
				if(!member.getActivated())
				{
					setNotificationMessage("Account is not yet activated. Please check your email for the activation.");
					return inactive;
				}
				
				if(!member.getVerified())
				{
					setNotificationMessage("Account is not yet verified by the admin. Please contact the admin.");
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
					
					loadMemberRegistrationItemOtherFields(member);
					loadQuickSearch();
					
					return SUCCESS;
				}
			}
			else
			{
				setNotificationMessage("Invalid Username / Password");
				return ERROR;
			}
		}
		return INPUT;
	}
	
	/**
	 * Member Registration
	 * @return
	 */
	public String register()
	{
		String actionError = null;
		try
		{
			final String kaptchaReceived = (String) request.getParameter("kaptcha");
			if(kaptchaReceived != null)
			{
				final String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
				if(kaptchaReceived.equalsIgnoreCase(kaptchaExpected))
				{
					final String username = StringUtils.trimToNull(request.getParameter("username"));
					final String email = StringUtils.trimToNull(request.getParameter("email"));
					
					/** Do not trim, the user might intend to use whitespace as password */
					final String password = request.getParameter("password");
					
					final Member usernameMember = memberDelegate.findAccount(username, company);
					final Member emailMember = memberDelegate.findEmail(company, email);
					
					if (usernameMember != null)
					{
						actionError = "Username already exists.";
					}
					else if(emailMember != null)
					{
						actionError = "Email address already used.";
					}
					else if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(password)
							&& StringUtils.isNotEmpty(request.getParameter("firstName"))
							&& StringUtils.isNotEmpty(request.getParameter("lastName"))
							&& StringUtils.isNotEmpty(request.getParameter("contactNumber"))
							&& StringUtils.isNotEmpty(request.getParameter("city"))
							&& StringUtils.isNotEmpty(request.getParameter("province"))
							&& StringUtils.isNotEmpty(request.getParameter("schedule"))
							&& StringUtils.isNotEmpty(request.getParameter("profession")))
					{
						final String firstName = request.getParameter("firstName");
						final String lastName = request.getParameter("lastName");
						final String contactNumber = request.getParameter("contactNumber");
						final String city = request.getParameter("city");
						final String province = request.getParameter("province");
						final String schedule = request.getParameter("schedule");
						final String profession = request.getParameter("profession");
						
						member = new Member();
						member.setCompany(company);
						
						member.setUsername(username);
						member.setPassword(encryptor.encrypt(password));
						member.setEmail(email);
						member.setNewsletter(false);
						//member.setPassword(password);
						member.setActivationKey(Encryption.hash(company + email + username + password));
						member.setActivated(false);
						member.setDateJoined(new Date());
						
						member.setFirstname(firstName);
						member.setLastname(lastName);
						member.setMobile(contactNumber);
						member.setCity(city);
						
						member.setProvince(province);			
						member.setInfo1(schedule);
						member.setInfo2(profession);
						
						final Map<String, String> fields = new LinkedHashMap<String, String>();
						for(String field : registrationOtherFields)
						{
							fields.put(field, null);
						}
						
						@SuppressWarnings("rawtypes")
						final Enumeration parameters = request.getParameterNames();
						while(parameters.hasMoreElements())
						{
							final String paramName = (String) parameters.nextElement();
							if(StringUtils.isNotEmpty(paramName) && registrationOtherFields.contains(paramName))
							{
								fields.put(paramName, StringUtils.trimToNull(request.getParameter(paramName)));
							}
						}
						
						final String imagePath = saveMemberImagePath(company, member, request, "photo");
						if(imagePath != null)
						{
							fields.put("photo", imagePath);
						}
						
						final Long id = memberDelegate.insert(member);
						if(id != null)
						{
							final Member member = memberDelegate.find(id);
							if(member != null)
							{
								if(saveMHTRegistrationOtherFields(member, fields))
								{
									sendEmailToMember(member);
									sendEmailToCompany(member);
								}
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
				actionError = "Enter the characters in captcha image.";
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
	
	public String view()
	{
		try
		{
			final String idString = StringUtils.trimToNull(request.getParameter("id"));
			if(idString != null)
			{
				final Long id = Long.parseLong(idString);
				final Member member = memberDelegate.find(id);
				if(member != null)
				{
					request.setAttribute("member", member);
					loadMemberRegistrationItemOtherFields(member);
					request.setAttribute("isEdit", Boolean.TRUE);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String update()
	{
		try
		{
			final String idString = StringUtils.trimToNull(request.getParameter("id"));
			if(idString != null)
			{
				final Long id = Long.parseLong(idString);
				member = memberDelegate.find(id);
				if(member != null)
				{
					final String firstName = request.getParameter("firstName");
					final String lastName = request.getParameter("lastName");
					final String contactNumber = request.getParameter("contactNumber");
					final String province = request.getParameter("province");
					final String city = request.getParameter("city");
					
					member.setFirstname(firstName);
					member.setLastname(lastName);
					member.setMobile(contactNumber);
					member.setCity(city);
					member.setProvince(province);			
					
					final Map<String, String> fields = new LinkedHashMap<String, String>();
					final Enumeration parameters = request.getParameterNames();
					while(parameters.hasMoreElements())
					{
						final String paramName = (String) parameters.nextElement();
						if(StringUtils.isNotEmpty(paramName) && registrationOtherFields.contains(paramName))
						{
							fields.put(paramName, StringUtils.trimToNull(request.getParameter(paramName)));
						}
					}
					
					final String imagePath = saveMemberImagePath(company, member, request, "photo");
					if(imagePath != null)
					{
						fields.put("photo", imagePath);
					}
					
					saveMHTRegistrationOtherFields(member, fields);
					memberDelegate.update(member);
					this.prepare();
					loadMemberRegistrationItemOtherFields(member);
					setNotificationMessage("Information successfully updated.");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * Activation for the member
	 * @return
	 */
	public String activate()
	{
		try
		{
			final String activationKey = request.getParameter("activation");
			final Member member = memberDelegate.findActivationKey(activationKey);
			if(member != null)
			{
				member.setActivated(true);
				memberDelegate.update(member);
				request.setAttribute("notificationMessage", "Your account is activated. Please <a href=\"login.do\">login</a> to continue.");
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
	
	/**
	 * Sends an email to the member with the activation code.
	 * @param member
	 */
	private void sendEmailToMember(Member member)
	{
		try
		{
			if(connectSMTP() && member != null)
			{
				final String to = member.getEmail();
				final String subject = "My Home Therapist Member Registration";
				final String memberName = member.getFirstname() + " " + member.getLastname();
				final String nextLine = " \r\n\r\n";
				
				final StringBuffer content = new StringBuffer();
				content.append("Hi " + memberName + "," + nextLine);
				content.append("<br/><br/> Welcome to " + company.getNameEditable() + "!,");
				content.append("<br/> Please click on the link below to activate your account .");
				content.append("<a target = '_blank' href='" + httpServer + "/activate.do?activation=" + member.getActivationKey()+"'>"+ httpServer + "/activate.do?activation=" + member.getActivationKey()+"</a>");
				content.append("<br/><br/> Thank you for registering.");
				content.append("<br/> All the Best,");
				content.append("<br/> The " + company.getNameEditable() + " Team");
				
				if(!StringUtils.isEmpty(content.toString()))
				{
					logger.info("sending email to member.... ");
					boolean flag = EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", to, subject, content.toString(), null);
					logger.info("sending email to member.... " + flag);
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
	 * @param member
	 */
	private void sendEmailToCompany(Member member)
	{
		try
		{
			if(connectSMTP())
			{
				final String[] to = company.getEmail().split(",");
				if(member != null)
				{
					StringBuffer content = new StringBuffer();
					content.append("The following are the details for the newly registered Member: \r\n\r\n");
					
					content.append("Firstname: " + member.getFirstname() + "\r\n\r\n");
					content.append("Lastname: " + member.getLastname() + "\r\n\r\n");
					content.append("Email: " + member.getEmail() + "\r\n\r\n");
					content.append("Username: " + member.getUsername() + "\r\n\r\n");
					
					logger.info("sending email to company.... ");
					boolean flag = EmailUtil.send("noreply@webtogo.com.ph", to, company.getNameEditable() + " Member Registration", content.toString(), null);
					logger.info("sending email to company.... " + flag);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Connects to SMTP server. Returns true if successfully connected.
	 * @return
	 */
	private boolean connectSMTP()
	{
		boolean success = false;
		try
		{
			final CompanySettings companySettings = companySettingsDelegate.find(company);
			String emailUsername = null;
			String emailPassword = null;
			String smtpHost = null;
			int smtpPort = 25;
			if(companySettings != null)
			{
				emailUsername = companySettings.getEmailUserName();
				emailPassword = companySettings.getEmailPassword();
				smtpHost = companySettings.getSmtp();
				try
				{
					smtpPort = Integer.parseInt(companySettings.getPortNumber());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if(!StringUtils.isEmpty(emailUsername) && !StringUtils.isEmpty(emailPassword) && !StringUtils.isEmpty(smtpHost))
			{
				EmailUtil.connect(smtpHost, smtpPort, emailUsername, emailPassword);
			}
			else
			{
				EmailUtil.connect("smtp.gmail.com", 587);
			}
			success = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * Save/add other registration fields needed for the member
	 * @param member
	 * @param fields
	 * @return
	 */
	private synchronized boolean saveMHTRegistrationOtherFields(Member member, Map<String, String> fields)
	{
		boolean success = false;
		try
		{
			if(fields.size() > 0)
			{
				for (Entry<String, String> entry : fields.entrySet())
				{
					final String name = entry.getKey();
					final String value = entry.getValue();
		
					OtherField otherField = otherFieldDelegate.find(name, company);
					
					if(otherField == null)
					{
						otherField = new OtherField();
						otherField.setCompany(company);
						otherField.setIsValid(true);
						otherField.setName(name);
						otherField.setSortOrder(1);
						otherField.setGroup(groupDelegate.find(100L));
						otherFieldDelegate.insert(otherField);
						otherField = otherFieldDelegate.find(name, company);
						logger.info("other field added: " + name);
					}
					
					if(otherField != null)
					{
						RegistrationItemOtherField registrationItem = registrationItemOtherFieldDelegate.findByName(company, name, member.getId());
						
						boolean isUpdate = true;
						if(registrationItem == null)
						{
							registrationItem = new RegistrationItemOtherField();
							isUpdate = false;
						}
						
						registrationItem.setCompany(company);
						registrationItem.setIsValid(true);
						registrationItem.setContent(value);
						registrationItem.setOtherField(otherField);
						registrationItem.setMember(member);
						
						logger.info("other field item saved: " + name + " for " + member.getFullName());
						
						if(isUpdate)
						{
							registrationItemOtherFieldDelegate.update(registrationItem);
						}
						else
						{
							registrationItemOtherFieldDelegate.insert(registrationItem);
						}
					}
				}
			}
			logger.info("other fields completed.");
			success = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("failed to save other fields.");
		}
		return success;
	}

	/* for test purposes only */
	/*public String testMembers()
	{
		final List<String> provinces = new ArrayList<String>();
		for(String loc : MyHomeTherapistConstants.LOCATIONS)
		{
			provinces.add(loc);
		}
		
		final List<String> cities = new ArrayList<String>();
		cities.add("Caloocan");
		cities.add("Las Pinas");
		cities.add("Makati");
		cities.add("Malabon");
		cities.add("Mandaluyong");
		cities.add("Abra");
		cities.add("Albay");
		cities.add("Apayao");
		cities.add("Bulacan");
		cities.add("Aklan");
		cities.add("Antique");
		cities.add("Agusan del Norte");
		cities.add("Basilan");
		cities.add("Bukidnon");
		cities.add("Camiguin");
		
		for(int i = 0; i < 100; i++)
		{
			final Random randomizer = new Random();
			final int max = 20;
			final String firstName = RandomStringUtils.randomAlphanumeric(randomizer.nextInt(max));
			final String lastName = RandomStringUtils.randomAlphanumeric(randomizer.nextInt(max));
			final String email = RandomStringUtils.randomAlphanumeric(randomizer.nextInt(max)) + "@" + RandomStringUtils.randomAlphanumeric(randomizer.nextInt(max)) + ".com";
			final String password = RandomStringUtils.randomAlphanumeric(randomizer.nextInt(max));
			final String contactNumber = RandomStringUtils.randomNumeric(randomizer.nextInt(max));
			
			final Member member = new Member();
			member.setCompany(company);
			member.setFirstname(firstName);
			member.setLastname(lastName);
			member.setUsername(email); // Username?
			member.setPassword(encryptor.encrypt(password));
			member.setEmail(email);
			member.setNewsletter(false);
			member.setPassword(password);
			member.setActivationKey(Encryption.hash(company + email + firstName + lastName + password));
			member.setActivated(false);
			member.setVerified(true);
			
			final String province = provinces.get(randomizer.nextInt(provinces.size()));
			final String city = cities.get(randomizer.nextInt(cities.size()));
			final String schedule = MyHomeTherapistConstants.SCHEDULES[randomizer.nextInt(MyHomeTherapistConstants.SCHEDULES.length)];
			final String profession = MyHomeTherapistConstants.PROFESSIONS[randomizer.nextInt(MyHomeTherapistConstants.PROFESSIONS.length)];
			
			member.setMobile(contactNumber);
			member.setCity(city);
			
			member.setProvince(province);			
			member.setInfo1(schedule);
			member.setInfo2(profession);
			
			final Map<String, String> fields = new LinkedHashMap<String, String>();
			for(String field : registrationOtherFields)
			{
				fields.put(field, RandomStringUtils.randomAlphanumeric(randomizer.nextInt(max)));
			}
			
			final Long id = memberDelegate.insert(member);
			if(id != null)
			{
				final Member m = memberDelegate.find(id);
				try { Thread.sleep(1000); } catch(InterruptedException e) { e.printStackTrace(); }  
				if(m != null)
				{
					saveMHTRegistrationOtherFields(m, fields);
				}
			}
		}
		return SUCCESS;
	}*/
	
	// GETTERS
	
	public String getNotificationMessage()
	{
		return notificationMessage;
	}
	
	/**
	 * @return the member
	 */
	public Member getMember()
	{
		return member;
	}
	
	/**
	 * @return the specializations
	 */
	public Set<String> getProfessions()
	{
		return professions;
	}

	/**
	 * @return the schedules
	 */
	public Set<String> getSchedules()
	{
		return schedules;
	}

	/**
	 * @return the locations
	 */
	public Set<String> getLocations()
	{
		return locations;
	}
	
	// SETTERS
	
	public void setNotificationMessage(String notificationMessage)
	{
		this.notificationMessage = notificationMessage;
	}
	
	// OTHERS
	
	private void loadMemberRegistrationItemOtherFields(Member member)
	{
		try
		{
			registrationItemOtherFieldsMap.clear();
			final List<RegistrationItemOtherField> riofList = registrationItemOtherFieldDelegate.findAll(company, member).getList();
			if(riofList != null && !riofList.isEmpty())
			{
				final Map<String, String> otherFieldNameMap = new HashMap<String, String>();
				for(RegistrationItemOtherField riof : riofList)
				{
					final OtherField parent = riof.getOtherField();
					if(parent != null)
					{
						final Long otherFieldId = parent.getId();
						final OtherField otherField = otherFieldDelegate.find(otherFieldId);
						if(otherField != null)
						{
							final String otherFieldName = otherField.getName();
							final String riofContent = riof.getContent();
							if(StringUtils.isNotBlank(otherFieldName))
							{
								final String otherFieldNameToTitleCaseSeparatedBySpace = StringUtils.capitalize(otherFieldName).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
								registrationItemOtherFieldsMap.put(otherFieldNameToTitleCaseSeparatedBySpace, riofContent);
								otherFieldNameMap.put(otherFieldNameToTitleCaseSeparatedBySpace, otherFieldName);
							}
						}
					}
				}
				if(otherFieldNameMap != null && !otherFieldNameMap.isEmpty())
				{
					request.setAttribute("otherFieldNameMap", otherFieldNameMap);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadQuickSearch()
	{
		final Map<String, Integer> locationCounts = new LinkedHashMap<String, Integer>();
		for(String province : locations)
		{
			final Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("province", province);
			final List<Member> membersByLocation = memberDelegate.findAllByPropertyName(company, Restrictions.conjunction(), filters).getList();
			locationCounts.put(province, 
				membersByLocation != null && !membersByLocation.isEmpty()
				? membersByLocation.size()
				: 0); 
		}
		request.setAttribute("locationCounts", locationCounts);
	}
	
	/** For uplaoded image */
	private String saveMemberImagePath(Company company, Member member, HttpServletRequest request, String inputName)
	{
		String photoFileName = null;
		try
		{
			final MultiPartRequestWrapper mprw = (MultiPartRequestWrapper) request;
			final File[] file = mprw.getFiles(inputName);
			final String[] filename = mprw.getFileNames(inputName);
			if((file != null && file.length > 0) && (filename != null && filename.length > 0))
			{
				final String path = 
						ServletActionContext.getServletContext().getRealPath(MyHomeTherapistConstants.MY_HOME_THERAPIST_ROOT_FOLDER) 
						+ File.separator
						+ MyHomeTherapistConstants.IMAGE_ATTACHMENT_FOLDER 
						+ File.separator;
				final String thumbnailPath = 
						ServletActionContext.getServletContext().getRealPath(MyHomeTherapistConstants.MY_HOME_THERAPIST_ROOT_FOLDER) 
						+ File.separator
						+ MyHomeTherapistConstants.IMAGE_THUMBNAIL_ATTACHMENT_FOLDER 
						+ File.separator;
				final File uploadedFileDestination = new File(path);
				final File thumbnailFileDestination = new File(thumbnailPath);
				
				if(!uploadedFileDestination.exists())
				{
					uploadedFileDestination.mkdirs();
				}
				if(!thumbnailFileDestination.exists())
				{
					thumbnailFileDestination.mkdirs();
				}
				
				photoFileName = (member.getUsername() + "_" + member.getEmail() + "_" + filename[0]).replaceAll("\\s+", "_");
				final String thumbnailPhotoFileName = MyHomeTherapistConstants.IMAGE_THUMBNAIL_TOKEN + (member.getUsername() + "_" + member.getEmail() + "_" + filename[0]).replaceAll("\\s+", "_");
				final File destination = new File(path + photoFileName);
				
				FileUtil.copyFile(file[0], destination);
				ImageUtil.generateThumbnailImage
				(
					company.getId(), 
					(path + photoFileName), 
					(thumbnailPath + thumbnailPhotoFileName)
				);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return photoFileName;
	}

	public Map<String, String> getRegistrationItemOtherFieldsMap()
	{
		return registrationItemOtherFieldsMap;
	}
	
	public String getImageFolder()
	{
		return imageFolder;
	}

	public String getThumbnailImageFolder()
	{
		return thumbnailImageFolder;
	}

	public String getThumbnailToken()
	{
		return thumbnailToken;
	}
	
}
