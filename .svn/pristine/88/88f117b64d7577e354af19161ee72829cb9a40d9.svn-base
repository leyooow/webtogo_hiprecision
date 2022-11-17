package com.ivant.cms.action.company.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.ivant.cart.action.AbstractBaseAction;
import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberLogDelegate;
import com.ivant.cms.entity.MemberLog;
import com.ivant.cms.helper.NwdiHelper;
import com.ivant.cms.interceptors.MemberInterceptor;
import com.ivant.cms.interceptors.UserInterceptor;
import com.ivant.utils.PasswordEncryptor;
import com.opensymphony.xwork2.Action;

/**
 * 
 * 
 * @author Eric John Apondar
 * @since Mar 17, 2017
 */
public class NwdiJSONAction extends AbstractBaseAction implements Action{

	private static final long serialVersionUID = -5499970571905957154L;

	protected Logger logger = Logger.getLogger(getClass());
	
	private final MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private final MemberLogDelegate memberLogDelegate = MemberLogDelegate.getInstance();
	
	

	private InputStream inputStream;
	
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String login(){
		JSONObject json = new JSONObject();
		json.put("result", ERROR);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (!isNull(username) && !isNull(password)) {
			
			member = NwdiHelper.checkCredential(username, password, company);

			if (!isNull(member)) {
					
					session.put(MemberInterceptor.MEMBER_SESSION_KEY, member.getId());
					session.put(UserInterceptor.USER_SESSION_KEY, member.getId());
					request.setAttribute("member", member);
					session.put("member", member);

					final MemberLog memberLog = new MemberLog();
					memberLog.setCompany(company);
					memberLog.setMember(member);
					memberLog.setRemarks("Logged In");
					memberLogDelegate.insert(memberLog);
					json.put("result", SUCCESS);
			} 
		}
		
		setInputStream(new ByteArrayInputStream(json.toJSONString().getBytes()));
		
		return "stream";
			
	}
	
	@SuppressWarnings("unchecked")
	public String logout(){
		JSONObject json = new JSONObject();
		json.put("result", SUCCESS);

		session.remove(MemberInterceptor.MEMBER_SESSION_KEY);
		session.remove(UserInterceptor.USER_SESSION_KEY);
		session.remove("member");
		
		setInputStream(new ByteArrayInputStream(json.toJSONString().getBytes()));
		
		return "stream";
			
	}
	
	@SuppressWarnings("unchecked")
	public String changePassword(){
		JSONObject json = new JSONObject();
		json.put("result", ERROR);
		
		if(member != null){
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			String confirmNewPassword = request.getParameter("confirmNewPassword");
			PasswordEncryptor encryptor = new PasswordEncryptor();
			if(member.getPassword().equals(encryptor.encrypt(oldPassword))){
				if(StringUtils.isNotBlank(newPassword) && newPassword.equals(confirmNewPassword)){
					member.setPassword(encryptor.encrypt(newPassword));
					if(memberDelegate.update(member)) json.put("result", SUCCESS);
				}else{
					json.put("result", "New password mismatched.");
				}
			}else{
				json.put("result", "Wrong old password.");
			}
		}
		
		setInputStream(new ByteArrayInputStream(json.toJSONString().getBytes()));
		
		return "stream";
			
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
