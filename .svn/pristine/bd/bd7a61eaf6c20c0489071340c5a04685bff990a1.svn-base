package com.ivant.cms.action.admin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.simple.JSONObject;

import com.ivant.cms.delegate.MemberDelegate;
import com.ivant.cms.delegate.MemberImagesDelegate;
import com.ivant.cms.delegate.PromoCodeDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.MemberImages;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class MemberImagesAction extends ActionSupport implements Preparable, ServletRequestAware, UserAware, CompanyAware{
	
	private static final long serialVersionUID = 1L;
	private MemberDelegate memberDelegate = MemberDelegate.getInstance();
	private MemberImagesDelegate memberImagesDelegate = MemberImagesDelegate.getInstance();
	private PromoCodeDelegate promoCodeDelegate = PromoCodeDelegate.getInstance();
	private HttpServletRequest request;
	
	private List<MemberImages> memberimages;
	private MemberImages memberimage;
	private String membername
	, imagename
	, promocode
	, ornumber
	, comment;
	private Boolean status;
	private Company company;
	private User user;
	private String errorType;
	private Long id;
	
	private InputStream inputStream;
	
	public void prepare() throws Exception{
		membername = request.getParameter("user");
		id = Long.parseLong(request.getParameter("memberId"));
		memberimage = memberImagesDelegate.find(id);
	}
	@Override
	public String execute() throws Exception{
		return Action.NONE;
	}
	
	private boolean commonParamsValid(){
		if(user.getCompany() == null){
			return false;
		}
		return true;
	}
	
	/*public void setServerRequest(HttpServletRequest request){
		this.request = request;
	}*/
		
	public String save(){
		
		if(!commonParamsValid()){
			
			return Action.ERROR;
		}
		
		MemberImages exist = memberImagesDelegate.findByMember(company, membername);
		
		try{
			if(exist == null){
				
			}else{
				errorType = "Duplicate";
			}
		}catch(Exception e){
			
		}

		return Action.SUCCESS;
		
	}
	
	@SuppressWarnings("unchecked")
	public String update() {
		
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		
		String comment = request.getParameter("comment");
		String status = request.getParameter("status");
		
		memberimage.setComment(comment);
		memberimage.setStatus(status);
		memberImagesDelegate.update(memberimage);
		
		setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		
		return SUCCESS;
	}
	
	public MemberImages getMemberImage(){
		return memberimage;
	}
	
	public void setMemberImages(MemberImages memberimages){
		this.memberimage = memberimage;
	}
	
	public Company getCompany(){
		return company;
		
	}
	
	public void setCompany(Company company){
		this.company = company;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public List<MemberImages> getMemberImages(){
		return memberimages;
	}
	
	public void setMemberImages(List<MemberImages> memberimages){
		this.memberimages = memberimages;
	}
	
	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
