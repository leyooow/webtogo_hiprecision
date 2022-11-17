package com.ivant.cms.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ivant.cms.entity.Company;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class ContextParamsInterceptor implements Interceptor {

	private static final long serialVersionUID = -4003479558053790493L;
	private static final Logger logger = Logger.getLogger(ContextParamsInterceptor.class);
	
	public void destroy() {
		logger.debug("DESTROYING CONTEXT PARAMS INTERCEPTOR...");
	}

	public void init() {
		logger.debug("CREATING CONTEXT PARAMS INTERCEPTOR...");
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		ServletContext servletContext = ServletActionContext.getServletContext();
		boolean local = (request.getServerName().equals("localhost")) ? true : false;
		
		Map<String, String> contextParams = new HashMap<String, String>();
//		Company company = (Company) session.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		Company company = (Company) request.getAttribute(CompanyInterceptor.COMPANY_REQUEST_KEY);
		
		
		// generate the http server name
		String httpServer = "";
		String protocol = request.isSecure() ? "https://" : "http://";
		
		if(local) {
			httpServer = protocol + request.getServerName() +":"+ request.getServerPort() + "/" + servletContext.getServletContextName();
		}
		else {
			String companyServerName = (company != null) ? company.getServerName() : "webtogo.com.ph";
			httpServer = protocol + companyServerName;
		}
					
		contextParams.put("HTTP_SERVER", httpServer);
		
		// generate the image path
		String imagePath = "";
		if(local) {
			imagePath = (company != null) ? (httpServer + "/companies/" + company.getName()) :
											(httpServer + "/admin"); 
		}
		else {
			imagePath = httpServer;
		}
		
		
		contextParams.put("IMAGE_PATH", imagePath);
		contextParams.put("FILE_PATH", imagePath);
		request.setAttribute("contextParams", contextParams);
					
		return arg0.invoke();
	}
}
