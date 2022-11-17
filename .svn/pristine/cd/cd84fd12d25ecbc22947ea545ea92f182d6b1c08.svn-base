package com.ivant.cms.action.admin.dwr;

import java.util.List;

import org.omg.PortableInterceptor.SUCCESSFUL;

import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.utils.PasswordEncryptor;

public class DWRMemberAction extends AbstractDWRAction {
	
	public boolean validatePassword(String username, String password) {
		boolean isIncorrectPassword = true;
		Member member = memberDelegate.findByUsername(company, username);
		PasswordEncryptor encryptor = new PasswordEncryptor();
		if(member.getPassword().equalsIgnoreCase(encryptor.encrypt(password)))
			return false;
		return isIncorrectPassword;		
	}
	
	public String saveMemberLog(String remarks) {
		MemberLog memberLog = new MemberLog();
		memberLog.setCompany(company);
		memberLog.setMember(member);
		memberLog.setRemarks(remarks);
		memberLogDelegate.insert(memberLog);
		return "SUCCESS";
	}
	
	public boolean checkIfLogExists(String remarks) {
		Object sessMemberId = session.getAttribute(MemberInterceptor.MEMBER_REQUEST_KEY);
		member = (Member) sessMemberId;
		List<MemberLog> logs = memberLogDelegate.findAllByMemberAndRemarks(company, member, remarks).getList();
		if(logs.size() == 0) {
			saveMemberLog(remarks); 
			return false;
		}
		return true; 
	}
	
	public List<Member> getAllMemberByPageModule(String pageModule) {
		return memberDelegate.findAllByPageModule(pageModule, company).getList();
	}
	
}
