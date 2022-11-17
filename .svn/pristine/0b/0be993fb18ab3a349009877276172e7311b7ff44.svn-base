package com.ivant.cms.action.registration;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.action.admin.ListFormSubmissionAction;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.OtherField;
import com.ivant.cms.entity.RegistrationItemOtherField;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.CompanyRegistration;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.ivant.utils.ImageUtil;
import com.ivant.utils.PasswordEncryptor;
import com.ivant.utils.SortingUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;



/**
 * For Registration Modules of companies
 * 
 * @author Glenn Allen B. Sapla
 *
 */
public abstract class AbstractBaseRegistrationAction extends ActionSupport implements Preparable,SessionAware, ServletRequestAware,
ServletContextAware, CompanyAware, MemberAware, CompanyRegistration {

	protected Logger logger = Logger.getLogger(getClass());
	protected MemberDelegate memberDelegate = MemberDelegate.getInstance();
	protected GroupDelegate groupDelegate = GroupDelegate.getInstance();
	protected OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	protected RegistrationItemOtherFieldDelegate registrationDelegate= RegistrationItemOtherFieldDelegate.getInstance();
	protected static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	protected String username;
	protected String password;

	protected Company company;
	protected Member member;
	protected String servletContextName;
	protected String pageName;
	protected String prevPage;
	protected String httpServer;
	protected String activationLink;
	protected ServletContext servletContext;
	protected HttpServletRequest request;
	
	protected boolean willSendMail;
	protected boolean withLoginInfo;
	protected String message;
	protected PasswordEncryptor encryptor;
	protected Long memberId;
	protected List<RegistrationItemOtherField> info;
	protected String[] allowedFields;
	protected String[] allowedFiles;
	protected List<String> listOfFields;
	protected List<String> listOfFiles;
	protected Map<String, File> mapFile;
	protected Map<String, String> mapFileName;
	protected Map<String, String> requiredMemberInfo;
	protected String attachmentNames;
	protected List<String> attachments;


	protected long submissionId;
	protected String selectedValues;
	protected User user;
	protected String status;

	public AbstractBaseRegistrationAction(){
		
	}

	@Override
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
	
	@Override
	public void prepare(List<String> listOfFieldsRepeating,List<String> listOfFields,List<String> listOfFiles,HttpServletRequest request, Company company, Member member
			, Map<String, File> mapFile,Map<String, String> mapFileName,ServletContext servletContext,String status){
		//list of allowed input fields
		this.listOfFields = listOfFields;
		//list of allowed input file upload fields
		this.listOfFiles = listOfFiles;
		this.request=request;
		this.company=company;
		this.member=member;
		this.mapFile=mapFile;
		this.status=status;
		this.servletContext =servletContext;
		if(this.mapFile==null)mapFile = new  TreeMap<String, File>();
		this.mapFileName=mapFileName;
		if(this.mapFileName==null)mapFileName = new  TreeMap<String, String>();
	}
	
	public String delete(Long submissionId) {
		Member member = memberDelegate.find(submissionId);
		if(member.getCompany().equals(company)) {
			memberDelegate.delete(member);
		}
		return SUCCESS; 
	} 
	
	public String deleteMultiple(String selectedValues) {
		try{
			selectedValues = request.getParameter("selectedValues");
			
			//System.out.println(selectedValues); //testing
			StringTokenizer st = new StringTokenizer(selectedValues,"_");
						
			while (st.hasMoreTokens()) {
				Member member = memberDelegate.find(Long.parseLong(st.nextToken()));
				
				if(member.getCompany().equals(company)) {
					memberDelegate.delete(member);
				}		
			}
			
		}
		catch(Exception e) {
			logger.fatal("problem intercepting action.", e);
			return ERROR;
		}
		return SUCCESS; 
	} 
	
	public void initializeCompany(){
		withLoginInfo=false;
		willSendMail=false;
		//System.out.println("No company was set. This will Probably cause error at RegistrationAction.java");
		requiredMemberInfo = new  TreeMap<String, String>();
		requiredMemberInfo.put("Full Name", "NO FULL NAME");
		requiredMemberInfo.put("Name on Certificate", "NO NAME ON CERTICATE");
		requiredMemberInfo.put("Family Name", "NO FAMILY NAME");
		requiredMemberInfo.put("Given Name", "NO GIVEN NAME");
		requiredMemberInfo.put("Username", "NO USERNAME");
		requiredMemberInfo.put("Password", "NO PASSWORD");
		requiredMemberInfo.put("Email", "NO EMAIL");
		requiredMemberInfo.put("Phone", "NO PHONE");
		
		requiredMemberInfo.put("Subject", "No subject");
		requiredMemberInfo.put("Message", "No message Content");
		
		
	}

	public Long executeRegistration(){
		//get all parameters
		RegistrationItemOtherField registrationItem = new RegistrationItemOtherField();
		Enumeration temp = request.getParameterNames();
		OtherField newOtherField= new OtherField();
		OtherField otherField= new OtherField();
		encryptor = new PasswordEncryptor();
		Long tempId=null;

		this.initializeCompany();//set filenames for companies (need to recode per company)
		
		Member memberTemp = new Member();
		if(member!=null && member.getCompany().equals(company)){
			memberTemp = member;
		}
		else{
			//Create Member
		
			memberTemp.setCompany(company);
			memberTemp.setUsername(requiredMemberInfo.get("Username"));
			memberTemp.setPassword(encryptor.encrypt(requiredMemberInfo.get("Password")));
			if(requiredMemberInfo.get("Last Name")!=null){
				memberTemp.setLastname(requiredMemberInfo.get("Last Name"));
			}else{
				memberTemp.setLastname(requiredMemberInfo.get("Family Name"));
			}
			if(requiredMemberInfo.get("Given Name")!=null){
				memberTemp.setFirstname(requiredMemberInfo.get("Given Name"));
			}
			memberTemp.setFullName(requiredMemberInfo.get("Full Name"));
			memberTemp.setNameOnCertificate(requiredMemberInfo.get("Name on Certificate"));
			memberTemp.setEmail(requiredMemberInfo.get("Email"));
			
			if(company.getId()==55) {
				//System.out.println(memberDelegate.findEmail(company, memberTemp.getEmail()));
				if(memberDelegate.findEmail(company, memberTemp.getEmail())!=null){
					message = "ERROR: Email already in use";
					return -1L;
				}
			}
			
			memberTemp.setDateJoined(new Date());
			memberTemp.setPurpose("Registration");
			memberTemp.setIsValid(Boolean.TRUE);
			memberTemp.setStatus("Pending");
			memberTemp.setNewsletter(false);
			//System.out.println("Password AbstractBase "+requiredMemberInfo.get("Password"));
			//System.out.println("Last AbstractBase "+requiredMemberInfo.get("Full Name"));
			//System.out.println("Given AbstractBase "+requiredMemberInfo.get("Name on Certificate"));
		
		}
		Long tempId2=0L;
		//if member is logged in update member otherwise insert new
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
			return -1L;
		}
	
		//add Member Id to (temporary)username to make it unique
		if(!withLoginInfo){

			if(company.getId()==150){ 
				
				memberTemp2.setUsername(memberTemp2.getUsername()+tempId2);
			}else{
				memberTemp2.setUsername(memberTemp2.getUsername()); //GamePlace wherein username is given by the user
			}
			
		}
		
		memberDelegate.update(memberTemp2);
		memberTemp2 = memberDelegate.find(tempId2);//refresh object
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
					else registrationItem.setContent("");
				}
				else registrationItem.setContent("");
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
		attachmentNames="";
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
			attachmentNames=attachmentNames+" "+imageName;
			registrationItem.setContent(imageName);
			registrationItem.setOtherField(otherField);
			registrationItem.setMember(memberTemp2);
			registrationDelegate.insert(registrationItem);	
		}
		
		//get registration items and refresh member object
		Member mem = memberDelegate.find(tempId2);
		if(company.getId()!=194){
			memberDelegate.refresh(mem);
		}
		
		info = new ArrayList<RegistrationItemOtherField>();
		info= mem.getRegistrationItemOtherFields();
	
		//sort registration items according to their sort order
		if(info!=null)
			SortingUtil.sortBaseObject("otherField.sortOrder", true,info );
		
		this.sendEmail();
		
		return memberId;
	}
	
	

	public void sendEmail(){
		//Construct email attachments and body
		String email="",family="", given="",phone="",content="",fullName="",nameOnCertificate="";
		email= requiredMemberInfo.get("Email");
		if(requiredMemberInfo.get("Last Name")!=null){
			family= requiredMemberInfo.get("Last Name");
		}else{
			family= requiredMemberInfo.get("Family Name");
		}
		if(requiredMemberInfo.get("Full Name")!=null){
			fullName= requiredMemberInfo.get("Full Name");
		}
		if(requiredMemberInfo.get("Name on Certificate")!=null){
			nameOnCertificate= requiredMemberInfo.get("Name on Certificate");
		}
		given= requiredMemberInfo.get("Given Name");
		phone= requiredMemberInfo.get("Phone");
		content= this.generateEmailBodyAndAttachments();
		//Save to formSubmissions
		SavedEmail savedEmail = new SavedEmail();
		savedEmail.setCompany(company);
		if(fullName !=null){
			savedEmail.setSender(fullName);
		}else{
			savedEmail.setSender(given+" "+family);
		}
		
		savedEmail.setEmail(email);
		savedEmail.setPhone(phone);
		savedEmail.setFormName("Registration");
		savedEmail.setEmailContent(content);
		if(attachmentNames!=null){
			savedEmail.setUploadFileName(attachmentNames);
		}
		
	
		savedEmailDelegate.insert(savedEmail);

		if(willSendMail){
			String[] attachements2 = new String[7];
			//System.out.println("servlet context::::::::::::::: " + servletContext.getRealPath(""));
			EmailUtil.connect("smtp.gmail.com", 25);
	 
			attachments.toArray(attachements2);
			if(company.getCompanySettings().getManyAttachments()){
		//	if(company.getId()!=194 && company.getId()!=199 && company.getId()!=192 && company.getId()!=208 && company.getId()!=49 && company.getId()!=217){
				
				EmailUtil.sendWithManyAttachments("noreply@ivant.com", company.getEmail().split(","),requiredMemberInfo.get("Subject"), content,attachements2 );
			}else{
				System.out.println("COMPANY ID w/o MANY ATTACHMENTS"+company.getId());
			}
			
			EmailUtil.send("noreply@ivant.com", requiredMemberInfo.get("Email"),requiredMemberInfo.get("Subject"), requiredMemberInfo.get("Message"));
		}
	}
	
	public String generateEmailBodyAndAttachments(){
		String content="<table>";
		String content2="";
		
		content2="-----------------"+company.getName() +"Registration--------------------<br/><br/>";
	
		
		String fieldValue="";
		attachments=new ArrayList<String>();
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
		
		return content2;
	}
	
	public  String saveSingleFile(File files,String filenames) {
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
	
	
	public String updateStatus(Long submissionId){
		Member memberTemp= memberDelegate.find(submissionId);
		memberTemp.setStatus(status);
		memberDelegate.update(memberTemp);
		EmailUtil.connect("smtp.gmail.com", 25);
		EmailUtil.send("noreply@ivant.com", memberTemp.getEmail() ,company.getNameEditable()+" Registration: Status Update", company.getNameEditable()+" Registration: Status Update\n\n"+
				memberTemp.getFullName()+ " "+
				"\n\n       Your registration status is now: "+status+
				" \n\n"+company.getNameEditable()+
				"\n\n\n-------------------------------\n This is an auto generated email - Do not reply through this email\n");
		EmailUtil.send("noreply@ivant.com", company.getEmail().split(","),company.getNameEditable()+" Registration: Status Update", 
				company.getNameEditable()+" Registration: Status Update\n\n"+
				memberTemp.getFullName()+ "'s registration status is now changed to : "+status+
				"\n\n\n-------------------------------\n This is an auto generated email - Do not reply through this email\n", null);
		return SUCCESS;
	}
	
	public String updateStatusNoEmail(Long submissionId){
		Member memberTemp= memberDelegate.find(submissionId);
		memberTemp.setStatus(status);
		memberDelegate.update(memberTemp);
		return SUCCESS;
	}
	
	public String update(Long submissionId){
		this.submissionId=submissionId;
		Enumeration temp = request.getParameterNames();
		String tempName="";
		RegistrationItemOtherField registrationItem = new RegistrationItemOtherField();
		
		this.initializeCompany();//init hardcoded company values
		
		Member memberTemp= memberDelegate.find(this.submissionId);
		memberTemp.setLastname(requiredMemberInfo.get("Family Name"));
		memberTemp.setFirstname(requiredMemberInfo.get("Given Name"));
		memberTemp.setEmail(requiredMemberInfo.get("Email"));
		memberDelegate.update(memberTemp);

		//update field values
		for(int i=0; i<listOfFields.size();i++){	
			registrationItem = new RegistrationItemOtherField();
			tempName=listOfFields.get(i);
				if(request.getParameter(tempName.trim())!=null){
					registrationItem = registrationDelegate.findByName(company, tempName.trim(), this.submissionId);
					if(registrationItem==null){
						OtherField otherField= new OtherField();
							registrationItem = new RegistrationItemOtherField();
							registrationItem.setContent(request.getParameter(tempName));
							registrationItem.setCompany(company);
							registrationItem.setIsValid(true);
							registrationItem.setMember(memberTemp);
							otherField = otherFieldDelegate.find(tempName.trim(), company);
							if(otherField==null){
								otherField= new OtherField();
								otherField.setCompany(company);
								otherField.setIsValid(true);
								otherField.setName(tempName.trim());
								otherField.setSortOrder(1);
								otherField.setGroup(groupDelegate.find(100L));
								Long tempId=otherFieldDelegate.insert(otherField);
								otherField = otherFieldDelegate.find(tempId);
							}
							registrationItem.setOtherField(otherField);
							registrationDelegate.insert(registrationItem);
						}
					else{
						registrationItem.setContent(request.getParameter(tempName));
						registrationDelegate.update(registrationItem);
					}
				}
				else{
					registrationItem = registrationDelegate.findByName(company, tempName.trim(), this.submissionId);
					if(registrationItem==null){
					OtherField otherField= new OtherField();
						registrationItem = new RegistrationItemOtherField();
						registrationItem.setContent("");
						registrationItem.setCompany(company);
						registrationItem.setIsValid(true);
						registrationItem.setMember(memberTemp);
						otherField = otherFieldDelegate.find(tempName.trim(), company);
						if(otherField==null){
							otherField= new OtherField();
							otherField.setCompany(company);
							otherField.setIsValid(true);
							otherField.setName(tempName.trim());
							otherField.setSortOrder(1);
							otherField.setGroup(groupDelegate.find(100L));
							Long tempId=otherFieldDelegate.insert(otherField);
							otherField = otherFieldDelegate.find(tempId);
						}
						registrationItem.setOtherField(otherField);
						registrationDelegate.insert(registrationItem);
					}
					else{
						registrationItem.setContent("");
						registrationDelegate.update(registrationItem);
					}
				}
		}

		
		//update files upload
		String imageName="";
		for(int i=0; i<listOfFiles.size();i++){
			registrationItem = registrationDelegate.findByName(company, listOfFiles.get(i), this.submissionId);
			imageName=saveSingleFile(mapFile.get(listOfFiles.get(i)),getMapFileName().get(listOfFiles.get(i)),registrationDelegate.findByName(company, listOfFiles.get(i), submissionId).getContent());
			if(imageName!=null){
				registrationItem.setContent(imageName);
				registrationDelegate.update(registrationItem);	
			}
		}
		return SUCCESS;
	}
	
	
	private String saveSingleFile(File files,String filenames, String oldFileToDelete) {
		if(files!=null){
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
		FileUtil.deleteFile(destinationPath  + File.separator + oldFileToDelete);
		FileUtil.copyFile(files, origFile2);
		return filename;
		}
		else{
			//System.out.println(filenames+" FILE IS NULL");
			return null;
		}
	}
	
	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}

	public void setListOfFields(List<String> listOfFields) {
		this.listOfFields = listOfFields;
	}

	public List<String> getListOfFields() {
		return listOfFields;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setMapFileName(Map<String, String> mapFileName) {
		this.mapFileName = mapFileName;
	}

	public Map<String, String> getMapFileName() {
		return mapFileName;
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

	@Override
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
