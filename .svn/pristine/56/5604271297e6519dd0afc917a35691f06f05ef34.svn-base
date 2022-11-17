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
public class BrightRegistrationAction extends AbstractBaseRegistrationAction{

	public BrightRegistrationAction(){
		
	}
	@Override
	public void prepare(List<String>  listOfFieldsRepeating,List<String> listOfFields,List<String> listOfFiles,HttpServletRequest request, Company company, Member member
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
	@Override
	public void initializeCompany(){
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
		//mapFile = new  TreeMap<String, File>();
		//mapFile.put("digital", digital);
		//mapFile.put("passport", passport);
		//mapFile.put("visa", visa);
		//mapFile.put("birth", birth);
		//mapFile.put("financial", financial);
		//mapFile.put("academic", academic);
		//mapFile.put("ecoe", ecoe);
		//mapFileName = new  TreeMap<String, String>();
		//mapFileName.put("digital", "digital_"+digitalFileName);
		//mapFileName.put("passport", "passport_"+passportFileName);
		//mapFileName.put("visa", "visa_"+visaFileName);
		//mapFileName.put("birth","birth_"+birthFileName);
		//mapFileName.put("financial","financial_"+financialFileName);
		//mapFileName.put("academic", "academic_"+academicFileName);
		//mapFileName.put("ecoe", "ecoe_"+ecoeFileName);
	}
	@Override
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
	
}
