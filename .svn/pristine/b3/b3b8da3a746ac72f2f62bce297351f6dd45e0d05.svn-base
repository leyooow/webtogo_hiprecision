package com.ivant.cms.action.registration;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
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
 * For Registration Module for Iave
 * 
 * @author Glenn Allen B. Sapla
 *
 */
public class IaveRegistrationAction extends AbstractBaseRegistrationAction{
	private String[] allowedFieldsRepeating;
	private List<String> listOfFieldsRepeating;
	
	public IaveRegistrationAction(){
		
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
		
		listOfFieldsRepeating = new ArrayList();
		if(allowedFieldsRepeating!=null)
		for(String x: allowedFieldsRepeating){
			listOfFieldsRepeating.add(x);
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
		this.listOfFieldsRepeating=listOfFieldsRepeating;
	}
	
	
	@Override
	public void initializeCompany(){
			willSendMail=false;
			withLoginInfo=true; //does registration provide login details
			//This values will be set for members required informations
			requiredMemberInfo = new  TreeMap<String, String>();
			requiredMemberInfo.put("Last Name", request.getParameter("Last Name"));
			requiredMemberInfo.put("Given Name", request.getParameter("First Name"));
			requiredMemberInfo.put("Username", request.getParameter("Username"));
			requiredMemberInfo.put("Password", request.getParameter("Password"));
			requiredMemberInfo.put("Email", request.getParameter("E-mail Address"));
			requiredMemberInfo.put("Phone", request.getParameter("Mobile Number"));
			
			//this is set for the subject and body of the email
			requiredMemberInfo.put("Subject", "");
			requiredMemberInfo.put("Message", "");
			
			mapFile = new  TreeMap<String, File>();
			mapFileName = new  TreeMap<String, String>();
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
			memberTemp.setLastname(requiredMemberInfo.get("Last Name"));
			memberTemp.setFirstname(requiredMemberInfo.get("Given Name"));
			memberTemp.setEmail(requiredMemberInfo.get("Email"));
			memberTemp.setDateJoined(new Date());
			memberTemp.setPurpose("Registration");
			memberTemp.setIsValid(true);
			memberTemp.setStatus("Pending");
			memberTemp.setNewsletter(false);
		
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
			memberTemp2.setUsername(memberTemp2.getUsername()+tempId2);
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
					registrationItem  = registrationDelegate.findByName(company, field, member.getId(),request.getParameter("year"),null);
				}
				if(registrationItem==null){
					registrationItem = new RegistrationItemOtherField();
					registrationItem.setCompany(company);
					registrationItem.setIsValid(true);
					update=false;
				}
				else{
					if(registrationItem.getNote()!=null)
						if(!registrationItem.getNote().equalsIgnoreCase(request.getParameter("year"))){
							registrationItem = new RegistrationItemOtherField();
							registrationItem.setCompany(company);
							registrationItem.setIsValid(true);
							update=false;
						}
				}
				if(request.getParameter(otherField.getName())!=null){
					if(request.getParameter(otherField.getName()).trim().length()!=0)
						registrationItem.setContent(request.getParameter(otherField.getName()));
					else registrationItem.setContent("");
				}
				else registrationItem.setContent("");
				registrationItem.setOtherField(otherField);
				registrationItem.setMember(memberTemp2);
				if(request.getParameter("year")!=null){
					registrationItem.setNote(""+request.getParameter("year"));
				}
				
				if(update){
					registrationDelegate.update(registrationItem);
				}else{
					registrationDelegate.insert(registrationItem);
				}
			}
		}
		
		//Create Fields
		update=false;
		String[] data;
		List<RegistrationItemOtherField> infoRepeating = new ArrayList();
		if(listOfFieldsRepeating != null && listOfFieldsRepeating.size()>0){
			infoRepeating=registrationDelegate.findAllWithIndexing(company, member, request.getParameter("year")).getList();
		}
		for(String field : listOfFieldsRepeating){	
			data=request.getParameterValues(field);
			if(data==null)data=new String[1];
			for(int x=0 ; x<data.length;x++){
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
						registrationItem  = registrationDelegate.findByName(company, field, member.getId(),request.getParameter("year"),x);
					}
					if(registrationItem==null){
						registrationItem = new RegistrationItemOtherField();
						registrationItem.setCompany(company);
						registrationItem.setIsValid(true);
						update=false;
					}
					else{
						if(registrationItem.getNote()!=null)
							if(!registrationItem.getNote().equalsIgnoreCase(request.getParameter("year"))){
								registrationItem = new RegistrationItemOtherField();
								registrationItem.setCompany(company);
								registrationItem.setIsValid(true);
								update=false;
							}
							else{
								infoRepeating.remove(registrationItem);
							}
					}
					if(request.getParameter("year")!=null){
						registrationItem.setNote(""+request.getParameter("year"));
					}
					registrationItem.setOtherField(otherField);
					registrationItem.setMember(memberTemp2);
					registrationItem.setIndex(x);
					
					
					if(request.getParameter(otherField.getName())!=null){
						if(data[x].trim().length()!=0)
							registrationItem.setContent(data[x]);
						else registrationItem.setContent("");
					}
					else registrationItem.setContent("");
					
					
					if(update){
						
						registrationDelegate.update(registrationItem);
					}else{
		
						registrationDelegate.insert(registrationItem);
					}
				}
			}
		}
		if(infoRepeating.size()>0)
		for(RegistrationItemOtherField x : infoRepeating){
			registrationDelegate.delete(x);
		}

	
		
		//get registration items and refresh member object
		Member mem = memberDelegate.find(tempId2);
		memberDelegate.refresh(mem);
		info = new ArrayList<RegistrationItemOtherField>();
		info= mem.getRegistrationItemOtherFields();
		
		//sort registration items according to their sort order
		if(info!=null)
			SortingUtil.sortBaseObject("otherField.sortOrder", true,info );
		
		
		//this.sendEmail();
		
		return memberId;
	}
	
	public String update(Long submissionId){
		this.submissionId=submissionId;
		//get all parameters
		RegistrationItemOtherField registrationItem = new RegistrationItemOtherField();
		Enumeration temp = request.getParameterNames();
		OtherField newOtherField= new OtherField();
		OtherField otherField= new OtherField();
		encryptor = new PasswordEncryptor();
		Long tempId=null;

		this.initializeCompany();//set filenames for companies (need to recode per company)
		
		Member memberTemp2 =  memberDelegate.find(this.submissionId);
		member=memberTemp2;

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
					registrationItem  = registrationDelegate.findByName(company, field, member.getId(),request.getParameter("year"),null);
				}
				if(registrationItem==null){
					registrationItem = new RegistrationItemOtherField();
					registrationItem.setCompany(company);
					registrationItem.setIsValid(true);
					update=false;
				}
				else{
					if(registrationItem.getNote()!=null)
						if(!registrationItem.getNote().equalsIgnoreCase(request.getParameter("year"))){
							registrationItem = new RegistrationItemOtherField();
							registrationItem.setCompany(company);
							registrationItem.setIsValid(true);
							update=false;
						}
				}
				if(request.getParameter(otherField.getName())!=null){
					if(request.getParameter(otherField.getName()).trim().length()!=0)
						registrationItem.setContent(request.getParameter(otherField.getName()));
					else registrationItem.setContent("");
				}
				else registrationItem.setContent("");
				registrationItem.setOtherField(otherField);
				registrationItem.setMember(memberTemp2);
				if(request.getParameter("year")!=null){
					registrationItem.setNote(""+request.getParameter("year"));
				}
				
				if(update){
					registrationDelegate.update(registrationItem);
				}else{
					registrationDelegate.insert(registrationItem);
				}
			}
		}
		
		//Create Fields
		update=false;
		String[] data;
		List<RegistrationItemOtherField> infoRepeating = new ArrayList();
		if(listOfFieldsRepeating != null){
			infoRepeating=registrationDelegate.findAllWithIndexing(company, member, request.getParameter("year")).getList();
		}
		for(String field : listOfFieldsRepeating){	
			data=request.getParameterValues(field);
			if(data==null)data=new String[1];
			for(int x=0 ; x<data.length;x++){
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
						registrationItem  = registrationDelegate.findByName(company, field, member.getId(),request.getParameter("year"),x);
					}
					if(registrationItem==null){
						registrationItem = new RegistrationItemOtherField();
						registrationItem.setCompany(company);
						registrationItem.setIsValid(true);
						update=false;
					}
					else{
						if(registrationItem.getNote()!=null)
							if(!registrationItem.getNote().equalsIgnoreCase(request.getParameter("year"))){
								registrationItem = new RegistrationItemOtherField();
								registrationItem.setCompany(company);
								registrationItem.setIsValid(true);
								update=false;
							}
							else{
								infoRepeating.remove(registrationItem);
							}
					}
					if(request.getParameter("year")!=null){
						registrationItem.setNote(""+request.getParameter("year"));
					}
					registrationItem.setOtherField(otherField);
					registrationItem.setMember(memberTemp2);
					registrationItem.setIndex(x);
					
					
					if(request.getParameter(otherField.getName())!=null){
						if(data[x].trim().length()!=0)
							registrationItem.setContent(data[x]);
						else registrationItem.setContent("");
					}
					else registrationItem.setContent("");
					
					
					if(update){
						
						registrationDelegate.update(registrationItem);
					}else{
		
						registrationDelegate.insert(registrationItem);
					}
				}
			}
		}
		if(infoRepeating.size()>0)
		for(RegistrationItemOtherField x : infoRepeating){
			registrationDelegate.delete(x);
		}

		return SUCCESS;
	}

	public void setAllowedFieldsRepeating(String[] allowedFieldsRepeating) {
		this.allowedFieldsRepeating = allowedFieldsRepeating;
	}

	public String[] getAllowedFieldsRepeating() {
		return allowedFieldsRepeating;
	}

	public void setListOfFieldsRepeating(List<String> listOfFieldsRepeating) {
		this.listOfFieldsRepeating = listOfFieldsRepeating;
	}

	public List<String> getListOfFieldsRepeating() {
		return listOfFieldsRepeating;
	}


	
}
