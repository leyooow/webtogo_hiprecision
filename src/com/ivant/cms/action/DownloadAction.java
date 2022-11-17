package com.ivant.cms.action;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.delegate.SavedEmailDelegate;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.SavedEmail;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.constants.CompanyConstants;
import com.opensymphony.xwork2.Action;

/**
 * @author Isaac Arenas Pichay
 * @since Apr 23, 2014
 */
public class DownloadAction implements Action, ServletRequestAware,
		CompanyAware, ServletContextAware {

	private HttpServletRequest request;
	private Company company;
	private ServletContext servletContext;
	private String message;
	
	private static String EXPIRED = "expired";

	private static final SavedEmailDelegate savedEmailDelegate = SavedEmailDelegate
			.getInstance();

	@Override
	public String execute() throws Exception {

		if(company.getName().equalsIgnoreCase(CompanyConstants.TOTAL_QUEUE)){
			String url = request.getRequestURL().toString();
			String requestKey = request.getParameter("requestKey");
			String downloadLink = url+"?requestKey="+requestKey;
			SavedEmail savedEmail = savedEmailDelegate.findByDownloadLink(downloadLink, company);
			if(savedEmail!=null){
				long diff = new Date().getTime() - savedEmail.getCreatedOn().getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				Boolean isExpired = diffDays>=5;
				if(isExpired){
					setMessage("Download Link is already expired");
					return EXPIRED;
				}
				savedEmail.setDownloadDate(new Date());
				savedEmailDelegate.update(savedEmail);
				
				
			}
			
		}
		
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	@Override
	public void setCompany(Company company) {
		this.company = company;

	}

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
