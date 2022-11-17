package com.ivant.cms.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.criterion.Order;

import com.ivant.cms.delegate.MemberTypeDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.CompanySettings;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MessageAction 
	extends ActionSupport 
	implements Preparable, ServletRequestAware, UserAware, CompanyAware{

	private static final long serialVersionUID = -7809920885748374572L;

	private MemberTypeDelegate memberTypeDelegate = MemberTypeDelegate.getInstance();
	
	private Company company;
	private User user;
	private HttpServletRequest request;
	
	private MemberType memberType;
	
	private Long memberTypeId;
	
	private String subject;
	
	private String body;
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	public String sendMessage() {
		List<Member> members = new ArrayList<Member>();
		if(CompanyConstants.UNIORIENT_AGENTS.equalsIgnoreCase(company.getName())){			
			if(memberTypeId != null){
				//memberTypeId == 0 it means all agent
				if(memberTypeId == 0L){
					List<MemberType> memberTypes = memberTypeDelegate.findAllWithPaging(company, -1, -1, null).getList();
					for(MemberType memberType:memberTypes){
						members.addAll(memberType.getMembers());
					}
				}else{
					
					memberType = memberTypeDelegate.find(memberTypeId);
					members = memberType.getMembers();
				}
				
			}
									
		}else{
			if(memberTypeId != null)
				memberType = memberTypeDelegate.find(memberTypeId);		
									
			members = memberType.getMembers();					
		}
		
		CompanySettings settings = company.getCompanySettings();
		
		if(StringUtils.isEmpty(settings.getEmailUserName())) {
			EmailUtil.connect("smtp.gmail.com", 587);
		} else {
			
			EmailUtil.connect(settings.getSmtp(), Integer.parseInt(settings.getPortNumber()), settings.getEmailUserName(), settings.getEmailPassword());
		}
		if(members != null && members.size() > 0) {
			String[] toArray = new String[members.size()];
			int i=0;
			for(Member member: members) {
				toArray[i] = member.getEmail();
				i++;
			}
			if(CompanyConstants.UNIORIENT_AGENTS.equalsIgnoreCase(company.getName())){	
				if(!StringUtils.isEmpty(settings.getEmailUserName())) {
					EmailUtil.send(settings.getEmailUserName(), toArray, subject, body, null);
				}else{
					EmailUtil.send("system@ivant.com", toArray, subject, body, null);
				}
			}else{
				EmailUtil.send(company.getEmail(), toArray, subject, body, null);
			}
			
		}
		return SUCCESS;
	}

	public List<MemberType> getMemberTypes() {
		Order[] orders = {Order.asc("name")};
		return memberTypeDelegate.findAllWithPaging(company, -1, -1, orders).getList();
	}
	
	public void setMemberTypeId(Long memberTypeId) {
		this.memberTypeId = memberTypeId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;
	}

}
