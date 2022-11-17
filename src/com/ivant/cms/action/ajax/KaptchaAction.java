package com.ivant.cms.action.ajax;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ivant.cms.entity.Company;
import com.ivant.cms.interfaces.CompanyAware;
import com.opensymphony.xwork2.Action;

public class KaptchaAction 
		implements Action, ServletRequestAware, CompanyAware, ServletContextAware{

	private Company company;
	private ServletContext servletContext;
	private HttpServletRequest request;
	private String errorMsg;
	private InputStream inputStream;
	
	public String execute() {
		
		JSONObject obj = new JSONObject();
		
		String kaptchaReceived = (String) request.getParameter("kaptcha");
		//System.out.println("kaptchaReceived: " + kaptchaReceived);
		if (kaptchaReceived != null) {
			String kaptchaExpected = (String) request
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			//System.out.println("kaptchaExpected: " + kaptchaExpected);
			if (kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
				setErrorMsg("invalid captcha");
				obj.put("valid", true);
			} else {
				obj.put("valid", false);
			}
		}

		inputStream = new ByteArrayInputStream(obj.toJSONString().getBytes());
		
		return Action.SUCCESS;
	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	@Override
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		servletContext = arg0;
	}


	public Company getCompany() {
		return company;
	}


	public ServletContext getServletContext() {
		return servletContext;
	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public InputStream getInputStream() {
		return inputStream;
	}

}
