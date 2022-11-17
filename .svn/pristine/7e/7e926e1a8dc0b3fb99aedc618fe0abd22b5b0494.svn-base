package com.ivant.cms.action.admin;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

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
import com.ivant.cms.interfaces.UserAware;
import com.ivant.utils.EmailUtil;
import com.ivant.utils.FileUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
/*
 * Author: Glenn Allen Sapla
 * Date: Aug 26, 2010
 * 
 * NOT IN USE PLEASE REFER TO THE "REGISTRATION ACTION CONTROLLER"
 */
public class RegistrantsAction extends ActionSupport implements Preparable,CompanyAware,UserAware,ServletRequestAware,ServletContextAware {

	private static final long serialVersionUID = -6584704073037144700L;
	private static final Logger logger = Logger.getLogger(ListFormSubmissionAction.class);
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	private static final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private RegistrationItemOtherFieldDelegate registrationDelegate= RegistrationItemOtherFieldDelegate.getInstance();

	private ServletContext servletContext;
	private HttpServletRequest request;
	private Company company;
	private long submissionId;
	private String selectedValues;
	private User user;
	private String status;
	
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
	
	/*public String delete() {
		Member member = memberDelegate.find(submissionId);
		if(member.getCompany().equals(company)) {
			memberDelegate.delete(member);
		}
		return SUCCESS; 
	} 
	
	public String deleteMultiple() {
		try{
			selectedValues = request.getParameter("selectedValues");
			
			System.out.println(selectedValues); //testing
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
	*/
	@Override
	public void prepare() throws Exception {
		/*//list of allowed input fields
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
		}*/
	}
	/*
	public String updateStatus(){
		Member memberTemp= memberDelegate.find(this.submissionId);
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
	
	public String updateStatusNoEmail(){
		Member memberTemp= memberDelegate.find(this.submissionId);
		memberTemp.setStatus(status);
		memberDelegate.update(memberTemp);
		return SUCCESS;
	}
	
	public String update(){
		Enumeration temp = request.getParameterNames();
		String tempName="";
		RegistrationItemOtherField registrationItem = new RegistrationItemOtherField();
		
		this.setInitCompanies();//init hardcoded company values
		
		Member memberTemp= memberDelegate.find(this.submissionId);
		memberTemp.setLastname(requiredMemberInfo.get("Family Name"));
		memberTemp.setFirstname(requiredMemberInfo.get("Given Name"));
		memberTemp.setEmail(requiredMemberInfo.get("Email"));
		memberDelegate.update(memberTemp);
		
		//update field values
		while(temp.hasMoreElements()){	
			registrationItem = new RegistrationItemOtherField();
			tempName=temp.nextElement().toString();
				if(listOfFields.contains(tempName.trim())){
					registrationItem = registrationDelegate.findByName(company, tempName.trim(), this.submissionId);
					registrationItem.setContent(request.getParameter(tempName));
					registrationDelegate.update(registrationItem);
				}
		}

		
		//update field values
		for(int i=0; i<listOfFields.size();i++){	
			registrationItem = new RegistrationItemOtherField();
			tempName=listOfFields.get(i);
				if(request.getParameter(tempName.trim())!=null){
					registrationItem = registrationDelegate.findByName(company, tempName.trim(), this.submissionId);
					registrationItem.setContent(request.getParameter(tempName));
					registrationDelegate.update(registrationItem);
				}
				else{
					registrationItem = registrationDelegate.findByName(company, tempName.trim(), this.submissionId);
					registrationItem.setContent("");
					registrationDelegate.update(registrationItem);
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
	
	public void setInitCompanies(){
		mapFile = new  TreeMap<String, File>();
		mapFileName = new  TreeMap<String, String>();
		if(company.getId()==150L){//Bright
			requiredMemberInfo = new  TreeMap<String, String>();
			requiredMemberInfo.put("Family Name", request.getParameter("Family Name"));
			requiredMemberInfo.put("Given Name", request.getParameter("Given Name"));
			requiredMemberInfo.put("Username", request.getParameter("Family Name"));
			requiredMemberInfo.put("Password", request.getParameter("Given Name"));
			requiredMemberInfo.put("Email", request.getParameter("E-mail Address"));
			requiredMemberInfo.put("Phone", request.getParameter("Home Mobile"));

			mapFile.put("digital", digital);
			mapFile.put("passport", passport);
			mapFile.put("visa", visa);
			mapFile.put("birth", birth);
			mapFile.put("financial", financial);
			mapFile.put("academic", academic);
			mapFile.put("ecoe", ecoe);

			getMapFileName().put("digital", "digital_"+digitalFileName);
			getMapFileName().put("passport", "passport_"+passportFileName);
			getMapFileName().put("visa", "visa_"+visaFileName);
			getMapFileName().put("birth","birth_"+birthFileName);
			getMapFileName().put("financial","financial_"+financialFileName);
			getMapFileName().put("academic", "academic_"+academicFileName);
			getMapFileName().put("ecoe", "ecoe_"+ecoeFileName);
		}
		else if(company.getId()==190L){//iave

			//This values will be set for members required informations
			requiredMemberInfo = new  TreeMap<String, String>();
			requiredMemberInfo.put("Family Name", request.getParameter("Organization Name"));
			requiredMemberInfo.put("Given Name", "");
			requiredMemberInfo.put("Username", request.getParameter("Username"));
			requiredMemberInfo.put("Password", request.getParameter("Password"));
			requiredMemberInfo.put("Email", request.getParameter("E-mail Address"));
			requiredMemberInfo.put("Phone", request.getParameter("Mobile Number"));
			
			//this is set for the subject and body of the email
			requiredMemberInfo.put("Subject", "");
			requiredMemberInfo.put("Message", "");
		}
		else{
			requiredMemberInfo = new  TreeMap<String, String>();
			requiredMemberInfo.put("Family Name", "NO FAMILY NAME");
			requiredMemberInfo.put("Given Name", "NO GIVEN NAME");
			requiredMemberInfo.put("Username", "NO USERNAME");
			requiredMemberInfo.put("Password", "NO PASSWORD");
			requiredMemberInfo.put("Email", "NO EMAIL");
			requiredMemberInfo.put("Phone", "NO PHONE");
			
			requiredMemberInfo.put("Subject", "No subject");
			requiredMemberInfo.put("Message", "No message Content");
		}
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
			System.out.println(filenames+" FILE IS NULL");
			return null;
		}
	}
	*/
	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;	
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void setDigital(File digital) {
		this.digital = digital;
	}

	public File getDigital() {
		return digital;
	}

	public void setPassport(File passport) {
		this.passport = passport;
	}

	public File getPassport() {
		return passport;
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

	public void setInfo(List<RegistrationItemOtherField> info) {
		this.info = info;
	}

	public List<RegistrationItemOtherField> getInfo() {
		return info;
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

	public void setAllowedFields(String[] allowedFields) {
		this.allowedFields = allowedFields;
	}

	public String[] getAllowedFields() {
		return allowedFields;
	}

	public void setListOfFields(List<String> listOfFields) {
		this.listOfFields = listOfFields;
	}

	public List<String> getListOfFields() {
		return listOfFields;
	}
	
	@Override
	public void setUser(User user) {
		this.user = user;
		
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
}
