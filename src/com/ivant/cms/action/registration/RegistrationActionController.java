package com.ivant.cms.action.registration;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.CategoryDelegate;
import com.ivant.cms.delegate.CategoryItemDelegate;
import com.ivant.cms.delegate.FormPageDelegate;
import com.ivant.cms.delegate.GroupDelegate;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.OtherFieldDelegate;
import com.ivant.cms.delegate.RegistrationItemOtherFieldDelegate;
import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.delegate.SinglePageDelegate;
import com.ivant.cms.entity.CategoryItem;
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
import com.ivant.cms.interfaces.CompanyRegistration;
import com.ivant.cms.interfaces.CompanyRegistration;
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
 * For registration you must consider the jsp pages and the setInitCompany methods at the ff classes
 * RegistrantsAction.java, RegistrationAction.java, ListRegistrantsAction.java
 * For JSP page refer to companies/bright/registration.jsp, admin/registration.jsp, admin/editRegistration.jsp
 */
public class RegistrationActionController extends ActionSupport implements Preparable, SessionAware, ServletRequestAware,
			ServletContextAware, CompanyAware, MemberAware {

	private Logger logger = Logger.getLogger(getClass());
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private GroupDelegate groupDelegate = GroupDelegate.getInstance();
	private CategoryDelegate categoryDelegate = CategoryDelegate.getInstance();
	private OtherFieldDelegate otherFieldDelegate = OtherFieldDelegate.getInstance();
	private RegistrationItemOtherFieldDelegate registrationDelegate= RegistrationItemOtherFieldDelegate.getInstance();
	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate.getInstance();
	
	private Map session;
	private HttpServletRequest request;
	private String year;
	
	private boolean login = false;
	
	private Company company;
	private Member member;
	private String servletContextName;
	private String pageName;
	private String prevPage;
	private String httpServer;
	private String activationLink;
	private Integer yearLimit;

	private ServletContext servletContext;
	private boolean isLocal;
	private boolean willSendMail;
	private boolean withLoginInfo;
	private String message;
	private Long memberId;
	private Integer formPage;
	
	
	private PasswordEncryptor encryptor;
	private List<RegistrationItemOtherField> info;
	private String attachmentNames;
	private String[] allowedFields;
	private String[] allowedFiles;
	private File[] uploadedFiles;
	private String[] uploadedFilesFileName;
	private List<String> listOfFields;
	private List<String> listOfFiles;
	private Map<String, File> mapFile;
	private Map<String, String> mapFileName;
	private String[] mapFileFieldName;
	private Map<String, String> requiredMemberInfo;
	private long submissionId;
	private String status;
	private String selectedValues;
	private String[] allowedFieldsRepeating;
	private List<String> listOfFieldsRepeating;
	private String content;
	private CategoryItemDelegate categoryItemDelegate = CategoryItemDelegate.getInstance();
	private CategoryItem catItem;
	private String totalPrice;
	private String finalPrice;
	private String pnpaStr;
	
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
		
		listOfFieldsRepeating = new ArrayList();
		if(getAllowedFieldsRepeating()!=null)
		for(String x: getAllowedFieldsRepeating()){
			listOfFieldsRepeating.add(x);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		
		//kaptcha
		String kaptchaReceived = (String)request.getParameter("kaptcha");
		//System.out.println("kaptcha: "+kaptchaReceived);
		if(kaptchaReceived != null)
		{
			String kaptchaExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			
			if (!kaptchaReceived.equalsIgnoreCase(kaptchaExpected))
			{
				return Action.ERROR;
			}
		}
		
		if(request.getParameter("formPage")==null){
			setFormPage(2);
		}else
			setFormPage(Integer.parseInt(request.getParameter("formPage"))+1);
		if(request.getParameter("year")!=null)
			content=""+(Integer.parseInt(request.getParameter("year"))+1);
		else{
			content="2007";
		}
		Calendar cal = Calendar.getInstance();
		yearLimit= cal.get(Calendar.YEAR)-2006;
		if(request.getParameter("year")!=null)
		if(Integer.parseInt(request.getParameter("year")) > 2006+yearLimit || Integer.parseInt(request.getParameter("year"))<=2006)
			return ERROR;
		
		mapFile = new  TreeMap<String, File>();
		mapFileName = new  TreeMap<String, String>();
		if(uploadedFiles!=null)
		for(int x=0; x<uploadedFiles.length;x++){
			mapFile.put(mapFileFieldName[x], uploadedFiles[x]);
			mapFileName.put(mapFileFieldName[x],mapFileFieldName[x]+"_"+ uploadedFilesFileName[x]);
		}
		
		Class<?> clazz = Class.forName(company.getRegistrationClassName());
		AbstractBaseRegistrationAction registrator = (AbstractBaseRegistrationAction)clazz.newInstance();

		registrator.prepare(getListOfFieldsRepeating(),listOfFields, listOfFiles,request,company,member,mapFile,mapFileName,servletContext,status);
		memberId=registrator.executeRegistration();
		
		if(memberId==-1L){
			message="Error: Username Already in Use";
			return ERROR;
		}

/*		if(request.getParameter("productPackage") != null || request.getParameter("pnpaStr")!=null){
			submitInfo();
		} */
		message="Thank You! You have succesfully registered.";
		return SUCCESS;
	}
	
/*	public String submitInfo(){
		if(request.getParameter("productPackage")!=null){
			catItem=categoryItemDelegate.find(Long.parseLong(request.getParameter("productPackage")));
		}
		if(request.getParameter("pnpaStr")!=null){
			CategoryItem it=new CategoryItem();
			it.setCompany(getCompany());
			it.setName(request.getParameter("pnpaStr"));
			it.setPrice(Double.parseDouble(request.getParameter("finalPrice")));
			it.setIsValid(Boolean.TRUE);
			it.setParent(categoryDelegate.find(Long.parseLong("3063")));
			it.setParentGroup(groupDelegate.find(Long.parseLong("189")));
		
			catItem=categoryItemDelegate.find(categoryItemDelegate.insert(it));
			
		}
		
		System.out.println("direct to payapal "+catItem.getName());
		totalPrice=request.getParameter("totalPrice");
		finalPrice=request.getParameter("finalPrice");
		
		return SUCCESS;
	}  */
	public String update() throws Exception {
		if(request.getParameter("year")!=null)
			content=""+(Integer.parseInt(request.getParameter("year")));
		
		mapFile = new  TreeMap<String, File>();
		mapFileName = new  TreeMap<String, String>();
		if(uploadedFiles!=null)
		for(int x=0; x<uploadedFiles.length;x++){
			mapFile.put(mapFileFieldName[x], uploadedFiles[x]);
			mapFileName.put(mapFileFieldName[x],mapFileFieldName[x]+"_"+ uploadedFilesFileName[x]);
		}
		
		Class<?> clazz = Class.forName(company.getRegistrationClassName());
		AbstractBaseRegistrationAction registrator = (AbstractBaseRegistrationAction)clazz.newInstance();

		registrator.prepare(getListOfFieldsRepeating(),listOfFields, listOfFiles,request,company,member,mapFile,mapFileName,servletContext,status);
		registrator.update(getSubmissionId());
		
		return SUCCESS;
	}
	
	public String updateStatus() throws Exception {
		mapFile = new  TreeMap<String, File>();
		mapFileName = new  TreeMap<String, String>();
		if(uploadedFiles!=null)
		for(int x=0; x<uploadedFiles.length;x++){
			mapFile.put(mapFileFieldName[x], uploadedFiles[x]);
			mapFileName.put(mapFileFieldName[x],mapFileFieldName[x]+"_"+ uploadedFilesFileName[x]);
		}
		
		Class<?> clazz = Class.forName(company.getRegistrationClassName());
		AbstractBaseRegistrationAction registrator = (AbstractBaseRegistrationAction)clazz.newInstance();

		registrator.prepare(getListOfFieldsRepeating(),listOfFields, listOfFiles,request,company,member,mapFile,mapFileName,servletContext,status);
		registrator.updateStatus(getSubmissionId());
		
		return SUCCESS;
	}
	
	public String updateStatusNoEmail() throws Exception {
		mapFile = new  TreeMap<String, File>();
		mapFileName = new  TreeMap<String, String>();
		if(uploadedFiles!=null)
		for(int x=0; x<uploadedFiles.length;x++){
			mapFile.put(mapFileFieldName[x], uploadedFiles[x]);
			mapFileName.put(mapFileFieldName[x],mapFileFieldName[x]+"_"+ uploadedFilesFileName[x]);
		}
		
		Class<?> clazz = Class.forName(company.getRegistrationClassName());
		AbstractBaseRegistrationAction registrator = (AbstractBaseRegistrationAction)clazz.newInstance();

		registrator.prepare(getListOfFieldsRepeating(),listOfFields, listOfFiles,request,company,member,mapFile,mapFileName,servletContext,status);
		registrator.updateStatusNoEmail(getSubmissionId());
		
		return SUCCESS;
	}

	
	public String delete() throws Exception {
		mapFile = new  TreeMap<String, File>();
		mapFileName = new  TreeMap<String, String>();
		if(uploadedFiles!=null)
		for(int x=0; x<uploadedFiles.length;x++){
			mapFile.put(mapFileFieldName[x], uploadedFiles[x]);
			mapFileName.put(mapFileFieldName[x],mapFileFieldName[x]+"_"+ uploadedFilesFileName[x]);
		}
		
		Class<?> clazz = Class.forName(company.getRegistrationClassName());
		AbstractBaseRegistrationAction registrator = (AbstractBaseRegistrationAction)clazz.newInstance();

		registrator.prepare(getListOfFieldsRepeating(),listOfFields, listOfFiles,request,company,member,mapFile,mapFileName,servletContext,status);
		registrator.delete(getSubmissionId());
		
		return SUCCESS;
	} 
	
	public String deleteMultiple() throws Exception {
		mapFile = new  TreeMap<String, File>();
		mapFileName = new  TreeMap<String, String>();
		if(uploadedFiles!=null)
		for(int x=0; x<uploadedFiles.length;x++){
			mapFile.put(mapFileFieldName[x], uploadedFiles[x]);
			mapFileName.put(mapFileFieldName[x],mapFileFieldName[x]+"_"+ uploadedFilesFileName[x]);
		}
		
		Class<?> clazz = Class.forName(company.getRegistrationClassName());
		AbstractBaseRegistrationAction registrator = (AbstractBaseRegistrationAction)clazz.newInstance();

		registrator.prepare(getListOfFieldsRepeating(),listOfFields, listOfFiles,request,company,member,mapFile,mapFileName,servletContext,status);
		registrator.deleteMultiple(selectedValues);
		
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

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setUploadedFiles(File[] uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public File[] getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFilesFileName(String[] uploadedFilesFileName) {
		this.uploadedFilesFileName = uploadedFilesFileName;
	}

	public String[] getUploadedFilesFileName() {
		return uploadedFilesFileName;
	}

	public void setMapFileFieldName(String[] mapFileFieldName) {
		this.mapFileFieldName = mapFileFieldName;
	}

	public String[] getMapFileFieldName() {
		return mapFileFieldName;
	}

	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}

	public long getSubmissionId() {
		return submissionId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	public String getSelectedValues() {
		return selectedValues;
	}

	public void setFormPage(Integer formPage) {
		this.formPage = formPage;
	}

	public Integer getFormPage() {
		return formPage;
	}

	public void setListOfFieldsRepeating(List<String> listOfFieldsRepeating) {
		this.listOfFieldsRepeating = listOfFieldsRepeating;
	}

	public List<String> getListOfFieldsRepeating() {
		return listOfFieldsRepeating;
	}

	public void setAllowedFieldsRepeating(String[] allowedFieldsRepeating) {
		this.allowedFieldsRepeating = allowedFieldsRepeating;
	}

	public String[] getAllowedFieldsRepeating() {
		return allowedFieldsRepeating;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setYearLimit(Integer yearLimit) {
		this.yearLimit = yearLimit;
	}

	public Integer getYearLimit() {
		return yearLimit;
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

	public String getPnpaStr() {
		return pnpaStr;
	}

	public void setPnpaStr(String pnpaStr) {
		this.pnpaStr = pnpaStr;
	}
	




	
}
