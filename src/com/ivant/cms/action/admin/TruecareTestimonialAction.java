package com.ivant.cms.action.admin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.simple.JSONObject;

import com.ivant.cms.delegate.TruecareTestimonialDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.TruecareTestimonial;
import com.ivant.cms.entity.User;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.UserAware;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class TruecareTestimonialAction extends ActionSupport implements Preparable, ServletRequestAware, UserAware, CompanyAware{
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private TruecareTestimonialDelegate truecareDelegate = TruecareTestimonialDelegate.getInstance();
	private List<TruecareTestimonial> truecare_testimonials;
	private TruecareTestimonial truecare_testimonial;
	private String name
	, email
	, testimonial;
	private Company company;
	private User user;
	private String errorType;
	private Long id;
	
	private InputStream inputStream;
	
	public void prepare() throws Exception{
		name = request.getParameter("hidden_name");
		email = request.getParameter("hidden_email");
		testimonial = request.getParameter("testimonial");
		
		try {
			id = Long.parseLong(request.getParameter("rating"));
			truecare_testimonial = truecareDelegate.find(id);
		}
		catch(Exception e) {
			truecare_testimonial = new TruecareTestimonial();
		}
		
		/*id = Long.parseLong(request.getParameter("memberId"));
		truecare_testimonial = truecareDelegate.find(id);*/
	}
	@Override
	public String execute() throws Exception{
		return Action.NONE;
	}
	
	private boolean commonParamsValid(){
		/*if(user.getCompany() == null){
			return false;
		}*/
		return true;
	}
	
	/*public void setServerRequest(HttpServletRequest request){
		this.request = request;
	}*/
		
	public String save(){
		
		if(!commonParamsValid()){
			
			return Action.ERROR;
		}
		
		TruecareTestimonial exist = truecareDelegate.findByEmail(company, email);
		
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
		
		truecare_testimonial.setName(comment);
		truecare_testimonial.setTestimonial(status);
		truecareDelegate.update(truecare_testimonial);
		
		setInputStream(new ByteArrayInputStream(obj.toJSONString().getBytes()));
		
		return SUCCESS;
	}
	public TruecareTestimonial getTestimonials(){
		return truecare_testimonial;
	}
	
	public void setTestimonials(TruecareTestimonial truecare_testimonial){
		this.truecare_testimonial = truecare_testimonial;
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
	
	public List<TruecareTestimonial> getTruecareTestimonials(){
		return truecare_testimonials;
	}
	
	public void setTestimonial(List<TruecareTestimonial> truecare_testimonials){
		this.truecare_testimonials = truecare_testimonials;
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
