package com.ivant.cms.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.FormPage;
import com.ivant.cms.entity.Group;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Menu;
import com.ivant.cms.entity.MultiPage;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.PageImage;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.SinglePage;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.SortingUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/*
 * Author: Glenn Allen Sapla
 * Date: Aug 26, 2010
 * 
 * NOT IN USE PLEASE REFER TO THE "REGISTRATION ACTION CONTROLLER"
 */
public class RegistrationAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware,
			ServletContextAware, CompanyAware, MemberAware {

	private static final long serialVersionUID = -100325022519476647L;
	
	private static final List<String> ALLOWED_PAGES;
	
	static {
		ALLOWED_PAGES = new ArrayList<String>();
		ALLOWED_PAGES.add("sitemap");
		ALLOWED_PAGES.add("printerfriendly");
		ALLOWED_PAGES.add("brands");
		ALLOWED_PAGES.add("calendarevents");
		ALLOWED_PAGES.add("cart");
	}
	
	private Logger logger = Logger.getLogger(getClass());
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private RegistrationItemOtherFieldDelegate registrationDelegate= RegistrationItemOtherFieldDelegate.getInstance();
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	private Map session;
	private HttpServletRequest request;
	
	private String username;
	private String password;
	private boolean login = false;
	
	private Company company;
	private Member member;
	private String servletContextName;
	private String pageName;
	private String prevPage;
	private String httpServer;
	private String activationLink;

	private ServletContext servletContext;
	private boolean isLocal;
	private boolean willSendMail;
	private boolean withLoginInfo;
	private String message;
	
	private PasswordEncryptor encryptor;
	private Long memberId;
	private File digital;
	private File passport;
	private File visa;
	private File birth;
	private File financial;
	private File academic;
	private File ecoe;
	private List<RegistrationItemOtherField> info;
	private String digitalFileName;
	private String passportFileName;
	private String visaFileName;
	private String birthFileName;
	private String financialFileName;
	private String academicFileName;
	private String ecoeFileName;
	private String[] allowedFields;
	private String[] allowedFiles;
	private List<String> listOfFields;
	private List<String> listOfFiles;
	private Map<String, File> mapFile;
	private Map<String, String> mapFileName;
	private Map<String, String> requiredMemberInfo;


	@Override
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
	/*	//get all parameters
		Enumeration temp = request.getParameterNames();
		OtherField newOtherField= new OtherField();
		OtherField otherField= new OtherField();
		Long tempId=null;
		encryptor = new PasswordEncryptor();

		this.setInitCompanies();//set filenames for companies (need to recode per company)
		RegistrationItemOtherField registrationItem = new RegistrationItemOtherField();
		Member memberTemp = new Member();
		if(member!=null && member.getCompany().equals(company)){
			memberTemp = member;
		}
		else{
		//Required Fields
			memberTemp.setCompany(company);
			memberTemp.setUsername(requiredMemberInfo.get("Username"));
			memberTemp.setPassword(encryptor.encrypt(requiredMemberInfo.get("Password")));
			memberTemp.setLastname(requiredMemberInfo.get("Family Name"));
			memberTemp.setFirstname(requiredMemberInfo.get("Given Name"));
			memberTemp.setEmail(requiredMemberInfo.get("Email"));
			memberTemp.setDateJoined(new Date());
			memberTemp.setPurpose("Registration");
			memberTemp.setIsValid(true);
			memberTemp.setStatus("Pending");
			memberTemp.setNewsletter(false);
		
		}
		Long tempId2=0L;
		Member memberTemp2 = new Member();
		
		if(member!=null && member.getCompany().equals(company)){
			tempId2=member.getId();
			memberTemp2=memberDelegate.find(member.getId());
		}else{
			tempId2 = memberDelegate.insert(memberTemp);
			memberTemp2 = memberDelegate.find(tempId2);
		}
	
		if(memberTemp2==null){
			message = "ERROR: Username already in use";
			return ERROR;
		}
		
		//add Member Id to (temporary)username to make it unique
		if(!withLoginInfo){
			memberTemp2.setUsername(memberTemp2.getUsername()+tempId2);
		}
		
		memberDelegate.update(memberTemp2);
		//refresh object
		memberTemp2 = memberDelegate.find(tempId2);
		memberId= tempId2;
		

		//Create Fields
		Boolean update=false;
		for(String field : listOfFields){	
			otherField = otherFieldDelegate.find(field, company);
			if(otherField==null){
				newOtherField= new OtherField();
				newOtherField.setCompany(company);
				newOtherField.setIsValid(true);
				newOtherField.setName(field);
				newOtherField.setSortOrder(1);
				newOtherField.setGroup(groupDelegate.find(100L));
				otherFieldDelegate.insert(newOtherField);
				otherField = otherFieldDelegate.find(field, company);
			}
			if(otherField!=null){
				update=true;
				registrationItem=null;
				if(member!=null){
					registrationItem  = registrationDelegate.findByName(company, field, member.getId());
				}
				if(registrationItem==null){
					registrationItem = new RegistrationItemOtherField();
					registrationItem.setCompany(company);
					registrationItem.setIsValid(true);
					update=false;
				}
				if(request.getParameter(otherField.getName())!=null){
					if(request.getParameter(otherField.getName()).trim().length()!=0)
						registrationItem.setContent(request.getParameter(otherField.getName()));
					else registrationItem.setContent("N/A");
				}
				else registrationItem.setContent("N/A");
				registrationItem.setOtherField(otherField);
				registrationItem.setMember(memberTemp2);
				if(update){
					registrationDelegate.update(registrationItem);
				}else{
					registrationDelegate.insert(registrationItem);
				}
			}
		}

		
		

		
		
		
		//insert other fields and files
		String imageName="";
		String attachments2="";
		for(int i=0; i<listOfFiles.size();i++){
			otherField = new OtherField();
			otherField =otherFieldDelegate.find(listOfFiles.get(i), company);
			if(otherField==null){
				newOtherField= new OtherField();
				newOtherField.setCompany(company);
				newOtherField.setIsValid(true);
				newOtherField.setName(listOfFiles.get(i));
				newOtherField.setSortOrder(1);
				newOtherField.setGroup(groupDelegate.find(100L));
				tempId=otherFieldDelegate.insert(newOtherField);
				otherField = otherFieldDelegate.find(tempId);
			}
			else{
				otherField = otherFieldDelegate.find(listOfFiles.get(i), company);
			}
			registrationItem = new RegistrationItemOtherField();
			registrationItem.setCompany(company);
			registrationItem.setIsValid(true);
			if(mapFile.get(listOfFiles.get(i))!=null)
				imageName=saveSingleFile(mapFile.get(listOfFiles.get(i)),mapFileName.get(listOfFiles.get(i)));
			else
				imageName= "No File";
			attachments2=attachments2+" "+imageName;
			registrationItem.setContent(imageName);
			registrationItem.setOtherField(otherField);
			registrationItem.setMember(memberTemp2);
			registrationDelegate.insert(registrationItem);	
		}
		
		//get registration items and refresh member object
		Member mem = memberDelegate.find(tempId2);
		memberDelegate.refresh(mem);
		info = new ArrayList<RegistrationItemOtherField>();
		info= mem.getRegistrationItemOtherFields();
		
		//sort registration items according to their sort order
		if(info!=null)
			SortingUtil.sortBaseObject("otherField.sortOrder", true,info );
		
		this.sendEmail(attachments2);
		*/
		return SUCCESS;
	}
	
	public String readInfos(){
		Member mem;
		if(member!=null)
			mem = memberDelegate.find(member.getId());
		else{
			mem= memberDelegate.find(Long.parseLong(request.getParameter("memberId")));
		}
		memberDelegate.refresh(mem);
		info = new ArrayList<RegistrationItemOtherField>();
		info= mem.getRegistrationItemOtherFields();
		
		return SUCCESS;
	}
	
	public void sendEmail(String attachments2){
		//Construct email attachments and body
		String content="<table>";
		String content2="-----------------Bright Registration--------------------<br/><br/>";
		String fieldValue="";
		List<String> attachments=new ArrayList<String>();
		String email="",family="", given="",phone="";
		email= requiredMemberInfo.get("Email");
		family= requiredMemberInfo.get("Family Name");
		given= requiredMemberInfo.get("Given Name");
		phone= requiredMemberInfo.get("Phone");

		for(RegistrationItemOtherField ri:info){
			fieldValue=ri.getOtherField().getName();
			content=content+"<tr>";
			if(listOfFiles.contains(fieldValue)){
				//email attachments path
				attachments.add(servletContext.getRealPath("")+"/companies/"+company.getName()+"/attachments/registrations/"+ri.getContent());
			}
			else{
				//email attachment files format(Field Name : Value)
				content2=content2+""+fieldValue;
				content2=content2+" : "+ri.getContent()+"<br/><br/>";
				content=content+"<td>"+fieldValue+": </td>";
				content=content+"<td>"+ri.getContent()+"</td>";
			}
			content=content+"</tr>";
		}
		content=content+"</table>";
		
		//Save to formSubmissions
		SavedEmail savedEmail = new SavedEmail();
		savedEmail.setCompany(company);
		savedEmail.setSender(given+" "+family);
		savedEmail.setEmail(email);
		savedEmail.setPhone(phone);
		savedEmail.setFormName("Registration");
		savedEmail.setEmailContent(content2);
		savedEmail.setUploadFileName(attachments2);
		savedEmailDelegate.insert(savedEmail);

		if(willSendMail){
			String[] attachements2 = new String[7];
			//System.out.println("servlet context::::::::::::::: " + servletContext.getRealPath(""));
			EmailUtil.connect("smtp.gmail.com", 25);
			attachments.toArray(attachements2);
			EmailUtil.sendWithManyAttachments("noreply@ivant.com", company.getEmail().split(","),requiredMemberInfo.get("Subject") , content,attachements2 );
			EmailUtil.send("noreply@ivant.com", email,requiredMemberInfo.get("Subject"), requiredMemberInfo.get("Message"));
		}
	}
	
	public void setInitCompanies(){
		if(company.getId()==150L){//Bright
			willSendMail=true;
			withLoginInfo=false;
			//This values will be set for members required informations
			requiredMemberInfo = new  TreeMap<String, String>();
			requiredMemberInfo.put("Family Name", request.getParameter("Family Name"));
			requiredMemberInfo.put("Given Name", request.getParameter("Given Name"));
			requiredMemberInfo.put("Username", request.getParameter("Family Name"));
			requiredMemberInfo.put("Password", request.getParameter("Given Name"));
			requiredMemberInfo.put("Email", request.getParameter("E-mail Address"));
			requiredMemberInfo.put("Phone", request.getParameter("Home Mobile"));
			
			//this is set for the subject and body of the email
			requiredMemberInfo.put("Subject", "REGISTRATION: Australia Bright Education Migration Centre");
			requiredMemberInfo.put("Message", "" +
					"Thank You: You have succesfully registered to Australia Bright Education Migration Centre" +
					"  \n\nYour registration status is now: Pending"+
					" \n\nWe will contact you for further instructions"+
					"\n\n\n-------------------------------\n This is an auto generated email - Do not reply through this email\n");
			
			//Set both files and file name, should be compatible with "listOfFiles" values
			//For each item in the "listOfFiles" there should be a corresponding file and filename mapped
			mapFile = new  TreeMap<String, File>();
			mapFile.put("digital", digital);
			mapFile.put("passport", passport);
			mapFile.put("visa", visa);
			mapFile.put("birth", birth);
			mapFile.put("financial", financial);
			mapFile.put("academic", academic);
			mapFile.put("ecoe", ecoe);
			mapFileName = new  TreeMap<String, String>();
			mapFileName.put("digital", "digital_"+digitalFileName);
			mapFileName.put("passport", "passport_"+passportFileName);
			mapFileName.put("visa", "visa_"+visaFileName);
			mapFileName.put("birth","birth_"+birthFileName);
			mapFileName.put("financial","financial_"+financialFileName);
			mapFileName.put("academic", "academic_"+academicFileName);
			mapFileName.put("ecoe", "ecoe_"+ecoeFileName);
		}
		//190 iave
		else if(company.getId()==190L){//iave
			willSendMail=false;
			withLoginInfo=true;
			//This values will be set for members required informations
			requiredMemberInfo = new  TreeMap<String, String>();
			requiredMemberInfo.put("Family Name", "");
			requiredMemberInfo.put("Given Name", request.getParameter("Organization Name"));
			requiredMemberInfo.put("Username", request.getParameter("Username"));
			requiredMemberInfo.put("Password", request.getParameter("Password"));
			requiredMemberInfo.put("Email", request.getParameter("E-mail Address"));
			requiredMemberInfo.put("Phone", request.getParameter("Mobile Number"));
			
			//this is set for the subject and body of the email
			requiredMemberInfo.put("Subject", "REGISTRATION: Australia Bright Education Migration Centre");
			requiredMemberInfo.put("Message", "" +
					"Thank You: You have succesfully registered to IAVE" +
					"  \n\nYour registration status is now: Pending"+
					" \n\nPlease Login to continue with your registration"+
					"\n\n\n-------------------------------\n This is an auto generated email - Do not reply through this email\n");
			
			//Set both files and file name, should be compatible with "listOfFiles" values
			//For each item in the "listOfFiles" there should be a corresponding file and filename mapped
			mapFile = new  TreeMap<String, File>();
			mapFileName = new  TreeMap<String, String>();
		}
		
		else{//No company data set (Probably will cause an error)
			withLoginInfo=false;
			willSendMail=false;
			//System.out.println("No company was set. This will Probably cause error at RegistrationAction.java");
			requiredMemberInfo = new  TreeMap<String, String>();
			requiredMemberInfo.put("Family Name", "NO FAMILY NAME");
			requiredMemberInfo.put("Given Name", "NO GIVEN NAME");
			requiredMemberInfo.put("Username", "NO USERNAME");
			requiredMemberInfo.put("Password", "NO PASSWORD");
			requiredMemberInfo.put("Email", "NO EMAIL");
			requiredMemberInfo.put("Phone", "NO PHONE");
			
			requiredMemberInfo.put("Subject", "No subject");
			requiredMemberInfo.put("Message", "No message Content");
			
			mapFile = new  TreeMap<String, File>();
			mapFileName = new  TreeMap<String, String>();
			
		}
	}
	
	public String successRegistration(){
		member = memberDelegate.find(Long.parseLong(request.getParameter("memberId")));
		info = new ArrayList<RegistrationItemOtherField>();
		info = member.getRegistrationItemOtherFields();
		return SUCCESS;
	}
	
	public void prepare() throws Exception {
		//list of allowed input fields
		listOfFields = new ArrayList();
		if(allowedFields!=null)
		for(String x: allowedFields){
			listOfFields.add(x);
		}
		//list of allowed input file upload fields
		listOfFiles = new ArrayList();
		if(allowedFiles!=null)
		for(String x: allowedFiles){
			listOfFiles.add(x);
		}
	}
	
	private boolean saveImages(File[] files,String[] filenames, Member member) {
				
		CompanySettings companySettings = company.getCompanySettings();
		
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));		
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "images";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "registrations";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));

		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "original"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image1"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image2"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "image3"));
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath + File.separator + "thumbnail"));

		destinationPath = servletContext.getRealPath(destinationPath);

		for(int i = 0; i < files.length; i++) {
			if(files[i]!=null)
			if(files[i].exists()) {
			String source = files[i].getAbsolutePath();
	
			//System.out.println("File #"+i+": source: "+source);
			
			
			String filename = FileUtil.insertPostfix(filenames[i].replace(" ", "_"),
			String.valueOf(new Date().getTime()));
	
	
			if (!FileUtil.getExtension(filename).equalsIgnoreCase("jpg") && !FileUtil.getExtension(filename).equalsIgnoreCase("jpeg") &&
			!FileUtil.getExtension(filename).equalsIgnoreCase("gif") )
			{
				return false;
			}

		
			if (FileUtil.getExtension(filename).equalsIgnoreCase("gif")) {

				Long companyId = company.getId();
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateGifThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
			
			
			if (FileUtil.getExtension(filename).endsWith("JPG")) {

				Long companyId = company.getId();
				filename = FileUtil.replaceExtension(filename, "jpg");
				FileUtil.getExtension(filename).toLowerCase();
				
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
			
			if (FileUtil.getExtension(filename).endsWith("GIF")) {
				
				filename = FileUtil.replaceExtension(filename, "gif");
				FileUtil.getExtension(filename).toLowerCase();
				Long companyId = company.getId();
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				
				// generate image 1
				File origFile2 = new File(destinationPath + File.separator + "image1" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile2);
			
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);				
		
				// instead of creating directly from the temporary file, create a file from the original file
			}
	
			//System.out.println("----------------------------------" + FileUtil.getExtension(filename));	
			
			if (FileUtil.getExtension(filename).equalsIgnoreCase("jpg") || FileUtil.getExtension(filename).equalsIgnoreCase("jpeg") || FileUtil.getExtension(filename).endsWith("JPG") )
			{
				logger.debug("inside the if resizer");
				// original image
				File origFile = new File(destinationPath + File.separator + "original" + File.separator + filename);
				
				// save the original files
				FileUtil.copyFile(files[i], origFile);				
				Long companyId = company.getId();
				// generate image 1
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image1" + File.separator + filename,
				companySettings.getImage1Width(), companySettings.getImage1Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage1Forced());
		
				// generate image 2
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image2" + File.separator + filename,
				companySettings.getImage2Width(), companySettings.getImage2Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage2Forced());
		
				// generate image 3
				ImageUtil.generateJpegImage(companyId, source, destinationPath + File.separator + "image3" + File.separator + filename,
				companySettings.getImage3Width(), companySettings.getImage3Heigth(),
				ImageUtil.DEFAULT_RESOLUTION, companySettings.getImage3Forced());
		
				// generate thumbnail
				ImageUtil.generateThumbnailImage(companyId, source, destinationPath + File.separator + "thumbnail" + File.separator + filename);
			}
			          
		}
		}
		
		return true;
	}
	
	private String saveSingleFile(File files,String filenames) {
		
		CompanySettings companySettings = company.getCompanySettings();
		
		String destinationPath = "companies";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));		
		destinationPath += File.separator + company.getName();
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "attachments";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		destinationPath += File.separator + "registrations";
		FileUtil.createDirectory(servletContext.getRealPath(destinationPath));
		
		destinationPath = servletContext.getRealPath(destinationPath);
		
		
		String filename = FileUtil.insertPostfix(filenames.replace(" ", "_"),
				String.valueOf(new Date().getTime()));
		
		File origFile2 = new File(destinationPath  + File.separator + filename);
		FileUtil.copyFile(files, origFile2);

		return filename;
		
	}
	
	public String skip(){
		
		return SUCCESS;
	}
		
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
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

	@Override
	public void setMember(Member member) {
		this.member = member;
		
	}
	
	public Member getMember(Member member){
		return member;
	}

	public void setPrevPage(String prevPage) {
		this.prevPage = prevPage;
	}

	public String getPrevPage() {
		return prevPage;
	}

	public void setActivationLink(String activationLink) {
		this.activationLink = activationLink;
	}

	public String getActivationLink() {
		return activationLink;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

	public void setPassport(File passport) {
		this.passport = passport;
	}

	public File getPassport() {
		return passport;
	}

	public void setDigital(File digital) {
		this.digital = digital;
	}

	public File getDigital() {
		return digital;
	}

	public void setVisa(File visa) {
		this.visa = visa;
	}

	public File getVisa() {
		return visa;
	}

	public void setBirth(File birth) {
		this.birth = birth;
	}

	public File getBirth() {
		return birth;
	}

	public void setFinancial(File financial) {
		this.financial = financial;
	}

	public File getFinancial() {
		return financial;
	}

	public void setAcademic(File academic) {
		this.academic = academic;
	}

	public File getAcademic() {
		return academic;
	}

	public void setEcoe(File ecoe) {
		this.ecoe = ecoe;
	}

	public File getEcoe() {
		return ecoe;
	}

	public void setDigitalFileName(String digitalFileName) {
		this.digitalFileName = digitalFileName;
	}

	public String getDigitalFileName() {
		return digitalFileName;
	}

	public void setPassportFileName(String passportFileName) {
		this.passportFileName = passportFileName;
	}

	public String getPassportFileName() {
		return passportFileName;
	}

	public void setVisaFileName(String visaFileName) {
		this.visaFileName = visaFileName;
	}

	public String getVisaFileName() {
		return visaFileName;
	}

	public void setBirthFileName(String birthFileName) {
		this.birthFileName = birthFileName;
	}

	public String getBirthFileName() {
		return birthFileName;
	}

	public void setFinancialFileName(String financialFileName) {
		this.financialFileName = financialFileName;
	}

	public String getFinancialFileName() {
		return financialFileName;
	}

	public void setAcademicFileName(String academicFileName) {
		this.academicFileName = academicFileName;
	}

	public String getAcademicFileName() {
		return academicFileName;
	}

	public void setEcoeFileName(String ecoeFileName) {
		this.ecoeFileName = ecoeFileName;
	}

	public String getEcoeFileName() {
		return ecoeFileName;
	}

	public void setInfo(List<RegistrationItemOtherField> info) {
		this.info = info;
	}

	public List<RegistrationItemOtherField> getInfo() {
		return info;
	}

	public void setAllowedFields(String[] allowedFields) {
		this.allowedFields = allowedFields;
	}

	public String[] getAllowedFields() {
		return allowedFields;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setAllowedFiles(String[] allowedFiles) {
		this.allowedFiles = allowedFiles;
	}

	public String[] getAllowedFiles() {
		return allowedFiles;
	}

	public void setListOfFiles(List<String> listOfFiles) {
		this.listOfFiles = listOfFiles;
	}

	public List<String> getListOfFiles() {
		return listOfFiles;
	}
	
}
