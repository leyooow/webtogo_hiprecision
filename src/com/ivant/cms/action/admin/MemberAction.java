package com.ivant.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.company.utils.AgianUtilities;
import com.ivant.cms.delegate.CartOrderDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.CompanyDelegate;
import com.ivant.cms.delegate.EventDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.LogDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberFileDelegate;
import com.ivant.cms.delegate.MemberFileItemDelegate;
import com.ivant.cms.delegate.MemberTypeDelegate;
import com.ivant.cms.delegate.NotificationDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.AplicMember;
import com.ivant.cms.entity.CartOrder;
import com.ivant.cms.entity.CategoryItem;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Event;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Log;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberFile;
import com.ivant.cms.entity.MemberFileItems;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.Notification;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.entity.User;
import com.ivant.cms.enums.EntityLogEnum;
import com.ivant.cms.enums.ReferralEnum;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.Encryption;
import com.ivant.utils.PasswordEncryptor;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberAction
		extends ActionSupport
		implements Preparable, ServletRequestAware, UserAware, ServletContextAware, CompanyAware
{

	private static final long serialVersionUID = 6315883485788215563L;
	private final Logger logger = Logger.getLogger(getClass());
	private final CompanyDelegate companyDelegate = CompanyDelegate.getInstance();
	private final EventDelegate eventDelegate = EventDelegate.getInstance();
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final MemberTypeDelegate memberTypeDelegate = MemberTypeDelegate.getInstance();
	private final MemberFileItemDelegate receiptImageDelegate = new MemberFileItemDelegate();
	private final MemberFileDelegate memberFileDelegate = new MemberFileDelegate();
	private final LogDelegate logDelegate = LogDelegate.getInstance();
	private final GroupDelegate groupDelegate = new GroupDelegate();
	private final CategoryItemDelegate categoryItemDelegate = new CategoryItemDelegate();
	private final CartOrderDelegate cartOrderDelegate = CartOrderDelegate.getInstance();
	private final RegistrationItemOtherFieldDelegate registrationItemOtherFieldDelegate = RegistrationItemOtherFieldDelegate.getInstance();
	private final OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();

	/**
	 * K: The {@link OtherField#getName()} (parent of {@link RegistrationItemOtherField}). <br>
	 * V: {@link RegistrationItemOtherField#getContent()}
	 */
	private final Map<String, String> registrationItemOtherFieldsMap = new HashMap<String, String>();

	private Member member;
	private Member member2;
	private AplicMember aplicMember;
	private final List<MemberFileItems> receiptList = new ArrayList<MemberFileItems>();
	private List<MemberFileItems> memberFileItemsList;
	private List<Event> events;
	private User user;
	private HttpServletRequest request;

	private Company company;
	private CompanySettings companySettings;
	private String selectedCompany;
	private List<Company> companies;
	private Log log;

	private PasswordEncryptor encryptor;

	private String username;
	private String email;
	private String password;

	private String prcLicense;
	private String typeofpractice;
	private String birthDate;
	private String nameClinic;
	private String streetClinic;
	private String cityClinic;
	private String provinceClinic;
	private String regionClinic;
	private String countryClinic;
	private String street1;
	private String street2;
	private String city;
	private String province;
	private String region;
	private String country;
	private String fax;
	private String landline;
	private String membership;
	private String mobilePhone1;
	private String mobilePhone2;
	private String mobilePhone3;
	private String middlename;
	private String suffix;
	private String sex;
	private int yearOfMembership;
	/** Member account given first name, must not be null */
	private String firstname;
	/** Member account paternal name, must not be null */
	private String lastname;
	private String searchStatus;
	private String notificationMessage = "";
	private String info1;
	
	private Long categoryItem;

	private Long memberTypeId;
	
	/* AGIAN UPLOAD MEMBER INDICATOR */
	private int numberOfRowRead;
	private int numberOfInsertedItem;
	private int numberOfIgnoreItem;
	
	private ServletContext servletContext;
	
	
	private NotificationDelegate notificationDelegate = NotificationDelegate.getInstance();
	private Long notifMemberID;
	
	private File[] files;	
	
	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	@Override
	public void prepare() throws Exception
	{
		final String[] orderBy = { "nameEditable" };
		companies = companyDelegate.findAll(orderBy).getList();

		try
		{
			final long memberId = Long.parseLong(request.getParameter("member_id"));
			member2 = memberDelegate.find(memberId);

			events = eventDelegate.findAll(company);
			request.setAttribute("events", events);



			logger.info("1  " + member2);
			encryptor = new PasswordEncryptor();
			String decryptedPassword;
			decryptedPassword = encryptor.decrypt(member2.getPassword());
			setPassword(decryptedPassword);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
			member2 = new Member();
		}
	}

	@Override
	public String execute() throws Exception
	{
		setCompanySettings(company.getCompanySettings());

		if(user.getCompany() == null || companySettings.getHasMemberFeature() == false)
		{
			return Action.ERROR;
		}
		return Action.NONE;
	}
	
	public String saveNissan(){
		encryptor = new PasswordEncryptor();
		String encryptedPassword, inputPassword;
		inputPassword = password;
		encryptedPassword = encryptor.encrypt(inputPassword);
		member2.setPassword(encryptedPassword);
		member2.setCompany(company);
		member2.setDateJoined(getDateTime());
		member2.setNewsletter(false);
		member2.setMemberType(memberTypeDelegate.find(memberTypeId));
		member2.setInfo1(getInfo1());
		member2.setActivationKey(Encryption.hash(member2.getCompany() + member2.getEmail() + member2.getUsername() + member2.getPassword()));
		memberDelegate.insert(member2);
		
		return Action.SUCCESS;
	}
	
	public String saveAgianMember(Member member) {
		
		return null;
	}

	public String save()
	{
		if(StringUtils.isEmpty(request.getParameter("member_id")))
		{
			if(memberDelegate.findEmail(user.getCompany(), member2.getEmail()) == null)
			{
				encryptor = new PasswordEncryptor();
				String encryptedPassword, inputPassword, generatedPassword = "";
				logger.info("3  " + member2);
				inputPassword = password;
				encryptedPassword = encryptor.encrypt(inputPassword);
				member2.setPassword(encryptedPassword);
				member2.setCompany(user.getCompany());
				member2.setDateJoined(getDateTime());
				member2.setNewsletter(false);
				member2.setMemberType(memberTypeDelegate.find(memberTypeId));
				member2.setInfo1(getInfo1());
				member2.setActivationKey(Encryption.hash(member2.getCompany() + member2.getEmail() + member2.getUsername() + member2.getPassword()));
				
				if(company.getName().equalsIgnoreCase("montero")){
					logger.debug("Montero ... Saving branch of member");
					member2.setCategoryItem(CategoryItemDelegate.getInstance().find(categoryItem));
				}
				
				if(company.getName().equalsIgnoreCase("agian")) {
					
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					String birthday = sdf.format(request.getParameter("birthday"));
					System.out.println(request.getParameter("birthday"));
					String birthday = request.getParameter("birthday");
					String privilege = request.getParameter("privilege");
					String nickname = request.getParameter("nickname");
					
					generatedPassword = AgianUtilities.generatePassword(8);
					encryptedPassword = encryptor.encrypt(generatedPassword);
					
					member2.setPassword(encryptedPassword);
					member2.setReg_companyName(request.getParameter("reg_companyName"));
					member2.setReg_companyPosition(request.getParameter("reg_companyPosition"));
					CategoryItem cat = categoryItemDelegate.findByName(company, request.getParameter("reg_companyName"));
					if(cat != null){
						if(cat.getParent() != null){
							if(cat.getParent().getParentCategory() != null){
								member2.setInfo7(cat.getParent().getName());
							}else{
								member2.setInfo7(null);
							}
						}
					}
					member2.setInfo5(nickname);
					member2.setInfo6(privilege);
					member2.setInfo3(birthday);
					member2.setLandline(request.getParameter("landline"));
					member2.setMobile(request.getParameter("mobile"));
					member2.setInfo4(request.getParameter("info4"));
					member2.setValue2(request.getParameter("value2"));
					member2.setValue3(request.getParameter("value3"));
					
				}
				
				if(company.getName().equalsIgnoreCase("agian")){
					notifMemberID = memberDelegate.insert(member2);
					sendAccountDetails(memberDelegate.find(notifMemberID), generatedPassword);
					
					Member mem = memberDelegate.find(notifMemberID);
					Notification notif = new Notification();
					notif.setCompany(company);
					notif.setTitle(mem.getFirstname() + " " + mem.getLastname() + " joined");
					notif.setBy(String.format("%s", mem.getFirstname()));
					notif.setType("member");
					notif.setNotifOne("members.do?member=" + notifMemberID);
					notificationDelegate.insert(notif);
				}else{
					memberDelegate.insert(member2);
				}
				
				
			}
		}
		else
		{
			if(company.getName().equalsIgnoreCase("agian")) {
				
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				String birthday = sdf.format(request.getParameter("birthday"));
				System.out.println(request.getParameter("birthday"));
				String birthday = request.getParameter("birthday");
				String privilege = request.getParameter("privilege");
				
				member2.setReg_companyName(request.getParameter("reg_companyName"));
				member2.setReg_companyPosition(request.getParameter("reg_companyPosition"));
				CategoryItem cat = categoryItemDelegate.findByName(company, request.getParameter("reg_companyName"));
				if(cat != null){
					if(cat.getParent() != null){
						if(cat.getParent().getParentCategory() != null){
							member2.setInfo7(cat.getParent().getName());
						}else{
							member2.setInfo7(null);
						}
					}
				}
				member2.setInfo6(privilege);
				member2.setInfo3(birthday);
				member2.setLandline(request.getParameter("landline"));
				member2.setMobile(request.getParameter("mobile"));
				member2.setInfo4(request.getParameter("info4"));
				member2.setValue2(request.getParameter("value2"));
				member2.setValue3(request.getParameter("value3"));
				
				String output = AgianUtilities.generatePassword(8);
				System.out.println(output);
				//String contactCompany = "",contactEmail ="",contactContact="";
				
				/*setEmailSettings();
				EmailUtil.connect(smtp, Integer.parseInt(portNumber),
						mailerUserName, mailerPassword);*/
				StringBuilder content = new StringBuilder("");
				content.append("The admin has made some changes in your account.<br>Use the following credentials to login your account.<br><br>")
					.append("Email: " + member2.getEmail() + "<br>")
					.append("Code: " + output + "<br><br>")
					.append("Change your password immediately upon login under Profile page located at the upper right corner of your account.<br><br>");
				
				System.out.println(content);
				member2.setPassword(encryptor.encrypt(output));
				
				if(memberDelegate.update(member2)){
					logger.info(member2);
					AgianUtilities.sendMemberAccountDetails("admin_agian@ayala.com.ph",member2.getEmail(),"AGIAN account updated",content.toString(),null);
					return Action.SUCCESS;
				}else{
					return "agian";
				}
				
			}
			if(company.getId() == CompanyConstants.MY_HOME_THERAPIST)
			{
				member2.setVerified(StringUtils.isNotBlank(request.getParameter("verified"))
					? "on".equalsIgnoreCase(request.getParameter("verified"))
					: false);
			}
			else
			{
				encryptor = new PasswordEncryptor();
				String encryptedPassword, inputPassword;
				inputPassword = password;
				encryptedPassword = encryptor.encrypt(inputPassword);
				member2.setPassword(encryptedPassword);
			}
			
			member2.setMemberType(memberTypeDelegate.find(memberTypeId));
			logger.info(member2);
			memberDelegate.update(member2);
		}
		return Action.SUCCESS;
	}
	
	public String saveAndAllowSameEmail()
	{

		Boolean update = Boolean.FALSE;
		Boolean success = Boolean.FALSE;
		if(StringUtils.isEmpty(request.getParameter("member_id")))
		{
			final Member tempMember = memberDelegate.findByUsername(user.getCompany(), member2.getUsername());
			if(tempMember == null)
			{
				encryptor = new PasswordEncryptor();
				String encryptedPassword, inputPassword;
				logger.info("3  " + member2);
				inputPassword = password;
				encryptedPassword = encryptor.encrypt(inputPassword);
				member2.setPassword(encryptedPassword);
				member2.setCompany(user.getCompany());
				member2.setDateJoined(getDateTime());
				member2.setMemberType(memberTypeDelegate.find(memberTypeId));
				member2.setNewsletter(false);
				member2.setActivationKey(Encryption.hash(member2.getCompany() + member2.getEmail() + member2.getUsername() + member2.getPassword()));
				memberDelegate.insert(member2);
				success = Boolean.TRUE;
			}
			else
			{
				notificationMessage = "invalid";

			}
		}
		else
		{
			update = Boolean.TRUE;
			encryptor = new PasswordEncryptor();
			String encryptedPassword, inputPassword;
			inputPassword = password;
			encryptedPassword = encryptor.encrypt(inputPassword);
			member2.setPassword(encryptedPassword);
			member2.setMemberType(memberTypeDelegate.find(memberTypeId));
			logger.info(member2);
			if(memberDelegate.update(member2))
			{
				success = Boolean.TRUE;
			}
		}

		if(success && company.getName().equalsIgnoreCase(CompanyConstants.CANCUN))
		{
			sendEmailToMember(member2, update);
		}
		return Action.SUCCESS;
	}
	
	public String sendAccountDetails(Member member){
		final String emailTitle = "Account Details";
		if(member != null) {
			EmailUtil.connect("smtp.gmail.com", 587);
			final StringBuffer content = new StringBuffer();
			PasswordEncryptor encryptor = new PasswordEncryptor();
			
			
			content.append("Welcome " + member.getFirstname() + " " + member.getLastname() + ",");
			content.append("<br><br>");
			content.append("Your account details is as follows:<br/></br>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("Password: "+ encryptor.decrypt(member.getPassword()) +" <br/>");
			content.append("<br><br>");
			content.append("Change your password upon login: <a href='agian.webtogo.com.ph/login.do'>AGIAN Web Portal</a>");
			
			EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), emailTitle + " from " + company.getNameEditable(), content.toString(), null);
		} else {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String sendAccountDetails(Member member, String password){
		final String emailTitle = "Account Details";
		if(member != null) {
			final StringBuffer content = new StringBuffer();
			PasswordEncryptor encryptor = new PasswordEncryptor();
			
			content.append("Welcome " + member.getFirstname() + " " + member.getLastname() + ",");
			content.append("<br><br>");
			content.append("Your account details is as follows:<br/></br>");
			content.append("Email: " + member.getEmail() + "<br/>");
			content.append("Password: "+ password +" <br/>");
			content.append("<br><br>");
			content.append("To log in your account click here <a href='agian.com.ph/login.do'>AGIAN Web Portal</a> or visit portal at <a href='agian.com.ph/login.do'>www.agian.com.ph</a>");
			content.append("<br>Once logged in you may change your password under Profile page located at the upper right corner of your account.");
			
			AgianUtilities.sendMemberAccountDetails("admin_agian@ayala.com.ph", 
					member.getEmail(), 
					emailTitle + " from " + company.getNameEditable(), 
					content.toString(), 
					null);
		
		} else {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	
	public String sendEmailToMember(Member member, Boolean isUpdate)
	{
		final String emailTitle = "Member Information";
		if(member != null)
		{
			EmailUtil.connect("smtp.gmail.com", 25);
			final StringBuffer content = new StringBuffer();
			content.append("Hi " + member.getFirstname() + " " + member.getLastname() + ",");

			content.append("<br><br>");
			content.append("<table>");
			content.append("<tr><td colspan='4'>");
			content.append("Your account in " + company.getNameEditable() + " has been " + ((isUpdate)
				? "updated"
				: "created") + ".");
			content.append("</td></tr>");
			content.append("<tr>");
			content.append("<tr><td colspan='4'>&nbsp;</td></tr>");
			content.append("<td colspan='4'><strong>Member Information</strong></td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td>&nbsp;</td>");
			content.append("<td>" + (company.getName().equalsIgnoreCase(CompanyConstants.CANCUN)
				? "Booking Number"
				: "Username") + "</td>");
			content.append("<td>:</td>");
			content.append("<td>" + member.getUsername() + "</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td>&nbsp;</td>");
			content.append("<td>First Name</td>");
			content.append("<td>:</td>");
			content.append("<td>" + member.getFirstname() + "</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td>&nbsp;</td>");
			content.append("<td>Last Name</td>");
			content.append("<td>:</td>");
			content.append("<td>" + member.getLastname() + "</td>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td>&nbsp;</td>");
			content.append("<td>Email</td>");
			content.append("<td>:</td>");
			content.append("<td>" + member.getEmail() + "</td>");
			content.append("</tr>");
			if(company.getName().equalsIgnoreCase(CompanyConstants.CANCUN))
			{
				content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>Vacation Specialist</td>");
				content.append("<td>:</td>");
				content.append("<td>" + ((member.getInfo1() == null)
					? ""
					: member.getInfo1()) + "</td>");
				content.append("</tr>");
			}

			content.append("<tr><td colspan='4'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='4'>&nbsp;</td></tr>");

			content.append("</table>");

			if(!company.getName().equalsIgnoreCase(CompanyConstants.CANCUN))
			{
				content.append("You may now use your " + (company.getName().equalsIgnoreCase(CompanyConstants.CANCUN)
					? "Booking Number"
					: "Username an passwod") + " to Log-In ");
				content.append("at <a href='" + company.getServerName() + "'>" + company.getNameEditable() + "</a>");
			}

			if(company.getName().equalsIgnoreCase(CompanyConstants.CANCUN))
			{
				final SinglePageDelegate singlePageDelegate = SinglePageDelegate.getInstance();
				final SinglePage promoCodeSetting = singlePageDelegate.find(company, ReferralEnum.PROMOCODE_SETTINGPAGE.getValue());
				final String currentPromocode = promoCodeSetting.getContent().replaceAll("<p>", "").replaceAll("</p>", "");

				content.append("<br><br>You may now use this Promocode to Log-in to ");
				content.append("<a href='" + company.getServerName() + "'>" + company.getNameEditable() + "</a>");
				content.append("<br>Promocode : <strong>" + currentPromocode + "</strong><br><br>");
				content.append("<ul>");
				content.append("<li> Type http://www.cancungetaways-mexico.com </li>");
				content.append("<li> Enter the assigned password given to you to access the main menu (example: the password given was PROMOCODE so this will be entered)</li>");
				content.append("<li> Successful log-in will display the Main Menu. Click Client Referrals Menu></li>");
				content.append("<li> Enter Your Booking Number </li>");
				content.append("<li> Successful Booking Number will display Client Referral Form.</li>");
				content.append("<li> Fill Up all the necessary information required and Click Submit Button.</li>");
				content.append("<li> An-email notification will be sent to the given e-mail address you had provided</li>");

				content.append("</ul>");

			}
			content.append("<br><br>Thank you.<br><br><br>All the Best, <br><br>");
			content.append("The " + company.getNameEditable() + " Team");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), emailTitle + " from " + company.getNameEditable(), content.toString(), null))
			{
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	public String saveMemberFile()
	{

		final long memberFileId = Long.parseLong(request.getParameter("memberFile_id"));
		final MemberFile memberFile = memberFileDelegate.find(memberFileId);

		final long memberFileItemId = Long.parseLong(request.getParameter("memberFileItem_id"));
		final MemberFileItems memberFileItem = receiptImageDelegate.find(memberFileItemId);

		memberFile.setStatus(request.getParameter("status"));
		memberFile.setApprovedBy(user.getId());

		if(memberFileDelegate.update(memberFile))
		{
			if(!StringUtils.isEmpty(request.getParameter("amount")))
			{
				memberFileItem.setAmount(Double.parseDouble(request.getParameter("amount")));
				receiptImageDelegate.update(memberFileItem);
			}

			final MemberFileItems tempMemberFileItem = receiptImageDelegate.findOtherMemberFileItem(memberFileItem);
			if(tempMemberFileItem != null)
			{
				final MemberFile tempMemberFile = tempMemberFileItem.getMemberFile();
				tempMemberFile.setStatus(request.getParameter("status"));

				if(memberFileDelegate.update(tempMemberFile))
				{
					if(!StringUtils.isEmpty(request.getParameter("amount")))
					{
						tempMemberFileItem.setAmount(Double.parseDouble(request.getParameter("amount")));
						receiptImageDelegate.update(tempMemberFileItem);
					}
				}
			}

			return Action.SUCCESS;
		}
		return Action.ERROR;
	}

	private void loadMemberRegistrationItemOtherFields(Member member)
	{
		try
		{
			registrationItemOtherFieldsMap.clear();
			final List<RegistrationItemOtherField> riofList = registrationItemOtherFieldDelegate.findAll(company, member).getList();
			if(riofList != null && !riofList.isEmpty())
			{
				for(final RegistrationItemOtherField riof : riofList)
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
							}
						}
					}
				}
			}
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
	}

	public Member getMember2()
	{
		return member2;
	}

	public void setMember2(Member member2)
	{
		this.member2 = member2;
	}

	public String delete()
	{
		if(member2.getId() > 0)
		{
			memberDelegate.delete(member2);
		}

		return Action.SUCCESS;
	}

	public String deleteMemberFile()
	{

		final long memberFileItemId = Long.parseLong(request.getParameter("memberFileItem_id"));
		final MemberFileItems memberFileItem = receiptImageDelegate.find(memberFileItemId);

		if(receiptImageDelegate.findOtherMemberFileItem(memberFileItem) != null)
		{
			final MemberFileItems memberFileItem2 = receiptImageDelegate.findOtherMemberFileItem(memberFileItem);

			memberFileDelegate.delete(memberFileItem2.getMemberFile());
			receiptImageDelegate.delete(memberFileItem2);
		}
		memberFileDelegate.delete(memberFileItem.getMemberFile());
		receiptImageDelegate.delete(memberFileItem);

		return Action.SUCCESS;
	}

	private boolean commonParamsValid()
	{
		if(member2 == null)
		{
			return false;
		}

		return true;
	}

	public String editEvent()
	{

		return SUCCESS;
	}

	private void findSelectedGroup()
	{
		try
		{

			final long id = Long.parseLong(request.getParameter("group_id"));
			final Group group = groupDelegate.find(id);
			if(group.getCompany().equals(company))
			{
				request.setAttribute("group", group);
			}
		}
		catch(final Exception e)
		{
			logger.fatal("Cannot find category. ", e);
		}
	}

	public String edit()
	{
		if(!commonParamsValid())
		{
			return Action.ERROR;
		}
		if(company.getName().equalsIgnoreCase("pco"))
		{}
		if(company.getName().equalsIgnoreCase("apc") || company.getName().equalsIgnoreCase("westerndigital"))
		{
			showMemberReceipt();

			if(request.getParameter("group_id") != null)
			{
				findSelectedGroup();
			}
		}
		if(company.getName().equalsIgnoreCase("clickbox"))
		{
			memberFileItemsList = new ArrayList<MemberFileItems>();
			List<MemberFile> memberFile = new ArrayList<MemberFile>();

			if((member2 != null))
			{
				memberFile = memberFileDelegate.findAll(member2);
				Collections.reverse(memberFile);
			}
			else
			{
				return ERROR;
			}

			for(final MemberFile memFile : memberFile)
			{
				final MemberFileItems fileInfo = receiptImageDelegate.findMemberFileItem(company, memFile.getId());
				if(fileInfo != null)
				{
					memberFileItemsList.add(fileInfo);
				}
			}

			if(memberFileItemsList != null)
			{
				Collections.reverse(memberFileItemsList);
				request.setAttribute("memberFileItemsList", memberFileItemsList);
			}
		}

		if(company.getId() == CompanyConstants.GURKKA || company.getId() == CompanyConstants.GURKKA_TEST)
		{}
		
		if(company.getId() == CompanyConstants.MY_HOME_THERAPIST)
		{
			final String memberIdParam = request.getParameter("member_id");
			if(StringUtils.isNumeric(memberIdParam))
			{
				final Long memberId = Long.parseLong(memberIdParam);
				final Member member = memberDelegate.find(memberId);
				loadMemberRegistrationItemOtherFields(member);
			}
		}

		return Action.SUCCESS;
	}

	/* SHOW ALL THE MEMBER UPLOAD FILES --COMPANY uses It is APC */
	public String showMemberReceipt()
	{
		// this will be calculated below

		final String approve_id = request.getParameter("approve_id");
		final String member_id = request.getParameter("member_id");
		final String cancel_id = request.getParameter("cancel_id");
		final String reject_id = request.getParameter("reject_id");
		final String ids = request.getParameter("gcIds");

		// get the current Value
		String membersTotalPoint = memberDelegate.find(Long.parseLong(member_id)).getValue();

		List<MemberFile> memberFile = null;
		if((approve_id != null || cancel_id != null) && member_id != null && reject_id != null && ids != null)
		{
			final Member member_1 = memberDelegate.find(Long.parseLong(member_id));
			memberFile = memberFileDelegate.findAll(member_1);
		}
		else
		{
			memberFile = memberFileDelegate.findAll(member2);
		}

		for(final MemberFile memFile : memberFile)
		{
			final MemberFileItems fileInfo = receiptImageDelegate.findMemberFileItem(company, memFile.getId());
			if(fileInfo == null)
			{
			}
			else
			{
				// APPROVE //-->cancel
				if(approve_id != null && fileInfo.getId() == Long.parseLong(approve_id) || cancel_id != null && fileInfo.getId() == Long.parseLong(cancel_id) || reject_id != null
						&& fileInfo.getId() == Long.parseLong(reject_id) || ids != null)
				{

					fileInfo.getMemberFile().setApprovedDate(new Date());
					if(user != null)
					{
						fileInfo.getMemberFile().setApprovedBy(user.getId());// USER ID WHO APPROVES IT
					}

					if(approve_id != null)
					{
						// pts of the Current Member in this memberfileitem
						String pts = fileInfo.getValue();
						// VALUE OF THE MEMBER
						String toBeAdded = fileInfo.getMemberFile().getMember().getValue();
						if(pts != null || toBeAdded != null)
						{
							pts = (pts != null
								? pts
								: "0");
							toBeAdded = (toBeAdded != null
								? toBeAdded
								: "0");
							final Double currentValue = Double.parseDouble(pts);
							final Double valueToBeAdded = Double.parseDouble(toBeAdded);
							final Member mem = memberDelegate.find(fileInfo.getMemberFile().getMember().getId());
							mem.setValue("" + (currentValue + valueToBeAdded));
							memberDelegate.update(mem);
							membersTotalPoint = mem.getValue();
						}
						else
						{
							fileInfo.getMemberFile().setValue("0");
							fileInfo.getMemberFile().getMember().setValue("0");
						}
						fileInfo.getMemberFile().setStatus("Approved");
					}
					else if(cancel_id != null)
					{
						// pts of the Current Member in this memberfileitem
						String pts = fileInfo.getMemberFile().getMember().getValue();
						// VALUE OF THE MEMBER
						String toBeMinus = fileInfo.getValue();
						if(pts != null || toBeMinus != null)
						{
							pts = (pts != null
								? pts
								: "0");
							toBeMinus = (toBeMinus != null
								? toBeMinus
								: "0");
							final Double currentValue = Double.parseDouble(pts);
							final Double valueToBetoBeMinus = Double.parseDouble(toBeMinus);
							final Member mem = memberDelegate.find(fileInfo.getMemberFile().getMember().getId());

							if(currentValue >= valueToBetoBeMinus)
							{
								mem.setValue("" + (currentValue - valueToBetoBeMinus));
								memberDelegate.update(mem);
								fileInfo.getMemberFile().setMember(mem);
								membersTotalPoint = mem.getValue();
							}
							else
							{
								fileInfo.getMemberFile().setRemarks("Attempt to deduct " + valueToBetoBeMinus + " to " + currentValue + " is an Invalid Action");
							}
						}
						else
						{
							fileInfo.getMemberFile().setValue("0");
							fileInfo.getMemberFile().getMember().setValue("0");
						}
						fileInfo.getMemberFile().setStatus("Cancelled");
					}
					else if(reject_id != null)
					{
						fileInfo.getMemberFile().setStatus("Rejected");
					}
					// apc GC redeeem status change
					else if(ids != null)
					{
						final String idArray[] = ids.split(",");
						final CartOrder cartOrder = cartOrderDelegate.find(Long.parseLong(request.getParameter("cartId")));
						for(final String id : idArray)
						{
							if(Long.parseLong(id) == memFile.getId())
							{
								fileInfo.getMemberFile().setStatus("Waiting to be Redeemed");
								fileInfo.getMemberFile().setCartOrder(cartOrder);

								System.out.println("EXISTING :: " + memberFileDelegate.find(Long.parseLong(id)));
							}
						}
					}

					/*** THIS PART IS FOR LOGS ***/
					log = new Log();
					log.setCompany(company);
					log.setUpdatedOn(getDateTime());
					log.setCreatedOn(getDateTime());

					if(fileInfo.getMemberFile().getRemarks() != null && fileInfo.getMemberFile().getRemarks().equalsIgnoreCase("sample remarks Only "))
					{
						log.setRemarks(fileInfo.getMemberFile().getRemarks());
					}
					else
					{
						log.setRemarks((fileInfo.getMemberFile().getStatus() == null
							? ""
							: fileInfo.getMemberFile().getStatus().toLowerCase()) + " this Invoice Number-(" + fileInfo.getInvoiceNumber() + ") that has been uploaded by "
								+ fileInfo.getMemberFile().getMember().getFullName());
					}

					if(user != null)
					{
						log.setUpdatedByUser(user);
						log.setEntityType(EntityLogEnum.MEMBERFILEITEM);
						logDelegate.insert(log);
					}
					memberFileDelegate.update(fileInfo.getMemberFile());
					receiptImageDelegate.update(fileInfo);
				}
				receiptList.add(fileInfo);
			}

		}
		if(receiptList != null)
		{
			Collections.reverse(receiptList);
		}
		request.setAttribute("membersTotalPoint", "" + membersTotalPoint);
		request.setAttribute("receiptList", receiptList);

		return Action.SUCCESS;
	}

	public Member getMember()
	{
		return member;
	}

	public void setMember(Member member)
	{
		this.member = member;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	@Override
	public void setUser(User user)
	{
		this.user = user;
	}

	public Company getCompany()
	{
		return company;
	}

	@Override
	public void setCompany(Company company)
	{
		this.company = company;
	}

	public String getSelectedCompany()
	{
		return selectedCompany;

	}

	public List<Company> getCompanies()
	{
		return companies;
	}

	public void setCompanies(List<Company> companies)
	{
		this.companies = companies;
	}

	private Date getDateTime()
	{
		final Date date = new Date();
		return date;
	}

	public void setCompanySettings(CompanySettings companySettings)
	{
		this.companySettings = companySettings;
	}

	public CompanySettings getCompanySettings()
	{
		return companySettings;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public String updateMemberDetails()
	{
		if(memberDelegate.update(member2))
		{
		}
		initMemberInformation();
		if(company.getName().equalsIgnoreCase("PCO"))
		{}
		return "success";
	}

	public void initMemberInformation()
	{
		email = request.getParameter("email");
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		prcLicense = request.getParameter("prcLicense");
		typeofpractice = request.getParameter("typeOfPractice");
		birthDate = request.getParameter("month") + "-" + request.getParameter("day") + "-" + request.getParameter("year");
		nameClinic = request.getParameter("nameClinic");
		streetClinic = request.getParameter("streetClinic");
		cityClinic = request.getParameter("cityClinic");
		provinceClinic = request.getParameter("provinceClinic");
		regionClinic = request.getParameter("regionClinic");
		countryClinic = request.getParameter("countryClinic");
		street1 = request.getParameter("street1");
		street2 = request.getParameter("street2");
		city = request.getParameter("city");
		province = request.getParameter("province");
		region = request.getParameter("region");
		country = request.getParameter("country");
		landline = request.getParameter("landline");
		fax = request.getParameter("fax");
		membership = request.getParameter("membership");
		mobilePhone1 = request.getParameter("mobilePhone1");
		mobilePhone2 = request.getParameter("mobilePhone2");
		mobilePhone3 = request.getParameter("mobilePhone3");
		middlename = request.getParameter("middlename");
		suffix = request.getParameter("suffix");
		sex = request.getParameter("sex");
		final Calendar cal = Calendar.getInstance();
		yearOfMembership = cal.get(Calendar.YEAR);

		request.setAttribute("email", email);
		request.setAttribute("firstname", firstname);
		request.setAttribute("lastname", lastname);
	}

	public void initializeMemberInformation()
	{}
	
	public String uploadMemberByBatch() {
		InputStream input = null;
		
		int readSuccessfull = 0;
		int readFailed = 0;
		int rowReaded = 0;
		int cellReaded = 0;
		int numberOfInsertedToDB = 0;
		int memberIgnoreSameEmail = 0;
		
		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
		
		files = requestWrapper.getFiles("members_upload");
		try {
				input = new FileInputStream(files[0]);
			
		} catch (FileNotFoundException e) {
			setNotificationMessage("error occured");
			return Action.SUCCESS;
		} catch (Exception e) {
			setNotificationMessage("<h2>Please select excel (MS excel 2013) File to upload.</h2>" );
			return Action.SUCCESS;
		}
		
		HSSFWorkbook workbook = null;
		
		try {
			workbook = new HSSFWorkbook(input);
		} catch (IOException e) {
			setNotificationMessage("File not valid. Create file using MS excel 2013.");
		} catch (Exception e) {
			setNotificationMessage("File not valid. Create file using MS excel 2013.");
		}
		
		int itemsRead = 0;
		
		
		// excel field index/column indicator
		final short LAST_NAME 				= 0;
		final short FIRST_NAME 				= 1;
		final short USERNAME 				= 2;
		
		final short MONTH 				    = 3;
		final short DAY					    = 4;
		final short YEAR 				    = 5;
		
		final short EMAIL_ADDRESS 			= 6;
		final short COMPANY_NAME 			= 7;
		final short SUB_COMPANY_NAME 		= 8;
		final short POSITION 				= 11;
		final short MOBILE_NUMBER 			= 12;
		final short TELEPHONE_NUMBER 		= 13;
		
		final short AREAS_EXPERTISE 		= 20;
		final short CERTIFICATION 			= 21;
		final short AFFLIATED_ORGANIZATION  = 22;
		
		
		// members batch storage
		List<Member> members = new ArrayList<Member>();
		
		for (int sheetPosition = 0; sheetPosition < workbook.getNumberOfSheets(); sheetPosition++) {
			HSSFSheet sheet = workbook.getSheetAt(sheetPosition); System.out.println("<< sheet position " + sheetPosition);
			
			for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); itemsRead++) {
				
				Row row = rowIterator.next();
				HashMap<String, String> fieldMap = new HashMap<String, String>();
				
				// SIGLE MEMEBER TEMPORARY STORAGE
				Member member = new Member();
				
				/* MEMBER FIELD  */
				String lastName 			= "";
				String firstName 			= "";
				String username 			= "";
				
				String month 				= "";
				String day 					= "";
				String year 				= "";
				
				String emailAddress 		= "";
				String companyName 			= "";
				String subCompanyName 		= ""; //sub company
				String position 			= "";
				String mobileNumber 		= "";
				String telephoneNumber 		= "";
				
				String areasOfExpertise 	= ""; // info4
				String certification 		= ""; // value2
				String affiatedOrganization = ""; // value3
				
				
				
				for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
					if (row.getRowNum() < 0) { break; }
					
					Cell cell = cellIterator.next();
					System.out.println("<< cell number debug " + cell.getColumnIndex());
					
					switch (cell.getColumnIndex()) {
						case LAST_NAME:
							lastName = cell.getStringCellValue();
							break;
						case FIRST_NAME:
							firstName = cell.getStringCellValue();
							break;
						case USERNAME:
							username = cell.getStringCellValue();
							break;
						case MONTH:
							month = cell.getStringCellValue();
							break;
						case DAY:
							day = ((cell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? String.valueOf(cell.getNumericCellValue()) : cell.getStringCellValue());
							break;
						case YEAR:
							year = ((cell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? String.valueOf(cell.getNumericCellValue()) : cell.getStringCellValue());
							break;
						case EMAIL_ADDRESS:
							emailAddress = cell.getStringCellValue();
							break;
						case COMPANY_NAME:
							companyName = cell.getStringCellValue();
							break;
						case SUB_COMPANY_NAME:
							subCompanyName = cell.getStringCellValue();
							break;	
						case POSITION:
							position = cell.getStringCellValue();
							break;
						case MOBILE_NUMBER:
							mobileNumber = cell.getStringCellValue();
							break;
						case TELEPHONE_NUMBER:
							telephoneNumber = cell.getStringCellValue();
							break;
						case AREAS_EXPERTISE:
							areasOfExpertise = cell.getStringCellValue();
							break;
						case CERTIFICATION:
							certification = cell.getStringCellValue();
							break;
						case AFFLIATED_ORGANIZATION:
							affiatedOrganization = cell.getStringCellValue();
							break;
						default:
							break;
					}
				}
				Group group = groupDelegate.find(613L);
				List<CategoryItem> gal = categoryItemDelegate.findAllByGroupNameAsc(company, group).getList();
				boolean companyExist = false;
				boolean subCompanyExist = false;
				for(int x = 0; x < gal.size(); x++){
					if(companyName.equals(gal.get(x).getName())){
						companyExist = true;
					}
					if(StringUtils.isNoneBlank(subCompanyName)){
						if(subCompanyName.equals(gal.get(x).getName())){
							subCompanyExist = true;
						}
					}
				}
				logger.info("companyExist: " + companyExist );
				logger.info("subCompanyExist: " + subCompanyExist );
				if (StringUtils.isNoneBlank(emailAddress) && StringUtils.isNotBlank(username) && companyExist && StringUtils.isNoneBlank(companyName) || companyExist && subCompanyExist && StringUtils.isNoneBlank(subCompanyName) && StringUtils.isNoneBlank(companyName)) {
					
					PasswordEncryptor encryptor = new PasswordEncryptor();
					String encryptedPassword = new String();
					String generatedPassword = "";
					
					encryptedPassword = AgianUtilities.generatePassword(8);
					generatedPassword = encryptedPassword;
					System.out.println("------------------AGIAN-------------------------------");
					System.out.println("-------------------------------------------------------");
					System.out.println("generated password : " + encryptedPassword);
					encryptedPassword = encryptor.encrypt(encryptedPassword);
					
					member2 = new Member();
				
					member2.setFirstname(firstName);
					member2.setLastname(lastName);
					member2.setEmail(emailAddress);
					
					member2.setUsername(username);
					member2.setPassword(encryptedPassword);
					member2.setCompany(user.getCompany());
					member2.setDateJoined(getDateTime());
					member2.setNewsletter(false);
					member2.setMemberType(memberTypeDelegate.find(memberTypeId));
					member2.setActivationKey(Encryption.hash(member2.getCompany() + member2.getEmail() + member2.getUsername() + member2.getPassword()));
					
					member2.setInfo3(AgianUtilities.getFormattedDate(month, day, year));
					if(StringUtils.isNoneBlank(subCompanyName)){
						member2.setReg_companyName(subCompanyName);
						member2.setInfo7(companyName);
					}else{
						member2.setReg_companyName(companyName);
					}
					member2.setReg_companyPosition(position);
					member2.setMobile(mobileNumber);
					member2.setLandline(telephoneNumber);
					member2.setInfo4(areasOfExpertise);
					member2.setValue2(certification);
					member2.setValue3(affiatedOrganization);
					
					member2.setVerified(Boolean.FALSE);
					
					System.out.println(">>>>>>>     AGIAN     <<<<<<<");
					System.out.println("lastname : " + lastName);
					System.out.println("firstname : " + firstName);
					System.out.println("username : " + username);
					System.out.println("month : " + month);
					System.out.println("day : " + AgianUtilities.trimZeroStringNumeric(day));
					System.out.println("year : " + year);
					System.out.println("email address : " + emailAddress);
					System.out.println("company : " + companyName);
					System.out.println("position : " + position);
					System.out.println("mobile : " + mobileNumber);
					System.out.println("telephone : " + telephoneNumber);
					System.out.println("areas of expertise : " + areasOfExpertise);
					System.out.println("certification : " + certification);
					System.out.println("affliated organization : " + affiatedOrganization);
					System.out.println("password generated : " + AgianUtilities.generatePassword(8));
					System.out.println("birthdate : " + AgianUtilities.getFormattedDate(month, day, year));
					System.out.println("password encrypted : " + encryptedPassword);
					
					Member memberz = memberDelegate.findEmail(user.getCompany(), emailAddress);
					if (memberz != null) {
						memberIgnoreSameEmail++;
					} else {
						String status = sendAccountDetails(memberDelegate.find(memberDelegate.insert(member2)), generatedPassword);
						System.out.println("mail status >>>>>>>>>>>>>>>");
						System.out.println(">>" + status);
						System.out.println("mail status >>>>>>>>>>>>>>>");
						if (status == SUCCESS) {
							numberOfInsertedToDB++;
						}
					}
					
					rowReaded++;
					
				}
				
			}
		}
	
		setNumberOfRowRead(rowReaded);
		setNumberOfInsertedItem(numberOfInsertedToDB);
		setNumberOfIgnoreItem(memberIgnoreSameEmail);
		
		setNotificationMessage("<h4>Members Upload Status: </h4> ");
		return Action.SUCCESS;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSearchStatus(String status)
	{
		this.searchStatus = status;
	}

	public String getSearchStatus()
	{
		return searchStatus;
	}

	public boolean isUseBillingNumberAsUserName()
	{
		if(company.getName().equalsIgnoreCase("cancun"))
		{
			return true;
		}
		return false;
	}

	public void setNotificationMessage(String notificationMessage)
	{
		this.notificationMessage = notificationMessage;
	}

	public String getNotificationMessage()
	{
		return notificationMessage;
	}

	public void setInfo1(String info1)
	{
		this.info1 = info1;
	}

	public String getInfo1()
	{
		return info1;
	}

	public void setMemberTypeId(Long memberTypeId)
	{
		this.memberTypeId = memberTypeId;
	}

	public List<MemberType> getMemberTypes()
	{
		return memberTypeDelegate.findAllWithPaging(company, -1, -1, null).getList();
	}

	/**
	 * @return the registrationItemOtherFieldsMap
	 */
	public Map<String, String> getRegistrationItemOtherFieldsMap()
	{
		return registrationItemOtherFieldsMap;
	}

	public Long getCategoryItem() {
		return categoryItem;
	}

	public void setCategoryItem(Long categoryItem) {
		this.categoryItem = categoryItem;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public int getNumberOfRowRead() {
		return numberOfRowRead;
	}
	
	public void setNumberOfRowRead(int numberOfRowRead) {
		this.numberOfRowRead = numberOfRowRead;
	}
	
	public int getNumberOfInsertedItem() {
		return numberOfInsertedItem;
	}
	
	public void setNumberOfInsertedItem(int numberOfInsertedItem) {
		this.numberOfInsertedItem = numberOfInsertedItem;
	}
	
	public int getNumberOfIgnoreItem() {
		return numberOfIgnoreItem;
	}
	
	public void setNumberOfIgnoreItem(int numberOfIgnoreItem) {
		this.numberOfIgnoreItem = numberOfIgnoreItem;
	}
	

	@SuppressWarnings("unchecked")
	public List<CategoryItem> getAgianCompanies(){
		Group group = groupDelegate.find(613L);
		List<CategoryItem> gal = categoryItemDelegate.findAllByGroupNameAsc(company, group).getList();
		return gal;
		
	}

}
