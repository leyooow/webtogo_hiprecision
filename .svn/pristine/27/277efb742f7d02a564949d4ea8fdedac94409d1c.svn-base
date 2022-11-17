package com.ivant.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.ivant.cms.delegate.MemberTypeDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MemberType;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberTypeAction extends ActionSupport implements Preparable, ServletRequestAware, UserAware, CompanyAware{

	private static final long serialVersionUID = 5004656775667909461L;
	private HttpServletRequest request;
	private User user;
	private Company company;
	
	private MemberTypeDelegate memberTypeDelegate = MemberTypeDelegate.getInstance();
	
	private Long memberTypeId;
	
	private MemberType memberType;
	
	@Override
	public void prepare() throws Exception {
		if(memberTypeId != null)
			memberType = memberTypeDelegate.find(memberTypeId);
		
		if(memberType == null)
			memberType = new MemberType();
	}
	
	
	public String save() {
		
		
		memberType.setCompany(company);
		if(memberType.getId() == null)
			memberTypeDelegate.insert(memberType);
		else 
			memberTypeDelegate.update(memberType);
		return SUCCESS;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}


	public void setMemberTypeId(Long memberTypeId) {
		this.memberTypeId = memberTypeId;
	}
	
	public List<MemberType> getMemberTypes() {
		
		return memberTypeDelegate.findAllWithPaging(company, -1, -1, null).getList();
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
