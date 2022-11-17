package com.ivant.cms.helper;

import java.util.Date;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.NwdiPatientDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.NwdiPatient;
import com.ivant.utils.PasswordEncryptor;

public class NwdiHelper {
	
	public static boolean toInludeSession(ServletRequest request){
		
		if(!toInludeSession()) return false;
		
		boolean toIncludeSession = false;
		
		if(request != null){
			if(request.getServerName() != null)
				return request.getServerName().toLowerCase().contains("nwdi") || request.getServerName().toLowerCase().contains("127.0.0.1");
		}else{
			return true;
		}
		
		return toIncludeSession;
	}
	
	/**
	 * Include only session in production (i.e. in our linux server)
	 * @return
	 */
	public static boolean toInludeSession(){
		//return true;
		return System.getProperty("os.name").toUpperCase().contains("LINUX");
	}
	
	
	public static final String BIRTH_DATE_PATTERN = "yyyy-MM-dd";
	public static final DateTimeFormatter formatter = DateTimeFormat.forPattern(BIRTH_DATE_PATTERN);
	
	public static Member checkCredential(String username, String password, Company company){
		Member member = null;
		if(StringUtils.isNoneBlank(username, password)){
			MemberDelegate memberDelegate = MemberDelegate.getInstance();
			PasswordEncryptor encryptor = new PasswordEncryptor();
			member = memberDelegate.findAccount(username, company);
			
			/** existing user **/
			if(member != null){
				// check password
				if(!encryptor.encrypt(password).equals(member.getPassword())){
					member = null;
				}
			}
			/** new user, check in remote database **/
			else{
				NwdiPatientDelegate nwdiPatientDelegate = NwdiPatientDelegate.getInstance();
				DateTime birthDate = null;
				try{
					birthDate = formatter.parseDateTime(password);
				}catch(Exception e){}
				
				NwdiPatient patient = nwdiPatientDelegate.find(username, birthDate);
				
				/** found **/
				if(patient != null){
					member = new Member();
					member.setUsername(patient.getPatientNo());
					member.setPassword(encryptor.encrypt(password));
					member.setVerified(true);
					member.setActivated(true);
					member.setEmail(StringUtils.isBlank(patient.getEmail()) ? "" : patient.getEmail());
					member.setCompany(company);
					member.setDateJoined(new Date());
					member.setFirstname(patient.getFirstName());
					member.setMiddlename(patient.getMiddleName());
					member.setLastname(patient.getLastName());
					member.setNwdiPatientId(patient.getId());
					member.setInfo1(formatter.print(patient.getBirthDate()));
					member = memberDelegate.find(memberDelegate.insert(member));
				}
			}
		}
		
		return member;
	}
	
}
