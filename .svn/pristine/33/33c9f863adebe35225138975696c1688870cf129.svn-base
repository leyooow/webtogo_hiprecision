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



public class CompanyRegistrationAction extends AbstractBaseRegistrationAction{

	public CompanyRegistrationAction(){
		
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
		//System.out.println("PASSWORD REQUEST "+request.getParameter("Password"));
		//System.out.println("Family REQUEST "+request.getParameter("Family Name"));
		//System.out.println("Given REQUEST "+request.getParameter("Given Name"));
		requiredMemberInfo = new  TreeMap<String, String>();
		if(request.getParameter("Family Name")!=null){
			requiredMemberInfo.put("Family Name", request.getParameter("Family Name"));
		}
		if(request.getParameter("Given Name")!=null){
			requiredMemberInfo.put("Given Name", request.getParameter("Given Name"));
		}
		if(request.getParameter("Full Name")!=null){
			requiredMemberInfo.put("Full Name", request.getParameter("Full Name"));
		}
		if(request.getParameter("Name on Certificate")!=null){
			requiredMemberInfo.put("Name on Certificate", request.getParameter("Name on Certificate"));
		}
		
		
		requiredMemberInfo.put("Username", request.getParameter("Email"));
		requiredMemberInfo.put("Password", request.getParameter("Password"));
		requiredMemberInfo.put("Email", request.getParameter("Email"));
		requiredMemberInfo.put("Phone", request.getParameter("Mobile No."));
		//System.out.println("Name on Certificate  REQUEST "+requiredMemberInfo.get("Name on Certificate"));
		//this is set for the subject and body of the email
		requiredMemberInfo.put("Subject", "REGISTRATION: "+company.getTitle() );
		if(this.company.getId()==192){
			requiredMemberInfo.put("Message", "" +
					"Thank You! You have succesfully registered." +
				"\n\n\n-------------------------------\n This is an auto generated email - Do not reply through this email\n");
		}else{
			requiredMemberInfo.put("Message", "" +
					"Thank You! You have succesfully registered." +
				"\nThis is your username: "+request.getParameter("Email")+" and password: "+ request.getParameter("Password")+
				"\n\n\n-------------------------------\n This is an auto generated email - Do not reply through this email\n");
		}
		
		

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
