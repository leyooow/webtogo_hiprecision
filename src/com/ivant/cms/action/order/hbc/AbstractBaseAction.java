package com.ivant.cms.action.order.hbc;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.interfaces.CompanyAware;
import com.ivant.cms.interfaces.MemberAware;
import com.ivant.cms.interfaces.NotificationMessageAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Common action method and local variables.
 * 
 * @author Mark Kenneth M. Rañosa
 *
 */
public abstract class AbstractBaseAction extends ActionSupport
	implements Preparable, SessionAware, ServletRequestAware, ServletContextAware,
	CompanyAware, MemberAware, NotificationMessageAware{
	
	private static final long serialVersionUID = -8728479002248656141L;
	
	public final String SAVE = "save";
	public final String EDIT = "edit";
	public final String LOAD = "load";
	
	/** The established communication between the front and back end */
	@SuppressWarnings("unchecked")
	protected Map session;
	
	/** The message recieved from the user agent */
	protected HttpServletRequest request;
	
	/** Name of the servlet context object */
	protected String servletContextName;
	
	/** An object that contains a servlet's view of the Web application within
	 * which the servlet is running */	 
	protected ServletContext servletContext;
	
	/** The entity engaging business with webtogo */
	protected Company company;
	
	/** The person registered in the company */
	protected Member member;
	
	/** Status response from the server for the user */
	protected String notificationMessage;

	/** Path of the software that services HTTP requests */
	protected String httpServer;
	
	/** True if web app is running locally, otherwise false */
	protected boolean isLocal;
	
	/**
	 * Populates new httpServer URL to be redirected to.
	 */
	protected void initHttpServerUrl() {
		servletContextName = servletContext.getServletContextName();
		isLocal = (request.getRequestURI().startsWith("/" + servletContextName)) ? true
				: false;
		httpServer = (isLocal) ? ("http://" + request.getServerName() + ":"
				+ request.getServerPort() + "/" + servletContextName
				+ "/companies/" + company.getDomainName())
				: ("http://" + request.getServerName());
	}
	
	/**
	 * Sets the message to notify the user of current cart action.
	 * 
	 * @param message
	 *            - notification for the user
	 */
	protected void updateNotificationMessage(String message) {
		notificationMessage = "";
		notificationMessage += message;

		request.getSession().setAttribute("notificationMessage",
				notificationMessage);
	}
	
	/**
	 * Returns true if specified parameter is null, otherwise false.
	 * 
	 * @param input
	 *            - any {@link Object} value
	 * 
	 * @return - true if specified parameter is null, otherwise false
	 */
	protected boolean isNull(Object param) {
		return null == param;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setSession(Map session) {
		this.session = session;
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
	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
}
