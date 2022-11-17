package com.ivant.cms.action;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Language;
import com.ivant.cms.entity.Member;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.LanguageAware;
import com.ivant.cms.interfaces.MemberAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


public class MemberAction  extends ActionSupport implements Action, Preparable, ServletRequestAware, 
ServletContextAware, CompanyAware, MemberAware, SessionAware, LanguageAware {
	MemberDelegate memberDelegate=MemberDelegate.getInstance();
	Member member;
	private String updateMemberInformation(){
		
		//System.out.println("UPDATE OF MEMBER HERE....."+member);
		
		return Action.SUCCESS;
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCompany(Company company) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMember(Member member) {
		this.member = member;		
	}
	
	public Member getMember(Member member){
		return member;
	}

	@Override
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLanguage(Language language) {
		// TODO Auto-generated method stub
		
	}

}
