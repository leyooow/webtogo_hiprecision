package com.ivant.cms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.MultiPageDelegate;
import com.ivant.cms.delegate.ReferralDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Referral;
import com.ivant.cms.enums.ReferralStatus;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.constants.CompanyConstants;
import com.ivant.utils.EmailUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ReferralAction extends  ActionSupport implements Action, Preparable, ServletRequestAware, 
ServletContextAware, CompanyAware, MemberAware, SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059790931975052250L;


	private Logger logger = Logger.getLogger(ReferralAction.class);
	
	
	private MultiPageDelegate multiPageDelegate = MultiPageDelegate.getInstance();
	
	private ReferralDelegate referralDelegate = ReferralDelegate.getInstance();
	
	
	private HttpServletRequest request;
	private ServletContext servletContext;
	private Company company;
	private Member member;
	private Map session;
	
	private String firstname;
	private String lastname;
	private String contact;
	private String email;
	
	
	private Long referralId;
	
	
	
	public String execute(){
		
		
		return Action.SUCCESS;
	}
	
	
	public String add(){
		
		//System.out.println("REFERRAL");
		
		if(company!=null && member!=null){
			
			Referral referral = new Referral();
			
				referral.setCreatedOn(new Date());
				referral.setIsValid(Boolean.TRUE);
				referral.setUpdatedOn(new Date());
				referral =  referralDetails(referral);
				referral.setStatus(ReferralStatus.PENDING);
			
			Long success = referralDelegate.insert(referral);
			if(success!=null){
				sendEmailToReferredClient(member, referral,Boolean.FALSE);
				sendEmailToReferrer(member,referral,Boolean.FALSE);
				sendEmailToCompanyAdmin(member,referral,Boolean.FALSE);
			}
			
			
		}
		return "success";
	}
	
	
	public String update(){
		
		if(company!=null && member!=null){
			
			Referral referral = referralDelegate.find(referralId);
			
			if(referral!=null){
					
				referral.setUpdatedOn(new Date());
				
				referral =  referralDetails(referral);

				Boolean updateSuccess = referralDelegate.update(referral);
				
				
				if(updateSuccess){
					
					sendEmailToReferredClient(member, referral,Boolean.TRUE);
					sendEmailToReferrer(member,referral,Boolean.TRUE);
					//sendEmailToCompanyAdmin(member,referral,Boolean.TRUE);
					
				}
			}
			
			
			
		}
		return SUCCESS;
		
	}




	private Referral referralDetails(Referral referral){
		
			referral.setCompany(company);
			referral.setContactNumber(contact);
			referral.setFirstname(firstname);
			referral.setLastname(lastname);
			referral.setEmail(email);
			referral.setReferredBy(member);
		
		return referral;
	}
	
	
	public String sendEmailToReferredClient(Member member,Referral referral,Boolean isUpdate) {	
	
		String emailTitle = "Referral at " + company.getNameEditable() ;
		
		if(isUpdate){
			emailTitle = "Updated Referral Details at "+company.getNameEditable();
		}
		
		if (member != null){
			EmailUtil.connect("smtp.gmail.com", 25);
			StringBuffer content = new StringBuffer();
			content.append("Hi " + referral.getFirstname() + " " + referral.getLastname()+",");
			
			content.append("<br><br>");
			content.append("<table>");
			content.append("<tr><td colspan='4'>" +
					"<br>" + "We would like to inform you that You were referred by " +
					"<br><br>" + member.getFirstname() +" "+member.getLastname() + " at "+company.getNameEditable()+
				//	"<br><br>Kindly visit our official website at " + company.getServerName() + "." +
				//	"<br><br>And Discover Cancun" +
					"</td></tr>");
			content.append("</table>");
			
			
			content.append("<br><br>Thank you.<br><br><br>All the Best, <br><br>");
			content.append("The "+company.getNameEditable()+" Team");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");
			
			
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", referral.getEmail(), 
					emailTitle
					, content.toString(),null)){
				return Action.SUCCESS;
			}
		}
		return Action.ERROR; 
	}
	
	
	public String sendEmailToReferrer(Member member,Referral referral,Boolean isUpdate) {	
		String emailTitle = "You have successfully Added a new Referral";
		
		if(isUpdate){
			emailTitle = "You have successfully Updated a Referral details";
		}
		if (member != null){
			EmailUtil.connect("smtp.gmail.com", 25);
			StringBuffer content = new StringBuffer();
			content.append("Hi " + member.getFirstname() + " " + member.getLastname()+",");
			
			content.append("<br><br>");
			content.append("<table>");
			content.append("<tr><td colspan='4'>");
				content.append("You have successfuly referred a new Client. Our team will verify this Referral.<br>");
			content.append("</td></tr>");
			content.append("<tr>");
			content.append("<tr><td colspan='4'>&nbsp;</td></tr>");
				content.append("<td colspan='4'><strong>Referral Details</strong></td>");	
			content.append("</tr>");
			content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>First Name</td>");
				content.append("<td>:</td>");
				content.append("<td>"+referral.getFirstname()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>Last Name</td>");
				content.append("<td>:</td>");
				content.append("<td>"+referral.getLastname()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>Email</td>");
				content.append("<td>:</td>");
				content.append("<td>"+referral.getEmail()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>Contact Number</td>");
				content.append("<td>:</td>");
				content.append("<td>"+referral.getContactNumber()+"</td>");
			content.append("</tr>");
			content.append("<tr><td colspan='4'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='4'>&nbsp;</td></tr>");
			
			content.append("</table>");
		
			
			content.append("<br><br>Thank you.<br><br><br>All the Best, <br><br>");
			content.append("The "+company.getNameEditable()+" Team");
			content.append("<br><br><strong style='color:red'>This is a system generated email. Please do not reply to this email.</strong>");
			
			
			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", member.getEmail(), 
					emailTitle + " at " + company.getNameEditable() 
					, content.toString(),null)){
				return Action.SUCCESS;
			}
		}
		return Action.ERROR; 
	}
	
	
	
	
	
	private String sendEmailToCompanyAdmin(Member member, Referral referral,Boolean isUpdate) {
		String emailTitle = "New Referral - "+member.getFullName();

		if (member != null){
			EmailUtil.connect("smtp.gmail.com", 25);
			StringBuffer content = new StringBuffer();
			content.append("<br><br>");
			content.append("<table>");
			content.append("<tr><td colspan='4'>");
			content.append("New Referral created by <strong>"+member.getFirstname()+"</strong>.<br>");
			content.append("</td></tr>");
			content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>First Name</td>");
				content.append("<td>:</td>");
				content.append("<td>"+referral.getFirstname()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>Last Name</td>");
				content.append("<td>:</td>");
				content.append("<td>"+referral.getLastname()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>Email</td>");
				content.append("<td>:</td>");
				content.append("<td>"+referral.getEmail()+"</td>");
			content.append("</tr>");
			content.append("<tr>");
				content.append("<td>&nbsp;</td>");
				content.append("<td>Contact Number</td>");
				content.append("<td>:</td>");
				content.append("<td>"+referral.getContactNumber()+"</td>");
			content.append("</tr>");
			content.append("<tr><td colspan='4'>&nbsp;</td></tr>");
			content.append("<tr><td colspan='4'>&nbsp;</td></tr>");
			
			content.append("</table>");
		
			
			content.append("<br><br>Thank you.<br><br><br>All the Best, <br><br>");

			if(EmailUtil.sendWithHTMLFormat("noreply@webtogo.com.ph", company.getEmail().split(","), 
					emailTitle
					, content.toString(),null)){
				return Action.SUCCESS;
			}
		}
		return Action.ERROR; 
		
		
		
		
	}
	
	
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContact() {
		return contact;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}


	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = request;
		
	}


	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}


	@Override
	public void setCompany(Company company) {
		this.company = company;
		
	}


	@Override
	public void setMember(Member member) {
		this.member = member;	
		
	}


	@Override
	public void setSession(Map arg0) {
		this.session = session;
		
	}


	public void setReferralId(Long referralId) {
		this.referralId = referralId;
	}


	public Long getReferralId() {
		return referralId;
	}

	
	

}
